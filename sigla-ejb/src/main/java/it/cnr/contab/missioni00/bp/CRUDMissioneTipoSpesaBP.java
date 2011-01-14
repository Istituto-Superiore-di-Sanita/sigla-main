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
public class CRUDMissioneTipoSpesaBP extends it.cnr.jada.util.action.SimpleCRUDBP {
/**
 * CRUDMissioneTabRifBP constructor comment.
 */
public CRUDMissioneTipoSpesaBP() {
	super();
}
/**
 * CRUDMissioneTabRifBP constructor comment.
 * @param function java.lang.String
 */
public CRUDMissioneTipoSpesaBP(String function) {
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
public void basicEdit(ActionContext context,OggettoBulk bulk, boolean doInitializeForEdit) throws it.cnr.jada.action.BusinessProcessException {

	super.basicEdit(context, bulk, doInitializeForEdit);

	if (!isViewing()){
		Missione_tipo_spesaBulk aSpesa = (Missione_tipo_spesaBulk)getModel();
		if(aSpesa.getDt_cancellazione() != null){
			setStatus(VIEW);
 		    setMessage("Codice spesa ' " + aSpesa.getCd_ti_spesa() + " ' cancellato!");
		}else if(aSpesa.getDataFineValidita()!=null && aSpesa.getDataFineValidita().compareTo(CompensoBulk.getDataOdierna())<=0){
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

		Missione_tipo_spesaBulk obj = (Missione_tipo_spesaBulk)getModel();

		MissioneTipoSpesaComponentSession session = (MissioneTipoSpesaComponentSession)createComponentSession();
		obj = session.gestioneNazione(context.getUserContext(), obj);
		if (obj.getNazione()==null){
			obj.setTi_area_geografica(null);
			setMessage("Non esiste la nazione associata all'area geografica selezionata");
		}
		setModel(context, obj);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
}
