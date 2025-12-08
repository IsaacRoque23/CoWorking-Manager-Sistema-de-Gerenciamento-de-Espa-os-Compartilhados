package View;

import Controle.Controle;
import Dados.*;
import Modelo.*;
import Servico.*;
import Excecoes.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        EspacoDAO espacoDAO = new EspacoDAO();
        ReservaDAO reservaDAO = new ReservaDAO();
        PagamentoDAO pagamentoDAO = new PagamentoDAO();

        EspacoServico espacoServico = new EspacoServico(espacoDAO);
        ReservaServico reservaServico = new ReservaServico();
        PagamentoServico pagamentoServico = new PagamentoServico();
        RelatorioServico relatorioServico = new RelatorioServico(reservaDAO);

        Controle controle = new Controle(espacoServico, reservaServico, pagamentoServico, relatorioServico);

        boolean rodando = true;

        while (rodando) {
            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1 - Cadastrar Cabine Individual");
            System.out.println("2 - Cadastrar Sala de Reunião");
            System.out.println("3 - Cadastrar Auditório");
            System.out.println("4 - Listar Espaços");
            System.out.println("5 - Criar Reserva");
            System.out.println("6 - Cancelar Reserva");
            System.out.println("7 - Registrar Pagamento");
            System.out.println("8 - Exibir Relatório");
            System.out.println("9 - Editar Espaço");
            System.out.println("10 - Remover Espaço");
            System.out.println("0 - Sair");
            System.out.print("opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1: {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Capacidade: ");
                        int capacidade = sc.nextInt();
                        sc.nextLine();
                        controle.cadastrarCabineIndividual(id, nome, capacidade, true);
                        System.out.println("Cabine cadastrada com sucesso!");
                        break;
                    }

                    case 2: {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Capacidade: ");
                        int capacidade = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Vai usar projetor? (S/N): ");
                        boolean usarProjetor = sc.nextLine().trim().equalsIgnoreCase("S");
                        controle.cadastrarSalaDeReuniao(id, nome, capacidade, true, usarProjetor);
                        System.out.println("Sala de reunião cadastrada com sucesso!");
                        break;
                    }

                    case 3: {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Capacidade: ");
                        int capacidade = sc.nextInt();
                        sc.nextLine();
                        controle.cadastrarAuditorio(id, nome, capacidade, true);
                        System.out.println("Auditório cadastrado com sucesso!");
                        break;
                    }

                    case 4: {
                        List<Espaco> lista = controle.listarTodosEspacos();
                        System.out.println("----- Espaços Cadastrados -----");
                        for (Espaco e : lista) {
                            System.out.println(e.getId() + " - " + e.getNome() + " (" + e.getTipo() + ")");
                        }
                        break;
                    }

                    case 5: {
                        try {
                            System.out.print("ID da reserva: ");
                            int id = sc.nextInt();
                            sc.nextLine();

                            System.out.print("ID do espaço: ");
                            int idEspaco = sc.nextInt();
                            sc.nextLine();

                            Espaco espaco = controle.buscarEspacoPorId(idEspaco);
                            if (espaco == null) {
                                System.out.println("Espaço não encontrado. Não é possível criar a reserva.");
                                break;
                            }

                            System.out.print("Data e hora de início (dd/MM/yyyy HH:mm): ");
                            String dataInicioStr = sc.nextLine();
                            System.out.print("Data e hora de fim (dd/MM/yyyy HH:mm): ");
                            String dataFimStr = sc.nextLine();

                            LocalDateTime dataInicio = LocalDateTime.parse(dataInicioStr, formatter);
                            LocalDateTime dataFim = LocalDateTime.parse(dataFimStr, formatter);

                            Reserva reserva = new Reserva(id, espaco, dataInicio, dataFim);
                            String msg = controle.criarReserva(reserva);
                            System.out.println(msg);

                        } catch (Exception e) {
                            System.out.println("Erro ao criar a reserva: " + e.getMessage());
                        }
                        break;
                    }

                    case 6: {
                        System.out.print("ID da reserva para cancelar: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        String msg = controle.cancelarReserva(id);
                        System.out.println(msg);
                        break;
                    }

                    case 7: {
                        System.out.print("ID da reserva: ");
                        int idReserva = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Valor pago: ");
                        double valorPago = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Digite o Método de pagamento (Pix, Cartão, Dinheiro): ");
                        String metodo = sc.nextLine();

                        Pagamento pagamento = new Pagamento(idReserva, valorPago, metodo);
                        String msg = controle.registrarPagamento(pagamento);
                        System.out.println(msg);
                        break;
                    }

                    case 8: {
                        System.out.println("===== Faturamento por tipo de espaço =====");
                        System.out.println("Cabine: R$ " + controle.faturamentoPorTipo("CabineIndividual"));
                        System.out.println("SalaDeReuniao: R$ " + controle.faturamentoPorTipo("SalaDeReuniao"));
                        System.out.println("Auditorio: R$ " + controle.faturamentoPorTipo("Auditorio"));

                        System.out.println("\n===== Uso de cada espaço em horas =====");
                        List<Espaco> listaEspacos = controle.listarTodosEspacos();
                        for (Espaco e : listaEspacos) {
                            System.out.println(e.getNome() + " = " + controle.usoDoEspaco(e.getId()) + " horas");
                        }

                        controle.exibirEspacosMaisUtilizados();
                        break;
                    }

                    case 9: {
                        System.out.print("ID do espaço que deseja editar: ");
                        int idEditar = sc.nextInt();
                        sc.nextLine();

                        Espaco espaco = controle.buscarEspacoPorId(idEditar);

                        System.out.print("Novo nome: ");
                        String novoNome = sc.nextLine();

                        System.out.print("Nova capacidade: ");
                        int novaCapacidade = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Está disponível? (S/N): ");
                        boolean novoDisponivel = sc.nextLine().trim().equalsIgnoreCase("S");

                        if (espaco instanceof SalaDeReuniao) {
                            System.out.print("Vai usar projetor? (S/N): ");
                            boolean usarProjetor = sc.nextLine().trim().equalsIgnoreCase("S");
                            controle.editarSalaDeReuniao(idEditar, novoNome, novaCapacidade, novoDisponivel, usarProjetor);
                        } else if (espaco instanceof CabineIndividual) {
                            controle.editarCabineIndividual(idEditar, novoNome, novaCapacidade, novoDisponivel);
                        } else if (espaco instanceof Auditorio) {
                            controle.editarAuditorio(idEditar, novoNome, novaCapacidade, novoDisponivel);
                        }

                        System.out.println("Espaço editado com sucesso!");
                        break;
                    }

                    case 10: {
                        System.out.print("ID do espaço que deseja remover: ");
                        int idRemover = sc.nextInt();
                        sc.nextLine();

                        controle.removerEspaco(idRemover);

                        System.out.println("Espaço removido com sucesso!!");
                        break;
                    }

                    case 0: {
                        rodando = false;
                        System.out.println("Encerrando...");
                        break;
                    }

                    default: {
                        System.out.println("Você digitou uma opção inválida!");
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro! Digite um valor numérico válido.");
                sc.nextLine(); // limpa o buffer
            } catch (EspacoJaExisteException e) {
                System.out.println("Erro! " + e.getMessage());
            } catch (EntradaInvalidaException e) {
                System.out.println("Erro! " + e.getMessage());
            } catch (EspacoNaoEncontradoException e) {
                System.out.println("Erro! " + e.getMessage());
            } catch (DataInvalidaException e) {
                System.out.println("Erro! " + e.getMessage());
            } catch (ReservaSobrepostaException e) {
                System.out.println("Erro! " + e.getMessage());
            } catch (ReservaInexistenteException e) {
                System.out.println("Erro! " + e.getMessage());
            } catch (ReservaNaoEncontradaException e) {
                System.out.println("Erro! " + e.getMessage());
            } catch (PagamentoInvalidoException e) {
                System.out.println("Erro! " + e.getMessage());
            } catch (ValorNegativoException e) {
                System.out.println("Erro! " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }

        sc.close();
    }
}
