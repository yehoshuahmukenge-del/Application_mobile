package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "contenir_medicament",
        primaryKeys = {"id_prescription", "id_medicament"},
        foreignKeys = {
                @ForeignKey(entity = Prescription.class,
                        parentColumns = "id_prescription",
                        childColumns = "id_prescription",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Medicament.class,
                        parentColumns = "id_medicament",
                        childColumns = "id_medicament",
                        onDelete = ForeignKey.CASCADE)
        })
public class ContenirMedicament {

    @ColumnInfo(name = "id_prescription")
    private int idPrescription;

    @ColumnInfo(name = "id_medicament")
    private int idMedicament;

    @ColumnInfo(name = "quantite")
    private int quantite;

    @ColumnInfo(name = "posologie")
    private String posologie;

    public ContenirMedicament(int idPrescription, int idMedicament, int quantite, String posologie) {
        this.idPrescription = idPrescription;
        this.idMedicament = idMedicament;
        this.quantite = quantite;
        this.posologie = posologie;
    }

    public int getIdPrescription() { return idPrescription; }
    public void setIdPrescription(int idPrescription) { this.idPrescription = idPrescription; }

    public int getIdMedicament() { return idMedicament; }
    public void setIdMedicament(int idMedicament) { this.idMedicament = idMedicament; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public String getPosologie() { return posologie; }
    public void setPosologie(String posologie) { this.posologie = posologie; }
}
