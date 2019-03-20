package com.sheremetev.receptomania;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
                case "porridge":
                    subCategoryNames = new String[Subcategories.porridge.length];
                    for (int i = 0; i < subCategoryNames.length; i++) {
                        subCategoryNames[i] = Subcategories.porridge[i].getName();
                    }
                    subCategoryImages = new int[Subcategories.porridge.length];
                    for (int i = 0; i < subCategoryImages.length; i++) {
                        subCategoryImages[i] = Subcategories.porridge[i].getImageResourceId();
                    }
            }
        }

        final CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(subCategoryNames, subCategoryImages);
        subCategoryRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        subCategoryRecycler.setLayoutManager(layoutManager);



        return subCategoryRecycler;


    }

}
