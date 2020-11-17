
# 구해줘! 홈트
2020-2 졸업프로젝트
# 프로젝트 요약
사용자 맞춤형 홈 트레이닝 서비스를 제공해주는 웹 사이트
- 사용자의 운동 경험, 나이, 성별에 맞게 코스를 추천해주고, 사용자의 아바타가 성장하는 것을 보면서
동기부여를 획득할 수 있음.

# 주제 선정 배경
* 코로나 시대에 어울리는 프로젝트
* 사회적 거리두기로 인한 헬스장 휴관 사태
* 웹 프로젝트로 구현 가능한 것
### 홈트레이닝 웹 프로젝트

# 기술 스택
- Spirng Boot 2.3.4 RELEASE (Spring Data Jpa, Spring Mail, Spring OAuth2 Client)
- Thymeleaf 3.0.11 RELEASE
- Java 11
#
- MySQL 8.0.21
- Docker 2.3.0.5
  - Redis 6.0.7
# 
- Gradle 6.6.1
- MapStruct 1.3.1 Final
#
- NGINX 1.19.3
- Tomcat 9.0.38

# 주요 아이디어
* 사용자 맞춤형 코스 추천
* 코스, 운동 정보 제공
* 아바타와 함께 성장
* SNS 로그인

# 실행 흐름
1. 사용자가 회원가입을 함 (SNS or 일반 회원가입)
2. 원하는 코스를 선택해서 코스 추가
3. 코스에 있는 그 날의 운동을 진행
4. 코스 완료(30일)시 경험치가 축적되고, 일저 경험치 만족시 아바타 레벨업

# 주요 기술 목표
* MapStruct로 ObjectMapper대신 DTO와 Entity 맵핑
* JPA를 이용해 각 테이블의 연관 관계 맵핑(ManyToOne, OneToMany)
* Spring Security로 OAuth 2.0를 구현
* jQuery 제외. VanillaJS로 구현
* Redis를 토큰 저장소로 사용
* JSP 대신 Thymeleaf 템플릿 엔진
* Façade 패턴, DDD 패턴 최대한 구현 하도록 노력

# 프로젝트 구조
![스크린샷 2020-11-18 오전 6 16 24](https://user-images.githubusercontent.com/44764810/99451471-97ca8600-2965-11eb-9614-7632da14eb38.png)

# 메뉴 구조
![스크린샷 2020-11-18 오전 6 13 27](https://user-images.githubusercontent.com/44764810/99451187-2d194a80-2965-11eb-94aa-5b9f9213da4a.png)

# DB 구조
![image](https://user-images.githubusercontent.com/44764810/99451277-56d27180-2965-11eb-8ae8-53fe2dd7168c.png)

# 기능 설명 
![스크린샷 2020-11-18 오전 6 17 48](https://user-images.githubusercontent.com/44764810/99451610-c8aabb00-2965-11eb-9eb4-324a092c5f06.png)
메인 페이지 입니다.

![스크린샷 2020-11-18 오전 6 19 05](https://user-images.githubusercontent.com/44764810/99451714-f7c12c80-2965-11eb-869f-38bbc0d9faeb.png)
로그인을 안했을 시애 리디렉션 되는 페이지 입니다.

![스크린샷 2020-11-18 오전 6 19 59](https://user-images.githubusercontent.com/44764810/99451806-18898200-2966-11eb-8c7f-1231e059a59f.png)
로그인 페이지 입니다. SNS 로그인 기능을 구현했습니다.

![스크린샷 2020-11-18 오전 6 20 39](https://user-images.githubusercontent.com/44764810/99451868-2f2fd900-2966-11eb-956e-351dd167ef17.png)
코스 리스트를 보여주는 페이지입니다.

![스크린샷 2020-11-18 오전 6 21 04](https://user-images.githubusercontent.com/44764810/99451910-3d7df500-2966-11eb-9320-e684e2128605.png)
코스 상세 정보 페이지 입니다.

![스크린샷 2020-11-18 오전 6 21 35](https://user-images.githubusercontent.com/44764810/99451978-4ff82e80-2966-11eb-9b7b-7b91e27fbbb6.png)
사용자가 선택한 운동 코스를 진행하는 페이지 입니다.

![스크린샷 2020-11-18 오전 6 22 24](https://user-images.githubusercontent.com/44764810/99452073-6ef6c080-2966-11eb-9e5d-0e5e99afd2e7.png)
운동 리스트 페이지 입니다. 

![스크린샷 2020-11-18 오전 6 24 06](https://user-images.githubusercontent.com/44764810/99452252-a9f8f400-2966-11eb-923e-3341298a2ca7.png)
마이 프로필 메뉴 접근시 비밀번호 확인 페이지 입니다. SNS 로그인 유저는 이 페이지를 스킵합니다.

![스크린샷 2020-11-18 오전 6 57 32](https://user-images.githubusercontent.com/44764810/99455539-550bac80-296b-11eb-9515-c30c6167c4da.png)
프로필 수정 페이지 입니다.

![스크린샷 2020-11-18 오전 6 59 02](https://user-images.githubusercontent.com/44764810/99455696-8ab09580-296b-11eb-9685-b8c7ee641245.png)
계정 찾기 페이지 입니다.

![스크린샷 2020-11-18 오전 7 00 19](https://user-images.githubusercontent.com/44764810/99455818-b895da00-296b-11eb-8b9f-23abc66f982f.png)
비밀번호 재설정 페이지입니다.
