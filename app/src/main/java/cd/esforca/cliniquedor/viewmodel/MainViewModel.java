package cd.esforca.cliniquedor.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import cd.esforca.cliniquedor.model.PatientAffilie;
import cd.esforca.cliniquedor.repository.CliniqueRepository;

public class MainViewModel extends AndroidViewModel {

    private final CliniqueRepository repository;
    private final LiveData<Integer> patientsCount;
    private final LiveData<Double> totalRevenue;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new CliniqueRepository(application);
        patientsCount = repository.getPatientsCount();
        totalRevenue = repository.getTotalRecettes();
    }

    public LiveData<Integer> getPatientsCount() {
        return patientsCount;
    }

    public LiveData<Double> getTotalRevenue() {
        return totalRevenue;
    }

    // Méthode pour ajouter un patient (MCT : Admission)
    public void insertPatient(PatientAffilie patient) {
        repository.insertPatient(patient);
    }
}
