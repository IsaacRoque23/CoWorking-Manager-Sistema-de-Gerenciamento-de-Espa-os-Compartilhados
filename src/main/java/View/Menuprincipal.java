package View;

import Controle.Controle;
import Dados.*;
import Excecoes.*;
import Modelo.*;
import Servico.EspacoServico;
import Servico.PagamentoServico;
import Servico.RelatorioServico;
import Servico.ReservaServico;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Menuprincipal {

    private static ReservaDAO reservaDAO2;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


        EspacoDAO espacoDAO = new EspacoDAO();
        ReservaDAO reservaDAO = new ReservaDAO();
        PagamentoDAO pagamentoDAO = new PagamentoDAO();


        EspacoServico espacoServico = new EspacoServico(espacoDAO);
        ReservaServico reservaServico = new ReservaServico();
        PagamentoServico pagamentoServico = new PagamentoServico();
        ReservaDAO reservaDAO2 = new ReservaDAO();
        RelatorioServico relatorioServico = new RelatorioServico(reservaDAO2);


        Controle controle = new Controle(
                espacoServico,
                reservaServico,
                pagamentoServico,
                relatorioServico
        );

        boolean rodando = true;

        while (rodando) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Cadastrar Cabine Individual");
            System.out.println("2 - Cadastrar Sala de Reunião");
            System.out.println("3 - Cadastrar Auditório");
            System.out.println("4 - Listar Espaços");
            System.out.println("5 - Criar Reserva");
            System.out.println("6 - Cancelar Reserva");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine(); // Consumir enter

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Capacidade: ");
                        int capacidade = sc.nextInt();
                        System.out.print("Preço por hora: ");
                        double preco = sc.nextDouble();
                        sc.nextLine();
                        controle.cadastrarCabineIndividual(id, nome, capacidade, true, preco);
                        System.out.println("Cabine cadastrada com sucesso!");
                    }
                    case 2 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Capacidade: ");
                        int capacidade = sc.nextInt();
                        System.out.print("Preço por hora: ");
                        double preco = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("Usar projetor? (true/false): ");
                        boolean usarProjetor = sc.nextBoolean();
                        sc.nextLine();
                        controle.cadastrarSalaDeReuniao(id, nome, capacidade, true, preco, usarProjetor);
                        System.out.println("Sala de reunião cadastrada com sucesso!");
                    }
                    case 3 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Capacidade: ");
                        int capacidade = sc.nextInt();
                        System.out.print("Preço por hora: ");
                        double preco = sc.nextDouble();
                        sc.nextLine();
                        controle.cadastrarAuditorio(id, nome, capacidade, true, preco);
                        System.out.println("Auditório cadastrado com sucesso!");
                    }
                    case 4 -> {
                        List<Espaco> lista = controle.listarTodosEspacos();
                        System.out.println("----- Espaços Cadastrados -----");
                        for (Espaco e : lista) {
                            System.out.println(e.getId() + " - " + e.getNome() + " (" + e.getTipo() + ")");
                        }
                    }
                    case 5 -> {
                        System.out.print("ID da reserva: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("ID do espaço: ");
                        int idEspaco = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Data e hora de início (dd/MM/yyyy HH:mm): ");
                        String dataInicioStr = sc.nextLine();
                        System.out.print("Data e hora de fim (dd/MM/yyyy HH:mm): ");
                        String dataFimStr = sc.nextLine();

                        LocalDateTime dataInicio = LocalDateTime.parse(dataInicioStr, formatter);
                        LocalDateTime dataFim = LocalDateTime.parse(dataFimStr, formatter);

                        Espaco espaco = controle.buscarEspacoPorId(idEspaco);
                        Reserva reserva = new Reserva(id, espaco, dataInicio, dataFim);
                        String msg = controle.criarReserva(reserva);
                        System.out.println(msg);
                    }
                    case 6 -> {
                        System.out.print("ID da reserva para cancelar: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        String msg = controle.cancelarReserva(id);
                        System.out.println(msg);
                    }
                    case 0 -> {
                        rodando = false;
                        System.out.println("Saindo...");
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        sc.close();
    }
}
