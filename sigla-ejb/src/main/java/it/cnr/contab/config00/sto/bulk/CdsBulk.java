package it.cnr.contab.config00.sto.bulk;

import it.cnr.jada.bulk.BulkCollection;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.util.OrderedHashtable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(value=Include.NON_NULL)
public class CdsBulk extends CdsBase {
	private it.cnr.contab.anagraf00.core.bulk.TerzoBulk responsabile = new it.cnr.contab.anagraf00.core.bulk.TerzoBulk();
	private OrderedHashtable tipologiaKeys;
	private BulkList percentuali = new BulkList();
	private Integer esercizioDiScrivania;
	private Area_scientificaBulk area_scientifica;
	public CdsBulk() {
		super();
		inizializza();
	}
	public CdsBulk(java.lang.String cd_unita_organizzativa) {
		super(cd_unita_organizzativa);
		inizializza();
		this.setCd_unita_organizzativa(cd_unita_organizzativa);
	}
	/**
	 * Aggiunge alla lista delle percentuali di copertura obbligazione la nuova percentuale
	 * 
	 *
	 * @param prr nuova percentuale
	 * @return ritorna l'ampiezza della lista percentuali prima dell'aggiunzione
	 */
	public int addToPercentuali( Prc_copertura_obbligBulk prc ) 
	{
		percentuali.add(prc);
		//	prc.setCd_unita_organizzativa( getCd_unita_organizzativa());
		prc.setCds(this);

		prc.setUser(this.getUser());
		return percentuali.size()-1;
	}
	/**
	 * <!-- @TODO: da completare -->
	 * Restituisce il valore della propriet� 'area_scientifica'
	 *
	 * @return Il valore della propriet� 'area_scientifica'
	 */
	public Area_scientificaBulk getArea_scientifica() {
		return area_scientifica;
	}
	public BulkCollection[] getBulkLists() 
	{
		return new it.cnr.jada.bulk.BulkCollection[] { 
				percentuali };
	}
	public String getCd_area_scientifica() {
		Area_scientificaBulk area_scientifica = this.getArea_scientifica();
		if (area_scientifica == null)
			return null;
		return area_scientifica.getCd_area_scientifica();
	}
	/**
	 * <!-- @TODO: da completare -->
	 * Restituisce il valore della propriet� 'cd_ds_cds'
	 *
	 * @return Il valore della propriet� 'cd_ds_cds'
	 */
	public String getCd_ds_cds() {
		return getCd_unita_organizzativa() + " - " + getDs_unita_organizzativa();
	}
	public java.lang.Integer getCd_responsabile() {
		it.cnr.contab.anagraf00.core.bulk.TerzoBulk responsabile = this.getResponsabile();
		if (responsabile == null)
			return null;
		return responsabile.getCd_terzo();
	}
	/**
	 * <!-- @TODO: da completare -->
	 * Restituisce il valore della propriet� 'ds_responsabile'
	 *
	 * @return Il valore della propriet� 'ds_responsabile'
	 */
	public java.lang.String getDs_responsabile() {
		if ( responsabile != null && responsabile.getAnagrafico() != null )
			return responsabile.getAnagrafico().getCognome() + " " + responsabile.getAnagrafico().getNome();
		return "";	
	}
	/**
	 * <!-- @TODO: da completare -->
	 * Restituisce il valore della propriet� 'esercizioDiScrivania'
	 *
	 * @return Il valore della propriet� 'esercizioDiScrivania'
	 */
	public java.lang.Integer getEsercizioDiScrivania() {
		return esercizioDiScrivania;
	}
	/**
	 * <!-- @TODO: da completare -->
	 * Restituisce il valore della propriet� 'percentuali'
	 *
	 * @return Il valore della propriet� 'percentuali'
	 */
	public it.cnr.jada.bulk.BulkList getPercentuali() {
		return percentuali;
	}
	/**
	 * @return it.cnr.contab.anagraf00.core.bulk.TerzoBulk
	 */
	public it.cnr.contab.anagraf00.core.bulk.TerzoBulk getResponsabile() {
		return responsabile;
	}
	/**
	 * @return java.util.Hashtable
	 */
	public OrderedHashtable getTipologiaKeys() {
		return tipologiaKeys;
	}
	/**
	 * Metodo per la gestione del campo <code>esercizio</code>.
	 */
	public OggettoBulk initializeForInsert(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
		setEsercizio_inizio(it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context));
		setEsercizioDiScrivania(it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context));
		setEsercizio_fine( it.cnr.contab.config00.esercizio.bulk.EsercizioBulk.ESERCIZIO_FINE);
		setResponsabile(new it.cnr.contab.anagraf00.core.bulk.V_terzo_persona_fisicaBulk());
		return this;
	}
	/**
	 * Metodo per la gestione del campo <code>esercizio</code>.
	 */
	public OggettoBulk initializeForSearch(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
		// setEsercizio_inizio(it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context));
		setResponsabile(new it.cnr.contab.anagraf00.core.bulk.V_terzo_persona_fisicaBulk());
		return this;
	}
	/**
	 * Inizializza gli attributi specifici del Cds
	 */

	protected void inizializza()
	{
		setFl_cds( new Boolean( true ) );
	}	
	/**
	 * Determina quando abilitare o meno nell'interfaccia utente il campo responsabile
	 * @return boolean true quando il campo deve essere disabilitato
	 */

	public boolean isROResponsabile() {
		return responsabile == null || responsabile.getCrudStatus() == NORMAL;
	}
	/**
	 * Rimuove una percentuale dalla lista delle percentuali copertura obbligazioni per indice
	 * 
	 *
	 * @param index	indice della percentuale da rimuvere
	 * @return percentuale rimossa
	 */
	public Prc_copertura_obbligBulk removeFromPercentuali(int index) 
	{
		return (Prc_copertura_obbligBulk)percentuali.remove(index);
	}
	/**
	 * <!-- @TODO: da completare -->
	 * Imposta il valore della propriet� 'area_scientifica'
	 *
	 * @param newArea_scientifica	Il valore da assegnare a 'area_scientifica'
	 */
	public void setArea_scientifica(Area_scientificaBulk newArea_scientifica) {
		area_scientifica = newArea_scientifica;
	}
	public void setCd_area_scientifica(String cd_area_scientifica) {
		this.getArea_scientifica().setCd_area_scientifica(cd_area_scientifica);
	}
	public void setCd_responsabile(java.lang.Integer cd_responsabile) {
		this.getResponsabile().setCd_terzo(cd_responsabile);
	}
	/**
	 * <!-- @TODO: da completare -->
	 * Imposta il valore della propriet� 'esercizioDiScrivania'
	 *
	 * @param newEsercizioDiScrivania	Il valore da assegnare a 'esercizioDiScrivania'
	 */
	public void setEsercizioDiScrivania(java.lang.Integer newEsercizioDiScrivania) {
		esercizioDiScrivania = newEsercizioDiScrivania;
	}
	/**
	 * <!-- @TODO: da completare -->
	 * Imposta il valore della propriet� 'percentuali'
	 *
	 * @param newPercentuali	Il valore da assegnare a 'percentuali'
	 */
	public void setPercentuali(it.cnr.jada.bulk.BulkList newPercentuali) {
		percentuali = newPercentuali;
	}
	/**
	 * @param newResponsabile it.cnr.contab.anagraf00.core.bulk.TerzoBulk
	 */
	public void setResponsabile(it.cnr.contab.anagraf00.core.bulk.TerzoBulk newResponsabile) {
		responsabile = newResponsabile;
	}
	/**
	 * @param newTipologiaKeys java.util.Hashtable
	 */
	public void setTipologiaKeys(OrderedHashtable newTipologiaKeys) {
		tipologiaKeys = newTipologiaKeys;
	}
	/**
	 * Esegue la validazione formale dei campi di input
	 */

	public void validate() throws ValidationException 
	{	
		if ( isNullOrEmpty( getCd_tipo_unita() ))
			throw new ValidationException( "Il campo TIPOLOGIA � obbligatorio. " );

		if ( getCd_tipo_unita().equals( Tipo_unita_organizzativaHome.TIPO_UO_SAC ) && 
				!getEsercizio_fine().equals( it.cnr.contab.config00.esercizio.bulk.EsercizioBulk.ESERCIZIO_FINE))
			throw new ValidationException( "L'esercizio fine per un CDS di tipo SAC deve essere uguale a " + it.cnr.contab.config00.esercizio.bulk.EsercizioBulk.ESERCIZIO_FINE );

		if ( getEsercizio_fine() == null )
		{
			setEsercizio_fine( it.cnr.contab.config00.esercizio.bulk.EsercizioBulk.ESERCIZIO_FINE);
			throw new ValidationException( "Il campo Esercizio di Terminazione deve essere valorizzato. " );
		}
		if ( getEsercizio_fine().compareTo( getEsercizio_inizio()) < 0 )
			throw new ValidationException( "L' esercizio di terminazione non pu� essere minore dell'esercizio di creazione. " );

		if ( getEsercizio_fine().toString().length() != 4 )
			throw new ValidationException( "Il campo Esercizio di terminazione deve essere di quattro cifre. " );

		if ( responsabile.getCd_terzo() == null )
			throw new ValidationException( "Il campo RESPONSABILE � obbligatorio. " );	
		if ( !isNullOrEmpty( getCd_proprio_unita() ) )
		{
			long cdLong;
			try
			{
				cdLong = Long.parseLong( getCd_proprio_unita() );
			}
			catch (java.lang.NumberFormatException e)
			{
				throw new ValidationException( "Il campo CODICE deve essere numerico. " );			
			}
			if ( cdLong < 0 )
				throw new ValidationException( "Il campo CODICE deve essere maggiore di 0. " );			
		}
		/*
	if ( getPrc_copertura_obblig_2() != null && (getPrc_copertura_obblig_2().floatValue() > 100 || getPrc_copertura_obblig_2().floatValue() < 0))
		throw new ValidationException( "Il campo PERCENTUALE deve essere inferiore a 100. " );

	if ( getPrc_copertura_obblig_3() != null && (getPrc_copertura_obblig_3().floatValue() > 100 || getPrc_copertura_obblig_3().floatValue() < 0))
		throw new ValidationException( "Il campo PERCENTUALE deve essere inferiore a 100. " );	
		 */	

	}
}
