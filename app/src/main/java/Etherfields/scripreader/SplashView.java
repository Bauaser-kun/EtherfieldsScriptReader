package Etherfields.scripreader;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashView extends AppCompatActivity {
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        Intent intent = new Intent(SplashView.this, MainActivity.class);
        startActivity(intent);
    }
}
