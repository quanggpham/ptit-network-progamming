/*
[Mã câu hỏi (qCode): D6hC7pHO]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:
 Đối tượng trao đổi là thể hiện của lớp UDP.Student được mô tả:
 • Tên đầy đủ lớp: UDP.Student
 • Các thuộc tính: id String,code String, name String, email String
 • 02 Hàm khởi tạo: 
 o public Student(String id, String code, String name, String email)
 o public Student(String code)
 • Trường dữ liệu: private static final long serialVersionUID = 20171107
 Thực hiện:
 • Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
 b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Student từ server. Trong đó, các thông tin được thiết lập gồm id và name.
 c. Yêu cầu:
 - Chuẩn hóa tên theo quy tắc: Chữ cái đầu tiên in hoa, các chữ cái còn lại in thường và gán lại thuộc tính name của đối tượng
 - Tạo email ptit.edu.vn từ tên người dùng bằng cách lấy tên và các chữ cái bắt đầu của họ và tên đệm. Ví dụ: nguyen van tuan nam -> namnvt@ptit.edu.vn. Gán giá trị này cho thuộc tính email của đối tượng nhận được
 - Gửi thông điệp chứa đối tượng xử lý ở bước c lên Server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Student đã được sửa đổi.
 d. Đóng socket và kết thúc chương trình.
 */
package UDP;

import java.util.*;
import java.io.*;
import UDP.Student;
import java.net.*;

public class UDP_Object_D6hC7pHO {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2209;
        
        byte[] sendBuf = ";B22DCCN136;D6hC7pHO".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        socket.send(sendPacket);
        
        byte[] recvBuf = new byte[2048];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(recvPacket);
        
        byte[] data = recvPacket.getData();
        
        byte[] requestId = new byte[8];
        System.arraycopy(data, 0, requestId, 0, 8);
        
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data, 8, data.length));
        Student raw = (Student)ois.readObject();
        System.out.println("raw: " + raw);
        
        String name = raw.getName();
        String[] parts = name.split("\\s+");
        StringBuilder fixedName = new StringBuilder();
        for (String part : parts)
        {
            fixedName.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1).toLowerCase()).append(" ");
        }
        raw.setName(fixedName.toString().trim());
        
        StringBuilder sb = new StringBuilder();
        sb.append(parts[parts.length - 1]);
        for (int i = 0; i <= parts.length - 2; i++)
        {
            sb.append(parts[i].charAt(0));
        }
        sb.append("@ptit.edu.vn");
        raw.setEmail(sb.toString().toLowerCase());
        
        System.out.println("result: " + raw);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(raw);
        
        byte[] outData = baos.toByteArray();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, address, port);
        socket.send(outPacket);
        
        socket.close();
    }
}
