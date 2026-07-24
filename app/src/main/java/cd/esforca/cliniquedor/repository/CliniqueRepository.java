package cd.esforca.cliniquedor.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cd.esforca.cliniquedor.db.AppDatabase;
import cd.esforca.cliniquedor.db.CliniqueDao;
import cd.esforca.cliniquedor.model.*;

public class CliniqueRepository {

    private CliniqueDao cliniqueDao;
    private LiveData<List<PatientAffilie>> allPatients;
    private LiveData<List<Medecin>> allMedecins;
    private LiveData<List<EntreprisePartenaire>> allEntreprises;
    private LiveData<List<Paiement>> allPaiements;
    private LiveData<List<Prescription>> allPrescriptions;
    private LiveData<List<Examen>> allExamens;
    private LiveData<List<ExamenWithDetails>> allExamensWithDetails;
    private LiveData<List<PrescriptionWithDetails>> allPrescriptionsWithDetails;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public CliniqueRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        cliniqueDao = db.cliniqueDao();
        allPatients = cliniqueDao.getAllPatients();
        allMedecins = cliniqueDao.getAllMedecins();
        allEntreprises = cliniqueDao.getAllEntreprises();
        allPaiements = cliniqueDao.getAllPaiements();
        allPrescriptions = cliniqueDao.getAllPrescriptions();
        allExamens = cliniqueDao.getAllExamens();
        allExamensWithDetails = cliniqueDao.getAllExamensWithDetails();
        allPrescriptionsWithDetails = cliniqueDao.getAllPrescriptionsWithDetails();
    }

    // Patients
    public void insertPatient(PatientAffilie patient) {
        executor.execute(() -> cliniqueDao.insertPatient(patient));
    }
    public void deletePatient(PatientAffilie patient) {
        executor.execute(() -> cliniqueDao.deletePatient(patient));
    }
    public LiveData<List<PatientAffilie>> getAllPatients() { return allPatients; }

    // Medecins
    public void insertMedecin(Medecin medecin) {
        executor.execute(() -> cliniqueDao.insertMedecin(medecin));
    }
    public void deleteMedecin(Medecin medecin) {
        executor.execute(() -> cliniqueDao.deleteMedecin(medecin));
    }
    public LiveData<List<Medecin>> getAllMedecins() { return allMedecins; }

    // Entreprises
    public void insertEntreprise(EntreprisePartenaire entreprise) {
        executor.execute(() -> cliniqueDao.insertEntreprise(entreprise));
    }
    public void deleteEntreprise(EntreprisePartenaire entreprise) {
        executor.execute(() -> cliniqueDao.deleteEntreprise(entreprise));
    }
    public LiveData<List<EntreprisePartenaire>> getAllEntreprises() { return allEntreprises; }

    // Paiements
    public void insertPaiement(Paiement paiement) {
        executor.execute(() -> cliniqueDao.insertPaiement(paiement));
    }
    public LiveData<List<Paiement>> getAllPaiements() { return allPaiements; }
    
    public LiveData<Integer> getPatientsCount() {
        return cliniqueDao.getPatientsCount();
    }

    public LiveData<Integer> getMedecinsCount() {
        return cliniqueDao.getMedecinsCount();
    }

    public LiveData<Integer> getPrescriptionsCount() {
        return cliniqueDao.getPrescriptionsCount();
    }

    public LiveData<Integer> getExamensCount() {
        return cliniqueDao.getExamensCount();
    }

    public LiveData<Double> getTotalRecettes() {
        return cliniqueDao.getTotalRecettes();
    }

    // Prescriptions
    public void insertPrescription(Prescription prescription) {
        executor.execute(() -> cliniqueDao.insertPrescription(prescription));
    }
    public void deletePrescription(Prescription prescription) {
        executor.execute(() -> cliniqueDao.deletePrescription(prescription));
    }
    public LiveData<List<Prescription>> getAllPrescriptions() { return allPrescriptions; }
    public LiveData<List<PrescriptionWithDetails>> getAllPrescriptionsWithDetails() { return allPrescriptionsWithDetails; }

    // Examens
    public void insertExamen(Examen examen) {
        executor.execute(() -> cliniqueDao.insertExamen(examen));
    }
    public void deleteExamen(Examen examen) {
        executor.execute(() -> cliniqueDao.deleteExamen(examen));
    }
    public LiveData<List<Examen>> getAllExamens() { return allExamens; }
    public LiveData<List<ExamenWithDetails>> getAllExamensWithDetails() { return allExamensWithDetails; }
}
