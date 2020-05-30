/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchain;

import java.util.ArrayList;
import java.util.List;
import transactions.Blockchain;
import transactions.Transaction;
import transactions.TransactionDAO;

/**
 *
 * @author USER
 */
public class Executer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.addTransaction("mot", "lan", "nua thoi");
//        Blockchain blockchain = new Blockchain();
//        System.out.println(blockchain.isValidChain());
//        TransactionDAO transDAO = new TransactionDAO();
    }
    
}
