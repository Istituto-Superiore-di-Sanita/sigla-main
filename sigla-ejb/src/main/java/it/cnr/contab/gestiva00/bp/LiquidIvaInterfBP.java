package it.cnr.contab.gestiva00.bp;

import it.cnr.contab.gestiva00.ejb.*;
import it.cnr.contab.gestiva00.core.bulk.*;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.ApplicationException;

public class LiquidIvaInterfBP extends it.cnr.jada.util.action.SimpleCRUDBP {
public LiquidIvaInterfBP() {
	super();
}

public LiquidIvaInterfBP(String function) {
	super(function);
}

/**
 * Inzializza il ricevente nello stato di SEARCH.
 */
	protected void init(it.cnr.jada.action.Config config,it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
		super.init(config,context);
		resetForSearch(context);
	}

	public void resetForSearch(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
		try {
			setModel(context,createEmptyModelForSearch(context));
			Liquid_iva_interfBulk liquid_iva = (Liquid_iva_interfBulk)getModel();
			liquid_iva.setEsercizio(CNRUserContext.getEsercizio(context.getUserContext()));
			liquid_iva.setCd_cds(CNRUserContext.getCd_cds(context.getUserContext()));
			setStatus(SEARCH);
			setDirty(false);
			//super.resetForSearch(context);
		} catch(Throwable e) {
			throw new it.cnr.jada.action.BusinessProcessException(e);
		}
	}
			
	protected void basicEdit(ActionContext actioncontext, OggettoBulk oggettobulk, boolean flag) throws BusinessProcessException{
		super.basicEdit(actioncontext,oggettobulk,flag);
		Liquid_iva_interfBulk liquid_iva = (Liquid_iva_interfBulk)getModel();
		//Per recuperare il mese dalla data
		java.util.Calendar cal = java.util.GregorianCalendar.getInstance();
		cal.setTime(new java.util.Date(liquid_iva.getDt_inizio().getTime()));
		liquid_iva.setMese((String)Liquid_iva_interfBulk.INT_MESI.get(new Integer(cal.get(java.util.Calendar.MONTH)+1)));
		//il metodo basicEdit scatta solo se la query restituisce i dati
		liquid_iva.setVisualizzaDati(true); 
		if (liquid_iva.getFl_gia_eleborata().booleanValue() == true)
			setStatus(VIEW);
	}
	
	public boolean controllaQuery(ActionContext context, Liquid_iva_interfBulk liquid_iva) throws it.cnr.jada.action.BusinessProcessException {
		try 
		{
			LiquidIvaInterfComponentSession sessione = (LiquidIvaInterfComponentSession) createComponentSession();
			//se contaRiga e' false chiamo un metodo del component che mi inserisce i dati
			return sessione.contaRiga(context.getUserContext(), liquid_iva);
			/**
			if (! sessione.contaRiga(context.getUserContext(), liquid_iva)){
				sessione.inserisciRighe(context.getUserContext(), liquid_iva);
				//setMessage("Per il mese in esame non esistono dati. Il sistema li inserir� in automatico.");				
			}**/
		} catch(Exception e) {
			throw handleException(e);
		}
	}
	public void inserisci(ActionContext context, Liquid_iva_interfBulk liquid_iva) throws it.cnr.jada.action.BusinessProcessException	{
		try 
		{
			LiquidIvaInterfComponentSession sessione = (LiquidIvaInterfComponentSession) createComponentSession();
				sessione.inserisciRighe(context.getUserContext(), liquid_iva);
		} catch(Exception e) {
				throw handleException(e);
		}
	}
	public boolean isDeleteButtonHidden()
	{
		return true;
	}
	public boolean isNewButtonHidden()
	{
		return true;
	}
	public boolean isFreeSearchButtonHidden()
	{
		return true;
	}	
}
