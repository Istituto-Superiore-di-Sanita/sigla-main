CREATE OR REPLACE PACKAGE CNRCTB573 AS
--==============================================================================
--
-- CNRCTB573 - Liquidazione massa contributi/ritenute
--
-- Date: 05/12/2003
-- Version: 1.3
--
-- Dependency: IBMERR 001
--
-- History:
--
-- Date: 22/05/2003
-- Version: 1.0
-- Creazione
--
-- Date: 23/05/2003
-- Version: 1.1
-- Gestione batch
--
-- Date: 03/12/2003
-- Version: 1.2
-- Documentazione
--
-- Date: 05/12/2003
-- Version: 1.3
-- Permette anche la liquidazione di gruppi cori nulli accentrati
--
-- Date: 20/12/2005
-- Version: 1.4
-- Creazione nuove procedure "job_liquid_cori_massa" e "job_liquid_cori_massa_istituti" 
-- per la gestione della Liquidazione CORI massiva. 
-- Tale Liquidazione viene fatta solo per gli Istituti (CDS non SAC) e solo per i gruppi accentrati.
-- Solo se ? "Da Esercizio Precedente" vengono presi anche i gruppi negativi.
--==============================================================================
--
-- Constants
--

LOG_TIPO_LIQCORIMAS CONSTANT VARCHAR2(20):='LIQUID_CORI_MASS00';

--
-- Functions e Procedures
--

-- Calcolo liquidazione CORI di massa da esercizio precedente
--
-- pre-post-name: Data da, Data a, esercizio o utente non specificati per la liquidazione
-- pre: alcuni parametri di imput necessari non sono specificati
-- post: viene sollevata un'eccezione
--
-- pre-post-name: Calcolo della liquidazione massiva da esercizio precedente
-- pre: L'utente chiede che venga effettuata la liquidazione massiva da esercizio precedente
-- post:
--    Per ogni UO diversa dall'UO di versamento accentrato ed esistente nel nuovo esercizio (aEs specificato)
--      Viene calcolato il pg di liquidazione
--      Viene richiamato i calcolo della liqiuidazione CORI da esercizio precedente
--      I gruppi di versamento accentrato calcolati con la liquidazione, vengono inseriti in vista VSX per il
--      processo di liquidazione vera e propria
--      Viene effettuata la liquidazione, svuotata la vista VSX
--      Se viene sollevato errore questo viene loggato con log err sui log
--      Se non viene sollevato errore questo viene loggato con log inf sui log
--    Al termine dell'operazione arriva un messaggio all'utente che ha lanciato l'operazione
--

 procedure job_liquidazione_cori_massa
   (
    job NUMBER, pg_exec NUMBER, next_date DATE,
    aEs number,
	aDtDa date,
	aDtA date,
	aUser varchar2
   );
 Procedure job_liquid_cori_massa
   (
    aEs NUMBER, es_prec VARCHAR2, aDtDa DATE, aDtA DATE, aUser VARCHAR2
   );
   
 Procedure job_liquid_cori_massa_istituti
   (
    job NUMBER,  pg_ex NUMBER, next_date DATE,
    aEs NUMBER, es_prec VARCHAR2, aDtDa DATE, aDtA DATE,  aUser VARCHAR2
   ); 
End;


