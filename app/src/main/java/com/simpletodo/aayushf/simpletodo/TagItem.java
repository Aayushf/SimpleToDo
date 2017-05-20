package com.simpletodo.aayushf.simpletodo;

/**
 * Created by ayfadia on 5/18/17.
 */

public class TagItem {
    String tag;
    int donet, pendingt, donep, pendingp;

    public TagItem(String tag, int donet, int pendingt, int donep, int pendingp) {
        this.tag = tag;
        this.donet = donet;
        this.pendingt = pendingt;
        this.donep = donep;
        this.pendingp = pendingp;
    }
}
