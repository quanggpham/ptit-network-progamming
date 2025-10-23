/*
[Mã câu hỏi (qCode): 0ChNPBGH].  Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;5B35BCC1”
b.	Nhận thông điệp từ server theo định dạng “requestId;data” 
-	requestId là một chuỗi ngẫu nhiên duy nhất
-	data là chuỗi dữ liệu cần xử lý
c.	Xử lý chuẩn hóa chuỗi đã nhận thành theo nguyên tắc 
i.	Ký tự đầu tiên của từng từ trong chuỗi là in hoa
ii.	Các ký tự còn lại của chuỗi là in thường
Gửi thông điệp chứa chuỗi đã được chuẩn hóa lên server theo định dạng “requestId;data”
d.	Đóng socket và kết thúc chương trình
 */
package UDP;

import java.util.*;
import java.io.*;
import java.net.*;

public class UDP_String_0ChNPBGH {
    public static void main(String[] args) throws Exception{
        DatagramSocket s = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("203.162.10.109");
        int port = 2208;
        
        String sendData = ";B22DCCN136;0ChNPBGH";
        s.send(new DatagramPacket(sendData.getBytes(), 20, addr, port));
        
        byte[] buffer = new byte[1024];
        DatagramPacket recvPacket = new DatagramPacket(buffer, buffer.length);
        s.receive(recvPacket);
        
        String data = new String(recvPacket.getData(), 0, recvPacket.getLength());
        
        String[] parts = data.split(";", 2);
        String requestId = parts[0];
        String raw = parts[1];
        
        StringBuilder sb = new StringBuilder();
        String[] lst = raw.split("\\s+");
        for (String str : lst)
        {
            sb.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1).toLowerCase()).append(" ");
        }
        
        String res = requestId + ";" + sb.toString().trim();
        s.send(new DatagramPacket(res.getBytes(), res.length(), addr, port));
    }
}
