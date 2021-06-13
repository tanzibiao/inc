package com.inc.admin.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.UUID;

public class FileUtils {
    public static String saveFile(byte[] file, String filePath, String fileName) {
        String date = DateUtils.format(new Date(), "yyyy/MM/dd");
        filePath = filePath + date + File.separator;
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filePath + fileName);
            FileChannel fileChannel = fileOutputStream.getChannel();
            ByteBuffer buf = ByteBuffer.wrap(file);
            while (fileChannel.write(buf) != 0) {
            }
        } catch (Exception e) {

        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //url
        return date + "/" + fileName;
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
