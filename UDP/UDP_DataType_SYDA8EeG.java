
package UDP;

import java.io.*;
import java.util.*;
import java.net.*;

public class UDP_DataType_SYDA8EeG {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress serverAdress = InetAddress.getByName("203.162.10.109");
        int serverPort = 2207;
        
        String id = ";B22DCCN136;SYDA8EeG";
        DatagramPacket sendPacket = new DatagramPacket(id.getBytes(), id.getBytes().length, serverAdress, serverPort);
        socket.send(sendPacket);
        
        byte recvBuf[] = new byte[1024];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(recvPacket);
        
        String data = new String(recvPacket.getData(), 0, recvPacket.getLength());
        System.out.println(data);
        String tmp[] = data.split(";", 2);
        
        String requestId = tmp[0];
        String numbers[] = tmp[1].split(",");
        
        List<Integer> nums = new ArrayList<>();
        
        for (String num : numbers)
        {
            nums.add(Integer.parseInt(num));
        }
        
        int max = Collections.max(nums);
        int min = Collections.min(nums);
        
        String result = requestId + ";" + max + "," + min;
        System.out.println(result);
        
        byte resBuf[] = result.getBytes();
        DatagramPacket resultPacket = new DatagramPacket(resBuf, resBuf.length, serverAdress, serverPort);
        socket.send(resultPacket);
        
    }
}
