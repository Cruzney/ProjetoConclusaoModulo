package com.tasklist.repository;

import com.tasklist.model.Status;
import com.tasklist.model.Tarefa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArquivoTarefaRepository implements ITarefaRepository {

    private static final String NOME_ARQUIVO = "tarefas.txt";

    @Override
    public void salvar(List<Tarefa> tarefas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Tarefa tarefa : tarefas) {
                writer.write(formatarTarefaParaArquivo(tarefa));
                writer.newLine();
            }
            System.out.println("Tarefas salvas com sucesso no arquivo: " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.err.println("Erro ao salvar tarefas no arquivo: " + e.getMessage());
        }
    }

    @Override
    public List<Tarefa> carregar() {
        List<Tarefa> tarefasCarregadas = new ArrayList<>();
        int maxId = 0;
        try (Scanner scanner = new Scanner(new java.io.File(NOME_ARQUIVO))) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                Tarefa tarefa = parseTarefaDoArquivo(linha);
                if (tarefa != null) {
                    tarefasCarregadas.add(tarefa);
                    if (tarefa.getId() > maxId) {
                        maxId = tarefa.getId();
                    }
                }
            }
            Tarefa.setProximoId(maxId + 1);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Arquivo de tarefas não encontrado. Um novo arquivo será criado.");
        }
        return tarefasCarregadas;
    }

    @Override
    public Tarefa obterPorId(int id) {
        List<Tarefa> tarefas = carregar();
        return tarefas.stream()
                .filter(tarefa -> tarefa.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private String formatarTarefaParaArquivo(Tarefa tarefa) {
        return String.format("%d;%s;%s;%s;%s;%s;%s",
                tarefa.getId(), tarefa.getTitulo(), tarefa.getDescricao(),
                tarefa.getDataLimite().format(Tarefa.DATE_FORMATTER),
                (tarefa.getDataExecucao() != null ? tarefa.getDataExecucao().format(Tarefa.DATE_FORMATTER) : ""),
                tarefa.getStatus().name(), tarefa.getCreatedAt().format(Tarefa.DATE_TIME_FORMATTER));
    }

    private Tarefa parseTarefaDoArquivo(String linha) {
        String[] parts = linha.split(";");
        if (parts.length == 7) {
            int id = Integer.parseInt(parts[0]);
            String titulo = parts[1];
            String descricao = parts[2];
            LocalDate dataLimite = LocalDate.parse(parts[3], Tarefa.DATE_FORMATTER);
            LocalDate dataExecucao = parts[4].isEmpty() ? null : LocalDate.parse(parts[4], Tarefa.DATE_FORMATTER);
            Status status = Status.valueOf(parts[5]);
            LocalDateTime createdAt = LocalDateTime.parse(parts[6], Tarefa.DATE_TIME_FORMATTER);

            Tarefa tarefa = new Tarefa(titulo, descricao, dataLimite);
            tarefa.setId(id);
            tarefa.setDataExecucao(dataExecucao);
            tarefa.setStatus(status);
            tarefa.setCreatedAt(createdAt);
            return tarefa;
        }
        return null;
    }
}