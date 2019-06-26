package com.sheremetev.receptomania;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sheremetev.receptomania.Model.Categories;

import java.util.Objects;

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

    Spinner categoryList;

    final int DIALOG_GALERY_or_CAMERA = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

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
        String[] categoryNames = new String[Categories.categories.length];
        for (int i = 0; i < categoryNames.length; i++) {
            categoryNames[i] = Categories.categories[i].getName();
        }
        ArrayAdapter<String> categotyListAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,categoryNames);
        categotyListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList.setAdapter(categotyListAdapter);

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
                        Glide.with(getContext()).load(imageUri).into(imageRecipe);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 101:
                if(resultCode == RESULT_OK){

                    Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    imageRecipe.setImageBitmap(photo);
                }
                break;
        }}

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                TextStep.setHint("шаг " + ((FirstIdStep + 1) / 1));
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
                ingredientsNamesArray = new String[FirstIdNum];
                ingredientsCountArray = new String[FirstIdNum];
                ingredientsMeraArray = new String[FirstIdNum];
                stepsArray = new String[FirstIdStep];

                EditText firstIngredientName = (EditText) Objects.requireNonNull(getView()).findViewWithTag("0ingedient");
                EditText firstIngredientsCount = (EditText) Objects.requireNonNull(getView()).findViewWithTag("0count");
                Spinner firstIngredientsMera = (Spinner) Objects.requireNonNull(getView()).findViewWithTag("0mera");
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
               /* for(int i = 0; i < ingredientsNamesArray.length; i++){
                    Toast.makeText(getContext(),ingredientsNamesArray[i],Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),ingredientsCountArray[i],Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),ingredientsMeraArray[i],Toast.LENGTH_SHORT).show();
                }*/
                for(int i = 0; i < stepsArray.length; i++){
                    Toast.makeText(getContext(),stepsArray[i],Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
