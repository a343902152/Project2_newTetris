package Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyController extends KeyAdapter {

    private GameController gameController;

    public KeyController(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (MusicPlayer.isturnOn())
            MusicPlayer.actionPlay();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.gameController.keyUp();
                break;
            case KeyEvent.VK_DOWN:
                this.gameController.keyDown();
                break;
            case KeyEvent.VK_LEFT:
                this.gameController.keyLeft();
                break;
            case KeyEvent.VK_RIGHT:
                this.gameController.keyRight();
                break;
            case KeyEvent.VK_SPACE:
                this.gameController.keyChange();
                break;
            case KeyEvent.VK_CONTROL:
                if (gameController.isRunning()) {
                    this.gameController.keyPause();
                    if (MusicPlayer.isRunning()) {
                        MusicPlayer.bgmStop();
                    }
                } else {
                    this.gameController.keyResume();
                    if (!MusicPlayer.isRunning()) {
                        MusicPlayer.bgmPlay();
                    }
                }
                break;
            default:
                break;
        }
    }

}
