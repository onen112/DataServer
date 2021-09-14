var vue = new Vue({
    el:"#app",
    data:{
        username:"",
        password:"",
        password_:""
    },
    methods:{
        sigin:function () {
            if(this.username == "" || this.password == "" || this.password_ == ""){
                alert("输入内容不能为空！");
            }else if(this.password == this.password_){
                axios.post("/user/sigin",{
                    username:this.username,
                    password: this.password
                }).then(res=>{
                    console.log(res.data);
                    if(res.data.state != 1){
                        alert(res.data.msg);
                    }else{
                        alert("账号注册成功！即将跳转到登录页面")
                        location.href = "login.html"
                    }
                })
            }

        }
    }
})