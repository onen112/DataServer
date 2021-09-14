var vue = new Vue({
    el:"#app",
    data:{
      username:"",
      password:""
    },
    methods:{
        login: function () {
            if(this.username == "" || this.password == null){
                alert("用户名和密码不能为空，请先输入！")
            }
            axios.post("user/login", {
                username:this.username,
                password:this.password
            }).then(response=> {
                    console.log(response);
                    if (response.data.state == 1) {
                        alert("用户名或密码输入错误，请重新输入！");
                    } else {
                        location.href = "/index.html";
                    }
                })
                .catch(function (err) {
                    console.log(err);
                });
        }
    }
})