# Dankan-BackEnd

### 프로젝트 소개
백엔드 개발 : 총 2인

backend tech : Spring Boot, Spring Security, Spring Data JPA, Spring MVC, Hibernate, Oauth2, Query DSL

devops tech : Mysql, RDS, EC2, DynamoDB, Redis 

단칸은 자취생들이 원하는 매물을 쉽게 확인해 볼 수 있는 플랫폼입니다. 실제 설문조사를 통해 대학생들이 방을 구하면서 어려웠던 점 중 매물을 볼 수 있는 플랫폼의 부족을 확인하였습니다.
매물 확인 플랫폼 부족의 니즈에 맞게, 단칸은 대학교 주변 매물을 한눈에 쉽게 확인할 수 있도록 기획된 프로젝트입니다.

### ERD
![dankanERD](https://github.com/khj1998/dankan-source/assets/80220062/33ce4d1d-7f2c-46d7-b8e7-ffc3af5acdfb)

### BackEnd Architecture
![단칸 아키텍처](https://github.com/Nestsoft-Team/Dankan-Backend/assets/80220062/6f5046dd-005e-4b54-b771-def090c654f9)


### Commit Convention

`Feat:` 새로운 기능을 추가할 경우

`Fix:` 버그를 고친 경우

`HotFix:` 급하게 치명적인 버그를 고쳐야하는 경우

`Refactor:` 프로덕션 코드 리팩토링

`Comment:` 필요한 주석 추가 및 변경

`Docs:` 문서를 수정한 경우

`Test:` 테스트 추가, 테스트 리팩토링(프로덕션 코드 변경 X)

`Chore:` 빌드 태스트 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경 X)

`Remove:` 파일을 삭제하는 작업만 수행한 경우

`Except:` 예외 처리 코드 추가 또는 변경

<hr>

### Code Convention
1. 변수, 함수, 인스턴스명: 카멜케이스
2. 함수명: 동사+명사
3. Boolean 변수명: 조동사+flag 종류. ex) isNum, hasNum
4. 디자인패턴: 싱글톤
5. tab의 최대 depth는 4로 제한
6. 깃허브 커밋 컨벤션을 지킨다.
7. 함수에 대한 주석
