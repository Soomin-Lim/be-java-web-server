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
- [x] step1 구현 - index.html 응답
- [x] step2 구현 - GET으로 회원가입
- [x] step3 구현 - stylesheet 지원
- [x] step4 구현 - POST로 회원 가입
- [x] step5 구현 - 쿠키를 이용한 로그인
- [x] step6 구현 - 동적인 HTML
- [x] step7 구현 - 한 줄 게시판 구현

### Step7 요구사항 구현 완료
**한 줄 게시판 구현**

**1. 로그인한 사용자만 글을 쓸 수 있다.**
- [x] `index.html` 화면에서 "질문하기" 버튼 삭제 후 <!-- 메모장 글 쓰기 버튼 구현 --> 주석 추가
- [x] `LoginView`에서 <!-- 메모장 글 쓰기 버튼 구현 --> 주석을 버튼으로 치환

**2.  로그인 여부와 상관없이 모든 사용자는 게시글을 볼 수 있다.**
- [x] `Memo` 클래스 생성
- [x] `MemoDatabase` 클래스 생성
    - [x] 메모 생성과 조회에 필요한 메서드 구현
- [x] `MemoService` 클래스 생성
    - [x] 메모 생성과 조회에 필요한 메서드 구현
- [x] `MemoController` 클래스 생성
    - [x] 메모 생성과 조회에 필요한 메서드 구현
- [x] `ControllerMapper` 클래스의 `controllerMap`에 `MemoController` 객체를 추가
    - [x] `MemoService`는 서버에서 하나의 객체만 필요하므로 싱글톤 패턴 적용
    - [x] `MemoController`의 `prefix`는 `"/memo"`로 설정
- [x] `memo/form.html`에서 메모의 내용을 작성하는 폼 태그의 아이디를 `contents`에서 `content`로 변경
- [x] `index.html`을 동적으로 생성해야 하므로 `DynamicResourceController`의 `process()` 메서드 내부 로직 변경
    - 로그인한 상태가 아니고 요청 URL이 `"/index.html"`이 아니면, 정적 파일 요청이므로 `StaticResourceController`를 호출
    - 홈 화면(`index.html`)을 보여줄 때 `HomeView.render()`를 호출하여 홈 화면을 동적으로 생성
        - 홈 화면에 메모 목록을 출력
    - 사용자가 로그인한 상태라면 `LoginView.render()`를 호출하여 화면을 동적으로 생성

**3. MySQL 데이터베이스와 연동**
- [x] MySQL 연동을 위해 `build.gradle` 파일에 의존성 추가
- [x] 데이터베이스와 커넥션을 생성하는 `DbConnectionManager `클래스 생성
- [x] `UserDatabase`와 `MemoDatabase`가 `DbConnectionManager`를 통해 얻은 `Connection`을 통해 데이터베이스에 접근, 객체를 DB에 저장하고 관리하는 기능 구현
- [x] 외부 데이터베이스 연결 정보는 `DbConnection `클래스에서 따로 보관하도록 함
    - `DbConnection `클래스는 git을 통해 관리하지 않음
- [x] `sql/ddl.sql` 파일: `user`, `memo `테이블 생성 쿼리문 저장하여 관리

### 리팩토링
- [x] 기존의 `Database `클래스를 `UserDatabase`로 이름 변경
- [x] 사용자 아이디로 사용자를 조회하는 기능을 `UserService`에 추가하고, `UserService`에 싱글톤 패턴 적용
    - `UserService `객체는 `UserController`, `DynamicResourceController `클래스 모두에 필요하고, 서버에서 `UserService` 객체는 하나만 필요하므로 `UserService `클래스를 싱글톤 패턴 적용
- [x] `resources/templates` 디렉토리 내의 `qna `디렉토리를 `memo`로 이름 변경
- [x] `HomeLoginView `클래스를 `LoginView `클래스로 이름 변경

