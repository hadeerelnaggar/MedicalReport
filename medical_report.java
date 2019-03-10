package blockchain;

import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class medical_report{
    public PublicKey publicKey;
    String signature;
    String patient_name;
    String prescription;

  public String medicalreport(){
      return publicKey.toString()+"&"+" signature:"+signature+" patient name:"+patient_name+" prescription:"+prescription;
  }
  public String concat(){
      return  Base64.getEncoder().encodeToString(publicKey.getEncoded())+"&"+signature+"&"+patient_name+"&"+prescription;
  }
    public medical_report(){

    }
    public void extract(String x){
      String arr[]=new String[4];
      String a="";
      for(int i=0;i<arr.length;i++){
          a="";
         for(int j=0;j<x.length();j++){
             if(x.charAt(j)=='&'){
                 arr[i]=a;
                 i++;
                 a="";
             }
             else{
              a+=x.charAt(j);
             }
             if(j==x.length())
                 break;
         }
      }
        byte[] data = Base64.getDecoder().decode(arr[0]);
        KeyFactory keyFactory=null;
        try {
             keyFactory = KeyFactory.getInstance("DSA");
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(data);
        try {
            publicKey= keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e){
            e.printStackTrace();
        }

        signature=arr[1];
        patient_name=arr[2];
        prescription=arr[3];

    }

    public medical_report(PublicKey publicKey,String patient_name, String prescription,PrivateKey p){
        this.publicKey = publicKey;
        generateSignature(p);
        this.patient_name = patient_name;
        this.prescription = prescription;
    }
    public String generatehash(){
        String input=getStringFromKey(publicKey)+patient_name+prescription;
        return getsha256(input);
    }
    public boolean verifiySignature(){
        String data =getStringFromKey(publicKey)+patient_name+prescription;
        return verifyECDSASig(publicKey, data, signature);
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
    public static String getStringFromKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    public static boolean verifyECDSASig(PublicKey publicKey, String data, String signature) {
        try {
            Signature ecdsaVerify = Signature.getInstance("DSA", "SUN");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature.getBytes());
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String applyECDSASig(PrivateKey privateKey, String input) {
        Signature dsa;
        byte[] output;
        try {
            dsa = Signature.getInstance("DSA", "SUN");
            dsa.initSign(privateKey);
            byte[] strByte = input.getBytes();
            dsa.update(strByte);
            byte[] realSig = dsa.sign();
            output = realSig;
            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < output.length; i++) {
                String hex = Integer.toHexString(0xff & output[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void generateSignature(PrivateKey privateKey) {
        String data =getStringFromKey(publicKey)+patient_name+prescription;
        signature = applyECDSASig(privateKey,data);
    }

    public PublicKey getPublic_key() {

        return publicKey;
    }

    public void setPublic_key(PublicKey public_key) {
        this.publicKey = public_key;
    }


    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
}
