/*
[Mã câu hỏi (qCode): lIQVug9S].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN000;XbYdNZ3”.

b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;b1,b2”, trong đó:
    requestId là chuỗi ngẫu nhiên duy nhất.
    b1 là số nhị phân thứ nhất
    b2 là số nhị phân thứ hai.
Ví dụ: requestId;0100011111001101,1101000111110101
c. Thực hiện tính tổng hai số nhị phân nhận được, chuyển về dạng thập phân và gửi lên server theo định dạng “requestId;sum”
Kết quả: requestId;72130 
d. Đóng socket và kết thúc chương trình.
 */
package UDP;

import java.util.*;
import java.net.*;
import java.io.*;

public class UDP_String_lIQVug9S {
    public static void main(String[] args) throws Exception{
        DatagramSocket soc = new DatagramSocket();
        
        InetAddress addr = InetAddress.getByName("203.162.10.109");
        int port = 2208;
        
        String id = ";B22DCCN016;lIQVug9S";
        soc.send(new DatagramPacket(id.getBytes(), id.length(), addr, port));
        
        byte[] buf = new byte[1024];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);
        soc.receive(recv);
        
        String raw = new String(recv.getData(), 0, recv.getLength());
        String[] parts = raw.split(";", 2);
        String[] nums = parts[1].split(",");
        
        int sum = Integer.parseInt(nums[0], 2) + Integer.parseInt(nums[1], 2);
        String res = parts[0] + ";" + sum;
        
        soc.send(new DatagramPacket(res.getBytes(), res.length(), addr, port));
        soc.close();
    }
}
