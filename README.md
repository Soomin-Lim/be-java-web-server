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
### [PR 링크](https://github.com/softeerbootcamp/be-java-web-server/pull/82)
- [x] step1 구현
- [x] step2 구현
- [x] step3 구현

**회원가입 성공 시 로그인 화면으로 리다이렉트** 하도록 구현하였고, **리팩토링**을 진행하였습니다.

### 웹 서버
1. 회원 가입 성공 시 로그인 화면으로 리다이렉트 하도록 구현
- `HttpStatusCode`에 `FOUND(302, "Not Found")` 추가
- 회원 가입 성공 시 상태 코드를 `FOUND`로 정하고, 응답 헤더에 `Location: "/user/login.html"` 헤더 추가

2. `ControllerMapper`에 `Map<String, Controller>` 타입 static 멤버변수 `cotrollerMap` 추가
- `cotrollerMap`에 `Controller`를 구현한 구현체 객체를 미리 생성하여 추가해 넣음으로써 `getController()` 메서드에서 요청 URL에 따라 적절한 `Controller`를 반환하도록 함

### 리팩토링
- 정적 팩토리 메서드를 추가하고, 기존의 생성자는 접근 제어자를 private으로 변경
- 응답이 200 OK인 경우, 응답의 status-line과 응답 헤더를 추가하는 기능을 `HttpResponse`의 `ok()` 메서드로 분리
    - 코드 중복 방지
- 응답 메시지의 바디 메시지를 `HttpResponse` 객체에 저장하고, 응답을 클라이언트에 보내는 기능을 `sendResponse()` 메서드로 분리
- `Handler` 클래스를 `Controller`로 이름 변경
- `viewPath`에 저장된 경로의 파일 객체를 불러와서 `HttpResponse`의 `body`에 저장하는 기능을 `HttpResponse`의 `setBody()` 메서드가 수행

## 추후 진행 사항
- [ ] 각 컨트롤러에서 잘못된 `URL`이 들어올 경우 `404 NOT_FOUND` 응답을 보내도록 처리
- [ ] 예외 처리
