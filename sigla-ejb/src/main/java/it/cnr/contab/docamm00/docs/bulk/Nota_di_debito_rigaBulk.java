package it.cnr.contab.docamm00.docs.bulk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Insert the type's description here.
 * Creation date: (10/25/2001 11:52:43 AM)
 * @author: Roberto Peli
 */
@JsonInclude(value=Include.NON_NULL)
public class Nota_di_debito_rigaBulk extends Fattura_passiva_rigaBulk {
	@JsonIgnore
	private Nota_di_debitoBulk notaDiDebito;
	@JsonIgnore
	private Fattura_passiva_rigaIBulk riga_fattura_origine;
	@JsonIgnore
	private Fattura_passiva_rigaIBulk riga_fattura_associata;

	public final static java.util.Dictionary STATO;

	static{
		STATO = new it.cnr.jada.util.OrderedHashtable();
		STATO.put(STATO_INIZIALE,"Iniziale");
		STATO.put(STATO_CONTABILIZZATO,"Addebitato");
		STATO.put(STATO_ANNULLATO,"Annullato");
		STATO.put(STATO_PAGATO,"Pagato");
	}

	/**
	 * Nota_di_credito_rigaBulk constructor comment.
	 */
	public Nota_di_debito_rigaBulk() {
		super();
	}
	/**
	 * Nota_di_credito_rigaBulk constructor comment.
	 */
	public Nota_di_debito_rigaBulk(Fattura_passiva_rigaIBulk dettaglio) {
		super();

		try {
			copyFrom(dettaglio);
		} catch (it.cnr.jada.bulk.FillException e) {
		}
	}
	public Nota_di_debito_rigaBulk(java.lang.String cd_cds,java.lang.String cd_unita_organizzativa,java.lang.Integer esercizio,java.lang.Long pg_fattura_passiva,java.lang.Long progressivo_riga) {
		super(cd_cds,cd_unita_organizzativa,esercizio,pg_fattura_passiva,progressivo_riga);
		setNotaDiDebito(new it.cnr.contab.docamm00.docs.bulk.Nota_di_debitoBulk(cd_cds,cd_unita_organizzativa,esercizio,pg_fattura_passiva));
	}
	public void copyFrom(Fattura_passiva_rigaIBulk dettaglio)
			throws it.cnr.jada.bulk.FillException {

		setStato_cofi(STATO_INIZIALE);
		setTi_associato_manrev(NON_ASSOCIATO_A_MANDATO);

		setDt_da_competenza_coge(dettaglio.getDt_da_competenza_coge());
		setDt_a_competenza_coge(dettaglio.getDt_a_competenza_coge());
		setTi_istituz_commerc(dettaglio.getTi_istituz_commerc());
		setProgressivo_riga(new Long(getNotaDiDebito().getFattura_passiva_dettColl().size() + 1));

		setBene_servizio(dettaglio.getBene_servizio());
		setVoce_iva(dettaglio.getVoce_iva());

		boolean zeroNotValid = false;
		if (dettaglio.hasAddebiti()) {
			setQuantita(new java.math.BigDecimal(1));
			setPrezzo_unitario(new java.math.BigDecimal(0));
			setFl_iva_forzata(Boolean.FALSE);
			zeroNotValid = true;
		} else {
			setQuantita(dettaglio.getQuantita());
			setPrezzo_unitario(dettaglio.getPrezzo_unitario());
			setFl_iva_forzata(dettaglio.getFl_iva_forzata());
			if (getFl_iva_forzata().booleanValue())
				setIm_iva(dettaglio.getIm_iva());
		}
		setDs_riga_fattura(dettaglio.getDs_riga_fattura());
		setRiga_fattura_origine(dettaglio);

		setFornitore(dettaglio.getFornitore());
		//setTermini(dettaglio.getTermini());
		setTermini_pagamento(dettaglio.getTermini_pagamento());
		setModalita_pagamento(dettaglio.getModalita_pagamento());
		setModalita(dettaglio.getModalita());
		setBanche(dettaglio.getBanche());
		setBanca(dettaglio.getBanca());
		setCessionario(dettaglio.getCessionario());

		calcolaCampiDiRiga();
		java.math.BigDecimal vecchioTotale = new java.math.BigDecimal(0).setScale(0, java.math.BigDecimal.ROUND_HALF_UP);
		java.math.BigDecimal totaleDiRiga = getIm_imponibile().add(getIm_iva());
		java.math.BigDecimal nuovoImportoDisponibile = dettaglio.getIm_diponibile_nc().add(totaleDiRiga.subtract(vecchioTotale));
		if (nuovoImportoDisponibile.signum() < 0 || (nuovoImportoDisponibile.signum() == 0 && zeroNotValid)) {
			if (dettaglio.getBene_servizio() == null)
				throw new it.cnr.jada.bulk.FillException("Attenzione: l'importo di storno massimo ancora disponibile è di " + dettaglio.getIm_diponibile_nc() + " EUR!");
			else 
				throw new it.cnr.jada.bulk.FillException("Attenzione: l'importo di storno massimo ancora disponibile per \"" + dettaglio.getBene_servizio().getDs_bene_servizio() + "\" è di " + dettaglio.getIm_diponibile_nc() + " EUR!");
		}
		dettaglio.setIm_diponibile_nc(nuovoImportoDisponibile.setScale(2, java.math.BigDecimal.ROUND_HALF_UP));

	}
	public IDocumentoAmministrativoRigaBulk getAssociatedDetail() {

		return (IDocumentoAmministrativoRigaBulk)getRiga_fattura_associata();
	}
	public java.lang.String getCd_cds() {
		it.cnr.contab.docamm00.docs.bulk.Nota_di_debitoBulk notaDiDebito = this.getNotaDiDebito();
		if (notaDiDebito == null)
			return null;
		return notaDiDebito.getCd_cds();
	}
	public java.lang.String getCd_cds_assncna_eco() {
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk riga_fattura_origine = this.getRiga_fattura_origine();
		if (riga_fattura_origine == null)
			return null;
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_IBulk fattura_passivaI = riga_fattura_origine.getFattura_passivaI();
		if (fattura_passivaI == null)
			return null;
		return fattura_passivaI.getCd_cds();
	}
	public java.lang.String getCd_cds_assncna_fin() {
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk riga_fattura_associata = this.getRiga_fattura_associata();
		if (riga_fattura_associata == null)
			return null;
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_IBulk fattura_passivaI = riga_fattura_associata.getFattura_passivaI();
		if (fattura_passivaI == null)
			return null;
		return fattura_passivaI.getCd_cds();
	}
	public java.lang.String getCd_unita_organizzativa() {
		it.cnr.contab.docamm00.docs.bulk.Nota_di_debitoBulk notaDiDebito = this.getNotaDiDebito();
		if (notaDiDebito == null)
			return null;
		return notaDiDebito.getCd_unita_organizzativa();
	}
	public java.lang.String getCd_uo_assncna_eco() {
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk riga_fattura_origine = this.getRiga_fattura_origine();
		if (riga_fattura_origine == null)
			return null;
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_IBulk fattura_passivaI = riga_fattura_origine.getFattura_passivaI();
		if (fattura_passivaI == null)
			return null;
		return fattura_passivaI.getCd_unita_organizzativa();
	}
	public java.lang.String getCd_uo_assncna_fin() {
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk riga_fattura_associata = this.getRiga_fattura_associata();
		if (riga_fattura_associata == null)
			return null;
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_IBulk fattura_passivaI = riga_fattura_associata.getFattura_passivaI();
		if (fattura_passivaI == null)
			return null;
		return fattura_passivaI.getCd_unita_organizzativa();
	}
	public java.lang.Integer getEsercizio() {
		it.cnr.contab.docamm00.docs.bulk.Nota_di_debitoBulk notaDiDebito = this.getNotaDiDebito();
		if (notaDiDebito == null)
			return null;
		return notaDiDebito.getEsercizio();
	}
	public java.lang.Integer getEsercizio_assncna_eco() {
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk riga_fattura_origine = this.getRiga_fattura_origine();
		if (riga_fattura_origine == null)
			return null;
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_IBulk fattura_passivaI = riga_fattura_origine.getFattura_passivaI();
		if (fattura_passivaI == null)
			return null;
		return fattura_passivaI.getEsercizio();
	}
	public java.lang.Integer getEsercizio_assncna_fin() {
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk riga_fattura_associata = this.getRiga_fattura_associata();
		if (riga_fattura_associata == null)
			return null;
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_IBulk fattura_passivaI = riga_fattura_associata.getFattura_passivaI();
		if (fattura_passivaI == null)
			return null;
		return fattura_passivaI.getEsercizio();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (10/25/2001 12:03:34 PM)
	 * @return it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_IBulk
	 */
	public Fattura_passivaBulk getFattura_passiva() {

		return getNotaDiDebito();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/17/2001 10:32:30 AM)
	 * @return it.cnr.contab.docamm00.docs.bulk.Nota_di_debitoBulk
	 */
	public Nota_di_debitoBulk getNotaDiDebito() {
		return notaDiDebito;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2/13/2002 1:28:51 PM)
	 * @return it.cnr.contab.docamm00.docs.bulk.IDocumentoAmministrativoBulk
	 */
	public IDocumentoAmministrativoRigaBulk getOriginalDetail() {
		return getRiga_fattura_origine();
	}
	public java.lang.Long getPg_fattura_assncna_eco() {
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk riga_fattura_origine = this.getRiga_fattura_origine();
		if (riga_fattura_origine == null)
			return null;
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_IBulk fattura_passivaI = riga_fattura_origine.getFattura_passivaI();
		if (fattura_passivaI == null)
			return null;
		return fattura_passivaI.getPg_fattura_passiva();
	}
	public java.lang.Long getPg_fattura_assncna_fin() {
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk riga_fattura_associata = this.getRiga_fattura_associata();
		if (riga_fattura_associata == null)
			return null;
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_IBulk fattura_passivaI = riga_fattura_associata.getFattura_passivaI();
		if (fattura_passivaI == null)
			return null;
		return fattura_passivaI.getPg_fattura_passiva();
	}
	public java.lang.Long getPg_fattura_passiva() {
		it.cnr.contab.docamm00.docs.bulk.Nota_di_debitoBulk notaDiDebito = this.getNotaDiDebito();
		if (notaDiDebito == null)
			return null;
		return notaDiDebito.getPg_fattura_passiva();
	}
	public java.lang.Long getPg_riga_assncna_eco() {
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk riga_fattura_origine = this.getRiga_fattura_origine();
		if (riga_fattura_origine == null)
			return null;
		return riga_fattura_origine.getProgressivo_riga();
	}
	public java.lang.Long getPg_riga_assncna_fin() {
		it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk riga_fattura_associata = this.getRiga_fattura_associata();
		if (riga_fattura_associata == null)
			return null;
		return riga_fattura_associata.getProgressivo_riga();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (10/25/2001 2:48:15 PM)
	 * @return it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk
	 */
	public Fattura_passiva_rigaIBulk getRiga_fattura_associata() {
		return riga_fattura_associata;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (10/25/2001 2:48:15 PM)
	 * @return it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk
	 */
	public Fattura_passiva_rigaIBulk getRiga_fattura_origine() {
		return riga_fattura_origine;
	}
	/**
	 * Restituisce il <code>Dictionary</code> per la gestione dei tipi di fattura.
	 *
	 * @return java.util.Dictionary
	 */

	public java.util.Dictionary getStato_cofiKeys() {
		return STATO;
	}
	/**
	 * Restituisce il <code>Dictionary</code> per la gestione dei tipi di fattura.
	 *
	 * @return java.util.Dictionary
	 */

	public boolean isAddebitato() {

		return !STATO_INIZIALE.equals(getStato_cofi());
	}
	public void setCd_cds(java.lang.String cd_cds) {
		this.getNotaDiDebito().setCd_cds(cd_cds);
	}
	public void setCd_cds_assncna_eco(java.lang.String cd_cds_assncna_eco) {
		this.getRiga_fattura_origine().getFattura_passivaI().setCd_cds(cd_cds_assncna_eco);
	}
	public void setCd_cds_assncna_fin(java.lang.String cd_cds_assncna_fin) {
		this.getRiga_fattura_associata().getFattura_passivaI().setCd_cds(cd_cds_assncna_fin);
	}
	public void setCd_unita_organizzativa(java.lang.String cd_unita_organizzativa) {
		this.getNotaDiDebito().setCd_unita_organizzativa(cd_unita_organizzativa);
	}
	public void setCd_uo_assncna_eco(java.lang.String cd_uo_assncna_eco) {
		this.getRiga_fattura_origine().getFattura_passivaI().setCd_unita_organizzativa(cd_uo_assncna_eco);
	}
	public void setCd_uo_assncna_fin(java.lang.String cd_uo_assncna_fin) {
		this.getRiga_fattura_associata().getFattura_passivaI().setCd_unita_organizzativa(cd_uo_assncna_fin);
	}
	public void setEsercizio(java.lang.Integer esercizio) {
		this.getNotaDiDebito().setEsercizio(esercizio);
	}
	public void setEsercizio_assncna_eco(java.lang.Integer esercizio_assncna_eco) {
		this.getRiga_fattura_origine().getFattura_passivaI().setEsercizio(esercizio_assncna_eco);
	}
	public void setEsercizio_assncna_fin(java.lang.Integer esercizio_assncna_fin) {
		this.getRiga_fattura_associata().getFattura_passivaI().setEsercizio(esercizio_assncna_fin);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (9/10/2001 5:51:50 PM)
	 * @return it.cnr.contab.docamm00.tabrif.bulk.Voce_ivaBulk
	 */
	public void setFattura_passiva(Fattura_passivaBulk fattura_passiva) {

		setNotaDiDebito((Nota_di_debitoBulk)fattura_passiva);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/17/2001 10:32:30 AM)
	 * @param newNotaDiDebito it.cnr.contab.docamm00.docs.bulk.Nota_di_debitoBulk
	 */
	public void setNotaDiDebito(Nota_di_debitoBulk newNotaDiDebito) {
		notaDiDebito = newNotaDiDebito;
	}
	public void setPg_fattura_assncna_eco(java.lang.Long pg_fattura_assncna_eco) {
		this.getRiga_fattura_origine().getFattura_passivaI().setPg_fattura_passiva(pg_fattura_assncna_eco);
	}
	public void setPg_fattura_assncna_fin(java.lang.Long pg_fattura_assncna_fin) {
		this.getRiga_fattura_associata().getFattura_passivaI().setPg_fattura_passiva(pg_fattura_assncna_fin);
	}
	public void setPg_fattura_passiva(java.lang.Long pg_fattura_passiva) {
		this.getNotaDiDebito().setPg_fattura_passiva(pg_fattura_passiva);
	}
	public void setPg_riga_assncna_eco(java.lang.Long pg_riga_assncna_eco) {
		this.getRiga_fattura_origine().setProgressivo_riga(pg_riga_assncna_eco);
	}
	public void setPg_riga_assncna_fin(java.lang.Long pg_riga_assncna_fin) {
		this.getRiga_fattura_associata().setProgressivo_riga(pg_riga_assncna_fin);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (10/25/2001 2:48:15 PM)
	 * @param newRiga_fattura_associata it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk
	 */
	public void setRiga_fattura_associata(Fattura_passiva_rigaIBulk newRiga_fattura_associata) {
		riga_fattura_associata = newRiga_fattura_associata;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (10/25/2001 2:48:15 PM)
	 * @param newRiga_fattura_origine it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaIBulk
	 */
	public void setRiga_fattura_origine(Fattura_passiva_rigaIBulk newRiga_fattura_origine) {
		riga_fattura_origine = newRiga_fattura_origine;
	}
}
