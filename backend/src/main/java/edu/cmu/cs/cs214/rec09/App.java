package edu.cmu.cs.cs214.rec09;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import edu.cmu.cs.cs214.rec09.framework.core.DataPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.DisplayPlugin;
import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFramework;
import edu.cmu.cs.cs214.rec09.framework.core.TextAnalysisFrameworkImpl;
import edu.cmu.cs.cs214.rec09.framework.gui.TextState;
import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private TextAnalysisFrameworkImpl textFram;
    private List<DataPlugin> dataPlugins;
    private List<DisplayPlugin> displayPlugins;
    private static final int PORT = 8080;

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(PORT);

        this.textFram = new TextAnalysisFrameworkImpl();
        this.dataPlugins = loadDataPlugins();
        this.displayPlugins = loadDisplayPlugins();
        // displayPlugins = 
        for (DataPlugin p: this.dataPlugins){
            this.textFram.registerDataPlugin(p);
        }

        for (DisplayPlugin p: this.displayPlugins){
            this.textFram.registerDisplayPlugin(p);
        }


        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> params = session.getParms();
        String uri = session.getUri();
        if (uri.equals("/dataPlugin")) {
            // e.g., /plugin?i=0
            this.textFram.setDataPlugin(this.dataPlugins.get(Integer.parseInt(params.get("i"))));
            
        } else if (uri.equals("/displayPlugin")) {
            this.textFram.setDisplayPlugin(this.displayPlugins.get(Integer.parseInt(params.get("i"))));
            String startHTML = "<a href=\"index.html\"><button style=\"width:100%\">Go Back</button></a><p>Still loading</p>";
            String fileName = "../frontend/public/test.html";
            File testFile = new File(fileName);
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(testFile));
                bw.write(startHTML);
                bw.close();
            } catch(IOException e) {
                System.out.println("IOException here:"+e);
            }
            this.textFram.visualizeData("7");
        }
        // Extract the view-specific data from the game and apply it to the template.
        TextState t = TextState.forText(this.textFram);
        // System.out.print(t.toString());
        return newFixedLengthResponse(t.toString());

    }


    /**
     * Load plugins listed in META-INF/services/...
     *
     * @return List of instantiated data plugins
     */
    private static List<DataPlugin> loadDataPlugins() {
        ServiceLoader<DataPlugin> plugins = ServiceLoader.load(DataPlugin.class);
        List<DataPlugin> result = new ArrayList<>();
        for (DataPlugin plugin : plugins) {
            System.out.println("Loaded data plugin " + plugin.getDataName());
            result.add(plugin);
        }
        return result;
    }

    /**
     * Load plugins listed in META-INF/services/...
     *
     * @return List of instantiated display plugins
     */
    private static List<DisplayPlugin> loadDisplayPlugins() {
        ServiceLoader<DisplayPlugin> plugins = ServiceLoader.load(DisplayPlugin.class);
        List<DisplayPlugin> result = new ArrayList<>();
        for (DisplayPlugin plugin : plugins) {
            System.out.println("Loaded display plugin " + plugin.getDisplayName());
            result.add(plugin);
        }
        return result;
    }

    public static class Test {
        public String getText() {
            return "Hello World!";
        }
    }
}

