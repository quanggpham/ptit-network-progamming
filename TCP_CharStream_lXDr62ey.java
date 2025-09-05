
package TCP;

import java.util.*;
import java.net.*;
import java.io.*;


public class TCP_CharStream_lXDr62ey {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2208);
        BufferedWriter output= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String id = "B22DCCN136;lXDr62ey";
        output.write(id);
        output.newLine();
        output.flush();
        
        String str = input.readLine();
        System.out.println("String: " + str);
        str = str.replace(",", "").trim();
        String[] domains = str.split("\\s+");
        
        ArrayList<String> eduDomains = new ArrayList<>();
        
        for (String s : domains)
        {
            if (s.endsWith(".edu"))
            {
                eduDomains.add(s);
            }
        }
        
        String res = String.join(", ", eduDomains);
//        String res = "";

        
//        for (int i = 0; i < list.size(); i++)
//        {
//            if (i != list.size() - 1)
//            {
//                res += (list.get(i) + ", ");
//            }
//            else
//            {
//                res += list.get(i);
//            }
//        }
        System.out.println(res);
        output.write(res);
        output.newLine();
        output.flush();
        
        socket.close();
    }
}
