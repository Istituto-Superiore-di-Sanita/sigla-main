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

package it.cnr.contab.config00.ejb;

import it.cnr.jada.UserContext;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.bulk.OggettoBulk;

import javax.ejb.Remote;
@Remote
public interface LockObjectSession extends it.cnr.jada.ejb.CRUDComponentSession{

	public OggettoBulk riempiListaUtenti(UserContext userContext, OggettoBulk model) throws it.cnr.jada.comp.ComponentException;
	public OggettoBulk riempiListaOggetti(UserContext userContext, OggettoBulk model) throws it.cnr.jada.comp.ComponentException;
	public void terminaSessioni(UserContext userContext, BulkList utentiSelezionati) throws it.cnr.jada.comp.ComponentException;

}
