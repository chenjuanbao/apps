package com.mediwit.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileUtil {

    /**
        @创建人：mocha
        @创建时间：2014-6-1
        @功能说明：获取本地图片对象
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {

         try {
              String path=Environment.getExternalStorageDirectory().getPath();
              System.out.println("path==="+path);
              FileInputStream fis = new FileInputStream(url);
              return BitmapFactory.decodeStream(fis);
         } catch (FileNotFoundException e) {

              e.printStackTrace();

              return null;

         }

    }

    /**

    * 从服务器取图片

    *http://bbs.3gstdy.com

    * @param url

    * @return

    */

    public static Bitmap getHttpBitmap(String url) {
         URL myFileUrl = null;
         Bitmap bitmap = null;
         try {
              myFileUrl = new URL(url);
         } catch (MalformedURLException e) {
              e.printStackTrace();
         }
         try {
              HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
              conn.setConnectTimeout(0);
              conn.setDoInput(true);
              conn.connect();
              InputStream is = conn.getInputStream();
              bitmap = BitmapFactory.decodeStream(is);
              is.close();
         } catch (IOException e) {
              e.printStackTrace();
         }
         return bitmap;
    }
    public static Bitmap getIcon(String name){

        String path = "com/icon/message/"+name; //图片存放的路径
        InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(path);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    public static File saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
