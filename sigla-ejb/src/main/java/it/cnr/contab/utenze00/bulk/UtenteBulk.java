package it.cnr.contab.utenze00.bulk;

/**
 * Definisce un generico Utente del sistema Gestione Contabilita' CNR. Rappresenta pertanto sia un Amministratore 
 * di Utenze, sia un Utente Comune, sia un Template di Utenze.
 *	
 */
import java.rmi.RemoteException;

import javax.ejb.EJBException;


import it.cnr.contab.config00.sto.bulk.DipartimentoBulk;
import it.cnr.contab.utente00.ejb.RuoloComponentSession;
import it.cnr.contab.utenze00.bp.CRUDUtenzaAmministratoreBP;
import it.cnr.contab.utenze00.bp.CRUDUtenzaBP;
import it.cnr.jada.DetailedRuntimeException;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.*;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;
import it.cnr.jada.util.*;
import it.cnr.jada.util.upload.Base64Encoder;

public class UtenteBulk extends UtenteBase {
	public static final String UTENTE_COMUNE_KEY = "U"; 
	public static final String UTENTE_AMMINISTRATORE_KEY = "A";
	public static final String UTENTE_SUPERUTENTE_KEY = "S";
	public static final String DEFAULT_PSWD = "PASSWORD";
	private it.cnr.jada.bulk.BulkList	utente_indirizzi_mail   = new it.cnr.jada.bulk.BulkList();

