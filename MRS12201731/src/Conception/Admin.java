package Conception;


import java.util.ArrayList;


import Generate.G_Member;
import Generate.G_Movie_Info;
import Generate.G_Opened_Movie;

public class Admin extends DBconnect {
   G_Member GMem = null;
   ArrayList<G_Member> arGMem = null;
   ArrayList<G_Movie_Info> arGMvInfo = null;
   ArrayList<G_Opened_Movie> arGOpen_mv = null;
   Prompt pt = new Prompt();
   Member mb;

   StringBuilder sb = null;

   String Line = "=".repeat(50);
   String Gubun = "-".repeat(110);

   public void Sign_Up_Admin() { // 관리자 등록
      try {
         mb = new Member();
         mb.sign_up(1);

      } catch (Exception ex) {
         System.out.println("관리자 등록 Error : " + ex.getMessage());
      }
   }
   
   public boolean Login_Admin() { // 관리자 로그인
      try {
         mb = new Member();

         System.out.print("관리자 아이디 : ");
         String id = sc.nextLine();
         System.out.print("관리자 비번 : ");
         String pw = sc.nextLine();

         GMem = mb.sign_in(id, pw, 1);

         if (GMem == null) {
            return false;
         } else if(GMem.getM_manager()) {
            System.out.println("관리자로 로그인 하였습니다.");
         }

      } catch (Exception ex) {
         System.out.println("관리자 로그인 Error : " + ex.getMessage());
         return false;
      }
      return true;
   }
   
   public void Find_Admin_Id() {  //관리자 아이디 찾기
      try {
         mb = new Member();
         
         pt.input_namephone();
         mb.find_id(pt.name, pt.phone, 1);

      } catch (Exception ex) {
         System.out.println("관리자 로그인 Error : " + ex.getMessage());
      }
   }
   
   public void Find_Admin_Pw() {   //관리자 비번 찾기
      try {
         mb = new Member();
         
         pt.input_idphone();
         mb.find_pw(pt.id, pt.phone, 1);

      } catch (Exception ex) {
         System.out.println("관리자 로그인 Error : " + ex.getMessage());
      }
   }
   
   public void Change_Admin_Pw() {  //관리자 비번 변경
      try {
         mb = new Member();
         
         pt.input_idpw();
         mb.change_pw(pt.id, pt.pw, 1);

      } catch (Exception ex) {
         System.out.println("관리자 로그인 Error : " + ex.getMessage());
      }
   }
   
   public void Del_Admin_Id() {   //관리자 삭제
      try {
         mb = new Member();
         
         pt.input_idpw();
         mb.del_id(pt.id, pt.pw, 1);

      } catch (Exception ex) {
         System.out.println("관리자 로그인 Error : " + ex.getMessage());
      }
   }
   
   public void View_mv() { // 영화 목록 보기
      try {
         sb = new StringBuilder();
         arGMvInfo = new ArrayList<>();
         sb.append("select * from movie_info");
         rs = Dblistup(sb.toString());

         if (rs.next() == false) {
            return;    //처음 영화 추가된게 없으면 넘어 갈 수 있도록 함
         }

         System.out.println(Line + " 영화 목록 " + Line + "\n");
         
         while (rs.next()) {
            String title = rs.getString("mv_title");
            String runningtime = rs.getString("mv_runningtime");
            String genre = rs.getString("mv_genre");
            String grade = rs.getString("mv_grade");
            String director = rs.getString("mv_director");
            String openning = rs.getString("mv_openning");

            G_Movie_Info mv1 = new G_Movie_Info(title, runningtime, genre, grade, director, openning);
            arGMvInfo.add(mv1);
         }

         System.out.printf("%-20s %-10s %-10s %-15s %-10s %-20s\n", "영화 제목", "러닝 타임", "장르", "등급", "감독", "개봉 날짜");
         System.out.println(Gubun);

         for (int i = 0; i < arGMvInfo.size(); i++) {

            System.out.printf("%-20s %-10s %-10s %-15s %-10s %-20s\n", arGMvInfo.get(i).getMv_title(),
                  arGMvInfo.get(i).getMv_runningtime(), arGMvInfo.get(i).getMv_genre(),
                  arGMvInfo.get(i).getMv_grade(), arGMvInfo.get(i).getMv_director(),
                  arGMvInfo.get(i).getMv_opening());
         }

         System.out.println();
      } catch (Exception ex) {
         System.out.println("영화 목록 불러오기 Error : " + ex.getMessage());
      }
   }

   public void Add_mv() { // 영화 추가
      try {
         View_mv();

         sb = new StringBuilder();

         String Line = "=";
         System.out.println(Line.repeat(80));

         System.out.print("추가할 영화 제목 : ");
         String title = sc.nextLine();
         
         if(Check_mv(title)) {
            System.out.println("이미 추가된 영화 입니다.\n 영화 정보를 다시 확인 하여 주세요.");
            return;
         }
         
         System.out.print("추가할 영화 러닝타임 : ");
         int runningtime = Integer.parseInt(sc.nextLine());
         System.out.print("추가할 영화 장르 : ");
         String genre = sc.nextLine();
         System.out.print("추가할 영화 등급(나이제한) : ");
         int grade = Integer.parseInt(sc.nextLine());
         System.out.print("추가할 영화 감독 : ");
         String director = sc.nextLine();
         System.out.print("추가할 영화 개봉날짜(ex 2023-12-20) : ");
         String openning = sc.nextLine();

         sb.append(
               "insert into movie_info(mv_title, mv_runningtime, mv_genre, mv_grade, mv_director, mv_openning) ");
         sb.append("values(" + "'" + title + "'" + "," + runningtime + "," + "'" + genre + "'" + "," + grade + ","
               + "'" + director + "'" + "," + "'" + openning); // + "')");

         if (DbUpdate(sb.toString())) {
            System.out.println("영화 정상 추가 되었습니다.\n");
            View_mv();
         }
      } catch (Exception ex) {
         System.out.println("영화 추가시 Error : " + ex.getMessage());
      }
   }
   
