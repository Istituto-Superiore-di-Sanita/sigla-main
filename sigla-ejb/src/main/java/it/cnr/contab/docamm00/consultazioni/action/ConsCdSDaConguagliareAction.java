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

package it.cnr.contab.docamm00.consultazioni.action;

import java.rmi.RemoteException;
import java.util.List;

import it.cnr.contab.docamm00.consultazioni.bp.ConsCdSDaConguagliareBP;
import it.cnr.contab.docamm00.consultazioni.bp.ConsTerziDaConguagliareBP;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Forward;
import it.cnr.jada.util.action.ConsultazioniAction;
import it.cnr.jada.util.action.ConsultazioniBP;

public class ConsCdSDaConguagliareAction extends ConsultazioniAction {

	public Forward doEMail(ActionContext actioncontext) throws RemoteException {
		ConsCdSDaConguagliareBP bp = (ConsCdSDaConguagliareBP)actioncontext.getBusinessProcess();
		try{
			if (bp.getSelection() != null){
				bp.setSelection(actioncontext);
				List cds = bp.getSelectedElements(actioncontext);
				if ( cds == null )
					return (Forward)actioncontext.findDefaultForward();
			
				if (cds.isEmpty()) {
					bp.setMessage("Non è stata selezionata nessuna riga.");
					return actioncontext.findDefaultForward();
				}							
				bp.inviaEMail(actioncontext, cds);
				bp.setMessage("Invio E-Mail effettuato con successo.");
				bp.clearSelection(actioncontext);
			}	
		}catch(BusinessProcessException e){
			handleException(actioncontext, e);
		}
		return actioncontext.findDefaultForward();
	}
}
