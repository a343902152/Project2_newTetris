package dao;

import entity.Rect;

import java.awt.*;
import java.util.Random;

/**
 * 游戏过程中的碰撞检测、分数计算等
 */
public class GameDao {

    private boolean[][] gamemap;
    public int cancelline = 0;
    public int score = 0;
    public int level = 0;

    private Rect curRect;
    private Rect nextRect;

    public GameDao() {
        // 11X19的方格，而且初始化为未占用
        this.gamemap = new boolean[11][20];
        for (int x = 0; x < 11; x++)
            for (int y = 0; y < 20; y++)
                gamemap[x][y] = false;

        curRect=new Rect(1);
        nextRect=new Rect(2);
    }

    /**
     * 上面有墙返回ture
     * @return
     */
    public boolean isUpSide() {
        for (int i = 0; i < 4; i++) {
            if (curRect.y[i] <= 0||gamemap[(curRect.x[i]) % 11][curRect.y[i]-1])
                return true;
        }
        return false;
    }

    /**
     * 左边有墙返回true
     * @return
     */
    public boolean isleftside() {
        for (int i = 0; i < 4; i++) {
            if (gamemap[(curRect.x[i] - 1 + 11) % 11][curRect.y[i]])
                return true;
        }
        return false;
    }

    /**
     * 右边有墙返回ture
     * @return
     */
    public boolean isrightside() {
        for (int i = 0; i < 4; i++) {
            if (gamemap[(curRect.x[i] + 1) % 11][curRect.y[i]])
                return true;
        }
        return false;
    }


    /**
     * 如果成功放置(底部碰到某个障碍物)就返回ture
     * @return
     */
    public boolean isput() {
        boolean isput = false;
        for (int i = 0; i < 4; i++)
            //底下有墙或者方块
            if (curRect.y[i] >= 19 || gamemap[curRect.x[i]][curRect.y[i] + 1]) {
                isput = true;
                break;
            }
        if (isput)
            //如果isput，放进地图_ doPut()
            for (int j = 0; j < 4; j++)
                gamemap[curRect.x[j]][curRect.y[j]] = true;
        return isput;
    }

    /**
     * 判断是否可以change，可以的话返回true
     * @return
     */
    public boolean ifcanChange() {
        for (int i = 0; i < 4; i++) {
            if (Math.abs(curRect.x[i] - curRect.x[(i + 1) % 4]) >= 5) {
                return false;
            }
            int change_x = curRect.y[i] - curRect.y[0] + curRect.x[0];
            int change_y = curRect.x[0] - curRect.x[i] + curRect.y[0];

            if (change_x < 0 || change_y < 0 || change_x > 10 ||change_y>19||gamemap[change_x][change_y])
                return false;
        }
        return true;
    }

    public boolean isGameover() {
        for (int x = 0; x < 11; x++) {
            if (gamemap[x][0] == true)
                return true;
        }
        return false;
    }

    /**
     * 是否成功消去一行
     * @return
     */
    public boolean ispop() {
        boolean iscancel = false;
        int tmpline = 0;                        //用于算分
        for (int y = 19; y >= 0; y--)
            for (int x = 0; x < 11; x++) {
                if (gamemap[x][y] != true)
                    break;
                if (x == 10) {
                    tmpline++;
                    cancelline++;
                    popLine(y);
                    y++;
                    iscancel = true;
                }
            }
        if (tmpline != 0) {
            //分数和等级计算
            score += tmpline * tmpline;
            if (score > (2 + 2 * level * level))
                level++;
        }
        return iscancel;
    }

    public void popLine(int line) {
        for (int y = line; y > 0; y--)
            for (int x = 0; x < 11; x++) {
                gamemap[x][y] = gamemap[x][y - 1];
            }
    }

    /**
     * 绘制底端已经落下的方块
     *
     * @param g
     * @param mode
     */
    public void drawmap(Graphics g, int mode) {
        Rect rect = new Rect(0);
        for (int x = 0; x < 11; x++)
            for (int y = 0; y < 20; y++)
                if (gamemap[x][y] == true) {
                    if (mode == 1) {
                        rect.drawone(g, 506, 12, x, y);
                    } else {
                        rect.drawone(g, 12, 12, x, y);
                    }
                }
    }

    public boolean doChange() {
        if(ifcanChange()){
            curRect.change();
            return true;
        }
        return false;
    }

    public boolean doUp() {
        if (!isUpSide()) {
            curRect.up();
            return true;
        }
        return false;
    }

    public boolean doDown() {
        if(!isput()){
            curRect.down();
            return true;
        }else{
            ispop();
            return false;
        }
    }

    public boolean doLeft() {
        if (!isleftside()) {
            curRect.left();
            return true;
        }
        return false;
    }

    public boolean doRight() {
        if (!isrightside()) {
            curRect.right();
            return true;
        }
        return false;
    }

    public int generateNewRect(){
        Random random = new Random();
        int temp=random.nextInt(7)+1;
        curRect.setColor(0);
        curRect = new Rect(nextRect.color);
        nextRect = new Rect(temp);

        return  temp;
    }

    public void generateNewRectFromNet(int color){
        curRect.setColor(0);
        curRect=new Rect(nextRect.color);
        nextRect=new Rect(color);
    }

    public void drawCurRect(Graphics g,int x,int y){
        curRect.draw(g,x,y);
    }
    public void drawNextRect(Graphics g,int x,int y){
        nextRect.draw(g,x,y);
    }

}