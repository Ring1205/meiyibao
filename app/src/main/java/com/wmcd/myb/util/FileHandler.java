package com.wmcd.myb.util;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * The type File handler.
 */
public class FileHandler {


    /**
     * The constant instance.
     */
    private static FileHandler instance;


    /**
     * Instantiates a new File handler.
     */
    private FileHandler() {

    }


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FileHandler getInstance() {

        if (instance == null) {
            instance = new FileHandler();
        }

        return instance;
    }


    /**
     * 获取DCIM路径
     *
     * @return dcim path
     */
    public String getDCIMPath() {

        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
    }


    /**
     * 获取照相机路径
     *
     * @return camera path
     */
    public String getCameraPath() {

        return getDCIMPath() + "/Camera/";
    }


    /**
     * 得到当前app的目录
     *
     * @param c the c
     * @return local path
     */
    public static String getLocalPath(Context c) {
        String sdPath = null;
        sdPath = c.getFilesDir().getAbsolutePath() + "/";
        return sdPath;
    }


    /**
     * 检测SDCard是否可用
     *
     * @return boolean
     */
    public boolean checkSDCardState() {

        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)
                && Environment.getExternalStorageDirectory().canWrite()
                && Environment.getExternalStorageDirectory().canRead()) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * 新建照相机存储路径
     *
     * @return boolean
     */
    public boolean createCameraPath() {

        File file = new File(getCameraPath());
        if (file.exists() && file.isDirectory()) {
            return true;
        }

        return file.mkdirs();
    }


    /**
     * 判断文件是否存在
     *
     * @param path the path
     * @return boolean
     */
    public boolean isExsit(String path) {

        File file = new File(path);

        return file.exists();
    }


    /**
     * 检测上级文件夹路径是否存在
     *
     * @param path   the path
     * @param create the create
     * @return boolean
     */
    public boolean checkParentExsit(String path, boolean create) {
        File file = new File(path);

        File parent = file.getParentFile();

        if (parent.exists()) {
            return true;
        }

        if (create) {
            try {
                if (parent.mkdirs()) {
                    return createFile(parent.getAbsolutePath() + "/.nomedia");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    /**
     * 新建文件
     *
     * @param path the path
     * @return boolean
     */
    public boolean createFile(String path) {

        File file = new File(path);
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 新建文件夹
     *
     * @param path the path
     * @return boolean
     */
    public boolean createDirectory(String path) {

        File file = new File(path);

        if (!file.exists()) {
            return file.mkdirs();
        }

        return true;
    }


    /**
     * 新建文件
     *
     * @param path the path
     * @return file
     */
    public File createNewFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        file.mkdirs();

        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**************************************************************************************
     * @param path the path
     * @return input stream
     */
    public InputStream read(String path) {

        File file = new File(path);

        if (file.exists()) {

            try {
                return new FileInputStream(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * Write boolean.
     *
     * @param path   the path
     * @param body   the body
     * @param append the append
     * @return the boolean
     */
    public boolean write(String path, byte[] body, boolean append) {

        File file = new File(path);
        try {
            if (!file.exists()) {
                if (checkParentExsit(path, true)) {
                    file.createNewFile();
                }
            }

            FileOutputStream output = new FileOutputStream(file, append);
            output.write(body);

            output.flush();
            output.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Read bytes byte [ ].
     *
     * @param in the in
     * @return the byte [ ]
     */
    public byte[] readBytes(InputStream in) {

        ByteArrayOutputStream baos = null;
        int c = 0;
        try {
            baos = new ByteArrayOutputStream();
            while ((c = in.read()) != -1) {
                baos.write((byte) c);
            }

            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            baos = null;
            in = null;
        }

        return null;
    }


    /**
     * 获取文件列表
     *
     * @param path the path
     * @return string [ ]
     */
    public String[] getFileList(String path) {

        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return file.list();
        }

        return null;
    }


    /**
     * 删除文件
     *
     * @param path the path
     * @return boolean
     */
    public boolean delete(String path) {

        File file = new File(path);

        if (file.exists()) {
            return file.delete();
        }

        return false;
    }


}
