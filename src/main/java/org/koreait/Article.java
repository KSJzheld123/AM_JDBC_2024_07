package org.koreait;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;


public class Article {

    // 연결
    public class DBConnection {
        public static Connection getConnection() {
            Connection conn = null;
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                String url = "jdbc:mariadb://localhost:3306/AM_JDBC_2024_07";
                conn = DriverManager.getConnection(url, "root", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conn;
        }
    }

    static Connection conn = null;
    static PreparedStatement stmt = null;
    static ResultSet rs = null;

    public static void write(Scanner sc) {
        System.out.println("제목을 입력해주세요 ");
        String titleAdd = sc.next();
        System.out.println("내용을 입력해주세요");
        String bodyAdd = sc.next();
        LocalDate now = LocalDate.now();
        String now2 = String.valueOf(now);
        try {

            String sql = "INSERT INTO " + "`article` " + "SET `regDate` = " + "?, " + "`updateDate` = " + "?, " + "`title` = " + "?, " + "`body` = " + "? ";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, now2);
            stmt.setString(2, now2);
            stmt.setString(3, titleAdd);
            stmt.setString(4, bodyAdd);

            stmt.executeUpdate();

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            System.out.println("게시글이 생성되었습니다");
        }
    }

//    public static void list(Scanner sc) {
//        String viewStr1 = "SELECT * FROM article";
//        ResultSet result1 = stmt.executeQuery(viewStr1);
//
//    }


    // 실행
    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        conn = DBConnection.getConnection();
        boolean loop = true;

        while (loop) {
            System.out.println("1.게시글 작성 2.게시글 리스트");
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    write(sc);
                    break;
                case 2:
                    break;

                case 3:
                    loop = false;
                    sc.close();
                    try {
                        stmt.close();
                        rs.close();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;




            }
        }
    }
}
