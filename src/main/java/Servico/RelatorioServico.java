package Servico;

import Dados.ReservaDAO;
import Modelo.Reserva;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RelatorioServico {

    private ReservaDAO reservaDAO;

    public RelatorioServico(ReservaDAO reservaDAO) {
        this.reservaDAO = reservaDAO;
    }

    public List<Reserva> reservasNoPeriodo(LocalDateTime inicio, LocalDateTime fim) throws Exception {

        List<Reserva> todas = reservaDAO.carregar();
        List<Reserva> resultado = new ArrayList<>();

        // pega reservas que INTERSETAM o período
        for (Reserva r : todas) {
            boolean naoTerminaAntes = !r.getDataFim().isBefore(inicio);
            boolean naoComecaDepois = !r.getDataInicio().isAfter(fim);

            if (naoTerminaAntes && naoComecaDepois) {
                resultado.add(r);
            }
        }

        return resultado;
    }

    public double faturamentoPorTipo(String tipo) throws Exception {

        List<Reserva> todas = reservaDAO.carregar();
        double soma = 0;

        for (Reserva r : todas) {
            if (r.getEspaco().getTipo().equalsIgnoreCase(tipo)
                    && r.getStatus().equalsIgnoreCase("Paga")) {
                soma += r.getValorTotal();
            }
        }

        return soma;
    }

    public int usoDoEspaco(int id) throws Exception {

        List<Reserva> todas = reservaDAO.carregar();
        int contador = 0;

        for (Reserva r : todas) {
            if (r.getEspaco().getId() == id) {
                contador++;
            }
        }

        return contador;
    }
}
