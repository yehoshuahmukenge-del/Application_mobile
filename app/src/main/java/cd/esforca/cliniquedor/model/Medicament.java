package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicaments")
public class Medicament {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_medicament")
    private int id;

    @ColumnInfo(name = "libelle")
    private String libelle;

    @ColumnInfo(name = "type_medicament")
    private String type;

    @ColumnInfo(name = "medicament_courant")
    private boolean isCourant;

    @ColumnInfo(name = "antecedent_medicament")
    private String antecedent;

    public Medicament(String libelle, String type, boolean isCourant, String antecedent) {
        this.libelle = libelle;
        this.type = type;
        this.isCourant = isCourant;
        this.antecedent = antecedent;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isCourant() { return isCourant; }
    public void setCourant(boolean courant) { isCourant = courant; }

    public String getAntecedent() { return antecedent; }
    public void setAntecedent(String antecedent) { this.antecedent = antecedent; }
}
