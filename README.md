# SpringBoot-E-Commerse
Spring Boot와 kafka를 활용한 온라인 커머스 서비스 

## Tech Stack
- Language : JAVA8
- Framework : Spring Boot
- DB : Mysql (MyBatis)
- Message Queue : Kafka

## Project
- 로그인, 상품등록, 구매, 리뷰와 같은 일반적인 쇼핑몰 기능의 백엔드 구현
- Kafka를 활용하여 유저의 주문, 리뷰 요청의 비동기 처리
- 각 서비스들을 스케일 아웃 가능하게 구현

## Architecture
![Arch](https://user-images.githubusercontent.com/29014659/146839779-dba4be4c-9946-44cf-8e2c-4f4000eec7ed.png)

## MSA
[API-Server](https://github.com/kimyoungi99/SpringBoot-E-Commerce/tree/main/api-server)
[Order](https://github.com/kimyoungi99/SpringBoot-E-Commerce/tree/main/order-server)
[Count](https://github.com/kimyoungi99/SpringBoot-E-Commerce/tree/main/count-server)
[Review](https://github.com/kimyoungi99/SpringBoot-E-Commerce/tree/main/review-server)
