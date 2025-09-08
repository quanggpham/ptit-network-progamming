
//[Mã câu hỏi (qCode): jxUGfwXe].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectOutputStream/ObjectInputStream) để gửi/nhận và chuẩn hóa thông tin địa chỉ của khách hàng.
//Biết rằng lớp TCP.Address có các thuộc tính (id int, code String, addressLine String, city String, postalCode String) và trường dữ liệu private static final long serialVersionUID = 20180801L.
//a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;A1B2C3D4"
//b. Nhận một đối tượng là thể hiện của lớp TCP.Address từ server. Thực hiện chuẩn hóa thông tin addressLine bằng cách:
//•	Chuẩn hóa addressLine: Viết hoa chữ cái đầu mỗi từ, in thường các chữ còn lại, loại bỏ ký tự đặc biệt và khoảng trắng thừa (ví dụ: "123 nguyen!!! van cu" → "123 Nguyen Van Cu") 
//•	Chuẩn hóa postalCode: Chỉ giữ lại số và ký tự "-" ví dụ: "123-456"
//c. Gửi đối tượng đã được chuẩn hóa thông tin địa chỉ lên server.
//d. Đóng kết nối và kết thúc chương trình.

package TCP;

import TCP.Address;
import java.util.*;
import java.net.*;
import java.io.*;


public class TCP_ObjectStream_jxUGfwXe {
    public static String chuanHoaAddressLine(String addressLine)
    {
        if (addressLine.equals(""))
            return "";
        addressLine = addressLine.replaceAll("[^A-Za-z0-9]", " ");
        addressLine = addressLine.trim().replaceAll("\\s+", " ");
        
        StringBuilder sb = new StringBuilder();
        
        for (String word : addressLine.split(" "))
        {
            sb.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1))
              .append(" ");
        }
        return sb.toString().trim();
    }
    
    public static String chuanHoaPostalCode(String postalCode)
    {
        postalCode = postalCode.replaceAll("[^0-9-]", "");
        return postalCode;
    }
    
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2209);
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        
        output.writeObject("B22DCCN136;jxUGfwXe");
        output.flush();
        
        Address data = (Address) input.readObject();
        
        System.out.println(data);
        data.setAddressLine(chuanHoaAddressLine(data.getAddressLine()));
        data.setPostalCode(chuanHoaPostalCode(data.getPostalCode()));
        System.out.println(data);
        
        output.writeObject(data);
        output.flush();
        
        socket.close();
    }
}
