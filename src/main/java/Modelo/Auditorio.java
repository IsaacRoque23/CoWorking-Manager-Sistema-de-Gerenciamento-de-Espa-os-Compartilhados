package Modelo;

import Excecoes.EntradaInvalidaException;
import Excecoes.HorasInvalidasException;

public class Auditorio extends Espaco {

    private static final double PRECO_PADRAO = 250.0;


    public Auditorio(int id, String nome, int capacidade, boolean disponivel) throws EntradaInvalidaException {
        super(id, nome, capacidade, disponivel, PRECO_PADRAO);
    }

    @Override
    public double calcularCustoReserva(double horas) throws HorasInvalidasException {
        if (horas <= 0) {
            throw new HorasInvalidasException("Horas da reserva devem ser maiores que zero.");
        }
        double custoTotal = ( PRECO_PADRAO * horas) + 100;

        return custoTotal;
    }

    @Override
    public String getTipo(){
        return "Auditorio";
    }
}
