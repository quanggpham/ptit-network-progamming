
package RMI;

import java.rmi.*;

public interface DataService extends Remote{
    public Object requestData(String studentCode, String qcode) throws RemoteException;
    public void submitData(String studentCode, String qcode, Object data) throws RemoteException;
}
