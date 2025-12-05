package Modelo;

import Excecoes.DataInvalidaException;
import Excecoes.EntradaInvalidaException;
import java.time.LocalDateTime;

public class Reserva {

    private int id;
    private Espaco espaco;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private double valorTotal;
    private String status;

    public Reserva(int id, Espaco espaco, LocalDateTime dataInicio, LocalDateTime dataFim)
            throws EntradaInvalidaException, DataInvalidaException {

        if (id <= 0) {
            throw new EntradaInvalidaException("ID da reserva inválido.");
        }

        if (espaco == null) {
            throw new EntradaInvalidaException("Espaço não pode ser nulo.");
        }

        if (dataInicio == null || dataFim == null) {
            throw new DataInvalidaException("Datas não podem ser nulas.");
        }

        if (dataInicio.isAfter(dataFim)) {
            throw new DataInvalidaException("Data de início não pode ser após a data de fim.");
        }

        if (valorTotal < 0) {
            throw new EntradaInvalidaException("Valor da reserva não pode ser negativo.");
        }

        if (status == null || status.isBlank()) {
            status = "Pendente";
        }

        this.id = id;
        this.espaco = espaco;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) throws EntradaInvalidaException {
        if (id <= 0) throw new EntradaInvalidaException("ID da reserva inválido.");
        this.id = id;
    }

    public Espaco getEspaco() {
        return espaco;
    }
    public void setEspaco(Espaco espaco) throws EntradaInvalidaException {
        if (espaco == null) throw new EntradaInvalidaException("Espaço não pode ser nulo.");
        this.espaco = espaco;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(LocalDateTime dataInicio) throws DataInvalidaException {
        if (dataInicio == null) throw new DataInvalidaException("Data de início inválida.");
        if (dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new DataInvalidaException("Data de início não pode ser após a data de fim.");
        }
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }
    public void setDataFim(LocalDateTime dataFim) throws DataInvalidaException {
        if (dataFim == null) throw new DataInvalidaException("Data de fim inválida.");
        if (dataInicio != null && dataInicio.isAfter(dataFim)) {
            throw new DataInvalidaException("Data de fim não pode ser antes da data de início.");
        }
        this.dataFim = dataFim;
    }

    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) throws EntradaInvalidaException {
        if (valorTotal < 0) throw new EntradaInvalidaException("Valor da reserva não pode ser negativo.");
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        if (status == null || status.isBlank()) {
            this.status = "Pendente";
        } else {
            this.status = status;
        }
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", espaco=" + espaco.getNome() +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", valorTotal=" + valorTotal +
                ", status='" + status + '\'' +
                '}';
    }
}
