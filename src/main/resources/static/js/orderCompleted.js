new Vue({
    el: '#app',
    data: {
    },
    methods: {
        gotoMain(userName) {
            document.location.href = '/main/' + userName;
        }
    }
});