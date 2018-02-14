CREATE OR REPLACE Package CONTROLLO_IMPORTI As

/*
--IM_STANZ_INIZIALE_A1
VARIAZIONI_PIU                  -- NON CI SONO ANCORA (variazioni al gestionale)
VARIAZIONI_MENO                 -- NON CI SONO ANCORA (variazioni al gestionale)
IM_STANZ_INIZIALE_CASSA         -- INUTILE
VARIAZIONI_PIU_CASSA            -- INUTILE
VARIAZIONI_MENO_CASSA           -- INUTILE
--IM_OBBL_ACC_COMP
--IM_STANZ_RES_IMPROPRIO
--VAR_PIU_STANZ_RES_IMP
--VAR_MENO_STANZ_RES_IMP
--IM_OBBL_RES_IMP
--IM_OBBL_RES_PRO
VAR_PIU_OBBL_RES_PRO            -- NON CI SONO ANCORA (variazioni ai residui propri)
VAR_MENO_OBBL_RES_PRO           -- NON CI SONO ANCORA (variazioni ai residui propri)
--IM_MANDATI_REVERSALI_PRO
--IM_MANDATI_REVERSALI_IMP
IM_PAGAMENTI_INCASSI            -- DA FARE
*/

-- ATTENZIONE !!!!! ESTRAE IL VALORE TRANNE CHE PER 999.000.000 (non ? facilmente recuperabile) !!!!
Function IM_STANZ_INIZIALE_A1 (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type) Return NUMBER;

Function IM_STANZ_RES_IMPROPRIO (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type) Return NUMBER;

Function VAR_PIU_STANZ_RES_IMP  (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type) Return NUMBER;

Function VAR_MENO_STANZ_RES_IMP  (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type) Return NUMBER;

Function IM_OBBL_ACC_COMP (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type) Return NUMBER;

Function IM_OBBL_RES_IMP (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type) Return NUMBER;

Function IM_OBBL_RES_PRO (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type) Return NUMBER;

Function IM_MANDATI_REVERSALI_PRO (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type) Return NUMBER;

Function IM_MANDATI_REVERSALI_IMP (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type) Return NUMBER;

End;


CREATE OR REPLACE package body CONTROLLO_IMPORTI is

Function IM_STANZ_RES_IMPROPRIO (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type)
RETURN NUMBER Is
VALORE NUMBER;
BEGIN

If INGEST = 'S' And INCDR != '999.000.000' And INES > INESRES And INES = 2006 And INESRES = 2005 /* per forza, per adeso */ Then

Select Sum(TOT_IM_RESIDUI_RICOSTRUITI) + Sum(tot_IM_STANZ_INIZIALE_A1) + Sum(tot_VARIAZIONI_PIU) - Sum(tot_VARIAZIONI_MENO) -
       Sum(tot_IM_OBBL_ACC_COMP)
Into   VALORE
From   v_disp_res_improprie
Where  ESERCIZIO                = INESRES    AND
       ESERCIZIO_RES            = INESRES    AND
       CD_CENTRO_RESPONSABILITA = INCDR      AND
       CD_LINEA_ATTIVITA        = INLA       AND
       TI_APPARTENENZA          = INAPP      AND
       TI_GESTIONE              = INGEST     AND
       CD_VOCE                  = INVOCE;

End If;

Return  Nvl(VALORE, 0);

END;

Function VAR_PIU_STANZ_RES_IMP  (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type)
RETURN NUMBER Is
VALORE NUMBER;

BEGIN

  Select NVL(SUM(Abs(IM_VARIAZIONE)), 0)
  Into   VALORE
  From   VAR_STANZ_RES_RIGA VR, VAR_STANZ_RES VT
  Where  VR.ESERCIZIO = INES And
         VR.ESERCIZIO_RES = INESRES And
         VR.ESERCIZIO_VOCE = INES And
         VR.CD_CDR = INCDR AND
         VR.CD_LINEA_ATTIVITA = INLA  AND
         VR.TI_APPARTENENZA = INAPP AND
         VR.TI_GESTIONE = INGEST AND
         VR.CD_VOCE = INVOCE And
         VR.IM_VARIAZIONE > 0 And
         VT.ESERCIZIO     = VR.ESERCIZIO And
         VT.PG_VARIAZIONE = VR.PG_VARIAZIONE And
         VT.STATO = 'APP';

Return  Nvl(VALORE, 0);

END;

Function VAR_MENO_STANZ_RES_IMP  (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type)
RETURN NUMBER Is
VALORE NUMBER;

