package sample;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class CodeBuilder {

    private Vector<Lexem> lexems;
    private BufferedWriter writer;
    private int size;
    //private int index = 0;

    public CodeBuilder(Vector<Lexem> lexems) throws IOException {
        this.lexems = lexems;
        writer = new BufferedWriter(new FileWriter("code.txt",true));
    }

    public void init () throws IOException {
        for (int i = 0; i < lexems.size(); i++) {
            if (lexems.get(i).getType() == Types.TYPE_NAMES) {
                switch (lexems.get(i).getName()) {
                    case "int":
                        i++;
                        writer.write("DECLARE: " + lexems.get(i).getName() + " 4\n");
                        writer.flush();
                        i++;
                        if (lexems.get(i).getName().equals("(")) {
                            writer.write("RETURNS: INTEGER\n");
                            writer.flush();
                            while (!lexems.get(i).getName().equals(")")) {
                                i++;
                                writer.write("ARGS: " + var(i) + " " +lexems.get(i + 1).getName() + size + " " + "\n");
                                writer.flush();
                                i++;
                                i++;
                            }
                        }
                        else {
                            while (!lexems.get(i).getName().equals(";")) {
                                i++;
                                writer.write("DECLARE: " + lexems.get(i).getName() + " 4\n");
                                writer.flush();
                                i++;
                            }
                        }
                        break;
                    case "char":
                        i++;
                        writer.write("DECLARE: " + lexems.get(i).getName() + " 1\n");
                        writer.flush();
                        i++;
                        if (lexems.get(i).getName().equals("(")) {
                            writer.write("RETURNS: CHAR\n");
                            writer.flush();
                            while (!lexems.get(i).getName().equals(")")) {
                                i++;
                                writer.write("ARGS: " + var(i) + " " +lexems.get(i + 1).getName() + " " + size + "\n");
                                writer.flush();
                                i++;
                                i++;
                            }
                        }
                        else {
                            while (!lexems.get(i).getName().equals(";")) {
                                i++;
                                writer.write("DECLARE: " + lexems.get(i).getName() + " 1\n");
                                writer.flush();
                                i++;
                            }
                        }
                        break;
                    case "bool":
                        i++;
                        writer.write("DECLARE: " + lexems.get(i).getName() + " 1\n");
                        writer.flush();
                        i++;
                        if (lexems.get(i).getName().equals("(")) {
                            writer.write("RETURNS: BOOLEAN\n");
                            writer.flush();
                            while (!lexems.get(i).getName().equals(")")) {
                                i++;
                                writer.write("ARGS: " + var(i) + " " +lexems.get(i + 1).getName() + " " + size + "\n");
                                writer.flush();
                                i++;
                                i++;
                            }
                        }
                        else {
                            while (!lexems.get(i).getName().equals(";")) {
                                i++;
                                writer.write("DECLARE: " + lexems.get(i).getName() + " 1\n");
                                writer.flush();
                                i++;
                            }
                        }
                        break;
                    case "string":
                        i++;
                        writer.write("DECLARE: " + lexems.get(i).getName() +" " + lexems.get(i).getName().length() + "\n");
                        writer.flush();
                        i++;
                        if (lexems.get(i).getName().equals("(")) {
                            writer.write("RETURNS: STRING\n");
                            writer.flush();
                            while (!lexems.get(i).getName().equals(")")) {
                                i++;
                                writer.write("ARGS: " + var(i) + " " +lexems.get(i + 1).getName() + size + "\n");
                                writer.flush();
                                i++;
                                i++;
                            }
                        }
                        else {
                            while (!lexems.get(i).getName().equals(";")) {
                                i++;
                                writer.write("DECLARE: " + lexems.get(i).getName() + " " + lexems.get(i).getName().length() + "\n");
                                writer.flush();
                                i++;
                            }
                        }
                        break;
                    case "float":
                        i++;
                        writer.write("DECLARE: " + lexems.get(i).getName() + " 4\n");
                        writer.flush();
                        i++;
                        if (lexems.get(i).getName().equals("(")) {
                            writer.write("RETURNS: FLOAT\n");
                            writer.flush();
                            while (!lexems.get(i).getName().equals(")")) {
                                i++;
                                writer.write("ARGS: " + var(i) + " " +lexems.get(i + 1).getName() + " " + size + "\n");
                                writer.flush();
                                i++;
                                i++;
                            }
                        }
                        else {
                            while (!lexems.get(i).getName().equals(";")) {
                                i++;
                                writer.write("DECLARE: " + lexems.get(i).getName() + " 4\n");
                                writer.flush();
                                i++;
                            }
                        }
                        break;
                    case "double":
                        i++;
                        writer.write("DECLARE: " + lexems.get(i).getName() + " 8\n");
                        writer.flush();
                        i++;
                        if (lexems.get(i).getName().equals("(")) {
                            writer.write("RETURNS: DOUBLE\n");
                            writer.flush();
                            while (!lexems.get(i).getName().equals(")")) {
                                i++;
                                writer.write("ARGS: " + var(i) + " " +lexems.get(i + 1).getName() + " " + size + "\n");
                                writer.flush();
                                i++;
                                i++;
                            }
                        }
                        else {
                            while (!lexems.get(i).getName().equals(";")) {
                                i++;
                                writer.write("DECLARE: " + lexems.get(i).getName() + " 8\n");
                                writer.flush();
                                i++;
                            }
                        }
                        break;
                    case "void":
                        i++;
                        writer.write("DECLARE: " + lexems.get(i).getName() + " 0\n");
                        writer.flush();
                        i++;
                        if (lexems.get(i).getName().equals("(")) {
                            writer.write("RETURNS: VOID\n");
                            writer.flush();
                            while (!lexems.get(i).getName().equals(")")) {
                                i++;
                                writer.write("ARGS: " + var(i) + " " +lexems.get(i + 1).getName() + " " + size + "\n");
                                writer.flush();
                                i++;
                                i++;
                            }
                        }
                        else {
                            while (!lexems.get(i).getName().equals(";")) {
                                i++;
                                writer.write("DECLARE: " + lexems.get(i).getName() + " 0\n");
                                writer.flush();
                                i++;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        writer.close();
    }

    private String var(int i) {
        switch (lexems.get(i).getName()) {
            case "int":
                size = 4;
                return "INTEGER";
            case "float":
                size = 4;
                return "FLOAT";
            case "bool":
                size = 1;
                return "BOOLEAN";
            case "double":
                size = 8;
                return "DOUBLE";
            case "char":
                size = 1;
                return "CHAR";
            case "string":
                size = lexems.get(i + 1).getName().length();
                return "STRING";
            default:
                return "ERROR";
        }
    }
}
