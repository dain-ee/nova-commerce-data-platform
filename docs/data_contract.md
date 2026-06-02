# 데이터 계약 (Data Contract)

## 목적

Order Service가 Kafka로 발행하는 이벤트의 구조와 규칙을 정의한다.
데이터 계약(Data Contract)은 Producer와 Consumer가 동일한 이벤트 구조를 사용하도록 보장하기 위한 문서이다.

## Topic Naming Rule

```text
nova.user.events
nova.order.events
nova.payment.events
nova.shipment.events
nova.return.events
nova.refund.events
nova.review.events
nova.coupon.events
```

## 첫 번째 이벤트

### Topic

**nova.order.events**

### Event Type

ORDER_CREATED


## ORDER_CREATED 이벤트 스키마

| Field | Type | Required | Description |
|---------|---------|---------|---------|
| event_id | string | Y | 이벤트 고유 ID |
| event_type | string | Y | 이벤트 타입 |
| event_time | string | Y | 이벤트 발생 시간 |
| order_id | string | Y | 주문 ID |
| user_id | string | Y | 회원 ID |
| product_id | string | Y | 상품 ID |
| quantity | integer | Y | 주문 수량 |
| order_amount | long | Y | 주문 금액 |
| currency | string | Y | 통화 |
| order_status | string | Y | 주문 상태 |
| channel | string | Y | 주문 채널 |
| coupon_id | string | N | 사용 쿠폰 ID |


## 이벤트 예시

```json
{
  "event_id": "evt-000001",
  "event_type": "ORDER_CREATED",
  "event_time": "2026-06-02T10:00:00+09:00",
  "order_id": "ord-000001",
  "user_id": "usr-000001",
  "product_id": "prd-000001",
  "quantity": 2,
  "order_amount": 59000,
  "currency": "KRW",
  "order_status": "CREATED",
  "channel": "APP",
  "coupon_id": "cpn-202606"
}
```

## 허용 상태값 (Order Status)

- CREATED
- PAID
- CANCELLED
- SHIPPED
- DELIVERED
- RETURN_REQUESTED
- RETURNED
- REFUNDED


## 데이터 품질 규칙

### event_id

- 전역 고유값이어야 함
- UUID 사용 예정

### event_time

- ISO-8601 형식 사용
- KST(+09:00) 기준

**예시**

```text
2026-06-02T10:00:00+09:00
```

### order_amount

- 0보다 커야 함
- 음수 금지

### quantity

- 1 이상

### order_status

- 허용된 상태값만 사용 가능


## DLQ 조건

다음 조건에 해당하는 이벤트는 DLQ로 전송한다.

- 필수 필드 누락
- event_id 중복
- order_amount <= 0
- quantity <= 0
- 허용되지 않은 order_status
- JSON 파싱 실패

**DLQ Topic**

nova.order.events.dlq


## 스키마 진화(Schema Evolution) 정책

### 허용

- 신규 컬럼 추가
- 기존 Optional 컬럼 추가
- 기존 필드 유지

**예시**

```json
{
  "coupon_id": "cpn-001",
  "seller_id": "seller-001"
}
```

### 비허용

- 필드 삭제
- 필드 타입 변경
- 필수 필드 제거

**예시**

order_amount

```bash
long
↓
string

금지!
```

## Bronze 저장 정책

원본 이벤트를 수정하지 않는다.

```bash
Kafka
↓
Bronze
```

에는 원본 JSON을 그대로 저장한다.

### 목적

- 재처리
- 감사 추적
- 장애 복구
- Backfill


## 향후 추가 이벤트

- ORDER_PAID
- ORDER_CANCELLED
- ORDER_SHIPPED
- ORDER_DELIVERED
- RETURN_REQUESTED
- RETURNED
- REFUND_COMPLETED
