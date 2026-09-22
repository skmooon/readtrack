package com.sast.readtrack;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // 告诉 Spring：这个类里的方法会对外提供 HTTP 接口
public class HelloController {

    @GetMapping("/hello") // 当有人访问 /hello 时，调用这个方法
    public Map<String, String> hello() {
        return Map.of("message", "Hello, World!");
    }
}
