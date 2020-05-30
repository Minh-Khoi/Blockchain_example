/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactions;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 *
 * @author USER
 */
public class Block {
    private long currentTime;
    private String  data, previousHash, currentHash;

    /** This constructor use for the method createNewBlock and getGenesisBlock of the Object Blockchain*/
    public Block(String currentTime, String data, String previousHash) throws NoSuchAlgorithmException {
        this.currentTime = (!currentTime.equals("")) ? (new Date(currentTime).getTime()) : (new Date().getTime()) ;
        this.data = data;
        this.previousHash = previousHash;
        this.currentHash = this.getCurrentHash();
    }
    
    /** This constructor use for the method readAll and readByHash in the Object BlockDAO*/
    public Block(long currentTime, String data, String previousHash, String currentHash)  {
        this.currentTime = currentTime ;
        this.data = data;
        this.previousHash = previousHash;
        this.currentHash = currentHash;
    }
    
    /** This constructor use for the method calculateBlockForChecking in the Object Blockchain*/
    public Block(long currentTime, String data, String previousHash) throws NoSuchAlgorithmException  {
        this.currentTime = currentTime ;
        this.data = data;
        this.previousHash = previousHash;
        this.currentHash = this.getCurrentHash();
    }    
    
    /** convert string input into hash array*/
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {  
        // Static getInstance method is called with hashing SHA  
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        // digest() method called  
        // to calculate message digest of an input  
        // and return array of byte 
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    } 

    /** Use this function to create hash*/
    public String getCurrentHash() throws NoSuchAlgorithmException{
        // Get byte[] hash from string input
        byte[] hash = getSHA(this.previousHash + this.data+this.currentTime);
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);  
        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
        // Pad with leading zeros 
        while (hexString.length() < 32)  {  
            hexString.insert(0, '0');  
        }  
        return hexString.toString();   
    }
    
    public String getCurrentHashStatic(){
        return currentHash;
    }
    
    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    
    
}
