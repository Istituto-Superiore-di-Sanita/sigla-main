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

package it.cnr.contab.docamm00.comp;

import it.cnr.contab.docamm00.docs.bulk.Numerazione_doc_ammBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.ComponentException;

/**
 * Insert the type's description here.
 * Creation date: (28/02/2002 16.20.36)
 * @author: Roberto Fantino
 */
public interface INumerazioneTempDocAmmMgr {
/** 
  *  tutti i controlli superati.
  *    PreCondition:
  *      Il progressivo è stato generato senza errori.
  *    PostCondition:
  *      Viene consentita la registrazione del progressivo.
  *  validazione generazione consuntivo.
  *    PreCondition:
  *      Rilevata una condizione di errore.
  *    PostCondition:
  *      Negato il consenso alla registrazione del progressivo.
 */

public abstract java.lang.Long getNextTempPG(it.cnr.jada.UserContext param0,it.cnr.contab.docamm00.docs.bulk.Numerazione_doc_ammBulk param1) throws it.cnr.jada.comp.ComponentException;
}
