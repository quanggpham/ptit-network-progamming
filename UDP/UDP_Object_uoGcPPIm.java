//[Mã câu hỏi (qCode): uoGcPPIm].  Thông tin khách hàng được yêu cầu thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
//a.	Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
//b.	Ngày sinh của khách hàng đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
//c.	Tài khoản khách hàng được tạo từ các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong
//
//
//Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server theo mô tả sau:
//a.	Đối tượng trao đổi là thể hiện của lớp UDP.Customer được mô tả như sau
//•	Tên đầy đủ của lớp: UDP.Customer
//•	Các thuộc tính: id String, code String, name String, , dayOfBirth String, userName String
//•	Một Hàm khởi tạo với đầy đủ các thuộc tính được liệt kê ở trên
//•	Trường dữ liệu: private static final long serialVersionUID = 20151107; 
//
//b.	Client giao tiếp với server theo các bước
//•       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
//
//•	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Customer từ server. Trong đó, các thuộc tính id, code, name,dayOfBirth đã được thiết lập sẵn.
//•	Yêu cầu thay đổi thông tin các thuộc tính như yêu cầu ở trên và gửi lại đối tượng khách hàng đã được sửa đổi lên server với cấu trúc:
//08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Customer đã được sửa đổi.
//•	Đóng socket và kết thúc chương trình.

package UDP;

import java.net.*;
import java.io.*;

public class UDP_Object_uoGcPPIm {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2209;
        
        byte[] sendBuf = ";B22DCCN136;uoGcPPIm".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        socket.send(sendPacket);
        
        byte[] recvBuf = new byte[2048];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(recvPacket);
        
        byte[] data = recvPacket.getData();
        int len = recvPacket.getLength();
        
        byte[] requestId = new byte[8];
        
        System.arraycopy(data, 0, requestId, 0, 8);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data, 8, len));
        Customer raw = (Customer) ois.readObject();
        System.out.println(raw);
        
        String[] name = raw.getName().split("\\s+");
        String fixName = name[name.length - 1].toUpperCase() + ",";
        for (int i = 0; i <= name.length - 2; i++)
        {
            fixName += " " + Character.toUpperCase(name[i].charAt(0)) + name[i].substring(1).toLowerCase();
        }
        
        raw.setName(fixName);
        
        String[] dob = raw.getDayOfBirth().split("-");
        String fixedDob = dob[1] + "/" + dob[0] + "/" + dob[2];
        
        raw.setDayOfBirth(fixedDob);
        
        StringBuilder userName = new StringBuilder();
        for (int i = 0; i <= name.length - 2; i++)
        {
            userName.append(Character.toLowerCase(name[i].charAt(0)));
        }
        userName.append(name[name.length - 1].toLowerCase());
        raw.setUserName(userName.toString().trim());
        
        System.out.println("result: " + raw);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(raw);
        oos.flush();
        
        byte[] outData = baos.toByteArray();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, address, port);
        socket.send(outPacket);
        
        socket.close();
    }
}
