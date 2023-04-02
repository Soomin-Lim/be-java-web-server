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
### [PR 링크](https://github.com/softeerbootcamp/be-java-web-server/pull/14)
- [x] step1 구현
- [ ] step2 구현
- [ ] step3 구현

웹 서버 1단계 요구사항에 맞춰 기능 구현을 완료했으며, 웹서버 2단계의 기능 요구사항은 완료했지만 리팩토링과 단위테스트를 미구현하였다.

### 웹 서버
1. 웹 서버 1단계 구현 완료
- [x] `http://localhost:8080/index.html` 로 접속했을 때 `src/main/resources/templates` 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
2. 웹 서버 2단계 구현 중
- [x] GET으로 회원가입 기능 구현
    - [x] 사용자(User)와 관련된 요청을 처리하는 `UserRequestHandler` 클래스 생성
    - [x] 비즈니스 로직을 처리하는 UserService 클래스 생성
        - [x] 쿼리스트링에서 사용자가 입력한 값을 파싱하여 파싱한 값을 바탕으로 `User` 객체를 생성
        - [x] 생성한 User 객체를 `Database`에 저장
    - [x] 회원가입을 완료하면 로그인 폼으로 리다이렉트하도록 구현
- [x] 사용자 요청에 따라 적절한 파일 경로를 반환하는 `ViewResolver` 클래스 생성
- [ ] 단위 테스트

## 추후 진행 사항
1. TODO
- `RequestHandler`의 `searchRequestHandler()` 메서드 수정
- `UserRequestHandler`의 `handle()` 메서드 수정
- `ViewResolver`의 `process()` 메서드 수정
2. 단위 테스트
3. 3단계 구현
