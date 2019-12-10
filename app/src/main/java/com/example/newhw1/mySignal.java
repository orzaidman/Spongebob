package com.example.newhw1;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class mySignal {

    public static void vibrate(Context context, int duration) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(duration);
        }

    }

    public static void animatePop(final TextView lbl) {

        lbl.setScaleY(0);
        lbl.setScaleX(0);

        lbl.animate()
                .scaleY(1)
                .scaleX(1)
                .setDuration(900)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                })
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    public static void animatePop(final ImageView img) {

        img.setScaleY(0);
        img.setScaleX(0);

        img.animate()
                .scaleY(1)
                .scaleX(1)
                .setDuration(900)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                })
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    public static void animatePop(final Button button) {

        button.setScaleY(0);
        button.setScaleX(0);

        button.animate()
                .scaleY(1)
                .scaleX(1)
                .setDuration(900)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                })
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    public static void animateHeart(final ImageView heart) {

        heart.setScaleY(1);
        heart.setScaleX(1);

        heart.animate()
                .scaleY(0)
                .scaleX(0)
                .setDuration(900)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        heart.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                })
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    public static void animatePlayer(final ImageView player) {

        player.setScaleY(1);
        player.setScaleX(1);
        player.setRotation(0);

        player.animate()
                .setDuration(400)
                .rotation(360)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                })
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    public static void animatePlus(final TextView plus) {

        plus.setScaleY(1);
        plus.setScaleX(1);

        plus.animate()
                .scaleY(0)
                .scaleX(0)
                .setDuration(2000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                })
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }
}
