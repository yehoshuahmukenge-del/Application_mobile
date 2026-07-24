package cd.esforca.cliniquedor;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.adapter.ExamenAdapter;
import cd.esforca.cliniquedor.model.Examen;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;

public class ExamenListActivity extends AppCompatActivity {

    private CliniqueViewModel viewModel;
    private ExamenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen_list);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);
        setupRecyclerView();

        viewModel.getAllExamensWithDetails().observe(this, examens -> {
            adapter.submitList(examens);
        });

        findViewById(R.id.fabAddExamen).setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, AddExamenActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvExamens);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExamenAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnExamenClickListener(examen -> {
            android.content.Intent intent = new android.content.Intent(this, AddExamenActivity.class);
            intent.putExtra("examen_id", examen.getId());
            intent.putExtra("examen_code", examen.getCode());
            intent.putExtra("examen_libelle", examen.getLibelle());
            intent.putExtra("examen_mesure", examen.getMesure());
            intent.putExtra("examen_categorie", examen.getCategorie());
            intent.putExtra("examen_date", examen.getDate());
            intent.putExtra("examen_patient_id", examen.getPatientId());
            intent.putExtra("examen_medecin_id", examen.getMedecinId());
            startActivity(intent);
        });

        adapter.setOnExamenLongClickListener(examen -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Supprimer l'examen")
                .setMessage("Voulez-vous vraiment supprimer " + examen.getLibelle() + " ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    viewModel.deleteExamen(examen);
                    android.widget.Toast.makeText(this, "Examen supprimé", android.widget.Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annuler", null)
                .show();
        });
    }
}
