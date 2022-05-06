package com.sprint1.be_java_hisp_w15_g03_acosta.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends Person{

    private List<User> followers;
    private List<Publication> publications;


    public Seller(Integer id, String name){
        super(id, name);
        followers = new ArrayList<>();
        publications = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Seller{" +
                "followers=" + followers +
                ", publications=" + publications +
                '}';
    }

}
