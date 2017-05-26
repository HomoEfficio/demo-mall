/**
 * Created by omwomw@sk.com on 2017. 5. 22..
 */
new Vue({
    el: '#app',
    data: {
        mainView: '/main',
        userName: '',
        birthYear: '',
        gender: ''
    },
    methods: {
        onSignIn() {
            console.log('onSignIn');
            // GET /sign-in/{memberName}
            document.location.href = this.mainView + '/' + this.userName;
        },
        onSignUp(endPoint) {
            console.log('onSignUp');
            // POST /members/{memberName}
            axios.post('/members/', {
                userName: this.userName,
                birthYear: this.birthYear,
                gender: this.gender
            })
                .then(res => {
                    console.log(res);
                    this.onSignIn();
                })
                .catch(err => {
                    console.log(err);
                });
        },
    }
});