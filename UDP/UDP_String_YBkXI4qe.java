/*
[Mã câu hỏi (qCode): YBkXI4qe].  Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
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

public class UDP_String_YBkXI4qe {
    
    public static String chuanHoa(String s)
    {
        String[] arr = s.trim().split("\\s+");
        
        StringBuilder sb = new StringBuilder();
        
        for (String str : arr)
        {
            sb.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1).toLowerCase()).append(" ");
        }
        return sb.toString().trim();
    }
    
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2208;
        
        String id = ";B22DCCN136;YBkXI4qe";
        DatagramPacket sendPacket = new DatagramPacket(id.getBytes(), id.getBytes().length, address, port);
        socket.send(sendPacket);
        
        byte[] recvBuf = new byte[1024];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(recvPacket);
        
        String data = new String(recvPacket.getData(), 0, recvPacket.getLength());
        System.out.println(data);
        String[] tmp = data.split(";", 2);
        
        String requestId = tmp[0];
        String name = chuanHoa(tmp[1]);
        
        String result = requestId + ";" + name;
        
        System.out.println(result);
        
        DatagramPacket resultPacket = new DatagramPacket(result.getBytes(), result.getBytes().length, address, port);
        socket.send(resultPacket);
    }
}
