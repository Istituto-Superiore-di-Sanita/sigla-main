package it.cnr.contab.config00.sto.bulk;

import it.cnr.contab.prevent01.bulk.Pdg_moduloBulk;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;
import it.cnr.jada.util.*;

public class CdrBulk extends CdrBase {
	private Unita_organizzativaBulk unita_padre = new Unita_organizzativaBulk();
	private it.cnr.contab.anagraf00.core.bulk.TerzoBulk responsabile = new it.cnr.contab.anagraf00.core.bulk.TerzoBulk();	
	private BulkList dettagli = new BulkList();

public CdrBulk() {}
public CdrBulk(java.lang.String cd_centro_responsabilita) {
	super(cd_centro_responsabilita);
	this.setCd_centro_responsabilita(cd_centro_responsabilita); 
}
/**
 * @return it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk
 */
public String getCd_ds_cdr() {
	return (getCd_centro_responsabilita() + " - " + (getDs_cdr() == null ? "" : getDs_cdr()));
}
public java.lang.Integer getCd_responsabile() {
	it.cnr.contab.anagraf00.core.bulk.TerzoBulk responsabile = this.getResponsabile();
	if (responsabile == null)
		return null;
	return responsabile.getCd_terzo();
}
public java.lang.String getCd_unita_organizzativa() {
	it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk unita_padre = this.getUnita_padre();
	if (unita_padre == null)
		return null;
	return unita_padre.getCd_unita_organizzativa();
}
public java.lang.String getCd_cds() {
	it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk unita_padre = this.getUnita_padre();
	if (unita_padre == null)
		return null;
	return unita_padre.getCd_cds();
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'ds_responsabile'
 *
 * @return Il valore della propriet� 'ds_responsabile'
 */
public java.lang.String getDs_responsabile() {
	if ( responsabile != null && responsabile.getAnagrafico() != null )
		return responsabile.getAnagrafico().getCognome() + " " + responsabile.getAnagrafico().getNome();
	return "";	

}
/**
 * @return it.cnr.contab.anagraf00.core.bulk.TerzoBulk
 */
public it.cnr.contab.anagraf00.core.bulk.TerzoBulk getResponsabile() {
	return responsabile;
}
/**
 * @return it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk
 */
public Unita_organizzativaBulk getUnita_padre() {
	return unita_padre;
}
/**
 * Metodo per la gestione del campo <code>esercizio</code>.
 */
public OggettoBulk initializeForInsert(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
	setEsercizio_inizio(it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context));
	setEsercizio_fine( it.cnr.contab.config00.esercizio.bulk.EsercizioBulk.ESERCIZIO_FINE);
	setResponsabile(new it.cnr.contab.anagraf00.core.bulk.V_terzo_persona_fisicaBulk());
	return this;
}
/**
 * Metodo per la gestione del campo <code>esercizio</code>.
 */
public OggettoBulk initializeForSearch(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
	// setEsercizio_inizio(it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context));
	setResponsabile(new it.cnr.contab.anagraf00.core.bulk.V_terzo_persona_fisicaBulk());
	return this;
}
/**
 * Verifica se il ricevente � il CDR dell'Amministrazione Centrale 
 * (se livello = 1 e l'unit� organizzativa � di tipo ENTE)
 */
public boolean isCdrAC() {
	return
	    getLivello() != null &&
		getLivello().intValue() == 1 &&
		getUnita_padre() != null &&
		Tipo_unita_organizzativaHome.TIPO_UO_ENTE.equalsIgnoreCase(getUnita_padre().getCd_tipo_unita());
}
/**
 * Verifica se il ricevente � un CDR SAC (cdr di primo livello 
 * appartenente ad un'unit� organizzativa di tipo SAC)
 */
public boolean isCdrSAC() {
	return
		Integer.parseInt(getCd_proprio_cdr()) == 0 &&
		Tipo_unita_organizzativaHome.TIPO_UO_SAC.equals(getUnita_padre().getCd_tipo_unita());
}
/**
 * Determina quando abilitare o meno nell'interfaccia utente il campo responsabile
 * @return boolean true quando il campo deve essere disabilitato
 */

public boolean isROResponsabile() {
	return responsabile == null || responsabile.getCrudStatus() == NORMAL;
}
/**
 * Determina quando abilitare o meno nell'interfaccia utente il campo unita organizzativa
 * @return boolean true quando il campo deve essere disabilitato
 */

public boolean isROUnita_padre() {
	return unita_padre == null || unita_padre.getCrudStatus() == NORMAL;
}
public void setCd_responsabile(java.lang.Integer cd_responsabile) {
	this.getResponsabile().setCd_terzo(cd_responsabile);
}
public void setCd_unita_organizzativa(java.lang.String cd_unita_organizzativa) {
	this.getUnita_padre().setCd_unita_organizzativa(cd_unita_organizzativa);
}
/**
 * @param newResponsabile it.cnr.contab.anagraf00.core.bulk.TerzoBulk
 */
public void setResponsabile(it.cnr.contab.anagraf00.core.bulk.TerzoBulk newResponsabile) {
	responsabile = newResponsabile;
}
/**
 * @param newUnita_padre it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk
 */
public void setUnita_padre(Unita_organizzativaBulk newUnita_padre) {
	unita_padre = newUnita_padre;
}
/**
 * Esegue la validazione formale dei campi di input
 */

public void validate() throws ValidationException 
{
	if ( getEsercizio_fine() == null )
	{
		setEsercizio_fine( it.cnr.contab.config00.esercizio.bulk.EsercizioBulk.ESERCIZIO_FINE);
		throw new ValidationException( "Il campo Esercizio di Terminazione deve essere valorizzato. " );
	}

	if ( getEsercizio_fine().compareTo( getEsercizio_inizio()) < 0 )
		throw new ValidationException( "L' esercizio di terminazione non pu� essere minore dell'esercizio di creazione. " );
		
	if ( getEsercizio_fine().toString().length() != 4 )
		throw new ValidationException( "Il campo Esercizio di terminazione deve essere di quattro cifre. " );


	if ( getUnita_padre() == null || getUnita_padre().getCd_unita_organizzativa() == null || getUnita_padre().getCd_unita_organizzativa().equals(""))
		throw new ValidationException( "Il campo CODICE UO � obbligatorio. " );
	if ( responsabile.getCd_terzo() == null )
		throw new ValidationException( "Il campo RESPONSABILE � obbligatorio. " );			
	if ( !isNullOrEmpty( getCd_proprio_cdr() ) )
	{
		long cdLong;
		try
		{
			cdLong = Long.parseLong( getCd_proprio_cdr() );
		}
		catch (java.lang.NumberFormatException e)
		{
			throw new ValidationException( "Il campo CODICE deve essere numerico. " );			
		}
		if ( cdLong < 0 )
			throw new ValidationException( "Il campo CODICE deve essere maggiore di 0. " );					
	}	
}

