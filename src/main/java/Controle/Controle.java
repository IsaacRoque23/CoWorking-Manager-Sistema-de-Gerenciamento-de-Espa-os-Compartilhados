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

    public void cadastrarCabineIndividual(int id, String nome, int capacidade, boolean disponivel, double precoPorHora)
            throws EspacoJaExisteException, EntradaInvalidaException, Exception {
        espacoServico.cadastrarCabineIndividual(id, nome, capacidade, disponivel, precoPorHora);
    }

    public void cadastrarSalaDeReuniao(int id, String nome, int capacidade, boolean disponivel,
                                       double precoPorHora, boolean usarProjetor)
            throws EspacoJaExisteException, EntradaInvalidaException, Exception {
        espacoServico.cadastrarSalaDeReuniao(id, nome, capacidade, disponivel, precoPorHora, usarProjetor);
    }

    public void cadastrarAuditorio(int id, String nome, int capacidade, boolean disponivel, double precoPorHora)
            throws EspacoJaExisteException, EntradaInvalidaException, Exception {
        espacoServico.cadastrarAuditorio(id, nome, capacidade, disponivel, precoPorHora);
    }

    public void removerEspaco(int id) throws EspacoNaoEncontradoException, Exception {
        espacoServico.removerEspaco(id);
    }

    public List<Espaco> listarTodosEspacos() throws Exception {
        return espacoServico.listarTodos();
    }

    public Espaco buscarEspacoPorId(int id) throws Exception {
        return espacoServico.buscarPorId(id);
    }

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

    public String registrarPagamento(Pagamento pagamento)
            throws ReservaNaoEncontradaException, PagamentoInvalidoException, Exception {
        return pagamentoServico.registrarPagamento(pagamento);
    }

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