BEGIN

  Select NVL(SUM(Abs(IM_VARIAZIONE)), 0)
  Into   VALORE
  From   VAR_STANZ_RES_RIGA VR, VAR_STANZ_RES VT
  Where  VR.ESERCIZIO = INES And
         VR.ESERCIZIO_RES = INESRES And
         VR.ESERCIZIO_VOCE = INES And
         VR.CD_CDR = INCDR AND
         VR.CD_LINEA_ATTIVITA = INLA  AND
         VR.TI_APPARTENENZA = INAPP AND
         VR.TI_GESTIONE = INGEST AND
         VR.CD_VOCE = INVOCE And
         VR.IM_VARIAZIONE < 0 And
         VT.ESERCIZIO     = VR.ESERCIZIO And
         VT.PG_VARIAZIONE = VR.PG_VARIAZIONE And
         VT.STATO = 'APP';

Return  Nvl(VALORE, 0);

END;


Function IM_STANZ_INIZIALE_A1 (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type)
RETURN NUMBER Is
VALORE NUMBER;
TIPO_UO VARCHAR2(10);

BEGIN

Select CD_TIPO_UNITA
Into   TIPO_UO
From   UNITA_ORGANIZZATIVA
Where  CD_UNITA_ORGANIZZATIVA = Substr(INCDR, 1, 7);

If INGEST = 'E' Then

  If TIPO_UO != 'AREA' Then

   Select Sum(IM_ENTRATA)
   Into   VALORE
   From   PDG_MODULO_ENTRATE_GEST PDG, UNITA_ORGANIZZATIVA AREA
   Where  PDG.ESERCIZIO           = INES   AND
          PDG.CD_CDR_ASSEGNATARIO = INCDR  AND
          PDG.CD_LINEA_ATTIVITA   = INLA   AND
          PDG.TI_APPARTENENZA     = INAPP  AND
          PDG.TI_GESTIONE         = INGEST AND
          PDG.CD_ELEMENTO_VOCE    = INELVOCE AND
          PDG.CD_CDS_AREA = AREA.CD_UNITA_ORGANIZZATIVA AND
          AREA.CD_TIPO_UNITA != 'AREA' AND
          PDG.FL_SOLA_LETTURA = 'N' And
          (PDG.ESERCIZIO, PDG.CD_CENTRO_RESPONSABILITA) IN
	  (SELECT ESERCIZIO, CD_CENTRO_RESPONSABILITA From PDG_ESERCIZIO Where STATO = 'CG');

  Elsif TIPO_UO = 'AREA' Then

   Select Sum(IM_ENTRATA)
   Into   VALORE
   From   PDG_MODULO_ENTRATE_GEST PDG
   Where  PDG.ESERCIZIO           = INES   AND
          PDG.CD_CDR_ASSEGNATARIO = INCDR  AND
          PDG.CD_LINEA_ATTIVITA   = INLA   AND
          PDG.TI_APPARTENENZA     = INAPP  AND
          PDG.TI_GESTIONE         = INGEST AND
          PDG.CD_ELEMENTO_VOCE    = INELVOCE AND
          (PDG.ESERCIZIO, PDG.CD_CENTRO_RESPONSABILITA) IN
	  (SELECT ESERCIZIO, CD_CENTRO_RESPONSABILITA From PDG_ESERCIZIO Where STATO = 'CG');

  End If;

Elsif INGEST = 'S' Then

  If TIPO_UO != 'AREA' Then

