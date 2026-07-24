package cd.esforca.cliniquedor;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import cd.esforca.cliniquedor.adapter.PatientAdapter;
import cd.esforca.cliniquedor.model.PatientAffilie;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;
import java.util.ArrayList;
import java.util.List;

public class PatientListActivity extends AppCompatActivity {

    private CliniqueViewModel viewModel;
    private PatientAdapter adapter;
    private List<PatientAffilie> allPatients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);
        setupRecyclerView();
        setupSearch();

        viewModel.getAllPatients().observe(this, patients -> {
            allPatients = patients;
            adapter.submitList(patients);
        });

        findViewById(R.id.fabAddPatient).setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(PatientListActivity.this, AddPatientActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvPatients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnPatientClickListener(new PatientAdapter.OnPatientClickListener() {
            @Override
            public void onPatientClick(PatientAffilie patient) {
                android.content.Intent intent = new android.content.Intent(PatientListActivity.this, AddPatientActivity.class);
                intent.putExtra("patient_id", patient.getId());
                intent.putExtra("nom", patient.getNom());
                intent.putExtra("postnom", patient.getPostnom());
                intent.putExtra("prenom", patient.getPrenom());
                intent.putExtra("sexe", patient.getSexe());
                intent.putExtra("matricule", patient.getMatricule());
                intent.putExtra("adresse", patient.getAdresse());
                intent.putExtra("entreprise_id", patient.getIdEntreprise());
                startActivity(intent);
            }
        });

        adapter.setOnPatientLongClickListener(patient -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Supprimer l'affilié")
                    .setMessage("Voulez-vous vraiment supprimer " + patient.getNom() + " " + patient.getPostnom() + " ?")
                    .setPositiveButton("Supprimer", (dialog, which) -> {
                        // Il manque peut-être deletePatient dans le ViewModel
                        // On va vérifier et l'ajouter si besoin
                        viewModel.deletePatient(patient);
                        android.widget.Toast.makeText(this, "Affilié supprimé", android.widget.Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        });
    }

    private void setupSearch() {
        TextInputEditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPatients(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterPatients(String query) {
        List<PatientAffilie> filteredList = new ArrayList<>();
        for (PatientAffilie patient : allPatients) {
            if (patient.getNom().toLowerCase().contains(query.toLowerCase()) ||
                patient.getPrenom().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(patient);
            }
        }
        adapter.submitList(filteredList);
    }
}
