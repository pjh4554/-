package Conception;

import java.sql.SQLException;
import java.util.ArrayList;

import Generate.G_Member;

public class Member extends DBconnect {
	public void sign_up(int manager) { // 가입

		String id;

		while (true) {
			System.out.print("\nID를 입력해주세요. > ");
			id = sc.nextLine();
			if(!regular(id, "id")) {
				continue;
			}
			if (dup_check(id) == false) {
				continue;
			}
			System.out.print("PW를 입력해주세요. > ");
			String pw = sc.nextLine(); 
			if(!regular(pw, "pw")) {
				continue;
			}
			System.out.print("이름을 입력해주세요. > ");
			String name = sc.nextLine();
			if(!regular(name, "name")) {
				continue;
			}
			System.out.print("전화번호를 입력해주세요. > ");
			String phone = sc.nextLine();
			if (!regular(phone, "phone")) {
				continue;
			}
			String sql = "select * from member where m_phone='" + phone + "'";
			if (search_member(sql) == null) {
				sql = "insert into member(m_id,m_pw,m_name,m_phone,m_manager) values ('"
						+ id + "','" + pw + "','" + name + "','" + phone + "',"
						+ manager + ")";

				DbUpdate(sql);
				System.out.println("\n회원가입이 완료되었습니다.");
				break;
			} else if (search_member(sql).get(0).getM_id().equals("비회원")) {
				sql = "update member set m_pw= '" + pw + "' ,m_id='" + id
						+ "',m_name='" + name + "' where m_phone= '" + phone
						+ "'";
				DbUpdate(sql);
				System.out.println("\n가입이 완료되었습니다.");
				System.out.println("비회원으로 저장된 내역이 회원 정보로 전환됩니다.");
				// 대충 비회원에서 회원으로 전환됬다는 말이 필요한데 뭐가 좋을지 몰라서 일단 넣어놓음
				break;
			} else {
				System.out.println("\n이미 등록된 전화번호입니다.");
			}
		}
	}

	public boolean dup_check(String id) { // 중복 체크

		String sql = "select * from member where m_id='" + id + "'";

		try {
			rs = Dblistup(sql);

			if (rs.next()) {
				System.out.println("\n사용할 수 없는 아이디입니다. 다시 입력해주세요.\n");
				return false;
			} else {
				System.out.println("\n사용할 수 있는 아이디입니다.\n");
				return true;
			}
		} catch (Exception e) {
			System.out.println("중복 체크 실패");
			e.printStackTrace();
			return false;
		}

	}

