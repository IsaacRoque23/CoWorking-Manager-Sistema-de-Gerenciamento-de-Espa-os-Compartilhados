package Modelo;

import Excecoes.EntradaInvalidaException;
import Excecoes.HorasInvalidasException;

public class SalaDeReuniao extends Espaco {

    private boolean usarProjetor;
    private static final double PRECO_PADRAO = 120.0;

    public SalaDeReuniao(int id, String nome, int capacidade, boolean disponivel, boolean usarProjetor) throws EntradaInvalidaException {
        super(id, nome,  capacidade, disponivel, PRECO_PADRAO );
        this.usarProjetor = usarProjetor;
    }

    @Override
    public double calcularCustoReserva(double horas) throws HorasInvalidasException {
        if(horas <= 0) {
            throw new HorasInvalidasException("Horas da reserva devem ser maiores que zero");
        }
        double custoTotal = PRECO_PADRAO * horas;

        if (usarProjetor) {
            custoTotal += 15.00;
        }
        return custoTotal;
    }

    public boolean getUsarProjetor() {
        return usarProjetor;
    }

    public void setUsarProjetor(boolean usarProjetor) {
        this.usarProjetor = usarProjetor;
    }

    @Override
    public String getTipo(){
        return "SalaDeReuniao";
    }
}
