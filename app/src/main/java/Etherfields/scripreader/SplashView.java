package Etherfields.scripreader;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import Etherfields.scripreader.Method;

public class SplashView extends AppCompatActivity {
    private File storage;
    private String[] allPath;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        allPath = StorageUtil.getStorageDirectories(this);

        for (String path: allPath) {
            storage = new File(path);
            Method.loadDirectoryFiles(storage);
        }

        Intent intent = new Intent(SplashView.this, MainActivity.class);
        startActivity(intent);
    }
}
