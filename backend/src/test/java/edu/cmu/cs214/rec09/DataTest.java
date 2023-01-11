package edu.cmu.cs214.rec09;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.rec09.DataPlugin.GuardianPlugin;
import edu.cmu.cs.cs214.rec09.DataPlugin.NYTimesPlugin;
import edu.cmu.cs.cs214.rec09.DataPlugin.WikiPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.DataPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFramework;
import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFrameworkImpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
public class DataTest {
    private TextAnalysisFramework framework;
    private List<DataPlugin> dataPlugins = new ArrayList<>();
    @Before
    public void setUp() {
        framework = new TextAnalysisFrameworkImpl();
        dataPlugins.add(new GuardianPlugin());
        dataPlugins.add(new NYTimesPlugin());
        dataPlugins.add(new WikiPlugin());
    }

    @Test
    public void testGuardian() {
        framework.setDataPlugin(dataPlugins.get(0));
        List<String> results = framework.getTextInfo("7");
        assertTrue(results!=null);
        for (String text:results) {
            // System.out.println(text);
        }
    }
    @Test
    public void testNYTimes() {
        framework.setDataPlugin(dataPlugins.get(1));
        List<String> results = framework.getTextInfo("7");
        assertTrue(results!=null);
        for (String text:results) {
            // System.out.println(text);
        }
    }
    @Test
    public void testWiki() {
        framework.setDataPlugin(dataPlugins.get(2));
        List<String> results = framework.getTextInfo("7");
        assertTrue(results!=null);
        for (String text:results) {
            // System.out.println(text);
        }
    }
}
