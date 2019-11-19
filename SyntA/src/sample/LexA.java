package sample;

public class LexA {

    private Library library;

    private boolean comment = false;

    public LexA(Library library)
    {
        this.library = library;
    }

    private boolean checkLexem(int numb,String line, boolean isEnd)
    {
        if ((library.isEndOfLexem(line))&& (line.length() == 1))
        {
            if (line.equals(" ") || line.equals("\n") || line.equals("\t")) {
                return true;
            }
            library.addLexem(new Lexem(numb,line,library.checkLexemType(line)));
            return true;
        }
        else if(isEnd) {
            if (line.length() > 1) {
                if (helpToAnalize(numb, line)) return true;
                library.addLexem(new Lexem(numb, line, library.checkLexemType(line)));
                return true;
            }
            library.addLexem(new Lexem(numb, line, library.checkLexemType(line)));
            return true;
        }
        else if(line.length() > 1) {
            return helpToAnalize(numb, line);

        } else {
            return false;
        }


    }

    private boolean helpToAnalize(int numb, String line) {
        int start = line.length() - 1;
        int end = line.length();
        String lineStart = line.substring(0, start);
        String lineEnd = line.substring(start, end);
        if (library.isEndOfLexem(lineEnd)) {
            if (lineEnd.equals(" ") || lineEnd.equals("\n") || lineEnd.equals("\t")) {
                library.addLexem(new Lexem(numb, lineStart, library.checkLexemType(lineStart)));
                return true;
            }
            library.addLexem(new Lexem(numb, lineStart, library.checkLexemType(lineStart)));
            library.addLexem(new Lexem(numb, lineEnd, library.checkLexemType(lineEnd)));
            return true;
        }
        return false;
    }

    public void checkLine(int numbOfLine, String line)
    {
        int commentIndex;
        String string = line;
        if(line.contains("//")) {
            commentIndex = line.indexOf("//");
            string = string.substring(0,commentIndex);
        } else if (line.contains("/*")) {
            commentIndex = line.indexOf("/*");
            string = line.substring(0,commentIndex);
            comment = true;
        }
        if(line.contains("*/")) {
            commentIndex = line.indexOf("*/") + 2;
            if (string.length() != line.length()) {
                string += " ";
                string += line.substring(commentIndex);
            }else {
                string = line.substring(0,commentIndex);
            }
            comment = false;
        }
        if(comment) return;
        int start = 0;
        int end = 1;
        String subLine;
        while (end < string.length())
        {
            subLine = string.substring(start,end);
            if(checkLexem(numbOfLine,subLine,false))
            {
                start = end;
            }
            ++end;
        }
        subLine = string.substring(start);
        checkLexem(numbOfLine,subLine,true);

    }
}






