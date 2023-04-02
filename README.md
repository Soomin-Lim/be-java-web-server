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
### [PR 링크](https://github.com/softeerbootcamp/be-java-web-server/pull/92)
- [x] step1 구현
- [x] step2 구현
- [x] step3 구현

**프로젝트 구조를 개선**하고 **리팩토링**을 진행하였습니다.

### 웹 서버 프로젝트 구조 개선
1. 요청에 따라 적절한 컨트롤러를 반환할 때, 정적 파일인 경우 파일 확장자를 검사하여 허용되는 확장자인 경우 `StaticResourceController`를 반환하도록 하고, 매핑할 수 있는 컨트롤러가 없을 경우 예외를 발생하도록 함
- `StaticResourceController`에 `supportedFileExtensions` 정적 멤버 변수를 추가해 허용 가능한 파일 확장자를 저장함
- `StaticResourceController`에 허용 가능한 `URL`인지 판별하는 `isSupported()` 메서드 추가
- `ControllerMapper`에서 `StaticResourceController.isSupported()` 메서드를 호출하여 허용 가능한 `URL`이면 `StatcResourceController`를 반환하도록 함

2. `"/user"`를 `UserController`에 `PREFIX` 상수로 정의하여 관리
- `controllerMapper`의 `key`는 해당 컨트롤러의 `PREFIX`

3. `HttpResponse` 클래스에 응답에 비어있는 바디 메시지를 저장하는 `setEmptyBody()` 메서드 추가

4. `makeBodyMessage()` 메서드 내에서 바디 메시지가 `null`이면 예외를 발생시키도록 함

5. 리다이렉트하는 경우 status line과 header를 추가하는 기능을 `HttpResponse` 클래스에 `redirect()` 메서드를 추가해 수행하도록 함

### 리팩토링
- `ResourceController` 클래스를 `StaticResourceController`로 이름 변경
- `httpResponse` 클래스의 `setBody()` 메서드를 `makeBodyMessage`로 이름 변경

## 추후 진행 사항
- [ ] 회원가입 시 아이디가 중복되는 경우 예외 처리
- [ ] 잘못된 요청 URL이 들어올 경우 예외 처리
