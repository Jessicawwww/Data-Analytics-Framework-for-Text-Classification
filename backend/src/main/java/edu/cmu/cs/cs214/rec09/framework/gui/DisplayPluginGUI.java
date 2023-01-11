package edu.cmu.cs.cs214.rec09.framework.gui;

public class DisplayPluginGUI {
    private final String name;

    public DisplayPluginGUI(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{ \"name\": \"" + this.name + "\" }";
    }
}
