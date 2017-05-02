/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import Funcoes.MergeSort;
import Funcoes.QuickSort;
import java.util.ArrayList;

/**
 *
 * @author nicolasferranti
 */
public class Tabela {

    public String nome;
    private Tabela prox = null;
    private ArrayList<String> colunas;

    private Registro[] registros;
    private int tamanhoInicial;
    private int qtdInserida;
    private int indice;
    private boolean estaOrdenada;

    public Tabela(String name, ArrayList<String> collumns) {
        this.nome = name;
        this.colunas = collumns;
        this.qtdInserida = 0;
        this.indice = 0;
        this.tamanhoInicial = 1000;
        this.registros = new Registro[tamanhoInicial];
        this.estaOrdenada = false;
    }

    private float fatorDeCarga() {
        return ((float) this.qtdInserida / (float) this.tamanhoInicial);
    }

    public void inserirRegistro(Registro r) {
        this.registros[this.indice] = r;
        this.indice++;
        this.qtdInserida++;
        if (this.fatorDeCarga() > 0.99) {
            this.aumentarTabela();
        }
        this.estaOrdenada = false;
    }

    private void aumentarTabela() {
        this.tamanhoInicial = tamanhoInicial * 2;
        Registro[] novoRegistro = new Registro[this.tamanhoInicial];
        for (int i = 0; i < this.indice; i++) {
            novoRegistro[i] = this.getRegistros()[i];
        }
        this.registros = novoRegistro;
    }

    public Registro buscarRegistro(String identificador) {
        if (this.indice == 0) {
            return null;
        }
        if (!this.estaOrdenada) {
            //long start = System.nanoTime();
            //MergeSort s = new MergeSort();
            //s.Sort(this.getRegistros(), this.indice);
            QuickSort q = new QuickSort();
            q.sort(registros, this.indice);
            //System.out.println("tempo para ordenar nano:" + (System.nanoTime() - start));
            this.estaOrdenada = true;
        }
        int lo = 0;
        int hi = this.indice;
        while (lo <= hi) {

            int mid = lo + (hi - lo) / 2;
            if (identificador.compareTo(this.registros[mid].id) < 0) {
                hi = mid - 1;
            } else if (identificador.compareTo(this.registros[mid].id) > 0) {
                lo = mid + 1;
            } else {
                return this.getRegistros()[mid];
            }
        }
        return null;
    }

    public void printTabela() {
        for (String col : this.colunas) {
            System.out.print(col + " | ");
        }
        System.out.println();
        for (int i = 0; this.registros[i] != null; i++) {
            for (int j = 0; j < this.registros[i].dados.length; j++) {
                System.out.print(this.registros[i].dados[j] + " | ");
            }
            System.out.println();
        }
    }

    /**
     * @return the prox
     */
    public Tabela getProx() {
        return prox;
    }

    /**
     * @param next the next to set
     */
    public void setProx(Tabela next) {
        this.prox = next;
    }

    /**
     * @return the colunas size
     */
    public int getTamColunas() {
        return colunas.size();
    }

    /**
     * @return the colunas
     */
    public ArrayList<String> getColunas() {
        return colunas;
    }

    /**
     * @return the registros
     */
    public Registro[] getRegistros() {
        return registros;
    }

    public int getTamanho() {
        return this.tamanhoInicial;
    }
}
