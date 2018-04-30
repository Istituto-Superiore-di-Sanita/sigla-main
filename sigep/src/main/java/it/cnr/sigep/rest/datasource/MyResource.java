package it.cnr.sigep.rest.datasource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/** Simple resource to test correct deployment and accessibility of {@code MyDS}.
 */
@Path("/datasource")
public class MyResource {

    @GET
    public String get() throws NamingException {
        InitialContext context = new InitialContext();
        DataSource ds = (DataSource) context.lookup("java:/jdbc/CIR");
        return "Found the datasource";
    }
}