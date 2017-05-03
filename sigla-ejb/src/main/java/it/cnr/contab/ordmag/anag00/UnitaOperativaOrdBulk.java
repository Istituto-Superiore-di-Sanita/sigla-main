/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 26/04/2017
 */
package it.cnr.contab.ordmag.anag00;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.jada.bulk.BulkCollection;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.util.action.CRUDBP;
public class UnitaOperativaOrdBulk extends UnitaOperativaOrdBase {
	/**
	 * [UNITA_ORGANIZZATIVA Rappresentazione dei Centri di Spesa e delle Unit� Organizzative in una struttura ad albero organizzata su pi� livelli]
	 **/
	private Unita_organizzativaBulk unitaOrganizzativa =  new Unita_organizzativaBulk();
	private BulkList unitaOperativaColl = new BulkList();
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: UNITA_OPERATIVA_ORD
	 **/
	public UnitaOperativaOrdBulk() {
		super();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: UNITA_OPERATIVA_ORD
	 **/
	public UnitaOperativaOrdBulk(java.lang.String cdUnitaOperativa) {
		super(cdUnitaOperativa);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Rappresentazione dei Centri di Spesa e delle Unit� Organizzative in una struttura ad albero organizzata su pi� livelli]
	 **/
	public Unita_organizzativaBulk getUnitaOrganizzativa() {
		return unitaOrganizzativa;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Rappresentazione dei Centri di Spesa e delle Unit� Organizzative in una struttura ad albero organizzata su pi� livelli]
	 **/
	public void setUnitaOrganizzativa(Unita_organizzativaBulk unitaOrganizzativa)  {
		this.unitaOrganizzativa=unitaOrganizzativa;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdUnitaOrganizzativa]
	 **/
	public java.lang.String getCdUnitaOrganizzativa() {
		Unita_organizzativaBulk unitaOrganizzativa = this.getUnitaOrganizzativa();
		if (unitaOrganizzativa == null)
			return null;
		return getUnitaOrganizzativa().getCd_unita_organizzativa();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdUnitaOrganizzativa]
	 **/
	public void setCdUnitaOrganizzativa(java.lang.String cdUnitaOrganizzativa)  {
		this.getUnitaOrganizzativa().setCd_unita_organizzativa(cdUnitaOrganizzativa);
	}
	protected OggettoBulk initialize(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
		impostaUnitaOrganizzativa(context);
		return super.initialize(bp,context);
	}
	private void impostaUnitaOrganizzativa(it.cnr.jada.action.ActionContext context) {
		setCdUnitaOrganizzativa(it.cnr.contab.utenze00.bulk.CNRUserInfo.getUnita_organizzativa(context).getCd_unita_organizzativa());
	}
	public OggettoBulk initializeForSearch(CRUDBP bp,it.cnr.jada.action.ActionContext context) {

		super.initializeForSearch(bp,context);

		impostaUnitaOrganizzativa(context);

		return this;
	}
	public BulkList getUnitaOperativaColl() {
		return unitaOperativaColl;
	}
	public void setUnitaOperativaColl(BulkList unitaOperativaColl) {
		this.unitaOperativaColl = unitaOperativaColl;
	}
	public BulkCollection[] getBulkLists() {
		 return new it.cnr.jada.bulk.BulkCollection[] { 
				 unitaOperativaColl};
	}
	public int addToUnitaOperativaColl( AssUnitaOperativaOrdBulk ass) 
	{
		unitaOperativaColl.add(ass);
		ass.setUnitaOperativaOrd(this);
		return unitaOperativaColl.size()-1;
	}
	public AssUnitaOperativaOrdBulk removeFromUnitaOperativaColl(int index) 
	{
		// Gestisce la selezione del bottone cancella repertorio
		return (AssUnitaOperativaOrdBulk)unitaOperativaColl.remove(index);
	}
}