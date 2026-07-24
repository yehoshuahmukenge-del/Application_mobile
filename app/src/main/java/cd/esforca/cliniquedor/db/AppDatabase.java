package cd.esforca.cliniquedor.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import cd.esforca.cliniquedor.model.Agent;
import cd.esforca.cliniquedor.model.ContenirMedicament;
import cd.esforca.cliniquedor.model.EffectuerExamen;
import cd.esforca.cliniquedor.model.EntreprisePartenaire;
import cd.esforca.cliniquedor.model.Examen;
import cd.esforca.cliniquedor.model.Frais;
import cd.esforca.cliniquedor.model.Medecin;
import cd.esforca.cliniquedor.model.Medicament;
import cd.esforca.cliniquedor.model.Paiement;
import cd.esforca.cliniquedor.model.PatientAffilie;
import cd.esforca.cliniquedor.model.Prescription;
import cd.esforca.cliniquedor.model.Soigner;

@Database(entities = {
        PatientAffilie.class,
        Medecin.class,
        EntreprisePartenaire.class,
        Agent.class,
        Prescription.class,
        Medicament.class,
        Examen.class,
        Frais.class,
        Paiement.class,
        Soigner.class,
        EffectuerExamen.class,
        ContenirMedicament.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract CliniqueDao cliniqueDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "cliniquedor_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
