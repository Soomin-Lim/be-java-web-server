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
### [PR 링크](https://github.com/softeerbootcamp/be-java-web-server/pull/56)
- [x] step1 구현
- [x] step2 구현
- [ ] step3 구현

웹 서버 3단계의 목표인 **stylesheet 지원 기능 구현**을 완료하였고, **리팩토링**과 **단위 테스트**를 진행하였습니다.

### 웹 서버 3단계 구현 완료
1. HTTP 응답 메시지를 저장하는 클래스 생성
- HTTP 응답 정보를 저장하는 `HttpResponse` 클래스 생성
    - HTTP 응답의 status-line 정보를 담는 `HttpStatusLine` 클래스 생성
    - HTTP 요청의 상태 코드를 관리하는 `HttpStatusCode` enum 클래스 생성
- view 패키지 생성 후 `ViewResolver` 클래스를 view 패키지로 이동
2. 각 `Handler`에서 요청 메시지의 status-line을 생성하고, 요청 헤더를 추가하도록 함
- 요청 헤더의 값을 불러오거나 요청 헤더를 추가하는데 필요한 메서드들을 `HttpRequest`, `HttpResponse`, `HttpHeader` 클래스에 추가함
3. 요청 메시지를 출력하는 기능을 `RequestLine`이 아니라 `HttpResponse`에서 수행하도록 함
- 요청 메시지 출력을 위해 `HttpStatusLine`, `HttpHeader` 클래스에 toString() 메서드 오버라이드

### 리팩토링
- `HttpRequestHeader` 클래스를 `HttpHeader`로 이름 변경
- `HttpRequestLine`에서 Method, URL, HTTP version을 추출하는 기능을 `HttpRequestUtils`의 메서드가 아닌 `HttpRequestLine`의 생성자에서 수행
- `HttpRequestLine`의 `version` 멤버 변수를 `httpVersion`으로 변수명 변경

### 단위 테스트
- `HttpRequestUtils`의 `parseRequestHeader()` 메서드를 검증하는 테스트 코드 작성

## 추후 진행 사항
1. 리팩토링
- [ ] HttpResponse에 요청 헤더를 추가할 때 중복되는 로직 따로 메서드로 분리하기
- [ ] 요청에 따라 적절한 Handler를 매핑하는 로직 고민하기
