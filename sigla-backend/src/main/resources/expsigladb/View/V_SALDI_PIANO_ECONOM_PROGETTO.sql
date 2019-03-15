--------------------------------------------------------
--  DDL for View V_SALDI_PIANO_ECONOM_PROGETTO
--------------------------------------------------------

CREATE OR REPLACE FORCE VIEW "V_SALDI_PIANO_ECONOM_PROGETTO" ("PG_PROGETTO", "ESERCIZIO", "CD_UNITA_PIANO", "CD_VOCE_PIANO", "TI_GESTIONE", "IMPORTO_FIN", "STANZIAMENTO_FIN", "VARIAPIU_FIN", "VARIAMENO_FIN", "TRASFPIU_FIN", "TRASFMENO_FIN", "IMPORTO_COFIN", "STANZIAMENTO_COFIN", "VARIAPIU_COFIN", "VARIAMENO_COFIN", "TRASFPIU_COFIN", "TRASFMENO_COFIN", "IMPACC", "MANRIS") AS 
  (SELECT   x.pg_progetto, x.esercizio, x.cd_unita_piano, x.cd_voce_piano,
             x.ti_gestione, 
             SUM (x.importo_fin) importo_fin,
             SUM (x.stanziamento_fin) stanziamento_fin,
             SUM (x.variapiu_fin) variapiu_fin,
             SUM (x.variameno_fin) variameno_fin,
             SUM (x.trasfpiu_fin) trasfpiu_fin,
             SUM (x.trasfmeno_fin) trasfmeno_fin,
             SUM (x.importo_cofin) importo_cofin,
             SUM (x.stanziamento_cofin) stanziamento_cofin,
             SUM (x.variapiu_cofin) variapiu_cofin,
             SUM (x.variameno_cofin) variameno_cofin,
             SUM (x.trasfpiu_cofin) trasfpiu_cofin,
             SUM (x.trasfmeno_cofin) trasfmeno_cofin,
             SUM (x.impacc) impacc,
             SUM (x.manris) manris
        FROM (SELECT a.pg_progetto, a.esercizio_piano esercizio,
                     a.cd_unita_organizzativa cd_unita_piano, a.cd_voce_piano,
                     'S' ti_gestione, NVL(a.im_spesa_finanziato, 0) importo_fin,
                     0 stanziamento_fin, 0 variapiu_fin, 0 variameno_fin,
                     0 trasfpiu_fin, 0 trasfmeno_fin,
                     NVL(a.im_spesa_cofinanziato, 0) importo_cofin,
                     0 stanziamento_cofin, 0 variapiu_cofin, 0 variameno_cofin, 
                     0 trasfpiu_cofin, 0 trasfmeno_cofin,
                     0 impacc, 0 manris
                FROM progetto_piano_economico a
              UNION ALL
              SELECT a.pg_progetto, a.esercizio, a.cd_unita_piano,
                     a.cd_voce_piano, 'S' ti_gestione, 
                     0 importo_fin, 
                     NVL(a.im_spese_gest_decentrata_est, 0) stanziamento_fin, 
                     0 variapiu_fin, 0 variameno_fin,
                     0 trasfpiu_fin, 0 trasfmeno_fin,
                     0 importo_cofin,
                     NVL (a.im_spese_gest_decentrata_int, 0) stanziamento_cofin, 
                     0 variapiu_cofin, 0 variameno_cofin, 
                     0 trasfpiu_cofin, 0 trasfmeno_cofin,
                     0 impacc, 0 manris
                FROM pdg_modulo_spese a
              WHERE a.cd_unita_piano is not null
              AND   a.cd_voce_piano is not null
              UNION ALL
              SELECT c.pg_progetto, a.esercizio, d.cd_unita_organizzativa,
                     d.cd_voce_piano, b.ti_gestione,
                     0 importo_fin, 0 stanziamento_fin, 
                     CASE
                       WHEN NVL(b.im_spese_gest_decentrata_est, 0) != 0
                       THEN CASE 
                              WHEN NVL(b.im_spese_gest_decentrata_est, 0)>0
                              THEN NVL(b.im_spese_gest_decentrata_est, 0)
                              ELSE 0
                            END
                       ELSE CASE
                               WHEN NVL (b.im_spese_gest_accentrata_est, 0) > 0 AND
                                    (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR')
                               THEN NVL(b.im_spese_gest_accentrata_est, 0)
                               ELSE 0
                             END
                     END variapiu_fin,
                     CASE
                       WHEN NVL(b.im_spese_gest_decentrata_est, 0) != 0
                       THEN CASE --NEL TRASFERIMENTO AEREE NON PRENDERE IN CONSIDERAZIONE GLI IMPORTI NEGATIVI
                              WHEN NVL(b.im_spese_gest_decentrata_est, 0)<0 
                              THEN ABS(NVL(b.im_spese_gest_decentrata_est, 0))
                              ELSE 0
                            END
                       ELSE CASE
                              WHEN NVL (b.im_spese_gest_accentrata_est, 0) < 0 AND
                                    (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR') 
                              THEN ABS(NVL(b.im_spese_gest_accentrata_est, 0))
                              ELSE 0
                            END
                     END variameno_fin,
                     CASE
                       WHEN NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                       THEN CASE
                              WHEN NVL(b.im_spese_gest_decentrata_est, 0) != 0
                              THEN CASE 
                                     WHEN NVL(b.im_spese_gest_decentrata_est, 0)>0
                                     THEN NVL(b.im_spese_gest_decentrata_est, 0)
                                     ELSE 0
                                   END
                              ELSE CASE
                                     WHEN NVL (b.im_spese_gest_accentrata_est, 0) > 0 AND
                                           (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR')
                                      THEN NVL(b.im_spese_gest_accentrata_est, 0)
                                      ELSE 0
                                    END
                            END
                       ELSE 0
                     END trasfpiu_fin,
                     CASE
                       WHEN NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                       THEN CASE
                              WHEN NVL(b.im_spese_gest_decentrata_est, 0) != 0
                              THEN CASE --NEL TRASFERIMENTO AEREE NON PRENDERE IN CONSIDERAZIONE GLI IMPORTI NEGATIVI
                                     WHEN NVL(b.im_spese_gest_decentrata_est, 0)<0
                                     THEN ABS(NVL(b.im_spese_gest_decentrata_est, 0))
                                     ELSE 0
                                   END
                              ELSE CASE
                                     WHEN NVL (b.im_spese_gest_accentrata_est, 0) < 0 AND
                                           (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR')
                                     THEN ABS(NVL(b.im_spese_gest_accentrata_est, 0))
                                     ELSE 0
                                   END
                            END
                       ELSE 0
                     END trasfmeno_fin,
                     0 importo_cofin, 0 stanziamento_cofin, 
                     CASE
                       WHEN NVL(b.im_spese_gest_decentrata_int, 0) != 0
                       THEN CASE 
                              WHEN NVL(b.im_spese_gest_decentrata_int, 0)>0
                              THEN NVL(b.im_spese_gest_decentrata_int, 0)
                              ELSE 0
                            END
                       ELSE CASE
                               WHEN NVL (b.im_spese_gest_accentrata_int, 0) > 0 AND
                                    (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR')
                               THEN NVL(b.im_spese_gest_accentrata_int, 0)
                               ELSE 0
                             END
                     END variapiu_cofin,
                     CASE
                       WHEN NVL(b.im_spese_gest_decentrata_int, 0) != 0
                       THEN CASE --NEL TRASFERIMENTO AEREE NON PRENDERE IN CONSIDERAZIONE GLI IMPORTI NEGATIVI
                              WHEN NVL(b.im_spese_gest_decentrata_int, 0)<0
                              THEN ABS(NVL(b.im_spese_gest_decentrata_int, 0))
                              ELSE 0
                            END
                       ELSE CASE
                              WHEN NVL (b.im_spese_gest_accentrata_int, 0) < 0 AND
                                   (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR')
                              THEN ABS(NVL(b.im_spese_gest_accentrata_int, 0))
                              ELSE 0
                            END
                     END variameno_cofin,
                     CASE
                       WHEN NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                       THEN CASE
                              WHEN NVL(b.im_spese_gest_decentrata_int, 0) != 0
                              THEN CASE 
                                     WHEN NVL(b.im_spese_gest_decentrata_int, 0)>0
                                     THEN NVL(b.im_spese_gest_decentrata_int, 0)
                                     ELSE 0
                                   END
                              ELSE CASE
                                      WHEN NVL (b.im_spese_gest_accentrata_int, 0) > 0 AND
                                           (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR')
                                      THEN NVL(b.im_spese_gest_accentrata_int, 0)
                                      ELSE 0
                                    END
                            END
                       ELSE 0
                     END trasfpiu_cofin,
                     CASE
                       WHEN NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                       THEN CASE
                              WHEN NVL(b.im_spese_gest_decentrata_int, 0) != 0
                              THEN CASE --NEL TRASFERIMENTO AEREE NON PRENDERE IN CONSIDERAZIONE GLI IMPORTI NEGATIVI
                                     WHEN NVL(b.im_spese_gest_decentrata_int, 0)<0
                                     THEN ABS(NVL(b.im_spese_gest_decentrata_int, 0))
                                     ELSE 0
                                   END
                              ELSE CASE
                                     WHEN NVL (b.im_spese_gest_accentrata_int, 0) < 0 AND
                                          (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR')
                                     THEN ABS(NVL(b.im_spese_gest_accentrata_int, 0))
                                     ELSE 0
                                   END
                            END
                       ELSE 0
                     END trasfmeno_cofin,
                     0 impacc, 0 manris
                FROM pdg_variazione a,
                     pdg_variazione_riga_gest b,
                     v_linea_attivita_valida c,
                     ass_progetto_piaeco_voce d
               WHERE a.esercizio = b.esercizio
                 AND a.pg_variazione_pdg = b.pg_variazione_pdg
                 AND a.stato IN ('APP', 'APF', 'PRD')
                 AND b.ti_gestione = 'S'
                 AND b.esercizio = c.esercizio
                 AND b.cd_cdr_assegnatario = c.cd_centro_responsabilita
                 AND b.cd_linea_attivita = c.cd_linea_attivita
                 AND c.pg_progetto = d.pg_progetto
                 AND d.esercizio_piano = b.esercizio
                 AND d.esercizio_voce = b.esercizio
                 AND d.ti_appartenenza = b.ti_appartenenza
                 AND d.ti_gestione = b.ti_gestione
                 AND d.cd_elemento_voce = b.cd_elemento_voce
                 AND c.cd_natura not in (select val01 from configurazione_cnr e
                                         where e.esercizio = 0
                                         and   e.cd_unita_funzionale = '*'
                                         and   e.cd_chiave_primaria = 'PROGETTI'
                                         and   e.cd_chiave_secondaria = 'NATURA_REIMPIEGO')
              UNION ALL
              SELECT c.pg_progetto, a.esercizio, d.cd_unita_organizzativa,
                     d.cd_voce_piano, b.ti_gestione,
                     0 importo_fin, 0 stanziamento_fin, 
                     0 variapiu_fin, 0 variameno_fin,
                     0 trasfpiu_fin, 0 trasfmeno_fin,
                     0 importo_cofin, 0 stanziamento_cofin, 
                     CASE
                       WHEN NVL(b.im_spese_gest_decentrata_est, 0) != 0
                       THEN CASE 
                              WHEN NVL(b.im_spese_gest_decentrata_est, 0)>0
                              THEN NVL(b.im_spese_gest_decentrata_est, 0)
                              ELSE 0
                            END
                       ELSE CASE
                               WHEN NVL (b.im_spese_gest_accentrata_est, 0) > 0 AND
                                    (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR')
                               THEN NVL(b.im_spese_gest_accentrata_est, 0)
                               ELSE 0
                             END
                     END variapiu_cofin,
                     CASE
                       WHEN NVL(b.im_spese_gest_decentrata_est, 0) != 0
                       THEN CASE 
                              WHEN NVL(b.im_spese_gest_decentrata_est, 0)<0 
                              THEN ABS(NVL(b.im_spese_gest_decentrata_est, 0))
                              ELSE 0
                            END
                       ELSE CASE
                              WHEN NVL (b.im_spese_gest_accentrata_est, 0) < 0 AND
                                   (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR')
                              THEN ABS(NVL(b.im_spese_gest_accentrata_est, 0))
                              ELSE 0
                            END
                     END variameno_cofin,
                     CASE
                       WHEN NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                       THEN CASE
                              WHEN NVL(b.im_spese_gest_decentrata_est, 0) != 0
                              THEN CASE 
                                     WHEN NVL(b.im_spese_gest_decentrata_est, 0)>0
                                     THEN NVL(b.im_spese_gest_decentrata_est, 0)
                                     ELSE 0
                                   END
                              ELSE CASE
                                      WHEN NVL (b.im_spese_gest_accentrata_est, 0) > 0 AND
                                           (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR')
                                      THEN NVL(b.im_spese_gest_accentrata_est, 0)
                                      ELSE 0
                                    END
                              END
                       ELSE 0
                     END trasfpiu_cofin,
                     CASE
                       WHEN NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                       THEN CASE
                              WHEN NVL(b.im_spese_gest_decentrata_est, 0) != 0
                              THEN CASE 
                                     WHEN NVL(b.im_spese_gest_decentrata_est, 0)<0 
                                     THEN ABS(NVL(b.im_spese_gest_decentrata_est, 0))
                                     ELSE 0
                                   END
                              ELSE CASE
                                     WHEN NVL (b.im_spese_gest_accentrata_est, 0) < 0 AND
                                          (b.cd_cdr_assegnatario_clgs is null OR b.categoria_dettaglio='SCR') 
                                     THEN ABS(NVL(b.im_spese_gest_accentrata_est, 0))
                                     ELSE 0
                                   END
                              END
                       ELSE 0
                     END trasfmeno_cofin,
                     0 impacc, 0 manris
                FROM pdg_variazione a,
                     pdg_variazione_riga_gest b,
                     v_linea_attivita_valida c,
                     ass_progetto_piaeco_voce d
               WHERE a.esercizio = b.esercizio
                 AND a.pg_variazione_pdg = b.pg_variazione_pdg
                 AND a.stato IN ('APP', 'APF', 'PRD')
                 AND b.ti_gestione = 'S'
                 AND b.esercizio = c.esercizio
                 AND b.cd_cdr_assegnatario = c.cd_centro_responsabilita
                 AND b.cd_linea_attivita = c.cd_linea_attivita
                 AND c.pg_progetto = d.pg_progetto
                 AND d.esercizio_piano = b.esercizio
                 AND d.esercizio_voce = b.esercizio
                 AND d.ti_appartenenza = b.ti_appartenenza
                 AND d.ti_gestione = b.ti_gestione
                 AND d.cd_elemento_voce = b.cd_elemento_voce
                 AND c.cd_natura in (select val01 from configurazione_cnr e
                                     where e.esercizio = 0
                                     and   e.cd_unita_funzionale = '*'
                                     and   e.cd_chiave_primaria = 'PROGETTI'
                                     and   e.cd_chiave_secondaria = 'NATURA_REIMPIEGO')
              UNION ALL
              SELECT c.pg_progetto, a.esercizio_res, d.cd_unita_organizzativa,
                     d.cd_voce_piano, b.ti_gestione, 
                     0 importo_fin, 0 stanziamento_fin, 
                     CASE
                        WHEN a.tipologia_fin='FES' AND NVL(b.im_variazione, 0) > 0
                        THEN NVL(b.im_variazione, 0)
                        ELSE 0
                     END variapiu_fin,
                     CASE
                        WHEN a.tipologia_fin='FES' AND NVL(b.im_variazione, 0) < 0
                        THEN ABS(NVL (b.im_variazione, 0))
                        ELSE 0
                     END variameno_fin,
                     CASE
                        WHEN a.tipologia_fin='FES' and NVL(b.im_variazione, 0) > 0 AND
                             NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                        THEN NVL(b.im_variazione, 0)
                        ELSE 0
                     END trasfpiu_fin,
                     CASE
                        WHEN a.tipologia_fin='FES' and NVL(b.im_variazione, 0) < 0 AND
                             NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                        THEN ABS(NVL (b.im_variazione, 0))
                        ELSE 0
                     END trasfmeno_fin,
                     0 importo_cofin, 0 stanziamento_cofin, 
                     CASE
                        WHEN a.tipologia_fin='FIN' AND NVL(b.im_variazione, 0) > 0
                        THEN NVL(b.im_variazione, 0)
                        ELSE 0
                     END variapiu_cofin,
                     CASE
                        WHEN a.tipologia_fin='FIN' AND NVL(b.im_variazione, 0) < 0
                        THEN ABS(NVL (b.im_variazione, 0))
                        ELSE 0
                     END variameno_cofin,
                     CASE
                        WHEN a.tipologia_fin='FIN' AND NVL(b.im_variazione, 0) > 0 AND
                             NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                        THEN NVL(b.im_variazione, 0)
                        ELSE 0
                     END trasfpiu_fin,
                     CASE
                        WHEN a.tipologia_fin='FIN' AND NVL(b.im_variazione, 0) < 0  AND
                             NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                        THEN ABS(NVL (b.im_variazione, 0))
                        ELSE 0
                     END trasfmeno_cofin,
                     0 impacc, 0 manris
                FROM var_stanz_res a,
                     var_stanz_res_riga b,
                     v_linea_attivita_valida c,
                     ass_progetto_piaeco_voce d
               WHERE a.esercizio = b.esercizio
                 AND a.pg_variazione = b.pg_variazione
                 AND a.stato IN ('APP', 'APF', 'PRD')
                 AND b.ti_gestione = 'S'
                 AND b.esercizio = c.esercizio
                 AND b.cd_cdr = c.cd_centro_responsabilita
                 AND b.cd_linea_attivita = c.cd_linea_attivita
                 AND c.pg_progetto = d.pg_progetto
                 AND d.esercizio_piano = b.esercizio_res
                 AND d.esercizio_voce = b.esercizio_res
                 AND d.ti_appartenenza = b.ti_appartenenza
                 AND d.ti_gestione = b.ti_gestione
                 AND d.cd_elemento_voce = b.cd_elemento_voce
                 AND c.cd_natura not in (select val01 from configurazione_cnr e
                                         where e.esercizio = 0
                                         and   e.cd_unita_funzionale = '*'
                                         and   e.cd_chiave_primaria = 'PROGETTI'
                                         and   e.cd_chiave_secondaria = 'NATURA_REIMPIEGO')                
              UNION ALL
              SELECT c.pg_progetto, a.esercizio_res, d.cd_unita_organizzativa,
                     d.cd_voce_piano, b.ti_gestione, 
                     0 importo_fin, 0 stanziamento_fin, 
                     0 variapiu_fin,
                     0 variameno_fin,
                     0 trasfpiu_fin,
                     0 trasfmeno_fin,
                     0 importo_cofin, 0 stanziamento_cofin, 
                     CASE
                        WHEN NVL(b.im_variazione, 0) > 0
                        THEN NVL(b.im_variazione, 0)
                        ELSE 0
                     END variapiu_cofin,
                     CASE
                        WHEN NVL(b.im_variazione, 0) < 0 AND 
                             NVL(a.ti_motivazione_variazione,'X')!='TAE'
                        THEN ABS(NVL (b.im_variazione, 0))
                        ELSE 0
                     END variameno_cofin,
                     CASE
                        WHEN NVL(b.im_variazione, 0) > 0 AND
                             NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                        THEN NVL(b.im_variazione, 0)
                        ELSE 0
                     END trasfpiu_cofin,
                     CASE
                        WHEN NVL(b.im_variazione, 0) < 0 AND
                             NVL(a.ti_motivazione_variazione,'X') in ('BAN','PRG','ALT','TAE','TAU')
                        THEN ABS(NVL (b.im_variazione, 0))
                        ELSE 0
                     END trasfmeno_cofin,
                     0 impacc, 0 manris
                FROM var_stanz_res a,
                     var_stanz_res_riga b,
                     v_linea_attivita_valida c,
                     ass_progetto_piaeco_voce d
               WHERE a.esercizio = b.esercizio
                 AND a.pg_variazione = b.pg_variazione
                 AND a.stato IN ('APP', 'APF', 'PRD')
                 AND b.ti_gestione = 'S'
                 AND b.esercizio = c.esercizio
                 AND b.cd_cdr = c.cd_centro_responsabilita
                 AND b.cd_linea_attivita = c.cd_linea_attivita
                 AND c.pg_progetto = d.pg_progetto
                 AND d.esercizio_piano = b.esercizio_res
                 AND d.esercizio_voce = b.esercizio_res
                 AND d.ti_appartenenza = b.ti_appartenenza
                 AND d.ti_gestione = b.ti_gestione
                 AND d.cd_elemento_voce = b.cd_elemento_voce
                 AND c.cd_natura in (select val01 from configurazione_cnr e
                                     where e.esercizio = 0
                                     and   e.cd_unita_funzionale = '*'
                                     and   e.cd_chiave_primaria = 'PROGETTI'
                                     and   e.cd_chiave_secondaria = 'NATURA_REIMPIEGO')
              UNION ALL
              SELECT b.pg_progetto, a.esercizio_res, c.cd_unita_organizzativa,
                     c.cd_voce_piano, a.ti_gestione, 
                     0 importo_fin, 0 stanziamento_fin, 
                     0 variapiu_fin, 0 variameno_fin,
                     0 trasfpiu_fin, 0 trasfmeno_fin,
                     0 importo_cofin, 0 stanziamento_cofin, 
                     0 variapiu_cofin, 0 variameno_cofin,
                     0 trasfpiu_cofin, 0 trasfmeno_cofin,
                     CASE
                        WHEN a.ESERCIZIO = a.ESERCIZIO_RES
                        THEN NVL(a.IM_OBBL_ACC_COMP, 0)
                        ELSE NVL(a.IM_OBBL_RES_IMP, 0) +
                             NVL(a.VAR_PIU_OBBL_RES_IMP, 0) - NVL(a.VAR_MENO_OBBL_RES_IMP, 0) +
                             NVL(a.VAR_PIU_OBBL_RES_PRO, 0) - NVL(a.VAR_MENO_OBBL_RES_PRO, 0)
                     END impacc,
                     NVL(a.IM_MANDATI_REVERSALI_PRO, 0) + NVL(a.IM_MANDATI_REVERSALI_IMP, 0) manris
                FROM voce_f_saldi_cdr_linea a,
                     v_linea_attivita_valida b,
                     ass_progetto_piaeco_voce c
               WHERE a.esercizio = b.esercizio
                 AND a.cd_centro_responsabilita = b.cd_centro_responsabilita
                 AND a.cd_linea_attivita = b.cd_linea_attivita
                 AND b.pg_progetto = c.pg_progetto
                 AND c.esercizio_piano = a.esercizio_res
                 AND c.esercizio_voce = a.esercizio_res
                 AND c.ti_appartenenza = a.ti_appartenenza
                 AND c.ti_gestione = a.ti_gestione
                 AND c.cd_elemento_voce = a.cd_elemento_voce) x
    GROUP BY x.pg_progetto,
             x.esercizio,
             x.cd_unita_piano,
             x.cd_voce_piano,
             x.ti_gestione);
