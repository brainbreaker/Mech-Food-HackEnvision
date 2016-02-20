package brainbreaker.mechfood.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import brainbreaker.mechfood.Listeners.CartDetailsListener;
import brainbreaker.mechfood.Listeners.Cart_Plus_Click_Listener;
import brainbreaker.mechfood.Listeners.Menu_Item_Click_Listener;
import brainbreaker.mechfood.Models.CartProduct;
import brainbreaker.mechfood.Models.Product;
import brainbreaker.mechfood.R;

import java.util.ArrayList;


/**
 * {@link RecyclerView.Adapter} that can display a {@link Product} and makes a call to the
 * specified {@link Menu_Item_Click_Listener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Product> mProductList = new ArrayList<>();
    private final Menu_Item_Click_Listener mListener;
    private final Cart_Plus_Click_Listener mCartListener;
    private Context context;
    public MenuRecyclerViewAdapter(Context context,ArrayList<Product> ProductList, Menu_Item_Click_Listener listener, Cart_Plus_Click_Listener mCartListener) {
        mListener = listener;
        this.mCartListener = mCartListener;
        this.context = context;
        mProductList = ProductList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mProductList.get(position);
        holder.ProductNameTextView.setText(mProductList.get(position).getProductName());
        holder.RateTextView.setText(" ₹"+mProductList.get(position).getRate().toString());
        Picasso.with(context)
                .load(mProductList.get(position).getImageURL())
                .placeholder(R.drawable.loading)
                .resize(500,400)
                .error(R.drawable.loading)
                .into(holder.ProductImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onMenuListItemInteraction(holder.mItem);
                }
            }
        });

        holder.CartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mCartListener){
                    CartProduct cartProduct = new CartProduct(holder.mItem.getProductName()
                            ,holder.mItem.getRate(),holder.mItem.getImageURL(),1,true);
                    mCartListener.onCartPlusClick(cartProduct, position);
                    holder.CartButton.setEnabled(false);
                    holder.CartButton.setBackground(context.getResources().getDrawable(R.drawable.tick));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView ProductImageView;
        public final TextView ProductNameTextView;
        public final TextView RateTextView;
        public final ImageButton CartButton;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ProductImageView = (ImageView) view.findViewById(R.id.ProductImage);
            ProductNameTextView = (TextView) view.findViewById(R.id.ProductName);
            RateTextView = (TextView) view.findViewById(R.id.Rate);
            CartButton = (ImageButton) view.findViewById(R.id.AddToCart);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + RateTextView.getText() + "'";
        }
    }
}
