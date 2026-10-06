-- code_nature : taux de prise en charge (en %, facultatif)
ALTER TABLE code_nature
    ADD COLUMN taux_prise_en_charge NUMERIC(5, 2)
        CONSTRAINT chk_code_nature_taux CHECK (taux_prise_en_charge BETWEEN 0 AND 100);

-- patient : identité (INS, nom de naissance / d'usage), sexe obligatoire, coordonnées
ALTER TABLE patient RENAME COLUMN nir TO ins;
ALTER TABLE patient ADD CONSTRAINT uk_patient_ins UNIQUE (ins);
ALTER TABLE patient RENAME COLUMN nom TO nom_naissance;
ALTER TABLE patient ALTER COLUMN sexe SET NOT NULL;
ALTER TABLE patient
    ADD COLUMN nom_usage          VARCHAR(100),
    ADD COLUMN adresse            VARCHAR(255),
    ADD COLUMN complement_adresse VARCHAR(255),
    ADD COLUMN code_postal        VARCHAR(10),
    ADD COLUMN ville              VARCHAR(100),
    ADD COLUMN telephone          VARCHAR(20),
    ADD COLUMN email              VARCHAR(255);

-- sejour : numero_sejour devient code_sejour
ALTER TABLE sejour RENAME COLUMN numero_sejour TO code_sejour;
ALTER TABLE sejour RENAME CONSTRAINT sejour_numero_sejour_key TO sejour_code_sejour_key;
