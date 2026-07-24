package cd.esforca.cliniquedor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.R;
import cd.esforca.cliniquedor.model.Examen;
import cd.esforca.cliniquedor.model.ExamenWithDetails;
import java.util.ArrayList;
import java.util.List;

public class ExamenAdapter extends RecyclerView.Adapter<ExamenAdapter.ExamenViewHolder> {

    private List<ExamenWithDetails> examens = new ArrayList<>();
    private OnExamenClickListener listener;
    private OnExamenLongClickListener longListener;

    public interface OnExamenClickListener {
        void onExamenClick(Examen examen);
    }

    public interface OnExamenLongClickListener {
        void onExamenLongClick(Examen examen);
    }

    public void setOnExamenClickListener(OnExamenClickListener listener) {
        this.listener = listener;
    }

    public void setOnExamenLongClickListener(OnExamenLongClickListener longListener) {
        this.longListener = longListener;
    }

    @NonNull
    @Override
    public ExamenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_examen, parent, false);
        return new ExamenViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamenViewHolder holder, int position) {
        ExamenWithDetails currentWithDetails = examens.get(position);
        Examen current = currentWithDetails.examen;
        holder.tvLibelle.setText(current.getLibelle());
        
        String patientName = currentWithDetails.patient != null ? 
            currentWithDetails.patient.getNom() + " " + currentWithDetails.patient.getPrenom() : "Inconnu";
            
        holder.tvDetails.setText(current.getCode() + " | " + patientName);
        holder.tvMesure.setText("Date: " + current.getDate() + " | " + current.getMesure());
    }

    @Override
    public int getItemCount() {
        return examens.size();
    }

    public void submitList(List<ExamenWithDetails> examens) {
        this.examens = examens;
        notifyDataSetChanged();
    }

    class ExamenViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLibelle;
        private TextView tvDetails;
        private TextView tvMesure;

        public ExamenViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLibelle = itemView.findViewById(R.id.tv_examen_libelle);
            tvDetails = itemView.findViewById(R.id.tv_examen_details);
            tvMesure = itemView.findViewById(R.id.tv_examen_mesure);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onExamenClick(examens.get(position).examen);
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (longListener != null && position != RecyclerView.NO_POSITION) {
                    longListener.onExamenLongClick(examens.get(position).examen);
                    return true;
                }
                return false;
            });
        }
    }
}
