package cd.esforca.cliniquedor;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import cd.esforca.cliniquedor.model.Medecin;
import cd.esforca.cliniquedor.model.PatientAffilie;
import cd.esforca.cliniquedor.model.Prescription;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddPrescriptionActivity extends AppCompatActivity {

    private TextInputEditText etDate, etObservation;
    private AutoCompleteTextView actvPatient, actvMedecin;
    private CliniqueViewModel viewModel;
    private Integer prescriptionId = null;
    private List<PatientAffilie> patientsList = new ArrayList<>();
    private List<Medecin> medecinsList = new ArrayList<>();
    private Integer selectedPatientId = null;
    private Integer selectedMedecinId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);

        etDate = findViewById(R.id.etDatePrescription);
        etObservation = findViewById(R.id.etObservation);
        actvPatient = findViewById(R.id.actvPatient);
        actvMedecin = findViewById(R.id.actvMedecin);

        setupDatePicker();
        setupDropdowns();

        if (getIntent().hasExtra("prescription_id")) {
            prescriptionId = getIntent().getIntExtra("prescription_id", -1);
            etDate.setText(getIntent().getStringExtra("prescription_date"));
            etObservation.setText(getIntent().getStringExtra("prescription_observation"));
            selectedPatientId = getIntent().getIntExtra("prescription_patient_id", -1);
            selectedMedecinId = getIntent().getIntExtra("prescription_medecin_id", -1);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Modifier la Prescription");
            }
        }

        findViewById(R.id.btnSavePrescription).setOnClickListener(v -> savePrescription());

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nouvelle Prescription");
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

    private void savePrescription() {
        String date = etDate.getText().toString().trim();
        String obs = etObservation.getText().toString().trim();

        if (date.isEmpty() || obs.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Prescription prescription = new Prescription(date, obs);
        prescription.setPatientId(selectedPatientId);
        prescription.setMedecinId(selectedMedecinId);
        if (prescriptionId != null) {
            prescription.setId(prescriptionId);
        }
        viewModel.insertPrescription(prescription);
        
        Toast.makeText(this, "Prescription enregistrée !", Toast.LENGTH_SHORT).show();
        finish();
    }
}
