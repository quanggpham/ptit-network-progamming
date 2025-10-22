/*
[Mã câu hỏi (qCode): MzpR5Z5g].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
a) Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
b) Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) để gửi/nhận và sửa các thông tin bị sai của sản phẩm. Chi tiết dưới đây:
a) Đối tượng trao đổi là thể hiện của lớp Laptop được mô tả như sau
      •	Tên đầy đủ của lớp: TCP.Laptop
      •	Các thuộc tính: id int, code String, name String, quantity int
      •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
      •	Trường dữ liệu: private static final long serialVersionUID = 20150711L; 
b)	Tương tác với server theo kịch bản
1)	Gửi đối tượng là chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;5AD2B818"
2)	Nhận một đối tượng là thể hiện của lớp Laptop từ server
3)	Sửa các thông tin sai của sản phẩm về tên và số lượng.  Gửi đối tượng vừa được sửa sai lên server
4)	Đóng socket và kết thúc chương trình.
 */
package TCP;

import java.util.*;
import java.io.*;
import java.net.*;
import TCP.Laptop;

public class TCP_ObjectStream_MzpR5Z5g {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("203.162.10.109", 2209);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        
        out.writeObject("B22DCCN136;MzpR5Z5g");
        out.flush();
        
        Laptop laptop = (Laptop) in.readObject();
        
        String[] parts = laptop.name.split("\\s+");
        String tmp = parts[0];
        parts[0] = parts[parts.length - 1];
        parts[parts.length - 1] = tmp;
        laptop.name = String.join(" ", parts);
        
        laptop.quantity = Integer.parseInt(new StringBuilder().append(laptop.quantity).reverse().toString());
        
        out.writeObject(laptop);
        out.flush();
        
        socket.close();
    }
}
