package com.PortalGames.PortalDEX;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.FileLayoutHolder> {
    private Context mContext;
    RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }
    
    @NonNull
    public FileLayoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.files_list, parent, false);

        return new FileLayoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileLayoutHolder holder, int position) {
        ((FileLayoutHolder)holder).title.setText(Constant.allMediaList.get(position).getName());
        Uri uri = Uri.fromFile(Constant.allMediaList.get(position));

        Glide.with(mContext)
                .load(uri).thumbnail(0.1f).into(((FileLayoutHolder)holder).thumbnail);
    }

    @Override
    public int getItemCount() {
        return Constant.allMediaList.size();
    }

    class FileLayoutHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView title;
        ImageButton ic_more_btn;

        public FileLayoutHolder (@NonNull View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            ic_more_btn = itemView.findViewById(R.id.ic_more_btn);
        }
    }
}
