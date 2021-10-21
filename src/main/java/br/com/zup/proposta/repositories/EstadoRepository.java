package br.com.zup.proposta.repositories;

import br.com.zup.proposta.models.*;
import org.springframework.data.repository.*;

import java.util.*;

public interface EstadoRepository extends CrudRepository<Estado, Long> {

    Optional<Estado> findByIdAndPaisId(Long idEstado, Long idPais);

    boolean existsByNomeAndPaisId (String nome, Long idPais);

}
