/*
 * Created on Nov 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.cnr.contab.inventario00.actions;

import java.util.Iterator;
import it.cnr.contab.inventario00.docs.bulk.V_impegni_inventarioBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.Forward;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;
import it.cnr.jada.util.action.ConsultazioniAction;
import it.cnr.jada.util.action.ConsultazioniBP;

public class ConsImpegniAssociazioniAction extends ConsultazioniAction {

	public Forward doConsultaDoc(ActionContext context) {
		try {
			ConsultazioniBP bp = (ConsultazioniBP)context.getBusinessProcess();
			CompoundFindClause clauses = new CompoundFindClause();
			if(bp.getElementsCount()==0){
				bp.setMessage("Non � stata selezionata nessuna riga.");
				return context.findDefaultForward();
			}
				
			if (bp.getSelection() != null && bp.getSelection().size() != bp.getElementsCount()){
				bp.setSelection(context);
	
				if ( bp.getSelectedElements(context) == null )
					return (Forward)context.findDefaultForward();
				
				if (bp.getSelectedElements(context).isEmpty()) {
					bp.setMessage("Non � stata selezionata nessuna riga.");
					return context.findDefaultForward();
				}							
				for (Iterator i = bp.getSelectedElements(context).iterator();i.hasNext();) 
				{
					V_impegni_inventarioBulk wpb = (V_impegni_inventarioBulk) i.next();
					CompoundFindClause claOR = new CompoundFindClause();
					claOR.addClause("AND","cd_unita_organizzativa",SQLBuilder.EQUALS,wpb.getCd_unita_organizzativa());
					claOR.addClause("AND","cd_cds",SQLBuilder.EQUALS,wpb.getCd_cds());
					//claOR.addClause("AND","esercizio",SQLBuilder.EQUALS,wpb.getEsercizio());
					claOR.addClause("AND","esercizio_obbligazione",SQLBuilder.EQUALS,wpb.getEsercizio());
					claOR.addClause("AND","esercizio_originale",SQLBuilder.EQUALS,wpb.getEsercizio_originale());
					claOR.addClause("AND","pg_obbligazione",SQLBuilder.EQUALS,wpb.getPg_obbligazione());
					claOR.addClause("AND","pg_obbligazione_scadenzario",SQLBuilder.EQUALS,wpb.getPg_obbligazione_scadenzario());
					clauses = clauses.or(clauses,claOR);
				}
			}			
			CompoundFindClause findclause = bp.getFindclause();
			if (findclause==null)
				findclause = new CompoundFindClause();
			if (clauses!=null)	
			    findclause.addChild(clauses);

			ConsultazioniBP ricercaLiberaBP = (ConsultazioniBP)context.createBusinessProcess("ConsDocPassivoObbBP");
			ricercaLiberaBP.addToBaseclause(findclause);
			ricercaLiberaBP.openIterator(context);
			
			context.addHookForward("close",this,"doDefault");
			return context.addBusinessProcess(ricercaLiberaBP);
		} catch(Throwable e) {
			return handleException(context,e);
		}
	}
	
	public Forward doConsultaAss(ActionContext context) {
		try {
			ConsultazioniBP bp = (ConsultazioniBP)context.getBusinessProcess();
			CompoundFindClause clauses = new CompoundFindClause();
			if(bp.getElementsCount()==0){
				bp.setMessage("Non � stata selezionata nessuna riga.");
				return context.findDefaultForward();
			}
			if (bp.getSelection() != null && bp.getSelection().size() != bp.getElementsCount() ){
				bp.setSelection(context);
	
				if ( bp.getSelectedElements(context) == null )
					return (Forward)context.findDefaultForward();
				
				if (bp.getSelectedElements(context).isEmpty()) {
					bp.setMessage("Non � stata selezionata nessuna riga.");
					return context.findDefaultForward();
				}							
				for (Iterator i = bp.getSelectedElements(context).iterator();i.hasNext();) 
				{
					V_impegni_inventarioBulk wpb = (V_impegni_inventarioBulk) i.next();
					CompoundFindClause claOR = new CompoundFindClause();
					claOR.addClause("AND","cd_unita_organizzativa",SQLBuilder.EQUALS,wpb.getCd_unita_organizzativa());
					claOR.addClause("AND","cds_imp",SQLBuilder.EQUALS,wpb.getCd_cds());
					claOR.addClause("AND","ESERCIZIO_imp",SQLBuilder.EQUALS,wpb.getEsercizio());
					claOR.addClause("AND","esercizio_originale",SQLBuilder.EQUALS,wpb.getEsercizio_originale());
					claOR.addClause("AND","pg_obbligazione",SQLBuilder.EQUALS,wpb.getPg_obbligazione());
					claOR.addClause("AND","pg_obbligazione_scadenzario",SQLBuilder.EQUALS,wpb.getPg_obbligazione_scadenzario());
					clauses = clauses.or(clauses,claOR);
				}
			}			
			CompoundFindClause findclause = bp.getFindclause();
			if (findclause==null)
				findclause = new CompoundFindClause();
			if (clauses!=null)	
			    findclause.addChild(clauses);

			ConsultazioniBP ricercaLiberaBP = (ConsultazioniBP)context.createBusinessProcess("ConsDocPassivoObbDettBP");
			ricercaLiberaBP.addToBaseclause(findclause);
			ricercaLiberaBP.openIterator(context);
			
			context.addHookForward("close",this,"doDefault");
			return context.addBusinessProcess(ricercaLiberaBP);
		} catch(Throwable e) {
			return handleException(context,e);
		}
	}
}
