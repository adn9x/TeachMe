package com.hcat.teachme;

import android.content.Context;

import com.cunoraz.tagview.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/9/2017.
 */

public class SubjectTagFactory {
    public static final String MATH_SUBJECT = "math";
    public static final String LITERATURE_SUBJECT = "literature";
    public static final String PHYSIC_SUBJECT = "physic";
    public static final String CHEMISTRY_SUBJECT = "chemistry";
    public static final String MUSIC_SUBJECT = "music";
    public static final String BIOLOGY_SUBJECT = "biology";
    public static final String ART_SUBJECT = "art";
    public static final String HISTORY_SUBJECT = "history";

    public static final String TROLL_SUBJECT = "troll";

    public static List<Tag> createListTags(Context context, List<String> listTagSubject) {
        List<Tag> result = new ArrayList<>();
        for (String tagSubject : listTagSubject){
            Tag temp = createTag(context, tagSubject);
            if (temp != null){
                result.add(temp);
            }
        }
        return result;
    }

    public static Tag createTag(Context context, String tagSubject){
        String tagName = null;
        switch (tagSubject){
            case MATH_SUBJECT:{
                tagName = context.getResources().getString(R.string.math);
            }
            break;
            
            case LITERATURE_SUBJECT:{
                tagName = context.getResources().getString(R.string.literature);
            }
            break;
            
            case PHYSIC_SUBJECT:{
                tagName = context.getResources().getString(R.string.physic);
            }
            break;
            
            case CHEMISTRY_SUBJECT:{
                tagName = context.getResources().getString(R.string.chemistry);
            }
            break;
            
            case ART_SUBJECT:{
                tagName = context.getResources().getString(R.string.art);
            }
            break;

            case MUSIC_SUBJECT:{
                tagName = context.getResources().getString(R.string.music);
            }
            break;

            case BIOLOGY_SUBJECT:{
                tagName = context.getResources().getString(R.string.biology);
            }
            break;

            case HISTORY_SUBJECT:{
                tagName = context.getResources().getString(R.string.history);
            }
            break;

            case TROLL_SUBJECT:{
                tagName = context.getResources().getString(R.string.troll);
            }
            break;
            
            default:{
                break;
            }
        }
        if(tagName == null){
            return null;
        }
        Tag resultTag = new Tag(tagName);
        resultTag.tagTextSize = 12.0f;
        return new Tag(tagName);
    }
}
