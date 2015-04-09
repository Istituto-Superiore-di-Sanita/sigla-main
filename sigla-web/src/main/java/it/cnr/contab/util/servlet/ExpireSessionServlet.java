package it.cnr.contab.util.servlet;

import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.utenze00.bulk.SessionTraceBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.ejb.CRUDComponentSession;
import it.cnr.jada.util.Log;
import it.cnr.jada.util.ejb.HttpEJBCleaner;
import it.cnr.jada.util.jsp.JSPUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ExpireSessionServlet extends HttpServlet implements Serializable,HttpSessionListener {
	@SuppressWarnings("unchecked")
	public  final static Hashtable<String,HttpSession> sessionObjects = new Hashtable();
	private static final Logger log = Log.getInstance(ExpireSessionServlet.class);
	
	public ExpireSessionServlet() {
		super();
	}
	@Override
	public void init() throws ServletException {
		super.init();
		new Thread(new ExpireThread()).start();	
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String invalidateSession = req.getParameter("sessionID");
		if (invalidateSession != null && sessionObjects.get(invalidateSession) != null){
			sessionObjects.get(invalidateSession).invalidate();
		}else{
			int indice = 0;
			resp.setContentType("text/html");			
			resp.getWriter().println("<body bgcolor=\"#C4CAD4\"><em><font color=\"#6666aa\" size=\"2\" face=\"Arial, Helvetica, sans-serif\">");		
			resp.getWriter().println("<TABLE border=1>");
			resp.getWriter().println("<TR><TD align=center>Num.</TD><TD align=center>User</TD><TD align=center>Session Id</TD><TD align=center>Creation Time</TD><TD align=center>Last Access Time</TD></TR>");
	    	for (Enumeration sessions = sessionObjects.elements();sessions.hasMoreElements();){
				try{
		    		HttpSession session = (HttpSession)sessions.nextElement();
					String id = session.getId();
					Date creationDate = new Date(session.getCreationTime());
					Date lastAccessedTime = new Date(session.getLastAccessedTime());
					UserContext userContext = (UserContext) session.getAttribute("UserContext");
		    		indice++;
		    		resp.getWriter().println("<TR>");
		    		resp.getWriter().println("<TD>");
		    		resp.getWriter().println(indice);
		    		resp.getWriter().println("</TD>");	    		
		    		resp.getWriter().println("<TD>");
		    		resp.getWriter().println(userContext==null?"nbsp;":userContext.getUser());
		    		resp.getWriter().println("</TD>");	    		
		    		resp.getWriter().println("<TD>");
		    		resp.getWriter().println(id);
		    		resp.getWriter().println("</TD>");
		    		resp.getWriter().println("<TD>");
		    		resp.getWriter().println(creationDate);
		    		resp.getWriter().println("</TD>");
		    		resp.getWriter().println("<TD>");
		    		resp.getWriter().println(lastAccessedTime);
		    		resp.getWriter().println("</TD>");
		    		resp.getWriter().println("</TR>");
				}catch(IllegalStateException e){
				}
	    	}		
			resp.getWriter().println("</TABLE>");
			resp.getWriter().println("</font></em></body>");
		}
	}
		
	class ExpireThread implements Runnable {
		public void run()
		{
			while (true){
			  try{
				Thread.sleep(1000*60);
				expireSession();
			  }
			  catch(Throwable e){
			  }
			}				
		}
		ExpireThread()
		{
		}
	}
    private void expireSession(){
    	for (Enumeration sessions = sessionObjects.elements();sessions.hasMoreElements();){
            long timeNow;
            timeNow = System.currentTimeMillis();
    		HttpSession session = (HttpSession)sessions.nextElement();
    		int maxInactiveInterval;
    		maxInactiveInterval = session.getMaxInactiveInterval();
            if(maxInactiveInterval < 0)
                continue;
            int timeIdle = (int)((timeNow - session.getLastAccessedTime()) / 1000L);
            if(timeIdle < maxInactiveInterval)
                continue;
    		try {
    			SessionTraceBulk sessionTrace = (SessionTraceBulk)createCRUDComponentSession().inizializzaBulkPerModifica(new CNRUserContext("SESSIONTRACE",session.getId(),null,null,null,null), new SessionTraceBulk(session.getId()));
    			sessionTrace.setToBeDeleted();
    			createCRUDComponentSession().eliminaConBulk(new CNRUserContext("SESSIONTRACE",session.getId(),null,null,null,null), sessionTrace);
    		} catch (Exception e) {
    		}
    		HttpEJBCleaner httpejbcleaner = (HttpEJBCleaner)session.getAttribute("it.cnr.jada.util.ejb.HttpEJBCleaner");
    		if (httpejbcleaner != null)
    			httpejbcleaner.remove();
            sessionObjects.remove(session.getId());
            session.invalidate();
    	}
    }
	public void sessionCreated(HttpSessionEvent se) {
		try {
			SessionTraceBulk sessionTrace = new SessionTraceBulk();
			sessionTrace.setId_sessione(se.getSession().getId());
			sessionTrace.setServer_url(" ");
			sessionTrace.setToBeCreated();
			createCRUDComponentSession().creaConBulk(new CNRUserContext("SESSIONTRACE",se.getSession().getId(),null,null,null,null), sessionTrace);
		} catch (Exception e) {
		}
		sessionObjects.put(se.getSession().getId(),se.getSession());
	}
	public void sessionDestroyed(HttpSessionEvent se) {
		UserContext userContext = (UserContext) se.getSession().getAttribute("UserContext");
		if (userContext != null){
	        StringBuffer infoUser = new StringBuffer();
	        infoUser.append("LogOut User:"+userContext.getUser());
	    	log.warn(infoUser.toString());
		}
		try {
			SessionTraceBulk sessionTrace = (SessionTraceBulk)createCRUDComponentSession().inizializzaBulkPerModifica(new CNRUserContext("SESSIONTRACE",se.getSession().getId(),null,null,null,null), new SessionTraceBulk(se.getSession().getId()));
			sessionTrace.setToBeDeleted();
			createCRUDComponentSession().eliminaConBulk(new CNRUserContext("SESSIONTRACE",se.getSession().getId(),null,null,null,null), sessionTrace);
		} catch (Exception e) {
		}
		HttpEJBCleaner httpejbcleaner = (HttpEJBCleaner)se.getSession().getAttribute("it.cnr.jada.util.ejb.HttpEJBCleaner");
		if (httpejbcleaner != null)
			httpejbcleaner.remove();
		sessionObjects.remove(se.getSession().getId());
	}
	public CRUDComponentSession createCRUDComponentSession() throws javax.ejb.EJBException,java.rmi.RemoteException {
		return (CRUDComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("JADAEJB_CRUDComponentSession");
	}
	
}