	private UtenteBulk gestore;
	private DipartimentoBulk dipartimento = new DipartimentoBulk();
	private RuoloBulk ruolo_supervisore = new RuoloBulk();
	private String ldap_password;
	private String utente_multiplo;
	
public UtenteBulk() {
	super();
}
public UtenteBulk(java.lang.String cd_utente) {
	super(cd_utente);
}
public UtenteBulk getGestore() {
	return gestore;
}
/**
 * Aggiunge una nuova associazione utente-indirizzi-mail (Utente_indirizzi_mailBulk) alla lista definita per l'utente
 */

public int addToUtente_indirizzi_mail(Utente_indirizzi_mailBulk dett) {
	dett.setUtente(this);
	getUtente_indirizzi_mail().add(dett);
	return getUtente_indirizzi_mail().size()-1;
}
public Utente_indirizzi_mailBulk removeFromUtente_indirizzi_mail(int index) {
	Utente_indirizzi_mailBulk dett = (Utente_indirizzi_mailBulk)getUtente_indirizzi_mail().remove(index);
	return dett;
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'passwordInChiaro'
 *
 * @return Il valore della propriet� 'passwordInChiaro'
 */
public String getPasswordInChiaro() {
	return null;
}
/**
 * Metodo per l'inizializzazione di alcuni attributi (esercizio, fl_password_change) 
 */
public OggettoBulk initializeForInsert(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
	setFl_password_change( new Boolean( true ) );
	if (bp instanceof CRUDUtenzaBP) {
		setFl_autenticazione_ldap(((CRUDUtenzaBP)bp).isAutenticazioneLdap(context.getUserContext()));
	}
	if (bp instanceof CRUDUtenzaAmministratoreBP) {
		setFl_autenticazione_ldap(((CRUDUtenzaAmministratoreBP)bp).isAutenticazioneLdap(context.getUserContext()));
	}
	setFl_supervisore( new Boolean( false ) );
	setFl_altra_proc( new Boolean( false ) );
	return this;
}
/**
 * Verifica se l'utente e' un superutente
 * @return true se e' un superutente
 */

public boolean isSuperutente() {
	return UTENTE_SUPERUTENTE_KEY.equals( getTi_utente() );
}
/**
 * Verifica se l'utente e' un amministratore di utenze
 * @return true se e' un amministratore
 */
public boolean isUtenteAmministratore() {
	return UTENTE_AMMINISTRATORE_KEY.equals( getTi_utente() );
}
/**
 * Verifica se l'utente e' un utente comune
 * @return true se e' un utente comune
 */
public boolean isUtenteComune() {
	return UTENTE_COMUNE_KEY.equals( getTi_utente() );
}
/**
 * Verifica se l'utente e' un template di utenze
 * @return true se e' un template di utenze
 */
public boolean isUtenteTemplate() {
	return getFl_utente_templ() != null && getFl_utente_templ().booleanValue();
}
/**
 * <!-- @TODO: da completare -->
 * Imposta il valore della propriet� 'gestore'
 *
 * @param newGestore	Il valore da assegnare a 'gestore'
 */
public void setGestore(UtenteBulk newGestore) {
	gestore = newGestore;
}
/**
 * <!-- @TODO: da completare -->
 * Imposta il valore della propriet� 'passwordInChiaro'
 *
 * @param password	Il valore da assegnare a 'passwordInChiaro'
 */
public void setPasswordInChiaro(String password) {
	if (password == null || getCd_utente() == null) {
		setPassword(null );
	} else {
		byte[] buser = getCd_utente().getBytes();
		byte[] bpassword = password.getBytes();
		byte h = 0;
		for (int i = 0;i < bpassword.length;i++) {
			h = (byte)(bpassword[i] ^ h);
			for (int j = 0;j < buser.length;j++)
				bpassword[i] ^= buser[j] ^ h;
		}
		setPassword( com.ibm.util.Base64.toString(bpassword));
	}
}
/**
 * Esegue la validazione formale dei campi di input
 */

public void validate() throws ValidationException 
{
	String cd_utente = getCd_utente();
	if ( cd_utente == null || cd_utente.length() == 0)
		throw new ValidationException( "Il campo CODICE UTENTE � obbligatorio." );
	for (int i = 0;i < cd_utente.length();i++)
		if (!Character.isLetterOrDigit(cd_utente.charAt(i)))
			throw new ValidationException( "Il codice utente pu� essere composto solo da cifre o lettere e non pu� contenere spazi." );
	if ( getDt_inizio_validita() == null )
		throw new ValidationException( "Il campo DATA INIZIO VALIDITA' � obbligatorio." );	
	if ( getDt_fine_validita() != null && getDt_fine_validita().before(getDt_inizio_validita()))
		throw new ValidationException( "La data di fine validit� deve essere posteriore alla data di inizio validit�." );	
	if ( getNome() == null || getNome().length() == 0)
		throw new ValidationException( "Il campo NOME � obbligatorio." );
	if ( getCognome() == null || getCognome().length() == 0)
		throw new ValidationException( "Il campo COGNOME � obbligatorio." );
}
public static boolean isAbilitatoApprovazioneBilancio(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isAbilitatoApprovazioneBilancio(param0);
}
public static boolean isCapoCommessa(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isCapoCommessa(param0);
}
public static boolean isAmministratoreInventario(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isAmministratoreInventario(param0);
}
public static boolean isInventarioUfficiale(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isInventarioUfficiale(param0);
}
public static boolean isAbilitatoECF(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isAbilitatoECF(param0);
}
public static RuoloComponentSession getRuoloComponentSession() throws javax.ejb.EJBException, java.rmi.RemoteException {
	return (RuoloComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRUTENZE00_EJB_RuoloComponentSession",RuoloComponentSession.class);
}
public it.cnr.jada.bulk.BulkList getUtente_indirizzi_mail() {
	return utente_indirizzi_mail;
}
public void setUtente_indirizzi_mail(it.cnr.jada.bulk.BulkList utente_indirizzi_mail) {
	this.utente_indirizzi_mail = utente_indirizzi_mail;
}
public DipartimentoBulk getDipartimento() {
	return dipartimento;
}
public void setDipartimento(DipartimentoBulk dipartimento) {
	this.dipartimento = dipartimento;
}
public String getCd_dipartimento() {
	return getDipartimento().getCd_dipartimento();
}
public void setCd_dipartimento(String cd_dipartimento) {
	getDipartimento().setCd_dipartimento(cd_dipartimento);
}
public static boolean isGestoreIstatSiope(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isGestoreIstatSiope(param0);
}
public boolean isAutenticazioneLdap() {
	return getFl_autenticazione_ldap() != null && getFl_autenticazione_ldap().booleanValue();
}
public String getLdap_password() {
	return ldap_password;
}
public void setLdap_password(String ldap_password) {
	this.ldap_password = ldap_password;
}
public boolean isAutenticatoLdap() {
	return isAutenticazioneLdap() && (getCd_utente_uid() != null);
}
public boolean isSupervisore() {
	return getFl_supervisore() != null && getFl_supervisore().booleanValue();
}
public RuoloBulk getRuolo_supervisore() {
	return ruolo_supervisore;
}
public void setRuolo_supervisore(RuoloBulk ruolo) {
	this.ruolo_supervisore = ruolo;
}
public java.lang.String getCd_ruolo_supervisore() {
	it.cnr.contab.utenze00.bulk.RuoloBulk ruolo= this.getRuolo_supervisore();
	if (ruolo == null)
		return null;
	return ruolo.getCd_ruolo();
}
public void setCd_ruolo_supervisore(java.lang.String cd_ruolo_supervisore) {
	this.getRuolo_supervisore().setCd_ruolo(cd_ruolo_supervisore);
}
public boolean isAltraProc() {
	return getFl_altra_proc() != null && getFl_altra_proc().booleanValue();
}
public static boolean isAbilitatoF24EP(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isAbilitatoF24EP(param0);
}
public boolean isROLdap () {
	return isAutenticazioneLdap()  || getCrudStatus() == OggettoBulk.NORMAL ;
}
public static boolean isAbilitatoModificaModPag(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isAbilitatoModificaModPag(param0);
}
public String getUtente_multiplo() {
	return utente_multiplo;
}
public void setUtente_multiplo(String utente_multiplo) {
	this.utente_multiplo = utente_multiplo;
}

public static boolean isAbilitatoPubblicazioneSito(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isAbilitatoPubblicazioneSito(param0);
}
public static boolean isAbilitatoFunzioniIncarichi(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isAbilitatoFunzioniIncarichi(param0);
}
public static boolean isSuperUtenteFunzioniIncarichi(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isSuperUtenteFunzioniIncarichi(param0);
}
public static boolean isAbilitatoSospensioneCori(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isAbilitatoSospensioneCori(param0);
}
public static boolean isAbilitatoModificaDescVariazioni(it.cnr.jada.UserContext param0) throws ComponentException, RemoteException{
	return getRuoloComponentSession().isAbilitatoModificaDescVariazioni(param0);
}
}
