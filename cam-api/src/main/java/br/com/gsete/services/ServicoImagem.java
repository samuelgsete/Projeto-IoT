package br.com.gsete.services;

import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import br.com.gsete.models.Imagem;
import br.com.gsete.respository.RepositorioImagem;

public class ServicoImagem {

    @Inject
    private RepositorioImagem repositorio;

    public List<Imagem> todosasImagens() {
        return repositorio.findAll();
    }

    public Imagem buscarPorId(Long id) {
        if(id > 0) {
            return repositorio.findBy(id);
        }
        return null;
    }

    @Transactional
    public void salvarOuAtualizar(Imagem imagem) {
        if(imagem != null) {
            repositorio.save(imagem);
        }
    }

    @Transactional
    public void removerImagem(Long id) {
        if(id > 0) {
            repositorio.remove(buscarPorId(id));
        }
    }
}