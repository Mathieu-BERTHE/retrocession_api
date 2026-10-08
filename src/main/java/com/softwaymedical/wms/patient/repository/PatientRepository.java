package com.softwaymedical.wms.patient.repository;

import com.softwaymedical.wms.patient.model.Patient;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PatientRepository implements PanacheRepository<Patient> {

    private static final Sort TRI_PAR_DEFAUT = Sort.by("nomNaissance").and("prenom");

    public Optional<Patient> findByIpp(String ipp) {
        return find("ipp", ipp).firstResultOptional();
    }

    public Optional<Patient> findByIns(String ins) {
        return find("ins", ins).firstResultOptional();
    }

    /**
     * Recherche globale : le terme est cherché (contient, insensible à la casse)
     * dans l'IPP, l'INS, le nom de naissance et le prénom.
     */
    public List<Patient> searchGlobal(String terme) {
        return list("lower(ipp) like :terme or lower(ins) like :terme"
                        + " or lower(nomNaissance) like :terme or lower(prenom) like :terme",
                TRI_PAR_DEFAUT,
                Parameters.with("terme", contient(terme)));
    }

    /**
     * Recherche avancée : critères combinés en ET logique, un critère vide est ignoré.
     */
    public List<Patient> searchAdvanced(String ipp, String ins, String nom, String prenom, LocalDate dateNaissance) {
        List<String> conditions = new ArrayList<>();
        Parameters parametres = new Parameters();

        if (estRenseigne(ipp)) {
            conditions.add("lower(ipp) like :ipp");
            parametres.and("ipp", contient(ipp));
        }
        if (estRenseigne(ins)) {
            conditions.add("lower(ins) like :ins");
            parametres.and("ins", contient(ins));
        }
        if (estRenseigne(nom)) {
            conditions.add("lower(nomNaissance) like :nom");
            parametres.and("nom", contient(nom));
        }
        if (estRenseigne(prenom)) {
            conditions.add("lower(prenom) like :prenom");
            parametres.and("prenom", contient(prenom));
        }
        if (dateNaissance != null) {
            conditions.add("dateNaissance = :dateNaissance");
            parametres.and("dateNaissance", dateNaissance);
        }

        if (conditions.isEmpty()) {
            return listAll(TRI_PAR_DEFAUT);
        }
        return list(String.join(" and ", conditions), TRI_PAR_DEFAUT, parametres);
    }

    /**
     * Nombre de séjours rattachés au patient (pas encore d'entité Sejour : requête native).
     */
    public long countSejours(Long patientId) {
        Number total = (Number) getEntityManager()
                .createNativeQuery("SELECT count(*) FROM \"sejour\" WHERE \"patientId\" = :patientId")
                .setParameter("patientId", patientId)
                .getSingleResult();
        return total.longValue();
    }

    private static boolean estRenseigne(String valeur) {
        return valeur != null && !valeur.isBlank();
    }

    private static String contient(String valeur) {
        return "%" + valeur.trim().toLowerCase() + "%";
    }
}
