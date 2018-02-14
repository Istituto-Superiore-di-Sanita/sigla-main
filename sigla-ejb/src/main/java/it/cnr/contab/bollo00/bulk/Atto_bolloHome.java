package it.cnr.contab.bollo00.bulk;

import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.PersistentCache;

public class Atto_bolloHome extends BulkHome {
	private static final long serialVersionUID = 1L;

	public Atto_bolloHome(java.sql.Connection conn) {
		super(Atto_bolloBulk.class,conn);
	}
	public Atto_bolloHome(java.sql.Connection conn,PersistentCache persistentCache) {
		super(Atto_bolloBulk.class,conn,persistentCache);
	}

	@Override
	public void initializePrimaryKeyForInsert(UserContext  usercontext,OggettoBulk oggettobulk)throws PersistencyException, ComponentException {
		try {
			Atto_bolloBulk atto = (Atto_bolloBulk)oggettobulk;
			atto.setId(
					new Integer(((Integer)findAndLockMax( oggettobulk, "id", new Integer(0) )).intValue()+1));
			super.initializePrimaryKeyForInsert(usercontext, atto);
		} catch(it.cnr.jada.bulk.BusyResourceException e) {
			throw new PersistencyException(e);
		}
	}
}
