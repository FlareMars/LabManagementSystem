package com.libmanagement.common.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;


public class FileUtils {

    private static Logger logger = Logger.getLogger(FileUtils.class);

    private static final String FILE_STORY_DIR = "H:\\LMS_FILES\\";

    public static final String SEPARATOR = "/";

    public static String saveFile(byte[] data,String fileName) throws IOException{

        String path = FILE_STORY_DIR + fileName;
        File file = new File(path);
        BufferedOutputStream stream = null;
        stream = new BufferedOutputStream(new FileOutputStream(file));
        stream.write(data);
        stream.close();

        return path;
    }

    public static boolean isDir(String path){
        if(path.endsWith("/")){
            return true;
        }else return false;
    }


    public static String getParentPath(String path){
        if(path==null) path=SEPARATOR;
        if(!path.startsWith(SEPARATOR)){
            path = SEPARATOR+path;
        }
        File file = new File(path.trim());
        String parentPath = SEPARATOR;

        if(file.getParent()!=null){
            parentPath =  file.getParent().replace('\\', '/');
        }

        if(!parentPath.endsWith(SEPARATOR)){
            parentPath = parentPath+SEPARATOR;
        }
        return parentPath;
    }

    public static String getFileName(String path){
        if(path==null) path=SEPARATOR;
        if(!path.startsWith(SEPARATOR)){
            path = SEPARATOR+path;
        }
        File file = new File(path.trim());
        return file.getName();
    }


    public static String formatPath(String path){
        if(path==null) path=SEPARATOR;
        File file = new File(path.trim());
        String temp = file.getPath().replace('\\', '/');
        if(!temp.startsWith(SEPARATOR)){
            temp = SEPARATOR+temp;
        }
        if(!temp.endsWith(SEPARATOR)){
            temp = temp+SEPARATOR;
        }
        return temp;
    }

    public static String formatFileName(String fileName){
        if(fileName==null) fileName=SEPARATOR;
        File file = new File(fileName.trim());
        String temp = file.getPath().replace('\\', '/');
        if(!temp.startsWith(SEPARATOR)){
            temp = SEPARATOR+temp;
        }
        return temp;
    }

    /**
     * NIO 方式实现文件读取
     */
    public static byte[] toByteArray(String filePath) throws IOException {

        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String formatFilePath(String path){
        if(path==null) return null;
        File file = new File(path.trim());
        String temp = file.getPath().replace('\\', '/');
        return temp;
    }

    public static String[] splitPath(String path){
        String temp = formatPath(path);
        return temp.split(SEPARATOR);
    }

    public static String subParentPath(String path,int level){
        String temp = formatFileName(path);
        for(int i=0;i<level;i++){
            int index = temp.indexOf("/",1);
            if(index!=-1) {
                temp = temp.substring(index);
            }
        }
        return temp;
    }

    public static final String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

}
