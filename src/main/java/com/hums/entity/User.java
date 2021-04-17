package com.hums.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName User
 * @Description TODO
 * @Author hums
 * @Date 2021/3/26 10:51:43
 **/
@Entity
@Table(name="t_user")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(length = 30)
    private String name;
    @Column(length = 30)
    private String pwd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
