package com.hcat.teachme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 3/9/2017.
 */

public class ListPostAdapter extends RecyclerView.Adapter<ItemPostViewHolder> {
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
    public int getItemCount() {
        return listPosts.size();
    }

    @Override
    public ItemPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.item_post, parent, false);
        return new ItemPostViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ItemPostViewHolder holder, int position) {
        ItemPost currentItemPost = listPosts.get(position);
        holder.userAvatar.setProfileId(currentItemPost.getFacebookId());
        holder.textName.setText(currentItemPost.getName());
        holder.textTitle.setText(currentItemPost.getPostTitle());
        holder.textDate.setText(currentItemPost.getStartDate()+" - "+currentItemPost.getEndDate());
        holder.tagView.addTags(currentItemPost.getListTags());
    }
}
