package com.tasklist.util;

import com.tasklist.model.Tarefa;
import com.tasklist.service.GerenciadorDeTarefas;
import com.tasklist.model.Status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main ( String[] args ) {
        GerenciadorDeTarefas gerenciador = new GerenciadorDeTarefas();
        Scanner scanner = new Scanner (System.in);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int opcao;

        do {
            System.out.println("\n--- Gerenciador de Tarefas ---");
            System.out.println("1. Cadastrar nova tarefa");
            System.out.println("2. Listar tarefas");
            System.out.println("3. Editar tarefa");
            System.out.println("4. Deletar tarefa");
            System.out.println("5. Consultar tarefas em vencimento");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha
            } catch (java.util.InputMismatchException e) {
                System.out.println("Opção inválida. Por favor, digite um número.");
                scanner.nextLine(); // Limpar o buffer do scanner
                opcao = -1; // Para continuar o loop
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Cadastrar nova tarefa ---");
                    System.out.print("Título (máximo 20 caracteres): ");
                    String titulo = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Data limite (yyyy-MM-dd): ");
                    String dataLimiteStr = scanner.nextLine();
                    try {
                        LocalDate dataLimite = LocalDate.parse(dataLimiteStr, dateFormatter);
                        gerenciador.cadastrarTarefa(titulo, descricao, dataLimite);
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato de data inválido. Use yyyy-MM-dd.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro ao cadastrar tarefa: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n--- Listar tarefas ---");
                    System.out.println("a) Listar todas");
                    System.out.println("b) Filtrar por título");
                    System.out.println("c) Filtrar por data de vencimento");
                    System.out.print("Escolha uma opção: ");
                    String listarOpcao = scanner.nextLine().toLowerCase();
                    if (listarOpcao.equals("a")) {
                        gerenciador.ListarTarefas(null, null).forEach(System.out::println);
                    } else if (listarOpcao.equals("b")) {
                        System.out.print("Digite o título para filtrar: ");
                        String filtroTitulo = scanner.nextLine();
                        gerenciador.ListarTarefas(filtroTitulo, null).forEach(System.out::println);
                    } else if (listarOpcao.equals("c")) {
                        System.out.print("Digite a data de vencimento para filtrar (yyyy-MM-dd): ");
                        String filtroDataStr = scanner.nextLine();
                        try {
                            LocalDate filtroData = LocalDate.parse(filtroDataStr, dateFormatter);
                            gerenciador.ListarTarefas(null, filtroData).forEach(System.out::println);
                        } catch (DateTimeParseException e) {
                            System.out.println("Formato de data inválido. Use yyyy-MM-dd.");
                        }
                    } else {
                        System.out.println("Opção inválida.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Editar tarefa ---");
                    System.out.print("Digite o ID da tarefa para editar: ");
                    try {
                        int idEditar = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha
                        Tarefa tarefaExistente = gerenciador.obterTarefaPorId(idEditar);
                        if (tarefaExistente != null) {
                            System.out.println("Tarefa encontrada: " + tarefaExistente);
                            System.out.print("Novo título (deixe em branco para manter): ");
                            String novoTitulo = scanner.nextLine();
                            System.out.print("Nova descrição (deixe em branco para manter): ");
                            String novaDescricao = scanner.nextLine();
                            System.out.print("Nova data limite (yyyy-MM-dd, deixe em branco para manter): ");
                            String novaDataLimiteStr = scanner.nextLine();
                            LocalDate novaDataLimite = null;
                            if (!novaDataLimiteStr.isEmpty()) {
                                try {
                                    novaDataLimite = LocalDate.parse(novaDataLimiteStr, dateFormatter);
                                } catch (DateTimeParseException e) {
                                    System.out.println("Formato de data inválido para data limite.");
                                }
                            }
                            System.out.print("Nova data de execução (yyyy-MM-dd, deixe em branco para manter): ");
                            String novaDataExecucaoStr = scanner.nextLine();
                            LocalDate novaDataExecucao = null;
                            if (!novaDataExecucaoStr.isEmpty()) {
                                try {
                                    novaDataExecucao = LocalDate.parse(novaDataExecucaoStr, dateFormatter);
                                } catch (DateTimeParseException e) {
                                    System.out.println("Formato de data inválido para data de execução.");
                                }
                            }
                            System.out.println("Novo status:");
                            for (Status s : Status.values()) {
                                System.out.println("- " + s.name());
                            }
                            System.out.print("Digite o novo status (deixe em branco para manter): ");
                            String novoStatusStr = scanner.nextLine().toUpperCase();
                            Status novoStatus = null;
                            if (!novoStatusStr.isEmpty()) {
                                try {
                                    novoStatus = Status.valueOf(novoStatusStr);
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Status inválido.");
                                }
                            }
                            gerenciador.editarTarefa(idEditar, novoTitulo.isEmpty() ? null : novoTitulo,
                                    novaDescricao.isEmpty() ? null : novaDescricao, novaDataLimite,
                                    novaDataExecucao, novoStatus);
                        } else {
                            System.out.println("Tarefa não encontrada.");
                        }
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("ID inválido. Por favor, digite um número.");
                        scanner.nextLine(); // Limpar o buffer do scanner
                    }
                    break;

                case 4:
                    System.out.println("\n--- Deletar tarefa ---");
                    System.out.print("Digite o ID da tarefa para deletar: ");
                    try {
                        int idDeletar = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha
                        gerenciador.deletarTarefa(idDeletar);
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("ID inválido. Por favor, digite um número.");
                        scanner.nextLine(); // Limpar o buffer do scanner
                    }
                    break;

                case 5:
                    System.out.println("\n--- Consultar tarefas em vencimento ---");
                    gerenciador.consultarTarefasEmVencimento();
                    break;

                case 6:
                    System.out.println("\nSaindo do Gerenciador de Tarefas. Até logo!");
                    break;

                default:
                    if (opcao != -1) {
                        System.out.println("Opção inválida. Por favor, escolha uma opção do menu.");
                    }
            }
        } while (opcao != 6);

        scanner.close();
    }
}
