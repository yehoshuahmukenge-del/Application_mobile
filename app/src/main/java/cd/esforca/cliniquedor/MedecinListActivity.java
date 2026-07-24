package cd.esforca.cliniquedor;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.adapter.MedecinAdapter;
import cd.esforca.cliniquedor.model.Medecin;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;
import android.widget.Toast;

public class MedecinListActivity extends AppCompatActivity {

    private CliniqueViewModel viewModel;
    private MedecinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medecin_list);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);
        
        RecyclerView recyclerView = findViewById(R.id.rvMedecins);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedecinAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnMedecinClickListener(new MedecinAdapter.OnMedecinClickListener() {
            @Override
            public void onMedecinClick(Medecin medecin) {
                android.content.Intent intent = new android.content.Intent(MedecinListActivity.this, AddMedecinActivity.class);
                intent.putExtra("MEDECIN_ID", medecin.getId());
                intent.putExtra("MEDECIN_NOM", medecin.getNom());
                intent.putExtra("MEDECIN_SPEC", medecin.getSpecialite());
                intent.putExtra("MEDECIN_RPPS", medecin.getNumeroRpps());
                intent.putExtra("MEDECIN_TEL", medecin.getTelephone());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Medecin medecin) {
                new androidx.appcompat.app.AlertDialog.Builder(MedecinListActivity.this)
                        .setTitle("Supprimer")
                        .setMessage("Voulez-vous supprimer ce médecin ?")
                        .setPositiveButton("Oui", (dialog, which) -> {
                            viewModel.deleteMedecin(medecin);
                            Toast.makeText(MedecinListActivity.this, "Médecin supprimé", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Non", null)
                        .show();
            }
        });

        viewModel.getAllMedecins().observe(this, medecins -> {
            adapter.submitList(medecins);
        });

        findViewById(R.id.fabAddMedecin).setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(MedecinListActivity.this, AddMedecinActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }
}
