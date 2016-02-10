package it.cnr.contab.anagraf00.core.bulk;

import iaik.asn1.BOOLEAN;
import it.cnr.contab.anagraf00.tabrif.bulk.Rif_modalita_pagamentoBulk;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;
import it.cnr.jada.util.action.CRUDBP;

/**
 * Gestione dei dati relativi alla tabella Dichiarazione_intento
 */

public class Dichiarazione_intentoBulk extends Dichiarazione_intentoBase {

	private AnagraficoBulk anagrafico;
public Dichiarazione_intentoBulk() { 
	super();
}
public Dichiarazione_intentoBulk(java.lang.Integer cd_anag,java.lang.Integer esercizio,java.lang.Integer progr) {
	super(cd_anag,esercizio,progr);
}
	/**
	 * Restituisce l'<code>AnagraficoBulk</code> a cui l'oggetto � correlato.
	 *
	 * @return it.cnr.contab.anagraf00.core.bulk.AnagraficoBulk
	 *
	 * @see setAnagrafico
	 */

	public AnagraficoBulk getAnagrafico() {
		return anagrafico;
	}
public java.lang.Integer getCd_anag() {
	it.cnr.contab.anagraf00.core.bulk.AnagraficoBulk anagrafico = this.getAnagrafico();
	if (anagrafico == null)
		return null;
	return anagrafico.getCd_anag();
}
	/**
	 * Imposta l'<code>AnagraficoBulk</code> a cui l'oggetto � correlato.
	 *
	 * @param newAnagrafico Anagrafica di riferimento.
	 *
	 * @see getAnagrafico
	 */

	public void setAnagrafico(AnagraficoBulk newAnagrafico) {
		anagrafico = newAnagrafico;
	}
public void setCd_anag(java.lang.Integer cd_anag) {
	this.getAnagrafico().setCd_anag(cd_anag);
}

public OggettoBulk initializeForInsert(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {

	setFl_acquisti(Boolean.FALSE);
	setFl_importazioni(Boolean.FALSE);
	setDt_fin_validita(null);
	setEsercizio(CNRUserContext.getEsercizio(context.getUserContext()));
	return super.initializeForInsert(bp,context);
}
public void validate(OggettoBulk parent) throws ValidationException {
	super.validate(parent);
	if (getFl_acquisti() && getFl_importazioni()) 
		throw new ValidationException("L'opzione acquisti e importazione non possono essere entrambi indicati.");
	 else  if(!getFl_acquisti() && !getFl_importazioni()) 
		throw new ValidationException("Scegliere un opzione tra acquisti e importazione.");
	if((getDt_inizio_val_dich()!=null && getDt_fine_val_dich()==null)||
		(getDt_inizio_val_dich()==null && getDt_fine_val_dich()!=null))
		throw new ValidationException("Indicare sia la data di inizio e fine validit�.");
	if(getDt_inizio_val_dich()!=null &&  getDt_fine_val_dich()!=null && getDt_inizio_val_dich().after(getDt_fine_val_dich()))
		throw new ValidationException("La data di inizio del periodo di riferimento deve essere antecedente alla data di fine.");
	if(getDt_inizio_val_dich()!=null &&  getDt_fine_val_dich()!=null &&
			(getIm_limite_op()!=null ||getIm_limite_sing_op()!=null))
		throw new ValidationException("La dichiarazione � valida o per periodo di riferimento o per importo.");
	if(getDt_inizio_val_dich()==null &&  getDt_fine_val_dich()==null &&
			((getIm_limite_op()!=null && getIm_limite_sing_op()!=null)))
		throw new ValidationException("Indicare solo uno degli importi limite.");
	if(getDt_inizio_val_dich()==null &&  getDt_fine_val_dich()==null &&
			((getIm_limite_op()==null && getIm_limite_sing_op()==null)))
		throw new ValidationException("Indicare il periodo di riferimento o uno degli importi limite.");
	if(getDt_ini_validita()!=null &&  getDt_fin_validita()!=null && getDt_ini_validita().after(getDt_fin_validita()))
		throw new ValidationException("La data di inizio validit� deve essere antecedente alla data di fine.");
	if(getId_dichiarazione()!=null && getId_dichiarazione().length()!=17)
		throw new ValidationException("Il codice telematico deve essere di 17 caratteri.");
}

public boolean isRODichiarazione() {
 return (!getAnagrafico().isUo_ente()); 
}
}
