package Etherfields.scripreader;

import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private PDFView pdfView;
    private EditText eventNumberInput;
    private Button openPdfButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        pdfView = findViewById(R.id.pdfView);
        eventNumberInput = findViewById(R.id.eventNumberInput);
        openPdfButton = findViewById(R.id.openPdfButton);

        // Load PDF from assets
        openPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventNumberStr = eventNumberInput.getText().toString();

                if (eventNumberStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter an event number", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int eventNumber = Integer.parseInt(eventNumberStr);
                    displayPDF(eventNumber + ".pdf");
                    runAudioFile(eventNumber + ".mp3");
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid event number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void runAudioFile(String assetFileName) {
        try {
            AssetManager assetManager = getAssets();

            InputStream inputStream = assetManager.open(assetFileName);


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading audio file", Toast.LENGTH_SHORT).show();
        }

    }

    private void displayPDF(String assetFileName) {
        try {
            AssetManager assetManager = getAssets();

            InputStream inputStream = assetManager.open(assetFileName);

            pdfView.fromStream(inputStream)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .swipeHorizontal(false)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .enableAnnotationRendering(true)
                    .pageFitPolicy(FitPolicy.WIDTH)
                    .load();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading PDF file", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean listAssetFiles(String path) {
        ArrayList<String> files = new ArrayList<>();
        String[] list;
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
