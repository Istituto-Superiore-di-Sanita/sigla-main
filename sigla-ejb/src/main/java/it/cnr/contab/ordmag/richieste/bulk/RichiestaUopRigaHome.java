/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 12/05/2017
 */
package it.cnr.contab.ordmag.richieste.bulk;
import java.sql.Connection;

import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.persistency.PersistentCache;
public class RichiestaUopRigaHome extends BulkHome {
	public RichiestaUopRigaHome(Connection conn) {
		super(RichiestaUopRigaBulk.class, conn);
	}
	public RichiestaUopRigaHome(Connection conn, PersistentCache persistentCache) {
		super(RichiestaUopRigaBulk.class, conn, persistentCache);
	}
}