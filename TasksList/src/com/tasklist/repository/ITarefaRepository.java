package com.tasklist.repository;

import com.tasklist.model.Tarefa;
import java.util.List;

public interface ITarefaRepository {
    void salvar(List<Tarefa> tarefas);
    List<Tarefa> carregar();
    Tarefa obterPorId(int id);
}
