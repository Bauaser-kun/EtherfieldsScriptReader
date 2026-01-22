package com.PortalGames.PortalDEX;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private com.PortalGames.PortalDEX.AudioManager audioManager;
    private PDFView pdfView;
    private EditText userInputString;
    private Button openPdfButton;
    private PDFManager pdfManager;
    private ImageButton pdfBackButton;
    private String currentPDF;
    private ArrayList<String> pdfList;
    private ArrayList<String> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find views
        recyclerView = findViewById(R.id.recyclerView);
        pdfView = findViewById(R.id.pdfView);
        pdfBackButton = findViewById(R.id.pdfBackButton);
        userInputString = findViewById(R.id.rulebookNameInput);
        openPdfButton = findViewById(R.id.openPdfButton);

        //Init helper
        pdfManager = new PDFManager(this);
        //audioManager = new com.PortalGames.PortalDEX.AudioManager(this);

        pdfList = new ArrayList<>(PDFManager.getPDFListFromAssets(this));
        filteredList = new ArrayList<>(pdfList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(
                this,
                filteredList,
                pdfName -> {
                    currentPDF = pdfName;
                    pdfManager.displayPDF(pdfName, pdfView);

                    recyclerView.setVisibility(View.GONE);
                    pdfView.setVisibility(View.VISIBLE);
                    pdfBackButton.setVisibility(View.VISIBLE);
                    //audioManager.runAudioFile(pdfName.replace(".pdf", ".mp3"));
                }
        );

        recyclerView.setAdapter(recyclerViewAdapter);

        pdfBackButton.setOnClickListener(v -> {
            pdfManager.closePdf(pdfView, pdfBackButton, recyclerView);
        });

        userInputString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filteredList.clear();
                filteredList.addAll(pdfManager.filterPdfList(pdfList, s.toString()));

                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        openPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputToFind = userInputString.getText().toString().trim();

                if (inputToFind.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter an name", Toast.LENGTH_SHORT).show();
                    return;
                }

                String foundPdf = null;

                for (String pdf: pdfList) {
                    if (pdf.toLowerCase().contains(inputToFind.toLowerCase())) {
                        foundPdf = pdf;
                        break;
                    }
                }

                if (foundPdf != null) {
                    currentPDF = foundPdf;
                    pdfManager.displayPDF(foundPdf, pdfView);
                    //audioManager.runAudioFile(eventNumber + ".mp3");

                    recyclerView.setVisibility(View.GONE);
                    pdfView.setVisibility(View.VISIBLE);
                    pdfBackButton.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, "No matching PDF found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (pdfView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("pdf_open", true);
            outState.putString("pdf_name", currentPDF);
            outState.putInt("pdf_page", pdfView.getCurrentPage());
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstance) {
        super.onRestoreInstanceState(savedInstance);

        if (savedInstance.getBoolean("pdf_open", false)) {
            String pdfName = savedInstance.getString("pdf_name");
            int page = savedInstance.getInt("pdf_page", 0);

            if (pdfName != null) {
                currentPDF = pdfName;

                recyclerView.setVisibility(View.GONE);
                pdfView.setVisibility(View.VISIBLE);
                pdfBackButton.setVisibility(View.VISIBLE);

                pdfManager.displayPDF(pdfName, pdfView);
                pdfView.jumpTo(page, false);
            }
        }
    }
}
