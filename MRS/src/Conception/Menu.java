package Conception;

import java.sql.ResultSet;
import java.sql.SQLException;

import Generate.G_Member;

public class Menu extends DBconnect {

	Prompt pt = new Prompt();

	public void start() {
		try {
			outer : while (true) {
				pt.display_menu();
				while(!sc.hasNextInt()) {
					sc.nextLine();
					System.out.print("  숫자를 입력해주세요. > ");
				}
				int select = sc.nextInt();
				sc.nextLine();
				switch (select) {
					case 1 :
						go_member();
						break;

					case 2 :
						go_admin();
						break;

					case 3 :
						go_book();
						break;

					case 4 :
						System.out.println("\n사용해 주셔서 감사합니다.");
						break outer;
					default :
						System.out.println("\n초기 메뉴로 돌아갑니다.");
						System.out.println("Enter키를 누르세요.");
						sc.nextLine();
						continue outer;
				}
			}
		} catch (Exception ex) {
			System.out.println("start Error : " + ex.getMessage());
		}
	}

	public void go_member() {
		try {
			Member mb = new Member();

			outer : while (true) {
				System.out.println("\n========= 회원 =========\n");
				System.out.println(
						"  1. 회원가입\n  2. 회원탈퇴\n  3. 회원정보 찾기 및 변경\n  4. 나가기");
				System.out.print("\n번호를 입력해주세요. > ");
				String num = sc.nextLine();

				switch (num) {
					case "1" :// 회원 가입 확인 확인
						System.out.println("\n====== 회원 가입 ======\n");
						mb.sign_up(0);
						continue outer;

					case "2" :// 회원 탈퇴 확인 확인
						System.out.println("\n====== 회원 탈퇴 ======");
						pt.input_idpw();
						if (!mb.regular(pt.id, "id")) {
							break;
						}
						if (!mb.regular(pt.pw, "pw")) {
							break;
						}
						mb.del_id(pt.id, pt.pw, 0);
						continue outer;

					case "3" :// 회원 정보 찾기 및 변경
						in : // 잘못 입력했을 경우에 gomember 초기로 돌아가는것 떄문에 in 반복문 추가
						while (true) {
							System.out.println("\n== 회원정보 찾기 및 변경 ==\n");
							System.out.println(
									"    1. 아이디 찾기\n    2. PW 찾기\n    3. PW 변경\n    4. 나가기");
							System.out.print("\n  번호를 입력해주세요. > ");
							String menu_num = sc.nextLine();

							switch (menu_num) {
								case "1" : {// 아이디 찾기 확인 확인
									pt.input_namephone();
									if (!mb.regular(pt.name, "name")) {
										continue in;
									}
									if (!mb.regular(pt.phone, "phone")) {

										continue in;
									}
									mb.find_id(pt.name, pt.phone, 0);
									continue outer;
								}
								case "2" : {// pw 찾기 확인 확인
									pt.input_idphone();
									if (!mb.regular(pt.id, "id")) {
										continue in;
									}

									if (!mb.regular(pt.phone, "phone")) {

										continue in;
									}
									mb.find_pw(pt.id, pt.phone, 0);
									continue outer;
								}
								case "3" : {// pw 변경 확인 확인
									pt.input_idpw();
									if (!mb.regular(pt.id, "id")) {
										continue in;
									}
									if (!mb.regular(pt.pw, "pw")) {
										continue in;
									}
									mb.change_pw(pt.id, pt.pw, 0);
									continue outer;
								}
								case "4" : {
									System.out.println("\n상위 메뉴로 돌아갑니다.");
									System.out.println("Enter키를 누르세요.");
									sc.nextLine();
									return;
								}
								default :
									System.out.println("\n잘못된 입력입니다 다시 입력해주세요");
									sc.nextLine();
									continue in;
							}
						}
					case "4" :
						System.out.println("\n초기 메뉴로 돌아갑니다.");
						System.out.println("Enter키를 누르세요.");
						sc.nextLine();
						return;
					// 디폴트 추가
					default :
						System.out.println("\n잘못된 입력입니다 다시 입력해주세요");
						sc.nextLine();
						continue outer;
				}
			}
		} catch (Exception ex) {
			System.out.println("member Error : " + ex.getMessage());
		}

	}

