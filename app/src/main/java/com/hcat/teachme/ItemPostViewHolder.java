package com.hcat.teachme;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cunoraz.tagview.TagView;
import com.facebook.login.widget.ProfilePictureView;

/**
 * Created by Admin on 3/10/2017.
 */

public class ItemPostViewHolder extends RecyclerView.ViewHolder{
    public ProfilePictureView userAvatar;
    public TextView textName;
    public TextView textTitle;
    public TextView textDate;
    public TagView tagView;

    public ItemPostViewHolder(View itemView) {
        super(itemView);

        userAvatar = (ProfilePictureView) itemView.findViewById(R.id.img_user);
        textName = (TextView) itemView.findViewById(R.id.txt_name);
        textTitle = (TextView) itemView.findViewById(R.id.txt_title);
        textDate = (TextView) itemView.findViewById(R.id.txt_date);
        tagView = (TagView) itemView.findViewById(R.id.tag_group);
    }
}
