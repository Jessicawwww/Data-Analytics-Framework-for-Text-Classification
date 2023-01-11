package edu.cmu.cs.cs214.rec09.DisplayPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.cmu.cs.cs214.rec09.DataPlugin.WikiPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.DataPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.DisplayPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFramework;
import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFrameworkImpl;

public class PolarAreaPlugin implements DisplayPlugin{
    private static final String DISPLAY_NAME = "Polar Area Displayer";
    private String chartType = "radar";
    private String dataSets;
    private String htmlFile;
    private TextAnalysisFramework framework;
    /**
     * Gets the name of the plug-in display mode.
     * @return display name
     */
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    /**
     * Gets the chart type.
     * @return chart type
     */
    public String getChartType() {
        return this.chartType;
    }

    /**
     * Gets the dataSets.
     * @return the data sets
     */
    public String getDataSets() {
        return this.dataSets;
    }
    /**
     * Gets the html file name.
     * @return the html 
     */
    public String getHTML() {
        return this.htmlFile;
    }

    /**
     * Called (only once) when the plug-in is first registered with the
     * framework, giving the plug-in a chance to perform any initial set-up
     * before the game has begun (if necessary).
     *
     * @param fw The {@link TextAnalysisFramework} instance with which the plug-in
     *                  was registered.
     */
    public void onRegister(TextAnalysisFramework fw) {
        this.framework = fw;
    }

    /**
     * Visualize processed text info
     *
     * @param results The processed text info
     */
    public void visualize(HashMap<String, Integer> results) {
        String dataSetsString = results.toString();
        // this.dataSets = dataSetsString.replace('=',':');
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, Integer>> itr1 = results.entrySet().iterator();
        sb.append("[");
        while (itr1.hasNext()) {
               Map.Entry pair = itr1.next();
               if (!itr1.hasNext()){
                sb.append("'"+pair.getKey()+"'");
               } else {
                sb.append("'"+pair.getKey()+"',");
               }
           }
        sb.append("]");
        this.dataSets = sb.toString();
        this.htmlFile = toHTML(results);
        System.out.println(this.htmlFile);
        System.out.println(this.dataSets);
    }

    /**
     * translate results to html
     * @param results the results to be translated
     * @return the html 
     */
    public String toHTML(HashMap<String, Integer> results) {
        String driver = "<script src=\"https://cdn.jsdelivr.net/npm/chart.js\"></script>";
        String html = """
        <a href=\"index.html\"><button style=\"width:100%\">Go Back</button></a>
        <div>
            <canvas id="myChart"></canvas>
        </div>
        """ +driver+
        """
        <script>
        const ctx = document.getElementById('myChart');
        new Chart(ctx, {
            type: 'polarArea',
            data: {
            labels: 
        """
        +dataSets+
        """
        ,datasets: [{label: 'Topic Frequency',
        data:      
        """+results.values()+
        """
        }]},
        options: {}
        });
        </script>
        """;
        String fileName = "../frontend/public/test.html";
        File testFile = new File(fileName);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(testFile));
            bw.write(html);
            bw.close();
        } catch(IOException e) {
            System.out.println("IOException here:"+e);
        }
        return fileName;
    }

    public static void main(String[] args) {
        PolarAreaPlugin testFW = new PolarAreaPlugin();
        testFW.framework = new TextAnalysisFrameworkImpl();
        DataPlugin testData = new WikiPlugin();
        testFW.framework.registerDataPlugin(testData);
        testFW.framework.setDataPlugin(testData);
        testFW.framework.setDisplayPlugin(testFW);
        testFW.framework.visualizeData("7");
    }
}
