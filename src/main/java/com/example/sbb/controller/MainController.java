package com.example.sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private int increaseNo = -1;

    @RequestMapping("/sbb")
    @ResponseBody
    // 아래 함수의 리턴값을 그대로 브라우저에 표시
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답을 바디에 담는다.
    public String index() {
        return "안녕하세요 되냥";
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showGet() {
        return """
                <form action="/page2" method="POST">
                    <input type="text" name="age" placeholder="숫자 입력"/>
                    <input type="submit" value="page2" />
                </form>    
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPost(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 숫자 : %d</h1>
                <h1>page2 POST 방식입니다.</h1>
                """.formatted(age);
    }

    @GetMapping("/plus")
    @ResponseBody
    public int showPlus(int a, int b) {
        return a+b;
    }
    @GetMapping("/minus")
    @ResponseBody
    public int showMinus(int a, int b) {
        return a-b;
    }
    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease() {

        increaseNo++;
        return increaseNo;
    }
}
