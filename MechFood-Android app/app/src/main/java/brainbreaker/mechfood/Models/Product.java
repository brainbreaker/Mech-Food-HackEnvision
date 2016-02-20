package brainbreaker.mechfood.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
        private String productName;
        private Integer rate;
        private String imageURL;

    public Product(){}
    public Product(String productName, Integer rate, String imageURL) {
            this.productName = productName;
            this.rate = rate;
            this.imageURL = imageURL;
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

}
