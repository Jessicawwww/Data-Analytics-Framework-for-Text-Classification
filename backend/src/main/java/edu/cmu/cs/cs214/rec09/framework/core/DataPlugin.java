package edu.cmu.cs.cs214.rec09.framework.core;

import java.util.List;

public interface DataPlugin {
    /**
     * Gets the name of the plug-in data source.
     * @return the data name
     */
    String getDataName();

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
     * Process the JSON data object received from API request to a list
     * of strings used for text analysis
     * @param data the data to be processed
     * @return the processed data
     */
    List<String> processData(String data);
}
