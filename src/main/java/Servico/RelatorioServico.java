package Servico;

import Dados.ReservaDAO;
import Modelo.Reserva;
import Modelo.Espaco;

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

        for (Reserva r : todas) {
            if (r.getEspaco() == null) continue;

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
            if (r.getEspaco() != null && r.getEspaco().getTipo().equalsIgnoreCase(tipo)
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
            if (r.getEspaco() != null && r.getEspaco().getId() == id) {
                contador++;
            }
        }

        return contador;
    }

    public void exibirEspacosMaisUtilizados() throws Exception {
        List<Reserva> todas = reservaDAO.carregar();
        List<Espaco> espacos = new ArrayList<>();


        for (Reserva r : todas) {
            if (r.getEspaco() != null && !espacos.contains(r.getEspaco())) {
                espacos.add(r.getEspaco());
            }
        }


        for (int i = 0; i < espacos.size(); i++) {
            for (int j = i + 1; j < espacos.size(); j++) {
                int usoI = usoDoEspaco(espacos.get(i).getId());
                int usoJ = usoDoEspaco(espacos.get(j).getId());
                if (usoJ > usoI) {
                    Espaco temp = espacos.get(i);
                    espacos.set(i, espacos.get(j));
                    espacos.set(j, temp);
                }
            }
        }

        System.out.println("===== Espaços mais utilizados =====");
        for (Espaco e : espacos) {
            System.out.println(e.getNome() + " = " + usoDoEspaco(e.getId()) + " reservas");
        }
    }
}
