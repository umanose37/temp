/* 주문 테이블 */
create table TORD_ORD
(
    ord_number  varchar2(20) not null,
    order_dtl   varchar2(10) not null,
    amount      number       not null,
    ord_status  varchar2(5)  not null,
    ord_dts     date         not null,
    send_date   varchar2(8),
    del_flg     char(1)      default '0' not null,
    reg_id      varchar(10)  not null,
    reg_url     varchar(50)  not null,
    reg_dts     date         not null,
    mod_id      varchar(10)  not null,
    mod_url     varchar(50)  not null,
    mod_dts     date         not null
);

alter table TORD_ORD add constraint pk_ord primary key (ord_number);
create sequence TORD_ORD_SEQ increment by 1 minvalue 1 maxvalue 9999 nocycle nocache noorder;


comment on table TORD_ORD is '주문 기본';
comment on column TORD_ORD.ord_number is '주문 번호';
comment on column TORD_ORD.order_dtl is '주문 내용';
comment on column TORD_ORD.amount is '주문 수량';
comment on column TORD_ORD.ord_status is '주문 상태';
comment on column TORD_ORD.ord_dts is '주문 일시';
comment on column TORD_ORD.send_date is '배송 일시';
comment on column TORD_ORD.del_flg is '삭제 플래그 - 1:삭제, 0:삭제안함';
comment on column TORD_ORD.reg_id is '등록자 id';
comment on column TORD_ORD.reg_url is '등록 프로그램 경로';
comment on column TORD_ORD.reg_dts is '주문 일시';
comment on column TORD_ORD.mod_id is '수장자 id';
comment on column TORD_ORD.mod_url is '수정 프로그램 경로';
comment on column TORD_ORD.mod_dts is '수정 일시';

/* 원료 재고 테이블 */
create table TMAT_STOCK
(
    mat_id        number       not null,
    mat_nm        varchar2(10) not null,
    mat_qty       number       not null,
    del_flg       char(1)      default '0' not null,
    use_flg       char(1)      default '1' not null,
    reg_id        varchar(10)  not null,
    reg_url       varchar(50)  not null,
    reg_dts       date         not null,
    mod_id        varchar(10)  not null,
    mod_url       varchar(50)  not null,
    mod_dts       date         not null
);

alter table TMAT_STOCK add constraint pk_mat_stock primary key (mat_nm);
create sequence TMAT_STOCK_SEQ increment by 1 minvalue 1 maxvalue 9999 nocycle nocache noorder;

comment on table TMAT_STOCK is '원료 재고';
comment on column TMAT_STOCK.mat_id is '원료 id';
comment on column TMAT_STOCK.mat_nm is '원료명';
comment on column TMAT_STOCK.mat_qty is '원료 잔량';
comment on column TMAT_STOCK.del_flg is '삭제 플래그 - 1:삭제, 0:삭제안함';
comment on column TMAT_STOCK.use_flg is '사용 플래그 - 1:사용, 0:미사용(단종)';
comment on column TMAT_STOCK.reg_id is '등록자 id';
comment on column TMAT_STOCK.reg_url is '등록 프로그램 경로';
comment on column TMAT_STOCK.reg_dts is '주문 일시';
comment on column TMAT_STOCK.mod_id is '수장자 id';
comment on column TMAT_STOCK.mod_url is '수정 프로그램 경로';
comment on column TMAT_STOCK.mod_dts is '수정 일시';

insert into TMAT_STOCK values ()

/* 원료 운영 테이블 */
create table TMAT_MAT_OP
(
    mat_id        number       not null,
    mat_nm        varchar2(10) not null,
    mat_qty       number       not null,
    supply_flg    char(1)      not null,
    del_flg       char(1)      default '0' not null,
    reg_id        varchar(10)  not null,
    reg_url       varchar(50)  not null,
    reg_dts       date         not null,
    mod_id        varchar(10)  not null,
    mod_url       varchar(50)  not null,
    mod_dts       date         not null
);

alter table TMAT_MAT_OP add constraint pk_mat_op primary key (mat_nm);
create sequence TMAT_MAT_OP_SEQ increment by 1 minvalue 1 maxvalue 9999 nocycle nocache noorder;

comment on table TMAT_MAT_OP is '원료 운영';
comment on column TMAT_MAT_OP.mat_id is '원료 id';
comment on column TMAT_MAT_OP.mat_nm is '원료명';
comment on column TMAT_MAT_OP.mat_qty is '원료 잔량';
comment on column TMAT_MAT_OP.supply_flg is '원료 공급 플래그 - 1:공급중, 0:운영중';
comment on column TMAT_MAT_OP.del_flg is '삭제 플래그 - 1:삭제, 0:삭제안함';
comment on column TMAT_MAT_OP.reg_id is '등록자 id';
comment on column TMAT_MAT_OP.reg_url is '등록 프로그램 경로';
comment on column TMAT_MAT_OP.reg_dts is '주문 일시';
comment on column TMAT_MAT_OP.mod_id is '수장자 id';
comment on column TMAT_MAT_OP.mod_url is '수정 프로그램 경로';
comment on column TMAT_MAT_OP.mod_dts is '수정 일시';

/* 원료 공급 이력 테이블 */
create table TMAT_SUPPLY_HIS
(
    his_no        number       not null,
    mat_id        number       not null,
    supply_qty    number       not null,
    supply_st_dts date         not null,
    supply_ed_dts date ,
    reg_id        varchar(10)  not null,
    reg_url       varchar(50)  not null,
    reg_dts       date         not null,
    mod_id        varchar(10)  not null,
    mod_url       varchar(50)  not null,
    mod_dts       date         not null
);

create sequence TMAT_SUPPLY_HIS_SEQ increment by 1 minvalue 1 maxvalue 9999 nocycle nocache noorder;
alter table TMAT_SUPPLY_HIS add constraint pk_mat_supply_his primary key (his_no);

comment on table TMAT_SUPPLY_HIS is '원료 공급 이력';
comment on column TMAT_SUPPLY_HIS.his_no is '이력 번호';
comment on column TMAT_SUPPLY_HIS.mat_id is '원료 id';
comment on column TMAT_SUPPLY_HIS.supply_qty is '공급 량';
comment on column TMAT_SUPPLY_HIS.supply_st_dts is '공급 시작일시';
comment on column TMAT_SUPPLY_HIS.supply_ed_dts is '공급 종료일시';
comment on column TMAT_SUPPLY_HIS.reg_id is '등록자 id';
comment on column TMAT_SUPPLY_HIS.reg_url is '등록 프로그램 경로';
comment on column TMAT_SUPPLY_HIS.reg_dts is '주문 일시';
comment on column TMAT_SUPPLY_HIS.mod_id is '수장자 id';
comment on column TMAT_SUPPLY_HIS.mod_url is '수정 프로그램 경로';
comment on column TMAT_SUPPLY_HIS.mod_dts is '수정 일시';
