CREATE OR REPLACE FUNCTION SOMMA_DOC_PASS_PER_SCADENZA
   (CD_CDS_OBB     In VARCHAR2,
    ES_OBB         In NUMBER,
    ES_ORI_OBB     In NUMBER,
    PG_OBB         In NUMBER,
    PG_SCAD        In NUMBER
    --CD_CDS_DOC     In VARCHAR2,
    --CD_UO_DOC      In VARCHAR2,
    --ES_DOC         In NUMBER,
    --CD_TIPO_DOC    In VARCHAR2,
    --PG_DOC         In NUMBER
    ) RETURN NUMBER IS

RISULTATO NUMBER;

BEGIN
 SELECT Nvl(Sum(NVL(IM_RIGA, 0)), 0)
 INTO   RISULTATO
 FROM   DOCUMENTO_GENERICO_RIGA
 WHERE  --CD_CDS                       = CD_CDS_DOC  And
        --CD_UNITA_ORGANIZZATIVA       = CD_UO_DOC  AND
        --ESERCIZIO                    = ES_DOC     AND
        --CD_TIPO_DOCUMENTO_AMM        = CD_TIPO_DOC And
        --PG_DOCUMENTO_GENERICO        = PG_DOC And
        CD_CDS_OBBLIGAZIONE          = CD_CDS_OBB  AND
        ESERCIZIO_OBBLIGAZIONE       = ES_OBB      AND
        ESERCIZIO_ORI_OBBLIGAZIONE   = ES_ORI_OBB  AND
        PG_OBBLIGAZIONE              = PG_OBB      AND
        PG_OBBLIGAZIONE_SCADENZARIO  = PG_SCAD And
        STATO_COFI != 'A';

RETURN NVL(RISULTATO, 0);
END;
/


