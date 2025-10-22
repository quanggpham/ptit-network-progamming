
package TCP;

import java.io.Serializable;

public class Laptop implements Serializable{
    public int id, quantity;
    public String code, name;

    private static final long serialVersionUID = 20150711L;
    
    public Laptop(int id, int quantity, String code, String name) {
        this.id = id;
        this.quantity = quantity;
        this.code = code;
        this.name = name;
    }
    
    
}
