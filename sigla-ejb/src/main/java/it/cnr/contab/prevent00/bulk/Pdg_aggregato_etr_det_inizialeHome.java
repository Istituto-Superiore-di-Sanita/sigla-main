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

package it.cnr.contab.prevent00.bulk;
/**
 * Gestisce i dati iniziali per le Entrate adattando ed implementando: {@link Pdg_aggregato_etr_detBulk } e  {@link Pdg_aggregato_det_iniziale } 
 * 		perchè si ottengano e si settino gli oggetti complessi.
 */
public class Pdg_aggregato_etr_det_inizialeHome extends Pdg_aggregato_etr_detHome {
public Pdg_aggregato_etr_det_inizialeHome(java.sql.Connection conn) {
	super(Pdg_aggregato_etr_det_inizialeBulk.class,conn);
}

public Pdg_aggregato_etr_det_inizialeHome(java.sql.Connection conn, it.cnr.jada.persistency.PersistentCache persistentCache) {
	super(Pdg_aggregato_etr_det_inizialeBulk.class,conn, persistentCache);
}

public it.cnr.jada.persistency.sql.SQLBuilder createSQLBuilder() {
	it.cnr.jada.persistency.sql.SQLBuilder sql = super.createSQLBuilder();
	sql.addClause("AND","ti_aggregato",sql.EQUALS,"I");
	return sql;
}
}