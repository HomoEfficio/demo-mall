<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>RecoPick Demo Mall</title>
    <link rel="stylesheet" href="https://unpkg.com/purecss@0.6.2/build/pure-min.css" integrity="sha384-UQiGfs9ICog+LwheBSRCt1o5cbyKIHbwjWscjemyBMT9YCUMZffs6UqUTd0hObXD" crossorigin="anonymous">
</head>
<body>

<div id="app">
    <div class="header">
        <h1>RecoPick Demo Mall</h1>
    </div>
    <div class="main">
        <div class="sign">
            <form id="sign-form" class="sign">
                <input v-model="userName" placeholder="UserName을 입력하세요.">
            </form>
            <button class="btn" v-on:click="onSignIn()">Sign In</button>
            <button class="btn" v-on:click="onSignUp()">Sign Up</button>
        </div>
    </div>
</div>

<script src="https://unpkg.com/vue"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="/js/index.js"></script>
</body>
</html>