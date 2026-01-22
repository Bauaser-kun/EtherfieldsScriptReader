package com.PortalGames.PortalDEX;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class AudioManager {
    private Context context;
    private float length;
    private MediaPlayer mediaPlayer;
    private android.media.AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mCompletitionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    public AudioManager (Context context) {
        this.context = context.getApplicationContext();
    }

    public void runAudioFile(String assetFileName) {
        releaseMediaPlayer(); // Release any existing media player instance

        mAudioManager = (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        // Request audio focus
        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN);

        if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            try {
                // Initialize MediaPlayer
                mediaPlayer = new MediaPlayer();

                // Open the audio file from the assets folder
                AssetFileDescriptor afd = context.getAssets().openFd(assetFileName);

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
                Toast.makeText(context, "Error playing audio file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Could not gain audio focus", Toast.LENGTH_SHORT).show();
        }
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    private android.media.AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mediaPlayer.pause();
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                length = mediaPlayer.getCurrentPosition();
                Log.d("loss focus", "loss of focus");
                releaseMediaPlayer();
            }
        }
    };
}
