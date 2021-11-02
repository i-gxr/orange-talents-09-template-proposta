package br.com.zup.proposta.commons.utils;

import com.google.gson.*;

import java.lang.reflect.*;
import java.time.*;
import java.time.format.*;

public class LocalDateAdapter implements JsonSerializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
    }

}