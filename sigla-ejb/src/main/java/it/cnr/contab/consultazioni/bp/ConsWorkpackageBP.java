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
 * Created on Jan 19, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.cnr.contab.consultazioni.bp;

import it.cnr.contab.config00.bulk.Parametri_cnrBulk;
import it.cnr.contab.config00.latt.bulk.WorkpackageBulk;
import it.cnr.contab.pdg00.ejb.StampaSituazioneSinteticaGAEComponentSession;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.util.Utility;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;
import it.cnr.jada.util.Config;
import it.cnr.jada.util.RemoteIterator;
import it.cnr.jada.util.action.CondizioneComplessaBulk;
import it.cnr.jada.util.action.SearchProvider;
import it.cnr.jada.util.action.SelectionListener;
import it.cnr.jada.util.action.SelezionatoreListaBP;
import it.cnr.jada.util.ejb.EJBCommonServices;
import it.cnr.jada.util.jsp.Button;

import java.util.*;

/**
 * @author mincarnato
 * <p>
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ConsWorkpackageBP extends SelezionatoreListaBP implements SearchProvider {

    private String componentSessioneName;
    private Class bulkClass;
    private CompoundFindClause findclause;
    private CompoundFindClause baseclause;
    private int navPosition = 0;
    private boolean flNuovoPdg = false;

    private java.math.BigDecimal pg_stampa = null;
    private java.math.BigDecimal currentSequence = null;

    public ConsWorkpackageBP() {
        super();
    }

    public ConsWorkpackageBP(String function) {
        super(function);
    }

    protected void init(it.cnr.jada.action.Config config, it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
        try {
            Parametri_cnrBulk parCnr = Utility.createParametriCnrComponentSession().getParametriCnr(context.getUserContext(), CNRUserContext.getEsercizio(context.getUserContext()));
            setFlNuovoPdg(parCnr.getFl_nuovo_pdg().booleanValue());
            setMultiSelection(true);
            setPageSize(10);
            try {
                setBulkInfo(it.cnr.jada.bulk.BulkInfo.getBulkInfo(
                        Class.forName(config.getInitParameter("bulkClassName"))
                ));
                OggettoBulk model = (OggettoBulk) getBulkInfo().getBulkClass().newInstance();
                setModel(context, model);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                throw handleException(e);
            }
            setColumns(getBulkInfo().getColumnFieldPropertyDictionary());
            super.init(config, context);
            openIterator(context);
        } catch (Throwable e) {
            throw new BusinessProcessException(e);
        }
    }

    public Button[] createToolbar() {
        List<Button> toolbar = new ArrayList<Button>();
        toolbar.addAll(Arrays.asList(super.createToolbar()));
        if (!(getName().equalsIgnoreCase("StampaSituazioneSinteticaDispGAEBP") ||
                getName().equalsIgnoreCase("StampaSituazioneSinteticaRendGAEBP"))) {
            toolbar.add(new Button(Config.getHandler().getProperties(getClass()), "Toolbar.obbligazioni"));
        }
        return toolbar.toArray(new Button[toolbar.size()]);
    }


    public java.math.BigDecimal getCurrentSequence() {
        return currentSequence;
    }

    public void setCurrentSequence(java.math.BigDecimal newCurrentSequence) {
        currentSequence = newCurrentSequence;
    }

    public java.math.BigDecimal getPg_stampa() {
        return pg_stampa;
    }

    public void setPg_stampa(java.math.BigDecimal newPg_stampa) {
        pg_stampa = newPg_stampa;
    }

    public void resetIdReport(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
        setPg_stampa(null);
        setCurrentSequence(null);
    }

    public void selectedGae(it.cnr.jada.action.ActionContext context, java.util.List gae_selezionate)
            throws it.cnr.jada.action.BusinessProcessException {
        try {
            setPg_stampa(createComponentSession().getPgStampa(context.getUserContext()));
            createComponentSession().inserisciRecord(context.getUserContext(), pg_stampa, gae_selezionate);
        } catch (it.cnr.jada.comp.ComponentException e) {
            throw handleException(e);
        } catch (java.rmi.RemoteException e) {
            throw handleException(e);
        }
    }

    public boolean isFlNuovoPdg() {
        return flNuovoPdg;
    }

    private void setFlNuovoPdg(boolean flNuovoPdg) {
        this.flNuovoPdg = flNuovoPdg;
    }


    public void openIterator(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
        try {
            it.cnr.jada.util.RemoteIterator ri = createComponentSession().selezionaGae(
                    context.getUserContext(),
                    addBaseClause(context,  Optional.ofNullable(getCondizioneCorrente())
                            .map(CondizioneComplessaBulk::creaFindClause)
                            .filter(CompoundFindClause.class::isInstance)
                            .map(CompoundFindClause.class::cast)
                            .orElseGet(() -> new CompoundFindClause()))
            );
            this.setIterator(context, ri);
        } catch (Throwable e) {
            throw new BusinessProcessException(e);
        }
    }

    @Override
    public RemoteIterator search(ActionContext actioncontext, CompoundFindClause compoundfindclause, OggettoBulk oggettobulk) throws BusinessProcessException {
        it.cnr.jada.util.RemoteIterator ri;
        try {
            return createComponentSession().selezionaGae(actioncontext.getUserContext(),
                    addBaseClause(actioncontext, Optional.ofNullable(compoundfindclause)
                            .filter(CompoundFindClause.class::isInstance)
                            .map(CompoundFindClause.class::cast)
                            .orElseGet(() -> new CompoundFindClause()))
            );
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    private CompoundFindClause addBaseClause(ActionContext actioncontext, CompoundFindClause compoundFindClause) {
        compoundFindClause.addClause("AND", "esercizio_inizio", SQLBuilder.LESS_EQUALS, it.cnr.contab.utenze00.bp.CNRUserContext.getEsercizio(actioncontext.getUserContext()));
        compoundFindClause.addClause("AND", "esercizio_fine", SQLBuilder.GREATER_EQUALS, it.cnr.contab.utenze00.bp.CNRUserContext.getEsercizio(actioncontext.getUserContext()));
        compoundFindClause.addClause("AND", "ti_gestione", SQLBuilder.EQUALS, WorkpackageBulk.TI_GESTIONE_SPESE);
        return compoundFindClause;
    }


    public StampaSituazioneSinteticaGAEComponentSession createComponentSession() throws javax.ejb.EJBException, java.rmi.RemoteException, BusinessProcessException {
        return (StampaSituazioneSinteticaGAEComponentSession) createComponentSession("CNRPDG00_EJB_StampaSituazioneSinteticaGAEComponentSession", StampaSituazioneSinteticaGAEComponentSession.class);
    }

}
