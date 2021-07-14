# Simple Order App

## 개요
- 간단소개 : 상품을 장바구니 주문 결제가 가능한 웹어플리케이션
- 개발 인원 : 1명
- 담당 역할 : 주제선정, 분석/설계, 인프라 구축 및 레이아웃, 개발, 테스트, 시연
- 주요 기능 : 로그인/회원가입, 장바구니, 주문, 결제
- 개발 환경 : Springboot 2.4.8, Spring Security, Spring Data Jpa, OpenJdk1.8
- 데이터베이스 : H2 inmemory
- 형상관리: git
- 특징 : REST API / Spring Boot / JPA / Dockerfile 를 통한 이미지 구성 / Spring Security + Jwt를 활용한 인증

## Installation

- 설치 및 실행 방법
```plian
$ git clone https://github.com/seongbinko/simple-order-app.git
$ cd simple-order-app
$ java -jar order-1.0.3-SNAPSHOT-20210714122042.jar
$ localhost:8080/ 메인페이지로 접속
```

## DB 설계
![스크린샷 2021-07-11 오후 11 31 56](https://user-images.githubusercontent.com/60464424/125199254-4e39fd00-e2a0-11eb-8f6a-9816913e63d0.png)

- 구조
    - member (유저 테이블)
    - order (주문정보 테이블)
    - order_items (주문한 상품 테이블)
    - items (상품 테이블)
    - categories (상품 카테고리 테이블)
    - payments (결제 정보 테이블)
- 특징
    - H2 Inmemory DB 사용 및 data.sql 스크립트를 통한 더미 데이터 삽입
- 접속
  
  <img width="440" alt="스크린샷 2021-07-12 오후 12 17 46" src="https://user-images.githubusercontent.com/60464424/125226111-6a26b880-e30b-11eb-906d-75cbc7c0f26b.png">
  
  localhost:8080/h2-console 접속 후 위의 정보 입력 후 확인
  

## API 

### 회원

| 기능       | Method | URI |  설명           | 
| ----------| -------|--------|--------------------------------------------- |
| 회원가입 | POST | /api/auth/local/new | 회원가입 |
| 로그인    | POST | /api/auth/local        |  로그인 |

- 소셜로그인 등이 추가되었을 때의 Rest api한 방식을 유지하기 위해 위처럼 설계
- ex) /api/auth/kakao, /api/auth/google 

### 상품
| 기능       | Method | URI |  설명           | 
| ----------| -------|--------|--------------------------------------------- |
| 상품 조회 | GET | /api/items | 상품을 전체 조회 한다. categroy, keyword로 조건별 검색 |


### 주문
| 기능       | Method | URI |  설명           | 
| ----------| -------|--------|--------------------------------------------- |
| 주문 조회 | GET | /api/orders | 주문 내역을 조회한다,|
| 주문 등록 | POST | /api/orders | 장바구니에 있는 상품을 주문한다.|
| 주문 취소 | DELETE | /api/orders | 주문을 취소한다  |


### 결제
| 기능       | Method | URI    |  설명           | 
| ----------| -------|--------|--------------------------------------------- |
| 결제 등록 | POST | /api/payments    | 주문내역 들을 결제한다. |
| 결제 조회 | GET | /api/payments |  결제 내역을 조회한다. |

## Example

### 메인
![스크린샷 2021-07-11 오후 11 42 01](https://user-images.githubusercontent.com/60464424/125199733-a3770e00-e2a2-11eb-9830-94a86487c6f6.png)

- 회원가입, 로그인(Jwt 토큰) 기능 제공
- 비회원도 똑같이 장바구니 기능 이용가능 하며 장바구니 정보는 쿠키에 보관한다.
- 카테고리별 분류와, 검색기능을 제공한다.

### 회원가입
![스크린샷 2021-07-11 오후 11 44 34](https://user-images.githubusercontent.com/60464424/125199744-a8d45880-e2a2-11eb-8929-5b2bdde07078.png)

- 클라이언트, 서버단 모두 유효성검사 로직이 수행된다.
- 가입이 완료되면 로그인창이 열리게된다.

### 로그인 시
![스크린샷 2021-07-11 오후 11 45 16](https://user-images.githubusercontent.com/60464424/125199745-a8d45880-e2a2-11eb-9a32-b3493d2776b5.png)

- 로그인시 주문기능, 결제가 가능하도록 하였다.
- 로그인시 jwt토큰이 발급되어 쿠키에 저장되며 유효시간은 1시간으로 설정하였다.

### 
![스크린샷 2021-07-11 오후 11 46 00](https://user-images.githubusercontent.com/60464424/125199746-a96cef00-e2a2-11eb-8c43-b3c968e97e38.png)

- 비회원도 장바구니는 이용이 가능하며, 주문은 로그인 후 이용가능하다.
- 쿠키에 장바구니를 담아 놓아 로그인 비로그인 상관없이 장바구니가 유지되도록 구현하였다.

###
![스크린샷 2021-07-11 오후 11 46 21](https://user-images.githubusercontent.com/60464424/125199748-a96cef00-e2a2-11eb-8933-6980cbe58606.png)
- N개의 장바구니에서 주문한 내역을 한개의 주문내역으로 보고 주문내역 결제를 할 수 있다.

### 결제 내역
![스크린샷 2021-07-11 오후 11 46 32](https://user-images.githubusercontent.com/60464424/125199749-aa058580-e2a2-11eb-8122-f787abee8549.png)

- 주문한 모든 내역 모두를 결제하고, 결제일과 최종 결제금액을 보여준다.

## TODO

---
- 테스트 코드 작성
- ~~Jpa N+1 문제 해결~~ (2021.07.14 12:19) 수정완료
- ~~주문 취소 로직 수정~~ (2021.07.13 18:47 수정완료)
- ~~장바구니 삭제 버그 수정~~ (2021.07.13 17:16 수정완료) 
