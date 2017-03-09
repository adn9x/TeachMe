package com.hcat.teachme;

import android.content.Context;

import com.cunoraz.tagview.Tag;

import java.util.List;

/**
 * Created by Admin on 3/9/2017.
 */

public class ItemPost {
    private String facebookId;
    private String name;
    private String postTitle;
    private String startDate;
    private String endDate;
    private List<Tag> listTags;

    public ItemPost(Context context, String facebookId, String name, String postTitle, String startDate, String endDate, List<String> listTagSubject) {
        this.facebookId = facebookId;
        this.name = name;
        this.postTitle = postTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        createTag(context, listTagSubject);
    }

    public void createTag(Context context, List<String> listTagSubject){
        listTags = SubjectTagFactory.createListTags(context, listTagSubject);
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getName() {
        return name;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public List<Tag> getListTags() {
        return listTags;
    }
}
