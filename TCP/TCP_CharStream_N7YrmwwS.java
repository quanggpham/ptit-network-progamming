
//[Mã câu hỏi (qCode): N7YrmwwS].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
//a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode".
//Ví dụ: "B15DCCN999;1D08FX21"
//b. Nhận từ server một chuỗi chứa nhiều từ, các từ được phân tách bởi khoảng trắng.
//Ví dụ: "hello world programming is fun"
//c. Thực hiện đảo ngược từ và mã hóa RLE để nén chuỗi ("aabb" nén thành "a2b2"). Gửi chuỗi đã được xử lý lên server. Ví dụ: "ol2eh dlrow gnim2argorp si nuf".
//d. Đóng kết nối và kết thúc chương trình

package TCP;

import java.util.*;
import java.net.*;
import java.io.*;


public class TCP_CharStream_N7YrmwwS {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2208);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        output.write("B22DCCN136;N7YrmwwS");
        output.newLine();
        output.flush();
        
        String data = input.readLine().trim();
        String[] words = data.split("\\s+");
        System.out.println("data: " + data);
        
        for (int i = 0; i < words.length; i++)
        {
            StringBuilder sb = new StringBuilder(words[i]);
            String reversed = sb.reverse().toString();
            words[i] = reversed;
        }
        
        List<String> result = new ArrayList<>();
        
        for(String word : words)
        {
            int cnt = 1;
            char prev = word.charAt(0);
            StringBuilder sb = new StringBuilder();
            
            for (int i = 1; i < word.length(); i++)
            {
                char c = word.charAt(i);
                if (c == prev)
                {
                    cnt++;
                }
                else
                {
                    sb.append(prev);
                    if (cnt > 1) sb.append(cnt);
                    cnt = 1;
                    prev = word.charAt(i);
                }
            }
            
            sb.append(prev);
            if (cnt > 1) sb.append(cnt);
            
            result.add(sb.toString());

        }
        
        String resultString = String.join(" ", result);
        System.out.println("result: " + resultString);
        
        output.write(resultString);
        output.newLine();
        output.flush();
        
        socket.close();
    }
}
