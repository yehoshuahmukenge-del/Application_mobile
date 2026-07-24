package cd.esforca.cliniquedor.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "prescriptions")
public class Prescription {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_prescription")
    private int id;

    @ColumnInfo(name = "date_prescription")
    private String date;

    @ColumnInfo(name = "observation_medecin")
    private String observation;

    @ColumnInfo(name = "id_patient")
    private Integer patientId;

    @ColumnInfo(name = "id_medecin")
    private Integer medecinId;

    public Prescription(String date, String observation) {
        this.date = date;
        this.observation = observation;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }

    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }

    public Integer getMedecinId() { return medecinId; }
    public void setMedecinId(Integer medecinId) { this.medecinId = medecinId; }
}
