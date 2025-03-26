package com.tasklist.service;

import com.tasklist.model.Status;
import com.tasklist.model.Tarefa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorDeTarefas {
    private List<Tarefa> tarefas;
    private static final String NOME_ARQUIVO = "tarefas.txt";

    public GerenciadorDeTarefas() {
        this.tarefas = new ArrayList<>();
        carregarTarefas(); // Carrega as tarefas ao criar o gerenciador
    }

    public void cadastrarTarefa(String titulo, String descricao, LocalDate dataLimite) {
        Tarefa novaTarefa = new Tarefa(titulo, descricao, dataLimite);
        this.tarefas.add(novaTarefa);
        System.out.println("Tarefa cadastrada com sucesso. ID: " + novaTarefa.getId());
    }

    public List<Tarefa> listarTarefas(String filtroTitulo, LocalDate filtroDataVencimento) {
        return this.tarefas.stream()
                .filter(tarefa -> filtroTitulo == null || tarefa.getTitulo().toLowerCase().contains(filtroTitulo.toLowerCase()))
                .filter(tarefa -> filtroDataVencimento == null || tarefa.getDataLimite().equals(filtroDataVencimento))
                .sorted(Comparator.comparing(Tarefa::getDataLimite).reversed())
                .collect(Collectors.toList());
    }

    public Tarefa obterTarefaPorId(int id) {
        return this.tarefas.stream()
                .filter(tarefa -> tarefa.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void editarTarefa(int id, String novoTitulo, String novaDescricao, LocalDate novaDataLimite, LocalDate novaDataExecucao, Status novoStatus) {
        Tarefa tarefaExistente = obterTarefaPorId(id);
        if (tarefaExistente != null) {
            if (novoTitulo != null && !novoTitulo.trim().isEmpty() && novoTitulo.length() <= 20) {
                tarefaExistente.setTitulo(novoTitulo);
            } else if (novoTitulo != null) {
                System.out.println("Aviso: Título inválido. Mantendo o título anterior.");
            }
            if (novaDescricao != null) {
                tarefaExistente.setDescricao(novaDescricao);
            }
            if (novaDataLimite != null && !novaDataLimite.isBefore(LocalDate.now())) {
                tarefaExistente.setDataLimite(novaDataLimite);
            } else if (novaDataLimite != null) {
                System.out.println("Aviso: Data limite inválida. Mantendo a data anterior.");
            }
            if (novaDataExecucao != null) {
                tarefaExistente.setDataExecucao(novaDataExecucao);
            }
            if (novoStatus != null) {
                tarefaExistente.setStatus(novoStatus);
            }
            System.out.println("Tarefa com ID " + id + " editada com sucesso.");
        } else {
            System.out.println("Tarefa com ID " + id + " não encontrada.");
        }
    }

    public void deletarTarefa(int id) {
        Tarefa tarefaParaRemover = obterTarefaPorId(id);
        if (tarefaParaRemover != null) {
            this.tarefas.remove(tarefaParaRemover);
            System.out.println("Tarefa com ID " + id + " deletada com sucesso.");
        } else {
            System.out.println("Tarefa com ID " + id + " não encontrada.");
        }
    }

    public void consultarTarefasEmVencimento() {
        LocalDate hoje = LocalDate.now();
        List<Tarefa> tarefasVencendoHoje = this.tarefas.stream()
                .filter(tarefa -> tarefa.getDataLimite().equals(hoje) && tarefa.getStatus() != Status.CONCLUIDO)
                .collect(Collectors.toList());

        if (!tarefasVencendoHoje.isEmpty()) {
            System.out.println("\nNotificação: As seguintes tarefas vencem hoje:");
            tarefasVencendoHoje.forEach(tarefa -> System.out.println("- " + tarefa.getTitulo()));
            System.out.println("Implementação de envio de e-mail de notificação aqui (assíncrono).");
        } else {
            System.out.println("Não há tarefas vencendo hoje.");
        }
    }

    public void salvarTarefas() {
        ArquivoTarefa.salvarTarefasEmArquivo(this.tarefas, NOME_ARQUIVO);
    }

    public void carregarTarefas() {
        List<Tarefa> tarefasCarregadas = ArquivoTarefa.carregarTarefasDoArquivo(NOME_ARQUIVO);
        if (tarefasCarregadas != null) {
            this.tarefas.addAll(tarefasCarregadas);
        }
    }
}

