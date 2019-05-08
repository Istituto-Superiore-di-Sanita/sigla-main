package it.cnr.contab.progettiric00.bp;

import it.cnr.contab.config00.bulk.Configurazione_cnrBulk;
import it.cnr.contab.config00.bulk.Parametri_cnrBulk;
import it.cnr.contab.config00.bulk.Parametri_enteBulk;
import it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.doccont00.core.bulk.ObbligazioneBulk;
import it.cnr.contab.pdg00.bulk.Pdg_variazioneBulk;
import it.cnr.contab.prevent01.bulk.Pdg_esercizioBulk;
import it.cnr.contab.progettiric00.core.bulk.*;
import it.cnr.contab.progettiric00.ejb.ProgettoRicercaComponentSession;
import it.cnr.contab.progettiric00.tabrif.bulk.Voce_piano_economico_prgBulk;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.util.Utility;
import it.cnr.contab.util00.bp.AllegatiCRUDBP;
import it.cnr.contab.varstanz00.bulk.Var_stanz_resBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Config;
import it.cnr.jada.action.HttpActionContext;
import it.cnr.jada.bulk.BulkInfo;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.ApplicationRuntimeException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.util.DateUtils;
import it.cnr.jada.util.RemoteBulkTree;
import it.cnr.jada.util.RemoteIterator;
import it.cnr.jada.util.action.SimpleDetailCRUDController;
import it.cnr.jada.util.jsp.Button;
import it.cnr.si.spring.storage.StorageObject;
import it.cnr.si.spring.storage.config.StoragePropertyNames;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

public class TestataProgettiRicercaBP extends AllegatiCRUDBP<AllegatoProgettoBulk, ProgettoBulk> implements IProgettoBP {
    protected boolean isUoCdsCollegata = false;
    protected SimpleDetailCRUDController crudPianoEconomicoAnnoCorrente = new ProgettoPianoEconomicoCRUDController("PianoEconomicoAnnoCorrente", Progetto_piano_economicoBulk.class, "dettagliPianoEconomicoAnnoCorrente", this) {
        public int addDetail(OggettoBulk oggettobulk) throws BusinessProcessException {
            ((Progetto_piano_economicoBulk) oggettobulk).setEsercizio_piano(((ProgettoBulk) this.getParentModel()).getEsercizio());
            return super.addDetail(oggettobulk);
        }

        @Override
        public boolean isGrowable() {
            return super.isGrowable() &&
                    Optional.ofNullable(getParentModel())
                            .filter(ProgettoBulk.class::isInstance)
                            .map(ProgettoBulk.class::cast)
                            .map(el -> !isROProgettoPianoEconomico(el))
                            .orElse(Boolean.FALSE);
        }

        @Override
        public boolean isShrinkable() {
            return super.isShrinkable() &&
                    Optional.ofNullable(getParentModel())
                            .filter(ProgettoBulk.class::isInstance)
                            .map(ProgettoBulk.class::cast)
                            .map(el -> !isROProgettoPianoEconomico(el))
                            .orElse(Boolean.FALSE);
        }

    };
    protected SimpleDetailCRUDController crudPianoEconomicoAltriAnni = new ProgettoPianoEconomicoCRUDController("PianoEconomicoAltriAnni", Progetto_piano_economicoBulk.class, "dettagliPianoEconomicoAltriAnni", this) {
        protected void validate(ActionContext actioncontext, OggettoBulk oggettobulk) throws ValidationException {
            super.validate(actioncontext, oggettobulk);
            if (Optional.ofNullable(oggettobulk).filter(Progetto_piano_economicoBulk.class::isInstance)
                    .map(Progetto_piano_economicoBulk.class::cast).flatMap(el -> Optional.ofNullable(el.getEsercizio_piano()))
                    .filter(el -> el.equals(((ProgettoBulk) this.getParentModel()).getEsercizio())).isPresent())
                throw new ValidationException("Operazione non possibile! Per caricare un dato relativo all'anno corrente utilizzare la sezione apposita.");
        }

    };
    protected SimpleDetailCRUDController crudPianoEconomicoVoceBilancioAnnoCorrente = new ProgettoPianoEconomicoVoceBilancioCRUDController("PianoEconomicoVoceBilancioAnnoCorrente", Ass_progetto_piaeco_voceBulk.class, "vociBilancioAssociate", crudPianoEconomicoAnnoCorrente);
    protected SimpleDetailCRUDController crudPianoEconomicoVoceBilancioAltriAnni = new ProgettoPianoEconomicoVoceBilancioCRUDController("PianoEconomicoVoceBilancioAltriAnni", Ass_progetto_piaeco_voceBulk.class, "vociBilancioAssociate", crudPianoEconomicoAltriAnni);
    private boolean flNuovoPdg = false;
    private boolean flInformix = false;
    private boolean flPrgPianoEconomico = false;
    private boolean isBilancioChiuso = false;
    private Integer annoFromPianoEconomico;
    private Unita_organizzativaBulk uoScrivania;
    private SimpleDetailCRUDController crudDettagli = new SimpleDetailCRUDController("Dettagli", Progetto_uoBulk.class, "dettagli", this) {
        public void validateForDelete(ActionContext context, OggettoBulk detail)
                throws ValidationException {
            validaUO(context, detail);
        }
    };
    private SimpleDetailCRUDController crudDettagliFinanziatori = new SimpleDetailCRUDController("DettagliFinanziatori", Progetto_finanziatoreBulk.class, "dettagliFinanziatori", this);
    private SimpleDetailCRUDController crudDettagliPartner_esterni = new SimpleDetailCRUDController("DettagliPartner_esterni", Progetto_partner_esternoBulk.class, "dettagliPartner_esterni", this);
    private SimpleDetailCRUDController crudPianoEconomicoTotale = new ProgettoPianoEconomicoCRUDController("PianoEconomicoTotale", Progetto_piano_economicoBulk.class, "dettagliPianoEconomicoTotale", this) {
        public int addDetail(OggettoBulk oggettobulk) throws BusinessProcessException {
            ((Progetto_piano_economicoBulk) oggettobulk).setEsercizio_piano(Integer.valueOf(0));
            return super.addDetail(oggettobulk);
        }

    };
    private SimpleDetailCRUDController pianoEconomicoSummaryVoce = new SimpleDetailCRUDController("PianoEconomicoSummaryVoce", Progetto_piano_economicoBulk.class, "pianoEconomicoSummaryVoce", this);
    private SimpleDetailCRUDController pianoEconomicoSummaryAnno = new SimpleDetailCRUDController("PianoEconomicoSummaryAnno", Progetto_piano_economicoBulk.class, "pianoEconomicoSummaryAnno", this);
    private SimpleDetailCRUDController pianoEconomicoVociBilancioDaAssociare = new SimpleDetailCRUDController("VociMovimentateNonAssociate", V_saldi_voce_progettoBulk.class, "vociMovimentateNonAssociate", this);

