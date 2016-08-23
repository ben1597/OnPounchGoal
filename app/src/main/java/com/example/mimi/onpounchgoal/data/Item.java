package com.example.mimi.onpounchgoal.data;

/**
 * Created by mimi林明昊 on 2016/4/23.
 */
public class Item {

    private String name;
    private String value;
//    private int id;
    private String unit;
    private String update_time;
    private int done;               // 0 未完成, 1 完成 , 2 強化

    public Item(){}

    public Item(String name,String value,String unit,String update_time,int done){
        this.setName(name);
        this.setValue(value);
        this.setUnit(unit);
        this.setUpdate_time(update_time);
        this.setDone(done);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }
}
