
package TCP;

import TCP.Product;
import java.util.*;
import java.io.*;
import java.net.*;

public class TCP_ObjectStream_Ukjh9Fwf {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2209);
        ObjectInputStream input= new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        
        output.writeObject("B22DCCN136;Ukjh9Fwf");
        output.flush();
        
        Product data = (Product) input.readObject();
        System.out.println(data);
        int price = (int) data.getPrice();
        int sumDigit = 0;
        for (char c : String.valueOf(price).toCharArray())
        {
            sumDigit += (c - '0');
        }
        
        data.setDiscount(sumDigit);
        System.out.println(data);
        
        output.writeObject(data);
        output.flush();
        
        socket.close();
    }
}
