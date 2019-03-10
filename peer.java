package blockchain;

import java.io.IOException;
import java.util.Scanner;

public class peer {
    private Thread reciever;
    private multicastsender sender;
    private Blockchain chain;

    public peer(Blockchain chain){
        this.chain=chain;
    }
    public void run(){
        reciever=new multicastreciever(chain);
        reciever.start();
        sender=new multicastsender();
        int choice;
        Scanner input=new Scanner(System.in);
        do{
            System.out.println("1-new medical report");
            System.out.println("2-send request to get some peer's copy of the blockchain");
            choice=input.nextInt();
            if(choice==1){
                user x=new user();
                System.out.println("enter patient name");
                Scanner input1=new Scanner(System.in);
                String patient_name=input1.nextLine();
                System.out.println("enter prescription");
                Scanner input2=new Scanner(System.in);
                String prescription=input2.nextLine();
                medical_report m=new medical_report(x.publicKey,patient_name,prescription,x.privateKey);
                try {
                    sender.multicast(m);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            else if(choice==2){
                try {
                    sender.sendreq("request");
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        while(choice!=0);

    }
    public Blockchain requestcopy(peer p){
        return p.chain;
    }
}