-- SPESE DECENTRATE

         Select Nvl(Sum(PDG_SPE.IM_SPESE_GEST_DECENTRATA_INT) + Sum(PDG_SPE.IM_SPESE_GEST_DECENTRATA_EST), 0)
         Into   VALORE
         From   PDG_MODULO_SPESE_GEST PDG_SPE,
                UNITA_ORGANIZZATIVA AREA
         Where  PDG_SPE.ESERCIZIO           = INES    AND
                PDG_SPE.CD_CDR_ASSEGNATARIO = INCDR   AND
                PDG_SPE.CD_LINEA_ATTIVITA   = INLA    AND
                PDG_SPE.TI_APPARTENENZA     = INAPP   AND
                PDG_SPE.TI_GESTIONE         = INGEST  AND
                PDG_SPE.CD_ELEMENTO_VOCE    = INELVOCE  AND
                PDG_SPE.CD_CDS_AREA         = AREA.CD_UNITA_ORGANIZZATIVA AND
                AREA.CD_TIPO_UNITA         != 'AREA' AND
                (PDG_SPE.ESERCIZIO, PDG_SPE.CD_CENTRO_RESPONSABILITA) IN
      	  (SELECT ESERCIZIO, CD_CENTRO_RESPONSABILITA From PDG_ESERCIZIO Where STATO = 'CG');

         Select Nvl(VALORE, 0) + Nvl(Sum(PDG_SPE.IM_SPESE_GEST_ACCENTRATA_INT) + Sum(PDG_SPE.IM_SPESE_GEST_ACCENTRATA_EST), 0)
         Into   VALORE
         From   PDG_MODULO_SPESE_GEST PDG_SPE, V_CLASSIFICAZIONE_VOCI CLA,
                UNITA_ORGANIZZATIVA AREA
         Where  PDG_SPE.ESERCIZIO           = INES    AND
                PDG_SPE.CD_CDR_ASSEGNATARIO = INCDR   AND
                PDG_SPE.CD_LINEA_ATTIVITA   = INLA    AND
                PDG_SPE.TI_APPARTENENZA     = INAPP   AND
                PDG_SPE.TI_GESTIONE         = INGEST  AND
                PDG_SPE.CD_ELEMENTO_VOCE    = INELVOCE  AND
                PDG_SPE.ID_CLASSIFICAZIONE  = CLA.ID_CLASSIFICAZIONE And
                NVL(CLA.CDR_ACCENTRATORE, 'xxx') = PDG_SPE.CD_CDR_ASSEGNATARIO And
                PDG_SPE.CD_CDS_AREA         = AREA.CD_UNITA_ORGANIZZATIVA AND
                AREA.CD_TIPO_UNITA         != 'AREA' AND
                (PDG_SPE.ESERCIZIO, PDG_SPE.CD_CENTRO_RESPONSABILITA) IN
      	  (SELECT ESERCIZIO, CD_CENTRO_RESPONSABILITA From PDG_ESERCIZIO Where STATO = 'CG');

  Elsif TIPO_UO = 'AREA' Then

   Select Nvl(Sum(PDG_SPE.IM_SPESE_GEST_DECENTRATA_INT) + Sum(PDG_SPE.IM_SPESE_GEST_DECENTRATA_EST) +
              Sum(PDG_SPE.IM_SPESE_GEST_ACCENTRATA_EST) + Sum(PDG_SPE.IM_SPESE_GEST_ACCENTRATA_EST), 0)
   Into   VALORE
   From   PDG_MODULO_SPESE_GEST PDG_SPE
   Where  PDG_SPE.ESERCIZIO           = INES    AND
          PDG_SPE.CD_CDR_ASSEGNATARIO = INCDR   AND
          PDG_SPE.CD_LINEA_ATTIVITA   = INLA    AND
          PDG_SPE.TI_APPARTENENZA     = INAPP   AND
          PDG_SPE.TI_GESTIONE         = INGEST  AND
          PDG_SPE.CD_ELEMENTO_VOCE    = INELVOCE  AND
          (PDG_SPE.ESERCIZIO, PDG_SPE.CD_CENTRO_RESPONSABILITA) IN
	  (SELECT ESERCIZIO, CD_CENTRO_RESPONSABILITA From PDG_ESERCIZIO Where STATO = 'CG');

   End If; -- TIPO UO

End If;

Return  Nvl(VALORE, 0);

END;


FUNCTION IM_OBBL_ACC_COMP (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                           INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                           INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                           INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                           INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                           INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                           INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                           INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type)
RETURN NUMBER Is
VALORE NUMBER;
PGIRO           ELEMENTO_VOCE.FL_PARTITA_GIRO%Type;
TIPOLOGIA1       VARCHAR2(200);
TIPOLOGIA2       VARCHAR2(200);
TIPOLOGIA3       VARCHAR2(200);

BEGIN

Dbms_Output.PUT_LINE ('A');

Select FL_PARTITA_GIRO
Into   PGIRO
From   ELEMENTO_VOCE
Where  ESERCIZIO        = INES And
       TI_APPARTENENZA  = INAPP And
       TI_GESTIONE      = INGEST And
       CD_ELEMENTO_VOCE = INELVOCE;

