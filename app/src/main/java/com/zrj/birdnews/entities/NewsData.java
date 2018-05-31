//package com.zrj.birdnews.entities;
//
//import com.google.gson.annotations.SerializedName;
//
//import java.util.List;
//
///**
// * Created by a on 2018/3/11.
// */
//
//public class NewsData {
//
//
//
//    private boolean success;
//
//    @SerializedName("code")
//    private String errorCode;
//
//    @SerializedName("msg")
//    private String errorMsg;
//
//    private Data data;
//
//    public String getErrorCode() {
//        return errorCode;
//    }
//
//    public String getErrorMsg() {
//        return errorMsg;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public Data getData() {
//        return data;
//    }
//
//    public class Data{
//        private int count;
//
//        @SerializedName("first_id")
//        private long firstId;
//
//
//        @SerializedName("last_id")
//        private long lastId;
//
//        @SerializedName("news")
//        private List<News> newsList;
//
//        public int getCount() {
//            return count;
//        }
//
//        public long getFirstId() {
//            return firstId;
//        }
//
//        public long getLastId() {
//            return lastId;
//        }
//
//        public List<News> getNewsList() {
//            return newsList;
//        }
//    }
//
//    public class News{
//        @SerializedName("news_id")
//        private long newsId;
//
//        private String title;
//
//        private String source;
//
//        @SerializedName("gmt_publish")
//        private long publishTime;
//
//        @SerializedName("hot_index")
//        private int hotIndex;
//
//        private String[] category;
//
//        @SerializedName("thumbnail_img")
//        private String[] imgUrls;
//
//        private String url;
//
//        public long getNewsId() {
//            return newsId;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public String getSource() {
//            return source;
//        }
//
//        public long getPublishTime() {
//            return publishTime;
//        }
//
//        public int getHotIndex() {
//            return hotIndex;
//        }
//
//        public String[] getImgUrls() {
//            return imgUrls;
//        }
//
//        public String[] getCategory() {
//            return category;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//    }
//
//
//
//}
