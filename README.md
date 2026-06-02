cat > README.md <<'EOF'
# Nova Commerce Data Platform

Nova Commerce는 패션, 라이프스타일, 식품을 판매하는 가상의 이커머스 회사입니다.

이 프로젝트는 단순 기술 실습이 아니라, 실제 커머스 회사의 주문, 결제, 배송, 환불, 마케팅, CS, 재무 데이터를 기반으로 부서별 의사결정을 지원하는 데이터 플랫폼을 구축하는 것을 목표로 합니다. 가장 큰 목표는 데이터 부서가 아닌 데이터 이용자가 구미에 맞게 데이터를 선택하고 재가공할 수 있도록 하는 것입니다.

## 프로젝트 목표

- Spring Boot 서비스에서 Kafka 이벤트 발행
- Kafka 기반 실시간 이벤트 수집
- Flink 기반 실시간 처리
- Iceberg + S3 기반 Lakehouse 구성
- EMR Serverless Spark 기반 배치 처리
- Airflow 기반 오케스트레이션
- Athena 기반 SQL 조회
- QuickSight 또는 Superset 기반 대시보드
- SNS 기반 장애 알림
- Bronze / Silver / Gold 데이터 레이어 설계
- DLQ, Late Event, Backfill, Schema Evolution, Compaction 운영 시나리오 구현

## 주요 부서

- 마케팅팀
- 상품팀
- 물류팀
- CS팀
- 재무팀
- 데이터플랫폼팀

## 전체 구조

```text
Spring Boot Services
↓
Kafka
↓
Flink
↓
Iceberg Bronze / Silver
↓
EMR Serverless Spark
↓
Iceberg Gold
↓
Athena
↓
Dashboard
```

### 첫 번째 구현 범위

1. Order Service
2. nova.order.events Kafka Topic
3. ORDER_CREATED 이벤트 계약
4. Bronze order_events 적재
5. Silver order_lifecycle 생성
6. Gold finance_daily_revenue 생성
