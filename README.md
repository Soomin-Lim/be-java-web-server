# java-was-2022
Java Web Application Server 2022


## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

---

## 프로젝트 진행 전 학습한 내용

### 입출력 스트림

입출력 스트림은 자바에서 입출력을 수행하기 위해, 두 대상을 연결하고 데이터를 전송하는데 사용되는 연결통로이다.

### 바이트 기반 스트림 - InputStream, OutputStream

- 스트림은 Byte 단위로 데이터를 전송한다.
- 모든 바이트 기반 스트림은 InputStream, OutputStream 을 상속한다.

#### 보조 스트림
- 보조 스트림은 입력 성능을 향상시키기거나 새로운 기능을 추가하기 위해 사용한다.
- 보조 스트림은 실제 데이터를를 주고받는 스트림은 아니기 때문에 데이터를 입출력할 수 있는 기능은 없다. 그래서 스트림을 먼저 생성한 다음 보조스트림을 생성해야 한다.
- `BufferedInputStream, BufferedOutputStream` : 버퍼를 이용하여 입출력 성능을 향상시킨다.
- `DataInputStream, DataOutputStream` : int, float과 같은 기본형 단위로 데이터를 처리할 수 있다.

### 문자 기반 스트림 - Reader, Writer
- 문자 데이터를 다루는 데 사용되는 스트림이다.
- 모든 문자 기반 스트림은 Reader, Writer를 상속한다.

#### InputStreamReader, OutputStreamReader
- 바이트 기반 스트림을 문자 기반 스트림으로 연결시켜주는 역할을 한다.

#### BufferedReader, BufferedWriter
- 버퍼를 이용해서 입출력의 효율을 높인다.
- `BufferedReaader`의 `readLine()`을 사용하면 데이터를 라인단위로 읽을 수 있다.

---

## 프로젝트 진행 상황
### [PR 링크](https://github.com/softeerbootcamp/be-java-web-server/pull/124)
- [x] step1 구현 - index.html 응답
- [x] step2 구현 - GET으로 회원가입
- [x] step3 구현 - stylesheet 지원
- [x] step4 구현 - POST로 회원 가입
- [x] step5 구현 - 쿠키를 이용한 로그인
- [ ] step6 구현 - 동적인 HTML

Step5(**쿠키와 세션을 이용한 로그인**) 구현을 완료하였고, Step6(**동적인 HTML을 응답**) 구현 중입니다. 또한 `Controller` 인터페이스를 변경하고 **리팩토링**을 진행하였습니다.

### `Controller` 인터페이스 변경
`Controller` 인터페이스의 `process()` 메서드 반환타입을 `String`에서 `void`로 변경하였습니다.

원래는 `Controller`의 `process()` 메서드의 반환타입을 `String`으로 정하여 응답에 사용할 뷰 파일 이름을 반환하도록 작성하였습니다. 이 때, 리다이렉트 응답인 경우 뷰 파일이 필요하지 않기 때문에 `Controller`의 `process()`가  "" 빈 문자열을 반환하도록 하였습니다. 하지만 실제 파일 경로를 통해 응답할 파일을 읽어오는 경우는 `StaticResourceController`만 수행하는 기능이기 때문에 인터페이스 메서드의 반환타입을 `void`로 변경하고, 응답에 보낼 파일을 읽어오는 기능은 `StaticResourceController`클래스의 `process()` 메서드가 수행하도록 수정하였습니다.

- `UserController`, `StaticResourceController`의 `process()` 메서드 반환타입을 `void`로 변경
- `StaticResourceController`의 `process()` 내부 로직 변경
    - `ViewResolver.resolveViewName()`을 호출해 실제 파일 경로를 가져옴
    - `Files.readAllBytes()`를 호출해 응답에 보낼 파일을 읽어옴
    - 정상적으로 파일을 읽어오면 `response.ok()`, `response.setBodyMessage()`를 호출해 응답 헤더와 바디 메시지를 생성
    - `IOException`이 발생하면 `response.notFound()`를 호출하여 404 응답 메시지를 생성
