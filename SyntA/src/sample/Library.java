package sample;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Vector;

public class Library {
    private HashMap<String, Types> typesOfLexems;
    private Vector<Lexem> lexems;

    private static final String RED_LETTERS = (char) 27 + "[31m";
    private static final String GREEN_LETTERS = (char) 27 + "[32m";
    private static final String YELLOW_LETTERS = (char) 27 + "[33m";
    private static final String BLY_LETTERS = (char) 27 + "[34m";
    private static final String PERPUL_BACKGROUND = (char) 27 + "[45m";
    private static final String RESET = (char) 27 + "[0m";
    private static final String VIOLET_LETTER = (char) 27 + "[96m";


    {
        typesOfLexems = new HashMap<String, Types>();
        lexems = new Vector<Lexem>();
        typesOfLexems.put("private", Types.ACCEPT_OPT);
        typesOfLexems.put("public", Types.ACCEPT_OPT);
        typesOfLexems.put("protected", Types.ACCEPT_OPT);

        typesOfLexems.put("abstract", Types.MODIFIER);
        typesOfLexems.put("extern", Types.MODIFIER);
        typesOfLexems.put("override", Types.MODIFIER);
        typesOfLexems.put("static", Types.MODIFIER);
        typesOfLexems.put("virtual", Types.MODIFIER);
        typesOfLexems.put("volatile", Types.MODIFIER);

        typesOfLexems.put(" ", Types.SEPARATOR);
        typesOfLexems.put(";", Types.SEPARATOR);
        typesOfLexems.put("(", Types.SEPARATOR);
        typesOfLexems.put(")", Types.SEPARATOR);
        typesOfLexems.put("{", Types.SEPARATOR);
        typesOfLexems.put("}", Types.SEPARATOR);
        typesOfLexems.put(",", Types.SEPARATOR);
        typesOfLexems.put("[", Types.SEPARATOR);
        typesOfLexems.put("]", Types.SEPARATOR);
        typesOfLexems.put("\t", Types.SEPARATOR);
        typesOfLexems.put("\n", Types.SEPARATOR);

        typesOfLexems.put("ref", Types.SPEC_WORDS);
        typesOfLexems.put("out", Types.SPEC_WORDS);
        typesOfLexems.put("return", Types.SPEC_WORDS);
        typesOfLexems.put("class", Types.SPEC_WORDS);
        typesOfLexems.put("default", Types.SPEC_WORDS);

        typesOfLexems.put("~", Types.SPEC_SYMBOL);
        typesOfLexems.put(":", Types.SPEC_SYMBOL);
        typesOfLexems.put("=", Types.SPEC_SYMBOL);

        typesOfLexems.put("int", Types.TYPE_NAMES);
        typesOfLexems.put("char", Types.TYPE_NAMES);
        typesOfLexems.put("bool", Types.TYPE_NAMES);
        typesOfLexems.put("double", Types.TYPE_NAMES);
        typesOfLexems.put("float", Types.TYPE_NAMES);
        typesOfLexems.put("string", Types.TYPE_NAMES);
        typesOfLexems.put("void", Types.TYPE_NAMES);
    }

    public boolean isEndOfLexem(String line) {
        if (typesOfLexems.get(line) != null) {

            if (typesOfLexems.get(line) == Types.SEPARATOR || typesOfLexems.get(line) == Types.SPEC_SYMBOL) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }


    public Types checkLexemType(String line) {
        if (typesOfLexems.get(line) == Types.ACCEPT_OPT) {
            return Types.ACCEPT_OPT;
        } else if (typesOfLexems.get(line) == Types.MODIFIER) {
            return Types.MODIFIER;
        } else if (typesOfLexems.get(line) == Types.SEPARATOR) {
            return Types.SEPARATOR;
        } else if (typesOfLexems.get(line) == Types.SPEC_WORDS) {
            return Types.SPEC_WORDS;
        } else if (typesOfLexems.get(line) == Types.SPEC_SYMBOL) {
            return Types.SPEC_SYMBOL;
        } else if (typesOfLexems.get(line) == Types.TYPE_NAMES) {
            return Types.TYPE_NAMES;
        }
        return Types.IDENTIFICATOR;
    }

    public void addLexem(Lexem lexem) {
        String str = lexem.getName();
        String line = chekType(str);
        if (!line.equals("string")) {
            lexem.setValue(line);
            lexems.add(lexem);
        } else {
            int start = 0;
            for (int i = 0; i < str.length(); ) {
                i++;
                String tmp = str.substring(start,i);
                line = chekType(tmp);
                boolean t = (line.charAt(0) != '+' || line.charAt(0) != '-' ||line.charAt(0) != '.');
                if (line.equals("char") && t) {
                    break;
                }
                if (line.equals("string")) {
                    tmp = tmp.substring(0,tmp.length()-1);
                    Lexem lexem1= new Lexem(lexem.getNo(),tmp,checkLexemType(tmp),chekType(tmp));
                    lexems.add(lexem1);
                    start = i;
                    break;
                }
            }
            if(start != 0) {
                String tmp1 = str.substring(start - 1);
                Lexem lexem1= new Lexem(lexem.getNo(),tmp1,checkLexemType(tmp1),chekType(tmp1));
                lexems.add(lexem1);
            } else {
                lexem.setValue("string");
                lexems.add(lexem);
            }
        }
    }

    private String chekType(String str) {
        if (chekInt(str)) {
            return "int";
        }
        else if (chekFloat(str)) {
            return "float";
        } else if (chekDoble(str)) {
            return "double";
        } else if (chekBool(str)) {
            return "bool";
        } else if (str.length() == 1) {
            return "char";
        } else {
            return "string";
        }
    }

    private boolean chekInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean chekDoble(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean chekFloat(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        if(str.contains("f"))return true;
        else return false;
    }

    private boolean chekBool(String str) {
        if (str.equals("true") || str.equals("false"))return true;
        return false;
    }

    Vector<Lexem> getLexems() {
        return lexems;
    }

    public void clearLexems()
    {
        lexems.clear();
    }
}
