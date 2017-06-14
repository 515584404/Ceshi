package com.ceshi.android.entity;

import java.util.List;

/**
 * Created by ztr on 2016/05/06.
 */
public class MainPictureEntity {


    /**
     * fileName : index_carousel2.png
     * pageUrl : http://101.201.50.209/ifensanweb/static/app/aboutus
     * lastModifyTime : 1463135316000
     * fileUrl : http://101.201.50.209/ifensanweb/resources/images/app/indexCarousel/index_carousel2.png
     */

    private List<LoadImagesBean> loadImages;
    private List<LoadImagesBean> deletedImages;

    public List<LoadImagesBean> getLoadImages() {
        return loadImages;
    }

    public void setLoadImages(List<LoadImagesBean> loadImages) {
        this.loadImages = loadImages;
    }

    public List<LoadImagesBean> getDeletedImages() {
        return deletedImages;
    }

    public void setDeletedImages(List<LoadImagesBean> deletedImages) {
        this.deletedImages = deletedImages;
    }

    public static class LoadImagesBean {
        private String fileName;
        private String pageUrl;
        private long lastModifyTime;
        private String fileUrl;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }

        public long getLastModifyTime() {
            return lastModifyTime;
        }

        public void setLastModifyTime(long lastModifyTime) {
            this.lastModifyTime = lastModifyTime;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}
