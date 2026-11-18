package com.jerry.assistant;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private LinearLayout navHome, navChat, navCalendar, navTasks;

    // We track which nav tab index is active (0=Home,1=Chat,2=Calendar,3=Tasks)
    private int activeTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab nav tabs
        navHome     = findViewById(R.id.navHome);
        navChat     = findViewById(R.id.navChat);
        navCalendar = findViewById(R.id.navCalendar);
        navTasks    = findViewById(R.id.navTasks);

        // Load Home by default
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), 0);
        }

        navHome.setOnClickListener(v -> loadFragment(new HomeFragment(), 0));
        navChat.setOnClickListener(v -> loadFragment(new ChatFragment(), 1));
        navCalendar.setOnClickListener(v -> loadFragment(new CalendarFragment(), 2));
        navTasks.setOnClickListener(v -> loadFragment(new TasksFragment(), 3));
    }

    private void loadFragment(Fragment fragment, int tabIndex) {
        if (activeTab == tabIndex) return; // already on this tab, do nothing
        activeTab = tabIndex;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();

        updateNavColors(tabIndex);
    }

    /**
     * Tints the active nav icon + label coral; inactive ones go grey.
     * Each nav LinearLayout has 1 ImageView and 1 TextView as direct children.
     */
    private void updateNavColors(int activeIndex) {
        LinearLayout[] tabs = {navHome, navChat, navCalendar, navTasks};
        int coral  = ContextCompat.getColor(this, R.color.coral);
        int grey   = ContextCompat.getColor(this, R.color.text_secondary);

        for (int i = 0; i < tabs.length; i++) {
            LinearLayout tab = tabs[i];
            int color = (i == activeIndex) ? coral : grey;

            // Child 0 = ImageView (icon), Child 1 = TextView (label)
            if (tab.getChildCount() >= 2) {
                View iconView  = tab.getChildAt(0);
                View labelView = tab.getChildAt(1);

                if (iconView instanceof ImageView) {
                    ((ImageView) iconView).setColorFilter(color);
                }
                if (labelView instanceof TextView) {
                    ((TextView) labelView).setTextColor(color);
                }
            }
        }
    }
}