package Controle;

import Excecoes.*;
import Modelo.*;
import Servico.*;

import java.time.LocalDateTime;
import java.util.List;


public class Controle {

    private final EspacoServico  espacoServico;
    private final ReservaServico reservaServico;
    private final PagamentoServico pagamentoServico;
    private final RelatorioServico relatorioServico;

    public Controle(EspacoServico espacoServico, ReservaServico reservaServico, PagamentoServico pagamentoServico, RelatorioServico relatorioServico) {

        this.espacoServico = espacoServico;
        this.reservaServico = reservaServico;
        this.pagamentoServico = pagamentoServico;
        this.relatorioServico = relatorioServico;
    }

    public void cadastrarCabineIndividual(int id, String nome, int capacidade, boolean disponivel) throws EspacoJaExisteException, EntradaInvalidaException, Exception {
        espacoServico.cadastrarCabineIndividual(id, nome, capacidade, disponivel);
    }


    public void cadastrarSalaDeReuniao(int id, String nome, int capacidade, boolean disponivel, boolean usarProjetor) throws EspacoJaExisteException, EntradaInvalidaException, Exception {
        espacoServico.cadastrarSalaDeReuniao(id, nome, capacidade, disponivel, usarProjetor);
    }

    public void cadastrarAuditorio(int id, String nome, int capacidade, boolean disponivel) throws EspacoJaExisteException, EntradaInvalidaException, Exception {
        espacoServico.cadastrarAuditorio(id, nome, capacidade, disponivel);
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

    public void editarCabineIndividual(int id, String novoNome, int novaCapacidade, boolean novoDisponivel) throws EspacoNaoEncontradoException, EntradaInvalidaException, Exception {
        espacoServico.editarCabineIndividual(id, novoNome, novaCapacidade, novoDisponivel);
    }

    public void editarSalaDeReuniao(int id, String novoNome, int novaCapacidade, boolean novoDisponivel, boolean usarProjetor) throws EspacoNaoEncontradoException, EntradaInvalidaException, Exception {
        espacoServico.editarSalaDeReuniao(id, novoNome, novaCapacidade, novoDisponivel, usarProjetor);
    }

    public void editarAuditorio(int id, String novoNome, int novaCapacidade, boolean novoDisponivel) throws EspacoNaoEncontradoException, EntradaInvalidaException, Exception {
        espacoServico.editarAuditorio(id, novoNome, novaCapacidade, novoDisponivel);
    }

    public String criarReserva(Reserva reserva) throws DataInvalidaException, ReservaSobrepostaException, Exception {
        return reservaServico.criarReserva(reserva);
    }

    public String cancelarReserva(int id) throws ReservaInexistenteException, Exception {
        return reservaServico.cancelarReserva(id);
    }


    public String registrarPagamento(Pagamento pagamento)
            throws  Exception {
        return pagamentoServico.registrarPagamento(pagamento);
    }

    public double faturamentoPorTipo(String tipo) throws Exception {
        return relatorioServico.faturamentoPorTipo(tipo);
    }

    public int usoDoEspaco(int id) throws Exception {
        return relatorioServico.usoDoEspaco(id);
    }

    public void exibirEspacosMaisUtilizados() throws Exception {
        relatorioServico.exibirEspacosMaisUtilizados();
    }

}
