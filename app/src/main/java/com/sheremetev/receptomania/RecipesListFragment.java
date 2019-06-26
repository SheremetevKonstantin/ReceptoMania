package com.sheremetev.receptomania;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sheremetev.receptomania.Adapter.MyAdapter;
import com.sheremetev.receptomania.Interface.ILoadMore;
import com.sheremetev.receptomania.Model.Item;
import com.sheremetev.receptomania.Model.Img;

import org.json.JSONException;
import org.json.JSONObject;


public class RecipesListFragment extends Fragment {

    String responce;
    int id;
    int number;
    int ListCount = 0;
    MyAdapter adapter;
    List<Item> items = new ArrayList<>();
    List<Img> img = new ArrayList<>();
    String subCatName;
    Object[][] Array = new Object[0][];
    public RecipesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView categoryRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_recipes_list, container, false);

        Bundle bundle = this.getArguments();
        JSONObject postDataParams;
        String postDataParamsString = "";
        if(bundle != null){
            try {
                subCatName = bundle.getString("subCatName");
                Toast.makeText(getContext(),subCatName,Toast.LENGTH_SHORT).show();
                postDataParams = new JSONObject();
                postDataParams.put("subCatName", subCatName);
                postDataParamsString = new PostDataStringer().getPostDataString(postDataParams);

                String url = "https://texturrariaone.ru/sher/Take_name_and_img_for_RecipesList.php";
                SendPostData sendPostData = new SendPostData();
                sendPostData.execute(url, postDataParamsString);

                responce = sendPostData.get();

                if(responce.equals("Рецептов не найдено")){
                    Toast.makeText(getContext(),responce,Toast.LENGTH_SHORT).show();
                }else if(responce.equals("Плохое интернет соединение")){
                    Toast.makeText(getContext(),responce,Toast.LENGTH_SHORT).show();
                }else{
                    JSONObject userJson = null;

                    userJson = new JSONObject(responce);
                    int leng = userJson.length();
                    Array = new Object[leng][3];
                    for(int i = 0; i < leng; i++){
                        JSONObject d = userJson.getJSONObject(String.valueOf(i));
                        Array[i][0] = d.getString("recipes_name");
                        Array[i][1] = d.getString("recipes_image");
                        Array[i][2] = d.getInt("recipes_id");
                    }

                    if(Array.length < 10){
                        for (int i = 0; i < Array.length; i++) {
                            String name = String.valueOf(Array[i][0]);
                            Item newItem = new Item(name);
                            items.add(newItem);

                            String image = String.valueOf(Array[i][1]);
                            Img newImg = new Img(image);
                            img.add(newImg);
                        }
                    }else{
                        for (int i = 0; i < 10; i++) {
                            ListCount = i;
                            String name = String.valueOf(Array[i][0]);
                            Item newItem = new Item(name);
                            items.add(newItem);

                            String image = String.valueOf(Array[i][1]);
                            Img newImg = new Img(image);
                            img.add(newImg);
                        }
                    }

                    final Object[][] finalArray = Array;
                    adapter.setLoadMore(new ILoadMore() {
                        @Override
                        public void onLoadMore() {
                            if (items.size() <= finalArray.length) {
                                items.add(null);
                                adapter.notifyItemInserted(items.size() - 1);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        items.remove(items.size() - 1);
                                        adapter.notifyItemRemoved(items.size());

                                        //random more data
                                        int index = items.size();
                                        int end = items.size();
                                        if((index + 10) < finalArray.length){
                                            end = index + 10;
                                        }
                                        else{
                                            end = finalArray.length;
                                        }
                                        if(ListCount != 0){
                                            for (int i = index; i < end; i++) {
                                                ListCount = i;
                                                String name = String.valueOf(finalArray[i][0]);
                                                Item newItem = new Item(name);
                                                items.add(newItem);

                                                String image = String.valueOf(finalArray[i][1]);
                                                Img newImg = new Img(image);
                                                img.add(newImg);
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                        adapter.setLoaded();
                                    }
                                }, 3000);
                            } else {
                                Toast.makeText(getActivity(), "Больше рецептов нет", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            } catch (JSONException e) {
                Toast.makeText(getContext(),"Ошибка",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getContext(),"Ошибка",Toast.LENGTH_SHORT);
            }
        }


        categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter(categoryRecycler,getActivity(),items,img);
        categoryRecycler.setAdapter(adapter);

        adapter.setListener(new MyAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Fragment fragment = new DetailRecipesFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("RecipeId", Integer.valueOf(String.valueOf(Array[position][2])));
                fragment.setArguments(bundle);

                assert getFragmentManager() != null;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.content_frame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return categoryRecycler;

    }

}
