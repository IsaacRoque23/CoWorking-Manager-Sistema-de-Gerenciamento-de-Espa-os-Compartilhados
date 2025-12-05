package Dados;

import Modelo.Reserva;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Paths;
import java.util.List;

public class ReservaDAO extends jsonPersistencia<Reserva> {

    public ReservaDAO() {
        super(
                Paths.get("reservas.json"),
                new GsonBuilder()
                        .registerTypeAdapter(java.time.LocalDateTime.class, new localDateTimeAdapter())
                        .setPrettyPrinting()
                        .create(),
                new TypeToken<List<Reserva>>() {}.getType()
        );
    }
}
