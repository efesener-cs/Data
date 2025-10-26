package model;

public record Student(
    String name,
    String surname,
    int studentNo,
    float gano,
    int departmentRank,
    int classRank,
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
        +"departmentRank:"+departmentRank+","
        +"classRank:"+classRank+","
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
    public int getDepartment(){
        return departmentRank;
    }
    public char getGender(){
        return gender;
    }
}
