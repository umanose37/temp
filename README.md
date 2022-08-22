< 프로그램 가이드 >

* 주어진 시간동안 과제의 모든 기능을 완성하지 못하여 구현된 기능만 안내 합니다.

< DB 테이블 개요 >
TORD_ORD: 주문 테이블, 주문 데이터를 기록.
TMAT_STOCK: 원료 재고 테이블, 생산 설비에 공급할 원료 재고를 보관.
TMAT_MAT_OP: 생산설비 원료 테이블, 제품 생산에 사용되는 원료를 보관. 잔량이 모두 소진되면 원료 재고 테이블에서 공급 받는다.
TMAT_SUPPLY_HIS: 생산설비 원료 충전 이력 테이블, 생산설비 원료가 부족해 공급하게 되면 그 이력을 기록.

< 구현된 기능 설명 >
1. 원료 재고 등록
   최초 원료는 사용자가 등록한다.
   등록하는 원료가 생산 설비 원료에 존재하지 않으면 생산설비 원료도 같이 채워준다.

생산 설비 원료 1회 공급량을 200으로 고정 하였기에 원료 재고 등록 최소 수치는 200, 최대 수치는 400으로 가정.
즉, 생산설비에 A~D원료가 존재하고 재고 원료에 E라는 원료 300을 공급할 경우 각 테이블 별 결과는 원료 E의 잔량은 아래와 같다.
TMAT_STOCK : 100
TMAT_MAT_OP : 200

재고, 생산설비 양쪽 모두 데이터가 없는 초기 상태에서 원료 A를 200, 원료 B를 400 공급하면 결과는 아래와 같다.

- 원료 A -
  TMAT_STOCK : 0
  TMAT_MAT_OP : 200

- 원료 B -
  TMAT_STOCK : 200
  TMAT_MAT_OP : 200

** 원료 재고에서 생산설비에 원료를 공급할 시 프로그램 시간은 40분(현실 40초)가 경과 된다. 따라서 40초 동안 응답이 없더라도 기다려야 한다.
** 생산설비 원료중 공급중인 원료가 있으면 원료 재고에만 공급하고 처리를 종료한다. (TMAT_MAT_OP.supply_flg == 1, 공급중)
** 사양서에는 원료는 제품 리뉴얼에 따라 최대 10개까지 등록이 가능하다 명시 되어있지만 현재 제한 없이 재고 추가 가능.

# request (POST)
url - http://localhost:8080/material/add
headers - Content-Type: application/json; charset=UTF-8
body -  
{
"matNm": "H"
, "matQty": 300
}

# reponse
성공시 - {"result":"SUCCEEDD"}
오류 - {"result":"FAILED","message":"원료량 최대치 초과"}

2. 원료 재고 현황 조회
   위 1. 기능을 통해 등록된 원료 재고 현황을 조회 한다.

# request (GET)
url - http://localhost:8080/material/getStockSts

#response
[{"matNm":"A","qty":600},{"matNm":"B","qty":200},{"matNm":"C","qty":0},{"matNm":"D","qty":100},{"matNm":"E","qty":300},{"matNm":"F","qty":300},{"matNm":"G","qty":300},{"matNm":"H","qty":100}]

3. 주문 접수
   사용자가 주문을 접수하는 기능.
   정상적으로 주문이 접수 되면 주문번호를 반환한다.
   주문 접수는 1회 1건에 한하며 복수 상품을 구매할 경우, 동일상품 복수 수량을 구매할 경우는 별도로 요청을 해야한다.
   본래 사양대로 라면 접수되어있는 주문 현황을 고려하며 (원료, 가동시간에 따른 배송일자 영향 등) 주문을 처리해야 하지만
   본 프로그램에선 아래의 조건에 따라 배송일자만 계산하여 주문을 접수하고 처리를 종료한다.
- 주문내용에 따른 원료가 부족하면 가동시간 40분과 제품을 생산하는 16분 가산한 시간이 금일 영업시간을 초과할 경우 익일로 배송일자를 반환. 금일 이내일 경우 금일을 배송일자로 반환.

# request (POST)
url - http://localhost:8080/order/order
headers - Content-Type: application/json; charset=UTF-8
body -  
{
"orderDtl": "C6D4"
}

# reponse
{"result":"SUCCEEDD","order_number":"P2022080109168"}

4. 주문 조회
   위 3. 의 요청 결과로 반환 받은 주문 번호로 주문 내역을 조회 할 수있다.
   ** 주문일자는 YYYYMMDD 8자리 문자열로 지정 한다.

# request (GET)
url - http://localhost:8080/order/getMyOrder
param - ordNumber(주문번호), orderDate(주문일자), orderDtl(주문내용)
ex) http://localhost:8080/order/getMyOrder?ordNumber=P2022080109167&orderDate=20220801&orderDtl=A6B4

#response
{"orderNumber":"P2022080109167","sendDate":"20220801","orderDtl":"A6B4"}