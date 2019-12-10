package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


import java.io.IOException;
import java.util.Vector;

public class Main extends Application {

    private Library library;
    private LexA lexA;
    private SyntaxA syntaxA;
    private CodeBuilder codeBuilder;

    @Override
    public void start(Stage primaryStage) throws Exception{

        library = new Library();
        lexA = new LexA(library);
        syntaxA = new SyntaxA(library);
        codeBuilder = new CodeBuilder(library.getLexems());

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, CornerRadii.EMPTY, Insets.EMPTY)));



        TextArea textArea = new TextArea();
        root.setCenter(textArea);

        TextArea chekArea = new TextArea();
        chekArea.setEditable(false);
        chekArea.setMaxWidth(300);
        root.setRight(chekArea);

        Button button = new Button();
        button.setText("Analization");
        button.setMaxWidth(100);
        root.setBottom(button);
        BorderPane.setMargin(button,new Insets(10,0,0,0));

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String text = textArea.getText();
                if(chekArea.getLength() != 0) {
                    chekArea.clear();
                    library.clearLexems();
                }

                int start = 0;
                //int k = 0;
                for (int i = 0,j = 0; i < text.length(); i++) {
                    if(text.charAt(i) == '\n' || i + 1 == text.length())
                    {
                        String line = separateLine(start, i + 1, text);
                        lexA.checkLine(j,line);
                        start = i;
                        /*
                        Vector<Lexem> lex = library.getLexems();
                        for (; k < lex.size(); k++) {
                            switch (lex.get(k).getType())
                            {
                                case ACCEPT_OPT:
                                    chekArea.appendText(j + 1 + ". ACCEPT_OPT  Lexem :" + "\"" +lex.get(k).getName() + "\"" + " type: " + lex.get(k).getValue() + "\n");
                                    break;
                                case MODIFIER:
                                    chekArea.appendText(j + 1 + ". MODIFIER  Lexem :" + "\"" +lex.get(k).getName() + "\"" + " type: " + lex.get(k).getValue() + "\n");
                                    break;
                                case SEPARATOR:
                                    if(lex.get(k).getName().equals("\n")) chekArea.appendText(j + 1 + ". SEPARATOR  Lexem :" + "\" \\n"  + "\"" + " type: " + lex.get(k).getValue() + "\n");
                                    else if(lex.get(k).getName().equals("\t"))chekArea.appendText(j + 1 + ". SEPARATOR  Lexem :" + "\" \\t"  + "\"" + " type: " + lex.get(k).getValue() + "\n");
                                    else chekArea.appendText(j + 1 + ". SEPARATOR  Lexem :" + "\"" +lex.get(k).getName() + "\"" + " type: " + lex.get(k).getValue() + "\n");
                                    break;
                                case SPEC_WORDS:
                                    chekArea.appendText(j + 1 + ". SPEC_WORDS  Lexem :" + "\"" +lex.get(k).getName() + "\"" + " type: " + lex.get(k).getValue() + "\n");
                                    break;
                                case SPEC_SYMBOL:
                                    chekArea.appendText(j + 1 + ". SPEC_SYMBOL  Lexem :" + "\"" +lex.get(k).getName() + "\"" + " type: " + lex.get(k).getValue() + "\n");
                                    break;
                                case TYPE_NAMES:
                                    chekArea.appendText(j + 1 + ". TYPE_NAMES  Lexem :" + "\"" +lex.get(k).getName() + "\"" + " type: " + lex.get(k).getValue() + "\n");
                                    break;
                                default:
                                    chekArea.appendText(j + 1 + ". IDENTIFICATOR  Lexem :" + "\"" +lex.get(k).getName() + "\"" + " type: " + lex.get(k).getValue() + "\n");
                                    break;
                            }
                        }*/
                        j++;
                    }
                }

                syntaxA.init();
                if(syntaxA.result()) {
                    chekArea.appendText(syntaxA.getBugReport().elementAt(0));
                    try {
                        codeBuilder.init();
                    } catch (IOException e) {
                        e.getMessage();
                    }
                }
                else {
                    for (String str: syntaxA.getBugReport()) {
                        chekArea.appendText(str);
                    }
                }
            }
        });

        primaryStage.setTitle("Application");
        primaryStage.setScene(new Scene(root, 700, 725));
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    private String separateLine(int start, int end, String line)
    {
        return line.substring(start, end);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
