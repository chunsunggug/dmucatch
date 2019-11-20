package com.example.dmu.dmucatch.utility;

import android.support.design.widget.NavigationView;
import android.view.Menu;

public class LayoutUtil {
    public void navVisible(NavigationView nView , int itemNum){
        Menu nav_Menu = nView.getMenu();
        nav_Menu.findItem(itemNum).setVisible(true);
    }

    public void navInvisible(NavigationView nView , int itemNum) {
        Menu nav_Menu = nView.getMenu();
        nav_Menu.findItem(itemNum).setVisible(false);
    }
}
