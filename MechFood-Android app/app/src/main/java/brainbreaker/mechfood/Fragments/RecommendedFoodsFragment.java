package brainbreaker.mechfood.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import brainbreaker.mechfood.Adapters.MenuRecyclerViewAdapter;
import brainbreaker.mechfood.Adapters.RecommendedFoodListRecyclerViewAdapter;
import brainbreaker.mechfood.Listeners.Cart_Plus_Click_Listener;
import brainbreaker.mechfood.Listeners.Menu_Item_Click_Listener;
import brainbreaker.mechfood.Models.CartProduct;
import brainbreaker.mechfood.Models.Product;
import brainbreaker.mechfood.R;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link brainbreaker.mechfood.Listeners.Menu_Item_Click_Listener}
 * interface.
 */
public class RecommendedFoodsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private ArrayList<Product> productList = new ArrayList<>();
    private static String CartProductKey;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecommendedFoodsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecommendedFoodsFragment newInstance(int columnCount) {
        RecommendedFoodsFragment fragment = new RecommendedFoodsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommended_list, container, false);
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        final Firebase MainRef = new Firebase("https://mechc.firebaseio.com");
        Firebase ProductRef = new Firebase("https://mechc.firebaseio.com/Products");

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // HERE WE ARE FETCHING DATA(Product List) FROM FIREBASE AND ADDING TO THE mValues ArrayList
            ProductRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    productList.clear();
                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        Product product = productSnapshot.getValue(Product.class);
                        System.out.println(product.getProductName() + " - " + product.getRate() + " - " + product.getImageURL());
                        Product currentProduct = new Product(product.getProductName()
                                , product.getRate()
                                , product.getImageURL()
                        );
                        productList.add(currentProduct);
                    }
                    RecommendedFoodListRecyclerViewAdapter adapter = new RecommendedFoodListRecyclerViewAdapter(getActivity()
                            , productList
                            , new Menu_Item_Click_Listener() {
                               @Override
                               public void onMenuListItemInteraction(Product item) {
                                    System.out.println("I AM A DISCO DANCER");
                               }
                            }
                            , new Cart_Plus_Click_Listener() {
                                @Override
                                public void onCartPlusClick(CartProduct item, Integer position) {
                                    Firebase CartRef = MainRef.child("Cart").push();
                                    CartRef.setValue(item);
                                    Toast.makeText(getActivity(),"Added to cart!",Toast.LENGTH_SHORT).show();
                                }
                            });
                    recyclerView.setAdapter(adapter);
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        return view;
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

}
