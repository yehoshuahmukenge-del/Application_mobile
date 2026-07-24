package cd.esforca.cliniquedor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Installation du Splash Screen pour une transition fluide
        SplashScreen.installSplashScreen(this);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Configuration du bouton de démarrage
        findViewById(R.id.btn_start).setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            // On ne termine pas forcément l'activité ici si on veut permettre un retour, 
            // mais pour un écran d'accueil/login, on finit généralement.
            finish();
        });
    }
}
