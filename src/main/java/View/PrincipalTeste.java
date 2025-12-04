package View;

import Modelo.*;
import Servico.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class PrincipalTeste {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        EspacoServico espacoServico = new EspacoServico();
        ReservaServico reservaServico = new ReservaServico();

        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar Sala de Reunião");
            System.out.println("2 - Listar Espaços");
            System.out.println("3 - Criar Reserva");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Capacidade: ");
                    int capacidade = sc.nextInt();
                    System.out.print("Preço por hora: ");
                    double preco = sc.nextDouble();
                    System.out.print("Uso de projetor? (true/false): ");
                    boolean projetor = sc.nextBoolean();

                    SalaDeReuniao sala = new SalaDeReuniao(id, nome, capacidade, true, preco, projetor);
                    espacoServico.cadastrar(sala);
                    System.out.println("Sala cadastrada!");
                    break;

                case 2:
                    System.out.println("Espaços cadastrados:");
                    espacoServico.listar().forEach(e -> System.out.println(
                            e.getId() + " - " + e.getNome() + " | Capacidade: " + e.getCapacidade()));
                    break;

                case 3:
                    System.out.print("ID da reserva: ");
                    int idRes = sc.nextInt();
                    System.out.print("ID do espaço: ");
                    int idEspaco = sc.nextInt();

                    // Buscar espaço pelo ID
                    Espaco espaco = espacoServico.listar().stream()
                            .filter(e -> e.getId() == idEspaco)
                            .findFirst()
                            .orElse(null);

                    if (espaco == null) {
                        System.out.println("Espaço não encontrado!");
                        break;
                    }

                    // Datas simuladas
                    LocalDateTime inicio = LocalDateTime.now().plusHours(1);
                    LocalDateTime fim = LocalDateTime.now().plusHours(3);

                    Reserva reserva = new Reserva(idRes, espaco, inicio, fim);
                    System.out.println(reservaServico.criarReserva(reserva));
                    break;

                case 0:
                    rodando = false;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        sc.close();
        System.out.println("Programa encerrado.");
    }
}
