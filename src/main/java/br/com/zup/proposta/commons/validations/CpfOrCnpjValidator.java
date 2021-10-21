package br.com.zup.proposta.commons.validations;

import br.com.zup.proposta.commons.validations.utils.*;
import br.com.zup.proposta.controllers.requests.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;

@Component
public class CpfOrCnpjValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PropostaRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors())
            return;

        PropostaRequest request = (PropostaRequest) target;
        String cpfOrCnpj = request.getDocumento();

        if (!CpfCnpjUtils.isValid(cpfOrCnpj))
            errors.rejectValue("documento", null,
                    "O documento informado não corresponde a um CPF/CNPJ: "
                            + request.getDocumento());
    }

}
