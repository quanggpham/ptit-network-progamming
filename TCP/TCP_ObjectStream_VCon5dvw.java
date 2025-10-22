/*
[Mã câu hỏi (qCode): VCon5dvw].  Thông tin khách hàng cần thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
a.	Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
b.	Ngày sinh của khách hàng hiện đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
c.	Tài khoản khách hàng là các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) thực hiện gửi/nhận đối tượng khách hàng và chuẩn hóa. Cụ thể:
a.	Đối tượng trao đổi là thể hiện của lớp Customer được mô tả như sau
      •	Tên đầy đủ của lớp: TCP.Customer
      •	Các thuộc tính: id int, code String, name String, dayOfBirth String, userName String
      •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
      •	Trường dữ liệu: private static final long serialVersionUID = 20170711L; 
b.	Tương tác với server theo kịch bản dưới đây:
	1) Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;F2DA54F3"
	2) Nhận một đối tượng là thể hiện của lớp Customer từ server với các thông tin đã được thiết lập
	3) Thay đổi định dạng theo các yêu cầu ở trên và gán vào các thuộc tính tương ứng.  Gửi đối tượng đã được sửa đổi lên server
	4) Đóng socket và kết thúc chương trình.
 */
package TCP;

import java.util.*;
import java.io.*;
import java.net.*;
import TCP.Customer;

public class TCP_ObjectStream_VCon5dvw {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2209);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        
        out.writeObject("B22DCCN016;VCon5dvw");
        out.flush();
        
        Customer raw = (Customer) in.readObject();
        String[] parts = raw.name.split("\\s+");
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(parts[parts.length - 1].toUpperCase()).append(",");
        for (int i = 0 ; i < parts.length - 1; i++)
        {
            sb.append(" ").append(Character.toUpperCase(parts[i].charAt(0))).append(parts[i].substring(1).toLowerCase());
        }
        raw.name = sb.toString();
        String[] ar2 = raw.dayOfBirth.split("-");
        raw.dayOfBirth = ar2[1] + "/" + ar2[0] + "/" + ar2[2];
        
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++)
        {
            sb2.append(Character.toLowerCase(parts[i].charAt(0)));
        }
        sb2.append(parts[parts.length - 1].toLowerCase());
        raw.userName = sb2.toString();
        
        out.writeObject(raw);
        out.flush();
         socket.close();
    }
}
