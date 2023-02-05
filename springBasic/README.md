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
| GET | /api/board/{id} | {"id"  : 1 } | {<br>&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;"title":"title",<br>&nbsp;&nbsp;"writer":"writer",<br>&nbsp;&nbsp;"content":"content",<br>&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>}|선택 게시글 조회|
| POST | /api/boards | {<br>"title":"title",<br>"writer":"writer",<br>"passwd":"passwd",<br>"content":"content"<br>}| {<br>&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;"title":"title",<br>&nbsp;&nbsp;"writer":"writer",<br>&nbsp;&nbsp;"content":"content",<br>&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>} |게시글 등록|
| PUT | /api/board/{id} | {<br>"title":"title",<br>"writer":"writer",<br>"passwd":"passwd",<br>"content":"content"<br>}| {<br>&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;"title":"modifedtitle",<br>&nbsp;&nbsp;"writer":"modifedwriter",<br>&nbsp;&nbsp;"content":"modifedcontent",<br>&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>} |게시글 수정|
| DELETE | /api/board/{id} | {<br>"id"  : 1,<br>"passwd":"passwd"<br>}| {<br>"msg":<br> "2xx" - 성공<br>"4x4" - 유효하지 않은 id<br>"4x3" - 비밀번호 불일치<br>} |게시글 삭제|
