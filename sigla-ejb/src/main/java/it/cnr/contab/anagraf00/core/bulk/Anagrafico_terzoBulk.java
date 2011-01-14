package it.cnr.contab.anagraf00.core.bulk;

import it.cnr.contab.compensi00.docs.bulk.V_terzo_per_compensoBulk;

/**
 * Gestione dei dati relativi alla tabella Anagrafico/Terzo
 */

public class Anagrafico_terzoBulk extends Anagrafico_terzoBase {
	private AnagraficoBulk anagrafico;
	private V_persona_fisicaBulk terzo;

	public static String LEGAME_STUDIO_ASSOCIATO = "STASS";
	
	public Anagrafico_terzoBulk() {
		super();
	}
	
	public Anagrafico_terzoBulk(java.lang.Integer cd_anag, java.lang.Integer cd_terzo, java.lang.String ti_legame) {
		super(cd_anag, cd_terzo, ti_legame);
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

	/**
	 * Restituisce l'<code>V_persona_fisicaBulk</code> a cui l'oggetto � correlato.
	 *
	 * @return it.cnr.contab.anagraf00.core.bulk.TerzoBulk
	 *
	 * @see setTerzo
	 */

	public V_persona_fisicaBulk getTerzo() {
		return terzo;
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

	/**
	 * Imposta l'<code>V_persona_fisicaBulk</code> a cui l'oggetto � correlato.
	 *
	 * @param newTerzo Terzo di riferimento.
	 *
	 * @see getTerzo
	 */

	public void setTerzo(V_persona_fisicaBulk newTerzo) {
		terzo = newTerzo;
	}
	
	@Override
	public Integer getCd_anag() {
		if (getAnagrafico()==null) return null;
		return getAnagrafico().getCd_anag();
	}
	
	@Override
	public Integer getCd_terzo() {
		if (getTerzo()==null) return null;
		return getTerzo().getCd_terzo();
	}
	
	@Override
	public void setCd_anag(Integer cd_anag) {
		if (getAnagrafico()!=null) 
			getAnagrafico().setCd_anag(cd_anag);
	}	

	@Override
	public void setCd_terzo(Integer cd_terzo) {
		if (getTerzo()!=null) 
			getTerzo().setCd_terzo(cd_terzo);
	}	

	/**
	 * Restituisce il valore della propriet� 'ds_terzo'
	 *
	 * @return Il valore della propriet� 'ds_terzo'
	 */
	public java.lang.String getDs_terzo() {
		if ( getTerzo() != null && getTerzo().getAnagrafico() != null &&
		     getTerzo().getAnagrafico().getCognome()!=null )
			return getTerzo().getAnagrafico().getCognome() + " " + getTerzo().getAnagrafico().getNome();
		return "";	
	}
}
