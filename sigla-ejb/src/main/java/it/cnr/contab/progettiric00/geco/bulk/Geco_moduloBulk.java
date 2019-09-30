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

/*
 * Created by Aurelio's BulkGenerator 1.0
 * Date 27/11/2006
 */
package it.cnr.contab.progettiric00.geco.bulk;
import it.cnr.contab.anagraf00.core.bulk.TerzoBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.progettiric00.core.bulk.ProgettoBulk;
import it.cnr.contab.progettiric00.core.bulk.Progetto_sipBulk;
import it.cnr.contab.progettiric00.tabrif.bulk.Tipo_progettoBulk;
import it.cnr.contab.util.Utility;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.util.action.CRUDBP;
public class Geco_moduloBulk extends Geco_moduloBase implements Geco_moduloIBulk{
	public Geco_moduloBulk() {
		super();
	}
	public Geco_moduloBulk(java.lang.Long id_mod, java.lang.Long esercizio, java.lang.String fase) {
		super(id_mod, esercizio, fase);
	}
	public void aggiornaProgettoSIP(Progetto_sipBulk progetto_sip){
		if (progetto_sip.getPg_progetto_padre()==null || !getId_comm().equals(new Long(progetto_sip.getPg_progetto_padre()))){
			progetto_sip.setProgettopadre(new Progetto_sipBulk(getEsercizio().intValue(),getId_comm().intValue(),getFase()));
			progetto_sip.setToBeUpdated();
		}
		if (!getCod_mod().equals(progetto_sip.getCd_progetto())){
			progetto_sip.setCd_progetto(getCod_mod());
			progetto_sip.setToBeUpdated();
		}
		if (!getDescr_mod().equals(progetto_sip.getDs_progetto())){
			progetto_sip.setDs_progetto(getDescr_mod());
			progetto_sip.setToBeUpdated();
		}
		if (getCod_tip() != null){
			if (getCod_tip().equals(new Long(1)) && (progetto_sip.getCd_tipo_progetto() == null || !progetto_sip.getCd_tipo_progetto().equals("PS"))){
				progetto_sip.setTipo(new Tipo_progettoBulk("PS"));
				progetto_sip.setToBeUpdated();
			}else if (getCod_tip().equals(new Long(2)) && (progetto_sip.getCd_tipo_progetto() == null ||!progetto_sip.getCd_tipo_progetto().equals("SC"))){
				progetto_sip.setTipo(new Tipo_progettoBulk("SC"));
				progetto_sip.setToBeUpdated();
			}else if (getCod_tip().equals(new Long(3)) && (progetto_sip.getCd_tipo_progetto() == null ||!progetto_sip.getCd_tipo_progetto().equals("MG"))){
				progetto_sip.setTipo(new Tipo_progettoBulk("MG"));
				progetto_sip.setToBeUpdated();
			}
		}
		if (getSede_princ_cdsuo() != null && !(getSede_princ_cdsuo().equals(progetto_sip.getCd_unita_organizzativa()))){
			progetto_sip.setUnita_organizzativa(new Unita_organizzativaBulk(getSede_princ_cdsuo()));
			progetto_sip.setToBeUpdated();
		}
		if (getCod_3rzo_gest() != null && progetto_sip.getCd_responsabile_terzo() != null && !getCod_3rzo_gest().equals(progetto_sip.getCd_responsabile_terzo().toString())){
			progetto_sip.setResponsabile(new TerzoBulk(new Integer(getCod_3rzo_gest())));
			progetto_sip.setToBeUpdated();
		}
		if (getData_inizio_attivita() != null && !getData_inizio_attivita().equals(progetto_sip.getDt_inizio())){
			progetto_sip.setDt_inizio(getData_inizio_attivita());
			progetto_sip.setToBeUpdated();
		}
		if (getEsito_negoz() != null){
			if (getEsito_negoz().equals(new Integer(2)) && !progetto_sip.getStato().equals(ProgettoBulk.TIPO_STATO_PROPOSTA)){
				progetto_sip.setStato(ProgettoBulk.TIPO_STATO_PROPOSTA);
				progetto_sip.setToBeUpdated();
			}else if (!getEsito_negoz().equals(new Integer(2)) && !progetto_sip.getStato().equals(ProgettoBulk.TIPO_STATO_APPROVATO)){
				progetto_sip.setStato(ProgettoBulk.TIPO_STATO_APPROVATO);
				progetto_sip.setToBeUpdated();
			}
		}
	}	
}