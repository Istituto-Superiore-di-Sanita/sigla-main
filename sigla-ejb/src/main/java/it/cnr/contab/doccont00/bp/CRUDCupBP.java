/*
 * Created on Feb 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.cnr.contab.doccont00.bp;


import it.cnr.contab.doccont00.ejb.CupComponentSession;
import it.cnr.contab.doccont00.tabrif.bulk.CupBulk;
import it.cnr.contab.reports.bp.OfflineReportPrintBP;
import it.cnr.contab.reports.bulk.Print_spooler_paramBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.util.action.AbstractPrintBP;
import it.cnr.jada.util.action.SimpleCRUDBP;
	
/**
 * @author rpucciarelli
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CRUDCupBP extends SimpleCRUDBP {
	private String cds;
	public String getCds() {
		return cds;
	}

	public void setCds(String cds) {
		this.cds = cds;
	}

	public CRUDCupBP() {
		super();
	}
	
	public CRUDCupBP(String function)  throws BusinessProcessException{
		super(function);
	}
		
	public OggettoBulk createNewBulk(ActionContext context) throws BusinessProcessException {
		CupBulk cup = (CupBulk)super.createNewBulk(context);
		return cup;
	}
	@Override
	protected void initialize(ActionContext context)
			throws BusinessProcessException {
		try {
			setCds(((CupComponentSession) createComponentSession()).recuperoCds(context.getUserContext()));
		} catch (Exception e) {
			throw handleException(e);
		}
		super.initialize(context);
	}
	public void validainserimento(ActionContext context, CupBulk bulk) throws ValidationException{
		if ( bulk.getCdCup()==null) 
			throw new ValidationException("E' necessario inserire il Codice");
		if ( bulk.getCdCup().length()!=15) 
			throw new ValidationException("La lunghezza del Codice non � valida");
		if ( bulk.getDescrizione()==null) 
			throw new ValidationException("E' necessario inserire la Descrizione");
		
	}
	public boolean isPrintButtonHidden() 
	{
		return super.isPrintButtonHidden() || isInserting() || isSearching();
	}
	protected void initializePrintBP(AbstractPrintBP bp) 
	{
		OfflineReportPrintBP printbp = (OfflineReportPrintBP) bp;
		CupBulk cup = (CupBulk)getModel();
		
		printbp.setReportName("/doccont/doccont/cup_mandato_gae.jasper");
		Print_spooler_paramBulk param = new Print_spooler_paramBulk();
		param.setNomeParam("cup");
		param.setValoreParam(cup.getCdCup());
		param.setParamType("java.lang.String");
		printbp.addToPrintSpoolerParam(param);
		param = new Print_spooler_paramBulk();
		param.setNomeParam("cds");
		param.setValoreParam(this.getCds());
		param.setParamType("java.lang.String");
		printbp.addToPrintSpoolerParam(param);
			
	}
}