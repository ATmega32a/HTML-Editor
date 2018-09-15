package main;

import listeners.FrameListener;
import listeners.TabbedPaneChangeListener;
import listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class View extends JFrame implements ActionListener {
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);
    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();

    public View(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Новый" : controller.createNewDocument();
                break;
            case "Открыть" : controller.openDocument();
                break;
            case  "Сохранить" : controller.saveDocument();
                break;
            case "Сохранить как..." : controller.saveDocumentAs();
                break;
            case "Выход" : controller.exit();
                break;
            case "О программе" : showAbout();
                break;
        }

    }
    public void init(){
        initGui();
        FrameListener frameListener = new FrameListener(this);
        addWindowListener(frameListener);
        setVisible(true);

    }
    public void exit(){
        controller.exit();
    }
    public void initMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        MenuHelper.initFileMenu(this,menuBar);
        MenuHelper.initEditMenu(this,menuBar);
        MenuHelper.initStyleMenu(this,menuBar);
        MenuHelper.initAlignMenu(this,menuBar);
        MenuHelper.initColorMenu(this,menuBar);
        MenuHelper.initFontMenu(this,menuBar);
        MenuHelper.initHelpMenu(this,menuBar);
        getContentPane().add(menuBar,BorderLayout.NORTH);
    }
    public void initEditor(){

        htmlTextPane.setContentType("text/html");

        JScrollPane scrollPaneHtmlTextPane = new JScrollPane(htmlTextPane);

        tabbedPane.addTab("HTML",scrollPaneHtmlTextPane);

        JScrollPane scrollPlaneTextPane = new JScrollPane(plainTextPane);

        tabbedPane.addTab("Текст",scrollPlaneTextPane);
        Dimension dimension = new Dimension();
        dimension.setSize(800,600);
        tabbedPane.setPreferredSize(dimension);
        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));
        getContentPane().add(tabbedPane,BorderLayout.CENTER);
    }
    public void initGui(){
        initMenuBar();
        initEditor();
        pack();
    }

    public void selectedTabChanged(){
        if(tabbedPane.getSelectedIndex()==0)
            controller.setPlainText(plainTextPane.getText());
        else if(tabbedPane.getSelectedIndex()==1)
            plainTextPane.setText(controller.getPlainText());
        resetUndo();
    }
    public void undo(){
        try{
            undoManager.undo();
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
    }
    public void redo(){
        try{
            undoManager.redo();
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
    }
    public boolean canUndo(){
        return undoManager.canUndo();
    }
    public boolean canRedo(){
        return undoManager.canRedo();
    }
    public void resetUndo(){
        undoManager.discardAllEdits();
    }
    public void selectHtmlTab(){
        tabbedPane.setSelectedIndex(0);
        resetUndo();

    }
    public boolean isHtmlTabSelected(){
        return tabbedPane.getSelectedIndex() == 0;
    }
    public UndoListener getUndoListener() {
        return undoListener;
    }
    public void update(){
        htmlTextPane.setDocument(controller.getDocument());
    }
    public void showAbout(){
        JOptionPane.showMessageDialog(this,
                "HTML Editor\nDeveloper: Aleksandr Volozhanin",
                "About program",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
