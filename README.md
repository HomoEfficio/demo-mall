# RecoPick Demo Mall

RecoPick의 기능 검증 및 테스트에 사용할 가상의 RecoPick 고객 쇼핑몰

## 11번가 상품 검색

```
curl -X GET \
  http://apis.skplanetx.com/11st/v2/common/categories \
  -H 'accept: application/json' \
  -H 'appkey: 83aeb0b1-94db-3372-9364-22a13e6b6df2' \
  -H 'cache-control: no-cache'
```

```
curl -X GET http://apis.skplanetx.com/11st/v2/common/categories -H 'accept: application/json' -H 'appkey: 83aeb0b1-94db-3372-9364-22a13e6b6df2' -H 'cache-control: no-cache'
```