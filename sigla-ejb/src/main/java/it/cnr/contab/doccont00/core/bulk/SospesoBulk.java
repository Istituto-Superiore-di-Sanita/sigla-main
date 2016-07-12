package it.cnr.contab.doccont00.core.bulk;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import it.cnr.contab.config00.sto.bulk.CdsBulk;
import it.cnr.jada.bulk.BulkCollection;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.util.OrderedHashtable;

public class SospesoBulk extends SospesoBase {
	public static final String STATO_DOCUMENTO_TUTTI = "TUTTI";

	protected it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk v_man_rev = new it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk();
	protected Sospeso_det_etrBulk dettaglio_etr;
	protected Sospeso_det_uscBulk dettaglio_usc;	
	protected it.cnr.contab.config00.sto.bulk.CdsBulk cds = new it.cnr.contab.config00.sto.bulk.CdsBulk();
	protected it.cnr.contab.config00.sto.bulk.CdsBulk cds_origine = new it.cnr.contab.config00.sto.bulk.CdsBulk();	
	
	public static final String TIPO_ENTRATA = "E";
	public static final String TIPO_SPESA = "S";

	public static final String TIPO_CC = "C";
	public static final String TIPO_BANCA_ITALIA = "B";	

	static private java.util.Hashtable ti_cc_biKeys;

	public final static String TI_SOSPESO   = "S";
	public final static String TI_RISCONTRO = "R";

	public final static Dictionary ti_sospeso_riscontroKeys;
	static 
	{
		ti_sospeso_riscontroKeys = new Hashtable();
		ti_sospeso_riscontroKeys.put(TI_SOSPESO,	"Sospeso");
		ti_sospeso_riscontroKeys.put(TI_RISCONTRO,	"Riscontro");
	};

	public final static String STATO_SOSP_NON_APPLICATA = "N";
	public final static String STATO_SOSP_INIZIALE 		= "I";
	public final static String STATO_SOSP_ASS_A_CDS 	= "A";
	public final static String STATO_SOSP_IN_SOSPESO 	= "S";

	public final static Dictionary stato_sospesoKeys;
	static 
	{
		stato_sospesoKeys = new Hashtable();
		stato_sospesoKeys.put(STATO_SOSP_NON_APPLICATA,	"Non applicata");
		stato_sospesoKeys.put(STATO_SOSP_INIZIALE,		"Iniziale");
		stato_sospesoKeys.put(STATO_SOSP_ASS_A_CDS,		"Assegnato a CdS");
		stato_sospesoKeys.put(STATO_SOSP_IN_SOSPESO,	"In sospeso");
	};

	public final static Dictionary stato_sospesoCNRKeys;
	static 
	{
		stato_sospesoCNRKeys = new Hashtable();
		stato_sospesoCNRKeys.put(STATO_SOSP_INIZIALE,		"Iniziale");
		stato_sospesoCNRKeys.put(STATO_SOSP_ASS_A_CDS,		"Assegnato a CdS");
		stato_sospesoCNRKeys.put(STATO_SOSP_IN_SOSPESO,	"In sospeso");
	};

	public final static Dictionary tiStatoTextForSearchKeys;
	static 
	{
		tiStatoTextForSearchKeys = new OrderedHashtable();
		tiStatoTextForSearchKeys.put(SospesoBulk.STATO_SOSP_INIZIALE, "Iniziale");
		tiStatoTextForSearchKeys.put(SospesoBulk.STATO_SOSP_IN_SOSPESO, "In sospeso");
		tiStatoTextForSearchKeys.put(SospesoBulk.STATO_SOSP_ASS_A_CDS, "Assegnato a CdS");
		tiStatoTextForSearchKeys.put("LIBERO", "Libero");
		tiStatoTextForSearchKeys.put("ANN", "Annullato");
		tiStatoTextForSearchKeys.put(SospesoBulk.STATO_DOCUMENTO_TUTTI,SospesoBulk.STATO_DOCUMENTO_TUTTI);
	};
	
	public final static Dictionary ti_entrata_spesaKeys;
	static 
	{
		ti_entrata_spesaKeys = new Hashtable();
		ti_entrata_spesaKeys.put(TIPO_ENTRATA,	"Entrata");
		ti_entrata_spesaKeys.put(TIPO_SPESA,	"Spesa");
	};

