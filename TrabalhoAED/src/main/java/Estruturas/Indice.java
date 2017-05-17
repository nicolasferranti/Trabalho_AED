/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import Funcoes.MergeSort;

/**
 *
 * @author nicolasferranti
 */
public class Indice {

    private boolean estaOrdenado;
    private Tupla[] registros;
    private int indice;

    public Indice() {
        this.estaOrdenado = false;
        this.registros = new Tupla[250];
        this.indice = 0;
    }

    public void addTupla(String key, String value) {
        Tupla t = new Tupla(key, value);
        if (this.indice >= this.registros.length) {
            aumentaIndice();
        }
        this.registros[this.indice] = t;
        this.indice++;
    }

    public void aumentaIndice() {
        Tupla[] novos = new Tupla[this.registros.length * 2];
        for (int i = 0; i < this.indice; i++) {
            novos[i] = this.registros[i];
        }
        this.registros = novos;
    }
    
    public String getKey(String valor){
        if(this.indice == 0){
            return null;
        }
        if(!this.estaOrdenado){
            // ordenar
        }
        int lo = 0;
        int hi = this.indice;
        while (lo <= hi) {

            int mid = lo + (hi - lo) / 2;
            if (valor.compareTo(this.registros[mid].valor) < 0) {
                hi = mid - 1;
            } else if (valor.compareTo(this.registros[mid].valor) > 0) {
                lo = mid + 1;
            } else {
                return this.registros[mid].chave;
            }
        }
        return null;
    }
}
