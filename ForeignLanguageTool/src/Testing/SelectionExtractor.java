package Testing;



import javafx.application.Application;

import javafx.geometry.*;

import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.scene.layout.VBox;

import javafx.scene.web.*;

import javafx.stage.Stage;



public class SelectionExtractor extends Application {



    public static final String HTML =

            "<p><em>\"Do not judge me by my successes, judge me by how many times I fell down and got back up again.\"</em></p>" +

            "&nbsp;&nbsp&nbsp&nbsp;-&nbspNelson Rolihlahla Mandela";



    public static final String SELECT_TEXT =

            "(function getSelectionText() {\n" +

            "    var text = \"\";\n" +

            "    if (window.getSelection) {\n" +

            "        text = window.getSelection().toString();\n" +

            "    } else if (document.selection && document.selection.type != \"Control\") {\n" +

            "        text = document.selection.createRange().text;\n" +

            "    }\n" +

            "    if (window.getSelection) {\n" +

            "      if (window.getSelection().empty) {  // Chrome\n" +

            "        window.getSelection().empty();\n" +

            "      } else if (window.getSelection().removeAllRanges) {  // Firefox\n" +

            "        window.getSelection().removeAllRanges();\n" +

            "      }\n" +

            "    } else if (document.selection) {  // IE?\n" +

            "      document.selection.empty();\n" +

            "    }" +

            "    return text;\n" +

            "})()";



    @Override

    public void start(Stage stage) throws Exception {

        HTMLEditor wisdom = new HTMLEditor();

        wisdom.setHtmlText(HTML);



        Label absorbedWisdom = new Label();



        Button selectWisdom = new Button("Get Selection");

        selectWisdom.setOnAction(event -> {

            WebView webView = (WebView) wisdom.lookup("WebView");

            if (webView != null) {

                WebEngine engine = webView.getEngine();

                Object selection = engine.executeScript(SELECT_TEXT);

                if (selection instanceof String) {

                    absorbedWisdom.setText((String) selection);

                }

            }

        });



        VBox layout = new VBox(

            10,

            selectWisdom,

            absorbedWisdom,

            wisdom

        );

        layout.setAlignment(Pos.CENTER);

        layout.setPadding(new Insets(10));



        stage.setScene(new Scene(layout));

        stage.show();

    }



    public static void main(String[] args) {

        launch(SelectionExtractor.class);

    }



}