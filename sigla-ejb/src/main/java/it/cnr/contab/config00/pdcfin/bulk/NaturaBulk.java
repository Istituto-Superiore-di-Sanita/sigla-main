package it.cnr.contab.config00.pdcfin.bulk;

import java.util.Dictionary;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class NaturaBulk extends NaturaBase {
	public static final String TIPO_NATURA_FONTI_INTERNE ="FIN";
	public static final String TIPO_NATURA_FONTI_ESTERNE ="FES";
	
	public final static Dictionary tipo_naturaKeys;
	static {
	tipo_naturaKeys = new it.cnr.jada.util.OrderedHashtable();
	tipo_naturaKeys.put(TIPO_NATURA_FONTI_INTERNE,"Fonti Interne");
	tipo_naturaKeys.put(TIPO_NATURA_FONTI_ESTERNE,"Fonti Esterne");
};

public NaturaBulk() {
	super();
}
public NaturaBulk(java.lang.String cd_natura) {
	super(cd_natura);
}
	public boolean equals(Object obj) {
	 if(obj instanceof NaturaBulk) {
	  NaturaBulk aFB = (NaturaBulk)obj;
	  if(( getCd_natura() == null) && (aFB.getCd_natura() == null)) return true;
	  if(   (( getCd_natura() != null) && (aFB.getCd_natura() == null))
		 || (( getCd_natura() == null) && (aFB.getCd_natura() != null))
		) return false;
	  
	  return ((NaturaBulk)obj).getCd_natura().equals( getCd_natura() );
	 }
	 return super.equals(obj);
	}
/**
 * Restituisce il valore della propriet� 'cd_ds_natura'
 *
 * @return Il valore della propriet� 'cd_ds_natura'
 */
public String getCd_ds_natura() {
	return getCd_natura() + " - " + getDs_natura();
}
}
