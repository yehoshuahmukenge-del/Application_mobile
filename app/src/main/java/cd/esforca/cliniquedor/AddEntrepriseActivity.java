package cd.esforca.cliniquedor;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import cd.esforca.cliniquedor.model.EntreprisePartenaire;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;

public class AddEntrepriseActivity extends AppCompatActivity {

    private TextInputEditText etLibelle, etService, etAdresse, etDepartement;
    private CliniqueViewModel viewModel;
    private int entrepriseId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entreprise);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);

        etLibelle = findViewById(R.id.etLibelle);
        etService = findViewById(R.id.etService);
        etAdresse = findViewById(R.id.etAdresse);
        etDepartement = findViewById(R.id.etDepartement);
        android.widget.Button btnSave = findViewById(R.id.btnSave);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nouvelle Entreprise");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Mode édition
        if (getIntent().hasExtra("ENTREPRISE_ID")) {
            entrepriseId = getIntent().getIntExtra("ENTREPRISE_ID", -1);
            etLibelle.setText(getIntent().getStringExtra("ENTREPRISE_LIBELLE"));
            etAdresse.setText(getIntent().getStringExtra("ENTREPRISE_ADRESSE"));
            etService.setText(getIntent().getStringExtra("ENTREPRISE_SERVICE"));
            etDepartement.setText(getIntent().getStringExtra("ENTREPRISE_DEPARTEMENT"));
            btnSave.setText("METTRE À JOUR");
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("Modifier Entreprise");
        }

        btnSave.setOnClickListener(v -> saveEntreprise());
    }

    private void saveEntreprise() {
        String libelle = etLibelle.getText().toString().trim();
        String service = etService.getText().toString().trim();
        String adresse = etAdresse.getText().toString().trim();
        String departement = etDepartement.getText().toString().trim();

        if (libelle.isEmpty()) {
            Toast.makeText(this, "Le libellé est obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }

        EntreprisePartenaire entreprise = new EntreprisePartenaire(libelle, adresse, service, departement);
        if (entrepriseId != -1) {
            entreprise.setId(entrepriseId);
            viewModel.insertEntreprise(entreprise);
            Toast.makeText(this, "Entreprise mise à jour !", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.insertEntreprise(entreprise);
            Toast.makeText(this, "Entreprise ajoutée !", Toast.LENGTH_SHORT).show();
        }
        
        finish();
    }
}
