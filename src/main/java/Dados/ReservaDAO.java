package Dados;

import Modelo.*;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Paths;
import java.util.List;

public class ReservaDAO extends JsonPersistencia<Reserva> {

    public ReservaDAO() {
        super(
                Paths.get("reservas.json"),
                new GsonBuilder()
                        .registerTypeAdapter(java.time.LocalDateTime.class, new LocalDateTimeAdapter())
                        .registerTypeAdapter(Espaco.class, new EspacoDAO.EspacoAdapter())
                        .setPrettyPrinting()
                        .create(),
                new TypeToken<List<Reserva>>() {}.getType()
        );
    }
}
