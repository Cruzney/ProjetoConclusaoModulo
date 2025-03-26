package com.tasklist.service;

import com.tasklist.model.Tarefa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ArquivoTarefa {
    public static void salvarTarefasEmArquivo ( List<Tarefa> tarefas , String nomeArquivo ) {
        try (BufferedWriter writer = new BufferedWriter ( new FileWriter ( nomeArquivo ) )) {
            for (Tarefa tarefa : tarefas) {
                writer.write ( tarefa.toFileFormat ( ) );
                writer.newLine ( );
            }
            System.out.println ( "Tarefas salvas com sucesso no arquivo: " + nomeArquivo );
        } catch (IOException e) {
            System.err.println ( "Erro ao salvar tarefas no arquivo: " + e.getMessage ( ) );
        }
    }

    public static List<Tarefa> carregarTarefasDoArquivo ( String nomeArquivo ) {
        List<Tarefa> tarefasCarregadas = new ArrayList<> ( );
        try (Scanner scanner = new Scanner ( new java.io.File ( nomeArquivo ) )) {
            while (scanner.hasNextLine ( )) {
                String linha = scanner.nextLine ( );
                Tarefa tarefa = Tarefa.fromFileFormat ( linha );
                if ( tarefa != null ) {
                    tarefasCarregadas.add ( tarefa );
                }
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println ( "Arquivo de tarefas não encontrado. Um novo arquivo será criado." );
            return tarefasCarregadas; // Retorna uma lista vazia se o arquivo não existir
        }
        return tarefasCarregadas;
    }
}
