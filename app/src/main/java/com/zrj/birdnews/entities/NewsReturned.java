package com.zrj.birdnews.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a on 2018/3/18.
 */

public class NewsReturned {



    @SerializedName("showapi_res_error")
    private String errorMsg;

    @SerializedName("showapi_res_code")
    private int code;

    @SerializedName("showapi_res_body")
    private Body body;

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getCode() {
        return code;
    }

    public Body getBody() {
        return body;
    }

    public class Body {

        @SerializedName("ret_code")
        private int returnCode;

        @SerializedName("pagebean")
        private Page page;

        public int getReturnCode() {
            return returnCode;
        }

        public Page getPage() {
            return page;
        }
    }

    public class Page {


        @SerializedName("contentlist")
        private List<News> newsList;

        public List<News> getNewsList() {
            return newsList;
        }
    }

    public class News {

        private String id;

        private String pubDate;

        private boolean havePic;

        private String channelName;

        private String title;

        @SerializedName("desc")
        private String summary;

        @SerializedName("imageurls")
        private List<Img> imgUrls;

        private String source;

        private String channelId;

        @SerializedName("link")
        private String url;

        public String getId() {
            return id;
        }

        public String getPubDate() {
            return pubDate;
        }

        public boolean isHavePic() {
            return havePic;
        }

        public String getChannelName() {
            return channelName;
        }

        public String getTitle() {
            return title;
        }

        public String getSummary() {
            return summary;
        }

        public List<Img> getImgUrls() {
            return imgUrls;
        }

        public String getSource() {
            return source;
        }

        public String getChannelId() {
            return channelId;
        }

        public String getUrl() {
            return url;
        }
    }

    public class Img {
        private String url;

        public String getUrl() {
            return url;
        }
    }


}
