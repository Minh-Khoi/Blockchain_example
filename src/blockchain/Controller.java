/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import transactions.Block;
import transactions.BlockDAO;
import transactions.Blockchain;
import transactions.Transaction;
import transactions.TransactionDAO;

/**
 *
 * @author USER
 */
public class Controller {
    
    public Controller(){
        
    }
    /***/
    public void addTransaction( String seller_id, String customer_id, String transaction_content){
        // Create the Map object which include seller_id, customer_id, transaction_content. 
        // Then convert Map result to String
        Map<String, Object> dataMap = new HashMap<String, Object>() ;
        dataMap.put("seller_id", seller_id);
        dataMap.put("customer_id", customer_id);
        dataMap.put("transaction_content", transaction_content);
        
        // Create a block and add it to Block table in database (add one more Block into Blockchain)
        Blockchain blockchain = new Blockchain();
        BlockDAO blockDAO = new BlockDAO();
        dataMap.put("trans_id", blockchain.getChainLength());
        String data = new JSONObject(dataMap).toString();
        Block block = blockchain.createNewBlock(data);
        
        // set a Transaction object with the attribute "block_hash" is empty
        Transaction trans = new Transaction(0, seller_id, customer_id, transaction_content, "");
        trans.setBlock_hash(block.getCurrentHashStatic());
        TransactionDAO transDAO = new TransactionDAO();
        transDAO.create(trans);

    }
    
}
