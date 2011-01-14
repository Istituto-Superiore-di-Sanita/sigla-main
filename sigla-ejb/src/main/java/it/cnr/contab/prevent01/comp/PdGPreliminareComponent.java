/*
 * Created on Oct 20, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.cnr.contab.prevent01.comp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;

import it.cnr.contab.config00.bulk.Parametri_cnrBulk;
import it.cnr.contab.config00.pdcfin.bulk.Elemento_voceHome;
import it.cnr.contab.config00.pdcfin.cla.bulk.V_classificazione_vociBulk;
import it.cnr.contab.config00.pdcfin.cla.bulk.V_classificazione_vociHome;
import it.cnr.contab.config00.sto.bulk.CdrBulk;
import it.cnr.contab.config00.sto.bulk.CdrHome;
import it.cnr.contab.config00.sto.bulk.CdsBulk;
import it.cnr.contab.config00.sto.bulk.CdsHome;
import it.cnr.contab.config00.sto.bulk.DipartimentoBulk;
import it.cnr.contab.config00.sto.bulk.Tipo_unita_organizzativaHome;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativa_enteBulk;
import it.cnr.contab.prevent00.bulk.Pdg_piano_ripartoBulk;
import it.cnr.contab.prevent00.bulk.Pdg_piano_ripartoHome;
import it.cnr.contab.prevent01.bulk.Ass_dipartimento_areaBulk;
import it.cnr.contab.prevent01.bulk.Pdg_Modulo_EntrateBulk;
import it.cnr.contab.prevent01.bulk.Pdg_Modulo_EntrateHome;
import it.cnr.contab.prevent01.bulk.Pdg_approvato_dip_areaBulk;
import it.cnr.contab.prevent01.bulk.Pdg_approvato_dip_areaHome;
import it.cnr.contab.prevent01.bulk.Pdg_contrattazione_speseBulk;
import it.cnr.contab.prevent01.bulk.Pdg_contrattazione_speseHome;
import it.cnr.contab.prevent01.bulk.Pdg_esercizioBulk;
import it.cnr.contab.prevent01.bulk.Pdg_esercizioHome;
import it.cnr.contab.prevent01.bulk.Pdg_moduloBulk;
import it.cnr.contab.prevent01.bulk.Pdg_moduloHome;
import it.cnr.contab.prevent01.bulk.Pdg_modulo_costiBulk;
import it.cnr.contab.prevent01.bulk.Pdg_modulo_speseBulk;
import it.cnr.contab.prevent01.bulk.Pdg_modulo_speseHome;
import it.cnr.contab.prevent01.bulk.V_pdg_piano_ripartoBulk;
import it.cnr.contab.prevent01.bulk.V_pdg_piano_ripartoHome;
import it.cnr.contab.progettiric00.core.bulk.ProgettoBulk;
import it.cnr.contab.progettiric00.core.bulk.ProgettoHome;
import it.cnr.contab.progettiric00.core.bulk.Progetto_sipBulk;
import it.cnr.contab.progettiric00.ejb.ProgettoRicercaModuloComponentSession;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.utenze00.bulk.UtenteBulk;
import it.cnr.contab.util.Utility;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.bulk.BusyResourceException;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.OutdatedResourceException;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.persistency.IntrospectionException;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.Persistent;
import it.cnr.jada.persistency.sql.ColumnMap;
import it.cnr.jada.persistency.sql.ColumnMapping;
import it.cnr.jada.persistency.sql.LoggableStatement;
import it.cnr.jada.persistency.sql.Query;
import it.cnr.jada.persistency.sql.SQLBroker;
import it.cnr.jada.persistency.sql.SQLBuilder;
import it.cnr.jada.util.ejb.EJBCommonServices;

/**
 * @author mincarnatoPdg_esercizioHome
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PdGPreliminareComponent extends it.cnr.jada.comp.CRUDComponent implements Cloneable, Serializable {

	public Pdg_esercizioBulk cambiaStatoConBulk( UserContext userContext,Pdg_esercizioBulk pdg_esercizio) throws it.cnr.jada.comp.ComponentException 
	{


		try 
		{

			Pdg_esercizioHome pdg_esercizioHome = (Pdg_esercizioHome) getHome(userContext,pdg_esercizio);

			String next =(String)Pdg_esercizioBulk.getProssimoStato().get( pdg_esercizio.getStato());
			if (next!=null) {

				// CONTROLLI

				// solo l'UO 999.000 pu� avanzare lo stato a STATO_IN_ESAME_CDR e STATO_ESAMINATO_CDR
				if (next.equals(Pdg_esercizioBulk.STATO_IN_ESAME_CDR)||
					next.equals(Pdg_esercizioBulk.STATO_ESAMINATO_CDR)) {
					Unita_organizzativaBulk uo = new Unita_organizzativaBulk(CNRUserContext.getCd_unita_organizzativa(userContext));
					uo = (Unita_organizzativaBulk) getHome(userContext,uo).findByPrimaryKey(uo);
					Unita_organizzativa_enteBulk uoEnte = (Unita_organizzativa_enteBulk) getHome( userContext, Unita_organizzativa_enteBulk.class ).findAll().get(0);
					if (!uoEnte.equalsByPrimaryKey(uo))
						throw new ApplicationException( "Lo stato pu� essere aggiornato unicamente dalla UO "+uoEnte.getCd_unita_organizzativa());
				}
	
				// solo il direttore pu� progredire lo stato in uno dei seguenti
				if (next.equals(Pdg_esercizioBulk.STATO_CHIUSURA_CDR)||
					next.equals(Pdg_esercizioBulk.STATO_APERTURA_GESTIONALE_CDR)||
					next.equals(Pdg_esercizioBulk.STATO_CHIUSURA_GESTIONALE_CDR)) {
					if (!UtenteBulk.isAbilitatoApprovazioneBilancio(userContext)) {
						throw new ApplicationException( "Lo stato pu� essere aggiornato unicamente dal Direttore dell'Istituto");
					}
				}

				lockBulk(userContext, pdg_esercizio);

				// AGGIORNAMENTI O CONTROLLI CON LOCK
				
				// se si cambia lo stato a STATO_ESAMINATO_CDR aggiorniamo lo stato dei moduli a STATO_AD 
				if (next.equals(Pdg_esercizioBulk.STATO_ESAMINATO_CDR)) {
					lockPdgModulo(userContext, pdg_esercizio);

					Parametri_cnrBulk pcnr = new Parametri_cnrBulk(pdg_esercizio.getEsercizio());
					pcnr = (Parametri_cnrBulk) getHome(userContext,Parametri_cnrBulk.class).findByPrimaryKey(pcnr);
					
					Unita_organizzativaBulk uo = new Unita_organizzativaBulk(CNRUserContext.getCd_unita_organizzativa(userContext));
					uo = (Unita_organizzativaBulk) getHome(userContext,uo).findByPrimaryKey(uo);
					
					/*
					 * Il passaggio allo stato ESAMINATO_CDR pu� avvenire solo se l'Ente ha approvato definitivamente
					 * il Piano di Gestione.
					 * Il controllo non deve essere effettuato per i CDS della SAC per i quali la contrattazione 
					 * viene automaticamente alimentata
					 */
					/*
					if (!pcnr.getFl_approvato_definitivo() &&
					    !pdg_esercizio.getCdr().getUnita_padre().getCd_tipo_unita().equalsIgnoreCase( it.cnr.contab.config00.sto.bulk.Tipo_unita_organizzativaHome.TIPO_UO_SAC ))
						throw new ApplicationException( "Lo stato non pu� essere aggiornato poich� il PDGP non risulta definitivamente approvato.");
					*/
					// controlliamo che le righe di pdg_modulo siano tutte CC
					List listaModuli = findPdgModulo(userContext, pdg_esercizio, Pdg_moduloBulk.STATO_CC );
					if (!listaModuli.isEmpty())
						throw new ApplicationException( "Lo stato non pu� essere aggiornato poich� non tutte le righe del PdGP hanno stato "+Pdg_moduloBulk.STATO_CC);
										
					controllaImportiFontiEsterneApprovate(userContext, pdg_esercizio);
					aggiungiModuliContrattazione(userContext, pdg_esercizio);
					
					// aggiorniamo le righe di pdg_modulo in AD
					BulkHome home = getHome(userContext,Pdg_moduloBulk.class);
					SQLBuilder sql = home.createSQLBuilder();
					sql.addSQLClause("AND","CD_CENTRO_RESPONSABILITA",sql.EQUALS,pdg_esercizio.getCd_centro_responsabilita());
					sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS,pdg_esercizio.getEsercizio());
					java.util.List moduli_list = getHome(userContext,Pdg_moduloBulk.class).fetchAll(sql);
					for (java.util.Iterator i = moduli_list.iterator();i.hasNext();) {
						Pdg_moduloBulk mod = (Pdg_moduloBulk)i.next();
						mod.setStato(Pdg_moduloBulk.STATO_AD);
						mod.setUser(userContext.getUser());
						updateBulk(userContext,mod);
					}
				}
				
				// se si cambia lo stato a STATO_PRECHIUSURA_CDR verifichiamo la
				// quadratura con il piano di riparto
				if (next.equals(Pdg_esercizioBulk.STATO_PRECHIUSURA_CDR)) {
					lockPdgModulo(userContext, pdg_esercizio);

					// controlliamo che le righe di pdg_modulo siano tutte CC
					List listaModuli = findPdgModulo(userContext, pdg_esercizio, Pdg_moduloBulk.STATO_CC );
					if (!listaModuli.isEmpty())
						throw new ApplicationException( "Lo stato non pu� essere aggiornato poich� non tutte le righe del PdGP hanno stato "+Pdg_moduloBulk.STATO_CC);
										
					controllaPianoRiparto(userContext, pdg_esercizio, false);

				}

				// se si cambia lo stato a STATO_CHIUSURA_CDR verifichiamo la
				// quadratura con il piano di riparto
				if (next.equals(Pdg_esercizioBulk.STATO_CHIUSURA_CDR)) {
					lockPdgModulo(userContext, pdg_esercizio);

					// controlliamo che le righe di pdg_modulo siano tutte CC
					List listaModuli = findPdgModulo(userContext, pdg_esercizio, Pdg_moduloBulk.STATO_CC );
					if (!listaModuli.isEmpty())
						throw new ApplicationException( "Lo stato non pu� essere aggiornato poich� non tutte le righe del PdGP hanno stato "+Pdg_moduloBulk.STATO_CC);
										
					controllaPianoRiparto(userContext, pdg_esercizio, false);

				}

				// se si cambia lo stato a STATO_IN_ESAME_CDR inseriamo
				// i dati somma in PDG_CONTRATTAZIONE_SPESE
				if (next.equals(Pdg_esercizioBulk.STATO_IN_ESAME_CDR)) {
					lockPdgModulo(userContext, pdg_esercizio);
					
					insPdgContrattazione(userContext, pdg_esercizio);

					if (pdg_esercizio.getCdr().getUnita_padre().getCd_tipo_unita().equalsIgnoreCase( it.cnr.contab.config00.sto.bulk.Tipo_unita_organizzativaHome.TIPO_UO_SAC ))
						quadraImportiPdgContrattazione(userContext, pdg_esercizio);
				}

				// se si cambia lo stato a STATO_APPROVAZIONE_CDR verifichiamo la
				// quadratura con il piano di riparto ma in maniera definitiva
				if (next.equals(Pdg_esercizioBulk.STATO_APPROVAZIONE_CDR)) {
					//r.p. da testare
					verificaPresenzaModuli(userContext,pdg_esercizio);
					lockPdgModulo(userContext, pdg_esercizio);
										
					// controlliamo che i moduli in pdg_modulo abbiano stato approvato
					List listaModuliNA = findPdgModuloAttivitaNonApprovati(userContext, pdg_esercizio );
					if (!listaModuliNA.isEmpty())
						throw new ApplicationException( "Lo stato pu� essere aggiornato poich� esistono righe del PdGP con moduli di attivit� con stato NON APPROVATO");

					// controlliamo che le righe di pdg_modulo siano tutte AP
					List listaModuli = findPdgModulo(userContext, pdg_esercizio, Pdg_moduloBulk.STATO_AP );
					if (!listaModuli.isEmpty())
						throw new ApplicationException( "Lo stato pu� essere aggiornato poich� non tutte le righe del PdGP hanno stato "+Pdg_moduloBulk.STATO_AP);

					controllaStatoPianoRiparto(userContext, pdg_esercizio);
					
					controllaPianoRiparto(userContext, pdg_esercizio, true);
					
					// disabilitato poich� dovrebbe farlo una procedura di stani
					//aggiornaSpeseAccentrate(userContext, pdg_esercizio);

				}

				// se si cambia lo stato a STATO_APERTURA_GESTIONALE_CDR aggiorniamo lo stato dei moduli a STATO_AG 
				if (next.equals(Pdg_esercizioBulk.STATO_APERTURA_GESTIONALE_CDR)) {
					lockPdgModulo(userContext, pdg_esercizio);

					// controlliamo che le righe di pdg_modulo siano tutte AP
					List listaModuli = findPdgModulo(userContext, pdg_esercizio, Pdg_moduloBulk.STATO_AP );
					if (!listaModuli.isEmpty())
						throw new ApplicationException( "Lo stato non pu� essere aggiornato poich� non tutte le righe del PdGP hanno stato "+Pdg_moduloBulk.STATO_AP);
										
					// aggiorniamo le righe di pdg_modulo in AG
					BulkHome home = getHome(userContext,Pdg_moduloBulk.class);
					SQLBuilder sql = home.createSQLBuilder();
					sql.addSQLClause("AND","CD_CENTRO_RESPONSABILITA",sql.EQUALS,pdg_esercizio.getCd_centro_responsabilita());
					sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS,pdg_esercizio.getEsercizio());
					java.util.List moduli_list = getHome(userContext,Pdg_moduloBulk.class).fetchAll(sql);
					for (java.util.Iterator i = moduli_list.iterator();i.hasNext();) {
						Pdg_moduloBulk mod = (Pdg_moduloBulk)i.next();
						mod.setStato(Pdg_moduloBulk.STATO_AG);
						mod.setUser(userContext.getUser());
						updateBulk(userContext,mod);
					}
				}

				// se si cambia lo stato a STATO_CHIUSURA_GESTIONALE_CDR aggiorniamo lo stato dei moduli a STATO_AG
				// e lanciamo le procedure db CNRCTB053.ribaltaSuAreaPDG_da_gest e CNRCTB055.predispBilFinCNR_da_gest 
				if (next.equals(Pdg_esercizioBulk.STATO_CHIUSURA_GESTIONALE_CDR)) {
					lockPdgModulo(userContext, pdg_esercizio);

					// controlliamo che le righe di pdg_modulo siano tutte CG
					List listaModuli = findPdgModulo(userContext, pdg_esercizio, Pdg_moduloBulk.STATO_CG);
					if (!listaModuli.isEmpty())
						throw new ApplicationException( "Lo stato non pu� essere aggiornato poich� non tutte le righe del PdGP hanno stato "+Pdg_moduloBulk.STATO_CG);
										
					// aggiorniamo le righe di pdg_modulo in CG
					BulkHome home = getHome(userContext,Pdg_moduloBulk.class);
					SQLBuilder sql = home.createSQLBuilder();
					sql.addSQLClause("AND","CD_CENTRO_RESPONSABILITA",sql.EQUALS,pdg_esercizio.getCd_centro_responsabilita());
					sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS,pdg_esercizio.getEsercizio());
					java.util.List moduli_list = getHome(userContext,Pdg_moduloBulk.class).fetchAll(sql);
					for (java.util.Iterator i = moduli_list.iterator();i.hasNext();) {
						Pdg_moduloBulk mod = (Pdg_moduloBulk)i.next();
						mod.setStato(Pdg_moduloBulk.STATO_CG);
						mod.setUser(userContext.getUser());
						updateBulk(userContext,mod);
					}

					ribaltaCDPSuPdg(userContext, pdg_esercizio);

					ribaltaCostiPdGArea(userContext, pdg_esercizio);
					
					predisponeBilancioPreventivoCNR(userContext, pdg_esercizio);

					creaSaldiCdRLineaVoceDaGest(userContext, pdg_esercizio);
				}

				pdg_esercizio.setStato(next);
				updateBulk( userContext,pdg_esercizio );
			}
			return pdg_esercizio;
		} catch (Throwable e) {
			throw handleException(pdg_esercizio,e);
		}
	}
	
	private void aggiornaSpeseAccentrate(UserContext userContext, Pdg_esercizioBulk pdg) throws ComponentException {
		try {
			
			SQLBuilder sql = sqlSpeseAccentrate(userContext, pdg);
			
			String cdrAcc = null;
			Integer idCla = null;
			BigDecimal impEst = Utility.ZERO;
			BigDecimal impInt = Utility.ZERO;
			try {
				java.sql.ResultSet rs = null;
				PreparedStatement ps = null;
				try {
					ps = sql.prepareStatement(getConnection(userContext));
					try {
						rs = ps.executeQuery();
						while (rs.next()) {
							idCla = new Integer(rs.getInt(1));
							if (rs.getString(2)!= null)
								cdrAcc = rs.getString(2);
							if (rs.getBigDecimal(3)!= null)
								impEst = impEst.add(rs.getBigDecimal(3));
							if (rs.getBigDecimal(4)!= null)
								impInt = impInt.add(rs.getBigDecimal(4));
								
							aggiornaRigaCdrAccentratore(userContext, pdg, idCla, cdrAcc, impEst, impInt);
						}
					} catch (java.sql.SQLException e) {
						throw handleSQLException(e);
					} finally {
						if (rs != null) try{rs.close();}catch( java.sql.SQLException e ){};
					}
				} finally {
					if (ps != null) try{ps.close();}catch( java.sql.SQLException e ){};
				}
			} catch (java.sql.SQLException ex) {
				throw handleException(ex);
			}		
			
		} catch (ComponentException e) {
			throw handleException(e);
		}
		
	}

	private SQLBuilder sqlSpeseAccentrate(UserContext userContext, Pdg_esercizioBulk pdg) throws ComponentException {
		try {
			Pdg_modulo_speseHome home = (Pdg_modulo_speseHome)getHome(userContext,Pdg_modulo_speseBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.resetColumns();
			sql.addColumn("PDG_MODULO_SPESE.ID_CLASSIFICAZIONE");
			sql.addColumn("CDR_ACCENTRATORE");
			sql.addColumn("SUM(IM_SPESE_GEST_ACCENTRATA_EST)");
			sql.addColumn("SUM(IM_SPESE_GEST_ACCENTRATA_INT)");
			sql.addTableToHeader("CLASSIFICAZIONE_VOCI");
			sql.addSQLJoin("PDG_MODULO_SPESE.ID_CLASSIFICAZIONE","CLASSIFICAZIONE_VOCI.ID_CLASSIFICAZIONE");
			sql.addSQLClause("AND","PDG_MODULO_SPESE.ESERCIZIO",SQLBuilder.EQUALS,pdg.getEsercizio());
			//sql.addSQLClause("AND","PDG_MODULO_SPESE.CD_CENTRO_RESPONSABILITA",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
			sql.addSQLClause("AND","CDR_ACCENTRATORE",SQLBuilder.ISNOTNULL,null);
			sql.addSQLGroupBy("PDG_MODULO_SPESE.ID_CLASSIFICAZIONE, CDR_ACCENTRATORE")	;
			
			return sql;
			
		} catch (ComponentException e) {
			throw handleException(e);
		}
		
	}

	private void aggiornaRigaCdrAccentratore(UserContext userContext, Pdg_esercizioBulk pdg, Integer idCla, String cdrAcc, BigDecimal impEst, BigDecimal impInt) throws ComponentException {

		try {
			BulkHome home = getHome(userContext,Pdg_moduloBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.addSQLClause("AND","CD_CENTRO_RESPONSABILITA",sql.EQUALS,cdrAcc);
			sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS,pdg.getEsercizio());
			sql.setOrderBy("PG_PROGETTO", it.cnr.jada.util.OrderConstants.ORDER_ASC);
			
			List lista_mod = home.fetchAll(sql);
	
			if (lista_mod.isEmpty())
				throw new ApplicationException("Record non trovato sul PdGP per il CdR Accentratore "+cdrAcc+".\nImpossibile aggiornare gli importi di Previsione di Impegno Spese a Gestione accentrata.");
			
			Pdg_moduloBulk pdgm = (Pdg_moduloBulk) lista_mod.get(0); 
	
			home = (Pdg_modulo_speseHome)getHome(userContext,Pdg_modulo_speseBulk.class);
			sql = home.createSQLBuilder();
			sql.addSQLClause("AND","ESERCIZIO",SQLBuilder.EQUALS,pdg.getEsercizio());
			sql.addSQLClause("AND","CD_CENTRO_RESPONSABILITA",SQLBuilder.EQUALS,cdrAcc);
			//sql.addSQLClause("AND","PG_PROGETTO",SQLBuilder.EQUALS,pdgm.getPg_progetto());
			sql.addSQLClause("AND","ID_CLASSIFICAZIONE",SQLBuilder.EQUALS,pdgm.getPg_progetto());
			sql.setOrderBy("PG_PROGETTO", it.cnr.jada.util.OrderConstants.ORDER_ASC);
		
			List lista_spese = home.fetchAll(sql);
			if (!lista_spese.isEmpty()) {
				Pdg_modulo_speseBulk spese = (Pdg_modulo_speseBulk) lista_spese.get(0);
				spese.setIm_spese_gest_accentrata_est(impEst);
				spese.setIm_spese_gest_accentrata_int(impInt);

				updateBulk(userContext, spese);
			}
			else {
				Pdg_modulo_speseBulk spese = new Pdg_modulo_speseBulk(pdg.getEsercizio(), cdrAcc, pdgm.getPg_progetto(), idCla, null);

				CdrBulk cdr = new CdrBulk(spese.getPdg_modulo_costi().getPdg_modulo().getCd_centro_responsabilita());
				cdr.getCd_unita_organizzativa();
				cdr = (CdrBulk)getHome(userContext, cdr).findByPrimaryKey(cdr);
				getHome(userContext,cdr.getUnita_padre()).findByPrimaryKey(cdr.getUnita_padre());
				spese.getPdg_modulo_costi().getPdg_modulo().setCdr(cdr);
				spese.setArea(spese.getPdg_modulo_costi().getPdg_modulo().getCdr().getUnita_padre().getUnita_padre());
				spese.setCd_cds_area(spese.getPdg_modulo_costi().getPdg_modulo().getCdr().getUnita_padre().getUnita_padre().getCd_unita_organizzativa());
				spese.setIm_spese_gest_accentrata_est(impEst);
				spese.setIm_spese_gest_accentrata_int(impInt);
				spese.setIm_spese_gest_decentrata_est(Utility.ZERO);
				spese.setIm_spese_gest_decentrata_int(Utility.ZERO);
				spese.setIm_spese_a2(Utility.ZERO);
				spese.setIm_spese_a3(Utility.ZERO);
				
				Pdg_modulo_costiBulk costi = new Pdg_modulo_costiBulk(pdg.getEsercizio(), cdrAcc, pdgm.getPg_progetto());
				Pdg_modulo_costiBulk new_costi = (Pdg_modulo_costiBulk)getHome(userContext, costi).findByPrimaryKey(costi);
				if (new_costi==null) {
					insertBulk(userContext, costi);
				}

				insertBulk(userContext, spese);
			}

		} catch (PersistencyException e) {
			throw handleException(e);
		}
	}

	private void lockPdgModulo(UserContext userContext, Pdg_esercizioBulk pdg_esercizio) throws ComponentException, PersistencyException, BusyResourceException, OutdatedResourceException {

		try {
			BulkHome home = getHome(userContext,Pdg_moduloBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.addSQLClause("AND","CD_CENTRO_RESPONSABILITA",sql.EQUALS,pdg_esercizio.getCd_centro_responsabilita());
			sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS,pdg_esercizio.getEsercizio());
			sql.setForUpdate(true);
			PreparedStatement stm = sql.prepareStatement(getConnection(userContext));
			try {
				java.sql.ResultSet rs = stm.executeQuery();
				while (rs.next());
			} finally {
				try{stm.close();}catch( java.sql.SQLException e ){};
			}
		} catch(java.sql.SQLException e) {
			throw new BusyResourceException();
		}
	}

	private java.util.List findPdgModulo(UserContext userContext, Pdg_esercizioBulk pdg_esercizio, String notStato ) throws ComponentException, PersistencyException, BusyResourceException, OutdatedResourceException {

		BulkHome home = getHome(userContext,Pdg_moduloBulk.class);
		SQLBuilder sql = home.createSQLBuilder();
		sql.addSQLClause("AND","CD_CENTRO_RESPONSABILITA",sql.EQUALS,pdg_esercizio.getCd_centro_responsabilita());
		sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS,pdg_esercizio.getEsercizio());
		sql.addSQLClause("AND","STATO",sql.NOT_EQUALS,notStato);
		java.util.List moduli_list = getHome(userContext,Pdg_moduloBulk.class).fetchAll(sql);
		return moduli_list;
	}

	/**
	 * Controlla che esistono testate in pdg_modulo per il CdR di pdg_esercizio, il
	 * cui modulo di attivit� non � stato ancora approvato (STATO!=A)
	 * 
	 * @param userContext
	 * @param pdg_esercizio
	 * @param notStato
	 * @return
	 * @throws ComponentException
	 * @throws PersistencyException
	 * @throws BusyResourceException
	 * @throws OutdatedResourceException
	 */
	private java.util.List findPdgModuloAttivitaNonApprovati(UserContext userContext, Pdg_esercizioBulk pdg_esercizio) throws ComponentException, PersistencyException, BusyResourceException, OutdatedResourceException {

		BulkHome home = getHome(userContext,Pdg_moduloBulk.class);
		SQLBuilder sql = home.createSQLBuilder();
		sql.addTableToHeader("V_PROGETTO_PADRE");
		sql.addSQLJoin("PDG_MODULO.ESERCIZIO", "V_PROGETTO_PADRE.ESERCIZIO");
		sql.addSQLJoin("PDG_MODULO.PG_PROGETTO", "V_PROGETTO_PADRE.PG_PROGETTO");
		sql.addSQLClause("AND","V_PROGETTO_PADRE.TIPO_FASE",sql.EQUALS,ProgettoBulk.TIPO_FASE_PREVISIONE);
		sql.addSQLClause("AND","PDG_MODULO.CD_CENTRO_RESPONSABILITA",sql.EQUALS,pdg_esercizio.getCd_centro_responsabilita());
		sql.addSQLClause("AND","PDG_MODULO.ESERCIZIO",sql.EQUALS,pdg_esercizio.getEsercizio());
		sql.addSQLClause("AND","V_PROGETTO_PADRE.STATO",sql.NOT_EQUALS,ProgettoBulk.TIPO_STATO_APPROVATO);
		java.util.List moduli_list = getHome(userContext,Pdg_moduloBulk.class).fetchAll(sql);
		return moduli_list;
	}

	public Pdg_esercizioBulk riportaStatoPrecedenteConBulk( UserContext userContext,Pdg_esercizioBulk pdg_esercizio) throws it.cnr.jada.comp.ComponentException 
	{
		try 
		{
			String prev =(String)Pdg_esercizioBulk.getPrecedenteStato().get( pdg_esercizio.getStato());

			if (prev!=null) {

				Pdg_esercizioHome pdg_esercizioHome = (Pdg_esercizioHome) getHome(userContext,pdg_esercizio);
	
				// solo l'UO 999.000 pu� regredire lo stato in STATO_PRECHIUSURA_CDR
				if (prev.equals(Pdg_esercizioBulk.STATO_PRECHIUSURA_CDR)) {
					Unita_organizzativaBulk uo = new Unita_organizzativaBulk(CNRUserContext.getCd_unita_organizzativa(userContext));
					uo = (Unita_organizzativaBulk) getHome(userContext,uo).findByPrimaryKey(uo);
					Unita_organizzativa_enteBulk uoEnte = (Unita_organizzativa_enteBulk) getHome( userContext, Unita_organizzativa_enteBulk.class ).findAll().get(0);
					if (!uoEnte.equalsByPrimaryKey(uo))
						throw new ApplicationException( "Lo stato pu� essere aggiornato unicamente dalla UO "+uoEnte.getCd_unita_organizzativa());
				}
	
				// solo il direttore pu� regredire lo stato in STATO_APERTURA_CDR
				if (prev.equals(Pdg_esercizioBulk.STATO_APERTURA_CDR)) {
					if (!UtenteBulk.isAbilitatoApprovazioneBilancio(userContext)) {
						throw new ApplicationException( "Lo stato pu� essere aggiornato unicamente dal Direttore dell'Istituto");
					}
				}

				pdg_esercizio.setStato(prev);
				updateBulk( userContext,pdg_esercizio );
			}
			return pdg_esercizio;
			
		} catch (Throwable e) {
			throw handleException(pdg_esercizio,e);
		}
	}

	public void eliminaConBulk (UserContext userContext,OggettoBulk bulk) throws it.cnr.jada.comp.ComponentException
	{
		throw handleException( new ApplicationException( "Non e' possibile cancellare il record." ));
	}

	public Query select(UserContext userContext,it.cnr.jada.persistency.sql.CompoundFindClause clauses,OggettoBulk bulk) throws ComponentException, it.cnr.jada.persistency.PersistencyException
	{
	
		//SQLBuilder sql = (SQLBuilder) super.select(userContext, clauses, bulk);
		SQLBuilder sql = ((CdrHome)getHome(userContext, CdrBulk.class)).createSQLBuilderEsteso();
		CdrBulk cdr = (CdrBulk) bulk;
		sql.addClause("AND", "cd_centro_responsabilita", SQLBuilder.EQUALS, it.cnr.contab.utenze00.bp.CNRUserContext.getCd_cdr(userContext));
		return sql;
	
	}
	
	/**
	 * Ricerca il CdR legato all'UO di scrivania
	 *
	 * @param userContext	lo UserContext che ha generato la richiesta
	 * @return CdR di tipo UO CDS legata alla UO di scrivania
	 */
	public CdrBulk findCdrUo(UserContext userContext) throws ComponentException {
		try
		{
			// uso createSQLBuilderEsteso perch� il metodo createSQLBuilder filtra alcune cose
			SQLBuilder sql = ((CdrHome)getHome(userContext, CdrBulk.class)).createSQLBuilderEsteso();
			sql.addTableToHeader("UNITA_ORGANIZZATIVA");
			sql.addSQLJoin("CDR.CD_UNITA_ORGANIZZATIVA", "UNITA_ORGANIZZATIVA.CD_UNITA_ORGANIZZATIVA");
			sql.addSQLClause("AND", "CDR.cd_unita_organizzativa", sql.EQUALS, CNRUserContext.getCd_unita_organizzativa(userContext));
			sql.addSQLClause("AND", "CDR.livello", sql.EQUALS, new Integer(1));
			sql.addSQLClause("AND", "UNITA_ORGANIZZATIVA.CD_TIPO_UNITA", sql.EQUALS, Tipo_unita_organizzativaHome.TIPO_UO_ENTE);

			List result = getHome( userContext, CdrBulk.class ).fetchAll( sql );
			if ( result.size() > 1 )
				throw new ComponentException("Impossibile determinare il CdR legato alla UO in scrivania.");

			return (CdrBulk) result.get(0);	
		}
		catch( Exception e )
		{
			throw handleException( e );
		}
	}

	private void controllaPianoRiparto(UserContext userContext, Pdg_esercizioBulk pdg, boolean bDefinitivo) throws ComponentException, PersistencyException, BusyResourceException, OutdatedResourceException {
		
		try {
			V_pdg_piano_ripartoHome home = (V_pdg_piano_ripartoHome)getHome(userContext,V_pdg_piano_ripartoBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.addSQLClause("AND","ESERCIZIO",SQLBuilder.EQUALS,CNRUserContext.getEsercizio(userContext));
			sql.addSQLClause("AND","CD_CENTRO_RESPONSABILITA",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
			SQLBroker broker = home.createBroker(sql);
			
			while(broker.next()) {
				V_pdg_piano_ripartoBulk pr = (V_pdg_piano_ripartoBulk)broker.fetch(V_pdg_piano_ripartoBulk.class);
				BigDecimal impRipartito = pr.getImporto_ripartito();
				BigDecimal impAssegnato = pr.getImporto_assegnato();

				if (bDefinitivo) {
					// se la chiusura � definitiva il valore nullo
					// dell'importo assegnato lo assililiamo a zero
					if (impAssegnato==null)
						impAssegnato=new BigDecimal(0);

					if (impRipartito.compareTo(impAssegnato)!=0)
						throw new ApplicationException("L'importo ripartito � diverso dall'importo assegnato nei piani di riparto.\nImpossibile procedere alla chiusura del dettaglio del PdGP.");
				}
				else {
					// se la chiusara non � definitiva per il valore nullo
					// dell'importo assegnato  il controllo va saltato
					if (impAssegnato!=null && impRipartito.compareTo(impAssegnato)!=0)
						throw new ApplicationException("L'importo ripartito � diverso dall'importo assegnato nei piani di riparto.\nImpossibile procedere alla chiusura del dettaglio del PdGP.");
				}
			}

		} catch (PersistencyException e) {
			throw handleException(e);
		} catch (ComponentException e) {
			throw handleException(e);
		}
	}
	/**
	 * Solleva una eccezione se almeno una riga del piano di riparto risulta in provvisorio per
	 * Pdg_esercizioBulk->esercizio e Pdg_esercizioBulk->cd_centro_responsabilita
	 * 
	 * @param userContext
	 * @param pdg
	 * @throws ComponentException
	 * @throws PersistencyException
	 * @throws BusyResourceException
	 * @throws OutdatedResourceException
	 */

	private void controllaStatoPianoRiparto(UserContext userContext, Pdg_esercizioBulk pdg) throws ComponentException, PersistencyException, BusyResourceException, OutdatedResourceException {
		
		try {
			Pdg_piano_ripartoHome home = (Pdg_piano_ripartoHome)getHome(userContext,Pdg_piano_ripartoBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.addSQLClause("AND","ESERCIZIO",SQLBuilder.EQUALS,CNRUserContext.getEsercizio(userContext));
			sql.addSQLClause("AND","CD_CENTRO_RESPONSABILITA",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
			sql.addSQLClause("AND","STATO",SQLBuilder.EQUALS,Pdg_piano_ripartoHome.STATO_PROVVISORIO);
			SQLBroker broker = home.createBroker(sql);

			List result = getHome(userContext, Pdg_piano_ripartoBulk.class).fetchAll(sql);
			if (!result.isEmpty())
				throw new ApplicationException("Il piano di riparto risulta ancora in stato provvisorio. Impossibile procedere.");

		} catch (PersistencyException e) {
			throw handleException(e);
		} catch (ComponentException e) {
			throw handleException(e);
		}
	}

	/**
	 *	Inserisce le righe in pdg_contrattazione_spese con i totali dei campi
	 *	IM_SPESE_GEST_DECENTRATA_INT e IM_SPESE_GEST_DECENTRATA_EST delle righe di pdg_modulo_spese
	 *	
	 * @param userContext
	 * @param pdg
	 * @throws ComponentException
	 */
	private void insPdgContrattazione(UserContext userContext, Pdg_esercizioBulk pdg) throws ComponentException  {

		try {
			
			Parametri_cnrBulk pcnr = new Parametri_cnrBulk(CNRUserContext.getEsercizio(userContext));
			pcnr = (Parametri_cnrBulk) getHome(userContext,Parametri_cnrBulk.class).findByPrimaryKey(pcnr);
			Integer livContrSpe = pcnr.getLivello_contratt_pdg_spe();

			SQLBuilder sql = sqlPdgContrattazione(userContext, pdg, livContrSpe);
			
			Integer pgMod = null;
			Integer idCla = null;
			String cds = null;
			BigDecimal impEst = Utility.ZERO;
			BigDecimal impInt = Utility.ZERO;

			try {
				java.sql.ResultSet rs = null;
				PreparedStatement ps = null;
				try {
					ps = sql.prepareStatement(getConnection(userContext));
					try {
						rs = ps.executeQuery();
						while (rs.next()) {
							if (rs.getString(1)!= null)
								pgMod = new Integer(rs.getInt(1));
							if (rs.getString(2)!= null)
								idCla = new Integer(rs.getInt(2));
							if (rs.getString(3)!= null)
								cds = rs.getString(3);
							if (rs.getBigDecimal(4)!= null)
								impEst = rs.getBigDecimal(4);
							if (rs.getBigDecimal(5)!= null)
								impInt = rs.getBigDecimal(5);
								
							// Inserisce le righe con i totali prelevati da pdg_modulo_spese
							Pdg_approvato_dip_areaBulk appDipArea = inserisciApprovatoDipArea(userContext, pdg, livContrSpe, pgMod, idCla, impEst, impInt, cds);
							inserisciRigaPdgContrattazione(userContext, pdg, appDipArea, livContrSpe, pgMod, idCla, impEst, impInt, cds);
						}
					} catch (java.sql.SQLException e) {
						throw handleSQLException(e);
					} finally {
						if (rs != null) try{rs.close();}catch( java.sql.SQLException e ){};
					}
				} finally {
					if (ps != null) try{ps.close();}catch( java.sql.SQLException e ){};
				}
			} catch (java.sql.SQLException ex) {
				throw handleException(ex);
			}			

			// Inserisce le righe con totali nulli delle varie classificazioni che 
			// non sono state inserite in pdg_modulo_spese dagli utenti in fase preliminare 
			inserisciRigaPdgContrattazioneAltre(userContext, pdg, livContrSpe);

		} catch (ComponentException e) {
			throw handleException(e);
		} catch (PersistencyException e) {
			throw handleException(e);
		}
	}

	private SQLBuilder sqlPdgContrattazione(UserContext userContext, Pdg_esercizioBulk pdg, Integer livContrSpe) throws ComponentException  {

		Pdg_modulo_speseHome home = (Pdg_modulo_speseHome)getHome(userContext,Pdg_modulo_speseBulk.class);
		SQLBuilder sql = home.createSQLBuilder();
		sql.resetColumns();
		sql.addColumn("PDG_MODULO_SPESE.PG_PROGETTO");
		if (livContrSpe.compareTo(new Integer(0))!=0) 
			sql.addColumn("V_CLASSIFICAZIONE_VOCI_ALL.ID_LIV"+livContrSpe);
		else
			sql.addColumn("NULL");
		sql.addColumn("PDG_MODULO_SPESE.CD_CDS_AREA");
		sql.addColumn("SUM(IM_SPESE_GEST_DECENTRATA_EST)");
		sql.addColumn("SUM(IM_SPESE_GEST_DECENTRATA_INT)");
		sql.addTableToHeader("V_CLASSIFICAZIONE_VOCI_ALL");
		sql.addSQLJoin("PDG_MODULO_SPESE.ID_CLASSIFICAZIONE","V_CLASSIFICAZIONE_VOCI_ALL.ID_CLASSIFICAZIONE");
		sql.addSQLClause("AND","PDG_MODULO_SPESE.ESERCIZIO",SQLBuilder.EQUALS,pdg.getEsercizio());
		sql.addSQLClause("AND","PDG_MODULO_SPESE.CD_CENTRO_RESPONSABILITA",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
		if (livContrSpe.compareTo(new Integer(0))!=0) 
			sql.addSQLGroupBy("PDG_MODULO_SPESE.PG_PROGETTO, V_CLASSIFICAZIONE_VOCI_ALL.ID_LIV"+livContrSpe+", PDG_MODULO_SPESE.CD_CDS_AREA");
		else	
			sql.addSQLGroupBy("PDG_MODULO_SPESE.PG_PROGETTO, PDG_MODULO_SPESE.CD_CDS_AREA");
		return sql;
	}

	private it.cnr.contab.progettiric00.ejb.ProgettoRicercaModuloComponentSession createProgettoRicercaModuloComponentSession() throws ComponentException 
	{
		try
		{
			return (ProgettoRicercaModuloComponentSession)EJBCommonServices.createEJB("CNRPROGETTIRIC00_EJB_ProgettoRicercaModuloComponentSession");
		}
		catch ( Exception e )
		{
			throw handleException( e )	;
		}	
	}

	private Pdg_approvato_dip_areaBulk inserisciApprovatoDipArea(UserContext userContext, Pdg_esercizioBulk pdg, Integer livContrSpe, Integer pgMod, Integer idCla, BigDecimal impEst, BigDecimal impInt, String cds) throws ComponentException {
		try {
			ProgettoHome progettohome = (ProgettoHome)getHome(userContext, ProgettoBulk.class); 
			ProgettoBulk progetto = (ProgettoBulk)progettohome.findByPrimaryKey(new ProgettoBulk(pdg.getEsercizio(),pgMod,ProgettoBulk.TIPO_FASE_PREVISIONE));
			
			if (progetto==null)
				throw new ApplicationException("Errore nella ricerca del Modulo con id " + pgMod);
				
			DipartimentoBulk dipartimento = createProgettoRicercaModuloComponentSession().getDipartimentoModulo(userContext, progetto);

			if ( dipartimento==null)
				throw new ApplicationException( "Non � stato possibile individuare il dipartimento associato al modulo " + progetto.getCd_progetto());

			CdsHome areahome = (CdsHome)getHome(userContext, CdsBulk.class);
			CdsBulk area = (CdsBulk)areahome.findByPrimaryKey(new CdsBulk(cds));

			if (area==null)
				throw new ApplicationException("Errore nella ricerca del cds con codice " + cds);

			//Inserisce le combinazioni Dipartimento/Aree prelevati da pdg_modulo_spese
			BulkHome appDipAreahome = getHome(userContext, Pdg_approvato_dip_areaBulk.class);
			SQLBuilder appDipAreaSql = appDipAreahome.createSQLBuilder();
			appDipAreaSql.addClause("AND","esercizio",SQLBuilder.EQUALS,pdg.getEsercizio());
			appDipAreaSql.addClause("AND","cd_dipartimento",SQLBuilder.EQUALS,dipartimento.getCd_dipartimento());

			if (area.getCd_tipo_unita().equals(Tipo_unita_organizzativaHome.TIPO_UO_AREA))
			    appDipAreaSql.addClause("AND","cd_cds_area",SQLBuilder.EQUALS,area.getCd_unita_organizzativa());
			else
				appDipAreaSql.addClause("AND","cd_cds_area",SQLBuilder.ISNULL,null);
			
			List appDipAreaLista = appDipAreahome.fetchAll(appDipAreaSql);
			
			Pdg_approvato_dip_areaBulk newAppDipArea=null;

			if (appDipAreaLista.isEmpty()) {
				if (area.getCd_tipo_unita().equals(Tipo_unita_organizzativaHome.TIPO_UO_AREA)) {
					BulkHome assDipAreahome = getHome(userContext, Ass_dipartimento_areaBulk.class);
					SQLBuilder assDipAreaSql = assDipAreahome.createSQLBuilder();
					assDipAreaSql.addClause("AND","esercizio",SQLBuilder.EQUALS,pdg.getEsercizio());
					assDipAreaSql.addClause("AND","cd_dipartimento",SQLBuilder.EQUALS,dipartimento.getCd_dipartimento());
					assDipAreaSql.addClause("AND","cd_cds_area",SQLBuilder.EQUALS,area.getCd_unita_organizzativa());
					
					List assDipAreaLista = assDipAreahome.fetchAll(assDipAreaSql);
				
					if (assDipAreaLista.isEmpty())
						throw new ApplicationException("Combinazione Dipartimento (" + dipartimento.getCd_dipartimento() + ")/Area (" + area.getCd_unita_organizzativa() + ") non consentita.\nImpossibile proseguire.");
				}   				

				newAppDipArea = new Pdg_approvato_dip_areaBulk();
				
				if (area.getCd_tipo_unita().equals(Tipo_unita_organizzativaHome.TIPO_UO_AREA))
					newAppDipArea.setArea(area);
   				
				newAppDipArea.setDipartimento(dipartimento);
				newAppDipArea.setImporto_approvato(Utility.ZERO);
			
   				insertBulk(userContext, newAppDipArea);
			}
   			else
   				newAppDipArea = (Pdg_approvato_dip_areaBulk)appDipAreaLista.get(0);
			
   			return newAppDipArea;
		} catch (PersistencyException e) {
			throw new ComponentException(e);
		} catch (RemoteException e) {
			throw new ComponentException(e);
		}
	}

	private void quadraImportiPdgContrattazione(UserContext userContext, Pdg_esercizioBulk pdg) throws ComponentException {
		try {
			BulkHome home = getHome(userContext,Pdg_contrattazione_speseBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.resetColumns();
			sql.addColumn("DISTINCT ESERCIZIO_DIP, CD_DIPARTIMENTO, PG_DETTAGLIO_DIP");
			sql.addClause("AND","esercizio",SQLBuilder.EQUALS,pdg.getEsercizio());
			sql.addClause("AND","cd_centro_responsabilita",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
			sql.addClause("AND","cd_dipartimento",SQLBuilder.ISNOTNULL,null);

			Integer esDip = null;
			Integer pgDip = null;
			String cdDip = null;
			BigDecimal totImpInt = Utility.ZERO;

			try {
				java.sql.ResultSet rs = null;
				PreparedStatement ps = null;
				try {
					ps = sql.prepareStatement(getConnection(userContext));
					try {
						rs = ps.executeQuery();
						while (rs.next()) {
							totImpInt = Utility.ZERO;
							if (rs.getString(1)!= null)
								esDip = new Integer(rs.getInt(1));
							if (rs.getString(2)!= null)
								cdDip = rs.getString(2);
							if (rs.getString(3)!= null)
								pgDip = new Integer(rs.getInt(3));
								
							Pdg_approvato_dip_areaHome appDipAreaHome = (Pdg_approvato_dip_areaHome)getHome(userContext,Pdg_approvato_dip_areaBulk.class);
							Pdg_approvato_dip_areaBulk appDipArea = (Pdg_approvato_dip_areaBulk)appDipAreaHome.findByPrimaryKey(new Pdg_approvato_dip_areaBulk(esDip, cdDip, pgDip));
							for (Iterator dett = appDipAreaHome.findPdgContrattazioneSpeseDettagli(userContext,appDipArea).iterator();dett.hasNext();){
								Pdg_contrattazione_speseBulk contr = (Pdg_contrattazione_speseBulk)dett.next();
								contr.setAppr_tot_spese_decentr_est(contr.getTot_spese_decentr_est());
								contr.setAppr_tot_spese_decentr_int(contr.getTot_spese_decentr_int());
								contr.setToBeUpdated();
								totImpInt = totImpInt.add(Utility.nvl(contr.getTot_spese_decentr_int())); 
								updateBulk(userContext, contr);
						    }
							appDipArea.setImporto_approvato(Utility.nvl(totImpInt));
							appDipArea.setToBeUpdated();
							updateBulk(userContext, appDipArea);
						}
					} catch (java.sql.SQLException e) {
						throw new ComponentException(e);
					} catch (IntrospectionException e) {
						throw new ComponentException(e);
					} finally {
						if (rs != null) try{rs.close();}catch( java.sql.SQLException e ){};
					}
				} finally {
					if (ps != null) try{ps.close();}catch( java.sql.SQLException e ){};
				}
			} catch (java.sql.SQLException ex) {
				throw new ComponentException(ex);
			}			
		} catch (PersistencyException e) {
			throw new ComponentException(e);
		}
	}

	private void inserisciRigaPdgContrattazione(UserContext userContext, Pdg_esercizioBulk pdg, Pdg_approvato_dip_areaBulk appDipArea, Integer livContrSpe, Integer pgMod, Integer idCla, BigDecimal impEst, BigDecimal impInt, String cds) throws ComponentException {
		try {

			// Inserisce le righe con i totali prelevati da pdg_modulo_spese

			BulkHome home = getHome(userContext,Pdg_contrattazione_speseBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.addClause("AND","esercizio",SQLBuilder.EQUALS,pdg.getEsercizio());
			sql.addClause("AND","cd_centro_responsabilita",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
			sql.addClause("AND","pg_progetto",SQLBuilder.EQUALS,pgMod);

			if (livContrSpe.compareTo(new Integer(0))!=0) 
				sql.addClause("AND","id_classificazione",SQLBuilder.EQUALS,idCla);
			else
				sql.addClause("AND","id_classificazione",SQLBuilder.ISNULL,null);

			if (appDipArea!=null){
				sql.addClause("AND","esercizio_dip",SQLBuilder.EQUALS,appDipArea.getEsercizio());
				sql.addClause("AND","cd_dipartimento",SQLBuilder.EQUALS,appDipArea.getCd_dipartimento());
				if ( appDipArea.getArea() != null && appDipArea.getArea().getCd_unita_organizzativa()!=null)
					sql.addClause("AND", "cd_cds_area", SQLBuilder.EQUALS, appDipArea.getArea().getCd_unita_organizzativa());
				else
					sql.addClause("AND", "cd_cds_area", SQLBuilder.EQUALS, pdg.getCdr().getCd_cds());
			}
			
			List lista = home.fetchAll(sql);
			
			if (!lista.isEmpty())
			{
				Pdg_contrattazione_speseBulk contrSpese = (Pdg_contrattazione_speseBulk)lista.get(0);
				contrSpese.setTot_spese_decentr_int(impInt);
				contrSpese.setTot_spese_decentr_est(impEst);

				updateBulk(userContext, contrSpese);
			}
			else
			{
				Pdg_contrattazione_speseBulk contrSpese = new Pdg_contrattazione_speseBulk(pdg.getEsercizio(), pdg.getCd_centro_responsabilita(), pgMod, null);
				if (livContrSpe.compareTo(new Integer(0))!=0) {
					V_classificazione_vociBulk clav = new V_classificazione_vociBulk(idCla);
					contrSpese.setClassificazione(clav);
				}
				contrSpese.setPdg_dip_area(appDipArea);
				if (appDipArea.getArea()!=null)
					contrSpese.setArea(appDipArea.getArea());
				else
					contrSpese.setArea(new CdsBulk(pdg.getCdr().getCd_cds()));
				contrSpese.setTot_spese_decentr_int(impInt);
				contrSpese.setTot_spese_decentr_est(impEst);
				contrSpese.setAppr_tot_spese_decentr_est(Utility.ZERO);
				contrSpese.setAppr_tot_spese_decentr_int(Utility.ZERO);
				
				insertBulk(userContext, contrSpese);
			}

		} catch (PersistencyException e) {
			throw handleException(e);
		}
	}

	private void inserisciRigaPdgContrattazioneAltre(UserContext userContext, Pdg_esercizioBulk pdg, Integer livContrSpe) throws ComponentException {

		try {

			// Inserisce le righe con totali nulli delle varie classificazioni che 
			// non sono state inserite in pdg_modulo_spese dagli utenti in fase preliminare 

			BulkHome home = (Pdg_moduloHome)getHome(userContext,Pdg_moduloBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS,pdg.getEsercizio());
			sql.addSQLClause("AND","CD_CENTRO_RESPONSABILITA",sql.EQUALS,pdg.getCd_centro_responsabilita());
			
			List lista = home.fetchAll(sql);
			
			Iterator itm = lista.iterator();
			while(itm.hasNext()) {

				Pdg_moduloBulk pdg_modulo = (Pdg_moduloBulk)itm.next();
				if (livContrSpe.compareTo(new Integer(0))!=0) {
					home = (V_classificazione_vociHome)getHome(userContext,V_classificazione_vociBulk.class);
					sql = home.createSQLBuilder();
					sql.addSQLClause("AND","NR_LIVELLO",sql.EQUALS,livContrSpe);
					sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS,pdg.getEsercizio());
					sql.addSQLClause("AND","TI_GESTIONE",sql.EQUALS,Elemento_voceHome.GESTIONE_SPESE);
					sql.addSQLClause("AND","ID_CLASSIFICAZIONE not in "+
									"(SELECT DISTINCT V_CLASSIFICAZIONE_VOCI_ALL.ID_LIV"+livContrSpe+
									" FROM PDG_MODULO_SPESE, V_CLASSIFICAZIONE_VOCI_ALL "+
									"WHERE PDG_MODULO_SPESE.ID_CLASSIFICAZIONE=V_CLASSIFICAZIONE_VOCI_ALL.ID_CLASSIFICAZIONE" +
									" AND PDG_MODULO_SPESE.ESERCIZIO = "+pdg.getEsercizio()+
									" AND PDG_MODULO_SPESE.CD_CENTRO_RESPONSABILITA = '"+pdg.getCd_centro_responsabilita()+"'"+
									" AND PDG_MODULO_SPESE.PG_PROGETTO = "+pdg_modulo.getPg_progetto()+")");
	
					List lista_cla = home.fetchAll(sql);
					Iterator it = lista_cla.iterator();
					while (it.hasNext()) {
						V_classificazione_vociBulk cla = (V_classificazione_vociBulk)it.next();
	
						Pdg_contrattazione_speseBulk contrSpese = new Pdg_contrattazione_speseBulk(pdg.getEsercizio(), pdg.getCd_centro_responsabilita(), pdg_modulo.getPg_progetto(), null);
						contrSpese = new Pdg_contrattazione_speseBulk(pdg.getEsercizio(), pdg.getCd_centro_responsabilita(), pdg_modulo.getPg_progetto(), null);
						V_classificazione_vociBulk clav = new V_classificazione_vociBulk(cla.getId_classificazione());
						contrSpese.setClassificazione(clav);
						contrSpese.setArea(new CdsBulk(pdg.getCdr().getCd_cds()));
						contrSpese.setTot_spese_decentr_int(Utility.ZERO);
						contrSpese.setTot_spese_decentr_est(Utility.ZERO);
						contrSpese.setAppr_tot_spese_decentr_est(Utility.ZERO);
						contrSpese.setAppr_tot_spese_decentr_int(Utility.ZERO);
	
						insertBulk(userContext, contrSpese);
					}
				}
			}

		} catch (PersistencyException e) {
			throw handleException(e);
		}
	}

	private void ribaltaCostiPdGArea(UserContext userContext, Pdg_esercizioBulk pdg) throws it.cnr.jada.comp.ApplicationException, it.cnr.jada.comp.ComponentException {
		try {
			LoggableStatement cs = new LoggableStatement(getConnection( userContext ),"{call " + it.cnr.jada.util.ejb.EJBCommonServices.getDefaultSchema()
					+ "CNRCTB053.ribaltaSuAreaPDG_da_gest(?,?,?)}",false,this.getClass());
			cs.setObject( 1, pdg.getEsercizio() );
			cs.setString( 2, pdg.getCd_centro_responsabilita());
			cs.setObject( 3, userContext.getUser());
			try {
				lockBulk(userContext,pdg);
				cs.executeQuery();
			} catch (Throwable e) {
				throw handleException(pdg,e);
			} finally {
				cs.close();
			}	
		} catch (java.sql.SQLException e) {
			// Gestisce eccezioni SQL specifiche (errori di lock,...)
			throw handleSQLException(e);
		}
	}

	private void ribaltaCDPSuPdg(UserContext userContext, Pdg_esercizioBulk pdg) throws it.cnr.jada.comp.ApplicationException, it.cnr.jada.comp.ComponentException {
		try {
			LoggableStatement cs = new LoggableStatement(getConnection( userContext ),"{call " + it.cnr.jada.util.ejb.EJBCommonServices.getDefaultSchema() 
					+ "CNRCTB061.scaricaCDPSuPdg(?,?,?)}",false,this.getClass());
			cs.setObject( 1, pdg.getEsercizio() );
			cs.setString( 2, pdg.getCd_centro_responsabilita());
			cs.setObject( 3, userContext.getUser());
			try {
				lockBulk(userContext,pdg);
				cs.executeQuery();
			} catch (Throwable e) {
				throw handleException(pdg,e);
			} finally {
				cs.close();
			}	
		} catch (java.sql.SQLException e) {
			// Gestisce eccezioni SQL specifiche (errori di lock,...)
			throw handleSQLException(e);
		}
	}

	private void predisponeBilancioPreventivoCNR (UserContext userContext, Pdg_esercizioBulk pdg) throws ComponentException
	{

		try
		{
			lockBulk(userContext, pdg);		
			LoggableStatement cs = new LoggableStatement(getConnection( userContext ),"{call "+it.cnr.jada.util.ejb.EJBCommonServices.getDefaultSchema()
					+"CNRCTB055.predispBilFinCNR_da_gest(?,?,?)}",false,this.getClass());
			cs.setObject( 1, pdg.getEsercizio() );
			cs.setString( 2, pdg.getCd_centro_responsabilita());
			cs.setObject( 3, userContext.getUser());
			cs.executeQuery();
			cs.close();
		} 
		catch (Throwable e) 
		{
			throw handleException(pdg, e);
		}
	}

	private void creaSaldiCdRLineaVoceDaGest (UserContext userContext, Pdg_esercizioBulk pdg) throws ComponentException
	{

		try
		{
			lockBulk(userContext, pdg);		
			LoggableStatement cs = new LoggableStatement(getConnection( userContext ), "{call "+it.cnr.jada.util.ejb.EJBCommonServices.getDefaultSchema()
					+"CNRCTB053.creasaldicdrlineavocedagest(?,?,?)}",false,this.getClass());
			cs.setObject( 1, pdg.getEsercizio() );
			cs.setString( 2, pdg.getCd_centro_responsabilita());
			cs.setObject( 3, userContext.getUser());
			cs.executeQuery();
			cs.close();
		} 
		catch (Throwable e) 
		{
			throw handleException(pdg, e);
		}
	}

	private void controllaImportiFontiEsterneApprovate(UserContext userContext, Pdg_esercizioBulk pdg) throws it.cnr.jada.comp.ComponentException {


		try {
			Pdg_moduloHome home = (Pdg_moduloHome)getHome(userContext,Pdg_moduloBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.addClause("AND","esercizio",SQLBuilder.EQUALS,pdg.getEsercizio());
			sql.addClause("AND","cd_centro_responsabilita",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
			
			List lista_mod = home.fetchAll(sql);
			Iterator it = lista_mod.iterator();
			getHomeCache(userContext).fetchAll(userContext);

			while (it.hasNext())
				controllaImportiFontiEsterneApprovate(userContext, (Pdg_moduloBulk)it.next());
		} catch (PersistencyException e) {
			throw handleException(e);
		} catch (ComponentException e) {
			throw handleException(e);
		}
	}

	private void controllaImportiFontiEsterneApprovate(UserContext userContext, Pdg_moduloBulk pdg) throws it.cnr.jada.comp.ComponentException {
		try{
			Pdg_moduloHome pdgHome = (Pdg_moduloHome)getHome(userContext,Pdg_moduloBulk.class);
			for(Iterator i=pdgHome.getAreeUtilizzate(pdg).iterator(); i.hasNext();)
				controllaImportiFontiEsterneApprovate(userContext, pdg, (CdsBulk)i.next());
		} catch(Throwable e) {
			throw handleException(e);
		}
	}

	private void controllaImportiFontiEsterneApprovate(UserContext userContext, Pdg_moduloBulk pdg, CdsBulk cds) throws it.cnr.jada.comp.ComponentException {

		BigDecimal impTotaleEntrate = new BigDecimal(0);
		BigDecimal impTotaleSpese = new BigDecimal(0);

		try {
			Pdg_Modulo_EntrateHome home = (Pdg_Modulo_EntrateHome)getHome(userContext,Pdg_Modulo_EntrateBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.addTableToHeader("CLASSIFICAZIONE_VOCI");
			sql.addSQLJoin("PDG_MODULO_ENTRATE.ID_CLASSIFICAZIONE","CLASSIFICAZIONE_VOCI.ID_CLASSIFICAZIONE");
			CdrBulk cdr = (CdrBulk)getHome(userContext, CdrBulk.class).findByPrimaryKey(pdg.getCdr());
			cdr.setUnita_padre((Unita_organizzativaBulk)getHome(userContext, Unita_organizzativaBulk.class).findByPrimaryKey(new Unita_organizzativaBulk(cdr.getCd_unita_organizzativa())));
			
			if (pdg.getCdr().isCdrSAC())
				sql.addSQLClause("AND","CLASSIFICAZIONE_VOCI.FL_ESTERNA_DA_QUADRARE_SAC",SQLBuilder.EQUALS,"Y");
			sql.addClause("AND","esercizio",SQLBuilder.EQUALS,pdg.getEsercizio());
			sql.addClause("AND","cd_centro_responsabilita",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
			sql.addClause("AND","pg_progetto",SQLBuilder.EQUALS,pdg.getPg_progetto());
			if (cds!=null && cds.getCd_unita_organizzativa()!=null)
				sql.addClause("AND","cd_cds_area",SQLBuilder.EQUALS,cds.getCd_unita_organizzativa());

			SQLBroker broker = home.createBroker(sql);
			
			while(broker.next()) {
				Pdg_Modulo_EntrateBulk pdge = (Pdg_Modulo_EntrateBulk)broker.fetch(Pdg_Modulo_EntrateBulk.class);
				if (pdge.getIm_entrata_app()!=null)
					impTotaleEntrate = impTotaleEntrate.add(pdge.getIm_entrata_app());
			}

		} catch (PersistencyException e) {
			throw handleException(e);
		} catch (ComponentException e) {
			throw handleException(e);
		}

		try {
			Pdg_modulo_speseHome home = (Pdg_modulo_speseHome)getHome(userContext,Pdg_modulo_speseBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.addClause("AND","esercizio",SQLBuilder.EQUALS,pdg.getEsercizio());
			sql.addClause("AND","cd_centro_responsabilita",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
			sql.addClause("AND","pg_progetto",SQLBuilder.EQUALS,pdg.getPg_progetto());
			if (cds!=null && cds.getCd_unita_organizzativa()!=null)
				sql.addClause("AND","cd_cds_area",SQLBuilder.EQUALS,cds.getCd_unita_organizzativa());
			SQLBroker broker = home.createBroker(sql);
			
			while(broker.next()) {
				Pdg_modulo_speseBulk pdgs = (Pdg_modulo_speseBulk)broker.fetch(Pdg_modulo_speseBulk.class);
				if (pdgs.getIm_spese_gest_accentrata_est()!=null)
					impTotaleSpese = impTotaleSpese.add(pdgs.getIm_spese_gest_accentrata_est());
			}

		} catch (PersistencyException e) {
			throw handleException(e);
		} catch (ComponentException e) {
			throw handleException(e);
		}

		try {
			Pdg_contrattazione_speseHome home = (Pdg_contrattazione_speseHome)getHome(userContext,Pdg_contrattazione_speseBulk.class);
			SQLBuilder sql = home.createSQLBuilder();
			sql.addClause("AND","esercizio",SQLBuilder.EQUALS,pdg.getEsercizio());
			sql.addClause("AND","cd_centro_responsabilita",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
			sql.addClause("AND","pg_progetto",SQLBuilder.EQUALS,pdg.getPg_progetto());
			if (cds!=null && cds.getCd_unita_organizzativa()!=null)
				sql.addClause("AND","cd_cds_area",SQLBuilder.EQUALS,cds.getCd_unita_organizzativa());

			SQLBroker broker = home.createBroker(sql);
			
			while(broker.next()) {
				Pdg_contrattazione_speseBulk pdgs = (Pdg_contrattazione_speseBulk)broker.fetch(Pdg_contrattazione_speseBulk.class);
				if (pdgs.getAppr_tot_spese_decentr_est()!=null)
					impTotaleSpese = impTotaleSpese.add(pdgs.getAppr_tot_spese_decentr_est());
			}

		} catch (PersistencyException e) {
			throw handleException(e);
		} catch (ComponentException e) {
			throw handleException(e);
		}

		if (impTotaleSpese.compareTo(impTotaleEntrate)!=0){
			if ( cds!=null ) {
				if ( cds.getCd_tipo_unita().equals(Tipo_unita_organizzativaHome.TIPO_UO_AREA) ) 
					throw new ApplicationException("Per l'area " + cds.getCd_unita_organizzativa() + " e per il modulo "+ pdg.getCd_progetto()+", il totale degli importi provenienti dalle fonti esterne delle entrate non corrisponde a quello delle spese. Impossibile procedere.");
				else
					throw new ApplicationException("Per il CDS " + cds.getCd_unita_organizzativa() + " e per il modulo "+ pdg.getCd_progetto()+", il totale degli importi provenienti dalle fonti esterne delle entrate non corrisponde a quello delle spese. Impossibile procedere.");
			}
			else
				throw new ApplicationException("Per il modulo "+ pdg.getCd_progetto()+" il totale degli importi provenienti dalle fonti esterne delle entrate non corrisponde a quello delle spese. Impossibile procedere.");
		}
	}

	private void aggiungiModuliContrattazione(UserContext userContext, Pdg_esercizioBulk pdg) throws it.cnr.jada.comp.ComponentException {
		Pdg_contrattazione_speseHome home = (Pdg_contrattazione_speseHome)getHome(userContext,Pdg_contrattazione_speseBulk.class);
		SQLBuilder sql = home.createSQLBuilder();
		sql.resetColumns();
		sql.addColumn("DISTINCT PG_PROGETTO");
		sql.addClause("AND","esercizio",SQLBuilder.EQUALS,pdg.getEsercizio());
		sql.addClause("AND","cd_centro_responsabilita",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());

		Integer pgProgetto = null;

		try {
			java.sql.ResultSet rs = null;
			PreparedStatement ps = null;
			try {
				ps = sql.prepareStatement(getConnection(userContext));
				try {
					rs = ps.executeQuery();
					while (rs.next()) {
						if (rs.getString(1)!= null) {
							pgProgetto = new Integer(rs.getInt(1));
							Pdg_moduloHome moduloHome = (Pdg_moduloHome)getHome(userContext,Pdg_moduloBulk.class);
							Pdg_moduloBulk modulo = new Pdg_moduloBulk(pdg.getEsercizio(),pdg.getCd_centro_responsabilita(),pgProgetto);
							Pdg_moduloBulk moduloDB = (Pdg_moduloBulk)moduloHome.findByPrimaryKey(modulo);
							
							if (moduloDB == null) {
								modulo.setStato(Pdg_moduloBulk.STATO_AD);
								modulo.setToBeCreated();
								creaConBulk(userContext, modulo);
							}
						}
					}
				} catch (java.sql.SQLException e) {
					throw new ComponentException(e);
				} catch (PersistencyException e) {
					throw new ComponentException(e);
				} finally {
					if (rs != null) try{rs.close();}catch( java.sql.SQLException e ){};
				}
			} finally {
				if (ps != null) try{ps.close();}catch( java.sql.SQLException e ){};
			}
		} catch (java.sql.SQLException ex) {
			throw new ComponentException(ex);
		}
	}
	// da testare
	private void verificaPresenzaModuli(UserContext userContext, Pdg_esercizioBulk pdg) throws it.cnr.jada.comp.ComponentException {
		Pdg_contrattazione_speseHome home = (Pdg_contrattazione_speseHome)getHome(userContext,Pdg_contrattazione_speseBulk.class);
		SQLBuilder sql = home.createSQLBuilder();
		sql.resetColumns();
		sql.addColumn("DISTINCT PG_PROGETTO");
		sql.addClause("AND","esercizio",SQLBuilder.EQUALS,pdg.getEsercizio());
		sql.addClause("AND","cd_centro_responsabilita",SQLBuilder.EQUALS,pdg.getCd_centro_responsabilita());
		
		Integer pgProgetto=null;
		try {
			java.sql.ResultSet rs = null;
			PreparedStatement ps = null;
			try {
				ps = sql.prepareStatement(getConnection(userContext));
				try {
					rs = ps.executeQuery();
					while (rs.next()) {
						if (rs.getString(1)!= null) {
							pgProgetto = new Integer(rs.getInt(1));
							Pdg_moduloHome moduloHome = (Pdg_moduloHome)getHome(userContext,Pdg_moduloBulk.class);
							Pdg_moduloBulk modulo = new Pdg_moduloBulk(pdg.getEsercizio(),pdg.getCd_centro_responsabilita(),pgProgetto);
							Pdg_moduloBulk moduloDB = (Pdg_moduloBulk)moduloHome.findByPrimaryKey(modulo);						
							if (moduloDB == null) 
								throw new ApplicationException("Non sono presenti tutti i moduli. Impossibile procedere.");
						}
					}
				} catch (java.sql.SQLException e) {
					throw new ComponentException(e);
				} catch (PersistencyException e) {
					throw new ComponentException(e);
				} finally {
					if (rs != null) try{rs.close();}catch( java.sql.SQLException e ){};
				}
			} finally {
				if (ps != null) try{ps.close();}catch( java.sql.SQLException e ){};
			}
		} catch (java.sql.SQLException ex) {
			throw new ComponentException(ex);
		}
	}
	public OggettoBulk inizializzaBulkPerModifica(UserContext userContext,OggettoBulk bulk) throws it.cnr.jada.comp.ComponentException {
		try {
			CdrBulk testata = (CdrBulk)super.inizializzaBulkPerModifica(userContext,bulk);
			CdrHome testataHome = (CdrHome)getHome(userContext, CdrBulk.class);
			testata.setDettagli(new it.cnr.jada.bulk.BulkList(testataHome.findPdgEsercizioDettagli(userContext, testata)));
			getHomeCache(userContext).fetchAll(userContext);
			return testata;
		} catch(Exception e) {
			throw handleException(e);
		}
	}
}