If INGEST = 'S' And INES = INESRES Then

   If PGIRO = 'Y' Then
      TIPOLOGIA1 := 'OBB';
      TIPOLOGIA2 := 'OBB_PGIRO';
      TIPOLOGIA3 := 'IMP';
   Else
      If INCDR = '999.000.000' Then
          TIPOLOGIA1 := 'IMP';
      Else
          TIPOLOGIA1 := 'OBB';
      End If;
   End If;

   Select Sum(IM_VOCE)
   Into   VALORE
   From   OBBLIGAZIONE_SCAD_VOCE OSV, OBBLIGAZIONE O, OBBLIGAZIONE_SCADENZARIO OS
   Where  OSV.ESERCIZIO = INES And
          OSV.TI_APPARTENENZA = INAPP And
          OSV.TI_GESTIONE = INGEST And
          OSV.CD_VOCE = INVOCE And
          OSV.CD_CENTRO_RESPONSABILITA = INCDR And
          OSV.CD_LINEA_ATTIVITA = INLA And
          OSV.CD_CDS = OS.CD_CDS And
          OSV.ESERCIZIO = OS.ESERCIZIO And
          OSV.PG_OBBLIGAZIONE = OS.PG_OBBLIGAZIONE And
          OSV.PG_OBBLIGAZIONE_SCADENZARIO = OS.PG_OBBLIGAZIONE_SCADENZARIO And
          OSV.CD_CDS = O.CD_CDS And
          OSV.ESERCIZIO = O.ESERCIZIO And
          OSV.PG_OBBLIGAZIONE = O.PG_OBBLIGAZIONE And
          O.ESERCIZIO_ORIGINALE = INES And
          (O.CD_TIPO_DOCUMENTO_CONT = TIPOLOGIA1 Or
           O.CD_TIPO_DOCUMENTO_CONT = Nvl(TIPOLOGIA2, TIPOLOGIA1) Or
           O.CD_TIPO_DOCUMENTO_CONT = Nvl(TIPOLOGIA3, TIPOLOGIA1));

Elsif INGEST = 'E' And INES = INESRES Then

   Select Sum(IM_VOCE)
   Into   VALORE
   From   ACCERTAMENTO_SCAD_VOCE OSV, ACCERTAMENTO O, ACCERTAMENTO_SCADENZARIO OS
   Where  O.ESERCIZIO = INES And
          O.TI_APPARTENENZA = INAPP And
          O.TI_GESTIONE = INGEST And
          O.CD_VOCE = INVOCE And
          OSV.CD_CENTRO_RESPONSABILITA = INCDR And
          OSV.CD_LINEA_ATTIVITA = INLA And
          OSV.CD_CDS = OS.CD_CDS And
          OSV.ESERCIZIO = OS.ESERCIZIO And
          OSV.PG_ACCERTAMENTO = OS.PG_ACCERTAMENTO And
          OSV.PG_ACCERTAMENTO_SCADENZARIO = OS.PG_ACCERTAMENTO_SCADENZARIO And
          OSV.CD_CDS = O.CD_CDS And
          OSV.ESERCIZIO = O.ESERCIZIO And
          OSV.PG_ACCERTAMENTO = O.PG_ACCERTAMENTO And
          O.ESERCIZIO_ORIGINALE = INES And
          O.CD_TIPO_DOCUMENTO_CONT In ('ACR', 'ACR_SIST');
End If;

Return  Nvl(VALORE, 0);

END;


FUNCTION IM_OBBL_RES_IMP (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type)
RETURN NUMBER Is
VALORE NUMBER;
BEGIN

Select Sum(IM_VOCE)
Into   VALORE
From   OBBLIGAZIONE_SCAD_VOCE OSV, OBBLIGAZIONE O, OBBLIGAZIONE_SCADENZARIO OS
Where  OSV.ESERCIZIO = INES And
       OSV.TI_APPARTENENZA = INAPP And
       OSV.TI_GESTIONE = INGEST And
       OSV.CD_VOCE = INVOCE And
       OSV.CD_CENTRO_RESPONSABILITA = INCDR And
       OSV.CD_LINEA_ATTIVITA = INLA And
       OSV.CD_CDS = OS.CD_CDS And
       OSV.ESERCIZIO = OS.ESERCIZIO And
       OSV.PG_OBBLIGAZIONE = OS.PG_OBBLIGAZIONE And
       OSV.PG_OBBLIGAZIONE_SCADENZARIO = OS.PG_OBBLIGAZIONE_SCADENZARIO And
       OSV.CD_CDS = O.CD_CDS And
       OSV.ESERCIZIO = O.ESERCIZIO And
       OSV.PG_OBBLIGAZIONE = O.PG_OBBLIGAZIONE And
       O.ESERCIZIO_ORIGINALE = INESRES And
       O.CD_TIPO_DOCUMENTO_CONT = 'OBB_RESIM';

Return  Nvl(VALORE, 0);
End;

FUNCTION IM_OBBL_RES_PRO (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type)
RETURN NUMBER Is
VALORE NUMBER;
BEGIN

If INGEST = 'S' Then

