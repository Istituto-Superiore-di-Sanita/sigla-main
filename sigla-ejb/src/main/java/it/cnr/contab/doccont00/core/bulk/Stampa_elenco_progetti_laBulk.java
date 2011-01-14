package it.cnr.contab.doccont00.core.bulk;

import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.progettiric00.core.bulk.ProgettoBulk;

/**
 * Insert the type's description here.
 * Creation date: (14/09/2005 16.02.55)
 * @author: 
 */
public class Stampa_elenco_progetti_laBulk extends it.cnr.jada.bulk.OggettoBulk {
	
	//	Esercizio di scrivania
	private Integer esercizio;
	private String flg_pdg;
	private static final java.util.Dictionary ti_flg_pdgKeys = new it.cnr.jada.util.OrderedHashtable();
	private ProgettoBulk progettoForPrint;
	private boolean progettoForPrintEnabled;	
	
	private ProgettoBulk commessaForPrint;
	private boolean commessaForPrintEnabled;
	private ProgettoBulk moduloForPrint;
	private boolean moduloForPrintEnabled;
	
	private String flg_impegno;
	private static final java.util.Dictionary ti_flg_impegnoKeys = new it.cnr.jada.util.OrderedHashtable();
		
	final public static String FLG_PDG_SI = "Y";
	final public static String FLG_PDG_NO = "N";
	final public static String FLG_PDG_TUTTO = "*";
	
			
	static {
			ti_flg_pdgKeys.put(FLG_PDG_SI,"Si");
			ti_flg_pdgKeys.put(FLG_PDG_NO,"No");
			ti_flg_pdgKeys.put(FLG_PDG_TUTTO,"Tutti");
	}
	
	final public static String FLG_IMPEGNO_SI = "Y";
	final public static String FLG_IMPEGNO_NO = "N";
	final public static String FLG_IMPEGNO_TUTTO = "*";
	
			
	static {
			ti_flg_impegnoKeys.put(FLG_IMPEGNO_SI,"Si");
			ti_flg_impegnoKeys.put(FLG_IMPEGNO_NO,"No");
			ti_flg_impegnoKeys.put(FLG_IMPEGNO_TUTTO,"Tutti");
	}
			
	/**
	 * Stampa_elenco_progettiBulk constructor comment.
	 */
	public Stampa_elenco_progetti_laBulk() {
		super();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (11/04/2005 12:34:48)
	 * @return java.util.Dictionary
	 */
	
	
	public final java.util.Dictionary getti_flg_pdgKeys() {
			return ti_flg_pdgKeys;
	}
	
	public final java.util.Dictionary getti_flg_impegnoKeys() {
				return ti_flg_impegnoKeys;
		}
	/**
	 * Insert the method's description here.
	 * Creation date: (20/01/2003 16.50.12)
	 * @return java.sql.Timestamp
	 */
	public String getCdProgettoForPrint() {
		if (getProgettoForPrint()==null)
		  return "*";
		if(getProgettoForPrint().getCd_progetto() == null)
		  return "*";	
		return getProgettoForPrint().getCd_progetto();
	}
    public String getCdCommessaForPrint() {
		if (getCommessaForPrint()==null)
		  return "*";
		if (getCommessaForPrint().getCd_progetto()==null)
		  return "*";
		return getCommessaForPrint().getCd_progetto();
	}
	public String getCdModuloForPrint() {
		   if (getModuloForPrint()==null)
			 return "*";
		   if (getModuloForPrint().getCd_progetto()==null)
			 return "*";
		   return getModuloForPrint().getCd_progetto();
	}
	
	
		    /**
		 * Insert the method's description here.
		 * Creation date: (14/05/2003 9.28.52)
		 * @return java.lang.Integer
		 */
		public java.lang.Integer getEsercizio() {
			return esercizio;
		}
		
		
		/**
		 * Insert the method's description here.
		 * Creation date: (23/01/2003 12.00.24)
		 * @return 
		 */
		public ProgettoBulk getProgettoForPrint() {
			return progettoForPrint;
		}
		
		public ProgettoBulk getCommessaForPrint() {
					return commessaForPrint;
		}
		public ProgettoBulk getModuloForPrint() {
						return moduloForPrint;
		}
		/**
		 * Insert the method's description here.
		 * Creation date: (20/12/2002 10.47.40)
		 * @param 
		 */
		public boolean isROProgettoForPrint() {
			return !isProgettoForPrintEnabled();
		}
		public boolean isROCommessaForPrint() {
			return !isCommessaForPrintEnabled();
		}
		public boolean isROModuloForPrint() {
				return !isModuloForPrintEnabled();
			}
	
			
		/**
		 * Insert the method's description here.
		 * Creation date: (19/05/2003 15.45.26)
		 * @param 
		 */
		public void setCommessaForPrint(ProgettoBulk newCommessaForPrint) {
			commessaForPrint = newCommessaForPrint;
		}
		/**
		 * Insert the method's description here.
		 * Creation date: (14/05/2003 9.28.52)
		 * @param newEsercizio java.lang.Integer
		 */
		
			
		public void setEsercizio(java.lang.Integer newEsercizio) {
			esercizio = newEsercizio;
		}
		
		
		/**
		 * Insert the method's description here.
		 * Creation date: (23/01/2003 12.00.24)
		 * @return 
		 */
		public void setProgettoForPrint(ProgettoBulk progetto) {
			this.progettoForPrint = progetto;
		}
		
		public void setModuloForPrint(ProgettoBulk modulo) {
				this.moduloForPrint = modulo;
		}
		
	/**
	 * @return
	 */
	
	
	public String getflg_pdg() {
			return flg_pdg;
	}
	
	/**
	 * @param string
	 */
	
	public void setflg_pdg(String string) {
			flg_pdg = string;
	}
	

	public String getflg_impegno() {
			return flg_impegno;
	}
	
		
	
	public void setflg_impegno(String string) {
			flg_impegno = string;
	}

	/**
	 * @return
	 */
	public boolean isCommessaForPrintEnabled() {
		return commessaForPrintEnabled;
	}
		/**
	 * @param b
	 */
	public void setCommessaForPrintEnabled(boolean b) {
		commessaForPrintEnabled = b;
	}

	/**
	 * @return
	 */
	public boolean isProgettoForPrintEnabled() {
		return progettoForPrintEnabled;
	}

	/**
	 * @param b
	 */
	public void setProgettoForPrintEnabled(boolean b) {
		progettoForPrintEnabled = b;
	}
	/**
	* @return
	*/
	public boolean isModuloForPrintEnabled() {
			return moduloForPrintEnabled;
		}

		/**
		 * @param b
		 */
		public void setModuloForPrintEnabled(boolean b) {
			moduloForPrintEnabled = b;
		}

}
