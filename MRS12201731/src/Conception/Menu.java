package Conception;

import java.sql.ResultSet;
import java.sql.SQLException;


import Generate.G_Member;

public class Menu extends DBconnect {

	
	Prompt pt = new Prompt();
	
	

	public void start() {
		outer : while (true) {
			pt.display_menu ();

			int select = Integer.parseInt(sc.nextLine());

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
					System.out.println("사용해 주셔서 감사합니다.");
					break outer;
				default :
					System.out.println("초기 메뉴로 돌아갑니다.");
					sc.nextLine();
					continue outer;
			}
		}
	}

	public void go_member() {

		Member mb = new Member();

		loop : while (true) {

			System.out.println("1.회원가입\n2.회원탈퇴\n3.회원정보 찾기 및 변경\n4.나가기");
			String num = sc.nextLine();

			switch (num) {
				case "1" :// 회원 가입 확인 확인
					System.out.println("회원 가입");
					mb.sign_up(0);
					continue loop;

				case "2" :// 회원 탈퇴 확인 확인
					System.out.println("회원 탈퇴");
					pt.input_idpw();
					mb.del_id(pt.id, pt.pw, 0);
					continue loop;

				case "3" :// 회원 정보 찾기 및 변경
					// System.out.println("ㅋㅋ");
					System.out.println("1.아이디 찾기\n2.비밀번호 찾기\n3.비밀번호 변경\n4.나가기");
					String menu_num = sc.nextLine();

					switch (menu_num) {
						case "1" : {// 아이디 찾기 확인 확인
							pt.input_namephone();
							mb.find_id(pt.name, pt.phone, 0);
							continue loop;
						}
						case "2" : {// pw 찾기 확인 확인
							pt.input_idphone();
							mb.find_id(pt.id, pt.phone, 0);
							continue loop;
						}
						case "3" : {// pw 변경 확인 확인
							pt.input_idpw();
							mb.change_pw(pt.id, pt.pw, 0);
							continue loop;
						}case "4" :
							System.out.println("상위 메뉴로 돌아갑니다.");
							sc.nextLine();
							return;
					}
				case "4" :
					System.out.println("초기 메뉴로 돌아갑니다.");
					sc.nextLine();
					return;
			}
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
					case "0" :
						ad.Sign_Up_Admin();
						break;
					case "1" :
						success = ad.Login_Admin();

						if (success) {
							in : while (true) {
								pt.display_adm_menu();
								num1 = sc.next();

								switch (num1) {
									case "1" :
										ad.Add_mv();
										break;
									case "2" :
										ad.Del_mv();
										break;
									case "3" :
										// ad.ListAdd_mv();
										break;
									case "4" :
										break in;
									default: 
										System.out.println("잘못된 입력입니다 다시 입력해주세요");
										sc.nextLine();
										continue in;
								}
							}
						}
						break;
					case "2" :
						ad.Find_Admin_Id();
						break;
					case "3" :
						ad.Find_Admin_Pw();
						break;
					case "4" :
						ad.Del_Admin_Id();
						break;
					case "5" :
						ad.Change_Admin_Pw();
						break;
					case "6" :
						System.out.println("초기 메뉴로 돌아갑니다.");
						sc.nextLine();
						break outer;
					default :
						System.out.println("잘못된 입력입니다 다시 입력해주세요");
						sc.nextLine();
						continue outer;
				}
			}
		} catch (Exception ex) {
			System.out.println("Admin Error : " + ex.getMessage());
		}
	}

	public void go_book() {

		Book bk = new Book();
		outer : while (true) {
			System.out.println("1. 로그인\n2. 비로그인\n3. 종료");
			System.out.print("입력:");
			int select = sc.nextInt();
			switch (select) {

				case 1 :
					Member mb = new Member();
					pt.input_idpw();
					G_Member mb_g = mb.sign_in(pt.id, pt.pw, 0);
					if (mb_g == null) {
						break;
					}
					bk.First_book_menu(mb_g.getM_phone());
					break outer;

				case 2 :
					System.out.println("비회원 예매를 위한 정보를 입력해 주세요.");
					pt.input_namephone();
					String sql = "select m_name,m_phone,m_id from member where m_phone = '" + pt.phone + "';";
					ResultSet rs = Dblistup(sql);
					try {
						if (rs.next() == false) {
							System.out.println(
									"정보가 등록되었습니다.\n" + "비회원 예매를 시작합니다.\n");
							sc.nextLine();
						} else {
							if (rs.getString("m_id") != "비회원") {
								System.out.println("가입된 회원입니다. 로그인하여 이용해 주세요");
								continue outer;
							} else {
								System.out.println("비회원 예매를 시작합니다.");
								sc.nextLine();
								bk.First_book_menu(pt.phone);
								break;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					String sql2 = "INSERT INTO member(m_phone, m_name, m_id, m_pw, m_manager) value('"
							+ pt.phone + "','" + pt.name + "','비회원','비회원',0)";
					DbUpdate(sql2);
					bk.First_book_menu(pt.phone);
					break outer;

				case 3 :
					System.out.println("초기 메뉴로 돌아갑니다.");
					sc.nextLine();
					break outer;

				default :
					System.out.println("잘못된 입력입니다 다시 입력해주세요");
					sc.nextLine();
					continue outer;

			}
		}
	}
}
