package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "effectuer_examen",
        primaryKeys = {"id_patient_affilie", "id_examen", "id_medecin"},
        foreignKeys = {
                @ForeignKey(entity = PatientAffilie.class,
                        parentColumns = "id_patient_affilie",
                        childColumns = "id_patient_affilie",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Examen.class,
                        parentColumns = "id_examen",
                        childColumns = "id_examen",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Medecin.class,
                        parentColumns = "id_medecin",
                        childColumns = "id_medecin",
                        onDelete = ForeignKey.CASCADE)
        })
public class EffectuerExamen {

    @ColumnInfo(name = "id_patient_affilie")
    private int idPatient;

    @ColumnInfo(name = "id_examen")
    private int idExamen;

    @ColumnInfo(name = "id_medecin")
    private int idMedecin;

    @ColumnInfo(name = "date_effectuation")
    private String date;

    @ColumnInfo(name = "heure_effectuation")
    private String heure;

    public EffectuerExamen(int idPatient, int idExamen, int idMedecin, String date, String heure) {
        this.idPatient = idPatient;
        this.idExamen = idExamen;
        this.idMedecin = idMedecin;
        this.date = date;
        this.heure = heure;
    }

    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }

    public int getIdExamen() { return idExamen; }
    public void setIdExamen(int idExamen) { this.idExamen = idExamen; }

    public int getIdMedecin() { return idMedecin; }
    public void setIdMedecin(int idMedecin) { this.idMedecin = idMedecin; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getHeure() { return heure; }
    public void setHeure(String heure) { this.heure = heure; }
}
