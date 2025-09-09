/*
[Mã câu hỏi (qCode): dyQH2bcG].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
Giao diện từ xa:
public interface ByteService extends Remote {
public byte[] requestData(String studentCode, String qCode) throws RemoteException;
public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
}
Trong đó:
•	Interface ByteService được viết trong package RMI.
•	Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là: RMIByteService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhị phân nhận được từ RMI Server:
a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server, đại diện cho một chuỗi văn bản ASCII.
b. Thực hiện mã hóa XOR cho mảng dữ liệu nhị phân bằng cách sử dụng một khóa là chuỗi "PTIT". Thực hiện phép XOR trên từng byte trong mảng dữ liệu với byte tương ứng trong khóa (khóa sẽ được lặp lại để khớp với độ dài của mảng dữ liệu).
Ví dụ: Nếu dữ liệu nhị phân nhận được là [72, 101, 108, 108, 111] (tương ứng với chuỗi "Hello"), và khóa là "PTIT", chương trình sẽ thực hiện mã hóa như sau:
•	Chuyển khóa "PTIT" thành mảng byte [80, 84, 73, 84].
•	Lặp lại khóa để khớp độ dài dữ liệu: [80, 84, 73, 84, 80].
•	Thực hiện phép XOR trên từng byte của dữ liệu với khóa:
•	Kết quả mã hóa là mảng [24, 49, 37, 56, 63].
c. Triệu gọi phương thức submitData để gửi mảng dữ liệu đã được mã hóa bằng XOR trở lại server.
d. Kết thúc chương trình client.
 */
package RMI;

import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;

public class RMI_Byte_dyQH2bcG {
    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
        
        ByteService service = (ByteService) registry.lookup("RMIByteService");
        
        String qCode = "dyQH2bcG";
        String studentCode = "B22DCCN136";
        
        byte[] data = (byte[]) service.requestData(studentCode, qCode);
        System.out.println("data: " + Arrays.toString(data));
        
        String ptit = "PTIT";
        byte[] key = ptit.getBytes();
        System.out.println("key: " + Arrays.toString(key));
        
        byte[] result = new byte[data.length];
        
        for (int i = 0; i < result.length; i++)
        {
            result[i] = (byte) (data[i] ^ key[i % 4]);
        }
        System.out.println("result: " + Arrays.toString(result));
        
        service.submitData(studentCode, qCode, result);
    }
    

}
