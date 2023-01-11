package edu.cmu.cs.cs214.rec09.framework.core;
import java.util.HashMap;
import java.util.List;

/**
 * The interface by which {@link DataPlugin} and {@link DisplayPlugin} instances can directly interact
 * with the game framework.
 */
public interface TextAnalysisFramework {
    /**
     * Registers a new {@link DataPlugin} with the framework.
     * @param plugin the data plugin to be registered
     */
    void registerDataPlugin(DataPlugin plugin);

    /**
     * Registers a new {@link DisplayPlugin} with the framework.
     * @param plugin the display plugin to be registered
     */
    void registerDisplayPlugin(DisplayPlugin plugin);

    /**
     * Gets {@link currentDataPlugin} with the framework.
     * @return the registered data plugin names
     */
    List<String> getRegisteredDataPluginName();

    /**
     * Gets {@link currentDisplayPlugin} with the framework.
     * @return the registered display plugin names
     */
    List<String> getRegisteredDisplayPluginName();

    /**
     * Gets {@link currentDataPlugin} with the framework.
     * 
     * @return the data plugin
     */
    DataPlugin getDataPlugin();

    /**
     * Gets {@link currentDisplayPlugin} with the framework.
     * 
     * @return the display plugin
     */
    DisplayPlugin getDisplayPlugin();

    /**
     * Sets {@link DataPlugin} with the framework.
     * @param plugin the data plugin to be set
     */
    void setDataPlugin(DataPlugin plugin);

    /**
     * Sets a new {@link DisplayPlugin} with the framework.
     * @param plugin the display plugin to be set
     */
    void setDisplayPlugin(DisplayPlugin plugin);
    /**
     * Gets text information from a {@link DataPlugin}
     * @param data
     * @return the text info
     */
    List<String> getTextInfo(String data);
    /**
     * Use google nlp api to classify article based on its abstract.
     * @param texts article abstract fetched from various API
     * @return topic category and their 
     */
    HashMap<String, Integer> classifyTexts(List<String> texts);
    /**
     * Visualizes data from current {@link DataPlugin} using current {@link DisplayPlugin}
     * 
     * @param data The time period of data requested
     */
    void visualizeData(String data);
}
