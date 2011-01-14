package it.cnr.contab.config00.latt.bulk;

import java.util.List;
import java.util.Vector;

import it.cnr.contab.config00.pdcfin.bulk.*;
import it.cnr.contab.anagraf00.core.bulk.TerzoBulk;
import it.cnr.contab.anagraf00.tabrif.bulk.*;
import it.cnr.contab.progettiric00.core.bulk.*;
import it.cnr.contab.config00.blob.bulk.PostItBulk;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;


public class WorkpackageBulk extends WorkpackageBase implements CostantiTi_gestione {
	protected Gruppo_linea_attivitaBulk gruppo_linea_attivita;
	protected Tipo_linea_attivitaBulk tipo_linea_attivita;
	protected FunzioneBulk funzione;
	protected NaturaBulk natura;
	protected java.util.Collection nature;
	protected java.util.Collection funzioni;
	protected it.cnr.contab.config00.sto.bulk.CdrBulk centro_responsabilita;
	protected it.cnr.contab.progettiric00.core.bulk.ProgettoBulk progetto;
	protected TerzoBulk responsabile;

	private BulkList risultati;
	private BulkList dettagliPostIt = new BulkList();

	private final static java.util.Dictionary ti_gestioneKeys;
	
	static {
		ti_gestioneKeys = new it.cnr.jada.util.OrderedHashtable();
		ti_gestioneKeys.put(TI_GESTIONE_SPESE, "Spese");
		ti_gestioneKeys.put(TI_GESTIONE_ENTRATE, "Entrate");
	}	
	private Insieme_laBulk insieme_la;

