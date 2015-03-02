/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tobiasschuerg.piemenu;

import com.jme3.app.state.AppState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ControlBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tobias Schürg
 */
public class PieMenuBuilder {

    public static final String PANEL_ID = "Panel_ID";
    public static final String LAYER_ID = "Layer_ID";
    public static final String MAIN_BUTTON_ID = "pie.menu.main.button";    
    
    private final ScreenBuilder mScreenBuilder;
    private final Nifty mNifty;
    private String mTextMainButton;
    private final PieMenuController mController;
    private List<ControlBuilder> buttons;

    public PieMenuBuilder(Nifty nifty, String id, PieMenuInterface pieMenu) {
        this.mTextMainButton = "Menu";
        mNifty = nifty;
        mController = new PieMenuController(pieMenu);
        mScreenBuilder = new ScreenBuilder(id, mController);
        buttons = new ArrayList<ControlBuilder>();
    }

    public Screen build() {
        mScreenBuilder.layer(new LayerBuilder(LAYER_ID) {
            
            {
                childLayoutVertical(); // layer properties, add more...
                childLayoutCenter();
                visibleToMouse(true);

                panel(new PanelBuilder(PANEL_ID) {
                    {
                        childLayoutCenter();
                        
                        height("80%");
                        width("60%");                        
                        interactOnClick("onMouseClick()");

                        // Main Button
                        control(new ButtonBuilder(MAIN_BUTTON_ID, mTextMainButton) {
                            {
                                alignCenter();
                                valignCenter();
                                height("40");
                                width("60");
                            }
                        });

                        panel(new PanelBuilder("OPTIONS") {
                            {
                                childLayoutAbsoluteInside();
                                for (ControlBuilder cb : buttons) {
                                    control(cb);
                                }
                            }
                        });


                    }
                }); // panel
            }
        });
        return mScreenBuilder.build(mNifty);
    }

    public AppState getController() {
        return mController;
    }

    public void setMainButtonText(String text) {
        mTextMainButton = text;
    }

    int buttonCount = 0;
    
    public void addButton(final String name) {
        buttonCount++;
        buttons.add(new ButtonBuilder("button_" + name, name) {
            {
                height("15%");
                width("20%");
                
                backgroundColor(Color.randomColor());
                               
                switch (buttonCount) {
                    case 1: x("0%");y("20%"); break;
                    case 2: x("40%");y("0%"); break;
                    case 3: x("90%");y("20%"); break;
                }   
                
                interactOnClick("onButtonClicked(" + name + ")");
            }
        });
    }
}
