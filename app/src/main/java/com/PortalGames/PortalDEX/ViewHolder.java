package com.PortalGames.PortalDEX;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    ImageView icon;

    public ViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.fileNameText);
        icon = itemView.findViewById(R.id.icon);
    }
}