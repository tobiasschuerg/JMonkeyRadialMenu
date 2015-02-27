/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import java.util.List;

/**
 *
 * @author Tobias
 */
public class PieMenuController extends AbstractAppState implements ScreenController {

    private Application app;
    private Nifty nifty;
    private Screen screen;
    private List<Element> layers;
    private List<Element> panels;
    private List<Element> controls;
    private Element closest;
    private int initialWith = 0;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
        this.app = app;
        
        
    }

    @Override
    public void update(float tpf) {
        if (controls != null) {

            Vector2f cursor  = new Vector2f(nifty.getNiftyMouse().getX(), nifty.getNiftyMouse().getY());

            float dist = cursor.distance(getElementCenter(closest));
            for (Element c : controls) {

                Vector2f controlPosition = getElementCenter(c);
                float dist2 = cursor.distance(controlPosition);
                if (dist2 < dist) {
                    if (c.getId().equalsIgnoreCase(closest.getId())
                            || c.getId().equalsIgnoreCase(Main.MAIN_BUTTON)) {
                    } else {
                        dist = dist2;
                        setClosestControl(c);
                    }

                }
            }

        } else {
            System.out.println("no controls");
        }
        //TODO: implement behavior during runtime
    }

    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = nifty.getScreen("piemenu");
        this.layers = screen.getLayerElements();
        this.panels = layers.get(0).getElements();

        this.controls = panels.get(0).getElements();
        setClosestControl(controls.get(0));
        initialWith = 50;
                
        
    }

    public void onStartScreen() {
        
        
    }

    public void onEndScreen() {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void checkMouse() {
        if (nifty != null) {

            int x = nifty.getNiftyMouse().getX();
            int y = nifty.getNiftyMouse().getY();
            //if (cursor != null) {
            System.out.println("Nifty Cursor at: " + x + ", " + y);

            int i = 0;



            //}
        }
        // app.stop();
    }

    private void setClosestControl(Element c) {
        if (closest != null) {
            closest.getNiftyControl(Button.class).setTextColor(Color.BLACK);
        }
        closest = c;
        closest.getNiftyControl(Button.class).setTextColor(Color.WHITE);
    }

    private Vector2f getElementCenter(Element e) {
        // System.out.println(c.getId() + " at: " + c.getX() + ", " + c.getY());
        float x = (float) (e.getX() + 0.5 * e.getWidth());
        float y = (float) (e.getY() + 0.5 * e.getHeight());
        Vector2f controlPosition = new Vector2f(x, y);
        return controlPosition;
    }
}
