package cd.esforca.cliniquedor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.R;
import cd.esforca.cliniquedor.model.EntreprisePartenaire;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseAdapter extends RecyclerView.Adapter<EntrepriseAdapter.EntrepriseHolder> {
    private List<EntreprisePartenaire> entreprises = new ArrayList<>();
    private OnEntrepriseClickListener listener;

    public interface OnEntrepriseClickListener {
        void onEntrepriseClick(EntreprisePartenaire entreprise);
        void onEntrepriseLongClick(EntreprisePartenaire entreprise);
    }

    public void setOnEntrepriseClickListener(OnEntrepriseClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public EntrepriseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entreprise, parent, false);
        return new EntrepriseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntrepriseHolder holder, int position) {
        EntreprisePartenaire current = entreprises.get(position);
        holder.textViewLibelle.setText(current.getLibelle());
        holder.textViewService.setText(current.getService());
        holder.textViewAdresse.setText(current.getAdresse());
    }

    @Override
    public int getItemCount() {
        return entreprises.size();
    }

    public void setEntreprises(List<EntreprisePartenaire> entreprises) {
        this.entreprises = entreprises;
        notifyDataSetChanged();
    }

    class EntrepriseHolder extends RecyclerView.ViewHolder {
        private TextView textViewLibelle;
        private TextView textViewService;
        private TextView textViewAdresse;

        public EntrepriseHolder(View itemView) {
            super(itemView);
            textViewLibelle = itemView.findViewById(R.id.text_view_libelle);
            textViewService = itemView.findViewById(R.id.text_view_service);
            textViewAdresse = itemView.findViewById(R.id.text_view_adresse);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onEntrepriseClick(entreprises.get(position));
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onEntrepriseLongClick(entreprises.get(position));
                    return true;
                }
                return false;
            });
        }
    }
}
