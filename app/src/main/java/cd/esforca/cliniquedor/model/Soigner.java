package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "soigner",
        primaryKeys = {"id_patient_affilie", "id_medecin"},
        foreignKeys = {
                @ForeignKey(entity = PatientAffilie.class,
                        parentColumns = "id_patient_affilie",
                        childColumns = "id_patient_affilie",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Medecin.class,
                        parentColumns = "id_medecin",
                        childColumns = "id_medecin",
                        onDelete = ForeignKey.CASCADE)
        })
public class Soigner {

    @ColumnInfo(name = "id_patient_affilie")
    private int idPatient;

    @ColumnInfo(name = "id_medecin")
    private int idMedecin;

    @ColumnInfo(name = "date_soin")
    private String dateSoin;

    @ColumnInfo(name = "type_soin")
    private String typeSoin;

    public Soigner(int idPatient, int idMedecin, String dateSoin, String typeSoin) {
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
        this.dateSoin = dateSoin;
        this.typeSoin = typeSoin;
    }

    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }

    public int getIdMedecin() { return idMedecin; }
    public void setIdMedecin(int idMedecin) { this.idMedecin = idMedecin; }

    public String getDateSoin() { return dateSoin; }
    public void setDateSoin(String dateSoin) { this.dateSoin = dateSoin; }

    public String getTypeSoin() { return typeSoin; }
    public void setTypeSoin(String typeSoin) { this.typeSoin = typeSoin; }
}
