/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import Estruturas.Registro;

/**
 *
 * @author nicolasferranti
 */
public class QuickSort {

    private Registro array[];
    private int length;

    public void sort(Registro[] inputArr,int tamanho) {

        if (inputArr == null || inputArr.length == 0) {
            return;
        }
        this.array = inputArr;
        length = tamanho;
        quickSort(0, length - 1);
    }

    private void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;
        String pivot = array[lowerIndex + (higherIndex - lowerIndex) / 2].id;

        while (i <= j) {
            while (array[i].id.compareTo(pivot) < 0) {
                i++;
            }
            while (array[j].id.compareTo(pivot) > 0) {
                j--;
            }
            if (i <= j) {
                swapNumbers(i, j);
                i++;
                j--;
            }
        }

        if (lowerIndex < j) {
            quickSort(lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(i, higherIndex);
        }
    }

    private void swapNumbers(int i, int j) {
        Registro temp;
        temp= array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}