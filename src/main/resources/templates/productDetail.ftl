<#-- RecoPick의 위젯을 테스트 할 때는 나누어진 페이지가 편리 -->
<#-- 상품 상세 보기를 별도 페이지로 렌더링 하기 위해 Freemarker를 사용한 서버쪽 렌더링으로 구현 -->
<#-- Todo: productDetail.ftl 로딩 후 다시 ajax로 필요한 정보를 요청하는 방식으로 js 분리 가능. main.js 참고 -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>RecoPick Demo Mall</title>
    <link rel="stylesheet" href="https://unpkg.com/purecss@0.6.2/build/pure-min.css" integrity="sha384-UQiGfs9ICog+LwheBSRCt1o5cbyKIHbwjWscjemyBMT9YCUMZffs6UqUTd0hObXD" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script src="https://unpkg.com/vue"></script>
    <!--RecoPick 로그수집 스크립트 -->
    <script type="text/javascript">
        (function(w,d,n,s,e,o) {
            w[n]=w[n]||function(){(w[n].q=w[n].q||[]).push(arguments)};
            e=d.createElement(s);e.async=1;e.charset='utf-8';e.src='//static.recopick.com/dist/production.min.js';
            o=d.getElementsByTagName(s)[0];o.parentNode.insertBefore(e,o);
        })(window, document, 'recoPick', 'script');
        recoPick('service', 'dev.recopick.com');
    </script>
    <!--RecoPick 로그수집 스크립트 -->


    <!--RecoPick 상품 메타 태그 -->
    <meta property="recopick:title" content="" id="reco-meta-title">
    <meta property="recopick:image" content="" id="reco-meta-image">
    <meta property="recopick:price" content="" id="reco-meta-price">
    <meta property="recopick:price:currency" content="KRW" id="reco-meta-price-currency">
    <meta property="recopick:description" content="" id="reco-meta-description">
    <meta property="recopick:author" content="" id="reco-meta-author">
    <!--RecoPick 상품 메타 태그 -->
</head>
<body>
<div id="app">
    <div class="header">
        <h1 class="link" @click="gotoMain('${Session.userName}')">RecoPick Demo Mall</h1>
    </div>
    <div class="main">
        <div class="products">
            <div class="search-results">
                <h2>Product Info</h2>
            </div>
            <div class="product">
                <div>
                    <div class="product-image">
                        <img :src="product.productImage">
                    </div>
                </div>
                <div>
                    <h4 class="product-title">{{product.productName}}</h4>
                    <p>가격: <strong>{{product.productPrice}}</strong></p>
                    <p>판매자 등급: {{product.sellerGrd}}</p>
                    <p>판매자 별명: {{product.sellerNick}}</p>
                    <p>구매 만족도: {{product.buySatisfy}}</p>
                    <button class="btn add-to-cart" @click="addToCart()">Add to cart</button>
                </div>
            </div>
        </div>
        <div class="cart">
            <demo-cart :added-item="addedItem"></demo-cart>
        </div>
    </div>
</div>

<script src="/js/component-cart.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="/js/scrollMonitor.js"></script>
<script src="/js/productDetail.js"></script>
</body>
</html>