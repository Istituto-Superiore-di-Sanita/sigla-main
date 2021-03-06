--------------------------------------------------------
--  DDL for View V_DISTINTA_CASS_IM_MAN_REV
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_DISTINTA_CASS_IM_MAN_REV" ("ESERCIZIO", "CD_CDS", "CD_UNITA_ORGANIZZATIVA", "PG_DISTINTA", "PG_DISTINTA_DEF", "DT_EMISSIONE", "DT_INVIO", "IM_MANDATI", "IM_REVERSALI", "IM_MANDATI_ANNULLATI", "IM_REVERSALI_ANNULLATE", "PG_VER_REC") AS 
  SELECT
--
-- Date: 18/07/2002
-- Version: 1.3
--
-- VIEW DI ESTRAZIONE DELLE DISTINTE CASSIERE E DEGLI IMPORTI TOTALI DEI MANDATI/REVERSALI INSERITI IN DISTINTA.
--
-- History:
--
-- Date: 30/04/2002
-- Version: 1.0
-- CREAZIONE
--
-- Date: 03/05/2002
-- Version: 1.1
--
-- Date: 28/05/2002
-- Version: 1.2
-- AGGIUNTO PG_DISTINTA_DEF
--
-- Date: 18/07/2002
-- Version: 1.3
-- Inclusi mandati/reversali con stato PAGATO
--
-- Body:
--
ESERCIZIO, CD_CDS, CD_UNITA_ORGANIZZATIVA, PG_DISTINTA, PG_DISTINTA_DEF, DT_EMISSIONE, DT_INVIO, SUM(IM_MANDATI), SUM(IM_REVERSALI), SUM(IM_MANDATI_ANNULLATI), SUM(IM_REVERSALI_ANNULLATE), PG_VER_REC
FROM
(
SELECT B.ESERCIZIO, B.CD_CDS, B.CD_UNITA_ORGANIZZATIVA, B.PG_DISTINTA, C.PG_DISTINTA_DEF, C.DT_EMISSIONE, C.DT_INVIO, SUM(A.IM_MANDATO) IM_MANDATI, 0 IM_REVERSALI, 0 IM_MANDATI_ANNULLATI, 0 IM_REVERSALI_ANNULLATE, C.PG_VER_REC
FROM MANDATO A, DISTINTA_CASSIERE_DET B, DISTINTA_CASSIERE C
WHERE
A.ESERCIZIO = B.ESERCIZIO AND
A.CD_CDS = B.CD_CDS_ORIGINE AND
A.PG_MANDATO = B.PG_MANDATO AND
A.STATO <> 'A' AND
B.ESERCIZIO = C.ESERCIZIO AND
B.CD_CDS = C.CD_CDS AND
B.CD_UNITA_ORGANIZZATIVA = C.CD_UNITA_ORGANIZZATIVA AND
B.PG_DISTINTA = C.PG_DISTINTA
GROUP BY B.ESERCIZIO, B.CD_CDS, B.CD_UNITA_ORGANIZZATIVA, B.PG_DISTINTA, C.PG_DISTINTA_DEF, C.DT_EMISSIONE, C.DT_INVIO, C.PG_VER_REC
UNION
SELECT B.ESERCIZIO, B.CD_CDS, B.CD_UNITA_ORGANIZZATIVA, B.PG_DISTINTA, C.PG_DISTINTA_DEF, C.DT_EMISSIONE, C.DT_INVIO, 0 IM_MANDATI , SUM(IM_REVERSALE) IM_REVERSALI, 0 IM_MANDATI_ANNULLATI, 0 IM_REVERSALI_ANNULLATE, C.PG_VER_REC
FROM REVERSALE A, DISTINTA_CASSIERE_DET B, DISTINTA_CASSIERE C
WHERE
A.ESERCIZIO = B.ESERCIZIO AND
A.CD_CDS = B.CD_CDS_ORIGINE AND
A.PG_REVERSALE = B.PG_REVERSALE AND
A.STATO <> 'A' AND
B.ESERCIZIO = C.ESERCIZIO AND
B.CD_CDS = C.CD_CDS AND
B.CD_UNITA_ORGANIZZATIVA = C.CD_UNITA_ORGANIZZATIVA AND
B.PG_DISTINTA = C.PG_DISTINTA
GROUP BY B.ESERCIZIO, B.CD_CDS, B.CD_UNITA_ORGANIZZATIVA, B.PG_DISTINTA, C.PG_DISTINTA_DEF, C.DT_EMISSIONE, C.DT_INVIO, C.PG_VER_REC
UNION
SELECT B.ESERCIZIO, B.CD_CDS, B.CD_UNITA_ORGANIZZATIVA, B.PG_DISTINTA, C.PG_DISTINTA_DEF, C.DT_EMISSIONE, C.DT_INVIO, 0 IM_MANDATI, 0 IM_REVERSALI, SUM(IM_MANDATO) IM_MANDATI_ANNULLATI, 0 IM_REVERSALI_ANUULATE, C.PG_VER_REC
FROM MANDATO A, DISTINTA_CASSIERE_DET B, DISTINTA_CASSIERE C
WHERE
A.ESERCIZIO = B.ESERCIZIO AND
A.CD_CDS = B.CD_CDS_ORIGINE AND
A.PG_MANDATO = B.PG_MANDATO AND
A.STATO = 'A' AND
B.ESERCIZIO = C.ESERCIZIO AND
B.CD_CDS = C.CD_CDS AND
B.CD_UNITA_ORGANIZZATIVA = C.CD_UNITA_ORGANIZZATIVA AND
B.PG_DISTINTA = C.PG_DISTINTA
GROUP BY B.ESERCIZIO, B.CD_CDS, B.CD_UNITA_ORGANIZZATIVA, B.PG_DISTINTA, C.PG_DISTINTA_DEF, C.DT_EMISSIONE, C.DT_INVIO, C.PG_VER_REC
UNION
SELECT B.ESERCIZIO, B.CD_CDS, B.CD_UNITA_ORGANIZZATIVA, B.PG_DISTINTA, C.PG_DISTINTA_DEF, C.DT_EMISSIONE, C.DT_INVIO, 0 IM_MANDATI , 0 IM_REVERSALI, 0 IM_MANDATI_ANNULLATI, SUM(IM_REVERSALE) IM_REVERSALI_ANNULLATE, C.PG_VER_REC
FROM REVERSALE A, DISTINTA_CASSIERE_DET B, DISTINTA_CASSIERE C
WHERE
A.ESERCIZIO = B.ESERCIZIO AND
A.CD_CDS = B.CD_CDS_ORIGINE AND
A.PG_REVERSALE = B.PG_REVERSALE AND
A.STATO = 'A' AND
B.ESERCIZIO = C.ESERCIZIO AND
B.CD_CDS = C.CD_CDS AND
B.CD_UNITA_ORGANIZZATIVA = C.CD_UNITA_ORGANIZZATIVA AND
B.PG_DISTINTA = C.PG_DISTINTA
GROUP BY B.ESERCIZIO, B.CD_CDS, B.CD_UNITA_ORGANIZZATIVA, B.PG_DISTINTA, C.PG_DISTINTA_DEF, C.DT_EMISSIONE, C.DT_INVIO, C.PG_VER_REC)
GROUP BY ESERCIZIO, CD_CDS, CD_UNITA_ORGANIZZATIVA, PG_DISTINTA, PG_DISTINTA_DEF, DT_EMISSIONE, DT_INVIO, PG_VER_REC;

   COMMENT ON TABLE "V_DISTINTA_CASS_IM_MAN_REV"  IS 'View di estrazione delle distinte cassiere e degli importi totali dei Mandati/Reversali inseriti in distinta.';
