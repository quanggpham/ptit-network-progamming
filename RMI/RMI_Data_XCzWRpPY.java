
// [Mã câu hỏi (qCode): XCzWRpPY].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
// Giao diện từ xa:
// public interface DataService extends Remote {
// public Object requestData(String studentCode, String qCode) throws RemoteException;
// public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
// }
// Trong đó:
// •	Interface DataService được viết trong package RMI.
// •	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
// a. Triệu gọi phương thức requestData để nhận một số nguyên dương N từ server, đại diện cho giới hạn trên của khoảng cần kiểm tra.
// b. Thực hiện tìm tất cả các số nguyên tố trong khoảng từ 1 đến N. Ví dụ: Với N = 10, kết quả là danh sách các số nguyên tố “2, 3, 5, 7”.
// c. Triệu gọi phương thức submitData để gửi List< Integer> danh sách các số nguyên tố đã tìm được trở lại server.
// d. Kết thúc chương trình client.

package RMI;

import java.rmi.*;
import java.util.*;
import java.rmi.registry.*;

public class RMI_Data_XCzWRpPY {
    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
        
        DataService service =(DataService) registry.lookup("RMIDataService");
        
        String qCode = "XCzWRpPY";
        String studentCode = "B22DCCN136";
        
        Integer data = (Integer) service.requestData(studentCode, qCode);
        System.out.println("data: " + data);
        
        List<Integer> result = findPrimes(data);
        
        service.submitData(studentCode, qCode, result);
    }
    
    public static List<Integer> findPrimes(int n)
    {
        List<Integer> list = new ArrayList<>();
        for (int i = 2; i <= n; i++)
        {
            if (isPrime(i))
            {
                list.add(i);
            }
        }
        return list;
    }
    
    public static boolean isPrime(int n)
    {
        for (int i = 2; i <= Math.sqrt(n); i++)
        {
            if (n % i == 0)
                return false;
        }
        return n > 1;
    }
}
