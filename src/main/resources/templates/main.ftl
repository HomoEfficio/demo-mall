<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>RecoPick Demo Mall</title>
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
        recoPick('setUID', '${Session.userName}');
//        recoPick('fetchUID', (uid)=>console.log('fetched UID:', uid));  // uid 설정 실제로 확인
        recoPick('setMID', '${mid}');
        recoPick('setUserInfo',{ birthyear:"${birthYear}", gender:"${gender}"});
        recoPick('sendLog','visit');
    </script>
    <!--RecoPick 로그수집 스크립트 -->
    <script>
    window.demoUserUid = '${Session.userName}';
    window.demoUserInfo = {
        mid: '${mid}',
        birthYear: '${birthYear}',
        gender: '${gender}'
    };
    </script>
    <script src="/js/component-cart.js"></script>
</head>
<body>
<div id="app">
    <div class="header">
        <h1>RecoPick Demo Mall</h1>
        <form class="searchbar" v-on:submit.prevent="onSearch('${mid}', '${birthYear}', '${gender}')">
            <input v-model="newSearch" placeholder="Search for posters" autofocus>
            <input type="submit" value="Search" class="btn">
        </form>
    </div>
    <div class="main">
        <div class="products">
            <div v-if="loading">
                Loading...
            </div>
            <div class="search-results" v-else>
                Found {{ results.length }} results for search term {{ lastSearch }}.
            </div>
            <div class="product" v-for="(item, index) in items">
                <div>
                    <div class="product-image">
                        <img v-bind:src="item.productImage">
                    </div>
                </div>
                <div>
                    <h4 class="product-title">
                        <span v-on:click="onDetail(index)">{{ item.productName }}</span>
                    </h4>
                    <p>Price: <strong>{{ item.productPrice }}</strong></p>
                    <button class="btn add-to-cart" v-on:click="addToCart(index)">Add to cart</button>
                </div>
            </div>
            <div id="product-list-bottom">
                <div v-if="noMoreItems">No more items</div>
            </div>
        </div>
        <div class="cart">
            <#--<h2>Shopping Cart</h2>-->
            <#--<transition-group name="fade" tag="ul">-->
                <#--<li class="cart-item" v-for="(item, index) in cart.cartItems" v-bind:key="index">-->
                    <#--<div class="item-title">{{ item.product.productName }}</div>-->
                    <#--<span class="item-qty">{{ item.product.productPrice }} * {{ item.quantity }}</span>-->
                    <#--<button class="btn" v-on:click="inc(index)">+</button>-->
                    <#--<button class="btn" v-on:click="dec(index)">-</button>-->
                <#--</li>-->
            <#--</transition-group>-->
            <#--<transition name="fade">-->
                <#--<div v-if="cart.length">-->
                    <#--<p>Total: {{ total | currency }}</p>-->
                    <#--<div><button class="btn order-now" v-on:click="onOrder(cart)">Order Now</button></div>-->
                <#--</div>-->
            <#--</transition>-->
            <#--<div v-if="cart.length === 0" class="empty-cart">-->
                <#--No items in the cart-->
            <#--</div>-->
            <demo-cart :added-item="addedItem" :uid="${Session.userName}" :user-info="{mid: '${mid}', birthYear: '${birthYear}', gender: '${gender}'}"></demo-cart>
        </div>
    </div>
</div>

<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="/js/scrollMonitor.js"></script>
<script src="/js/main.js"></script>
</body>
</html>