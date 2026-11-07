package br.edu.ifsp;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;

// Ensina o Gson a converter LocalDate <-> texto "2026-11-01" no JSON
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    // Java -> JSON
    @Override
    public void write(JsonWriter out, LocalDate data) throws IOException {
        if (data == null) {
            out.nullValue();          // escreve null
        } else {
            out.value(data.toString()); // LocalDate.toString() já devolve "2026-11-01"
        }
    }

    // JSON -> Java
    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) { // se o valor for null no JSON
            in.nextNull();
            return null;
        }
        return LocalDate.parse(in.nextString()); // converte "2026-11-01" em LocalDate
    }
}