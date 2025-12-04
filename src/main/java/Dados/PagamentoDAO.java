package Dados;

import java.nio.file.Paths;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import Modelo.Pagamento;

public class PagamentoDAO extends jsonPersistencia<Pagamento> {
    public PagamentoDAO() {
        super(Paths.get("pagamentos.json"), new TypeToken<List<Pagamento>>(){}.getType());
    }
}
