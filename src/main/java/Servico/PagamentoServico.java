package Servico;

import Dados.PagamentoDAO;
import Dados.ReservaDAO;
import Excecoes.ReservaNaoEncontradaException;
import Excecoes.PagamentoInvalidoException;
import Modelo.Pagamento;
import Modelo.Reserva;

import java.util.List;

public class PagamentoServico {

    private PagamentoDAO pagamentoDAO;
    private ReservaDAO reservaDAO;

    public PagamentoServico() {
        this.pagamentoDAO = new PagamentoDAO();
        this.reservaDAO = new ReservaDAO();
    }

    public String registrarPagamento(Pagamento pagamento) throws ReservaNaoEncontradaException, PagamentoInvalidoException, Exception {

        List<Pagamento> pagamentos = pagamentoDAO.carregar();
        List<Reserva> reservas = reservaDAO.carregar();

        boolean reservaEncontrada = false;

        for (Reserva r : reservas) {
            if (r.getId() == pagamento.getIdReserva()) {

                if (r.getStatus().equals("Paga")) {
                    throw new PagamentoInvalidoException(
                            "A reserva " + r.getId() + " já está paga."
                    );
                }
                if (pagamento.getValorPago() < r.getValorTotal()) {
                    throw new PagamentoInvalidoException ("Pagamento insuficiente. Valor total da reserva: R$ " + r.getValorTotal()
                    );
                }

                r.setStatus("Paga");
                reservaEncontrada = true;
                break;
            }
        }

        if (!reservaEncontrada) {
            throw new ReservaNaoEncontradaException(
                    "A reserva com ID " + pagamento.getIdReserva() + " não foi encontrada."
            );
        }

        reservaDAO.salvar(reservas);
        pagamentos.add(pagamento);
        pagamentoDAO.salvar(pagamentos);

        return "Pagamento registrado com sucesso!";
    }
}