- `HttpResponse` 클래스의 `makeBodyMessageWithFile()` 메서드 삭제
- `HttpResponse` 클래스에 `byte[]`를 인자로 받는 `setBodyMessage()` 메서드 추가

### Step5 요구사항 구현 완료
**쿠키와 세션을 이용한 로그인**
- [x] 요청 URL이 "/user/login"이고 POST 요청인 경우 로그인 기능을 실행하도록 `UserController`에 if 분기문 추가
- [x] `UserService` 에 로그인 기능을 수행하는 `login()` 메서드 추가
    - [x] 로그인 시 사용자가 유효하지 않은 아이디를 입력하거나, 일치하지 않는 아이디와 비밀번호를 입력하였을 경우 UserService의 login()에서 UserValidationException 예외를 발생시키도록 함
    - [x] 정상적으로 로그인에 성공하면 해당 아이디의 User 객체를 반환
- [x] 로그인에 성공하면 SessionManager 클래스를 통해 생성된 세션ID를 받아 응답에 Set-Cookie 헤더를 추가하고, "/index.html"로 리다이렉트하도록 구현
- [x] 로그인에 실패하면 "/user/login_failed.html"로 리다이렉트하도록 구현
- [x] 로그인 시 사용자가 입력 form에서 아무 값도 입력하지 않아 NullValueException이 발생하면, "/user/login.html"로 리다이렉트하도록 구현
- [x] 세션을 저장하고 관리하는 `SessionManager` 클래스를 생성
    - [x] `SessionManager` 클래스에 세션을 저장하는 static 멤버변수 `sessionStore` 추가
        - [x] `sessionStore`는 세션ID와 사용자ID를 각각 키, 값으로 저장함
    - [x] SessionManager의 createSession()은 세션ID를 생성하고 sessionStore에 저장한 다음 생성된 세션ID를 반환

### Step6 요구사항 구현 중
**동적인 html**
- [x] `UserController` 클래스에 사용자 목록을 출력하는 html을 동적으로 생성하여 응답할 `showUserList()` 메서드 추가
- [x] 사용자 목록 페이지(`/user/list`) 접근시 로그인하지 않은 상태일 경우 로그인 페이지(`login.html`)로 리다이렉트 하는 기능 구현
- [x] `HttpRequest` 클래스에 로그인 여부를 반환하는 `isLogin()` 메서드 추가
    - [ ] 세션 매니저를 사용하여 로그인 여부 반환

### 리팩토링
- [x] `UserController`의 final 멤버 변수로 `UserService` 타입 변수를 추가해 외부에서 `UserService` 객체를 만들어 주입하도록 함
    - 중복 코드 방지
    - 매번 `UserService` 객체를 생성하는 대신 `UserController`가 생성됨과 동시에 `UserService` 객체를 만들어 생성자 주입함으로써 메모리 낭비 방지
- [x] `UserController`의 `process()`에서 회원가입을 처리하는 로직과 로그인을 처리하는 로직을 각각 메서드로 분리
    - `createUser()`: 회원가입 처리
    - `login()`: 로그인 처리

### 예외 처리 수정
- [x] 회원가입 시 사용자가 입력 form에서 아무 값도 입력하지 않아 `NullValueException`이 발생하여 회원가입에 실패하면, `/user/form_failed.html` 대신 `/user/form.html` 화면으로 리다이렉트하도록 구현

## 추후 진행 사항
- [ ] 웹 서버 6단계 구현: 동적인 html
    - [ ] 사용자가 로그인 상태일 경우 /index.html에서 사용자 이름을 표시해 준다.
    - [ ] 사용자가 로그인 상태가 아닐 경우 /index.html에서 [로그인] 버튼을 표시해 준다.
    - [ ] 사용자가 로그인 상태일 경우 http://localhost:8080/user/list 에서 사용자 목록을 출력한다.
    - [ ] http://localhost:8080/user/list  페이지 접근시 로그인하지 않은 상태일 경우 로그인 페이지(login.html)로 이동한다.
- [ ] 단위 테스트
