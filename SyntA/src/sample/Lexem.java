package sample;

public class Lexem {
    private int No;
    private Types type;
    private String name;
    private String value;

    public Lexem(int numb,String name,Types type,String value)
    {
        this.name = name;
        this.No = numb;
        this.type = type;
        this.value = value;
    }

    public Lexem(int numb,String name,Types type)
    {
        this.No = numb;
        this.name = name;
        this.type = type;
        this.value = null;
    }

    Types getType()
    {
        return type;
    }

    String getName()
    {
        return name;
    }

    int getNo()
    {
        return No;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
