package it.cnr.contab.anagraf00.tabrif.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Rif_modalita_pagamentoBase extends Rif_modalita_pagamentoKey implements Keyed {
	// CODICE_CASSIERE VARCHAR(10)
	private java.lang.String codice_cassiere;

	// DS_MODALITA_PAG VARCHAR(100) NOT NULL
	private java.lang.String ds_modalita_pag;

	// FL_PER_CESSIONE CHAR(1) NOT NULL
	private java.lang.Boolean fl_per_cessione;

	// TI_PAGAMENTO CHAR(1) NOT NULL
	private java.lang.String ti_pagamento;

	// TIPO_PAGAMENTO_SDI VARCHAR(5) NOT NULL
	private java.lang.String tipoPagamentoSdi;

	// fl_utilizzabile_art35 CHAR(1) NOT NULL
	private java.lang.Boolean fl_utilizzabile_art35;

	// fl_utilizzabile_art35 CHAR(1) NOT NULL
	private java.lang.Boolean fl_cancellato;

	// fl_all_obbl_mandato CHAR(1) NOT NULL
	private java.lang.Boolean fl_all_obbl_mandato;

	public java.lang.Boolean getFl_utilizzabile_art35() {
		return fl_utilizzabile_art35;
	}
	public void setFl_utilizzabile_art35(java.lang.Boolean fl_utilizzabile_art35) {
		this.fl_utilizzabile_art35 = fl_utilizzabile_art35;
	}
	public Rif_modalita_pagamentoBase() {
		super();
	}
	public Rif_modalita_pagamentoBase(java.lang.String cd_modalita_pag) {
		super(cd_modalita_pag);
	}
	/* 
	 * Getter dell'attributo codice_cassiere
	 */
	public java.lang.String getCodice_cassiere() {
		return codice_cassiere;
	}
	/* 
	 * Getter dell'attributo ds_modalita_pag
	 */
	public java.lang.String getDs_modalita_pag() {
		return ds_modalita_pag;
	}
	/* 
	 * Getter dell'attributo fl_per_cessione
	 */
	public java.lang.Boolean getFl_per_cessione() {
		return fl_per_cessione;
	}
	/* 
	 * Getter dell'attributo ti_pagamento
	 */
	public java.lang.String getTi_pagamento() {
		return ti_pagamento;
	}
	/* 
	 * Setter dell'attributo codice_cassiere
	 */
	public void setCodice_cassiere(java.lang.String codice_cassiere) {
		this.codice_cassiere = codice_cassiere;
	}
	/* 
	 * Setter dell'attributo ds_modalita_pag
	 */
	public void setDs_modalita_pag(java.lang.String ds_modalita_pag) {
		this.ds_modalita_pag = ds_modalita_pag;
	}
	/* 
	 * Setter dell'attributo fl_per_cessione
	 */
	public void setFl_per_cessione(java.lang.Boolean fl_per_cessione) {
		this.fl_per_cessione = fl_per_cessione;
	}
	/* 
	 * Setter dell'attributo ti_pagamento
	 */
	public void setTi_pagamento(java.lang.String ti_pagamento) {
		this.ti_pagamento = ti_pagamento;
	}
	public java.lang.Boolean getFl_cancellato() {
		return fl_cancellato;
	}
	public void setFl_cancellato(java.lang.Boolean fl_cancellato) {
		this.fl_cancellato = fl_cancellato;
	}
	public java.lang.String getTipoPagamentoSdi() {
		return tipoPagamentoSdi;
	}
	public void setTipoPagamentoSdi(java.lang.String tipoPagamentoSdi) {
		this.tipoPagamentoSdi = tipoPagamentoSdi;
	}
	public java.lang.Boolean getFl_all_obbl_mandato() {
		return fl_all_obbl_mandato;
	}
	public void setFl_all_obbl_mandato(java.lang.Boolean fl_all_obbl_mandato) {
		this.fl_all_obbl_mandato = fl_all_obbl_mandato;
	}	
}
