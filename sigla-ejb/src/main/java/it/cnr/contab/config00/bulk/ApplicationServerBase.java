/*
 * Created by BulkGenerator 1.5 [30/07/2008]
 * Date 31/07/2008
 */
package it.cnr.contab.config00.bulk;
import it.cnr.jada.persistency.Keyed;
public class ApplicationServerBase extends ApplicationServerKey implements Keyed {
//    ATTIVO VARCHAR(1) NOT NULL
	private java.lang.Boolean attivo;
 
//    LOGIN VARCHAR(1) NOT NULL
	private java.lang.Boolean login;
 
//    HTTP_PORT VARCHAR(5)
	private java.lang.String http_port;
 
//    IP_ADDRESS VARCHAR(200)
	private java.lang.String ip_address;
 
	/**
	 * Created by BulkGenerator 1.5 [30/07/2008]
	 * Table name: APPLICATION_SERVER
	 **/
	public ApplicationServerBase() {
		super();
	}
	public ApplicationServerBase(java.lang.String hostname, java.lang.String session_id) {
		super(hostname, session_id);
	}
	/**
	 * Created by BulkGenerator 1.5 [30/07/2008]
	 * Restituisce il valore di: [attivo]
	 **/
	public java.lang.Boolean getAttivo() {
		return attivo;
	}
	/**
	 * Created by BulkGenerator 1.5 [30/07/2008]
	 * Setta il valore di: [attivo]
	 **/
	public void setAttivo(java.lang.Boolean attivo)  {
		this.attivo=attivo;
	}
	/**
	 * Created by BulkGenerator 1.5 [30/07/2008]
	 * Restituisce il valore di: [login]
	 **/
	public java.lang.Boolean getLogin() {
		return login;
	}
	/**
	 * Created by BulkGenerator 1.5 [30/07/2008]
	 * Setta il valore di: [login]
	 **/
	public void setLogin(java.lang.Boolean login)  {
		this.login=login;
	}
	/**
	 * Created by BulkGenerator 1.5 [30/07/2008]
	 * Restituisce il valore di: [http_port]
	 **/
	public java.lang.String getHttp_port() {
		return http_port;
	}
	/**
	 * Created by BulkGenerator 1.5 [30/07/2008]
	 * Setta il valore di: [http_port]
	 **/
	public void setHttp_port(java.lang.String http_port)  {
		this.http_port=http_port;
	}
	/**
	 * Created by BulkGenerator 1.5 [30/07/2008]
	 * Restituisce il valore di: [ip_address]
	 **/
	public java.lang.String getIp_address() {
		return ip_address;
	}
	/**
	 * Created by BulkGenerator 1.5 [30/07/2008]
	 * Setta il valore di: [ip_address]
	 **/
	public void setIp_address(java.lang.String ip_address)  {
		this.ip_address=ip_address;
	}
}