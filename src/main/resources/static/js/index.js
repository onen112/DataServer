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
            axios.get('/data/getDataList?docName=' + this.docName)
                .then(function (resp){
                    that.datas = resp.data.data.dataList;
                    that.user = resp.data.data.userInfo;
                    that.maxPage = that.datas.length;
                    this.changeSort();
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
            this.items.reverse();
        },
        logOut:function () {
            axios.post("/user/logOut").then(res=>{
                console.log("success!");
                location.href = "login.html";
            })
        }
    },
    created:function () {
        this.init();
    }
})
