package com.example.termend.fragment;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.termend.MainActivity;
import com.example.termend.R;
import com.example.termend.activity.MineActivity;
import com.example.termend.entity.Result;
import com.example.termend.entity.UserInfo;
import com.example.termend.utils.ProductUtils;
import com.example.termend.utils.ResultUtils;
import com.example.termend.utils.URLUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MineFragment extends Fragment {

    private TextView tv_name;
    private TextView tv_email;
    private TextView tv_phone;
    private ImageView iv_icon;
    private ImageView iv_name;
    private ImageView iv_email;
    private ImageView iv_phone;
    private Handler handler;
    private ActivityResultLauncher launcher;
    private Context context;
    private static UserInfo userInfo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,null);
        context = this.getContext();
        initHandler();
        initViews(view);
        initData();
        initEvents();
        initLauncher();
        return view;
    }


    private void initLauncher() {
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // 如果返回正常的访问
                        if (result.getResultCode() == RESULT_OK) {
                            // 获取图片
                            Uri uri = result.getData().getData();
                            // 通过URI获得一个BitMap
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                                iv_icon.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // 上传至服务器
                            new Thread(){
                                @Override
                                public void run() {
                                    try {
                                        Log.i("termEnd","开始上传头像"+ResultUtils.userInfo.getPhoneNumber());
                                        ProductUtils.uploadAvatar(uri,ResultUtils.userInfo.getPhoneNumber(),context);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        }
                    }
                }
        );
    }

    private void initEvents() {
        iv_icon.setOnClickListener(v->{
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            );
            launcher.launch(intent);
        });
        iv_email.setOnClickListener(v->{
            AlertDialog.Builder customDialog= new AlertDialog.Builder(this.getContext());
            customDialog.setTitle("修改邮箱");
            //在activity里面直getLayOutInflater
            //如果要设置自定义的布局就不要设置message！！！！！！！！
            View view=getLayoutInflater().inflate(R.layout.alert_alter,null);
            //给自定义的视图绑定事件使用view.findViewById
            EditText edt= view.findViewById(R.id.edt_alter);
            TextView tv= view.findViewById(R.id.tv_alter);
            customDialog.setView(view);
            //防止别人从别的地方关闭
            customDialog.setCancelable(false);
            customDialog.setNegativeButton("cancel",null);
            customDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tv_email.setText(edt.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("termEnd","开始修改邮箱");
                            alterMessage("email",edt.getText().toString());
                            Log.i("termEnd","修改邮箱完成");
                        }
                    }).start();
                }
            });
            AlertDialog dialog = customDialog.create();
            dialog.show();
        });
        iv_name.setOnClickListener(v->{
            AlertDialog.Builder customDialog= new AlertDialog.Builder(this.getContext());
            customDialog.setTitle("修改名字");
            //在activity里面直getLayOutInflater
            //如果要设置自定义的布局就不要设置message！！！！！！！！
            View view=getLayoutInflater().inflate(R.layout.alert_alter,null);
            //给自定义的视图绑定事件使用view.findViewById
            EditText edt= view.findViewById(R.id.edt_alter);
            TextView tv= view.findViewById(R.id.tv_alter);
            customDialog.setView(view);
            //防止别人从别的地方关闭
            customDialog.setCancelable(false);
            customDialog.setNegativeButton("cancel",null);
            customDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tv_name.setText(edt.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("termEnd", "开始修改名字");
                            alterMessage("name", edt.getText().toString());
                            Log.i("termEnd", "修改名字完成");
                        }
                    }).start();
                }
            });
            AlertDialog dialog = customDialog.create();
            dialog.show();
        });
        iv_phone.setOnClickListener(v->{
            AlertDialog.Builder customDialog= new AlertDialog.Builder(this.getContext());
            customDialog.setTitle("修改手机号");
            //在activity里面直getLayOutInflater
            //如果要设置自定义的布局就不要设置message！！！！！！！！
            View view=getLayoutInflater().inflate(R.layout.alert_alter,null);
            //给自定义的视图绑定事件使用view.findViewById
            EditText edt= view.findViewById(R.id.edt_alter);
            TextView tv= view.findViewById(R.id.tv_alter);
            edt.setInputType(EditorInfo.TYPE_CLASS_PHONE);
            customDialog.setView(view);
            //防止别人从别的地方关闭
            customDialog.setCancelable(false);
            customDialog.setNegativeButton("cancel", null);
            customDialog.setPositiveButton("ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (edt.getText().length()==11) {
                        tv_phone.setText(edt.getText().toString());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("termEnd","开始修改手机号");
                                    alterMessage("phone",edt.getText().toString());
                                    Log.i("termEnd","修改手机号完成");
                                }
                            }).start();
                    }else {
                        Toast.makeText(getContext(),"输入的手机号有误",Toast.LENGTH_LONG).show();
                    }
                }
            });
            AlertDialog dialog = customDialog.create();
            dialog.show();
        });
    }
    private Result<UserInfo> alterMessage(String label, String message){
        HttpURLConnection connection = null;
        InputStream inputStream =null;
        Result<UserInfo> result=null;
        try {
            URL url = new URL(URLUtil.PATH+"alter?phone="+ResultUtils.userInfo.getPhoneNumber()+"&label="+label+"&para="+message);
            Log.i("termEnd",url.getPath());
            connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod(URLUtil.REQUEST_METHOD_POST);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            inputStream = connection.getInputStream();
            //获取服务端响应
            String resultJson = ResultUtils.getResultJsonFromURLInputStream(inputStream);
            result= ResultUtils.resultJsonToUserResult(resultJson);
            //交付给主线程
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private void initHandler() {
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bitmap icon = (Bitmap) msg.obj;
                iv_icon.setImageBitmap(icon);
            }
        };
    }

    private void initData() {
        UserInfo userInfo = ResultUtils.userInfo;
        String name="暂无";
        String phone="暂无";
        String email="暂无";
        if (userInfo.getName()==null||userInfo.getName().equals("")||userInfo.getName().equals("null")) {
            Log.i("termEnd","名字空");
            tv_name.setText(name);
        } else {
            Log.i("termEnd",userInfo.getName());
            tv_name.setText(userInfo.getName());
        }
        if (userInfo.getPhoneNumber()==null&&userInfo.getPhoneNumber().equals("")||userInfo.getPhoneNumber().equals("null")) {
            Log.i("termEnd","手机号空");
            tv_phone.setText(phone);
        } else {
            Log.i("termEnd",userInfo.getPhoneNumber());
            tv_phone.setText(userInfo.getPhoneNumber());
        }
        if (userInfo.getEmail()==null&&userInfo.getEmail().equals("")||userInfo.getEmail().equals("null")) {
            Log.i("termEnd","邮箱空");
            tv_email.setText(email);
        } else {
            Log.i("termEnd",userInfo.getEmail());
            tv_email.setText(userInfo.getEmail());
        }
        if (userInfo.getIconAddress()==null||userInfo.getIconAddress().equals("")||userInfo.getIconAddress().equals("null")){
            Log.i("termEnd","头像空");
            iv_icon.setImageResource(R.mipmap.ic_launcher);
        }else {
            String finalIconAddress = userInfo.getIconAddress();
            new Thread(){
                @Override
                public void run() {
                    showAvatar(finalIconAddress);
                }
            }.start();
        }

    }

    private void showAvatar(String iconAddress) {
        handler.sendMessage(handler.obtainMessage(1,ProductUtils.getPic(iconAddress)));
    }

    private void initViews(View view) {
        tv_name = view.findViewById(R.id.tv_user_name);
        tv_email = view.findViewById(R.id.tv_user_email);
        tv_phone = view.findViewById(R.id.tv_user_phone);
        iv_icon = view.findViewById(R.id.iv_user_icon);
        iv_name = view.findViewById(R.id.iv_name_change);
        iv_email = view.findViewById(R.id.iv_email_change);
        iv_phone = view.findViewById(R.id.iv_phone_change);
    }
}
