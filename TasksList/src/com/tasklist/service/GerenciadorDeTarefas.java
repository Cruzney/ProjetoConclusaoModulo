package com.tasklist.service;

import com.tasklist.model.Status;
import com.tasklist.model.Tarefa;
import com.tasklist.repository.ITarefaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorDeTarefas {

    private final ITarefaRepository tarefaRepository;

    public GerenciadorDeTarefas(ITarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public void cadastrarTarefa(String titulo, String descricao, LocalDate dataLimite) {
        Tarefa novaTarefa = new Tarefa(titulo, descricao, dataLimite);
        List<Tarefa> tarefas = tarefaRepository.carregar();
        tarefas.add(novaTarefa);
        tarefaRepository.salvar(tarefas);
        System.out.println("Tarefa cadastrada com sucesso. ID: " + novaTarefa.getId());
    }

    public List<Tarefa> listarTarefas(String filtroTitulo, LocalDate filtroDataVencimento) {
        List<Tarefa> tarefas = tarefaRepository.carregar();
        return tarefas.stream()
                .filter(tarefa -> filtroTitulo == null || tarefa.getTitulo().toLowerCase().contains(filtroTitulo.toLowerCase()))
                .filter(tarefa -> filtroDataVencimento == null || tarefa.getDataLimite().equals(filtroDataVencimento))
                .sorted((t1, t2) -> t2.getDataLimite().compareTo(t1.getDataLimite()))
                .collect(Collectors.toList());
    }

    public Tarefa obterTarefaPorId(int id) {
        return tarefaRepository.obterPorId(id);
    }

    public void editarTarefa(int id, String novoTitulo, String novaDescricao, LocalDate novaDataLimite, LocalDate novaDataExecucao, Status novoStatus) {
        List<Tarefa> tarefas = tarefaRepository.carregar();
        Tarefa tarefaExistente = tarefas.stream()
                .filter(tarefa -> tarefa.getId() == id)
                .findFirst()
                .orElse(null);

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
            tarefaRepository.salvar(tarefas);
            System.out.println("Tarefa com ID " + id + " editada com sucesso.");
        } else {
            System.out.println("Tarefa com ID " + id + " não encontrada.");
        }
    }

    public void deletarTarefa(int id) {
        List<Tarefa> tarefas = tarefaRepository.carregar();
        boolean removed = tarefas.removeIf(tarefa -> tarefa.getId() == id);
        if (removed) {
            tarefaRepository.salvar(tarefas);
            System.out.println("Tarefa com ID " + id + " deletada com sucesso.");
        } else {
            System.out.println("Tarefa com ID " + id + " não encontrada.");
        }
    }

    public void consultarTarefasEmVencimento() {
        LocalDate hoje = LocalDate.now();
        List<Tarefa> tarefas = tarefaRepository.carregar();
        List<Tarefa> tarefasVencendoHoje = tarefas.stream()
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
        List<Tarefa> tarefas = tarefaRepository.carregar();
        tarefaRepository.salvar(tarefas);
    }

    public void carregarTarefas() {
// A lógica de carregar agora está no ArquivoTarefaRepository
    }
}