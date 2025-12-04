package Modelo;

import Excecoes.EntradaInvalidaException;
import Excecoes.HorasInvalidasException;

public abstract class Espaco {

    private int id;
    private String nome;
    private int capacidade;
    private boolean disponivel;
    private double precoPorHora;

    public Espaco(int id, String nome, int capacidade, boolean disponivel, double precoPorHora)
            throws EntradaInvalidaException {


        if (id <= 0) {
            throw new EntradaInvalidaException("ID do espaço inválido.");
        }

        if (nome == null || nome.isBlank()) {
            throw new EntradaInvalidaException("Nome do espaço não pode ser vazio.");
        }

        if (capacidade <= 0) {
            throw new EntradaInvalidaException("Capacidade do espaço deve ser maior que zero.");
        }

        if (precoPorHora <= 0) {
            throw new EntradaInvalidaException("Preço por hora deve ser maior que zero.");
        }


        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.disponivel = disponivel;
        this.precoPorHora = precoPorHora;
    }


    public abstract double calcularCustoReserva(double horas) throws HorasInvalidasException;
    public abstract String getTipo();


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public double getPrecoPorHora() {
        return precoPorHora;
    }
    public void setPrecoPorHora(double precoPorHora) {
        this.precoPorHora = precoPorHora;
    }
}
