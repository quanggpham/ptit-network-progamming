
package TCP;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.stream.Collectors;

//[Mã câu hỏi (qCode): N1HCm5f8].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B10DCCN003;C6D7E8F9"
//b. Nhận lần lượt:
//•	Một số nguyên k là độ dài đoạn.
//•	Chuỗi chứa mảng số nguyên, các phần tử được phân tách bởi dấu phẩy ",".
//Ví dụ: Nhận k = 3 và "1,2,3,4,5,6,7,8".
//c. Thực hiện chia mảng thành các đoạn có độ dài k và đảo ngược mỗi đoạn, sau đó gửi mảng đã xử lý lên server. Ví dụ: Với k = 3 và mảng "1,2,3,4,5,6,7,8", kết quả là "3,2,1,6,5,4,8,7". Gửi chuỗi kết quả "3,2,1,6,5,4,8,7" lên server.
//d. Đóng kết nối và kết thúc chương trình

public class TCP_DataStream_N1HCm5f8 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2207);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        
        output.writeUTF("B22DCCN136;N1HCm5f8");
        output.flush();
        
        int k = input.readInt();
        System.out.println("k: " + k);
        String str = input.readUTF().trim();
        System.out.println("data: " + str);
        str = str.replace(",", " ");
        String parts[] = str.split("\\s+");
        List<Integer> numbers = new ArrayList<>();
        for (String s : parts)
        {
            numbers.add(Integer.parseInt(s));
        }
        
        List<Integer> result = new ArrayList<>();
        
        for (int i = 0; i < numbers.size(); i+=k)
        {
            int end = Math.min(numbers.size(), i + k);
            
            List<Integer> tmpList = new ArrayList<>();
            for (int j = i; j < end; j++)
            {
                tmpList.add(numbers.get(j));
            }
            
            Collections.reverse(tmpList);
            
            result.addAll(tmpList);
        }
        
        String resString = result.stream()
                                 .map(String::valueOf)
                                 .collect(Collectors.joining(","));
        System.out.println("result: " + resString);
        output.writeUTF(resString);
        output.flush();
        
        socket.close();
    }
}
