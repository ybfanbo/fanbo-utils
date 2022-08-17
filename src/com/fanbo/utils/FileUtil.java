package com.fanbo.utils;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件工具类
 * @author FanBo
 */
public class FileUtil {

    public static String UNIT_B = "b";
    public static String UNIT_K = "K";
    public static String UNIT_M = "M";
    public static String UNIT_G = "G";

    /**
     * 判断文件大小处于限制内
     *
     * @param fileLen  文件长度
     * @param fileSize 限制大小
     * @param fileUnit 限制的单位（B,K,M,G）
     * @return
     */
    public boolean checkFileSizeIsLimit(Long fileLen, int fileSize, String fileUnit) {
        double fileSizeCom = 0;
        if (UNIT_B.equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen;
        } else if (UNIT_K.equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / 1024;
        } else if (UNIT_M.equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024 * 1024);
        } else if (UNIT_G.equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024 * 1024 * 1024);
        }
        if (fileSizeCom > fileSize) {
            return false;
        }
        return true;
    }

    //获取文件类型
    public static String getFileSuffix(String filePath) {
        String[] strArray = filePath.split("\\.");
        int suffixIndex = strArray.length - 1;
        return strArray[suffixIndex];
    }

    /**
     * 将网络图片转成Base64码，此方法可以解决解码后图片显示不完整的问题
     *
     * @param imgURL。 例如：http://***.com/271025191524034.jpg
     * @return
     */
    public static String imgBase64(String imgURL) {
        ByteArrayOutputStream outPut = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10 * 1000);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "fail";//连接失败/链接失效/图片不存在
            }
            InputStream inStream = conn.getInputStream();
            int len = -1;
            while ((len = inStream.read(data)) != -1) {
                outPut.write(data, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outPut.toByteArray());
    }
}
