/*
 * Created by Aurelio's BulkGenerator 1.0
 * Date 26/07/2007
 */
package it.cnr.contab.incarichi00.xmlfp.bulk;
import it.cnr.jada.persistency.Keyed;
public class Incarichi_archivio_xml_fpBase extends Incarichi_archivio_xml_fpKey implements Keyed {
//  DS_ARCHIVIO VARCHAR(2000)
	private java.lang.String ds_archivio;

//    NOME_FILE_INV  VARCHAR2(400) NOT NULL
	private java.lang.String nome_file_inv;

//    NOME_FILE_RIC  VARCHAR2(400) NOT NULL
	private java.lang.String nome_file_ric;
	
//    FL_PERLA  VARCHAR2(1) NOT NULL
	private java.lang.Boolean fl_perla;

//    FL_ALLINEA_SIGLA_PERLA  VARCHAR2(1) NOT NULL
	private java.lang.Boolean fl_merge_perla;

	public java.lang.String getDs_archivio() {
		return ds_archivio;
	}

	public void setDs_archivio(java.lang.String dsArchivio) {
		ds_archivio = dsArchivio;
	}

	public java.lang.String getNome_file_inv() {
		return nome_file_inv;
	}

	public void setNome_file_inv(java.lang.String nomeFileInv) {
		nome_file_inv = nomeFileInv;
	}

	public java.lang.String getNome_file_ric() {
		return nome_file_ric;
	}

	public void setNome_file_ric(java.lang.String nomeFileRic) {
		nome_file_ric = nomeFileRic;
	}

	public java.lang.Boolean getFl_perla() {
		return fl_perla;
	}

	public void setFl_perla(java.lang.Boolean fl_perla) {
		this.fl_perla = fl_perla;
	}

	public java.lang.Boolean getFl_merge_perla() {
		return fl_merge_perla;
	}

	public void setFl_merge_perla(java.lang.Boolean fl_merge_perla) {
		this.fl_merge_perla = fl_merge_perla;
	}
}
