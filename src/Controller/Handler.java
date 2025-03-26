package Controller;

import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
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
}
