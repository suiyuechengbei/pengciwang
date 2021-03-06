package com.example.myapplication.Util;
/**
 *
 */
public class User {
    private long id;                //用户ID
    private String name;            //用户名
    private String password;        //密码
    private String telephone;       //手机号
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public User(String name, String password,String telephone) {
        this.name = name;
        this.password = password;
        this.telephone =telephone;
    }
    public User(long id, String name, String password, String telephone){
        this.id = id;
        this.name = name;
        this.password = name;
        this.telephone = name;
    }
    public long getId() {return id; }
    public void setId(long id){ this.id = id;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getTelephone(){return telephone;}
    public void setTelephone(String telephone){this.telephone=telephone;}

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}

