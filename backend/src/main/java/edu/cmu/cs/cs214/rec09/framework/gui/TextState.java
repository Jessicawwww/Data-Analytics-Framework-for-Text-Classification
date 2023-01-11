package edu.cmu.cs.cs214.rec09.framework.gui;

import java.util.Arrays;
import java.util.List;

import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFrameworkImpl;

public final class TextState {
    private final String chartType;
    private final String dataSets;
    private final DataPluginGUI[] dataPlugins;
    private final DisplayPluginGUI[] displayPlugins;

    private TextState(String chartType, String dataSets, DataPluginGUI[] dataPlugins, DisplayPluginGUI[] displayPlugins) {
        this.chartType = chartType;
        this.dataSets = dataSets;
        this.dataPlugins = dataPlugins;
        this.displayPlugins = displayPlugins;
    }

    public static TextState forText(TextAnalysisFrameworkImpl textFram) {
        String chartType = "";
        String dataSets = "";
        if(textFram.getDisplayPlugin() != null) {
            chartType = textFram.getDisplayPlugin().getChartType();
            dataSets = textFram.getDisplayPlugin().getDataSets();
        }
        DataPluginGUI[] dataPlugins = getDataPlugins(textFram);
        DisplayPluginGUI[] displayPlugins = getDisplayPlugins(textFram);
        return new TextState(chartType, dataSets, dataPlugins, displayPlugins);
    }

    private static DataPluginGUI[] getDataPlugins(TextAnalysisFrameworkImpl textFram) {
        List<String> dataPlugins = textFram.getRegisteredDataPluginName();
        DataPluginGUI[] plugins = new DataPluginGUI[dataPlugins.size()];
        for (int i=0; i<dataPlugins.size(); i++){
            plugins[i] = new DataPluginGUI(dataPlugins.get(i));
        }
        return plugins;
    }

    private static DisplayPluginGUI[] getDisplayPlugins(TextAnalysisFrameworkImpl textFram) {
        List<String> displayPlugins = textFram.getRegisteredDisplayPluginName();
        DisplayPluginGUI[] plugins = new DisplayPluginGUI[displayPlugins.size()];
        for (int i=0; i<displayPlugins.size(); i++){
            plugins[i] = new DisplayPluginGUI(displayPlugins.get(i));
        }
        return plugins;
    }

    @Override
    public String toString() {
        return ("{ \"chartType\": \"" + this.chartType + "\"," +
                " \"dataSets\": \"" + this.dataSets + "\"," +
                " \"dataPlugins\": " +  Arrays.toString(dataPlugins) + "," +
                " \"displayPlugins\": " +  Arrays.toString(displayPlugins) + "}").replace("null", "");
    }

}
