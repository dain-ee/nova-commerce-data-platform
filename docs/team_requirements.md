# 부서별 데이터 요구사항

## 마케팅팀

### 주요 질문

- 광고 캠페인별 구매 전환율은?
- 신규 가입자의 30일 이내 재구매율은?
- 쿠폰 캠페인의 매출 효과는?
- 광고비 대비 매출은?

### 필요한 지표

- 캠페인별 구매 전환율
- 30일 재구매율
- 쿠폰 사용 매출
- 캠페인 ROI

### Gold 테이블 후보

- gold_marketing_campaign_daily
- gold_marketing_repurchase_30d_daily
- gold_marketing_coupon_roi_daily

## 상품팀

### 주요 질문

- 상품별 구매 전환율은?
- 반품률이 높은 상품은?
- 리뷰 평점은 높지만 매출이 낮은 상품은?
- 카테고리별 매출 순위는?

### 필요한 지표

- 상품별 매출
- 상품별 반품률
- 상품별 리뷰 평점
- 카테고리별 판매 순위

### Gold 테이블 후보

- gold_product_performance_daily
- gold_product_return_rate_daily

## 물류팀

### 주요 질문

- 결제 완료 후 배송 시작까지 얼마나 걸리는가?
- 배송 지연이 많은 지역은?
- 택배사별 SLA 준수율은?
- 주문량 증가 시 어느 구간에서 지연이 발생하는가?

### 필요한 지표

- 평균 배송 시작 지연 시간
- 지역별 배송 지연율
- 택배사별 SLA 위반율

### Gold 테이블 후보

- gold_logistics_delivery_sla_daily
- gold_logistics_region_delay_daily

## CS팀

### 주요 질문

- CS 문의가 급증한 상품은?
- 반품 사유 Top N은?
- 환불 처리 SLA를 초과한 건은?
- 배송 지연과 CS 문의 증가가 관련 있는가?

### 필요한 지표

- 일별 CS 문의 수
- 반품 사유별 건수
- 환불 SLA 초과 건수

### Gold 테이블 후보

- gold_cs_ticket_daily
- gold_cs_refund_reason_daily

## 재무팀

### 주요 질문

- 일별 총매출과 순매출은?
- 환불을 제외한 확정 매출은?
- 결제수단별 정산 금액은?
- 쿠폰 할인 반영 후 실제 매출은?

### 필요한 지표

- 총매출
- 순매출
- 환불액
- 결제수단별 정산 금액

### Gold 테이블 후보

- gold_finance_daily_revenue
- gold_finance_payment_settlement_daily
