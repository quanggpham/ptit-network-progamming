/*
[Mã câu hỏi (qCode): t7pt7RxG].  Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s. Ví dụ: với s = 3 thì ký tự “A” sẽ được thay thế bằng ký tự “D”.
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng chương trình client tương tác với server trên, sử dụng các luồng byte (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;D68C93F7"
b.	Nhận lần lượt chuỗi đã bị mã hóa caesar và giá trị dịch chuyển s nguyên
c.	Thực hiện giải mã ra thông điệp ban đầu và gửi lên Server
d.	Đóng kết nối và kết thúc chương trình.
 */
package TCP;


import java.util.*;
import java.net.*;
import java.io.*;

public class TCP_DataStream_t7pt7RxG {
    public static String encrypt(String str, int s)
    {
        StringBuilder sb = new StringBuilder();
        
        for (char c : str.toCharArray())
        {
            if (Character.isUpperCase(c))
            {
                char tmp = (char) (((c - 'A' + s ) % 26) + 'A');
                sb.append(tmp);
            }
            else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2207);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        
        out.writeUTF("B22DCCN016;t7pt7RxG");
        out.flush();
        
        String data = in.readUTF();
        int s = in.readInt();
        
        data = encrypt(data, 26 - s);
        out.writeUTF(data);
        out.flush();
        
        socket.close();
    }
}
