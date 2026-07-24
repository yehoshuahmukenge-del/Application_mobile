package cd.esforca.cliniquedor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import cd.esforca.cliniquedor.model.Medecin;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;

public class AddMedecinActivity extends AppCompatActivity {

    private EditText etNom, etSpecialite, etRPPS, etTelephone;
    private ImageView ivMedecinPhoto;
    private CliniqueViewModel viewModel;
    private Uri selectedImageUri;
    private int medecinId = -1;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivMedecinPhoto.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medecin);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);

        etNom = findViewById(R.id.etNom);
        etSpecialite = findViewById(R.id.etSpecialite);
        etRPPS = findViewById(R.id.etRPPS);
        etTelephone = findViewById(R.id.etTelephone);
        ivMedecinPhoto = findViewById(R.id.ivMedecinPhoto);
        Button btnSave = findViewById(R.id.btnSave);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        ivMedecinPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        // Vérifier si on est en mode édition
        if (getIntent().hasExtra("MEDECIN_ID")) {
            medecinId = getIntent().getIntExtra("MEDECIN_ID", -1);
            etNom.setText(getIntent().getStringExtra("MEDECIN_NOM"));
            etSpecialite.setText(getIntent().getStringExtra("MEDECIN_SPEC"));
            etRPPS.setText(getIntent().getStringExtra("MEDECIN_RPPS"));
            etTelephone.setText(getIntent().getStringExtra("MEDECIN_TEL"));
            btnSave.setText("METTRE À JOUR");
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("Modifier Médecin");
        }

        btnSave.setOnClickListener(v -> saveMedecin());
    }

    private void saveMedecin() {
        String nom = etNom.getText().toString().trim();
        String specialite = etSpecialite.getText().toString().trim();
        String rpps = etRPPS.getText().toString().trim();
        String tel = etTelephone.getText().toString().trim();

        if (nom.isEmpty() || specialite.isEmpty() || rpps.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        Medecin medecin = new Medecin(nom, specialite, rpps, tel);
        if (medecinId != -1) {
            medecin.setId(medecinId);
            // Ici il faudrait une méthode update dans le ViewModel
            viewModel.insertMedecin(medecin); // Room avec OnConflictStrategy.REPLACE gère l'update
            Toast.makeText(this, "Médecin mis à jour !", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.insertMedecin(medecin);
            Toast.makeText(this, "Médecin ajouté !", Toast.LENGTH_SHORT).show();
        }
        
        finish();
    }
}


