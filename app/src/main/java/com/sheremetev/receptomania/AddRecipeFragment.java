package com.sheremetev.receptomania;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sheremetev.receptomania.Model.Categories;
import com.sheremetev.receptomania.Model.NullPage;
import com.sheremetev.receptomania.Model.Subcategories;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;


import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;


public class AddRecipeFragment extends Fragment implements View.OnClickListener{

    ImageView imageRecipe;
    LinearLayout llMain;
    LinearLayout llMain2;
    LinearLayout linearLayout;
    LinearLayout SteplinearLayout;
    int FirstIdNum = 1;
    int FirstIdStep = 1;

    Button CreateBtn;
    Button DeleteBtn;
    Button CreateBtnStep;
    Button DeleteBtnStep;
    Button AddRecipeBTN;

    String[] ingredientsNamesArray;
    String[] ingredientsCountArray;
    String[] ingredientsMeraArray;
    String[] stepsArray;

    EditText RecName;

    Spinner categoryList;
    Spinner subCategoryList;

    Bitmap photo;

    JSONObject postDataParams;
    String postDataParamsString = "";
    String responce;

    String ingredientsNamesString = "";
    String ingredientsCountString = "";
    String ingredientsMeraString = "";
    String stepsString = "";
    String recipeName = "";
    String recipeCatName = "";
    String recipeSubCatName = "";

    boolean isNameNull = false;
    boolean isCountNull = false;
    boolean isMeraNull = false;
    boolean isStepNull = false;

    ProgressBar progressBar;

    final int DIALOG_GALERY_or_CAMERA = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        RecName = (EditText) view.findViewById(R.id.addRecipeName);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        CreateBtn = (Button) view.findViewById(R.id.btnAddIngredient);
        CreateBtn.setOnClickListener(this);
        DeleteBtn = (Button) view.findViewById(R.id.btnDelIngredient);
        DeleteBtn.setOnClickListener(this);

        CreateBtnStep = (Button) view.findViewById(R.id.btnAddStep);
        CreateBtnStep.setOnClickListener(this);
        DeleteBtnStep = (Button) view.findViewById(R.id.btnDelStep);
        DeleteBtnStep.setOnClickListener(this);

        AddRecipeBTN = (Button) view.findViewById(R.id.btnAddRecipe);
        AddRecipeBTN.setOnClickListener(this);

        llMain = (LinearLayout) view.findViewById(R.id.llMain);
        llMain2 = (LinearLayout) view.findViewById(R.id.llMain2);

