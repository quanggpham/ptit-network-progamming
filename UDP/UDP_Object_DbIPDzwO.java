/*
[Mã câu hỏi (qCode): DbIPDzwO].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:
Đối tượng trao đổi là thể hiện của lớp UDP.Employee được mô tả:
    Tên đầy đủ lớp: UDP.Employee
    Các thuộc tính: id (String), name (String), salary (double), hireDate (String)
    Hàm khởi tạo:
        public Employee(String id, String name, double salary, String hireDate)
    Trường dữ liệu: private static final long serialVersionUID = 20261107L

Thực hiện:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN006;ITleSdqV"
b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Employee từ server. Trong đó, các thuộc tính id, name, salary và hireDate đã được thiết lập sẵn.
c. Thực hiện:
    Chuẩn hóa name: viết hoa chữ cái đầu của mỗi từ, ví dụ "john doe" thành "John Doe".
    Tăng salary: tăng x% lương, với x bằng tổng các chữ số của năm bắt đầu làm việc (hireDate).
    Chuyển đổi hireDate từ định dạng yyyy-mm-dd sang dd/mm/yyyy. Ví dụ: "2023-07-15" thành "15/07/2023".
    Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Employee đã được sửa đổi.
d. Đóng socket và kết thúc chương trình.
 */
package UDP;

import java.util.*;
import java.net.*;
import java.io.*;

public class UDP_Object_DbIPDzwO {
    public static String formatName(String name)
    {
        StringBuilder fixedName = new StringBuilder();
        String[] parts = name.split(" ");
        for (String part : parts)
        {
            fixedName.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1).toLowerCase()).append(" ");
        }
        return fixedName.toString().trim();
    }
    
    public static double updateSalary(int hireDate, double salary)
    {
        int tmp = hireDate;
        int sumDigit = 0;
        while (tmp != 0)
        {
            sumDigit += tmp % 10;
            tmp /= 10;
        }
        salary = salary * (1 + sumDigit / 100.0);
        return salary;
    }
    
    public static String formatHireDate(String hireDate)
    {
        String[] parts = hireDate.split("-");
        String fixedDate = parts[2] + "/" + parts[1] + "/" + parts[0];
        return fixedDate;
    }
    
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2209;
        
        byte[] sendBuf = ";B22DCCN136;DbIPDzwO".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        socket.send(sendPacket);
        
        byte[] inBuf = new byte[2048];
        DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);
        socket.receive(inPacket);
        
        byte[] data = inPacket.getData();
        int len = inPacket.getLength();
        
        byte[] requestId = Arrays.copyOfRange(data, 0, 8);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data, 8, len - 8));
        Employee raw = (Employee) ois.readObject();
        System.out.println("before: " + raw);
        
        String[] hireDate = raw.getHireDate().split("-");
        raw.setName(formatName(raw.getName()));
        raw.setSalary(updateSalary(Integer.parseInt(hireDate[0]), raw.getSalary()));
        raw.setHireDate(formatHireDate(raw.getHireDate()));
        System.out.println("after: " + raw);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(raw);
        oos.flush();
        
        byte[] outBuf = baos.toByteArray();
        DatagramPacket outPacket = new DatagramPacket(outBuf, outBuf.length, address, port);
        socket.send(outPacket);
        
        socket.close();
    }
}
