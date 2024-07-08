package org.koreait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Article {

    List<ArticleList> articles = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    int lastId = 0;

    public void run() {

        System.out.printf("명령어 ) ");
        String cmd = sc.nextLine().trim();

        while (true) {

            if(cmd.equals("exit")) {
                break;
            }

            if(cmd.length() ==  0) {
                System.out.println("명령어를 입력해 주세요");
                run();
            }

            if (cmd.equals("article write")) {
                write();
            } else if (cmd.equals("article list")) {
                list();
            } else {
                System.out.println("명령어를 다시 입력해주세요");
                run();
            }

            System.out.println("시스템 종료");
        }
    }















    void write() {
        int id = lastId + 1;
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        ArticleList article = new ArticleList(id, title, body);
        articles.add(article);
        lastId++;

        System.out.println(id + "번 게시글이 생성되었습니다");
        run();
    }

    void list() {
        System.out.println(" 번호  ||  제목  ||   내용   ");
        for (int i = articles.size() - 1; i >= 0; i--) {
            String titleCut = articles.get(i).title;
            if(articles.get(i).title.length() > 3) {
                titleCut = articles.get(i).title.substring(0,3);
            }
            System.out.printf("   %d   ||   %s  ||   %s   \n", articles.get(i).id, titleCut, articles.get(i).body);
        }
        run();
    }

}
