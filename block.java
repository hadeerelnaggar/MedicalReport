package blockchain;

import java.security.MessageDigest;
import java.util.Date;

public class block {
    private String hash;
    private String prevhash;
   private medical_report data;
   private long timestamp;
   private int nonce;


    public block(medical_report data, String prevhash){
        this.prevhash = prevhash;
        this.data = data;
        timestamp=new Date().getTime();
        nonce=0;
        hash=generatehash();
    }

    public String generatehash(){
         String input=prevhash+Long.toString(timestamp)+Integer.toString(nonce)+data;
             return getsha256(input);
    }
    private String getsha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Applies sha256 to our input,
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void mineblock(int difficulty){
         String target=new String(new char[difficulty]).replace("\0","0");
         while(!hash.substring(0,difficulty).equals(target)){
             nonce++;
             hash=generatehash();
         }
         System.out.println("block mined!!!! : "+hash);
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPrevhash() {
        return prevhash;
    }

    public void setPrevhash(String prevhash) {
        this.prevhash = prevhash;
    }

    public medical_report getData() {
        return data;
    }

    public void setData(medical_report data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
