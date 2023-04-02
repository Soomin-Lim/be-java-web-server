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
### [PR 링크](https://github.com/softeerbootcamp/be-java-web-server/pull/145)
- [x] step1 구현 - index.html 응답
- [x] step2 구현 - GET으로 회원가입
- [x] step3 구현 - stylesheet 지원
- [x] step4 구현 - POST로 회원 가입
- [x] step5 구현 - 쿠키를 이용한 로그인
- [x] step6 구현 - 동적인 HTML

## 2022.01.18 진행 상황
Step6(**동적인 HTML을 응답 기능**) 구현을 완료하였고, Step5의 **세션을 활용하여 로그인하는 기능을 수정**했습니다.

### Step6 요구사항 구현 완료
**동적인 html 생성**
1. http://localhost:8080/user/list  페이지 접근시 로그인하지 않은 상태일 경우 로그인 페이지(`login.html`)로 이동한다.
- [x] `httpRequest`의 `isLogin()` 메서드에서 `SessionManager`를 통해 로그인 여부를 반환하는 로직 추가
- [x] `SessionManager` 클래스에 `hasSessoin()` 메서드 추가
2.  사용자가 로그인 상태일 경우 http://localhost:8080/user/list 에서 사용자 목록을 출력한다.
- [x] `UserService` 클래스에 사용자 목록을 반환하는 `findUserList()` 메서드 추가
- [x] 사용자 목록을 출력하는 `UserController`의 `showUserList()` 메서드가 `list.html` 파일을 활용하여 사용자 목록 페이지를 동적으로 생성하도록 구현
    - [x] `UserListView` 클래스를 생성하고, `render()` 메서드는 사용자 목록(`users`)를 인자로 받아와 페이지를 렌더링하는 기능 수행
    - [x] `FileUtils` 클래스를 생성하고, 파일 이름을 인자로 받아 실제 파일을 읽어오는 `loadFile()` 메서드 추가
    - [x] `UserController`의 `showUserList()` 메서드는 `UserListView`가 렌더링한 페이지를 받아와 응답 메시지를 작성하도록 수정

3. 사용자가 로그인 상태일 경우 `/index.html`에서 사용자 이름을 표시해 준다.
- [x] `StaticResourceController`의 `process()` 메서드에서 url이 `"/"`이거나 `"/index.html"`이고, 로그인 상태인 경우 `HomeLoginController`의 `process()` 메서드 호출
- [x] `index.html` 파일을 동적으로 생성하는 `HomeLoginView` 클래스 생성
- [x] `HomeLoginController` 컨트롤러 클래스 생성
    - [x] 싱글톤 패턴 적용
    - [x] `HomeLoginView`가 렌더링한 페이지를 받아와 응답 메시지를 작성하도록 수정

### Step5 기능 수정
- [x] 세션 정보를 저장하는 `Session` 클래스 생성
- [x] 세션에 데이터를 보관하고 조회할 때 같은 상수가 중복되어 사용되므로, 세션 관련 상수를 보관하는 추상 클래스 `SessionConst` 클래스 생성

### 리팩토링
- [x] `FrontController` 클래스의 `process()` 메서드 이름을 `service()`로 변경

## 추후 진행 사항
- [ ] `index.html` 파일 외의 html 파일일 때도 로그인 상태일 때 사용자 이름을 표시하도록 구현 예정
- [ ] 단위 테스트

---
## 2022.01.19 진행 상황
Step6의 **동적인 HTML 응답 기능을 수정**하였고, **단위 테스트**를 작성하였습니다.

### Step6 기능 수정
**동적인 html 생성**
- [x] `index.html` 파일 외의 html 파일일 때도 로그인 상태일 때 사용자 이름을 표시하도록 구현
    - [x] `HomeLoginController` 클래스를 `DynamicResourceController`로 이름 변경
    - [x] `ControllerMapper`에서 매핑되는 컨트롤러를 찾을 때, URL이 `"/"`이거나 요청 파일의 확장자가 `".html"`일 때 `DynamicResourceController`를 반환하도록 수정
    - [x] `DynamicResourceController`의 `process()` 메서드 내부 로직 변경
        - 로그인하지 않았으면 S`taticResourceController.process()` 호출
        - 화면을 렌더링하기 위해 `HomeLoginView.render()`를 호출할 때 인자로 URL도 같이 넘겨주도록 변경
- [x] `index.html` 파일의 로그인, 회원가입 버튼의 <a> 태그의 href 속성을 변경
    - `HomeLoginView`에서 html 파일 내용을 바꿀 때, `index.html`을 포함한 모든 html 파일에서 대체되는 부분을 통일하기 위해 변경
    - `"user/login.html"` -> `"../user/login.html"`
    - `"user/form.html"` -> `"../user/form.html"`

### 단위 테스트
- [x] `ViewResolver` 클래스의 테스트 코드 작성
- [x] `Session` 클래스의 테스트 코드 작성
- [x] `SessionManager` 클래스의 테스트 코드 작성 
