package edu.cmu.cs214.rec09;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFramework;
import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFrameworkImpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
public class NLPTest {
    private TextAnalysisFramework framework;
    private List<String> texts = new ArrayList<>();
    @Before
    public void setUp() {
        framework = new TextAnalysisFrameworkImpl();
        String text1 = "Richard M. Fierro, who served for 15 years in the military, was at the nightclub in Colorado Springs with his family when the gunman opened fire. “I just knew I had to take him down,” he said.";
        String text2 = "A navigation app that illuminates public land within privately held property has supercharged the question of who gets to go where.";
        String text3 = "A message found by the police on the killer’s phone asked God to forgive him for targeting co-workers, whose deaths were the latest in a string of mass shooting fatalities.";
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
    }
    @Test
    public void testClassification() {
        HashMap<String, Integer> results = framework.classifyTexts(texts);
        assertTrue(results!=null);
        Iterator<Entry<String, Integer>> itr = results.entrySet().iterator();
        while (itr.hasNext()) {
               Map.Entry pair = itr.next();
               System.out.println(pair.getKey() + " : " + pair.getValue());
        }
    }
}
