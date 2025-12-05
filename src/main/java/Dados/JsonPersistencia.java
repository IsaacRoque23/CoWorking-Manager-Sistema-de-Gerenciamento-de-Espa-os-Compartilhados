package Dados;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class JsonPersistencia<E> implements persistencia<E> {

    private final Path arquivo;
    private final Gson gson;
    private final Type tipoLista;

    protected JsonPersistencia(Path arquivo, Type tipoLista) {
        this(arquivo, new GsonBuilder().setPrettyPrinting().create(), tipoLista);
    }

    protected JsonPersistencia(Path arquivo, Gson gson, Type tipoLista) {
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
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar arquivo: " + arquivo, e);
        }
    }

    @Override
    public synchronized void salvar(List<E> lista) throws Exception {
        try (Writer writer = Files.newBufferedWriter(arquivo)) {
            gson.toJson(lista, tipoLista, writer);
        }
    }

    @Override
    public synchronized List<E> carregar() throws Exception {
        try (Reader reader = Files.newBufferedReader(arquivo)) {
            List<E> lista = gson.fromJson(reader, tipoLista);
            return lista == null ? new ArrayList<>() : lista;
        }
    }
}
