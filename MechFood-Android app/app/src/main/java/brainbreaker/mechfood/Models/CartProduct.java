package brainbreaker.mechfood.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by brainbreaker on 19/2/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartProduct {
    private String productName;
    private Integer rate;
    private String imageURL;
    private boolean status;
    private Integer quantity;

    public CartProduct(){}
    public CartProduct(String ProductName, Integer Rate, String ImageURL, Integer Quantity, Boolean status) {
        this.productName = ProductName;
        this.rate = Rate;
        this.imageURL = ImageURL;
        this.quantity = Quantity;
        this.status = status;
    }

    public String getProductName(){
        return productName;
    }
    public Integer getRate(){
        return rate;
    }
    public String getImageURL(){
        return imageURL;
    }
    public boolean getStatus(){
        return status;
    }
    public Integer getQuantity(){
        return quantity;
    }

}
