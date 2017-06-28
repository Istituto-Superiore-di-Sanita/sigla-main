package it.cnr.contab.config00.bulk;

import it.cnr.jada.persistency.Keyed;

/**
 * @author aimprota
 *
 */
public class Parametri_enteBase extends Parametri_enteKey implements Keyed{

	// TIPO_DB CHAR(1)
	private java.lang.String tipo_db;
	
	// DESCRIZIONE VARCHAR(2000)
	private java.lang.String descrizione;
	
	// ATTIVO
	private java.lang.Boolean attivo;
	
	// CANCELLA_STAMPE
	private java.lang.Integer cancella_stampe;
	
	// BOX_COMUNICAZIONI
	private java.lang.String box_comunicazioni;
	
	// BOX_SCADENZE
	private java.lang.String box_scadenze;	

	// FL_AUTENTICAZIONE_LDAP CHAR(1)
	private java.lang.Boolean fl_autenticazione_ldap;

	// FL_INFORMIX CHAR(1)
	private java.lang.Boolean fl_informix;

	// FL_GAE_ES CHAR(1)
	private java.lang.Boolean fl_gae_es;

	// FL_PRG_PIANOECO CHAR(1)
	private java.lang.Boolean fl_prg_pianoeco;

	private java.lang.String ldap_user;	

	private java.lang.String ldap_password;	

	private java.lang.String ldap_base_dn;	

	private java.lang.String ldap_app_name;	
	
	private java.sql.Timestamp dt_ldap_migrazione;

	private java.lang.String ldap_link_cambio_password;	

	/**
	 * 
	 */
	public Parametri_enteBase()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Parametri_enteBase(Integer id)
	{
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return
	 */
	public java.lang.String getDescrizione()
	{
		return descrizione;
	}

	/**
	 * @param string
	 */
	public void setDescrizione(java.lang.String val)
	{
		descrizione = val;
	}

	/**
	 * @return
	 */
	public java.lang.Boolean getAttivo()
	{
		return attivo;
	}

	/**
	 * @param string
	 */
	public void setAttivo(java.lang.Boolean val)
	{
		attivo = val;
	}

	/**
	 * @return
	 */
	public java.lang.String getBox_comunicazioni() {
		return box_comunicazioni;
	}

	/**
	 * @return
	 */
	public java.lang.String getBox_scadenze() {
		return box_scadenze;
	}

	/**
	 * @return
	 */
	public java.lang.Integer getCancella_stampe() {
		return cancella_stampe;
	}

	/**
	 * @param string
	 */
	public void setBox_comunicazioni(java.lang.String newBox_comunicazioni) {
		box_comunicazioni = newBox_comunicazioni;
	}

	/**
	 * @param string
	 */
	public void setBox_scadenze(java.lang.String newBox_scadenze) {
		box_scadenze = newBox_scadenze;
	}

	/**
	 * @param integer
	 */
	public void setCancella_stampe(java.lang.Integer newCancella_stampe) {
		cancella_stampe = newCancella_stampe;
	}

	/**
	 * @return
	 */
	public java.lang.String getTipo_db() {
		return tipo_db;
	}

	/**
	 * @param string
	 */
	public void setTipo_db(java.lang.String string) {
		tipo_db = string;
	}

	public java.lang.String getLdap_app_name() {
		return ldap_app_name;
	}

	public void setLdap_app_name(java.lang.String ldap_app_name) {
		this.ldap_app_name = ldap_app_name;
	}

	public java.lang.String getLdap_base_dn() {
		return ldap_base_dn;
	}

	public void setLdap_base_dn(java.lang.String ldap_base_dn) {
		this.ldap_base_dn = ldap_base_dn;
	}

	public java.lang.String getLdap_password() {
		return ldap_password;
	}

	public void setLdap_password(java.lang.String ldap_password) {
		this.ldap_password = ldap_password;
	}

	public java.lang.String getLdap_user() {
		return ldap_user;
	}

	public void setLdap_user(java.lang.String ldap_user) {
		this.ldap_user = ldap_user;
	}

	public java.sql.Timestamp getDt_ldap_migrazione() {
		return dt_ldap_migrazione;
	}

	public void setDt_ldap_migrazione(java.sql.Timestamp dt_ldap_migrazione) {
		this.dt_ldap_migrazione = dt_ldap_migrazione;
	}

	public java.lang.Boolean getFl_autenticazione_ldap() {
		return fl_autenticazione_ldap;
	}

	public void setFl_autenticazione_ldap(java.lang.Boolean fl_autenticazione_ldap) {
		this.fl_autenticazione_ldap = fl_autenticazione_ldap;
	}

	public java.lang.String getLdap_link_cambio_password() {
		return ldap_link_cambio_password;
	}

	public void setLdap_link_cambio_password(
			java.lang.String ldap_link_cambio_password) {
		this.ldap_link_cambio_password = ldap_link_cambio_password;
	}
	
	public java.lang.Boolean getFl_informix() {
		return fl_informix;
	}

	public void setFl_informix(java.lang.Boolean fl_informix) {
		this.fl_informix = fl_informix;
	}
	
	public java.lang.Boolean getFl_gae_es() {
		return fl_gae_es;
	}
	
	public void setFl_gae_es(java.lang.Boolean fl_gae_es) {
		this.fl_gae_es = fl_gae_es;
	}
	
	public java.lang.Boolean getFl_prg_pianoeco() {
		return fl_prg_pianoeco;
	}
	
	public void setFl_prg_pianoeco(java.lang.Boolean fl_prg_pianoeco) {
		this.fl_prg_pianoeco = fl_prg_pianoeco;
	}
}