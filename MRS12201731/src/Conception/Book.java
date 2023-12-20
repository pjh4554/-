package Conception;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Generate.G_Book;
import Generate.G_Movie_Info;
import Generate.G_Opened_Movie;

public class Book extends DBconnect {

   Prompt pt = new Prompt();

   String[][] theaterA = { { " \\ ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 ", " 9 ", " 10 " },
         { " A ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " B ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " C ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " D ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " E ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " F ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " G ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " H ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " I ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " J ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " } };

   String[][] theaterB = { { " \\ ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 ", " 9 ", " 10 " },
         { " A ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " B ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " C ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " D ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " E ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " F ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " G ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " H ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " } };

   String[][] theaterC = { { " \\ ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 ", " 9 ", " 10 " },
         { " A ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " B ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " C ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " D ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " E ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " },
         { " F ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ ", " □ " } };

   int theaterA_length = 11;
   int theaterB_length = 9;
   int theaterC_length = 7;

   public G_Opened_Movie current_movie_info() { // 현재 상영 영화 정보

      int i = 1;
      for (G_Opened_Movie gom : opened_movie()) {
         System.out.printf("%d. %s\n", i, gom);
         i++;
      }

      System.out.println("원하시는 상영 시간을 선택해주세요");
      int movieStartTime = sc.nextInt();

      return opened_movie().get(movieStartTime - 1);
   }

   public void Book_Pt() { // 박스오피스

      System.out.println("====== < BOX OFFICE > ======");
      String sql = "SELECT mv_title,COUNT(*) AS 누적관객 FROM mrs.book GROUP BY mv_title order by 누적관객 desc;";
      ResultSet rs = null;
      rs = Dblistup(sql);

      try {

         int i = 0;
         while (rs.next()) {
            i++;
            String currentOpened = rs.getString("mv_title");
            int totalAttendance = rs.getInt("누적관객");
            System.out.printf("%d위 : %s : %d명\n", i, currentOpened, totalAttendance);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      System.out.println("============================");
      System.out.println();
   }

   //////////////// 새로 추가한것(영훈님꺼 보고필요한 기능을
   //////////////// 넣음)/////////////////////////////////////
   public void Book_Pt(int b) { // ??

      int i = 1;
      for (G_Movie_Info gmi : find_movie_info()) {
         System.out.printf("%s\n", gmi.getMv_title());
         i++;
      }
   }

   ////////////////////// 영훈님이 한것/////////////////////////////////
   public void view_mvinfo_pt(String mv_name) { // 입력한 영화의 정보 출력

      for (G_Movie_Info gmi : find_movie_info()) {
         if (mv_name.equals(gmi.getMv_title())) {
            System.out.printf("영화 제목 : %s\n러닝 타임 : %s\n장르 \t : %s\n등급 \t : %s\n감독 \t : %s\n개봉일  \t : %s\n", gmi.getMv_title(),
                  gmi.getMv_runningtime(), gmi.getMv_genre(), gmi.getMv_grade(), gmi.getMv_director(), gmi.getMv_opening());
         }
      }
   }

   public void close_seat(G_Opened_Movie G_opened_mv, String[][] A, int theaterA_length, String phone_number) { // 예약된 좌석 표시

      ArrayList<G_Book> al_gbook = new ArrayList<>();
      al_gbook = dup_seat_check(G_opened_mv.getO_starttime(), G_opened_mv.getMv_title(), G_opened_mv.getT_theater());

      for (G_Book gb : al_gbook) {
         String rowName = (gb.getB_bookseat()).substring(0, 1);
         int columnName = Integer.parseInt((gb.getB_bookseat()).substring(1));

         for (int i = 0; i < 11; i++) {
            if (A[i][0].trim().equals(rowName)) {
               A[i][columnName] = " ■ ";
            }
         }
      }

      for (int i = 0; i < theaterA_length; i++) {
         for (int j = 0; j < 11; j++) {

            System.out.print(A[i][j]);
         }
         System.out.println();
      }
      //////////////////////////////////////////////////////////////////////////////////////
      System.out.println();

      outer: while (true) {

         System.out.println("원하시는 행과 열을 골라주세요.ex>A 1");
         String row = sc.next();
         String column = sc.next();

         for (int i = 0; i < theaterA_length; i++) {
            if (row.equals(A[i][0].trim())) {

               for (int j = 0; j < 11; j++) {
                  if (column.equals(A[0][j].trim())) {
                     String reservationSeat = row + column;
                     StringBuilder reservationNumber = new StringBuilder();
                     String[] result = G_opened_mv.getO_starttime().split(":");
                     String newStartTime = result[0] + result[1];
                     reservationNumber.append(newStartTime).append(G_opened_mv.getT_theater()).append(reservationSeat);
                     String ReservationNumber = reservationNumber.toString();

                     if (!dup_seat_cancel(ReservationNumber)) {
                        continue outer;
                     }

                     insert_address(ReservationNumber, phone_number, G_opened_mv.getO_starttime(), G_opened_mv.getMv_title(),
                           G_opened_mv.getT_theater(), "2023-11-11", reservationSeat);
                     ///////////////////////////////////////////////////////////////////////////////////////////////////

                     int count = 0;
                     for (int t = 0; t < theaterA_length; t++) {
                        for (int q = 0; q < 11; q++) {
                           if (A[t][q].equals(" □ ")) {
                              count++;
                           }
                        }
                     }

                     view_open_seat(count - 1, G_opened_mv.getO_starttime(), G_opened_mv.getT_theater(), G_opened_mv.getMv_title());
                     return;
                  }
               }
            }

         }
         System.out.println("잘못된 좌석선택입니다.");
         continue outer;

      }
   }

   public void First_book_menu(String phone_number) { // 로그인시 출력 화면

      outer: while (true) {

         Book_Pt();
         System.out.println("1. 상영중인 영화");
         System.out.println("2. 예매");
         System.out.println("3. 예매 조회");
         System.out.println("4. 예매 취소");
         System.out.println();

         int selectMenu = sc.nextInt();

         switch (selectMenu) {

         case 1: // 상영중인 영화
            Book_Pt();

            System.out.println("영화정보를 보시겠습니까?\n1.네\n2.아니요");
            int viewMvInfo = sc.nextInt();

            switch (viewMvInfo) {
            case 1:
               System.out.println();
               Book_Pt(viewMvInfo);
               System.out.println("어떤 영화의 정보를 보시겠습니까?(영화제목을 입력하세요.)");
               sc.nextLine();

               String selectMv = sc.nextLine();
               System.out.println();

               view_mvinfo_pt(selectMv);

               continue outer;

            case 2:
               System.out.println("메뉴화면으로 돌아갑니다.");
               continue outer;
            }

         case 2: // 예매
            Book_Pt();

            System.out.println("예약하시겠습니까?\n1.네\n2.아니요");
            int selectBook = sc.nextInt();

            switch (selectBook) {

            case 1:
               System.out.println("몇자리를 예약하시겠습니까?");
               int howMuchBook = sc.nextInt();

               G_Opened_Movie G_opened_mv = current_movie_info();

               System.out.println("좌석을 선택해주세요");

               for (int k = 0; k < howMuchBook; k++) {
                  switch (G_opened_mv.getT_theater()) {

                  case "A":
                     close_seat(G_opened_mv, theaterA, theaterA_length, phone_number);
                     break;

                  case "B":
                     close_seat(G_opened_mv, theaterB, theaterB_length, phone_number);
                     break;

                  case "C":
                     close_seat(G_opened_mv, theaterC, theaterC_length, phone_number);
                     break;
                  }
               }

               System.out.println("메뉴화면으로 돌아갑니다.");
               continue outer;

            case 2:
               System.out.println("메뉴화면으로 돌아갑니다.");
               continue outer;
            }
            break;

         case 3: // 예매 조회
            for (G_Book G_book : view_book(phone_number)) {
               System.out.println("==  예매 내역 확인  == ");
               System.out.println("예매번호: " + G_book.getB_number());
               System.out.println("시간: " + G_book.getO_starttime());
               System.out.println("영화: " + G_book.getMv_title());
               System.out.println("상영관: " + G_book.getT_theater());
               System.out.println("상영시간: " + G_book.getB_date());
               System.out.println("예약좌석: " + G_book.getB_bookseat());
            }

            System.out.println("메뉴화면으로 돌아갑니다.");
            continue outer;

         case 4: // 예매 취소
            int i = 1;
            for (G_Book G_book : view_book(phone_number)) {

               System.out.printf("%d. %s %s %s %s %s %s\n", i, G_book.getB_number(), G_book.getO_starttime(), G_book.getMv_title(),
                     G_book.getT_theater(), G_book.getB_date(), G_book.getB_bookseat());
               i++;
            }

            System.out.println("삭제할 내역의 번호를 선택하세요");

            int delNumber = sc.nextInt();
            G_Book gbook = view_book(phone_number).get(delNumber - 1);
            int mf_openseat = 1 + find_open_seat(gbook.getO_starttime(), gbook.getMv_title(), gbook.getT_theater());
            
            view_open_seat(mf_openseat, gbook.getO_starttime(), gbook.getT_theater(), gbook.getMv_title());
            
            delete_book(gbook.getB_number());
            break;

         }
      }
   }
}