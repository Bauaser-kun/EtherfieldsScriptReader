package com.PortalGames.PortalDEX;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.FileLayoutHolder> {
    private Context mContext;
    RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private ArrayList<PdfListItem> pdfList;
    private OnPdfClickListener listener;

    public RecyclerViewAdapter(Context context,
                               ArrayList<PdfListItem> pdfList,
                               OnPdfClickListener listener) {
        this.mContext = context;
        this.pdfList = pdfList;
        this.listener = listener;
    }

    public interface OnPdfClickListener {
        void onPdfClick(String pdfName);
    }
    
    @NonNull
    public FileLayoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.files_list, parent, false);

        return new FileLayoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileLayoutHolder holder, int position) {
        PdfListItem item = pdfList.get(position);

        holder.fileNameText.setText(item.getName());
        holder.icon.setImageResource(item.getIconId());

        /*Glide.with(mContext)
                .load(R.drawable.ic_pdf)   // your pdf icon
                .into(holder.thumbnail);*/

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPdfClick(item.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    class FileLayoutHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView fileNameText;
        ImageButton moreButton;

        FileLayoutHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            fileNameText = itemView.findViewById(R.id.fileNameText);
            moreButton = itemView.findViewById(R.id.ic_more_btn);
        }
    }
}
