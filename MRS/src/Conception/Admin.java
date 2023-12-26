package Conception;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Generate.G_Member;
import Generate.G_Movie_Info;
import Generate.G_Opened_Movie;

public class Admin extends DBconnect {

   G_Member gMem = null;
   ArrayList<G_Movie_Info> alGmvInfo = null;
   ArrayList<G_Opened_Movie> alGopenMv = null;
   ArrayList<String> alOpenedTime = null;
   HashMap<String, String> hmOpenedTime = null;

   Prompt pt = new Prompt();
   Member mb = null;

   StringBuilder sb = null;

   int mvRunningTime = 0;
   String line = "=".repeat(50);
   String gubun = "-".repeat(110);

   public void sign_up_admin() { // 관리자 등록
      try {
         mb = new Member();
         mb.sign_up(1);

      } catch (Exception ex) {
         System.out.println("관리자 등록 Error : " + ex.getMessage());
      }
   }

   public boolean login_admin() { // 관리자 로그인
      try {
         mb = new Member();
         System.out.print("  관리자 ID : ");
         String id = sc.nextLine();
         System.out.print("  관리자 PW : ");
         String pw = sc.nextLine();

         gMem = mb.sign_in(id, pw, 1);

         if (gMem == null) {
            System.out.println("\n관리자 ID 및 PW가 맞지 않습니다\n다시 확인 바랍니다\n");
            return false;
         } else if (gMem.getM_manager()) {
            System.out.println("\n관리자로 로그인 하였습니다.");
         }

      } catch (Exception ex) {
         System.out.println("관리자 로그인 Error : " + ex.getMessage());
         return false;
      }
      return true;
   }

   public void find_admin_id() { // 관리자 ID 찾기
      try {
         mb = new Member();

         pt.input_namephone();
         mb.find_id(pt.name, pt.phone, 1);

      } catch (Exception ex) {
         System.out.println("관리자 ID 찾기 Error : " + ex.getMessage());
      }
   }

   public void find_admin_pw() { // 관리자 PW 찾기
      try {
         mb = new Member();

         pt.input_idphone();
         mb.find_pw(pt.id, pt.phone, 1);

      } catch (Exception ex) {
         System.out.println("관리자 PW 찾기 Error : " + ex.getMessage());
      }
   }

   public void change_admin_pw() { // 관리자 PW 변경
      try {
         mb = new Member();

         pt.input_idpw();
         mb.change_pw(pt.id, pt.pw, 1);

      } catch (Exception ex) {
         System.out.println("관리자 PW 변경 Error : " + ex.getMessage());
      }
   }

   public void del_admin_id() { // 관리자 삭제
      try {
         mb = new Member();

         pt.input_idpw();
         mb.del_id(pt.id, pt.pw, 1);

      } catch (Exception ex) {
         System.out.println("관리자 삭제 Error : " + ex.getMessage());
      }
   }

   public void view_mv() { // 영화 목록 보기
      try {
         sb = new StringBuilder();
         alGmvInfo = new ArrayList<>();
         sb.append("select * from movie_info");
         rs = Dblistup(sb.toString());

         if (rs.next() == false) {
            return; // 처음 영화 추가된게 없으면 넘어 갈 수 있도록 함
         }

         rs.beforeFirst();

         System.out.println(line + " 영화 목록 " + line + "\n");

         while (rs.next()) {
            String title = rs.getString("mv_title");
            String runningtime = rs.getString("mv_runningtime");
            String genre = rs.getString("mv_genre");
            String grade = rs.getString("mv_grade");
            String director = rs.getString("mv_director");
            String openning = rs.getString("mv_openning");

            G_Movie_Info mv1 = new G_Movie_Info(title, runningtime, genre, grade, director, openning);
            alGmvInfo.add(mv1);
         }

         System.out.printf("%-25s\t%-10s\t%-10s\t%-15s\t%-10s\t%-20s\n", "영화 제목", "러닝 타임", "장르", "등급", "감독", "개봉 날짜");
         System.out.println(gubun);

         for (int i = 0; i < alGmvInfo.size(); i++) {

            System.out.printf("%-25s\t%-10s\t%-10s\t%-15s\t%-10s\t%-20s\n", alGmvInfo.get(i).getMv_title(),
                  alGmvInfo.get(i).getMv_runningtime(), alGmvInfo.get(i).getMv_genre(),
                  alGmvInfo.get(i).getMv_grade(), alGmvInfo.get(i).getMv_director(),
                  alGmvInfo.get(i).getMv_opening());
         }

         sb.setLength(0); // append했던 문자열 남지 않도록 초기화

         System.out.println();
      } catch (Exception ex) {
         System.out.println("영화 목록 불러오기 Error : " + ex.getMessage());
      }
   }

