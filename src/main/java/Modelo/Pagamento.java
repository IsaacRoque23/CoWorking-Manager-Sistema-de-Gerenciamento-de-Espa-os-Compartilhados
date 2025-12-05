package Modelo;

import Excecoes.EntradaInvalidaException;
import Excecoes.PagamentoInvalidoException;

public class Pagamento {

    private int idReserva;
    private double valorPago;
    private String metodo;

    public Pagamento(int idReserva, double valorPago, String metodo)
            throws PagamentoInvalidoException, EntradaInvalidaException {

        if (idReserva <= 0) {
            throw new EntradaInvalidaException("ID da reserva inválido.");
        }

        if (valorPago <= 0) {
            throw new PagamentoInvalidoException("O valor do pagamento deve ser maior que zero.");
        }

        if (metodo == null || metodo.isBlank()) {
            throw new EntradaInvalidaException("Método de pagamento inválido.");
        }

        if (!metodo.equalsIgnoreCase("Pix") && !metodo.equalsIgnoreCase("Cartão") && !metodo.equalsIgnoreCase("Dinheiro")) {
            throw new EntradaInvalidaException("Método de pagamento deve ser Pix, Cartão ou Dinheiro.");
        }

        this.idReserva = idReserva;
        this.valorPago = valorPago;
        this.metodo = metodo;
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

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "idReserva=" + idReserva +
                ", valorPago=" + valorPago +
                ", metodo='" + metodo + '\'' +
                '}';
    }
}