	public it.cnr.jada.bulk.BulkList getDettagli() {
		return dettagli;
	}
	public void setDettagli(it.cnr.jada.bulk.BulkList newDettagli) {
		dettagli = newDettagli;
	}
	public int addToDettagli(OggettoBulk dett) {
		if (dett instanceof Pdg_moduloBulk) 
			((Pdg_moduloBulk)dett).setCdr(this);
		dettagli.add(dett);
		return dettagli.size()-1;
	}

	public OggettoBulk removeFromDettagli(int index) {
		OggettoBulk dett = (OggettoBulk)dettagli.remove(index);
		return dett;
	}
	public it.cnr.jada.bulk.BulkCollection[] getBulkLists() {
		return new it.cnr.jada.bulk.BulkCollection[] {dettagli};
	}
	
	/**
	 * Verifica se il ricevente � il CDR dell'Amministrazione Centrale 
	 * (se livello = 1 e l'unit� organizzativa � di tipo ENTE)
	 */
	public boolean isCdrILiv() {
		return
		    getLivello() != null &&
			getLivello().intValue() == 1 &&
			getUnita_padre() != null &&
			!Tipo_unita_organizzativaHome.TIPO_UO_ENTE.equalsIgnoreCase(getUnita_padre().getCd_tipo_unita());
	}
}
