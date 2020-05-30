/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author USER
 */
public class TransactionDAO {
    
    private Connection conn;
    private PreparedStatement prs;
    private String SQL_CREATE = "insert into Transactions (seller_id, customer_id, transaction_content, block_hash) values (?,?,?,?)",
                    SQL_READALL = "select * from Transactions",
                    SQL_READ_BY_BLOCKHASH = "select * from Transactions where block_hash = ?",
                    SQL_UPDATE = "update Transactions set seller_id = ?, "
                                                        + "customer_id =?, "
                                                        + "transaction_content = ?, "
                                                        + "block_hash=? "
                                                        + "where trans_id = ?",
                    SQL_DELETE = "delete from Transactions where trans_id=?";

    public TransactionDAO() {
        this.conn = new DBConnector().getConn();
    }
    
    public int create(Transaction trans){
        try {
            prs = conn.prepareStatement(SQL_CREATE);
            prs.setString(1, trans.getSeller_id());
            prs.setString(2, trans.getCustomer_id());
            prs.setString(3, trans.getTransaction_content());
            prs.setString(4, trans.getBlock_hash());
            int newCreate = prs.executeUpdate();
            System.out.println("create succesfully");
            return newCreate;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public List<Transaction> readAll(){
        try {
            List<Transaction> list = new ArrayList<>();
            prs = conn.prepareStatement(SQL_READALL);
            ResultSet res = prs.executeQuery();
            while (res.next()){
                Transaction trans = new Transaction(res.getInt("trans_id"), res.getString("seller_id"), res.getString("customer_id"), 
                                                                 res.getString("transaction_content"), res.getString("block_hash"));
                list.add(trans);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Transaction readByBlockHash(String block_hash){
        try {
            List<Transaction> list = new ArrayList<>();
            prs = conn.prepareStatement(SQL_READ_BY_BLOCKHASH);
            prs.setString(1, block_hash);
            ResultSet res = prs.executeQuery();
            while (res.next()){
                Transaction trans = new Transaction(res.getInt("trans_id"), res.getString("seller_id"), res.getString("customer_id"), 
                                                                 res.getString("transaction_content"), res.getString("block_hash"));
                list.add(trans);
            }
            return list.get(0);
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int update(int id){
        try {
            prs = conn.prepareStatement(SQL_UPDATE);
            prs.setInt(1, id);
            int newUpdate = prs.executeUpdate();
            System.out.println(newUpdate!=0 ? "update succesfully" : "failed");
            return newUpdate; 
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int delete(int id){
        try {
            prs=conn.prepareCall(SQL_DELETE);
            prs.setInt(1, id);
            int del = prs.executeUpdate();
            System.out.println(del!=0 ? "delete suscessfully" : "failed");
            return del;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
//    public static void main(String[] args) {
//        // TODO code application logic here
//    }
    
}
