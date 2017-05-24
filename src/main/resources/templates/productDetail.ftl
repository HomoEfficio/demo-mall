<#-- RecoPick의 위젯을 테스트 할 때는 나누어진 페이지가 편리 -->
<#-- 상품 상세 보기를 별도 페이지로 렌더링 하기 위해 Freemarker를 사용한 서버쪽 렌더링으로 구현 -->
<#-- Todo: productDetail.ftl 로딩 후 다시 ajax로 필요한 정보를 요청하는 방식으로 js 분리 가능. main.js 참고 -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>RecoPick Demo Mall</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div id="app">
    <div class="header">
        <h1>RecoPick Demo Mall</h1>
    </div>
    <div class="main">
        <div class="products">
            <div class="search-results">
                <h2>Product Info</h2>
            </div>
            <div class="product">
                <div>
                    <div class="product-image">
                        <img :src="product.basicImage">
                    </div>
                </div>
                <div>
                    <h4 class="product-title">{{product.productName}}</h4>
                    <p>Price: <strong>{{product.productPrice}}</strong></p>
                    <p>Point: {{product.point}}</p>
                    <p>Chip: {{product.chip}}</p>
                    <p>Installment: {{product.installment}}</p>
                    <p>Ship Fee: {{product.shipFee}}</p>
                    <p>Sell Satisfaction: {{product.sellSatisfaction}}</p>
                    <p>Sell Grade: {{product.sellGrade}}</p>
                    <button class="btn add-to-cart" @click="addToCart()">Add to cart</button>
                </div>
            </div>
        </div>
        <div class="cart">
            <h2>Shopping Cart</h2>
            <transition-group name="fade" tag="ul">
                <li class="cart-item" v-for="(item, index) in cart.cartItems" v-bind:key="index">
                    <div class="item-title">{{ item.product.productName }}</div>
                    <span class="item-qty">{{ item.product.productPrice }} * {{ item.quantity }}</span>
                    <button class="btn" v-on:click="inc(index)">+</button>
                    <button class="btn" v-on:click="dec(index)">-</button>
                </li>
            </transition-group>
            <transition name="fade">
                <div v-if="cart.length">
                    <p>Total: {{ total }}</p>
                    <div><button class="btn order-now" v-on:click="onOrder(cart)">Order Now</button></div>
                </div>
            </transition>
            <div v-if="cart.length === 0" class="empty-cart">
                No items in the cart
            </div>
        </div>
    </div>
</div>
<script src="https://unpkg.com/vue"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="/js/scrollMonitor.js"></script>
<script src="/js/productDetail.js"></script>
</body>
</html>