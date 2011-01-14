package it.cnr.contab.anagraf00.tabter.bulk;

import it.cnr.jada.action.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;
import it.cnr.jada.util.action.*;

/**
 * Gestione dei dati relativi alla tabella Comune
 */

public class ComuneBulk extends ComuneBase {

	private ProvinciaBulk provincia;
	private NazioneBulk nazione;

	public static final String COMUNE_ITALIANO = "I";
	public static final String COMUNE_ESTERO   = "E";

	public static final String CODICE_CATASTALE_ESTERO= "*";
	
	protected static final long PG_PRIMO_COMUNE_ESTERO = 40000;

	public final static it.cnr.jada.util.OrderedHashtable TI_ITALIANO_ESTERO = new it.cnr.jada.util.OrderedHashtable();
	static {
		TI_ITALIANO_ESTERO.put(COMUNE_ITALIANO, "Italiano");
		TI_ITALIANO_ESTERO.put(COMUNE_ESTERO, "Estero");
	}
/**
 * 
 */
public ComuneBulk() {
	super();
}
public ComuneBulk(java.lang.Long pg_comune) {
	super(pg_comune);
}
public java.lang.String getCd_provincia() {
	it.cnr.contab.anagraf00.tabter.bulk.ProvinciaBulk provincia = this.getProvincia();
	if (provincia == null)
		return null;
	return provincia.getCd_provincia();
}
	/**
	 * Restituisce la nazione associata al comune.
	 *
	 * @return <code>it.cnr.contab.anagraf00.tabter.bulk.NazioneBulk</code>
	 *
	 * @see setNazione
	 */

	public NazioneBulk getNazione() {
		return nazione;
	}
public java.lang.Long getPg_nazione() {
	it.cnr.contab.anagraf00.tabter.bulk.NazioneBulk nazione = this.getNazione();
	if (nazione == null)
		return null;
	return nazione.getPg_nazione();
}
	/**
	 * Restituisce la provincia associata al comune.
	 *
	 * @return <code>it.cnr.contab.anagraf00.tabter.bulk.ProvinciaBulk</code>
	 *
	 * @see setProvincia
	 */

	public ProvinciaBulk getProvincia() {
		return provincia;
	}
public java.util.Dictionary getTi_italiano_esteroKeys() {
	return TI_ITALIANO_ESTERO;
}
/**
  * Inizializza l'Oggetto Bulk COMUNE
  * Questo metodo � chiamato sia dall'initializeForInsert()
  * sia dall'initializeForSearch()
  *
 */

public OggettoBulk initialize(CRUDBP bp, ActionContext context) {

	super.initialize(bp, context);
	setNazione(new NazioneBulk());
	setProvincia(new ProvinciaBulk());
	return this;
}
public boolean isEstero(){
	return false;
}
public boolean isItaliano(){
	return false;
}
public boolean isROCdProvincia() {
	return getProvincia()==null || getProvincia().getCrudStatus()==NORMAL;
}
public boolean isROPgNazione() {
	return getNazione()==null || getNazione().getCrudStatus()==NORMAL;
}
public void setCd_provincia(java.lang.String cd_provincia) {
	this.getProvincia().setCd_provincia(cd_provincia);
}
	/**
	 * Imposta la nazione associata al comune.
	 *
	 * @param newNazione <code>it.cnr.contab.anagraf00.tabter.bulk.NazioneBulk</code>
	 *
	 * @see getNazione
	 */

	public void setNazione(NazioneBulk newNazione) {
		nazione = newNazione;
	}
public void setPg_nazione(java.lang.Long pg_nazione) {
	this.getNazione().setPg_nazione(pg_nazione);
}
	/**
	 * Imposta la provincia associata al comune.
	 *
	 * @param newProvincia <code>it.cnr.contab.anagraf00.tabter.bulk.ProvinciaBulk</code>
	 *
	 * @see getProvincia
	 */

	public void setProvincia(ProvinciaBulk newProvincia) {
		provincia = newProvincia;
	}
}
