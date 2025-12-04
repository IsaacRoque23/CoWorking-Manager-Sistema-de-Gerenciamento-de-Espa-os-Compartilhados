package Modelo;

import Excecoes.DataInvalidaException;
import Excecoes.EntradaInvalidaException;
import Excecoes.PagamentoInvalidoException;

public class Pagamento {

    private int id;
    private int idReserva;
    private double valorPago;
    private String dataPagamento;
    private String metodo;

    public Pagamento(int id, int idReserva, double valorPago, String dataPagamento, String metodo)
            throws PagamentoInvalidoException, EntradaInvalidaException, DataInvalidaException {

        if (id <= 0) {
            throw new EntradaInvalidaException("ID do pagamento inválido.");
        }

        if (idReserva <= 0) {
            throw new EntradaInvalidaException("ID da reserva inválido.");
        }

        if (valorPago <= 0) {
            throw new PagamentoInvalidoException("O valor do pagamento deve ser maior que zero.");
        }

        if (dataPagamento == null || dataPagamento.isBlank()) {
            throw new DataInvalidaException("Data do pagamento não pode ser nula ou vazia.");
        }

        if (metodo == null || metodo.isBlank()) {
            throw new EntradaInvalidaException("Método de pagamento inválido.");
        }
        if (!metodo.equalsIgnoreCase("Pix") && !metodo.equalsIgnoreCase("Cartão") && !metodo.equalsIgnoreCase("Dinheiro")) {

            throw new EntradaInvalidaException("Método de pagamento deve ser Pix, Cartão ou Dinheiro.");
        }


        this.id = id;
        this.idReserva = idReserva;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.metodo = metodo;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdReserva() {
        return idReserva;
    }
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public double getValorPago() {
        return valorPago;
    }
    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public String getDataPagamento() {

        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "id=" + id +
                ", idReserva=" + idReserva +
                ", valorPago=" + valorPago +
                ", dataPagamento='" + dataPagamento + '\'' +
                ", metodo='" + metodo + '\'' +
                '}';
    }
}
