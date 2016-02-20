package brainbreaker.mechfood.Listeners;

import java.util.ArrayList;

import brainbreaker.mechfood.Models.CartProduct;

/**
 * Created by brainbreaker on 20/2/16.
 */
public interface CartDetailsListener {
    public void onCartUpdateListener(ArrayList<CartProduct> cartProductList);
    public void onCartRemoveListener(ArrayList<CartProduct> cartProductList);
}
