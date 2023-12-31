package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : View를 통해서 사용자가 요청한 기능에 대해서 처리하는 담당
//            해당 메소드로 전달된 데이터 [가공처리후] dao로 전달하며 호출
//            dao로부터 반환받은 결과에따라서 성공인지 실패인지 판단 후 응답화면 결정(View 메소드 호출)
public class MemberController {
   
   /**
    * 사용자의 추가요청을 처리해주는 메소드
    * @param userId ~ hoppy : 사용자가 입력했던 정보들이 담겨있는 매게변수
    */
   public void insertMember(String userId, String userPwd, String userName, String gender, String age,
         String email, String phone, String address, String hoppy) {
      
      // view로부터 전달받은 값을 바로 dao쪽으로 전달x
      // 어딘가에(Member객체(vo)) 담아서 전달
      
      // 방법1) 기본생성자로 생성한 후에 각 필드에 setter메소드를 통해서 일일이 담는 방법
      // 방법2) 매게변수 생성자로 생성과 동시에 담는 방법
      
      Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hoppy);
      
      
      int result =new MemberDao().insertMember(m);
      
      if(result > 0) {
         new MemberMenu().displaySuccess("성공적으로 화면이 추가 되었습니다");
      } else {
         new MemberMenu().displayFail("회원추가 실패");
      }
   }
   
   public void selectList() { // 전체 회원 출력
      ArrayList<Member> list = new MemberDao().selectList();
      
      //조회된 결과에 따라서 사용자가 보게될 응답화면 지정
      if(list.isEmpty()) { // list가 비어있을 경우
         new MemberMenu().displayNoData("전체 조회 결과가 없습니다.");
      } else { //조회된 데이터가 있을경
         new MemberMenu().displayMemberList(list);
      }
   }
   
   /**
    * 사용자의 아이디로 회원 검색 요청을 처리해주는 메소드
    * @param userId : 사용자가 입력한 검색하고자하는 회원 아이디
    */
   public void selectByUserId(String userId) { // 회원 검색
	   Member m = new MemberDao().selectByUserId(userId);
	   
	   if(m == null) { // 검색 결과가 없는 경우
		   new MemberMenu().displayNoData(userId + "에 해당하는 조회 결과가 없습니다.");
	   } else { // 검색결과 있을 경우
		   new MemberMenu().displayMember(m);
	   }
   }
   
   /**
    * 이름으로 키워드 검색 요청시 처리해주는 메소드
    */
   public void selectByUserName(String keyword) {
	   
	  ArrayList<Member> list = new MemberDao().selectByUserName(keyword);
	  
	  if(list.size() > 0) { // 리스트에 검색결과가 담겨있을때
		  new MemberMenu().displayMemberList(list);
	  } else { // 검색결과가 비어있을 때
		  new MemberMenu().displayNoData(keyword + "로 조회되는 결과가 없습니다.");
	  }
   }
   
   public void updateMember(String userId, String userPwd, String eamile, String phone, String address) {

	   Member m = new Member();
	   m.setUserId(userId);
	   m.setUserPwd(userPwd);
	   m.setEmail(address);
	   m.setPhone(phone);
	   m.setAddress(address);
	   
	   int result = new MemberDao().updateMember(m);
	   
	   if(result > 0) {
		   new MemberMenu().displaySuccess("성공적으로 회원 정보 변경 되었습니다.");
	   } else {
		   new MemberMenu().displayFail("회원 정보 변경에 실패하였습니다.");
	   }
   }
   
   
   public void deleteMember(String userId) {
	   
	   int result = new MemberDao().deleteMember(userId);
	   
	   if(result > 0) {
		   new MemberMenu().displaySuccess("성공적으로 회원 탈퇴처리 하였습니다");
	   } else {
		   new MemberMenu().displayFail("회원 탈퇴 실패 되었습니다.");
	   }
	   
   }
   
   
}