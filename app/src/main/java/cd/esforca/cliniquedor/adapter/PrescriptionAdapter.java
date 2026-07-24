package cd.esforca.cliniquedor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cd.esforca.cliniquedor.R;
import cd.esforca.cliniquedor.model.Prescription;
import cd.esforca.cliniquedor.model.PrescriptionWithDetails;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder> {

    private List<PrescriptionWithDetails> prescriptions = new ArrayList<>();
    private OnPrescriptionClickListener listener;
    private OnPrescriptionLongClickListener longListener;

    public interface OnPrescriptionClickListener {
        void onPrescriptionClick(Prescription prescription);
    }

    public interface OnPrescriptionLongClickListener {
        void onPrescriptionLongClick(Prescription prescription);
    }

    public void setOnPrescriptionClickListener(OnPrescriptionClickListener listener) {
        this.listener = listener;
    }

    public void setOnPrescriptionLongClickListener(OnPrescriptionLongClickListener longListener) {
        this.longListener = longListener;
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prescription, parent, false);
        return new PrescriptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {
        PrescriptionWithDetails currentWithDetails = prescriptions.get(position);
        Prescription current = currentWithDetails.prescription;
        
        String patientName = currentWithDetails.patient != null ? 
            currentWithDetails.patient.getNom() + " " + currentWithDetails.patient.getPrenom() : "Inconnu";
            
        holder.tvDate.setText(current.getDate() + " | " + patientName);
        holder.tvObservation.setText(current.getObservation());
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    public void submitList(List<PrescriptionWithDetails> prescriptions) {
        this.prescriptions = prescriptions;
        notifyDataSetChanged();
    }

    class PrescriptionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvObservation;

        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date_prescription);
            tvObservation = itemView.findViewById(R.id.tv_observation);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onPrescriptionClick(prescriptions.get(position).prescription);
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (longListener != null && position != RecyclerView.NO_POSITION) {
                    longListener.onPrescriptionLongClick(prescriptions.get(position).prescription);
                    return true;
                }
                return false;
            });
        }
    }
}
