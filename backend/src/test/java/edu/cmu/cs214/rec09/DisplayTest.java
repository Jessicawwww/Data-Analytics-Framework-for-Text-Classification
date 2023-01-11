package edu.cmu.cs214.rec09;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.rec09.DisplayPlugin.BarPlugin;
import edu.cmu.cs.cs214.rec09.DisplayPlugin.PolarAreaPlugin;
import edu.cmu.cs.cs214.rec09.DisplayPlugin.RadarPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.DataPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.DisplayPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFramework;
import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFrameworkImpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * this class is for testing {@link UI} 
 */
public class DisplayTest {
    private TextAnalysisFramework framework;
    private List<DisplayPlugin> displayPlugins = new ArrayList<>();
    private HashMap<String, Integer> results = new HashMap<>();
    @Before
    public void setUp() {
        framework = new TextAnalysisFrameworkImpl();
        displayPlugins.add(new BarPlugin());
        displayPlugins.add(new PolarAreaPlugin());
        displayPlugins.add(new RadarPlugin());
        
        results.put("People & Society", 2);
        results.put("Travel & Transportation", 1);
        results.put("Business & Industrial", 1);
        results.put("Arts & Entertainment", 5);
        results.put("Sensitive Subjects", 7);
    }

    @Test
    public void testBar() {
        DisplayPlugin currentPlugin = displayPlugins.get(0);
        framework.setDisplayPlugin(currentPlugin);
        currentPlugin.visualize(results);
        assertTrue(currentPlugin.getDataSets()!=null);
        assertTrue(currentPlugin.getHTML()!=null);
        
    }
    @Test
    public void testPolarArea() {
        DisplayPlugin currentPlugin = displayPlugins.get(1);
        framework.setDisplayPlugin(currentPlugin);
        currentPlugin.visualize(results);
        assertTrue(currentPlugin.getDataSets()!=null);
        assertTrue(currentPlugin.getHTML()!=null);
    }
    @Test
    public void testRadar() {
        DisplayPlugin currentPlugin = displayPlugins.get(2);
        framework.setDisplayPlugin(currentPlugin);
        currentPlugin.visualize(results);
        assertTrue(currentPlugin.getDataSets()!=null);
        assertTrue(currentPlugin.getHTML()!=null);
    }
}
