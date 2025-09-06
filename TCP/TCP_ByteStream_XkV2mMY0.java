
package TCP;

import java.io.*;
import java.net.*;
import java.util.*;


public class TCP_ByteStream_XkV2mMY0 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2206);
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        
        output.write("B22DCCN136;XkV2mMY0".getBytes());
        output.flush();
        
        byte buffer[] = new byte[1024];
        int len = input.read(buffer);
        String str = new String(buffer, 0, len);
        
        str = str.replace("|", " ").trim();
        System.out.println(str);
        String strings[] = str.split("\\s+");
        
        long sum = 0;
        
        for (String s : strings)
        {
            sum += Long.parseLong(s);
        }
        
        System.out.println(sum);
        output.write((sum + "").getBytes());
        output.flush();
    }
}
