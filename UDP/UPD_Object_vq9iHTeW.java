/*
[Mã câu hỏi (qCode): vq9iHTeW]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:

Đối tượng trao đổi là thể hiện của lớp UDP.Book được mô tả:

 Tên đầy đủ lớp: UDP.Book
 Các thuộc tính: id (String), title (String), author (String), isbn (String), publishDate (String)
 Hàm khởi tạo:
 public Book(String id, String title, String author, String isbn, String publishDate)
 Trường dữ liệu: private static final long serialVersionUID = 20251107L

Thực hiện:

a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN005;eQkvAeId"

b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Book từ server. Trong đó, các thuộc tính id, title, author, isbn, và publishDate đã được thiết lập sẵn.

c. Thực hiện:
 Chuẩn hóa title: viết hoa chữ cái đầu của mỗi từ.
 Chuẩn hóa author theo định dạng "Họ, Tên".
 Chuẩn hóa mã ISBN theo định dạng "978-3-16-148410-0"
 Chuyển đổi publishDate từ yyyy-mm-dd sang mm/yyyy.
 d. Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Book đã được sửa đổi.

Đóng socket và kết thúc chương trình.


 */
package UDP;

import java.net.*;
import java.util.*;
import UDP.Book;
import java.io.*;

public class UPD_Object_vq9iHTeW {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        
        InetAddress address = InetAddress.getByName("203.162.10.109");
        int port = 2209;
        
        byte[] sendBuf = ";B22DCCN136;vq9iHTeW".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        socket.send(sendPacket);
        
        byte[] recvBuf = new byte[2048];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(recvPacket);
        
        byte[] data = recvPacket.getData();
        
        byte[] requestId = new byte[8];
        System.arraycopy(data, 0, requestId, 0, 8);
        
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data, 8, data.length));
        Book raw = (Book) ois.readObject();
        System.out.println("before: " + raw);
        
        String[] titleParts = raw.getTitle().split("\\s+");
        StringBuilder fixedTitle = new StringBuilder();
        for (String part : titleParts)
        {
            fixedTitle.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1).toLowerCase()).append(" ");
        }
        raw.setTitle(fixedTitle.toString().trim());
        
        String[] nameParts = raw.getAuthor().split("\\s+");
        StringBuilder fixedName = new StringBuilder();
        fixedName.append(nameParts[0].toUpperCase()).append(",");
        for (int i = 1; i <= nameParts.length - 1; i++)
        {
            fixedName.append(" ").append(Character.toUpperCase(nameParts[i].charAt(0))).append(nameParts[i].substring(1).toLowerCase());
        }
        raw.setAuthor(fixedName.toString().trim());
        
        String isbn = raw.getIsbn();
        List<String> isbnParts= new ArrayList<>();
        isbnParts.add(isbn.substring(0, 3));
        isbnParts.add(isbn.substring(3, 4));
        isbnParts.add(isbn.substring(4, 6));
        isbnParts.add(isbn.substring(6, 12));
        isbnParts.add(isbn.substring(12));
        String fixedIsbn = String.join("-", isbnParts);
        raw.setIsbn(fixedIsbn);
        
        String[] dateParts = raw.getPublishDate().split("-");
        String fixedDate = dateParts[1] + "/" + dateParts[0];
        raw.setPublishDate(fixedDate);
        
        System.out.println("after: " + raw);
        
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