    /**
     * TestataProgettiRicercaBP constructor comment.
     */
    public TestataProgettiRicercaBP() {
        super();
    }

    /**
     * TestataProgettiRicercaBP constructor comment.
     *
     * @param function java.lang.String
     */
    public TestataProgettiRicercaBP(String function) {
        super(function);
    }

    @Override
    protected void init(Config config, ActionContext actioncontext) throws BusinessProcessException {
        try {
            Parametri_cnrBulk parCnr = Utility.createParametriCnrComponentSession().getParametriCnr(actioncontext.getUserContext(), CNRUserContext.getEsercizio(actioncontext.getUserContext()));
            setFlNuovoPdg(parCnr.getFl_nuovo_pdg().booleanValue());
            Parametri_enteBulk parEnte = Utility.createParametriEnteComponentSession().getParametriEnte(actioncontext.getUserContext());
            setFlInformix(parEnte.getFl_informix().booleanValue());
            setFlPrgPianoEconomico(parEnte.getFl_prg_pianoeco().booleanValue());
            uoScrivania = (Unita_organizzativaBulk) Utility.createUnita_organizzativaComponentSession().findUOByCodice(actioncontext.getUserContext(), CNRUserContext.getCd_unita_organizzativa(actioncontext.getUserContext()));
            isUoCdsCollegata = uoScrivania.getFl_uo_cds();

            Pdg_esercizioBulk pdgEsercizio = Utility.createProgettoRicercaComponentSession().getPdgEsercizio(actioncontext.getUserContext());
            isBilancioChiuso = Optional.ofNullable(pdgEsercizio).map(el -> el.getStato().equals(Pdg_esercizioBulk.STATO_CHIUSURA_GESTIONALE_CDR)).orElse(Boolean.FALSE);

            it.cnr.contab.config00.ejb.Configurazione_cnrComponentSession configSession = (it.cnr.contab.config00.ejb.Configurazione_cnrComponentSession) it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRCONFIG00_EJB_Configurazione_cnrComponentSession", it.cnr.contab.config00.ejb.Configurazione_cnrComponentSession.class);
            BigDecimal annoFrom = configSession.getIm01(actioncontext.getUserContext(), new Integer(0), null, Configurazione_cnrBulk.PK_GESTIONE_PROGETTI, Configurazione_cnrBulk.SK_PROGETTO_PIANO_ECONOMICO);
            if (Optional.ofNullable(annoFrom).isPresent())
                setAnnoFromPianoEconomico(annoFrom.intValue());
        } catch (Throwable e) {
            throw new BusinessProcessException(e);
        }
        super.init(config, actioncontext);
        if (isFlInformix())
            resetForSearch(actioncontext);
    }

    public BulkInfo getBulkInfo() {
        BulkInfo infoBulk = super.getBulkInfo();
        if (!this.isFlNuovoPdg())
            infoBulk.setShortDescription("Commesse");
        else
            infoBulk.setShortDescription(ProgettoBulk.LABEL_AREA_PROGETTUALE);
        return infoBulk;
    }

    public final it.cnr.jada.util.action.SimpleDetailCRUDController getCrudDettagli() {
        return crudDettagli;
    }

    public final it.cnr.jada.util.action.SimpleDetailCRUDController getCrudDettagliFinanziatori() {
        return crudDettagliFinanziatori;
    }

    /**
     * Returns the crudDettagliPartner_esterni.
     *
     * @return SimpleDetailCRUDController
     */
    public final it.cnr.jada.util.action.SimpleDetailCRUDController getCrudDettagliPartner_esterni() {
        return crudDettagliPartner_esterni;
    }

    protected void resetTabs(it.cnr.jada.action.ActionContext context) {
        setTab("tab", "tabTestata");
        setTab("tabProgettoPianoEconomico", "tabProgettoPianoEconomicoSummary");
    }

    /**
     * E' stata generata la richiesta di cercare il Progetto che sarà nodo padre del Progetto
     * che si sta creando.
     * Il metodo restituisce un Iteratore che permette di navigare tra i Progetti passando
     * da un livello ai suoi nodi figli e viceversa. Il metodo isLeaf, permette di definire un
     * "livello foglia", il livello, cioè, che non ha nodi sotto di esso.
     *
     * @param context la <code>ActionContext</code> che ha generato la richiesta
     * @return <code>RemoteBulkTree</code> l'albero richiesto
     **/
    public RemoteBulkTree getProgettiTree(ActionContext context) throws it.cnr.jada.comp.ComponentException {
        return
                new RemoteBulkTree() {
                    public RemoteIterator getChildren(ActionContext context, OggettoBulk bulk) throws java.rmi.RemoteException {
                        try {
                            return it.cnr.jada.util.ejb.EJBCommonServices.openRemoteIterator(context, ((ProgettoRicercaComponentSession) createComponentSession()).getChildren(context.getUserContext(), bulk));
                        } catch (it.cnr.jada.comp.ComponentException ex) {
                            throw new java.rmi.RemoteException("Component Exception", ex);
                        } catch (it.cnr.jada.action.BusinessProcessException ex) {
                            throw new java.rmi.RemoteException("BusinessProcess Exception", ex);
                        }

                    }

                    public OggettoBulk getParent(ActionContext context, OggettoBulk bulk) throws java.rmi.RemoteException {
                        try {
                            return ((ProgettoRicercaComponentSession) createComponentSession()).getParent(context.getUserContext(), bulk);
                        } catch (it.cnr.jada.comp.ComponentException ex) {
                            throw new java.rmi.RemoteException("Component Exception", ex);
                        } catch (it.cnr.jada.action.BusinessProcessException ex) {
                            throw new java.rmi.RemoteException("BusinessProcess Exception", ex);
                        }
                    }


                    public boolean isLeaf(ActionContext context, OggettoBulk bulk) throws java.rmi.RemoteException {
                        try {
                            return ((ProgettoRicercaComponentSession) createComponentSession()).isLeaf(context.getUserContext(), bulk);
                        } catch (it.cnr.jada.comp.ComponentException ex) {
                            throw new java.rmi.RemoteException("Component Exception", ex);
                        } catch (it.cnr.jada.action.BusinessProcessException ex) {
                            throw new java.rmi.RemoteException("BusinessProcess Exception", ex);
                        }
                    }
                };
    }

