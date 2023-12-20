package Conception;

import java.util.Scanner;

public class Prompt {
	Scanner sc = new Scanner(System.in);

	String id;
	String pw;
	String phone;
	String name;
	String prompt;

	public void display_menu () {
		System.out.println("1. 회원\n2. 관리자 \n3. 예매\n4. 종료");
		System.out.print("입력: ");
	}

	public void input_idpw() {
		System.out.println("아이디를 입력하세요");
		System.out.print("ID: ");
		id = sc.nextLine();
		System.out.println("비밀번호를 입력하세요");
		System.out.print("PW: ");
		pw = sc.nextLine();
	}

	public void input_idphone() {
		System.out.println("아이디를 입력하세요");
		System.out.print("ID: ");
		id = sc.nextLine();
		System.out.println("전화번호를 입력하세요");
		System.out.print("전화 번호: ");
		phone = sc.nextLine();
	}

	public void input_namephone() {
		System.out.println("이름을 입력하세요");
		System.out.print("이름: ");
		name = sc.nextLine();
		System.out.println("전화번호를 입력하세요");
		System.out.print("전화 번호: ");
		phone = sc.nextLine();
	}

	// =================================관리자 부분=================================
	// //이상효
	public void login_admin() {
		prompt = """
				메뉴선택
				0. 관리자 등록
				1. 관리자 로그인
				2. 관리자 아이디 찾기
				3. 관리자 비번 찾기
				4. 관리자 탈퇴
				5. 관리자 비번 변경
				6. 나가기

				입력 : """;
		System.out.print(prompt);
	}

	public void display_adm_menu() {
		prompt = """
				메뉴선택
				1. 영화 추가
				2. 영화 삭제
				3. 상영 영화 등록
				4. 나가기

				입력 : """;
		System.out.print(prompt);
	}
	// ==========================================================================
}