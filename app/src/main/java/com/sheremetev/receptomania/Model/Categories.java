package com.sheremetev.receptomania.Model;

import com.sheremetev.receptomania.R;

public class Categories {
    private String name;
    private int imageResourceId;
    private String catName;

    public static final Categories[] categories = {
            new Categories("Горячие блюда", R.drawable.category_hot,"hot"),
            new Categories("Супы",R.drawable.category_soup,"soup"),
            new Categories("Салаты",R.drawable.category_salat,"salat"),
            new Categories("Напитки",R.drawable.category_drinks,"drinks"),
            new Categories("Закуски",R.drawable.category_snacks,"snacks"),
            new Categories("Выпечка",R.drawable.category_bakery,"bakery"),
            new Categories("Десерты",R.drawable.category_desserts,"desserts"),
            new Categories("Соусы",R.drawable.category_souses,"souses"),
            new Categories("Каши",R.drawable.category_porridge,"porridge"),
            new Categories("Студенческая еда",R.drawable.category_students,"students"),
            new Categories("Готовим в мультиварке",R.drawable.category_multicooker,"multicooker"),
            new Categories("Бутерброды",R.drawable.category_sandwich,"sandwich")
    };
    private Categories(String name, int imageResourceId,String catName) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.catName = catName;
    }
    public String getName() {
        return name;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }
    public String getCatName(){return catName;}
}
