| 기능 | Method | URL | Req | ReqHeader| Res | ResHeader|
| :-------:| :---: | :---: | --- | :----: | --- | --- |
|회원가입| POST | /api/user/signup| {<br>&nbsp;&nbsp;&nbsp;"username":"계정명",<br>&nbsp;&nbsp;&nbsp;"password":"비밀번호",<br>&nbsp;&nbsp;&nbsp;"adminCheck":"관리자여부"<br>}||200 : <br>{<br>&nbsp;&nbsp;"statusCode": 200,<br>&nbsp;&nbsp;"statusMsg": "가입 완료"<br>}<br>400 : <br>{<br>&nbsp;&nbsp;"statusCode": 400,<br>&nbsp;&nbsp;"statusMsg": "Error 메세지"<br>}||
|로그인| POST | /api/user/login| {<br>&nbsp;&nbsp;&nbsp;"username":"계정명",<br>&nbsp;&nbsp;&nbsp;"password":"비밀번호",<br>}||200 : <br>{<br>&nbsp;&nbsp;"statusCode": 200,<br>&nbsp;&nbsp;"statusMsg": "로그인 완료"<br>}<br>400 : <br>{<br>&nbsp;&nbsp;"statusCode": 400,<br>&nbsp;&nbsp;"statusMsg": "Error 메세지"<br>}|{&nbsp;"Authorization" : "발급받은 토큰"&nbsp;}|
|전체 게시글 조회 | GET | /api/boards | | | [<br>&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;"id":2,<br>&nbsp;&nbsp;&nbsp;&nbsp;"title":"글제목",<br>&nbsp;&nbsp;&nbsp;&nbsp;"username":"작성자",<br>&nbsp;&nbsp;&nbsp;&nbsp;"content":"글내용",<br>&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;}&nbsp;,<br>&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;&nbsp;&nbsp;"title":"글제목",<br>&nbsp;&nbsp;&nbsp;&nbsp;"username":"작성자",<br>&nbsp;&nbsp;&nbsp;&nbsp;"content":"글내용",<br>&nbsp;&nbsp;&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;}&nbsp;<br>] | 
| 선택 게시글 조회|GET | /api/board/{id} | ||200 : <br>{<br>&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;"title":"글내용",<br>&nbsp;&nbsp;"username":"작성자",<br>&nbsp;&nbsp;"content":"글내용",<br>&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>}<br>400 : <br>{<br>&nbsp;&nbsp;"statusCode": 400,<br>&nbsp;&nbsp;"statusMsg": "Error 메세지"<br>}||
| 게시글 등록| POST | /api/boards | {<br>&nbsp;&nbsp;"title":"글 제목",<br>&nbsp;&nbsp;"content":"글 내용"<br>}| {&nbsp;"Authorization" : "발급받은 토큰"&nbsp;}| 200 : <br>{<br>&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;"title":"글 제목",<br>&nbsp;&nbsp;"username":"작성자",<br>&nbsp;&nbsp;"content":"글내용",<br>&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>}<br>400 : <br>{<br>&nbsp;&nbsp;"statusCode": 400,<br>&nbsp;&nbsp;"statusMsg": "Error 메세지"<br>}|
|게시글 수정| PUT | /api/board/{id} | {<br>&nbsp;&nbsp;"title":"글 제목",<br>&nbsp;&nbsp;"content":"글 내용"<br>}|{&nbsp;"Authorization" : "발급받은 토큰"&nbsp;}|200 : <br>{<br>&nbsp;&nbsp;"id":1,<br>&nbsp;&nbsp;"title":"변경 제목",<br>&nbsp;&nbsp;"username":"작성자",<br>&nbsp;&nbsp;"content":"변경글내용",<br>&nbsp;&nbsp;"createdAt": "2022-07-25T12:43:01.226062”,<br>&nbsp;&nbsp;"modifiedAt": "2022-07-25T12:43:01.226062”,<br>}<br>400 : <br>{<br>&nbsp;&nbsp;"statusCode": 400,<br>&nbsp;&nbsp;"statusMsg": "Error 메세지"<br>} |
| 게시글 삭제| DELETE | /api/board/{id} | | {&nbsp;"Authorization" : "발급받은 토큰"&nbsp;}|<br>200:<br>{<br>"statusCode": 200,<br>&nbsp;&nbsp;"statusMsg": "삭제완료!"<br>}<br>400 : <br>{<br>&nbsp;&nbsp;"statusCode": 400,<br>&nbsp;&nbsp;"statusMsg": "Error 메세지"<br>} |