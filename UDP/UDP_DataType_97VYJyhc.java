/*
[Mã câu hỏi (qCode): 97VYJyhc].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B21DCCN795;ylrhZ6UM".
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;n;k;z1,z2,...,zn", trong đó:
    requestId là chuỗi ngẫu nhiên duy nhất.
    n là số phần tử của mảng.
    k là kích thước cửa sổ trượt (k < n).
    z1 đến zn là n phần tử là số nguyên của mảng.

c. Thực hiện tìm giá trị lớn nhất trong mỗi cửa sổ trượt với kích thước k trên mảng số nguyên nhận được, và gửi thông điệp lên server theo định dạng "requestId;max1,max2,...,maxm", trong đó max1 đến maxm là các giá trị lớn nhất tương ứng trong mỗi cửa sổ.
Ví dụ: "requestId;5;3;1,5,2,3,4"
Kết quả: "requestId;5,5,4"
d. Đóng socket và kết thúc chương trình.
 */
package UDP;

import java.util.*;
import java.net.*;
import java.io.*;

public class UDP_DataType_97VYJyhc {
    public static void main(String[] args) throws Exception{
        DatagramSocket soc = new DatagramSocket();
        
        InetAddress addr = InetAddress.getByName("203.162.10.109");
        int port = 2207;
        
        String id = ";B22DCCN016;97VYJyhc";
        soc.send(new DatagramPacket(id.getBytes(), 20, addr, port));
         
        byte[] buf = new byte[1024];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);
        soc.receive(recv);
        
        String data = new String(recv.getData(), 0, recv.getLength());
        String[] parts = data.split(";");
        int n = Integer.parseInt(parts[1]);
        int k = Integer.parseInt(parts[2]);
        List<Integer> arr = new ArrayList<>();
        for (String s : parts[3].split(","))
        {
            arr.add(Integer.parseInt(s));
        }
        
        StringBuilder sb = new StringBuilder(parts[0]+ ";");
        for (int i = 0; i <= n - k; i++)
        {
            int max = arr.get(i);
            for (int j = 1; j < k; j++)
            {
                if (arr.get(i + j) > max)
                    max = arr.get(i + j);
            }
            if (i > 0)
                sb.append(",");
            sb.append(max);
        }
        
        String res = sb.toString();
        soc.send(new DatagramPacket(res.getBytes(), res.length(), addr, port));
        soc.close();
    }
}
