package it.cnr.contab.doccont00.action;




import it.cnr.contab.doccont00.bp.ConsSospesiBP;
import it.cnr.contab.doccont00.bp.ConsSospesiEntrateBP;
import it.cnr.contab.doccont00.bp.ConsSospesiSpeseBP;
import it.cnr.contab.doccont00.intcass.bulk.V_cons_sospesiBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Forward;
import it.cnr.jada.util.action.ConsultazioniAction;

public class ConsSospesiAction extends ConsultazioniAction{

	public Forward doConsulta(ActionContext context, String livelloDestinazione) {
		try {
			ConsSospesiBP bp = (ConsSospesiBP)context.getBusinessProcess();
			V_cons_sospesiBulk sospesi = (V_cons_sospesiBulk)bp.getModel();
			long selectElements = bp.getSelection().size();

			if (selectElements == 0)
				selectElements = Integer.valueOf(bp.getSelection().getFocus()).compareTo(-1);
			
			if (selectElements == 0) {
				bp.setMessage("Non � stata selezionata nessuna riga.");
				return context.findDefaultForward();
			}

			ConsSospesiBP consultazioneBP = null;
			if (bp instanceof ConsSospesiEntrateBP) 
				consultazioneBP = (ConsSospesiEntrateBP)context.createBusinessProcess("ConsSospesiEntrateBP");			
			else
				consultazioneBP = (ConsSospesiSpeseBP)context.createBusinessProcess("ConsSospesiSpeseBP");
			
			consultazioneBP.initVariabili(context, bp.getPathConsultazione(), livelloDestinazione);
			if ((bp.getElementsCount()!=selectElements)||!(bp.getBaseclause().toString().equals(consultazioneBP.getBaseclause().toString()))||bp.getFindclause()!=null)
				consultazioneBP.addToBaseclause(bp.getSelezione(context));
			
			if (consultazioneBP instanceof ConsSospesiEntrateBP)
				consultazioneBP.setIterator(context,consultazioneBP.createConsSospesiEntSpeComponentSession().findConsSospesiEntrata(context.getUserContext(),bp.getPathDestinazione(livelloDestinazione), livelloDestinazione, consultazioneBP.getBaseclause(), null));
			else 
				consultazioneBP.setIterator(context,consultazioneBP.createConsSospesiEntSpeComponentSession().findConsSospesiSpesa(context.getUserContext(),bp.getPathDestinazione(livelloDestinazione), livelloDestinazione, consultazioneBP.getBaseclause(), null));			
				
			return context.addBusinessProcess(consultazioneBP);
		} catch(Throwable e) {
			return handleException(context,e);
		}
	}
	
	
	public Forward doDettaglioSospesi(ActionContext context) {
		ConsSospesiBP bp = (ConsSospesiBP)context.getBusinessProcess();
		bp.setSelection(context);
		String livDest=null;
		if(bp instanceof ConsSospesiSpeseBP)
			livDest = ConsSospesiBP.LIV_SOSMANMDETT;
		if(bp instanceof ConsSospesiEntrateBP)
			livDest = ConsSospesiBP.LIV_SOSREVRDETT;
		return doConsulta(context, livDest);
	}
	
	
	public Forward doConsultaMandati(ActionContext context) {
		return doConsulta(context, ConsSospesiBP.LIV_SOSMANMDETTMAN);
	}
	
	public Forward doConsultaReversali(ActionContext context) {
		return doConsulta(context, ConsSospesiBP.LIV_SOSREVRDETTREV);
	}
	public Forward doCloseForm(ActionContext context) throws BusinessProcessException {
		Forward appoForward = super.doCloseForm(context);
		if (context.getBusinessProcess() instanceof ConsSospesiBP) {
			ConsSospesiBP bp = (ConsSospesiBP)context.getBusinessProcess();
			bp.setTitle();
		}
		return appoForward;
	}
}
