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

package it.cnr.contab.docamm00.tabrif.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Bene_servizioHome extends BulkHome {
	public Bene_servizioHome(java.sql.Connection conn) {
		super(Bene_servizioBulk.class,conn);
	}
	public Bene_servizioHome(java.sql.Connection conn,PersistentCache persistentCache) {
		super(Bene_servizioBulk.class,conn,persistentCache);
	}
	@Override
	public SQLBuilder createSQLBuilder() {
		SQLBuilder sql=super.createSQLBuilder();
		sql.addClause(FindClause.AND, "fl_valido", SQLBuilder.EQUALS,"Y");
		return sql;
	}
}
