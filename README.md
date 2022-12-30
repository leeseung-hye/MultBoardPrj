# MultBoardPrj
본 프로젝트는 자바 진영의 동적 웹 사이트를 구현하고자 스프링 프레임워크를 사용해 구현한 커뮤니티 웹 입니다.

자바 플랫폼을 위한 오픈소스 애플리케이션 프레임워크로서 엔터프라이즈급 
애플리케이션을 개발하기 위한 모든 기능을 종합적으로 제공하는 경량화된 솔루션

# 



### 📅 개발기간
#### 2022.11.02 ~ 2022.11.18

#

### ⚙️ 개발환경
- window 10
- 전자정부프레임워크 3.9
- Spring Framework
- Oracle SQLDeveloper
- JDK 18.0.2
- HTML
- CSS
- JavaScript
- JSP
- ORACLE 11G Express Edition Release2
- Tomcat 9.0
- Mybatis
- Bootstrap5.2.2

#

### 💡 주요기능
* 회원가입 및 로그인
  * 현업자 : 취업준비생 회원가입과 동일하되 직장명/명함 등 인증가능한 사진파일 첨부 항목이 추가됨
  * 취업준비생 : 회원가입 시 아이디/비밀번호/비밀번호 재입력/이름/이메일을 입력해야 함
  * 관리자 : 관리자 계정 로그인 후 관리자 페이지로 이동
    * 관리자의 경우 현업자가 회원가입시 인증한 현업자 인증사진을 확인한 뒤 현업자로 가입 승인을 허락해주는 역할 수행
    * 현업자 가입 인증을 받은 회원은 현업자 게시판에 글작성이 가능
      (단, 현업자 가입 승인을 받지 못한 회원은 현업자 게시판에 글쓰기 작업 불가능)
      
* 글 작성 및 수정
  * 판매할 제품 단가, 사진, 제품 상태 상세설명, 판매자 연락처 등의 정보를 기입
  * 초기 입력한 데이터 수정
+ 글 목록 조회
  - 조회화면 
    + 전체 목록 조회 창에서 모든 매물 조회 가능
    + 메인 창에서 상위 20개의 인기 게시물 조회 가능
* 검색
  * 제품명, 거래장소 키워드를 검색해 매물 조회 가능
