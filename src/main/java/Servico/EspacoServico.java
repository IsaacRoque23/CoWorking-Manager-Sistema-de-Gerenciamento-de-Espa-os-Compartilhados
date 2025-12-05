package Servico;

import Dados.EspacoDAO;
import Excecoes.*;
import Modelo.*;

import java.util.List;

public class EspacoServico {

    private final EspacoDAO dao;

    public EspacoServico(EspacoDAO dao) {
        this.dao = dao;
    }

    private void validarId(int id) throws EntradaInvalidaException {
        if (id <= 0) {
            throw new EntradaInvalidaException("ID inválido! O ID deve ser um número inteiro positivo.");
        }
    }

    private void validarNomeUnico(String nome) throws Exception {
        boolean existe = listarTodos().stream()
                .anyMatch(e -> e.getNome().equalsIgnoreCase(nome));
        if (existe) {
            throw new EspacoJaExisteException("Já existe um espaço com o nome: \"" + nome + "\".");
        }
    }

    public Espaco buscarPorId(int id) throws Exception {

        validarId(id);

        for (Espaco e : dao.carregar()) {
            if (e.getId() == id) return e;
        }
        throw new EspacoNaoEncontradoException("Espaço com ID " + id + " não encontrado.");
    }

    public List<Espaco> listarTodos() throws Exception {
        return dao.carregar();
    }

    public void cadastrarCabineIndividual(int id, String nome, int capacidade, boolean disponivel) throws Exception {
        validarId(id);
        validarNomeUnico(nome);
        CabineIndividual cabine = new CabineIndividual(id, nome, capacidade, disponivel);
        List<Espaco> lista = dao.carregar();
        lista.add(cabine);
        dao.salvar(lista);
    }

    public void cadastrarSalaDeReuniao(int id, String nome, int capacidade, boolean disponivel, boolean usarProjetor) throws Exception {
        validarId(id);
        validarNomeUnico(nome);
        SalaDeReuniao sala = new SalaDeReuniao(id, nome, capacidade, disponivel, usarProjetor);
        List<Espaco> lista = dao.carregar();
        lista.add(sala);
        dao.salvar(lista);
    }

    public void cadastrarAuditorio(int id, String nome, int capacidade, boolean disponivel) throws Exception {
        validarId(id);
        validarNomeUnico(nome);
        Auditorio auditorio = new Auditorio(id, nome, capacidade, disponivel);
        List<Espaco> lista = dao.carregar();
        lista.add(auditorio);
        dao.salvar(lista);
    }

    private void editarEspaco(Espaco espaco, String novoNome, int novaCapacidade, boolean novoDisponivel) throws Exception {
        if (!espaco.getNome().equalsIgnoreCase(novoNome)) {
            validarNomeUnico(novoNome);
        }
        if (novaCapacidade <= 0) {
            throw new EntradaInvalidaException("Capacidade inválida.");
        }

        espaco.setNome(novoNome);
        espaco.setCapacidade(novaCapacidade);
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

    public void editarCabineIndividual(int id, String novoNome, int novaCapacidade, boolean novoDisponivel) throws Exception {
        validarId(id);
        CabineIndividual cabine = (CabineIndividual) buscarPorId(id);
        editarEspaco(cabine, novoNome, novaCapacidade, novoDisponivel);
    }

    public void editarSalaDeReuniao(int id, String novoNome, int novaCapacidade, boolean novoDisponivel, boolean usarProjetor) throws Exception {
        validarId(id);
        SalaDeReuniao sala = (SalaDeReuniao) buscarPorId(id);
        editarEspaco(sala, novoNome, novaCapacidade, novoDisponivel);
        sala.setUsarProjetor(usarProjetor);
    }

    public void editarAuditorio(int id, String novoNome, int novaCapacidade, boolean novoDisponivel) throws Exception {
        validarId(id);
        Auditorio auditorio = (Auditorio) buscarPorId(id);
        editarEspaco(auditorio, novoNome, novaCapacidade, novoDisponivel);
    }

    public void removerEspaco(int id) throws Exception {
        validarId(id);

        List<Espaco> lista = dao.carregar();
        boolean removido = lista.removeIf(e -> e.getId() == id);
        if (!removido) {
            throw new EspacoNaoEncontradoException("Espaço com ID " + id + " não encontrado.");
        }
        dao.salvar(lista);
    }
}