   public boolean Check_mv(String mv_title) {   // 영화 제목 있는지 체크
      try {
         sb = new StringBuilder();
         
         sb.append("select * from movie_info where mv_title = " + "'" + mv_title + "'");
         
         rs = Dblistup(sb.toString());

         if (rs.next() == false) {
            return false;
         }
         
      } catch (Exception ex) {
         System.out.println("영화 체크시 Error : " + ex.getMessage());
         return true;   //
      }
      return true;
   }
   
   public void Del_mv() { // 영화 삭제
      try {
         View_mv();

         sb = new StringBuilder();

         String Line = "=";
         System.out.println(Line.repeat(80));

         System.out.print("삭제할 영화 제목 : ");
         String title = sc.nextLine();
         
         if(!Check_mv(title)) {
            System.out.println("추가하지 않은 영화 정보를 삭제 할려고 합니다.\n영화 정보 확인 바랍니다.");
            return;
         }

         sb.append("delete from movie_info where mv_title = " + "'" + title + "'");

         if (DbUpdate(sb.toString())) {
            System.out.println("영화 정상 삭제 되었습니다.\n");
            View_mv();
         }
      } catch (Exception ex) {
         System.out.println("영화 삭제시 Error : " + ex.getMessage());
      }
   }

   public void List_mv() { // 상영 영화 조회
      try {
         sb = new StringBuilder();
         arGOpen_mv = new ArrayList<>();
         sb.append("select * from opened_mv");
         rs = Dblistup(sb.toString());

         if (rs.next() == false) {
            return;   //처음 상영 영화 등록된게 없으면 넘어 갈 수 있도록 함
         }

         System.out.println(Line + " 상영 영화 목록 " + Line + "\n");
         
         while (rs.next()) {
            String starttime = rs.getString("o_starttime");
            String title = rs.getString("mv_title");
            String theater = rs.getString("t_theater");
            int openseat = rs.getInt("o_openseat");

            G_Opened_Movie o1 = new G_Opened_Movie(openseat, starttime, title, theater);
            arGOpen_mv.add(o1);
         }

         System.out.printf("%-15s %-10s %-10s %-15s\n", "상영시작시간", "영화 제목", "상영관", "남은 좌석");
         System.out.println(Gubun);

         for (int i = 0; i < arGOpen_mv.size(); i++) {

            System.out.printf("%-15s %-10s %-10s %-15s\n", arGOpen_mv.get(i).getO_starttime(),
                  arGOpen_mv.get(i).getMv_title(), arGOpen_mv.get(i).getT_theater(),
                  arGOpen_mv.get(i).getO_openseat());
         }

         System.out.println();
      } catch (Exception ex) {
         System.out.println("상영 영화 목록 불러오기 Error : " + ex.getMessage());
      }
   }
   
   //상영 영화 등록시 시간 체크 함수
   /*public boolean CheckTime(String time, String theater) {   
      try {
         sb = new StringBuilder();
         
         sb.append("select * from opened_mv where o_starttime = " + "'" + time + "' ");
         sb.append("and t_theater = '" + theater + "'");
         
         rs = Dblistup(sb.toString());
   
         if (rs.next()) {   //
            return false;
         }
         
      } catch (Exception ex) {
         System.out.println("시간 체크시 Error : " + ex.getMessage());
         return false;
      }
      return true;
   }*/
   
   /*public void ListAdd_mv() { // 상영 영화 등록
      try {
         View_mv();
         List_mv();
   
         sb = new StringBuilder();
         String starttime = "";
         String title = "";
         String theater = "";
         int openseat = 0;
         int stime = 0;
         
         out:
         while(true) {
            System.out.print("추가할 상영영화 시작시간(08~22시까지만) : ");
            starttime = sc.nextLine();
            stime = Integer.parseInt(starttime);
            
            if(stime < 8 || stime > 22) {
               System.out.println("08~22시 사이를 입력 하여 주세요!\n");
               continue out;
            }
            
            starttime = starttime + "0000";  //0000붙여주는 이유는 DB에서 타입이 Time이기 때문에 시간으로 받기 위해서
            
            System.out.print("추가할 상영영화 제목 : ");
            title = sc.nextLine();
            
            if(!Check_mv(title)) {
               System.out.println("\n추가 되지 않은 영화 입니다.\n영화 정보 먼저 추가하고 등록 하여 주세요.\n");
               return;
            }
            
            System.out.print("추가할 상영영화 상영관 : ");
            theater = sc.nextLine();
            System.out.print("추가할 상영영화 좌석수 : ");
            openseat = sc.nextInt();
            break;
         }
   
         sb.append("insert into opened_mv(o_starttime, mv_title, t_theater, o_openseat) ");
         sb.append("values(" + "'" + starttime + "'" + "," + "'" + title + "'" + "," + "'" + theater + "'" + ","
               + openseat + ")");
   
         if (db.DbUpdate(sb.toString())) {
            System.out.println("상영영화 정상 추가 되었습니다.\n");
            List_mv();
         }
      } catch (Exception ex) {
         System.out.println("상영 영화 등록 Error : " + ex.getMessage());
      }
   }*/
}

