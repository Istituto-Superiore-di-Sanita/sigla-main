--------------------------------------------------------
--  DDL for View V_ELEMENTO_VOCE_PDG_SPE
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_ELEMENTO_VOCE_PDG_SPE" ("ESERCIZIO", "CD_ELEMENTO_VOCE", "TI_APPARTENENZA", "TI_GESTIONE", "TI_ELEMENTO_VOCE", "CD_PARTE", "CD_PROPRIO_ELEMENTO", "DS_ELEMENTO_VOCE", "DUVA", "CD_ELEMENTO_PADRE", "UTUV", "DACR", "UTCR", "PG_VER_REC", "FL_LIMITE_ASS_OBBLIG", "FL_VOCE_PERSONALE", "FL_PARTITA_GIRO", "CD_CAPOCONTO_FIN", "FL_VOCE_SAC", "FL_VOCE_NON_SOGG_IMP_AUT", "CD_FUNZIONE", "CD_TIPO_UNITA", "ESERCIZIO_CLA_S", "COD_CLA_S", "ESERCIZIO_CLA_E", "COD_CLA_E", "FL_RECON", "FL_INV_BENI_PATR", "FL_VOCE_FONDO", "FL_CHECK_TERZO_SIOPE", "ID_CLASSIFICAZIONE", "CD_CLASSIFICAZIONE", "FL_INV_BENI_COMP", "FL_LIMITE_SPESA", "FL_PRELIEVO", "FL_SOGGETTO_PRELIEVO", "PERC_PRELIEVO_PDGP_ENTRATE", "FL_SOLO_RESIDUO", "FL_SOLO_COMPETENZA", "FL_TROVATO", "FL_MISSIONI", "FL_AZZERA_RESIDUI", "ESERCIZIO_ELEMENTO_PADRE", "TI_APPARTENENZA_ELEMENTO_PADRE", "TI_GESTIONE_ELEMENTO_PADRE", "CD_UNITA_PIANO", "CD_VOCE_PIANO", "GG_DEROGA_OBBL_COMP_PRG_SCAD", "GG_DEROGA_OBBL_RES_PRG_SCAD") AS 
  SELECT ELEMENTO_VOCE.ESERCIZIO                              --
							    --
							    -- Date: 16/05/2007
							    -- Version: 1.7
							    --
							    -- Estrae tutti gli elementi voce validi per un dettaglio di spese del pdg
							    -- bisogna mettere la clausola sulla funzione e il tipo unita.
							    --
							    -- History:
							    --
							    -- Date: 15/09/2001
							    -- Version: 1.0
							    -- Creazione
							    --
							    -- Date: 25/09/2001
							    -- Version: 1.1
							    -- Aggiunto clausole su TI_GESTIONE = 'S' e TI_APPARTENENZA = 'C' TI_ELEMENTO_VOCE = 'C'
							    --
							    -- Date: 16/11/2001
							    -- Version: 1.2
							    -- Aggiunto FL_PARTITA_GIRO
							    --
							    -- Date: 27/11/2001
							    -- Version: 1.3
							    -- Aggiunto CD_CAPOCONTO_FIN
							    --
							    -- Date: 30/11/2001
							    -- Version: 1.4
							    -- Aggiunt FL_VOCE_SAC, FL_VOCE_NON_SOGG_IMP_AUT
							    --
							    -- Date: 16/06/2006
							    -- Version: 1.5
							    -- Aggiunta campo FL_VOCE_FONDO
							    --
							    -- Date: 16/05/2007
							    -- Version: 1.7
							    -- Aggiunta campo FL_CHECK_TERZO_SIOPE
							    --
                                -- Date: 06/10/2015
                                -- Version: 1.8
                                -- Aggiunta nuova gestione pdg e campi ELEMENTO_VOCE_PADRE
                                --
							    -- Body:
							    --
     , ELEMENTO_VOCE.CD_ELEMENTO_VOCE
     , ELEMENTO_VOCE.TI_APPARTENENZA
     , ELEMENTO_VOCE.TI_GESTIONE
     , ELEMENTO_VOCE.TI_ELEMENTO_VOCE
     , ELEMENTO_VOCE.CD_PARTE
     , ELEMENTO_VOCE.CD_PROPRIO_ELEMENTO
     , ELEMENTO_VOCE.DS_ELEMENTO_VOCE
     , ELEMENTO_VOCE.DUVA
     , ELEMENTO_VOCE.CD_ELEMENTO_PADRE
     , ELEMENTO_VOCE.UTUV
     , ELEMENTO_VOCE.DACR
     , ELEMENTO_VOCE.UTCR
     , ELEMENTO_VOCE.PG_VER_REC
     , ELEMENTO_VOCE.FL_LIMITE_ASS_OBBLIG
     , ELEMENTO_VOCE.FL_VOCE_PERSONALE
     , ELEMENTO_VOCE.FL_PARTITA_GIRO
     , ELEMENTO_VOCE.CD_CAPOCONTO_FIN
     , ELEMENTO_VOCE.FL_VOCE_SAC
     , ELEMENTO_VOCE.FL_VOCE_NON_SOGG_IMP_AUT
     , ASS_EV_FUNZ_TIPOCDS.CD_FUNZIONE
     , ASS_EV_FUNZ_TIPOCDS.CD_TIPO_UNITA
     , ELEMENTO_VOCE.ESERCIZIO_CLA_S
     , ELEMENTO_VOCE.COD_CLA_S
     , ELEMENTO_VOCE.ESERCIZIO_CLA_E
     , ELEMENTO_VOCE.COD_CLA_E
     , ELEMENTO_VOCE.FL_RECON
     , ELEMENTO_VOCE.FL_INV_BENI_PATR
     , ELEMENTO_VOCE.FL_VOCE_FONDO
     , ELEMENTO_VOCE.FL_CHECK_TERZO_SIOPE
     , ELEMENTO_VOCE.ID_CLASSIFICAZIONE
     , V_CLASSIFICAZIONE_VOCI.CD_CLASSIFICAZIONE
     , ELEMENTO_VOCE.FL_INV_BENI_COMP
     , Elemento_voce.fl_limite_spesa
     , ELEMENTO_VOCE.FL_PRELIEVO
     , ELEMENTO_VOCE.FL_SOGGETTO_PRELIEVO
     , ELEMENTO_VOCE.PERC_PRELIEVO_PDGP_ENTRATE
     , ELEMENTO_VOCE.FL_SOLO_RESIDUO
     , ELEMENTO_VOCE.FL_SOLO_COMPETENZA
     , ELEMENTO_VOCE.FL_TROVATO
     , ELEMENTO_VOCE.FL_MISSIONI
     , ELEMENTO_VOCE.FL_AZZERA_RESIDUI
     , ELEMENTO_VOCE.ESERCIZIO_ELEMENTO_PADRE
     , ELEMENTO_VOCE.TI_APPARTENENZA_ELEMENTO_PADRE
     , ELEMENTO_VOCE.TI_GESTIONE_ELEMENTO_PADRE
     , ELEMENTO_VOCE.CD_UNITA_PIANO
     , ELEMENTO_VOCE.CD_VOCE_PIANO
     , ELEMENTO_VOCE.GG_DEROGA_OBBL_COMP_PRG_SCAD
     , ELEMENTO_VOCE.GG_DEROGA_OBBL_RES_PRG_SCAD
  From ASS_EV_FUNZ_TIPOCDS
     , ELEMENTO_VOCE
     , V_CLASSIFICAZIONE_VOCI
     , PARAMETRI_CNR
 WHERE PARAMETRI_CNR.ESERCIZIO = ELEMENTO_VOCE.ESERCIZIO
   AND PARAMETRI_CNR.FL_NUOVO_PDG = 'N'
   AND ASS_EV_FUNZ_TIPOCDS.ESERCIZIO = ELEMENTO_VOCE.ESERCIZIO
   AND ASS_EV_FUNZ_TIPOCDS.CD_CONTO = ELEMENTO_VOCE.CD_ELEMENTO_VOCE
   AND ELEMENTO_VOCE.ID_CLASSIFICAZIONE = V_CLASSIFICAZIONE_VOCI.ID_CLASSIFICAZIONE ( + )
   And ELEMENTO_VOCE.TI_GESTIONE = 'S'
   AND ELEMENTO_VOCE.TI_APPARTENENZA = 'D'
   AND ELEMENTO_VOCE.TI_ELEMENTO_VOCE = 'C'
 UNION
