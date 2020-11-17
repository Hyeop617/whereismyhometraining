# 구해줘! 홈트
2020-2 졸업프로젝트
# 프로젝트 요약
사용자 맞춤형 홈 트레이닝 서비스를 제공해주는 웹 사이트
- 사용자의 운동 경험, 나이, 성별에 맞게 코스를 추천해주고, 사용자의 아바타가 성장하는 것을 보면서
동기부여를 획득할 수 있음.

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

# 주요 기술 설명
* MapStruct로 ObjectMapper대신 DTO와 Entity 맵핑
* JPA를 이용해 각 테이블의 연관 관계 맵핑
* Spring Security로 OAuth 2.0를 구현하도록 노력
* jQuery대신 VanillaJS 
