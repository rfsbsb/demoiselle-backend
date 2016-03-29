package br.gov.serpro.cursoangular.rest;

import br.gov.frameworkdemoiselle.BadRequestException;
import br.gov.frameworkdemoiselle.NotFoundException;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Strings;
import br.gov.frameworkdemoiselle.util.ValidatePayload;
import br.gov.serpro.cursoangular.business.AutorBC;
import br.gov.serpro.cursoangular.entity.Autor;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("autores")
public class AutorREST {

    @Inject
    private AutorBC bc;

    @GET
    @Produces("application/json")
    public List<Autor> find(@QueryParam("q") String query) throws Exception {
        List<Autor> result;

        if (Strings.isEmpty(query)) {
            result = bc.findAll();
        } else {
            result =  bc.find(query);
        }
        return result;
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Autor load(@PathParam("id") Long id) throws Exception {
        Autor result = bc.load(id);

        if (result == null) {
            throw new NotFoundException();
        }

        return result;
    }

    @POST
    @Transactional
    @ValidatePayload
    @Produces("application/json")
    @Consumes("application/json")
    public Response insert(Autor autor, @Context UriInfo uriInfo) throws Exception {
        checkId(autor);

        String id = bc.insert(autor).getId().toString();
        URI location = uriInfo.getRequestUriBuilder().path(id).build();

        return Response.created(location).entity(id).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @ValidatePayload
    @Produces("application/json")
    @Consumes("application/json")
    public void update(@PathParam("id") Long id, Autor autor) throws Exception {
//        checkId(autor);
        load(id);

        autor.setId(id);
        bc.update(autor);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) throws Exception {
        load(id);
        bc.delete(id);
    }

    private void checkId(Autor entity) throws Exception {
        if (entity.getId() != null) {
            throw new BadRequestException();
        }
    }

}
