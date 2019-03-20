package com.sheremetev.receptomania;

public class Subcategories {
    private String name;
    private int imageResourceId;
    private String subCatName;

    public String getName() {
        return name;
    }
    public int getImageResourceId() {
        return imageResourceId;
    }
    public String getCatName(){return subCatName;}

    private Subcategories(String name, int imageResourceId,String subCatName) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.subCatName = subCatName;
    }

    public static final Subcategories[] porridge = {
            new Subcategories("Каши",R.drawable.porridge_other,"porridge_other"),
            new Subcategories("Каши на воде",R.drawable.porridge_water,"porridge_water"),
            new Subcategories("Каши на молоке",R.drawable.porridge_milk,"porridge_milk"),
            new Subcategories("Сладкие каши",R.drawable.porridge_sweet,"porridge_sweet")
    };

}