SELECT ELEMENTO_VOCE.ESERCIZIO                              --
     , ELEMENTO_VOCE.CD_ELEMENTO_VOCE
     , ELEMENTO_VOCE.TI_APPARTENENZA
     , ELEMENTO_VOCE.TI_GESTIONE
     , ELEMENTO_VOCE.TI_ELEMENTO_VOCE
     , ELEMENTO_VOCE.CD_PARTE
     , ELEMENTO_VOCE.CD_PROPRIO_ELEMENTO
     , ELEMENTO_VOCE.DS_ELEMENTO_VOCE
     , ELEMENTO_VOCE.DUVA
     , ELEMENTO_VOCE.CD_ELEMENTO_PADRE
     , ELEMENTO_VOCE.UTUV
     , ELEMENTO_VOCE.DACR
     , ELEMENTO_VOCE.UTCR
     , ELEMENTO_VOCE.PG_VER_REC
     , ELEMENTO_VOCE.FL_LIMITE_ASS_OBBLIG
     , ELEMENTO_VOCE.FL_VOCE_PERSONALE
     , ELEMENTO_VOCE.FL_PARTITA_GIRO
     , ELEMENTO_VOCE.CD_CAPOCONTO_FIN
     , ELEMENTO_VOCE.FL_VOCE_SAC
     , ELEMENTO_VOCE.FL_VOCE_NON_SOGG_IMP_AUT
     , '01'
     , 'IST'
     , ELEMENTO_VOCE.ESERCIZIO_CLA_S
     , ELEMENTO_VOCE.COD_CLA_S
     , ELEMENTO_VOCE.ESERCIZIO_CLA_E
     , ELEMENTO_VOCE.COD_CLA_E
     , ELEMENTO_VOCE.FL_RECON
     , ELEMENTO_VOCE.FL_INV_BENI_PATR
     , ELEMENTO_VOCE.FL_VOCE_FONDO
     , ELEMENTO_VOCE.FL_CHECK_TERZO_SIOPE
     , ELEMENTO_VOCE.ID_CLASSIFICAZIONE
     , V_CLASSIFICAZIONE_VOCI.CD_CLASSIFICAZIONE
     , ELEMENTO_VOCE.FL_INV_BENI_COMP
     , Elemento_voce.fl_limite_spesa
     , ELEMENTO_VOCE.FL_PRELIEVO
     , ELEMENTO_VOCE.FL_SOGGETTO_PRELIEVO
     , ELEMENTO_VOCE.PERC_PRELIEVO_PDGP_ENTRATE
     , ELEMENTO_VOCE.FL_SOLO_RESIDUO
     , ELEMENTO_VOCE.FL_SOLO_COMPETENZA
     , ELEMENTO_VOCE.FL_TROVATO
     , ELEMENTO_VOCE.FL_MISSIONI
     , ELEMENTO_VOCE.FL_AZZERA_RESIDUI
     , ELEMENTO_VOCE.ESERCIZIO_ELEMENTO_PADRE
     , ELEMENTO_VOCE.TI_APPARTENENZA_ELEMENTO_PADRE
     , ELEMENTO_VOCE.TI_GESTIONE_ELEMENTO_PADRE
     , ELEMENTO_VOCE.CD_UNITA_PIANO
     , ELEMENTO_VOCE.CD_VOCE_PIANO
     , ELEMENTO_VOCE.GG_DEROGA_OBBL_COMP_PRG_SCAD
     , ELEMENTO_VOCE.GG_DEROGA_OBBL_RES_PRG_SCAD
  From ELEMENTO_VOCE
     , V_CLASSIFICAZIONE_VOCI
     , PARAMETRI_CNR
 WHERE PARAMETRI_CNR.ESERCIZIO = ELEMENTO_VOCE.ESERCIZIO
   AND PARAMETRI_CNR.FL_NUOVO_PDG = 'Y'
   AND ELEMENTO_VOCE.ID_CLASSIFICAZIONE = V_CLASSIFICAZIONE_VOCI.ID_CLASSIFICAZIONE ( + )
   And ELEMENTO_VOCE.TI_GESTIONE = 'S'
   AND ELEMENTO_VOCE.TI_APPARTENENZA = 'D'
   AND ELEMENTO_VOCE.TI_ELEMENTO_VOCE = 'C';

   COMMENT ON TABLE "V_ELEMENTO_VOCE_PDG_SPE"  IS 'Estrae tutti gli elementi voce validi per un dettaglio di spese del pdg
	bisogna mettere la clausola sulla funzione e il tipo unita.';
