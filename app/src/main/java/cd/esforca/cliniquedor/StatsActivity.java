package cd.esforca.cliniquedor;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;

public class StatsActivity extends AppCompatActivity {

    private CliniqueViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);
        
        TextView tvStatsPatients = findViewById(R.id.tvStatsPatients);
        TextView tvStatsRevenue = findViewById(R.id.tvStatsRevenue);
        TextView tvStatsMedecins = findViewById(R.id.tvStatsMedecins);

        viewModel.getPatientsCount().observe(this, count -> {
            tvStatsPatients.setText(String.valueOf(count != null ? count : 0));
        });

        viewModel.getTotalRecettes().observe(this, total -> {
            tvStatsRevenue.setText(String.format("%,.0f FC", total != null ? total : 0.0));
        });

        viewModel.getAllMedecins().observe(this, medecins -> {
            tvStatsMedecins.setText(String.valueOf(medecins != null ? medecins.size() : 0));
        });

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }
}
