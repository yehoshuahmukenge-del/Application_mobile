package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medecins")
public class Medecin {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_medecin")
    private int id;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "adresse")
    private String adresse;

    @ColumnInfo(name = "telephone")
    private String telephone;

    @ColumnInfo(name = "numero_rpps")
    private String numeroRpps;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "specialite")
    private String specialite;

    // Constructeur obligatoire pour Room
    public Medecin() {}

    // Constructeur pratique pour l'ajout
    public Medecin(String nom, String specialite, String numeroRpps, String telephone) {
        this.nom = nom;
        this.specialite = specialite;
        this.numeroRpps = numeroRpps;
        this.telephone = telephone;
    }

    // Getters et Setters (Vérifiés un par un)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getNumeroRpps() { return numeroRpps; }
    public void setNumeroRpps(String numeroRpps) { this.numeroRpps = numeroRpps; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
}
