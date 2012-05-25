package sfumobile;

import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class MyScreen extends MainScreen
{
    /**
     * Creates a new MyScreen object
     */
	ButtonField bf;
    public MyScreen()
    {        
        // Set the displayed title of the screen       
        setTitle("Hello World");
        bf = new ButtonField("next");
        add( bf );
    }
}
