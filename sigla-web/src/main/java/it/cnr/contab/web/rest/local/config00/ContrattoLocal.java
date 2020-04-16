/*
 * Copyright (C) 2019  Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package it.cnr.contab.web.rest.local.config00;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import it.cnr.contab.config00.contratto.bulk.ContrattoBulk;
import it.cnr.contab.missioni00.docs.bulk.MissioneBulk;
import it.cnr.contab.web.rest.config.SIGLARoles;
import it.cnr.contab.web.rest.config.SIGLASecurityContext;
import it.cnr.contab.web.rest.model.ContrattoDtoBulk;

@Local
@Path("/contratto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(SIGLARoles.CONTRATTO)
@Api("Contratti")
public interface ContrattoLocal {
    /**
     * PUT  /restapi/contratto -> return Contratto
     */
    @PUT
    @ApiOperation(value = "Inserisce un contratto",
            notes = "Accesso consentito solo alle utenze abilitate al ruolo CONTRATTO",
            response = ContrattoDtoBulk.class,
            authorizations = {
                    @Authorization(value = "BASIC"),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_ESERCIZIO),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDS),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_UNITA_ORGANIZZATIVA),
                    @Authorization(value = SIGLASecurityContext.X_SIGLA_CD_CDR),
            }
    )
    public Response insertContratto(@Context HttpServletRequest request, ContrattoDtoBulk contrattoBulk) throws Exception;

}