Select Sum(IM_VOCE)
Into   VALORE
From   OBBLIGAZIONE_SCAD_VOCE OSV, OBBLIGAZIONE O, OBBLIGAZIONE_SCADENZARIO OS
Where  OSV.ESERCIZIO = INES And
       OSV.TI_APPARTENENZA = INAPP And
       OSV.TI_GESTIONE = INGEST And
       OSV.CD_VOCE = INVOCE And
       OSV.CD_CENTRO_RESPONSABILITA = INCDR And
       OSV.CD_LINEA_ATTIVITA = INLA And
       OSV.CD_CDS = OS.CD_CDS And
       OSV.ESERCIZIO = OS.ESERCIZIO And
       OSV.PG_OBBLIGAZIONE = OS.PG_OBBLIGAZIONE And
       OSV.PG_OBBLIGAZIONE_SCADENZARIO = OS.PG_OBBLIGAZIONE_SCADENZARIO And
       OSV.CD_CDS = O.CD_CDS And
       OSV.ESERCIZIO = O.ESERCIZIO And
       OSV.PG_OBBLIGAZIONE = O.PG_OBBLIGAZIONE And
       O.ESERCIZIO_ORIGINALE = INESRES And
       O.CD_TIPO_DOCUMENTO_CONT = Decode(INCDR, '999.000.000', 'IMP_RES', 'OBB_RES');

Elsif INGEST = 'E' Then

   Select Sum(IM_VOCE)
   Into   VALORE
   From   ACCERTAMENTO_SCAD_VOCE OSV, ACCERTAMENTO O, ACCERTAMENTO_SCADENZARIO OS
   Where  O.ESERCIZIO = INES And
          O.TI_APPARTENENZA = INAPP And
          O.TI_GESTIONE = INGEST And
          O.CD_VOCE = INVOCE And
          OSV.CD_CENTRO_RESPONSABILITA = INCDR And
          OSV.CD_LINEA_ATTIVITA = INLA And
          OSV.CD_CDS = OS.CD_CDS And
          OSV.ESERCIZIO = OS.ESERCIZIO And
          OSV.PG_ACCERTAMENTO = OS.PG_ACCERTAMENTO And
          OSV.PG_ACCERTAMENTO_SCADENZARIO = OS.PG_ACCERTAMENTO_SCADENZARIO And
          OSV.CD_CDS = O.CD_CDS And
          OSV.ESERCIZIO = O.ESERCIZIO And
          OSV.PG_ACCERTAMENTO = O.PG_ACCERTAMENTO And
          O.ESERCIZIO_ORIGINALE = INESRES And
          O.CD_TIPO_DOCUMENTO_CONT = 'ACR_RES';

End If;

Return  Nvl(VALORE, 0);
End;


Function IM_MANDATI_REVERSALI_PRO (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type)
RETURN NUMBER Is
VALORE NUMBER;
PGIRO           ELEMENTO_VOCE.FL_PARTITA_GIRO%Type;
TIPOLOGIA1       VARCHAR2(200);
TIPOLOGIA2       VARCHAR2(200);
TIPOLOGIA3       VARCHAR2(200);

BEGIN

Dbms_Output.PUT_LINE ('A');

Select FL_PARTITA_GIRO
Into   PGIRO
From   ELEMENTO_VOCE
Where  ESERCIZIO        = INES And
       TI_APPARTENENZA  = INAPP And
       TI_GESTIONE      = INGEST And
       CD_ELEMENTO_VOCE = INELVOCE;

