package Servico;

import Dados.ReservaDAO;
import Excecoes.DataInvalidaException;
import Excecoes.ReservaInexistenteException;
import Excecoes.ReservaSobrepostaException;
import Modelo.Reserva;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ReservaServico {

    private ReservaDAO dao = new ReservaDAO();


    public String criarReserva(Reserva nova) throws DataInvalidaException, ReservaSobrepostaException, Exception {
        List<Reserva> reservas = dao.carregar();


        if (nova.getDataInicio().isAfter(nova.getDataFim())) {
            throw new DataInvalidaException("Data inicial não pode ser depois da final");
        }

        for (Reserva r : reservas) {
            boolean mesmoEspaco = r.getEspaco().getId() == nova.getEspaco().getId();
            boolean sobrepoe = nova.getDataInicio().isBefore(r.getDataFim()) && nova.getDataFim().isAfter(r.getDataInicio());

            if (mesmoEspaco && sobrepoe) {
                throw new ReservaSobrepostaException("Já existe uma reserva nesse período no mesmo espaço.");
            }
        }

        double horas = calcularHoras(nova.getDataInicio(), nova.getDataFim());
        double valor = nova.getEspaco().calcularCustoReserva(horas);
        double valorTotal = Math.round(valor * 100.0) / 100.0; // arredonda para 2 casas decimais
        nova.setValorTotal(valorTotal);


        reservas.add(nova);
        dao.salvar(reservas);

        return "Reserva criada com sucesso!";
    }

    public double calcularHoras(LocalDateTime inicio, LocalDateTime fim) {
        long minutos = Duration.between(inicio, fim).toMinutes();
        return minutos / 60.0;
    }

    public String cancelarReserva(int id) throws ReservaInexistenteException, Exception {
        List<Reserva> reservas = dao.carregar();

        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getId() == id) {
                LocalDateTime agora = LocalDateTime.now();

                long minutos = Duration.between(agora, r.getDataInicio()).toMinutes();
                double horasRestantes = minutos / 60.0;

                double taxa = 0;

                if (horasRestantes > 24) {
                    taxa = r.getValorTotal() * 0.20;
                }

                reservas.remove(i);
                dao.salvar(reservas);

                return "Reserva cancelada! Taxa aplicada: R$ " + taxa;
            }
        }

        throw new ReservaInexistenteException("Reserva não encontrada");
    }

}
