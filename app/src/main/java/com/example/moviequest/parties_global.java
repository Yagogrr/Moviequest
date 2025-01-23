package com.example.moviequest;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class parties_global extends AppCompatActivity {
    Button btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parties_global);
        btn_menu = findViewById(R.id.btn_desplegable);
        registerForContextMenu(btn_menu);

    }
}