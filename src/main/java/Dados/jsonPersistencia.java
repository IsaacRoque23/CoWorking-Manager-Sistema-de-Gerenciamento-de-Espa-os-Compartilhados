package Dados;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Modelo.Espaco;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class jsonPersistencia<E> implements jsonPersistencia1<E> {

    private final Path arquivo;
    private final Gson gson;
    private final Type tipoLista;

    protected jsonPersistencia(Path arquivo, Type tipoLista) {
        this(arquivo, new GsonBuilder().setPrettyPrinting().create(), tipoLista);
    }

    protected jsonPersistencia(Path arquivo, Gson gson, Type tipoLista) {
        this.arquivo = arquivo;
        this.tipoLista = tipoLista;
        this.gson = gson;

        try {
            if (!Files.exists(arquivo)) {
                Files.createFile(arquivo);

                try (Writer w = Files.newBufferedWriter(arquivo)) {
                    w.write("[]");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o arquivo: " + arquivo, e);
        }
    }


    @Override
    public synchronized Espaco salvar(List<E> lista) throws Exception {
        try (Writer writer = Files.newBufferedWriter(arquivo)) {
            gson.toJson(lista, tipoLista, writer);
        }
        return null;
    }

    @Override
    public synchronized List<E> carregar() {
        try (Reader reader = Files.newBufferedReader(arquivo)) {
            List<E> lista = gson.fromJson(reader, this.tipoLista );
            if (lista == null) return new ArrayList<>();
            return lista;
        }
        catch(IOException e){
            System.err.println("Não foi possivel ler o arquivo! ");
            System.err.println(e.getMessage());


        }
        return new ArrayList<>();
    }

}
