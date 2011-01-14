/*
* Creted by Generator 1.0
* Date 18/02/2005
*/
package it.cnr.contab.config00.sto.bulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.util.action.CRUDBP;
public class DipartimentoBulk extends DipartimentoBase {
	private it.cnr.contab.anagraf00.core.bulk.TerzoBulk direttore = new it.cnr.contab.anagraf00.core.bulk.TerzoBulk();	
	public DipartimentoBulk() {
		super();
	}
	public DipartimentoBulk(java.lang.String cd_dipartimento) {
		super(cd_dipartimento);
	}
	/**
	 * @return
	 */
	public it.cnr.contab.anagraf00.core.bulk.TerzoBulk getDirettore() {
		return direttore;
	}

	/**
	 * @param bulk
	 */
	public void setDirettore(
		it.cnr.contab.anagraf00.core.bulk.TerzoBulk bulk) {
		direttore = bulk;
	}
	public java.lang.Integer getCd_terzo() {
		it.cnr.contab.anagraf00.core.bulk.TerzoBulk direttore = this.getDirettore();
		if (direttore == null)
			return null;
		return direttore.getCd_terzo();
	}
	/**
	 * Restituisce il valore della propriet� 'ds_responsabile'
	 *
	 * @return Il valore della propriet� 'ds_responsabile'
	 */
	public java.lang.String getDs_direttore() {
		if ( direttore != null && direttore.getAnagrafico() != null )
			return direttore.getAnagrafico().getCognome() + " " + direttore.getAnagrafico().getNome();
		return "";	
	}	
	public OggettoBulk initializeForInsert(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
		setDirettore(new it.cnr.contab.anagraf00.core.bulk.V_terzo_persona_fisicaBulk());
		return this;
	}	
	public OggettoBulk initializeForSearch(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
		setDirettore(new it.cnr.contab.anagraf00.core.bulk.V_terzo_persona_fisicaBulk());
		return this;
	}	
	/**
	 * Determina quando abilitare o meno nell'interfaccia utente il campo direttore
	 * @return boolean true quando il campo deve essere disabilitato
	 */

	public boolean isRODirettore() {
		return direttore == null || direttore.getCrudStatus() == NORMAL;
	}
	public void setCd_terzo(java.lang.Integer cd_direttore) {
		this.getDirettore().setCd_terzo(cd_direttore);
	}		
	
}