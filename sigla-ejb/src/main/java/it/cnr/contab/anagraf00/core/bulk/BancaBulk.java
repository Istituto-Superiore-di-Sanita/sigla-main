package it.cnr.contab.anagraf00.core.bulk;

import java.util.StringTokenizer;

import it.cnr.contab.anagraf00.tabrif.bulk.*;
import it.cnr.contab.anagraf00.tabter.bulk.NazioneBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.bulk.*;
import it.cnr.jada.util.StrServ;

/**
 * Gestione dei dati relativi alla tabella Banca
 */

public class BancaBulk extends BancaBase {

	private TerzoBulk terzo;
	private it.cnr.contab.anagraf00.tabrif.bulk.AbicabBulk abi_cab;
	private java.util.Collection nazioniIban;
	private TerzoBulk  terzo_delegato;
	private String chiave;

	public static String ORIGINE_ON_LINE = "O";
	public static String ORIGINE_STIPENDI = "S";
	
	private NazioneBulk nazione_iban;

	// CODICE_IBAN_PARTE1 VARCHAR(34)
	private java.lang.String codice_iban_parte1;

	// CODICE_IBAN_PARTE2 VARCHAR(34)
	private java.lang.String codice_iban_parte2;

	// CODICE_IBAN_PARTE3 VARCHAR(34)
	private java.lang.String codice_iban_parte3;

	// CODICE_IBAN_PARTE4 VARCHAR(34)
	private java.lang.String codice_iban_parte4;

	// CODICE_IBAN_PARTE5 VARCHAR(34)
	private java.lang.String codice_iban_parte5;

	// CODICE_IBAN_PARTE6 VARCHAR(34)
	private java.lang.String codice_iban_parte6;
	
	/**
	 * Costruttore di default.
	 *
	 */

	public  BancaBulk() {
		super();
	}
/**
* Costruttore di default.
*
*/

public  BancaBulk(it.cnr.jada.util.action.CRUDController crud){

  	Modalita_pagamentoBulk mod_pagamento = (Modalita_pagamentoBulk)((it.cnr.jada.util.action.SimpleDetailCRUDController)crud).getParentController().getModel();
  	
    String rifModalita = mod_pagamento.getRif_modalita_pagamento().getTi_pagamento();
	String perCessione = "N";
	String cdTerzoDelegato = "XXX";
	
    setTi_pagamento(mod_pagamento.getRif_modalita_pagamento().getTi_pagamento());

 //  	if (mod_pagamento.isPerCessione())

	if (mod_pagamento.isPerCessione()){		
		setCd_terzo_delegato(getTerzo_delegato().getCd_terzo());		
		perCessione = "Y";
		//cdTerzoDelegato = Integer.toString(getCd_terzo_delegato().intValue());
	} 

	setChiave(rifModalita + "-" + perCessione + "-" + cdTerzoDelegato);
}
public BancaBulk(java.lang.Integer cd_terzo,java.lang.Long pg_banca) {
	super(cd_terzo,pg_banca);
	setTerzo(new it.cnr.contab.anagraf00.core.bulk.TerzoBulk(cd_terzo));
}
public java.lang.String getAbi() {
	it.cnr.contab.anagraf00.tabrif.bulk.AbicabBulk abi_cab = this.getAbi_cab();
	if (abi_cab == null)
		return null;
	return abi_cab.getAbi();
}
	/**
	 * Restituisce l'<code>AbicabBulk</code> relativo all'oggetto banca.
	 *
	 * @return it.cnr.contab.anagraf00.tabrif.bulk.AbicabBulk
	 *
	 * @see setAbi_cab
	 */

