/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


/**
 *
 * @author USER
 */
public final class Blockchain {
    private List<Block> chain = new ArrayList<>();
    
    public Blockchain() {
        TransactionDAO transDAO = new TransactionDAO();
        this.chain = new ArrayList<Block>();
        List<Transaction> list =  transDAO.readAll();
        if (list.size()!=0){
            BlockDAO blockDAO = new BlockDAO();
            this.chain = blockDAO.readAll();
        } else {
            this.chain.add(this.createGenesisBlock());
        }
    }
    
    public Block createGenesisBlock() {
        try {
            Block genesisBlock = new Block("", "Genesis Block", "");
            return genesisBlock;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Blockchain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Block getLatestBlock(){
        return this.chain.get(this.chain.size()-1);
    }
    
    public Block createNewBlock(String data){
        try {
//            System.out.println(this.getLatestBlock().getCurrentHashStatic());
            Block lastBlock = this.getLatestBlock();
            BlockDAO blockDAO = new BlockDAO();
            Block newBlock = new Block("", data, lastBlock.getCurrentHashStatic());
            this.chain.add(newBlock);
            if (this.isValidChain()){
                blockDAO.create(newBlock);
                System.out.println("1 new Block created");
                return newBlock;
            } else {
                System.out.println("Chain has not been entire");
                return null;
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Blockchain.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Creation failed");
        }
        return null;
    }
    
    public boolean isValidChain(){
        try{
            for(int i=1; i<this.chain.size()-1;i++){
                Block currentBlock = this.chain.get(i),
                        previousBlock = this.chain.get(i-1);
                TransactionDAO transDAO = new TransactionDAO();
                Transaction currentTrans = transDAO.readByBlockHash(currentBlock.getCurrentHashStatic()),
                            previousTrans = transDAO.readByBlockHash(previousBlock.getCurrentHashStatic());
                Block currentFictionBlock = this.calculateBlockForChecking(currentTrans, currentBlock);
                if (!currentTrans.getBlock_hash().equals(currentFictionBlock.getCurrentHash())){
                    return false;
                }
                if (!previousTrans.getBlock_hash().equals(previousBlock.getCurrentHashStatic())){
                    return false;
                }
            }
        } catch (NoSuchAlgorithmException ex) { 
            Logger.getLogger(Blockchain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    /** This method calculate a fiction block 
     *  whose data by specified transaction  
     *  and currentTime, previousHash  by specified Block
     */
    public Block calculateBlockForChecking(Transaction trans, Block blockOfTrans){
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("seller_id", trans.getSeller_id());
            dataMap.put("customer_id", trans.getCustomer_id());
            dataMap.put("transaction_content", trans.getTransaction_content());
            dataMap.put("trans_id", trans.getTrans_id());
            String data = new JSONObject(dataMap).toString();
            Block block = new Block(blockOfTrans.getCurrentTime(), data, blockOfTrans.getPreviousHash());
            return block;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Blockchain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /** You must guarantee that the identity in Transaction table in database is 1-based*/
    public int getChainLength(){
        return this.chain.size();
    }
}
