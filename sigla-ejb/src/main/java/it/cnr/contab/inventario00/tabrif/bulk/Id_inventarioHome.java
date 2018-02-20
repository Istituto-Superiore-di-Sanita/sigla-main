package it.cnr.contab.inventario00.tabrif.bulk;

import it.cnr.contab.inventario01.bulk.Inventario_beni_apgBulk;
import it.cnr.jada.bulk.*;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Id_inventarioHome extends BulkHome {
protected Id_inventarioHome(Class clazz,java.sql.Connection connection) {
	super(clazz,connection);
}
protected Id_inventarioHome(Class clazz,java.sql.Connection connection,PersistentCache persistentCache) {
	super(clazz,connection,persistentCache);
}
public Id_inventarioHome(java.sql.Connection conn) {
	super(Id_inventarioBulk.class,conn);
}
public Id_inventarioHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(Id_inventarioBulk.class,conn,persistentCache);
}
public void assegnaProgressivo(Id_inventarioBulk id_inventario) throws PersistencyException,OutdatedResourceException,BusyResourceException {
	Long x=new Long(0);
	try{
	  x=(Long) (findAndLockMax( id_inventario, "pg_inventario",new Integer(0)))+1;
	} catch(it.cnr.jada.bulk.BusyResourceException e) {
		throw new PersistencyException(e);
	} 
	id_inventario.setPg_inventario(new Long(x.intValue()));
}
//^^@@
/**
  *
  *  Carica il consegnatario relativo all'Inventario "inv"
  *		Ritorna null se non è definita un'associazione
  *
  *  
 */
//^^@@

public it.cnr.contab.anagraf00.core.bulk.TerzoBulk findConsegnatarioFor(Id_inventarioBulk inv) throws PersistencyException, IntrospectionException{

	Inventario_consegnatarioHome invCHome= (Inventario_consegnatarioHome)getHomeCache().getHome(Inventario_consegnatarioBulk.class);
	Inventario_consegnatarioBulk invC = invCHome.findInventarioConsegnatarioFor(inv);
	if ( invC == null )
		return null;
	return invC.getConsegnatario();
}
//^^@@
/**
  *
  *  Carica il delegato relativo all'Inventario "inv"
  *		Ritorna null se non è definita un'associazione
  *
  *  
 */
//^^@@

public it.cnr.contab.anagraf00.core.bulk.TerzoBulk findDelegatoFor(Id_inventarioBulk inv) throws PersistencyException, IntrospectionException{

	Inventario_consegnatarioHome invCHome= (Inventario_consegnatarioHome)getHomeCache().getHome(Inventario_consegnatarioBulk.class);
	Inventario_consegnatarioBulk invC = invCHome.findInventarioConsegnatarioFor(inv);
	if ( invC == null )
		return null;
	return invC.getDelegato();
}
public Id_inventarioBulk findInventarioFor(it.cnr.jada.UserContext aUC, boolean resp) throws PersistencyException, IntrospectionException{

	// Trova l'inventario associato alla U.O. di scrivania
	String cdCds = it.cnr.contab.utenze00.bp.CNRUserContext.getCd_cds(aUC);
	String cdUo = it.cnr.contab.utenze00.bp.CNRUserContext.getCd_unita_organizzativa(aUC);

	return findInventarioFor(aUC,cdCds, cdUo, resp);
}
public Id_inventarioBulk findInventarioFor(it.cnr.jada.UserContext userContext,String cdCds, String cdUO, boolean resp) throws PersistencyException, IntrospectionException{

	Ass_inventario_uoHome assInvUoHome = (Ass_inventario_uoHome)getHomeCache().getHome(Ass_inventario_uoBulk.class);
	Ass_inventario_uoBulk assInvUo = assInvUoHome.findAssInvUoFor(userContext,cdCds,cdUO, resp);
	if ( assInvUo == null )
		return null;
	return assInvUo.getInventario();
}
public Id_inventarioBulk findInventarioRespFor(it.cnr.jada.UserContext aUC) throws PersistencyException, IntrospectionException{

	return findInventarioFor(aUC, true);
}
public it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk findUoRespFor(it.cnr.jada.UserContext userContext,Id_inventarioBulk inv) throws PersistencyException, IntrospectionException{

	Ass_inventario_uoHome assInvUoHome = (Ass_inventario_uoHome)getHomeCache().getHome(Ass_inventario_uoBulk.class);
	Ass_inventario_uoBulk assInvUo = assInvUoHome.findAssInvUoRespFor(userContext,inv);
	if ( assInvUo == null )
		return null;
	return assInvUo.getUnita_organizzativa();
}
/**
 * Insert the method's description here.
 * Creation date: (11/12/2001 15.15.15)
 * @return boolean
 * @throws ApplicationException 
 */
public boolean isAperto(Id_inventarioBulk inv, Integer esercizio) throws PersistencyException, IntrospectionException, ApplicationException{

	Inventario_ap_chHome invApChHome = (Inventario_ap_chHome)getHomeCache().getHome(Inventario_ap_chBulk.class);
	return invApChHome.isAperto(inv,esercizio);
}
/**
 * Insert the method's description here.
 * Creation date: (11/12/2001 15.15.15)
 * @return boolean
 * @throws ApplicationException 
 */
public boolean isChiuso(Id_inventarioBulk inv, Integer esercizio) throws PersistencyException, IntrospectionException, ApplicationException{

	Inventario_ap_chHome invApChHome = (Inventario_ap_chHome)getHomeCache().getHome(Inventario_ap_chBulk.class);
	return invApChHome.isChiuso(inv,esercizio);
}
}
