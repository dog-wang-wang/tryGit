package com.example.termend;

public class ConfigString {
    //我数据库主机安装失败了所以我就租了个服务器安装到服务器上了,然后我把tomcat项目也放上去了，所以下面我写的代码访问的数据库地址是本地的，而注释掉的地址是我没有部署到服务器之前验证其他功能时候用的，只能够展示该次作业部分功能
    //所以提交的是依据服务器上的地址写的,注释注掉的就是我原本在自己主机上写的然后去链接了服务器的数据库，
    //头像一开始存在我本地的后来扔服务器的/android_term/icon目录下了
    //商品的图片数据也是一开始在本地后来扔到服务器的/android_term/product_pic/10.jpg目录下了
    //下面的用户我没用root是因为我怕被删表删库我就只在服务器的数据库建了个zhangsan用户开放了%的访问权限，root仍然是本地的访问权限

    //所以您如果在本地建库查看我的期末任务的话需要更改几个地方：
    //  1.product表里picAddress更改为您的图片数据存放的目录
    //  2.下面的userIconAddress更改为您本地的一个目录用来存放用户头像


    //如果您放弃直接建库建表查看我的期末任务的话只把下面第一行注释取消给下面的mysql地址注释掉也不行，因为图片是在服务器上存着的在我其他的servlet中获取图片的逻辑是从tomcat服务器所在的主机上进行存储的，所以只在这样更改也不行，仍然需要更改上面说的两个地方


    //但是如果这玩意直接扔服务器上然后和数据库在同一个服务器上照片目录正确的情况下就可以直接用，但记得服务器要有下面这个用户

    //public static String databaseAddress = "jdbc:mysql://123.249.14.197:3306/android_termend?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2b8";
    public static String databaseAddress = "jdbc:mysql://localhost:3306/android_termend?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2b8";
    public static String databaseUser="zhangsan";
    public static String databasePassword="123456";
//    public static String userIconAddress="E:/";
    public static String userIconAddress="/android_term/icon/";


}
