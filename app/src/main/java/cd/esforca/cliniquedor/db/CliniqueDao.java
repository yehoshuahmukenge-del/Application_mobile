package cd.esforca.cliniquedor.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import cd.esforca.cliniquedor.model.Agent;
import cd.esforca.cliniquedor.model.EntreprisePartenaire;
import cd.esforca.cliniquedor.model.Examen;
import cd.esforca.cliniquedor.model.ExamenWithDetails;
import cd.esforca.cliniquedor.model.Frais;
import cd.esforca.cliniquedor.model.Medicament;
import cd.esforca.cliniquedor.model.Medecin;
import cd.esforca.cliniquedor.model.Paiement;
import cd.esforca.cliniquedor.model.PatientAffilie;
import cd.esforca.cliniquedor.model.Prescription;
import cd.esforca.cliniquedor.model.PrescriptionWithDetails;

@Dao
public interface CliniqueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPatient(PatientAffilie patient);

    @Query("SELECT * FROM patients_affilies ORDER BY nom ASC")
    LiveData<List<PatientAffilie>> getAllPatients();

    @Delete
    void deletePatient(PatientAffilie patient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMedecin(Medecin medecin);

    @Delete
    void deleteMedecin(Medecin medecin);

    @Query("SELECT * FROM medecins")
    LiveData<List<Medecin>> getAllMedecins();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntreprise(EntreprisePartenaire entreprise);

    @Query("SELECT * FROM entreprises_partenaires")
    LiveData<List<EntreprisePartenaire>> getAllEntreprises();

    @Delete
    void deleteEntreprise(EntreprisePartenaire entreprise);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPaiement(Paiement paiement);

    @Query("SELECT * FROM paiements ORDER BY date_paiement DESC")
    LiveData<List<Paiement>> getAllPaiements();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFrais(Frais frais);

    @Query("SELECT * FROM frais")
    LiveData<List<Frais>> getAllFrais();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPrescription(Prescription prescription);

    @Query("SELECT * FROM prescriptions")
    LiveData<List<Prescription>> getAllPrescriptions();

    @androidx.room.Transaction
    @Query("SELECT * FROM prescriptions ORDER BY date_prescription DESC")
    LiveData<List<PrescriptionWithDetails>> getAllPrescriptionsWithDetails();

    @Delete
    void deletePrescription(Prescription prescription);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMedicament(Medicament medicament);

    @Query("SELECT * FROM medicaments")
    LiveData<List<Medicament>> getAllMedicaments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExamen(Examen examen);

    @Query("SELECT * FROM examens")
    LiveData<List<Examen>> getAllExamens();

    @androidx.room.Transaction
    @Query("SELECT * FROM examens ORDER BY date_examen DESC")
    LiveData<List<ExamenWithDetails>> getAllExamensWithDetails();

    @Delete
    void deleteExamen(Examen examen);

    @Query("SELECT COUNT(*) FROM patients_affilies")
    LiveData<Integer> getPatientsCount();

    @Query("SELECT COUNT(*) FROM medecins")
    LiveData<Integer> getMedecinsCount();

    @Query("SELECT COUNT(*) FROM prescriptions")
    LiveData<Integer> getPrescriptionsCount();

    @Query("SELECT COUNT(*) FROM examens")
    LiveData<Integer> getExamensCount();

    @Query("SELECT SUM(montant) FROM paiements")
    LiveData<Double> getTotalRecettes();
}
