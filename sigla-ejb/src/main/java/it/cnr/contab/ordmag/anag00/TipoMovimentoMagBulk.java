/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 26/04/2017
 */
package it.cnr.contab.ordmag.anag00;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.jada.bulk.OggettoBulk;
public class TipoMovimentoMagBulk extends TipoMovimentoMagBase {
	/**
	 * [UNITA_ORGANIZZATIVA Rappresentazione dei Centri di Spesa e delle Unit� Organizzative in una struttura ad albero organizzata su pi� livelli]
	 **/
	private Unita_organizzativaBulk unitaOrganizzativa =  new Unita_organizzativaBulk();
	/**
	 * [TIPO_MOVIMENTO_MAG Anagrafica delle Tipologie dei Movimenti.]
	 **/
	private TipoMovimentoMagBulk tipoMovimentoMagStorno;
	private TipoMovimentoMagBulk tipoMovimentoMagAlt;
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: TIPO_MOVIMENTO_MAG
	 **/
	public TipoMovimentoMagBulk() {
		super();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: TIPO_MOVIMENTO_MAG
	 **/
	public TipoMovimentoMagBulk(java.lang.String cdCds, java.lang.String cdTipoMovimento) {
		super(cdCds, cdTipoMovimento);
		setUnitaOrganizzativa( new Unita_organizzativaBulk(cdCds) );
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
	 * Restituisce il valore di: [Anagrafica delle Tipologie dei Movimenti.]
	 **/
	public TipoMovimentoMagBulk getTipoMovimentoMagStorno() {
		return tipoMovimentoMagStorno;
	}
	public TipoMovimentoMagBulk getTipoMovimentoMagAlt() {
		return tipoMovimentoMagAlt;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Anagrafica delle Tipologie dei Movimenti.]
	 **/
	public void setTipoMovimentoMagStorno(TipoMovimentoMagBulk tipoMovimentoMagStorno)  {
		this.tipoMovimentoMagStorno=tipoMovimentoMagStorno;
	}
	public void setTipoMovimentoMagAlt(TipoMovimentoMagBulk tipoMovimentoMagAlt)  {
		this.tipoMovimentoMagAlt=tipoMovimentoMagAlt;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdCds]
	 **/
	public java.lang.String getCdCds() {
		Unita_organizzativaBulk unitaOrganizzativa = this.getUnitaOrganizzativa();
		if (unitaOrganizzativa == null)
			return null;
		return getUnitaOrganizzativa().getCd_unita_organizzativa();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdCds]
	 **/
	public void setCdCds(java.lang.String cdCds)  {
		this.getUnitaOrganizzativa().setCd_unita_organizzativa(cdCds);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdCdsStorno]
	 **/
	public java.lang.String getCdCdsStorno() {
		TipoMovimentoMagBulk tipoMovimentoMag = this.getTipoMovimentoMagStorno();
		if (tipoMovimentoMag == null)
			return null;
		return getTipoMovimentoMagStorno().getCdCds();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdCdsStorno]
	 **/
	public void setCdCdsStorno(java.lang.String cdCdsStorno)  {
		this.getTipoMovimentoMagStorno().setCdCds(cdCdsStorno);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdTipoMovimentoStorno]
	 **/
	public java.lang.String getCdTipoMovimentoStorno() {
		TipoMovimentoMagBulk tipoMovimentoMag = this.getTipoMovimentoMagStorno();
		if (tipoMovimentoMag == null)
			return null;
		return getTipoMovimentoMagStorno().getCdTipoMovimento();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdTipoMovimentoStorno]
	 **/
	public void setCdTipoMovimentoStorno(java.lang.String cdTipoMovimentoStorno)  {
		this.getTipoMovimentoMagStorno().setCdTipoMovimento(cdTipoMovimentoStorno);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdCdsAlt]
	 **/
	public java.lang.String getCdCdsAlt() {
		TipoMovimentoMagBulk tipoMovimentoMag = this.getTipoMovimentoMagAlt();
		if (tipoMovimentoMag == null)
			return null;
		return getTipoMovimentoMagAlt().getCdCds();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdCdsAlt]
	 **/
	public void setCdCdsAlt(java.lang.String cdCdsAlt)  {
		this.getTipoMovimentoMagAlt().setCdCds(cdCdsAlt);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdTipoMovimentoAlt]
	 **/
	public java.lang.String getCdTipoMovimentoAlt() {
		TipoMovimentoMagBulk tipoMovimentoMag = this.getTipoMovimentoMagAlt();
		if (tipoMovimentoMag == null)
			return null;
		return getTipoMovimentoMagAlt().getCdTipoMovimento();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdTipoMovimentoAlt]
	 **/
	public void setCdTipoMovimentoAlt(java.lang.String cdTipoMovimentoAlt)  {
		this.getTipoMovimentoMagAlt().setCdTipoMovimento(cdTipoMovimentoAlt);
	}

	public OggettoBulk initializeForInsert(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {

		super.initializeForInsert(bp,context);
		
		setTipoMovimentoMagAlt(new TipoMovimentoMagBulk());
		setTipoMovimentoMagStorno(new TipoMovimentoMagBulk());
		return this;
	}
	protected OggettoBulk initialize(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
		setCdCds(it.cnr.contab.utenze00.bulk.CNRUserInfo.getUnita_organizzativa(context).getCd_cds());
		return super.initialize(bp,context);
	}

}