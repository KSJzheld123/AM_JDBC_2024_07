package org.koreait;

import java.sql.*;
import java.util.InputMismatchException;
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
        try {

            String sql = "INSERT INTO `article` SET `regDate` = NOW(), `updateDate` =  NOW(), `title` = ?, `body` = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, titleAdd);
            stmt.setString(2, bodyAdd);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("게시글이 생성되었습니다");
    }


    public static void list(Scanner sc) {
        try {
            String sql = "SELECT * FROM `article`";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String updateDate = rs.getString("updateDate");
                String title = rs.getString("title");
                if (title.length() > 3) {
                    title = title.substring(0, 3);
                }
                String body = rs.getString("body");
                System.out.printf("%d   ||          %s          ||   %s   ||   %s   \n", id, updateDate, title, body);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Scanner sc) {
        System.out.println("수정할 게시글의 id를 입력해주세요");
        System.out.printf("수정할 id : ");
        int updateId = sc.nextInt();
        System.out.printf("수정할 제목 : ");
        String titleUpdate = sc.next();
        System.out.printf("수정할 내용 : ");
        String bodyUpdate = sc.next();

        try {
            String sql = "UPDATE `article` SET `title` = ?, `body` = ? WHERE `id` = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, titleUpdate);
            stmt.setString(2, bodyUpdate);
            stmt.setInt(3, updateId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(updateId + "번 게시글이 수정되었습니다");
    }

    public static void delete(Scanner sc) {
        System.out.println("삭제할 게시글의 id를 입력해주세요");
        System.out.printf("삭제할 id : ");
        int updateId = sc.nextInt();

        try {
            String sql = "DELETE FROM `article` WHERE `id` = ?;";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, updateId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(updateId + "번 게시글이 삭제되었습니다");
    }


    // 실행
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        conn = DBConnection.getConnection();
        boolean loop = true;

        while (loop) {
            System.out.println("1.게시글 작성 2.게시글 리스트 3.게시글 수정 4.게시글 삭제");
            System.out.printf("명령어 ) ");
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    write(sc);
                    break;
                case 2:
                    list(sc);
                    break;
                case 3:
                    update(sc);
                    break;
                case 4:
                    delete(sc);
                    break;
                case 9:
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
