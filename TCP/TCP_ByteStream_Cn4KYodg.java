
package TCP;

import java.util.*;
import java.io.*;
import java.net.*;

public class TCP_ByteStream_Cn4KYodg {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2206);
        socket.setSoTimeout(5000);
        
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();


        output.write("B22DCCN136;Cn4KYodg".getBytes());
        output.flush();
        
        byte buffer[] = new byte[1024];
        int len = input.read(buffer);
        String str = new String(buffer, 0, len);
        
        str = str.replace(",", " ").trim();
        String array[] = str.split("\\s+");
        ArrayList<Long> list = new ArrayList<>();
        
        long sum = 0;
        
        for (String s : array)
        {
            long tmp = Long.parseLong(s);
            list.add(tmp);
            sum += tmp;
        }
        
        long average = sum / list.size();
        long target = average * 2;

        long min_diff = Long.MAX_VALUE;
        long best_num1 = 0;
        long best_num2 = 0;
        
        for (int i = 0; i < list.size(); i++)
        {
            for (int j = i + 1; j < list.size(); j++)
            {
                long a = list.get(i);
                long b = list.get(j);
                long total = a + b;
                long diff = Math.abs(total - target);
                
                if (diff < min_diff)
                {
                    min_diff = diff;
                    if (a <= b)
                    {
                        best_num1 = a;
                        best_num2 = b;
                    }
                    else
                    {
                        best_num1 = b;
                        best_num2 = a;
                    }
                }
            }
        }
        System.out.println(target);
        String result = new String(best_num1 + "," + best_num2);
        System.out.println(result);
        output.write(result.getBytes());
        output.flush();
        
        input.close();
        output.close();
        socket.close();
    }
}
