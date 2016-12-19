package fi.questionofday.android.ui.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

/**
 * Utility class to help animating views
 *
 * Created by jduran on 11/07/16.
 */
public class AnimationUtils {

    /**
     * Private constructor to avoid object instances
     */
    private AnimationUtils() {
    }

    /**
     * Utility method to help making a push pull animation when a View is clicked
     *
     * @param viewToAnimate {@link View} that will animate
     */
    public static void clickAnimation(View viewToAnimate) {
        clickAnimation(viewToAnimate, null, 0.9f);
    }

    /**
     * Utility method to help making a push pull animation when a View is clicked
     *
     * @param viewToAnimate        {@link View} that will animate
     * @param actionAfterAnimation {@link Runnable} to execute when the animation is over
     *                             (optional)
     */
    public static void clickAnimation(View viewToAnimate, @Nullable Runnable actionAfterAnimation) {
        clickAnimation(viewToAnimate, actionAfterAnimation, 0.9f);
    }

    /**
     * Utility method to help making a push pull animation when a View is clicked
     *
     * @param viewToAnimate        {@link View} that will animate
     * @param actionAfterAnimation {@link Runnable} to execute when the animation is over
     *                             (optional)
     * @param toScale               The percentage to scale the view to when animating
     */
    public static void clickAnimation(View viewToAnimate, @Nullable Runnable
            actionAfterAnimation, float toScale) {

        final Animation pushAnimation = new ScaleAnimation(1f, toScale, 1f, toScale,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        pushAnimation.setDuration(60);
        pushAnimation.setAnimationListener(new SimpleAnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                final Animation pullAnimation = new ScaleAnimation(toScale, 1f, toScale, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                pullAnimation.setDuration(60);
                if (actionAfterAnimation != null) {
                    pullAnimation.setAnimationListener(new SimpleAnimationListener() {
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            actionAfterAnimation.run();
                        }
                    });
                }
                viewToAnimate.startAnimation(pullAnimation);
            }
        });
        viewToAnimate.startAnimation(pushAnimation);
    }

    /**
     * Translates Y coordinates a little bit
     *
     * @param viewToAnimate  The view to animate
     * @param distanceToMove Y distance to move in pixels
     * @param startDelay     time to wait before starting the animation
     */
    public static void translateYAnimation(View viewToAnimate,
                                           float distanceToMove,
                                           int startDelay) {

        translateYAnimation(viewToAnimate, distanceToMove, startDelay, null);
    }

    /**
     * Translates Y coordinates a little bit
     *
     * @param viewToAnimate  The view to animate
     * @param distanceToMove Y distance to move in pixels
     * @param startDelay     time to wait before starting the animation
     * @param endAction      The action to perform after the animation ends
     */
    @SuppressWarnings("all")
    public static void translateYAnimation(View viewToAnimate,
                                           float distanceToMove,
                                           int startDelay,
                                           @Nullable final Runnable endAction) {

        final float initialPosition = distanceToMove > 0 ? distanceToMove : 0;
        final float finalMovePosition = distanceToMove > 0 ? 0 : distanceToMove * -1;

        final TimeInterpolator interpolator;
        if (distanceToMove > 0) {
            // casting is forced by lint .-> http://stackoverflow.com/a/35633793
            interpolator = (TimeInterpolator) new DecelerateInterpolator(2f);
        } else {
            // casting is forced by lint .-> http://stackoverflow.com/a/35633793
            interpolator = (TimeInterpolator) new AccelerateInterpolator(2f);
        }

        viewToAnimate.setTranslationY(initialPosition);
        viewToAnimate.setVisibility(View.VISIBLE);
        viewToAnimate.animate()
                .translationY(finalMovePosition)
                .setDuration(viewToAnimate.getContext()
                        .getResources().getInteger(android.R.integer.config_mediumAnimTime))
                .setInterpolator(interpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(final Animator animation) {

                        if (endAction != null) {
                            endAction.run();
                        }
                    }
                }).setStartDelay(startDelay)
                .start();
    }

}
