/*
[Mã câu hỏi (qCode): AeDYwwJV].  Một chương trình máy chủ cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5s), yêu cầu xây dựng chương trình (tạm gọi là client) thực hiện kết nối tới server tại cổng 2207, sử dụng luồng byte dữ liệu (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự: 
a.	Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1D25ED92"
b.	Nhận lần lượt hai số nguyên a và b từ server
c.	Thực hiện tính toán tổng, tích và gửi lần lượt từng giá trị theo đúng thứ tự trên lên server
d.	Đóng kết nối và kết thúc
 */
package TCP;

import java.util.*;
import java.io.*;
import java.net.*;

public class TCP_DataStream_AeDYwwJV {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2207);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        
        out.writeUTF("B22DCCN136;AeDYwwJV");
        out.flush();
        
        int a = in.readInt();
        int b = in.readInt();
        
        int sum = a + b;
        int m = a * b;
        
        out.writeInt(sum);
        out.writeInt(m);
        out.flush();
        
        socket.close();
    }
}
