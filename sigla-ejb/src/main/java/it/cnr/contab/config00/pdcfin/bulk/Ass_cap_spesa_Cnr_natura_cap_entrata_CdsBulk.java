package it.cnr.contab.config00.pdcfin.bulk;

import it.cnr.contab.config00.sto.bulk.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;
import it.cnr.jada.util.*;

/**
 * Questa classe eredita le caratteristiche della classe <code>Ass_ev_evBulk</code>,
 * che contiene le variabili e i metodi comuni a tutte le sue sottoclassi.
 * In particolare, si tratta di un'associazione tra capitolo di spesa del Cnr e natura
 * con capitolo di entrata del Cds.
 */
public class Ass_cap_spesa_Cnr_natura_cap_entrata_CdsBulk extends Ass_ev_evBulk {

	protected Tipo_unita_organizzativaBulk tipo_unita = new Tipo_unita_organizzativaBulk();
public Ass_cap_spesa_Cnr_natura_cap_entrata_CdsBulk() {
	super();

	setTi_appartenenza(Elemento_voceHome.APPARTENENZA_CNR);
	setTi_gestione(Elemento_voceHome.GESTIONE_SPESE);
	// elemento_voce.setTi_appartenenza(Elemento_voceHome.APPARTENENZA_CNR);
	// elemento_voce.setTi_gestione(Elemento_voceHome.GESTIONE_SPESE);
	
	setTi_appartenenza_coll(Elemento_voceHome.APPARTENENZA_CDS);
	setTi_gestione_coll(Elemento_voceHome.GESTIONE_ENTRATE);
	// elemento_voce_coll.setTi_appartenenza(Elemento_voceHome.APPARTENENZA_CDS);
	// elemento_voce_coll.setTi_gestione(Elemento_voceHome.GESTIONE_ENTRATE);
}
public Ass_cap_spesa_Cnr_natura_cap_entrata_CdsBulk(java.lang.String cd_cds,java.lang.String cd_elemento_voce,java.lang.String cd_elemento_voce_coll,java.lang.String cd_natura,java.lang.Integer esercizio,java.lang.String ti_appartenenza,java.lang.String ti_appartenenza_coll,java.lang.String ti_gestione,java.lang.String ti_gestione_coll) {
	super(cd_cds,cd_elemento_voce,cd_elemento_voce_coll,cd_natura,esercizio,ti_appartenenza,ti_appartenenza_coll,ti_gestione,ti_gestione_coll);
	setTipo_unita(new Tipo_unita_organizzativaBulk(cd_cds));
}
public java.lang.String getCd_cds() {
	Tipo_unita_organizzativaBulk tipo_unita = this.getTipo_unita();
	if (tipo_unita == null)
		return null;
	return tipo_unita.getCd_tipo_unita();
}
public java.lang.String getCd_elemento_voce() {
	it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk elemento_voce = this.getElemento_voce();
	if (elemento_voce == null)
		return null;
	return elemento_voce.getCd_elemento_voce();
}
public java.lang.String getCd_elemento_voce_coll() {
	it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk elemento_voce_coll = this.getElemento_voce_coll();
	if (elemento_voce_coll == null)
		return null;
	return elemento_voce_coll.getCd_elemento_voce();
}
public java.lang.String getTi_appartenenza() {
	it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk elemento_voce = this.getElemento_voce();
	if (elemento_voce == null)
		return null;
	return elemento_voce.getTi_appartenenza();
}
public java.lang.String getTi_appartenenza_coll() {
	it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk elemento_voce_coll = this.getElemento_voce_coll();
	if (elemento_voce_coll == null)
		return null;
	return elemento_voce_coll.getTi_appartenenza();
}
public java.lang.String getTi_elemento_voce() {
	it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk elemento_voce = this.getElemento_voce();
	if (elemento_voce == null)
		return null;
	return elemento_voce.getTi_elemento_voce();
}
public java.lang.String getTi_elemento_voce_coll() {
	it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk elemento_voce_coll = this.getElemento_voce_coll();
	if (elemento_voce_coll == null)
		return null;
	return elemento_voce_coll.getTi_elemento_voce();
}
public java.lang.String getTi_gestione() {
	it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk elemento_voce = this.getElemento_voce();
	if (elemento_voce == null)
		return null;
	return elemento_voce.getTi_gestione();
}
public java.lang.String getTi_gestione_coll() {
	it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk elemento_voce_coll = this.getElemento_voce_coll();
	if (elemento_voce_coll == null)
		return null;
	return elemento_voce_coll.getTi_gestione();
}
/**
 * Metodo con cui si ottiene un'istanza di tipo <code>Tipo_unita_organizzativaBulk</code>.
 * @return cds L'istanza di Tipo_unita_organizzativaBulk.
 */
public Tipo_unita_organizzativaBulk getTipo_unita() {
	return tipo_unita;
}
/**
 * Determina quando abilitare o meno nell'interfaccia utente il campo <code>cds</code>.
 * @return boolean true quando il campo deve essere disabilitato.
 */
public boolean isROTipo_unita() {
	return tipo_unita == null || tipo_unita.getCrudStatus() == NORMAL;
}
public void setCd_cds(java.lang.String cd_cds) {
	this.getTipo_unita().setCd_tipo_unita(cd_cds);
}
public void setCd_elemento_voce(java.lang.String cd_elemento_voce) {
	this.getElemento_voce().setCd_elemento_voce(cd_elemento_voce);
}
public void setCd_elemento_voce_coll(java.lang.String cd_elemento_voce_coll) {
	this.getElemento_voce_coll().setCd_elemento_voce(cd_elemento_voce_coll);
}
public void setTi_appartenenza(java.lang.String ti_appartenenza) {
	this.getElemento_voce().setTi_appartenenza(ti_appartenenza);
}
public void setTi_appartenenza_coll(java.lang.String ti_appartenenza_coll) {
	this.getElemento_voce_coll().setTi_appartenenza(ti_appartenenza_coll);
}
public void setTi_elemento_voce(java.lang.String ti_elemento_voce) {
	this.getElemento_voce().setTi_elemento_voce(ti_elemento_voce);
}
public void setTi_elemento_voce_coll(java.lang.String ti_elemento_voce_coll) {
	this.getElemento_voce_coll().setTi_elemento_voce(ti_elemento_voce_coll);
}
public void setTi_gestione(java.lang.String ti_gestione) {
	this.getElemento_voce().setTi_gestione(ti_gestione);
}
public void setTi_gestione_coll(java.lang.String ti_gestione_coll) {
	this.getElemento_voce_coll().setTi_gestione(ti_gestione_coll);
}
/**
 * Metodo con cui si definisce il valore dell'attributo <code>tipo_unita</code>
 * di tipo <code>Tipo_unita_organizzativaBulk</code>.
 * @param newTipo_unita 
 */
public void setTipo_unita(Tipo_unita_organizzativaBulk newTipo_unita) {
	tipo_unita = newTipo_unita;
}
/**
 * Metodo con cui si verifica la validità di alcuni campi, mediante un 
 * controllo sintattico o contestuale.
 */
public void validate() throws ValidationException 
{
	super.validate();

	if ( elemento_voce == null || elemento_voce.getCd_elemento_voce() == null || elemento_voce.getCd_elemento_voce().equals("") )
		throw new ValidationException( "Il codice del CAPITOLO SPESA CNR è obbligatorio." );
	if ( getCd_natura() == null  )
		throw new ValidationException( "Il campo NATURA è obbligatorio." );
	if ( elemento_voce_coll == null || elemento_voce_coll.getCd_elemento_voce() == null || elemento_voce_coll.getCd_elemento_voce().equals(""))
		throw new ValidationException( "Il codice del CAPITOLO ENTRATA CDS è obbligatorio." );
}
}
