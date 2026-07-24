package cd.esforca.cliniquedor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import cd.esforca.cliniquedor.model.PatientAffilie;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;
import java.util.Calendar;
import java.util.Locale;

public class AddPatientActivity extends AppCompatActivity {

    private TextInputEditText etNom, etPostnom, etPrenom, etTelephone, etAdresse, etDateNaissance;
    private AutoCompleteTextView autoSexe, autoEntreprise;
    private ImageView ivPatientPhoto;
    private CliniqueViewModel viewModel;
    private Uri selectedImageUri;
    private Integer patientId = null;
    private java.util.List<cd.esforca.cliniquedor.model.EntreprisePartenaire> entrepriseList = new java.util.ArrayList<>();

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivPatientPhoto.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);

        initViews();
        setupSpinners();
        setupDatePicker();
        setupImagePicker();
        checkEditMode();

        findViewById(R.id.btnSave).setOnClickListener(v -> savePatient());
        
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nouveau Affilié");
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initViews() {
        etNom = findViewById(R.id.etNom);
        etPostnom = findViewById(R.id.etPostnom);
        etPrenom = findViewById(R.id.etPrenom);
        etTelephone = findViewById(R.id.etTelephone);
        etAdresse = findViewById(R.id.etAdresse);
        etDateNaissance = findViewById(R.id.etDateNaissance);
        autoSexe = findViewById(R.id.autoSexe);
        autoEntreprise = findViewById(R.id.autoEntreprise);
        ivPatientPhoto = findViewById(R.id.ivPatientPhoto);
    }

    private void setupImagePicker() {
        ivPatientPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });
    }

    private void setupDatePicker() {
        etDateNaissance.setFocusable(false);
        etDateNaissance.setClickable(true);
        etDateNaissance.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        String date = String.format(Locale.FRANCE, "%02d/%02d/%d", dayOfMonth, (monthOfYear + 1), year1);
                        etDateNaissance.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void setupSpinners() {
        String[] sexeOptions = {"M", "F"};
        ArrayAdapter<String> sexeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sexeOptions);
        autoSexe.setAdapter(sexeAdapter);

        viewModel.getAllEntreprises().observe(this, entreprises -> {
            if (entreprises != null) {
                entrepriseList = entreprises;
                java.util.List<String> names = new java.util.ArrayList<>();
                for (cd.esforca.cliniquedor.model.EntreprisePartenaire e : entreprises) {
                    names.add(e.getLibelle());
                }
                ArrayAdapter<String> entAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
                autoEntreprise.setAdapter(entAdapter);
            }
        });
    }

    private void checkEditMode() {
        if (getIntent().hasExtra("patient_id")) {
            patientId = getIntent().getIntExtra("patient_id", -1);
            etNom.setText(getIntent().getStringExtra("nom"));
            etPostnom.setText(getIntent().getStringExtra("postnom"));
            etPrenom.setText(getIntent().getStringExtra("prenom"));
            autoSexe.setText(getIntent().getStringExtra("sexe"), false);
            etAdresse.setText(getIntent().getStringExtra("adresse"));
            
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Modifier l'affilié");
            }
        }
    }

    private void savePatient() {
        String nom = etNom.getText().toString().trim();
        String postnom = etPostnom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String sexe = autoSexe.getText().toString().trim();
        String telephone = etTelephone.getText().toString().trim();
        String adresse = etAdresse.getText().toString().trim();
        String dateNais = etDateNaissance.getText().toString().trim();
        String entName = autoEntreprise.getText().toString().trim();

        if (nom.isEmpty() || postnom.isEmpty() || sexe.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedEntId = -1;
        for (cd.esforca.cliniquedor.model.EntreprisePartenaire e : entrepriseList) {
            if (e.getLibelle().equals(entName)) {
                selectedEntId = e.getId();
                break;
            }
        }

        if (selectedEntId == -1) {
            Toast.makeText(this, "Veuillez sélectionner une entreprise valide", Toast.LENGTH_SHORT).show();
            return;
        }

        PatientAffilie patient = new PatientAffilie();
        if (patientId != null) {
            patient.setId(patientId);
        }
        patient.setNom(nom);
        patient.setPostnom(postnom);
        patient.setPrenom(prenom);
        patient.setSexe(sexe);
        patient.setTelephone(telephone);
        patient.setAdresse(adresse);
        patient.setDateNaissance(dateNais);
        patient.setIdEntreprise(selectedEntId);
        
        // Génération du matricule selon le style Béthanie
        String matricule = "AFF-" + (System.currentTimeMillis() % 100000);
        patient.setMatricule(matricule);

        viewModel.insertPatient(patient);
        Toast.makeText(this, "Patient " + nom + " enregistré avec succès !", Toast.LENGTH_SHORT).show();
        finish();
    }
}
