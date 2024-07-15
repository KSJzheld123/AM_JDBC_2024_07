package org.koreait;

import java.sql.*;
import java.util.Scanner;

public class Member {

    static LoginStatus loginStatus;

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
    int loginCount = 0;

    public void memberJoin(Scanner sc) {
        if (dbLoginCheck == false) {

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
        } else {
            System.out.println("로그아웃 후 이용해주세요");
        }
    }

    public void login(Scanner sc) {

        if (dbLoginCheck == false) {

            while (true) {
                if (loginCount == 3) {
                    System.out.println("비밀번호 입력실패 3회로 프로그램을 종료합니다");
                    sc.close();
                }

                System.out.printf("아이디 : ");
                String loginId = sc.nextLine();
                System.out.printf("비밀번호 : ");
                String loginPw = sc.nextLine();
                String loginIdCheck = "";
                String loginPwCheck = "";
                int loginNum = 0;
                String loginName = "";

                try {
                    String sql = "SELECT * FROM `members`";
                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        if (loginId.equals(rs.getString("m_Id"))) {
                            loginIdCheck = rs.getString("m_Id");
                            loginPwCheck = rs.getString("m_Pw");
                            loginNum = rs.getInt("id");
                            loginName = rs.getString("m_Name");
                            break;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (loginIdCheck.equals(loginId) && loginPwCheck.equals(loginPw)) {

                    LoginStatus loginStatusA = new LoginStatus(loginNum, loginId, loginPw, loginName);
                    loginStatus = loginStatusA;

                    System.out.println("로그인이 확인되었습니다");
                    dbLoginCheck = true;
                    loginCount = 0;
                    break;
                } else if (loginIdCheck.equals(loginId) && !loginPwCheck.equals(loginPw)) {
                    System.out.println("비밀번호 입력오류");
                    loginCount++;
                } else {
                    System.out.println("아이디 또는 비밀번호를 확인해주세요");
                }
            }
        } else {
            System.out.println("로그아웃 후 이용해주세요");
        }
    }

    public void memberUpdate(Scanner sc) {
        String updatePw = "";
        String updatePwCheck = "";
        String name = "";

        if (dbLoginCheck == true) {
            System.out.printf("비밀번호 확인");
            String pW = sc.nextLine();
            if (pW.equals(loginStatus.getLoginPw())) {
                while (true) {
                    System.out.println("수정전 닉네임 : " + loginStatus.getLoginName());
                    System.out.printf("닉네임 : ");
                    name = sc.nextLine();
                    while (true) {
                        System.out.printf("수정할 패스워드 : ");
                        updatePw = sc.nextLine();
                        System.out.printf("수정할 패스워드 확인 : ");
                        updatePwCheck = sc.nextLine();
                        if (updatePw.equals("") && updatePwCheck.equals("")) {
                        } else if (updatePw.equals(updatePwCheck)) {
                            try {
                                String sql = "UPDATE members SET m_Pw = ? WHERE id = ?";
                                pstmt = conn.prepareStatement(sql);
                                pstmt.setString(1, updatePw);
                                pstmt.setInt(2, loginStatus.getLoginNum());
                                pstmt.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("비밀번호를 확인해주세요");
                            continue;
                        }
                        break;
                    }

                    if (name.equals("")) {
                    } else {
                        try {
                            String sql = "UPDATE members SET m_Name = ? WHERE id = ?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, name);
                            pstmt.setInt(2, loginStatus.getLoginNum());
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                System.out.println("회원정보가 수정되었습니다");

                if (!updatePw.equals("")) {
                    loginStatus.setLoginPw(updatePw);
                }
                if (!name.equals("")) {
                    loginStatus.setLoginName(name);
                }
            }
        } else {
            System.out.println("로그인 후 이용해주세요");
        }
    }

    public void logout() {
        loginStatus = null;
        dbLoginCheck = false;
        System.out.println("로그아웃 되었습니다");
    }
}

