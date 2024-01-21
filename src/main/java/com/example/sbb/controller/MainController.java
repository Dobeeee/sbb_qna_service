package com.example.sbb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/gugudan")
    @ResponseBody
    public String showGugudan(int dan, int limit) {
        String rs = "";
        for(int i = 0; i<=limit; i++) {
            rs += "%d * %d = %d<br>".formatted(dan, i, dan*i);
        }
        return rs;
    }

    @GetMapping("/gugu")
    @ResponseBody
    public String showGugu(Integer dan, Integer limit) {
        if(dan == null) {
            dan =9;
        }
        if(limit == null) {
            limit =9;
        }
        final Integer finalDan = dan;
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan*i))
                .collect(Collectors.joining("<br>\n"));
    }

    //Servlet 방식
    @GetMapping("/servlet")
    @ResponseBody

    public void showServlet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));

        resp.getWriter().append(a + b + "");
    }


    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String showMbti(@PathVariable String name) {
        return  switch (name) {
            case "홍길동" -> "INFP";
            case "이순신" -> "INTJ";
            default -> "모름";
        };
    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(name, value);

        return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable String name, HttpSession session) {
        String value = (String) session.getAttribute(name);

        return "세션 %s의 값은 %s 입니다.".formatted(name, value);
    }


    private List<Article> articles = new ArrayList<>(
            Arrays.asList(
                    new Article("제목", "내용"),
                    new Article("제목", "내용")
            )
    );


    @GetMapping("/addArticle")
    @ResponseBody
    public String article(String title, String body) {
        int id =1;
        Article article = new Article(title, body);
        articles.add(article);

        return "%d번째 게시물이 작성되었습니다.".formatted(article.getId());

    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable int id) {
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst().orElse(null);


        return article;
    }

    @GetMapping("/modifyArticle/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id, String title, String body) {
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst().orElse(null);

        if(article == null) {
            return "%d번의 게시물은 존재하지 않습니다.".formatted(id);
        }
        article.setTitle(title);
        article.setBody(body);
        return "%d번의 게시물을 수정하였습니다.".formatted(article.getId());
    }

    @GetMapping("/deleteArticle/{id}")
    @ResponseBody
    public String deleteArticle(@PathVariable int id) {
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst().orElse(null);

        if(article == null) {
            return "%d번의 게시물은 존재하지 않습니다.".formatted(id);
        }

        articles.remove(article);

        return "%d번의 게시물을 삭제하였습니다.".formatted(article.getId());
    }

    // Old version
    @GetMapping("/addPersonOnlyWay")
    @ResponseBody
    public Person addPersonOnlyWay(int id, int age, String name) {
       Person p = new Person(id, age, name);
       return p;
    }

    @GetMapping("/addPerson/{id}")
    @ResponseBody
    public Person addPerson(Person p) {

        return p;
    }



    @AllArgsConstructor
    @Getter
    class Person {
        private int id;
        private int age;
        private String name;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    class Article {

        private static int lastId=0;
        private int id;
        private String title;
        private String body;

        public Article(String title, String body) {
            this(++lastId, title, body);
        }
    }
}
