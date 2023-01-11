package edu.cmu.cs.cs214.rec09.framework.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.google.cloud.language.v1.ClassificationModelOptions;
import com.google.cloud.language.v1.ClassificationModelOptions.V2Model;
import com.google.cloud.language.v1.ClassificationModelOptions.V2Model.ContentCategoriesVersion;
import com.google.cloud.language.v1.ClassifyTextRequest;
import com.google.cloud.language.v1.ClassifyTextResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;

import com.google.cloud.language.v1.LanguageServiceClient;

/**
 * The framework core implementation.
 */
public class TextAnalysisFrameworkImpl implements TextAnalysisFramework {
    private final String name = "A visualization framework";
    private DataPlugin currentDataPlugin;
    private DisplayPlugin currentDisplayPlugin;
    private List<DataPlugin>  registeredDataPlugins;
    private List<DisplayPlugin>  registeredDisplayPlugins;
    
    public TextAnalysisFrameworkImpl() {
        registeredDataPlugins = new ArrayList<DataPlugin>();
        registeredDisplayPlugins = new ArrayList<DisplayPlugin>();
    }

    /**
     * Registers a new {@link DataPlugin} with the framework
     * @param plugin the data plugin to be registered
     */
    public void registerDataPlugin(DataPlugin plugin) {
        plugin.onRegister(this);
        registeredDataPlugins.add(plugin);
    }

    /**
     * Registers a new {@link DisplayPlugin} with the framework
     * @param plugin the display plugin to be registered
     */
    public void registerDisplayPlugin(DisplayPlugin plugin) {
        plugin.onRegister(this);
        registeredDisplayPlugins.add(plugin);
    }

    /**
     * Gets {@link currentDataPlugin} with the framework
     * @return the registered data plugin names
     */
    public List<String> getRegisteredDataPluginName() {
        return registeredDataPlugins.stream().map(DataPlugin::getDataName).collect(Collectors.toList());
    }

    /**
     * Gets {@link currentDisplayPlugin} with the framework
     * @return the registered display plugin names
     */
    public List<String> getRegisteredDisplayPluginName() {
        return registeredDisplayPlugins.stream().map(DisplayPlugin::getDisplayName).collect(Collectors.toList());
    }

    /**
     * Gets {@link currentDataPlugin} with the framework
     * 
     * @return the data plugin
     */
    public DataPlugin getDataPlugin() {
        return this.currentDataPlugin;
    }

    /**
     * Gets {@link currentDisplayPlugin} with the framework
     * 
     * @return the registered display plugin names
     */
    public DisplayPlugin getDisplayPlugin() {
        return this.currentDisplayPlugin;
    }

    /**
     * Sets {@link DataPlugin} with the framework
     * @param plugin plugin to be set
     */
    public void setDataPlugin(DataPlugin plugin) {
        currentDataPlugin = plugin;
    }

    /**
     * Sets a new {@link DisplayPlugin} with the framework
     * @param plugin plugin to be set
     */
    public void setDisplayPlugin(DisplayPlugin plugin) {
        currentDisplayPlugin = plugin;
    }


    /**
     * Gets {@link currentDisplayPlugin} with the framework
     * @param data the data to be used
     * @return the text info
     */
    public List<String> getTextInfo(String data) {
        return currentDataPlugin.processData(data);
    }

    /**
     * Gets {@link currentDisplayPlugin} with the framework
     * @param texts texts to be classifed
     * @return the classifed text
     */
    public HashMap<String, Integer> classifyTexts(List<String> texts) {
        HashMap<String, Integer> results = new HashMap<String, Integer>();
        String topic, topic2;
        String[] mainTopic;
        Document doc;
        ClassifyTextRequest request;

        // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            V2Model v2Model = V2Model.newBuilder()
                    .setContentCategoriesVersion(ContentCategoriesVersion.V2)
                    .build();
            ClassificationModelOptions options =
                    ClassificationModelOptions.newBuilder().setV2Model(v2Model).build();
            for (String text : texts) {
                // Set content to the text string
                doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
                request = ClassifyTextRequest.newBuilder()
                          .setDocument(doc)
                          .setClassificationModelOptions(options)
                          .build();
                // Detect categories in the given text
                ClassifyTextResponse response = language.classifyText(request);
                try {
                    topic = response.getCategoriesList().get(0).getName();
                    mainTopic = topic.split("/");
                    topic2 = mainTopic[1];
                    if (results.containsKey(topic2)) {
                        results.put(topic2, results.get(topic2)+ 1);
                    } else {
                        results.put(topic2, 1);
                    }
                } catch(Exception e){
                    continue;
                }
                
            }
        } catch (Exception e){
            System.out.println("Exception occurred here: "+e);
            return null;
        }
        Iterator<Entry<String, Integer>> itr = results.entrySet().iterator();
        System.out.println(results==null);
        while (itr.hasNext()) {
               Map.Entry pair = itr.next();
               System.out.println(pair.getKey() + " : " + pair.getValue());
        }
        return results;
    }

    /**
     * Registers a new {@link DisplayPlugin} with the framework
     * @param data the display plugin to be registered
     */
    public void visualizeData(String data) {
        List<String> texts = getTextInfo(data);
        System.out.println(texts.get(0));
        HashMap<String, Integer> results = classifyTexts(texts);
        if (results==null) {
            return;
        }
        currentDisplayPlugin.visualize(results);
    }
}