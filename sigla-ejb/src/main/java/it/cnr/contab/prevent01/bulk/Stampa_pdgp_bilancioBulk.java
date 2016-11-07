/*
 * Created on Oct 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.cnr.contab.prevent01.bulk;

import java.util.Enumeration;

import it.cnr.jada.bulk.OggettoBulk;

/**
 * @author 
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Stampa_pdgp_bilancioBulk extends OggettoBulk {

	private Integer esercizio;
	
	//	Tipo gestione
	private String ti_gestione;

	//	Tipo di stampa
	private String ti_stampa;

	//	Tipo di aggregazione
	private String ti_aggregazione;

	//	Tipo di orgine
	private String ti_origine;

	//	Tipo livello
	private String ti_livello;

	private Integer percCassa;

	//	Stampa Riepilogo Titoli
	private boolean ti_riepilogo;

	private it.cnr.jada.util.OrderedHashtable livelliOptions = new it.cnr.jada.util.OrderedHashtable();
	
	public final static String TIPO_DECISIONALE = "DEC";
	public final static String TIPO_GESTIONALE = "GEST";

	public final static java.util.Dictionary ti_stampaKeys;
	
	static {
		ti_stampaKeys = new it.cnr.jada.util.OrderedHashtable();
		ti_stampaKeys.put(TIPO_DECISIONALE,"Decisionale");
		ti_stampaKeys.put(TIPO_GESTIONALE,"Gestionale");
	}

	public final static String TIPO_SCIENTIFICO = "SCI";
	public final static String TIPO_FINANZIARIO = "FIN";

	public final static java.util.Dictionary ti_aggregazioneKeys;
	
	static {
		ti_aggregazioneKeys = new it.cnr.jada.util.OrderedHashtable();
		ti_aggregazioneKeys.put(TIPO_SCIENTIFICO,"Scientifico");
		ti_aggregazioneKeys.put(TIPO_FINANZIARIO,"Finanziario");
	}

	public final static String TIPO_PROVVISORIO = "EXT";
	public final static String TIPO_REALE = "REA";

	public final static java.util.Dictionary ti_origineKeys;
	
	static {
		ti_origineKeys = new it.cnr.jada.util.OrderedHashtable();
		ti_origineKeys.put(TIPO_PROVVISORIO,"Provvisoria");
		ti_origineKeys.put(TIPO_REALE,"Reale");
	}

	public final static String TIPO_DECISIONALE_SCIENTIFICO = "DECSCI";
	public final static String TIPO_GESTIONALE_SCIENTIFICO = "GESTSCI";
	public final static String TIPO_STANZIAMENTO_SCIENTIFICO = "STASCI";
	public final static String TIPO_PROVVISORIO_SCIENTIFICO = "EXTSCI";

	public final static String TIPO_DECISIONALE_FINANZIARIO = "DECFIN";
	public final static String TIPO_GESTIONALE_FINANZIARIO = "GESTFIN";
	public final static String TIPO_STANZIAMENTO_FINANZIARIO = "STAFIN";
	public final static String TIPO_PROVVISORIO_FINANZIARIO = "EXTFIN";

	public final static String TIPO_GESTIONE_ENTRATA = "E";
	public final static String TIPO_GESTIONE_SPESA = "S";

	public final static java.util.Dictionary ti_gestioneKeys;
	
	static {
		ti_gestioneKeys = new it.cnr.jada.util.OrderedHashtable();
		ti_gestioneKeys.put(TIPO_GESTIONE_ENTRATA,"Entrata");
		ti_gestioneKeys.put(TIPO_GESTIONE_SPESA,"Spesa");
	};	

	public Stampa_pdgp_bilancioBulk() {
		super();
	}
	
	
	public java.lang.Integer getEsercizio() {
		return esercizio;
	}
	public void setEsercizio(java.lang.Integer newEsercizio) {
		esercizio = newEsercizio;
	}

	public String getTi_gestione() {
		return ti_gestione;
	}
	public void setTi_gestione(String ti_gestione) {
		this.ti_gestione = ti_gestione;
	}
	
	public String getTi_origine() {
		return ti_origine;
	}
	public void setTi_origine(String ti_origine) {
		this.ti_origine = ti_origine;
	}

	public String getTi_aggregazione() {
		return ti_aggregazione;
	}
	public void setTi_aggregazione(String ti_aggregazione) {
		this.ti_aggregazione = ti_aggregazione;
	}
	
	public String getTi_stampa() {
		return ti_stampa;
	}
	public void setTi_stampa(String ti_stampa) {
		this.ti_stampa = ti_stampa;
	}

	public String getTi_fonte() {
		if (TIPO_REALE.equals(getTi_origine()))
			return getTi_stampa().concat(getTi_aggregazione());
		return TIPO_PROVVISORIO.concat(getTi_aggregazione());
	}
	
	public String getTi_livello() {
		return ti_livello;
	}
	public void setTi_livello(String ti_livello) {
		this.ti_livello = ti_livello;
	}

	public Integer getPercCassa() {
		return percCassa;
	}
	public void setPercCassa(Integer percCassa) {
		this.percCassa = percCassa;
	}
	
	public boolean getTi_riepilogo() {
		return ti_riepilogo;
	}
	public void setTi_riepilogo(boolean ti_riepilogo) {
		this.ti_riepilogo = ti_riepilogo;
	}
	
	public it.cnr.jada.util.OrderedHashtable getLivelliOptions() {
		return livelliOptions;
	}
	public void setLivelliOptions(it.cnr.jada.util.OrderedHashtable livelliOptions) {
		this.livelliOptions = livelliOptions;
	}
	
	public Integer getNr_livello() {
		Enumeration a = livelliOptions.keys();
		while (a.hasMoreElements()) {
			Integer key = (Integer) a.nextElement();
			if (livelliOptions.get(key).equals(ti_livello))
				return key;
		}
		return 1;
	}

	public String getRiepilogo() {
		return ti_riepilogo?"S":"N";
	}
}
