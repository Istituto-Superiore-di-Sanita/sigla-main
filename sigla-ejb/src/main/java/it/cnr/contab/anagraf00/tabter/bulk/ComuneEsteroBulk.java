package it.cnr.contab.anagraf00.tabter.bulk;

import it.cnr.jada.action.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.util.action.*;
/**
 * Gestione dei dati relativi ai comuni esteri nella tabella Comune
 */

public class ComuneEsteroBulk extends ComuneBulk {

public ComuneEsteroBulk() {
	super();
}
public ComuneEsteroBulk(java.lang.Long pg_comune) {
	super(pg_comune);
}
public OggettoBulk initializeForInsert(CRUDBP bp, ActionContext context) {

	super.initializeForInsert(bp, context);
	setTi_italiano_estero(COMUNE_ESTERO);
	setCd_catastale(CODICE_CATASTALE_ESTERO);
	return this;
}
public boolean isEstero(){
	return true;
}
/**
 * Oltre alla normale validate da un avviso di errore se la nazione � nulla.
 *
 * @exeption it.cnr.jada.bulk.ValidationException
*/
public void validate() throws ValidationException {

	super.validate();
	if (getDs_comune()==null)
		throw new ValidationException("Il campo DESCRIZIONE non pu� essere vuoto");
	if(getPg_nazione() == null)
		throw new ValidationException("Il campo NAZIONE non pu� essere vuoto");
}
}
