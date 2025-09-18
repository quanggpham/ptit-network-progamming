/*
[Mã câu hỏi (qCode): ZiS2XrS4].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
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

import UDP.Product;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class UDP_Object_ZiS2XrS4 {
public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();

        String msg = ";B22DCCN136;ZiS2XrS4";
        byte[] sendData = msg.getBytes(StandardCharsets.UTF_8);
        DatagramPacket sendPacket =
            new DatagramPacket(sendData, sendData.length,
                               InetAddress.getByName("203.162.10.109"), 2209);
        socket.send(sendPacket);

        byte[] buf = new byte[65535];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);

        byte[] data = receivePacket.getData();
        int len = receivePacket.getLength();

        String requestId = new String(data, 0, 8);
        ObjectInputStream ois =
            new ObjectInputStream(new ByteArrayInputStream(data, 8, len - 8));
        Product p = (Product) ois.readObject();
        ois.close();

        System.out.println("Nhận được: " + p);


        String fixedName = fixName(p.getName());
        int fixedQuantity = fixQuantity(p.getQuantity());
        Product fixed = new Product(p.getId(), p.getCode(), fixedName, fixedQuantity);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId.getBytes());
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(fixed);
        oos.flush();

        byte[] outData = baos.toByteArray();
        DatagramPacket outPacket =
            new DatagramPacket(outData, outData.length,
                               receivePacket.getAddress(), receivePacket.getPort());
        socket.send(outPacket);

        socket.close();
    }

    private static String fixName(String wrongName) {
        String[] parts = wrongName.split(" ");
        String tmp = parts[0];
        parts[0] = parts[parts.length - 1];
        parts[parts.length - 1] = tmp;
        return String.join(" ", parts);
    }

    private static int fixQuantity(int wrongQty) {
        String reversed = new StringBuilder(String.valueOf(wrongQty)).reverse().toString();
        return Integer.parseInt(reversed);
    }
}
