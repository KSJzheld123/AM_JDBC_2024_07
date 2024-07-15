package org.koreait;


import java.sql.*;
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
    static PreparedStatement pstmt = null;
    static ResultSet rs = null;
    static String idSearching = "";
    static boolean check = true;
    static boolean nullCheck = false;
    static LoginStatus loginStatus;
    static int idMatching = -1;

    public static void write(Scanner sc) {
        System.out.println("제목을 입력해주세요");
        String titleAdd = sc.nextLine().trim();
        String bodyAdd = "";
        if (titleAdd.length() == 0) {
            System.out.println("제목은 필수 입력 입니다");
            check = false;
        } else if (check == true) {
            System.out.println("내용을 입력해주세요");
            bodyAdd = sc.nextLine().trim();
            if (bodyAdd.length() == 0) {
                System.out.println("내용은 필수 입력 입니다");
                check = false;
            }
        }

        if (check == true) {
            try {
                String sql = "INSERT INTO `article` SET `regDate` = NOW(), `updateDate` =  NOW(), `title` = ?, `body` = ?, `w_name` = ?, `w_id` = ?";

                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, titleAdd);
                pstmt.setString(2, bodyAdd);
                pstmt.setString(3, loginStatus.getLoginName());
                pstmt.setInt(4, loginStatus.getLoginNum());

                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                String sql = "SELECT * FROM `article`";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    idSearching = rs.getString("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(idSearching + "번 게시글이 생성되었습니다");
        }
    }

    public static void list() {
        try {
            String sql = "SELECT * FROM `article` ORDER BY `id` DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("번호 ||             최초작성날짜              ||                작성날짜               ||   작성자   ||   제목    ||   내용");
            while (rs.next()) {
                int id = rs.getInt("id");
                String regDate = rs.getString("regDate");
                String updateDate = rs.getString("updateDate");
                String title = rs.getString("title");
                if (title.length() > 3) {
                    title = title.substring(0, 3);
                }
                String writer = rs.getString("w_name");
                if (writer.length() > 3) {
                    writer = writer.substring(0, 3);
                }
                String body = rs.getString("body");
                System.out.printf("%d   ||          %s          ||          %s          ||   %s   ||   %s   ||   %s   \n", id, regDate, updateDate, writer, title, body);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Scanner sc) {
        int getId = -1;
        String titleUpdate = "";
        String bodyUpdate = "";
        System.out.println("수정할 게시글의 id를 입력해주세요");
        System.out.printf("수정할 id : ");
        String searchId = sc.nextLine().trim();
        if (searchId.length() == 0) {
            nullCheck = true;
            check = false;
            System.out.println("id를 입력해주세요");
        }
        idSearching = searchId;
        idSearch();
        if (check == true) {
            try {
                String sql = "SELECT * FROM `article` WHERE `id` = ?";
                pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, Integer.parseInt(searchId));

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    titleUpdate = rs.getString("title");
                    bodyUpdate = rs.getString("body");
                    getId = rs.getInt("w_id");

                }
                if (titleUpdate.length() == 0) {
                    titleUpdate = rs.getString("title");
                }
                if (bodyUpdate.length() == 0) {
                    bodyUpdate = rs.getString("body");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (idMatching == getId) {
            System.out.println("기존 제목 : " + titleUpdate);
            System.out.printf("수정할 제목 : ");
            titleUpdate = sc.nextLine();
            System.out.println("기존 내용 : " + bodyUpdate);
            System.out.printf("수정할 내용 : ");
            bodyUpdate = sc.nextLine();
        } else {
            System.out.println(idSearching + "번글 작성자가 아닙니다");
            check = false;
            nullCheck = true;
        }

        if (check == true) {
            try {
                String sql = "UPDATE `article` SET `title` = ?, `body` = ?, `updateDate` = NOW() WHERE `id` = ?";

                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, titleUpdate);
                pstmt.setString(2, bodyUpdate);
                pstmt.setInt(3, Integer.parseInt(searchId));

                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(searchId + "번 게시글이 수정되었습니다");
        } else {
            if (check == false && nullCheck == false) {
                System.out.println(idSearching + "번 게시글은 없습니다");
            }
        }
    }

    public static void delete(Scanner sc) {
        int id = 0;
        String regDate = "";
        String updateDate = "";
        String title = "";
        String body = "";
        boolean deleteCheck = true;
        int getId = -1;
        System.out.println("삭제할 게시글의 id를 입력해주세요");
        System.out.printf("삭제할 id : ");
        String searchId = sc.nextLine().trim();
        if (searchId.length() == 0) {
            nullCheck = true;
            check = false;
            System.out.println("id를 입력해주세요");
        }
        idSearching = searchId;
        idSearch();

        try {
            String sql = "SELECT * FROM `article` WHERE `id` = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, Integer.parseInt(searchId));

            rs = pstmt.executeQuery();

                while (rs.next()) {
                    id = rs.getInt("id");
                    regDate = rs.getString("regDate");
                    updateDate = rs.getString("updateDate");
                    title = rs.getString("title");
                    body = rs.getString("body");
                    getId = rs.getInt("w_id");
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(loginStatus.getLoginNum() == getId) {
            System.out.println("===== 삭제할 게시글 =====");
            System.out.println("번호 : " + id);
            System.out.println("최초 작성 날짜 : " + regDate);
            System.out.println("최근 작성 날짜 : " + updateDate);
            System.out.println("제목 : " + title);
            System.out.println("내용 : " + body);
        } else {
            System.out.println(idSearching + "번글 작성자가 아닙니다");
            check = false;
            nullCheck = true;
        }

        if (check == true) {
            System.out.println("지우시겠습니까 y / n");
            String yN = sc.nextLine().trim();
            if (yN.length() == 0) {
                System.out.println("둘중하나 입력해주세요");
                check = false;
                deleteCheck = false;
            } else if (yN.equals("N")) {
                check = false;
                deleteCheck = false;
            } else if (yN.equals("y")) {
                check = true;
            } else {
                System.out.println("둘중하나 입력해주세요");
                check = false;
                deleteCheck = false;
            }
        }

        if (check == true) {
            try {
                String sql = "DELETE FROM `article` WHERE `id` = ?";

                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(searchId));
                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(searchId + "번 게시글이 삭제되었습니다");
        } else {
            if (check == false && nullCheck == false && deleteCheck == true) {
                System.out.println(idSearching + "번 게시글은 없습니다");
            }
        }
    }

    public static void detail(Scanner sc) {
        System.out.println("상세보기할 게시글의 id를 입력해주세요");
        System.out.printf("id : ");
        String searchId = sc.nextLine().trim();
        if (searchId.length() == 0) {
            nullCheck = true;
            check = false;
            System.out.println("id를 입력해주세요");
        }
        idSearching = searchId;
        idSearch();

        if (check == true) {
            try {
                String sql = "SELECT * FROM `article` WHERE `id` = ?";
                pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, Integer.parseInt(searchId));

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    System.out.println("번호 : " + rs.getInt("id"));
                    System.out.println("작성자 : " + rs.getString("w_name"));
                    System.out.println("최초 작성 날짜 : " + rs.getString("regDate"));
                    System.out.println("최근 작성 날짜 : " + rs.getString("updateDate"));
                    System.out.println("제목 : " + rs.getString("title"));
                    System.out.println("내용 : " + rs.getString("body"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (check == false && nullCheck == false) {
                System.out.println(idSearching + "번 게시글은 없습니다");
            }
        }
    }

    // 실행
    public static void main(String[] args) {
        Member mJ = new Member();
        Scanner sc = new Scanner(System.in);
        conn = DBConnection.getConnection();

        while (true) {

            loginStatus = Member.loginStatus;
            try {
                idMatching = Member.loginStatus.getLoginNum();
            } catch (NullPointerException e) {
                idMatching = -1;
            }

            boolean goBack = true;
            System.out.println("1. 로그인 관련 2. 게시글 관련");
            System.out.println("exit. 나가기");
            System.out.printf("명령어 ) ");
            String input = sc.nextLine();
            if (input.equals("1")) {

                while (goBack == true) {
                    System.out.println("1. 회원가입 || 2. 로그인 || 3. 로그인상태 || 4. 회원정보 수정");
                    System.out.println("8. 로그아웃");
                    System.out.println("9. 메인 메뉴");
                    System.out.printf("명령어 ) ");
                    input = sc.nextLine();

                    if (input.equals("9")) {
                        goBack = false;
                    }

                    try {
                        switch (Integer.parseInt(input)) {
                            case 1:
                                mJ.memberJoin(sc);
                                break;
                            case 2:
                                mJ.login(sc);
                                break;
                            case 3:
                                mJ.memberStatus();
                                break;
                            case 4:
                                mJ.memberUpdate(sc);
                                break;
                            case 8:
                                mJ.logout();
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("제대로 입력해주세요");
                    }
                }

            } else if (input.equals("2")) {
                check = true;
                nullCheck = false;
                System.out.println("1. 게시글 작성     || 2. 게시글 리스트 || 3. 게시글 수정 || 4. 게시글 삭제");
                System.out.println("5. 게시글 상세보기 || 8. 로그아웃");
                System.out.println("9. 메인 메뉴");
                System.out.printf("명령어 ) ");
                input = sc.nextLine();

                if (input.equals("9")) {
                    goBack = false;
                }

                if (Member.dbLoginCheck == true && goBack == true) {
                    try {
                        switch (Integer.parseInt(input)) {
                            case 1:
                                write(sc);
                                break;
                            case 2:
                                list();
                                break;
                            case 3:
                                update(sc);
                                break;
                            case 4:
                                delete(sc);
                                break;
                            case 5:
                                detail(sc);
                                break;
                            case 8:
                                mJ.logout();
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("제대로 입력해주세요");
                    }
                } else {
                    System.out.println("로그인 후 이용해 주세요");
                }
            } else if (input.equals("exit")) {
                sc.close();
                try {
                    pstmt.close();
                    rs.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //
    public static void idSearch() {
        boolean searching = false;
        String sql = "SELECT * FROM `article` WHERE `id`";
        int idSearchingInt = 0;

        try {
            idSearchingInt = Integer.parseInt(idSearching);
        } catch (NumberFormatException e) {
            check = false;
        }

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("id") == idSearchingInt) {
                    searching = true;
                }
            }

            if (searching == false) {
                check = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
