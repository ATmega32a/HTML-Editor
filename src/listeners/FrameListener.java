package listeners;

import main.View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameListener extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        view.exit();
    }

    private View view;

    public FrameListener(View view) {
        this.view = view;
    }
}
