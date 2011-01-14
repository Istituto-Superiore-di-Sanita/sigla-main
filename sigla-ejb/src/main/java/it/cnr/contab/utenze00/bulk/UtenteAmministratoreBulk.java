package it.cnr.contab.utenze00.bulk;

/**
 * Definisce una Utente di tipo Amministratore di Utenti (tipo utente = AMMINISTRATORE)
 *	
 */

import it.cnr.contab.config00.sto.bulk.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.util.*;

public class UtenteAmministratoreBulk extends UtenteBulk 
{
	private CdsBulk cds = new CdsBulk();
public UtenteAmministratoreBulk() {
	super();
	inizializza();	
}
public UtenteAmministratoreBulk(String cd_utente) {
	super(cd_utente);
	inizializza();
}
/**
 * @return it.cnr.contab.config00.sto.bulk.CdsBulk
 */
public it.cnr.contab.config00.sto.bulk.CdsBulk getCds() {
	return cds;
}
/**
 * Inizializza gli attributi specifici dell'Utente Amministratore
 */

private void inizializza() 
{
	setTi_utente(UtenteHome.TIPO_AMMINISTRATORE);

}
/**
 * Esegue una inizializzazione degli attributi dell'UtenteAmministratoreBulk prima di eseguire 
 * l'inserimento di un record nel db
 */

public void insertingUsing(it.cnr.jada.persistency.Persister persister) 
{
	updatingUsing( persister );

}
/**
 * Determina quando abilitare o meno nell'interfaccia utente il campo cds 
 * @return boolean true se il campo deve essere disabilitato
 */

public boolean isROCds() {
	return cds == null || cds.getCrudStatus() == NORMAL;
}
/**
 * @param newCds it.cnr.contab.config00.sto.bulk.CdsBulk
 */
public void setCds(it.cnr.contab.config00.sto.bulk.CdsBulk newCds) {
	cds = newCds;
}
/**
 * Esegue una inizializzazione degli attributi dell'UtenteAmministratoreBulk prima di eseguire 
 * l'aggiornamento di un record nel db
 */

public void updatingUsing(it.cnr.jada.persistency.Persister persister) 
{
	if ( cds != null )
		setCd_cds_configuratore( cds.getCd_unita_organizzativa() );
	
}
/**
 * Esegue la validazione formale dei campi di input
 */

public void validate() throws ValidationException
{
	super.validate();
	
	if ( cds.getCd_unita_organizzativa() == null  )
		throw new ValidationException( "Il campo CODICE CDS AMMINISTRATO � obbligatorio." );

}
}