	private java.util.Collection tipi_risultato;
	
public WorkpackageBulk() {
	super();
}
public WorkpackageBulk(java.lang.String cd_centro_responsabilita,java.lang.String cd_linea_attivita) {
	super(cd_centro_responsabilita,cd_linea_attivita);
	setCentro_responsabilita(new it.cnr.contab.config00.sto.bulk.CdrBulk(cd_centro_responsabilita));
}
/**
 * Aggiunge il nuovo risultato "risultato" alla lista dei risultati
 * @return it.cnr.jada.bulk.BulkList
 */
public int addToRisultati(RisultatoBulk risultato) {
	risultato.setLinea_attivita(this);
	risultati.add(risultato);
	return risultati.size()-1;
}

public int addToDettagliPostIt(it.cnr.contab.config00.blob.bulk.PostItBulk dett) {	
	dett.setCd_centro_responsabilita(getCd_centro_responsabilita());
	/*Nel caso di creazione del Wp da Zero la linea_attivita � null*/
	dett.setCd_linea_attivita(getCd_linea_attivita());
	dettagliPostIt.add(dett);
	return dettagliPostIt.size()-1;
}

/*
 * Ritorna true se il ricevente � valido nell'esercizio specificato
 */

public boolean checkValiditaInEsercizio(Integer aEsercizio) {
 return 
   (   getEsercizio_inizio().compareTo(aEsercizio) <= 0
	&& getEsercizio_fine().compareTo(aEsercizio) >= 0
   );
}
public BulkCollection[] getBulkLists() {
	return new BulkList[] { risultati,dettagliPostIt };
}
public java.lang.String getCd_centro_responsabilita() {
	it.cnr.contab.config00.sto.bulk.CdrBulk centro_responsabilita = this.getCentro_responsabilita();
	if (centro_responsabilita == null)
		return null;
	return centro_responsabilita.getCd_centro_responsabilita();
}
public java.lang.String getCd_funzione() {
	it.cnr.contab.config00.pdcfin.bulk.FunzioneBulk funzione = this.getFunzione();
	if (funzione == null)
		return null;
	return funzione.getCd_funzione();
}
public java.lang.String getCd_gruppo_linea_attivita() {
	it.cnr.contab.config00.latt.bulk.Gruppo_linea_attivitaBulk gruppo_linea_attivita = this.getGruppo_linea_attivita();
	if (gruppo_linea_attivita == null)
		return null;
	return gruppo_linea_attivita.getCd_gruppo_linea_attivita();
}
public java.lang.String getCd_insieme_la() {
	it.cnr.contab.config00.latt.bulk.Insieme_laBulk insieme_la = this.getInsieme_la();
	if (insieme_la == null)
		return null;
	return insieme_la.getCd_insieme_la();
}
public java.lang.String getCd_natura() {
	it.cnr.contab.config00.pdcfin.bulk.NaturaBulk natura = this.getNatura();
	if (natura == null)
		return null;
	return natura.getCd_natura();
}
public java.lang.String getCd_tipo_linea_attivita() {
	it.cnr.contab.config00.latt.bulk.Tipo_linea_attivitaBulk tipo_linea_attivita = this.getTipo_linea_attivita();
	if (tipo_linea_attivita == null)
		return null;
	return tipo_linea_attivita.getCd_tipo_linea_attivita();
}
/**
 * Restituisce il valore della propriet� 'centro_responsabilita'
 *
 * @return Il valore della propriet� 'centro_responsabilita'
 */
public it.cnr.contab.config00.sto.bulk.CdrBulk getCentro_responsabilita() {
	return centro_responsabilita;
}
/**
 * Restituisce il valore della propriet� 'funzione'
 *
 * @return Il valore della propriet� 'funzione'
 */
public FunzioneBulk getFunzione() {
	return funzione;
}
/**
 * Restituisce il valore della propriet� 'funzioni'
 *
 * @return Il valore della propriet� 'funzioni'
 */
public java.util.Collection getFunzioni() {
	return funzioni;
}
/**
 * Restituisce il valore della propriet� 'gruppo_linea_attivita'
 *
 * @return Il valore della propriet� 'gruppo_linea_attivita'
 */
public Gruppo_linea_attivitaBulk getGruppo_linea_attivita() {
	return gruppo_linea_attivita;
}
/**
 * Restituisce il valore della propriet� 'insieme_la'
 *
 * @return Il valore della propriet� 'insieme_la'
 */
public Insieme_laBulk getInsieme_la() {
	return insieme_la;
}
/**
 * Restituisce il valore della propriet� 'natura'
 *
 * @return Il valore della propriet� 'natura'
 */
public NaturaBulk getNatura() {
	return natura;
}
/**
 * Restituisce il valore della propriet� 'nature'
 *
 * @return Il valore della propriet� 'nature'
 */
public java.util.Collection getNature() {
	return nature;
}
/**
 * Ritorna la collezione dei risultati
 * @return it.cnr.jada.bulk.BulkList
 */
public it.cnr.jada.bulk.BulkList getRisultati() {
	return risultati;
}
/**
 * Restituisce il valore della propriet� 'ti_gestioneKeys'
 *
 * @return Il valore della propriet� 'ti_gestioneKeys'
 */
public java.util.Dictionary getTi_gestioneKeys() {
	return ti_gestioneKeys;
}
/**
 * Restituisce il valore della propriet� 'dettagliPostIt'
 *
 * @return Il valore della propriet� 'dettagliPostIt'
 */
public it.cnr.jada.bulk.BulkList getDettagliPostIt() {
	return dettagliPostIt;
}
/**
 * Rimuove il PostIt selezionato dai dettagliPostIt
 *
 * @return PostIt
 */
public PostItBulk removeFromDettagliPostIt(int index) {
	PostItBulk dett = (PostItBulk)dettagliPostIt.remove(index);
	return dett;
}
public void setDettagliPostIt(it.cnr.jada.bulk.BulkList newDettagliPostIt) {
	dettagliPostIt = newDettagliPostIt;
}
/**
 * Ritorna i tipi di risultati
 * @return java.util.Collection
 */
public java.util.Collection getTipi_risultato() {
	return tipi_risultato;
}
/**
 * Restituisce il valore della propriet� 'tipo_linea_attivita'
 *
 * @return Il valore della propriet� 'tipo_linea_attivita'
 */
public Tipo_linea_attivitaBulk getTipo_linea_attivita() {
	return tipo_linea_attivita;
}
public OggettoBulk initialize(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
	// esercizio = it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context);
	gruppo_linea_attivita = new Gruppo_linea_attivitaBulk();
	tipo_linea_attivita = new Tipo_linea_attivitaBulk();
	risultati = new BulkList();
	progetto = new ProgettoBulk();
	return this;
}
public OggettoBulk initializeForFreeSearch(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
	centro_responsabilita = new it.cnr.contab.config00.sto.bulk.CdrBulk();
	funzione = new FunzioneBulk();
	natura = new NaturaBulk();
	return super.initializeForFreeSearch(bp,context);
}
/**
 * Metodo per inizializzare l'oggetto bulk in fase di inserimento.
 * @param bp  Business Process <code>CRUDBP</code> in uso.
 * @param context  <code>ActionContext</code> in uso.
 * @return OggettoBulk this Oggetto bulk in uso.
 */
public OggettoBulk initializeForInsert(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
	setEsercizio_inizio(it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context));
	setEsercizio_fine( it.cnr.contab.config00.esercizio.bulk.EsercizioBulk.ESERCIZIO_FINE);
	setCentro_responsabilita(new it.cnr.contab.config00.sto.bulk.CdrBulk());
	setFl_limite_ass_obblig(Boolean.TRUE);
	return super.initializeForInsert(bp,context);
}
public OggettoBulk initializeForSearch(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
	centro_responsabilita = new it.cnr.contab.config00.sto.bulk.CdrBulk();
	return super.initializeForSearch(bp,context);
}
/**
 * Restituisce il valore della propriet� 'linea_attivita_sistema'
 *
 * @return Il valore della propriet� 'linea_attivita_sistema'
 */
