package com.dk.dkweibo.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by feng on 2015/10/8.
 */
public class Status {
    private long id;
    private String create_at;
    private String text;
    private String source;
    private int retweet_count;
    private int comments_count;
    private int agree_count;

    public List<String> getMiddle_pic_urls() {
        return middle_pic_urls;
    }

    public void setMiddle_pic_urls(List<String> middle_pic_urls) {
        this.middle_pic_urls = middle_pic_urls;
    }

    public List<String> getThumbnail_pic_urls() {
        return thumbnail_pic_urls;
    }

    public void setThumbnail_pic_urls(List<String> thumbnail_pic_urls) {
        this.thumbnail_pic_urls = thumbnail_pic_urls;
    }

    public List<String> getOriginal_pic_urls() {
        return original_pic_urls;
    }

    public void setOriginal_pic_urls(List<String> original_pic_urls) {
        this.original_pic_urls = original_pic_urls;
    }

    private List<String> thumbnail_pic_urls;
    private List<String> middle_pic_urls;
    private List<String> original_pic_urls;
    private Geo geo;
    private User user;
    private Status retweeted_status;
    private boolean isTruncated;

    private static final String THUMBNAIL="thumbnail";
    private static final String BMIDDLE="bmiddle";
    private static final String LARGE="large";

    public static Status getStatusFromJson(JSONObject json){
        if (json==null||json.length()==0)
            return null;
        Status status=new Status();

        status.setId(json.optLong("id"));
        status.setText(json.optString("text"));
        status.setUser(User.getUserFromJson(json.optJSONObject("user")));
        status.setCreate_at(json.optString("created_at"));
        //set pictures list
        List<String> thumbnailPicUrls=new ArrayList<>();
        List<String> middlePicUrls=new ArrayList<>();
        List<String> originPicUrls=new ArrayList<>();
        JSONArray pic_urls=json.optJSONArray("pic_urls");
        for (int i = 0; i <pic_urls.length(); i++) {
            String thumbnailPicUrl=pic_urls.optJSONObject(i).optString("thumbnail_pic");
            thumbnailPicUrls.add(thumbnailPicUrl);
            middlePicUrls.add(thumbnailPicUrl.replaceFirst(THUMBNAIL,BMIDDLE));
            originPicUrls.add(thumbnailPicUrl.replaceFirst(THUMBNAIL, LARGE));
        }
        status.setThumbnail_pic_urls(thumbnailPicUrls);
        status.setMiddle_pic_urls(middlePicUrls);
        status.setOriginal_pic_urls(originPicUrls);

        status.setRetweet_count(json.optInt("reposts_count"));
        status.setComments_count(json.optInt("comments_count"));
        status.setAgree_count(json.optInt("attitudes_count"));
        status.setIsTruncated(json.optBoolean("truncated"));

        Status retweetStatus=getStatusFromJson(json.optJSONObject("retweeted_status"));
        status.setRetweeted_status(retweetStatus);

        return status;
    }

    private static List<String> getMiddlePicUrlList(List<String> thumbnail_pic_urls){
        ArrayList<String> middlePicUrlList=new ArrayList<>();
        Pattern p=Pattern.compile(THUMBNAIL);
        for (int i = 0; i < thumbnail_pic_urls.size(); i++) {
            String url=thumbnail_pic_urls.get(i);
            Matcher m=p.matcher(url);
            middlePicUrlList.add(m.replaceFirst(BMIDDLE));
        }
        return middlePicUrlList;
    }

    private static List<String> getLargePicUrlList(List<String> thumbnail_pic_urls){
        ArrayList<String> largePicUrlList=new ArrayList<>();
        Pattern p=Pattern.compile(THUMBNAIL);
        for (int i = 0; i < thumbnail_pic_urls.size(); i++) {
            String url=thumbnail_pic_urls.get(i);
            Matcher m=p.matcher(url);
            largePicUrlList.add(m.replaceFirst(LARGE));
        }
        return largePicUrlList;
    }

    @Override
    public String toString() {
        return "\n"+
                "id:"+id+",\n" +
                "create_at:"+create_at+",\n" +
                "text:"+text+",\n" +
                "thumbnail_pic_urls:"+thumbnail_pic_urls.toString()+",\n" +
                "middle_pic_urls:"+middle_pic_urls.toString()+",\n" +
                "truncated:"+isTruncated+",\n" +
                "user:"+user.toString()+",\n" +
                "retweet_status:"+(retweeted_status==null?"":retweeted_status.toString())+"\n"
                ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getRetweet_count() {
        return retweet_count;
    }

    public void setRetweet_count(int retweet_count) {
        this.retweet_count = retweet_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAgree_count() {
        return agree_count;
    }

    public void setAgree_count(int agree_count) {
        this.agree_count = agree_count;
    }


    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getRetweeted_status() {
        return retweeted_status;
    }

    public void setRetweeted_status(Status retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    public boolean isTruncated() {
        return isTruncated;
    }

    public void setIsTruncated(boolean isTruncated) {
        this.isTruncated = isTruncated;
    }




    static class Geo{

    }

}