If INGEST = 'S' Then

  If INES = INESRES Then -- MANDATI SU IMPEGNI A COMPETENZA

     If PGIRO = 'Y' Then
        TIPOLOGIA1 := 'OBB';
        TIPOLOGIA2 := 'OBB_PGIRO';
        TIPOLOGIA3 := 'IMP';
     Else
        If INCDR = '999.000.000' Then
            TIPOLOGIA1 := 'IMP';
        Else
            TIPOLOGIA1 := 'OBB';
        End If;
     End If;

     Select SUM(DECODE(NVL(OS.IM_SCADENZA,0),0,0,(OSV.IM_VOCE/OS.IM_SCADENZA )*MR.IM_MANDATO_RIGA))
     Into   VALORE
     From   OBBLIGAZIONE_SCAD_VOCE OSV, OBBLIGAZIONE O, OBBLIGAZIONE_SCADENZARIO OS, MANDATO_RIGA MR, MANDATO M
     Where  OSV.ESERCIZIO = INES And
            OSV.TI_APPARTENENZA = INAPP And
            OSV.TI_GESTIONE = INGEST And
            OSV.CD_VOCE = INVOCE And
            OSV.CD_CENTRO_RESPONSABILITA = INCDR And
            OSV.CD_LINEA_ATTIVITA = INLA And
            OSV.CD_CDS = OS.CD_CDS And
            OSV.ESERCIZIO = OS.ESERCIZIO And
            OSV.PG_OBBLIGAZIONE = OS.PG_OBBLIGAZIONE And
            OSV.PG_OBBLIGAZIONE_SCADENZARIO = OS.PG_OBBLIGAZIONE_SCADENZARIO And
            OSV.CD_CDS = O.CD_CDS And
            OSV.ESERCIZIO = O.ESERCIZIO And
            OSV.PG_OBBLIGAZIONE = O.PG_OBBLIGAZIONE And
            O.ESERCIZIO_ORIGINALE = INES And
           (O.CD_TIPO_DOCUMENTO_CONT = TIPOLOGIA1 Or
            O.CD_TIPO_DOCUMENTO_CONT = Nvl(TIPOLOGIA2, TIPOLOGIA1) Or
            O.CD_TIPO_DOCUMENTO_CONT = Nvl(TIPOLOGIA3, TIPOLOGIA1)) And
            OS.CD_CDS = MR.CD_CDS AND
            OS.ESERCIZIO = MR.ESERCIZIO_OBBLIGAZIONE AND
            OS.PG_OBBLIGAZIONE = MR.PG_OBBLIGAZIONE And
            OS.PG_OBBLIGAZIONE_SCADENZARIO = MR.PG_OBBLIGAZIONE_SCADENZARIO And
            MR.CD_CDS     = M.CD_CDS     AND
            MR.ESERCIZIO  = M.ESERCIZIO  AND
            MR.PG_MANDATO = M.PG_MANDATO And
            M.STATO != 'A';

  Elsif INES > INESRES Then -- RESIDUI PROPRI

    If INCDR != '999.000.000' Then

     Select SUM(DECODE(NVL(OS.IM_SCADENZA,0),0,0,(OSV.IM_VOCE/OS.IM_SCADENZA )*MR.IM_MANDATO_RIGA))
     Into   VALORE
     From   OBBLIGAZIONE_SCAD_VOCE OSV, OBBLIGAZIONE O, OBBLIGAZIONE_SCADENZARIO OS, MANDATO_RIGA MR, MANDATO M
     Where  OSV.ESERCIZIO = INES And
            OSV.TI_APPARTENENZA = INAPP And
            OSV.TI_GESTIONE = INGEST And
            OSV.CD_VOCE = INVOCE And
            OSV.CD_CENTRO_RESPONSABILITA = INCDR And
            OSV.CD_LINEA_ATTIVITA = INLA And
            OSV.CD_CDS = OS.CD_CDS And
            OSV.ESERCIZIO = OS.ESERCIZIO And
            OSV.PG_OBBLIGAZIONE = OS.PG_OBBLIGAZIONE And
            OSV.PG_OBBLIGAZIONE_SCADENZARIO = OS.PG_OBBLIGAZIONE_SCADENZARIO And
            OSV.CD_CDS = O.CD_CDS And
            OSV.ESERCIZIO = O.ESERCIZIO And
            OSV.PG_OBBLIGAZIONE = O.PG_OBBLIGAZIONE And
            O.ESERCIZIO_ORIGINALE = INESRES And
            O.CD_TIPO_DOCUMENTO_CONT = Decode(INCDR, '999.000.000', 'IMP_RES', 'OBB_RES') And
            OS.CD_CDS = MR.CD_CDS AND
            OS.ESERCIZIO = MR.ESERCIZIO_OBBLIGAZIONE AND
            OS.PG_OBBLIGAZIONE = MR.PG_OBBLIGAZIONE And
            OS.PG_OBBLIGAZIONE_SCADENZARIO = MR.PG_OBBLIGAZIONE_SCADENZARIO And
            MR.CD_CDS     = M.CD_CDS     AND
            MR.ESERCIZIO  = M.ESERCIZIO  AND
            MR.PG_MANDATO = M.PG_MANDATO And
            M.STATO != 'A';

    Elsif INCDR = '999.000.000' Then

     Select SUM(DECODE(NVL(OS.IM_SCADENZA,0),0,0,(OSV.IM_VOCE/OS.IM_SCADENZA )*MR.IM_MANDATO_RIGA))
     Into   VALORE
     From   OBBLIGAZIONE_SCAD_VOCE OSV, OBBLIGAZIONE O, OBBLIGAZIONE_SCADENZARIO OS, MANDATO_RIGA MR, MANDATO M
     Where  OSV.ESERCIZIO = INES And
            OSV.TI_APPARTENENZA = INAPP And
            OSV.TI_GESTIONE = INGEST And
            OSV.CD_VOCE = INVOCE And
            OSV.CD_CENTRO_RESPONSABILITA = INCDR And
            OSV.CD_LINEA_ATTIVITA = INLA And
            OSV.CD_CDS = OS.CD_CDS And
            OSV.ESERCIZIO = OS.ESERCIZIO And
            OSV.PG_OBBLIGAZIONE = OS.PG_OBBLIGAZIONE And
            OSV.PG_OBBLIGAZIONE_SCADENZARIO = OS.PG_OBBLIGAZIONE_SCADENZARIO And
            OSV.CD_CDS = O.CD_CDS And
            OSV.ESERCIZIO = O.ESERCIZIO And
            OSV.PG_OBBLIGAZIONE = O.PG_OBBLIGAZIONE And
            O.ESERCIZIO_ORIGINALE = INESRES And
            O.CD_TIPO_DOCUMENTO_CONT = 'IMP_RES' And
            OS.CD_CDS = MR.CD_CDS AND
            OS.ESERCIZIO = MR.ESERCIZIO_OBBLIGAZIONE AND
            OS.PG_OBBLIGAZIONE = MR.PG_OBBLIGAZIONE And
            OS.PG_OBBLIGAZIONE_SCADENZARIO = MR.PG_OBBLIGAZIONE_SCADENZARIO And
            MR.CD_CDS     = M.CD_CDS     AND
            MR.ESERCIZIO  = M.ESERCIZIO  AND
            MR.PG_MANDATO = M.PG_MANDATO And
            M.STATO != 'A';

    End If;

  End If;

