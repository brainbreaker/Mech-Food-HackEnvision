package brainbreaker.mechfood.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import brainbreaker.mechfood.Adapters.CartRecyclerViewAdapter;
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
 */
public class MenuFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private Menu_Item_Click_Listener mListener;
    private ArrayList<Product> ProductList = new ArrayList<>();
    private SliderLayout mDemoSlider;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MenuFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MenuFragment newInstance(int columnCount) {
        MenuFragment fragment = new MenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        final Firebase MainRef = new Firebase("https://mechc.firebaseio.com");
        Firebase ProductRef = new Firebase("https://mechc.firebaseio.com/Products");
        // Set the adapter
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));
            }
            //HERE WE ARE FETCHING DATA(Product List) FROM FIREBASE AND ADDING TO THE mValues ArrayList
            ProductRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        Product product = productSnapshot.getValue(Product.class);
                        System.out.println(product.getProductName() + " - " + product.getRate() + " - " + product.getImageURL());
                        Product currentProduct = new Product(product.getProductName()
                                , product.getRate()
                                , product.getImageURL()
                        );
                        ProductList.add(currentProduct);
                    }
                    MenuRecyclerViewAdapter adapter = new MenuRecyclerViewAdapter(getActivity()
                            , ProductList
                            , new Menu_Item_Click_Listener() {
                        @Override
                        public void onMenuListItemInteraction(Product item) {
                            System.out.println("I AM A DISCO DANCER");
                        }
                    }, new Cart_Plus_Click_Listener() {
                        @Override
                        public void onCartPlusClick(CartProduct item, Integer position) {
                            Firebase CartRef = MainRef.child("Cart").push();
                            CartRef.setValue(item);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }

        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Veg Thali", "http://images.grabhouse.com/urbancocktail/wp-content/uploads/2015/09/veg-2.jpg");
        url_maps.put("Chhole Bhature", "http://2.bp.blogspot.com/-w4KOeWh0DXk/UA7pfsmVXhI/AAAAAAAARSk/qAOZ30Zy1AM/s640/cb+five+s.jpg");
        url_maps.put("Masala Dosa", "http://www.ndtv.com/cooks/images/mysore-masala-dosa_article.jpg");
        url_maps.put("Chilli Potato", "http://mrgrill.in/wp-content/uploads/2015/07/chilli-potato2.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        return view;
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnMenuListFragmentInteractionListener) {
//            mListener = (OnMenuListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnMenuListFragmentInteractionListener");
//        }
//    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }
    @Override
    public void onPageScrollStateChanged(int state) {}


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
