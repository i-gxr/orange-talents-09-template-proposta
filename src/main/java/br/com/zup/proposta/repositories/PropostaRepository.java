package br.com.zup.proposta.repositories;

import br.com.zup.proposta.models.*;
import br.com.zup.proposta.models.enums.*;
import org.springframework.data.repository.*;

import java.util.*;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    boolean existsByDocumento (String documento);

    Optional<Proposta> findByDocumento (String documento);

    List<Proposta> findAllByCartaoNumeroCartaoIsNullAndStatusProposta(StatusProposta statusProposta);

}