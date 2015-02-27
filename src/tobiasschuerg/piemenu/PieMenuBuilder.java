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

    public static final String MAIN_BUTTON = "pie.menu.main.button";
    private final ScreenBuilder mScreenBuilder;
    private final Nifty mNifty;
    private String mTextMainButton;
    private final PieMenuController mController;
    private List<ControlBuilder> buttons;

    public PieMenuBuilder(Nifty nifty, String id) {
        this.mTextMainButton = "Menu";
        mNifty = nifty;
        mController = new PieMenuController();
        mScreenBuilder = new ScreenBuilder(id, mController);
        buttons = new ArrayList<ControlBuilder>();
    }

    public Screen build() {
        mScreenBuilder.layer(new LayerBuilder("Layer_ID") {
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

                        // Main Button
                        control(new ButtonBuilder(MAIN_BUTTON, mTextMainButton) {
                            {
                                alignCenter();
                                valignCenter();
                                height("15%");
                                width("15%");
                            }
                        });

                        for (ControlBuilder cb : buttons) {
                            control(cb);
                        }

                    }
                });
                // </panel>
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
    
    public void addButton(String name) {
        buttonCount++;
        buttons.add(new ButtonBuilder("button_" + name, name) {
            {
                height("15%");
                width("15%");
                
                backgroundColor(Color.randomColor());
                
                switch (buttonCount) {
                    case 1: alignLeft(); break;
                    case 2: alignCenter(); break;
                    case 3: alignRight(); break;
                }
                
                valign(VAlign.Top);
                //alignCenter(); // works but I need more than three options
                
                // align(Align.valueOf("10px")); // IllegalArgumentException
                
                // x("10px"); // does nothing
                
                // set("x", "10px"); // does nothing                    
            }
        });
    }
}
