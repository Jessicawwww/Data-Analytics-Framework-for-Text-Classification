package edu.cmu.cs.cs214.rec09.framework.core;

import java.util.HashMap;

public interface DisplayPlugin {
     /**
     * Gets the name of the plug-in display mode.
     * @return the display name
     */
    String getDisplayName();

    /**
     * Called (only once) when the plug-in is first registered with the
     * framework, giving the plug-in a chance to perform any initial set-up
     * before the game has begun (if necessary).
     *
     * @param framework The {@link TextAnalysisFramework} instance with which the plug-in
     *                  was registered.
     */
    void onRegister(TextAnalysisFramework framework);

    /**
     * Gets the chart type.
     * @return the dhart type
     */
    String getChartType();

    /**
     * Gets the dataSets.
     * @return the data sets
     */
    String getDataSets();
    /**
     * Get the html file name.
     * @return the html 
     */
    String getHTML();
    /**
     * Visualize processed text info
     *
     * @param results The processed text info, a HashMap that maps a topic to its frequency
     */
    void visualize(HashMap<String, Integer> results);
}
