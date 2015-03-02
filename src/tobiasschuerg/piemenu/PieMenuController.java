/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tobiasschuerg.piemenu;

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
 * @author Tobias Schürg
 */
public class PieMenuController extends AbstractAppState implements ScreenController, PieMenu {

    private Nifty nifty;
    private List<Element> layers;
    private List<Element> panels;
    private List<Element> buttons;
    private Element closest;
    private final PieMenu pieMenu;

    PieMenuController(PieMenu pieMenu) {
        this.pieMenu = pieMenu;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);    
    }

    @Override
    public void update(float tpf) {
        if (buttons != null) {

            Vector2f cursor  = new Vector2f(nifty.getNiftyMouse().getX(), nifty.getNiftyMouse().getY());

            float dist = cursor.distance(getElementCenter(closest));
            for (Element c : buttons) {

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
            // System.out.println("no controls");
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
        this.layers = screen.getLayerElements();
        this.panels = layers.get(0).getElements();
        
        List<Element> controls = panels.get(0).getElements();
        this.buttons = controls.get(1).getElements();
        setClosestControl(controls.get(0));  
    }

    public void onStartScreen() {

    }

    public void onEndScreen() {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onMouseClick() { 
        System.out.println("No button touched, closest button is " + closest.getId());
        closest.onClick();               
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

    public void onButtonClicked(String name) {
        System.out.println("Performing button click " + closest.getId());
        pieMenu.onButtonClicked(name);
    }

}
