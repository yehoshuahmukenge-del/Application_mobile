package cd.esforca.cliniquedor.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import cd.esforca.cliniquedor.model.*;
import cd.esforca.cliniquedor.repository.CliniqueRepository;

public class CliniqueViewModel extends AndroidViewModel {

    private CliniqueRepository repository;
    private LiveData<List<PatientAffilie>> allPatients;
    private LiveData<List<Medecin>> allMedecins;
    private LiveData<List<EntreprisePartenaire>> allEntreprises;
    private LiveData<List<Paiement>> allPaiements;
    private LiveData<List<Prescription>> allPrescriptions;
    private LiveData<List<PrescriptionWithDetails>> allPrescriptionsWithDetails;
    private LiveData<List<Examen>> allExamens;
    private LiveData<List<ExamenWithDetails>> allExamensWithDetails;

    public CliniqueViewModel(@NonNull Application application) {
        super(application);
        repository = new CliniqueRepository(application);
        allPatients = repository.getAllPatients();
        allMedecins = repository.getAllMedecins();
        allEntreprises = repository.getAllEntreprises();
        allPaiements = repository.getAllPaiements();
        allPrescriptions = repository.getAllPrescriptions();
        allPrescriptionsWithDetails = repository.getAllPrescriptionsWithDetails();
        allExamens = repository.getAllExamens();
        allExamensWithDetails = repository.getAllExamensWithDetails();
    }

    // Patients
    public void insertPatient(PatientAffilie patient) { repository.insertPatient(patient); }
    public void deletePatient(PatientAffilie patient) { repository.deletePatient(patient); }
    public LiveData<List<PatientAffilie>> getAllPatients() { return allPatients; }

    // Medecins
    public void insertMedecin(Medecin medecin) { repository.insertMedecin(medecin); }
    public void deleteMedecin(Medecin medecin) { repository.deleteMedecin(medecin); }
    public LiveData<List<Medecin>> getAllMedecins() { return allMedecins; }

    // Entreprises
    public void insertEntreprise(EntreprisePartenaire entreprise) { repository.insertEntreprise(entreprise); }
    public void deleteEntreprise(EntreprisePartenaire entreprise) { repository.deleteEntreprise(entreprise); }
    public LiveData<List<EntreprisePartenaire>> getAllEntreprises() { return allEntreprises; }

    // Paiements
    public void insertPaiement(Paiement paiement) { repository.insertPaiement(paiement); }
    public LiveData<List<Paiement>> getAllPaiements() { return allPaiements; }

    // Prescriptions
    public void insertPrescription(Prescription prescription) { repository.insertPrescription(prescription); }
    public void deletePrescription(Prescription prescription) { repository.deletePrescription(prescription); }
    public LiveData<List<Prescription>> getAllPrescriptions() { return allPrescriptions; }
    public LiveData<List<PrescriptionWithDetails>> getAllPrescriptionsWithDetails() { return allPrescriptionsWithDetails; }

    // Examens
    public void insertExamen(Examen examen) { repository.insertExamen(examen); }
    public void deleteExamen(Examen examen) { repository.deleteExamen(examen); }
    public LiveData<List<Examen>> getAllExamens() { return allExamens; }
    public LiveData<List<ExamenWithDetails>> getAllExamensWithDetails() { return allExamensWithDetails; }

    // Statistics
    public LiveData<Integer> getPatientsCount() { return repository.getPatientsCount(); }
    public LiveData<Integer> getMedecinsCount() { return repository.getMedecinsCount(); }
    public LiveData<Integer> getPrescriptionsCount() { return repository.getPrescriptionsCount(); }
    public LiveData<Integer> getExamensCount() { return repository.getExamensCount(); }
    public LiveData<Double> getTotalRecettes() { return repository.getTotalRecettes(); }
}
