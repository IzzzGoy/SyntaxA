package sample;

import java.util.Vector;

class SyntaxA {
    private Vector<Lexem> lexems;
    private int index;
    private int prevIndex;
    private Vector<String> bugReport;
    private String class_name;

    SyntaxA(Library library) {
        this.lexems = library.getLexems();
        bugReport = new Vector<>();
    }

    public boolean result () {
        return bugReport.isEmpty();
    }

    public void init() {
        bugReport.clear();
        index = 0;
        Header();
        if (!checkTerminal("class","class or modifier")) {
            return;
        }
        index++;
        if (lexems.elementAt(index).getType() != Types.IDENTIFICATOR) {
            makeReportMassage(lexems.elementAt(index),"class name");
        }
        lexems.elementAt(index).setValue("className");
        class_name = lexems.elementAt(index).getName();
        index++;
        Class_Base_Opt();
        if (!checkTerminal("{","class start {")) {
            return;
        }
        index++;
        Class_Item_Decs_Opt();
        if (!checkTerminal("}","class end }")) {
            return;
        }
    }

    private boolean checkTerminal(String expectedTerminal, String errorMessage) {
        boolean one = true;
        if (index >= lexems.size()) {
            makeReportMassage(lexems.elementAt(lexems.size() - 1), "end of class");
            return false;
        }
        while (!lexems.elementAt(index).getName().equals(expectedTerminal)) {
            if (index >= lexems.size()) {
                makeReportMassage(lexems.elementAt(index), "end of class");
                return false;
            }
            if (one) {
                one = false;
                makeReportMassage(lexems.elementAt(index), errorMessage);
                return false;
            }
        }
        return true;
    }

    private void  makeReportMassage(Lexem lexem, String string) {
        String stringL = "Bug in " + (lexem.getNo() + 1) + " line, expected " + string;
        if (!bugReport.contains(stringL)) {
            bugReport.add("Bug in " + (lexem.getNo() + 1) + " line, expected " + string);
        }
    }

    public Vector<String> getBugReport() {
        if (bugReport.isEmpty()) {
            bugReport.add("Successful!");
        }
        return bugReport;
    }

    private void Header() {
        Access_Opt();
        Modifier_ListOpt();
    }

    private void Access_Opt() {
        if (lexems.elementAt(index).getType() == Types.ACCEPT_OPT) {
            index++;
        }
    }

    private void Modifier_ListOpt() {
        while (Modifier()) {
            index++;
        }
    }

    private boolean Modifier() {
        return lexems.elementAt(index).getType() == Types.MODIFIER;
    }

    private void Class_Base_Opt() {
        if (!lexems.elementAt(index).getName().equals(":")) {
            //makeReportMassage(lexems.elementAt(index), ":");
            //index--;
            return;
        }
        index++;
        Class_Base_List();
    }

    private void Class_Base_List() {
        while (true) {
            if (!Type()) {
                makeReportMassage(lexems.elementAt(index), "classType");
                return;
            }
            index++;
            if (index == lexems.size()) return;
            if (lexems.elementAt(index).getName().equals("{")) {
                return;
            } else if (!lexems.elementAt(index).getName().equals(",")) {
                makeReportMassage(lexems.elementAt(index),",");
                return;
            }
            index++;
            if (index == lexems.size()) return;
        }
    }

    private boolean Type() {
        return lexems.elementAt(index).getType() == Types.IDENTIFICATOR;
    }

    private void Semicolon_Opt() {
        if (lexems.elementAt(index).getName().equals(";")) {
            index++;
        }
    }

    private void Class_Item_Decs_Opt() {
        while (Class_Item()) {
        }
    }

    private boolean Class_Item() {
        if (lexems.elementAt(index).getName().equals("}")) {
            return false;
        }
        Vector<String>reportsSave = new Vector<>();
        for (String string: bugReport) {
            reportsSave.add(string);
        }
        //reportsSave = bugReport;
        boolean isField = false;
        if (Destructor_Dec()) {
            isField = true;
        }
        else if (Method_Dec()) {
            isField = true;
        }
        else if (Constructor_Dec()) {
            isField = true;
        }
        else if (Field_Dec()) {
            isField = true;
        }
        if (isField) {
            bugReport = reportsSave;
            return true;
        }
        return false;
    }

