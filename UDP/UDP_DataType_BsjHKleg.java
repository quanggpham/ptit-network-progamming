/*
[Mã câu hỏi (qCode): BsjHKleg].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:

a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN011;A1F3D5B7".

b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;num", với:
   - requestId là chuỗi ngẫu nhiên duy nhất.
   - num là một số nguyên lớn.

c. Tính tổng các chữ số trong num và gửi lại tổng này về server theo định dạng "requestId;sumDigits".

d. Đóng socket và kết thúc chương trình.
 */
package UDP;

import java.util.*;
import java.io.*;
import java.net.*;

public class UDP_DataType_BsjHKleg {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2207;
        
        String id = ";B22DCCN136;BsjHKleg";
        socket.send(new DatagramPacket(id.getBytes(), id.length(), address, port));
        
        byte[] buffer = new byte[2048];
        DatagramPacket recvPacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(recvPacket);
        
        String data = new String(recvPacket.getData(), 0, recvPacket.getLength());
        
        String[] parts = data.split(";", 2);
        String requestId = parts[0];
        String num = parts[1];
        
        int sumDigit = sumDigit(num);
        String res = requestId + ";" + sumDigit;
        
        socket.send(new DatagramPacket(res.getBytes(), res.length(), address, port));
        
        socket.close();
    }
    
    public static int sumDigit(String s)
    {
        int sum = 0;
        for (char c : s.toCharArray())
        {
            sum += c - '0';
        }
        return sum;
    }
}
