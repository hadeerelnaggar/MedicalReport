package blockchain;

import java.io.IOException;
import java.net.*;
import java.util.Base64;

public class multicastreciever extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[1500];
    Blockchain chain;

    public multicastreciever(Blockchain chain){
        this.chain = chain;
    }

    public void run(){

        try {
            socket = new MulticastSocket(4446);
        } catch (IOException e){
            e.printStackTrace();
        }
        InetAddress group = null;
        try {
            group = InetAddress.getByName("230.0.0.0");
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
        try {
            socket.joinGroup(group);
        } catch (IOException e){
            e.printStackTrace();
        }
        while (true){
            System.out.println("while");
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e){
                e.printStackTrace();
            }
            String received = new String(packet.getData(), 0, packet.getLength());
            if(received.equals("request")){
               System.out.println("request");
            }
            else if(received.equals("end")){
                break;
            }
            else{
            medical_report m=new medical_report();
            m.extract(received);
            chain.addblock(m);
            }

        }
        try {
            socket.leaveGroup(group);
        } catch (IOException e){
            e.printStackTrace();
        }
        socket.close();
    }
}