    /**
     * E' stata generata la richiesta di cercare il Progetto che sarà nodo padre del Progetto
     * che si sta creando.
     * Il metodo restituisce un Iteratore che permette di navigare tra i Progetti passando
     * da un livello ai suoi nodi figli e viceversa. Il metodo isLeaf, permette di definire un
     * "livello foglia", il livello, cioè, che non ha nodi sotto di esso.
     *
     * @param context la <code>ActionContext</code> che ha generato la richiesta
     * @return <code>RemoteBulkTree</code> l'albero richiesto
     **/
    public RemoteBulkTree getProgetti_sipTree(ActionContext context) throws it.cnr.jada.comp.ComponentException {
        return
                new RemoteBulkTree() {
                    public RemoteIterator getChildren(ActionContext context, OggettoBulk bulk) throws java.rmi.RemoteException {
                        try {
                            return it.cnr.jada.util.ejb.EJBCommonServices.openRemoteIterator(context, ((ProgettoRicercaComponentSession) createComponentSession()).getChildrenForSip(context.getUserContext(), bulk));
                        } catch (it.cnr.jada.comp.ComponentException ex) {
                            throw new java.rmi.RemoteException("Component Exception", ex);
                        } catch (it.cnr.jada.action.BusinessProcessException ex) {
                            throw new java.rmi.RemoteException("BusinessProcess Exception", ex);
                        }

                    }

                    public OggettoBulk getParent(ActionContext context, OggettoBulk bulk) throws java.rmi.RemoteException {
                        try {
                            return ((ProgettoRicercaComponentSession) createComponentSession()).getParentForSip(context.getUserContext(), bulk);
                        } catch (it.cnr.jada.comp.ComponentException ex) {
                            throw new java.rmi.RemoteException("Component Exception", ex);
                        } catch (it.cnr.jada.action.BusinessProcessException ex) {
                            throw new java.rmi.RemoteException("BusinessProcess Exception", ex);
                        }
                    }


                    public boolean isLeaf(ActionContext context, OggettoBulk bulk) throws java.rmi.RemoteException {
                        try {
                            return ((ProgettoRicercaComponentSession) createComponentSession()).isLeafForSip(context.getUserContext(), bulk);
                        } catch (it.cnr.jada.comp.ComponentException ex) {
                            throw new java.rmi.RemoteException("Component Exception", ex);
                        } catch (it.cnr.jada.action.BusinessProcessException ex) {
                            throw new java.rmi.RemoteException("BusinessProcess Exception", ex);
                        }
                    }
                };
    }

    /**
     * E' stata generata la richiesta di cercare il Progetto che sarà nodo padre del Progetto
     * che si sta creando.
     * Il metodo restituisce un Iteratore che permette di navigare tra i Progetti passando
     * da un livello ai suoi nodi figli e viceversa. Il metodo isLeaf, permette di definire un
     * "livello foglia", il livello, cioè, che non ha nodi sotto di esso.
     *
     * @param context la <code>ActionContext</code> che ha generato la richiesta
     * @return <code>RemoteBulkTree</code> l'albero richiesto
     **/
    public RemoteBulkTree getProgettiTreeWorkpackage(ActionContext context) throws it.cnr.jada.comp.ComponentException {
        return
                new RemoteBulkTree() {
                    public RemoteIterator getChildren(ActionContext context, OggettoBulk bulk) throws java.rmi.RemoteException {
                        try {
                            return it.cnr.jada.util.ejb.EJBCommonServices.openRemoteIterator(context, ((ProgettoRicercaComponentSession) createComponentSession()).getChildrenWorkpackage(context.getUserContext(), bulk));
                        } catch (it.cnr.jada.comp.ComponentException ex) {
                            throw new java.rmi.RemoteException("Component Exception", ex);
                        } catch (it.cnr.jada.action.BusinessProcessException ex) {
                            throw new java.rmi.RemoteException("BusinessProcess Exception", ex);
                        }

                    }

                    public OggettoBulk getParent(ActionContext context, OggettoBulk bulk) throws java.rmi.RemoteException {
                        try {
                            return ((ProgettoRicercaComponentSession) createComponentSession()).getParent(context.getUserContext(), bulk);
                        } catch (it.cnr.jada.comp.ComponentException ex) {
                            throw new java.rmi.RemoteException("Component Exception", ex);
                        } catch (it.cnr.jada.action.BusinessProcessException ex) {
                            throw new java.rmi.RemoteException("BusinessProcess Exception", ex);
                        }
                    }


                    public boolean isLeaf(ActionContext context, OggettoBulk bulk) throws java.rmi.RemoteException {
                        try {
                            return ((ProgettoRicercaComponentSession) createComponentSession()).isLeaf(context.getUserContext(), bulk);
                        } catch (it.cnr.jada.comp.ComponentException ex) {
                            throw new java.rmi.RemoteException("Component Exception", ex);
                        } catch (it.cnr.jada.action.BusinessProcessException ex) {
                            throw new java.rmi.RemoteException("BusinessProcess Exception", ex);
                        }
                    }
                };
    }
    /*
     * Necessario per la creazione di una form con enctype di tipo "multipart/form-data"
     * Sovrascrive quello presente nelle superclassi
     *
     */

