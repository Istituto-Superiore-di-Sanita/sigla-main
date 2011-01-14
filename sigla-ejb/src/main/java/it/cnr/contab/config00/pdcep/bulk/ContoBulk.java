package it.cnr.contab.config00.pdcep.bulk;

import java.util.*;
import it.cnr.contab.config00.pdcep.bulk.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.util.*;

/**
 * Classe che eredita le caratteristiche della classe <code>Voce_epBulk</code>,
 * che contiene le variabili e i metodi comuni a tutte le sue sottoclassi.
 * In particolare si riferisce ad un'entit� di tipo Conto.
 */
public class ContoBulk extends Voce_epBulk {
	private CapocontoBulk voce_ep_padre = new CapocontoBulk();
	private ContoBulk riapre_a_conto;
	static private java.util.Hashtable associazioni_natura_gruppo;
	private boolean fl_gruppoNaturaNonCongruiConfermati = false;
/**
 * Costruttore della classe <code>ContoBulk</code>.
 */
public ContoBulk() 
{
	setTi_voce_ep(Voce_epHome.TIPO_CONTO); 
}
public ContoBulk(java.lang.String cd_voce_ep,java.lang.Integer esercizio) {
	super(cd_voce_ep,esercizio);
}
/**
 * Metodo con cui si ottiene il valore della variabile <code>associazioni_natura_gruppo</code>
 * di tipo <code>Hashtable</code>.
 * In particolare, questo metodo carica in una Hashtable l'elenco dei possibili valori
 * che pu� assumere l'associazione delle nature con i gruppi.
 * @return java.util.Hashtable associazioni_natura_gruppo I valori dell'associazione delle
 * 														  nature con i gruppi.
 */
public java.util.Hashtable getAssociazioni_natura_gruppo() {
		Hashtable associazioni_natura_gruppo = new java.util.Hashtable();
		
		associazioni_natura_gruppo.put("NUA", "A");
		associazioni_natura_gruppo.put("EPC", "C");
		associazioni_natura_gruppo.put("EEC", "C");
		associazioni_natura_gruppo.put("CDC", "N");
		associazioni_natura_gruppo.put("CDO", "O");
		associazioni_natura_gruppo.put("NUP", "P");
		associazioni_natura_gruppo.put("EPR", "R");
		associazioni_natura_gruppo.put("EER", "R");
		
	return associazioni_natura_gruppo;
}
public java.lang.String getCd_voce_ep_padre() {
	it.cnr.contab.config00.pdcep.bulk.CapocontoBulk voce_ep_padre = this.getVoce_ep_padre();
	if (voce_ep_padre == null)
		return null;
	return voce_ep_padre.getCd_voce_ep();
}
/**
 * Metodo con cui si ottiene la descrizione del gruppo relativo al capoconto
 * selezionato.
 * @return String La descrizione del gruppo.
 */
public String getDs_gruppo()
{
//	if ( cd_voce_ep_padre != null && getGruppiKeys() != null )
//		return (String) getGruppiKeys().get( cd_voce_ep_padre.substring( 0, 1) );

	if ( voce_ep_padre != null  && voce_ep_padre.getCd_voce_ep() != null && !voce_ep_padre.getCd_voce_ep().equals(""))
		return voce_ep_padre.getCd_voce_ep().substring( 0, 1);
	return null;	
		
}
/**
 * Metodo con cui si ottiene il valore della variabile <code>riapre_a_conto</code>
 * di tipo <code>ContoBulk</code>.
 * @return it.cnr.contab.config00.pdcep.bulk.ContoBulk
 */
public ContoBulk getRiapre_a_conto() {
	return riapre_a_conto;
}
public java.lang.String getRiapre_a_conto_economico() {
	it.cnr.contab.config00.pdcep.bulk.ContoBulk riapre_a_conto = this.getRiapre_a_conto();
	if (riapre_a_conto == null)
		return null;
	return riapre_a_conto.getCd_voce_ep();
}
/**
 * Metodo con cui si ottiene il valore della variabile <code>voce_ep_padre</code>
 * di tipo <code>CapocontoBulk</code>.
 * @return it.cnr.contab.config00.pdcep.bulk.CapocontoBulk
 */
public CapocontoBulk getVoce_ep_padre() {
	return voce_ep_padre;
}
/**
 * Metodo per inizializzare l'oggetto bulk in fase di inserimento.
 * @param bp  Business Process <code>CRUDBP</code> in uso.
 * @param context  <code>ActionContext</code> in uso.
 * @return OggettoBulk this Oggetto bulk in uso.
 */
public OggettoBulk initializeForInsert(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
	setEsercizio(it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context));
	riapre_a_conto = new ContoBulk();
	setFl_a_pareggio(new Boolean (false));
	return this;
}
/**
 * Metodo per inizializzare l'oggetto bulk in fase di ricerca.
 * @param bp  Business Process <code>CRUDBP</code> in uso.
 * @param context  <code>ActionContext</code> in uso.
 * @return OggettoBulk this Oggetto bulk in uso.
 */
