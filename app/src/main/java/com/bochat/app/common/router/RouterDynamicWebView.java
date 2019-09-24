package com.bochat.app.common.router;

import java.util.Map;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 17:32
 * Description :
 */

public class RouterDynamicWebView extends AbstractRouter {
    public static final String PATH = "/path/RouterDynamicWebView";

    private String url;
    private String title;
    private Map<String, Object> splicing;
    private Map<String, Object> share;
    private String shareUrl;


    public RouterDynamicWebView(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public RouterDynamicWebView(String url, String shareBaseUrl, Map<String, Object> splicing, Map<String, Object> share) {
        this.url = url;
        this.splicing = splicing;
        this.share = share;
        this.shareUrl=shareBaseUrl;
        initLink();
    }

    public RouterDynamicWebView(String url,String shareBaseUrl, String title, Map<String, Object> splicing, Map<String, Object> share) {
        this.url = url;
        this.title = title;
        this.splicing = splicing;
        this.share = share;
        this.shareUrl=shareBaseUrl;
        initLink();
    }

    private void initLink() {
        if (splicing != null && splicing.size() != 0) {
            int i=0;
            for (Map.Entry<String, Object> entry : splicing.entrySet()) {
                String mapKey = entry.getKey();
                String mapValue = entry.getValue().toString();
                if(i==0){
                    url=url+"?";
                }else{
                    url=url+"&";
                }
                url=url+mapKey+"="+mapValue;
                i++;
            }
        }
        if (share != null && share.size() != 0) {
            int i=0;
            for (Map.Entry<String, Object> entry : share.entrySet()) {
                String mapKey = entry.getKey();
                String mapValue = entry.getValue().toString();
                if(i==0){
                    shareUrl=shareUrl+"?";
                }else{
                    shareUrl=shareUrl+"&";
                }
                shareUrl=shareUrl+mapKey+"="+mapValue;
                i++;
            }
        }
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Map<String, Object> getSplicing() {
        return splicing;
    }

    public void setSplicing(Map<String, Object> splicing) {
        this.splicing = splicing;
    }

    public Map<String, Object> getShare() {
        return share;
    }

    public void setShare(Map<String, Object> share) {
        this.share = share;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
