package com.mei.model;

/**
 * Desc:    相册实体
 * Date:    2016/8/5 15:04
 * Email:   frank.xiong@zeusis.com
 */
public class AlbumModel {
    private String pathName;//路径名称
    private int fileCount;//文件大小
    private String firstImagePath;//首页图的路径

    public AlbumModel(String pathName, int fileCount, String firstImagePath) {
        this.pathName = pathName;
        this.fileCount = fileCount;
        this.firstImagePath = firstImagePath;
    }

    public String getPathName() {
        return pathName;
    }

    public int getFileCount() {
        return fileCount;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    @Override
    public String toString() {
        return "AlbumModel{" +
                "pathName='" + pathName + '\'' +
                ", fileCount=" + fileCount +
                ", firstImagePath='" + firstImagePath + '\'' +
                '}';
    }
}
