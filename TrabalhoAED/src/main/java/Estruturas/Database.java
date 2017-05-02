/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import Funcoes.Hash;
import java.util.ArrayList;

/**
 *
 * @author nicolasferranti
 */
public class Database {

    private Tabela[] tabelas = null;
    private int tam;

    /*
     *   Cria novo banco aumentando o tamanho inicial de tabelas
     *   @param qtdTabelas a quantidade de tabelas iniciais
     *
     */
    public Database(int qtdTabelas) {
        float qtd = (float) qtdTabelas;
        float pct = (float) 4 / 3;
        this.tam = Math.round(qtd * pct);
        this.tabelas = new Tabela[this.tam];
    }

    /*
     * Adiciona nova tabela vazia ao banco
     * @param nome da tabela a ser adicionada
     * @param vetor de colunas dessa tabela
     * @return o objeto Tabela já adicionado
     */
    public Tabela addTable(String tableName, ArrayList<String> colunas) {
        try {
            Tabela t = new Tabela(tableName, colunas);

            int indice = Hash.Hash1TableSize(tableName, this.tam);
            this.insereTabela(indice, t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // insere tabela em um dado indice, busca posiçã olivre em caso de conflito
    private void insereTabela(int indice, Tabela t) {
        if (this.tabelas[indice] == null) {
            this.tabelas[indice] = t;
        } else {
            Tabela aux = this.tabelas[indice];
            while (aux.getProx() != null) {
                aux = aux.getProx();
            }
            aux.setProx(t);
        }
    }

    public boolean addRegistro(String tableName, Registro r) {
        Tabela t = this.getTabela(tableName);
        if (t == null || r == null) {
            return false;
        }
        t.inserirRegistro(r);
        return true;
    }

    public void printBD() {
        for (int i = 0; i < this.tam; i++) {
            if (this.tabelas[i] != null) {
                Tabela it = this.tabelas[i];
                System.out.print(i + " " + it.nome + "(" + it.getColunas().toString() + ")");
                Tabela aux = this.tabelas[i].getProx();
                while (aux != null) {
                    System.out.print(" -> " + aux.nome + "(" + aux.getColunas().toString() + ")");
                    aux = aux.getProx();
                }
            } else {
                System.out.print(i);
            }
            System.out.println();
        }
    }

    private Tabela getTabela(String tableName) {
        int indice = Hash.Hash1TableSize(tableName, this.tam);
        Tabela t = this.tabelas[indice];
        if (t == null) {
            return null;
        } else {
            while (!t.nome.equals(tableName) && t != null) {
                t = t.getProx();
            }
            if (t == null) {
                return null;
            }
            return t;
        }
    }

    public void Consulta(String tableName, String id) {
        long startTime = System.currentTimeMillis();

        int indice = Hash.Hash1TableSize(tableName, this.tam);
        Tabela t = this.tabelas[indice];
        if (t == null) {
            System.out.println("Table null");
        } else {
            try {
                while (t != null && !t.nome.equals(tableName)) {
                    t = t.getProx();
                }
                if (t == null) {
                    System.out.println("Table not found");
                } else if (id.equals("*")) {
                        t.printTabela();
                        long endTime = System.currentTimeMillis();
                        long duration = (endTime - startTime);
                        System.out.println();
                        System.out.println("(" + duration + " milliseconds)");
                    } else {
                        Registro r = t.buscarRegistro(id);
                        if (r != null) {
                            for (String col : t.getColunas()) {
                                System.out.print(col + " | ");
                            }
                            System.out.println();
                            for (String data : r.dados) {
                                System.out.print(data + " | ");
                            }
                            long endTime = System.currentTimeMillis();
                            long duration = (endTime - startTime);
                            System.out.println();
                            System.out.println("(" + duration + " milliseconds)");
                        } else {
                            long endTime = System.currentTimeMillis();
                            long duration = (endTime - startTime);
                            System.out.println();
                            System.out.println("Empty set(" + duration + " milliseconds)");
                        }
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void ConsultaSemPrint(String tableName, String id) {
        long startTime = System.currentTimeMillis();

        int indice = Hash.Hash1TableSize(tableName, this.tam);
        Tabela t = this.tabelas[indice];
        if (t == null) {
            System.out.println("Table null");
        } else {
            try {
                while (t != null && !t.nome.equals(tableName)) {
                    t = t.getProx();
                }
                if (t == null) {
                    System.out.println("Table not found");
                } else {
                    Registro r = t.buscarRegistro(id);
                    if (r != null) {
                    } else {
                        System.out.println("registro nulo");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
