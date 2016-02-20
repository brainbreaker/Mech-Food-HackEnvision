package brainbreaker.mechfood;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import brainbreaker.mechfood.Adapters.CartRecyclerViewAdapter;
import brainbreaker.mechfood.Adapters.SellerOrderListAdapter;
import brainbreaker.mechfood.Listeners.Cart_Plus_Click_Listener;
import brainbreaker.mechfood.Listeners.Cart_Remove_Click_Listener;
import brainbreaker.mechfood.Models.CartProduct;

public class SellerActivity extends AppCompatActivity {
    ArrayList<CartProduct> cartproductList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Firebase CartRef = new Firebase("https://mechc.firebaseio.com/Cart");
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        CartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartproductList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    CartProduct currentProduct = snapshot.getValue(CartProduct.class);
                    cartproductList.add(currentProduct);
                }
                SellerOrderListAdapter adapter = new SellerOrderListAdapter(cartproductList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
