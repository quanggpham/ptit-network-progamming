/*
[Mã câu hỏi (qCode): ASdmVqbn].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: ";B15DCCN009;EF56GH78"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;data", với:
•	requestId là chuỗi ngẫu nhiên duy nhất.
•	data là một chuỗi ký tự chứa nhiều từ, được phân cách bởi dấu cách.
Ví dụ: "EF56GH78;The quick brown fox"
c. Sắp xếp các từ trong chuỗi theo thứ tự từ điển ngược (z đến a) và gửi thông điệp lên server theo định dạng "requestId;word1,word2,...,wordN".
Ví dụ: Với data = "The quick brown fox", kết quả là: "EF56GH78;quick,fox,brown,The"
d. Đóng socket và kết thúc chương trình
 */
package UDP;

import java.util.*;
import java.io.*;
import java.net.*;


public class UDP_String_ASdmVqbn {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2208;
        
        String id = ";B22DCCN136;ASdmVqbn";
        DatagramPacket sendPacket = new DatagramPacket(id.getBytes(), id.getBytes().length, address, port);
        socket.send(sendPacket);
        
        byte[] recvBuf = new byte[1024];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(recvPacket);
        
        String raw = new String(recvPacket.getData(), 0, recvPacket.getLength());
        System.out.println("raw: " + raw);
        String[] arr = raw.split(";", 2);
        String requestId = arr[0];
        String data = arr[1];
        
        
        
        List<String> words = new ArrayList<>();
        
        for (String s : data.split("\\s+"))
        {
            words.add(s);
        }
        
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
            
        });
        
        String result = requestId + ";" + String.join(",", words);
        System.out.println("result: " + result);
        
        DatagramPacket resPacket = new DatagramPacket(result.getBytes(), result.getBytes().length, address, port);
        socket.send(resPacket);
    }
}
