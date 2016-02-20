package brainbreaker.mechfood.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import brainbreaker.mechfood.Listeners.CartDetailsListener;
import brainbreaker.mechfood.Listeners.Cart_Plus_Click_Listener;
import brainbreaker.mechfood.Listeners.Cart_Remove_Click_Listener;
import brainbreaker.mechfood.Models.CartProduct;
import brainbreaker.mechfood.Models.Product;
import brainbreaker.mechfood.R;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Product} and makes a call to the
 * specified {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<CartProduct> CartProductList;
    CartDetailsListener mCartDetailsListener;
    private final Cart_Remove_Click_Listener mCartRemoveListner;
    private final Cart_Plus_Click_Listener mCartPlusListner;
    public static int flag;
    public CartRecyclerViewAdapter(ArrayList<CartProduct> CartProductList,Cart_Plus_Click_Listener mCartPlusListner,Cart_Remove_Click_Listener mCartRemoveListner) {
        this.CartProductList = CartProductList;
        this.mCartPlusListner = mCartPlusListner;
        this.mCartRemoveListner = mCartRemoveListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mCartItem = CartProductList.get(position);
        holder.ProductName.setText(CartProductList.get(position).getProductName());
        holder.Rate.setText("₹"+CartProductList.get(position).getRate().toString()+"/plate");
        holder.Quantity.setText(CartProductList.get(position).getQuantity().toString());
        Integer Total = (CartProductList.get(position).getRate())*(CartProductList.get(position).getQuantity());
        holder.Total.setText("₹"+Total.toString());

        holder.Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mCartPlusListner){
                    Integer UpdatedQuantity = Integer.parseInt(holder.Quantity.getText().toString());
                    mCartPlusListner.onCartPlusClick(CartProductList.get(position), UpdatedQuantity);
                }
            }
        });

        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mCartRemoveListner){
                    flag = 1;
                    mCartRemoveListner.onCartRemoveClick(CartProductList.get(position), position);
                    CartProductList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return CartProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public  TextView ProductName;
        public TextView Rate;
        public EditText Quantity;
        public TextView Total;
        public Button Save;
        public ImageButton Remove;
        public CartProduct mCartItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ProductName = (TextView) view.findViewById(R.id.ProductName);
            Rate = (TextView) view.findViewById(R.id.Rate);
            Quantity = (EditText) view.findViewById(R.id.Quantity);
            Total = (TextView) view.findViewById(R.id.Total);
            Save = (Button) view.findViewById(R.id.save);
            Remove = (ImageButton) view.findViewById(R.id.remove);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + Rate.getText() + "'";
        }
    }
}
