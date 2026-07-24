package cd.esforca.cliniquedor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import cd.esforca.cliniquedor.model.Examen;
import cd.esforca.cliniquedor.model.Medecin;
import cd.esforca.cliniquedor.model.PatientAffilie;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddExamenActivity extends AppCompatActivity {

    private TextInputEditText etCode, etLibelle, etMesure, etCategorie, etDate;
    private AutoCompleteTextView actvPatient, actvMedecin;
    private CliniqueViewModel viewModel;
    private Integer examenId = null;
    private List<PatientAffilie> patientsList = new ArrayList<>();
    private List<Medecin> medecinsList = new ArrayList<>();
    private Integer selectedPatientId = null;
    private Integer selectedMedecinId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_examen);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);

        etCode = findViewById(R.id.etCodeExamen);
        etLibelle = findViewById(R.id.etLibelleExamen);
        etMesure = findViewById(R.id.etMesureExamen);
        etCategorie = findViewById(R.id.etCategorieExamen);
        etDate = findViewById(R.id.etDateExamen);
        actvPatient = findViewById(R.id.actvPatient);
        actvMedecin = findViewById(R.id.actvMedecin);

        setupDatePicker();
        setupDropdowns();

        if (getIntent().hasExtra("examen_id")) {
            examenId = getIntent().getIntExtra("examen_id", -1);
            etCode.setText(getIntent().getStringExtra("examen_code"));
            etLibelle.setText(getIntent().getStringExtra("examen_libelle"));
            etMesure.setText(getIntent().getStringExtra("examen_mesure"));
            etCategorie.setText(getIntent().getStringExtra("examen_categorie"));
            etDate.setText(getIntent().getStringExtra("examen_date"));
            selectedPatientId = getIntent().getIntExtra("examen_patient_id", -1);
            selectedMedecinId = getIntent().getIntExtra("examen_medecin_id", -1);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Modifier l'examen");
            }
        }

        findViewById(R.id.btnSaveExamen).setOnClickListener(v -> saveExamen());
        
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupDatePicker() {
        etDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String date = String.format(Locale.FRANCE, "%02d/%02d/%d", dayOfMonth, (month + 1), year);
                etDate.setText(date);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void setupDropdowns() {
        viewModel.getAllPatients().observe(this, patients -> {
            this.patientsList = patients;
            List<String> names = new ArrayList<>();
            for (PatientAffilie p : patients) {
                names.add(p.getNom() + " " + p.getPrenom());
                if (selectedPatientId != null && p.getId() == selectedPatientId) {
                    actvPatient.setText(p.getNom() + " " + p.getPrenom(), false);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
            actvPatient.setAdapter(adapter);
        });

        actvPatient.setOnItemClickListener((parent, view, position, id) -> {
            selectedPatientId = patientsList.get(position).getId();
        });

        viewModel.getAllMedecins().observe(this, medecins -> {
            this.medecinsList = medecins;
            List<String> names = new ArrayList<>();
            for (Medecin m : medecins) {
                names.add(m.getNom() + " " + m.getPrenom());
                if (selectedMedecinId != null && m.getId() == selectedMedecinId) {
                    actvMedecin.setText(m.getNom() + " " + m.getPrenom(), false);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
            actvMedecin.setAdapter(adapter);
        });

        actvMedecin.setOnItemClickListener((parent, view, position, id) -> {
            selectedMedecinId = medecinsList.get(position).getId();
        });
    }

    private void saveExamen() {
        String code = etCode.getText().toString().trim();
        String libelle = etLibelle.getText().toString().trim();
        String mesure = etMesure.getText().toString().trim();
        String categorie = etCategorie.getText().toString().trim();
        String date = etDate.getText().toString().trim();

        if (code.isEmpty() || libelle.isEmpty() || selectedPatientId == null || selectedMedecinId == null) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        Examen examen = new Examen(code, libelle, mesure, categorie);
        examen.setPatientId(selectedPatientId);
        examen.setMedecinId(selectedMedecinId);
        examen.setDate(date);

        if (examenId != null) {
            examen.setId(examenId);
        }

        viewModel.insertExamen(examen);
        Toast.makeText(this, "Examen enregistré", Toast.LENGTH_SHORT).show();
        finish();
    }
}
