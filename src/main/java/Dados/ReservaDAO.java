package Dados;

import java.nio.file.Paths;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import Modelo.Reserva;

public class ReservaDAO extends jsonPersistencia<Reserva> {
    public ReservaDAO() {
        super(Paths.get("reservas.json"), new TypeToken<List<Reserva>>(){}.getType());
    }
}
