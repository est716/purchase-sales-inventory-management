package Controller;

import java.awt.event.KeyListener;

import javax.swing.event.DocumentListener;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import Model.Data;

import View.ViewPanel;

// can test used KeyAdapter, MouseAdapter
public abstract class Handler implements KeyListener, ActionListener, DocumentListener{

    public Handler() {

    }

    public abstract void bindingToModel(Data dataModel);

    public abstract void bindingToView(ViewPanel viewPanel);

    protected boolean isDigitOrEnter(KeyEvent e) {
        int code = e.getKeyCode();
        return (KeyEvent.VK_0 <= code && code <= KeyEvent.VK_9) || 
                (KeyEvent.VK_NUMPAD0 <= code && code <= KeyEvent.VK_NUMPAD9) ||
                code == KeyEvent.VK_ENTER || 
                code == KeyEvent.VK_BACK_SPACE;
    }
}
