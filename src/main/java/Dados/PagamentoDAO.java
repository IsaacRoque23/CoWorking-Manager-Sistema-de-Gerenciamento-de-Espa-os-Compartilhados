package Dados;

import Modelo.Pagamento;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Paths;
import java.util.List;

public class PagamentoDAO extends jsonPersistencia<Pagamento> {

    public PagamentoDAO() {
        super(
                Paths.get("pagamentos.json"),
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create(),
                new TypeToken<List<Pagamento>>() {}.getType()
        );
    }
}
