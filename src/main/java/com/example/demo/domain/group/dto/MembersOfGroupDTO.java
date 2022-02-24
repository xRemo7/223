package com.example.demo.domain.group.dto;

import com.example.demo.domain.appUser.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class MembersOfGroupDTO {
    private UUID id;
    private String groupname;
    private Set<User> members;
}
