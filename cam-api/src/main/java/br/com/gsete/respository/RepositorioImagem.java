package br.com.gsete.respository;

import org.apache.deltaspike.data.api.FullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import br.com.gsete.models.Imagem;

@Repository
public interface RepositorioImagem extends FullEntityRepository<Imagem, Long> {
    
}