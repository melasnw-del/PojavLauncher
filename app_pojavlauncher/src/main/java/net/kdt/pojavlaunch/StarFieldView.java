package net.kdt.pojavlaunch;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import java.util.Random;

public class StarFieldView extends View {
    private static final int STAR_COUNT = 90;
    private final float[] x = new float[STAR_COUNT];
    private final float[] y = new float[STAR_COUNT];
    private final float[] size = new float[STAR_COUNT];
    private final float[] speed = new float[STAR_COUNT];
    private final float[] phase = new float[STAR_COUNT];
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Random rnd = new Random();
    private Paint bgPaint;
    private boolean ready = false;

    public StarFieldView(Context c) { super(c); }
    public StarFieldView(Context c, AttributeSet a) { super(c, a); }
    public StarFieldView(Context c, AttributeSet a, int s) { super(c, a, s); }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        bgPaint = new Paint();
        bgPaint.setShader(new LinearGradient(0, 0, 0, h,
                new int[]{0xFF1A1033, 0xFF2D1B5E, 0xFF5B3FA0},
                null, Shader.TileMode.CLAMP));
        for (int i = 0; i < STAR_COUNT; i++) {
            x[i] = rnd.nextFloat() * w;
            y[i] = rnd.nextFloat() * h;
            size[i] = 1f + rnd.nextFloat() * 2.5f;
            speed[i] = 0.15f + rnd.nextFloat() * 0.6f;
            phase[i] = rnd.nextFloat() * 6.28f;
        }
        ready = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!ready) return;
        canvas.drawRect(0, 0, getWidth(), getHeight(), bgPaint);
        long t = System.currentTimeMillis();
        for (int i = 0; i < STAR_COUNT; i++) {
            x[i] -= speed[i];
            if (x[i] < 0) { x[i] = getWidth(); y[i] = rnd.nextFloat() * getHeight(); }
            float tw = 0.5f + 0.5f * (float) Math.sin(t / 500.0 + phase[i]);
            paint.setColor(Color.WHITE);
            paint.setAlpha((int) (80 + 175 * tw));
            canvas.drawCircle(x[i], y[i], size[i], paint);
        }
        postInvalidateOnAnimation();
    }
}
