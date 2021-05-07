package com.greenart.MyHome.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name =  "user_id"),
            inverseJoinColumns = @JoinColumn(name ="role_id"))
    private List<Role> roles = new ArrayList<>();
    
	// @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false) // cascade = CascadeType.ALL은 user 엔티티에서도 보드가 딸려있으므로 보드 내용을 user쪽에서 변경하겠다.
   @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)     // orphanRemoval = true 는 삭제시 필요함
	// @JsonIgnore                             //  fetch = FetchType.EAGER 옵션은user 엔티티 사용시 board를 바로 가져올것인지(FetchType.EAGER) 나중에 필요할때 사용할 것인지 ( fetch = FetchType.LAZY)
	 private List<Board> boards = new ArrayList<>();
}