package br.com.zup.proposta.repositories;

import br.com.zup.proposta.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

public interface CartaoRepository extends JpaRepository<Cartao, String> {

}
