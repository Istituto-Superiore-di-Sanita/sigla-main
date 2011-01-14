package it.cnr.contab.compensi00.tabrif.bulk;

import it.cnr.contab.compensi00.docs.bulk.CompensoBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

import java.sql.*;

public class Detrazioni_familiariHome extends BulkHome {
public Detrazioni_familiariHome(java.sql.Connection conn) {
	super(Detrazioni_familiariBulk.class,conn);
}
public Detrazioni_familiariHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(Detrazioni_familiariBulk.class,conn,persistentCache);
}
/**
 * Metodo che verifica la validit� delle detrazioni
 * Creation date: (27/11/2001 13.12.51)
 *
 * @param detraz	Detrazioni_familiariBulk
 * @param uc  it.cnr.jada.UserContext
 *
 * @return boolean
 *
 * @exception PersistencyException
 */

public boolean checkValidita(UserContext userContext, Detrazioni_familiariBulk detraz)  throws PersistencyException{

	try{
		boolean accepted = false;
	
		Timestamp dataMax = (Timestamp)findMax(detraz, "dt_inizio_validita", null);
			
		if (dataMax == null) // non ci sono records in tabella: l'inserzione pu� avvenire   
			  accepted = true;
		else{
			if(detraz.getDt_inizio_validita().after(dataMax)){
				    
				Detrazioni_familiariBulk oldDetraz = (Detrazioni_familiariBulk)findAndLock(new Detrazioni_familiariBulk(dataMax,detraz.getIm_inferiore(),detraz.getNumero(),detraz.getTi_persona()));
				oldDetraz.setDt_fine_validita(CompensoBulk.decrementaData(detraz.getDt_inizio_validita()));
				update(oldDetraz,userContext);
			    accepted = true;
			}else
				accepted = false;
		}

		detraz.setDt_fine_validita(it.cnr.contab.config00.esercizio.bulk.EsercizioHome.DATA_INFINITO);
		return accepted;

	}catch(OutdatedResourceException ex){
	    throw new PersistencyException(ex);
	}catch(BusyResourceException ex){
	    throw new PersistencyException(ex);
	}
}
}
