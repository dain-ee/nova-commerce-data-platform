# 아키텍처

## 전체 아키텍처

Spring Boot Services
  - order-service
  - payment-service
  - shipment-service
  - user-service
  - coupon-service
        ↓
Kafka Topics
        ↓
Flink Streaming Jobs
        ↓
Iceberg Bronze / Silver Tables on S3
        ↓
EMR Serverless Spark Batch Jobs
        ↓
Iceberg Gold Tables
        ↓
Athena
        ↓
Dashboard

## 서비스 계층

서비스 계층

각 서비스는 자신의 도메인 이벤트를 Kafka로 발행합니다.

* Order Service: 주문 생성, 주문 취소
* Payment Service: 결제 성공, 결제 실패
* Shipment Service: 배송 시작, 배송 완료
* User Service: 회원 가입, 회원 상태 변경
* Coupon Service: 쿠폰 발급, 쿠폰 사용

## Kafka 계층

도메인별 Topic을 사용합니다.

nova.user.events
nova.order.events
nova.payment.events
nova.shipment.events
nova.return.events
nova.refund.events
nova.review.events
nova.coupon.events

### Bronze Layer

원천 이벤트를 거의 그대로 저장합니다.

목적:

* 원본 보존
* 재처리
* 감사 추적
* 장애 복구

### Silver Layer

비즈니스 엔티티 기준으로 정제합니다.

예시:

* silver_orders
* silver_payments
* silver_shipments
* silver_order_lifecycle

### Gold Layer

부서별 KPI를 제공합니다.

예시:

* gold_finance_daily_revenue
* gold_marketing_campaign_roi_daily
* gold_logistics_delivery_sla_daily
* gold_cs_refund_reason_daily

# 처리 방식

## 실시간 처리

Kafka 이벤트를 Flink로 처리하여 Bronze / Silver 테이블에 적재합니다.

## 배치 처리

EMR Serverless Spark를 사용하여 Gold KPI, Backfill, Compaction 작업을 수행합니다.

## 오케스트레이션

Airflow가 배치 Job, 검증, 알림, 유지보수 작업을 관리합니다.

## 운영 고려사항

* Schema Evolution
* DLQ
* Late Event
* Duplicate Event
* Backfill
* Iceberg Compaction
* Snapshot Expiration
* Job Failure Alert

