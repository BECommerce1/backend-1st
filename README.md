8월 11일 금 
[전달사항]
1. 프론트 연동 위해 React 설치가 필요해 일단 보류후 postman으로 api 테스트하기
2. application.yaml의 datasource에 각자 database 환경설정 필수
3. Run/Debug Configurations에서 active profiles : local
4. loggingFilter, SwaggerConfig, JpaConfig 구현 되어 있음
5. conflict 주의
   - JpaConfig : basePackage 설정
   - ExceptionControllerAdvice : 기본 InvaliedValueException, NotAcceptException, NotFoundException, AccessDenied 구현 되어 있음.
6. README.md는 main에서만 편집!



[회의내용]


[TODO]


[건의사항]







# SpringBoot - backend-1st
슈퍼코딩 half 백엔드 커머스 1팀 - 1주차 프로젝트

<br>
## 🖥️ 프로젝트 소개 : 
CRUD 게시판.
지난 4주간 배운 JAVA,  DB를 바탕으로 실제 백엔드 애플리케이션을 직접 만들어본다.

## 🕰️ 개발 기간
* 23.08.09 ~ 23.08.18

### 🧑‍🤝‍🧑 맴버구성
 - 이동현 : 좋아요 API
 - 신화정 : 댓글 API
 - 정희영 : 게시글 API 
 - 차현아 : 회원 API

### ⚙️ 개발 환경, 기술
- **JAVA 11**
- **IDE** : IntelliJ
- **Framework** : Springboot 2.7.14
- **Database** : MySQL
- **ORM** : JPA
- **DOCS** : Swagger
- **LOGGING** : Slf4J

## 📌 주요 기능
### 요구사항 명세
| 기능          | 상세            | 비고           |
| :---         |     :---:      |      ---      |
| 회원          | 1. 이메일과 비밀번호를 입력하여 회원을 가입한다.<br> 2.이메일과 비밀번호를 입력하여 로그인 한다. <br>3. 로그아웃 버튼을 눌러 로그아웃 한다. |     |
|      |        |       |

<hr>

### 회원 API
#### 회원가입 - <a href="" >상세</a>
- 
- 
#### 로그인 - <a href="" >상세</a>
- 
-
- 로그인 시 JWT 토큰 생성
