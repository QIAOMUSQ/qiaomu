/**
 * @description:
 * @author 李品先
 * @Date 2019-03-24 23:10
 */
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'communityMessage/list',
        datatype: "json",
        mtype:"POST",
        colModel: [
            { label: 'id', name: 'id', index: "id", width: 45, key: true,hidden:true },
            { label: '社区名称', name: 'name',  width: 45 },
            { label: '城市', name: 'cityName', width: 75 },
            { label: '地址', name: 'address', width: 90 },
            { label: '描述', name: 'describe', width: 90 },
            { label: '时间', name: 'creatTime', width: 100 }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});



var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            name: null
        },
        showList: true,
        title:null,
        community:{
            name:null,
            id:null
        },
        provinceList:[],
        cityList:[],
        province:'省份',
        city:'城市',
        provinceCityId:null

    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.provinceCityId=null;
            vm.selectProvince();
        },

        update: function () {
            var id = getSelectedRows();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getCommunity(id);

        },
        del: function () {
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "communityMessage/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function(r){
                        if(r.status == "success"){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var url = "communityMessage/save";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.community),
                success: function(r){
                    if(r.status == "success"){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        getCommunity: function(id){
            $.get(baseURL + "communityMessage/getCommunityById/"+id, function(result){

                vm.community.id = result.data.id;
                vm.community.name = result.data.name;
                vm.community.legalPerson=result.data.legalPerson;
                vm.community.telPhone = result.data.telPhone;
                vm.community.address =result.data.address;
               /// vm.community.cityCode = result.data.cityCode;
                vm.provinceCityId = result.data.cityId;
                vm.community.describe = result.data.describe;
                vm.selectProvince();
            });
        },

        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
        },
        selectProvince:function () {
            //查询省份
            $.post(baseURL + "mobile/provinceCity/getPrivateDate", function(result){
                vm.provinceList = [];
                var i= 0;
                $.each(result.data,function (index,item) {
                    if(item.id === vm.provinceCityId){i = index}
                    vm.provinceList.push({value:item.provinceCode,text:item.provinceName})
                })
                vm.province = vm.provinceList[i].value;
                vm.queryCity(vm.provinceList[i].value);
            });
        },
        indexSelect:function (event) {
            //查询城市
            vm.queryCity(event.target.value);
        },
        queryCity:function (value) {
            $.post(baseURL + "mobile/provinceCity/getCityDateByProvinceCode",{"provinceCode":value},function(result){
                vm.cityList =[];
                var i =0;
                $.each(result.data,function (index,item) {
                    if(i === vm.provinceCityId){i = index}
                    vm.cityList.push({value:item.cityCode,text:item.cityName})
                })
                vm.city= vm.cityList[i].value;
                vm.community.cityCode = vm.cityList[i].value;
            });
        },
        selectCity:function (event) {
            vm.community.cityCode = event.target.value;
        }
    }
});