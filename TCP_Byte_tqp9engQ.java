package TCP;


import java.util.*;
import java.io.*;
import java.net.*;

public class TCP_Byte_tqp9engQ {
    public static boolean isPrime(long n)
    {
        for (int i = 2; i <= Math.sqrt(n); i++)
        {
            if (n % i == 0)
                return false;
        }
        return n > 1;
    }
    
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2206);
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        
        String id = "B22DCCN136;tqp9engQ";
        output.write(id.getBytes());
        output.flush();
        
        byte byte_in[] = new byte[1024];
        int len = input.read(byte_in);
        String str = new String(byte_in, 0, len);
        System.out.println("String: " + str);
        
        str = str.replace(",", " ").trim();
        String array[] = str.split("\\s+");
        
        long sum = 0;
        
        for (String s : array)
        {
            long temp = Long.parseLong(s);
            if (isPrime(temp))
            {
                sum += temp;
            }
        }
        System.out.println(sum);
        
        output.write((sum + "").getBytes());
        output.flush();
        
        output.close();
        input.close();
        socket.close();
        
    }
}