public boolean isLinea_attivita_sistema() {
	return 
		getTipo_linea_attivita() != null &&
		getTipo_linea_attivita().getTi_tipo_la() != null &&
		getTipo_linea_attivita().getTi_tipo_la().equals(Tipo_linea_attivitaBulk.SISTEMA);
}
/**
 * Restituisce il valore della propriet� 'rOCentro_responsabilita'
 *
 * @return Il valore della propriet� 'rOCentro_responsabilita'
 */
public boolean isROCentro_responsabilita() {
	return false;
}
/**
 * Restituisce il valore della propriet� 'rODenominazione'
 *
 * @return Il valore della propriet� 'rODenominazione'
 */
public boolean isRODenominazione() {
	return tipo_linea_attivita == null || !Tipo_linea_attivitaBulk.PROPRIA.equals(tipo_linea_attivita.getTi_tipo_la());
}
/**
 * Restituisce il valore della propriet� 'rOprogetto'
 *
 * @return Il valore della propriet� 'rOprogetto'
 */
public boolean isROprogetto() {
	
	return getProgetto() == null ||
			getProgetto().getCrudStatus() == it.cnr.jada.bulk.OggettoBulk.NORMAL;
}
/**
 * Restituisce il valore della propriet� 'rODescrizione'
 *
 * @return Il valore della propriet� 'rODescrizione'
 */
public boolean isRODescrizione() {
	return tipo_linea_attivita == null || !Tipo_linea_attivitaBulk.PROPRIA.equals(tipo_linea_attivita.getTi_tipo_la());
}
/**
 * Restituisce il valore della propriet� 'rOEsercizio_fine'
 *
 * @return Il valore della propriet� 'rOEsercizio_fine'
 */
public boolean isROEsercizio_fine() {
	return tipo_linea_attivita == null || !Tipo_linea_attivitaBulk.PROPRIA.equals(tipo_linea_attivita.getTi_tipo_la());
}
/**
 * Restituisce il valore della propriet� 'rOFunzione'
 *
 * @return Il valore della propriet� 'rOFunzione'
 */