	public it.cnr.contab.anagraf00.tabrif.bulk.AbicabBulk getAbi_cab() {
		return abi_cab;
	}
public java.lang.String getCab() {
	it.cnr.contab.anagraf00.tabrif.bulk.AbicabBulk abi_cab = this.getAbi_cab();
	if (abi_cab == null)
		return null;
	return abi_cab.getCab();
}
public String getCd_ds_banca() {
	return (getPg_banca() + " - " + getIntestazione() + " (" + getTi_pagamento() + ")");
}
public java.lang.Integer getCd_terzo() {
	it.cnr.contab.anagraf00.core.bulk.TerzoBulk terzo = this.getTerzo();
	if (terzo == null)
		return null;
	return terzo.getCd_terzo();
}
/**
 * Insert the method's description here.
 * Creation date: (05/11/2002 16.44.37)
 * @return java.lang.String
 */
public java.lang.String getChiave() {
	
	if (chiave == null/* && this.getCrudStatus() == OggettoBulk.NORMAL*/){
		String perCessione = "N";
		String cdTerzoDelegato = "XXX";
		
		if (getCd_terzo_delegato() != null){
			perCessione = "Y";
			//cdTerzoDelegato = Integer.toString(getCd_terzo_delegato().intValue());
		}

		this.setChiave(getTi_pagamento() + "-" + perCessione + "-" + cdTerzoDelegato);	
	}
	return chiave;
}
public String getDs_estesa() {

	StringBuffer ds = new StringBuffer();
	
	if ( getNumero_conto() != null )
		ds.append( " Nr: " + getNumero_conto() );
	if ( getIntestazione() != null )	
      ds.append( " Intestazione: " + getIntestazione() );
   if ( getAbi() != null )   
		ds.append( " ABI: " + getAbi() );
	if ( getCab() != null )
		ds.append(" CAB: "  + getCab() );
	return new String(ds);
}
	/**
	 * Restituisce il <code>Dictionary</code> per la gestione della descrizione dei tipi banche.
	 *
	 * @return java.util.Dictionary
	 */

	public java.util.Dictionary getDs_lista_bancheKeys() {
		return it.cnr.contab.anagraf00.tabrif.bulk.Rif_modalita_pagamentoBulk.DS_LISTA_PAGAMENTI_KEYS;
	}
	/**
	 * Restituisce l'<code>TerzoBulk</code> a cui � associato l'oggetto banca.
	 *
	 * @return it.cnr.contab.anagraf00.core.bulk.TerzoBulk
	 *
	 * @see setTerzo
	 */

	public TerzoBulk getTerzo() {
		return terzo;
	}
	/**
	 * Restituisce il <code>Dictionary</code> per la gestione delle modalit� di pagamento.
	 *
	 * @return java.util.Dictionary
	 *
	 * @see <code>Rif_modalita_pagamentoBulk</code>.<code>TI_PAGAMENTO_KEYS</code>
	 */

