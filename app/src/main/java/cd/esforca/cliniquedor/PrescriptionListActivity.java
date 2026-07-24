package cd.esforca.cliniquedor;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.adapter.PrescriptionAdapter;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;

public class PrescriptionListActivity extends AppCompatActivity {

    private CliniqueViewModel viewModel;
    private PrescriptionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);
        setupRecyclerView();

        viewModel.getAllPrescriptionsWithDetails().observe(this, prescriptions -> {
            adapter.submitList(prescriptions);
        });

        findViewById(R.id.fabAddPrescription).setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, AddPrescriptionActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvPrescriptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrescriptionAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnPrescriptionClickListener(prescription -> {
            android.content.Intent intent = new android.content.Intent(this, AddPrescriptionActivity.class);
            intent.putExtra("prescription_id", prescription.getId());
            intent.putExtra("prescription_date", prescription.getDate());
            intent.putExtra("prescription_observation", prescription.getObservation());
            intent.putExtra("prescription_patient_id", prescription.getPatientId());
            intent.putExtra("prescription_medecin_id", prescription.getMedecinId());
            startActivity(intent);
        });

        adapter.setOnPrescriptionLongClickListener(prescription -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Supprimer la prescription")
                .setMessage("Voulez-vous vraiment supprimer cette prescription ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    viewModel.deletePrescription(prescription);
                    android.widget.Toast.makeText(this, "Prescription supprimée", android.widget.Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annuler", null)
                .show();
        });
    }
}
