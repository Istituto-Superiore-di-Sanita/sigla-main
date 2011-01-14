package it.cnr.contab.anagraf00.tabter.bulk;

import it.cnr.jada.action.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;
import it.cnr.jada.util.action.*;

/**
 * Gestione dei dati relativi alla tabella Tipo_rapporto
 */

public class CapBulk extends CapBase {

	private ComuneBulk comune;
/**
 * 
 */
public CapBulk() {}
public CapBulk(java.lang.String cd_cap,java.lang.Long pg_comune) {
	super(cd_cap,pg_comune);
}
/**
 * Insert the method's description here.
 * Creation date: (30/10/2002 11.52.30)
 * @return it.cnr.contab.anagraf00.tabter.bulk.ComuneBulk
 */
public ComuneBulk getComune() {
	return comune;
}
public java.lang.Long getPg_comune() {
	it.cnr.contab.anagraf00.tabter.bulk.ComuneBulk comune = this.getComune();
	if (comune == null)
		return null;
	return comune.getPg_comune();
}
public OggettoBulk initialize(CRUDBP bp, ActionContext context) {

	super.initialize(bp, context);
	setComune(new ComuneBulk());
	return this;
}
/**
 * Insert the method's description here.
 * Creation date: (30/10/2002 11.52.30)
 * @return it.cnr.contab.anagraf00.tabter.bulk.ComuneBulk
 */
public boolean isROComune() {
	return getComune()==null || getComune().getCrudStatus()==NORMAL;
}
/**
 * Insert the method's description here.
 * Creation date: (30/10/2002 11.52.30)
 * @param newComune it.cnr.contab.anagraf00.tabter.bulk.ComuneBulk
 */
public void setComune(ComuneBulk newComune) {
	comune = newComune;
}
public void setPg_comune(java.lang.Long pg_comune) {
	this.getComune().setPg_comune(pg_comune);
}
/**
 * Oltre alla normale validate da un avviso di errore se la nazione � nulla.
 *
 * @exeption it.cnr.jada.bulk.ValidationException
*/
public void validate() throws ValidationException {

	super.validate();
	if (getPg_comune()==null)
		throw new ValidationException("Il campo COMUNE non pu� essere vuoto");
	if (getCd_cap()==null)
		throw new ValidationException("Il campo CAP non pu� essere vuoto");
}
}
