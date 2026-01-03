package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fallDerWoerter;

import javax.swing.Timer;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielController;

import java.awt.event.ActionEvent;

public class FallDerWoerterController extends SpielController<FallDerWoerterModel, FallDerWoerterController, FallDerWoerterView> {

    private Timer gameLoop;

    public FallDerWoerterController(){
        model = new FallDerWoerterModel();
        view = new FallDerWoerterView(this);
        
        // Game Loop (approx 60 FPS)
        gameLoop = new Timer(16, (ActionEvent e) -> {
            model.update();
            view.repaintGame();
        });
    }

    @Override
    public void spielStarten() {
        model.spielStarten(spielListener);
        gameLoop.start();
        view.requestFocus(); // Important for KeyListener
    }

    @Override
    public void spielBeenden() {
        gameLoop.stop();
        model.stopGame();
        super.spielBeenden();
    }
    
    public void moveLeft() {
        model.moveLeft();
    }
    
    public void moveRight() {
        model.moveRight();
    }

	public void setMovingLeft(boolean moving) {
        model.setMovingLeft(moving);
    }
    
    public void setMovingRight(boolean moving) {
        model.setMovingRight(moving);
    }
    
    public FallDerWoerterModel getModel() {
        return model;
    }
}