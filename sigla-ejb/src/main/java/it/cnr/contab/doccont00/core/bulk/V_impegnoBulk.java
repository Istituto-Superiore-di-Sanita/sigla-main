package it.cnr.contab.doccont00.core.bulk;

import it.cnr.jada.bulk.*;

import java.util.*;
public class V_impegnoBulk extends V_impegnoBase {
	protected java.math.BigDecimal im_da_trasferire = new java.math.BigDecimal(0);
	protected Integer priorita;
	protected int nrImpegni;

	public final static Dictionary competenzaResiduoKeys = MandatoBulk.competenzaResiduoKeys;
	

public V_impegnoBulk() {
	super();
}
public V_impegnoBulk( String cd_cds, Integer esercizio, Integer esercizio_originale, Long pg_obbligazione, Long pg_obbligazione_scadenzario ) 
{	super( cd_cds, esercizio, esercizio_originale, pg_obbligazione, pg_obbligazione_scadenzario);
	
}
/**
 * @return java.util.Dictionary
 */
public java.util.Dictionary getCompetenzaResiduoKeys() {
	return competenzaResiduoKeys;
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'im_da_trasferire'
 *
 * @return Il valore della propriet� 'im_da_trasferire'
 */
public java.math.BigDecimal getIm_da_trasferire() {
	return im_da_trasferire;
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'im_disponibile'
 *
 * @return Il valore della propriet� 'im_disponibile'
 */
public java.math.BigDecimal getIm_disponibile() {
	if ( getIm_scadenza() != null && getIm_associato_doc_contabile() != null )
		return getIm_scadenza().subtract(getIm_associato_doc_contabile());
	return null;	
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'nrImpegni'
 *
 * @return Il valore della propriet� 'nrImpegni'
 */
public int getNrImpegni() {
	return nrImpegni;
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'priorita'
 *
 * @return Il valore della propriet� 'priorita'
 */
public java.lang.Integer getPriorita() {
	return priorita;
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'prioritaKeys'
 *
 * @return Il valore della propriet� 'prioritaKeys'
 */
public java.util.Dictionary getPrioritaKeys() {
	it.cnr.jada.util.OrderedHashtable prioritaKeys = new it.cnr.jada.util.OrderedHashtable();
	for ( int i = 1; i <= nrImpegni; i ++ )
		prioritaKeys.put( new Integer( i ),new Integer( i ) );
	return prioritaKeys;
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'ti_competenza_residuo'
 *
 * @return Il valore della propriet� 'ti_competenza_residuo'
 */
public String getTi_competenza_residuo() 
{
	if ( getCd_tipo_documento_cont().equals( Numerazione_doc_contBulk.TIPO_IMP ))
		return MandatoBulk.TIPO_COMPETENZA;
	if ( getCd_tipo_documento_cont().equals( Numerazione_doc_contBulk.TIPO_IMP_RES ))
		return MandatoBulk.TIPO_RESIDUO;		
	return null;
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'competenza'
 *
 * @return Il valore della propriet� 'competenza'
 */
public boolean isCompetenza()
{
	return  getCd_tipo_documento_cont().equals( Numerazione_doc_contBulk.TIPO_IMP );
}
/**
 * <!-- @TODO: da completare -->
 * 
 *
 * @param impegniColl	
 * @return 
 */
public List ordinaPerPriorita( List impegniColl ) 
{
	// riordino la lista degli impegni per priorita
	
	Collections.sort(impegniColl,new Comparator() {	

		public int compare(Object o1, Object o2) 
		{
			V_impegnoBulk i1 = (V_impegnoBulk) o1;
			V_impegnoBulk i2 = (V_impegnoBulk) o2;
			
			return i1.getPriorita().compareTo( i2.getPriorita());
		}
		public boolean equals(Object o)  
		{
			return (getPriorita() == ((V_impegnoBulk)o).getPriorita());
		}
	});

	return impegniColl;
}
/**
 * <!-- @TODO: da completare -->
 * Imposta il valore della propriet� 'im_da_trasferire'
 *
 * @param newIm_da_trasferire	Il valore da assegnare a 'im_da_trasferire'
 */
public void setIm_da_trasferire(java.math.BigDecimal newIm_da_trasferire) {
	im_da_trasferire = newIm_da_trasferire;
}
/**
 * <!-- @TODO: da completare -->
 * Imposta il valore della propriet� 'nrImpegni'
 *
 * @param newNrImpegni	Il valore da assegnare a 'nrImpegni'
 */
public void setNrImpegni(int newNrImpegni) {
	nrImpegni = newNrImpegni;
}
/**
 * <!-- @TODO: da completare -->
 * Imposta il valore della propriet� 'priorita'
 *
 * @param newPriorita	Il valore da assegnare a 'priorita'
 */
public void setPriorita(java.lang.Integer newPriorita) {
	priorita = newPriorita;
}
/**
 * Metodo con cui si verifica la validit� di alcuni campi, mediante un 
 * controllo sintattico o contestuale.
 */
public void validate() throws ValidationException {
	super.validate();
	if ( im_da_trasferire == null )
		throw new ValidationException( "Voce " + getCd_voce() + ":  e' necessario specificare un importo per ogni voce selezionata!" );
	if ( im_da_trasferire.compareTo( new java.math.BigDecimal(0) ) <= 0 )
		throw new ValidationException( "Voce " + getCd_voce() + ":  e' necessario specificare un importo maggiore di zero per ogni voce selezionata!" );	
	if ( getIm_disponibile().compareTo( im_da_trasferire ) < 0 )
			throw new ValidationException( "Voce " + getCd_voce() + ": non e' possibile trasferire un importo superiore all'importo disponibile!" );
}
}
