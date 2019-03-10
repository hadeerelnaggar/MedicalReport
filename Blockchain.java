package blockchain;

import java.util.ArrayList;

public class Blockchain {
    private ArrayList<block> chain=new ArrayList<block>();
    public int difficulty=5;

    public void addblock(medical_report data){
        block x;
        if(chain.size()==0){
             x=new block(data,"0");
        }
        else{
        x=new block(data,chain.get(chain.size()-1).getHash());}
        x.mineblock(difficulty);

       chain.add(x);

    }
    public void replaceChain(Blockchain Block) {
        if (Block.getChain().size() > chain.size()) {
            chain = Block.getChain();
        }
    }
    public void print(){
        for(int i=0;i<chain.size();i++){
            System.out.println("block "+i+" "+chain.get(i).getHash());
            System.out.println(chain.get(i).getData().medicalreport());
        }
    }
    public boolean chainvalidity(){
        block current;
        block prev;
        String target=new String(new char[difficulty]).replace("\0","0");
        for(int i=1;i<chain.size();i++){
            current=chain.get(i);
            prev=chain.get(i-1);
            if(!prev.getHash().equals(current.getPrevhash())){
                System.out.println("previous hashes not equal");
                return false;
            }
            if(!current.getHash().equals(current.generatehash())){
                System.out.println("current hashes not equal");
                return false;
            }
            if(!current.getHash().substring(0,difficulty).equals(target)){
                System.out.println("block "+ i +"is not mined");
                return false;
            }
        }
        return true;
    }

    public  int getDifficulty() {
        return difficulty;
    }

    public  void setDifficulty(int difficulty) {
       this.difficulty = difficulty;
    }

    public ArrayList<block> getChain() {
        return chain;
    }

    public void setChain(ArrayList<block> chain) {
        this.chain = chain;
    }
}
