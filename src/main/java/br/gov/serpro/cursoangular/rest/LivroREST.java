package br.gov.serpro.cursoangular.rest;

import br.gov.frameworkdemoiselle.BadRequestException;
import br.gov.frameworkdemoiselle.NotFoundException;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Strings;
import br.gov.frameworkdemoiselle.util.ValidatePayload;
import br.gov.serpro.cursoangular.business.LivroBC;
import br.gov.serpro.cursoangular.entity.Livro;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("livros")
public class LivroREST {

    @Inject
    private LivroBC bc;

    @GET
    @Produces("application/json")
    public List<Livro> find(@QueryParam("q") String query) throws Exception {
        List<Livro> result;

        if (Strings.isEmpty(query)) {
            bc.load();
            result = bc.findAll();
        } else {
            result =  bc.find(query);
        }
        return result;
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Livro load(@PathParam("id") Long id) throws Exception {
        Livro result = bc.load(id);

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
    public Response insert(Livro livro, @Context UriInfo uriInfo) throws Exception {
        checkId(livro);

        String id = bc.salvar(livro).getId().toString();
        URI location = uriInfo.getRequestUriBuilder().path(id).build();

        return Response.created(location).entity(id).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @ValidatePayload
    @Produces("application/json")
    @Consumes("application/json")
    public void update(@PathParam("id") Long id, Livro livro) throws Exception {
//        checkId(livro);
        load(id);

        livro.setId(id);
        bc.salvar(livro);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) throws Exception {
        load(id);
        bc.delete(id);
    }

    private void checkId(Livro entity) throws Exception {
        if (entity.getId() != null) {
            throw new BadRequestException();
        }
    }

}
