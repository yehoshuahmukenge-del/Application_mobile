package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "entreprises_partenaires")
public class EntreprisePartenaire {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_entreprise")
    private int id;

    @ColumnInfo(name = "libelle")
    private String libelle;

    @ColumnInfo(name = "adresse")
    private String adresse;

    @ColumnInfo(name = "service")
    private String service;

    @ColumnInfo(name = "departement")
    private String departement;

    public EntreprisePartenaire(String libelle, String adresse, String service, String departement) {
        this.libelle = libelle;
        this.adresse = adresse;
        this.service = service;
        this.departement = departement;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getDepartement() { return departement; }
    public void setDepartement(String departement) { this.departement = departement; }
}
