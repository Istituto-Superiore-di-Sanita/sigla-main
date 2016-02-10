/*
 * Created on Feb 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.cnr.contab.config00.bp;


import it.cnr.contab.config00.bulk.CigBulk;
import it.cnr.contab.util.ICancellatoLogicamente;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.util.action.SimpleCRUDBP;

/**
 * @author mspasiano
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CRUDCodiciCIGBP extends SimpleCRUDBP {
	private static final long serialVersionUID = -4170076026547194358L;

	public CRUDCodiciCIGBP() {
		super();
	}
	
	public CRUDCodiciCIGBP(String function)  throws BusinessProcessException{
		super(function);
	}

	public void basicEdit(it.cnr.jada.action.ActionContext context,it.cnr.jada.bulk.OggettoBulk bulk, boolean doInitializeForEdit) throws it.cnr.jada.action.BusinessProcessException {	
		super.basicEdit(context, bulk, doInitializeForEdit);
		if (getStatus()!=VIEW){
			ICancellatoLogicamente bulkCancellato= (ICancellatoLogicamente)getModel();
			if (bulkCancellato!=null && bulkCancellato.isCancellatoLogicamente()) {
				setStatus(VIEW);
			}			
		}
	}
	@Override
	public void validate(ActionContext actioncontext)
			throws ValidationException {
		CigBulk bulk=(CigBulk)this.getModel();
		if ( bulk.getCdCig()==null) 
			throw new ValidationException("E' necessario inserire il Codice");
		if ( bulk.getCdCig().length()!=10) 
			throw new ValidationException("La lunghezza del Codice non � valida");
	}
}