    public void openForm(javax.servlet.jsp.PageContext context, String action, String target) throws java.io.IOException, javax.servlet.ServletException {

        openForm(context, action, target, "multipart/form-data");

    }

    /*
     * Utilizzato per la gestione del titolo
     * Sovrascrive il metodo si CRUDBP
     * */
    public String getFormTitle() {
        StringBuffer stringbuffer = new StringBuffer();
        if (isFlNuovoPdg())
            stringbuffer = stringbuffer.append(ProgettoBulk.LABEL_PROGETTO);
        else
            stringbuffer = stringbuffer.append("Commesse");

        stringbuffer.append(" - ");
        switch (getStatus()) {
            case 1: // '\001'
                stringbuffer.append("Inserimento");
                break;

            case 2: // '\002'
                stringbuffer.append("Modifica");
                break;

            case 0: // '\0'
                stringbuffer.append("Ricerca");
                break;

            case 5: // '\005'
                stringbuffer.append("Visualizza");
                break;
        }
        return stringbuffer.toString();
    }

    public int getLivelloProgetto() {
        return ProgettoBulk.LIVELLO_PROGETTO_SECONDO.intValue();
    }

    public void validaUO(ActionContext context, it.cnr.jada.bulk.OggettoBulk detail) throws ValidationException {
        try {
            // controllo viene effettuato solo per i moduli attività
            if ((this.isFlNuovoPdg() && getLivelloProgetto() == ProgettoBulk.LIVELLO_PROGETTO_SECONDO.intValue()) ||
                    (!this.isFlNuovoPdg() && getLivelloProgetto() == ProgettoBulk.LIVELLO_PROGETTO_TERZO.intValue()))
                ((ProgettoRicercaComponentSession) createComponentSession()).validaCancellazioneUoAssociata(
                        context.getUserContext(),
                        (ProgettoBulk) getModel(),
                        detail);
        } catch (ComponentException e) {
            throw new ValidationException(e.getMessage());
        } catch (RemoteException e) {
            throw new ValidationException(e.getMessage());
        } catch (BusinessProcessException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public boolean isFlNuovoPdg() {
        return flNuovoPdg;
    }

    private void setFlNuovoPdg(boolean flNuovoPdg) {
        this.flNuovoPdg = flNuovoPdg;
    }

    public boolean isFlInformix() {
        return flInformix;
    }

    public void setFlInformix(boolean flInformix) {
        this.flInformix = flInformix;
    }

    public boolean isFlPrgPianoEconomico() {
        return flPrgPianoEconomico;
    }

    public void setFlPrgPianoEconomico(boolean flPrgPianoEconomico) {
        this.flPrgPianoEconomico = flPrgPianoEconomico;
    }

    @Override
    public void basicEdit(ActionContext actioncontext, OggettoBulk oggettobulk, boolean flag)
            throws BusinessProcessException {
        super.basicEdit(actioncontext, oggettobulk, flag);
        ProgettoBulk progetto = (ProgettoBulk) getModel();
        if (Optional.ofNullable(progetto.getCd_unita_organizzativa())
                .map(el -> !el.equals(CNRUserContext.getCd_unita_organizzativa(actioncontext.getUserContext())))
                .orElse(Boolean.TRUE) ||
                Optional.ofNullable(progetto.getOtherField()).filter(el -> el.isStatoChiuso() || el.isStatoAnnullato() || el.isStatoApprovato()).isPresent())
            this.setStatus(VIEW);
    }

    public String[][] getTabs(HttpSession session) {
        String uo = CNRUserContext.getCd_unita_organizzativa(HttpActionContext.getUserContext(session));
        ProgettoBulk progetto = (ProgettoBulk) this.getModel();

        TreeMap<Integer, String[]> hash = new TreeMap<Integer, String[]>();
        int i = 0;

        hash.put(i++, new String[]{"tabTestata", "Testata", "/progettiric00/progetto_ricerca_testata_commesse.jsp"});

        if (this.isFlNuovoPdg() &&
                (isUoCdsCollegata ||
                        (progetto.getCd_unita_organizzativa() != null &&
                                progetto.getCd_unita_organizzativa().equals(uo)))) {

            if (this.isFlPrgPianoEconomico() &&
                    ((progetto.isPianoEconomicoRequired() && !isBilancioChiuso &&
                            Optional.ofNullable(progetto.getOtherField()).flatMap(el -> Optional.ofNullable(el.getDtInizio())).isPresent() &&
                            Optional.ofNullable(progetto.getOtherField()).flatMap(el -> Optional.ofNullable(el.getDtFine())).isPresent()) ||
                            (progetto.isDettagliPianoEconomicoPresenti() &&
                                    Optional.ofNullable(this.getAnnoFromPianoEconomico()).map(el -> el.compareTo(CNRUserContext.getEsercizio(HttpActionContext.getUserContext(session))) <= 0)
                                            .orElse(Boolean.FALSE))))
                hash.put(i++, new String[]{"tabPianoEconomico", "Piano Economico", "/progettiric00/progetto_piano_economico.jsp"});

            if (!this.isFlInformix()) {
                hash.put(i++, new String[]{"tabDettagliFinanziatori", "Finanziatori", "/progettiric00/progetto_ricerca_dettagliFinanziatori.jsp"});
                hash.put(i++, new String[]{"tabDettagliPartner_esterni", "Partner esterni", "/progettiric00/progetto_ricerca_dettagliPartner_esterni.jsp"});
                hash.put(i++, new String[]{"tabDettagli", "UO partecipanti", "/progettiric00/progetto_ricerca_dettagli.jsp"});
            }
        }

        if (!isSearching())
            hash.put(i++, new String[]{"tabAllegati", "Allegati", "/util00/tab_allegati.jsp"});

        String[][] tabs = new String[i][3];
        for (int j = 0; j < i; j++) {
            tabs[j] = new String[]{hash.get(j)[0], hash.get(j)[1], hash.get(j)[2]};
        }
        return tabs;
    }

    public String[][] getTabsPianoEconomico() {
        TreeMap<Integer, String[]> hash = new TreeMap<Integer, String[]>();
        int i = 0;

        hash.put(i++, new String[]{"tabProgettoPianoEconomicoSummary", "Totali", "/progettiric00/progetto_piano_economico_summary.jsp"});

        ProgettoBulk progetto = (ProgettoBulk) this.getModel();

        boolean existAnnoCorrente = false;
        if (progetto.getAnnoInizioOf() > progetto.getEsercizio() || progetto.getAnnoFineOf() < progetto.getEsercizio()) {
            //non sono nell'anno ma verifico se per caso non l'ho erronemanete caricato
            if (progetto.getDettagliPianoEconomicoAnnoCorrente().size() > 0)
                existAnnoCorrente = true;
        } else
            existAnnoCorrente = true;

        if (existAnnoCorrente)
            hash.put(i++, new String[]{"tabProgettoPianoEconomicoAnnoCorrente", "Anno " + progetto.getEsercizio(), "/progettiric00/progetto_piano_economico_anno_corrente.jsp"});

        if (!progetto.getAnnoInizioOf().equals(progetto.getEsercizio()) || !progetto.getAnnoFineOf().equals(progetto.getEsercizio()))
            hash.put(i++, new String[]{"tabProgettoPianoEconomicoAltriAnni", "Altri Anni", "/progettiric00/progetto_piano_economico_altri_anni.jsp"});

        if (!progetto.getVociMovimentateNonAssociate().isEmpty())
            hash.put(i++, new String[]{"tabProgettoVociMovimentateNonAssociate", "Voci Movimentate da Associare", "/progettiric00/progetto_piano_economico_voci_da_associare.jsp"});

        String[][] tabs = new String[i][3];
        for (int j = 0; j < i; j++) {
            tabs[j] = new String[]{hash.get(j)[0], hash.get(j)[1], hash.get(j)[2]};
        }
        return tabs;
    }

    @Override
    public OggettoBulk initializeModelForInsert(ActionContext actioncontext, OggettoBulk oggettobulk)
            throws BusinessProcessException {
        ProgettoBulk progetto = (ProgettoBulk) oggettobulk;
        if (this.isFlNuovoPdg()) {
            ProgettoBulk progettopadre = new ProgettoBulk();
            progettopadre.setLivello(ProgettoBulk.LIVELLO_PROGETTO_PRIMO);
            progetto.setProgettopadre(progettopadre);
            progetto.setLivello(ProgettoBulk.LIVELLO_PROGETTO_SECONDO);
        }
        if (!this.isFlInformix()) {
            Progetto_other_fieldBulk otherField = new Progetto_other_fieldBulk();
            otherField.setStato(Progetto_other_fieldBulk.STATO_INIZIALE);
            otherField.setToBeCreated();
            progetto.setOtherField(otherField);
        }
        return super.initializeModelForInsert(actioncontext, oggettobulk);
    }

    @Override
    public OggettoBulk initializeModelForEdit(ActionContext actioncontext, OggettoBulk oggettobulk)
            throws BusinessProcessException {
        ProgettoBulk progetto = (ProgettoBulk) super.initializeModelForEdit(actioncontext, oggettobulk);
        if (isBilancioChiuso &&
                Optional.ofNullable(progetto.getOtherField()).filter(Progetto_other_fieldBulk::isStatoIniziale).isPresent() &&
                !Optional.ofNullable(progetto.getOtherField()).flatMap(el -> Optional.ofNullable(el.getImFinanziato())).isPresent() &&
                !Optional.ofNullable(progetto.getOtherField()).flatMap(el -> Optional.ofNullable(el.getImCofinanziato())).isPresent()) {
            progetto.getOtherField().setImFinanziato(BigDecimal.ZERO);
            progetto.getOtherField().setImCofinanziato(BigDecimal.ZERO);
        }
        return progetto;
    }

    @Override
    public OggettoBulk initializeModelForSearch(ActionContext actioncontext, OggettoBulk oggettobulk)
            throws BusinessProcessException {
        if (this.isFlNuovoPdg()) {
            ProgettoBulk progettopadre = new ProgettoBulk();
            progettopadre.setLivello(ProgettoBulk.LIVELLO_PROGETTO_PRIMO);
            ((ProgettoBulk) oggettobulk).setProgettopadre(progettopadre);
            ((ProgettoBulk) oggettobulk).setLivello(ProgettoBulk.LIVELLO_PROGETTO_SECONDO);
        }
        ProgettoBulk oggettobulk2 = (ProgettoBulk) super.initializeModelForSearch(actioncontext, oggettobulk);
        oggettobulk2.setOtherField(new Progetto_other_fieldBulk());
        return oggettobulk2;
    }

    public SimpleDetailCRUDController getCrudPianoEconomicoTotale() {
        return crudPianoEconomicoTotale;
    }

    public SimpleDetailCRUDController getCrudPianoEconomicoAnnoCorrente() {
        return crudPianoEconomicoAnnoCorrente;
    }

    public SimpleDetailCRUDController getCrudPianoEconomicoAltriAnni() {
        return crudPianoEconomicoAltriAnni;
    }

    public SimpleDetailCRUDController getCrudPianoEconomicoVoceBilancioAnnoCorrente() {
        return crudPianoEconomicoVoceBilancioAnnoCorrente;
    }

    public SimpleDetailCRUDController getCrudPianoEconomicoVoceBilancioAltriAnni() {
        return crudPianoEconomicoVoceBilancioAltriAnni;
    }

    @Override
    public void validate(ActionContext actioncontext) throws ValidationException {
        Optional<ProgettoBulk> optProgetto = Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance).map(ProgettoBulk.class::cast);
        if (!optProgetto.isPresent())
            throw new ValidationException("Operazione non possibile! Non è stato possibile individuare il progetto da aggiornare!");

        if ((optProgetto.get().getProgettopadre() == null || optProgetto.get().getProgettopadre().getPg_progetto() == null) &&
                !optProgetto.get().getLivello().equals(ProgettoBulk.LIVELLO_PROGETTO_PRIMO))
            throw new ValidationException("Attenzione: Per salvare " +
                    (this.isFlNuovoPdg() ? "il " + ProgettoBulk.LABEL_PROGETTO : "la " + ProgettoBulk.LABEL_COMMESSA) +
                    " è necessario inserire " +
                    (this.isFlNuovoPdg() ? "l' " + ProgettoBulk.LABEL_AREA_PROGETTUALE : "la " + ProgettoBulk.LABEL_COMMESSA) + "!");

        Optional<Progetto_other_fieldBulk> optOtherField = optProgetto.flatMap(el -> Optional.ofNullable(el.getOtherField()));
        validateStato(actioncontext, optProgetto, optOtherField.flatMap(el -> Optional.ofNullable(el.getStato())).orElse(null));
        super.validate(actioncontext);
    }

    @Override
    public boolean isNewButtonHidden() {
        return super.isNewButtonHidden() || this.isFlInformix();
    }

    @Override
    public boolean isSaveButtonEnabled() {
        return super.isSaveButtonEnabled() &&
                (!this.isFlInformix() || !Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast)
                        .flatMap(el -> Optional.ofNullable(el.getOtherField()))
                        .filter(el -> el.isStatoAnnullato() || el.isStatoChiuso())
                        .isPresent());
    }

