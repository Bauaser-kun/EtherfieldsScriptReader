package com.PortalGames.PortalDEX;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFManager {
    private final Context context;

    public PDFManager (Context context) {
        this.context = context.getApplicationContext();
    }

    public static List<String> getPDFListFromAssets(Context context) {
        List<String> pdfs = new ArrayList<>();
        AssetManager manager = context.getAssets();

        try {
            String[] fileNames = manager.list("");
            if (fileNames != null) {
                for (String file: fileNames) {
                    if (file.toLowerCase().endsWith(".pdf")) {
                        pdfs.add(file);
                    }
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return pdfs;
    }

    public void closePdf(PDFView pdfView, ImageButton backButton, RecyclerView recyclerView) {
        pdfView.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public ArrayList<String> filterPdfList(ArrayList<String> pdfList, String query) {
        ArrayList<String> filteredList = new ArrayList<>();

        if (query.isEmpty()) {
            filteredList.addAll(pdfList);
        } else {
            for (String pdf: pdfList) {
                if (pdf.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(pdf);
                }
            }
        }

        return filteredList;
    }

    public void displayPDF(String assetFileName, PDFView pdfView) {
        try {
            AssetManager assetManager = context.getAssets();

            InputStream inputStream = assetManager.open(assetFileName);

            pdfView.fromStream(inputStream)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .swipeHorizontal(false)
                    .scrollHandle(new DefaultScrollHandle(context))
                    .enableAnnotationRendering(true)
                    .pageFitPolicy(FitPolicy.WIDTH)
                    .load();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error loading PDF file", Toast.LENGTH_SHORT).show();
        }
    }
}
