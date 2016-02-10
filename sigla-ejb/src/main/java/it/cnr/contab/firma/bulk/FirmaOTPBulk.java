package it.cnr.contab.firma.bulk;

import it.cnr.jada.bulk.OggettoBulk;

public class FirmaOTPBulk extends OggettoBulk {
	private static final long serialVersionUID = 1L;
	private String userName, password, otp;
	public FirmaOTPBulk() {
		super();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	public static String errorMessage(String messageException) {
	    if (messageException.contains("0001"))
	    	return "Errore generico nel processo di firma";
    	else if (messageException.contains("0002"))
    		return "Parametri non corretti per il tipo di trasporto indicato";
    	else if (messageException.contains("0003"))
    		return "Errore in fase di verifica delle credenziali";
    	else if (messageException.contains("0003"))
    		return "Errore in fase di verifica delle credenziali";
    	else if (messageException.contains("0004"))
    		return "Errore nel PIN";
    	else if (messageException.contains("0005"))
    		return "Tipo di trasporto non valido";
    	else if (messageException.contains("0006"))
    		return "Tipo di trasporto non autorizzato";
    	else if (messageException.contains("0007"))
    		return "Profilo Di firma PDF non valido";
    	else if (messageException.contains("0008"))
    		return "Impossibile completare l'operazione di marcatura temporale (es irraggiungibilit&agrave; del servizio, marche residue terminate, etc..)";
    	else if (messageException.contains("0009"))
    		return "Credenziali di delega non valide";
    	else if (messageException.contains("0010"))
    		return "Lo stato dell'utente non � valido (es. utente sospeso)";
	    return messageException;
		
	}
}
