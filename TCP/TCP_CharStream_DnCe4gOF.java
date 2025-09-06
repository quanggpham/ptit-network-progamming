
package TCP;

import java.util.*;
import java.io.*;
import java.net.*;


public class TCP_CharStream_DnCe4gOF {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2208);
        socket.setSoTimeout(5000);
        BufferedWriter output = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        output.write("B22DCCN136;DnCe4gOF");
        output.newLine();
        output.flush();
        
        String str = input.readLine().trim();
        String words[] = str.split("\\s+");
        System.out.println(str);
        
        List<String> wordList = new ArrayList<>(Arrays.asList(words));
        Collections.sort(wordList, new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        
        String res = String.join(", ", wordList);
        System.out.println(res);
        
        output.write(res);
        output.newLine();
        output.flush();
        
        socket.close();
    }
}
