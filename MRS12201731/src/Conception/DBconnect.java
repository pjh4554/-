package Conception;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Generate.G_Book;
import Generate.G_Member;
import Generate.G_Movie_Info;
import Generate.G_Opened_Movie;
public class DBconnect { // db 연결

      // final 상수
      private static final String URL = "jdbc:mysql://localhost:3306/MRS";
      private static final String USER = "root";
      private static final String PASSWORD = "1234";

      // DB 접근 변수
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      Scanner sc = new Scanner(System.in);

      public Connection getConnection() { // DB 접속

         Connection conn = null;

         try {
            // 1. Connection 획득
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
         } catch (Exception e) {
            System.out.println("DB 작업중 문제 발생 : " + e.getMessage());
         }
         return conn;
      }

      DBconnect() {
         conn = getConnection();
      }

      //////////////////////// 종현/////////////////////////////////////////////

      

      public ArrayList<G_Opened_Movie> opened_movie() { // 상영중인 영화 정보 받아오는 객체
         
         ArrayList<G_Opened_Movie> al_openedMovie = new ArrayList<>();
         
         try {
            conn = getConnection();
            String sql = "SELECT * FROM mrs.opened_mv";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
               String getTime = rs.getString("o_starttime");
               String getTheater = rs.getString("t_theater");
               String getTitle = rs.getString("mv_title");
               int getOpenSeat = rs.getInt("o_openseat");
               
               G_Opened_Movie om = new G_Opened_Movie(getOpenSeat, getTime, getTheater, getTitle);
               
               al_openedMovie.add(om);
            }
         } catch (Exception e) {
            System.out.println("List DB 작업중 문제 발생 : ");
            e.printStackTrace();
         }
         return al_openedMovie;
      }
      

      public void insert_address(String b_number, String m_phone, String o_starttime, String mv_title, String t_theater, String b_date, String b_bookseat) {
         
          try {
              String sql = "INSERT INTO book(b_number, m_phone, o_starttime, mv_title, t_theater, b_date, b_bookseat) VALUES(?, ?, ?, ?, ?, ?, ?)";
              pstmt = conn.prepareStatement(sql);
              pstmt.setString(1, b_number);
              pstmt.setString(2, m_phone);
              pstmt.setString(3, o_starttime);
              pstmt.setString(4, mv_title);
              pstmt.setString(5, t_theater);
              pstmt.setString(6, b_date);
              pstmt.setString(7, b_bookseat);
              pstmt.executeUpdate();
          } catch (Exception e) {
              System.out.println("DB 작업 중 문제 발생: " + e.getMessage());
              e.printStackTrace();
          }
      }
      
      
      public ArrayList<G_Book> dup_seat_check(String o_starttime, String mv_title, String t_theater) { // 중복 좌석 체크하는 함수

         ArrayList<G_Book> al_dupSeatCheck = new ArrayList<>();

         try {

            conn = getConnection();
            String sql = "SELECT * FROM MRS.book WHERE o_starttime like ? and mv_title like ? and t_theater like ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, o_starttime);
            pstmt.setString(2, mv_title);
            pstmt.setString(3, t_theater);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
               String bookSeat = rs.getString("b_bookseat");
               
               G_Book sbi = new G_Book(bookSeat);
               
               al_dupSeatCheck.add(sbi);
            }
         } catch (Exception e) {
            System.out.println("List DB 작업중 문제 발생 : ");
            e.printStackTrace();
         }
         return al_dupSeatCheck;
      }
      
      
      /////////////////////////////////////// 종현 -> 호진님꺼
      /////////////////////////////////////// 고쳐보기/////////////////////////
      
      public ArrayList<G_Book> view_book(String a) {

         ArrayList<G_Book> al_viewBook = new ArrayList<>();

         try {
            conn = getConnection();
            String sql = "SELECT * FROM mrs.book WHERE m_phone = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, a);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
               String getNumber = rs.getString("b_number");
               String getPhone = rs.getString("m_phone");
               String getStarttime = rs.getString("o_starttime");
               String getTitle = rs.getString("mv_title");
               String getTheater = rs.getString("t_theater");
               String getDate = rs.getString("b_date");
               String getBookseat = rs.getString("b_bookseat");
               
               G_Book vb = new G_Book(getNumber, getPhone, getStarttime, getTitle, getTheater, getDate, getBookseat);
               
               al_viewBook.add(vb);
            }
         } catch (Exception e) {
            System.out.println("List DB 작업중 문제 발생 : ");
            e.printStackTrace();
         }
         return al_viewBook;
      }
      
      
      public int find_open_seat(String a, String b, String c) { //좌석을 찾는 클래스
         int result = 0;
         
         try {
            conn = getConnection();
            String sql = "SELECT * FROM opened_mv WHERE o_starttime = ? AND mv_title = ? AND t_theater = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, a);
            pstmt.setString(2,b);
            pstmt.setString(3, c);
            rs=pstmt.executeQuery();
            while (rs.next()) {
                   result += rs.getInt("o_openseat");
               }
         } catch (Exception e) {
            System.out.println("List DB 작업중 문제 발생 : ");
            e.printStackTrace();
         }
         
         return result;
      }
      

      ////////////////////////////////////////////////////////////////////////////////////
      /////////////// 영훈/////////////////////////////////
      public boolean dup_seat_cancel(String b_number) {
         
            try {
               String sql = "SELECT * FROM mrs.book WHERE b_number = ?;";
               pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, b_number);
               rs = pstmt.executeQuery();
               
               if (rs.next() == true) { // sql에서 불러온 라인이 존재하면 실행
                  System.out.println("이미 예약된 좌석입니다. 다시 입력해주세요. > ");
                  return false;
               }
            } catch (Exception e) {
               System.out.println("List DB 작업중 문제 발생 : ");
               e.printStackTrace();
            }
            System.out.println("좌석 예매를 합니다. > ");
            return true;
         }

      
      
      public ArrayList<G_Movie_Info> find_movie_info() { // 영화 정보 찾는 객체
         
         ArrayList<G_Movie_Info> al_findMovieInfo = new ArrayList<>();

         try {
            conn = getConnection();
            String sql = "SELECT * FROM MRS.movie_info;";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
               String getTitle = rs.getString("mv_title");
               String getRunningtime = rs.getString("mv_runningtime");
               String getGenre = rs.getString("mv_genre");
               String getGrade = rs.getString("mv_grade");
               String getDirector = rs.getString("mv_director");
               String getOpenning = rs.getString("mv_openning");
               
               G_Movie_Info smi = new G_Movie_Info(getTitle, getRunningtime, getGenre, getGrade, getDirector, getOpenning);
               
               al_findMovieInfo.add(smi);
            }
         } catch (Exception e) {
            System.out.println("List DB 작업중 문제 발생 : ");
            e.printStackTrace();
         }
         return al_findMovieInfo;
      }
      
      
      
      public void view_open_seat(int o_openseat, String o_starttime, String t_theater, String mv_title) { // 빈좌석 확인하는 메서드

         try {
            conn = getConnection();
            String sql = "UPDATE opened_mv SET o_openseat = ? WHERE o_starttime = ? AND t_theater = ? AND mv_title = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, o_openseat);
            pstmt.setString(2, o_starttime);
            pstmt.setString(3, t_theater);
            pstmt.setString(4, mv_title);
            pstmt.executeUpdate();
         } catch (Exception e) {
            System.out.println("List DB 작업중 문제 발생 : ");
            e.printStackTrace();
         }
         
         
      }
      public void delete_book(String b_number) { // 삭제 후 DB에 적용
         
         try {
            String sql = "DELETE FROM mrs.book WHERE b_number = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, b_number);
            pstmt.executeUpdate();
            
         } catch (Exception e) {
            System.out.println("List DB 작업중 문제 발생 : ");
            e.printStackTrace();
         }
      }


      ///////////////////////////////////////////////////////////////////////////
      //////////////////////////// 구조 바꾸기//////////////////////////////////////

      // void ->boolean / return null
      public boolean DbUpdate(String sql) { // 삭제, 수정, 삽입시 사용하는 함수
         
         try {
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

         } catch (SQLException e) {
            System.out.println("DB작업중 문제발생: " + e.getMessage());
            e.printStackTrace();
            return false;
         }
         return true;

      }

      public ResultSet Dblistup(String sql) { // 검색, 선택하는 함수
         
         try {
            pstmt = conn.prepareStatement(sql,
                  ResultSet.TYPE_SCROLL_INSENSITIVE,
                  ResultSet.CONCUR_READ_ONLY);
            rs = pstmt.executeQuery();

         } catch (Exception e) {
            System.out.println("list DB 작업중 문제 발생!");
            e.printStackTrace();
            return null;
         }
         return rs;
      }
      /////////////////////////////////////////////////////////////////////
   }