package Servico;

import Dados.ReservaDAO;
import Modelo.Reserva;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ReservaServico {

    private ReservaDAO dao = new ReservaDAO();


    // Criar reserva
    public String criarReserva(Reserva nova) {
        try {
            List<Reserva> reservas = dao.carregar();

            // validar datas
            if (nova.getDataInicio().isAfter(nova.getDataFim())) {
                throw new IllegalArgumentException("Data inicial não pode ser depois da final.");
            }

            // verificar sobreposição
            for (Reserva r : reservas) {

                boolean mesmoEspaco = r.getEspaco().getId() == nova.getEspaco().getId();

                boolean sobrepoe =
                        nova.getDataInicio().isBefore(r.getDataFim()) &&
                                nova.getDataFim().isAfter(r.getDataInicio());

                if (mesmoEspaco && sobrepoe) {
                    throw new IllegalArgumentException("Já existe uma reserva nesse período.");
                }
            }

            // calcular custo
            double horas = calcularHoras(nova.getDataInicio(), nova.getDataFim());
            double valor = nova.getEspaco().calcularCustoReserva(horas);
            nova.setValorTotal(valor);

            reservas.add(nova);
            dao.salvar(reservas);

            return "Reserva criada com sucesso!";

        } catch (Exception e) {
            return "Erro ao criar reserva: " + e.getMessage();
        }
    }


    // cálculo de horas
    public double calcularHoras(LocalDateTime inicio, LocalDateTime fim) {
        long minutos = Duration.between(inicio, fim).toMinutes();
        return minutos / 60.0;
    }


    // cancelar reserva
    public String cancelarReserva(int id) throws Exception {

        List<Reserva> reservas = dao.carregar();

        for (Reserva r : reservas) {

            if (r.getId() == id) {

                LocalDateTime agora = LocalDateTime.now();

                long minutos = Duration.between(agora, r.getDataInicio()).toMinutes();
                double horasRestantes = minutos / 60.0;

                double taxa = 0;

                if (horasRestantes < 24) {
                    taxa = r.getValorTotal() * 0.20;
                }

                r.setStatus("Cancelada");

                dao.salvar(reservas);

                return "Reserva cancelada. Taxa aplicada: R$ " + taxa;
            }
        }

        return "Reserva não encontrada.";
    }
}
