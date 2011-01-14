/*
* Created by Generator 1.0
* Date 29/08/2005
*/
package it.cnr.contab.config00.pdcfin.cla.bulk;
import it.cnr.contab.prevent00.bulk.Pdg_piano_ripartoBulk;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.IntrospectionException;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.PersistentCache;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.PersistentHome;
import it.cnr.jada.persistency.sql.SQLBuilder;
public class Classificazione_vociHome extends BulkHome {
	public final static int LIVELLO_PRIMO = 1 ;
	public final static int LIVELLO_SECONDO = 2 ;
	public final static int LIVELLO_TERZO = 3 ;
	public final static int LIVELLO_QUARTO = 4 ;
	public final static int LIVELLO_QUINTO = 5 ;
	public final static int LIVELLO_SESTO = 6 ;
	public final static int LIVELLO_SETTIMO = 7;
	public final static int LIVELLO_MAX = LIVELLO_SETTIMO ;
	public final static int LIVELLO_MIN = LIVELLO_PRIMO ;

	protected Classificazione_vociHome(Class clazz,java.sql.Connection connection) {
		super(clazz,connection);
	}
	protected Classificazione_vociHome(Class clazz,java.sql.Connection connection,PersistentCache persistentCache) {
		super(clazz,connection,persistentCache);
	}

	public Classificazione_vociHome(java.sql.Connection conn) {
		super(Classificazione_vociBulk.class, conn);
	}
	public Classificazione_vociHome(java.sql.Connection conn, PersistentCache persistentCache) {
		super(Classificazione_vociBulk.class, conn, persistentCache);
	}
	public SQLBuilder selectByClause(UserContext usercontext, CompoundFindClause compoundfindclause)
		throws PersistencyException
	{
		SQLBuilder sql = super.selectByClause(usercontext, compoundfindclause);
		sql.addSQLClause("AND","esercizio",SQLBuilder.EQUALS,CNRUserContext.getEsercizio(usercontext));
		return sql;
	}	

