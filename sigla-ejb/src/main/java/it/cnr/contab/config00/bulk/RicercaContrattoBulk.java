package it.cnr.contab.config00.bulk;

import it.cnr.jada.bulk.OggettoBulk;

public class RicercaContrattoBulk  extends OggettoBulk{
	private String[] listaUo;
	private String oggetto;
	private String giuridica;
	private String stato;
	private Integer esercizio_da;
	private Integer esercizio_a;
	private Integer esercizio;
	private Long id;
	private Integer num;
	private Integer daNum;
	private String user;
	
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getDaNum() {
		return daNum;
	}
	public void setDaNum(Integer daNum) {
		this.daNum = daNum;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String[] getListaUo() {
		return listaUo;
	}
	public void setListaUo(String[] lista_uo) {
		this.listaUo = lista_uo;
	}
	public String getGiuridica() {
		return giuridica;
	}
	public void setGiuridica(String giuridica) {
		this.giuridica = giuridica;
	}
	public Integer getEsercizio_da() {
		return esercizio_da;
	}
	public void setEsercizio_da(Integer esercizio_da) {
		this.esercizio_da = esercizio_da;
	}
	public Integer getEsercizio_a() {
		return esercizio_a;
	}
	public void setEsercizio_a(Integer esercizio_a) {
		this.esercizio_a = esercizio_a;
	}
	public Integer getEsercizio() {
		return esercizio;
	}
	public void setEsercizio(Integer esercizio) {
		this.esercizio = esercizio;
	}
}
