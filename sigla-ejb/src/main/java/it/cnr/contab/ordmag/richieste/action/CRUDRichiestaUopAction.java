package it.cnr.contab.ordmag.richieste.action;

import java.math.BigDecimal;

import it.cnr.contab.docamm00.tabrif.bulk.Bene_servizioBulk;
import it.cnr.contab.ordmag.anag00.UnitaMisuraBulk;
import it.cnr.contab.ordmag.richieste.bulk.RichiestaUopRigaBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.Forward;
import it.cnr.jada.util.action.CRUDBP;

public class CRUDRichiestaUopAction extends it.cnr.jada.util.action.CRUDAction {

public CRUDRichiestaUopAction() {
        super();
    }
//private Documento_genericoBulk basicCalcolaTotale(ActionContext context) throws it.cnr.jada.comp.ApplicationException {
//
//    it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//    Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//    try {
//        DocumentoGenericoComponentSession component= null;
//        if (!documentoGenerico.isGenericoAttivo())
//            component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoPassivoBP) bp).createComponentSession();
//        else
//            component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//        return (Documento_genericoBulk) component.calcoloConsuntivi(context.getUserContext(), documentoGenerico);
//    } catch (it.cnr.jada.comp.ComponentException e) {
//        throw new it.cnr.jada.comp.ApplicationException(e);
//    } catch (Throwable t) {
//        throw new it.cnr.jada.comp.ApplicationException(t);
//    }
//}
///**
// * effettua i controlli post-selezione dell'accertamento
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @param newAccert	
// * @return Il Forward alla pagina di risposta
// */
//protected Forward basicDoBringBackOpenAccertamentiWindow(ActionContext context, Accertamento_scadenzarioBulk newAccert) {
////effettua i controlli post-selezione dell'accertamento
//
//	CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//    try {
//        Accertamento_scadenzarioBulk accertamento= (Accertamento_scadenzarioBulk) bp.getAccertamentiController().getModel();
//        if (accertamento != null) {
//            resyncAccertamento(context, accertamento, newAccert);
//        } else {
//            basicDoContabilizzaAccertamenti(context, newAccert, null);
//        }
//    } catch (Throwable t) {
//		it.cnr.contab.doccont00.core.bulk.IDefferUpdateSaldi defSaldiBulk = bp.getDefferedUpdateSaldiParentBP().getDefferedUpdateSaldiBulk();
//		if (newAccert.getAccertamento().getPg_ver_rec().equals((Long)newAccert.getAccertamento().getSaldiInfo().get("pg_ver_rec")))
//			defSaldiBulk.removeFromDefferredSaldi(newAccert.getAccertamento());
//		try {
//			it.cnr.contab.doccont00.bp.CRUDVirtualAccertamentoBP.rollbackToSafePoint(context);
//		} catch (Exception e) {
//			handleException(context, e);
//		}
//		return handleException(context, t);
//    }
//    return context.findDefaultForward();
//}
///**
// * effettua i controlli post-selezione dell'obbligazione
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @param newObblig	
// * @return Il Forward alla pagina di risposta
// */
//protected Forward basicDoBringBackOpenObbligazioniWindow(ActionContext context, Obbligazione_scadenzarioBulk newObblig) {
////effettua i controlli post-selezione dell'obbligazione
//	CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) getBusinessProcess(context);
//    try {
//        Obbligazione_scadenzarioBulk obbligazione= (Obbligazione_scadenzarioBulk) bp.getObbligazioniController().getModel();
//        if (obbligazione != null) {
//            resyncObbligazione(context, obbligazione, newObblig);
//        } else {
//            basicDoContabilizza(context, newObblig, null);
//        }
//    } catch (Throwable t) {
//		it.cnr.contab.doccont00.core.bulk.IDefferUpdateSaldi defSaldiBulk = bp.getDefferedUpdateSaldiParentBP().getDefferedUpdateSaldiBulk();
//		if (newObblig.getObbligazione().getPg_ver_rec().equals((Long)newObblig.getObbligazione().getSaldiInfo().get("pg_ver_rec")))
//			defSaldiBulk.removeFromDefferredSaldi(newObblig.getObbligazione());
//		try {
//			it.cnr.contab.doccont00.bp.CRUDVirtualObbligazioneBP.rollbackToSafePoint(context);
//		} catch (Exception e) {
//			handleException(context, e);
//		}
//	    return handleException(context, t);
//    }
//    return context.findDefaultForward();
//}
///**
// * Gestisce una richiesta di ricerca.
// *
// * L'implementazione di default utilizza il metodo astratto <code>read</code>
// * di <code>CRUDBusinessProcess</code>.
// * Se la ricerca fornisce pi� di un risultato viene creato un
// * nuovo <code>SelezionatoreListaBP</code> per la selezione di un elemento.
// * Al business process viene anche chiesto l'elenco delle colonne da
// * visualizzare.
// */
//protected Forward basicDoCerca(ActionContext context) throws java.rmi.RemoteException,InstantiationException,javax.ejb.RemoveException {
////per spesa	
//	try {
//		fillModel(context);
//		CRUDBP bp = getBusinessProcess(context);
//		OggettoBulk model = (OggettoBulk)bp.getModel();
//		it.cnr.jada.util.RemoteIterator ri = bp.find(context,null,model);
//		if (ri == null || ri.countElements() == 0) {
//			it.cnr.jada.util.ejb.EJBCommonServices.closeRemoteIterator(context,ri);
//			bp.setMessage("La ricerca non ha fornito alcun risultato.");
//			return context.findDefaultForward();
//		//} else if (ri.countElements() == 1) {
//			//OggettoBulk bulk = (OggettoBulk)ri.nextElement();
//			//return basicDoRiportaSelezione(context, bulk);
//		} else {
//			bp.setModel(context,model);
//			SelezionatoreListaBP nbp = (SelezionatoreListaBP)context.createBusinessProcess("Selezionatore");
//			nbp.setIterator(context,ri);
//			nbp.setBulkInfo(bp.getBulkInfo());
//			nbp.setColumns(getBusinessProcess(context).getSearchResultColumns());
//			context.addHookForward("seleziona",this,"doRiportaSelezione");
//			return context.addBusinessProcess(nbp);
//		}
//	} catch(Throwable e) {
//		return handleException(context,e);
//	} 
//}
//    private void basicDoContabilizza(ActionContext context, it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk obbligazione, java.util.List selectedModels)
//        throws it.cnr.jada.comp.ComponentException {
//
//        if (obbligazione != null && selectedModels != null) {
//            //// controlla l'obbligazione selezionata
//            //if (obbligazione.getIm_associato_doc_amm().compareTo(new java.math.BigDecimal(0))!=0 || 
//            //obbligazione.getIm_associato_doc_contabile().compareTo(new java.math.BigDecimal(0))!=0 ||
//            //obbligazione.getIm_scadenza().compareTo(new java.math.BigDecimal(0))==0)
//            //throw new it.cnr.jada.comp.ApplicationException("L'obbligazione selezionata non � valida");
//            CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
//
//            try {
//	            //richiama il metodo della component per la contabilizzazione
//                DocumentoGenericoComponentSession h= (DocumentoGenericoComponentSession) bp.createComponentSession();
//                Documento_genericoBulk doc= h.contabilizzaDettagliSelezionati(context.getUserContext(), (Documento_genericoBulk) bp.getModel(), selectedModels, obbligazione);
//                try {
//                    bp.setModel(context, doc);
//                    bp.resyncChildren(context);
//                } catch (BusinessProcessException e) {
//                }
//            } catch (java.rmi.RemoteException e) {
//                bp.handleException(e);
//            } catch (BusinessProcessException e) {
//                bp.handleException(e);
//            }
//
//            doCalcolaTotalePerObbligazione(context, obbligazione);
//        }
//    }
//    private void basicDoContabilizzaAccertamenti(ActionContext context, it.cnr.contab.doccont00.core.bulk.Accertamento_scadenzarioBulk accertamento, java.util.List selectedModels)
//        throws it.cnr.jada.comp.ComponentException {
//
//        if (accertamento != null && selectedModels != null) {
//            //if (accertamento.getIm_associato_doc_amm().compareTo(new java.math.BigDecimal(0))!=0 || 
//            //accertamento.getIm_associato_doc_contabile().compareTo(new java.math.BigDecimal(0))!=0 ||
//            //accertamento.getIm_scadenza().compareTo(new java.math.BigDecimal(0))==0)
//            //throw new it.cnr.jada.comp.ApplicationException("L'accertamento selezionato non � valida");
//
//            CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) context.getBusinessProcess();
//
//            try {
//	            //richiama il metodo della component per la contabilizzazione
//                DocumentoGenericoComponentSession h= (DocumentoGenericoComponentSession) bp.createComponentSession();
//
//                Documento_genericoBulk doc= h.contabilizzaDettagliSelezionati(context.getUserContext(), (Documento_genericoBulk) bp.getModel(), selectedModels, accertamento);
//                try {
//                    bp.setModel(context, doc);
//                    bp.resyncChildren(context);
//                } catch (BusinessProcessException e) {
//                }
//            } catch (java.rmi.RemoteException e) {
//                bp.handleException(e);
//            } catch (BusinessProcessException e) {
//                bp.handleException(e);
//            }
//
//            doCalcolaTotalePerAccertamento(context, accertamento);
//        }
//    }
///**
//* Inizializza il terzo e per i generici passivi inizializza i riferimenti bancari
//* Creation date: (27/11/2001 17.04.56)
//* @param context it.cnr.jada.action.ActionContext
//* @return it.cnr.jada.action.Forward
//*/
//
//public Documento_generico_rigaBulk basicDoResetTerzo(ActionContext context, Documento_generico_rigaBulk documentoGenericoRiga) throws java.rmi.RemoteException {
//
//    //inizializza il terzo
//    it.cnr.contab.anagraf00.core.bulk.TerzoBulk terzo = new it.cnr.contab.anagraf00.core.bulk.TerzoBulk();
//    terzo.setAnagrafico(new AnagraficoBulk());
//    documentoGenericoRiga.setTerzo(terzo);
//    documentoGenericoRiga.setNome(null);
//    documentoGenericoRiga.setCognome(null);
//    documentoGenericoRiga.setRagione_sociale(null);
//    documentoGenericoRiga.setCodice_fiscale(null);
//    documentoGenericoRiga.setPartita_iva(null);
//    //per i generici passivi inizializza i riferimenti bancari
//    if (!documentoGenericoRiga.getDocumento_generico().isGenericoAttivo()) {
//        documentoGenericoRiga.setBanca(null);
//        documentoGenericoRiga.setModalita(null);
//        documentoGenericoRiga.setRiferimenti_bancari(null);
//        documentoGenericoRiga.setTermini(null);
//        documentoGenericoRiga.setModalita_pagamento(null);
//        documentoGenericoRiga.setCessionario(null);
//    }
//    return documentoGenericoRiga;
//}
//private Forward basicDoRicercaAccertamento(ActionContext context, Documento_genericoBulk doc, java.util.List models) {
//
//    try {
//        //controlla che gli importi dei dettagli siano diversi da 0
//        //e che sia stato selezionato il terzo per i dettagli selezionati
//        Documento_generico_rigaBulk riga= null;
//        boolean diversi= false;
//        Integer cd_terzo= null;
//
//        //controlla se sono diversi i terzi
//        if (models != null)
//            for (java.util.Iterator i= models.iterator(); i.hasNext();) {
//                riga= (Documento_generico_rigaBulk) i.next();
//                if (cd_terzo == null)
//                    cd_terzo= riga.getTerzo().getCd_terzo();
//                if (riga.getTerzo().getCd_terzo() == null)
//                    throw new it.cnr.jada.comp.ApplicationException("Non � possibile contabilizzare poich� su un dettaglio\n non � stato selezionato alcun terzo.");
//                if (riga.getIm_imponibile() == null || riga.getIm_imponibile().compareTo(new java.math.BigDecimal(0)) == 0)
//                    throw new it.cnr.jada.comp.ApplicationException("Non � possibile contabilizzare poich� un dettaglio\nselezionato ha un importo pari a 0.");
//                if (!diversi && ((!riga.getTerzo().getCd_terzo().equals(cd_terzo))))
//                    diversi= true;
//            }
//
//        //imposta il filtro per la ricerca
//        Filtro_ricerca_accertamentiVBulk filtro= new Filtro_ricerca_accertamentiVBulk();
//
//        //passivo per l'ente
//        filtro.setPassivo_ente(doc.isPassivo_ente());
//
//        //data scadenza
//        filtro.setData_scadenziario(doc.getDt_scadenza());
//
//        //se sono diversi i terzi viene passato una nuova istanza di terzobulk altrimenti il primo
//        //calcola il totale dei dettagli selezionati
//        if (models != null) {
//	        if (!diversi) {
//            	filtro.setCliente(((Documento_generico_rigaBulk) models.get(0)).getTerzo());
//	        } else {
//		        TerzoBulk nuovoTerzo = new TerzoBulk();
//		        nuovoTerzo.setAnagrafico(new AnagraficoBulk());
//            	filtro.setCliente(nuovoTerzo);
//            }
//            filtro.setIm_importo(calcolaTotaleSelezionati(models));
//        }
//
//        //unit� organizzativa
//        filtro.setCd_unita_organizzativa(doc.getCd_unita_organizzativa());
//        //unit� organizzativa di origine
//        filtro.setCd_uo_origine(doc.getCd_uo_origine());
//
//        //competenza coge
//        filtro.setHasDocumentoCompetenzaCOGEInAnnoPrecedente(doc.hasCompetenzaCOGEInAnnoPrecedente());
//        filtro.setHasDocumentoCompetenzaCOGESoloInAnnoCorrente(
//	        !doc.hasCompetenzaCOGEInAnnoPrecedente() &&
//	        doc.getDateCalendar(doc.getDt_a_competenza_coge()).get(java.util.Calendar.YEAR) == doc.getEsercizio().intValue());
//		filtro.setCompetenzaCOGESuEnte(doc.isFlagEnte());
//
//		if (!doc.isFlagEnte() && doc.isGenericoAttivo() &&
//			(filtro.hasDocumentoCompetenzaCOGEInAnnoPrecedente() ||
//				!filtro.hasDocumentoCompetenzaCOGESoloInAnnoCorrente()))
//			throw new it.cnr.jada.comp.ApplicationException("Non � possibile contabilizzare alcun dettaglio\nper un CdS con competenza NON appartenente all'esercizio corrente");
//
//		//imposta i flag per l'importo e la scadenza
//        if (models == null || models.isEmpty())
//            filtro.setFl_importo(Boolean.FALSE);
//        if (filtro.getData_scadenziario() == null)
//            filtro.setFl_data_scadenziario(Boolean.FALSE);
//
//        filtro.setAttivoCds(!doc.isFlagEnte() && doc.isGenericoAttivo());
//        
//        //richiama il filtro RicercaAccertamentiBP 
//        it.cnr.jada.util.action.BulkBP bp= (it.cnr.jada.util.action.BulkBP) context.getUserInfo().createBusinessProcess(context, "RicercaAccertamentiBP");
//        bp.setModel(context, filtro);
//        //imposta il bringback su doContabilizzaAccertamenti
//        //if (!((SimpleCRUDBP)context.getBusinessProcess()).getTab("tab").equals("tabDocumentoGenericoAccertamenti"))
//	        context.addHookForward("bringback", this, "doContabilizzaAccertamenti");
//     	//else
//            //context.addHookForward("bringback", this, "doAggiungiAccertamenti");
//     	HookForward hook= (HookForward) context.findForward("bringback");
//        return context.addBusinessProcess(bp);
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//
//}
//private Forward basicDoRicercaObbligazione(ActionContext context, Documento_genericoBulk doc, java.util.List models) {
//
//    try {
//        //controlla che gli importi dei dettagli siano diversi da 0
//        //e che sia stato selezionato il terzo per i dettagli selezionati
//        Documento_generico_rigaBulk riga= null;
//        boolean diversi= false;
//        Integer cd_terzo= null;
//
//        //controlla se sono diversi i terzi
//        if (models != null)
//            for (java.util.Iterator i= models.iterator(); i.hasNext();) {
//                riga= (Documento_generico_rigaBulk) i.next();
//                if (riga.getTerzo().getCd_terzo() == null)
//                    throw new it.cnr.jada.comp.ApplicationException("Non � possibile contabilizzare poich� su un dettaglio\ns non � stato selezionato alcun terzo");
//                if (cd_terzo == null)
//                    cd_terzo= riga.getTerzo().getCd_terzo();
//                if (riga.getIm_imponibile() == null || riga.getIm_imponibile().compareTo(new java.math.BigDecimal(0)) == 0)
//                    throw new it.cnr.jada.comp.ApplicationException("Non � possibile contabilizzare poich� un dettaglio\nselezionato ha un importo pari a 0");
//                if (!diversi && ((!riga.getTerzo().getCd_terzo().equals(cd_terzo))))
//                    diversi= true;
//            }
//
//        //imposta il filtro per la ricerca
//        Filtro_ricerca_obbligazioniVBulk filtro= new Filtro_ricerca_obbligazioniVBulk();
//
//        CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
////        filtro.setSoloVociSenzaIndicazioneTrovato(true);
//        //Documento_genericoBulk documento = ((Documento_genericoBulk) bp.getModel());
//
//        //imposta il tipo documento
//        if (doc.getTipo_documento()!=null)
//        	filtro.setTipo_documento(doc.getTipo_documento());
//        	
//        //passivo per l'ente
//        filtro.setPassivo_ente(doc.isPassivo_ente());
//
//
//        //data scadenza
//        filtro.setData_scadenziario(doc.getDt_scadenza());
//
//        //se sono diversi i terzi viene passato una nuova istanza di terzobulk altrimenti il primo
//        //calcola il totale dei dettagli selezionati
//        if (models != null) {
//	        if (!diversi) {
//            	filtro.setFornitore(((Documento_generico_rigaBulk) models.get(0)).getTerzo());
//	        } else {
//		        TerzoBulk nuovoTerzo = new TerzoBulk();
//		        nuovoTerzo.setAnagrafico(new AnagraficoBulk());
//            	filtro.setFornitore(nuovoTerzo);
//            }
//            filtro.setIm_importo(calcolaTotaleSelezionati(models));
//        }
//        //unit� organizzativa e unit� organizzativa di origine
//        filtro.setCd_unita_organizzativa(doc.getCd_unita_organizzativa());
//        filtro.setCd_uo_origine(doc.getCd_uo_origine());
//
//        //competenza coge
//        filtro.setHasDocumentoCompetenzaCOGEInAnnoPrecedente(doc.hasCompetenzaCOGEInAnnoPrecedente());
//        filtro.setHasDocumentoCompetenzaCOGESoloInAnnoCorrente(
//	        !doc.hasCompetenzaCOGEInAnnoPrecedente() &&
//	        doc.getDateCalendar(doc.getDt_a_competenza_coge()).get(java.util.Calendar.YEAR) == doc.getEsercizio().intValue());
//		filtro.setCompetenzaCOGESuEnte(doc.isFlagEnte());
//
//		//if (doc.isPassivo_ente() &&
//			//filtro.hasDocumentoCompetenzaCOGEInAnnoPrecedente() &&
//			//((doc.getUo_CNR() != null && !doc.getUo_CNR().equalsIgnoreCase(doc.getCd_uo_origine())) &&
//			 //(doc.getCds_CNR() != null && !doc.getCds_CNR().equalsIgnoreCase(doc.getCd_cds_origine()))))
//			//throw new it.cnr.jada.comp.ApplicationException("Non � possibile contabilizzare alcun dettaglio\nper un documento passivo dell'Ente con competenza appartenente all'anno precedente.");
//		
//        //imposta i flag per l'importo e la scadenza
//        if (models == null || models.isEmpty())
//            filtro.setFl_importo(Boolean.FALSE);
//        if (filtro.getData_scadenziario() == null)
//            filtro.setFl_data_scadenziario(Boolean.FALSE);
//
//        //richiama il filtro RicercaAccertamentiBP 
//        it.cnr.jada.util.action.BulkBP robp= (it.cnr.jada.util.action.BulkBP) context.getUserInfo().createBusinessProcess(context, "RicercaObbligazioniBP");
//        robp.setModel(context, filtro);
//        //imposta il bringback su doContabilizzaAccertamenti
//        context.addHookForward("bringback", this, "doContabilizzaObbligazioni");
//        HookForward hook= (HookForward) context.findForward("bringback");
//        return context.addBusinessProcess(robp);
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce un HookForward di ritorno da un risultato di una ricerca (SelezionatoreBP)
// */
//protected Forward basicDoRiportaSelezione(ActionContext context, it.cnr.jada.bulk.OggettoBulk selezione)  throws java.rmi.RemoteException {
//
//	//per spesa
//	try {
//		if (selezione != null) {
//			CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)context.getBusinessProcess();
//			bp.edit(context,selezione);
//			Documento_genericoBulk selezioneBulk = (Documento_genericoBulk)bp.getModel();
//
//			// Borriello: integrazione Err. CNR 775
//			Integer esScriv = it.cnr.contab.utenze00.bp.CNRUserContext.getEsercizio(context.getUserContext());
//			
//			if ( (selezioneBulk.getEsercizio().compareTo(esScriv) == 0) && selezioneBulk.isRiportata()){
//				throw new it.cnr.jada.comp.ApplicationException("Il documento amministrativo risulta (parzialmente) riportato! Operazione annullata.");
//			}
//			// Gennaro Borriello - (09/11/2004 18.08.57)
//			//	Nuova gestione dello stato <code>getRiportata()</code>					
//			if ( (selezioneBulk.getEsercizio().compareTo(esScriv) != 0) && (!Documento_genericoBulk.COMPLETAMENTE_RIPORTATO.equalsIgnoreCase(selezioneBulk.getRiportataInScrivania())) ){					
//				throw new it.cnr.jada.comp.ApplicationException("Il documento amministrativo non risulta completamente riportato! Operazione annullata.");
//			}
//			//richiamo del terzo di default per la spesa..
//			DocumentoGenericoComponentSession h= (DocumentoGenericoComponentSession) bp.createComponentSession();
//			if(h.getTerzoUnivoco(context.getUserContext(),selezioneBulk)!= null)
//					selezioneBulk.setTerzo_spesa(h.getTerzoUnivoco(context.getUserContext(),selezioneBulk));
//			else
//					selezioneBulk.setTerzo_spesa(h.getTerzoDefault(context.getUserContext()));
//            
//			context.closeBusinessProcess();
//			HookForward forward = (HookForward)context.findForward("bringback");
//			forward.addParameter("documentoAmministrativoSelezionato", selezioneBulk);
//			return forward;
//		}
//	} catch(Exception e) {
//		return handleException(context,e);
//	}		
//	return context.findDefaultForward();
//}
///**
// * Calcola l'importo totale delle riche selezionate
// * 
// *
// * @param selectedModels	
// * @return 
// * @throws ApplicationException	
// */
//protected java.math.BigDecimal calcolaTotaleSelezionati(java.util.List selectedModels) throws it.cnr.jada.comp.ApplicationException {
////calcola l'importo totale delle riche selezionate
//    java.math.BigDecimal importo= new java.math.BigDecimal(0);
//
//    if (selectedModels != null) {
//        for (java.util.Iterator i= selectedModels.iterator(); i.hasNext();) {
//            Documento_generico_rigaBulk rigaSelected= (Documento_generico_rigaBulk) i.next();
//            java.math.BigDecimal imTotale= rigaSelected.getIm_riga();
//           if (imTotale != null)
//                importo= importo.add(imTotale);
//        }
//    }
//
//    importo= importo.setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
//    return importo;
//}
//private void controllaQuadraturaAccertamenti(ActionContext context, Documento_genericoBulk doc) throws it.cnr.jada.comp.ComponentException {
////controllo di quadratura tra la scadenza e le righe associate
//    CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) context.getBusinessProcess();
//
//    try {
//	    //richiamo della component
//        DocumentoGenericoComponentSession h= (DocumentoGenericoComponentSession) bp.createComponentSession();
//        h.controllaQuadraturaAccertamenti(context.getUserContext(), doc);
//    } catch (java.rmi.RemoteException e) {
//        bp.handleException(e);
//    } catch (BusinessProcessException e) {
//        bp.handleException(e);
//    }
//}
//private void controllaQuadraturaObbligazioni(ActionContext context, Documento_genericoBulk doc) throws it.cnr.jada.comp.ComponentException {
////controllo di quadratura tra la scadenza e le righe associate
//
//    CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
//
//    try {
//	    //richiamo della component
//        DocumentoGenericoComponentSession h= (DocumentoGenericoComponentSession) bp.createComponentSession();
//        h.controllaQuadraturaObbligazioni(context.getUserContext(), doc);
//    } catch (java.rmi.RemoteException e) {
//        bp.handleException(e);
//    } catch (BusinessProcessException e) {
//        bp.handleException(e);
//    }
//}
//    /**
//     * Controlla che le righe siano non Annullate e non associate a Mandati
//     */
//    private void controllaRighePerMandatieAnnullati(java.util.List models, Accertamento_scadenzarioBulk scadenza, boolean dettagli) throws it.cnr.jada.comp.ApplicationException {
//        String emsg;
//        for (java.util.Iterator it= models.iterator(); it.hasNext();) {
//            Documento_generico_rigaBulk riga= (Documento_generico_rigaBulk) it.next();
//            if (riga.getTi_associato_manrev() != null && riga.ASSOCIATO_A_MANDATO.equalsIgnoreCase(riga.getTi_associato_manrev())) {
//                if (!dettagli)
//                    emsg=
//                        "Impossibile scollegare l'accertamento \""
//							+ scadenza.getEsercizio_originale().intValue()
//                        	+ "/" + scadenza.getPg_accertamento().longValue()
//                            + "\" perch� il dettaglio collegato \""
//                            + ((riga.getDs_riga() != null) ? riga.getDs_riga() : String.valueOf(riga.getProgressivo_riga().longValue()))
//                            + "\" � associato a mandato.";
//                else
//                    emsg= "Impossibile scollegare il dettaglio \"" + ((riga.getDs_riga() != null) ? riga.getDs_riga() : String.valueOf(riga.getProgressivo_riga().longValue())) + "\" perch� associato a mandato.";
//                throw new it.cnr.jada.comp.ApplicationException(emsg);
//            }
//            if (riga.isAnnullato()) {
//                if (!dettagli)
//                    emsg=
//                        "Impossibile scollegare l'accertamento \""
//                            + scadenza.getEsercizio_originale().intValue()
//                        	+ "/" + scadenza.getPg_accertamento().longValue()
//                            + "\" perch� il dettaglio collegato \""
//                            + ((riga.getDs_riga() != null) ? riga.getDs_riga() : String.valueOf(riga.getProgressivo_riga().longValue()))
//                            + "\" � in stato "
//                            + riga.STATO_ANNULLATO
//                            + ".";
//                else
//                    emsg=
//                        "Impossibile scollegare il dettaglio \""
//                            + ((riga.getDs_riga() != null) ? riga.getDs_riga() : String.valueOf(riga.getProgressivo_riga().longValue()))
//                            + "\" perch� � in stato "
//                            + riga.STATO_ANNULLATO
//                            + ".";
//                throw new it.cnr.jada.comp.ApplicationException(emsg);
//            }
//        }
//    }
//    /**
//     * Controlla che le righe siano non Annullate e non associate a Mandati
//     */
//    private void controllaRighePerMandatieAnnullati(java.util.List models, Obbligazione_scadenzarioBulk scadenza) throws it.cnr.jada.comp.ApplicationException {
//        String emsg;
//        for (java.util.Iterator it= models.iterator(); it.hasNext();) {
//            Documento_generico_rigaBulk riga= (Documento_generico_rigaBulk) it.next();
//            if (riga.getTi_associato_manrev() != null && riga.ASSOCIATO_A_MANDATO.equalsIgnoreCase(riga.getTi_associato_manrev())) {
//                emsg=
//                    "Impossibile scollegare l'impegno \""
//						+ scadenza.getEsercizio_originale().intValue()
//						+ "/" + scadenza.getPg_obbligazione().longValue()
//                        + "\" perch� il dettaglio collegato \""
//                        + ((riga.getDs_riga() != null) ? riga.getDs_riga() : String.valueOf(riga.getProgressivo_riga().longValue()))
//                        + "\" � associato a mandato.";
//                throw new it.cnr.jada.comp.ApplicationException(emsg);
//            }
//            if (riga.isAnnullato()) {
//                emsg=
//                    "Impossibile scollegare il dettaglio \""
//                        + ((riga.getDs_riga() != null) ? riga.getDs_riga() : String.valueOf(riga.getProgressivo_riga().longValue()))
//                        + "\" perch� � in stato "
//                        + riga.STATO_ANNULLATO
//                        + ".";
//            }
//        }
//    }
///**
// * Controlla le righe selezionate per la contabilizzazione
// * 
// *
// * @param selectedModels	
// * @throws ApplicationException	
// */
//protected void controllaSelezione(java.util.Iterator selectedModels) throws it.cnr.jada.comp.ApplicationException {
////controlla le righe selezionate per la contabilizzazione
//    if (selectedModels != null) {
//        while (selectedModels.hasNext()) {
//            Documento_generico_rigaBulk rigaSelected= (Documento_generico_rigaBulk) selectedModels.next();
//            if (!Documento_generico_rigaBulk.STATO_INIZIALE.equals(rigaSelected.getStato_cofi()))
//                throw new it.cnr.jada.comp.ApplicationException("Il dettaglio \"" + rigaSelected.getDs_riga() + "\" non pu� essere contabilizzato poich� � in stato " + rigaSelected.getStato_cofi() + ".");
//            if (rigaSelected.getTerzo()==null || rigaSelected.getTerzo().getAnagrafico()==null)
//                throw new it.cnr.jada.comp.ApplicationException("Il dettaglio \"" + rigaSelected.getDs_riga() + "\" non pu� essere contabilizzato poich� il suo terzo non e' valido");
//			try {
//				rigaSelected.validaDateCompetenza();
//			} catch (ValidationException e) {
//				throw new it.cnr.jada.comp.ApplicationException(e.getMessage());
//			}
//        }
//    }
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//public Forward doAddToCRUD(ActionContext context, String tableName) {
//
//    return super.doAddToCRUD(context, tableName);
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//public Forward doAddToCRUDMain_Accertamenti(ActionContext context) {
//    //imposta l'azione di aggiunta di una scadenza
//    try {
//        CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//        bp.getDettaglio().getSelection().clearSelection();
//        fillModel(context);
//        Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//        return basicDoRicercaAccertamento(context, doc, null);
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//public Forward doAddToCRUDMain_Accertamenti_DettaglioAccertamenti(ActionContext context) {
//    //imposta l'azione di aggiunta di un dettaglio ad una scadenza
//    try {
//        CRUDDocumentoGenericoAttivoBP bp =
//            (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//        bp.getDettaglio().getSelection().clearSelection();
//        fillModel(context);
//        Accertamento_scadenzarioBulk scadenza =
//            (Accertamento_scadenzarioBulk) bp.getAccertamentiController().getModel();
//        //controlla che sia stata selezionata una scadenza sulla quale si vuole aggiungere il dettaglio
//        if (scadenza == null)
//            throw new it.cnr.jada.comp.ApplicationException(
//                "Selezionare l'accertamento a cui associare i dettagli.");
//
//        //estrae solo le righe contabilizzabili, che abbiano anche il terzo della scadenza selezionata
//        java.util.Vector selectedModels = new java.util.Vector();
//        for (java.util.Enumeration e = bp.getDettaglio().getElements();
//            e.hasMoreElements();
//            ) {
//            Documento_generico_rigaBulk riga =
//                (Documento_generico_rigaBulk) e.nextElement();
//            //se la scadenza ha un debitore di tipo diversi prendo tutte le righe in stato iniziale
//            if (riga.STATO_INIZIALE.equals(riga.getStato_cofi())
//	            && scadenza.getAccertamento().getDebitore().getAnagrafico().getTi_entita().equals(AnagraficoBulk.DIVERSI))
//         		selectedModels.add(riga);
//         	else
//         	//altrimenti prendo quelle in stato iniziale e con lo stesso terzo o di tipo diversi
//            if (riga.STATO_INIZIALE.equals(riga.getStato_cofi())
//                && riga.getTerzo() != null
//                && riga.getTerzo().getCd_terzo() != null
//                && (riga.getTerzo().getCd_terzo().equals(scadenza.getAccertamento().getCd_terzo())
//                    || (riga.getAccertamento_scadenziario() != null
//                        && riga
//                            .getAccertamento_scadenziario()
//                            .getAccertamento()
//                            .getDebitore()
//                            .getAnagrafico()
//                            .getTi_entita()
//                            .equals(AnagraficoBulk.DIVERSI)))
//                || (riga.getTerzo().getAnagrafico() != null
//                    && riga.getTerzo().getAnagrafico().getTi_entita().equals(AnagraficoBulk.DIVERSI)))
//                selectedModels.add(riga);
//        }
//        //controlla che ci sia almeno una riga non contabilizzata
//        if (selectedModels.isEmpty())
//            throw new it.cnr.jada.comp.ApplicationException(
//                "Tutti i dettagli sono gi� stati contabilizzati oppure non sono compatibili i terzi.");
//        //richiama il selezionatore con selezione multipla
//        it.cnr.jada.util.action.SelezionatoreListaBP slbp =
//            (it.cnr.jada.util.action.SelezionatoreListaBP) select(context,
//                new it.cnr.jada.util.ListRemoteIterator(selectedModels),
//                it.cnr.jada.bulk.BulkInfo.getBulkInfo(Documento_generico_rigaBulk.class),
//                "righiSet",
//                "doBringBackAddToCRUDMain_Accertamenti_DettaglioAccertamenti");
//        slbp.setMultiSelection(true);
//        return slbp;
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//public Forward doAddToCRUDMain_Dettaglio(ActionContext context) {
//    //imposta l'azione di aggiunta di un dettaglio (riga)
//    try {
//
//        it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//        Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//        DocumentoGenericoComponentSession component= null;
//        //controlla che il documento non sia pagato
//        //forse � un controllo superfluo...
//        if (documentoGenerico.getStato_cofi() != null && documentoGenerico.getStato_cofi().equals(documentoGenerico.STATO_PAGATO))
//            throw new it.cnr.jada.comp.ApplicationException("Non � possibile inserire righe in un documento pagato");
//
//        //per gli attivi... 
//        if (documentoGenerico.isGenericoAttivo()) {
//            //aggiunge la riga di dettaglio
//             ((CRUDDocumentoGenericoAttivoBP) bp).getDettaglio().add(context);
//            component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//            //aggiorna le modalit� di pagamento
//            documentoGenerico=
//                (Documento_genericoBulk) component.aggiornaModalita(
//                    context.getUserContext(),
//                    documentoGenerico,
//                    (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoAttivoBP) bp).getDettaglio().getModel(),
//                    documentoGenerico.getTerzo_uo_cds());
//
//            bp.setModel(context, documentoGenerico);
//
//        } else {
//            //aggiunge la riga di dettaglio
//             ((CRUDDocumentoGenericoPassivoBP) bp).getDettaglio().add(context);
//        }
//
//        return context.findDefaultForward();
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//public Forward doAddToCRUDMain_Obbligazioni(ActionContext context) {
//    //aggiunge una scadenza dell'obbligazione
//    try {
//        CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) getBusinessProcess(context);
//        bp.getDettaglio().getSelection().clearSelection();
//        fillModel(context);
//        Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//        //richiama il metodo che gestisce la ricerca
//        return basicDoRicercaObbligazione(context, doc, null);
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//public Forward doAddToCRUDMain_Obbligazioni_DettaglioObbligazioni(ActionContext context) {
//    //imposta l'azione di aggiunta di un dettaglio ad una scadenza
//    try {
//        CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) getBusinessProcess(context);
//        bp.getDettaglio().getSelection().clearSelection();
//        fillModel(context);
//        Obbligazione_scadenzarioBulk obbligazione= (Obbligazione_scadenzarioBulk) bp.getObbligazioniController().getModel();
//        Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//
//        //controlla che sia stata selezionata una scadenza sulla quale si vuole aggiungere il dettaglio
//        if (doc.getDocumento_generico_obbligazioniHash() != null && doc.getDocumento_generico_obbligazioniHash().isEmpty() || obbligazione == null)
//            throw new it.cnr.jada.comp.ApplicationException("Selezionare l'impegno a cui associare i dettagli.");
//
//        //estrae solo le righe contabilizzabili e con terzi associati
//        java.util.Vector selectedModels= new java.util.Vector();
//        for (java.util.Enumeration e= bp.getDettaglio().getElements(); e.hasMoreElements();) {
//            Documento_generico_rigaBulk riga= (Documento_generico_rigaBulk) e.nextElement();
//            //se la scadenza ha un creditore di tipo diversi prendo tutte le righe in stato iniziale
//            if (riga.STATO_INIZIALE.equals(riga.getStato_cofi())
//	            && obbligazione.getObbligazione().getCreditore().getAnagrafico().getTi_entita().equals(AnagraficoBulk.DIVERSI))
//         		selectedModels.add(riga);
//         	else
//         	//altrimenti prendo quelle in stato iniziale e con lo stesso terzo o di tipo diversi
//            if (riga.STATO_INIZIALE.equals(riga.getStato_cofi())
//                && riga.getTerzo() != null
//                && riga.getTerzo().getCd_terzo() != null
//                && (riga.getTerzo().getCd_terzo().equals(obbligazione.getObbligazione().getCd_terzo())
//                    || (riga.getObbligazione_scadenziario()!=null && riga.getObbligazione_scadenziario().getObbligazione().getCreditore().getAnagrafico().getTi_entita().equals(AnagraficoBulk.DIVERSI)))
//                || (riga.getTerzo().getAnagrafico().getTi_entita().equals(AnagraficoBulk.DIVERSI)))
//                selectedModels.add(riga);
//        }
//        //controlla che ci sia almeno una riga non contabilizzata
//        if (selectedModels.isEmpty())
//            throw new it.cnr.jada.comp.ApplicationException("Tutti i dettagli sono gi� stati contabilizzati oppure non sono compatibili i terzi.");
//
//        //richiama il selezionatore con selezione multipla
//        it.cnr.jada.util.action.SelezionatoreListaBP slbp=
//            (it.cnr.jada.util.action.SelezionatoreListaBP) select(context,
//                new it.cnr.jada.util.ListRemoteIterator(selectedModels),
//                it.cnr.jada.bulk.BulkInfo.getBulkInfo(Documento_generico_rigaBulk.class),
//                "righiSet",
//                "doBringBackAddToCRUDMain_Obbligazioni_DettaglioObbligazioni");
//        slbp.setMultiSelection(true);
//        return slbp;
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//}
///**
//* Inizializza i valori per il sospeso
//* Creation date: (27/11/2001 17.04.56)
//* @param context it.cnr.jada.action.ActionContext
//* @param doc Documento_genericoBulk
//* @return it.cnr.jada.action.Forward
//*/
//public Forward doBlankSearchFindUnitaMisura(ActionContext context,
//	RichiestaUopRigaBulk riga) 
//	throws java.rmi.RemoteException {
//		
//	try{
//		riga.setCoefConv(null);
//		riga.setUnitaMisura(null);
//		return context.findDefaultForward();
//
//	} catch(Exception e) {
//		return handleException(context,e);
//	}
//}
///**
//* Inizializza i valori per il terzo
//* Creation date: (27/11/2001 17.04.56)
//* @param context it.cnr.jada.action.ActionContext
//* @param doc Documento_genericoBulk
//* @return it.cnr.jada.action.Forward
//*/
//public Forward doBlankSearchTerzo(ActionContext context, Documento_generico_rigaBulk documentoGenericoRiga) throws java.rmi.RemoteException {
//
//    try {
//        //inizializza il terzo
//        basicDoResetTerzo(context, documentoGenericoRiga);
//
//        return context.findDefaultForward();
//
//    } catch (Exception e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//public Forward doBringBackAddToCRUDMain_Accertamenti_DettaglioAccertamenti(ActionContext context) {
////dopo la selezione di una scadenza si occupa di contabilizzare le righe associate
////e calcolare il totale su di questa
//    try {
//        HookForward fwd= (HookForward) context.getCaller();
//        java.util.List selectedModels= (java.util.List) fwd.getParameter("selectedElements");
//        if (selectedModels != null && !selectedModels.isEmpty()) {
//            CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//            Accertamento_scadenzarioBulk accertamento= (Accertamento_scadenzarioBulk) bp.getAccertamentiController().getModel();
//            if (accertamento != null) {
//                //if (accertamento.getAccertamento().getCd_terzo()!=((Documento_generico_rigaBulk)selectedModels.get(0)).getTerzo().getCd_terzo())
//                //throw new it.cnr.jada.comp.ApplicationException("La riga selezionata ha un cliente diverso dall'accertamento");
//                basicDoContabilizzaAccertamenti(context, accertamento, selectedModels);
//                bp.setDirty(true);
//
//            }
//            doCalcolaTotalePerAccertamento(context, accertamento);
//        }
//        return context.findDefaultForward();
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//public Forward doBringBackAddToCRUDMain_Obbligazioni_DettaglioObbligazioni(ActionContext context) {
////dopo la selezione di una scadenza si occupa di contabilizzare le righe associate
////e calcolare il totale su di questa
//    try {
//        HookForward fwd= (HookForward) context.getCaller();
//        java.util.List selectedModels= (java.util.List) fwd.getParameter("selectedElements");
//        if (selectedModels != null && !selectedModels.isEmpty()) {
//            CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) getBusinessProcess(context);
//            Obbligazione_scadenzarioBulk obbligazione= (Obbligazione_scadenzarioBulk) bp.getObbligazioniController().getModel();
//            if (obbligazione != null) {
//                basicDoContabilizza(context, obbligazione, selectedModels);
//                bp.setDirty(true);
//
//            }
//            doCalcolaTotalePerObbligazione(context, obbligazione);
//        }
//        return context.findDefaultForward();
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce una richiesta di assegnamento al crudtool "crea_terzo"
// *
// * @param context	L'ActionContext della richiesta
// * @param documentoGenericoRiga	
// * @param terzo	
// * @return Il Forward alla pagina di risposta
// * @throws RemoteException	Se si verifica qualche eccezione di sistema per cui non � possibile effettuare l'operazione
// */
//public Forward doBringBackCRUDCrea_terzo(ActionContext context, Documento_generico_rigaBulk documentoGenericoRiga, TerzoBulk terzo) throws java.rmi.RemoteException {
////controlla il terzo dopo averlo riportato dall'anagrafica
//    try {
//
//        return doBringBackSearchTerzo(context, documentoGenericoRiga, terzo);
//
//    } catch (Exception e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce un operazione bringback sull'accertamento
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doBringBackOpenAccertamentoWindow(ActionContext context) {
////gestisce un operazione bringback sull'accertamento
//    HookForward caller= (HookForward) context.getCaller();
//    Accertamento_scadenzarioBulk accertamento= (Accertamento_scadenzarioBulk) caller.getParameter("bringback");
//    if (accertamento != null) {
//        try {
//            basicDoBringBackOpenAccertamentiWindow(context, accertamento);
//
//            CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//            bp.getAccertamentiController().reset(context);
//            bp.getAccertamentiController().setModelIndex(context, it.cnr.jada.bulk.BulkCollections.indexOfByPrimaryKey(bp.getAccertamentiController().getDetails(), accertamento));
//
//            doCalcolaTotalePerAccertamento(context, (Accertamento_scadenzarioBulk) bp.getAccertamentiController().getModel());
//
//            bp.setDirty(true);
//        } catch (Throwable t) {
//            return handleException(context, t);
//        }
//    }
//    return context.findDefaultForward();
//}
///**
// * Gestisce un operazione bringback sull'obbligazione
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doBringBackOpenObbligazioniWindow(ActionContext context) {
////gestisce un operazione bringback sull'obbligazione
//
//    HookForward caller= (HookForward) context.getCaller();
//    Obbligazione_scadenzarioBulk obblig= (Obbligazione_scadenzarioBulk) caller.getParameter("bringback");
//    if (obblig != null) {
//        try {
//            basicDoBringBackOpenObbligazioniWindow(context, obblig);
//
//            CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) getBusinessProcess(context);
//            bp.getObbligazioniController().reset(context);
//            bp.getObbligazioniController().setModelIndex(context, it.cnr.jada.bulk.BulkCollections.indexOfByPrimaryKey(bp.getObbligazioniController().getDetails(), obblig));
//
//            doCalcolaTotalePerObbligazione(context, (Obbligazione_scadenzarioBulk) bp.getObbligazioniController().getModel());
//
//            bp.setDirty(true);
//        } catch (Throwable t) {
//            return handleException(context, t);
//        }
//    }
//    return context.findDefaultForward();
//}
///**
// * Gestisce il comando di rimozione di un dettaglio su un CRUDController
// * figlio del ricevente
// */
//public Forward doBringBackRemoveToCRUDMain_Dettaglio(ActionContext context) {
////rimozione di un dettaglio
//    try {
//        HookForward fwd= (HookForward) context.getCaller();
//        java.util.List selectedModels= (java.util.List) fwd.getParameter("selectedElements");
//        if (selectedModels != null && !selectedModels.isEmpty()) {
//            CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) getBusinessProcess(context);
//            Obbligazione_scadenzarioBulk obbligazione= (Obbligazione_scadenzarioBulk) bp.getObbligazioniController().getModel();
//            if (obbligazione != null) {
//                basicDoContabilizza(context, obbligazione, selectedModels);
//                bp.setDirty(true);
//            }
//            doCalcolaTotalePerObbligazione(context, obbligazione);
//        }
//        return context.findDefaultForward();
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce una richiesta di ricerca del searchtool "listabanche"
// *
// * @param context	L'ActionContext della richiesta
// * @param documentoGenericoRiga	L'OggettoBulk padre del searchtool
// * @param banca	L'OggettoBulk selezionato dall'utente
// * @return Il Forward alla pagina di risposta
// * @throws RemoteException	Se si verifica qualche eccezione di sistema per cui non � possibile effettuare l'operazione
// */
//public Forward doBringBackSearchListabanche(ActionContext context, Documento_generico_rigaBulk documentoGenericoRiga, BancaBulk banca) throws java.rmi.RemoteException {
////imposta la banca selezionata
//    try {
//    	fillModel(context);
//		it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//		DocumentoGenericoComponentSession component= null;
//
//		Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//		if (!documentoGenerico.isGenericoAttivo()) {
//			component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoPassivoBP) bp).createComponentSession();
//		} else {
//			component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//		}
//
//	    if (banca != null) {//per attivi la banca � quella sulla uo
//	        if (documentoGenericoRiga.getDocumento_generico().isGenericoAttivo()) {
//	            documentoGenericoRiga.setBanca_uo_cds(banca);
//	        } else
//	        //per passivi � quella del terzo
//	            documentoGenericoRiga.setBanca(banca);
//	        	documentoGenericoRiga.setCessionario(component.findCessionario(context.getUserContext(), documentoGenericoRiga));
//	    }
//        return context.findDefaultForward();
//
//    } catch (Exception e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce una richiesta di ricerca del searchtool "sospeso"
// *
// * @param context	L'ActionContext della richiesta
// * @param doc	L'OggettoBulk padre del searchtool
// * @param sospesoTrovato	L'OggettoBulk selezionato dall'utente
// * @return Il Forward alla pagina di risposta
// * @throws RemoteException	Se si verifica qualche eccezione di sistema per cui non � possibile effettuare l'operazione
// */
public Forward doBringBackSearchFindUnitaMisura(ActionContext context,
	RichiestaUopRigaBulk riga,
	UnitaMisuraBulk unitaMisura) 
	throws java.rmi.RemoteException {

	riga.setUnitaMisura(unitaMisura);
	((CRUDBP)context.getBusinessProcess()).setDirty(true);
		
	try{
		if (unitaMisura!=null && riga.getBeneServizio() != null && riga.getBeneServizio().getUnitaMisura() != null && unitaMisura.getCdUnitaMisura().equals(riga.getBeneServizio().getUnitaMisura().getCdUnitaMisura())) {
			riga.setCoefConv(BigDecimal.ONE);
		} else {
			riga.setCoefConv(null);
		}
		return context.findDefaultForward();

	} catch(Exception e) {
		return handleException(context,e);
	}
}
public Forward doBringBackSearchFindBeneServizio(ActionContext context,
		RichiestaUopRigaBulk riga,
		Bene_servizioBulk bene) 
		throws java.rmi.RemoteException {

		riga.setBeneServizio(bene);
		((CRUDBP)context.getBusinessProcess()).setDirty(true);
			
		if (bene.getUnitaMisura() != null){
			riga.setUnitaMisura(bene.getUnitaMisura());
			riga.setCoefConv(BigDecimal.ONE);
		}
//		try{
//			if (riga.getUnitaMisura()!=null && riga.getUnitaMisura().getCdUnitaMisura()!=null && riga.getBeneServizio() != null && riga.getBeneServizio().getUnitaMisura() != null && riga.getUnitaMisura().getCdUnitaMisura().equals(riga.getBeneServizio().getUnitaMisura().getCdUnitaMisura())) {
//				riga.setCoefConv(BigDecimal.ONE);
//			} else {
//				riga.setCoefConv(null);
//			}
//			return context.findDefaultForward();
//
//		} catch(Exception e) {
//			return handleException(context,e);
//		}
		return context.findDefaultForward();
}
///**
// * Gestisce una richiesta di ricerca del searchtool "terzo"
// *
// * @param context	L'ActionContext della richiesta
// * @param documentoGenericoRiga	L'OggettoBulk padre del searchtool
// * @param terzo	L'OggettoBulk selezionato dall'utente
// * @return Il Forward alla pagina di risposta
// * @throws RemoteException	Se si verifica qualche eccezione di sistema per cui non � possibile effettuare l'operazione
// */
//public Forward doBringBackSearchTerzo(ActionContext context, Documento_generico_rigaBulk documentoGenericoRiga, TerzoBulk terzo) throws java.rmi.RemoteException {
////effettua una serie di controlli di validazione del terzo selezionato
////che pu� essere o da ricerca o da riporta dell'anagrafica
//    try {
//        if (terzo != null) {
//            //inizializza il terzo
//            basicDoResetTerzo(context, documentoGenericoRiga);
//            //if (!documentoGenericoRiga.getDocumento_generico().isGenericoAttivo()) {
//            //if (terzo.getAnagrafico().getTi_entita().equals(AnagraficoBulk.DIVERSI) || terzo.getTi_terzo().equals(TerzoBulk.CREDITORE)) {
//            //throw new it.cnr.jada.comp.ApplicationException("Il terzo selezionato non � un debitore");
//            //}
//            //} else if (terzo.getAnagrafico().getTi_entita().equals(AnagraficoBulk.DIVERSI) || terzo.getTi_terzo().equals(TerzoBulk.DEBITORE)) {
//            //throw new it.cnr.jada.comp.ApplicationException("Il terzo selezionato non � un creditore");
//            //}
//            
//            //per passivi controlla che non siano 'creditori'
//            if (!documentoGenericoRiga.getDocumento_generico().isGenericoAttivo()) {
//                if (terzo.getTi_terzo().equals(TerzoBulk.DEBITORE)) {
//                    throw new it.cnr.jada.comp.ApplicationException("Il terzo selezionato non pu� essere un debitore");
//                }            
//            //per attivi controlla che non siano 'debitori'
//            } else if (terzo.getTi_terzo().equals(TerzoBulk.CREDITORE)) {
//                throw new it.cnr.jada.comp.ApplicationException("Il terzo selezionato non pu� essere un creditore");
//            }
//
//            //il terzo selezionato ha la data di fine rapporto antecedente alla data di registrazione
//            if (terzo.getDt_fine_rapporto() != null
//                && documentoGenericoRiga.getDocumento_generico().getData_registrazione() != null
//                && terzo.getDt_fine_rapporto().before(documentoGenericoRiga.getDocumento_generico().getData_registrazione()))
//                throw new it.cnr.jada.comp.ApplicationException("Attenzione, il terzo selezionato ha la data di fine rapporto antecedente alla data di registrazione");
//
//            // richiama il component per il generico attivo o il passivo
//            it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//            Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//            DocumentoGenericoComponentSession component= null;
//            if (!documentoGenerico.isGenericoAttivo())
//                component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoPassivoBP) bp).createComponentSession();
//            else
//                component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//
//            //gestisce i valori da inserire dalla component
//            documentoGenerico= (Documento_genericoBulk) component.completaTerzo(context.getUserContext(), documentoGenerico, documentoGenericoRiga, terzo);
//            bp.setModel(context, documentoGenerico);
//        }
//
//        return context.findDefaultForward();
//
//    } catch (Exception e) {
//        return handleException(context, e);
//    }
//}
///**
// *calcola il totale dei dettagli per la scadenza selezionata
// *il valore � visualizzato del tab accertamento/obbligazioni
// *e viene preso come riferimento per l'aggiornamento in automatico
// *
// * @param context	L'ActionContext della richiesta
// * @param accertamento	
// * @return Il Forward alla pagina di risposta
// */
//public Forward doCalcolaTotalePerAccertamento(ActionContext context, it.cnr.contab.doccont00.core.bulk.Accertamento_scadenzarioBulk accertamento) {
////calcola il totale dei dettagli per la scadenza selezionata
////il valore � visualizzato del tab accertamento/obbligazioni
////e viene preso come riferimento per l'aggiornamento in automatico
//
//    it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//    Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//    if (documentoGenerico.getDocumento_generico_accertamentiHash() != null)
//        try {
//	        //utilizza il metodo calcolaTotaleSelezionati
//            documentoGenerico.setImportoTotalePerAccertamento(calcolaTotaleSelezionati((java.util.List) documentoGenerico.getDocumento_generico_accertamentiHash().get(accertamento)));
//        } catch (it.cnr.jada.comp.ApplicationException e) {
//	        //imposta 0
//            documentoGenerico.setImportoTotalePerAccertamento(new java.math.BigDecimal(0).setScale(2, java.math.BigDecimal.ROUND_HALF_UP));
//        }
//    return context.findDefaultForward();
//}
///**
// *Calcola il totale dei dettagli per la scadenza selezionata
// *il valore � visualizzato del tab accertamento/obbligazioni
// *e viene preso come riferimento per l'aggiornamento in automatico
// *
// * @param context	L'ActionContext della richiesta
// * @param obbligazione	
// * @return Il Forward alla pagina di risposta
// */
//public Forward doCalcolaTotalePerObbligazione(ActionContext context, it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk obbligazione) {
////calcola il totale dei dettagli per la scadenza selezionata
////il valore � visualizzato del tab accertamento/obbligazioni
////e viene preso come riferimento per l'aggiornamento in automatico
//
//    it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//    Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//    if (documentoGenerico.getDocumento_generico_obbligazioniHash() != null)
//        try {
//            documentoGenerico.setImportoTotalePerObbligazione(calcolaTotaleSelezionati((java.util.List) documentoGenerico.getDocumento_generico_obbligazioniHash().get(obbligazione)));
//        } catch (it.cnr.jada.comp.ApplicationException e) {
//            documentoGenerico.setImportoTotalePerObbligazione(new java.math.BigDecimal(0).setScale(2, java.math.BigDecimal.ROUND_HALF_UP));
//        }
//    return context.findDefaultForward();
//}
///**
// * Calcola i totali di riga
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doCalcolaTotaliDiRiga(ActionContext context) {
//
//    // richiama il component per il generico attivo o il passivo
//    it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//    DocumentoGenericoComponentSession component= null;
//    Documento_generico_rigaBulk riga= null;
//    Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//    try {
//        //DivisaBulk divisa= documentoGenerico.getValuta();
//        fillModel(context);
//
//        if (!documentoGenerico.isGenericoAttivo()) {
//            component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoPassivoBP) bp).createComponentSession();
//            riga= (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoPassivoBP) bp).getDettaglio().getModel();
//        } else {
//            component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//            riga= (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoAttivoBP) bp).getDettaglio().getModel();
//        }
//        //imposta la valuta
//
//        if (riga.getIm_riga_divisa()==null)
//        	riga.setIm_riga_divisa(new BigDecimal(0));
//
//        //richiama il metodo del bulk per il calcolo del totale
//        //java.math.BigDecimal pu= riga.getIm_riga();
//        riga.calcolaCampiDiRiga();
//
//        //chiama i metodi dove vengono calcolati i totali per le obbl/acc
//        //if (!documentoGenerico.isGenericoAttivo()) {
//            //doSelectObbligazioni(context);
//        //} else {
//            //doSelectAccertamenti(context);
//        //}
//        documentoGenerico=(Documento_genericoBulk)component.calcoloConsuntivi(context.getUserContext(),documentoGenerico);
//        bp.setModel(context, documentoGenerico);
//        fillModel(context);
//
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//    return context.findDefaultForward();
//}
///**
// * Viene richiamato nel momento in cui si seleziona una valuta dal combo Valuta nella 
// * testata del documento.
// * Richiama a sua volta il metodo cercaCambio dalla component.
// */
//public Forward doCambiaDataPagamento(ActionContext context) {
//    it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//    java.sql.Timestamp dataPagamentoOld= ((Documento_genericoBulk) bp.getModel()).getData_registrazione();
//
//    try {
//        // richiama il component per il generico attivo o il passivo
//        DocumentoGenericoComponentSession component= null;
//
//        fillModel(context);
//
//        Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//        java.sql.Timestamp dataPagamento= documentoGenerico.getData_registrazione();
//        if (dataPagamento == null) {
//            documentoGenerico.setData_registrazione(dataPagamentoOld);
//            throw new it.cnr.jada.comp.ApplicationException("Inserire una data di registrazione valida!");
//        }
//
//        //controlla se esiste un cambio relativo alla data di registrazione del documento generico
//        if (dataPagamento != null && !dataPagamento.equals(documentoGenerico.getInizio_validita_valuta()) && !dataPagamento.equals(documentoGenerico.getFine_validita_valuta())) {
//            if (documentoGenerico.getInizio_validita_valuta() == null
//                || documentoGenerico.getFine_validita_valuta() == null
//                || !(dataPagamento.after(documentoGenerico.getInizio_validita_valuta()) && dataPagamento.before(documentoGenerico.getFine_validita_valuta()))) {
//                try {
//                    if (!documentoGenerico.isGenericoAttivo())
//                        component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoPassivoBP) bp).createComponentSession();
//                    else
//                        component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//
//                    documentoGenerico= component.cercaCambio(context.getUserContext(), documentoGenerico);
//                    return context.findDefaultForward();
//                } catch (Throwable e) {
//                    ((Documento_genericoBulk) bp.getModel()).setData_registrazione(dataPagamentoOld);
//                    return handleException(context, e);
//                }
//            }
//        }
//
//        //controlla che sulle righe siano impostati terzi con data fine rapporto valide
//        if (dataPagamento != null && !documentoGenerico.getDocumento_generico_dettColl().isEmpty()) {
//            for (java.util.Iterator i= documentoGenerico.getDocumento_generico_dettColl().iterator(); i.hasNext();) {
//                Documento_generico_rigaBulk riga= (Documento_generico_rigaBulk) i.next();
//                if (riga.getTerzo().getDt_fine_rapporto() != null && riga.getTerzo().getDt_fine_rapporto().before(documentoGenerico.getData_registrazione())) {
//                    documentoGenerico.setData_registrazione(dataPagamentoOld);
//                    throw new it.cnr.jada.comp.ApplicationException(
//                        "La data di registrazione non � valida poiche' il terzo impostato per " + ((riga.getDs_riga() == null) ? "una riga" : "la riga '" + riga.getDs_riga() + "'") + " ha una data fine rapporto precedente");
//                }
//            }
//        }
//
//        return context.findDefaultForward();
//    } catch (Throwable e) {
//        ((Documento_genericoBulk) bp.getModel()).setData_registrazione(dataPagamentoOld);
//        return handleException(context, e);
//    }
//
//}
///**
// * Crea e imposta la lettera di pagamento estero
// */ 
//public Forward doCancellaLettera(ActionContext context) {
//
//	try {
//		fillModel(context);
//		CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//		Documento_genericoBulk model = (Documento_genericoBulk)bp.getModel();
//		if (model != null) {
//			if (model.getLettera_pagamento_estero() == null)
//				return handleException(
//							context,
//							new it.cnr.jada.comp.ApplicationException("Lettera di pagamento estero NON presente!"));
//
//			Lettera_pagam_esteroBulk lettera = model.getLettera_pagamento_estero();
//			int status = lettera.getCrudStatus();
//			if (status == OggettoBulk.UNDEFINED || status == OggettoBulk.TO_BE_CREATED) {
//				model.setLettera_pagamento_estero(null);
//			
//			} else {
//				model = ((DocumentoGenericoComponentSession)bp.createComponentSession()).eliminaLetteraPagamentoEstero(context.getUserContext(), model);
//				bp.setModel(context, model);
//			}
//		}
//		return context.findDefaultForward();
//	} catch(Throwable e) {
//		return handleException(context,e);
//	}
//}
///**
// * Gestisce una richiesta di ricerca.
// *
// * L'implementazione di default utilizza il metodo astratto <code>read</code>
// * di <code>CRUDBusinessProcess</code>.
// * Se la ricerca fornisce pi� di un risultato viene creato un
// * nuovo <code>SelezionatoreListaBP</code> per la selezione di un elemento.
// * Al business process viene anche chiesto l'elenco delle colonne da
// * visualizzare.
// */
//public Forward doCerca(ActionContext context) throws java.rmi.RemoteException,InstantiationException,javax.ejb.RemoveException {
//
//	//per spesa
//	if (context.getBusinessProcess() instanceof CRUDDocumentoGenericoPassivoBP){
//		CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)context.getBusinessProcess();
//		if (bp instanceof IDocumentoAmministrativoSpesaBP && ((IDocumentoAmministrativoSpesaBP)bp).isSpesaBP())
//			return basicDoCerca(context);
//	}
//	return super.doCerca(context);
//}
///**
// * Contabilizza gli accertamenti
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doContabilizzaAccertamenti(ActionContext context) {
//
//    HookForward caller= (HookForward) context.getCaller();
//    Accertamento_scadenzarioBulk scadenza= (Accertamento_scadenzarioBulk) caller.getParameter("accertamentoSelezionato");
//    CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) context.getBusinessProcess();
//    java.util.List selectedModels= null;
//    try {
//        selectedModels= bp.getDettaglio().getSelectedModels(context);
//        bp.getDettaglio().getSelection().clearSelection();
//    } catch (Throwable e) {
//    }
//
//    if (scadenza != null) {
//        try {
//	        Documento_genericoBulk doc = (Documento_genericoBulk)bp.getModel();
//			Accertamento_scadenzarioBulk accertamento = null;
//			AccertamentiTable accHash = doc.getAccertamentiHash();
//			if (accHash != null && !accHash.isEmpty())
//				accertamento = accHash.getKey(scadenza);
//			if (accertamento != null && scadenza.getAccertamento().isTemporaneo()) {
//				java.util.Vector models = ((java.util.Vector)accHash.get(accertamento));
//				java.util.Vector clone = (java.util.Vector)models.clone();
//				if (!clone.isEmpty()) {
//					scollegaDettagliDaAccertamento(context, clone);
//					clone.addAll(selectedModels);
//					basicDoContabilizzaAccertamenti(context, scadenza, selectedModels);
//				} else {
//					accHash.remove(accertamento);
//					basicDoContabilizzaAccertamenti(context, scadenza, selectedModels);
//				}
//			} else {
//				basicDoContabilizzaAccertamenti(context, scadenza, selectedModels);
//			}            
//        } catch (it.cnr.jada.comp.ComponentException e) {
//            return handleException(context, e);
//        }
//        bp.getAccertamentiController().getSelection().clear();
//        bp.getAccertamentiController().setModelIndex(context, -1);
//        bp.getAccertamentiController().setModelIndex(context, it.cnr.jada.bulk.BulkCollections.indexOfByPrimaryKey(bp.getAccertamentiController().getDetails(), scadenza));
//
//        bp.setDirty(true);
//        if (!"tabDocumentoGenericoAccertamenti".equals(bp.getTab("tab")))
//            bp.setTab("tab", "tabDocumentoGenericoAccertamenti");
//    }
//    return context.findDefaultForward();
//}
///**
// * Contabilizza le obbligazioni
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doContabilizzaObbligazioni(ActionContext context) {
//
//	HookForward caller = (HookForward) context.getCaller();
//	Obbligazione_scadenzarioBulk scadenza =
//		(Obbligazione_scadenzarioBulk) caller.getParameter("obbligazioneSelezionata");
//	CRUDDocumentoGenericoPassivoBP bp =
//		(CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
//	java.util.List selectedModels = null;
//	try {
//		selectedModels = bp.getDettaglio().getSelectedModels(context);
//		bp.getDettaglio().getSelection().clearSelection();
//	} catch (Throwable e) {
//	}
//	if (scadenza != null) {
//		try {
//			Documento_genericoBulk doc = (Documento_genericoBulk) bp.getModel();
//			Obbligazione_scadenzarioBulk obbligazione = null;
//			/***** Correzzione Err. 823 - Borriello Gennaro *****/
//			if (bp.isEditing()) {
//				java.util.Enumeration e = doc.getDocumentiContabiliCancellati().elements();
//				while (e.hasMoreElements()) {
//					Obbligazione_scadenzarioBulk scad_cancellata =
//						(Obbligazione_scadenzarioBulk) e.nextElement();
//					if (scad_cancellata.equalsByPrimaryKey(scadenza)) {
//						return handleException(
//							context,
//							new it.cnr.jada.bulk.ValidationException(
//								"Attenzione: Operazione non consentita.\nLa scadenza selezionata � stata precedentemente scollegata da questo documento."));
//					}
//				}
//			}
//			/****************************************************/
//			ObbligazioniTable obbHash = doc.getObbligazioniHash();
//			if (obbHash != null && !obbHash.isEmpty())
//				obbligazione = obbHash.getKey(scadenza);
//			if (obbligazione != null && obbligazione.getObbligazione().isTemporaneo()) {
//				java.util.Vector models = ((java.util.Vector) obbHash.get(obbligazione));
//				java.util.Vector clone = (java.util.Vector) models.clone();
//				if (!clone.isEmpty()) {
//					scollegaDettagliDaObbligazione(context, clone);
//					clone.addAll(selectedModels);
//					basicDoContabilizza(context, scadenza, clone);
//				} else {
//					obbHash.remove(obbligazione);
//					basicDoContabilizza(context, scadenza, selectedModels);
//				}
//			} else {
//				basicDoContabilizza(context, scadenza, selectedModels);
//			}
//		} catch (it.cnr.jada.comp.ComponentException e) {
//			return handleException(context, e);
//		}
//		bp.getObbligazioniController().getSelection().clear();
//		bp.getObbligazioniController().setModelIndex(context, -1);
//		bp.getObbligazioniController().setModelIndex(
//			context,
//			it.cnr.jada.bulk.BulkCollections.indexOfByPrimaryKey(
//				bp.getObbligazioniController().getDetails(),
//				scadenza));
//
//		bp.setDirty(true);
//		if (!"tabDocumentoGenericoObbligazioni".equals(bp.getTab("tab")))
//			bp.setTab("tab", "tabDocumentoGenericoObbligazioni");
//	}
//	return context.findDefaultForward();
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */ 
//public Forward doCreaLettera(ActionContext context) {
//
//	try {
//		fillModel(context);
//		CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//		Documento_genericoBulk model = (Documento_genericoBulk)bp.getModel();
//		if (model != null) {
//			if (model.getLettera_pagamento_estero() == null) {
//				Lettera_pagam_esteroBulk lettera = new Lettera_pagam_esteroBulk(
//															model.getCd_cds(),
//											 				model.getCd_unita_organizzativa(),
//															it.cnr.contab.utenze00.bp.CNRUserContext.getEsercizio(context.getUserContext()),
//															null);
//				lettera.completeFrom(context);
//				lettera.setToBeCreated();
//				model.setLettera_pagamento_estero(lettera);
//				//Annullo la selezione sul fondo economale nel caso in cui venga
//				//creata la lettera 1210
//				model.setStato_pagamento_fondo_eco(model.NO_FONDO_ECO);
//			} else {
//				return handleException(
//							context,
//							new it.cnr.jada.comp.ApplicationException("La lettera per il pagamento estero � gi� stata creata!"));
//			}
//		}
//		return context.findDefaultForward();
//	} catch(Throwable e) {
//		return handleException(context,e);
//	}
//}
//public Forward doDisassociaLettera(ActionContext context) {
//
//	try {
//		fillModel(context);
//			CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//			Documento_genericoBulk model = (Documento_genericoBulk)bp.getModel();
//			CNRUserInfo ui = (CNRUserInfo)context.getUserInfo();
//			UtenteBulk utente = ui.getUtente();
//			if (utente.isSupervisore()){
//				if (model != null) {
//					if (model.getLettera_pagamento_estero() != null) {
//						model = ((DocumentoGenericoComponentSession)bp.createComponentSession()).eliminaLetteraPagamentoEstero(context.getUserContext(), model,false);
//						bp.setModel(context, model);
//					}
//				}
//			}else throw new it.cnr.jada.comp.ApplicationException("Utente non abilitato!");
//		return context.findDefaultForward();
//	} catch(Throwable e) {
//		return handleException(context,e);
//	}
//}
//public Forward doElimina(ActionContext context) throws java.rmi.RemoteException {
//
//    it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//    Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//
//    if (!documentoGenerico.isGenericoAttivo()) {
//
//        try {
//            fillModel(context);
//
//            if (!bp.isEditing()) {
//                bp.setMessage("Non � possibile cancellare in questo momento");
//            } else {
//                bp.delete(context);
//                bp.commitUserTransaction();
//                if (documentoGenerico.isVoidable()) {
//                    bp.setMessage("Annullamento effettuato.");
//                    bp.edit(context, bp.getModel());
//                } else {
//                    bp.reset(context);
//                    bp.setMessage("Cancellazione effettuata.");
//                }
//            }
//            return context.findDefaultForward();
//        } catch (Throwable e) {
//            try {
//                bp.rollbackUserTransaction();
//                bp.edit(context, bp.getModel());
//            } catch (it.cnr.jada.action.BusinessProcessException ex) {
//                return handleException(context, ex);
//            }
//            return handleException(context, e);
//        }
//    } else if (documentoGenerico.isGenericoAttivo()) {
//        try {
//            fillModel(context);
//
//            if (!bp.isEditing()) {
//                bp.setMessage("Non � possibile cancellare in questo momento");
//            } else {
//                bp.delete(context);
//                bp.commitUserTransaction();
//                if (documentoGenerico.isVoidable()) {
//                    bp.setMessage("Annullamento effettuato.");
//                    bp.edit(context, bp.getModel());
//                } else {
//                    bp.reset(context);
//                    bp.setMessage("Cancellazione effettuata.");
//                }
//            }
//            return context.findDefaultForward();
//
//        } catch (Throwable e) {
//            try {
//                bp.rollbackUserTransaction();
//                bp.edit(context, bp.getModel());
//            } catch (it.cnr.jada.action.BusinessProcessException ex) {
//                return handleException(context, ex);
//            }
//            return handleException(context, e);
//        }
//    }
//    return context.findDefaultForward();
//}
///**
// * Modifica le scadenze in automatico
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @param prefix	
// * @return Il Forward alla pagina di risposta
// */
//public Forward doModificaScadenzaInAutomatico(ActionContext context, String prefix) {
//
//    try {
//        fillModel(context);
//        it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//        DocumentoGenericoComponentSession component= null;
//        Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//
//        if (!documentoGenerico.isGenericoAttivo()) {
//            if (documentoGenerico.getTipo_documento() != null && documentoGenerico.getTipo_documento().getFl_solo_partita_giro().booleanValue())
//                throw new it.cnr.jada.comp.ApplicationException("Operazione non consentita per generici solo su partite di giro");
//
//            if (documentoGenerico.isPassivo_ente())
//                throw new it.cnr.jada.comp.ApplicationException("Impossibile modificare in automatico per generici passivi su ente!");
//            Obbligazione_scadenzarioBulk scadenza= (Obbligazione_scadenzarioBulk) ((CRUDDocumentoGenericoPassivoBP) bp).getObbligazioniController().getModel();
//            if (scadenza == null)
//                throw new it.cnr.jada.comp.ApplicationException("Selezionare l'impegno da modificare in automatico!");
//            java.math.BigDecimal importoScadenza=
//                getImportoPerAggiornamentoScadenzaObbligazioniInAutomatico(context, scadenza, documentoGenerico, new java.math.BigDecimal(0).setScale(2, java.math.BigDecimal.ROUND_HALF_UP));
//
//            java.util.Vector righeAssociate= (java.util.Vector) documentoGenerico.getDocumento_generico_obbligazioniHash().get(scadenza);
//            if (righeAssociate == null || righeAssociate.isEmpty())
//                throw new it.cnr.jada.comp.ApplicationException("Associare dei dettagli prima di aggiornare in automatico la scadenza dell'impegno!");
//
//            if (!scadenza.getIm_scadenza().setScale(2).equals(importoScadenza)) {
//
//                it.cnr.contab.doccont00.ejb.ObbligazioneAbstractComponentSession h= it.cnr.contab.doccont00.bp.CRUDVirtualObbligazioneBP.getVirtualComponentSession(context, true);
//                scadenza=
//                    (Obbligazione_scadenzarioBulk) h.modificaScadenzaInAutomatico(
//                        context.getUserContext(),
//                        scadenza,
//                        getImportoPerAggiornamentoScadenzaObbligazioniInAutomatico(context, scadenza, documentoGenerico, new java.math.BigDecimal(0).setScale(2, java.math.BigDecimal.ROUND_HALF_UP)),
//                        false);
//                documentoGenerico.addToDefferredSaldi(scadenza.getObbligazione(), scadenza.getObbligazione().getSaldiInfo());
//                Forward fwd= basicDoBringBackOpenObbligazioniWindow(context, scadenza);
//
//                ((CRUDDocumentoGenericoPassivoBP) bp).getObbligazioniController().getSelection().clear();
//                ((CRUDDocumentoGenericoPassivoBP) bp).getObbligazioniController().setModelIndex(context, -1);
//                ((CRUDDocumentoGenericoPassivoBP) bp).getObbligazioniController().setModelIndex(
//                    context,
//                    it.cnr.jada.bulk.BulkCollections.indexOfByPrimaryKey(((CRUDDocumentoGenericoPassivoBP) bp).getObbligazioniController().getDetails(), scadenza));
//                ((CRUDDocumentoGenericoPassivoBP) bp).setDirty(true);
//                return fwd;
//            } else
//                throw new it.cnr.jada.comp.ApplicationException("La scadenza non ha bisogno di essere aggiornata!");
//
//        } else {
//            Accertamento_scadenzarioBulk scadenza= (Accertamento_scadenzarioBulk) ((CRUDDocumentoGenericoAttivoBP) bp).getAccertamentiController().getModel();
//            if (scadenza == null)
//                throw new it.cnr.jada.comp.ApplicationException("Selezionare l'accertamento da modificare in automatico!");
//            if (documentoGenerico.isFlagEnte() && Numerazione_doc_contBulk.TIPO_ACR_RES.equalsIgnoreCase(scadenza.getAccertamento().getCd_tipo_documento_cont()))
//                throw new it.cnr.jada.comp.ApplicationException("Impossibile modificare in automatico documenti contabili di tipo \"accertamenti residui\"!");
//            java.math.BigDecimal importoScadenza=
//                getImportoPerAggiornamentoScadenzaAccertamentiInAutomatico(context, scadenza, documentoGenerico, new java.math.BigDecimal(0).setScale(2, java.math.BigDecimal.ROUND_HALF_UP));
//            java.util.Vector righeAssociate= (java.util.Vector) documentoGenerico.getDocumento_generico_accertamentiHash().get(scadenza);
//            if (righeAssociate == null || righeAssociate.isEmpty())
//                throw new it.cnr.jada.comp.ApplicationException("Associare dei dettagli prima di aggiornare in automatico la scadenza dell'accertamento!");
//
//            if (!scadenza.getIm_scadenza().setScale(2).equals(importoScadenza)) {
//                it.cnr.contab.doccont00.ejb.AccertamentoAbstractComponentSession h= it.cnr.contab.doccont00.bp.CRUDVirtualAccertamentoBP.getVirtualComponentSession(context, true);
//                scadenza=
//                    (Accertamento_scadenzarioBulk) h.modificaScadenzaInAutomatico(
//                        context.getUserContext(),
//                        scadenza,
//                        getImportoPerAggiornamentoScadenzaAccertamentiInAutomatico(context, scadenza, documentoGenerico, new java.math.BigDecimal(0).setScale(2, java.math.BigDecimal.ROUND_HALF_UP)),
//                        false);
//                documentoGenerico.addToDefferredSaldi(scadenza.getAccertamento(), scadenza.getAccertamento().getSaldiInfo());
//                Forward fwd= basicDoBringBackOpenAccertamentiWindow(context, scadenza);
//
//                ((CRUDDocumentoGenericoAttivoBP) bp).getAccertamentiController().getSelection().clear();
//                ((CRUDDocumentoGenericoAttivoBP) bp).getAccertamentiController().setModelIndex(
//                    context,
//                    it.cnr.jada.bulk.BulkCollections.indexOfByPrimaryKey(((CRUDDocumentoGenericoAttivoBP) bp).getAccertamentiController().getDetails(), scadenza));
//                ((CRUDDocumentoGenericoAttivoBP) bp).setDirty(true);
//                return fwd;
//
//            } else
//                throw new it.cnr.jada.comp.ApplicationException("La scadenza non ha bisogno di essere aggiornata!");
//        }
//    } catch (Exception e) {
//        return handleException(context, e);
//    }
//}
///**
// * Viene richiamato nel momento in cui si seleziona una valuta dal combo Valuta nella 
// * testata deli documenti generici passivi.
// * Richiama a sua volta il metodo cercaCambio dalla component.
// */
//public Forward doOnChangeModified(ActionContext context) {
//    try {
//        CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
//        Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//        //it.cnr.jada.bulk.PrimaryKeyHashtable vecchiTotali= new it.cnr.jada.bulk.PrimaryKeyHashtable();
//        //for (java.util.Iterator i= doc.getDocumento_generico_dettColl().iterator(); i.hasNext();) {
//            //Documento_generico_rigaBulk dettaglio= (Documento_generico_rigaBulk) i.next();
//            ////java.math.BigDecimal vecchioTotale= dettaglio.getIm_imponibile().add(dettaglio.getIm_iva());
//            ////vecchioTotale= vecchioTotale.add(dettaglio.getIm_totale_addebiti());
//            ////vecchiTotali.put(dettaglio, vecchioTotale);
//        //}
//        BigDecimal vecchioCambio=documentoGenerico.getCambio();
//        fillModel(context);
//        BigDecimal cambioAttuale = documentoGenerico.getCambio();
//        if (cambioAttuale == null)
//        	documentoGenerico.setCambio((cambioAttuale = new java.math.BigDecimal(0)));
//        cambioAttuale = cambioAttuale.setScale(4,BigDecimal.ROUND_HALF_UP);
//        documentoGenerico.setCambio(cambioAttuale);
//        if (cambioAttuale.compareTo(new java.math.BigDecimal(0))==0){
//        	documentoGenerico.setCambio(vecchioCambio);
//        	throw new it.cnr.jada.comp.ApplicationException("Non � stato inserito un cambio valido (>0)");
//        }
//        documentoGenerico= basicCalcolaTotale(context);
//        bp.setModel(context, documentoGenerico);
//    } catch (Throwable t) {
//        return handleException(context, t);
//    }
//    return context.findDefaultForward();
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */ 
//public Forward doOnCheckDisponibilitaCassaFailed(
//	ActionContext context,
//	int option) {
//
//	if (option == it.cnr.jada.util.action.OptionBP.OK_BUTTON) {
//		CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//		try {
//			boolean modified = fillModel(context);
//			OptionRequestParameter userConfirmation = new OptionRequestParameter();
//			userConfirmation.setCheckDisponibilitaDiCassaRequired(Boolean.FALSE);
//			bp.setUserConfirm(userConfirmation);
//			if (bp.isBringBack())
//				doConfermaRiporta(context, it.cnr.jada.util.action.OptionBP.OK_BUTTON);
//			else if (bp.isCarryingThrough())
//				doRiportaAvanti(context);
//			else
//				doSalva(context);
//		} catch(Throwable e) {
//			return handleException(context,e);
//		}
//	}
//	return context.findDefaultForward();
//}
///**
// * Metodo utilizzato per gestire la conferma dell'inserimento/modifica di una obbligazione che ha sfondato
// * la disponibilit� per il contratto
// * @param context <code>ActionContext</code> in uso.
// * @param option Esito della risposta alla richiesta di sfondamento
// *
// * @return <code>Forward</code>
// *
// * @exception <code>RemoteException</code>
// *
// */
//public Forward doOnCheckDisponibilitaContrattoFailed( ActionContext context, int option) {
//    if (option == it.cnr.jada.util.action.OptionBP.OK_BUTTON){
//    	it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//        Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//        try 
//        {
//            boolean modified = fillModel(context);
//            OptionRequestParameter userConfirmation = new OptionRequestParameter();
//            userConfirmation.setCheckDisponibilitaContrattoRequired(Boolean.FALSE);
//            if (documentoGenerico.isGenericoAttivo()){
//            	((CRUDDocumentoGenericoAttivoBP)bp).setUserConfirm(userConfirmation);
//            	if (bp.isBringBack())
//            		doConfermaRiporta(context, it.cnr.jada.util.action.OptionBP.OK_BUTTON);
//  				else if (((CRUDDocumentoGenericoAttivoBP)bp).isCarryingThrough())
//  					doRiportaAvanti(context);
//  				else
//  					doSalva(context);
//            }else{
//              ((CRUDDocumentoGenericoPassivoBP)bp).setUserConfirm(userConfirmation);
//              if (bp.isBringBack())
//            	  doConfermaRiporta(context, it.cnr.jada.util.action.OptionBP.OK_BUTTON);
//              else if (((CRUDDocumentoGenericoPassivoBP)bp).isCarryingThrough())
//            	  doRiportaAvanti(context);
//              else
//            	  doSalva(context);              
//            }
//        } 
//        catch(Throwable e){
//            return handleException(context,e);
//        }
//    }
//    return context.findDefaultForward();
//}
//
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//
//public Forward doOnFlagEnteChange(ActionContext context) {
//
//    try {
//        fillModel(context);
//
//        //to be modify
//        it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//        DocumentoGenericoComponentSession component= null;
//        Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//        if (documentoGenerico.isGenericoAttivo())
//	        component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//	    else
//	    	component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoPassivoBP) bp).createComponentSession();
//
//	    if (documentoGenerico.getTipo_documento()!=null &&
//		    documentoGenerico.getTipo_documento().getFl_solo_partita_giro().booleanValue()){
//			documentoGenerico.setFlagEnte(false);
//			throw new it.cnr.jada.comp.ApplicationException("Non � possibile generare dei documenti generici su ente per il tipo selezionato");
//	    	//return context.findDefaultForward();
//		}
//        if (documentoGenerico.isFlagEnte()
//            && ((documentoGenerico.isGenericoAttivo()
//                && (documentoGenerico.getAccertamentiHash() == null || documentoGenerico.getAccertamentiHash().isEmpty()))
//                || (!documentoGenerico.isGenericoAttivo()
//                && (documentoGenerico.getObbligazioniHash() == null || documentoGenerico.getObbligazioniHash().isEmpty())))) {
//            documentoGenerico= component.setEnte(context.getUserContext(), documentoGenerico);
//            documentoGenerico.setPassivo_ente(true);
//        } else {
//            documentoGenerico.setCd_cds(documentoGenerico.getCd_cds_origine());
//            documentoGenerico.setCd_unita_organizzativa(documentoGenerico.getCd_uo_origine());
//            documentoGenerico.setFlagEnte(false);
//            documentoGenerico.setPassivo_ente(false);
//        }
//        bp.setModel(context, documentoGenerico);
//    } catch (Throwable t) {
//        return handleException(context, t);
//    }
//    return context.findDefaultForward();
//}
///**
// * Gestisce il cambiamento del tipo sezionale ricaricandoli
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
// 
//public Forward doOnIstituzionaleCommercialeChange(ActionContext context) {
//
//	try {
//		//CRUDBP bp = (CRUDBP)getBusinessProcess(context);
//		fillModel( context );		
//		return context.findDefaultForward();
//	} catch (Throwable t) {
//		return handleException(context, t);
//	}
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//
//public Forward doOnModalitaPagamentoChange(ActionContext context) {
//
//	try {
//		fillModel(context);
//		it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//		DocumentoGenericoComponentSession component= null;
//		Documento_generico_rigaBulk riga= null;
//		Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//		if (!documentoGenerico.isGenericoAttivo()) {
//			component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoPassivoBP) bp).createComponentSession();
//			riga= (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoPassivoBP) bp).getDettaglio().getModel();
//		} else {
//			component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//			riga= (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoAttivoBP) bp).getDettaglio().getModel();
//		}
//
//		//visualizza la prima banca della lista
//		if (riga.getModalita_pagamento() != null) {
//			java.util.Collection coll= component.findListabanche(context.getUserContext(), riga);
//			riga.setBanca(getBancaDefaultForCdsFrom(context, riga, coll));
//			riga.setCessionario(component.findCessionario(context.getUserContext(), riga));
//		} else
//			riga.setBanca(null);
//
//		bp.setModel(context, documentoGenerico);
//	} catch (Throwable t) {
//		return handleException(context, t);
//	}
//	
//	return context.findDefaultForward();
//}
//    /**
//     * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
//     * figlio del ricevente
//     */
//
//    public Forward doOnModalitaPagamentoUOCDSChange(ActionContext context) {
//
//        try {
//            fillModel(context);
//            it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//            DocumentoGenericoComponentSession component= null;
//            Documento_generico_rigaBulk riga= null;
//            Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//            if (!documentoGenerico.isGenericoAttivo()) {
//                component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoPassivoBP) bp).createComponentSession();
//                riga= (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoPassivoBP) bp).getDettaglio().getModel();
//            } else {
//                component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//                riga= (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoAttivoBP) bp).getDettaglio().getModel();
//            }
//
//            //visualizza la prima banca della lista
//            if (riga.getModalita_pagamento_uo_cds() != null) {
//
//                java.util.List coll= (java.util.List)component.findListabanche(context.getUserContext(), riga);
//                if (coll == null || coll.isEmpty())
//                    throw new it.cnr.jada.comp.ApplicationException("Non e' stato possibile trovare i riferimenti bancari");
//                if (documentoGenerico.isGenericoAttivo()) {
//                	((CRUDDocumentoGenericoAttivoBP)bp).setContoEnte(false);
//					if (!Rif_modalita_pagamentoBulk.BANCARIO.equals(riga.getModalita_pagamento_uo_cds().getTi_pagamento()))
//		            	riga.setBanca_uo_cds(getBancaDefaultForCdsFrom(context, riga, coll));
//		            else {
//			        	BancaBulk banca = component.setContoEnteIn(context.getUserContext(), riga, coll);
//			        	((CRUDDocumentoGenericoAttivoBP)bp).setContoEnte(true);
//			        	if (banca == null)
//			        		banca = getBancaDefaultForCdsFrom(context, riga, coll);
//			        	riga.setBanca_uo_cds(banca);
//		            }
//                } else
//                    riga.setBanca(getBancaDefaultForCdsFrom(context, riga, coll));
//
//            } else {
//                riga.setBanca_uo_cds(null);
//            }
//            bp.setModel(context, documentoGenerico);
//        } catch (Throwable t) {
//            return handleException(context, t);
//        }
//        return context.findDefaultForward();
//    }
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//
//public Forward doOnTipoDocumentoChange(ActionContext context) {
//
//    try {
//        fillModel(context);
//
//        
//        it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//        DocumentoGenericoComponentSession component= null;
//        Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();        
//        //if (documentoGenerico.isGenericoAttivo())
//            //component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//        //else
//            //component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoPassivoBP) bp).createComponentSession();
//
//        if (documentoGenerico.getCd_tipo_doc_amm() !=null && documentoGenerico.getCd_tipo_doc_amm().compareTo(documentoGenerico.GENERICO_S)!=0){
//        	documentoGenerico.setStato_liquidazione(null);
//        	documentoGenerico.setCausale(null);
//        }else if (documentoGenerico.getCd_tipo_doc_amm() !=null && documentoGenerico.getCd_tipo_doc_amm().compareTo(documentoGenerico.GENERICO_S)==0){
//        	if(documentoGenerico.getStato_liquidazione()==null){
//        		documentoGenerico.setStato_liquidazione(documentoGenerico.SOSP);
//        		documentoGenerico.setCausale(documentoGenerico.ATTLIQ);
//        	}
//        }
//        if (documentoGenerico.getTipo_documento() != null && documentoGenerico.getTipo_documento().getFl_solo_partita_giro().booleanValue()) {
//
//	        if (!documentoGenerico.isDefaultValuta()) {
//		        documentoGenerico.setTipo_documento(null);
//	        	throw new it.cnr.jada.comp.ApplicationException("Attenzione, non � possibile selezionare questo tipo di documento per la valuta selezionata.");
//	        }
//
//	        if (documentoGenerico.isFlagEnte() || documentoGenerico.isPassivo_ente()) {
//		        documentoGenerico.setFlagEnte(false);
//	            documentoGenerico.setPassivo_ente(false);
//				documentoGenerico.setCd_cds(documentoGenerico.getCd_cds_origine());
//	            documentoGenerico.setCd_unita_organizzativa(documentoGenerico.getCd_uo_origine());
//	        }
//             
//            documentoGenerico.setStato_pagamento_fondo_eco(documentoGenerico.NO_FONDO_ECO);
//
//            return context.findDefaultForward();
//        }
//    } catch (Throwable t) {
//        return handleException(context, t);
//    }
//    return context.findDefaultForward();
//}
///**
// * Gestisce l'apertura dell'accertamento
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doOpenAccertamentiWindow(ActionContext context) {
//
//        try {
//            CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//            fillModel(context);
//
//            Accertamento_scadenzarioBulk scadenza= (Accertamento_scadenzarioBulk) bp.getAccertamentiController().getModel();
//            if (scadenza == null)
//                throw new it.cnr.jada.comp.ApplicationException("Selezionare un accertamento");
//
//            if (scadenza.getAccertamento().getCd_tipo_documento_cont().equals(Numerazione_doc_contBulk.TIPO_ACR_SIST))
//	            throw new it.cnr.jada.comp.ApplicationException("Impossibile visualizzare l'accertamento selezionato");
//	            
//            boolean viewMode= bp.isViewing() || ((Documento_genericoBulk)bp.getModel()).isAnnullato();
//            if (!viewMode && bp instanceof IDocumentoAmministrativoBP)
//                viewMode= !((IDocumentoAmministrativoBP) bp).getDocumentoAmministrativoCorrente().isEditable();
//            String status= viewMode ? "V" : "M";
//
//            it.cnr.contab.doccont00.bp.CRUDVirtualAccertamentoBP nbp= it.cnr.contab.doccont00.bp.CRUDVirtualAccertamentoBP.getBusinessProcessFor(context, scadenza.getAccertamento(), status + "RSWTh");
//            nbp.edit(context, scadenza.getAccertamento());
//            nbp.selezionaScadenza(scadenza, context);
//
//            context.addHookForward("bringback", this, "doBringBackOpenAccertamentoWindow");
//            HookForward hook= (HookForward) context.findForward("bringback");
//            return context.addBusinessProcess(nbp);
//        } catch (Exception e) {
//            return handleException(context, e);
//        }
//    }
///**
// * Gestisce l'apertura dell'accertamento
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doOpenObbligazioniWindow(ActionContext context) {
//
//    try {
//        CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) getBusinessProcess(context);
//        fillModel(context);
//
//        Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//        Obbligazione_scadenzarioBulk scadenza= (Obbligazione_scadenzarioBulk) bp.getObbligazioniController().getModel();
//        if (scadenza == null)
//            throw new it.cnr.jada.comp.ApplicationException("Selezionare un'obbligazione");
//
//        if ((scadenza.getObbligazione().getCd_tipo_documento_cont().equals(Numerazione_doc_contBulk.TIPO_IMP) || 
//	        scadenza.getObbligazione().getCd_tipo_documento_cont().equals(Numerazione_doc_contBulk.TIPO_IMP_RES)) &&
//        	!scadenza.getObbligazione().getFl_pgiro().booleanValue())
//			throw new it.cnr.jada.comp.ApplicationException("Impossibile visualizzare l'impegno selezionato");
//				
//        String status;
//        if ((!(documentoGenerico.isPassivo_ente() && !scadenza.getObbligazione().getFl_pgiro().booleanValue())) || documentoGenerico.isAnnullato()) 
//        {
//            if (scadenza == null)
//                return context.findDefaultForward();
//
//            boolean viewMode= bp.isViewing() || documentoGenerico.isAnnullato();
//            if (!viewMode && bp instanceof IDocumentoAmministrativoBP)
//                viewMode= !((IDocumentoAmministrativoBP) bp).getDocumentoAmministrativoCorrente().isEditable();
//            status= viewMode ? "V" : "M";
//
//        }
//        else
//	        status= "V";
//        it.cnr.contab.doccont00.bp.CRUDVirtualObbligazioneBP nbp= it.cnr.contab.doccont00.bp.CRUDVirtualObbligazioneBP.getBusinessProcessFor(context, scadenza.getObbligazione(), status + "RSWTh");
//        nbp.edit(context, scadenza.getObbligazione());
//        nbp.selezionaScadenza(scadenza, context);
//
//        context.addHookForward("bringback", this, "doBringBackOpenObbligazioniWindow");
//        HookForward hook= (HookForward) context.findForward("bringback");
//
//        return context.addBusinessProcess(nbp);
//    } catch (Exception e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce una richiesta di cancellazione dal controller "accertamenti"
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doRemoveFromCRUDMain_Accertamenti(ActionContext context) {
//
//    CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) context.getBusinessProcess();
//    it.cnr.jada.util.action.Selection selection= bp.getAccertamentiController().getSelection();
//    try {
//        if (selection.isEmpty())
//            throw new it.cnr.jada.comp.ApplicationException("Selezionare le scadenze che si desidera eliminare!");
//    } catch (it.cnr.jada.comp.ApplicationException e) {
//        return handleException(context, e);
//    }
//    java.util.List accertamenti= bp.getAccertamentiController().getDetails();
//    for (it.cnr.jada.util.action.SelectionIterator i= bp.getAccertamentiController().getSelection().iterator(); i.hasNext();) {
//        Accertamento_scadenzarioBulk accertamento= (Accertamento_scadenzarioBulk) accertamenti.get(i.nextIndex());
//        java.util.Vector models= (java.util.Vector) ((Documento_genericoBulk) bp.getModel()).getDocumento_generico_accertamentiHash().get(accertamento);
//        try {
//            if (models != null && models.isEmpty()) {
//                Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//                doc.getDocumento_generico_accertamentiHash().remove(accertamento);
//                doc.addToDocumentiContabiliCancellati(accertamento);
//            } else {
//                controllaRighePerMandatieAnnullati(models, accertamento, false);
//                scollegaDettagliDaAccertamento(context, (java.util.List) models.clone());
//            }
//        } catch (it.cnr.jada.comp.ComponentException e) {
//            return handleException(context, e);
//        }
//
//        doCalcolaTotalePerAccertamento(context, null);
//
//        Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//        setAndVerifyStatusFor(context, doc);
//
//        bp.getAccertamentiController().getSelection().clear();
//        bp.getAccertamentiController().setModelIndex(context, -1);
//        bp.setDirty(true);
//    }
//    return context.findDefaultForward();
//}
///**
// * Gestisce una richiesta di cancellazione dal controller "accertamenti_DettaglioAccertamenti"
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doRemoveFromCRUDMain_Accertamenti_DettaglioAccertamenti(ActionContext context) {
//
//    CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) context.getBusinessProcess();
//    try {
//        it.cnr.jada.util.action.Selection selection= bp.getDettaglioAccertamentoController().getSelection();
//        if (selection.isEmpty())
//            throw new it.cnr.jada.comp.ApplicationException("Selezionare i dettagli che si desidera scollegare!");
//        java.util.List models= selection.select(bp.getDettaglioAccertamentoController().getDetails());
//        controllaRighePerMandatieAnnullati(models, null, true);
//        scollegaDettagliDaAccertamento(context, models);
//    } catch (it.cnr.jada.comp.ComponentException e) {
//        return handleException(context, e);
//    }
//
//    doCalcolaTotalePerAccertamento(context, (Accertamento_scadenzarioBulk) bp.getAccertamentiController().getModel());
//
//    Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//    setAndVerifyStatusFor(context, doc);
//
//    bp.getDettaglioAccertamentoController().getSelection().clear();
//    bp.getDettaglioAccertamentoController().setModelIndex(context, -1);
//    java.util.List dettagli= bp.getDettaglioAccertamentoController().getDetails();
//    if (dettagli == null || dettagli.isEmpty()) {
//        bp.getAccertamentiController().getSelection().clear();
//        bp.getAccertamentiController().setModelIndex(context, -1);
//    }
//    bp.setDirty(true);
//
//    return context.findDefaultForward();
//}
///**
// * Gestisce una richiesta di cancellazione dal controller "obbligazioni"
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doRemoveFromCRUDMain_Obbligazioni(ActionContext context) {
//
//    CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
//    it.cnr.jada.util.action.Selection selection = bp.getObbligazioniController().getSelection();
//	try {
//		if (selection.isEmpty())
//			throw new it.cnr.jada.comp.ApplicationException("Selezionare le scadenze che si desidera eliminare!");
//	} catch (it.cnr.jada.comp.ApplicationException e) {
//		return handleException(context, e);
//	}
//    java.util.List obbligazioni= bp.getObbligazioniController().getDetails();
//    for (it.cnr.jada.util.action.SelectionIterator i= bp.getObbligazioniController().getSelection().iterator(); i.hasNext();) {
//        Obbligazione_scadenzarioBulk obbligazione= (Obbligazione_scadenzarioBulk) obbligazioni.get(i.nextIndex());
//        java.util.Vector models= (java.util.Vector) ((Documento_genericoBulk) bp.getModel()).getDocumento_generico_obbligazioniHash().get(obbligazione);
//        try {
//            if (models != null && models.isEmpty()) {
//                Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//                doc.getDocumento_generico_obbligazioniHash().remove(obbligazione);
//                doc.addToDocumentiContabiliCancellati(obbligazione);
//            } else {
//                controllaRighePerMandatieAnnullati(models, obbligazione);
//                java.util.List clone = (java.util.List) models.clone();
//                scollegaDettagliDaObbligazione(context, clone);
//		        resetImportoIniziale(context, clone);
//            }
//        } catch (it.cnr.jada.comp.ComponentException e) {
//            return handleException(context, e);
//        }
//
//        doCalcolaTotalePerObbligazione(context, null);
//
//        Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//        setAndVerifyStatusFor(context, doc);
//
//        bp.getObbligazioniController().getSelection().clear();
//        bp.getObbligazioniController().setModelIndex(context, -1);
//        bp.setDirty(true);
//    }
//    return context.findDefaultForward();
//}
///**
// * Gestisce una richiesta di cancellazione dal controller "obbligazioni_DettaglioObbligazioni"
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doRemoveFromCRUDMain_Obbligazioni_DettaglioObbligazioni(ActionContext context) {
//
//    CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
//    try {
//        it.cnr.jada.util.action.Selection selection = bp.getDettaglioObbligazioneController().getSelection();
//		if (selection.isEmpty())
//			throw new it.cnr.jada.comp.ApplicationException("Selezionare i dettagli che si desidera scollegare!");
//		java.util.List models = selection.select(bp.getDettaglioObbligazioneController().getDetails());
//        controllaRighePerMandatieAnnullati(models, null, true);
//        scollegaDettagliDaObbligazione(context, models);
//        resetImportoIniziale(context, models);
//    } catch (it.cnr.jada.comp.ComponentException e) {
//        return handleException(context, e);
//    }
//
//    doCalcolaTotalePerObbligazione(context, (Obbligazione_scadenzarioBulk) bp.getObbligazioniController().getModel());
//
//    Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//    setAndVerifyStatusFor(context, doc);
//
//    bp.getDettaglioObbligazioneController().getSelection().clear();
//    bp.getDettaglioObbligazioneController().setModelIndex(context, -1);
//    java.util.List dettagli= bp.getDettaglioObbligazioneController().getDetails();
//    if (dettagli == null || dettagli.isEmpty()) {
//        bp.getObbligazioniController().getSelection().clear();
//        bp.getObbligazioniController().setModelIndex(context, -1);
//    }
//    bp.setDirty(true);
//
//    return context.findDefaultForward();
//}
///**
// * Gestisce la ricerca degli accertamenti
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doRicercaAccertamento(ActionContext context) {
//
//        try {
//            CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//            fillModel(context);
//            java.util.List models= bp.getDettaglio().getSelectedModels(context);
//            Forward forward= context.findDefaultForward();
//            if (models == null || models.isEmpty())
//                bp.setErrorMessage("Per procedere, selezionare i dettagli da contabilizzare!");
//            else {
//                controllaSelezione(models.iterator());
//                Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//                forward= basicDoRicercaAccertamento(context, doc, models);
//            }
//            return forward;
//        } catch (Throwable e) {
//            return handleException(context, e);
//        }
//    }
///**
// * Gestisce la ricerca delle obbligazioni
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doRicercaObbligazione(ActionContext context) {
//
//        try {
//            CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) getBusinessProcess(context);
//            fillModel(context);
//            java.util.List models= bp.getDettaglio().getSelectedModels(context);
//            Forward forward= context.findDefaultForward();
//            if (models == null || models.isEmpty())
//                bp.setErrorMessage("Per procedere, selezionare i dettagli da contabilizzare!");
//            else {
//                controllaSelezione(models.iterator());
//                Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//                forward= basicDoRicercaObbligazione(context, doc, models);
//            }
//            return forward;
//        } catch (Throwable e) {
//            return handleException(context, e);
//        }
//    }
//public Forward doRiportaAvanti(ActionContext context) throws java.rmi.RemoteException {
//
//	CRUDBP bp = getBusinessProcess(context);
//	
//	try {
//		fillModel(context);
//		if (((Documento_genericoBulk)bp.getModel()).isGenericoAttivo()) {
//			((CRUDDocumentoGenericoAttivoBP)bp).salvaRiportandoAvanti(context);
//			((CRUDDocumentoGenericoAttivoBP)bp).getAccertamentiController().setModelIndex(context, -1);
//		} else {
//			((CRUDDocumentoGenericoPassivoBP)bp).salvaRiportandoAvanti(context);
//			((CRUDDocumentoGenericoPassivoBP)bp).getObbligazioniController().setModelIndex(context, -1);
//		}
//		return context.findDefaultForward();
//	} catch(ValidationException e) {
//		bp.setErrorMessage(e.getMessage());
//		return context.findDefaultForward();
//	} catch(Throwable e) {
//		return handleException(context,e);
//	}
//}
//public Forward doRiportaIndietro(ActionContext context) throws java.rmi.RemoteException {
//
//	try {
//		fillModel(context);
//		CRUDBP bp = getBusinessProcess(context);
//		if (((Documento_genericoBulk)bp.getModel()).isGenericoAttivo())
//			((CRUDDocumentoGenericoAttivoBP)bp).riportaIndietro(context);
//		else
//			((CRUDDocumentoGenericoPassivoBP)bp).riportaIndietro(context);
//		return context.findDefaultForward();
//	} catch (Throwable t) {
//		return handleException(context, t);
//	}
//	
//}
///**
// * Gestisce un HookForward di ritorno da un risultato di una ricerca (SelezionatoreBP)
// */
//public Forward doRiportaSelezione(ActionContext context)  throws java.rmi.RemoteException {
//
//	CRUDBP bp = (CRUDBP)context.getBusinessProcess();
//	if (bp instanceof IDocumentoAmministrativoSpesaBP && ((IDocumentoAmministrativoSpesaBP)bp).isSpesaBP()) {
//		HookForward caller = (HookForward)context.getCaller();
//		it.cnr.jada.bulk.OggettoBulk selezione = (it.cnr.jada.bulk.OggettoBulk)caller.getParameter("focusedElement");
//		return basicDoRiportaSelezione(context, selezione);
//	}
//	return super.doRiportaSelezione(context);
//}
///**
// * Gestisce un HookForward di ritorno da un risultato di una ricerca (SelezionatoreBP)
// */
//public Forward doRiportaSelezioneAccertamento(ActionContext context) throws java.rmi.RemoteException {
//    HookForward caller= (HookForward) context.getCaller();
//    Accertamento_scadenzarioBulk accertamento= (Accertamento_scadenzarioBulk) caller.getParameter("focusedElement");
//    CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) context.getBusinessProcess();
//    java.util.List selectedModels= null;
//    try {
//        selectedModels= bp.getDettaglio().getSelectedModels(context);
//        bp.getDettaglio().getSelection().clearSelection();
//    } catch (Throwable e) {
//    }
//    if (accertamento != null) {
//        try {
//            basicDoContabilizzaAccertamenti(context, accertamento, selectedModels);
//        } catch (it.cnr.jada.comp.ComponentException e) {
//            return handleException(context, e);
//        }
//        bp.getAccertamentiController().getSelection().clear();
//        bp.getAccertamentiController().setModelIndex(context, -1);
//        bp.getAccertamentiController().setModelIndex(context, it.cnr.jada.bulk.BulkCollections.indexOfByPrimaryKey(bp.getAccertamentiController().getDetails(), accertamento));
//
//        bp.setDirty(true);
//        if (!"tabDocumentoGenericoAccertamenti".equals(bp.getTab("tab")))
//            bp.setTab("tab", "tabDocumentoGenericoAccertamenti");
//    }
//    return context.findDefaultForward();
//    /*
//    	try {
//    		Forward fwd = super.doAnnullaRiporta(context);
//    		return fwd;
//    	} catch(Exception e) {
//    		return handleException(context,e);
//    	}
//    */
//}
///**
// * Gestisce un HookForward di ritorno da un risultato di una ricerca (SelezionatoreBP)
// */
//public Forward doRiportaSelezioneObbligazione(ActionContext context) throws java.rmi.RemoteException {
//    HookForward caller= (HookForward) context.getCaller();
//    it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk obblig= (it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk) caller.getParameter("focusedElement");
//    CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
//    java.util.List selectedModels= null;
//    try {
//        selectedModels= bp.getDettaglio().getSelectedModels(context);
//        bp.getDettaglio().getSelection().clearSelection();
//    } catch (Throwable e) {
//    }
//    if (obblig != null) {
//        try {
//            basicDoContabilizza(context, obblig, selectedModels);
//        } catch (it.cnr.jada.comp.ComponentException e) {
//            return handleException(context, e);
//        }
//        bp.getObbligazioniController().getSelection().clear();
//        bp.getObbligazioniController().setModelIndex(context, -1);
//        bp.getObbligazioniController().setModelIndex(context, it.cnr.jada.bulk.BulkCollections.indexOfByPrimaryKey(bp.getObbligazioniController().getDetails(), obblig));
//
//        bp.setDirty(true);
//        if (!"tabDocumentoGenericoObbligazioni".equals(bp.getTab("tab")))
//            bp.setTab("tab", "tabDocumentoGenericoObbligazioni");
//    }
//    return context.findDefaultForward();
//    /*
//    	try {
//    		Forward fwd = super.doAnnullaRiporta(context);
//    		return fwd;
//    	} catch(Exception e) {
//    		return handleException(context,e);
//    	}
//    */
//}
///**
// * Gestisce un cambiamento di pagina su un controllo tabbed {@link it.cnr.jada.util.jsp.JSPUtils.tabbed}
// */
//public Forward doSalva(ActionContext context) throws java.rmi.RemoteException {
//
//    it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//    Documento_genericoBulk documento= (Documento_genericoBulk) bp.getModel();
//    if (documento.isGenericoAttivo() && ((CRUDDocumentoGenericoAttivoBP) bp).getAccertamentiController()!=null)
//         ((CRUDDocumentoGenericoAttivoBP) bp).getAccertamentiController().setModelIndex(context, -1);
//    if (!documento.isGenericoAttivo() && ((CRUDDocumentoGenericoPassivoBP) bp).getObbligazioniController()!=null)
//         ((CRUDDocumentoGenericoPassivoBP) bp).getObbligazioniController().setModelIndex(context, -1);
//   	
//    java.sql.Timestamp competenzaABck = documento.getDt_a_competenza_coge();
//    java.sql.Timestamp competenzaDaBck = documento.getDt_da_competenza_coge();
//    
//	java.util.GregorianCalendar tsOdiernoGregorian = new GregorianCalendar();
//	String cds = CNRUserContext.getCd_cds(context.getUserContext());
//		
//		try {
//			fillModel(context);
//			java.sql.Timestamp competenzaA = documento.getDt_a_competenza_coge();
//			java.sql.Timestamp competenzaDa = documento.getDt_da_competenza_coge();
//			
//			if (bp instanceof CRUDDocumentoGenericoAttivoBP){
//				CRUDDocumentoGenericoAttivoBP bpA= (CRUDDocumentoGenericoAttivoBP) bp;
//				if (competenzaA != null && competenzaABck != null && competenzaA!=competenzaABck){
//					tsOdiernoGregorian.setTime(new Date(competenzaA.getTime()));
//					Integer esercizioCompetenzaA = new Integer (tsOdiernoGregorian.get(java.util.GregorianCalendar.YEAR));
//					if (((DocumentoGenericoComponentSession)bpA.createComponentSession()).isEsercizioChiusoPerDataCompetenza(context.getUserContext(),esercizioCompetenzaA,cds))
//						throw new it.cnr.jada.comp.ApplicationException("Le date \"Competenza da\" e \"Competenza a\" non possono appartenere ad un esercizio chiuso");
//				}
//				if (competenzaDa != null && competenzaDaBck != null && competenzaDa!=competenzaDaBck){
//					tsOdiernoGregorian.setTime(new Date(competenzaDa.getTime()));
//					Integer esercizioCompetenzaDa = new Integer (tsOdiernoGregorian.get(java.util.GregorianCalendar.YEAR));
//					if (((DocumentoGenericoComponentSession)bpA.createComponentSession()).isEsercizioChiusoPerDataCompetenza(context.getUserContext(),esercizioCompetenzaDa,cds))
//						throw new it.cnr.jada.comp.ApplicationException("Le date \"Competenza da\" e \"Competenza a\" non possono appartenere ad un esercizio chiuso");
//				}	
//			}
//			if (bp instanceof CRUDDocumentoGenericoPassivoBP){
//				CRUDDocumentoGenericoPassivoBP bpP= (CRUDDocumentoGenericoPassivoBP) bp;
//				if (competenzaA != null && competenzaABck != null && competenzaA!=competenzaABck){
//						tsOdiernoGregorian.setTime(new Date(competenzaA.getTime()));
//						Integer esercizioCompetenzaA = new Integer (tsOdiernoGregorian.get(java.util.GregorianCalendar.YEAR));
//						if (((DocumentoGenericoComponentSession)bpP.createComponentSession()).isEsercizioChiusoPerDataCompetenza(context.getUserContext(),esercizioCompetenzaA,cds))
//							throw new it.cnr.jada.comp.ApplicationException("Le date \"Competenza da\" e \"Competenza a\" non possono appartenere ad un esercizio chiuso");
//				}
//				if (competenzaDa != null && competenzaDaBck != null && competenzaDa!=competenzaDaBck){
//					tsOdiernoGregorian.setTime(new Date(competenzaDa.getTime()));
//					Integer esercizioCompetenzaDa = new Integer (tsOdiernoGregorian.get(java.util.GregorianCalendar.YEAR));
//					if (((DocumentoGenericoComponentSession)bpP.createComponentSession()).isEsercizioChiusoPerDataCompetenza(context.getUserContext(),esercizioCompetenzaDa,cds))
//						throw new it.cnr.jada.comp.ApplicationException("Le date \"Competenza da\" e \"Competenza a\" non possono appartenere ad un esercizio chiuso");
//				}
//			}
//		} catch (Throwable t) {
//			documento.setDt_a_competenza_coge(competenzaABck);
//			documento.setDt_da_competenza_coge(competenzaDaBck);
//			return handleException(context, t);
//		}
//		
//	return super.doSalva(context);
//}
//    /**
//     * Viene richiamato nel momento in cui si seleziona la lista delle banche nella
//     * riga del documento generico.
//     * Viene passato un parametro relativo al tipo di banca.
//     */
//    public Forward doSearchListabanche(ActionContext context) {
//
//        it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//        Documento_generico_rigaBulk riga= null;
//        Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//        if (!documentoGenerico.isGenericoAttivo()) {
//            riga= (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoPassivoBP) bp).getDettaglio().getModel();
//            return search(context, getFormField(context, "main.Dettaglio.listabanche"), riga.getModalita_pagamento().getTi_pagamento());
//        } else {
//            riga= (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoAttivoBP) bp).getDettaglio().getModel();
//            return search(context, getFormField(context, "main.Dettaglio.listabanche"), riga.getModalita_pagamento_uo_cds().getTi_pagamento());
//        }
//
//    }
///**
// * Viene richiamato nel momento in cui si seleziona una valuta dal combo Valuta nella 
// * testata del documento
// * Richiama a sua volta il metodo cercaCambio dalla component.
// */
//public Forward doSearchSospeso(ActionContext context) {
//
//	Documento_genericoBulk documento = (Documento_genericoBulk)getBusinessProcess(context).getModel();
//	if (documento != null && documento.getLettera_pagamento_estero() != null) {
//		Lettera_pagam_esteroBulk lettera = documento.getLettera_pagamento_estero();
//		if (lettera.getSospeso() == null)
//			try {
//				doBlankSearchSospeso(context, documento);
//			} catch (Exception e) {
//				return handleException(context, e);
//			}
//		return search(context, getFormField(context, "main.sospeso"), null);
//	}
//	return null;
//}
//    /**
//     * Viene richiamato nel momento in cui si seleziona la lista delle banche nella
//     * riga del documento generico.
//     * Viene passato un parametro relativo al tipo di banca.
//     */
//    public Forward doSearchTerzo(ActionContext context) {
//
//        it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//        Documento_generico_rigaBulk riga= null;
//        Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//        if (!documentoGenerico.isGenericoAttivo()) {
//            riga= (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoPassivoBP) bp).getDettaglio().getModel();
//        } else {
//            riga= (Documento_generico_rigaBulk) ((CRUDDocumentoGenericoAttivoBP) bp).getDettaglio().getModel();
//        }
//
//        try {
//            it.cnr.jada.util.action.FormField field= getFormField(context, "main.Dettaglio.terzo");
//            it.cnr.jada.bulk.OggettoBulk model= field.getModel();
//            it.cnr.jada.bulk.OggettoBulk value= (it.cnr.jada.bulk.OggettoBulk) field.getField().getValueFrom(model);
//            if (value == null)
//                value= createEmptyModelForSearchTool(context, field);
//            it.cnr.jada.util.RemoteIterator selezione= bp.find(context, null, value, model, field.getField().getProperty());
//            if (selezione.countElements() == 0) {
//                riga.getTerzo().setCd_terzo(null);
//            }
//            //bp.fillModel(context);
//            return super.selectFromSearchResult(context, field, selezione, null);
//        } catch (Exception e) {
//            return handleException(context, e);
//        }
//
//    }
///**
// * Gestisce una richiesta di selezione dal controller "accertamenti"
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doSelectAccertamenti(ActionContext context) {
//
//    CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) context.getBusinessProcess();
//    try {
//        bp.getAccertamentiController().setSelection(context);
//    } catch (Throwable e) {
//    }
//
//    doCalcolaTotalePerAccertamento(context, (Accertamento_scadenzarioBulk) bp.getAccertamentiController().getModel());
//    return context.findDefaultForward();
//}
///**
// * Gestisce una richiesta di selezione dal controller "obbligazioni"
// *
// * @param context	L'ActionContext della richiesta
// * @return Il Forward alla pagina di risposta
// */
//public Forward doSelectObbligazioni(ActionContext context) {
//
//    CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
//    try {
//        bp.getObbligazioniController().setSelection(context);
//    } catch (Throwable e) {
//    }
//
//    doCalcolaTotalePerObbligazione(context, (Obbligazione_scadenzarioBulk) bp.getObbligazioniController().getModel());
//    return context.findDefaultForward();
//}
///**
// * Viene richiamato nel momento in cui si seleziona una valuta dal combo Valuta nella 
// * riga del documento generico.
// * Richiama a sua volta il metodo cercaCambio dalla component.
// */
//public Forward doSelezionaValuta(ActionContext context) throws it.cnr.jada.comp.ApplicationException {
//    it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//    Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//    try {
//        DocumentoGenericoComponentSession component= null;
//        if (documentoGenerico.getStato_cofi() != null && !documentoGenerico.getStato_cofi().equals(documentoGenerico.STATO_INIZIALE))
//            throw new it.cnr.jada.comp.ApplicationException("Non � possibile modificare la valuta in un documento in stato non iniziale");
//        if (!documentoGenerico.isGenericoAttivo())
//            component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoPassivoBP) bp).createComponentSession();
//        else
//            component= (DocumentoGenericoComponentSession) ((CRUDDocumentoGenericoAttivoBP) bp).createComponentSession();
//        DivisaBulk divisa= documentoGenerico.getValuta();
//        fillModel(context);
//        try {
//            documentoGenerico= component.cercaCambio(context.getUserContext(), documentoGenerico);
//        } catch (Throwable t) {
//            documentoGenerico.setValuta(divisa);
//            throw t;
//        }
//        bp.setModel(context, documentoGenerico);
//        documentoGenerico= basicCalcolaTotale(context);
//        bp.setModel(context, documentoGenerico);
//
//    } catch (Throwable t) {
//        return handleException(context, t);
//    }
//    return context.findDefaultForward();
//}
///**
// * Gestisce un cambiamento di pagina su un controllo tabbed {@link it.cnr.jada.util.jsp.JSPUtils.tabbed}
// */
//public Forward doTab(ActionContext context, String tabName, String pageName) {
//
//    it.cnr.jada.util.action.CRUDBP bp= (it.cnr.jada.util.action.CRUDBP) getBusinessProcess(context);
//    Documento_genericoBulk documento= (Documento_genericoBulk) bp.getModel();
//    
//	java.sql.Timestamp competenzaABck = documento.getDt_a_competenza_coge();
//    java.sql.Timestamp competenzaDaBck = documento.getDt_da_competenza_coge();
//    
//    java.util.GregorianCalendar tsOdiernoGregorian = new GregorianCalendar();
//	String cds = CNRUserContext.getCd_cds(context.getUserContext());
//		
//		try {
//			fillModel(context);
//			java.sql.Timestamp competenzaA = documento.getDt_a_competenza_coge();
//			java.sql.Timestamp competenzaDa = documento.getDt_da_competenza_coge();
//			
//			if (bp instanceof CRUDDocumentoGenericoAttivoBP){
//			CRUDDocumentoGenericoAttivoBP bpA= (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//			
//			if (competenzaA!=null )
//				if(competenzaA!=competenzaABck){
//				tsOdiernoGregorian.setTime(new Date(competenzaA.getTime()));
//				Integer esercizioCompetenzaA = new Integer (tsOdiernoGregorian.get(java.util.GregorianCalendar.YEAR));
//				if (((DocumentoGenericoComponentSession)bpA.createComponentSession()).isEsercizioChiusoPerDataCompetenza(context.getUserContext(),esercizioCompetenzaA,cds))
//					throw new it.cnr.jada.comp.ApplicationException("Le date \"Competenza da\" e \"Competenza a\" non possono appartenere ad un esercizio chiuso");
//			
//			}
//			if (competenzaDa !=null)
//				if ( competenzaDa!=competenzaDaBck){
//				tsOdiernoGregorian.setTime(new Date(competenzaDa.getTime()));
//				Integer esercizioCompetenzaDa = new Integer (tsOdiernoGregorian.get(java.util.GregorianCalendar.YEAR));
//				if (((DocumentoGenericoComponentSession)bpA.createComponentSession()).isEsercizioChiusoPerDataCompetenza(context.getUserContext(),esercizioCompetenzaDa,cds))
//					throw new it.cnr.jada.comp.ApplicationException("Le date \"Competenza da\" e \"Competenza a\" non possono appartenere ad un esercizio chiuso");
//			}	
//			}
//			
//			if (bp instanceof CRUDDocumentoGenericoPassivoBP){
//				CRUDDocumentoGenericoPassivoBP bpP= (CRUDDocumentoGenericoPassivoBP) getBusinessProcess(context);
//			if (competenzaA!=null )	
//			if (competenzaA!=competenzaABck){
//					tsOdiernoGregorian.setTime(new Date(competenzaA.getTime()));
//					Integer esercizioCompetenzaA = new Integer (tsOdiernoGregorian.get(java.util.GregorianCalendar.YEAR));
//					if (((DocumentoGenericoComponentSession)bpP.createComponentSession()).isEsercizioChiusoPerDataCompetenza(context.getUserContext(),esercizioCompetenzaA,cds))
//						throw new it.cnr.jada.comp.ApplicationException("Le date \"Competenza da\" e \"Competenza a\" non possono appartenere ad un esercizio chiuso");
//			}
//			if (competenzaDa!=null )
//			if (competenzaDa!=competenzaDaBck){
//				tsOdiernoGregorian.setTime(new Date(competenzaDa.getTime()));
//				Integer esercizioCompetenzaDa = new Integer (tsOdiernoGregorian.get(java.util.GregorianCalendar.YEAR));
//				if (((DocumentoGenericoComponentSession)bpP.createComponentSession()).isEsercizioChiusoPerDataCompetenza(context.getUserContext(),esercizioCompetenzaDa,cds))
//					throw new it.cnr.jada.comp.ApplicationException("Le date \"Competenza da\" e \"Competenza a\" non possono appartenere ad un esercizio chiuso");
//			}
//			}
//		} catch (Throwable t) {
//			documento.setDt_a_competenza_coge(competenzaABck);
//			documento.setDt_da_competenza_coge(competenzaDaBck);
//			return handleException(context, t);
//		}
//		
//    try {
//		fillModel(context);
//		if (documento.getTipo_documento()==null || documento.getTipo_documento().getCd_tipo_documento_amm() == null)
//			throw new ValidationException("Selezionare un tipo di documento");
//
//        if (("tabDocumentoAttivo".equalsIgnoreCase(bp.getTab(tabName)) ||
//	        "tabDocumentoPassivo".equalsIgnoreCase(bp.getTab(tabName))) &&
//        	!bp.isSearching() && !bp.isViewing() && !documento.isRODateCompetenzaCOGE())
//				documento.validaDateCompetenza();
//
//		if ("tabDocumentoGenericoObbligazioni".equalsIgnoreCase(bp.getTab(tabName))) {
//            try {
//                fillModel(context);
//                if (!documento.isPassivo_ente())
//                    controllaQuadraturaObbligazioni(context, documento);
//            } catch (it.cnr.jada.comp.ApplicationException e) {
//                bp.setErrorMessage(e.getMessage());
//            }
//        }
//        if ("tabDocumentoGenericoAccertamenti".equalsIgnoreCase(bp.getTab(tabName))) {
//            try {
//                fillModel(context);
//                if (!Numerazione_doc_ammBulk.TIPO_REGOLA_E.equalsIgnoreCase(documento.getTipo_documento().getCd_tipo_documento_amm()) &&
//	                !Numerazione_doc_ammBulk.TIPO_GEN_CH_FON.equalsIgnoreCase(documento.getTipo_documento().getCd_tipo_documento_amm()))
//                    if (!documento.isPassivo_ente())
//                    	controllaQuadraturaAccertamenti(context, documento);
//            } catch (it.cnr.jada.comp.ApplicationException e) {
//                bp.setErrorMessage(e.getMessage());
//            }
//        }
//    		
//        return super.doTab(context, tabName, pageName);
//    } catch (Throwable e) {
//        return handleException(context, e);
//    }
//}
///**
// * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
// * figlio del ricevente
// */
//
//protected BancaBulk getBancaDefaultForCdsFrom(
//	ActionContext context,
//	Documento_generico_rigaBulk riga,
//	java.util.Collection banche) {
//
//	if (banche == null || banche.isEmpty())
//		return null;
//	BancaBulk primaBanca = (BancaBulk) new java.util.Vector(banche).firstElement();
//	if (banche.size() == 1)
//		return primaBanca;
//
//	BancaBulk defaultBanca = null;
//	if (riga.getDocumento_generico().isGenericoAttivo())
//		for (java.util.Iterator i = banche.iterator(); i.hasNext();) {
//			BancaBulk banca = (BancaBulk)i.next();
//			if (defaultBanca == null) {
//				if (banca.getFl_cc_cds() != null &&
//					banca.getFl_cc_cds().booleanValue())
//					defaultBanca = banca;
//			}
//		}
//
//	if (defaultBanca == null)
//		defaultBanca = primaBanca;
//	return defaultBanca;
//}
///**
// * Restituisce l'importo per l'aggiornamento automatico
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @param scadenza	
// * @param documento	
// * @param delta	
// * @return 
// */
//protected java.math.BigDecimal getImportoPerAggiornamentoScadenzaAccertamentiInAutomatico(
//        ActionContext context,
//        Accertamento_scadenzarioBulk scadenza,
//        Documento_genericoBulk documento,
//        java.math.BigDecimal delta) {
//
//        return documento.getImportoTotalePerAccertamento();
//    }
///**
// * Restituisce l'importo per l'aggiornamento automatico
// * 
// *
// * @param context	L'ActionContext della richiesta
// * @param scadenza	
// * @param documento	
// * @param delta	
// * @return 
// */
//protected java.math.BigDecimal getImportoPerAggiornamentoScadenzaObbligazioniInAutomatico(
//        ActionContext context,
//        Obbligazione_scadenzarioBulk scadenza,
//        Documento_genericoBulk documento,
//        java.math.BigDecimal delta) {
//
//	    //if (documento.isDoc1210Associato()) {
//		    //return documento.getLettera_pagamento_estero().getIm_pagamento();
//	    //}
//        return documento.getImportoTotalePerObbligazione();
//    }
//protected Forward handleException(ActionContext context, Throwable ex) {
//	try {
//		throw ex;
//	} catch(it.cnr.contab.doccont00.comp.CheckDisponibilitaCassaFailed e) {
//		CRUDBP bp = (CRUDBP)context.getBusinessProcess();
//		if (bp instanceof CRUDDocumentoGenericoPassivoBP) {
//			Documento_genericoBulk doc = (Documento_genericoBulk)bp.getModel();
//			if (!doc.isGenericoAttivo() && doc.isDoc1210Associato()) {
//				String msg = "L'importo della lettera di pagamento 1210 supera la disponiblit� di cassa relativa al capitolo! Operazione interrotta.";
//				return super.handleException(context, new it.cnr.jada.comp.ApplicationException(msg));
//			}
//		}
//		return super.handleException(context,e);
//	} catch(Throwable e) {
//		return super.handleException(context,e);
//	}
//}
//private void resetImportoIniziale(
//	ActionContext context, 
//	java.util.List models) {
//
//	if (models != null) {
//	    //CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
//	    //Documento_genericoBulk doc = (Documento_genericoBulk)bp.getModel();
//		for (java.util.Iterator i= models.iterator(); i.hasNext();) {
//			Documento_generico_rigaBulk dettaglio= (Documento_generico_rigaBulk) i.next();
//			if (dettaglio.getDocumento_generico().isPassivo_ente())
//				dettaglio.setIm_riga_iniziale(null);
//			//int idx = doc.getDocumento_generico_dettColl().indexOfByPrimaryKey(dettaglio);
//			//((Documento_generico_rigaBulk)doc.getDocumento_generico_dettColl().get(idx)).setIm_riga_iniziale(null);
//		}
//	}
//}
//private void resyncAccertamento(ActionContext context, Accertamento_scadenzarioBulk oldAccert, Accertamento_scadenzarioBulk newAccert) throws it.cnr.jada.comp.ComponentException {
//
//    CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//    Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//    java.util.Vector models= ((java.util.Vector) doc.getDocumento_generico_accertamentiHash().get(oldAccert));
//    java.util.Vector clone= (java.util.Vector) models.clone();
//    if (!clone.isEmpty())
//        scollegaDettagliDaAccertamento(context, clone);
//    else
//        doc.getDocumento_generico_accertamentiHash().remove(oldAccert);
//    basicDoContabilizzaAccertamenti(context, newAccert, clone);
//}
//    private void resyncObbligazione(ActionContext context, Obbligazione_scadenzarioBulk oldObblig, Obbligazione_scadenzarioBulk newObblig) throws it.cnr.jada.comp.ComponentException {
//
//        CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) getBusinessProcess(context);
//        Documento_genericoBulk doc= (Documento_genericoBulk) bp.getModel();
//        java.util.Vector models= ((java.util.Vector) doc.getDocumento_generico_obbligazioniHash().get(oldObblig));
//        java.util.Vector clone= (java.util.Vector) models.clone();
//        if (!clone.isEmpty())
//            scollegaDettagliDaObbligazione(context, clone);
//        else
//            doc.getDocumento_generico_obbligazioniHash().remove(oldObblig);
//        basicDoContabilizza(context, newObblig, clone);
//    }
//    private void scollegaDettagliDaAccertamento(ActionContext context, java.util.List models) throws it.cnr.jada.comp.ComponentException {
//
//        if (models != null) {
//            try {
//                for (java.util.Iterator i= models.iterator(); i.hasNext();) {
//                    Documento_generico_rigaBulk dettaglio= (Documento_generico_rigaBulk) i.next();
//                    if (!dettaglio.STATO_CONTABILIZZATO.equals(dettaglio.getStato_cofi()))
//                        throw new it.cnr.jada.comp.ApplicationException(
//                            "Non � possibile scollegare il dettaglio \"" + dettaglio.getDs_riga() + "\". Questa operazione � permessa solo per dettagli in stato contabilizzato ");
//                    dettaglio.getDocumento_generico().removeFromDocumento_generico_accertamentiHash(dettaglio);
//                    dettaglio.setStato_cofi(dettaglio.STATO_INIZIALE);
//                    dettaglio.setAccertamento_scadenziario(null);
//                    dettaglio.setToBeUpdated();
//                }
//
//	            CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) context.getBusinessProcess();
//                Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//                boolean iniziale= true;
//                for (java.util.Iterator i= documentoGenerico.getDocumento_generico_dettColl().iterator(); i.hasNext();) {
//                    Documento_generico_rigaBulk dettaglio= (Documento_generico_rigaBulk) i.next();
//                    if (!dettaglio.getStato_cofi().equals(dettaglio.STATO_INIZIALE))
//                        iniziale= false;
//                }
//                if (iniziale) {
//                    documentoGenerico.setStato_cofi(documentoGenerico.STATO_INIZIALE);
//                    documentoGenerico.setToBeUpdated();
//                }
//
//            } catch (it.cnr.jada.comp.ApplicationException e) {
//                try {
//                    it.cnr.contab.doccont00.bp.CRUDVirtualObbligazioneBP.rollbackToSafePoint(context);
//                } catch (Throwable t) {
//                    throw new it.cnr.jada.comp.ComponentException(t);
//                }
//                throw e;
//            }
//        }
//    }
//    private void scollegaDettagliDaObbligazione(ActionContext context, java.util.List models) throws it.cnr.jada.comp.ComponentException {
//
//        if (models != null) {
//            try {
//                for (java.util.Iterator i= models.iterator(); i.hasNext();) {
//                    Documento_generico_rigaBulk dettaglio= (Documento_generico_rigaBulk) i.next();
//                    if (!dettaglio.STATO_CONTABILIZZATO.equals(dettaglio.getStato_cofi()))
//                        throw new it.cnr.jada.comp.ApplicationException(
//                            "Non � possibile scollegare il dettaglio \"" + dettaglio.getDs_riga() + "\". Questa operazione � permessa solo per dettagli in stato contabilizzato ");
//                    dettaglio.getDocumento_generico().removeFromDocumento_generico_obbligazioniHash(dettaglio);
//                    dettaglio.setStato_cofi(dettaglio.STATO_INIZIALE);
//                    dettaglio.setObbligazione_scadenziario(null);
//                    dettaglio.setToBeUpdated();
//                }
//                CRUDDocumentoGenericoPassivoBP bp= (CRUDDocumentoGenericoPassivoBP) context.getBusinessProcess();
//                Documento_genericoBulk documentoGenerico= (Documento_genericoBulk) bp.getModel();
//                boolean iniziale= true;
//                for (java.util.Iterator i= documentoGenerico.getDocumento_generico_dettColl().iterator(); i.hasNext();) {
//                    Documento_generico_rigaBulk dettaglio= (Documento_generico_rigaBulk) i.next();
//                    if (!dettaglio.getStato_cofi().equals(dettaglio.STATO_INIZIALE))
//                        iniziale= false;
//                }
//                if (iniziale) {
//                    documentoGenerico.setStato_cofi(documentoGenerico.STATO_INIZIALE);
//                    documentoGenerico.setToBeUpdated();
//                }
//            } catch (it.cnr.jada.comp.ApplicationException e) {
//                try {
//                    it.cnr.contab.doccont00.bp.CRUDVirtualObbligazioneBP.rollbackToSafePoint(context);
//                } catch (Throwable t) {
//                    throw new it.cnr.jada.comp.ComponentException(t);
//                }
//                throw e;
//            }
//        }
//    }
//    /**
//     * Gestisce il comando di aggiunta di un nuovo dettaglio su un CRUDController
//     * figlio del ricevente
//     */
//    protected void setAndVerifyStatusFor(ActionContext context, Documento_genericoBulk doc) {
//
//        //if (doc.getStato_cofi() != doc.STATO_PAGATO && doc.getCd_tipo_documento_amm().equalsIgnoreCase(doc.GENERICO_S) && doc.getDocumento_generico_obbligazioniHash() != null)
//            //doc.setStato_cofi((doc.getDocumento_generico_obbligazioniHash().isEmpty()) ? doc.STATO_INIZIALE : doc.STATO_CONTABILIZZATO);
//        //if (doc.getStato_cofi() != doc.STATO_PAGATO && doc.getCd_tipo_documento_amm().equalsIgnoreCase(doc.GENERICO_E) && doc.getDocumento_generico_obbligazioniHash() != null)
//            //doc.setStato_cofi((doc.getDocumento_generico_accertamentiHash().isEmpty()) ? doc.STATO_INIZIALE : doc.STATO_CONTABILIZZATO);
//        doc.setAndVerifyStatus();
//    }
//    /**
//     * Gestisce lo sdoppiamento della riga di dettaglio
//     * 
//     *
//     * @param context	L'ActionContext della richiesta
//     * @return Il Forward alla pagina di risposta
//     */
//    public Forward doSdoppiaDettaglio(ActionContext context) {
//       try {
//            CRUDDocumentoGenericoAttivoBP bp= (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//            fillModel(context);
//            Documento_generico_rigaBulk riga= (Documento_generico_rigaBulk)bp.getDettaglio().getModel();
//            Forward forward= context.findDefaultForward();
//            if (riga == null)
//                bp.setErrorMessage("Per procedere, selezionare il dettaglio da sdoppiare!");
//            else {
//            	riga.setIm_riga_sdoppia(Utility.ZERO);
//                bp.setDetailDoubling(true);
//            }
//            return forward;
//        } catch (Throwable e) {
//            return handleException(context, e);
//        }
//    }
//    /**
//     * Gestisce lo sdoppiamento della riga di dettaglio
//     *
//     * @param context	L'ActionContext della richiesta
//     * @return Il Forward alla pagina di risposta
//     */
//    public Forward doConfirmSdoppiaDettaglio(ActionContext context) {
//        try {
//	        CRUDDocumentoGenericoAttivoBP bp = (CRUDDocumentoGenericoAttivoBP) getBusinessProcess(context);
//	        fillModel(context);
//	        
//	        bp.sdoppiaDettaglioInAutomatico(context);
//
//	        Documento_genericoBulk documento = (Documento_genericoBulk)bp.getModel();
//
//	        if (documento != null) {
//	        	for (Iterator s = documento.getDocumento_generico_dettColl().iterator(); s.hasNext(); ) {
//	        		Documento_generico_rigaBulk riga = (Documento_generico_rigaBulk)s.next();
//	        		if ((riga.isToBeCreated() || riga.isToBeUpdated()) && riga.getAccertamento_scadenziario()!=null)
//	    				basicDoBringBackOpenAccertamentiWindow(context, riga.getAccertamento_scadenziario());
//	        	}
//	        }
//
//	        bp.setDirty(true);
//	   		return context.findDefaultForward();
//        } catch (Throwable e) {
//            return handleException(context, e);
//        }
//    }
//       	/**
//    	 * Inventaria i dettagli
//    	 * Richiesta la quadratura tra dettagli e testata
//    	 *
//    	 * @param context	L'ActionContext della richiesta
//    	 * @return Il Forward alla pagina di risposta
//    	 */
//    	public Forward doInventariaDettagli(ActionContext context) {
//    	
//    		try {
//    			CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//    			fillModel(context);
//    			Documento_genericoBulk doc = (Documento_genericoBulk)bp.getModel();
//    				java.util.List dettagliDaInventariare = getDettagliDaInventariare(context, bp.getDettaglio().getDetails().iterator());
//    			if (dettagliDaInventariare != null && !dettagliDaInventariare.isEmpty()  ) 
//    				return basicDoInventariaDettagli(context,doc.getTi_entrate_spese());
//    			else
//    				bp.setMessage("Nessun dettaglio � inventariabile o tutti i dettagli inventariabili sono gi� stati caricati!");
//    				return context.findDefaultForward();
//    			
//    		} catch(Exception e) {
//    			return handleException(context,e);
//    		}
//    		
//    	}
//    	/**
//    	 * Inventaria i dettagli
//    	 * 
//    	 *
//    	 * @param context	L'ActionContext della richiesta
//    	 * @return Il Forward alla pagina di risposta
//    	 */
//    	public Forward doInventariaDettagliAtt(ActionContext context) {
////    	inventario
//    		try {
//    			CRUDDocumentoGenericoAttivoBP bp = (CRUDDocumentoGenericoAttivoBP)getBusinessProcess(context);
//    			fillModel(context);
//    			Documento_genericoBulk doc = (Documento_genericoBulk)bp.getModel();
//				java.util.List dettagliDaInventariare = getDettagliDaInventariare(context, bp.getDettaglio().getDetails().iterator());
//			if (dettagliDaInventariare != null && !dettagliDaInventariare.isEmpty()  ) 
//				return basicDoInventariaDettagli(context,doc.getTi_entrate_spese());
//			else
//				bp.setMessage("Nessun dettaglio � inventariabile o tutti i dettagli inventariabili sono gi� stati caricati!");
//				return context.findDefaultForward();
//    		} catch(Exception e) {
//    			return handleException(context,e);
//    		}
//    		
//    	}
//    	/**
//    	 * Inventaria i dettagli per aumento di valore
//    	 * Richiesta la quadratura tra dettagli e testata
//    	 *
//    	 * @param context	L'ActionContext della richiesta
//    	 * @return Il Forward alla pagina di risposta
//    	 */				
//    	public Forward doInventariaDettagliPerAumentoValore(ActionContext context) {
//    	
//    		try {
//    			CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//    			fillModel(context);
//    				
//    			java.util.List dettagliDaInventariare = getDettagliDaInventariare(context, bp.getDettaglio().getDetails().iterator());
//    			if (dettagliDaInventariare != null && !dettagliDaInventariare.isEmpty()) {
//    				AssBeneFatturaBP ibp = (AssBeneFatturaBP)context.getUserInfo().createBusinessProcess(context,"AssBeneFatturaBP",new Object[] { "MRSWTh" });
//    				Ass_inv_bene_fatturaBulk associa = new Ass_inv_bene_fatturaBulk();			
//    				associa.setLocal_transactionID(ibp.getLocalTransactionID(context.getUserContext(),true));
//    				associa.completeFrom(dettagliDaInventariare);
//    				associa.setPerAumentoValoreDoc(Boolean.TRUE);
//    				// Crea ed inizializza il Buono di Carico
//    				Buono_carico_scaricoBulk buonoC = new Buono_carico_scaricoBulk();
//    				buonoC.setToBeCreated();
//    				buonoC.setByDocumentoPerAumentoValore(Boolean.TRUE);
//    				buonoC.setTi_documento(Buono_carico_scaricoBulk.CARICO);
//    				buonoC = (Buono_carico_scaricoBulk)ibp.createComponentSession().inizializzaBulkPerInserimento(context.getUserContext(), buonoC);
//    				associa.setTest_buono(buonoC);
//    				associa.setInventario(buonoC.getInventario());
//    				try{
//    					associa.getTest_buono().setPg_buono_c_s(((NumerazioneTempBuonoComponentSession)EJBCommonServices.createEJB(
//    								"CNRINVENTARIO01_EJB_NumerazioneTempBuonoComponentSession",
//    								NumerazioneTempBuonoComponentSession.class)).getNextTempPG(context.getUserContext(),associa.getTest_buono()));
//    					}catch (Throwable e){
//    						throw new ComponentException (e);
//    					}
//    				ibp.setModel(context,associa);			
//    				ibp.setDirty(false);
//    				ibp.setPerAumentoValoreDoc(Boolean.TRUE);
//    				
//    				context.addHookForward("bringback",this,"doBringBackAssociaInventario");
//    				HookForward hook = (HookForward)context.findForward("bringback");
//    				hook.addParameter("dettagliDaInventariare", dettagliDaInventariare);
//    				return context.addBusinessProcess(ibp);
//    			}
//    			bp.setMessage("Nessun dettaglio � inventariabile o tutti i dettagli inventariabili sono gi� stati caricati!");
//    			return context.findDefaultForward();
//    		} catch(Exception e) {
//    			return handleException(context,e);
//    		}
//    	}
//    	/**
//    	 * Associa ad un buono di carico gi� creato i dettagli selezionati in fattura
//    	 * 
//    	 *
//    	 * @param context	L'ActionContext della richiesta
//    	 * @return Il Forward alla pagina di risposta
//    	 */
//    	public Forward doAssociaInventario(ActionContext context) {
//    	
//    		try {
//    			CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//    			fillModel(context);
//    	
//    			Documento_genericoBulk documento = (Documento_genericoBulk)bp.getModel();
//    			
//    			if ((documento.getAssociazioniInventarioHash() != null && !documento.getAssociazioniInventarioHash().isEmpty())||(documento.getHa_beniColl()))
//    				return openConfirm(context,"Alcuni dettagli sono gi� stati associati. Si vuole continuare?",it.cnr.jada.util.action.OptionBP.CONFIRM_YES_NO,"doConfermaAssocia");
//    					
//    			return basicDoAssociaDettagli(context);
//    							
//    		} catch(Exception e) {
//    				return handleException(context,e);
//    			}
//    	}
//    	/**
//    	 * Associa ad un buono di carico gi� creato i dettagli selezionati in fattura
//    	 * 
//    	 *
//    	 * @param context	L'ActionContext della richiesta
//    	 * @return Il Forward alla pagina di risposta
//    	 */
//    	public Forward doAssociaInventarioAtt(ActionContext context) {
//    	
//    		try {
//    			CRUDDocumentoGenericoAttivoBP bp = (CRUDDocumentoGenericoAttivoBP)getBusinessProcess(context);
//    			fillModel(context);
//    	
//    			Documento_genericoBulk documento = (Documento_genericoBulk)bp.getModel();
//    			
//    			if ((documento.getAssociazioniInventarioHash() != null && !documento.getAssociazioniInventarioHash().isEmpty())||(documento.getHa_beniColl()))
//    				return openConfirm(context,"Alcuni dettagli sono gi� stati associati. Si vuole continuare?",it.cnr.jada.util.action.OptionBP.CONFIRM_YES_NO,"doConfermaAssocia");
//    					
//    			return basicDoAssociaDettagli(context);
//    							
//    		} catch(Exception e) {
//    				return handleException(context,e);
//    			}
//    	}
//    	public Forward doBeni_coll(ActionContext context) {
//    		Documento_genericoBulk doc=null;
//    		CRUDDocumentoGenericoPassivoBP bp=null;
//    		CRUDDocumentoGenericoAttivoBP bp_att=null;
//    		it.cnr.jada.util.RemoteIterator ri=null;
//    	 if (getBusinessProcess(context) instanceof CRUDDocumentoGenericoPassivoBP)
//    		bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//    	 else
//    		bp_att = (CRUDDocumentoGenericoAttivoBP)getBusinessProcess(context);
//    	 try{
//    	 if (bp!=null){
//    		 doc = (Documento_genericoBulk)bp.getModel();
//    		 ri = ((DocumentoGenericoComponentSession)bp.createComponentSession()).selectBeniFor(context.getUserContext(),doc);
//    	 }
//    	 else{
//    		 doc = (Documento_genericoBulk)bp_att.getModel();
//    		 ri = ((DocumentoGenericoComponentSession)bp_att.createComponentSession()).selectBeniFor(context.getUserContext(),doc);
//    	 }
//    				
//    			ri = it.cnr.jada.util.ejb.EJBCommonServices.openRemoteIterator(context,ri);
//    			if (ri.countElements() == 0) {
//    				it.cnr.jada.util.ejb.EJBCommonServices.closeRemoteIterator(context,ri);
//    				throw new it.cnr.jada.comp.ApplicationException("Attenzione: Nessun dato disponibile.");
//    			}
//    			SelezionatoreListaBP nbp = (SelezionatoreListaBP)context.createBusinessProcess("Selezionatore");
//    			nbp.setIterator(context,ri);   			
//    			nbp.disableSelection();
//    			nbp.setBulkInfo(it.cnr.jada.bulk.BulkInfo.getBulkInfo(V_ass_inv_bene_fatturaBulk.class));		
//    			HookForward hook = (HookForward)context.findForward("seleziona");		
//    			return context.addBusinessProcess(nbp);		
//    		}
//    		catch (Throwable e){		
//    			return handleException(context,e);
//    		}
//    	 }
//    	/**
//    	 * Restituisce un vettore di dettagli ancora da inventariare
//    	 * 
//    	 *
//    	 * @param context	L'ActionContext della richiesta
//    	 * @param dettagli	
//    	 * @return 
//    	 * @throws SQLException  
//    	 * @throws PersistencyException 
//    	 * @throws RemoteException 
//    	 * @throws ComponentException 
//    	 */
//    	protected java.util.List getDettagliDaInventariare(
//    		ActionContext context,
//    		java.util.Iterator dettagli) throws EJBException, SQLException, PersistencyException, ComponentException, RemoteException  {
//
//    		java.util.Vector coll = new java.util.Vector();
//    		if (dettagli != null) {
//    			while (dettagli.hasNext()) {
//    				Documento_generico_rigaBulk riga = (Documento_generico_rigaBulk)dettagli.next();
//    				if (riga.getDocumento_generico().getTi_entrate_spese()==Documento_genericoBulk.SPESE){
//    					if (riga.getObbligazione_scadenziario() !=null && riga.getObbligazione_scadenziario().getObbligazione()!= null &&
//    								riga.getObbligazione_scadenziario().getObbligazione().getElemento_voce()!=null &&
//    								riga.getObbligazione_scadenziario().getObbligazione().getElemento_voce().getFl_inv_beni_patr().booleanValue() &&
//    								!riga.isInventariato())
//    							coll.add(riga);
//    				}else{
//    					if (riga.getAccertamento_scadenziario() !=null && riga.getAccertamento_scadenziario().getAccertamento()!= null &&
//								riga.getAccertamento_scadenziario().getAccertamento().getCd_elemento_voce()!=null){ 
//    							Elemento_voceBulk elem_voce = new Elemento_voceBulk(riga.getAccertamento_scadenziario().getAccertamento().getCd_elemento_voce(),
//    																			riga.getAccertamento_scadenziario().getAccertamento().getEsercizio(),
//    																			riga.getAccertamento_scadenziario().getAccertamento().getTi_appartenenza(),
//    																			riga.getAccertamento_scadenziario().getAccertamento().getTi_gestione());
//    							elem_voce =((Elemento_voceBulk)(((FatturaAttivaSingolaComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRDOCAMM00_EJB_FatturaAttivaSingolaComponentSession",FatturaAttivaSingolaComponentSession.class)).completaOggetto(context.getUserContext(),elem_voce)));    							
//								if (elem_voce.getFl_inv_beni_patr().booleanValue() && !riga.isInventariato())
//									coll.add(riga);
//    							}
//    			   				 }
//    					}							
//    				}
//    		return coll;
//    	}
//    	/**
//    	 * Aggiunge, una volta terminata l'operazione di associazione, tale elenco nel
//    	 * modello
//    	 *
//    	 * @param context	L'ActionContext della richiesta
//    	 * @return Il Forward alla pagina di risposta
//    	 */
//    	public Forward doBringBackAssociaInventario(ActionContext context) {
//    	
//    		try {
//    			HookForward hook = (HookForward)context.getCaller();
//    			CRUDDocumentoGenericoPassivoBP bp=null;
//    			CRUDDocumentoGenericoAttivoBP bp_att=null;
//    			Documento_genericoBulk doc =null;
//    			Ass_inv_bene_fatturaBulk ass = (Ass_inv_bene_fatturaBulk)hook.getParameter("bringback");	
//    			if (ass != null) {
//    				java.util.List dettagliInventariati = (java.util.List)hook.getParameter("dettagliDaInventariare");
//    				if(context.getBusinessProcess() instanceof CRUDDocumentoGenericoPassivoBP){
//    					bp = (CRUDDocumentoGenericoPassivoBP)context.getBusinessProcess();
//    					doc = (Documento_genericoBulk)bp.getModel();
//    					for (java.util.Iterator i = dettagliInventariati.iterator(); i.hasNext();) {
//        					Documento_generico_rigaBulk dettaglio = (Documento_generico_rigaBulk)i.next();
//        					if (ass.getPg_riga()== null) {
//        							BuonoCaricoScaricoComponentSession h = (BuonoCaricoScaricoComponentSession)bp.createComponentSession(
//        									"CNRINVENTARIO01_EJB_BuonoCaricoScaricoComponentSession", 
//        									BuonoCaricoScaricoComponentSession.class);
//        							ass.setPg_riga(h.findMaxAssociazione(context.getUserContext(),ass));
//        					}
//        					doc.addToAssociazioniInventarioHash(ass, dettaglio);
//        					dettaglio.setInventariato(true);
//        				}
//    				}
//    				else{
//    					bp_att = (CRUDDocumentoGenericoAttivoBP)context.getBusinessProcess();
//    					doc= (Documento_genericoBulk)bp_att.getModel();
//    					for (java.util.Iterator i = dettagliInventariati.iterator(); i.hasNext();) {
//        					Documento_generico_rigaBulk dettaglio = (Documento_generico_rigaBulk)i.next();
//        					if (ass.getPg_riga()== null) {
//        							BuonoCaricoScaricoComponentSession h = (BuonoCaricoScaricoComponentSession)bp_att.createComponentSession(
//        									"CNRINVENTARIO01_EJB_BuonoCaricoScaricoComponentSession", 
//        									BuonoCaricoScaricoComponentSession.class);
//        							ass.setPg_riga(h.findMaxAssociazione(context.getUserContext(),ass));
//        					}
//        					doc.addToAssociazioniInventarioHash(ass, dettaglio);
//        					dettaglio.setInventariato(true);
//        				}
//    				}
//    				
//    			}
//    			else{
//    				java.util.List dettagliInventariati = (java.util.List)hook.getParameter("dettagliDaInventariare");
//    				if(context.getBusinessProcess() instanceof CRUDDocumentoGenericoPassivoBP){
//    					bp = (CRUDDocumentoGenericoPassivoBP)context.getBusinessProcess();
//    					doc = (Documento_genericoBulk)bp.getModel();
//    					for (java.util.Iterator i = dettagliInventariati.iterator(); i.hasNext();) {
//        					Documento_generico_rigaBulk dettaglio = (Documento_generico_rigaBulk)i.next();
//        					if (((DocumentoGenericoComponentSession)bp.createComponentSession()).ha_beniColl(context.getUserContext(), dettaglio))
//        						dettaglio.setInventariato(true);
//        					else
//        						dettaglio.setInventariato(false);
//        				}
//    				}
//    				else{
//    					bp_att = (CRUDDocumentoGenericoAttivoBP)context.getBusinessProcess();
//    					doc =(Documento_genericoBulk)bp_att.getModel();
//    					for (java.util.Iterator i = dettagliInventariati.iterator(); i.hasNext();) {
//        					Documento_generico_rigaBulk dettaglio = (Documento_generico_rigaBulk)i.next();
//        					if (((DocumentoGenericoComponentSession)bp_att.createComponentSession()).ha_beniColl(context.getUserContext(), dettaglio))
//        						dettaglio.setInventariato(true);
//        					else
//        						dettaglio.setInventariato(false);
//        				}
//    				}
//    				
//    			}
//    			
//    			return context.findDefaultForward();
//    		} catch(Exception e) {
//    			return handleException(context,e);
//    		}
//    	}
//    	/**
//    	 * Aggiunge, una volta terminata l'operazione di creazione di buono di carico,
//    	 * tale buono al modello
//    	 *
//    	 * @param context	L'ActionContext della richiesta
//    	 * @return Il Forward alla pagina di risposta
//    	 */
//    	public Forward doBringBackInventariaDettagli(ActionContext context) {
//    	
//    		try {
//    			HookForward hook = (HookForward)context.getCaller();
//    			Buono_carico_scaricoBulk buonoCS = (Buono_carico_scaricoBulk)hook.getParameter("bringback");
//    			
//    			if (buonoCS != null) {
//    				buonoCS.setByDocumento(true);
//    				java.util.List dettagliInventariati = (java.util.List)hook.getParameter("dettagliDaInventariare");
//    				if (buonoCS.getTi_documento().compareTo(Buono_carico_scaricoBulk.CARICO)==0){
//    					CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)context.getBusinessProcess();
//	    				Documento_genericoBulk documento = (Documento_genericoBulk)bp.getModel();
//	    				for (java.util.Iterator i = dettagliInventariati.iterator(); i.hasNext();) {
//	    					Documento_generico_rigaBulk dettaglio = (Documento_generico_rigaBulk)i.next();
//	    					documento.addToCarichiInventarioHash(buonoCS, dettaglio);
//	    					dettaglio.setInventariato(true);
//	    				}
//    			}else{
//    				CRUDDocumentoGenericoAttivoBP bp = (CRUDDocumentoGenericoAttivoBP)context.getBusinessProcess();
//    				Documento_genericoBulk documento = (Documento_genericoBulk)bp.getModel();
//    				for (java.util.Iterator i = dettagliInventariati.iterator(); i.hasNext();) {
//    					Documento_generico_rigaBulk dettaglio = (Documento_generico_rigaBulk)i.next();
//    					documento.addToCarichiInventarioHash(buonoCS, dettaglio);
//    					dettaglio.setInventariato(true);
//    				}
//    			}
//    		}
//    			return context.findDefaultForward();
//    		} catch(Exception e) {
//    			return handleException(context,e);
//    		}
//    	}
//   	public Forward basicDoAssociaDettagli(ActionContext context) 
//  	throws BusinessProcessException,
//  			ComponentException, 
//  			java.rmi.RemoteException, PersistencyException, IntrospectionException, EJBException, SQLException {
//    		
//    		CRUDDocumentoGenericoPassivoBP bp=null;
//    		CRUDDocumentoGenericoAttivoBP bp_att=null;
//    		Documento_genericoBulk documento =null;
//    		
//    		if(context.getBusinessProcess() instanceof  CRUDDocumentoGenericoPassivoBP){
//    			bp = (CRUDDocumentoGenericoPassivoBP)context.getBusinessProcess();
//    			documento = (Documento_genericoBulk)bp.getModel();
//    		}
//    		else{
//    			bp_att = (CRUDDocumentoGenericoAttivoBP)context.getBusinessProcess();
//    			documento = (Documento_genericoBulk)bp_att.getModel();
//    		}
//    			
//    		
//    		for (java.util.Iterator i = documento.getDocumento_generico_dettColl().iterator(); i.hasNext();) {
//    			Documento_generico_rigaBulk riga = (Documento_generico_rigaBulk)i.next();
//    			AssociazioniInventarioTable associazioni = documento.getAssociazioniInventarioHash();	
//    			if ((associazioni != null && !associazioni.isEmpty())|| (riga.getCrudStatus()!=OggettoBulk.TO_BE_CREATED)) {
//    				Ass_inv_bene_fatturaBulk ass = documento.getAssociationWithInventarioFor(riga);
//    				if ((ass != null) && !ass.isPerAumentoValore()) {
//    					if (riga.isInventariato()) riga.setInventariato(false);
//    				}
//    				else if (riga.getCrudStatus()!=OggettoBulk.TO_BE_CREATED && documento.getTi_entrate_spese()==Documento_genericoBulk.SPESE &&
//    						(riga.getObbligazione_scadenziario() !=null && riga.getObbligazione_scadenziario().getObbligazione()!= null && 
//    						 riga.getObbligazione_scadenziario().getObbligazione().getElemento_voce()!=null &&
//    						riga.getObbligazione_scadenziario().getObbligazione().getElemento_voce().getFl_inv_beni_patr().booleanValue())){
//    					riga.setInventariato(false);
//    				}
//    				else if (riga.getCrudStatus()!=OggettoBulk.TO_BE_CREATED && documento.getTi_entrate_spese()==Documento_genericoBulk.ENTRATE &&
//    						riga.getAccertamento_scadenziario() !=null && riga.getAccertamento_scadenziario().getAccertamento()!= null &&
//    						riga.getAccertamento_scadenziario().getAccertamento().getCd_elemento_voce()!=null){ 
//        							Elemento_voceBulk elem_voce = new Elemento_voceBulk(riga.getAccertamento_scadenziario().getAccertamento().getCd_elemento_voce(),
//        																			riga.getAccertamento_scadenziario().getAccertamento().getEsercizio(),
//        																			riga.getAccertamento_scadenziario().getAccertamento().getTi_appartenenza(),
//        																			riga.getAccertamento_scadenziario().getAccertamento().getTi_gestione());
//        							elem_voce =((Elemento_voceBulk)(((FatturaAttivaSingolaComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRDOCAMM00_EJB_FatturaAttivaSingolaComponentSession",FatturaAttivaSingolaComponentSession.class)).completaOggetto(context.getUserContext(),elem_voce)));
//    								if (elem_voce.getFl_inv_beni_patr().booleanValue())
//    									riga.setInventariato(false);
//        			}							
//    			}			
//    		}//ricerca
//    		java.util.List dettagliDaInventariare =null;
//    		if (bp!=null)
//    			dettagliDaInventariare = getDettagliDaInventariare(context, bp.getDettaglio().getDetails().iterator());
//    		else
//    			dettagliDaInventariare = getDettagliDaInventariare(context, bp_att.getDettaglio().getDetails().iterator());
//    		if (dettagliDaInventariare != null && !dettagliDaInventariare.isEmpty()) {
//    			AssBeneFatturaBP ibp = (AssBeneFatturaBP)context.getUserInfo().createBusinessProcess(context,"AssBeneFatturaBP",new Object[] { "MRSWTh" });
//    			Ass_inv_bene_fatturaBulk associa = new Ass_inv_bene_fatturaBulk();
//    			associa.setLocal_transactionID(ibp.getLocalTransactionID(context.getUserContext(),true));
//    			associa.completeFrom(dettagliDaInventariare);
//    			associa.setInventario(((BuonoCaricoScaricoComponentSession)EJBCommonServices.createEJB(
//    									"CNRINVENTARIO01_EJB_BuonoCaricoScaricoComponentSession",
//    									BuonoCaricoScaricoComponentSession.class)).caricaInventario(context.getUserContext()));
//    				
//    			ibp.setModel(context,associa);
//    			ibp.setDaDocumento(true);
//    			ibp.setDirty(false);		
//    			context.addHookForward("bringback",this,"doBringBackAssociaInventario");
//    			HookForward hook = (HookForward)context.findForward("bringback");
//    			hook.addParameter("dettagliDaInventariare", dettagliDaInventariare);
//    			return context.addBusinessProcess(ibp);
//    		}else
//    		{  //R.P. MODIFICA BENI gi� ASSOCIATI(IN SOSPESO)
//    			if (documento.getCrudStatus()!=OggettoBulk.TO_BE_CREATED){
//    				java.util.List dettagli=null;
//    				if(bp!=null)
//    				    dettagli = bp.getDettaglio().getDetails();
//    				else
//    					 dettagli = bp_att.getDettaglio().getDetails();
//    				   for(Iterator i=dettagli.iterator();i.hasNext();){
//    					   Documento_generico_rigaBulk riga=(Documento_generico_rigaBulk)i.next();
//    						if (documento.getTi_entrate_spese()==Documento_genericoBulk.SPESE &&  riga.getObbligazione_scadenziario() !=null && riga.getObbligazione_scadenziario().getObbligazione()!= null && 
//    								riga.getObbligazione_scadenziario().getObbligazione().getElemento_voce()!=null &&
//    								riga.getObbligazione_scadenziario().getObbligazione().getElemento_voce().getFl_inv_beni_patr().booleanValue() &&
//    								!riga.isInventariato())
//    					   			dettagliDaInventariare.add(riga);
//    						else{
//    							if(riga.getAccertamento_scadenziario() !=null && riga.getAccertamento_scadenziario().getAccertamento()!= null &&
//    							   riga.getAccertamento_scadenziario().getAccertamento().getCd_elemento_voce()!=null){ 
//            							Elemento_voceBulk elem_voce = new Elemento_voceBulk(riga.getAccertamento_scadenziario().getAccertamento().getCd_elemento_voce(),
//            																			riga.getAccertamento_scadenziario().getAccertamento().getEsercizio(),
//            																			riga.getAccertamento_scadenziario().getAccertamento().getTi_appartenenza(),
//            																			riga.getAccertamento_scadenziario().getAccertamento().getTi_gestione());
//            							elem_voce =((Elemento_voceBulk)(((FatturaAttivaSingolaComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRDOCAMM00_EJB_FatturaAttivaSingolaComponentSession",FatturaAttivaSingolaComponentSession.class)).completaOggetto(context.getUserContext(),elem_voce)));
//        								if (elem_voce.getFl_inv_beni_patr().booleanValue() && !riga.isInventariato())
//        									dettagliDaInventariare.add(riga);
//    						}
//    					 }
//    				   }
//    				if (dettagliDaInventariare.size()==0 && bp!=null){
//    					bp.setMessage("Nessun dettaglio � inventariabile o tutti i dettagli inventariabili sono gi� stati caricati!");
//    					return context.findDefaultForward();
//    				}
//    				else if (dettagliDaInventariare.size()==0 && bp_att!=null){
//    					bp_att.setMessage("Nessun dettaglio � inventariabile o tutti i dettagli inventariabili sono gi� stati caricati!");
//    					return context.findDefaultForward();
//    				}
//    					
//    				AssBeneFatturaBP ibp = (AssBeneFatturaBP)context.getUserInfo().createBusinessProcess(context,"AssBeneFatturaBP",new Object[] { "MRSWTh" });
//    				Ass_inv_bene_fatturaBulk associa = new Ass_inv_bene_fatturaBulk();
//    				associa.setLocal_transactionID(ibp.getLocalTransactionID(context.getUserContext(),true));
//    				associa.completeFrom(dettagliDaInventariare);
//    				associa.setInventario(((BuonoCaricoScaricoComponentSession)EJBCommonServices.createEJB(
//    						"CNRINVENTARIO01_EJB_BuonoCaricoScaricoComponentSession",
//    						BuonoCaricoScaricoComponentSession.class)).caricaInventario(context.getUserContext()));
//    			
//    				ibp.setModel(context,associa);			
//    				ibp.setDirty(false);		
//    					
//    				context.addHookForward("bringback",this,"doBringBackAssociaInventario");
//    				HookForward hook = (HookForward)context.findForward("bringback");
//    				hook.addParameter("dettagliDaInventariare", dettagliDaInventariare);
//    				return context.addBusinessProcess(ibp);
//    			}
//    			else{
//    				if (bp!=null){
//    					bp.setMessage("Nessun dettaglio � inventariabile o tutti i dettagli inventariabili sono gi� stati caricati!");
//    					return context.findDefaultForward();
//    				}
//    				else if( bp_att!=null){
//    					bp_att.setMessage("Nessun dettaglio � inventariabile o tutti i dettagli inventariabili sono gi� stati caricati!");
//    					return context.findDefaultForward();
//    				}else
//       					return context.findDefaultForward();        				
//    			}
//    		}
//    	}
//    	/**
//    	 * creo una nuova istanza di buono di carico; lo inizializzo, aggiungo i dettagli
//    	 * selezionati e ne richiedo l'apertura. Se esistevano gi� dei buoni di carico, li
//    	 * elimino
//    	 *
//    	 * @param context	L'ActionContext della richiesta
//    	 * @return Il Forward alla pagina di risposta
//    	 * @throws BusinessProcessException	
//    	 * @throws ComponentException	
//    	 * @throws RemoteException	Se si verifica qualche eccezione di sistema per cui non � possibile effettuare l'operazione
//    	 * @throws SQLException 
//    	 * @throws PersistencyException 
//    	 */
//    	public Forward basicDoInventariaDettagli(ActionContext context,char tipo) 
//    		throws BusinessProcessException,
//    				ComponentException, 
//    				java.rmi.RemoteException, PersistencyException, SQLException {
//    	if (tipo==Documento_genericoBulk.SPESE){
//    		CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)context.getBusinessProcess();
//    		Documento_genericoBulk documento = (Documento_genericoBulk)bp.getModel();
//    		CarichiInventarioTable carichi = documento.getCarichiInventarioHash();
//    		if (carichi != null && !carichi.isEmpty()) {
//    			BuonoCaricoScaricoComponentSession h = (BuonoCaricoScaricoComponentSession)bp.createComponentSession(
//    														"CNRINVENTARIO01_EJB_BuonoCaricoScaricoComponentSession", 
//    														BuonoCaricoScaricoComponentSession.class);
//    			for (java.util.Enumeration e = ((CarichiInventarioTable)carichi.clone()).keys(); e.hasMoreElements();) {
//    				Buono_carico_scaricoBulk buono = (Buono_carico_scaricoBulk)e.nextElement();
//    				buono.setToBeDeleted();
//    				h.eliminaConBulk(context.getUserContext(), buono);
//    				carichi.remove(buono);
//    			}
//    			for (java.util.Iterator i = documento.getDocumento_generico_dettColl().iterator(); i.hasNext();) {
//    				Documento_generico_rigaBulk riga = (Documento_generico_rigaBulk)i.next();
//    				if (riga.isInventariato() && !documento.getHa_beniColl()) riga.setInventariato(false);
//    			}
//    		}
//    	
//    		java.util.List dettagliDaInventariare = getDettagliDaInventariare(context, bp.getDettaglio().getDetails().iterator());
//    		if (dettagliDaInventariare != null && !dettagliDaInventariare.isEmpty()) {
//    			CRUDCaricoInventarioBP ibp = (CRUDCaricoInventarioBP)context.createBusinessProcess("CRUDCaricoInventarioBP",new Object[] { "MRSWTh" });
//    			Buono_carico_scaricoBulk bcs = new Buono_carico_scaricoBulk();
//    		
//    			bcs.setTi_documento(Buono_carico_scaricoBulk.CARICO);
//    			bcs.setByDocumento(Boolean.TRUE);
//    			bcs.initializeForInsert(ibp,context);
//    			
//    			bcs = (it.cnr.contab.inventario01.bulk.Buono_carico_scaricoBulk)ibp.createComponentSession().inizializzaBulkPerInserimento(context.getUserContext(),bcs);
//    			bcs.completeFrom(dettagliDaInventariare);
//    			try{
//    				
//    				bcs.setPg_buono_c_s(((NumerazioneTempBuonoComponentSession)EJBCommonServices.createEJB(
//    									"CNRINVENTARIO01_EJB_NumerazioneTempBuonoComponentSession",
//    									NumerazioneTempBuonoComponentSession.class)).getNextTempPG(context.getUserContext(),bcs));
//    				
//    				}catch (Throwable e){
//    					throw new ComponentException (e);
//    				}
//    			ibp.setModel(context,bcs);
//    			ibp.setStatus(ibp.INSERT);
//    			ibp.setDirty(false);
//    			ibp.setBy_documento(Boolean.TRUE);
//    			ibp.resetChildren(context);
//    			
//    			context.addHookForward("bringback",this,"doBringBackInventariaDettagli");
//    			HookForward hook = (HookForward)context.findForward("bringback");
//    			hook.addParameter("dettagliDaInventariare", dettagliDaInventariare);
//    			return context.addBusinessProcess(ibp);
//    		}
//    		bp.setMessage("Nessun dettaglio � inventariabile o tutti i dettagli inventariabili sono gi� stati caricati!");
//    		return context.findDefaultForward();
//    	}else{
//    		CRUDDocumentoGenericoAttivoBP bp = (CRUDDocumentoGenericoAttivoBP)context.getBusinessProcess();
//    		Documento_genericoBulk documento = (Documento_genericoBulk)bp.getModel();
//    		CarichiInventarioTable carichi = documento.getCarichiInventarioHash();
//    		if (carichi != null && !carichi.isEmpty()) {
//    			BuonoCaricoScaricoComponentSession h = (BuonoCaricoScaricoComponentSession)bp.createComponentSession(
//    														"CNRINVENTARIO01_EJB_BuonoCaricoScaricoComponentSession", 
//    														BuonoCaricoScaricoComponentSession.class);
//    			for (java.util.Enumeration e = ((CarichiInventarioTable)carichi.clone()).keys(); e.hasMoreElements();) {
//    				Buono_carico_scaricoBulk buono = (Buono_carico_scaricoBulk)e.nextElement();
//    				buono.setToBeDeleted();
//    				h.eliminaConBulk(context.getUserContext(), buono);
//    				carichi.remove(buono);
//    			}
//    			for (java.util.Iterator i = documento.getDocumento_generico_dettColl().iterator(); i.hasNext();) {
//    				Documento_generico_rigaBulk riga = (Documento_generico_rigaBulk)i.next();
//    				if (riga.isInventariato() && !documento.getHa_beniColl()) riga.setInventariato(false);
//    			}
//    		}
//    	
//    		java.util.List dettagliDaInventariare = getDettagliDaInventariare(context, bp.getDettaglio().getDetails().iterator());
//    		if (dettagliDaInventariare != null && !dettagliDaInventariare.isEmpty()) {
//    			CRUDScaricoInventarioBP ibp = (CRUDScaricoInventarioBP)context.createBusinessProcess("CRUDScaricoInventarioBP",new Object[] { "MRSWTh" });
//    			Buono_carico_scaricoBulk bcs = new Buono_carico_scaricoBulk();
//    		
//    			bcs.setTi_documento(Buono_carico_scaricoBulk.SCARICO);
//    			bcs.setByDocumento(Boolean.TRUE);
//    			bcs.setPerVendita(Boolean.TRUE);
//    			bcs.initializeForInsert(ibp,context);
//    			
//    			bcs = (it.cnr.contab.inventario01.bulk.Buono_carico_scaricoBulk)ibp.createComponentSession().inizializzaBulkPerInserimento(context.getUserContext(),bcs);
//    			bcs.completeFrom(dettagliDaInventariare);
//    			bcs.setUser(documento.getUser());
//    			try{
//    				
//    				bcs.setPg_buono_c_s(((NumerazioneTempBuonoComponentSession)EJBCommonServices.createEJB(
//    									"CNRINVENTARIO01_EJB_NumerazioneTempBuonoComponentSession",
//    									NumerazioneTempBuonoComponentSession.class)).getNextTempPG(context.getUserContext(),bcs));
//    				
//    				}catch (Throwable e){
//    					throw new ComponentException (e);
//    				}
//    			ibp.setModel(context,bcs);
//    			ibp.setStatus(ibp.INSERT);
//    			ibp.setDirty(false);
//    			ibp.setBy_documento(Boolean.TRUE);
//    			ibp.resetChildren(context);
//    			
//    			context.addHookForward("bringback",this,"doBringBackInventariaDettagli");
//    			HookForward hook = (HookForward)context.findForward("bringback");
//    			hook.addParameter("dettagliDaInventariare", dettagliDaInventariare);
//    			return context.addBusinessProcess(ibp);
//    		}
//    		bp.setMessage("Nessun dettaglio � inventariabile o tutti i dettagli inventariabili sono gi� stati caricati!");
//    		return context.findDefaultForward();
//    	}
//    }
//    public Forward doRemoveFromCRUDMain_Dettaglio(ActionContext context) {
//    		try {
//    			CRUDDocumentoGenericoAttivoBP bp_att=null;
//    			CRUDDocumentoGenericoPassivoBP bp=null;
//    			if (getBusinessProcess(context)instanceof CRUDDocumentoGenericoPassivoBP)
//    				 bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//    			else 
//    				 bp_att = (CRUDDocumentoGenericoAttivoBP)getBusinessProcess(context);
//    			if (bp!=null){
//    				Documento_genericoBulk documento = (Documento_genericoBulk)bp.getModel();
//    				java.util.Vector dettagliInventariatiEliminati = new java.util.Vector();
//    				for (it.cnr.jada.util.action.SelectionIterator i = bp.getDettaglio().getSelection().iterator(); i.hasNext();) {
//    					Documento_generico_rigaBulk dett = (Documento_generico_rigaBulk)bp.getDettaglio().getDetails().get(i.nextIndex());
//    				
//    					if (dett.getObbligazione_scadenziario() !=null && dett.getObbligazione_scadenziario().getObbligazione()!= null && 
//							dett.getObbligazione_scadenziario().getObbligazione().getElemento_voce()!=null &&
//							dett.getObbligazione_scadenziario().getObbligazione().getElemento_voce().getFl_inv_beni_patr().booleanValue() &&
//							dett.isInventariato()){
//    						dettagliInventariatiEliminati.add(dett);
//    					}
//    				}
//	    			bp.getDettaglio().remove(context);
//	    			if(dettagliInventariatiEliminati.size()!=0){
//	    			for (java.util.Iterator i = dettagliInventariatiEliminati.iterator(); i.hasNext();) {
//	    				Documento_generico_rigaBulk dett = (Documento_generico_rigaBulk)i.next();
//	    				AssociazioniInventarioTable associazioni = documento.getAssociazioniInventarioHash();
//	    				if (associazioni != null && !associazioni.isEmpty() && dett instanceof Documento_generico_rigaBulk) {
//	    					Ass_inv_bene_fatturaBulk ass = documento.getAssociationWithInventarioFor(dett);
//	    						if (ass != null && !ass.isPerAumentoValoreDoc()) {
//	    							Documento_generico_rigaBulk dettaglio = (Documento_generico_rigaBulk)dett;
//	    							DocumentoGenericoComponentSession h = (DocumentoGenericoComponentSession)bp.createComponentSession();
//	    							h.rimuoviDaAssociazioniInventario(
//	    													context.getUserContext(),
//	    													dettaglio,
//	    													ass);
//	    							documento.removeFromAssociazioniInventarioHash(ass, dettaglio);
//	    						
//	    					    }else if (ass !=null && ass.isPerAumentoValoreDoc()){ 
//	    									BuonoCaricoScaricoComponentSession buono_session = (BuonoCaricoScaricoComponentSession)bp.createComponentSession(
//	    									"CNRINVENTARIO01_EJB_BuonoCaricoScaricoComponentSession", 
//	    									BuonoCaricoScaricoComponentSession.class);
//	    									Buono_carico_scaricoBulk buono=ass.getTest_buono();
//	    									buono.setToBeDeleted();
//	    									buono_session.eliminaConBulk(context.getUserContext(), buono);
//	    									for (java.util.Iterator iter = documento.getDocumento_generico_dettColl().iterator(); iter.hasNext();) {
//	    										Documento_generico_rigaBulk riga = (Documento_generico_rigaBulk)iter.next();
//	    										if (riga.isInventariato() && !documento.getHa_beniColl()) riga.setInventariato(false);
//	    									}
//	    									Documento_generico_rigaBulk dettaglio = (Documento_generico_rigaBulk)dett;
//	    									DocumentoGenericoComponentSession h = (DocumentoGenericoComponentSession)bp.createComponentSession();
//	    									h.rimuoviDaAssociazioniInventario(
//	    																context.getUserContext(),
//	    																dettaglio,
//	    																ass);
//	    									documento.removeFromAssociazioniInventarioHash(ass, dettaglio);
//	    						}
//	    					 }
//	    					 else{
//    							CarichiInventarioTable carichi = documento.getCarichiInventarioHash();
//    							if (carichi != null && !carichi.isEmpty()) {
//    								BuonoCaricoScaricoComponentSession h = (BuonoCaricoScaricoComponentSession)bp.createComponentSession(
//    																		"CNRINVENTARIO01_EJB_BuonoCaricoScaricoComponentSession", 
//    																		BuonoCaricoScaricoComponentSession.class);
//    								for (java.util.Enumeration e = ((CarichiInventarioTable)carichi.clone()).keys(); e.hasMoreElements();) {
//    									Buono_carico_scaricoBulk buono = (Buono_carico_scaricoBulk)e.nextElement();
//    									buono.setToBeDeleted();
//    									h.eliminaConBulk(context.getUserContext(), buono);
//    									carichi.remove(buono);
//    								}
//    								for (java.util.Iterator iter = documento.getDocumento_generico_dettColl().iterator(); iter.hasNext();) {
//    									Documento_generico_rigaBulk riga = (Documento_generico_rigaBulk)iter.next();
//    									if (riga.isInventariato() && !documento.getHa_beniColl()) riga.setInventariato(false);
//    								}
//    							}
//    							
//	    					 }
//    					}
//	    			}
//    			}// Generici attivi
//	    		else{
//	    			Documento_genericoBulk documento = (Documento_genericoBulk)bp_att.getModel();
//	    			java.util.Vector dettagliInventariatiEliminati = new java.util.Vector();
//	    			for (it.cnr.jada.util.action.SelectionIterator i = bp_att.getDettaglio().getSelection().iterator(); i.hasNext();) {
//	    				Documento_generico_rigaBulk dett = (Documento_generico_rigaBulk)bp_att.getDettaglio().getDetails().get(i.nextIndex());
//    					if (dett.getAccertamento_scadenziario() !=null && dett.getAccertamento_scadenziario().getAccertamento()!= null &&
//        						dett.getAccertamento_scadenziario().getAccertamento().getCd_elemento_voce()!=null){ 
//    							Elemento_voceBulk elem_voce = new Elemento_voceBulk(dett.getAccertamento_scadenziario().getAccertamento().getCd_elemento_voce(),
//    									dett.getAccertamento_scadenziario().getAccertamento().getEsercizio(),
//    									dett.getAccertamento_scadenziario().getAccertamento().getTi_appartenenza(),
//    									dett.getAccertamento_scadenziario().getAccertamento().getTi_gestione());
//    							elem_voce =((Elemento_voceBulk)(((FatturaAttivaSingolaComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRDOCAMM00_EJB_FatturaAttivaSingolaComponentSession",FatturaAttivaSingolaComponentSession.class)).completaOggetto(context.getUserContext(),elem_voce)));
//    							if (elem_voce.getFl_inv_beni_patr().booleanValue() && dett.isInventariato())
//    								dettagliInventariatiEliminati.add(dett);
//    					}
//    				}
//	    			
//	    			bp_att.getDettaglio().remove(context);
//	    			for (java.util.Iterator i = dettagliInventariatiEliminati.iterator(); i.hasNext();) {
//	    				Documento_generico_rigaBulk dett = (Documento_generico_rigaBulk)i.next();
//	    				AssociazioniInventarioTable associazioni = documento.getAssociazioniInventarioHash();
//	    				if (associazioni != null && !associazioni.isEmpty()) {
//	    					Ass_inv_bene_fatturaBulk ass = documento.getAssociationWithInventarioFor(dett);
//	    					if (ass != null && !ass.isPerAumentoValore()) {
//	    						Documento_generico_rigaBulk dettaglio = (Documento_generico_rigaBulk)dett;
//	    						DocumentoGenericoComponentSession h = (DocumentoGenericoComponentSession)bp_att.createComponentSession();
//	    						h.rimuoviDaAssociazioniInventario(
//	    													context.getUserContext(),
//	    													dettaglio,
//	    													ass);
//	    						documento.removeFromAssociazioniInventarioHash(ass, dettaglio);
//	    					
//	    				}else if (ass !=null && ass.isPerAumentoValore()){ 
//	    								BuonoCaricoScaricoComponentSession buono_session = (BuonoCaricoScaricoComponentSession)bp_att.createComponentSession(
//	    								"CNRINVENTARIO01_EJB_BuonoCaricoScaricoComponentSession", 
//	    								BuonoCaricoScaricoComponentSession.class);
//	    								Buono_carico_scaricoBulk buono=ass.getTest_buono();
//	    								buono.setToBeDeleted();
//	    								buono_session.eliminaConBulk(context.getUserContext(), buono);
//	    								for (java.util.Iterator iter = documento.getDocumento_generico_dettColl().iterator(); iter.hasNext();) {
//	    									Documento_generico_rigaBulk riga = (Documento_generico_rigaBulk)iter.next();
//	    									if (riga.isInventariato() && !documento.getHa_beniColl()) riga.setInventariato(false);
//	    								}
//	    								
//	    								DocumentoGenericoComponentSession h = (DocumentoGenericoComponentSession)bp_att.createComponentSession();
//	    								h.rimuoviDaAssociazioniInventario(
//	    															context.getUserContext(),
//	    															dett,
//	    															ass);
//	    								documento.removeFromAssociazioniInventarioHash(ass, dett);
//	    					}
//	    				}
//	    					else{
//	    						CarichiInventarioTable carichi = documento.getCarichiInventarioHash();
//	    						if (carichi != null && !carichi.isEmpty()) {
//	    							BuonoCaricoScaricoComponentSession h = (BuonoCaricoScaricoComponentSession)bp_att.createComponentSession(
//	    																	"CNRINVENTARIO01_EJB_BuonoCaricoScaricoComponentSession", 
//	    																	BuonoCaricoScaricoComponentSession.class);
//	    							for (java.util.Enumeration e = ((CarichiInventarioTable)carichi.clone()).keys(); e.hasMoreElements();) {
//	    								Buono_carico_scaricoBulk buono = (Buono_carico_scaricoBulk)e.nextElement();
//	    								buono.setToBeDeleted();
//	    								h.eliminaConBulk(context.getUserContext(), buono);
//	    								carichi.remove(buono);
//	    							}
//	    							for (java.util.Iterator iter = documento.getDocumento_generico_dettColl().iterator(); iter.hasNext();) {
//	    								Documento_generico_rigaBulk riga = (Documento_generico_rigaBulk)iter.next();
//	    								if (riga.isInventariato() && !documento.getHa_beniColl()) riga.setInventariato(false);
//	    							}
//	    						}
//	    			  }			
//	    			}	
//	    		}
//    			return context.findDefaultForward();
//    		} catch(Throwable e) {
//    			return handleException(context,e);
//    		}
//    	}
//    public Forward doConfermaAssocia(ActionContext context,int option) {
//
//    	try {
//    		//CRUDDocumentoGenericoPassivoBP bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//    		if (option == it.cnr.jada.util.action.OptionBP.YES_BUTTON) {
//    			return basicDoAssociaDettagli(context);
//    		}
//    	} catch (Throwable t) {
//    		return handleException(context, t);
//    	}
//    	return context.findDefaultForward();
//    } 
//   public Forward doOnStatoLiquidazioneChange(ActionContext context) {
//		 try { 
//			 CRUDDocumentoGenericoPassivoBP bp=null; 
//	  		if (getBusinessProcess(context)instanceof CRUDDocumentoGenericoPassivoBP)
//				 bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//	  		if (bp!=null){ 
//				Documento_genericoBulk documento = (Documento_genericoBulk)bp.getModel();
//				//if (documento.getTipo_documento().getCd_tipo_documento_amm().compareTo(documento.GENERICO_S)==0)
//	  		 String oldCausale=documento.getCausale();
//	  		 fillModel(context);
//			 if(documento.getStato_liquidazione()!=null && documento.getStato_liquidazione().equals(documento.LIQ)){
//		       	if(documento.getCausale()!=null){ 
//		       		documento.setCausale(null);
//		       	} 
//		     }else if(documento.getStato_liquidazione()!=null && documento.getStato_liquidazione().equals(documento.SOSP)){
//		    	 documento.setCausale(documento.ATTLIQ);
//		     } else if(documento.getStato_liquidazione()!=null && documento.getStato_liquidazione().equals(documento.NOLIQ)){
//		    	 documento.setCausale(documento.CONT);
//		     }
//		     bp.setModel(context, documento);
//	  		}
//		   } catch (Throwable t) {
//		        return handleException(context, t);
//		  }
//	return context.findDefaultForward();
//   }
//	public Forward doOnCausaleChange(ActionContext context) {
//		try {
//			  CRUDDocumentoGenericoPassivoBP bp=null; 
//		  		if (getBusinessProcess(context)instanceof CRUDDocumentoGenericoPassivoBP)
//					 bp = (CRUDDocumentoGenericoPassivoBP)getBusinessProcess(context);
//		  		if (bp!=null){
//					Documento_genericoBulk documento = (Documento_genericoBulk)bp.getModel();
//		  		 String oldCausale=documento.getCausale();
//		  		 fillModel(context);
//		         if(documento.getStato_liquidazione()!=null && documento.getStato_liquidazione().equals(documento.LIQ)){
//		        	if(documento.getCausale()!=null){ 
//		        		documento.setCausale(null);
//		        		throw new ApplicationException("Causale non valida, per lo stato della Liquidazione");
//		        	}
//		         }else if(documento.getStato_liquidazione()!=null && documento.getStato_liquidazione().equals(documento.NOLIQ)){
//		        	
//		        	if (documento.getCausale()!= null && !documento.getCausale().equals(documento.CONT)){
//		        		if(oldCausale!=null)
//		        			documento.setCausale(oldCausale);
//		        		else
//		        			documento.setCausale(null);
//		        		throw new ApplicationException("Causale non valida, per lo stato della Liquidazione");
//		        	}
//		         }else if(documento.getStato_liquidazione()!=null && documento.getStato_liquidazione().equals(documento.SOSP)){
//		        	if (documento.getCausale()!= null && (!documento.getCausale().equals(documento.ATTLIQ)&&!documento.getCausale().equals(documento.CONT))){
//		        		if(oldCausale!=null )
//		        			documento.setCausale(oldCausale);
//		        		else
//		        			documento.setCausale(null);
//	        		  throw new ApplicationException("Causale non valida, per lo stato della Liquidazione");
//		        	}
//		        }
//		 	       
//		        bp.setModel(context, documento);
//		  	}
//		  } catch (Throwable t) {
//		      return handleException(context, t);
//		  }
//		 return context.findDefaultForward();
//	}
}
