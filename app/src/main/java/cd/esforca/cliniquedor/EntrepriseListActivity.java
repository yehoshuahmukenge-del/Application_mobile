package cd.esforca.cliniquedor;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.adapter.EntrepriseAdapter;
import cd.esforca.cliniquedor.model.EntreprisePartenaire;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;
import android.widget.Toast;

public class EntrepriseListActivity extends AppCompatActivity {

    private CliniqueViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entreprise_list);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.rvEntreprises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); 

        final EntrepriseAdapter adapter = new EntrepriseAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnEntrepriseClickListener(new EntrepriseAdapter.OnEntrepriseClickListener() {
            @Override
            public void onEntrepriseClick(EntreprisePartenaire entreprise) {
                android.content.Intent intent = new android.content.Intent(EntrepriseListActivity.this, AddEntrepriseActivity.class);
                intent.putExtra("ENTREPRISE_ID", entreprise.getId());
                intent.putExtra("ENTREPRISE_LIBELLE", entreprise.getLibelle());
                intent.putExtra("ENTREPRISE_ADRESSE", entreprise.getAdresse());
                intent.putExtra("ENTREPRISE_SERVICE", entreprise.getService());
                intent.putExtra("ENTREPRISE_DEPARTEMENT", entreprise.getDepartement());
                startActivity(intent);
            }

            @Override
            public void onEntrepriseLongClick(EntreprisePartenaire entreprise) {
                new androidx.appcompat.app.AlertDialog.Builder(EntrepriseListActivity.this)
                        .setTitle("Supprimer")
                        .setMessage("Voulez-vous supprimer l'entreprise " + entreprise.getLibelle() + " ?")
                        .setPositiveButton("Oui", (dialog, which) -> {
                            viewModel.deleteEntreprise(entreprise);
                            Toast.makeText(EntrepriseListActivity.this, "Entreprise supprimée", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Non", null)
                        .show();
            }
        });

        viewModel.getAllEntreprises().observe(this, adapter::setEntreprises);

        findViewById(R.id.fabAddEntreprise).setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, AddEntrepriseActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }
}
