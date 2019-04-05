package com.sheremetev.receptomania.Model;

import com.sheremetev.receptomania.R;

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
    public String getSubCatName(){return subCatName;}

    private Subcategories(String name, int imageResourceId,String subCatName) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.subCatName = subCatName;
    }

    public static final Subcategories[] bakery = {
            new Subcategories("Выпечка", R.drawable.bakery_others,"bakery_others"),
            new Subcategories("Пироги",R.drawable.bakery_pirog,"bakery_pirog"),
            new Subcategories("Лепешки",R.drawable.bakery_lepeshka,"bakery_lepeshka"),
            new Subcategories("Блины",R.drawable.bakery_blini,"bakery_blini"),
            new Subcategories("Кексы",R.drawable.bakery_muffin,"bakery_muffin"),
            new Subcategories("Булочки",R.drawable.bakery_bulochka,"bakery_bulochka"),
            new Subcategories("Куличи",R.drawable.bakery_kulich,"bakery_kulich"),
            new Subcategories("Тесто",R.drawable.bakery_testo,"bakery_testo"),
            new Subcategories("Сырники",R.drawable.bakery_sirnik,"bakery_sirnik"),
            new Subcategories("Пицца",R.drawable.bakery_pizza,"bakery_pizza"),
            new Subcategories("Слойки",R.drawable.bakery_sloyki,"bakery_sloyki"),
            new Subcategories("Оладьи",R.drawable.bakery_olady,"bakery_olady"),
            new Subcategories("Пончики",R.drawable.bakery_ponchik,"bakery_ponchik")
    };

    public static final Subcategories[] desserts = {
            new Subcategories("Десерты",R.drawable.desserts_others,"desserts_others"),
            new Subcategories("Торты",R.drawable.desserts_others_tort,"desserts_others_tort"),
            new Subcategories("Слоеный торт",R.drawable.desserts_sloy_tort,"desserts_sloy_tort"),
            new Subcategories("Бисквитный торт",R.drawable.desserts_biskvit_tort,"desserts_biskvit_tort"),
            new Subcategories("Творожный торт",R.drawable.desserts_curd_tort,"desserts_curd_tort"),
            new Subcategories("Печенье",R.drawable.desserts_cookies,"desserts_cookies"),
            new Subcategories("Творожное печенье",R.drawable.desserts_curd_cookies,"desserts_curd_cookies"),
            new Subcategories("Печенье с начинкой",R.drawable.desserts_filing_cookies,"desserts_filing_cookies"),
            new Subcategories("Песочное печенье",R.drawable.desserts_sand_cookies,"desserts_sand_cookies"),
            new Subcategories("Пирожные",R.drawable.desserts_piroznie,"desserts_piroznie"),
            new Subcategories("Пудинг",R.drawable.desserts_puding,"desserts_puding"),
            new Subcategories("Муссы",R.drawable.desserts_muss,"desserts_muss"),
            new Subcategories("Вафли",R.drawable.desserts_waffles,"desserts_waffles")
    };

    public static final Subcategories[] drinks = {
            new Subcategories("Напитки",R.drawable.drinks_others,"drinks_others"),
            new Subcategories("Сок",R.drawable.drinks_sok,"drinks_sok"),
            new Subcategories("Лимонад",R.drawable.drinks_limonad,"drinks_limonad"),
            new Subcategories("Коктели",R.drawable.drinks_koktels,"drinks_koktels"),
            new Subcategories("Морс",R.drawable.drinks_mors,"drinks_mors"),
            new Subcategories("Компот",R.drawable.drinks_kompot,"drinks_kompot"),
            new Subcategories("Смузи",R.drawable.drinks_smuzi,"drinks_smuzi"),
            new Subcategories("Чай",R.drawable.drinks_tea,"drinks_tea"),
            new Subcategories("Кофе",R.drawable.drinks_coffie,"drinks_coffie")
    };

    public static final Subcategories[] hot = {
            new Subcategories("Горячие блюда",R.drawable.hot_others,"hot_others"),
            new Subcategories("Горячие блюда из мяса",R.drawable.hot_meat,"hot_meat"),
            new Subcategories("Горячие блюда из курицы",R.drawable.hot_chicken,"hot_chicken"),
            new Subcategories("Горячие блюда из утки",R.drawable.hot_duck,"hot_duck"),
            new Subcategories("Горячие блюда из рыбы",R.drawable.hot_fish,"hot_fish"),
            new Subcategories("Горячие блюда с грибами",R.drawable.hot_mushrooms,"hot_mushrooms"),
            new Subcategories("Гарнир",R.drawable.hot_garnir,"hot_garnir"),
            new Subcategories("Гарнир из круп",R.drawable.hot_garnir_krupi,"hot_garnir_krupi"),
            new Subcategories("На гриле",R.drawable.hot_greel,"hot_greel"),
            new Subcategories("Котлеты, фрикадельки",R.drawable.hot_kotleti,"hot_kotleti"),
            new Subcategories("Пельмени, манты",R.drawable.hot_pelmen,"hot_pelmen"),
            new Subcategories("Плов",R.drawable.hot_plov,"hot_plov"),
            new Subcategories("Рагу",R.drawable.hot_ragu,"hot_ragu"),
            new Subcategories("Шашлык",R.drawable.hot_shashlik,"hot_shashlik"),
            new Subcategories("Запеканка",R.drawable.hot_zapekan,"hot_zapekan")
    };

    public static final Subcategories[] porridge = {
            new Subcategories("Каши",R.drawable.porridge_other,"porridge_other"),
            new Subcategories("Каши на воде",R.drawable.porridge_water,"porridge_water"),
            new Subcategories("Каши на молоке",R.drawable.porridge_milk,"porridge_milk"),
            new Subcategories("Сладкие каши",R.drawable.porridge_sweet,"porridge_sweet")
    };

    public static final Subcategories[] salat = {
            new Subcategories("Салаты",R.drawable.salat_others,"salat_others"),
            new Subcategories("Салаты с мясом",R.drawable.salat_meat,"salat_meat"),
            new Subcategories("Салаты с курицей",R.drawable.salat_chicken,"salat_chicken"),
            new Subcategories("Салаты с рыбой",R.drawable.salat_fish,"salat_fish"),
            new Subcategories("Салаты с сыром",R.drawable.salat_cheese,"salat_cheese"),
            new Subcategories("Салаты с колбасой",R.drawable.salat_kolbasa,"salat_kolbasa"),
            new Subcategories("Салаты с грибами",R.drawable.salat_mashrooms,"salat_mashrooms"),
            new Subcategories("Овощные салаты",R.drawable.salat_vegetables,"salat_vegetables"),
            new Subcategories("Фруктовые салаты",R.drawable.salat_fruits,"salat_fruits")
    };

    public static final Subcategories[] snacks = {
            new Subcategories("Закуски",R.drawable.snacks_other,"snacks_other"),
            new Subcategories("Мясные закуски",R.drawable.snacks_meat,"snacks_meat"),
            new Subcategories("Рыбные закуски",R.drawable.snacks_fish,"snacks_fish"),
            new Subcategories("Овощные закуски",R.drawable.snacks_vegetables,"snacks_vegetables"),
            new Subcategories("Канапе",R.drawable.snacks_kanape,"snacks_kanape"),
            new Subcategories("Паштет",R.drawable.snacks_pashtet,"snacks_pashtet"),
            new Subcategories("Рулеты",R.drawable.snacks_roll,"snacks_roll"),
            new Subcategories("Снэк",R.drawable.snacks_snack,"snacks_snack"),
            new Subcategories("Суши, роллы",R.drawable.snacks_sushi,"snacks_sushi"),
            new Subcategories("Торталетки",R.drawable.snacks_tortalet,"snacks_tortalet"),
            new Subcategories("Заливное",R.drawable.snacks_zalivnoe,"snacks_zalivnoe")
    };

    public static final Subcategories[] soup = {
            new Subcategories("Супы",R.drawable.soup_others,"soup_others"),
            new Subcategories("Борщ",R.drawable.soup_borsch,"soup_borsch"),
            new Subcategories("Гуляш",R.drawable.soup_gulash,"soup_gulash"),
            new Subcategories("Лагман",R.drawable.soup_lagman,"soup_lagman"),
            new Subcategories("Солянка",R.drawable.soup_solyanka,"soup_solyanka"),
            new Subcategories("Рыбный суп",R.drawable.soup_fish,"soup_fish"),
            new Subcategories("Гороховый суп",R.drawable.soup_goroh,"soup_goroh"),
            new Subcategories("Фасолевый суп",R.drawable.soup_fasol,"soup_fasol"),
            new Subcategories("Рисовый суп",R.drawable.soup_ris,"soup_ris"),
            new Subcategories("Суп-лапша",R.drawable.soup_lapsha,"soup_lapsha"),
            new Subcategories("Свекольник",R.drawable.soup_svekoln,"soup_svekoln")
    };

    public static final Subcategories[] souses = {
            new Subcategories("Соусы",R.drawable.souses_others,"souses_others"),
            new Subcategories("Соусы к мясу",R.drawable.souses_meat,"souses_meat"),
            new Subcategories("Соусы к рыбе",R.drawable.souses_fish,"souses_fish"),
            new Subcategories("Соусы к курице",R.drawable.souses_chicken,"souses_chicken"),
            new Subcategories("Слакие соусы",R.drawable.souses_sweet,"souses_sweet"),
            new Subcategories("Домашний майонез",R.drawable.souses_mayonez,"souses_mayonez"),
            new Subcategories("Салатная заправка",R.drawable.souses_salat,"souses_salat")
    };

}