package br.com.zup.proposta.commons.utils;

import org.springframework.stereotype.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import javax.persistence.*;
import java.security.*;
import java.util.*;

@Component
public class EncryptConverter implements AttributeConverter<String, String> {

    private static final String AES = "AES";
    private static final String SECRET = "79ac63c7d9fdaae1";

    private final Key key;
    private final Cipher cipher;

    public EncryptConverter() throws Exception {
        key = new SecretKeySpec(SECRET.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new IllegalStateException(e);
        }
    }

}