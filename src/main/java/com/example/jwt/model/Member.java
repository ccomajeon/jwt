package com.example.jwt.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class Member {

    @SequenceGenerator(name = "memberSeq", sequenceName = "Member_Seq", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberSeq")
    private long id;
    private String memberName;
    private String password;
    private String roles;   // MEMBER, ADMIN

    // 하나의 member에 여러 롤을 담기 위해.
    public List<String> getRoleList(){
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