   public void add_mv() { // 영화 추가
      try {
         view_mv();

         sb = new StringBuilder();

         String Line = "=";
         System.out.println(Line.repeat(110));

         System.out.print("추가할 영화 제목 : ");
         String title = sc.nextLine();

         if (check_mv(title)) {
            System.out.println("이미 추가된 영화 입니다.\n영화 정보를 다시 확인 하여 주세요.\n");
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

         sb.setLength(0);
         sb.append(
               "insert into movie_info(mv_title, mv_runningtime, mv_genre, mv_grade, mv_director, mv_openning) ");
         sb.append("values(" + "'" + title + "'" + "," + runningtime + "," + "'" + genre + "'" + "," + grade + ","
               + "'" + director + "'" + "," + "'" + openning + "')");

         if (DbUpdate(sb.toString())) {
            System.out.println("영화 정상 추가 되었습니다.\n");
            view_mv();
         }

         sb.setLength(0); // append했던 문자열 남지 않도록 초기화

      } catch (Exception ex) {
         System.out.println("영화 추가시 Error : " + ex.getMessage());
      }
   }

   public boolean book_confirm(String mvTitle) { // 예매가 있는지 확인하는 함수
      try {
         sb = new StringBuilder();

         sb.append("select * from book where mv_title = '" + mvTitle + "'");

         rs = Dblistup(sb.toString());

         if (rs.next() == false) {
            return false;
         }

         return true;

      } catch (Exception ex) {
         System.out.println("예매 확인시 Error : " + ex.getMessage());
         return false;
      }
   }

   public boolean check_mv(String mv_title) { // 영화 제목 있는지 체크
      try {
         sb = new StringBuilder();

         sb.append("select * from movie_info where mv_title = " + "'" + mv_title + "'");

         rs = Dblistup(sb.toString());

         if (rs.next() == false) {
            return false;
         }

         return true;

      } catch (Exception ex) {
         System.out.println("영화 체크시 Error : " + ex.getMessage());
         return false;
      }
   }

   public void del_mv() { // 영화 삭제
      try {
         view_mv();

         sb = new StringBuilder();

         String Line = "=";
         System.out.println(Line.repeat(110));

         System.out.print("삭제할 영화 제목 : ");
         String title = sc.nextLine();

         if (!check_mv(title)) {
            System.out.println("추가하지 않은 영화 정보를 삭제 할려고 합니다.\n영화 정보 확인 바랍니다.\n");
            return;
         }

         if (book_confirm(title)) {
            System.out.println("\n예매 정보가 있습니다.\n영화 정보 삭제 할 수 없습니다.\n");
            return;
         }

         sb.setLength(0); // append했던 문자열 지우기 위해 초기화
         sb.append("delete from opened_mv where mv_title = " + "'" + title + "'");

         if (DbUpdate(sb.toString())) {
            sb.setLength(0); // append했던 문자열 지우기 위해 초기화
            sb.append("delete from movie_info where mv_title = " + "'" + title + "'");

            if (DbUpdate(sb.toString())) {

               System.out.println("영화 정상 삭제 되었습니다.\n");
               view_mv();
            }
         }

      } catch (Exception ex) {
         System.out.println("영화 삭제시 Error : " + ex.getMessage());
      }
   }

   public void view_opened_mv() { // 상영 영화 조회
      try {
         sb = new StringBuilder();
         alGopenMv = new ArrayList<>();
         String theaterTemp = "";
         
         sb.append(
               "select DATE_FORMAT(o_starttime, '%H:%i') AS starttime, mv_title, t_theater, o_openseat from opened_mv order by t_theater,o_starttime");
         rs = Dblistup(sb.toString());

         if (rs.next() == false) {
            return; // 처음 상영 영화 등록된게 없으면 넘어 갈 수 있도록 함
         }

         rs.beforeFirst();
         System.out.println(line + " 상영 영화 목록 " + line + "\n");

         while (rs.next()) {
            String starttime = rs.getString("starttime");
            String title = rs.getString("mv_title");
            String theater = rs.getString("t_theater");
            int openseat = rs.getInt("o_openseat");

            G_Opened_Movie o1 = new G_Opened_Movie(openseat, starttime, theater, title);
            alGopenMv.add(o1);
         }

         System.out.printf("%s\t%s\t%s\n", "상영시간", "상영관", "영화(남은좌석)");
         System.out.println(gubun);

         for (int i = 0; i < alGopenMv.size(); i++) {
            if( i == 0) {                                 //상영관 별로 구분짓기 위해 처음 상영관 저장
               theaterTemp = alGopenMv.get(i).getT_theater();
            }
            
            if(!theaterTemp.equals(alGopenMv.get(i).getT_theater())) {    //상영관이 다를때마다 '-'으로 구분 표시
               System.out.println(gubun);
               theaterTemp = alGopenMv.get(i).getT_theater();
            }
            
            System.out.println(alGopenMv.get(i).toString());
         }

         System.out.println();
      } catch (Exception ex) {
         System.out.println("상영 영화 목록 불러오기 Error : " + ex.getMessage());
      }
   }

   public int get_seat_cnt(String theater) { // 좌석 가져오기
      try {
         sb = new StringBuilder();

         sb.append(String.format("select t_seat from theater where t_theater = '%s'", theater));

         rs = Dblistup(sb.toString());

         if (rs.next()) {
            int cnt = rs.getInt("t_seat");
            return cnt;
         }

         sb.setLength(0); // append했던 문자열 남지 않도록 초기화

         return 0;
      } catch (Exception ex) {
         System.out.println("좌석 가져오기 Error : " + ex.getMessage());
         return 0;
      }
   }

   // 상영 가능한 시간 가져오기
   public ArrayList<String> get_opened_time(HashMap<String, String> hmopendtime, int runningtime) {
      try {
         LocalTime starttime;
         LocalTime endtime;
         LocalTime time;
         int k = 0; // 단순 순번 가져오기 위한 변수
         alOpenedTime = new ArrayList<String>();

         // 시간리스트(7시~22시로 설정함)
         ArrayList<Integer> arTime = new ArrayList<>(
               List.of(7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22));

         ArrayList<Integer> resultList = new ArrayList<>(arTime);

         if (hmopendtime == null) {
            // 등록된 상영시간이 없으면 7~22시까지 전체 보여지도록 함
            for (int i : resultList) {
               k++;
               time = LocalTime.of(i, 0);
               System.out.printf("%d[%s]\n", k, time);
               alOpenedTime.add(time.toString());
            }
         } else {
            // 등록된 상영시간을 제외하여 등록할 수 있는 상영시간이 보여지도록 함
            for (String key : hmopendtime.keySet()) {
               starttime = LocalTime.parse(key); // 등록했던 상영시작시간 가져옴
               endtime = LocalTime.parse(hmopendtime.get(key)); // 상영시작시간에서 runningtime을 더한 상영종료시간 가져옴

               // endtime의 자정시간이 오면 시간 비교시 무조건 작기 때문에 23시 59분으로 Setting
               // 7시로 설정한것은 시간리스트(7~22)에서 첫 시간이기 때문
               if (endtime.isBefore(LocalTime.of(7, 0))) {
                  endtime = LocalTime.of(23, 59);
               }

               for (int i = 0; i < resultList.size(); i++) {
                  time = LocalTime.of(resultList.get(i), 0);

                  // 시간리스트(7~22)에서 가져온 시간 보다 starttime에서 runningtime시간 뺀 시간이 과거이고
                  // 시간리스트(7~22)에서 가져온 시간 보다 endtime시간이 미래이면 해당 시간 시간리스트(7~22)에서 제거
                  // ex)starttime - 09:00, endtime - 11:00, runningtime : 120
                  // 시간 리스트에서 10을 가져오면 7<10 && 11>10 이므로 10 제거
                  if ((starttime.minusMinutes(runningtime).isBefore(time) && endtime.isAfter(time))) {
                     resultList.remove(i);
                     i--;
                  }
               }
            }
         }

         for (int i : resultList) {
            k++;
            time = LocalTime.of(i, 0);
            System.out.printf("%d.[%s]\n", k, time);
            alOpenedTime.add(time.toString());
         }

         return alOpenedTime;

      } catch (Exception ex) {
         System.out.println("상영영화 시간 가져오기 Error : " + ex.getMessage());
         return null;
      }
   }

   // 상영 영화 등록시 시간 체크 함수
   public ArrayList<String> check_time(String mvtitle, String theater) {
      try {
         hmOpenedTime = new HashMap<String, String>();
         alOpenedTime = new ArrayList<String>();
         sb = new StringBuilder();

         // 영화 정보에서 runningtime 가져오기
         sb.append(String.format("select mv_runningtime from movie_info where mv_title = '%s'", mvtitle));
         rs = Dblistup(sb.toString());

         if (rs.next()) {
            mvRunningTime = rs.getInt("mv_runningtime");
         }

         sb.setLength(0); // append했던 문자열 지우기 위해 초기화

         // 상영시작시간과 상영종료시간을 가져옴
         sb.append(
               "select DATE_FORMAT(a.o_starttime, '%H:%i:%s') as starttime, DATE_FORMAT(date_add(o_starttime, interval (b.mv_runningtime) minute),'%H:%i:%s') as endtime, a.mv_title, b.mv_runningtime\n");
         sb.append("from opened_mv a, movie_info b\n");
         sb.append("where true\n");
         sb.append("and a.t_theater = '" + theater + "'\n");
         sb.append("and b.mv_title = a.mv_title");

         rs = Dblistup(sb.toString());

         if (rs == null) {
            alOpenedTime = get_opened_time(null, mvRunningTime);
            return alOpenedTime;
         }

         while (rs.next()) {
            hmOpenedTime.put(rs.getString("starttime"), rs.getString("endtime"));
         }

         alOpenedTime = get_opened_time(hmOpenedTime, mvRunningTime);
         return alOpenedTime;

      } catch (Exception ex) {
         System.out.println("상영시간 체크시 Error : " + ex.getMessage());
         return null;
      }
   }

   public void add_opened_mv() { // 상영 영화 등록
      try {
         view_mv();
         view_opened_mv();

         alOpenedTime = new ArrayList<String>();
         sb = new StringBuilder();

         System.out.print("추가할 상영영화 제목 : ");
         String title = sc.nextLine();

         if (!check_mv(title)) {
            System.out.println("\n추가 되지 않은 영화 입니다.\n영화 정보 먼저 추가하고 등록 하여 주세요.\n");
            return;
         }

         System.out.print("추가할 상영영화 상영관 : ");
         String theater = sc.nextLine();

         alOpenedTime = check_time(title, theater);

         if (alOpenedTime.size() == 0) {
            System.out.println("추가할 상영영화 시간이 없습니다.\n");
            return;
         }

         System.out.print("추가할 상영영화 시간(번호선택) : ");
         int num = sc.nextInt();
         sc.nextLine(); // 상영 영화 등록 종료후 개행 버퍼가 남을 수 있어 개행 버퍼 제거
         String starttime = alOpenedTime.get(num - 1).replace(":", "") + "00"; // "00"을 붙여주는 이유는 시간을 만들기 위해
         // ex)140000 -> 이렇게 해야 DB에서 시간으로 입력 되기 때문
         int openseat = get_seat_cnt(theater);

         sb.setLength(0); // append했던 문자열 지우기 위해 초기화
         sb.append("insert into opened_mv(o_starttime, mv_title, t_theater, o_openseat) ");
         sb.append("values(" + "'" + starttime + "'" + "," + "'" + title + "'" + "," + "'" + theater + "'" + ","
               + openseat + ")");

         if (DbUpdate(sb.toString())) {
            System.out.println("상영영화 정상 추가 되었습니다.\n");
            view_opened_mv();
         }

      } catch (Exception ex) {
         System.out.println("상영 영화 등록 Error : " + ex.getMessage());
      }
   }
}