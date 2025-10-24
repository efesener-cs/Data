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
        +"studentNo:"+studentNo+","
        +"gano:"+gano+","
        +"departmentRank:"+departmentRank+","
        +"classRank:"+classRank+","
        +"class:"+_class+","
        +"gender:"+gender;
    }
}
