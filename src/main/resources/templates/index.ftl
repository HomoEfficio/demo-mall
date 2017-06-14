<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>RecoPick Demo Mall</title>
    <link rel="stylesheet" href="https://unpkg.com/purecss@0.6.2/build/pure-min.css" integrity="sha384-UQiGfs9ICog+LwheBSRCt1o5cbyKIHbwjWscjemyBMT9YCUMZffs6UqUTd0hObXD" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <!--RecoPick 로그수집 스크립트 -->
    <script type="text/javascript">
        (function(w,d,n,s,e,o) {
            w[n]=w[n]||function(){(w[n].q=w[n].q||[]).push(arguments)};
            e=d.createElement(s);e.async=1;e.charset='utf-8';e.src='//static.recopick.com/dist/production.min.js';
            o=d.getElementsByTagName(s)[0];o.parentNode.insertBefore(e,o);
        })(window, document, 'recoPick', 'script');
        recoPick('service', 'dev.recopick.com');
        recoPick('sendLog','visit');
    </script>
    <!--RecoPick 로그수집 스크립트 -->
</head>
<body>

<div id="app">
    <div class="header-center">
        <h1>RecoPick Demo Mall</h1>
    </div>
    <div class="sign-up">
        <h2>회원 가입</h2>
        <form class="pure-form">
            <input v-model="userName" placeholder="UserName을 입력하세요." autofocus>
            <input v-model="birthYear" placeholder="출생년도를 입력하세요. - yyyy">
            <input v-model="gender" placeholder="성별을 입력하세요. - M 또는 F">
            <button class="pure-button pure-button-primary" @click.prevent="onSignUp()">Sign Up</button>
        </form>
    </div>
    <div class="sign-in">
        <h2>로그인</h2>
        <form class="pure-form">
            <input v-model="userName" placeholder="UserName을 입력하세요." autofocus>
            <button class="pure-button pure-button-primary" @click.prevent="onSignIn()">Sign In</button>
        </form>
    </div>
</div>

<script src="https://unpkg.com/vue"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="/js/index.js"></script>
</body>
</html>