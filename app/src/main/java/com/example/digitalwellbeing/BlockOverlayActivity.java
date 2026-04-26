package com.example.digitalwellbeing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class BlockOverlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_overlay);

        Button btnLeave = findViewById(R.id.btn_leave);
        btnLeave.setOnClickListener(v -> {
            // Acha o Home Launcher (fecha o app atual e volta pra tela inicial)
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            
            // Depois finaliza essa tela de aviso
            finish();
        });
    }
    
    @Override
    public void onBackPressed() {
        // Bloqueia o botão de voltar para forçar o usuário a ir pra home
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }
}
