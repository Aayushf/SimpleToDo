package com.simpletodo.aayushf.simpletodo;

import android.support.annotation.Nullable;

/**
 * Created by ayfadia on 4/19/17.
 */

public class Category {
    String category;
    int catcolour;

    public Category(String category, @Nullable int catcolour) {
        this.category = category;
        this.catcolour = catcolour;
    }
}
