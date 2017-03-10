package com.hcat.teachme;

import android.view.View;

/**
 * Created by Admin on 3/10/2017.
 */

public interface ClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
