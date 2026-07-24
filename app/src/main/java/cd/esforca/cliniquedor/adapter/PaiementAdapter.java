package cd.esforca.cliniquedor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.R;
import cd.esforca.cliniquedor.model.Paiement;
import java.util.ArrayList;
import java.util.List;

public class PaiementAdapter extends RecyclerView.Adapter<PaiementAdapter.PaiementHolder> {
    private List<Paiement> paiements = new ArrayList<>();

    @NonNull
    @Override
    public PaiementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_paiement, parent, false);
        return new PaiementHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaiementHolder holder, int position) {
        Paiement current = paiements.get(position);
        holder.textViewMotif.setText(current.getMotif());
        holder.textViewMontant.setText(String.format("%.2f $", current.getMontant()));
        holder.textViewDate.setText(current.getDate());
    }

    @Override
    public int getItemCount() {
        return paiements.size();
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
        notifyDataSetChanged();
    }

    class PaiementHolder extends RecyclerView.ViewHolder {
        private TextView textViewMotif;
        private TextView textViewMontant;
        private TextView textViewDate;

        public PaiementHolder(View itemView) {
            super(itemView);
            textViewMotif = itemView.findViewById(R.id.text_view_motif);
            textViewMontant = itemView.findViewById(R.id.text_view_montant);
            textViewDate = itemView.findViewById(R.id.text_view_date);
        }
    }
}
