package com.fanbo.utils;


import java.io.*;
import java.util.Base64;

/**
 * 图片工具
 * */
public class ImageUtil {

    //将图片的base64字符串转成图片
    public static boolean generateImage(String imgString, String imgPath) {
        if (imgString == null)
            return false;
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] b = decoder.decode(imgString);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgPath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //将图片转成base64字符串
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }


}
