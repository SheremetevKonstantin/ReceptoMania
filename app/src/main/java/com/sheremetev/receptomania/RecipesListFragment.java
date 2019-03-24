package com.sheremetev.receptomania;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import java.util.List;

import com.sheremetev.receptomania.Adapter.MyAdapter;
import com.sheremetev.receptomania.Interface.ILoadMore;
import com.sheremetev.receptomania.Model.Item;
import com.sheremetev.receptomania.Model.Img;

import org.json.JSONException;
import org.json.JSONObject;


public class RecipesListFragment extends Fragment {

    String res;
    int id;
    int number;
    int ListCount = 0;
    MyAdapter adapter;
    List<Item> items = new ArrayList<>();
    List<Img> img = new ArrayList<>();


    public RecipesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        UpdateJoke upd = new UpdateJoke();
        upd.execute("https://texturrariaone.ru/sher/insert_test.php");

        try {
            res = upd.get();
        }
        catch (Throwable t )
        {
            t.printStackTrace();
        }

        JSONObject userJson = null;
        Object[][] Array = new Object[0][];
        try {
            userJson = new JSONObject(res);
            int leng = userJson.length();
            Array = new Object[leng][3];
            for(int i = 0; i < leng; i++){
                JSONObject d = userJson.getJSONObject(String.valueOf(i));
                Array[i][0] = d.getString("name");
                Array[i][1] = d.getString("img");
            }

        } catch (JSONException e) {
            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
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

        RecyclerView categoryRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_recipes_list, container, false);

        categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter(categoryRecycler,getActivity(),items,img);
        categoryRecycler.setAdapter(adapter);

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

        return categoryRecycler;

    }

}


class UpdateJoke extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... url){
        String s = "";
        try {
            s = doGet(url[0]);
        } catch (Throwable e) {
            e.printStackTrace();
            return e.toString();
        }
        return s;
    }

    public static String doGet(String url)
            throws Exception {




        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0" );
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        bufferedReader.close();

        return response.toString();

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
