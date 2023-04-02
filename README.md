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
### [PR 링크](https://github.com/softeerbootcamp/be-java-web-server/pull/112)
- [x] step1 구현 - index.html 응답
- [x] step2 구현 - GET으로 회원가입
- [x] step3 구현 - stylesheet 지원
- [x] step4 구현 - POST로 회원 가입
- [ ] step5 구현 - 쿠키를 이용한 로그인
- [ ] step6 구현 - 동적인 HTML

웹 서버 4단계인 **Post로 회원가입 기능 구현**을 완료하였고, 저번주에 미처 하지 못했던 **예외 처리**와 404 Not Found 응답을 보내는 기능을 구현하였습니다.

예외처리를 쉽게 하기 위해 `FrontController` 클래스를 생성하고, `RequestHandler`의 `run()` 메서드에서 `HttpRequest`, `HttpResponse` 객체 생성 후 수행하는 로직을 `FrontController`의 `process()` 메서드로 이동하였습니다.
매핑되는 컨트롤러가 없는 경우, 컨트롤러 내에서 매핑되는 URL이 없는 경우, `ViewResolver`를 통해 받은 파일 경로에 파일이 없는 경우 404 Not Found 응답을 보내도록 구현하였습니다.

또한 회원가입 시 발생한 예외를 처리하고 `/user/form_failed.html`로 리다이렉트 하는 기능을 구현하였습니다.

### Step4 요구사항 구현
**Post로 회원가입**
- `HttpRequest` 클래스 수정사항
    - [x] `HttpRequest`에 바디 메시지를 저장하는 `String body` 멤버변수 추가
    - [x] `BufferedReader` 객체에서 `contentLength`만큼 읽어들여 바디 메시지를 반환하는 `readBodyMessage()` 메서드 추가
    - [x] `HttpRequest`의 정적 팩토리 메서드 `from()` 내부 로직 수정
        - [x] `httpHeader`에서 `Content-Length` 헤더의 값을 가져와 값이 `null`이 아닌 경우 `readBodyMessage()` 호출
    - [x] `readHttpRequestHeader()` 메서드에서 `header`가 없는 경우 `null` 대신 빈 `HttpHeader` 객체를 반환하도록 함
    - [x] 바디 메시지를 가져오는 `getBody()` 메서드 추가
    - [x] `HttpRequest` 클래스에 해당 요청의 HTTP Method를 가져오는 `getMethod()` 메서드 추가
- [x]  회원가입 시 쿼리 스트링이 아닌 요청의 바디 메시지를 읽어오도록 변경
- [x] 회원가입 성공 시 로그인 화면이 아닌 `/index.html`로 리다이렉트 하도록 구현
- [x]  `/user/create` 요청 시 POST 메서드인 경우에만 회원가입 기능이 동작하도록 구현

### 예외 처리
1.  `FrontController`의 `process()` 메서드에서 `ControllerNotFoundException`, `UrlNotFoundException`, `FileNotFoundException `예외를 try-catch 문으로 잡아내어 예외가 발생할 경우  `httpResponse.notFound()` 메서드를 호출하여 404 Not Found 응답을 보내도록 함
- [x] 매핑되는 컨트롤러가 없을 시 `ControllerNotFoundException`이 발생하도록 구현
    - [x] RuntimeException을 상속하는 `ControllerNotFoundException` 클래스 생성
- [x] 컨트롤러 내에서 매핑되는 URL이 없을 시 `UrlNotFoundException`이 발생하도록 구현
    - [x] RuntimeException을 상속하는 `UrlNotFoundException` 클래스 생성
- [x] 바디 메시지를 생성할 때, 인자로 받은 파일 경로에 파일이 없을 경우 `FileNotFoundException`이 발생하도록 구현
- [x] httpResponse클래스에 notFound() 메서드를 추가하여 404 Not Found 응답을 생성하는 기능을 구현

2. 회원가입 시 아이디가 중복되었거나 입력 폼에 아무 값도 입력하지 않은 경우 발생하는 예외를 처리
- [x] 회원가입 시 아이디 중복인 경우 `UserValidationException `예외를 발생시키도록 구현
    - [x] 회원가입 시 아이디가 중복될 경우 발생하는 예외인 `UserValidationException `클래스 생성
- [x] 사용자가 입력 form에 아무 값도 입력하지 않았을 경우 `NullValueException `예외를 발생시키도록 구현
    - [x] 사용자가 입력 form에 아무 값도 입력하지 않았을 경우 발생하는 `NullValueException `예외 클래스 생성
    - [x] 사용자가 입력 form에 아무 값도 입력하지 않아 `HttpRequestUtils `클래스의 `parseBodyMessage()` 메서드에서 `ArrayIndexOutOfBoundsException`이 발생할 경우 `NullValueException `예외를 발생시키도록 구현
- [x] 회원가입에 실패했을 때 보여줄 `form_failed.html` 파일 추가
- [x] 회원가입에 실패하여 예외가 발생할 경우 `/user/form_failed.html` 화면으로 리다이렉트하도록 구현

## 추후 진행 사항
- [ ] 웹 서버 5단계 구현: 쿠키와 세션
- [ ] 웹 서버 6단계 구현: 동적인 html
- [ ] 단위 테스트
