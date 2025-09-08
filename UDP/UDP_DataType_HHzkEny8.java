
//[Mã câu hỏi (qCode): HHzkEny8].  
//Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN004;99D9F604”
//b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;z1,z2,...,z50” requestId là chuỗi ngẫu nhiên duy nhất
//    z1 -> z50 là 50 số nguyên ngẫu nhiên
//    c. Thực hiện tính số lớn thứ hai và số nhỏ thứ hai của thông điệp trong z1 -> z50 và gửi thông điệp lên server theo định dạng “requestId;secondMax,secondMin”
//    d. Đóng socket và kết thúc chương trình

package UDP;

import java.util.*;
import java.io.*;
import java.net.*;

public class UDP_DataType_HHzkEny8 {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
        int serverPort = 2207;
        
        String id = ";B22DCCN136;HHzkEny8";
        DatagramPacket sendPacket = new DatagramPacket(id.getBytes(), id.getBytes().length, serverAddress, serverPort);
        socket.send(sendPacket);
        
        byte recvBuf[] = new byte[1024];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(recvPacket);
        
        String data = new String (recvPacket.getData(), 0, recvPacket.getLength());
        System.out.println("data: " + data);
        
        String tmp[] = data.split(";", 2);
        String requestId = tmp[0];
        String numbers = tmp[1];
        
        List<Integer> nums = new ArrayList<>();
        
        for (String s : numbers.split(","))
        {
            nums.add(Integer.parseInt(s));
        }
        
        Collections.sort(nums);
        
        int secondMax = nums.get(nums.size() - 2);
        int secondMin = nums.get(1);
        
        String result = requestId + ";" + secondMax + "," + secondMin;
        System.out.println("result: " + result);
        
        DatagramPacket resPacket = new DatagramPacket(result.getBytes(), result.getBytes().length, serverAddress, serverPort);
        socket.send(resPacket);
    }
}