        categoryList = (Spinner) view.findViewById(R.id.categoryList);
        final String[] categoryNames = new String[Categories.categories.length];
        for (int i = 0; i < categoryNames.length; i++) {
            categoryNames[i] = Categories.categories[i].getName();
        }
        final String[] categoryCatNames = new String[Categories.categories.length];
        for (int i = 0; i < categoryCatNames.length; i++) {
            categoryCatNames[i] = Categories.categories[i].getCatName();
        }
        ArrayAdapter<String> categotyListAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,categoryNames);
        categotyListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList.setAdapter(categotyListAdapter);



        subCategoryList = (Spinner) view.findViewById(R.id.subCategoryList);
        categoryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int catIndex = getIndex(categoryList.getSelectedItem().toString(),categoryNames);
                SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
                String[] subCategoryNames = subCategoryFragment.getArrayNames(categoryCatNames[catIndex]);
                ArrayAdapter<String> subCategoryAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,subCategoryNames);
                subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subCategoryList.setAdapter(subCategoryAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageRecipe = view.findViewById(R.id.imageRecipe);

        imageRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(getContext())

                    .setItems(R.array.add_recipe_photo_array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 0:
                                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, 101);
                                    break;
                                case 1:
                                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                    photoPickerIntent.setType("image/*");
                                    startActivityForResult(photoPickerIntent, 1);
                            }
                        }
                    }).create().show();
            }
        });

        return view;

    }
    //Обрабатываем результат выбора в галерее:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                        Glide.with(getContext()).load(imageUri).into(imageRecipe);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 101:
                if(resultCode == RESULT_OK){
                    photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    imageRecipe.setImageBitmap(photo);
                }
                break;
        }}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddIngredient:
                LayoutInflater inflater = getLayoutInflater();

                View layer = inflater.inflate(R.layout.ingridients_temporary_layout,null);

                linearLayout = layer.findViewById(R.id.layer1);

                EditText IngredientName = layer.findViewById(R.id.name0);
                IngredientName.setTag(FirstIdNum + "ingedient");

                EditText IngredientCount = layer.findViewById(R.id.count0);
                IngredientCount.setTag(FirstIdNum + "count");

                Spinner IngredientMera = layer.findViewById(R.id.mera0);
                IngredientMera.setTag(FirstIdNum + "mera");

                FirstIdNum+= 1;

                llMain.addView(layer);
                break;
            case R.id.btnDelIngredient:
                if(FirstIdNum - 1 != 0) {
                    FirstIdNum -= 1;
                    llMain.removeViewAt(llMain.getChildCount() - 1);
                }
                break;
            case R.id.btnAddStep:
                LayoutInflater inflaterStep = getLayoutInflater();
                View layerStep = inflaterStep.inflate(R.layout.steps_layout,null);
                SteplinearLayout = layerStep.findViewById(R.id.layerStep);
                EditText TextStep = layerStep.findViewById(R.id.step0);
                TextStep.setHint("шаг " + ((FirstIdStep + 1)));
                TextStep.setTag(FirstIdStep + "step");

                FirstIdStep+= 1;

                llMain2.addView(layerStep);
                break;
            case R.id.btnDelStep:
                if(FirstIdStep - 1 != 0) {
                    FirstIdStep -= 1;
                    llMain2.removeViewAt(llMain2.getChildCount() - 1);
                }
                break;
            case R.id.btnAddRecipe:

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        });
                        ingredientsNamesArray = new String[FirstIdNum];
                        ingredientsCountArray = new String[FirstIdNum];
                        ingredientsMeraArray = new String[FirstIdNum];
                        stepsArray = new String[FirstIdStep];

                        EditText firstIngredientName = (EditText) (getView()).findViewWithTag("0ingedient");
                        EditText firstIngredientsCount = (EditText) (getView()).findViewWithTag("0count");
                        Spinner firstIngredientsMera = (Spinner) (getView()).findViewWithTag("0mera");
                        EditText firstStep = (EditText) getView().findViewWithTag("0step");

                        ingredientsNamesArray[0] = firstIngredientName.getText().toString();
                        ingredientsCountArray[0] = firstIngredientsCount.getText().toString();
                        ingredientsMeraArray[0] = firstIngredientsMera.getSelectedItem().toString();
                        stepsArray[0] = firstStep.getText().toString();


                        for(int i = 1; i < FirstIdNum; i++){
                            EditText ingrName = llMain.findViewWithTag(i + "ingedient");
                            EditText ingrCount = llMain.findViewWithTag(i + "count");
                            Spinner ingrMera = llMain.findViewWithTag(i + "mera");

                            ingredientsNamesArray[i] = ingrName.getText().toString();
                            ingredientsCountArray[i] = ingrCount.getText().toString();
                            ingredientsMeraArray[i] = ingrMera.getSelectedItem().toString();
                        }
                        for(int i = 1; i < FirstIdStep; i++){
                            EditText stepText = llMain2.findViewWithTag(i + "step");
                            stepsArray[i] = stepText.getText().toString();
                        }

                        try {
                            while (photo.getWidth() > 2000) {
                                photo =  Bitmap.createScaledBitmap(photo,(photo.getWidth() / 2),(photo.getHeight() / 2),true);
                            }
                            String Base64img = toBase64(photo);

                            //Создание объекта(массива) JSON
                            JSONArray ja = new JSONArray();
                            for(int i = 0; i < ingredientsNamesArray.length; i++){
                                if(i == 0){
                                    ingredientsNamesString += ingredientsNamesArray[i];
                                    ingredientsCountString += ingredientsCountArray[i];
                                    ingredientsMeraString += ingredientsMeraArray[i];
                                }else{
                                    ingredientsNamesString += ";" + ingredientsNamesArray[i];
                                    ingredientsCountString += ";" + ingredientsCountArray[i];
                                    ingredientsMeraString += ";" + ingredientsMeraArray[i];
                                }
                            }
                            for(int i = 0; i < FirstIdStep; i++){
                                if(i == 0){
                                    stepsString += stepsArray[i];
                                }else{
                                    stepsString += ";" + stepsArray[i];
                                }
                            }


                            recipeCatName = categoryList.getSelectedItem().toString();
                            recipeName = RecName.getText().toString();
                            int indexSubCat = subCategoryList.getSelectedItemPosition();
                            switch (recipeCatName){
                                case "Горячие блюда":
                                    recipeSubCatName = Subcategories.hot[indexSubCat].getSubCatName();
                                    break;
                                case "Супы":
                                    recipeSubCatName = Subcategories.soup[indexSubCat].getSubCatName();
                                    break;
                                case "Салаты":
                                    recipeSubCatName = Subcategories.salat[indexSubCat].getSubCatName();
                                    break;
                                case "Напитки":
                                    recipeSubCatName = Subcategories.drinks[indexSubCat].getSubCatName();
                                    break;
                                case "Закуски":
                                    recipeSubCatName = Subcategories.snacks[indexSubCat].getSubCatName();
                                    break;
                                case "Выпечка":
                                    recipeSubCatName = Subcategories.bakery[indexSubCat].getSubCatName();
                                    break;
                                case "Десерты":
                                    recipeSubCatName = Subcategories.desserts[indexSubCat].getSubCatName();
                                    break;
                                case "Соусы":
                                    recipeSubCatName = Subcategories.souses[indexSubCat].getSubCatName();
                                    break;
                                case "Каши":
                                    recipeSubCatName = Subcategories.porridge[indexSubCat].getSubCatName();
                                    break;
                                case "Студенческая еда":

                                    break;
                                case "Готовим в мультиварке":

                                    break;
                                case "Бутерброды":

                                    break;
                            }


                            if(Arrays.asList(ingredientsNamesArray).contains("")){
                                isNameNull = true;
                            }
                            if(Arrays.asList(ingredientsCountArray).contains("")){
                                isCountNull = true;
                            }
                            if(Arrays.asList(ingredientsMeraArray).contains("")){
                                isMeraNull = true;
                            }
                            if(Arrays.asList(stepsArray).contains("")){
                                isStepNull = true;
                            }

                            if(recipeName.equals("") || isNameNull || isCountNull || isMeraNull || isStepNull){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(),"Пожалуйста заполните все поля",Toast.LENGTH_SHORT).show();
                                        ingredientsNamesString = "";
                                        ingredientsCountString = "";
                                        ingredientsMeraString = "";
                                        stepsString = "";
                                    }
                                });

                                isNameNull = false;
                                isCountNull = false;
                                isMeraNull = false;
                                isStepNull = false;
                            }else {


                                postDataParams = new JSONObject();
                                postDataParams.put("base64", Base64img);
                                postDataParams.put("ingrNamesArray", ingredientsNamesString);
                                postDataParams.put("ingrCountArray", ingredientsCountString);
                                postDataParams.put("ingrMeraArray", ingredientsMeraString);
                                postDataParams.put("stepsArray", stepsString);
                                postDataParams.put("recipeName", recipeName);
                                postDataParams.put("recipeCatName", recipeCatName);
                                postDataParams.put("recipeSubCatName", recipeSubCatName);
                                postDataParams.put("recipeAuthor", NullPage.email);
                                postDataParamsString = new PostDataStringer().getPostDataString(postDataParams);


                                String url = "https://texturrariaone.ru/sher/Post.php";

                                SendPostData sendPostData = new SendPostData();

                                sendPostData.execute(url, postDataParamsString);

                                responce = sendPostData.get();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), responce, Toast.LENGTH_SHORT).show();

                                        Fragment fragment = new CategoryFragment();
                                        assert getFragmentManager() != null;
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.add(R.id.content_frame, fragment);
                                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                        ft.addToBackStack(null);
                                        ft.commit();
                                    }
                                });


                            }

                        }
                        catch (Exception ex){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getContext(),"Не было выбрано изображение, либо его размер слишком велик",Toast.LENGTH_SHORT).show();
                                    ingredientsNamesString = "";
                                    ingredientsCountString = "";
                                    ingredientsMeraString = "";
                                    stepsString = "";
                                }
                            });

                        }
                    }
                }).start();


                break;
        }
    }
    //функция получения индекса нужной категории
    public int getIndex(String catName, String[] catArray){
       for(int i = 0; i < catArray.length; i++){
           if(catArray[i].equals(catName)){
               return i;
           }
       }
        return 0;
    }

    public String toBase64(Bitmap bitmap) {

        // Записываем изображение в поток байтов.
        // При этом изображение можно сжать и / или перекодировать в другой формат.
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, byteArrayOutputStream);

        // Получаем изображение из потока в виде байтов
        byte[] bytes = byteArrayOutputStream.toByteArray();

        // Кодируем байты в строку Base64 и возвращаем
        return Base64.encodeToString(bytes, Base64.NO_PADDING);
    }

}
