package com.example.digitalwellbeing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("carpediem_prefs", MODE_PRIVATE);

        Button btnOverlay = findViewById(R.id.btn_overlay_permission);
        Button btnAccessibility = findViewById(R.id.btn_accessibility_permission);

        btnOverlay.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Permissão de sobreposição já concedida!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAccessibility.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });

        SwitchCompat switchGlobal = findViewById(R.id.switch_global);
        SwitchCompat switchTiktok = findViewById(R.id.switch_tiktok);
        SwitchCompat switchYoutube = findViewById(R.id.switch_youtube);
        SwitchCompat switchInstagram = findViewById(R.id.switch_instagram);

        switchGlobal.setChecked(prefs.getBoolean("is_blocking_enabled", true));
        switchTiktok.setChecked(prefs.getBoolean("block_tiktok", true));
        switchYoutube.setChecked(prefs.getBoolean("block_youtube", true));
        switchInstagram.setChecked(prefs.getBoolean("block_instagram", true));

        switchGlobal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("is_blocking_enabled", isChecked).apply();
            switchTiktok.setEnabled(isChecked);
            switchYoutube.setEnabled(isChecked);
            switchInstagram.setEnabled(isChecked);
        });
        
        switchTiktok.setEnabled(switchGlobal.isChecked());
        switchYoutube.setEnabled(switchGlobal.isChecked());
        switchInstagram.setEnabled(switchGlobal.isChecked());

        switchTiktok.setOnCheckedChangeListener((buttonView, isChecked) -> 
            prefs.edit().putBoolean("block_tiktok", isChecked).apply()
        );

        switchYoutube.setOnCheckedChangeListener((buttonView, isChecked) -> 
            prefs.edit().putBoolean("block_youtube", isChecked).apply()
        );

        switchInstagram.setOnCheckedChangeListener((buttonView, isChecked) -> 
            prefs.edit().putBoolean("block_instagram", isChecked).apply()
        );
    }
}
