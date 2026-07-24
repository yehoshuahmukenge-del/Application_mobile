package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "frais")
public class Frais {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_frais")
    private int id;

    @ColumnInfo(name = "libelle_frais")
    private String libelle;

    @ColumnInfo(name = "montant_frais")
    private double montant;

    @ColumnInfo(name = "motif_frais")
    private String motif;

    @ColumnInfo(name = "cout_remboursement")
    private double coutRemboursement;

    public Frais(String libelle, double montant, String motif, double coutRemboursement) {
        this.libelle = libelle;
        this.montant = montant;
        this.motif = motif;
        this.coutRemboursement = coutRemboursement;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public double getCoutRemboursement() { return coutRemboursement; }
    public void setCoutRemboursement(double coutRemboursement) { this.coutRemboursement = coutRemboursement; }
}
