/*
[Mã câu hỏi (qCode): TjIe84X1].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để gán mã code cho sách trong một hệ thống quản lý thư viện. Chương trình sẽ ngẫu nhiên tạo ra đối tượng Book với các giá trị ban đầu và cung cấp cho RMI client như sau:
Giao diện từ xa:
public interface ObjectService extends Remote {
    public Serializable requestObject(String studentCode, String qCode) throws RemoteException;
    public void submitObject(String studentCode, String qCode, Serializable object) throws RemoteException;
}
Lớp Book gồm các thuộc tính: id String, title String, author String, yearPublished int, pageCount int, code String.
Trường dữ liệu: private static final long serialVersionUID = 20241123L;
02 hàm khởi dựng:
    public Book()
    public Book(String id, String title, String author, int yearPublished, int pageCount)
Trong đó:
    Interface ObjectService và lớp Book được viết trong package RMI.
    Đối tượng cài đặt giao diện từ xa ObjectService được đăng ký với RegistryServer: RMIObjectService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với đối tượng sách được nhận từ RMI Server:
a. Triệu gọi phương thức requestObject để nhận đối tượng Book ngẫu nhiên từ server.
b. Tạo mã code cho sách dựa trên các thành phần sau:
•	Lấy chữ cái đầu của họ và tên đầu của tác giả. Ví dụ, với tác giả "John Doe", hai chữ cái đầu sẽ là "JD".
•	Lấy hai chữ số cuối cùng của năm xuất bản (yearPublished). Ví dụ, nếu yearPublished là 1985, sẽ lấy "85".
•	Lấy tổng số chữ cái trong title của sách.
•	Lấy số chữ số của pageCount (số trang) và nếu số chữ số < 3, thêm các số 0 ở đầu để đạt đủ ba chữ số.
Kết hợp tất cả các thành phần trên để tạo ra mã code theo định dạng: "[Chữ cái đầu tên tác giả][Hai chữ số cuối của năm xuất bản][Tổng số chữ cái trong title][Số chữ số của pageCount]". Ví dụ, nếu tác giả là "Jane Austen", yearPublished là 1813, title là "Pride and Prejudice" với 18 ký tự, và pageCount là 345, thì mã code sẽ là: "JA1318345".
c. Cập nhật giá trị code trong đối tượng Book.
d. Triệu gọi phương thức submitObject để gửi đối tượng Book đã được xử lý trở lại server.
e. Kết thúc chương trình client.
 */
package RMI;

import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;
import RMI.Book;

public class RMI_Object_TjIe84X1 {
    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
        
        ObjectService service = (ObjectService) registry.lookup("RMIObjectService");
        
        String studentCode = "B22DCCN136";
        String qCode = "TjIe84X1";
        
        Book data = (Book) service.requestObject(studentCode, qCode);
        System.out.println("raw: " + data);
        
        String code = "";
        
        String[] parts = data.getAuthor().split(" ");
        code += "" + parts[0].toUpperCase().charAt(0) + parts[parts.length - 1].toUpperCase().charAt(0);

        
        String yearPublished = Integer.toString(data.getYearPublished());
        code += yearPublished.substring(yearPublished.length() - 2);
        
        int titleCharCount = data.getTitle().replaceAll("[^a-zA-Z]", "").length();
        code += titleCharCount + "";
        
        int pageDigitCount = Integer.toString(data.getPageCount()).length();
        if (pageDigitCount < 3)
        {
            int thieu = 3 - pageDigitCount;
            for (int i = 1; i <= thieu; i++)
            {
                code += "0";
            }
        }
        else
        {
            code += data.getPageCount() + "";
        }
        
        data.setCode(code);
        System.out.println("result: " + data);
        
        service.submitObject(studentCode, qCode, data);
    }
}
