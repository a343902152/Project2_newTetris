package dao;

import entity.Rect;

import java.awt.*;

/**
 * ��Ϸ�����е���ײ��⡢���������
 */
public class GameDao {

    private boolean[][] gamemap;
    public int cancelline = 0;
    public int score = 0;
    public int level = 0;

    public GameDao() {
        // 11X19�ķ��񣬶��ҳ�ʼ��Ϊδռ��
        this.gamemap = new boolean[11][20];
        for (int x = 0; x < 11; x++)
            for (int y = 0; y < 20; y++)
                gamemap[x][y] = false;

    }

    public boolean isUpSide(Rect curRect) {
        for (int i = 0; i < 4; i++) {
            if (curRect.y[i] <= 0||gamemap[(curRect.x[i]) % 11][curRect.y[i]-1])
                return true;
        }
        return false;
    }

    public boolean isleftside(Rect nowrect) {
        for (int i = 0; i < 4; i++) {
            if (gamemap[(nowrect.x[i] - 1 + 11) % 11][nowrect.y[i]])
                return true;
        }
        return false;
    }

    public boolean isrightside(Rect nowrect) {
        for (int i = 0; i < 4; i++) {
            if (gamemap[(nowrect.x[i] + 1) % 11][nowrect.y[i]])
                return true;
        }
        return false;
    }

    public boolean isput(Rect nowrect) {
        boolean isput = false;
        for (int i = 0; i < 4; i++)
            //������ǽ���߷���
            if (nowrect.y[i] >= 19 || gamemap[nowrect.x[i]][nowrect.y[i] + 1]) {
                isput = true;
                break;
            }
        if (isput)
            //���isput���Ž���ͼ
            for (int j = 0; j < 4; j++)
                gamemap[nowrect.x[j]][nowrect.y[j]] = true;

        return isput;
    }

    public boolean ifcanChange(Rect nowrect) {
        for (int i = 0; i < 4; i++) {
            System.out.println(nowrect.x[i] + "," + nowrect.x[(i + 1) % 4]);
            if (Math.abs(nowrect.x[i] - nowrect.x[(i + 1) % 4]) >= 5) {
                return false;
            }
            int change_x = nowrect.y[i] - nowrect.y[0] + nowrect.x[0];
            int change_y = nowrect.x[0] - nowrect.x[i] + nowrect.y[0];

            if (change_x < 0 || change_y < 0 || change_x > 10 || gamemap[change_x][change_y])
                return false;
        }
        return true;
    }

    public boolean gameover() {
        for (int x = 0; x < 11; x++) {
            if (gamemap[x][0] == true)
                return true;
        }
        return false;
    }

    /**
     * �Ƿ�ɹ���ȥһ��
     *
     * @return
     */
    public boolean ispop() {
        boolean iscancel = false;
        int tmpline = 0;                        //�������
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
            //�����͵ȼ�����
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
     * ���Ƶ׶��Ѿ����µķ���
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


}