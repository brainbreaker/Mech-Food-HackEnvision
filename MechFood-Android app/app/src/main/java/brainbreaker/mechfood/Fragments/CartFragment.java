package brainbreaker.mechfood.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import brainbreaker.mechfood.Adapters.CartRecyclerViewAdapter;
import brainbreaker.mechfood.Listeners.CartDetailsListener;
import brainbreaker.mechfood.Listeners.Cart_Plus_Click_Listener;
import brainbreaker.mechfood.Listeners.Cart_Remove_Click_Listener;
import brainbreaker.mechfood.Models.CartProduct;
import brainbreaker.mechfood.Models.Product;
import brainbreaker.mechfood.R;
import java.util.ArrayList;
/**
 * A fragment representing a list of Items.
 * <p/>
// * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class CartFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ArrayList<CartProduct> cartproductList = new ArrayList<>();
    CartRecyclerViewAdapter adapter;
    CartDetailsListener mCartCallback;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CartFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CartFragment newInstance(int columnCount) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);

        final Firebase CartRef = new Firebase("https://mechc.firebaseio.com/Cart");

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            CartRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cartproductList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    CartProduct currentProduct = snapshot.getValue(CartProduct.class);
                    cartproductList.add(currentProduct);
                }
                     mCartCallback.onCartUpdateListener(cartproductList);
                     adapter = new CartRecyclerViewAdapter(cartproductList
                            , new Cart_Plus_Click_Listener() {
                        @Override
                        public void onCartPlusClick(final CartProduct item, final Integer quantity) {
                            CartRef.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    CartProduct currentProduct = dataSnapshot.getValue(CartProduct.class);
                                    if (currentProduct.getProductName().equals(item.getProductName())){
                                        if (quantity!=0) {
                                            CartRef.child(dataSnapshot.getKey()).child("quantity").setValue(quantity);
                                        }
                                        else{
                                            CartRef.child(dataSnapshot.getKey()).removeValue();
                                            Toast.makeText(getActivity(), "Item removed from the cart.", Toast.LENGTH_SHORT).show();
                                        }
                                        mCartCallback.onCartUpdateListener(cartproductList);
                                    }
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {
                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                    }
                            ,new Cart_Remove_Click_Listener() {
                        @Override
                        public void onCartRemoveClick(final CartProduct item, final Integer position) {
//
                            CartRef.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    CartProduct cartProduct = dataSnapshot.getValue(CartProduct.class);
                                    if (item.getProductName().equals(cartProduct.getProductName())){

                                    if (CartRecyclerViewAdapter.flag==1) {
                                        CartRef.child(dataSnapshot.getKey()).removeValue();
                                        Toast.makeText(getActivity(), "Item removed from the cart.", Toast.LENGTH_SHORT).show();
                                        CartRecyclerViewAdapter.flag=0;
                                    }
                                    }
                                    mCartCallback.onCartUpdateListener(cartproductList);
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                            mCartCallback.onCartRemoveListener(cartproductList);
                        }
                     });
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCartCallback = (CartDetailsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CartDetailsListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
