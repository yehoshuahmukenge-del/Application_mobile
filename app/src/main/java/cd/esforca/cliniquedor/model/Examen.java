package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "examens")
public class Examen {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_examen")
    private int id;

    @ColumnInfo(name = "code_examen")
    private String code;

    @ColumnInfo(name = "libelle_examen")
    private String libelle;

    @ColumnInfo(name = "mesure_examen")
    private String mesure;

    @ColumnInfo(name = "categorie")
    private String categorie;

    @ColumnInfo(name = "id_patient")
    private Integer patientId;

    @ColumnInfo(name = "id_medecin")
    private Integer medecinId;

    @ColumnInfo(name = "date_examen")
    private String date;

    public Examen(String code, String libelle, String mesure, String categorie) {
        this.code = code;
        this.libelle = libelle;
        this.mesure = mesure;
        this.categorie = categorie;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getMesure() { return mesure; }
    public void setMesure(String mesure) { this.mesure = mesure; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }

    public Integer getMedecinId() { return medecinId; }
    public void setMedecinId(Integer medecinId) { this.medecinId = medecinId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
