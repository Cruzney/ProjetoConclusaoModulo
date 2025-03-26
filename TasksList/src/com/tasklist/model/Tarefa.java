package com.tasklist.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tarefa  {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDate dataLimite;
    private LocalDate dataExecucao;
    private Status status;
    private LocalDateTime createdAt;

    private static int proximoId = 1;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Tarefa(String titulo, String descricao, LocalDate dataLimite) {
        if (titulo == null || titulo.trim().isEmpty() || titulo.length() > 20) {
            throw new IllegalArgumentException("O título não pode ser vazio ou ter mais de 20 caracteres.");
        }
        if (dataLimite == null || dataLimite.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data limite não pode ser no passado.");
        }
        this.id = proximoId++;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLimite = dataLimite;
        this.status = Status.PENDENTE;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty() || titulo.length() > 20) {
            throw new IllegalArgumentException("O título não pode ser vazio ou ter mais de 20 caracteres.");
        }
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        if (dataLimite == null || dataLimite.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data limite não pode ser no passado.");
        }
        this.dataLimite = dataLimite;
    }

    public LocalDate getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDate dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Título: " + titulo +
                ", Descrição: " + descricao +
                ", Data Limite: " + dataLimite.format(dateFormatter) +
                ", Data de Execução: " + (dataExecucao != null ? dataExecucao.format(dateFormatter) : "N/A") +
                ", Status: " + status +
                ", Criada em: " + createdAt.format(dateTimeFormatter);
    }

    public String toFileFormat() {
        return String.format("%d;%s;%s;%s;%s;%s;%s",
                id, titulo, descricao, dataLimite.format(dateFormatter),
                (dataExecucao != null ? dataExecucao.format(dateFormatter) : ""),
                status.name(), createdAt.format(dateTimeFormatter));
    }

    public static Tarefa fromFileFormat(String line) {
        String[] parts = line.split(";");
        if (parts.length == 7) {
            int id = Integer.parseInt(parts[0]);
            String titulo = parts[1];
            String descricao = parts[2];
            LocalDate dataLimite = LocalDate.parse(parts[3], dateFormatter);
            LocalDate dataExecucao = parts[4].isEmpty() ? null : LocalDate.parse(parts[4], dateFormatter);
            Status status = Status.valueOf(parts[5]);
            LocalDateTime createdAt = LocalDateTime.parse(parts[6], dateTimeFormatter);

            Tarefa tarefa = new Tarefa(titulo, descricao, dataLimite);
            tarefa.id = id; // Define o ID manualmente
            tarefa.dataExecucao = dataExecucao;
            tarefa.status = status;
            tarefa.createdAt = createdAt;
            if (id >= proximoId) {
                proximoId = id + 1;
            }
            return tarefa;
        }
        return null;
    }
}