	public java.util.Dictionary getTi_pagamentoKeys() {
		return it.cnr.contab.anagraf00.tabrif.bulk.Rif_modalita_pagamentoBulk.TI_PAGAMENTO_KEYS;
	}
public OggettoBulk initializeForInsert(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {

	setFlagsToFalse();
	setOrigine(BancaBulk.ORIGINE_ON_LINE);
	setFl_cc_cds(new Boolean(false));
	return super.initializeForInsert(bp,context);
}
public OggettoBulk initializeForSearch(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {

	setFl_cancellato(null);
	return super.initializeForSearch(bp,context);
}
/**
 * Insert the method's description here.
 * Creation date: (26/04/2002 15.16.36)
 * @return boolean
 */
public boolean isOrigineStipendi() {

	if (getOrigine() != null)
		return (getOrigine().compareTo(ORIGINE_STIPENDI)==0);
	
	return false;
}
/**
 * Restituisce TRUE se la chiave contiene il flag per cessione == "Y"
 *
 * @return boolean
 */

public boolean isPerCessione() {

	java.util.StringTokenizer st = new java.util.StringTokenizer(getChiave(), "-", false);
	int count = 0;
	boolean perCessione = false;
	
	while (st.hasMoreTokens()) {
		count++;
		String tok = st.nextToken();
		if (count == 2){
			perCessione = tok.equals("Y");
		}
	} 
	
	
	return perCessione;
}
/**
 * Insert the method's description here.
 * Creation date: (26/04/2002 15.16.36)
 * @return boolean
 */
public boolean isROAbiCab() {
	
	return isROBanca() || (abi_cab == null || abi_cab.getCrudStatus() == OggettoBulk.NORMAL);
	//return cd_ente_app == null || cd_ente_app.getCrudStatus() == OggettoBulk.NORMAL;

}
/**
 * Insert the method's description here.
 * Creation date: (26/04/2002 15.16.36)
 * @return boolean
 */
public boolean isROBanca() {
	
	if (isPerCessione() || isOrigineStipendi()|| getPg_banca()!=null)
		return true;
	if (getFl_cancellato()==null)
		return false;
	return getFl_cancellato().booleanValue();

}
/**
 * Insert the method's description here.
 * Creation date: (26/04/2002 15.16.36)
 * @return boolean
 */
public boolean isROCcd() {

	if(this.getTerzo()!=null && this.getTerzo().getUnita_organizzativa()==null)
		return true;
	else
		return isROBanca();
}
/**
 * Insert the method's description here.
 * Creation date: (26/04/2002 15.16.36)
 * @return boolean
 */
public boolean isROCin() {

	return isROBanca();
}
public void setAbi(java.lang.String abi) {
	this.getAbi_cab().setAbi(abi);
}
	/**
	 * Imposta l'<code>AbicabBulk</code> relativo all'oggetto banca.
	 *
	 * @return newAbi_cab Abi e cab da associare.
	 *
	 * @see getAbi_cab
	 */

	public void setAbi_cab(it.cnr.contab.anagraf00.tabrif.bulk.AbicabBulk newAbi_cab) {
		abi_cab = newAbi_cab;
	}
public void setCab(java.lang.String cab) {
	this.getAbi_cab().setCab(cab);
}
public void setCd_terzo(java.lang.Integer cd_terzo) {
	this.getTerzo().setCd_terzo(cd_terzo);
}
/**
 * Insert the method's description here.
 * Creation date: (05/11/2002 16.44.37)
 * @param newChiave java.lang.String
 */
public void setChiave(java.lang.String newChiave) {
	chiave = newChiave;
}
public void setFlagsToFalse() {

	this.setFl_cancellato(new Boolean(false));
}
	/**
	 * Imposta l'<code>TerzoBulk</code> a cui � associato l'oggetto banca.
	 *
	 * @param newTerzo Terzo da associare.
	 *
	 * @see getTerzo
	 */

	public void setTerzo(TerzoBulk newTerzo) {
		terzo = newTerzo;
	}
public void validate(OggettoBulk parent) throws ValidationException {
	if (Rif_modalita_pagamentoBulk.BANCARIO.equals(getTi_pagamento())) {
		// SE � BANCARIO
		if (!isROBanca()) validaIban();
		if (this.getNazione_iban()!=null && this.getNazione_iban().getCd_iso().equals("IT") ) {
			if (getAbi_cab() == null ||
				getAbi_cab().getAbi() == null ||
				getAbi_cab().getCab() == null ||
				getNumero_conto() == null)
				throw new ValidationException("Modalit� di pagamento: ABI, CAB, e Numero Conto sono obbligatori.");
		 }
	} else if(Rif_modalita_pagamentoBulk.POSTALE.equals(getTi_pagamento())) {
		// SE � POSTALE
		if (getNumero_conto() == null)
			throw new ValidationException("Modalit� di pagamento: Numero Conto � obbligatorio.");
	} else if(Rif_modalita_pagamentoBulk.ALTRO.equals(getTi_pagamento())) {
		// SE � ALTRO
		if (getIntestazione() == null)
			throw new ValidationException("Modalit� di pagamento: Intestazione � obbligatoria.");
	} else if(Rif_modalita_pagamentoBulk.QUIETANZA.equals(getTi_pagamento())) {
		// SE � QUIETANZA
		if (getQuietanza() == null)
			throw new ValidationException("Modalit� di pagamento: Quietanza � obbligatoria.");
	} else if(Rif_modalita_pagamentoBulk.IBAN.equals(getTi_pagamento())) {
		// SE � ALTRO CON IBAN OBBLIGATORIO
		if (getIntestazione() == null)
			throw new ValidationException("Modalit� di pagamento: Intestazione � obbligatoria.");
		if (this.getNazione_iban()!=null) 
			validaIban();
		else
			setCodice_iban(null);
	}
	if (!isROBanca() &&((Rif_modalita_pagamentoBulk.IBAN.equals(getTi_pagamento())||Rif_modalita_pagamentoBulk.BANCARIO.equals(getTi_pagamento())))){
		if(this.getNazione_iban()!=null && this.getNazione_iban().getFl_iban()){
			if ((this.getNazione_iban()==null)||(this.getNazione_iban()!=null && this.getNazione_iban().getCd_iso().compareTo(new String("IT"))!=0 ))
				if(this.getCodice_swift()==null) 
					throw new ValidationException("Il codice swift � obbligatorio.");
		  
			if (this.getCodice_swift().length()<8 || this.getCodice_swift().length()>11) 
				throw new ValidationException("Formato del codice bic non valido.");
		}
	}
			
}
public NazioneBulk getNazione_iban() {
	return nazione_iban;
}
public void setNazione_iban(NazioneBulk nazione_iban) {
	this.nazione_iban = nazione_iban;
}
public java.util.Collection getNazioniIban() {
	return nazioniIban;
}
public void setNazioniIban(java.util.Collection nazioniIban) {
	this.nazioniIban = nazioniIban;
}
public java.lang.String getCodice_iban_parte1() {
	return codice_iban_parte1;
}
public void setCodice_iban_parte1(java.lang.String codice_iban_parte1) {
	this.codice_iban_parte1 = codice_iban_parte1;
}
public java.lang.String getCodice_iban_parte2() {
	return codice_iban_parte2;
}
public void setCodice_iban_parte2(java.lang.String codice_iban_parte2) {
	this.codice_iban_parte2 = codice_iban_parte2;
}
public java.lang.String getCodice_iban_parte3() {
	return codice_iban_parte3;
}
public void setCodice_iban_parte3(java.lang.String codice_iban_parte3) {
	this.codice_iban_parte3 = codice_iban_parte3;
}
public java.lang.String getCodice_iban_parte4() {
	return codice_iban_parte4;
}
public void setCodice_iban_parte4(java.lang.String codice_iban_parte4) {
	this.codice_iban_parte4 = codice_iban_parte4;
}
public java.lang.String getCodice_iban_parte5() {
	return codice_iban_parte5;
}
public void setCodice_iban_parte5(java.lang.String codice_iban_parte5) {
	this.codice_iban_parte5 = codice_iban_parte5;
}
public java.lang.String getCodice_iban_parte6() {
	return codice_iban_parte6;
}
public void setCodice_iban_parte6(java.lang.String codice_iban_parte6) {
	this.codice_iban_parte6 = codice_iban_parte6;
}
public int getCodice_iban_parte1MaxLength(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte1MaxLength();
}
public int getCodice_iban_parte2MaxLength(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte2MaxLength();
}
public int getCodice_iban_parte3MaxLength(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte3MaxLength();
}
public int getCodice_iban_parte4MaxLength(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte4MaxLength();
}
public int getCodice_iban_parte5MaxLength(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte5MaxLength();
}
public int getCodice_iban_parte6MaxLength(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte6MaxLength();
}
public int getCodice_iban_parte1InputSize(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte1InputSize();
}
public int getCodice_iban_parte2InputSize(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte2InputSize();
}
public int getCodice_iban_parte3InputSize(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte3InputSize();
}
public int getCodice_iban_parte4InputSize(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte4InputSize();
}
public int getCodice_iban_parte5InputSize(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte5InputSize();
}
public int getCodice_iban_parte6InputSize(){
	if (getNazione_iban()==null) return 0;
	return getNazione_iban().getStruttura_iban_parte6InputSize();
}
public String getCodice_iban_calcolato(){
	if (this.getNazione_iban()==null) return null;
	String iban = this.getNazione_iban().getCd_iso();
	for (int i=0;i<this.getNazione_iban().getStrutturaIbanNrLivelli();i++){
		if (i==0) iban = iban+getCodice_iban_parte1();
		if (i==1) iban = iban+getCodice_iban_parte2();
		if (i==2) iban = iban+getCodice_iban_parte3();
		if (i==3) iban = iban+getCodice_iban_parte4();
		if (i==4) iban = iban+getCodice_iban_parte5();
		if (i==5) iban = iban+getCodice_iban_parte6();
	}
	return iban;
}
public void validaIban() throws ValidationException {
	if (this.getNazione_iban()==null) 
		throw new ValidationException("Codice IBAN obbligatorio.");
	else if (this.getNazione_iban()!=null) {
		allineaIbanDaContoIT();
		String codiceParteIban = null;
		for (int i=0;i<this.getNazione_iban().getStrutturaIbanNrLivelli();i++){
			String strutturaParteIban = this.getNazione_iban().getStrutturaIbanLivello(i+1);
			codiceParteIban=null;
			if (i==0) codiceParteIban=this.getCodice_iban_parte1();
			if (i==1) codiceParteIban=this.getCodice_iban_parte2();
			if (i==2) codiceParteIban=this.getCodice_iban_parte3();
			if (i==3) codiceParteIban=this.getCodice_iban_parte4();
			if (i==4) codiceParteIban=this.getCodice_iban_parte5();
			if (i==5) codiceParteIban=this.getCodice_iban_parte6();
	
			if (codiceParteIban==null)
				throw new ValidationException("Inserire tutti i campi del codice Iban.");

			if (codiceParteIban.length()!=strutturaParteIban.length())
				throw new ValidationException("La lunghezza della "+(i+2)+"^ parte del codice Iban deve essere di " + strutturaParteIban.length()+" caratteri.");
	
			for (int y=0;y<strutturaParteIban.length();y++){
				if (strutturaParteIban.charAt(y)!=NazioneBulk.IBAN_TIPO_ALFANUMERICO.charAt(0)){
					try{
						char data[] = {codiceParteIban.charAt(y)};
						int appo = Integer.parseInt(new String(data));
						if (strutturaParteIban.charAt(y)==NazioneBulk.IBAN_TIPO_CARATTERE.charAt(0))
							throw new ValidationException("Il "+(y+1)+"� carattere della "+(i+2)+"^ parte del codice Iban non deve essere un numero.");					
					} catch (ValidationException e) {
						throw e;
					} catch (Exception e) {
						if (strutturaParteIban.charAt(y)==NazioneBulk.IBAN_TIPO_NUMERICO.charAt(0))
							throw new ValidationException("Il "+(y+1)+"� carattere della "+(i+2)+"^ parte del codice Iban deve essere un numero.");
					}
				}
			}
		}
		setCodice_iban(getCodice_iban_calcolato());
		if (this.getTi_pagamento() == null || !this.getTi_pagamento().equals(Rif_modalita_pagamentoBulk.IBAN))
			allineaContoDaIbanIT();
	}
}
public void allineaIbanDaContoIT() throws ValidationException {
	if (this.getNazione_iban()!=null && this.getNazione_iban().getCd_iso().equals("IT") ) {
		if (getCodice_iban_parte2()==null && getCin()!=null)
			setCodice_iban_parte2(getCin());
		else if (getCodice_iban_parte2()!=null && getCin()!=null && !getCodice_iban_parte2().equals(getCin()))
			throw new ValidationException("Attenzione! La 3^ parte del codice Iban � diversa dal codice CIN indicato.");
				
        // Cod. ABI
		if (getCodice_iban_parte3()==null && getAbi()!=null)
			setCodice_iban_parte3(StrServ.lpad(getAbi().toString(),this.getNazione_iban().getStrutturaIbanLivello(3).length(),"0"));
		else if (getCodice_iban_parte3()!=null && getAbi()!=null && !getCodice_iban_parte3().equals(getAbi()))
			throw new ValidationException("Attenzione! La 4^ parte del codice Iban � diversa dal codice ABI indicato.");

        // Cod. CAB
		if (getCodice_iban_parte4()==null && getCab()!=null)
			setCodice_iban_parte4(StrServ.lpad(getCab().toString(),this.getNazione_iban().getStrutturaIbanLivello(4).length(),"0"));
		else if (getCodice_iban_parte4()!=null && getCab()!=null && !getCodice_iban_parte4().equals(getCab()))
			throw new ValidationException("Attenzione! La 5^ parte del codice Iban � diversa dal codice CAB indicato.");

        // Cod. C/C
		if (getCodice_iban_parte5()==null && getNumero_conto()!=null)
			setCodice_iban_parte5(StrServ.lpad(getNumero_conto().toString(),this.getNazione_iban().getStrutturaIbanLivello(5).length(),"0"));
		else if (getCodice_iban_parte5()!=null && getNumero_conto()!=null && !getNumero_conto().equals(getCodice_iban_parte5().substring(getCodice_iban_parte5().length()>=getNumero_conto().length()?getCodice_iban_parte5().length()-getNumero_conto().length():0)))
			throw new ValidationException("Attenzione! La 6^ parte del codice Iban � diversa dal Numero Conto indicato.");
	}
}
public void allineaContoDaIbanIT() throws ValidationException {
	if (this.getNazione_iban()!=null && this.getNazione_iban().getCd_iso().equals("IT") ) {
		if (getCodice_iban_parte2()!=null && getCin()==null)
			setCin(getCodice_iban_parte2());
		else if (getCodice_iban_parte2()!=null && getCin()!=null && !getCodice_iban_parte2().equals(getCin()))
			throw new ValidationException("Attenzione! La 3^ parte del codice Iban � diversa dal codice CIN indicato.");
				
        // Cod. ABI
		if (getCodice_iban_parte3()!=null && getAbi()==null) {
			if (getAbi_cab()==null) setAbi_cab(new AbicabBulk());
			setAbi(getCodice_iban_parte3());
		}
		else if (getCodice_iban_parte3()!=null && getAbi()!=null && !getCodice_iban_parte3().equals(getAbi()))
			throw new ValidationException("Attenzione! La 4^ parte del codice Iban � diversa dal codice ABI indicato.");

        // Cod. CAB
		if (getCodice_iban_parte4()!=null && getCab()==null) {
			if (getAbi_cab()==null) setAbi_cab(new AbicabBulk());
			setCab(getCodice_iban_parte4());
		}
		else if (getCodice_iban_parte4()!=null && getCab()!=null && !getCodice_iban_parte4().equals(getCab()))
			throw new ValidationException("Attenzione! La 5^ parte del codice Iban � diversa dal codice CAB indicato.");

        // Cod. C/C
		if (getCodice_iban_parte5()!=null && getNumero_conto()==null)
			setNumero_conto(getCodice_iban_parte5());
		else if (getCodice_iban_parte5()!=null && getNumero_conto()!=null && !getNumero_conto().equals(getCodice_iban_parte5().substring(getCodice_iban_parte5().length()>=getNumero_conto().length()?getCodice_iban_parte5().length()-getNumero_conto().length():0)))
			throw new ValidationException("Attenzione! La 6^ parte del codice Iban � diversa dal Numero Conto indicato.");
	}
}
public TerzoBulk getTerzo_delegato() {
	return terzo_delegato;
}
public void setTerzo_delegato(TerzoBulk terzo_delegato) {
	this.terzo_delegato = terzo_delegato;
}
public boolean isROterzo_delegato() {
	return terzo_delegato == null || terzo_delegato.getCrudStatus() == OggettoBulk.NORMAL;
}
public java.lang.Integer getCd_terzo_delegato() {
	it.cnr.contab.anagraf00.core.bulk.TerzoBulk terzo_delegato = this.getTerzo_delegato();
	if (terzo_delegato == null)
		return null;
	return terzo_delegato.getCd_terzo();
}
public void setCd_terzo_delegato(java.lang.Integer cd_terzo_delegato) {
	this.getTerzo_delegato().setCd_terzo(cd_terzo_delegato);
}
}
