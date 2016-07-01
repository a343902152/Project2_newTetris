package entity;

/**
 * Created by hp on 2016/7/1.
 */
public class GamedaoMessage {
    private boolean[][] gamemap;
    public int cancelline = 0;
    public int score = 0;
    public int level = 0;

    private RectMessage curRect;
    private RectMessage nextRect;

    public GamedaoMessage(boolean[][] gamemap, int cancelline, int score, int level, RectMessage curRect, RectMessage nextRect) {
        this.gamemap = gamemap;
        this.cancelline = cancelline;
        this.score = score;
        this.level = level;
        this.curRect = curRect;
        this.nextRect = nextRect;
    }

    public boolean[][] getGamemap() {
        return gamemap;
    }

    public void setGamemap(boolean[][] gamemap) {
        this.gamemap = gamemap;
    }

    public int getCancelline() {
        return cancelline;
    }

    public void setCancelline(int cancelline) {
        this.cancelline = cancelline;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public RectMessage getCurRect() {
        return curRect;
    }

    public void setCurRect(RectMessage curRect) {
        this.curRect = curRect;
    }

    public RectMessage getNextRect() {
        return nextRect;
    }

    public void setNextRect(RectMessage nextRect) {
        this.nextRect = nextRect;
    }
}
