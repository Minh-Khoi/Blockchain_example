/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import java.security.NoSuchAlgorithmException;
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
public class BlockDAO {
    private Connection conn;
    private PreparedStatement prs;
    private String SQL_CREATE = "insert into Blocks(block_hash, previous_block_hash, data, currentTime) values (?,?,?,?)",
                    SQL_READALL = "select * from Blocks",
                    SQL_READ_BY_BLOCKHASH = "select * from Blocks where block_hash = ?",
                    SQL_UPDATE = "update Blocks set previous_hash = ?, "
                                                        + "data =?, "
                                                        + "currentTime = ? "
                                                        + "where block_hash = ?",
                    SQL_DELETE = "delete from Blocks where block_hash=?";
    
    public BlockDAO(){
        this.conn = new DBConnector().getConn();
    }
    
    public int create(Block blo){
        try {
            prs = conn.prepareStatement(SQL_CREATE);
            prs.setString(1, blo.getCurrentHash());
            prs.setString(2, blo.getPreviousHash());
            prs.setString(3, blo.getData());
            prs.setLong(4, blo.getCurrentTime());
            int newCreate = prs.executeUpdate();
            System.out.println("create succesfully");
            return newCreate; 
        } catch (SQLException ex) {
            Logger.getLogger(BlockDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(BlockDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public List<Block> readAll(){
        try {
            List<Block> list = new ArrayList<>();
            prs = conn.prepareStatement(SQL_READALL);
            ResultSet rs = prs.executeQuery();
            while (rs.next()){
                Block blo = new Block(rs.getLong("currentTime"),rs.getString("data"),  
                                                   rs.getString("previous_block_hash"),  rs.getString("block_hash"));
                list.add(blo);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(BlockDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Block readByHash(String hash){
        try {
            List<Block> list = new ArrayList<>();
            prs = conn.prepareStatement(SQL_READ_BY_BLOCKHASH);
            prs.setString(1, hash);
            ResultSet rs = prs.executeQuery();
            while (rs.next()){
                Block blo = new Block(rs.getLong("currentTime"),rs.getString("data"),  
                                                   rs.getString("previous_block_hash"),  rs.getString("block_hash"));
                list.add(blo);
            }
            return list.get(0);
        } catch (SQLException ex) {
            Logger.getLogger(BlockDAO.class.getName()).log(Level.SEVERE, null, ex);
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
}
