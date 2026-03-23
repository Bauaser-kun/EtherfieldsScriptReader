package com.PortalGames.PortalDEX;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class FileLayoutViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    ImageView icon;
    ImageButton downloadButton;
    ImageView downloadedIcon;

    public FileLayoutViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.fileNameText);
        icon = itemView.findViewById(R.id.icon);
        downloadButton = itemView.findViewById(R.id.ic_more_btn);
        downloadedIcon = itemView.findViewById(R.id.downloaded);
    }
}