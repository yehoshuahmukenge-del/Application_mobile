package cd.esforca.cliniquedor.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PrescriptionWithDetails {
    @Embedded
    public Prescription prescription;

    @Relation(
        parentColumn = "id_patient",
        entityColumn = "id_patient_affilie"
    )
    public PatientAffilie patient;

    @Relation(
        parentColumn = "id_medecin",
        entityColumn = "id_medecin"
    )
    public Medecin medecin;
}
