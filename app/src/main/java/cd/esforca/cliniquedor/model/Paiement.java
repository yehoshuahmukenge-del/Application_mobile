package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "paiements",
        foreignKeys = {
                @ForeignKey(entity = PatientAffilie.class,
                        parentColumns = "id_patient_affilie",
                        childColumns = "id_patient_affilie",
                        onDelete = ForeignKey.CASCADE)
        })
public class Paiement {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_paiement")
    private int id;

    @ColumnInfo(name = "motif")
    private String motif;

    @ColumnInfo(name = "date_paiement")
    private String date;

    @ColumnInfo(name = "montant")
    private double montant;

    @ColumnInfo(name = "id_patient_affilie")
    private int idPatient;

    @ColumnInfo(name = "id_frais")
    private int idFrais;

    public Paiement(String motif, String date, double montant, int idPatient, int idFrais) {
        this.motif = motif;
        this.date = date;
        this.montant = montant;
        this.idPatient = idPatient;
        this.idFrais = idFrais;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }

    public int getIdFrais() { return idFrais; }
    public void setIdFrais(int idFrais) { this.idFrais = idFrais; }
}
