/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WS;

import vn.medianews.*;
import java.util.*;

public class WS_CharacterService_sG3fZ5jE {
    public static String ToPascalCase(String[] data)
    {
        StringBuilder sb = new StringBuilder();
        for (String s : data)
        {
            sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1).toLowerCase());
        }
        return sb.toString();
    }
    
    public static String toCamelCase(String[] data)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toLowerCase(data[0].charAt(0))).append(data[0].substring(1).toLowerCase());
        for (int i = 1; i < data.length; i++)
        {
            sb.append(Character.toUpperCase(data[i].charAt(0))).append(data[i].substring(1).toLowerCase());
        }
        return sb.toString();
    }
    
    public static String to_snake_case(String[] data)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(data[0].toLowerCase());
        for (int i = 1; i < data.length; i++)
        {
            sb.append("_").append(data[i].toLowerCase());
        }
        return sb.toString();
    }
    public static void main(String[] args) throws Exception{
        CharacterService_Service service = new CharacterService_Service();
        CharacterService port = service.getCharacterServicePort();
        
        String id = "B22DCCN136";
        String qCode = "sG3fZ5jE";
        
        String data  = port.requestString(id, qCode);
        System.out.println("data: " + data);
        String[] parts = data.split("[ _]+");
        
        String pascal = ToPascalCase(parts);
        String camel = toCamelCase(parts);
        String snake = to_snake_case(parts);
        
        System.out.println("pascal: " + pascal);
        System.out.println("camel: " + camel);
        System.out.println("snake: " + snake);
        
        List<String> result = new ArrayList<>();
        result.add(pascal);
        result.add(camel);
        result.add(snake);
        
        port.submitCharacterStringArray(id, qCode, result);
    }
}
