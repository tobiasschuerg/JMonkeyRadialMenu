package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    public static final String MAIN_BUTTON = "main_button";

    private Nifty nifty;

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
        
        final PieMenuController pieMenuController = new PieMenuController();
        getStateManager().attach(pieMenuController);
        
        // <screen>
        nifty.addScreen("piemenu", new ScreenBuilder("Hello Nifty Screen") {
            {
                controller(pieMenuController); // Screen properties       
                

                // <layer>
                layer(new LayerBuilder("Layer_ID") {
                    {
                        childLayoutVertical(); // layer properties, add more...
                        childLayoutCenter();
                        visibleToMouse(true);
                        

                        // <panel>
                        panel(new PanelBuilder("Panel_ID") {
                            
                            {
                                childLayoutCenter(); // panel properties, add more...                                  
                                height("60%");
                                width("60%");
                                interactOnClickMouseMove("checkMouse()");

                                // GUI elements
                                control(new ButtonBuilder(MAIN_BUTTON, "Menu") {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("15%");
                                        width("15%");                                                                                
                                    }
                                });
                                
                                control(new ButtonBuilder("option_button1", "Option 1") {
                                    {
                                        alignLeft();
                                        valignTop();
                                        height("15%");
                                        width("15%");
                                        backgroundColor(Color.randomColor());
                                    }
                                });
                                
                                control(new ButtonBuilder("option_button2", "Option 2") {
                                    {
                                        alignCenter();
                                        valignTop();
                                        height("15%");
                                        width("15%");
                                        backgroundColor(Color.randomColor());
                                    }
                                });
                                
                                control(new ButtonBuilder("option_button3", "Option 3") {
                                    {
                                        alignRight();
                                        valignTop();
                                        height("15%");
                                        width("15%");
                                        backgroundColor(Color.randomColor());
                                    }
                                });

                                //.. add more GUI elements here              

                            }
                        });
                        // </panel>
                    }
                });
                // </layer>
            }
        }.build(nifty));
        // </screen>

        // nifty.registerMouseCursor("hand", "Interface/mouse-cursor-hand.png", 5, 4);
        // nifty.setDebugOptionPanelColors(true);
        nifty.gotoScreen("piemenu"); // start the screen
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
