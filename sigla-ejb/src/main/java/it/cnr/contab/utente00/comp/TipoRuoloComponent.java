package it.cnr.contab.utente00.comp;

import java.util.*;

import it.cnr.contab.utenze00.bulk.*;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.*;

import java.io.Serializable;


/**
 * Classe che ridefinisce alcune operazioni di CRUD su RuoloBulk
 */

public class TipoRuoloComponent extends it.cnr.jada.comp.CRUDComponent implements ICRUDMgr, it.cnr.contab.utenze00.comp.IRuoloMgr, Cloneable,Serializable

{


public  TipoRuoloComponent()
{

}

/**
 * Esegue l'inizializzazione di una nuova istanza di Tipo_ruoloBulk impostando l'elenco di privilegi disponibili per
 * l'utente corrente
 *
 * Pre-post-conditions:
 *
 * Nome: Inizializzazione bulk 
 * Pre:  L'inizializzazione di un Tipo_ruoloBulk per eventuale inserimento e' stata generata
 * Post: Il Tipo_ruoloBulk viene aggiornato con l'elenco delle istanze di PrivilegioBulk disponibili per l'utente corrente
 *
 * Nome: Gestore non trovato
 * Pre:  L'utente che ha generato la richiesta non esiste
 * Post: Viene generata una ComponentException con detail l'ApplicationException con il messaggio 
 *       da visualizzare all'utente
 *
 * @param userContext lo userContext che ha generato la richiesta
 * @param bulk l'istanza di Tipo_ruoloBulk che deve essere inizializzata
 * @return il Tipo_ruoloBulk inizializzato 
 */



public OggettoBulk inizializzaBulkPerInserimento(UserContext userContext,OggettoBulk bulk) throws it.cnr.jada.comp.ComponentException
{
	try
	{
		Tipo_ruoloBulk tipo_ruolo = (Tipo_ruoloBulk) bulk;
		Tipo_ruoloHome tipo_ruoloHome = (Tipo_ruoloHome) getHomeCache(userContext).getHome(Tipo_ruoloBulk.class);

		UtenteBulk gestore = (UtenteBulk) getHomeCache(userContext).getHome( UtenteBulk.class ).findByPrimaryKey( new UtenteKey( bulk.getUser()));
		if ( gestore == null )
			throw  new it.cnr.jada.comp.ApplicationException( "Utente Gestore non definito" );
		tipo_ruolo.setGestore( gestore );		

		// carica privilegi disponibili
		tipo_ruolo.setPrivilegi_disponibili(tipo_ruoloHome.findPrivilegi_disponibili( gestore ));

		return tipo_ruolo;
			
	}
	catch (Exception e )
	{
		throw handleException( e );
	}
}
/**
 * Esegue l'inizializzazione di una nuova istanza di Tipo_ruoloBulk impostando l'elenco di Privilegi gi� assegnati 
 * e l'elenco di Privilegi ancora disponibili
 *
 * Pre-post-conditions:
 *
 * Nome: Inizializzazione bulk 
 * Pre:  L'inizializzazione di un Tipo_ruoloBulk per eventuale modifica e' stata generata
 * Post: Il Tipo_ruoloBulk viene aggiornato con l'elenco delle istanze di PrivilegioBulk ancora disponibili e con
 *		 l'elenco di istanze di Ass_tipo_ruolo_privilegioBulk gia' assegnate al tipo ruolo
 * 
 * Nome: Gestore non trovato
 * Pre:  L'utente che ha generato la richiesta non esiste
 * Post: Viene generata una ComponentException con detail l'ApplicationException con il messaggio 
 *       da visualizzare all'utente
 *
 * @param userContext lo userContext che ha generato la richiesta
 * @param bulk l'istanza di Tipo_ruoloBulk che deve essere inizializzata
 * @return il Tipo_ruoloBulk inizializzato
 */



public OggettoBulk inizializzaBulkPerModifica (UserContext userContext,OggettoBulk bulk) throws it.cnr.jada.comp.ComponentException
{
	try
	{
		Tipo_ruoloBulk tipo_ruolo = (Tipo_ruoloBulk) super.inizializzaBulkPerModifica(userContext, bulk );
		Tipo_ruoloHome tipo_ruoloHome = (Tipo_ruoloHome) getHomeCache(userContext).getHome(Tipo_ruoloBulk.class);
		
		UtenteBulk gestore = (UtenteBulk) getHomeCache(userContext).getHome( UtenteBulk.class ).findByPrimaryKey( new UtenteKey( bulk.getUser()));
		if ( gestore == null )
			throw  new it.cnr.jada.comp.ApplicationException( "Utente Gestore non definito" );
		tipo_ruolo.setGestore( gestore );		
    //		carica TIPO_RUOLO_PRIVILEGI
		Collection result = tipo_ruoloHome.findTipo_ruolo_privilegi(tipo_ruolo);
		for (java.util.Iterator i = result.iterator();i.hasNext();) 
		{
			Ass_tipo_ruolo_privilegioBulk ra = (Ass_tipo_ruolo_privilegioBulk)i.next();
			tipo_ruolo.addToTipo_ruolo_privilegi( ra );
		}

		// carica privilegi disponibili
		tipo_ruolo.setPrivilegi_disponibili(tipo_ruoloHome.findPrivilegi_disponibili( gestore ) );
		
		return tipo_ruolo;		
			
	}
	catch (Exception e )
	{
		throw handleException( e );
	}	

	
}
}
