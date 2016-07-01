package entity;

/**
 * Created by hp on 2016/7/1.
 */
public class RectMessage {
    private int[] x = new int[4];
    private int[] y = new int[4];
    private int color;

    public RectMessage(int[] x, int[] y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
