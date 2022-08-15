package com.example.exception.controller;


import com.example.exception.dto.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/user")
@Validated
public class ApiController {

    @GetMapping()
    public User get(
            @Size(min=2)//2글자 이상
            @RequestParam String name ,

            @NotNull
            @RequestParam Integer age)
    {
        //(required = false) 리퀘스트 파라미터가 없어도 동작할수 있도록함
        User user = new User();
        user.setName(name);
        user.setAge(age);

        return user;
    }

    @PostMapping()
    public User post(@Valid @RequestBody User user)
    {

        System.out.println(user);
        return user;
    }
}
