# SpringBoot-E-Commerce
Spring Boot와 kafka를 활용한 온라인 커머스 서비스 

## 목표
- 로그인, 회원가입, 상품 등록, 구매 등 기본적인 커머스 백엔드 기능 구현
- 분리된 DB를 가지는 마이크로 서비스
- 카프카를 활용하여 데이터 일관성 보장


## Architecture
![Arch](https://user-images.githubusercontent.com/29014659/154828633-018ac876-ba5a-4b98-b0b3-6dba21309d8c.png)

## TODOS
- stock-service mysql lock 부분 프록시 패턴 적용
- 카프카 메세지 전달 보장성 DB 업데이트 부분 exactly once로 수정 구현하기
