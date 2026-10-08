-- Passage des identifiants snake_case en camelCase.
-- PostgreSQL convertit en minuscules les identifiants non quotés :
-- les noms camelCase doivent donc être entre guillemets ici et dans toutes les requêtes.

-- code_nature
ALTER TABLE code_nature RENAME COLUMN taux_prise_en_charge TO "tauxPriseEnCharge";
ALTER TABLE code_nature RENAME TO "codeNature";

-- patient
ALTER TABLE patient RENAME COLUMN nom_naissance      TO "nomNaissance";
ALTER TABLE patient RENAME COLUMN nom_usage          TO "nomUsage";
ALTER TABLE patient RENAME COLUMN date_naissance     TO "dateNaissance";
ALTER TABLE patient RENAME COLUMN complement_adresse TO "complementAdresse";
ALTER TABLE patient RENAME COLUMN code_postal        TO "codePostal";
ALTER TABLE patient RENAME COLUMN created_at         TO "createdAt";

-- sejour
ALTER TABLE sejour RENAME COLUMN code_sejour    TO "codeSejour";
ALTER TABLE sejour RENAME COLUMN patient_id     TO "patientId";
ALTER TABLE sejour RENAME COLUMN code_nature_id TO "codeNatureId";
ALTER TABLE sejour RENAME COLUMN date_entree    TO "dateEntree";
ALTER TABLE sejour RENAME COLUMN date_sortie    TO "dateSortie";
ALTER TABLE sejour RENAME COLUMN created_at     TO "createdAt";
