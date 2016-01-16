/*
 * Created on Nov 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.cnr.contab.prevent01.consultazioni.action;

import it.cnr.contab.prevent01.consultazioni.bp.ConsPDGPAreaBP;
import it.cnr.contab.prevent01.consultazioni.bp.ConsPDGPAreaEtrBP;
import it.cnr.contab.prevent01.consultazioni.bp.ConsPDGPAreaSpeBP;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Forward;
import it.cnr.jada.util.action.ConsultazioniAction;


public class ConsPDGPAreaAction extends ConsultazioniAction {
	private static final long serialVersionUID = 1L;

	public Forward doConsulta(ActionContext context, String livelloDestinazione) {
		try {
			ConsPDGPAreaBP bp = (ConsPDGPAreaBP)context.getBusinessProcess();
			bp.setSelection(context);
			long selectElements = bp.getSelection().size();
			if (selectElements == 0)
				selectElements = Integer.valueOf(bp.getSelection().getFocus()).compareTo(-1);
			
			if (selectElements == 0) {
				bp.setMessage("Non � stata selezionata nessuna riga.");
				return context.findDefaultForward();
			}
			ConsPDGPAreaBP consultazioneBP = null;
			context.closeBusinessProcess(bp);
			if (bp instanceof ConsPDGPAreaSpeBP) 
				consultazioneBP = (ConsPDGPAreaSpeBP)context.createBusinessProcess("ConsPDGPAreaSpeBP");
			else
				consultazioneBP = (ConsPDGPAreaEtrBP)context.createBusinessProcess("ConsPDGPAreaEtrBP");
			
			consultazioneBP.initVariabili(context, bp.getPathConsultazione(), livelloDestinazione);
			if ((bp.getElementsCount()!=selectElements)||!(bp.getBaseclause().toString().equals(consultazioneBP.getBaseclause().toString()))||bp.getFindclause()!=null)
				consultazioneBP.addToBaseclause(bp.getSelezione(context));
			if (consultazioneBP instanceof ConsPDGPAreaSpeBP)
				consultazioneBP.setIterator(context,consultazioneBP.createPdgpAreaComponentSession().findConsultazioneSpe(context.getUserContext(),bp.getPathDestinazione(livelloDestinazione),livelloDestinazione,consultazioneBP.getBaseclause(),null));			
			else
				consultazioneBP.setIterator(context,consultazioneBP.createPdgpAreaComponentSession().findConsultazioneEtr(context.getUserContext(),bp.getPathDestinazione(livelloDestinazione),livelloDestinazione,consultazioneBP.getBaseclause(),null));			
			return context.addBusinessProcess(consultazioneBP);
		} catch(Throwable e) {
			return handleException(context,e);
		}
	}

	public Forward doConsultaArea(ActionContext context) {
		return doConsulta(context, ConsPDGPAreaSpeBP.LIVELLO_AREA);
	}
	public Forward doConsultaModulo(ActionContext context) {
		return doConsulta(context, ConsPDGPAreaSpeBP.LIVELLO_MOD);
	}
	public Forward doConsultaLivello1(ActionContext context) {
		return doConsulta(context, ConsPDGPAreaSpeBP.LIVELLO_LIV1);
	}
	public Forward doConsultaLivello2(ActionContext context) {
		return doConsulta(context, ConsPDGPAreaSpeBP.LIVELLO_LIV2);
	}
	public Forward doConsultaLivello3(ActionContext context) {
			return doConsulta(context, ConsPDGPAreaSpeBP.LIVELLO_LIV3);
		}
	public Forward doConsultaDettagli(ActionContext context) {
		return doConsulta(context, ConsPDGPAreaSpeBP.LIVELLO_DET);
	}
	public Forward doCloseForm(ActionContext context) throws BusinessProcessException {
		Forward appoForward = super.doCloseForm(context);
		if (context.getBusinessProcess() instanceof ConsPDGPAreaBP) {
			ConsPDGPAreaBP bp = (ConsPDGPAreaBP)context.getBusinessProcess();
			bp.setTitle();
		}
		return appoForward;
	}
}