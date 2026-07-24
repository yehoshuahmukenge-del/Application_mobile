package cd.esforca.cliniquedor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.R;
import cd.esforca.cliniquedor.model.Medecin;
import java.util.ArrayList;
import java.util.List;

public class MedecinAdapter extends RecyclerView.Adapter<MedecinAdapter.MedecinViewHolder> {

    private List<Medecin> medecins = new ArrayList<>();

    @NonNull
    @Override
    public MedecinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medecin, parent, false);
        return new MedecinViewHolder(itemView);
    }

    public interface OnMedecinClickListener {
        void onMedecinClick(Medecin medecin);
        void onDeleteClick(Medecin medecin);
    }

    private OnMedecinClickListener listener;

    public void setOnMedecinClickListener(OnMedecinClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull MedecinViewHolder holder, int position) {
        Medecin current = medecins.get(position);
        
        String nom = current.getNom() != null ? current.getNom() : "";
        holder.tvName.setText("Dr. " + nom);
        holder.tvSpeciality.setText(current.getSpecialite());
        holder.tvPhone.setText(current.getTelephone());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onMedecinClick(current);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onDeleteClick(current);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return medecins.size();
    }

    public void submitList(List<Medecin> medecins) {
        this.medecins = medecins;
        notifyDataSetChanged();
    }

    public static class MedecinViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName;
        public final TextView tvSpeciality;
        public final TextView tvPhone;

        public MedecinViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_medecin_name);
            tvSpeciality = itemView.findViewById(R.id.tv_medecin_speciality);
            tvPhone = itemView.findViewById(R.id.tv_medecin_phone);
        }
    }
}
