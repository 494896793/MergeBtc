package com.bochat.app.app.view.popup;

import android.animation.Animator;
import android.view.View;
import android.view.animation.Animation;
import android.widget.PopupWindow;

public interface IPopup {
    boolean isShowing();

    void dismiss();

    void showPopupWindow(View anchorView);

    void showPopupWindow(View anchorView, int gravity);

    void showPopupWindow(View anchorView, int gravity, int x, int y);

    void setXOffset(int xOffset);

    int getXOffset();

    void setYOffset(int yOffset);

    int getYOffset();

    void setPopupWindowGravity(int gravity);

    View getContentView();

    PopupWindow getPopupWindow();

    Animation onCreateShowAnimation();

    Animator onCreateShowAnimator();

    Animation onCreateDismissAnimation();

    Animator onCreateDismissAnimator();
}

