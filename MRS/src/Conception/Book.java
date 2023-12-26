package Conception;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

	int theaterA_length = 11;// 상영관마다 길이가 다르기때문에 상영관의 길이를 정해놓은 변수
	int theaterB_length = 9;// 상영관마다 길이가 다르기때문에 상영관의 길이를 정해놓은 변수
	int theaterC_length = 7;// 상영관마다 길이가 다르기때문에 상영관의 길이를 정해놓은 변수

	LocalDateTime date = LocalDateTime.now();

	public G_Opened_Movie current_movie_info() { // 현재 상영 영화 정보

		int i = 1;
		String theater = "";

		System.out.println("      ");
		System.out.println("  상영시작시간	상영관\t영화제목(빈좌석수)");
		System.out.println("==============================================");
		for (G_Opened_Movie gom : opened_movie()) {// 상영하는 영화를 리스트화하고 번호를 줘서 인덱스
													// 찾기 쉽게만듬
			if (i == 1) {
				theater = gom.getT_theater(); // 상영관 별로 구분짓기 위해 처음 상영관 저장
			}

			if (!theater.equals(gom.getT_theater())) { // 상영관이 다를때마다 '-'으로 구분 표시
				theater = gom.getT_theater();
				System.out.println("----------------------------------------------");

			}

			System.out.printf("%2d. %s\n", i, gom);
			i++;
		}
		outer22: while (true) {
			System.out.print("\n  원하시는 상영시간을 선택해주세요. > ");
			
			int movieStartTime = pt.input_error();
			System.out.println();
			if (movieStartTime > opened_movie().size()) {
				// 예매 번호 넘어갔을떄 텍스트 출력 어케함?
				System.out.println("잘못된 번호입니다. 다시입력해주세요.");
				continue outer22;
			} else {
				// 위에서 인덱스를 찾았으니 그 인덱스에서 -1한 값이 내가 선택한 위치
				return opened_movie().get(movieStartTime - 1);
			}
		}
	}

	public void Book_Pt() { // 박스오피스

		System.out.println("\n\n======= < BOX OFFICE > =======\n");
		String sql = "SELECT mv_title,COUNT(*) AS 누적관객 FROM mrs.book GROUP BY mv_title order by 누적관객 desc;";// 조건을
																											// 걸어서
																											// 영화
																											// 관객의
																											// 카운트를
																											// 세서순위를
																											// 매김
		ResultSet rs = null;
		rs = Dblistup(sql);

		try {

			int i = 0;
			while (rs.next()) {
				i++;
				String currentOpened = rs.getString("mv_title");
				int totalAttendance = rs.getInt("누적관객");
				System.out.printf("  %d위  %-10s\t: %d명\n", i, currentOpened,
		                  totalAttendance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("\n==============================\n");

		System.out.println("     ✦");
		System.out.println("     │     어서오세요 ");
		System.out.println("     │  육빔 시네마입니다.");
		System.out.println("     │");
		System.out.println("     ╰─────────────────🥀\n");

		System.out.println();
	}

	public void current_mv() { // 영화를 선택하는데, 영화의 제목을 입력해야함
		System.out.println("\n======= 현재 상영작 =======\n");
		for (G_Movie_Info gmi : find_movie_info()) {
			System.out.printf("      %s\n", gmi.getMv_title());
		}
		System.out.println("\n========================\n");
	}

	public void view_mvinfo_pt(String mv_name) { 
		// 입력한 영화의 정보 출력

		for (G_Movie_Info gmi : find_movie_info()) {
			if (mv_name.equals(gmi.getMv_title())) {
				System.out.println("========= 영화 정보 ============\n");
				System.out.printf(
						"	영화제목	: %s\n	러닝타임	: %s\n	장르	: %s\n	등급	: %s\n	감독	: %s\n	개봉일	: %s\n",
						gmi.getMv_title(), gmi.getMv_runningtime(), gmi.getMv_genre(), gmi.getMv_grade(),
						gmi.getMv_director(), gmi.getMv_opening());
				System.out.println();
				System.out.println("=============================\n\n");
			}
		}
	}

	public void close_seat(G_Opened_Movie G_opened_mv, String[][] A, int theaterA_length, String phone_number) { // 예약된
		// 좌석
		// 표시

		ArrayList<G_Book> al_gbook = new ArrayList<>();
		al_gbook = dup_seat_check(G_opened_mv.getO_starttime(), G_opened_mv.getMv_title(), G_opened_mv.getT_theater());// 좌석의
		// 정보를
		// 불러옴

		for (G_Book gb : al_gbook) {// 좌석정보를 불러와서 파싱한다음 그 좌석을을 검은색 네모로 표시
			String rowName = (gb.getB_bookseat()).substring(0, 1);
			int columnName = Integer.parseInt((gb.getB_bookseat()).substring(1));
			for (int i = 0; i < theaterA_length; i++) {
				if (A[i][0].trim().equals(rowName)) {// 위의 극장을 보면 이중배열인데, 요소들안에
														// 공백이 포함되있어서 trim을 사용
					A[i][columnName] = " ■ ";
				}
			}
		}
		System.out.println("=========================================");
		System.out.println("\t\t  Screen  \t\t");
		for (int i = 0; i < theaterA_length; i++) {
			for (int j = 0; j < 11; j++) {
				if (j == 4) {
					System.out.print("｜｜");
				}
				if (j == 8) {
					System.out.print("｜｜");
				}
				System.out.print(A[i][j]);
			}
			System.out.println();

		}

		System.out.println("\t\t\t\t     Exit");
		System.out.println("====================================[ ↓ ]");
		//////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////
		System.out.println();

		outer: while (true) {

			System.out.print("원하시는 행과 열을 입력해주세요. > ");
			String rowcolumn = sc.next();
			String row = rowcolumn.substring(0, 1).toUpperCase();
			String column = rowcolumn.substring(1);

			for (int i = 0; i < theaterA_length; i++) {// 위의 극장을 보면 이중배열인데,
														// 요소들안에 공백이 포함되있어서
														// trim을 사용
				if (row.equals(A[i][0].trim())) {
					for (int j = 0; j < 11; j++) {
						if (column.equals(A[0][j].trim())) {
							if (Integer.parseInt(column) < 10) {
								column = "0" + column;
							}
							String reservationSeat = row + column;
							StringBuilder reservationNumber = new StringBuilder();// StringBuilder를
																					// 사용해서
																					// 예약번호를
																					// 만듬
							String[] result = G_opened_mv.getO_starttime().split(":");// 시간이 time이여서 ":"가 있기 때문에 ":"를
																						// 없앰
							String newStartTime = result[0] + result[1];
							reservationNumber.append(newStartTime).append(G_opened_mv.getT_theater())
									.append(reservationSeat);// 이정보들로 예약정보를 만듬
							String ReservationNumber = reservationNumber.toString();

							if (!dup_seat_cancel(ReservationNumber)) {// 예약번호가
																		// 존재하면
																		// 예약이
																		// 불가능하니까
																		// 돌아가게함
								continue outer;
							}

							insert_book(ReservationNumber, phone_number, G_opened_mv.getO_starttime(),
									G_opened_mv.getMv_title(), G_opened_mv.getT_theater(), date.toString(),
									reservationSeat);// 예약과 관련된 정보를 넣음
							///////////////////////////////////////////////////////////////////////////////////////////////////

							int count = 0;
							for (int t = 0; t < theaterA_length; t++) {
								for (int q = 0; q < 11; q++) {
									if (A[t][q].equals(" □ ")) {
										count++;
									}
								}
							}

							view_open_seat(count - 1, G_opened_mv.getO_starttime(), G_opened_mv.getT_theater(),
									G_opened_mv.getMv_title());// 현재 좌석을
																// 수정함
							return;
						}
					}
				}

			}
			System.out.println("\n잘못된 좌석선택입니다.");
			continue outer;

		}
	}

	public void first_book_menu(String phone_number) { // 로그인시 출력 화면

		outer: while (true) {

//			Book_Pt();
			System.out.println("======= 예매하기 =======\n");
			System.out.println("    1. 상영중인 영화");
			System.out.println("    2. 예매");
			System.out.println("    3. 예매 조회");
			System.out.println("    4. 예매 취소");
			System.out.println("    5. 나가기");
			System.out.print("\n  번호를 입력해주세요. > ");
			

			int selectMenu = pt.input_error();
			
			System.out.println();

			switch (selectMenu) {

			case 1: // 상영중인 영화
				current_mv();// 영화순위

				System.out.println("= 현재 상영작에 대한 정보를 보시겠습니까? =\n\n\t1. 네\n\t2. 아니요\n");
				System.out.print("  번호를 입력해주세요. > ");

				int viewMvInfo = pt.input_error();
				//개행 추가

				switch (viewMvInfo) {
				case 1:
					System.out.println();
					System.out.println("\n어떤 영화의 정보를 보시겠습니까?(영화제목을 입력하세요.)"); // 영화를 선택하는데, 영화의 제목을 입력해야함
					System.out.print("영화제목 > ");

					String selectMv = sc.nextLine();
					System.out.println();
					view_mvinfo_pt(selectMv);// 영화제목을 입력하면 영화정보가 나옴
					continue outer;

				case 2:
					System.out.println("\n메뉴화면으로 돌아갑니다.\n");
					continue outer;

				default :
					System.out.println("\n잘못된 입력입니다 다시 입력해주세요");
					sc.nextLine();
					continue outer;
				}

			case 2: // 예매
				current_mv();
				System.out.println("== 예매하시겠습니까? == \n\n      1. 네\n      2. 아니요\n");
				System.out.print("  번호를 입력해주세요. > ");
				System.out.println();
				int selectBook = pt.input_error();

				switch (selectBook) {

				case 1:
					System.out.println("= 몇 자리를 예약하시겠습니까? =");
					System.out.print("\n  인원수를 입력해주세요. > ");
					
					int howMuchBook = pt.input_error();
					G_Opened_Movie G_opened_mv = current_movie_info();// 현재
																		// 영화와
																		// 좌석수를
																		// 보여줌
					System.out.println("\t  == 좌석을 선택해주세요 ==");

					for (int k = 0; k < howMuchBook; k++) {
						switch (G_opened_mv.getT_theater()) {

						case "A":
							close_seat(G_opened_mv, theaterA, theaterA_length, phone_number);// 예약을
																								// 함
							break;

						case "B":
							close_seat(G_opened_mv, theaterB, theaterB_length, phone_number);// 예약을
																								// 함
							break;

						case "C":
							close_seat(G_opened_mv, theaterC, theaterC_length, phone_number);// 예약을
																								// 함
							break;
						}
					}

					System.out.println("메뉴화면으로 돌아갑니다.\n");
					continue outer;

				case 2:
					System.out.println("메뉴화면으로 돌아갑니다.\n");
					continue outer;
				default :
					System.out.println("\n잘못된 입력입니다 다시 입력해주세요");
					sc.nextLine();
					break ;
				}
				break;

			case 3: // 예매 조회
				System.out.println("\t\t\t\t==  예매 내역 확인  == ");
				System.out.println(
						"==============================================================================================");
				System.out.println(" 예매번호\t\t시작시간\t\t예약좌석\t\t상영관\t\t예약시간\t\t  영화");
				for (G_Book G_book : view_book(phone_number)) {// 전화번호를 통해
					// 예매관련 내용들을
					// 불러옴

					String booknumber = G_book.getB_number();
					String start = G_book.getO_starttime().substring(0, 5);
					String title = G_book.getMv_title();
					String theater = G_book.getT_theater();
					String date = G_book.getB_date().substring(0, 19);
					String bookseat = G_book.getB_bookseat();

					System.out.println(String.format("%s\t%s\t\t %s\t\t %s\t %s\t  %s", booknumber, start, bookseat,
							theater, date, title));

					// System.out.println(G_book.getB_number() + " " +
					// G_book.getO_starttime() + " " + G_book.getMv_title()
					// + " " + G_book.getT_theater()
					// + " " + G_book.getB_date() + " " +
					// G_book.getB_bookseat());
				}

				System.out.println();
				System.out.println("메뉴화면으로 돌아갑니다.");
				sc.nextLine();
				continue outer;

			case 4: // 예매 취소
				int i = 1;
				int max = 0;
				for (G_Book G_book : view_book(phone_number)) {
					max = Math.max(G_book.getMv_title().length(), max);
				}

				// 헤더 출력
				System.out.printf("%2s\t%-10s\t%-5s\t%-" + (max + 2) + "s\t%-6s\t%-20s\t%-4s\n", "번호", "예매번호", "상영시작시간",
						"영화제목", "상영관", "예매한날짜", "예약좌석");
				// 예매 정보 출력

				for (G_Book G_book : view_book(phone_number)) {
					System.out.printf("%2d.\t%-15s\t%-15s\t%-" + (max + 2) + "s\t%-6s\t%-20s\t%-4s\n", i,
							G_book.getB_number(), G_book.getO_starttime().substring(0, 5), G_book.getMv_title(),
							G_book.getT_theater(), G_book.getB_date(), G_book.getB_bookseat());
					i++;
				}
				System.out.println();
				outer33: while (true) {
					System.out.print("삭제할 내역의 번호를 입력해주세요. > ");
					
					int delNumber = pt.input_error();
					if (delNumber > view_book(phone_number).size() || delNumber==0) {
						// 삭제할 번호에서 넘어갔을떄 텍스트 출력 어케함?
						System.out.println("\n잘못된 번호입니다. 다시 입력해주세요.");
						continue outer33;
					} else {
						// 번호는 1부터 시작하니 -1의 값이 삭제할 행임
						G_Book gbook = view_book(phone_number).get(delNumber - 1);
						int mf_openseat = 1 + find_open_seat(gbook.getO_starttime(), gbook.getMv_title(),
								// 예매취소를 했으니 사용가능좌석에 +1을 해야함
								gbook.getT_theater());

						view_open_seat(mf_openseat, gbook.getO_starttime(),
								// 좌석값을 넣음
								gbook.getT_theater(), gbook.getMv_title());
						// 관련 내용을 삭제함
						delete_book(gbook.getB_number());
						break;
					}
				}
				break;
			case 5:
				break outer;
			default :
				System.out.println("\n잘못된 입력입니다 다시 입력해주세요");
				sc.nextLine();
				break ;
			}
		}
	}

	public ArrayList<G_Opened_Movie> opened_movie() { // 상영중인 영화 정보 받아오는 객체

		ArrayList<G_Opened_Movie> al_openedMovie = new ArrayList<>();

		try {
			conn = getConnection();
			String sql = "SELECT * FROM mrs.opened_mv order by t_theater,o_starttime"; // 상영관,
																						// 상영시간으로
																						// 정렬
																						// 받아옴
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

	public void insert_book(String b_number, String m_phone, String o_starttime, String mv_title, String t_theater,
			String b_date, String b_bookseat) {

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

	public int find_open_seat(String a, String b, String c) { // 좌석을 찾는 클래스
		int result = 0;

		try {
			conn = getConnection();
			String sql = "SELECT * FROM opened_mv WHERE o_starttime = ? AND mv_title = ? AND t_theater = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a);
			pstmt.setString(2, b);
			pstmt.setString(3, c);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result += rs.getInt("o_openseat");
			}
		} catch (Exception e) {
			System.out.println("List DB 작업중 문제 발생 : ");
			e.printStackTrace();
		}

		return result;
	}

	public boolean dup_seat_cancel(String b_number) {

		try {
			String sql = "SELECT * FROM mrs.book WHERE b_number = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_number);
			rs = pstmt.executeQuery();

			if (rs.next() == true) { // sql에서 불러온 라인이 존재하면 실행
				System.out.println("\n이미 예약된 좌석입니다. 다시 입력해주세요");
				return false;
			}
		} catch (Exception e) {
			System.out.println("List DB 작업중 문제 발생 : ");
			e.printStackTrace();
		}
		System.out.println("\n좌석 예매를 합니다. > ");
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

				G_Movie_Info smi = new G_Movie_Info(getTitle, getRunningtime, getGenre, getGrade, getDirector,
						getOpenning);

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

}