	/**
	 * Inizializza il modello per l'inserimento impostando il progressivo ottenuto come max progressivo + 1 o 1
	 * nel caso non ci siano classificazioni sul DB
	 *
	 * @param cla classificazione da inserire
	 */
	public void initializePrimaryKeyForInsert(it.cnr.jada.UserContext userContext,OggettoBulk cla) throws PersistencyException {
		try {
			((Classificazione_vociBulk)cla).setId_classificazione(
				new Integer(
					((Integer)findAndLockMax( cla, "id_classificazione", new Integer(0) )).intValue()+1
				)
			);
		} catch(it.cnr.jada.bulk.BusyResourceException e) {
			throw new PersistencyException(e);
		}
	}
	/**
	 * Recupera solo le classificazioni associate direttamente a quella in uso.
	 *
	 * @param testata La classificazione in uso.
	 *
	 * @return java.util.Collection Collezione di oggetti <code>OggettoBulk</code>
	 */
	public java.util.Collection findClassVociAssociate(OggettoBulk testata) throws IntrospectionException, PersistencyException {
		return findClassVociAssociate(testata, new CompoundFindClause());
	}	
	/**
	 * Recupera solo le classificazioni associate direttamente a quella in uso.
	 *
	 * @param testata La classificazione in uso.
	 *
	 * @return java.util.Collection Collezione di oggetti <code>OggettoBulk</code>
	 */
	public java.util.Collection findClassVociAssociate(OggettoBulk testata, CompoundFindClause compoundfindclause) throws IntrospectionException, PersistencyException {
		PersistentHome dettHome;
		if (testata instanceof Classificazione_voci_etr_liv7Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_etr_liv7Bulk.class);
		else if (testata instanceof Classificazione_voci_etr_liv6Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_etr_liv7Bulk.class);
		else if (testata instanceof Classificazione_voci_etr_liv5Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_etr_liv6Bulk.class);
		else if (testata instanceof Classificazione_voci_etr_liv4Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_etr_liv5Bulk.class);
		else if (testata instanceof Classificazione_voci_etr_liv3Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_etr_liv4Bulk.class);
		else if (testata instanceof Classificazione_voci_etr_liv2Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_etr_liv3Bulk.class);
		else if (testata instanceof Classificazione_voci_etr_liv1Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_etr_liv2Bulk.class);
		else if (testata instanceof Classificazione_voci_spe_liv7Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_spe_liv7Bulk.class);
		else if (testata instanceof Classificazione_voci_spe_liv6Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_spe_liv7Bulk.class);
		else if (testata instanceof Classificazione_voci_spe_liv5Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_spe_liv6Bulk.class);
		else if (testata instanceof Classificazione_voci_spe_liv4Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_spe_liv5Bulk.class);
		else if (testata instanceof Classificazione_voci_spe_liv3Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_spe_liv4Bulk.class);
		else if (testata instanceof Classificazione_voci_spe_liv2Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_spe_liv3Bulk.class);
		else if (testata instanceof Classificazione_voci_spe_liv1Bulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_spe_liv2Bulk.class);
		else
			dettHome = getHomeCache().getHome(Classificazione_vociBulk.class);

		SQLBuilder sql = dettHome.createSQLBuilder();
		sql.addClause(compoundfindclause);
		sql.addSQLClause("AND","ID_CLASS_PADRE",sql.EQUALS,((Classificazione_vociBulk)testata).getId_classificazione());
		return dettHome.fetchAll(sql);
	}	
	/**
	 * Recupera tutte le classificazioni associate, direttamente o indirettamente tramite le classificazioni figlie, 
	 * a quella in uso.
	 *
	 * @param testata La classificazione in uso.
	 *
	 * @return java.util.Collection Collezione di oggetti <code>OggettoBulk</code>
	 */
	public java.util.Collection findAllClassVociAssociate(OggettoBulk testata) throws IntrospectionException, PersistencyException {
		PersistentHome dettHome = null; 
		if (testata instanceof Classificazione_voci_etrBulk)
			dettHome = getHomeCache().getHome(Classificazione_voci_etrBulk.class);
		else
			dettHome = getHomeCache().getHome(Classificazione_voci_speBulk.class);

		SQLBuilder sql = dettHome.createSQLBuilder();
		sql.addSQLClause("AND","CD_LIVELLO"+((Classificazione_vociBulk)testata).getLivelloMax().intValue(),sql.EQUALS,((Classificazione_vociBulk)testata).getCd_livello(((Classificazione_vociBulk)testata).getLivelloMax().intValue()));
		if ((((Classificazione_vociBulk)testata).getLivelloMax().intValue()+1)!=Classificazione_vociHome.LIVELLO_MAX)
			sql.addSQLClause("AND","CD_LIVELLO"+(((Classificazione_vociBulk)testata).getLivelloMax().intValue()+1),sql.ISNOTNULL, null);
		return dettHome.fetchAll(sql);
	}	
	/**
	 * Recupera i piani di riparto associati alla classificazione
	 *
	 * @param testata La classificazione in uso.
	 *
	 * @return java.util.Collection Collezione di oggetti <code>OggettoBulk</code>
	 */
	public java.util.Collection findPdgPianoRipartoSpese(OggettoBulk testata) throws IntrospectionException, PersistencyException {
		return findPdgPianoRipartoSpese(testata, null);
	}	
	/**
	 * Recupera i piani di riparto associati alla classificazione aggiungendo l'eventuale condizione 
	 * <compoundfindclause> richiesta.
	 *
	 * @param testata La classificazione in uso.
	 *
	 * @return java.util.Collection Collezione di oggetti <code>OggettoBulk</code>
	 */
	public java.util.Collection findPdgPianoRipartoSpese(OggettoBulk testata, CompoundFindClause compoundfindclause) throws IntrospectionException, PersistencyException {
		PersistentHome dettHome = getHomeCache().getHome(Pdg_piano_ripartoBulk.class);
		SQLBuilder sql = dettHome.createSQLBuilder();
		sql.addSQLClause("AND","ESERCIZIO",sql.EQUALS,((Classificazione_vociBulk)testata).getEsercizio());
		sql.addSQLClause("AND","ID_CLASSIFICAZIONE",sql.EQUALS,((Classificazione_vociBulk)testata).getId_classificazione());
		if (compoundfindclause!=null)
			sql.addClause(compoundfindclause);

		return dettHome.fetchAll(sql);
	}	
	/**
	 * Recupera i figli dell'oggetto bulk
	 * Creation date: (28/11/2001 10.57.42)
	 * @return it.cnr.jada.persistency.sql.SQLBuilder
	 * @param bulk it.cnr.contab.inventario00.tabrif.bulk.Classificazione_vociBulk
	 */
	public SQLBuilder selectChildrenFor(it.cnr.jada.UserContext aUC, Classificazione_vociBulk cla) throws it.cnr.jada.comp.ComponentException{

		SQLBuilder sql= createSQLBuilder();

		sql.addSQLClause("AND","ID_CLASS_PADRE",sql.EQUALS,cla.getId_classificazione());
		return sql;
	}
	public Classificazione_vociBulk getParent(Classificazione_vociBulk bulk) throws PersistencyException, IntrospectionException{
		if (bulk == null)
			return null;
    	
		PersistentHome dettHome = getHomeCache().getHome(bulk.getClass().getSuperclass());
		SQLBuilder sql = dettHome.createSQLBuilder();
		if (bulk.getId_class_padre()==null)
			sql.addSQLClause("AND","ID_CLASSIFICAZIONE",sql.ISNULL,null);
		else
			sql.addSQLClause("AND","ID_CLASSIFICAZIONE",sql.EQUALS,bulk.getId_class_padre());

		java.util.Collection coll = this.fetchAll(sql);
		if (coll.size() != 1)
			return null;
    
		return (Classificazione_vociBulk)coll.iterator().next();
	}
}