/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

/**
 *
 * @author USER
 */
public class Transaction {
    private int trans_id; 
    private String seller_id, customer_id, transaction_content, block_hash; 

    public Transaction(int trans_id, String seller_id, String customer_id, String transaction_content, String block_hash) {
        this.trans_id = trans_id;
        this.seller_id = seller_id;
        this.customer_id = customer_id;
        this.transaction_content = transaction_content;
        this.block_hash = block_hash;
    }

    public int getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(int trans_id) {
        this.trans_id = trans_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getTransaction_content() {
        return transaction_content;
    }

    public void setTransaction_content(String transaction_content) {
        this.transaction_content = transaction_content;
    }

    public String getBlock_hash() {
        return block_hash;
    }

    public void setBlock_hash(String block_hash) {
        this.block_hash = block_hash;
    }
    
    
}
