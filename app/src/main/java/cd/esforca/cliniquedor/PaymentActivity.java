package cd.esforca.cliniquedor;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.adapter.PaiementAdapter;
import cd.esforca.cliniquedor.viewmodel.CliniqueViewModel;
import android.widget.Toast;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    private CliniqueViewModel viewModel;
    private TextView tvTotalRevenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        RecyclerView rvPayments = findViewById(R.id.rvPayments);
        rvPayments.setLayoutManager(new LinearLayoutManager(this));
        rvPayments.setHasFixedSize(true);

        final PaiementAdapter adapter = new PaiementAdapter();
        rvPayments.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CliniqueViewModel.class);

        viewModel.getAllPaiements().observe(this, paiements -> {
            if (paiements != null) {
                adapter.setPaiements(paiements);
                double total = 0;
                for (cd.esforca.cliniquedor.model.Paiement p : paiements) {
                    total += p.getMontant();
                }
                tvTotalRevenue.setText(String.format(Locale.getDefault(), "%,.0f FC", total));
            }
        });

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
        
        findViewById(R.id.fabAddPayment).setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(this, AddPaymentActivity.class);
            startActivity(intent);
        });
    }
    
    // Ajout du Toast pour éviter les erreurs de compilation si non importé
    private void showToast(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}
