
package TCP;

import java.io.Serializable;


public class Customer implements Serializable{
    public int id;
    public String code, name, dayOfBirth, userName;

    public Customer(int id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }
    private static final long serialVersionUID = 20170711L; 
    
}
