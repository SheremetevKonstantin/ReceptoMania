package com.sheremetev.receptomania;


import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import android.view.ViewGroup.LayoutParams;

import com.sheremetev.receptomania.Model.NullPage;

public class DetailRecipesFragment extends Fragment{

    String TAG_CATCH = "Сервер недоступен";

    NullPage nullPage;

    int RecipesId;
    JSONObject postDataParams;
    String postDataParamsString = "";
    String responce;

    JSONObject postDataParamsFavorite;
    String postDataParamsStringFavorite = "";
    String responceFavorite;

    JSONObject postDataParamsFavoriteChange;
    String postDataParamsStringFavoriteChange = "";
    String responceFavoriteChange;

    Object[] detailRecipeArray;
    JSONObject detailRecipeJson = null;

    ImageView detailRecipeImg;
    CheckBox detailRecipeFavorite;
    TextView detailRecipeText;
    TableLayout detailRecipeTable;
    TextView detailRecipeCooking;
    TextView detailRecipeAuthor;
    String email;

    String[] detailRecipeIngredientsArray;
    String[] detailRecipeIngredientsCountsArray;
    String[] detailRecipeIngredientsValueArray;


    public DetailRecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_recipes, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            try {
                RecipesId = bundle.getInt("RecipeId");

                postDataParams = new JSONObject();
                postDataParams.put("RecipeId", RecipesId);
                postDataParamsString = new PostDataStringer().getPostDataString(postDataParams);

                String url = "https://texturrariaone.ru/sher/Take_detail_recipe.php";

                SendPostData sendPostData = new SendPostData();

                sendPostData.execute(url,postDataParamsString);

                responce = sendPostData.get();

                postDataParamsFavorite = new JSONObject();
                email = NullPage.email;
                postDataParamsFavorite.put("RecipeId", RecipesId);
                postDataParamsFavorite.put("UserName", email);
                postDataParamsStringFavorite = new PostDataStringer().getPostDataString(postDataParamsFavorite);

                String urlFavorite = "https://texturrariaone.ru/sher/FavoriteGetStatus.php";

                SendPostData sendPostDataFavorite = new SendPostData();
                sendPostDataFavorite.execute(urlFavorite,postDataParamsStringFavorite);

                responceFavorite = sendPostDataFavorite.get();


                if(responce.equals("Подробного описания рецепта не найдено")){
                    Toast.makeText(getContext(),responce,Toast.LENGTH_SHORT).show();
                }else if(responce.equals("Плохое интернет соединение")){
                    Toast.makeText(getContext(),responce,Toast.LENGTH_SHORT).show();
                }else{

                    detailRecipeJson = new JSONObject(responce);
                    int leng = detailRecipeJson.length();
                    detailRecipeArray = new Object[leng];

                    detailRecipeArray[0] = detailRecipeJson.getString("recipes_name");
                    detailRecipeArray[1] = detailRecipeJson.getString("recipes_ingredients");
                    detailRecipeArray[2] = detailRecipeJson.getString("recipes_ingredientsCount");
                    detailRecipeArray[3] = detailRecipeJson.getString("recipes_ingredientsValue");
                    detailRecipeArray[4] = detailRecipeJson.getString("recipes_cooking");
                    detailRecipeArray[5] = detailRecipeJson.getString("recipes_image");
                    detailRecipeArray[6] = detailRecipeJson.getString("recipes_category");
                    detailRecipeArray[7] = detailRecipeJson.getString("recipes_status");
                    detailRecipeArray[8] = detailRecipeJson.getString("recipes_author");

                    detailRecipeImg = view.findViewById(R.id.detailRecipeImg);
                    detailRecipeText = view.findViewById(R.id.detailRecipeText);
                    detailRecipeTable = view.findViewById(R.id.detailRecipeIngredientsTable);
                    detailRecipeCooking = view.findViewById(R.id.detailRecipeCooking);
                    detailRecipeFavorite = view.findViewById(R.id.detailRecipeFavorite);
                    detailRecipeAuthor = view.findViewById(R.id.detailRecipeAuthor);

                    Glide.with(view).load(String.valueOf(detailRecipeArray[5])).into(detailRecipeImg);
                    detailRecipeText.setText(String.valueOf(detailRecipeArray[0]));

                    detailRecipeIngredientsArray = String.valueOf(detailRecipeArray[1]).split(";");
                    detailRecipeIngredientsCountsArray = String.valueOf(detailRecipeArray[2]).split(";");
                    detailRecipeIngredientsValueArray = String.valueOf(detailRecipeArray[3]).split(";");

                    for(int i = 0; i < detailRecipeIngredientsArray.length; i++){
                        TableRow tableRow = new TableRow(getContext());
                        tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
                        tableRow.setWeightSum(10);

                        for(int j  = 0; j < 3; j++ ){
                            TextView textView = new TextView(getContext());
                            TableRow.LayoutParams lp;
                            switch (j){
                                case 0:
                                    lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.7f);
                                    lp.weight = 7;
                                    textView.setText(detailRecipeIngredientsArray[i]);
                                    textView.setLayoutParams(lp);
                                    textView.setTextSize(20);
                                    break;
                                case 1:
                                    lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.2f);
                                    lp.weight = 2;
                                    textView.setText(detailRecipeIngredientsCountsArray[i]);
                                    textView.setLayoutParams(lp);
                                    textView.setTextSize(20);
                                    break;
                                case 2:
                                    lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.1f);
                                    lp.weight = 1;
                                    textView.setText(detailRecipeIngredientsValueArray[i]);
                                    textView.setLayoutParams(lp);
                                    textView.setTextSize(20);
                                    break;
                            }
                            tableRow.addView(textView,j);
                        }
                        detailRecipeTable.addView(tableRow,i);

                    }

                    detailRecipeCooking.setText(Html.fromHtml(String.valueOf(detailRecipeArray[4]).replace(";","<br /><br />")));
                    detailRecipeAuthor.setText(String.valueOf(detailRecipeArray[8]));


                    if(responceFavorite.equals("success")){
                        detailRecipeFavorite.setChecked(true);
                    }else{
                        detailRecipeFavorite.setChecked(false);
                    }

                    detailRecipeFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            try {
                                if (isChecked) {
                                    if (email != null && !email.equals("Гость")) {
                                        postDataParamsFavoriteChange = new JSONObject();
                                        postDataParamsFavoriteChange.put("RecipeId", RecipesId);
                                        postDataParamsFavoriteChange.put("UserName", email);
                                        postDataParamsStringFavoriteChange = new PostDataStringer().getPostDataString(postDataParamsFavoriteChange);

                                        String urlFavorite = "https://texturrariaone.ru/sher/FavoriteSet.php";

                                        SendPostData sendPostDataFavoriteChange = new SendPostData();
                                        sendPostDataFavoriteChange.execute(urlFavorite,postDataParamsStringFavoriteChange);

                                        responceFavoriteChange = sendPostDataFavoriteChange.get();

                                        if(!responceFavoriteChange.equals("success")){
                                            detailRecipeFavorite.setChecked(!isChecked);
                                        }
                                    }
                                }else{
                                        postDataParamsFavoriteChange = new JSONObject();
                                        postDataParamsFavoriteChange.put("RecipeId", RecipesId);
                                        postDataParamsFavoriteChange.put("UserName", email);
                                        postDataParamsStringFavoriteChange = new PostDataStringer().getPostDataString(postDataParamsFavoriteChange);

                                        String urlFavorite = "https://texturrariaone.ru/sher/FavoriteUnset.php";

                                        SendPostData sendPostDataFavoriteChange = new SendPostData();
                                        sendPostDataFavoriteChange.execute(urlFavorite,postDataParamsStringFavoriteChange);

                                        responceFavoriteChange = sendPostDataFavoriteChange.get();

                                        if(!responceFavoriteChange.equals("success")) {
                                            detailRecipeFavorite.setChecked(!isChecked);
                                        }
                                }
                            }catch (Exception ignored){}
                        }
                    });

                }

            } catch (Exception e) {
                Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }
        }

        return view;


    }


}