    @Override
    public boolean isDeleteButtonHidden() {
        return super.isDeleteButtonHidden() || this.isFlInformix();
    }

    public void caricaVociPianoEconomicoAssociate(ActionContext context, Progetto_piano_economicoBulk progettoPiaeco) throws BusinessProcessException {
        try {
            if (Optional.ofNullable(progettoPiaeco.getVoce_piano_economico()).map(Voce_piano_economico_prgBulk::getFl_link_vocibil_associate).orElse(Boolean.FALSE)) {
                List<Elemento_voceBulk> listVoci = ((ProgettoRicercaComponentSession) createComponentSession()).find(context.getUserContext(), Elemento_voceBulk.class, "findElementoVociAssociate", progettoPiaeco);
                progettoPiaeco.setVociBilancioAssociate(new BulkList<>());
                listVoci.stream().forEach(el -> {
                    Ass_progetto_piaeco_voceBulk dett = new Ass_progetto_piaeco_voceBulk();
                    dett.setElemento_voce(el);
                    dett.setToBeCreated();
                    progettoPiaeco.addToVociBilancioAssociate(dett);
                });
            }
        } catch (ComponentException | RemoteException e) {
            throw handleException(e);
        }
    }

    public SimpleDetailCRUDController getPianoEconomicoSummaryVoce() {
        return pianoEconomicoSummaryVoce;
    }

