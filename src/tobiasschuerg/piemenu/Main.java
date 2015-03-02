package tobiasschuerg.piemenu;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication implements PieMenu{

    public static final String MAIN_BUTTON = "main_button";
    public static final String PIE_MENU_ID = "pie.menu";
    public static final String SCREEN_ID = "pie.menu.screen";
    public static final String TRANSLATE = "Translate";

    public static void main(String[] args) {
        Main app = new Main();
        app.setPauseOnLostFocus(false);
        app.start();

    }
    private Nifty nifty;
    private Geometry selected;
    private boolean menuEnabled = true;
    private float menuReload = 1001;

    @Override
    public void simpleInitApp() {       
        initCrossHairs(); // a "+" in the middle of the screen to help aiming
        initGeometry();

        // Set up the nifty specific stuff
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        // Create a new NiftyGUI object
        nifty = niftyDisplay.getNifty();
        // nifty.fromXml("Interface/newNiftyGui.xml", "start");
        // attach the Nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        // Set up the pie menu
        PieMenuBuilder menu = new PieMenuBuilder(nifty, PIE_MENU_ID, this);
        menu.addButton(TRANSLATE);
        menu.addButton("Rotate");
        menu.addButton("Scale");
        
        nifty.addScreen(SCREEN_ID, menu.build());
        getStateManager().attach(menu.getController());

        nifty.registerMouseCursor("hand", "Interface/mouse-cursor-hand.png", 5, 4);
        nifty.setDebugOptionPanelColors(true);

    }
    /**
     * Defining the "Shoot" action: Determine what was hit and how to respond.
     */
    private ActionListener actionListener = new ActionListener() {
        
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {

            if (name.equals("Shoot") && !keyPressed) {
                CollisionResults results = new CollisionResults();
                Ray ray = new Ray(cam.getLocation(), cam.getDirection());
                rootNode.collideWith(ray, results);

                if (results.size() > 0) {
                    selected = results.getClosestCollision().getGeometry();
                    String hit = selected.getName();
                    System.out.println(" Selected object " + hit);

                    // disable the fly cam and show the pie menu.
                    flyCam.setDragToRotate(true);
                    nifty.gotoScreen(SCREEN_ID);
                    inputManager.deleteMapping("Shoot");
                    menuEnabled = false;
                }
                
            }
        }
    };

    @Override
    public void simpleUpdate(float tpf) {

        if (menuEnabled && !inputManager.hasMapping("Shoot") && menuReload > 0.5) {                        
            System.out.println("init keys");
             initKeys();
        } else if (menuEnabled && !inputManager.hasMapping("Shoot") ) {
            menuReload = menuReload + tpf;
            System.out.println("waiting " + menuReload);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * A centred plus sign to help the player aim.
     */
    protected void initCrossHairs() {
        setDisplayStatView(false);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    /**
     * Declaring the "Shoot" action and its triggers.
     */
    private void initKeys() {
        inputManager.addMapping("Shoot", // Declare...
                new KeyTrigger(KeyInput.KEY_SPACE), // trigger 1: spacebar, or
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));         // trigger 2: left-button click
        inputManager.addListener(actionListener, "Shoot"); // ... and add.
        menuReload = 0;
    }

    private void initGeometry() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }

    public void onButtonClicked(String name) {
        if (name.equalsIgnoreCase(TRANSLATE)) {
            selected.getMaterial().setColor("Color", ColorRGBA.randomColor());
        }
        nifty.exit();
        flyCam.setDragToRotate(false);
        menuEnabled = true;
    }
}
