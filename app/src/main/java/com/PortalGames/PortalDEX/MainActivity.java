package com.PortalGames.PortalDEX;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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
    private PDFManager pdfManager;
    private MediaPlayer.OnCompletionListener mCompletitionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private float length;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mediaPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                length = mediaPlayer.getCurrentPosition();
                Log.d("loss focus", "loss of focus");
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> pdfList =
                new ArrayList<>(PDFManager.getPDFListFromAssets(this));

        recyclerViewAdapter = new RecyclerViewAdapter(
                this,
                pdfList,
                pdfName -> {
                    displayPDF(pdfName);
                    // runAudioFile(pdfName.replace(".pdf", ".mp3"));
                }
        );

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
                    //runAudioFile(eventNumber + ".mp3");
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid event number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void runAudioFile(String assetFileName) {
        releaseMediaPlayer(); // Release any existing media player instance

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Request audio focus
        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            try {
                // Initialize MediaPlayer
                mediaPlayer = new MediaPlayer();

                // Open the audio file from the assets folder
                AssetFileDescriptor afd = getAssets().openFd(assetFileName);

                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();

                // Prepare the MediaPlayer asynchronously
                mediaPlayer.prepare();

                // Set the onCompletion listener to release media player once the audio finishes
                mediaPlayer.setOnCompletionListener(mCompletitionListener);

                // Start the audio file
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error playing audio file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Could not gain audio focus", Toast.LENGTH_SHORT).show();
        }
    }


    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
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

}
