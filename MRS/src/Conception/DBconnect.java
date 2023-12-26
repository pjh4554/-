package Conception;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;


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
      
   }