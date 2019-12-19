package br.com.gsete.api.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import br.com.gsete.models.Imagem;
import br.com.gsete.services.ServicoImagem;

@Path("/imagem")
public class RecursoImagem {

    @Inject
    private ServicoImagem servico;

    public RecursoImagem() { }

    @GET
    @Produces("application/json")
    public Response todosAsImagens() {
        return Response
                .ok()
                .entity(servico.todosasImagens())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getImagem(@PathParam("id") Long id) {
        return Response
                .ok()
                .entity(servico.buscarPorId(id))
                .build();
    }

    @POST
	@Consumes("application/json")
    public Response salvarImagem(Imagem imagem) {
        servico.salvarOuAtualizar(imagem);
        return Response.status(Status.CREATED).build();
    } 

    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    public Response removerImagem(@PathParam("id") Long id) {
        servico.removerImagem(id);
        return Response
            .status(Status.NO_CONTENT)
            .build();
    }
}