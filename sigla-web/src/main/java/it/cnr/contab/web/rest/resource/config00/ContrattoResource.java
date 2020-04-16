/*
 * Copyright (C) 2019  Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package it.cnr.contab.web.rest.resource.config00;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import it.cnr.contab.anagraf00.tabter.bulk.NazioneBulk;
import it.cnr.contab.doccont00.tabrif.bulk.CupBulk;
import it.cnr.contab.doccont00.tabrif.bulk.CupHome;
import it.cnr.contab.web.rest.model.ContrattoDtoBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.persistency.PersistencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cnr.contab.config00.contratto.bulk.Ass_contratto_ditteBulk;
import it.cnr.contab.config00.contratto.bulk.Ass_contratto_uoBulk;
import it.cnr.contab.config00.contratto.bulk.ContrattoBulk;
import it.cnr.contab.web.rest.model.DittaInvitataExt;
import it.cnr.contab.web.rest.model.UoAbilitataExt;
import it.cnr.contab.config00.ejb.ContrattoComponentSession;
import it.cnr.contab.config00.ejb.Unita_organizzativaComponentSession;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.web.rest.exception.RestException;
import it.cnr.contab.web.rest.local.config00.ContrattoLocal;
import it.cnr.jada.ejb.CRUDComponentSession;

@Stateless
public class ContrattoResource implements ContrattoLocal {
    private final Logger LOGGER = LoggerFactory.getLogger(ContrattoResource.class);
	@Context SecurityContext securityContext;
	@EJB CRUDComponentSession crudComponentSession;
	@EJB ContrattoComponentSession contrattoComponentSession;
	@EJB Unita_organizzativaComponentSession unita_organizzativaComponentSession;
	
    public Response insertContratto(@Context HttpServletRequest request, ContrattoDtoBulk contrattoBulk) throws Exception {

    	CNRUserContext userContext = (CNRUserContext) securityContext.getUserPrincipal();
    	Optional.ofNullable(contrattoBulk.getEsercizio()).filter(x -> userContext.getEsercizio().equals(x)).
		orElseThrow(() -> new RestException(Status.BAD_REQUEST, "Esercizio del contesto diverso da quello del Contratto"));
		Optional.ofNullable(contrattoBulk.getEsercizio()).orElse(getYearFromToday());
		ContrattoBulk contrattoBulkSigla = creaContrattoSigla(contrattoBulk, userContext);
		contrattoBulkSigla.setStato(ContrattoBulk.STATO_PROVVISORIO);
		if (contrattoBulkSigla.getCd_unita_organizzativa() != null){
			if (contrattoBulkSigla.getCd_unita_organizzativa().length() == 6){
				contrattoBulkSigla.setCd_unita_organizzativa(contrattoBulkSigla.getCd_unita_organizzativa().substring(0, 3)+"."+contrattoBulkSigla.getCd_unita_organizzativa().substring(3));
			} else {
				throw new RestException(Status.BAD_REQUEST, "L'Unita Organizzativa indicata "+contrattoBulkSigla.getCd_unita_organizzativa()+" non è conforme con il formato atteso");
			}
		} else {
			throw new RestException(Status.BAD_REQUEST, "Unita Organizzativa non indicata");
		}

		contrattoBulkSigla.setNatura_contabile(ContrattoBulk.NATURA_CONTABILE_PASSIVO);
		contrattoBulkSigla.setCd_tipo_atto("DET");

		final ContrattoBulk contratto = (ContrattoBulk) contrattoComponentSession.inizializzaBulkPerInserimento(
    			userContext,
				contrattoBulkSigla);
				
    	ContrattoBulk contrattoCreated = (ContrattoBulk) contrattoComponentSession.creaContrattoDaFlussoAcquisti(userContext, contratto);
    	contrattoBulk.setPg_contratto(contrattoCreated.getPg_contratto());
    	return Response.status(Status.CREATED).entity(contrattoBulk).build();
    }

	private ContrattoBulk creaContrattoSigla(ContrattoDtoBulk contrattoBulk, CNRUserContext userContext) throws PersistencyException, ValidationException, ComponentException, RemoteException {
		ContrattoBulk contrattoBulkSigla = new ContrattoBulk();

		contrattoBulkSigla.setCd_unita_organizzativa(contrattoBulk.getCd_unita_organizzativa());
		contrattoBulkSigla.setEsercizio(contrattoBulk.getEsercizio());
		contrattoBulkSigla.setPg_contratto(contrattoBulk.getPg_contratto());
		contrattoBulkSigla.setStato(contrattoBulk.getStato());
		contrattoBulkSigla.setIm_contratto_attivo(contrattoBulk.getIm_contratto_attivo());
		contrattoBulkSigla.setCd_organo(contrattoBulk.getCd_organo());
		contrattoBulkSigla.setCd_proc_amm(contrattoBulk.getCd_proc_amm());
		contrattoBulkSigla.setCd_protocollo(contrattoBulk.getCd_protocollo());
		contrattoBulkSigla.setCd_protocollo_generale(contrattoBulk.getCd_protocollo_generale());
		contrattoBulkSigla.setCd_terzo_firmatario(contrattoBulk.getCd_terzo_firmatario());
		contrattoBulkSigla.setCd_terzo_resp(contrattoBulk.getCd_terzo_resp());
		contrattoBulkSigla.setCd_tipo_atto(contrattoBulk.getCd_tipo_atto());
		contrattoBulkSigla.setCdCigExt(contrattoBulk.getCdCigExt());
		contrattoBulkSigla.setCd_tipo_contratto(contrattoBulk.getCd_tipo_contratto());
		contrattoBulkSigla.setCdCigFatturaAttiva(contrattoBulk.getCdCigFatturaAttiva());
		contrattoBulkSigla.setCodfisPivaAggiudicatarioExt(contrattoBulk.getCodfisPivaAggiudicatarioExt());
		contrattoBulkSigla.setCodfisPivaFirmatarioExt(contrattoBulk.getCodfisPivaFirmatarioExt());
		contrattoBulkSigla.setCodfisPivaRupExt(contrattoBulk.getCodfisPivaRupExt());
		contrattoBulkSigla.setCodiceFlussoAcquisti(contrattoBulk.getCodiceFlussoAcquisti());
		contrattoBulkSigla.setDs_atto(contrattoBulk.getDs_atto());
		contrattoBulkSigla.setDs_organo_non_definito(contrattoBulk.getDs_organo_non_definito());
		contrattoBulkSigla.setDt_fine_validita(contrattoBulk.getDt_fine_validita());
		contrattoBulkSigla.setDt_inizio_validita(contrattoBulk.getDt_inizio_validita());
		contrattoBulkSigla.setDt_proroga(contrattoBulk.getDt_proroga());
		contrattoBulkSigla.setDt_registrazione(contrattoBulk.getDt_registrazione());
		contrattoBulkSigla.setDt_stipula(contrattoBulk.getDt_stipula());
		contrattoBulkSigla.setEsercizio_protocollo(contrattoBulk.getEsercizio_protocollo());

		contrattoBulkSigla.setFig_giur_est(contrattoBulk.getFig_giur_est());
		contrattoBulkSigla.setFig_giur_int(contrattoBulk.getFig_giur_int());
		contrattoBulkSigla.setFl_art82(contrattoBulk.getFl_art82());
		contrattoBulkSigla.setFl_mepa(contrattoBulk.getFl_mepa());
		contrattoBulkSigla.setFl_pubblica_contratto(contrattoBulk.getFl_pubblica_contratto());
		contrattoBulkSigla.setIm_contratto_passivo(contrattoBulk.getIm_contratto_passivo());
		contrattoBulkSigla.setIm_contratto_passivo_netto(contrattoBulk.getIm_contratto_passivo_netto());
		contrattoBulkSigla.setNatura_contabile(contrattoBulk.getNatura_contabile());
		contrattoBulkSigla.setOggetto(contrattoBulk.getOggetto());
		contrattoBulkSigla.setPg_contratto_padre(contrattoBulk.getPg_contratto_padre());
		contrattoBulkSigla.setEsercizio_padre(contrattoBulk.getEsercizio_padre());
		contrattoBulkSigla.setPg_progetto(contrattoBulk.getPg_progetto());
		contrattoBulkSigla.setResp_esterno(contrattoBulk.getResp_esterno());
		contrattoBulkSigla.setStato_padre(contrattoBulk.getStato_padre());
		gestioneCupSuContrattoDaFlows(userContext, contrattoBulkSigla, contrattoBulk.getCdCupExt());
		if (contrattoBulk.getListaDitteInvitateExt() != null && !contrattoBulk.getListaDitteInvitateExt().isEmpty()){
			for (DittaInvitataExt ditta : contrattoBulk.getListaDitteInvitateExt()){
				Ass_contratto_ditteBulk dittaContr = new Ass_contratto_ditteBulk();
				if (ditta.getDittaExtraUE() != null && ditta.getDittaExtraUE().equals("NO")){
					dittaContr.setCodice_fiscale(ditta.getpIvaCodiceFiscaleDittaInvitata());
				} else {
					dittaContr.setId_fiscale(ditta.getpIvaCodiceFiscaleDittaInvitata());
				}
				dittaContr.setTipologia(Ass_contratto_ditteBulk.LISTA_INVITATE);
				dittaContr.setUser(contrattoBulkSigla.getUser());
				dittaContr.setDenominazione(ditta.getRagioneSocialeDittaInvitata());
				contrattoBulkSigla.addToDitteInvitate(dittaContr);
			}
		}
		if (contrattoBulk.getListaUoAbilitateExt() != null && !contrattoBulk.getListaUoAbilitateExt().isEmpty()){
			for (UoAbilitataExt uoExt : contrattoBulk.getListaUoAbilitateExt()){
				Ass_contratto_uoBulk uo = new Ass_contratto_uoBulk();
				if (uoExt.getUo() != null){
					if (uoExt.getUo().length() == 6){
						uo.setUnita_organizzativa(new Unita_organizzativaBulk());
						uo.setCd_unita_organizzativa(uoExt.getUo().substring(0, 3)+"."+uoExt.getUo().substring(3));
						uo.setContratto(contrattoBulkSigla);
						uo.setEsercizio(contrattoBulkSigla.getEsercizio());
						uo.setStato_contratto(contrattoBulkSigla.getStato());
						contrattoBulkSigla.addToAssociazioneUO(uo);
					} else {
						throw new RestException(Status.BAD_REQUEST, "L'Unita Organizzativa indicata "+uoExt.getUo()+" non è conforme con il formato atteso");
					}
				} else {
					throw new RestException(Status.BAD_REQUEST, "Unita Organizzativa non indicata");
				}
			}
		}
		return contrattoBulkSigla;
	}

	private Date getDateTodayWithoutTime(){
    	Calendar cal = getTodayWithoutTime();
    	return cal.getTime();	
    }
    private Calendar getTodayWithoutTime(){
		Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal;	
    }

    private Integer getYearFromToday() {
		Calendar cal = getTodayWithoutTime();
		return cal.get(Calendar.YEAR);
	}
	private void gestioneCupSuContrattoDaFlows(UserContext userContext, ContrattoBulk contratto, String cupExt)
			throws PersistencyException, ComponentException, RemoteException, EJBException {
		if (cupExt != null){

			CupBulk cup = new CupBulk();
			cup.setCdCup(cupExt);
			cup.setDescrizione(contratto.getOggetto());
			CupBulk cupDb = (CupBulk)crudComponentSession.findByPrimaryKey(userContext, cup);
			if (cupDb != null){
				contratto.setCup(cupDb);
			} else {
				cup.setUser(contratto.getUser());
				cup.setToBeCreated();
				contratto.setCup(cup);
			}
		}
	}
}