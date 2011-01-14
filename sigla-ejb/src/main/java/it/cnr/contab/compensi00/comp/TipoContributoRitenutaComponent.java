package it.cnr.contab.compensi00.comp;

import java.io.Serializable;
import java.rmi.RemoteException;
import it.cnr.contab.compensi00.tabrif.bulk.*;

import it.cnr.contab.compensi00.tabrif.bulk.*;
import it.cnr.contab.compensi00.docs.bulk.CompensoBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.comp.ICRUDMgr;
import it.cnr.jada.persistency.sql.*;
import it.cnr.jada.util.RemoteIterator;

public class TipoContributoRitenutaComponent extends it.cnr.jada.comp.CRUDComponent implements ITipoContributoRitenutaMgr,Cloneable,Serializable{
/**
 * TipoContributoRitenutaComponent constructor comment.
 */
public TipoContributoRitenutaComponent() {
	super();
}
/**
 * Ricerca lista intervalli di validit� Tipo CORI
 * PreCondition:
 *   Viene richiesta la lista degli intervalli di validit� del tipo CORI
 *        definiti con data inizio = a quella del tipo CORI in processo
 * PostCondition:
 *   Viene restituita la lista dei Tipi CORI o null nel caso il codice tipo CORI
 *        in processo sia null
 *
*/
public java.util.List caricaIntervalli(UserContext userContext, Tipo_contributo_ritenutaBulk tipoCori) throws ComponentException{

	try{

		if (tipoCori.getCd_contributo_ritenuta()==null)
			return null;

		Tipo_contributo_ritenutaHome home = (Tipo_contributo_ritenutaHome)getHome(userContext, tipoCori);
		return home.caricaIntervalli(tipoCori);

	}catch (it.cnr.jada.persistency.PersistencyException ex){
		throw handleException(tipoCori,ex);
	}
}
/**
  *  Inserisce un nuovo Tipo CORI
  *
**/
public OggettoBulk creaConBulk(UserContext userContext, OggettoBulk bulk) throws ComponentException{

	super.validaCreaConBulk(userContext, bulk);
	
	Tipo_contributo_ritenutaBulk tipoCori = (Tipo_contributo_ritenutaBulk)bulk;
	validaTipoContributoRintenuta(userContext, tipoCori);

	return inserisciTipoCori(userContext, tipoCori);
}
/**
 * Cancellazione di un intervallo di validit� con data anteriore alla data odierna
 * PreCondition:
 *   La data di inizio dell'intervallo � anteriore alla data odierna
 * PostCondition:
 *   La data di fine validit� dell'intervallo viene posta uguale alla data corrente + 1
 *        Tutti gli intervalli successivi a quello in processo vengono eliminati fisicamente
 *
 * Cancellazione di un intervallo di validit� con data uguale alla data odierna
 * PreCondition:
 *   La data di inizio dell'intervallo � anteriore alla data odierna
 * PostCondition:
 *        Tutti gli intervalli successivi a quello in processo vengono eliminati fisicamente
 */
public void eliminaConBulk(UserContext userContext,OggettoBulk bulk) throws it.cnr.jada.comp.ComponentException 
{
	try
	{
		Tipo_contributo_ritenutaBulk tipoCori = (Tipo_contributo_ritenutaBulk)bulk;
		java.sql.Timestamp dataOdierna = it.cnr.jada.util.ejb.EJBCommonServices.getServerDate();
		
		// cancellazione logica dell'oggetto attivo
		if (tipoCori.getDt_ini_validita().compareTo(dataOdierna)<=0){
			tipoCori.setDt_fin_validita(CompensoBulk.incrementaData(dataOdierna));
			updateBulk(userContext, tipoCori);
		}

		// cancellazione fisica di tutti gli intervalli successivi
		for(java.util.Iterator i = tipoCori.getIntervalli().iterator();i.hasNext();){
			Tipo_contributo_ritenutaBulk el = (Tipo_contributo_ritenutaBulk)i.next();
			if ( el.getDt_ini_validita().compareTo(dataOdierna)>0 && 
				 el.getDt_ini_validita().compareTo(tipoCori.getDt_ini_validita())>=0 )
				deleteBulk(userContext,el);
		}
	}
	catch(javax.ejb.EJBException ex)
	{
		throw handleException(ex);
	}	
	catch(it.cnr.jada.persistency.PersistencyException ex)
	{
		throw handleException(ex);
	}
}
public java.util.Collection findClassificazioneCoriColl(UserContext userContext, OggettoBulk bulk) throws ComponentException{
	return loadClassificazioneCori(userContext);
}
public java.util.Collection findClassificazioneMontantiColl(UserContext userContext, OggettoBulk bulk) throws ComponentException{
	return loadClassificazioneMontanti(userContext);
}
public OggettoBulk inizializzaBulkPerModifica(UserContext userContext,OggettoBulk bulk) throws ComponentException {

	Tipo_contributo_ritenutaBulk tipoCori = (Tipo_contributo_ritenutaBulk)super.inizializzaBulkPerModifica(userContext,bulk);
	tipoCori.setIntervalli(caricaIntervalli(userContext, tipoCori));

	return tipoCori;
}
/**
 * Inserimento di un nuovo intervallo di validit� di tipo CORI (primo intervallo)
 * PreCondition:
 *   La lista degli intervalli di validit� esistenti per il record in processo � vuota
 *        Il controllo di validit� date � superato
 * PostCondition:
 *   Viene creato per il tipo CORI il nuovo intervallo di validit� (che � anche il primo)
 *
 *      Data di inizio validit� nuovo intervallo non corretta
 * PreCondition:
 *   La lista degli intervalli di validit� esistenti per il record in processo non � vuota
 *        Il controllo di validit� date � superato
 *               La data di inizio validit� dell'intervallo in processo <> dalla data di fine dell'ultimo intervallo + 1
 *        oppure la data di inizio validit� dell'intervallo in processo <= della data odierna *        oppure la data di inizio validit� dell'intervallo in processo > ultima data fine di intervallo esistente
 * PostCondition:
 *        Viene sollevata un'eccezione
 *
 * Inserimento di un nuovo intervallo di validit� di record (intervallo n+1-esimo) futuro
 * PreCondition:
 *   La lista degli intervalli di validit� esistenti per il record in processo non � vuota
 *        Il controllo di validit� date � superato
 *        La data di inizio validit� dell'intervallo in processo = alla data di fine dell'ultimo intervallo + 1
 *        La data di inizio validit� dell'intervallo in processo > ultima data fine di intervallo esistente
 * PostCondition:
 *   Viene creato per il tipo CORI il nuovo intervallo di validit�
 *
 * Inserimento di un nuovo intervallo non valido (intervallo n+1-esimo) a spaccatura dell'intervallo corrente
 * PreCondition:
 *   La lista degli intervalli di validit� esistenti per il record in processo non � vuota
 *        La data di inizio dell'intervallo nuovo � contenuta in un intervallo esistente 
 *        L'intervallo non � l'ultimo e la data di fine validit� dell'intervallo in processo � maggiore della data di fine validit� dell'intervallo corrente
 * PostCondition:
 *   Viene sollevata un'eccezione
 *
 * Inserimento di un nuovo intervallo di validit� di record (intervallo n+1-esimo) a spaccatura del corrente
 * PreCondition:
 *   La lista degli intervalli di validit� esistenti per il record in processo non � vuota
 *        La data di inizio dell'intervallo nuovo � contenuta in un intervallo esistente 
 *        La data di fine validit� del nuovo intervallo � DATA_INFINITO
 * PostCondition:
 *   Viene aggiornata la data fine dell'intervallo corrente al giorno precedente alla data di inizio dell'intervallo nuovo
 *        Se la data di fine validit� del nuovo intervallo � DATA_INFINITO, viene posta uguale alla data di fine dell'intervallo corrente
 *
 * Richiesta di Inserimento di un nuovo intervallo a copertura di intervalli temporali "occupati" parzialmente
 * PreCondition:
 *   La lista degli intervalli di validit� esistenti per il record in processo non � vuota
 *        La data di inizio dell'intervallo nuovo non � contenuta in un intervallo esistente 
 *        La data di fine non � precedente ad ogni data di inizio validit� di intervalli esistenti
 *      PostCondition:
 *   Viene sollevata un'eccezione
 *
 * Inserimento di un nuovo intervallo a copertura di intervalli temporali non "occupati"
 * PreCondition:
 *   La lista degli intervalli di validit� esistenti per il record in processo non � vuota
 *        La data di inizio dell'intervallo nuovo non � contenuta in un intervallo esistente 
 *        La data di fine � precedente ad ogni data di inizio validit� di intervalli esistenti
 * PostCondition:
 *   Viene inserito il nuovo intervallo
 *
 */
private Tipo_contributo_ritenutaBulk inserisciTipoCori(UserContext userContext, Tipo_contributo_ritenutaBulk tipoCori) throws ComponentException{

	try{

		// Carico tutti gli intervalli definiti per quel tipo co/ri
		Tipo_contributo_ritenutaHome home = (Tipo_contributo_ritenutaHome)getHome(userContext, tipoCori);
		java.util.List l = caricaIntervalli(userContext, tipoCori);

		if (l.isEmpty()){
			validaDate(userContext, tipoCori);
			insertBulk(userContext, tipoCori);
			return tipoCori;
		}
			
		java.sql.Timestamp dataOdierna = home.getServerDate();
		Tipo_contributo_ritenutaBulk ultimo = (Tipo_contributo_ritenutaBulk)l.get(l.size()-1);

		// caso 1: inserimenti futuri
		validaDate(userContext, tipoCori);
		if (tipoCori.getDt_ini_validita().after(ultimo.getDt_fin_validita())){
			if (tipoCori.getDt_ini_validita().after(dataOdierna)){
				if (tipoCori.getDt_ini_validita().equals(CompensoBulk.incrementaData(ultimo.getDt_fin_validita()))){
					insertBulk(userContext, tipoCori);
				}else
					throw new it.cnr.jada.comp.ApplicationException("La Data Inizio Validita non � valida.\nGli intervalli devono essere contigui");
			}else
				throw new it.cnr.jada.comp.ApplicationException("La Data Inizio Validita deve essere superiore alla data odierna");

		}else{

			// caso 2: inserimenti in intervallo corrente
			Tipo_contributo_ritenutaBulk corrente = home.findIntervallo(tipoCori);

			if (corrente != null){
				if (tipoCori.getDt_ini_validita().after(dataOdierna)){
					if (tipoCori.getDt_fin_validita().equals(it.cnr.contab.config00.esercizio.bulk.EsercizioHome.DATA_INFINITO))
						tipoCori.setDt_fin_validita(corrente.getDt_fin_validita());

					if (!isUltimoIntervallo(userContext, corrente)){
						if(tipoCori.getDt_fin_validita().after(corrente.getDt_fin_validita()))
							throw new it.cnr.jada.comp.ApplicationException("La Data Fine Validita non � valida");
					}
					corrente.setDt_fin_validita(CompensoBulk.decrementaData(tipoCori.getDt_ini_validita()));
					updateBulk(userContext, corrente);
					insertBulk(userContext, tipoCori);
				}else
					throw new it.cnr.jada.comp.ApplicationException("La Data Inizio Validita deve essere superiore alla data odierna");
		
			}else{

				// caso 3: riempimento "buchi" in intervalli preesistenti
				for(int i = 0;i<l.size();i++){
					Tipo_contributo_ritenutaBulk el = (Tipo_contributo_ritenutaBulk)l.get(i);
					if (tipoCori.getDt_fin_validita().before(el.getDt_ini_validita())){
						insertBulk(userContext, tipoCori);
						return(tipoCori);
					}else if (tipoCori.getDt_ini_validita().before(el.getDt_fin_validita()))
						throw new it.cnr.jada.comp.ApplicationException("Intervallo non valido.\nSovrapposizione con intervalli preesistenti");
				}
			}
		}
			
		return(tipoCori);

	}catch (it.cnr.jada.persistency.PersistencyException ex){
		throw handleException(tipoCori,ex);
	}
}
/**
  *    L'intervallo in processo � l'ultimo intervallo esistente
  *    PreCondition:
  *       La data di inizio validit� dell'intervallo in processo >= della massima data di inizio di intervalli
  *    PostCondition:
  *       Viene ritornato TRUE
  *
  *    L'intervallo in processo non � l'ultimo intervallo esistente
  *    PreCondition:
  *       La data di inizio validit� dell'intervallo in processo < della massima data di inizio di intervalli
  *    PostCondition:
  *       Viene ritornato FALSE
 */
public boolean isUltimoIntervallo(UserContext userContext, Tipo_contributo_ritenutaBulk tipoCori) throws ComponentException{

	try{
		Tipo_contributo_ritenutaHome home = (Tipo_contributo_ritenutaHome)getHome(userContext, tipoCori);

		it.cnr.jada.persistency.sql.SQLBuilder sql = home.createSQLBuilder();
		java.sql.Timestamp maxData = (java.sql.Timestamp)home.findMax(tipoCori, "dt_ini_validita", null, false);
		if (maxData == null)
			return(true);

		if (!tipoCori.getDt_ini_validita().before(maxData) )
			return (true);

		return (false);
	}catch(it.cnr.jada.persistency.PersistencyException ex){
		throw handleException(ex);
	}catch(it.cnr.jada.bulk.BusyResourceException ex){
		throw handleException(ex);
	}
}
/**
 * Caricamento delle Classificazioni CORI residenti su DB
 *
 * Pre-post-conditions
 *
 * Name: Caricamento classificazioni cori
 * Pre: Viene richiesto il caricamento delle classificazioni cori presenti nel db
 * Post: Viene restituita la collezione delle classificazioni cori presenti
*/
public java.util.Collection loadClassificazioneCori(UserContext userContext) throws ComponentException{

	try{
		Classificazione_coriHome home = (Classificazione_coriHome)getHome(userContext, Classificazione_coriBulk.class);
		return home.fetchAll(home.createSQLBuilder());
	}catch(it.cnr.jada.persistency.PersistencyException ex){
		throw handleException(ex);
	}
}
/**
 * Caricamento delle Classificazioni montanti residenti su DB
 *
 * Pre-post-conditions
 *
 * Name: Caricamento classificazioni cori
 * Pre: Viene richiesto il caricamento delle classificazioni montanti presenti nel db
 * Post: Viene restituita la collezione delle classificazioni montanti presenti
*/
public java.util.Collection loadClassificazioneMontanti(UserContext userContext) throws ComponentException{

	try{
		Classificazione_montantiHome home = (Classificazione_montantiHome)getHome(userContext, Classificazione_montantiBulk.class);
		return home.fetchAll(home.createSQLBuilder());
	}catch (it.cnr.jada.persistency.PersistencyException ex){
		throw handleException(ex);
	}
}
/**
 * Modifica di intervallo ponendo la data fine nel futuro
 * PreCondition:
 *   Il controllo di validit� date � superato
 * PostCondition:
 *   Viene aggiornato l'intervallo in processo
 *
 *      Modifica di intervallo ponendo la data fine < alla data odierna
 * PreCondition: La data di fine intervallo = alla data odierna
 * PostCondition:
 *        Viene sollevata un'eccezione
 *
 *      Modifica di intervallo ponendo la data fine nel passato
 * PreCondition: La data di fine intervallo = alla data odierna
 * PostCondition:
 *        La data di fine validit� dell'intervallo corrente viene posta = data odierna
 *        Viene creato il nuovo intervallo con data di inizio validit� = alla data odierna + 1
 *
 */
public OggettoBulk modificaConBulk(UserContext userContext,OggettoBulk bulk) throws it.cnr.jada.comp.ComponentException {

	try{
		super.validaModificaConBulk(userContext, bulk);
		
		Tipo_contributo_ritenutaBulk tipoCori = (Tipo_contributo_ritenutaBulk)bulk;
		Tipo_contributo_ritenutaHome home = (Tipo_contributo_ritenutaHome)getHome(userContext, tipoCori);
		java.sql.Timestamp dataOdierna = home.getServerDate();

		// Intervallo futuro
		if (tipoCori.getDt_ini_validita().after(dataOdierna)){
			validaDate(userContext, tipoCori);
			updateBulk(userContext, tipoCori);
		}else{

		// Intervallo corrente: spezzo in due
			Tipo_contributo_ritenutaBulk current = (Tipo_contributo_ritenutaBulk)home.findByPrimaryKey(tipoCori, true);
			if (!isUltimoIntervallo(userContext, current) && tipoCori.getDt_fin_validita()==null){
				tipoCori.setDt_fin_validita(current.getDt_fin_validita());
			}
			validaDate(userContext, tipoCori);

			if (!tipoCori.getDt_fin_validita().before(dataOdierna)){
				current.setDt_fin_validita(dataOdierna);
				updateBulk(userContext, current);
				tipoCori.setDt_ini_validita(CompensoBulk.incrementaData(dataOdierna));
				insertBulk(userContext, tipoCori);
			}else{
				throw new it.cnr.jada.comp.ApplicationException("La data fine validit� deve essere superiore alla data odierna");
			}
		}

		return tipoCori;
	
	}catch(it.cnr.jada.persistency.PersistencyException ex){
		throw handleException(ex);
	}
}
/**
  *    Controlli di validazione del periodo di inizio/fine validita' 
  *    del nuovo record non superati
  *    PreCondition:
  *  validazione periodo inizio/fine non superata (data inizio validita del nuovo 
  *  record non definita o maggiore delloa data di fine validit�)
  *    PostCondition:
  *      VIene sollevata un'eccezione
  *
  *    Impostazione data di fine periodo INFINITO
  *    PreCondition:
  *  la data di fine validit� periodo non impostata
  *    PostCondition:
  *      La data di fine periodo viene impostata a DATA_INFINITO
  */
private void validaDate(UserContext userContext, Tipo_contributo_ritenutaBulk tipoCori) throws ComponentException{

	if (tipoCori.getDt_ini_validita()==null)
		throw new it.cnr.jada.comp.ApplicationException("Il campo Data Inizio Validit� deve essere valorizzato");

	if (tipoCori.getDt_fin_validita()==null)
		tipoCori.setDt_fin_validita(it.cnr.contab.config00.esercizio.bulk.EsercizioHome.DATA_INFINITO);

	if (tipoCori.getDt_ini_validita().after(tipoCori.getDt_fin_validita()))
		throw new it.cnr.jada.comp.ApplicationException("La Data Fine Validit� deve essere superiore alla Data Inizio Validit�");
}
/**
  *    Controlli di validazione del record tipo CORI non superati 
  *    PreCondition:
  *       Il codice o la descrizione del tipo CORI non sono definiti
  *    PostCondition:
  *       Viene sollevata un'eccezione
 */
private void validaTipoContributoRintenuta(UserContext userContext, Tipo_contributo_ritenutaBulk tipoCori) throws ComponentException{

	// controllo su campo CD_CONTRIBUTO_RITENUTA
	if ( tipoCori.getCd_contributo_ritenuta() == null )
		throw new it.cnr.jada.comp.ApplicationException( "Il campo Codice deve essere valorizzato !" );
	
	// controllo su campo DS_CONTRIBUTO_RITENUTA
	if ( tipoCori.getDs_contributo_ritenuta() == null )
		throw new it.cnr.jada.comp.ApplicationException( "Il campo Descrizione deve essere valorizzato !" );
}
}
