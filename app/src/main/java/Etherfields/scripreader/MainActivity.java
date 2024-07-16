package Etherfields.scripreader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewHolder recyclerViewHolder;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int PDF_SELECTION_CODE = 200;

    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        pdfView = findViewById(R.id.pdfView);


        final AssetManager assets = getBaseContext().getAssets();
        listAssetFiles("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                selectPDFFile();
            } else {
                requestPermission();
            }
        } else {
            selectPDFFile();
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPDFFile();
        } else {
            Toast.makeText(this, "Permission Denied! Please allow the permission to read PDF files.", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPDFFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select PDF File"), PDF_SELECTION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PDF_SELECTION_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();
            displayPDF(selectedFileUri);
        }
    }

    private void displayPDF(Uri fileUri) {
        pdfView.fromUri(fileUri)
            .defaultPage(0)
            .enableSwipe(true)
            .enableDoubletap(true)
            .swipeHorizontal(false)
            .onLoad(nbPages -> {
    })
        .onPageChange((page, pageCount) -> {
    })
        .scrollHandle(new DefaultScrollHandle(this))
        .enableAnnotationRendering(true)
        .password(null)
        .pageFitPolicy(FitPolicy.WIDTH)
        .load();
    }

    private boolean listAssetFiles(String path) {
        ArrayList<String> files = new ArrayList<>();
        String [] list;
        try {
            list = getAssets().list(path);
            if (list.length > 0) {
                for (String file : list) {
                    if (!listAssetFiles(path + "/" + file))
                        return false;
                    else {
                        files.add(file);
                    }
                }
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}