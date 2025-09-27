
package WS;

import java.io.Serializable;


public class ProductY implements Serializable{
    private float price;
    private float taxRate;
    private float discount;
    private float finalPrice;

    public float getPrice() {
        return price;
    }

    public float getTaxRate() {
        return taxRate;
    }

    public float getDiscount() {
        return discount;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice() {
        this.finalPrice = this.price * (1 + this.taxRate / 100) * (1 - this.discount / 100);
    }

    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public ProductY() {
    }
    
    
}