    private boolean Field_Dec() {
        prevIndex = index;
        Header();
        if (Type_Field()) {
            index++;
            while (true) {
                if (!Type()) {
                    makeReportMassage(lexems.elementAt(index), "fieldName");
                    index = prevIndex;
                    return false;
                }
                index++;
                if (lexems.elementAt(index).getName().equals(";")) {
                    index++;
                    return true;
                }
                if (index == lexems.size()){
                    index = prevIndex;
                    return false;
                }
                if (lexems.elementAt(index).getName().equals("}")) {
                    makeReportMassage(lexems.elementAt(index), "end of field");
                    index = prevIndex;
                    return false;
                } else if (!lexems.elementAt(index).getName().equals(",")) {
                    makeReportMassage(lexems.elementAt(index),";");
                    index = prevIndex;
                    return false;
                }
                index++;
                if (index == lexems.size()) {
                    index = prevIndex;
                    return false;
                }
            }
        }
        makeReportMassage(lexems.elementAt(index),"fieldType");
        index = prevIndex;
        return false;
    }

    private boolean Method_Dec() {
        prevIndex = index;
        Header();
        if (Type_Field()) {
            index++;
            if (!Type()) {
                makeReportMassage(lexems.elementAt(index), "fieldName");
                index = prevIndex;
                return false;
            }
            index++;
            if (!lexems.elementAt(index).getName().equals("(")) {
                makeReportMassage(lexems.elementAt(index), "start of method");
                index = prevIndex;
                return false;
            }
            index++;
            if (Formal_Param_List_Opt()) {
                index++;
                if (lexems.elementAt(index).getName().equals(";")) {
                    index++;
                    return true;
                }
                else {
                    makeReportMassage(lexems.elementAt(index),";");
                    index = prevIndex;
                    return false;
                }
            }
            else {
                index = prevIndex;
                return false;
            }
        }
        index = prevIndex;
        return false;
    }

    private boolean Formal_Param_List_Opt() {
        if (lexems.elementAt(index).getName().equals(")")) {
            return true;
        }
        else return Formal_Param_List();
    }

    private boolean Formal_Param_List() {
        while (true) {
            if (index == lexems.size()){
                index = prevIndex;
                return false;
            }
            if (!Formal_Param()){
                makeReportMassage(lexems.elementAt(index), "ParamName");
                index = prevIndex;
                return false;
            }
            index++;
            if (index == lexems.size()){
                index = prevIndex;
                return false;
            }
            if (lexems.elementAt(index).getName().equals(")")) {
                return true;
            }
            else if (!lexems.elementAt(index).getName().equals(",")) {
                makeReportMassage(lexems.elementAt(index),",");
                index = prevIndex;
                return false;
            }
            index++;
        }
    }

    private boolean Formal_Param() {
        if (lexems.elementAt(index).getName().equals("ref") || lexems.elementAt(index).getName().equals("out")) {
            index++;
        }
        if (index == lexems.size()){
            index = prevIndex;
            return false;
        }
        if (Type_Field()) {
            index++;
            if (index == lexems.size()){
                index = prevIndex;
                return false;
            }
            return Type();
        }
        else {
            return false;
        }
    }

    private boolean Constructor_Dec() {
        prevIndex = index;
        Header();
        return Constructor_Declarator();
    }

    private boolean Constructor_Declarator() {
        if (!lexems.elementAt(index).getName().equals(class_name)) {
            makeReportMassage(lexems.elementAt(index),"className");
            index = prevIndex;
            return false;
        }
        index++;
        if (!lexems.elementAt(index).getName().equals("(")) {
            makeReportMassage(lexems.elementAt(index), "start of method");
            index = prevIndex;
            return false;
        }
        index++;
        if (Formal_Param_List_Opt()) {
            index++;
            if (lexems.elementAt(index).getName().equals(";")) {
                index++;
                return true;
            } else {
                makeReportMassage(lexems.elementAt(index),";");
                index = prevIndex;
                return false;
            }
        } else {
            index = prevIndex;
            return false;
        }
    }

    private boolean Destructor_Dec() {
        prevIndex = index;
        Header();
        if (!lexems.elementAt(index).getName().equals("~")) {
            index = prevIndex;
            return false;
        }
        index++;
        if (!lexems.elementAt(index).getName().equals(class_name)) {
            makeReportMassage(lexems.elementAt(index),"className");
            index = prevIndex;
            return false;
        }
        index++;
        if (!lexems.elementAt(index).getName().equals("(")) {
            makeReportMassage(lexems.elementAt(index),"start of Destructor");
            index = prevIndex;
            return false;
        }
        index++;
        if (!lexems.elementAt(index).getName().equals(")")) {
            makeReportMassage(lexems.elementAt(index),"end of Destructor");
            index = prevIndex;
            return false;
        }
        index++;
        if (!lexems.elementAt(index).getName().equals(";")) {
            makeReportMassage(lexems.elementAt(index),";");
            index = prevIndex;
            return false;
        }
        index++;
        return true;
    }

    private boolean Type_Field() {
        return lexems.elementAt(index).getType() == Types.TYPE_NAMES;
    }
}
