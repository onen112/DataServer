var vue = new Vue({
    el:"#app",
    data:{
        userInfo:{},
        isFocus:false,
        username:""
    },
    methods:{
        getUserInfo:function () {
            var that = this;
            axios.get("/user/getUserInfo").then(
                function (res) {
                    that.userInfo = res.data.data;
                    that.username = that.userInfo.name;
                    console.log(res.data);
                }
            )
        },
        on_focus:function () {
            this.isFocus = true;
        },
        dis_focus:function () {
            this.username = this.userInfo.name;
            this.isFocus = false;

        },
        changeName:function () {
            console.log(this.username)
            if(this.username === this.userInfo.name){
                alert("修改成功！")
            }else if(this.username.length > 20){
                alert("请输入不超过20个字符！")
                this.username = this.userInfo.name;
            }else{
                var that = this;
                console.log(that.username)
                alert("修改成功！")
                axios.post("/user/changeName",{
                    name:that.username
                }).then(
                    function (res) {
                        console.log(res)
                            that.getUserInfo();
                    }
                )
            }
        }
    }
})
window.onload = function () {
    vue.getUserInfo();
}