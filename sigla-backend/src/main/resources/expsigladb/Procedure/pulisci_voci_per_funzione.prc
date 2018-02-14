CREATE OR REPLACE PROCEDURE Pulisci_Voci_Per_Funzione (aesercizio IN NUMBER, aFunzione IN VARCHAR) IS
CONTA_CANC      NUMBER := 0;
CONTA_ERR       NUMBER := 0;
-- S.F.  A CAUSA DEL CAMBIO DI REGOLAMENTO LE FUNZIONI NON ESISTONO PIU'
-- QUINDI VENGONO CANCELLATE LE ASSOCIAZIONI CON I CDS PER EVITARE
-- LA CREAZIONE DI VOCI LUNGHE INESISTENTI 

BEGIN
FOR VOCI_DA_CANC IN (SELECT * FROM ASS_EV_FUNZ_TIPOCDS
                     WHERE ESERCIZIO = aEsercizio AND
                           CD_FUNZIONE = aFunzione) LOOP

BEGIN
DELETE ASS_EV_FUNZ_TIPOCDS
WHERE ESERCIZIO     = VOCI_DA_CANC.ESERCIZIO     AND
      CD_FUNZIONE   = VOCI_DA_CANC.CD_FUNZIONE   AND
      CD_TIPO_UNITA = VOCI_DA_CANC.CD_TIPO_UNITA AND
      CD_CONTO      = VOCI_DA_CANC.CD_CONTO;

CONTA_CANC := NVL(CONTA_CANC, 0) + 1;

EXCEPTION
  WHEN OTHERS THEN
    CONTA_ERR := NVL(CONTA_ERR, 0) + 1;
END;

END LOOP;

DBMS_OUTPUT.PUT_LINE ('ELEMENTI VOCE CANCELLATI PER FUNZIONE '||AfUNZIONE||' N. '||CONTA_CANC);
DBMS_OUTPUT.PUT_LINE ('ERRORI CANCELLAZIONE PER FUNZIONE '||AfUNZIONE||' UTILIZZATA N. '||CONTA_ERR);

END;
/


