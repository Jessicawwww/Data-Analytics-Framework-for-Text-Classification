package edu.cmu.cs.cs214.rec09.framework.gui;

public class DataPluginGUI {
    private final String name;

    public DataPluginGUI(String name){
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
