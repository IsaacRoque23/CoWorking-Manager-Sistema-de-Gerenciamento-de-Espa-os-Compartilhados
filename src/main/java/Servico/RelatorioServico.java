package Servico;

import Dados.ReservaDAO;
import Modelo.Reserva;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RelatorioServico {

    private ReservaDAO reservaDAO = new ReservaDAO();


    // 1) reservas em um período
    public List<Reserva> reservasNoPeriodo(LocalDateTime inicio, LocalDateTime fim) throws Exception {

        List<Reserva> todas = reservaDAO.carregar();
        List<Reserva> resultado = new ArrayList<>();

        for (Reserva r : todas) {
            if (r.getDataInicio().isAfter(inicio) && r.getDataFim().isBefore(fim)) {
                resultado.add(r);
            }
        }

        return resultado;
    }



    public double faturamentoPorTipo(String tipo) throws Exception {

        List<Reserva> todas = reservaDAO.carregar();
        double soma = 0;

        for (Reserva r : todas) {
            if (r.getEspaco().getTipo().equalsIgnoreCase(tipo)) {
                if (r.getStatus().equalsIgnoreCase("Paga")) {
                    soma += r.getValorTotal();
                }
            }
        }

        return soma;
    }


    // Espaçoes mais utilizados
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