public boolean isROFunzione() {
// Modifica del 06/05/2002 -> concordato con Mingarelli il 3/5/2002
	return true;
//	return tipo_linea_attivita == null || !Tipo_linea_attivitaBulk.PROPRIA.equals(tipo_linea_attivita.getTi_tipo_la());
}
/**
 * Restituisce il valore della propriet� 'rONatura'
 *
 * @return Il valore della propriet� 'rONatura'
 */
public boolean isRONatura() {
// Modifica del 06/05/2002 -> concordato con Mingarelli il 3/5/2002
	return true;
//	return tipo_linea_attivita == null || !Tipo_linea_attivitaBulk.PROPRIA.equals(tipo_linea_attivita.getTi_tipo_la());
}
/**
 * Rimuove dalla lista dei risultati l'i-esimo risultato
 * @return it.cnr.jada.bulk.BulkList
 */
public RisultatoBulk removeFromRisultati(int index) {
	return (RisultatoBulk)risultati.remove(index);
}
public void setCd_centro_responsabilita(java.lang.String cd_centro_responsabilita) {
	this.getCentro_responsabilita().setCd_centro_responsabilita(cd_centro_responsabilita);
}
public void setCd_funzione(java.lang.String cd_funzione) {
	this.getFunzione().setCd_funzione(cd_funzione);
}
public void setCd_gruppo_linea_attivita(java.lang.String cd_gruppo_linea_attivita) {
	this.getGruppo_linea_attivita().setCd_gruppo_linea_attivita(cd_gruppo_linea_attivita);
}
public void setCd_insieme_la(java.lang.String cd_insieme_la) {
	this.getInsieme_la().setCd_insieme_la(cd_insieme_la);
}
public void setCd_natura(java.lang.String cd_natura) {
	this.getNatura().setCd_natura(cd_natura);
}
public void setCd_tipo_linea_attivita(java.lang.String cd_tipo_linea_attivita) {
	this.getTipo_linea_attivita().setCd_tipo_linea_attivita(cd_tipo_linea_attivita);
}
/**
 * Imposta il valore della propriet� 'centro_responsabilita'
 *
 * @param newCentro_responsabilita	Il valore da assegnare a 'centro_responsabilita'
 */
public void setCentro_responsabilita(it.cnr.contab.config00.sto.bulk.CdrBulk newCentro_responsabilita) {
	centro_responsabilita = newCentro_responsabilita;
}
/**
 * Imposta il valore della propriet� 'funzione'
 *
 * @param newFunzione	Il valore da assegnare a 'funzione'
 */
public void setFunzione(FunzioneBulk newFunzione) {
	funzione = newFunzione;
}
/**
 * Imposta il valore della propriet� 'funzioni'
 *
 * @param newFunzioni	Il valore da assegnare a 'funzioni'
 */
public void setFunzioni(java.util.Collection newFunzioni) {
	funzioni = newFunzioni;
}
/**
 * Imposta il valore della propriet� 'gruppo_linea_attivita'
 *
 * @param newGruppo_linea_attivita	Il valore da assegnare a 'gruppo_linea_attivita'
 */
public void setGruppo_linea_attivita(Gruppo_linea_attivitaBulk newGruppo_linea_attivita) {
	gruppo_linea_attivita = newGruppo_linea_attivita;
}
/**
 * Imposta il valore della propriet� 'insieme_la'
 *
 * @param newInsieme_la	Il valore da assegnare a 'insieme_la'
 */
public void setInsieme_la(Insieme_laBulk newInsieme_la) {
	insieme_la = newInsieme_la;
}
/**
 * Imposta il valore della propriet� 'natura'
 *
 * @param newNatura	Il valore da assegnare a 'natura'
 */
public void setNatura(NaturaBulk newNatura) {
	natura = newNatura;
}
/**
 * Imposta il valore della propriet� 'nature'
 *
 * @param newNature	Il valore da assegnare a 'nature'
 */
public void setNature(java.util.Collection newNature) {
	nature = newNature;
}
/**
 * Imposta i risultati
 * @param newRisultati it.cnr.jada.bulk.BulkList
 */
