package com.sheremetev.receptomania;

public class Categories {
    private String name;
    private int imageResourceId;

    public static final Categories[] categories = {
            new Categories("Горячие блюда",R.drawable.hot),
            new Categories("Супы",R.drawable.soup),
            new Categories("Салаты",R.drawable.salat),
            new Categories("Напитки",R.drawable.drinks),
            new Categories("Закуски",R.drawable.snacks),
            new Categories("Выпечка",R.drawable.bakery),
            new Categories("Десерты",R.drawable.desserts),
            new Categories("Соусы",R.drawable.souses),
            new Categories("Каши",R.drawable.porridge),
            new Categories("Студенческая еда",R.drawable.students),
            new Categories("Готовим в мультиварке",R.drawable.multicooker),
            new Categories("Бутерброды",R.drawable.sandwich)
    };
    private Categories(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }
    public String getName() {
        return name;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }
}
