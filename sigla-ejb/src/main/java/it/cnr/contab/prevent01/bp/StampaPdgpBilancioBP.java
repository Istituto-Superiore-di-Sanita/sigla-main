package it.cnr.contab.prevent01.bp;

import java.util.Enumeration;

import it.cnr.contab.config00.pdcfin.cla.bulk.Parametri_livelliBulk;
import it.cnr.contab.prevent01.bulk.Stampa_pdgp_bilancioBulk;
import it.cnr.contab.prevent01.ejb.PdgAggregatoModuloComponentSession;
import it.cnr.contab.reports.bp.ParametricPrintBP;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.util.Utility;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.ComponentException;


public class StampaPdgpBilancioBP extends ParametricPrintBP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3023075061400467210L;

	/**
	 * StampaRendicontoFinanziarioBP constructor comment.
	 */
	public StampaPdgpBilancioBP() {
		super();
	}
	/**
	 * StampaRendicontoFinanziarioBP constructor comment.
	 * @param function java.lang.String
	 */
	public StampaPdgpBilancioBP(String function) {
		super(function);
	}
	
	@Override
	public OggettoBulk initializeBulkForPrint(ActionContext context, OggettoBulk bulk) throws BusinessProcessException {
		try {
			OggettoBulk oggettoBulk = super.initializeBulkForPrint(context, bulk);
			if (oggettoBulk instanceof Stampa_pdgp_bilancioBulk) {
				((Stampa_pdgp_bilancioBulk) oggettoBulk).setEsercizio(CNRUserContext.getEsercizio(context.getUserContext()));
				((Stampa_pdgp_bilancioBulk) oggettoBulk).setTi_stampa(Stampa_pdgp_bilancioBulk.TIPO_DECISIONALE);				
				((Stampa_pdgp_bilancioBulk) oggettoBulk).setTi_aggregazione(Stampa_pdgp_bilancioBulk.TIPO_SCIENTIFICO);				
				((Stampa_pdgp_bilancioBulk) oggettoBulk).setTi_origine(Stampa_pdgp_bilancioBulk.TIPO_PROVVISORIO);				
				((Stampa_pdgp_bilancioBulk) oggettoBulk).setTi_gestione(Stampa_pdgp_bilancioBulk.TIPO_GESTIONE_ENTRATA);
				((Stampa_pdgp_bilancioBulk) oggettoBulk).setTi_parte(Stampa_pdgp_bilancioBulk.TIPO_PARTE_ENTRAMBE);
				
				loadModelBulkOptions(context, (Stampa_pdgp_bilancioBulk) oggettoBulk);
			}
			return oggettoBulk;
		}catch(Throwable e) {
			throw new BusinessProcessException(e);
		}
	}
	
	public void loadModelBulkOptions(ActionContext context) throws BusinessProcessException {
		try {
			OggettoBulk oggettoBulk = this.getModel();
			if (oggettoBulk instanceof Stampa_pdgp_bilancioBulk)
				loadModelBulkOptions(context, (Stampa_pdgp_bilancioBulk) oggettoBulk);
		}catch(Throwable e) {
			throw new BusinessProcessException(e);
		}
	}

	private Stampa_pdgp_bilancioBulk loadModelBulkOptions(ActionContext context, Stampa_pdgp_bilancioBulk stampa) throws BusinessProcessException {
		try {
			Parametri_livelliBulk parliv = Utility.createClassificazioneVociComponentSession().findParametriLivelli(context.getUserContext(), stampa.getEsercizio());
	
			it.cnr.jada.util.OrderedHashtable livelliOptions = new it.cnr.jada.util.OrderedHashtable();
	
			int index = 1;
				
			if (Stampa_pdgp_bilancioBulk.TIPO_GESTIONE_SPESA.equals(stampa.getTi_gestione()) &&
				Stampa_pdgp_bilancioBulk.TIPO_SCIENTIFICO.equals(stampa.getTi_aggregazione())) {
				livelliOptions.put(index++, "Programma");
				livelliOptions.put(index++, "Missione");
			}
				
			if (Stampa_pdgp_bilancioBulk.TIPO_GESTIONE_ENTRATA.equals(stampa.getTi_gestione())) {
				if (parliv.getDs_livello1e()!=null)
					livelliOptions.put(index++, parliv.getDs_livello1e());
				if (parliv.getDs_livello2e()!=null)
					livelliOptions.put(index++, parliv.getDs_livello2e());
				if (parliv.getDs_livello3e()!=null)
					livelliOptions.put(index++, parliv.getDs_livello3e());
				if (parliv.getDs_livello4e()!=null)
					livelliOptions.put(index++, parliv.getDs_livello4e());
				if (parliv.getDs_livello5e()!=null)
					livelliOptions.put(index++, parliv.getDs_livello5e());
				if (parliv.getDs_livello6e()!=null)
					livelliOptions.put(index++, parliv.getDs_livello6e());
				if (parliv.getDs_livello7e()!=null)
					livelliOptions.put(index++, parliv.getDs_livello7e());
			} else {
				if (parliv.getDs_livello1s()!=null)
					livelliOptions.put(index++, parliv.getDs_livello1s());
				if (parliv.getDs_livello2s()!=null)
					livelliOptions.put(index++, parliv.getDs_livello2s());
				if (parliv.getDs_livello3s()!=null)
					livelliOptions.put(index++, parliv.getDs_livello3s());
				if (parliv.getDs_livello4s()!=null)
					livelliOptions.put(index++, parliv.getDs_livello4s());
				if (parliv.getDs_livello5s()!=null)
					livelliOptions.put(index++, parliv.getDs_livello5s());
				if (parliv.getDs_livello6s()!=null)
					livelliOptions.put(index++, parliv.getDs_livello6s());
				if (parliv.getDs_livello7s()!=null)
					livelliOptions.put(index++, parliv.getDs_livello7s());
			}

			stampa.setLivelliOptions(livelliOptions);
			if (stampa.getTi_livello()!=null) {
				Enumeration a = livelliOptions.keys();
				while (a.hasMoreElements()) {
					Integer key = (Integer) a.nextElement();
					if (livelliOptions.get(key).equals(stampa.getTi_livello()))
						return stampa;
				}
			}
			stampa.setTi_livello(livelliOptions.get(0).toString());
		}catch(Throwable e) {
			throw new BusinessProcessException(e);
		}
		return stampa;
	}
	
	public void stampaBilancioCallAggiornaDati(ActionContext context, boolean aggPrevAC, boolean aggResiduiAC, boolean aggPrevAP, boolean aggResiduiAP, boolean aggCassaAC) throws BusinessProcessException, ComponentException {
		((PdgAggregatoModuloComponentSession)createComponentSession()).stampaBilancioCallAggiornaDati(context.getUserContext(), (Stampa_pdgp_bilancioBulk)this.getModel(), aggPrevAC, aggResiduiAC, aggPrevAP, aggResiduiAP, aggCassaAC);
	}
	
	public void stampaRendicontoCallAggiornaDati(ActionContext context, boolean aggCompAC, boolean aggResiduiAC, boolean aggCassaAC, boolean aggCompAP, boolean aggResiduiAP, boolean aggCassaAP) throws BusinessProcessException, ComponentException {
		((PdgAggregatoModuloComponentSession)createComponentSession()).stampaRendicontoCallAggiornaDati(context.getUserContext(), (Stampa_pdgp_bilancioBulk)this.getModel(), aggCompAC, aggResiduiAC, aggCassaAC, aggCompAP, aggResiduiAP, aggCassaAP);
	}

	public boolean isStampaRendiconto() {
		return "StampaPdgRendicontoBP".equals(this.getName());
	}
	
	public boolean isStampaRendicontoGestionale() {
		return isStampaRendiconto() &&
				Stampa_pdgp_bilancioBulk.TIPO_GESTIONALE.equals(((Stampa_pdgp_bilancioBulk)this.getModel()).getTi_stampa());
	}

	@Override
	public String getFormTitle() {
		if (this.isStampaRendiconto())
			return "Stampa Rendiconto Finanziario";
		else 
			return "Stampa Bilancio Previsione";
	}
	
	@Override
	public String getReportName() {
		if (this.isStampaRendiconto()) {
			Stampa_pdgp_bilancioBulk oggettoBulk = (Stampa_pdgp_bilancioBulk)this.getModel();
			if (Stampa_pdgp_bilancioBulk.TIPO_DECISIONALE.equals(oggettoBulk.getTi_stampa()))
				return "/preventivo/preventivo/stampa_pdg_renddec.jasper";
			else if (Stampa_pdgp_bilancioBulk.TIPO_PARTE_PRIMA.equals(oggettoBulk.getTi_parte()))
				return "/preventivo/preventivo/stampa_pdg_rendges_competenza.jasper";
			else if (Stampa_pdgp_bilancioBulk.TIPO_PARTE_SECONDA.equals(oggettoBulk.getTi_parte()))
				return "/preventivo/preventivo/stampa_pdg_rendges_residui.jasper";
			else 
				return "/preventivo/preventivo/stampa_pdg_rendges_all.jasper";
		} else
			return "/preventivo/preventivo/stampa_pdgp_bilancio.jasper";
	}
}
