package com.sheremetev.receptomania;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SubCategoryFragment extends Fragment {

    String[] subCategoryNames;
    int[] subCategoryImages;

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView subCategoryRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_sub_category, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            String catName = bundle.getString("catName");


            assert catName != null;
            switch (catName){
                case "bakery":
                    subCategoryNames = getArrayNames("bakery");
                    subCategoryImages = getArrayImages("bakery");
                    break;
                case "desserts":
                    subCategoryNames = getArrayNames("desserts");
                    subCategoryImages = getArrayImages("desserts");
                    break;
                case "drinks":
                    subCategoryNames = getArrayNames("drinks");
                    subCategoryImages = getArrayImages("drinks");
                    break;
                case "hot":
                    subCategoryNames = getArrayNames("hot");
                    subCategoryImages = getArrayImages("hot");
                    break;
                case "porridge":
                    subCategoryNames = getArrayNames("porridge");
                    subCategoryImages = getArrayImages("porridge");
                    break;
                case "salat":
                    subCategoryNames = getArrayNames("salat");
                    subCategoryImages = getArrayImages("salat");
                    break;
                case "snacks":
                    subCategoryNames = getArrayNames("snacks");
                    subCategoryImages = getArrayImages("snacks");
                    break;
                case "soup":
                    subCategoryNames = getArrayNames("soup");
                    subCategoryImages = getArrayImages("soup");
                    break;
                case "souses":
                    subCategoryNames = getArrayNames("souses");
                    subCategoryImages = getArrayImages("souses");
                    break;
            }
        }

        final CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(subCategoryNames, subCategoryImages);
        subCategoryRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        subCategoryRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {

                Fragment fragment = new RecipesListFragment();

                assert getFragmentManager() != null;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.content_frame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        return subCategoryRecycler;

    }

    public String[] getArrayNames(String catName){
        String[] Array;
        switch(catName){
            case "bakery":
                Array = new String[Subcategories.bakery.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.bakery[i].getName();
                }
                break;
            case "desserts":
                Array = new String[Subcategories.desserts.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.desserts[i].getName();
                }
                break;
            case "drinks":
                Array = new String[Subcategories.drinks.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.drinks[i].getName();
                }
                break;
            case "hot":
                Array = new String[Subcategories.hot.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.hot[i].getName();
                }
                break;
            case "porridge":
                Array = new String[Subcategories.porridge.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.porridge[i].getName();
                }
                break;
            case "salat":
                Array = new String[Subcategories.salat.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.salat[i].getName();
                }
                break;
            case "snacks":
                Array = new String[Subcategories.snacks.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.snacks[i].getName();
                }
                break;
            case "soup":
                Array = new String[Subcategories.soup.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.soup[i].getName();
                }
                break;
            case "souses":
                Array = new String[Subcategories.souses.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.souses[i].getName();
                }
                break;
            default:
                Array = new String[0];
        }
        return Array;
    }

    public int[] getArrayImages(String catName){
        int[] Array;
        switch (catName){
            case "bakery":
                Array = new int[Subcategories.bakery.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.bakery[i].getImageResourceId();
                }
                break;
            case "desserts":
                Array = new int[Subcategories.desserts.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.desserts[i].getImageResourceId();
                }
                break;
            case "drinks":
                Array = new int[Subcategories.drinks.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.drinks[i].getImageResourceId();
                }
                break;
            case "hot":
                Array = new int[Subcategories.hot.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.hot[i].getImageResourceId();
                }
                break;
            case "porridge":
                Array = new int[Subcategories.porridge.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.porridge[i].getImageResourceId();
                }
                break;
            case "salat":
                Array = new int[Subcategories.salat.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.salat[i].getImageResourceId();
                }
                break;
            case "snacks":
                Array = new int[Subcategories.snacks.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.snacks[i].getImageResourceId();
                }
                break;
            case "soup":
                Array = new int[Subcategories.soup.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.soup[i].getImageResourceId();
                }
                break;
            case "souses":
                Array = new int[Subcategories.souses.length];
                for (int i = 0; i < Array.length; i++) {
                    Array[i] = Subcategories.souses[i].getImageResourceId();
                }
                break;
            default:
                Array = new int[0];
        }
        return Array;
    }

}