	protected Collection reversaliAccertamentiColl = new java.util.ArrayList();
	protected Collection mandatiImpegniColl = new java.util.ArrayList();
	public Collection getMandatiImpegniColl() {
		return mandatiImpegniColl;
	}
	public void setMandatiImpegniColl(Collection mandatiImpegniColl) {
		this.mandatiImpegniColl = mandatiImpegniColl;
	}
	protected BulkList sospesiFigliColl = new BulkList();

	private boolean manRevRiportato;
	private String statoText;
	private String statoTextForSearch;
	
public SospesoBulk() {
	super();
}
public SospesoBulk(java.lang.String cd_cds,java.lang.String cd_sospeso,java.lang.Integer esercizio,java.lang.String ti_entrata_spesa,java.lang.String ti_sospeso_riscontro) {
	super(cd_cds,cd_sospeso,esercizio,ti_entrata_spesa,ti_sospeso_riscontro);
	setCds(new it.cnr.contab.config00.sto.bulk.CdsBulk(cd_cds));
}
/* aggiunge un sospeso figlio con stato iniziale e non assegnato ad alcun CDS */
public  int addToSospesiFigliColl( SospesoBulk figlio ) 
{
	figlio.setToBeCreated();
	figlio.setCd_cds( getCd_cds());
	figlio.setEsercizio( getEsercizio());
   figlio.setTi_entrata_spesa(getTi_entrata_spesa());
   figlio.setTi_sospeso_riscontro(getTi_sospeso_riscontro());
  	figlio.setDt_registrazione( getDt_registrazione());
   figlio.setDs_anagrafico( getDs_anagrafico());
   figlio.setCausale(getCausale());
   figlio.setTi_cc_bi(getTi_cc_bi());
   figlio.setFl_stornato( getFl_stornato());
   /*
   figlio.setIm_sospeso( getIm_sospeso());
   figlio.setIm_associato( getIm_associato());
   */
   figlio.setIm_sospeso( new BigDecimal(0));
   figlio.setIm_associato( new BigDecimal(0));
   figlio.setIm_ass_mod_1210( new BigDecimal(0));
   figlio.setCd_proprio_sospeso(getNextCdSospesoFiglio());   
   figlio.setCd_sospeso( getCd_sospeso() + "." + figlio.getCd_proprio_sospeso());
   figlio.setCd_sospeso_padre( getCd_sospeso());

  
   figlio.setCd_cds_origine( null );
   figlio.setCd_uo_origine( null );
   figlio.setStato_sospeso( SospesoBulk.STATO_SOSP_INIZIALE );

	this.sospesiFigliColl.add( figlio );
	return sospesiFigliColl.size()-1;
}

/* assegna un sospeso ad un cds impostandone lo stato a assegnato e il cds origine */
public  SospesoBulk assegnaACds( String cd_cds  ) 
{
	setCd_cds_origine( cd_cds );
	setCd_uo_origine( null );
	setStato_sospeso( this.STATO_SOSP_ASS_A_CDS );
	return this;
}

public void  cambiaStato()
{
	if ( STATO_SOSP_IN_SOSPESO.equals( getStato_sospeso()) ||
		  STATO_SOSP_INIZIALE.equals( getStato_sospeso()) )
		setCds_origine( new CdsBulk());
}	
/**
 * Restituisce un array di <code>BulkCollection</code> contenenti oggetti
 * bulk da rendere persistenti insieme al ricevente.
 * L'implementazione standard restituisce <code>null</code>.
 * @see it.cnr.jada.comp.GenericComponent#makeBulkPersistent
 */ 
public BulkCollection[] getBulkLists() {
	 return new it.cnr.jada.bulk.BulkCollection[] { 
			sospesiFigliColl };
}
public java.lang.String getCd_cds() {
	it.cnr.contab.config00.sto.bulk.CdsBulk cds = this.getCds();
	if (cds == null)
		return null;
	return cds.getCd_unita_organizzativa();
}
public java.lang.String getCd_cds_origine() {
	it.cnr.contab.config00.sto.bulk.CdsBulk cds_origine = this.getCds_origine();
	if (cds_origine == null)
		return null;
	return cds_origine.getCd_unita_organizzativa();
}
/**
 * @return it.cnr.contab.config00.sto.bulk.CdsBulk
 */
public it.cnr.contab.config00.sto.bulk.CdsBulk getCds() {
	return cds;
}
/**
 * Insert the method's description here.
 * Creation date: (13/02/2003 11.22.07)
 * @return it.cnr.contab.config00.sto.bulk.CdsBulk
 */
public it.cnr.contab.config00.sto.bulk.CdsBulk getCds_origine() {
	return cds_origine;
}
/**
 * @return it.cnr.contab.doccont00.core.bulk.Sospeso_det_etrBulk
 */
public Sospeso_det_etrBulk getDettaglio_etr() {
	return dettaglio_etr;
}
/**
 * @return it.cnr.contab.doccont00.core.bulk.Sospeso_det_uscBulk
 */
public Sospeso_det_uscBulk getDettaglio_usc() {
	return dettaglio_usc;
}

public java.math.BigDecimal getIm_associato_figli() 
{
	if ( getIm_associato() != null && getIm_associato().compareTo( new BigDecimal(0)) > 0 )
		return getIm_associato();
	BigDecimal importo = new BigDecimal(0);
	for ( Iterator	i = sospesiFigliColl.iterator(); i.hasNext(); )
		importo = importo.add( ((SospesoBulk)i.next()).getIm_associato());
	return importo;
}
/**
  * Metodo per calcolare l'importo disponibile del sospeso (im_sospeso - im_associato)
  * @return <code>BigDecimal</code> l'importo disponibile calcolato del sospeso
  */
public java.math.BigDecimal getIm_disponibile() 
{
	if ( getIm_sospeso() != null )
		return getIm_sospeso().subtract( getIm_associato());
	return new BigDecimal(0);	
}
public String getNextCdSospesoFiglio() 
{
	if ( getSospesiFigliColl().size() == 0)
		return "001";

	//identifico l'ultimo codice numerico	
	int[] codici = new int[ getSospesiFigliColl().size() ];
	int index = 0;

	for ( Iterator i = getSospesiFigliColl().iterator(); i.hasNext(); )
	{
		SospesoBulk sospeso = (SospesoBulk) i.next();
		codici[ index++] = Integer.parseInt(sospeso.getCd_proprio_sospeso());
	}	
	
	Arrays.sort( codici );

	int last = codici[ getSospesiFigliColl().size() - 1 ];

	//incremento l'ultimo codice numerico
	last++;

	//converto l'ultimo codice in stringa

	String tmp = (new Integer(last)).toString();

	//padding con 0 a sx
	String codice = new String();
	
	for ( int i = 0; i < 3 - tmp.length(); i++ )
		codice = codice.concat("0");
		
	codice = codice.concat( tmp );
	return codice;
	
}
/**
 * @return Collection
 */
public Collection getReversaliAccertamentiColl() {
	return reversaliAccertamentiColl;
}
/**
 * Insert the method's description here.
 * Creation date: (06/02/2003 14.45.07)
 * @return it.cnr.jada.bulk.BulkList
 */
public it.cnr.jada.bulk.BulkList getSospesiFigliColl() {
	return sospesiFigliColl;
}
/**
 * Metodo con cui si ottiene il valore della variabile <code>ti_cc_biKeys</code>
 * di tipo <code>Hashtable</code>.
 * @return java.util.Hashtable ti_cc_biKeys
 */
public java.util.Hashtable getTi_cc_biKeys() {
		Hashtable ti_cc_biKeys = new java.util.Hashtable();
		ti_cc_biKeys.put("C", "C/C");
		ti_cc_biKeys.put("B", "Banca d'Italia"); 
	return ti_cc_biKeys;
}
/**
 * @return it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk
 */
public it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk getV_man_rev() {
	return v_man_rev;
}
/**
 * @return it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk
 */
public it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk getV_man_rev_for_search() {
	return v_man_rev;
}
/* e' associato se
   - o il sospeso stesso � associato ad un mandato/reversale o lettera pag. 1210
   - o uno dei suoi figli � associato ad un mandato/reversale o lettera pag. 1210
*/   
   
public boolean isAssociato() 
{
	if (getIm_associato() == null)
		return false;

	if ( sospesiFigliColl.size() == 0 )
		return new BigDecimal(0).compareTo( getIm_associato()) < 0 || 
	       new BigDecimal(0).compareTo( getIm_ass_mod_1210()) < 0 ;
	       
	for ( Iterator i = sospesiFigliColl.iterator(); i.hasNext(); )
		if (((SospesoBulk) i.next()).isAssociato())
			return true;
	return false;		


}
/**
 * Insert the method's description here.
 * Creation date: (22/09/2003 12.05.19)
 * @return boolean
 */
public boolean isManRevRiportato() {
	return manRevRiportato;
}
public boolean isROCdsOrigine() {
	return cds_origine == null || cds_origine.getCrudStatus() == NORMAL || !STATO_SOSP_ASS_A_CDS.equals( getStato_sospeso());
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'rODocumentoCont'
 *
 * @return Il valore della propriet� 'rODocumentoCont'
 */
public boolean isRODocumentoCont() {
	return this.getV_man_rev() == null || this.getV_man_rev().getCrudStatus() == NORMAL;
}
public boolean isROFind_cds_origine() 
{
	return !STATO_SOSP_ASS_A_CDS.equals( getStato_sospeso()) || isAssociato();


}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'rOFindDocumentoCont'
 *
 * @return Il valore della propriet� 'rOFindDocumentoCont'
 */
public boolean isROFindDocumentoCont() {
	
	if (this.getV_man_rev() != null)
		return isManRevRiportato();
		
	return false;
}
public boolean isROIm_sospeso_figlio() 
{
	return isAssociato();
}
public boolean isROStato_sospesoCNR() 
{
	return isAssociato();

}
/* rimuove un sospeso figlio  */

public  SospesoBulk removeFromSospesiFigliColl( int index ) 
{
	return (SospesoBulk) sospesiFigliColl.remove( index );
}

public void setCd_cds(java.lang.String cd_cds) {
	this.getCds().setCd_unita_organizzativa(cd_cds);
}
public void setCd_cds_origine(java.lang.String cd_cds_origine) {
	this.getCds_origine().setCd_unita_organizzativa(cd_cds_origine);
}
/**
 * @param newCds it.cnr.contab.config00.sto.bulk.CdsBulk
 */
public void setCds(it.cnr.contab.config00.sto.bulk.CdsBulk newCds) {
	cds = newCds;
}
/**
 * Insert the method's description here.
 * Creation date: (13/02/2003 11.22.07)
 * @param newCds_origine it.cnr.contab.config00.sto.bulk.CdsBulk
 */
public void setCds_origine(it.cnr.contab.config00.sto.bulk.CdsBulk newCds_origine) {
	cds_origine = newCds_origine;
}
/**
 * @param newDettaglio_etr it.cnr.contab.doccont00.core.bulk.Sospeso_det_etrBulk
 */
public void setDettaglio_etr(Sospeso_det_etrBulk newDettaglio_etr) {
	dettaglio_etr = newDettaglio_etr;
}
/**
 * @param newDettaglio_usc it.cnr.contab.doccont00.core.bulk.Sospeso_det_uscBulk
 */
public void setDettaglio_usc(Sospeso_det_uscBulk newDettaglio_usc) {
	dettaglio_usc = newDettaglio_usc;
}
/**
 * Insert the method's description here.
 * Creation date: (22/09/2003 12.05.19)
 * @param newManRevRiportato boolean
 */
public void setManRevRiportato(boolean newManRevRiportato) {
	manRevRiportato = newManRevRiportato;
}
/**
 * @param newReversaliAccertamentiColl Collection
 */
public void setReversaliAccertamentiColl(Collection newReversaliAccertamentiColl) {
	reversaliAccertamentiColl = newReversaliAccertamentiColl;
}
/**
 * Insert the method's description here.
 * Creation date: (06/02/2003 14.45.07)
 * @param newFigliColl it.cnr.jada.bulk.BulkList
 */
public void setSospesiFigliColl(it.cnr.jada.bulk.BulkList newFigliColl) {
	sospesiFigliColl = newFigliColl;
}
/**
 * @param newV_man_rev it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk
 */
public void setV_man_rev(it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk newV_man_rev) {
	v_man_rev = newV_man_rev;
}
public  void setV_man_rev_for_search( it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk newV_man_rev) 
{
	v_man_rev = newV_man_rev;
}
/**
 * Metodo con cui si verifica la validit� di alcuni campi, mediante un 
 * controllo sintattico o contestuale.
 */
public void validate() throws ValidationException {
	super.validate();
	
	// controllo su campo PROGRESSIVO DOC.CONTABILE
	if ( this.TI_RISCONTRO.equals( getTi_sospeso_riscontro()) && (getV_man_rev().getPg_documento_cont() == null) )
		throw new ValidationException( "Il campo PROGRESSIVO DOC.CONTABILE � obbligatorio." );

	// verifica sul campo IMPORTO
	if ( ( getIm_sospeso() == null ) || getIm_sospeso().compareTo( new java.math.BigDecimal(0) ) <= 0 )
		throw new ValidationException( "L' IMPORTO deve essere maggiore di 0." );

	// verifica sul campo DATA REGISTRAZIONE
	if ( (getDt_registrazione() == null ) )
		throw new ValidationException( "Il campo DATA di REGISTRAZIONE � obbligatorio." );

	java.sql.Timestamp dataRegistrazione = getDt_registrazione();
//	java.sql.Timestamp dataSistema = new java.sql.Timestamp(System.currentTimeMillis());
	java.sql.Timestamp dataSistema;
	try
	{
		dataSistema = it.cnr.jada.util.ejb.EJBCommonServices.getServerDate();
	}
	catch ( Exception e )
	{
		throw new ValidationException( "Impossibile recuperare la data di sistema!");
	}		

	if (dataRegistrazione.after(dataSistema))
		throw new ValidationException( "Non � possibile inserire una data di registrazione posteriore a quella di sistema." );

	// verifica sul campo C/C - BANCA D'ITALIA
	if ( (getTi_cc_bi() == null ) )
		throw new ValidationException( "Il campo C/C-BANCA D'ITALIA � obbligatorio." );

	// verifica sul campo TIPO
	if ( (getTi_sospeso_riscontro() == null ) )
		throw new ValidationException( "Il campo TIPO � obbligatorio." );

	// verifica sul campo ENTRATA/SPESA
	if ( (getTi_entrata_spesa() == null ) )
		throw new ValidationException( "Il campo ENTRATA/SPESA � obbligatorio." );

	// verifica sul campo CODICE
	if ( (getCd_sospeso() == null ) )
		throw new ValidationException( "Il campo CODICE � obbligatorio." );

/*		spostato nella component
if ( SospesoBulk.TI_SOSPESO.equals( getTi_sospeso_riscontro())	&&
		  getSospesiFigliColl().size() == 0 )
		throw new ValidationException( "Deve essere definito almeno un dettaglio." );
*/		
		
	SospesoBulk figlio;
	BigDecimal im_figli = new BigDecimal(0);	
	for ( Iterator i = sospesiFigliColl.iterator(); i.hasNext(); )
	{
		figlio = (SospesoBulk) i.next();
		figlio.validateFiglio();
		im_figli = im_figli.add( figlio.getIm_sospeso() );
	}
	if ( sospesiFigliColl.size() > 0 && im_figli.compareTo( getIm_sospeso()) != 0 )
		throw new ValidationException( "La somma degli importi dei dettagli deve essere uguale all'importo del sospeso." );	

}
/**
 * Metodo con cui si verifica la validit� di alcuni campi, mediante un 
 * controllo sintattico o contestuale.
 */
public void validateFiglio() throws ValidationException 
{
	if ( STATO_SOSP_ASS_A_CDS.equals( getStato_sospeso()) &&
		  getCd_cds_origine() == null )
		throw new ValidationException( "Per ogni dettaglio con stato 'assegnato a cds', deve essere specificato il Cds a cui � stato assegnato." );
	if ( getIm_sospeso() == null )
	{
		setIm_sospeso( new BigDecimal(0));
		throw new ValidationException( "Per ogni dettaglio deve essere specificato un importo." );
	}
	if ( getIm_sospeso().compareTo( new BigDecimal(0)) <= 0 )
		throw new ValidationException( "Per ogni dettaglio deve essere specificato un importo positivo." );

	
}
public String getStatoText() {
	return statoText;
}
public void setStatoText(String statoText) {
	this.statoText = statoText;
}
public String getStatoTextForSearch() {
	return statoTextForSearch;
}
public void setStatoTextForSearch(String statoTextForSearch) {
	this.statoTextForSearch = statoTextForSearch;
}
}
