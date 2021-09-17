
var vue = new Vue({
    el:"#app",
    data:{
        datas:[],
        items:[],
        user:{},
        docName:"",
        sortBy:true,
        index:0,
        maxPage:0,
        nums:5
    },
    methods:{
        init:function () {
            var that = this;
            console.log("123123")
            axios.get('/data/getDataList?docName=' + this.docName)
                .then(function (resp){
                    that.datas = resp.data.data.dataList;
                    that.user = resp.data.data.userInfo;
                    that.maxPage = (that.datas.length);
                    that.maxPage = Math.ceil(that.maxPage/that.nums);
                    if(that.index > that.maxPage-1){
                        that.index--;
                    }
                    that.getData();
                }).catch(function (error) {
                    console.log(error);
                });
        },
        deleteData:function(id_,md5_){
            if(confirm("你确定要删除嘛？")){
                axios.post('/data/deleteData',{
                    id:id_,
                    md5:md5_
                }).then(res=>{
                    console.log(res);
                    this.init();
                })
            }
        },
        nextPage:function(){
            if(this.index < this.maxPage-1){
                this.index++;
                this.getData();
            }
        },
        prePage:function(){
            if(this.index > 0){
                this.index--;
                this.getData();
            }
        },
        copystr:function (str) {
            var oInput = document.createElement('input');
             oInput.value = str;
            document.body.appendChild(oInput);
            oInput.select(); // 选择对象
            oInput.setSelectionRange(0, oInput.value.length), document.execCommand('Copy');
            document.body.removeChild(oInput);
            alert('下载链接已经成功复制到剪贴板，快去分享给你的好友们吧！');
        },
        sub:function () {
            var j =$("#form").contents().find("body").text();
            console.log(data)
            if(j != ""){
                var data = $.parseJSON(j);
                console.log(data)
                if(data.state == 1){
                    alert(data.msg);
                    this.init();
                }else{
                    alert(data.msg)
                }
            }
        },
        changeSort:function () {
            this.sortBy = !this.sortBy;
            console.log(this.sortBy);
            this.datas.reverse();
            this.getData();
        },
        logOut:function () {
            axios.post("/user/logOut").then(res=>{
                console.log("success!");
                location.href = "login.html";
            })
        },
        changePage:function(n){
            this.index = n-1;
            this.getData();
        },
        getData:function () {
            this.items = this.datas.slice(this.nums*this.index,this.nums*this.index+this.nums);
            console.log(this.items)
        }
    },
    created:function () {
        this.init();
    }
})
