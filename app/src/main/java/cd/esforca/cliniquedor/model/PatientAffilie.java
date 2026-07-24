package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "patients_affilies",
        foreignKeys = @ForeignKey(entity = EntreprisePartenaire.class,
                parentColumns = "id_entreprise",
                childColumns = "id_entreprise",
                onDelete = ForeignKey.CASCADE))
public class PatientAffilie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_patient_affilie")
    private int id;

    @ColumnInfo(name = "matricule")
    private String matricule;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "postnom")
    private String postnom;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "sexe")
    private String sexe; // 'M' ou 'F'

    @ColumnInfo(name = "adresse")
    private String adresse;

    @ColumnInfo(name = "telephone")
    private String telephone;

    @ColumnInfo(name = "date_naissance")
    private String dateNaissance;

    @ColumnInfo(name = "id_entreprise")
    private int idEntreprise;

    // Constructeurs
    public PatientAffilie() {}

    @Ignore
    public PatientAffilie(String matricule, String nom, String postnom, String prenom, String sexe, String adresse, String telephone, String dateNaissance, int idEntreprise) {
        this.matricule = matricule;
        this.nom = nom;
        this.postnom = postnom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.adresse = adresse;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.idEntreprise = idEntreprise;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPostnom() { return postnom; }
    public void setPostnom(String postnom) { this.postnom = postnom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }

    public int getIdEntreprise() { return idEntreprise; }
    public void setIdEntreprise(int idEntreprise) { this.idEntreprise = idEntreprise; }
}