Elsif INGEST = 'E' Then

  If INES = INESRES Then -- COMPETENZA

   Select SUM(DECODE(NVL(OS.IM_SCADENZA,0),0,0,(OSV.IM_VOCE/OS.IM_SCADENZA )*RR.IM_REVERSALE_RIGA))
   Into   VALORE
   From   ACCERTAMENTO_SCAD_VOCE OSV, ACCERTAMENTO O, ACCERTAMENTO_SCADENZARIO OS, REVERSALE_RIGA RR, REVERSALE R
   Where  O.ESERCIZIO = INES And
          O.TI_APPARTENENZA = INAPP And
          O.TI_GESTIONE = INGEST And
          O.CD_VOCE = INVOCE And
          OSV.CD_CENTRO_RESPONSABILITA = INCDR And
          OSV.CD_LINEA_ATTIVITA = INLA And
          OSV.CD_CDS = OS.CD_CDS And
          OSV.ESERCIZIO = OS.ESERCIZIO And
          OSV.PG_ACCERTAMENTO = OS.PG_ACCERTAMENTO And
          OSV.PG_ACCERTAMENTO_SCADENZARIO = OS.PG_ACCERTAMENTO_SCADENZARIO And
          OSV.CD_CDS = O.CD_CDS And
          OSV.ESERCIZIO = O.ESERCIZIO And
          OSV.PG_ACCERTAMENTO = O.PG_ACCERTAMENTO And
          O.ESERCIZIO_ORIGINALE = INES And
          O.CD_TIPO_DOCUMENTO_CONT In ('ACR', 'ACR_SIST') And
          OS.CD_CDS = RR.CD_CDS And
          OS.ESERCIZIO = RR.ESERCIZIO_ACCERTAMENTO And
          OS.PG_ACCERTAMENTO = RR.PG_ACCERTAMENTO And
          OS.PG_ACCERTAMENTO_SCADENZARIO = RR.PG_ACCERTAMENTO_SCADENZARIO And
          RR.CD_CDS     = R.CD_CDS     AND
          RR.ESERCIZIO  = R.ESERCIZIO  AND
          RR.PG_REVERSALE = R.PG_REVERSALE And
          R.STATO != 'A';

  Elsif INES > INESRES Then -- RESIDUI

   Select SUM(DECODE(NVL(OS.IM_SCADENZA,0),0,0,(OSV.IM_VOCE/OS.IM_SCADENZA )*RR.IM_REVERSALE_RIGA))
   Into   VALORE
   From   ACCERTAMENTO_SCAD_VOCE OSV, ACCERTAMENTO O, ACCERTAMENTO_SCADENZARIO OS, REVERSALE_RIGA RR, REVERSALE R
   Where  O.ESERCIZIO = INES And
          O.TI_APPARTENENZA = INAPP And
          O.TI_GESTIONE = INGEST And
          O.CD_VOCE = INVOCE And
          OSV.CD_CENTRO_RESPONSABILITA = INCDR And
          OSV.CD_LINEA_ATTIVITA = INLA And
          OSV.CD_CDS = OS.CD_CDS And
          OSV.ESERCIZIO = OS.ESERCIZIO And
          OSV.PG_ACCERTAMENTO = OS.PG_ACCERTAMENTO And
          OSV.PG_ACCERTAMENTO_SCADENZARIO = OS.PG_ACCERTAMENTO_SCADENZARIO And
          OSV.CD_CDS = O.CD_CDS And
          OSV.ESERCIZIO = O.ESERCIZIO And
          OSV.PG_ACCERTAMENTO = O.PG_ACCERTAMENTO And
          O.ESERCIZIO_ORIGINALE = INESRES And
          O.CD_TIPO_DOCUMENTO_CONT = 'ACR_RES' And
          OS.CD_CDS = RR.CD_CDS AND
          OS.ESERCIZIO = RR.ESERCIZIO_ACCERTAMENTO And
          OS.PG_ACCERTAMENTO = RR.PG_ACCERTAMENTO AND
          OS.PG_ACCERTAMENTO_SCADENZARIO = RR.PG_ACCERTAMENTO_SCADENZARIO And
          RR.CD_CDS     = R.CD_CDS     AND
          RR.ESERCIZIO  = R.ESERCIZIO  AND
          RR.PG_REVERSALE = R.PG_REVERSALE And
          R.STATO != 'A';

  End If;

