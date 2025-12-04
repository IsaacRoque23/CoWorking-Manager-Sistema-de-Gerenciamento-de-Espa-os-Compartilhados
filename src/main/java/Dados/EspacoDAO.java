package Dados;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import Modelo.Auditorio;
import Modelo.CabineIndividual;
import Modelo.Espaco;
import Modelo.SalaDeReuniao;

public class EspacoDAO extends jsonPersistencia<Espaco> {

    public EspacoDAO() {
        super(
                Paths.get("espacos.json"),
                new GsonBuilder()
                        .registerTypeAdapter(Espaco.class, new EspacoAdapter())
                        .setPrettyPrinting()
                        .create(),
                new TypeToken<List<Espaco>>() {}.getType()
        );
    }

    public static class EspacoAdapter extends TypeAdapter<Espaco> {

        private final Gson gson = new Gson();

        @Override
        public void write(JsonWriter out, Espaco value) throws IOException {
            out.beginObject();

            out.name("tipo").value(value.getClass().getSimpleName());
            out.name("Dados");
            gson.toJson(value, value.getClass(), out);

            out.endObject();
        }

        @Override
        public Espaco read(JsonReader in) throws IOException {
            in.beginObject();

            String tipo = null;
            Espaco objeto = null;

            while (in.hasNext()) {
                String campo = in.nextName();

                switch (campo) {
                    case "tipo" -> tipo = in.nextString();

                    case "Dados" -> {
                        if (tipo == null)
                            throw new IOException("Campo 'tipo' ausente na leitura do arquivo JSON.");

                        objeto = switch (tipo) {
                            case "Auditorio" -> gson.fromJson(in, Auditorio.class);
                            case "CabineIndividual" -> gson.fromJson(in, CabineIndividual.class);
                            case "SalaDeReuniao" -> gson.fromJson(in, SalaDeReuniao.class);
                            default -> throw new IOException("Tipo desconhecido: " + tipo);
                        };
                    }
                }
            }

            in.endObject();
            return objeto;
        }
    }
}
