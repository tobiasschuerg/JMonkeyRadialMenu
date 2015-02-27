package tobiasschuerg.piemenu;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.tools.Color;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    public static final String MAIN_BUTTON = "main_button";
    public static final String PIE_MENU_ID = "pie.menu";
    public static final String SCREEN_ID = "pie.menu.screen";
    

    public static void main(String[] args) {
        Main app = new Main();
        app.setPauseOnLostFocus(false);
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);

        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        // Create a new NiftyGUI object
        Nifty nifty = niftyDisplay.getNifty();
        // nifty.fromXml("Interface/newNiftyGui.xml", "start");
        // attach the Nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
        // disable the fly cam
        flyCam.setDragToRotate(true);        
        
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");              
        
        PieMenuBuilder menu = new PieMenuBuilder(nifty, PIE_MENU_ID);
        menu.addButton("Translate");
        menu.addButton("Rotate");
        menu.addButton("Scale");

        // <screen>
        nifty.addScreen(SCREEN_ID, menu.build());
        getStateManager().attach(menu.getController());

        // nifty.registerMouseCursor("hand", "Interface/mouse-cursor-hand.png", 5, 4);
        // nifty.setDebugOptionPanelColors(true);
        nifty.gotoScreen(SCREEN_ID); // start the screen
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

}
