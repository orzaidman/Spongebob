package com.example.newhw1;


public class Person implements Comparable {
    private String name;
    private String score;
    private double locationX;
    private double locationY;
    private String time;


    public Person(String name, String score,double locationX, double locationY ,String time) {
        if((name.length() == 0))
            this.name = "player";
       else
        this.name = name;
       this.score = score;
       this.locationX = locationX;
       this.locationY = locationY;
       this.time = time;
    }



    public String  getScore() {
        return score;
    }
    public String getName() {
        return name;
    }
    public double getLocationX() {
        return locationX;
    }
    public double getLocationY() {
        return locationY;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public void setLocationX(Double location) {
        this.locationX = location;
    }
    public void setLocationY(Double location) {
        this.locationY = location;
    }


    @Override
    public int compareTo(Object o) {
       Person temp = (Person) o;
        return  temp.score.compareTo(this.score);
    }


}
