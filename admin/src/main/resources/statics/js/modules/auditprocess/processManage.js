$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'processMessage/process/list',
        datatype: "json",
        mtype:"POST",
        colModel: [
            { label: '', name: 'id', width: 45,hidden:true,index: "id", key: true   },
            { label: '流程名称', name: 'processName', width: 45,sortable: false},
            { label: '流程类型', name: 'processType', width: 75 ,sortable: false},
            { label: '一级处理人', name: 'phoneOne', width: 90 ,sortable: false},
            { label: '二级处理人', name: 'phoneTwo', width: 90 ,sortable: false},
            { label: '上报人', name: 'reportPerson', width: 70 ,sortable: false},
            { label: '社区名称', name: 'communityName', width: 60,sortable: false},
            { label: '创建时间', name: 'createTime', index: "create_time", width: 85}
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
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            processName: null,
            communityName:null
        },
        showList: true,
        title:null,
        roleList:{},
        processMessage:{
            communityId:null,
            id:null
        },
        typeList:[]
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.getDictor();
            
        },
       
        update: function () {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getProcess(id);
            //获取角色信息

        },
        del: function () {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "processMessage/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
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
            $.ajax({
                type: "POST",
                url: baseURL + "processMessage/process/save",
                contentType: "application/json",
                data: JSON.stringify(vm.processMessage),
                success: function(r){
                    if(r.status == "success"){
                        alert('操作成功', function(){
                            vm.processMessage=[];
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        getProcess: function(id){
            $.get(baseURL + "processMessage/getProcess/"+id, function(result){
                $("#phoneOne").val(result.process.phoneOneName);
                $("#phoneTwo").val(result.process.phoneTwoName);
                $("#reportPerson").val(result.process.reportPersonName);
                $("#communityName").val(result.process.communityName);
                vm.processMessage.phoneOne =result.process.phoneOne ;
                vm.processMessage.phoneTwo =result.process.phoneTwo ;
                vm.processMessage.reportPerson =result.process.reportPerson;
                vm.processMessage.processName =result.process.processName;
                vm.processMessage.processType = result.process.processType;
                vm.processMessage.communityId = result.process.communityId;
                vm.processMessage.id = result.process.id;
                vm.getDictor();
            });
        },
        //加载用户
        loadUser:function () {
            $("#jqUserGrid").jqGrid({
                url: baseURL + 'communityUser/people/list',
                datatype: "json",
                mtype:"POST",
                postData:{"companyRoleType":"3"},
                colModel: [
                    { label: '', name: 'id', width: 45,hidden:true  },
                    { label: '用户手机', name: 'userPhone', width: 75 },
                    { label: '真实姓名', name: 'realName', width: 75 },
                    { label: '所属社区', name: 'communityName',  width: 45 }
                ],
                viewrecords: true,
                height: 250,
                width:700,
                rowNum: 5,
                rowList : [5,10,30,50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth:true,
                multiselect: true,
                pager: "#jqUserGridPager",
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
                    $("#jqUserGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                }
            });
            $("#jqUserGrid").jqGrid("resetSelection");

        },
        getUser: function(type){
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "用户信息",
                area: ['750px', '450px'],
                fixed:false,
                content:jQuery("#userGrid"),
                closeBtn:1,
                btn: ['确定','取消'],
                btn1: function (index) {
                    var grid = $("#jqUserGrid");
                    var ids = grid.getGridParam("selarrrow");
                    var name = "";
                    var phone = "";
                    $.each(ids,function (index,item) {
                        phone+=grid.getRowData(item).userPhone +"_";
                        name +=grid.getRowData(item).realName+" ";
                    });

                    if(type=="phoneOne"){
                         vm.processMessage.phoneOne =phone;
                        $("#phoneOne").val(name);
                    }else if(type=="phoneTwo") {
                        vm.processMessage.phoneTwo =phone;
                        $("#phoneTwo").val(name);
                    }else {
                        vm.processMessage.reportPerson =phone;
                        $("#reportPerson").val(name);
                    }
                    layer.close(index);

                }
            });
            layer.ready(function () {
                vm.loadUser();
            });
        },
        getDictor:function () {
            vm.typeList=[];
            $.get(baseURL + "sys/dict/getDictByType",{"type":"property_process"}, function(r){
                var i =0;
                $.each(r.dict,function (index,item) {
                    if(item.value == vm.processMessage.processType) i=index;
                    vm.typeList.push({"value":item.value,"name":item.code});
                });
                vm.processMessage.processType = vm.typeList[i].value;
            });
        },
        selectType:function (event) {

            vm.processMessage.processType = event.target.value;

        },
        //获取社区框口
        getCommunity:function () {
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "社区信息",
                area: ['750px', '450px'],
                fixed:false,
                content:jQuery("#communityGrid"),
                closeBtn:1,
                btn: ['确定','取消'],
                btn1: function (index) {
                    var grid = $("#jqCommunityGrid");
                    var ids = grid.getGridParam("selarrrow");
                    var name = "";
                    if(ids.length > 1){
                        alert("只能选择一条记录");
                        return ;
                    }
                    $.each(ids,function (index,item) {
                        name += grid.getRowData(item).name;
                    });
                    vm.processMessage.communityId =parseInt(ids[0]);
                    $("#communityName").val(name);
                    layer.close(index);

                }
            });
            layer.ready(function () {
                vm.loadCommunity();
            });
        },
        //加载社区
        loadCommunity:function () {
            $("#jqCommunityGrid").jqGrid({
                url: baseURL + 'communityMessage/list',
                datatype: "json",
                mtype:"POST",
                colModel: [
                    { label: 'id', name: 'id', index: "id", width: 45, key: true,hidden:true },
                    { label: '社区名称', name: 'name',  width: 45,sortable:false },
                    { label: '城市', name: 'cityName', width: 75 ,sortable:false },
                    { label: '地址', name: 'address', width: 90,sortable:false  },
                ],
                viewrecords: true,
                height: 250,
                width:700,
                rowNum: 5,
                rowList : [5,10,30,50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth:true,
                multiselect: true,
                pager: "#jqCommunityGridPager",
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
                    $("#jqCommunityGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                }
            });
            $("#jqCommunityGrid").jqGrid("resetSelection");
        }
        ,
        reload: function () {
            $("#userTable").css("display","none");
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'communityName': vm.q.communityName,
                    'processName': vm.q.processName},
                page:page
            }).trigger("reloadGrid");
        }
    }
});