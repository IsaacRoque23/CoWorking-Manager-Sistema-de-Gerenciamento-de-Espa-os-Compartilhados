package Controle;

import Excecoes.*;
import Modelo.*;
import Servico.*;

import java.time.LocalDateTime;
import java.util.List;

public class Controle {

    private EspacoServico espacoServico;
    private ReservaServico reservaServico;
    private PagamentoServico pagamentoServico;
    private RelatorioServico relatorioServico;

    public Controle(EspacoServico espacoServico, ReservaServico reservaServico,
                    PagamentoServico pagamentoServico, RelatorioServico relatorioServico) {
        this.espacoServico = espacoServico;
        this.reservaServico = reservaServico;
        this.pagamentoServico = pagamentoServico;
        this.relatorioServico = relatorioServico;
    }

    // CABINE
    public void cadastrarCabineIndividual(int id, String nome, int capacidade, boolean disponivel)
            throws EspacoJaExisteException, EntradaInvalidaException, Exception {
        espacoServico.cadastrarCabineIndividual(id, nome, capacidade, disponivel);
    }

    // SALA
    public void cadastrarSalaDeReuniao(int id, String nome, int capacidade, boolean disponivel,
                                       boolean usarProjetor)
            throws EspacoJaExisteException, EntradaInvalidaException, Exception {
        espacoServico.cadastrarSalaDeReuniao(id, nome, capacidade, disponivel, usarProjetor);
    }

    // AUDITÓRIO
    public void cadastrarAuditorio(int id, String nome, int capacidade, boolean disponivel)
            throws EspacoJaExisteException, EntradaInvalidaException, Exception {
        espacoServico.cadastrarAuditorio(id, nome, capacidade, disponivel);
    }

    // REMOVER ESPAÇO
    public void removerEspaco(int id) throws EspacoNaoEncontradoException, Exception {
        espacoServico.removerEspaco(id);
    }

    // LISTAGEM
    public List<Espaco> listarTodosEspacos() throws Exception {
        return espacoServico.listarTodos();
    }

    public Espaco buscarEspacoPorId(int id) throws Exception {
        return espacoServico.buscarPorId(id);
    }

    // EDIÇÃO DE ESPAÇOS
    public void editarCabineIndividual(int id, String novoNome, int novaCapacidade, boolean novoDisponivel)
            throws EspacoNaoEncontradoException, EntradaInvalidaException, Exception {
        espacoServico.editarCabineIndividual(id, novoNome, novaCapacidade, novoDisponivel);
    }

    public void editarSalaDeReuniao(int id, String novoNome, int novaCapacidade, boolean novoDisponivel, boolean usarProjetor)
            throws EspacoNaoEncontradoException, EntradaInvalidaException, Exception {
        espacoServico.editarSalaDeReuniao(id, novoNome, novaCapacidade, novoDisponivel, usarProjetor);
    }

    public void editarAuditorio(int id, String novoNome, int novaCapacidade, boolean novoDisponivel)
            throws EspacoNaoEncontradoException, EntradaInvalidaException, Exception {
        espacoServico.editarAuditorio(id, novoNome, novaCapacidade, novoDisponivel);
    }

    // RESERVAS
    public String criarReserva(Reserva reserva)
            throws DataInvalidaException, ReservaSobrepostaException, Exception {
        return reservaServico.criarReserva(reserva);
    }

    public String cancelarReserva(int id) throws ReservaInexistenteException, Exception {
        return reservaServico.cancelarReserva(id);
    }

    public double calcularHorasReserva(LocalDateTime inicio, LocalDateTime fim) {
        return reservaServico.calcularHoras(inicio, fim);
    }

    // PAGAMENTOS
    public String registrarPagamento(Pagamento pagamento)
            throws ReservaNaoEncontradaException, PagamentoInvalidoException, Exception {
        return pagamentoServico.registrarPagamento(pagamento);
    }

    // RELATÓRIOS
    public List<Reserva> reservasNoPeriodo(LocalDateTime inicio, LocalDateTime fim) throws Exception {
        return relatorioServico.reservasNoPeriodo(inicio, fim);
    }

    public double faturamentoPorTipo(String tipo) throws Exception {
        return relatorioServico.faturamentoPorTipo(tipo);
    }

    public int usoDoEspaco(int id) throws Exception {
        return relatorioServico.usoDoEspaco(id);
    }
}
