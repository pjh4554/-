package Conception;

import java.sql.SQLException;
import java.util.ArrayList;


import Generate.G_Member;

public class Member extends DBconnect {
	public void sign_up(int manager) { // 가입

		String id;

		while (true) {
			System.out.println("아이디를 입력하세요.");
			System.out.print("ID: ");

			id = sc.nextLine();

			if (dup_check(id) != false) {
				break;
			}
		}

		System.out.print("PW: ");
		String pw = sc.nextLine();
		System.out.print("이름: ");
		String name = sc.nextLine();
		System.out.print("번호: ");
		String phone = sc.nextLine();
		String sql = "insert into member(m_id,m_pw,m_name,m_phone,m_manager) values ('"
				+ id + "','" + pw + "','" + name + "','" + phone + "',"
				+ manager + ")";

		DbUpdate(sql);
	}

	public boolean dup_check(String id) { // 중복 체크

		String sql = "select * from member where m_id='" + id + "'";

		try {
			rs = Dblistup(sql);

			if (rs.next()) {
				System.out.println("중복입니다.");
				return false;
			} else {
				System.out.println("통과");
				return true;
			}
		} catch (Exception e) {
			System.out.println("db연결 실패");
			e.printStackTrace();
			return false;
		}

	}

	public ArrayList<G_Member> search_member(String sql) { // DB에서 정보 조회(중복 방지용)
		ArrayList<G_Member> al = new ArrayList<G_Member>();
		try {
			rs = Dblistup(sql);

			if (rs.next() == false) {
				System.out.println("틀린 정보입니다. 다시 입력해 주세요");
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
			System.out.println("db연결 실패");
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
		} else {
			return al.get(0);
		}

	}

	public void del_id(String id, String pw, int manager) { // 탈퇴 -> DB
		G_Member mb = sign_in(id, pw, manager);
		if (mb == null) {
			System.out.println("불일치");
		} else {
			String sql;
			if (view_book(mb.getM_phone()).size() > 0) {
				System.out.println("예매 내역이 존재해 예매내역을 비회원으로 전환합니다.");
				sql = "update member set m_pw= '비회원' ,m_id='비회원' where m_phone= '"
						+ mb.getM_phone() + "'";
			} else {
				sql = "delete from member where m_phone ='" + mb.getM_phone()
						+ "'";

			}
			System.out.println("탈퇴가 완료되었습니다.");
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

				System.out.print("ID: " + mb.getM_id() + "\n");
			} else {
				System.out.println("정보를 찾을수 없습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("db연결 실패");
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

				System.out.print("PW: " + mb.getM_pw() + "\n");
			} else {
				System.out.println("정보를 찾을수 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("db연결 실패");
		}

	}

	public void change_pw(String id, String pw, int manager) { // 비밀번호 변경 -> DB

		G_Member mb = sign_in(id, pw, manager);

		if (mb == null) {
			System.out.println("불일치");
		} else {
			System.out.print("새로운 비밀번호 입력: ");
			String newPw = sc.nextLine();
			String sql = "update member set m_pw ='" + newPw
					+ "' where m_phone='" + mb.getM_phone() + "'";

			DbUpdate(sql);

		}
	}

}
