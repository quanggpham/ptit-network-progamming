/*
[Mã câu hỏi (qCode): 04pRyyyW].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:

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

import java.util.*;
import java.net.*;
import java.io.*;
import java.nio.channels.DatagramChannel;
import UDP.Book;

public class UDP_Object_04pRyyyW {
    public static void main(String[] args) throws Exception{
        DatagramSocket soc = new DatagramSocket();
        
        InetAddress addr = InetAddress.getByName("203.162.10.109");
        int port = 2209;
        
        String id = ";B22DCCN016;04pRyyyW";
        soc.send(new DatagramPacket(id.getBytes(), id.length(), addr, port));
        
        byte[] buf = new byte[1024];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);
        soc.receive(recv);
        
        byte[] raw = recv.getData();
        int len = recv.getLength();
        
        byte[] requestId = new byte[8];
        System.arraycopy(raw, 0, requestId, 0, 8);
        
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(raw, 8, len - 8));
        Book data = (Book) ois.readObject();
        
        String[] titles = data.title.split("\\s+");
        for (int i = 0; i < titles.length; i++)
        {
            titles[i] = titles[i].substring(0, 1).toUpperCase() + titles[i].substring(1).toLowerCase();
        }
        data.title = String.join(" ", titles);
                
        String[] words = data.author.split("\\s+");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(words[0].toUpperCase()).append(",");
        for (int i = 1; i < words.length; i++)
        {
            sb2.append(" ").append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1).toLowerCase());
        }
        data.author = sb2.toString();
        
        String isbn = data.isbn;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(isbn.substring(0, 3)).append("-");
        sb3.append(isbn.substring(3, 4)).append("-");
        sb3.append(isbn.substring(4, 6)).append("-");
        sb3.append(isbn.substring(6, 12)).append("-");
        sb3.append(isbn.substring(12));
        data.isbn = sb3.toString();
        
        String[] dates = data.publishDate.split("-");
        data.publishDate = dates[1] + "/" + dates[0];
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId);
        new ObjectOutputStream(baos).writeObject(data);
        
        
        byte[] sendBuf = baos.toByteArray();
        soc.send(new DatagramPacket(sendBuf, sendBuf.length, addr, port));
        soc.close();
                
    }
}
