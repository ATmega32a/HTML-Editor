package listeners;

import main.View;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabbedPaneChangeListener implements ChangeListener {
    private View view;
    @Override
    public void stateChanged(ChangeEvent e) {
        view.selectedTabChanged();
    }

    public TabbedPaneChangeListener(View view) {
        this.view = view;
    }
}
