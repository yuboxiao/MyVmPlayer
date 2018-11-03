package com.huawei.myvmplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.huawei.myvmplayer.R;

import java.util.List;

/**
 * Created by x00378851 on 2018/8/26.
 */
public class HomeLiveAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List<String> dapeiqs6data;

    public HomeLiveAdapter(Context mContext, List<String> dapeiqs6data) {
        this.mContext = mContext;
        this.dapeiqs6data = dapeiqs6data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_page_recommend_item, null);
        return new LiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LiveViewHolder myViewHolder = (LiveViewHolder) holder;
        myViewHolder.iv_figure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //在这里做Activity的跳转
                Toast.makeText(mContext, position + "被点击了", Toast.LENGTH_LONG).show();
            }
        });
        myViewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return dapeiqs6data.size();
    }

    class LiveViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_figure;


        public LiveViewHolder(View itemView) {
            super(itemView);
            iv_figure = (ImageView) itemView.findViewById(R.id.iv_figure);
        }

        public void setData(int position) {
            String dapeiBean = dapeiqs6data.get(position);
                Glide.with(mContext)
                        .load(dapeiBean)
                        .into(iv_figure);
        }
    }


}
