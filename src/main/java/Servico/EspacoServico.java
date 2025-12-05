package Servico;

import Dados.EspacoDAO;
import Excecoes.*;
import Modelo.*;

import java.util.List;
import java.util.stream.Collectors;

public class EspacoServico {

    private final EspacoDAO dao;

    public EspacoServico(EspacoDAO dao) {
        this.dao = dao;
    }


    private void validarNomeUnico(String nome) throws EspacoJaExisteException {
        boolean existe = listarTodos().stream()
                .anyMatch(e -> e.getNome().equalsIgnoreCase(nome));
        if (existe) {
            throw new EspacoJaExisteException("Já existe um espaço com o nome: \"" + nome + "\".");
        }
    }

    // Buscar por ID
    public Espaco buscarPorId(int id) throws EspacoNaoEncontradoException {
        for (Espaco e : dao.carregar()) {
            if (e.getId() == id) return e;
        }
        throw new EspacoNaoEncontradoException("Espaço com ID " + id + " não encontrado.");
    }


    public List<Espaco> listarTodos() {
        return dao.carregar();
    }


    public <T extends Espaco> List<T> listarPorTipo(Class<T> tipo) {
        return dao.carregar().stream()
                .filter(e -> tipo.isAssignableFrom(e.getClass()))
                .map(e -> (T) e)
                .collect(Collectors.toList());
    }

    public void cadastrarCabineIndividual(int id, String nome, int capacidade, boolean disponivel, double precoPorHora)
            throws EspacoJaExisteException, EntradaInvalidaException, Exception {

        validarNomeUnico(nome);
        CabineIndividual cabine = new CabineIndividual(id, nome, capacidade, disponivel, precoPorHora);

        List<Espaco> lista = dao.carregar();
        lista.add(cabine);
        dao.salvar(lista);
    }

    public void cadastrarSalaDeReuniao(int id, String nome, int capacidade, boolean disponivel, double precoPorHora, boolean usarProjetor)
            throws EspacoJaExisteException, EntradaInvalidaException, Exception {

        validarNomeUnico(nome);
        SalaDeReuniao sala = new SalaDeReuniao(id, nome, capacidade, disponivel, precoPorHora, usarProjetor);

        List<Espaco> lista = dao.carregar();
        lista.add(sala);
        dao.salvar(lista);
    }

    public void cadastrarAuditorio(int id, String nome, int capacidade, boolean disponivel, double precoPorHora)
            throws EspacoJaExisteException, EntradaInvalidaException, Exception {

        validarNomeUnico(nome);
        Auditorio auditorio = new Auditorio(id, nome, capacidade, disponivel, precoPorHora);

        List<Espaco> lista = dao.carregar();
        lista.add(auditorio);
        dao.salvar(lista);
    }


    private void atualizarEspaco(Espaco espaco, String novoNome, int novaCapacidade, double novoPreco, boolean novoDisponivel)
            throws EspacoJaExisteException, EntradaInvalidaException, Exception {

        if (!espaco.getNome().equalsIgnoreCase(novoNome)) {
            validarNomeUnico(novoNome);
        }

        if (novaCapacidade <= 0) throw new EntradaInvalidaException("Capacidade inválida.");
        if (novoPreco <= 0) throw new EntradaInvalidaException("Preço inválido.");

        espaco.setNome(novoNome);
        espaco.setCapacidade(novaCapacidade);
        espaco.setPrecoPorHora(novoPreco);
        espaco.setDisponivel(novoDisponivel);

        List<Espaco> lista = dao.carregar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == espaco.getId()) {
                lista.set(i, espaco);
                break;
            }
        }
        dao.salvar(lista);
    }

    public void atualizarCabineIndividual(int id, String novoNome, int novaCapacidade, double novoPreco, boolean novoDisponivel)
            throws EspacoNaoEncontradoException, EspacoJaExisteException, EntradaInvalidaException, Exception {

        CabineIndividual cabine = (CabineIndividual) buscarPorId(id);
        atualizarEspaco(cabine, novoNome, novaCapacidade, novoPreco, novoDisponivel);
    }

    public void atualizarSalaDeReuniao(int id, String novoNome, int novaCapacidade, double novoPreco, boolean novoDisponivel, boolean usarProjetor)
            throws EspacoNaoEncontradoException, EspacoJaExisteException, EntradaInvalidaException, Exception {

        SalaDeReuniao sala = (SalaDeReuniao) buscarPorId(id);
        atualizarEspaco(sala, novoNome, novaCapacidade, novoPreco, novoDisponivel);
        sala.setUsarProjetor(usarProjetor);
    }

    public void atualizarAuditorio(int id, String novoNome, int novaCapacidade, double novoPreco, boolean novoDisponivel)
            throws EspacoNaoEncontradoException, EspacoJaExisteException, EntradaInvalidaException, Exception {

        Auditorio auditorio = (Auditorio) buscarPorId(id);
        atualizarEspaco(auditorio, novoNome, novaCapacidade, novoPreco, novoDisponivel);
    }

    public void removerEspaco(int id) throws EspacoNaoEncontradoException, Exception {
        List<Espaco> lista = dao.carregar();
        boolean removido = lista.removeIf(e -> e.getId() == id);
        if (!removido) {
            throw new EspacoNaoEncontradoException("Espaço com ID " + id + " não encontrado.");
        }
        dao.salvar(lista);
    }
}