End If;

Return  Nvl(VALORE, 0);

END;


Function IM_MANDATI_REVERSALI_IMP (INES            VOCE_F_SALDI_CDR_LINEA.ESERCIZIO%Type,
                               INESRES         VOCE_F_SALDI_CDR_LINEA.ESERCIZIO_RES%Type,
                               INCDR           VOCE_F_SALDI_CDR_LINEA.CD_CENTRO_RESPONSABILITA%Type,
                               INLA            VOCE_F_SALDI_CDR_LINEA.CD_LINEA_ATTIVITA%Type,
                               INAPP           VOCE_F_SALDI_CDR_LINEA.TI_APPARTENENZA%Type,
                               INGEST          VOCE_F_SALDI_CDR_LINEA.TI_GESTIONE%Type,
                               INVOCE          VOCE_F_SALDI_CDR_LINEA.CD_VOCE%Type,
                               INELVOCE        VOCE_F_SALDI_CDR_LINEA.CD_ELEMENTO_VOCE%Type)
RETURN NUMBER Is
VALORE NUMBER;

BEGIN

If INGEST = 'S' Then

     Select SUM(DECODE(NVL(OS.IM_SCADENZA,0),0,0,(OSV.IM_VOCE/OS.IM_SCADENZA )*MR.IM_MANDATO_RIGA))
     Into   VALORE
     From   OBBLIGAZIONE_SCAD_VOCE OSV, OBBLIGAZIONE O, OBBLIGAZIONE_SCADENZARIO OS, MANDATO_RIGA MR, MANDATO M
     Where  OSV.ESERCIZIO = INES And
            OSV.TI_APPARTENENZA = INAPP And
            OSV.TI_GESTIONE = INGEST And
            OSV.CD_VOCE = INVOCE And
            OSV.CD_CENTRO_RESPONSABILITA = INCDR And
            OSV.CD_LINEA_ATTIVITA = INLA And
            OSV.CD_CDS = OS.CD_CDS And
            OSV.ESERCIZIO = OS.ESERCIZIO And
            OSV.PG_OBBLIGAZIONE = OS.PG_OBBLIGAZIONE And
            OSV.PG_OBBLIGAZIONE_SCADENZARIO = OS.PG_OBBLIGAZIONE_SCADENZARIO And
            OSV.CD_CDS = O.CD_CDS And
            OSV.ESERCIZIO = O.ESERCIZIO And
            OSV.PG_OBBLIGAZIONE = O.PG_OBBLIGAZIONE And
            O.ESERCIZIO_ORIGINALE = INESRES And
            O.CD_TIPO_DOCUMENTO_CONT = 'OBB_RESIM' And
            OS.CD_CDS = MR.CD_CDS And
            OS.ESERCIZIO = MR.ESERCIZIO_OBBLIGAZIONE And
            OS.PG_OBBLIGAZIONE  = MR.PG_OBBLIGAZIONE And
            OS.PG_OBBLIGAZIONE_SCADENZARIO = MR.PG_OBBLIGAZIONE_SCADENZARIO And
            MR.CD_CDS     = M.CD_CDS     AND
            MR.ESERCIZIO  = M.ESERCIZIO  AND
            MR.PG_MANDATO = M.PG_MANDATO And
            M.STATO != 'A';
End If;

Return  Nvl(VALORE, 0);

END;


End;


