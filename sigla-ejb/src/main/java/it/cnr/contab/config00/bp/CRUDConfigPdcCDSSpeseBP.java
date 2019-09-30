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

package it.cnr.contab.config00.bp;

import it.cnr.contab.config00.bulk.Parametri_cnrBulk;
import it.cnr.contab.config00.bulk.Parametri_enteBulk;
import it.cnr.contab.config00.pdcfin.bulk.EV_cds_spese_capitoloBulk;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.util.Utility;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;

public class CRUDConfigPdcCDSSpeseBP extends it.cnr.jada.util.action.SimpleCRUDBP {
	private static final long serialVersionUID = 1L;

	private boolean flNuovoPdg = false;
	private boolean flPrgPianoeco = false;

	/**
	 * Primo costruttore della classe <code>CRUDConfigPdcCDSSpeseBP</code>.
	 */
	public CRUDConfigPdcCDSSpeseBP() {
		super();
	}
	/**
	 * Secondo costruttore della classe <code>CRUDConfigPdcCDSSpeseBP</code>.
	 * @param String function
	 */
	public CRUDConfigPdcCDSSpeseBP(String function) {
		super(function);
	}
	
	@Override
	public void validate(ActionContext actioncontext) throws ValidationException {
		super.validate(actioncontext);
		EV_cds_spese_capitoloBulk bulk = (EV_cds_spese_capitoloBulk)getModel();
		if (!isFlNuovoPdg()) {
			if ( bulk==null || bulk.getElemento_padre() == null || OggettoBulk.isNullOrEmpty( bulk.getElemento_padre().getCd_elemento_voce() ))
				throw new ValidationException( "Inserire il codice titolo. " );
		}
	}
	
	public boolean isFlNuovoPdg() {
		return flNuovoPdg;
	}
	
	private void setFlNuovoPdg(boolean flNuovoPdg) {
		this.flNuovoPdg = flNuovoPdg;
	}
	
	protected void initialize(ActionContext actioncontext) throws BusinessProcessException {
		super.initialize(actioncontext);
		try {
			Parametri_cnrBulk parCnr = Utility.createParametriCnrComponentSession().getParametriCnr(actioncontext.getUserContext(), CNRUserContext.getEsercizio(actioncontext.getUserContext())); 
			setFlNuovoPdg(parCnr.getFl_nuovo_pdg().booleanValue());
			Parametri_enteBulk parEnte = Utility.createParametriEnteComponentSession().getParametriEnte(actioncontext.getUserContext());
			setFlPrgPianoeco(parEnte.getFl_prg_pianoeco().booleanValue());
		}
	    catch(Throwable throwable)
	    {
	        throw new BusinessProcessException(throwable);
	    }
	}
	public boolean isFlPrgPianoeco() {
		return flPrgPianoeco;
	}
	public void setFlPrgPianoeco(boolean flPrgPianoeco) {
		this.flPrgPianoeco = flPrgPianoeco;
	}
}
