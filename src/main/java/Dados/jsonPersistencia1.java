package Dados;

import Modelo.Espaco;

import java.util.List;

public interface jsonPersistencia1<T> {
    Espaco salvar(List<T> lista) throws Exception;

    List<T> carregar() throws Exception;

}
