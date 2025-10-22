/*
[Mã câu hỏi (qCode): C83TGHdS].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
    a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;76B68B3B".
    b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách bởi ký tự ",". Ví dụ: 5,10,20,25,50,40,30,35.
    c. Tìm chuỗi con tăng dần dài nhất và gửi độ dài của chuỗi đó lên server theo format "chuỗi tăng dài nhất; độ dài". Ví dụ: 5,10,20,25,50;5
    d. Đóng kết nối và kết thúc chương trình.
 */
package TCP;

import java.io.*;
import java.util.*;
import java.net.*;


public class TCP_CharStream_OUGdacPQ {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2208);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        String request = "B22DCCN136;OUGdacPQ";
        out.write(request);
        out.newLine();
        out.flush();
        
        String data = in.readLine();
        int[] count = new int[255];
        
        data = data.replaceAll(" ", "");
        
        LinkedHashSet<Character> lhs = new LinkedHashSet<>();
        
        for (char c : data.toCharArray())
        {
            if (Character.isLetterOrDigit(c))
            {
                count[c]++;
                lhs.add(c);
            }
        }
        
        String res = "";
        
        for (char c : lhs)
        {
            if (count[c] > 1)
            {
                res = res + Character.toString(c) + ":" + count[c] + ",";
                
            }
        }
        
        out.write(res);
        out.newLine();
        out.flush();
        
        socket.close();
    }
}
