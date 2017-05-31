<#-- RecoPick의 위젯을 테스트 할 때는 나누어진 페이지가 편리 -->
<#-- 상품 상세 보기를 별도 페이지로 렌더링 하기 위해 Freemarker를 사용한 서버쪽 렌더링으로 구현 -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>RecoPick Demo Mall</title>
    <link rel="stylesheet" href="https://unpkg.com/purecss@0.6.2/build/pure-min.css" integrity="sha384-UQiGfs9ICog+LwheBSRCt1o5cbyKIHbwjWscjemyBMT9YCUMZffs6UqUTd0hObXD" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script src="https://unpkg.com/vue"></script>
</head>
<body>
<div id="app">
    <div class="header">
        <h1 class="link" @click="gotoMain('${Session.userName}')">RecoPick Demo Mall</h1>
        <h1>주문이 완료되었습니다.</h1>
        <#list order.orderItems as orderItem>
            <p>${orderItem.product.productName} - ${orderItem.quantity} 개 - ${orderItem.amounts} 원</p>
        </#list>
        <div><button class="pure-button button-secondary" @click.prevent="gotoMain('${Session.userName}')">메인 화면으로 가기</button></div>
    </div>
</div>

<!-- RecoPick 주문완료_top 스크립트 -->
<div id="recopick_widget_ncdX88Te" data-widget_id="ncdX88Te">
    <script>
        (function(w,n){w[n]=w[n]||function(){(w[n].q=w[n].q||[]).push(arguments)};}(window, 'recoPick'));
        recoPick('widget', 'recopick_widget_ncdX88Te');
    </script>
</div>
<!-- RecoPick 주문완료_top 스크립트 -->
<script src="/js/component-cart.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="/js/scrollMonitor.js"></script>
<script src="/js/orderCompleted.js"></script>
</body>
</html>