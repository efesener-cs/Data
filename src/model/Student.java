package model;

public record Student(
    String name,
    String surname,
    int studentNo,
    double gano,
    int _class,
    char gender)
{
    @Override
    public String toString(){
        return
        "name:"+name+","
        +"surname:"+surname+","
        +"no:"+studentNo+","
        +"gano:"+gano+","
        +"class:"+_class+","
        +"gender:"+gender;
    }

    public int getNo(){
        return studentNo;
    }
    public String getName(){
        return name+" "+surname;
    }
    public int getclass(){
        return _class;
    }
    public char getGender(){
        return gender;
    }
    public double getGano(){
        return gano;
    }
}
