package Controller;

import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import java.util.Vector;

import Model.Data;

import View.ViewPanel;

// can test used KeyAdapter, MouseAdapter
public abstract class Handler implements KeyListener, ActionListener, MouseListener {

    public Handler() {

    }

    public abstract void bindingToModel(Data dataModel);

    public abstract void bindingToView(ViewPanel viewPanel);

    public abstract Vector<Vector<String>> getData();

    public abstract Vector<String> getColumnName();

    protected boolean isDigitOrEnter(KeyEvent e) {
        int code = e.getKeyCode();
        return (KeyEvent.VK_0 <= code && code <= KeyEvent.VK_9) || 
                (KeyEvent.VK_NUMPAD0 <= code && code <= KeyEvent.VK_NUMPAD9) ||
                code == KeyEvent.VK_ENTER || 
                code == KeyEvent.VK_BACK_SPACE;
    }
}