    public SimpleDetailCRUDController getPianoEconomicoSummaryAnno() {
        return pianoEconomicoSummaryAnno;
    }

    public SimpleDetailCRUDController getPianoEconomicoVociBilancioDaAssociare() {
        return pianoEconomicoVociBilancioDaAssociare;
    }

    public boolean isNegoziazioneButtonHidden() {
        return !Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance)
                .map(ProgettoBulk.class::cast).flatMap(el -> Optional.ofNullable(el.getOtherField()))
                .filter(Progetto_other_fieldBulk::isStatoIniziale)
                .flatMap(el -> Optional.ofNullable(el.getTipoFinanziamento()))
                .flatMap(el -> Optional.ofNullable(el.getCodice()))
                .filter(el -> el.equals(TipoFinanziamentoBulk.CODICE_FIN) || el.equals(TipoFinanziamentoBulk.CODICE_COF))
                .isPresent();
    }

    public boolean isApprovaButtonHidden() {
        return !Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance)
                .map(ProgettoBulk.class::cast).flatMap(el -> Optional.ofNullable(el.getCd_unita_organizzativa()))
                .filter(el -> el.equals(uoScrivania.getCd_unita_organizzativa()))
                .isPresent() ||
                Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast).flatMap(el -> Optional.ofNullable(el.getOtherField()))
                        .map(Progetto_other_fieldBulk::isStatoChiuso).orElse(Boolean.FALSE) ||
                !Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast).flatMap(el -> Optional.ofNullable(el.getOtherField()))
                        .filter(el -> Optional.ofNullable(el.getIdTipoFinanziamento()).isPresent())
                        .filter(el -> el.isStatoIniziale() || el.isStatoNegoziazione())
                        .isPresent();
    }

    public boolean isAnnullaButtonHidden() {
        return !Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance)
                .map(ProgettoBulk.class::cast).flatMap(el -> Optional.ofNullable(el.getCd_unita_organizzativa()))
                .filter(el -> el.equals(uoScrivania.getCd_unita_organizzativa()))
                .isPresent() ||
                !Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast).flatMap(el -> Optional.ofNullable(el.getOtherField()))
                        .filter(el -> Optional.ofNullable(el.getIdTipoFinanziamento()).isPresent())
                        .filter(Progetto_other_fieldBulk::isStatoNegoziazione)
                        .isPresent();
    }

    public boolean isChiusuraButtonHidden() {
        return !Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance)
                .map(ProgettoBulk.class::cast).flatMap(el -> Optional.ofNullable(el.getCd_unita_organizzativa()))
                .filter(el -> el.equals(uoScrivania.getCd_unita_organizzativa()))
                .isPresent() ||
                Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast).flatMap(el -> Optional.ofNullable(el.getOtherField()))
                        .map(Progetto_other_fieldBulk::isStatoChiuso).orElse(Boolean.FALSE) ||
                !Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast).flatMap(el -> Optional.ofNullable(el.getOtherField()))
                        .filter(el -> Optional.ofNullable(el.getIdTipoFinanziamento()).isPresent())
                        .filter(el -> !el.isDatePianoEconomicoRequired())
                        .isPresent();
    }

    public boolean isRiapriButtonHidden() {
        return !Optional.ofNullable(this.getModel())
                        .filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast)
                        .flatMap(el -> Optional.ofNullable(el.getCd_unita_organizzativa()))
                        .filter(el -> el.equals(uoScrivania.getCd_unita_organizzativa()))
                        .isPresent()||
                !Optional.ofNullable(this.getModel())
                        .filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast)
                        .flatMap(el -> Optional.ofNullable(el.getOtherField()))
                        .map(Progetto_other_fieldBulk::isStatoChiuso)
                        .orElse(Boolean.FALSE)||
                !Optional.ofNullable(this.getModel())
                        .filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast)
                        .flatMap(el -> Optional.ofNullable(el.getCd_unita_organizzativa()))
                        .map(el -> uoScrivania.isUoEnte())
                        .orElse(Boolean.FALSE);
    }

    public boolean isRimodulaButtonHidden() {
        return !Optional.ofNullable(this.getModel())
                        .filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast)
                        .flatMap(el -> Optional.ofNullable(el.getCd_unita_organizzativa()))
                        .filter(el -> el.equals(uoScrivania.getCd_unita_organizzativa()))
                        .isPresent() ||
                !Optional.ofNullable(this.getModel())
                        .filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast)
                        .flatMap(el -> Optional.ofNullable(el.getOtherField()))
                        .map(Progetto_other_fieldBulk::isStatoApprovato)
                        .orElse(Boolean.FALSE)||
                !Optional.ofNullable(this.getModel())
                        .filter(ProgettoBulk.class::isInstance)
                        .map(ProgettoBulk.class::cast)
                        .flatMap(el -> Optional.ofNullable(el.getCd_unita_organizzativa()))
                        .map(el -> uoScrivania.isUoEnte())
                        .orElse(Boolean.FALSE);
    }

    @Override
    protected Button[] createToolbar() {
        Button[] toolbar = super.createToolbar();
        Button[] newToolbar = new Button[toolbar.length + 6];
        int i;
        for (i = 0; i < toolbar.length; i++)
            newToolbar[i] = toolbar[i];
        newToolbar[i] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()), "Toolbar.negoziazione");
        newToolbar[i].setSeparator(true);
        newToolbar[i + 1] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()), "Toolbar.approva");
        newToolbar[i + 1].setSeparator(true);
        newToolbar[i + 2] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()), "Toolbar.annulla");
        newToolbar[i + 2].setSeparator(true);
        newToolbar[i + 3] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()), "Toolbar.chiusura");
        newToolbar[i + 3].setSeparator(true);
        newToolbar[i + 4] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()), "Toolbar.riapri");
        newToolbar[i + 4].setSeparator(true);
        newToolbar[i + 5] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()), "Toolbar.rimodula");
        newToolbar[i + 5].setSeparator(true);

        return newToolbar;
    }

    public void changeStato(ActionContext context, String newStato) throws ValidationException, BusinessProcessException, ComponentException, RemoteException {
        Optional<ProgettoBulk> optProgetto = Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance).map(ProgettoBulk.class::cast);
        if (!optProgetto.isPresent())
            throw new ValidationException("Operazione non possibile! Non è stato possibile individuare il progetto da aggiornare!");

        Optional<Progetto_other_fieldBulk> optOtherField = optProgetto.flatMap(el -> Optional.ofNullable(el.getOtherField()));

        if (Progetto_other_fieldBulk.STATO_NEGOZIAZIONE.equals(newStato)) {
            if (!optOtherField.get().isStatoIniziale())
                throw new ValidationException("Lo stato corrente del progetto non consente il suo aggiornamento allo stato \"NEGOZIAZIONE\".");
        } else if (Progetto_other_fieldBulk.STATO_APPROVATO.equals(newStato)) {
            if (!optOtherField.get().isStatoIniziale() && !optOtherField.get().isStatoNegoziazione())
                throw new ValidationException("Lo stato corrente del progetto non consente il suo aggiornamento allo stato \"APPROVATO\".");
        } else if (Progetto_other_fieldBulk.STATO_ANNULLATO.equals(newStato)) {
            if (!optOtherField.get().isStatoNegoziazione())
                throw new ValidationException("Lo stato corrente del progetto non consente il suo aggiornamento allo stato \"ANNULLATO\".");
        } else if (ProgettoBulk.STATO_CHIUSURA.equals(newStato)) {
            if (optProgetto.get().isDatePianoEconomicoRequired())
                throw new ValidationException("Attenzione! Operazione non possibile in presenza delle date del progetto.");
        } else if (ProgettoBulk.STATO_RIAPERTURA.equals(newStato)) {
            if (!optOtherField.get().isStatoChiuso())
                throw new ValidationException("E' possibile riaprire un progetto solo se lo stato corrente risulta essere \"CHIUSO\".");
            List<ObbligazioneBulk> listObb = createComponentSession().find(context.getUserContext(), ProgettoBulk.class, "findObbligazioniAssociate", optProgetto.get().getPg_progetto(), this.getAnnoFromPianoEconomico());
            if (listObb.stream().count() > 0)
                throw new ApplicationRuntimeException("Attenzione: risultano obbligazioni emesse sul progetto. "
                        + "Non è possibile attribuirgli uno stato diverso da Approvato o Chiuso. Operazione non consentita!");
            List<Pdg_variazioneBulk> listVarComp = createComponentSession().find(context.getUserContext(), ProgettoBulk.class, "findVariazioniCompetenzaAssociate", optProgetto.get().getPg_progetto(), this.getAnnoFromPianoEconomico());
            if (listVarComp.stream().count() > 0)
                throw new ApplicationRuntimeException("Attenzione: risultano variazioni di competenza emesse sul progetto. "
                        + "Non è possibile attribuirgli uno stato diverso da Approvato o Chiuso. Operazione non consentita!");
            List<Var_stanz_resBulk> listVarRes = createComponentSession().find(context.getUserContext(), ProgettoBulk.class, "findVariazioniResiduoAssociate", optProgetto.get().getPg_progetto(), this.getAnnoFromPianoEconomico());
            if (listVarRes.stream().count() > 0)
                throw new ApplicationRuntimeException("Attenzione: risultano variazioni di residuo emesse sul progetto. "
                        + "Non è possibile attribuirgli uno stato diverso da Approvato o Chiuso. Operazione non consentita!");
        } else
            throw new ValidationException("Operazione non gestita.");

        validateStato(context, optProgetto, newStato);

        //effettuo l'operazione richiesta
        if (Progetto_other_fieldBulk.STATO_NEGOZIAZIONE.equals(newStato) || Progetto_other_fieldBulk.STATO_APPROVATO.equals(newStato) ||
                Progetto_other_fieldBulk.STATO_ANNULLATO.equals(newStato)) {
            optOtherField.get().setStato(newStato);
            optOtherField.get().setToBeUpdated();
            if (!optProgetto.get().isDatePianoEconomicoRequired()) {
                optOtherField.get().setDtInizio(null);
                optOtherField.get().setDtFine(null);
                optOtherField.get().setDtProroga(null);
            }
        } else if (ProgettoBulk.STATO_CHIUSURA.equals(newStato)) {
            optOtherField.get().setDtInizio(null);
            optOtherField.get().setDtProroga(null);
            optOtherField.get().setDtFine(DateUtils.truncate(it.cnr.jada.util.ejb.EJBCommonServices.getServerDate()));
            optOtherField.get().setToBeUpdated();
        } else if (ProgettoBulk.STATO_RIAPERTURA.equals(newStato)) {
            this.setStatus(EDIT);
            optOtherField.get().setDtFine(null);
            optOtherField.get().setToBeUpdated();
        }
        optProgetto.get().setToBeUpdated();
        this.setModel(context, optProgetto.get());
        this.save(context);
    }

    private void validateStato(ActionContext context, Optional<ProgettoBulk> optProgetto, String stato) throws ValidationException {
        if (!optProgetto.isPresent())
            throw new ValidationException("Operazione non possibile! Non è stato possibile individuare il progetto da aggiornare!");

        Optional<Progetto_other_fieldBulk> optOtherField = optProgetto.flatMap(el -> Optional.ofNullable(el.getOtherField()));

        if (optOtherField.isPresent() &&
                (Progetto_other_fieldBulk.STATO_NEGOZIAZIONE.equals(stato) ||
                        Progetto_other_fieldBulk.STATO_APPROVATO.equals(stato) ||
                        ProgettoBulk.STATO_CHIUSURA.equals(stato) ||
                        Progetto_other_fieldBulk.STATO_ANNULLATO.equals(stato))) {
            if (!optOtherField.flatMap(el -> Optional.ofNullable(el.getTipoFinanziamento()))
                    .flatMap(el -> Optional.ofNullable(el.getCodice())).isPresent())
                throw new ValidationException("Operazione non possibile! Indicare il tipo di finanziamento!");

            if (optProgetto.get().isDatePianoEconomicoRequired()) {
                if (!optOtherField.map(Progetto_other_fieldBulk::getDtInizio).isPresent())
                    throw new ValidationException("Operazione non possibile! Indicare la data di inizio progetto!");
                if (!optOtherField.map(Progetto_other_fieldBulk::getDtFine).isPresent())
                    throw new ValidationException("Operazione non possibile! Indicare la data di fine progetto!");
            }

            if (!optOtherField.map(Progetto_other_fieldBulk::getImFinanziato).filter(el -> !(el.compareTo(BigDecimal.ZERO) < 0)).isPresent())
                throw new ValidationException("Operazione non possibile! Indicare l'importo finanziato (valore maggiore o uguale a 0)!");
            if (!optOtherField.map(Progetto_other_fieldBulk::getImCofinanziato).filter(el -> !(el.compareTo(BigDecimal.ZERO) < 0)).isPresent())
                throw new ValidationException("Operazione non possibile! Indicare l'importo cofinanziato (valore maggiore o uguale a 0)!");

            if (optProgetto.get().isPianoEconomicoRequired() && !isBilancioChiuso) {
                if (!optProgetto.map(ProgettoBulk::getImTotale).filter(el -> el.compareTo(BigDecimal.ZERO) > 0).isPresent())
                    throw new ValidationException("Operazione non possibile! Indicare almeno un importo positivo tra quello finanziato e cofinanziato!");
                if (!optProgetto.map(ProgettoBulk::isDettagliPianoEconomicoPresenti).orElse(Boolean.TRUE))
                    throw new ValidationException("Operazione non possibile! E' obbligatorio caricare il piano economico del progetto!");
                if (!optProgetto.map(ProgettoBulk::getImFinanziatoDaRipartire).filter(el -> el.compareTo(BigDecimal.ZERO) == 0).isPresent())
                    throw new ValidationException("Operazione non possibile! E' obbligatorio ripartire correttamente nel piano economico tutto l'importo finanziato del progetto!");
                if (!optProgetto.map(ProgettoBulk::getImCofinanziatoDaRipartire).filter(el -> el.compareTo(BigDecimal.ZERO) == 0).isPresent())
                    throw new ValidationException("Operazione non possibile! E' obbligatorio ripartire correttamente nel piano economico tutto l'importo cofinanziato del progetto!");
            }
        }
    }

    protected Integer getAnnoFromPianoEconomico() {
        return annoFromPianoEconomico;
    }

    public void setAnnoFromPianoEconomico(Integer annoFromPianoEconomico) {
        this.annoFromPianoEconomico = annoFromPianoEconomico;
    }

    protected boolean isROProgettoPianoEconomico(ProgettoBulk progettoBulk) {
        return Optional.ofNullable(progettoBulk)
                .map(ProgettoBulk::isROProgettoPianoEconomico)
                .orElse(Boolean.TRUE);
    }

    @Override
    protected String getStorePath(ProgettoBulk allegatoParentBulk, boolean create) throws BusinessProcessException {
        return allegatoParentBulk.getStorePath();
    }

    @Override
    protected Class<AllegatoProgettoBulk> getAllegatoClass() {
        return AllegatoProgettoBulk.class;
    }

    @Override
    protected void completeAllegato(AllegatoProgettoBulk allegato) throws ApplicationException {
        super.completeAllegato(allegato);
        StorageObject storageObject = storeService.getStorageObjectBykey(allegato.getStorageKey());
        allegato.setType(storageObject.getPropertyValue(StoragePropertyNames.BASE_TYPE_ID.value()));
    }

    public boolean isRODatiContabili() {
        Optional<ProgettoBulk> optProgetto = Optional.ofNullable(this.getModel()).filter(ProgettoBulk.class::isInstance).map(ProgettoBulk.class::cast);
        return !this.isSearching() && isBilancioChiuso &&
                optProgetto.filter(ProgettoBulk::isPianoEconomicoRequired).isPresent();
    }
}