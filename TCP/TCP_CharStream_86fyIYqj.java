/*
[Mã câu hỏi (qCode): 86fyIYqj].  [Loại bỏ ký tự đặc biệt, trùng và giữ nguyên thứ tự xuất hiện] Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản dưới đây:
a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;7D6265E3"
b.	Nhận một chuỗi ngẫu nhiên từ server
c.	Loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi đã được xử lý lên server.
d.	Đóng kết nối và kết thúc chương trình
 */
package TCP;

import java.util.*;
import java.io.*;
import java.net.*;

public class TCP_CharStream_86fyIYqj {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2208);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        out.write("B22DCCN016;86fyIYqj");
        out.newLine();
        out.flush();
        
        String data = in.readLine();
        int[] arr = new int[255];
        System.out.println(data);
        
        StringBuilder sb = new StringBuilder();
        for (char c : data.toCharArray())
        {
            if (Character.isAlphabetic(c) && arr[c] == 0)
            {
                sb.append(c);
                arr[c]++;
            }
        }
        
        out.write(sb.toString());
        out.newLine();
        out.flush();
        System.out.println(sb.toString());
        
        socket.close();
    }
}
