package ru.ifmo.ctddev.segal.hw3.myjavafx;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 * A WebView which has getters and setters for content or a document url.
 *
 * Usage in FXML element is:
 *
 *   by specifying CDATA escaped html content:
 *
 *     <EmbeddedWebView fx:id="embeddedWebView">
 *         <content>
 *             <![CDATA[
 *                 <h3>Embedded WebView</h3>
 *                 <p>HTML content inline in FXML</p>
 *             ]]>
 *         </content>
 *     </EmbeddedWebView>
 *
 */
public class EmbeddedWebView extends StackPane {

    final private WebView webView;

    private String content;

    public EmbeddedWebView() {
        webView = new WebView();
        getChildren().add(webView);
    }

    public String getContent() {
        return content;
    }

    /**
     * Loads html content directly into the webview.
     * @param content a html content string to load into the webview.
     */
    public void setContent(String content) {
        this.content = content;
        webView.getEngine().loadContent(content);
    }
}
