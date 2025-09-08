
//[Mã câu hỏi (qCode): j3ypHG6J].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B16DCCN999;E56FAB67"
//b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",".
//Ví dụ: " 3,7,2,5,8,1"
//c. Tìm vị trí mà độ lệch của tổng bên trái và tổng bên phải là nhỏ nhất -> Gửi lên server vị trí đó, tổng trái, tổng phải và độ lệch. Ví dụ: với dãy " 3,7,2,5,8,1", vị trí 3 có độ lệch nhỏ nhất = 3 → Kết quả gửi server: "3,12,9,3"
//d. Đóng kết nối và kết thúc chương trình.

package TCP;

import java.util.*;
import java.net.*;
import java.io.*;


public class TCP_ByteStream_j3ypHG6J {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2206);
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        
        output.write("B22DCCN136;j3ypHG6J".getBytes());
        output.flush();
        
        byte buffer[] = new byte[1024];
        int len = input.read(buffer);
        String data = new String(buffer, 0, len);
        System.out.println("data: " + data);
        
        String parts[] = data.trim().split(",");
        List<Integer> nums = new ArrayList<>();
        for (String part : parts)
        {
            nums.add(Integer.parseInt(part));
        }
        
        
        
        int bestLeft = 0;
        int bestRight = 0;
        int bestIndex = -1;
        int minDiff = Integer.MAX_VALUE;
        
        int rightSum = 0;
        int leftSum = 0;
        
        for (int i = 1; i < nums.size(); i++)
        {
            rightSum += nums.get(i);
        }
        
        
        
        for (int i = 1; i < nums.size(); i++)
        {
            
            int diff = Math.abs(rightSum - leftSum);
            
            if (diff < minDiff)
            {
                minDiff = diff;
                bestRight = rightSum;
                bestLeft = leftSum;
                bestIndex = i - 1;
            }
            
            
            rightSum = rightSum - nums.get(i);
            leftSum = leftSum + nums.get(i - 1);
            
            
        }
        
        String result = bestIndex + "," + bestLeft + "," + bestRight + "," + minDiff;
        System.out.println("result: " + result);
        
        output.write(result.getBytes());
        output.flush();
        
        socket.close();
    }
}
