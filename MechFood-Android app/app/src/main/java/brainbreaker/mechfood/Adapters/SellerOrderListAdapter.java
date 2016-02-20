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
import android.widget.Toast;

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
public class SellerOrderListAdapter extends RecyclerView.Adapter<SellerOrderListAdapter.ViewHolder> {

    private final ArrayList<CartProduct> CartProductList;
    public SellerOrderListAdapter(ArrayList<CartProduct> CartProductList) {
        this.CartProductList = CartProductList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seller_order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mCartItem = CartProductList.get(position);
        Integer Total = 0;
        for (int i = 0; i<CartProductList.size(); i++){
            holder.Dishes.setText(CartProductList.get(i).getProductName()+" ,");
            Total = Total + CartProductList.get(i).getQuantity()*CartProductList.get(i).getRate();
        }
        holder.Total.setText("₹"+ Total);

        holder.Orderid.setText("KAxqcL2Gnzy5dcau9vP - Table Number 7");

        holder.prepared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.Status.setText(" Prepared ");

            }
        });

    }

    @Override
    public int getItemCount() {
        return CartProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        
        public  TextView Dishes;
        public TextView Total;
        public TextView Orderid;
        public TextView Status;
        public Button prepared;
        public CartProduct mCartItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            Dishes = (TextView) view.findViewById(R.id.Dishes);
            Status = (TextView) view.findViewById(R.id.Status);
            Total = (TextView) view.findViewById(R.id.Amount);
            Orderid = (TextView) view.findViewById(R.id.OrderId);
            prepared = (Button) view.findViewById(R.id.prepared);
        }
        
    }
}