public OggettoBulk initializeForSearch(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
	setEsercizio(it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context));
	return this;
}
/**
 * Restituisce il valore della propriet� 'fl_gruppoNaturaNonCongruiConfermati'
 *
 * @return Il valore della propriet� 'fl_gruppoNaturaNonCongruiConfermati'
 */
public boolean isFl_gruppoNaturaNonCongruiConfermati() {
	return fl_gruppoNaturaNonCongruiConfermati;
}
/**
 * Determina quando abilitare o meno nell'interfaccia utente il campo <code>riapre_a_conto</code>.
 * @return boolean true quando il campo deve essere disabilitato.
 */
public boolean isRORiapre_a_conto() {
	return riapre_a_conto == null || riapre_a_conto.getCrudStatus() == NORMAL;
}
/**
 * Determina quando abilitare o meno nell'interfaccia utente il campo <code>voce_ep_padre</code>.
 * @return boolean true quando il campo deve essere disabilitato.
 */
public boolean isROVoce_ep_padre() {
	return voce_ep_padre == null || voce_ep_padre.getCrudStatus() == NORMAL;
}
public void setCd_voce_ep_padre(java.lang.String cd_voce_ep_padre) {
	this.getVoce_ep_padre().setCd_voce_ep(cd_voce_ep_padre);
}
/**
 * Imposta il valore della propriet� 'fl_gruppoNaturaNonCongruiConfermati'
 *
 * @param newFl_gruppoNaturaNonCongruiConfermati	Il valore da assegnare a 'fl_gruppoNaturaNonCongruiConfermati'
 */
public void setFl_gruppoNaturaNonCongruiConfermati(boolean newFl_gruppoNaturaNonCongruiConfermati) {
	fl_gruppoNaturaNonCongruiConfermati = newFl_gruppoNaturaNonCongruiConfermati;
}
/**
 * Metodo con cui si definisce il valore della variabile <code>riapre_a_conto</code>
 * di tipo <code>ContoBulk</code>.
 * @param newRiapre_a_conto it.cnr.contab.config00.pdcep.bulk.ContoBulk
 */
public void setRiapre_a_conto(ContoBulk newRiapre_a_conto) {
	riapre_a_conto = newRiapre_a_conto;
}
public void setRiapre_a_conto_economico(java.lang.String riapre_a_conto_economico) {
	this.getRiapre_a_conto().setCd_voce_ep(riapre_a_conto_economico);
}
/**
 * Metodo con cui si definisce il valore della variabile <code>voce_ep_padre</code>
 * di tipo <code>CapocontoBulk</code>.
 * @param newVoce_ep_padre it.cnr.contab.config00.pdcep.bulk.CapocontoBulk
 */
public void setVoce_ep_padre(CapocontoBulk newVoce_ep_padre) {
	voce_ep_padre = newVoce_ep_padre;
}
/**
 * Metodo con cui si verifica la validit� di alcuni campi, mediante un 
 * controllo sintattico o contestuale.
 */
public void validate() throws ValidationException 
{
	super.validate();

	// controllo su campo CODICE del CAPOCONTO
	if ( getVoce_ep_padre() == null || getVoce_ep_padre().getCd_voce_ep() == null || getVoce_ep_padre().getCd_voce_ep().equals("") )
		throw new ValidationException( "Il CODICE del CAPOCONTO deve essere inserito." );
			
//			if ( cd_voce_ep_padre != null )
	getVoce_ep_padre().setCd_voce_ep( getVoce_ep_padre().getCd_voce_ep().toUpperCase());
//				setCd_voce_ep_padre( getCd_voce_ep_padre().toUpperCase());

	// controllo su campo NATURA CONTO
	if ( getNatura_voce() == null || getNatura_voce().equals("") )
		throw new ValidationException( "Il campo NATURA CONTO deve essere selezionato." );

	// controllo su campo RIEPILOGA A
	if ( getRiepiloga_a() == null || getRiepiloga_a().equals("") )
		throw new ValidationException( "Il campo RIEPILOGA A deve essere selezionato." );

	// controllo su campo SEZIONE
	if ( getTi_sezione() == null || getTi_sezione().equals("") )
		throw new ValidationException( "Il campo SEZIONE deve essere selezionato." );
	
			
	// controllo su campo TIPO CONTO SPECIALE
	// Richiesta n.35 - tipo conto speciale facoltativo
	// if ( getConto_speciale() == null || getConto_speciale().equals("") )
	//	throw new ValidationException( "Il campo TIPO CONTO SPECIALE deve essere selezionato." );
	
	// controllo su campo CODICE PROPRIO
	if ( !isNullOrEmpty( getCd_proprio_voce_ep() ) )
	{
		long cdLong;
		try
		{
			cdLong = Long.parseLong( getCd_proprio_voce_ep() );
		}
		catch (java.lang.NumberFormatException e)
		{
			throw new ValidationException( "Il campo CODICE PROPRIO deve essere numerico. " );			
		}
		if ( cdLong < 0 )
			throw new ValidationException( "Il campo CODICE PROPRIO deve essere maggiore di 0. " );					
	}
	
	// controllo su campo DESCRIZIONE 
		if ( getDs_voce_ep() == null || getDs_voce_ep().equals("") )
			throw new ValidationException("Il campo NOME CONTO � obbligatorio.");

				
}
}
