package br.com.zup.proposta.repositories;

import br.com.zup.proposta.models.*;
import org.springframework.data.repository.*;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    boolean existsByDocumento (String documento);

    boolean existsByEmail (String email);

}