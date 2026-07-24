package cd.esforca.cliniquedor;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import cd.esforca.cliniquedor.model.Paiement;
import cd.esforca.cliniquedor.model.PatientAffilie;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddPaymentActivity extends AppCompatActivity {

    private AutoCompleteTextView autoPatient;
    private TextInputEditText etMotif, etMontant;
    private CliniqueViewModel viewModel;
    private List<PatientAffilie> patientList = new ArrayList<>();
    private PatientAffilie selectedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);

        autoPatient = findViewById(R.id.autoPatient);
        etMotif = findViewById(R.id.etMotif);
        etMontant = findViewById(R.id.etMontant);

        setupPatientSpinner();

        findViewById(R.id.btnSave).setOnClickListener(v -> savePayment());

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupPatientSpinner() {
        viewModel.getAllPatients().observe(this, patients -> {
            if (patients != null) {
                patientList = patients;
                List<String> names = new ArrayList<>();
                for (PatientAffilie p : patients) {
                    names.add(p.getNom() + " " + p.getPrenom() + " (" + p.getMatricule() + ")");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
                autoPatient.setAdapter(adapter);
            }
        });

        autoPatient.setOnItemClickListener((parent, view, position, id) -> {
            String selection = (String) parent.getItemAtPosition(position);
            for (PatientAffilie p : patientList) {
                String fullName = p.getNom() + " " + p.getPrenom() + " (" + p.getMatricule() + ")";
                if (fullName.equals(selection)) {
                    selectedPatient = p;
                    break;
                }
            }
        });
    }

    private void savePayment() {
        String motif = etMotif.getText().toString().trim();
        String montantStr = etMontant.getText().toString().trim();

        if (selectedPatient == null || motif.isEmpty() || montantStr.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double montant = Double.parseDouble(montantStr);
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            // idFrais est mis à 0 car nous avons supprimé la contrainte stricte
            Paiement paiement = new Paiement(motif, date, montant, selectedPatient.getId(), 0);
            viewModel.insertPaiement(paiement);

            Toast.makeText(this, "Paiement enregistré avec succès !", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Montant invalide", Toast.LENGTH_SHORT).show();
        }
    }
}
