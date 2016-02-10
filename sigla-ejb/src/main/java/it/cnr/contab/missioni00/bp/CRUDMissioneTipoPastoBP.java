package it.cnr.contab.missioni00.bp;

import it.cnr.contab.missioni00.tabrif.bulk.*;
import it.cnr.contab.anagraf00.tabter.bulk.*;
import it.cnr.contab.missioni00.ejb.*;
import it.cnr.contab.compensi00.docs.bulk.CompensoBulk;
import it.cnr.jada.action.*;
import it.cnr.jada.bulk.*;

/**
 * Insert the type's description here.
 * Creation date: (19/11/2001 15.02.10)
 * @author: Paola sala
 */
public class CRUDMissioneTipoPastoBP extends it.cnr.jada.util.action.SimpleCRUDBP {
/**
 * CRUDMissioneTabRifBP constructor comment.
 */
public CRUDMissioneTipoPastoBP() {
	super();
}
/**
 * CRUDMissioneTabRifBP constructor comment.
 * @param function java.lang.String
 */
public CRUDMissioneTipoPastoBP(String function) {
	super(function);
}
/**
 * Non devo poter modificare un record che abbia data fine validita
 * inferiore alla data odierna oppure un record con data cancellazione valorizzata
 *
 * Posso modificare tutti i record che hanno data fine validita
 * maggiore o uguale della data odierna e data cancellazione nulla
 *
*/
public void basicEdit(ActionContext context, OggettoBulk bulk, boolean doInitializeForEdit) throws it.cnr.jada.action.BusinessProcessException {

	super.basicEdit(context, bulk, doInitializeForEdit);
		
	if (!isViewing()){
		Missione_tipo_pastoBulk aPasto = (Missione_tipo_pastoBulk) getModel();
		if(aPasto.getDt_cancellazione() != null){
			setStatus(VIEW);
 		    setMessage("Codice pasto ' " + aPasto.getCd_ti_pasto() + " ' cancellato!");
		}else if(aPasto.getDataFineValidita()!=null && aPasto.getDataFineValidita().compareTo(CompensoBulk.getDataOdierna())<=0){
			setStatus(VIEW);		
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (12/02/2002 17.00.40)
 * @param context it.cnr.jada.action.ActionContext
 */
public void gestioneNazione(ActionContext context) throws BusinessProcessException{

	try{

		Missione_tipo_pastoBulk obj = (Missione_tipo_pastoBulk)getModel();

		MissioneTipoPastoComponentSession session = (MissioneTipoPastoComponentSession)createComponentSession();
		obj = session.gestioneNazione(context.getUserContext(), obj);
		if (obj.getNazione()==null){
			obj.setTi_area_geografica(null);
			setMessage("Non esiste la nazione associata all'area geografica selezionata");
		}
		
		if (obj.getNazione().getTi_nazione()!= null && obj.getNazione().getTi_nazione().compareTo(TipoAreaGeografica.INDIFFERENTE) != 0)
		{
			obj.setRifAreePaesiEsteri(new RifAreePaesiEsteriBulk(obj.getNazione().getCd_area_estera()));
			obj.setCd_area_estera(obj.getRifAreePaesiEsteri().getCd_area_estera());
		}
		else
		{	
			obj.setRifAreePaesiEsteri(new RifAreePaesiEsteriBulk(null));
		    obj.setCd_area_estera(obj.getRifAreePaesiEsteri().getCd_area_estera());
		}    
		setModel(context, obj);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}

}
