/*
 * Created by Aurelio's BulkGenerator 1.0
 * Date 23/04/2007
 */
package  it.cnr.contab.config00.bulk;
import it.cnr.jada.persistency.Keyed;
public class Codici_siopeBase extends Codici_siopeKey implements Keyed {
//    DESCRIZIONE VARCHAR(200) NOT NULL
	private java.lang.String descrizione;
 

	public Codici_siopeBase() {
		super();
	}
	public Codici_siopeBase(java.lang.Integer esercizio, java.lang.String ti_gestione, java.lang.String cd_siope) {
		super(esercizio, ti_gestione, cd_siope);
	}
	public java.lang.String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(java.lang.String descrizione)  {
		this.descrizione=descrizione;
	}
	
	
}