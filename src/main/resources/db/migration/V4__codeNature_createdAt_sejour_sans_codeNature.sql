-- codeNature : date de création
ALTER TABLE "codeNature"
    ADD COLUMN "createdAt" TIMESTAMP NOT NULL DEFAULT now();

-- sejour : suppression du lien vers codeNature
-- (la clé étrangère et l'index idx_sejour_code_nature sont supprimés avec la colonne)
ALTER TABLE sejour DROP COLUMN "codeNatureId";
