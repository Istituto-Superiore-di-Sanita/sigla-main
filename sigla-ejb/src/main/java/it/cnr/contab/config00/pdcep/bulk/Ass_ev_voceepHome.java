/*
 * Copyright (C) 2019  Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package it.cnr.contab.config00.pdcep.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

/**
 * Home che gestisce elementi voce associati a voci economico patrimoniali.
 */
public class Ass_ev_voceepHome extends BulkHome {
protected Ass_ev_voceepHome(Class clazz,java.sql.Connection connection) {
	super(clazz,connection);
}
protected Ass_ev_voceepHome(Class clazz,java.sql.Connection connection,PersistentCache persistentCache) {
	super(clazz,connection,persistentCache);
}
/**
 * <!-- @TODO: da completare -->
 * 
 *
 * @param conn	
 */
public Ass_ev_voceepHome(java.sql.Connection conn) {
	super(Ass_ev_voceepBulk.class,conn);
}
/**
 * <!-- @TODO: da completare -->
 * 
 *
 * @param conn	
 * @param persistentCache	
 */
public Ass_ev_voceepHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(Ass_ev_voceepBulk.class,conn,persistentCache);
}
}