CREATE OR REPLACE PACKAGE BODY CNRCTB573 AS
-- sembra che se c'? un errore (es:Saldo CC dei Capitoli oppure Cassa Cds, non sufficente),
-- non viene creato il mandato verso la SAC ma viene ugualmente aggiornata la PGIRO presente in LIQUID_GRUPPO_CENTRO 
-- e quindi la 000.407 verser? anche questi importi (non aggiorna la PGIRO ma se non c'? la crea)
 procedure job_liquidazione_cori_massa
   (
    job NUMBER, pg_exec NUMBER, next_date DATE,
    aEs number,
	aDtDa date,
	aDtA date,
	aUser varchar2
   ) is
  aTitolo varchar2(50):='Liquidazione CORI anno prec. per tutte le UO';
  aUOVERSACC unita_organizzativa%rowtype;
  aPgLiq number(8);
  aTDtDa date;
  aTDtA date;
  aPgCall number(15);
  aNR number;
 begin
  -- Lancio start esecuzione log
  IBMUTL210.logStartExecutionUpd(pg_exec, LOG_TIPO_LIQCORIMAS, job, 'Richista utente:' || aUser,
                                  aTitolo||'. Start:' || TO_CHAR(sysdate,'YYYY/MM/DD HH-MI-SS'));
  begin
   if
        aEs is null
	 or aDtDa is null
	 or aDtA is null
     or aUser is null
   then
    IBMERR001.RAISE_ERR_GENERICO('Parametri di liquidazione non completamente specificati');
   end if;

   aTDtDa:=trunc(aDtDa);
   aTDtA:=trunc(aDtA);

   aUOVERSACC:=CNRCTB020.getUOVersCori(aEs);

   for aUO in (select * from v_unita_organizzativa_valida u where 
        esercizio = aEs-1                                         
	and cd_unita_organizzativa != aUOVERSACC.cd_unita_organizzativa
	and fl_cds = 'N'                                          
	and exists (select 1 from v_unita_organizzativa_valida  where
        esercizio = aEs                                           
	and fl_cds = 'N'                                          
	and cd_unita_organizzativa  =u.cd_unita_organizzativa)    
   ) loop
    begin
     -- Calcolo il pg di liquidazione
     aPgLiq:=CNRCTB575.getNextNumLiquid(aUO.cd_unita_padre, aEs, aUO.cd_unita_organizzativa);
     -- Effettuo il calcolo della liquidazione (si tratta di liquidazione da esercizio precedente)
     CNRCTB570.CALCOLALIQUIDAZIONE(aUO.cd_unita_padre, aEs, 'Y', aUO.cd_unita_organizzativa, aPgLiq, aTDtDa, aTDtA, aUser);
     -- Preparo la chiamata a liquidGruppoCORI
     -- Recupero il pg VSX
     aPgCall:=IBMUTL020.vsx_get_pg_call();
     -- Azzera il contatore righe
     aNR:=0;
     for aLGC in (select * from liquid_gruppo_cori where
            esercizio=aEs
        and pg_liquidazione = aPgLiq
        and cd_cds = aUO.cd_unita_padre
        and cd_unita_organizzativa = aUO.cd_unita_organizzativa
--        and im_liquidato != 0 Rimossa per richiesta di Sandra mascagni del 04/12/2003
        and fl_accentrato = 'Y'
        for update nowait
     ) loop
      aNR:=aNR+1;
      insert into VSX_LIQUIDAZIONE_CORI (
       PG_CALL,
       PAR_NUM,
       PROC_NAME,
       MESSAGETOUSER,
       CD_CDS,
       ESERCIZIO,
       CD_UNITA_ORGANIZZATIVA,
       CD_CDS_ORIGINE,
       CD_UO_ORIGINE,
       PG_LIQUIDAZIONE,
       PG_LIQUIDAZIONE_ORIGINE,
       CD_GRUPPO_CR,
       CD_REGIONE,
       PG_COMUNE,
       UTCR,
       DACR,
       UTUV,
       DUVA,
       PG_VER_REC
      ) values (
       aPgCall,
       aNR,
       'CNRCTB570.vsx_liquida_cori',
       null,
       aLGC.CD_CDS,
       aLGC.ESERCIZIO,
       aLGC.CD_UNITA_ORGANIZZATIVA,
       aLGC.CD_CDS_ORIGINE,
       aLGC.CD_UO_ORIGINE,
       aLGC.PG_LIQUIDAZIONE,
       aLGC.PG_LIQUIDAZIONE_ORIGINE,
       aLGC.CD_GRUPPO_CR,
       aLGC.CD_REGIONE,
       aLGC.PG_COMUNE,
       aLGC.UTCR,
       aLGC.DACR,
       aLGC.UTUV,
       aLGC.DUVA,
       aLGC.PG_VER_REC
      );
     end loop; -- Fine loop su liquid gruppo cori
     CNRCTB570.VSX_LIQUIDA_CORI(aPgCall);

     -- Clean up VSX
     delete from VSX_LIQUIDAZIONE_CORI where
      pg_call = aPgCall;

     -- Messaggio di operazione completata ad utente

     IBMUTL200.logInf(pg_exec, aTitolo||' UO:'||aUO.cd_unita_organizzativa||' '||aUO.ds_unita_organizzativa,null,Null);

     -- Commit al termine del ciclo per UO
     COMMIT;
	exception when OTHERS then
     ROLLBACK;
     IBMUTL200.logErr(pg_exec, aTitolo||' UO:'||aUO.cd_unita_organizzativa||' '||aUO.ds_unita_organizzativa, DBMS_UTILITY.FORMAT_ERROR_STACK,null);
	end;
   end loop; -- Fine loop su UO


   IBMUTL205.LOGINF(aTitolo,
                     aTitolo||' ' ||TO_CHAR(sysdate,'DD/MM/YYYY HH:MI:SS'),
                     'Operazione completata con successo',aUser);

  exception
   when others then
    rollback;
    -- Messaggio di attenzione ad utente
    IBMUTL205.LOGWAR(aTitolo,
                     aTitolo||' ' || TO_CHAR(sysdate,'DD/MM/YYYY HH:MI:SS') ||
                     ' (pg_exec=' || pg_exec || ')',DBMS_UTILITY.FORMAT_ERROR_STACK,aUser);
  end;
 end;

       
  Procedure job_liquid_cori_massa_istituti
   (
    job NUMBER,  pg_ex NUMBER, next_date DATE,
    aEs NUMBER, es_prec VARCHAR2, aDtDa DATE, aDtA DATE, aUser VARCHAR2
   )Is
   aTitolo varchar2(50):='Liquidazione CORI Massiva';
   aUOVERSACC unita_organizzativa%rowtype;
   aPgLiq number(8);
   aTDtDa date;
   aTDtA date;
   aPgCall number(15);
   aNR number;
   pg_exec NUMBER;
   esitoOk VARCHAR2(1):='S';
   errore VARCHAR2(4000);
   codice_errore VARCHAR2(20);
 Begin
     pg_exec := pg_ex;
     -- Lancio start esecuzione log
     IBMUTL210.logStartExecutionUpd(pg_exec, LOG_TIPO_LIQCORIMAS, job, 'Periodo di Riferimento: ' ||To_Char(aDtDa,'dd/mm/yyyy')||' - '||To_Char(aDtA,'dd/mm/yyyy')||'. Da Esercizio precedente: '||es_prec,
                                  aTitolo||'. Start:' || TO_CHAR(sysdate,'YYYY/MM/DD HH-MI-SS'));
     Begin
        If aEs is null
	    Or aDtDa is null
	    Or aDtA is null
            Or aUser is Null Then
                 IBMERR001.RAISE_ERR_GENERICO('Parametri di liquidazione massiva non completamente specificati');
        End If;
	 
   	aTDtDa:=trunc(aDtDa);
   	aTDtA:=trunc(aDtA);

   	aUOVERSACC:=CNRCTB020.getUOVersCori(aEs);

	For aUO in 
   	    (Select * from v_unita_organizzativa_valida u 
   	     Where esercizio = Decode(es_prec,'Y',aEs-1,aEs)
	       And cd_unita_organizzativa != aUOVERSACC.cd_unita_organizzativa
	       And fl_cds = 'N'
	       And cd_tipo_unita != CNRCTB020.TIPO_SAC 
	       And Exists (Select 1 from v_unita_organizzativa_valida 
	      	  	   Where esercizio = aEs
		 	     And fl_cds = 'N'
			     and cd_unita_organizzativa  =u.cd_unita_organizzativa)
			     order by u.cd_unita_organizzativa 
   	) Loop
       Begin
    	Begin
    	esitoOk := 'S';
    	errore := Null;
    	savepoint ELIMINAUOLIQ;
     	   -- Calcolo il pg di liquidazione
           aPgLiq:=CNRCTB575.getNextNumLiquid(aUO.cd_unita_padre, aEs, aUO.cd_unita_organizzativa);
           -- Effettuo il calcolo della liquidazione 
           CNRCTB570.CALCOLALIQUIDAZIONE(aUO.cd_unita_padre, aEs, es_prec, aUO.cd_unita_organizzativa, aPgLiq, aTDtDa, aTDtA, aUser);
           -- Preparo la chiamata a liquidGruppoCORI
           -- Recupero il pg VSX
           aPgCall:=IBMUTL020.vsx_get_pg_call();
           -- Azzera il contatore righe
           aNR:=0;
     	   for aLGC in (select * from liquid_gruppo_cori 
     	    		where esercizio=aEs
        		  and pg_liquidazione = aPgLiq
        		  and cd_cds = aUO.cd_unita_padre
        		  and cd_unita_organizzativa = aUO.cd_unita_organizzativa
        		  and fl_accentrato = 'Y'
        		  And ((es_prec = 'Y')
        		       Or
        		        (es_prec = 'N' And im_liquidato >= 0))
        		for update nowait
     	   ) loop
      		aNR:=aNR+1;
      		insert into VSX_LIQUIDAZIONE_CORI (
       			PG_CALL,
       			PAR_NUM,
       			PROC_NAME,
       			MESSAGETOUSER,
       			CD_CDS,
       			ESERCIZIO,
       			CD_UNITA_ORGANIZZATIVA,
       			CD_CDS_ORIGINE,
       			CD_UO_ORIGINE,
       			PG_LIQUIDAZIONE,
       			PG_LIQUIDAZIONE_ORIGINE,
       			CD_GRUPPO_CR,
       			CD_REGIONE,
       			PG_COMUNE,
       			UTCR,
       			DACR,
       			UTUV,
       			DUVA,
       			PG_VER_REC
      		) values (
       			aPgCall,
       			aNR,
       			'CNRCTB570.vsx_liquida_cori',
       			null,
       			aLGC.CD_CDS,
       			aLGC.ESERCIZIO,
       			aLGC.CD_UNITA_ORGANIZZATIVA,
       			aLGC.CD_CDS_ORIGINE,
       			aLGC.CD_UO_ORIGINE,
       			aLGC.PG_LIQUIDAZIONE,
       			aLGC.PG_LIQUIDAZIONE_ORIGINE,
       			aLGC.CD_GRUPPO_CR,
       			aLGC.CD_REGIONE,
       			aLGC.PG_COMUNE,
       			aLGC.UTCR,
       			aLGC.DACR,
       			aLGC.UTUV,
       			aLGC.DUVA,
       			aLGC.PG_VER_REC
      		);
     	   end loop; -- Fine loop su liquid gruppo cori
     	   CNRCTB570.VSX_LIQUIDA_CORI(aPgCall);

           -- Clean up VSX
           delete from VSX_LIQUIDAZIONE_CORI where
           pg_call = aPgCall;

	Exception when OTHERS then
     	   esitoOk := 'N';
     	   errore := DBMS_UTILITY.FORMAT_ERROR_STACK;
     	   codice_errore := Sqlcode;
     	   rollback to savepoint ELIMINAUOLIQ;
	End;
	 If esitoOk = 'S' Then
	    IBMUTL200.logInf(pg_exec, 'UO:'||aUO.cd_unita_organizzativa||' '||aUO.ds_unita_organizzativa,null,Null);
	 Else  
	    If codice_errore = ibmerr001.WRN_GENERICO Then 
	        Null;
	    Else    
	    	IBMUTL200.logErr(pg_exec, 'UO:'||aUO.cd_unita_organizzativa||' '||aUO.ds_unita_organizzativa, errore,Null);
	    End If;	
	 End If;
	End;  
   	end loop; -- Fine loop su UO
   	IBMUTL205.LOGINF(aTitolo,
                     aTitolo||' ' ||TO_CHAR(sysdate,'DD/MM/YYYY HH:MI:SS'),
                     'Operazione completata con successo. Progressivo: '|| pg_exec,aUser);
                     
     Exception
   	when others then
     	   -- Messaggio di attenzione ad utente
    	   IBMUTL205.LOGWAR(aTitolo,
                     aTitolo||' ' || TO_CHAR(sysdate,'DD/MM/YYYY HH:MI:SS') ||
                     ' (pg_exec=' || pg_exec || ')',DBMS_UTILITY.FORMAT_ERROR_STACK,aUser);
            Null;
    End;
 End;

 Procedure job_liquid_cori_massa
   (
    aEs NUMBER, es_prec VARCHAR2, aDtDa DATE, aDtA DATE, aUser VARCHAR2
   ) Is
   aProcedure VARCHAR2(2000);
   aDtInizio VARCHAR2(50);
   aDtFine VARCHAR2(50);
   pg_exec NUMBER;
   job number:=null;
 Begin
      aDtInizio:='TO_DATE(' || '''' || TO_CHAR(aDtDa,'DDMMYYYY') || '''' || ',''DDMMYYYY'')';
      aDtFine:='TO_DATE(' || '''' || TO_CHAR(aDtA,'DDMMYYYY') || '''' || ',''DDMMYYYY'')';

      aProcedure:='CNRCTB573.job_liquid_cori_massa_istituti(job, pg_exec, next_date, ' ||
                  aEs || ', ''' || es_prec || ''',' ||
                  aDtInizio || ',' || aDtFine || ', ''' || aUser || ''');';
                  
     IBMUTL210.CREABATCHDINAMICO('Liquidazione cori per tutte le UO',
                                 aProcedure,
                                 aUser);

     IBMUTL001.deferred_commit;
     IBMERR001.RAISE_ERR_GENERICO
         ('Operazione sottomessa per esecuzione. Al completamento l''utente ricever? un messaggio di notifica ' ||
          'dello stato dell''operazione');
 End;
End;


