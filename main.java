package blockchain;

import de.tum.in.www1.jReto.Connection;
import de.tum.in.www1.jReto.LocalPeer;
import de.tum.in.www1.jReto.RemotePeer;
import de.tum.in.www1.jReto.module.wlan.WlanModule;
import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class main {
    public static void main(String[] args)
    {
       Blockchain chain=new Blockchain();
       Blockchain chain1=new Blockchain();
       user x=new user();
       user y=new user();
        medical_report m=new medical_report(x.publicKey,"ahmed","kdjdja",x.privateKey);
        medical_report m1=new medical_report(y.publicKey,"hadeer","hdhjsus",y.privateKey);
        medical_report m2=new medical_report(y.publicKey,"gag","fd",y.privateKey);
        chain.addblock(m);
        chain1.addblock(m1);
        peer peer=new peer(chain);
        peer.run();
        }

}
