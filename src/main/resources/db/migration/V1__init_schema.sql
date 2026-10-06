-- Nomenclature des natures (de séjour)
CREATE TABLE code_nature (
    id          BIGSERIAL    PRIMARY KEY,
    code        VARCHAR(10)  NOT NULL UNIQUE,
    libelle     VARCHAR(255) NOT NULL,
    actif       BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE patient (
    id              BIGSERIAL    PRIMARY KEY,
    ipp             VARCHAR(20)  NOT NULL UNIQUE,
    nom             VARCHAR(100) NOT NULL,
    prenom          VARCHAR(100) NOT NULL,
    date_naissance  DATE         NOT NULL,
    sexe            CHAR(1)      CHECK (sexe IN ('M', 'F', 'I')),
    nir             VARCHAR(15),
    created_at      TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE sejour (
    id              BIGSERIAL    PRIMARY KEY,
    numero_sejour   VARCHAR(20)  NOT NULL UNIQUE,
    patient_id      BIGINT       NOT NULL REFERENCES patient (id),
    code_nature_id  BIGINT       NOT NULL REFERENCES code_nature (id),
    date_entree     TIMESTAMP    NOT NULL,
    date_sortie     TIMESTAMP,
    created_at      TIMESTAMP    NOT NULL DEFAULT now(),
    CONSTRAINT chk_sejour_dates CHECK (date_sortie IS NULL OR date_sortie >= date_entree)
);

CREATE INDEX idx_sejour_patient     ON sejour (patient_id);
CREATE INDEX idx_sejour_code_nature ON sejour (code_nature_id);
