package fi.questionofday.android.ui.util;

import android.view.animation.Animation;

/**
 * Abstract class to use when not all the animation listener methods are required
 */
public abstract class SimpleAnimationListener implements Animation.AnimationListener {

    int id;
    String text;

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }
}
