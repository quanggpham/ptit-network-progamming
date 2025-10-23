/*
[Mã câu hỏi (qCode): i06wzZUE].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
a.	Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
b.	Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899

Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server để gửi/nhận các sản phẩm theo mô tả dưới đây:
a.	Đối tượng trao đổi là thể hiện của lớp Product được mô tả như sau
•	Tên đầy đủ của lớp: UDP.Product
•	Các thuộc tính: id String, code String, name String, quantity int
•	Một hàm khởi tạo có đầy đủ các thuộc tính được liệt kê ở trên
•	Trường dữ liệu: private static final long serialVersionUID = 20161107; 
b.	Giao tiếp với server theo kịch bản
•       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”

•	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Product từ server. Trong đối tượng này, các thuộc tính id, name và quantity đã được thiết lập giá trị.
•	Sửa các thông tin sai của đối tượng về tên và số lượng như mô tả ở trên và gửi đối tượng vừa được sửa đổi lên server theo cấu trúc:
08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Product đã được sửa đổi.
•	Đóng socket và kết thúc chương trình.
 */
package UDP;

import java.util.*;
import java.io.*;
import java.net.*;
import UDP.Product;

public class UDP_Object_i06wzZUE {
    public static void main(String[] args) throws Exception{
        DatagramSocket soc = new DatagramSocket();
        
        InetAddress addr = InetAddress.getByName("203.162.10.109");
        int port = 2209;
        
        String id = ";B22DCCN136;i06wzZUE";
        soc.send(new DatagramPacket(id.getBytes(), 20, addr, port));
        
        byte[] buf = new byte[1024];
        DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);
        soc.receive(recvPacket);
        
        byte[] raw = recvPacket.getData();
        int len = recvPacket.getLength();
        
        byte[] requestId = new byte[8];
        System.arraycopy(raw, 0, requestId, 0, 8);
        
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(raw, 8, len - 8));
        Product data = (Product) ois.readObject();
        
        String[] words = data.name.split("\\s+");
        String temp = words[0];
        words[0] = words[words.length - 1];
        words[words.length - 1] = temp;
        data.name = String.join(" ", words);
        
        data.quantity = Integer.parseInt(new StringBuilder().append(data.quantity).reverse().toString());
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(data);
        
        soc.send(new DatagramPacket(baos.toByteArray(), baos.toByteArray().length, addr, port));
        soc.close();
    }
}
