package com.sheremetev.receptomania;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;


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


        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(categoryNames, categoryImages);
        categoryRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        categoryRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {

                Toast.makeText(getContext(),"dsd",Toast.LENGTH_SHORT).show();
            }
        });

       // return inflater.inflate(R.layout.fragment_category,container,false);

        return categoryRecycler;
    }

}
