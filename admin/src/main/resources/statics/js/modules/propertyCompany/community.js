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
            { label: '管理人员', name: 'admin', width: 75 },
            { label: '户数', name: 'households', width: 75 },
            { label: '地址', name: 'address', width: 90 },
            { label: '描述', name: 'describe', width: 90 },
            { label: '时间', name: 'createTime', width: 100 }
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
        userTableList:true,
        userformList:false,
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
            vm.userTableList=true;
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
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('当前社区记录将只保存3天记录，3天后将清除有关该社区所有记录，确定要删除该社区？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "communityMessage/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.status == "success"){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.respMsg);
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
                        alert(r.respMsg);
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
                vm.community.households = result.data.households;
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
        },
        setCommunityUser:function () {
            let communityId = getSelectedRow();
            if(communityId == null){
                return ;
            }
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "设置管理员",
                area: ['780px', '450px'],
                fixed:false,
                content:jQuery("#userTable"),
                closeBtn:1,
                btn: ['确定','取消'],
                btn1: function (index) {
                    var grid = $("#userjqGrid");
                    var id = grid.getGridParam("selarrrow");
                    $.ajax({
                        type: "POST",
                        url: baseURL + "communityMessage/setCommunityAdministrator",
                        data: {communityId:communityId,userId:id[0]},
                        success: function(r){
                            if(r.status == "success"){
                                alert('操作成功', function(){
                                    layer.close(index);
                                    vm.reload();

                                });
                            }else{
                                alert(r.respMsg);
                            }
                        }
                    });
                }
            });
            layer.ready(function () {
                vm.loadWorker(communityId);
            });

        },
        loadWorker:function (communityId) {
            debugger;
            $("#userjqGrid").jqGrid({
                url: baseURL + 'communityUser/people/list',
                datatype: "json",
                mtype:"POST",
                postData:{
                    "communityId": communityId,
                    "companyRoleType":2},
                colModel: [
                    { label: '', name: 'id',hidden:true  },
                    { label: '用户手机', name: 'userPhone'},
                    { label: '真实姓名', name: 'realName' },
                    { label: '所属社区', name: 'communityName'},
                    {label: '创建时间', name: 'createTime'},
                    {label: '维修工作类型', name: 'repairsType'},
                    {
                        label: '操作', name: '', formatter: function (cellvalue, options, rowObject) {
                            return ' <a class="btn btn-default" onclick="queryInfo(' + rowObject.id + ')">分配维修类型</a>';
                        }
                    }

                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList : [10,30,50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth:true,
                multiselect: true,
                pager: "#userjqGridPager",
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
                    $("#userjqGridPager").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                }
            });
        }
    }
});