package com.hcat.teachme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cunoraz.tagview.TagView;
import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 3/9/2017.
 */

public class ListPostAdapter extends BaseAdapter {
    private List<ItemPost> listPosts;
    private Context context;
    private LayoutInflater mInflater;
    private String facebookId;

    public ListPostAdapter(Context context, String facebookId) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.facebookId = facebookId;
        initListPost();
    }

    private void initListPost() {
        listPosts = new ArrayList<>();
        listPosts.add(new ItemPost(context, facebookId, "Nguyễn Văn A", "Nhận dạy kèm giải tích 1", "09/03/2017", "15/03/2017", Arrays.asList("math")));
        listPosts.add(new ItemPost(context, facebookId, "Trần Văn B", "Nhận dạy kèm văn học ôn thi đại học", "10/03/2017", "12/04/2017", Arrays.asList("literature")));
        listPosts.add(new ItemPost(context, facebookId, "Đỗ Văn C", "Ôn thi thi toán, lí, hoá khối A", "15/04/2017", "27/04/2017", Arrays.asList("math","physic","chemistry")));
        listPosts.add(new ItemPost(context, facebookId, "Nguyễn Văn D", "Cần tìm người dạy kèm giải tích", "14/04/2017", "25/05/2017", Arrays.asList("math")));
        listPosts.add(new ItemPost(context, facebookId, "Nguyễn Văn E", "Cần tìm người dạy kèm sinh học", "06/04/2017", "29/04/2017", Arrays.asList("biology")));
        listPosts.add(new ItemPost(context, facebookId, "Nguyễn Văn F", "Nhận dạy kèm mĩ thuật và âm nhạc", "30/04/2017", "06/05/2017", Arrays.asList("art", "music")));
        listPosts.add(new ItemPost(context, facebookId, "Nguyễn Văn G", "Cần tìm người dạy kèm môn lich sử", "01/04/2017", "01/05/2017", Arrays.asList("history")));
        listPosts.add(new ItemPost(context, facebookId, "Nguyễn Văn H", "Cần tìm người dạy kèm chào cờ đầu tuần", "08/04/2017", "12/05/2017", Arrays.asList("troll")));
    }

    @Override
    public int getCount() {
        return listPosts.size();
    }

    @Override
    public ItemPost getItem(int position) {
        return listPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_post, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.userAvatar = (ProfilePictureView) convertView.findViewById(R.id.img_user);
            viewHolder.textName = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.textTitle = (TextView) convertView.findViewById(R.id.txt_title);
            viewHolder.textDate = (TextView) convertView.findViewById(R.id.txt_date);
            viewHolder.tagView = (TagView) convertView.findViewById(R.id.tag_group);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setDataForItemView(viewHolder, position);
        return convertView;
    }

    private void setDataForItemView(ViewHolder viewHolder, int position){
        ItemPost currentItemPost = getItem(position);
        viewHolder.userAvatar.setProfileId(currentItemPost.getFacebookId());
        viewHolder.textName.setText(currentItemPost.getName());
        viewHolder.textTitle.setText(currentItemPost.getPostTitle());
        viewHolder.textDate.setText(currentItemPost.getStartDate()+" - "+currentItemPost.getEndDate());
        viewHolder.tagView.addTags(currentItemPost.getListTags());
    }

    private class ViewHolder {
        ProfilePictureView userAvatar;
        TextView textName;
        TextView textTitle;
        TextView textDate;
        TagView tagView;
    }
}
