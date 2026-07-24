package cd.esforca.cliniquedor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.R;
import cd.esforca.cliniquedor.model.PatientAffilie;
import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private List<PatientAffilie> patients = new ArrayList<>();
    private OnPatientClickListener listener;
    private OnPatientLongClickListener longListener;

    public interface OnPatientClickListener {
        void onPatientClick(PatientAffilie patient);
    }

    public interface OnPatientLongClickListener {
        void onPatientLongClick(PatientAffilie patient);
    }

    public void setOnPatientClickListener(OnPatientClickListener listener) {
        this.listener = listener;
    }

    public void setOnPatientLongClickListener(OnPatientLongClickListener longListener) {
        this.longListener = longListener;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        PatientAffilie currentPatient = patients.get(position);
        
        String nom = currentPatient.getNom() != null ? currentPatient.getNom() : "";
        String postnom = currentPatient.getPostnom() != null ? currentPatient.getPostnom() : "";
        holder.tvName.setText((nom + " " + postnom).trim());
        
        holder.tvDetails.setText("Matricule: " + (currentPatient.getMatricule() != null ? currentPatient.getMatricule() : "N/A"));
        
        holder.tvBadge.setText(currentPatient.getSexe());
        if ("F".equalsIgnoreCase(currentPatient.getSexe())) {
            holder.tvBadge.setBackgroundResource(R.drawable.bg_sex_badge_female);
        } else {
            holder.tvBadge.setBackgroundResource(R.drawable.bg_sex_badge);
        }
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void submitList(List<PatientAffilie> patients) {
        this.patients = patients;
        notifyDataSetChanged();
    }

    class PatientViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDetails;
        private TextView tvBadge;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_patient_name);
            tvDetails = itemView.findViewById(R.id.tv_patient_details);
            tvBadge = itemView.findViewById(R.id.tv_sex_badge);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onPatientClick(patients.get(position));
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (longListener != null && position != RecyclerView.NO_POSITION) {
                    longListener.onPatientLongClick(patients.get(position));
                    return true;
                }
                return false;
            });
        }
    }
}
