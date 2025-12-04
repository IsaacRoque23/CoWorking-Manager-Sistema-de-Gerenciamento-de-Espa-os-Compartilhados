package Modelo;

import Excecoes.EntradaInvalidaException;
import Excecoes.HorasInvalidasException;

public class Auditorio extends Espaco {

    public Auditorio(int id, String nome, int capacidade, boolean disponivel, double precoPorHora) throws EntradaInvalidaException {
        super(id, nome, capacidade, disponivel, precoPorHora);
    }

    @Override
    public double calcularCustoReserva(double horas) throws HorasInvalidasException {
        if (horas <= 0) {
            throw new HorasInvalidasException("Horas da reserva devem ser maiores que zero.");
        }
        double custoTotal = (getPrecoPorHora() * horas) + 100;

        return custoTotal;
    }

    @Override
    public String getTipo(){
        return "Auditorio";
    }
}
