/*
[Mã câu hỏi (qCode): nE6M5TSp].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode".
Ví dụ: ";B15DCCN010;D3F9A7B8"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;a;b", với:
•	requestId là chuỗi ngẫu nhiên duy nhất.
•	a và b là chuỗi thể hiện hai số nguyên lớn (hơn hoặc bằng 10 chữ số).
Ví dụ: "X1Y2Z3;9876543210;123456789"
c. Tính tổng và hiệu của hai số a và b, gửi thông điệp lên server theo định dạng "requestId;sum;difference".Ví dụ: 
Nếu nhận được "X1Y2Z3;9876543210,123456789", tổng là 9999999999 và hiệu là 9753086421. Kết quả gửi lại sẽ là "X1Y2Z3;9999999999,9753086421".
d. Đóng socket và kết thúc chương trình
 */
package UDP;

import java.util.*;
import java.net.*;
import java.io.*;
import java.math.BigInteger;

public class UDP_DataType_nE6M5TSp {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2207;
        
        String id = ";B22DCCN136;nE6M5TSp";
        DatagramPacket sendPacket = new DatagramPacket(id.getBytes(), id.getBytes().length, address, port);
        socket.send(sendPacket);
        
        byte[] recvBuf = new byte[1024];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(recvPacket);
        
        String raw = new String(recvPacket.getData(), 0, recvPacket.getLength());
        System.out.println("raw: " + raw);
        String[] arr = raw.split(";");
        
        String requestId = arr[0];
        BigInteger a = new BigInteger(arr[1]);
        BigInteger b = new BigInteger(arr[2]);
        
        BigInteger sum = a.add(b);
        BigInteger substract = a.subtract(b);
        
        String result = requestId + ";" + sum + "," + substract;
        System.out.println("result: " + result);
        DatagramPacket resPacket = new DatagramPacket(result.getBytes(), result.getBytes().length, address, port);
        socket.send(resPacket);
    }
}
