# 게시글 만들기

## 🔨 기능
#### 회원 가입
- username, password를 Client에서 전달받기
- username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성
- password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성
- 중복된 **username**이 없다면 회원을 저장
- Client로 메시지, 상태코드 반환하기

#### 로그인
- username, password를 Client에서 전달받기
- username으로 조회된 회원이 있다면 password 비교하기
- 로그인 성공 시, 유저의 정보와 JWT를 활용하여 토큰을 발급하여 Header에 추가
- Client로 메시지, 상태코드 반환하기

#### 전체 게시글 목록 조회
- 제목, 작성자명(username), 작성 내용, 작성 날짜를 조회하기
- 작성 날짜 기준 내림차순으로 정렬하기

#### 게시글 작성 
- 토큰 검사 후 유효한 토큰일 경우에만 게시글 작성 가능
- 제목, 작성자명(username), 작성 내용을 저장
- 저장된 게시글을 Client 로 반환하기

#### 선택한 게시글 조회 
- 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기 

#### 선택한 게시글 수정
- 토큰 검사 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
- 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기

#### 선택한 게시글 삭제 
- 토큰 검사 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능
- 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기

## ✏ Usecase Diagram
![게시글_유스케이스](https://github.com/jyparkDev/hanghae_spring_homework/blob/main/springBasic/docs/usecase.png)

## 📜 ERD
![게시판 ERD](https://github.com/jyparkDev/hanghae_spring_homework/blob/main/springBasic/docs/erd.png)

## 📜 API Docs
<a href="https://github.com/jyparkDev/hanghae_spring_homework/blob/main/springBasic/docs/api_docs.md">[API문서보기]</a>


## ❓ 생각해보기
1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
    - 데이터 수정 / 삭제를 위한 개인고유정보인 패스워드가 요구되기에 body에 담아 전송함으로 웹 상에 URL에 노출되지 않도록 하였다.
2. 어떤 상황에 어떤 방식의 request를 써야하나요?
    - 게시글 등록 - POST
    - 게시글 조회 - GET
    - 게시글 수정 - PUT
    - 게시글 삭제 - DELETE
3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
    - URI를 통하여 board라는 자원과 {id} 통하여 자원의 고유번호를 식별가능하며, 단순 데이터를 응답하는 방식으로 설계하여 클라이언트와 서버 간 느슨하게 연결되어 있다.
4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
    -Controller에서는 단순 클라이언트로부터 요청을 분리하였으며, Service 계층에서는 해당 컨트롤러로부터 요청을 받아 처리해야할 작업들을 수행하였고, repository는 단순 DB와의 접근에만 집중하게 분리하였다.
5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
    - 작성 가이드와 비교해보았으며, swagger / spring rest docs 등을 주로 API 명세서를 만들기 위해 사용하고 있는 것 같다. 공통적인 사항들로 기능, 요청정보, 응답정보, 요청방식, 요청 URL 등을 기재한다.
    
    