	public ArrayList<G_Member> search_member(String sql) { // DB에서 정보 조회(중복 방지용)
		ArrayList<G_Member> al = new ArrayList<G_Member>();
		try {
			rs = Dblistup(sql);

			if (rs.next() == false) {
				//이부분 날릴것 필요없는 부분임
//				System.out.println("\n틀린 정보입니다. 다시 확인해 주세요.");
				return al = null;
			}

			rs.beforeFirst();

			while (rs.next()) {
				String phone = rs.getString("m_phone");
				String m_pw = rs.getString("m_pw");
				String name = rs.getString("m_name");
				String m_id = rs.getString("m_id");
				boolean m_manager = rs.getBoolean("m_manager");
				G_Member mb = new G_Member(phone, m_pw, name, m_id, m_manager);
				al.add(mb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("멤버 서치 실패");
		}
		return al;

	}

	public G_Member sign_in(String id, String pw, int manager) { // 로그인 -> DB
		ArrayList<G_Member> al = new ArrayList<G_Member>();
		String sql = "select * from member where m_id = '" + id
				+ "' and m_pw = '" + pw + "' and m_manager =" + manager;

		al = search_member(sql);

		if (al == null) {
			return null;
		} else if (al.get(0).getM_id().equals("비회원")) {// id 비밀번호 비회원 입력시 통과하는것// 막기 위함
			return null;
		} else {
			return al.get(0);
		}

	}

	public void del_id(String id, String pw, int manager) { // 탈퇴 -> DB
		G_Member mb = sign_in(id, pw, manager);
		Book book = new Book();
		if (mb == null) {
			System.out.println("가입 되지않은 ID입니다.");
		} else {
			String sql;
			if (book.view_book(mb.getM_phone()).size() > 0) {
				System.out.println("\n예매 내역이 존재해 예매내역을 비회원으로 전환합니다.");
				sql = "update member set m_pw= '비회원' ,m_id='비회원' where m_phone= '"
						+ mb.getM_phone() + "'";
			} else {
				sql = "delete from member where m_phone ='" + mb.getM_phone()
						+ "'";

			}
			System.out.println("\n탈퇴가 완료되었습니다.");
			DbUpdate(sql);

		}

	}

	public void find_id(String name, String phone, int manager) { // 아이디/비밀번호 찾기
																	// -> DB

		String sql = "select * from member where m_name = '" + name
				+ "' and m_phone = '" + phone + "' and m_manager =" + manager;
		G_Member mb = null;

		try {
			rs = Dblistup(sql);

			if (rs.next()) {
				String pw = rs.getString("m_pw");
				String id = rs.getString("m_id");
				boolean bManager = (manager != 0);

				mb = new G_Member(phone, pw, name, id, bManager);
				if (mb.getM_id().equals("비회원")) {
					System.out.println("\n정보를 찾을수 없습니다.");
					return;
				}

				System.out.print("\n당신의 ID는 \'" + mb.getM_id() + "\'입니다.\n");
			} else {
				System.out.println("\n정보를 찾을수 없습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ID 찾기 실패");
		}
	}

	public void find_pw(String id, String phone, int manager) { // 아이디/비밀번호 찾기
																// -> DB

		String sql = "select * from member where m_id = '" + id
				+ "' and m_phone = '" + phone + "' and m_manager = " + manager;
		G_Member mb = null;

		try {
			rs = Dblistup(sql);

			if (rs.next()) {
				String name = rs.getString("m_name");
				String pw = rs.getString("m_pw");
				boolean bManager = (manager != 0);

				mb = new G_Member(phone, pw, name, id, bManager);
				if (mb.getM_pw().equals("비회원")) {
					System.out.println("\n정보를 찾을수 없습니다.");
					return;
				}

				System.out.print("\n당신의 PW는 \'" + mb.getM_pw() + "\'입니다\n");
			} else {
				System.out.println("\n정보를 찾을수 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("PW 찾기 실패");
		}

	}

	public void change_pw(String id, String pw, int manager) { // 비밀번호 변경 -> DB

		G_Member mb = sign_in(id, pw, manager);

		if (mb == null) {
			System.out.println("\nPW가 불일치합니다.");
		} else {
			System.out.print("\n새로운 PW 입력 : ");
			String newPw = sc.nextLine();
			String sql = "update member set m_pw ='" + newPw
					+ "' where m_phone='" + mb.getM_phone() + "'";

			DbUpdate(sql);
			System.out.println("\nPW가 변경되었습니다.");
		}
	}
	
	public boolean regular(String string,  String num) {
		String regex;
		boolean isVal;

		switch (num) {
			case "phone" :// 핸드폰 정규식
				regex = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$";
				isVal = string.matches(regex);
				if (!isVal)
					System.out.println("\n유효한 전화번호가 아닙니다. ex) 010-0000-0000");
				return isVal;
			case "name" :// 이름 정규식
				regex = "^[ㄱ-ㅎ가-힣a-zA-Z]{2,10}$";
				isVal = string.matches(regex);
				if (!isVal)
					System.out.println("\n이름: 한글 또는 영대소문자 2~10 자리를 입력해주세요");
				return isVal;
			case "id" :// id 정규식
				regex = "^[a-zA-Z0-9가-힣]{5,20}$";
				isVal = string.matches(regex);
				if (!isVal)
					System.out.println("\nID: 영대소문자 또는 숫자를 포함한 5~20자리 문자를 사용해주세요");
				return isVal;
			case "pw" :// pw 정규식
				regex = "^[a-zA-Z0-9]{5,20}$";
				isVal = string.matches(regex);
				if (!isVal)
					System.out.println("\nPW: 영대소문자 또는 숫자를 포함한 5~20자리 문자를 사용해주세요");
				return isVal;
			default :
				return false;
		}
	}

}
