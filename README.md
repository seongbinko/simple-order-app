# Simple Order App
- 소개: 간단하게 상품 주문 결제할 수 있는 어플리케이션
- 특징: REST API / Spring Boot / JPA / Dockerfile 를 통한 이미지 구성 / Spring Security/ Jwt 인증 /

## Intallation

## API 

### 회원

| 기능       | Method | URI |  설명           | 
| ----------| -------|--------|--------------------------------------------- |
| 회원가입 | GET | /api/auth/local/new | 회원가입 HTML|
|        | POST | /api/auth/local/new | 회원 가입 |
| 로그인   | GET | /api/auth/local      | 로그인 HTML |
|     | POST | /api/auth/local        |  로그인 |
| 로그아웃 | DELETE | /api/auth/local    | 로그아웃 |

### 상품
| 기능       | Method | URI |  설명           | 
| ----------| -------|--------|--------------------------------------------- |
| 상품 조회 | GET | /api/items | 상품을 전체 조회 한다. param으로 검색 조건을 건다. |


### 주문
| 기능       | Method | URI |  설명           | 
| ----------| -------|--------|--------------------------------------------- |
| 주문 등록 | POST | /api/orders | 장바구니에 있는 상품을 주문한다.|
| 주문 조회 | GET | /api/orders | 주문을 전체 조회한다. |
| 주문 취소   | DELETE | /api/orders      | 주문을 취소한다  |


### 결제
| 기능       | Method | URI    |  설명           | 
| ----------| -------|--------|--------------------------------------------- |
| 결제 등록 | POST | /api/payments    | 모든 주문내역을 결제 하고 (주문내역은 삭제한다) |
| 결제 조회 | GET | /api/payments/{id} |  결제내역을 조회한다. |

## Example

## Structure