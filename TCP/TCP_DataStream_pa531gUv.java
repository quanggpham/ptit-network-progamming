
package TCP;

import java.util.*;
import java.io.*;
import java.net.*;

public class TCP_DataStream_pa531gUv {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2207);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        
        output.writeUTF("B22DCCN136;pa531gUv");
        output.flush();
        
        int a, b;
        a = input.readInt();
        b = input.readInt();
        System.out.println(a + " " + b);
        
        int tong = a + b;
        int tich = a * b;
        
        output.writeInt(tong);
        System.out.println(tong);
        output.writeInt(tich);
        System.out.println(tich);
        output.flush();
        
        socket.close();
    }
}
