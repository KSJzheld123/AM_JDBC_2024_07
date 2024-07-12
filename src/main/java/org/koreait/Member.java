package org.koreait;

import java.sql.*;
import java.util.Scanner;

public class Member {

    static {
        try {
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/AM_JDBC_2024_07", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static Connection conn;
    static PreparedStatement pstmt = null;
    static ResultSet rs = null;
    static boolean dbLoginCheck = false;

    public void memberJoin(Scanner sc) {
        String addId = "";
        String addPw = "";
        String addPwCheck = "";
        String addName = "";
        boolean idLoop = true;

        while (idLoop == true) {
            String dbId = "";
            System.out.printf("아이디 : ");
            addId = sc.nextLine();
            try {
                String sql = "SELECT * FROM `members` where m_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, addId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    if (addId.equals(rs.getString("m_id"))) {
                        dbId = rs.getString("m_id");
                        System.out.println("중복된 ID 입니다");
                        break;
                    }
                }
                if (dbId == "") {
                    System.out.println("사용가능한 ID입니다");
                    idLoop = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        boolean pwLoop = true;
        while (pwLoop == true) {
            System.out.printf("비밀번호 : ");
            addPw = sc.nextLine();
            System.out.println("비밀번호 확인 : ");
            addPwCheck = sc.nextLine();
            if (addPw.equals(addPwCheck)) {
                pwLoop = false;
            } else {
                System.out.println("비밀번호를 확인해주세요");
            }
        }
        System.out.printf("닉네임 : ");
        addName = sc.nextLine();

        try {
            String sql = "INSERT INTO members set regDate = NOW(), m_Id = ?, m_PW = ?, m_Name = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, addId);
            pstmt.setString(2, addPw);
            pstmt.setString(3, addName);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("회원가입이 완료되었습니다");
    }

    public void login(Scanner sc) {
        while (true) {
            System.out.printf("아이디 : ");
            String loginId = sc.nextLine();
            System.out.printf("비밀번호 : ");
            String loginPw = sc.nextLine();
            String loginIdCheck = sc.nextLine();
            String loginPwCheck = sc.nextLine();

            try {
                String sql = "SELECT * FROM `members`";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    if(loginId.equals(rs.getString("m_id"))) {
                        loginIdCheck = rs.getString("m_id");
                        loginPwCheck = rs.getString("m_PW");
                        break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(loginIdCheck.equals(loginId) && loginPwCheck.equals(loginPw)) {
                System.out.println("로그인이 확인되었습니다");
                dbLoginCheck = true;
                break;
            } else {
                System.out.println("아이디 또는 비밀번호를 확인해주세요");
            }
        }

    }







}
