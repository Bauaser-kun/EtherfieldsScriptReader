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
        String pdfName = pdfList.get(position).getName();

        holder.title.setText(pdfName);

        /*Glide.with(mContext)
                .load(R.drawable.ic_pdf)   // your pdf icon
                .into(holder.thumbnail);*/

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPdfClick(pdfName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
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
