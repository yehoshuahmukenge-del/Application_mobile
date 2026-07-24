package cd.esforca.cliniquedor;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CliniqueViewModel viewModel;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
            }

            drawerLayout = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);

            if (drawerLayout != null && toolbar != null) {
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawerLayout, toolbar, R.string.menu_dashboard, R.string.menu_dashboard);
                drawerLayout.addDrawerListener(toggle);
                toggle.syncState();
            }

            viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);

            setupDashboard();
            if (navigationView != null) {
                setupNavigation(navigationView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupNavigation(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            android.content.Intent intent = null;

            if (id == R.id.nav_patients) {
                intent = new android.content.Intent(MainActivity.this, PatientListActivity.class);
            } else if (id == R.id.nav_medecins) {
                intent = new android.content.Intent(MainActivity.this, MedecinListActivity.class);
            } else if (id == R.id.nav_entreprises) {
                intent = new android.content.Intent(MainActivity.this, EntrepriseListActivity.class);
            } else if (id == R.id.nav_paiements) {
                intent = new android.content.Intent(MainActivity.this, PaymentActivity.class);
            } else if (id == R.id.nav_prescriptions) {
                intent = new android.content.Intent(MainActivity.this, PrescriptionListActivity.class);
            } else if (id == R.id.nav_examens) {
                intent = new android.content.Intent(MainActivity.this, ExamenListActivity.class);
            } else if (id == R.id.nav_stats) {
                intent = new android.content.Intent(MainActivity.this, StatsActivity.class);
            } else if (id == R.id.nav_dashboard) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

            if (intent != null) {
                startActivity(intent);
            }
            
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void setupDashboard() {
        TextView tvPatientCount = findViewById(R.id.tvPatientCount);
        TextView tvMedecinCount = findViewById(R.id.tvMedecinCount);
        TextView tvConsultationCount = findViewById(R.id.tvConsultationCount);
        TextView tvRevenue = findViewById(R.id.tvRevenue);

        viewModel.getAllPatients().observe(this, patients -> {
            if (patients != null && tvPatientCount != null) {
                tvPatientCount.setText(String.valueOf(patients.size()));
            }
        });

        viewModel.getAllMedecins().observe(this, medecins -> {
            if (medecins != null && tvMedecinCount != null) {
                tvMedecinCount.setText(String.valueOf(medecins.size()));
            }
        });

        viewModel.getAllPaiements().observe(this, paiements -> {
            if (paiements != null && !paiements.isEmpty()) {
                double total = 0;
                for (cd.esforca.cliniquedor.model.Paiement p : paiements) {
                    total += p.getMontant();
                }
                if (tvRevenue != null) {
                    tvRevenue.setText(String.format(Locale.getDefault(), "%,.0f FC", total));
                }
                if (tvConsultationCount != null) {
                    tvConsultationCount.setText(String.valueOf(paiements.size()));
                }
            } else {
                if (tvRevenue != null) tvRevenue.setText("0 FC");
                if (tvConsultationCount != null) tvConsultationCount.setText("0");
            }
        });
    }
}
