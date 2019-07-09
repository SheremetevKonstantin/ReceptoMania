package com.sheremetev.receptomania;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.sheremetev.receptomania.Model.Categories;
import com.sheremetev.receptomania.Model.NullPage;


public class CategoryFragment extends Fragment {

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView categoryRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_category, container, false);



        String[] categoryNames = new String[Categories.categories.length];
        for (int i = 0; i < categoryNames.length; i++) {
            categoryNames[i] = Categories.categories[i].getName();
        }
        int[] categoryImages = new int[Categories.categories.length];
        for (int i = 0; i < categoryImages.length; i++) {
            categoryImages[i] = Categories.categories[i].getImageResourceId();
        }


        final CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(categoryNames, categoryImages);
        categoryRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        categoryRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {

                String catName = Categories.categories[position].getCatName();

                Fragment fragment = new SubCategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("catName", catName);
                fragment.setArguments(bundle);

                assert getFragmentManager() != null;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.content_frame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
                //Toast.makeText(getContext(),catName,Toast.LENGTH_SHORT).show();
            }
        });

        return categoryRecycler;
    }



}
