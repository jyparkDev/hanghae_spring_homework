# 게시글 만들기

## 🔨 기능
### 전체 게시글 목록 조회 API
- 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
- 작성 날짜 기준 내림차순으로 정렬하기
### 게시글 작성 API 
- 제목, 작성자명, 비밀번호, 작성 내용을 저장
- 저장된 게시글을 Client 로 반환하기
### 선택한 게시글 조회 API 
- 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 
### 선택한 게시글 수정 API
- 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인
- 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
### 선택한 게시글 삭제 API
- 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인
- 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기

## ✏ Usecase Diagram
![게시글_유스케이스](https://user-images.githubusercontent.com/72076023/216767627-3d466878-4fb9-47a9-b148-771579d1a344.png)


## 📜 API Docs

| Method | URL | Request | Response | 기능 |
| :-------: | :---: | :---| :--- | :----: |
| GET | /api/boards | | {<br>&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"title":"title",<br>&nbsp;&nbsp;&nbsp;&nbsp;"writer":"writer",<br>&nbsp;&nbsp;&nbsp;&nbsp;"content":"content",<br>&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;}&nbsp;,<br>&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;"id":2,<br>&nbsp;&nbsp;&nbsp;&nbsp;"title":"title",<br>&nbsp;&nbsp;&nbsp;&nbsp;"writer":"writer",<br>&nbsp;&nbsp;&nbsp;&nbsp;"content":"content",<br>&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;}&nbsp;, ....<br>} | 전체 게시글 조회 
| GET | /api/board/{id} | {&nbsp;"id" : 1&nbsp; } | {<br>&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;"title":"title",<br>&nbsp;&nbsp;"writer":"writer",<br>&nbsp;&nbsp;"content":"content",<br>&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>}|선택 게시글 조회|
| POST | /api/boards | {<br>&nbsp;&nbsp;"title":"title",<br>&nbsp;&nbsp;"writer":"writer",<br>&nbsp;&nbsp;"passwd":"passwd",<br>&nbsp;&nbsp;"content":"content"<br>}| {<br>&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;"title":"title",<br>&nbsp;&nbsp;"writer":"writer",<br>&nbsp;&nbsp;"content":"content",<br>&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>} |게시글 등록|
| PUT | /api/board/{id} | {<br>&nbsp;&nbsp;"title":"title",<br>&nbsp;&nbsp;"writer":"writer",<br>&nbsp;&nbsp;"passwd":"passwd",<br>&nbsp;&nbsp;"content":"content"<br>}| {<br>&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;"title":"modifedtitle",<br>&nbsp;&nbsp;"writer":"modifedwriter",<br>&nbsp;&nbsp;"content":"modifedcontent",<br>&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>} |게시글 수정|
| DELETE | /api/board/{id} | {<br>&nbsp;&nbsp;"passwd":"passwd"<br>}| {<br>&nbsp;&nbsp;"msg":<br> &nbsp;&nbsp;"2xx" - 성공<br>&nbsp;&nbsp;"4x4" - 유효하지 않은 id<br>&nbsp;&nbsp;"4x3" - 비밀번호 불일치<br>} |게시글 삭제|


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