public void setRisultati(it.cnr.jada.bulk.BulkList newRisultati) {
	risultati = newRisultati;
}
/**
 * Imposta i tipi di risultato
 * @param newTipi_risultato java.util.Collection
 */
public void setTipi_risultato(java.util.Collection newTipi_risultato) {
	tipi_risultato = newTipi_risultato;
}
/**
 * Imposta il valore della propriet� 'tipo_linea_attivita'
 *
 * @param newTipo_linea_attivita	Il valore da assegnare a 'tipo_linea_attivita'
 */
public void setTipo_linea_attivita(Tipo_linea_attivitaBulk newTipo_linea_attivita) {
	tipo_linea_attivita = newTipo_linea_attivita;
}
/**
 * Esegue la validazione formale dei campi di input
 */

public void validate() throws ValidationException 
{
	super.validate();

	if ( getResponsabile() == null || getResponsabile().getCd_terzo() == null)
	{
		throw new ValidationException( "Il campo Responsabile deve essere valorizzato. " );
	}
	
	if ( getEsercizio_fine() == null )
	{
		setEsercizio_fine( it.cnr.contab.config00.esercizio.bulk.EsercizioBulk.ESERCIZIO_FINE);
		throw new ValidationException( "Il campo Esercizio di Terminazione deve essere valorizzato. " );
	}
	if ( getEsercizio_fine().compareTo( getEsercizio_inizio()) < 0 )
		throw new ValidationException( "L' esercizio di terminazione non pu� essere minore dell'esercizio di creazione. " );

	if ( getEsercizio_fine().toString().length() != 4 )
		throw new ValidationException( "Il campo Esercizio di terminazione deve essere di quattro cifre. " );

	if (getCd_centro_responsabilita() == null )
		throw new ValidationException( "Il campo CENTRO DI RESPONSABILITA' non pu� essere nullo. " );
}
	/**
	 * Returns the progetto.
	 * @return it.cnr.contab.progettiric00.core.bulk.ProgettoBulk
	 */
	public it.cnr.contab.progettiric00.core.bulk.ProgettoBulk getProgetto() {
		return progetto;
	}

	/**
	 * Sets the progetto.
	 * @param progetto The progetto to set
	 */
	public void setProgetto(
		it.cnr.contab.progettiric00.core.bulk.ProgettoBulk progetto) {
		this.progetto = progetto;
	}
	
    public java.lang.Integer getPg_progetto() {
	it.cnr.contab.progettiric00.core.bulk.ProgettoBulk progetto = this.getProgetto();
	if (progetto == null)
		return null;
	return progetto.getPg_progetto();
    }
	/**
	 * Sets the pg_progetto_padre.
	 * @param pg_progetto_padre The pg_progetto_padre to set
	 */
	public void setPg_progetto(java.lang.Integer progetto) {
		  this.getProgetto().setPg_progetto(progetto);
	}
	
    public java.lang.String getCd_progetto() {
	it.cnr.contab.progettiric00.core.bulk.ProgettoBulk progetto = this.getProgetto();
	if (progetto == null)
		return null;
	return progetto.getCd_progetto();
    }	

	public ProgettoBulk getProgettopadre() {
	it.cnr.contab.progettiric00.core.bulk.ProgettoBulk progetto = getProgetto();
	if (progetto == null)
		return null;
	it.cnr.contab.progettiric00.core.bulk.ProgettoBulk progettopadre = progetto.getProgettopadre();
	if (progettopadre == null)
		return null;
	return progettopadre;
	}

	/**
	 * Ritorna il Resonsabile
	 * 
	 * @return responsabile
	 */
	public TerzoBulk getResponsabile() {
		return responsabile;
	}

	/**
	 * Setta il Resonsabile
	 * 
	 * @param obj
	 */
	public void setResponsabile(TerzoBulk obj) {
		responsabile = obj;
	}
	
	

}
