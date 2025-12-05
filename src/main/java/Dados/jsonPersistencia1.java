package Dados;

import java.util.List;

public interface jsonPersistencia1<T> {
    void salvar(List<T> lista) throws Exception;

    List<T> carregar() throws Exception;
}
