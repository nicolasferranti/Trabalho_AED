/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import utils.utils;

/**
 *
 * @author nicolasferranti
 */
public class Hash {

    public static int Hash1TableSize(String tableName, int tableSize) {
        int hash = 7;
        for (int i = 0; i < tableName.length(); i++) {
            // 31 ou 37 empiricamente
            hash = hash * 37 + tableName.charAt(i);
        }
        hash = utils.abs(hash);

        
        // 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 77, 79, 83, 89. 91, 97
        return (hash % tableSize);
    }

}
