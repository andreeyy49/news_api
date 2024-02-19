package com.example.news_v2.web.model.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserListResponse {

    private List<UserResponse> users = new ArrayList<>();
}
