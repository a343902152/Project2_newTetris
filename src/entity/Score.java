package entity;

/**
 * Created by hp on 2016/6/28.
 */
public class Score {
    private int socreID;
    private String userName;
    private int socre;

    public Score(int socreID, String userName, int socre) {
        this.socreID = socreID;
        this.userName = userName;
        this.socre = socre;
    }

    public int getSocreID() {
        return socreID;
    }

    public void setSocreID(int socreID) {
        this.socreID = socreID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSocre() {
        return socre;
    }

    public void setSocre(int socre) {
        this.socre = socre;
    }

    
}