	public void go_admin() {
		try {
			Admin ad = new Admin();
			boolean success = false;
			String num = "";
			String num1 = "";

			outer : while (true) {
				pt.login_admin();
				num = sc.next();

				switch (num) {
					case "1" :
						success = ad.login_admin();

						if (success) {
							in : while (true) {
								pt.display_adm_menu();
								num1 = sc.next();

								switch (num1) {
									case "0" :
										ad.sign_up_admin();
										break;
									case "1" :
										ad.add_mv();
										break;
									case "2" :
										ad.del_mv();
										break;
									case "3" :
										ad.add_opened_mv();
										break;
									case "4" :
										break in;
									default :
										System.out.println(
												"\n잘못된 입력입니다 다시 입력해주세요");
										sc.nextLine();
										continue in;
								}
							}
						}
						break;
					case "2" :
						ad.find_admin_id();
						break;
					case "3" :
						ad.find_admin_pw();
						break;
					case "4" :
						ad.del_admin_id();
						break;
					case "5" :
						ad.change_admin_pw();
						break;
					case "6" :
						System.out.println("\n초기 메뉴로 돌아갑니다.");
						System.out.println("Enter키를 누르세요.");
						sc.nextLine();
						break outer;
					default :
						System.out.println("\n잘못된 입력입니다 다시 입력해주세요");
						sc.nextLine();
						continue outer;
				}
			}
		} catch (Exception ex) {
			System.out.println("Admin Error : " + ex.getMessage());
		}
	}

	public void go_book() {
		try {
			Member mb = new Member();// 멤버 내의 메서드 사용을 위해 상위 이동
			Book bk = new Book();
			outer : while (true) {
				System.out.println("\n======= 예매 =======\n");
				System.out.println("    1. 회원\n    2. 비회원\n    3. 종료");
				System.out.print("\n  번호를 입력해주세요. > ");
				int select = pt.input_error();
				
				switch (select) {
					case 1 :
						pt.input_idpw();
						G_Member mb_g = mb.sign_in(pt.id, pt.pw, 0);
						if (mb_g == null) {
							System.out.println("\n존재하지 않는 정보입니다 선택화면으로 돌아갑니다.");

							break;
						}
						bk.Book_Pt();
						bk.first_book_menu(mb_g.getM_phone());
						break outer;

					case 2 :
						System.out.println("\n 비회원 예매를 위한 정보를 입력해주세요.");
						pt.input_namephone();
						if (!mb.regular(pt.name, "name")) {
							break;
						}
						if (!mb.regular(pt.phone, "phone")) {

							break;
						}
						String sql = "select m_name,m_phone,m_id from member where m_phone = '"
								+ pt.phone + "';";
						ResultSet rs = Dblistup(sql);
						try {
							if (rs.next() == false) {
								System.out.println("\n정보가 등록되었습니다.\n"
										+ "비회원 예매를 시작합니다.\n");
								sc.nextLine();

							} else {
								if (rs.getString("m_id").equals("비회원")) {
									System.out.println("\n비회원 예매를 시작합니다.");
									sc.nextLine();
									bk.first_book_menu(pt.phone);
									break;

								} else {
									System.out.println(
											"\n가입된 회원입니다. 로그인하여 이용해 주세요");
									continue outer;

								}
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						String sql2 = "INSERT INTO member(m_phone, m_name, m_id, m_pw, m_manager) value('"
								+ pt.phone + "','" + pt.name
								+ "','비회원','비회원',0)";
						DbUpdate(sql2);
						bk.first_book_menu(pt.phone);
						break outer;

					case 3 :
						System.out.println("\n초기 메뉴로 돌아갑니다.");
						System.out.println("Enter키를 누르세요.");
						sc.nextLine();
						break outer;

					default :
						System.out.println("\n잘못된 입력입니다 다시 입력해주세요");
						sc.nextLine();
						continue outer;

				}
			}
		} catch (Exception ex) {
			System.out.println("book Error : " + ex.getMessage());
		}
	}
}