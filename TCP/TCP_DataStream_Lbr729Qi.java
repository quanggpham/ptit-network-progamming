
package TCP;

import java.util.*;
import java.io.*;
import java.net.*;

public class TCP_DataStream_Lbr729Qi {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2207);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        
        output.writeUTF("B22DCCN136;Lbr729Qi");
        output.flush();
        
        String data = input.readUTF().trim();
        System.out.println(data);
        
        List<Integer> numbers = new ArrayList();
        for (String s : data.split(","))
        {
            numbers.add(Integer.parseInt(s));
        }
        
        int count = 0;
        int sum = 0;
        
        for (int i = 1; i < numbers.size(); i++)
        {
            
            sum += Math.abs(numbers.get(i) - numbers.get(i - 1));
            if (i >= 2)
            {
                boolean increasing = numbers.get(i) >= numbers.get(i - 1) && numbers.get(i - 1) >= numbers.get(i - 2);
                boolean decreasing = numbers.get(i) <= numbers.get(i - 1) && numbers.get(i - 1) <= numbers.get(i - 2);
                
                if (!increasing && !decreasing)
                {
                    count++;
                }
            }
            
        }
        
        System.out.println(count + " " + sum);
        
        output.writeInt(count);
        output.writeInt(sum);
        output.flush();
        
        socket.close();
    }
}
