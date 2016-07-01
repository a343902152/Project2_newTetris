package Controller;


import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.io.FileInputStream;
import java.io.IOException;

public class MusicPlayer {

    static volatile boolean running =false;
    static volatile boolean turnOn =true;
    private static String bgmUrl="/Music/bgm.wav";
    private static String actionUrl="/Music/action.wav";
    

    private static ContinuousAudioDataStream bgmAudio;
    private static AudioStream actionAudio;

    public static void bgmPlay(){
        if(!turnOn)
    		return;

        if(bgmAudio ==null){
            try {
                FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + bgmUrl);
                bgmAudio= new ContinuousAudioDataStream(new AudioStream(fileInputStream).getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AudioPlayer.player.start(bgmAudio);
        running=true;
    }

    public static void bgmStop(){
    	try{
            AudioPlayer.player.stop(bgmAudio);
            running =false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void actionPlay(){
        if(!turnOn) {
            return;
        }
//        if(actionAudio ==null){
            try {
                FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + actionUrl);
                actionAudio= new AudioStream(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
        AudioPlayer.player.start(actionAudio);
    }
    /**
     * 返回是否正在运行
     * @return
     */
    public static boolean isRunning(){
        return running;
    }
    /**
     * 返回是否打开了音乐开关
     * 没打开表示静音状态
     * @return
     */
    public static boolean isturnOn(){
    	return turnOn;
    }
    /**
     * 设置是否静音
     * @param turn
     */
    public static void setturnOn(Boolean turn){
    	turnOn=turn;
    }
    
}

