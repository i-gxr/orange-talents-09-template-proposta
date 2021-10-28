package br.com.zup.proposta.commons.validations;

import br.com.zup.proposta.commons.validations.utils.*;
import br.com.zup.proposta.requests.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;

import java.util.*;

@Component
public class FingerprintValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BiometriaRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors())
            return;

        BiometriaRequest request = (BiometriaRequest) target;
        String fingerprint = request.getFingerprint();

        Base64.Decoder decoder = Base64.getDecoder();

        try {
            decoder.decode(fingerprint);
        }
            catch (IllegalArgumentException e) {
                errors.rejectValue("fingerprint", null,
                        "O fingerprint informado não está em base64: "
                                + request.getFingerprint());
            }
    }

}
