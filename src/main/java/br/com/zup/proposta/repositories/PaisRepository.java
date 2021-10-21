package br.com.zup.proposta.repositories;

import br.com.zup.proposta.models.*;
import org.springframework.data.repository.*;

public interface PaisRepository extends CrudRepository<Pais, Long> {

    boolean existsByNome (String nome);

}
