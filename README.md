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
### [PR 링크](https://github.com/softeerbootcamp/be-java-web-server/pull/29)
- [x] step1 구현
- [x] step2 구현
- [ ] step3 구현

웹 서버 2단계의 단위 테스트와 리팩토링을 진행하였습니다. HTTP 요청 정보를 저장하는 클래스, Handler 클래스 등 클래스를 최대한 작게 분리하려 시도하였고, 프로젝트 구조와 요청마다 응답을 어떻게 처리할지는 더 고민해야 할 것 같습니다.

### 웹 서버 2단계 구현 완료
1. 단위 테스트
- `HttpRequestUtils`의 public 메서드 테스트 코드 작성
2. 리팩토링
- HTTP 요청의 정보를 담는 `HttpRequest` 클래스 생성
- HTTP 요청의 request line 정보를 담는 `HttpRequestLine` 클래스 생성
- HTTP 요청의 헤더 정보를 담는 `HttpRequestHeader` 클래스 생성
- HTTP Method를 enum 클래스로 생성하여 관리
- 요청에 따라 적절한 `Handler`를 반환하는 `HandlerMapper` 클래스 생성
- `Handler` 인터페이스 생성 후 `UserHandler`, `ResourceHandler` 클래스 생성
- `service` 패키지 생성 후 `UserService` 패키지 이동
- URL에서 쿼리스트링을 추출하는 기능을 `HttpRequestUtils`의 메서드로 분리
    - `HttpRequestUtils.getQueryString()` 메서드 추가
- `UserService`가 아닌 `UserRequestHandler`에서 `HttpRequestUtils.getQueryString()` 호출
    - `UserService`의 `signUp()` 메서드의 인자로 쿼리 스트링의 이름/값이 담긴 Map 타입의 `userInfo`를 넘기도록 함
- `ViewResolver` 클래스의 process() 메서드 로직 수정
    - 파일 확장자가 ".html"인 경우에만 뷰 파일의 위치 경로를 templates 디렉토리로 지정하고, 그렇지 않으면 static 디렉토리로 지정함

## 추후 진행 사항
1. 3단계 구현
- [ ] `HttpResponse` 객체 생성
- [ ] `Handler`에 따라 `HttpResponse`에 적절한 정보 담기
    - [ ] 요청 헤더의 Accept 헤더에 따라 응답 헤더의 `Content-type` 헤더 값 변경하기
