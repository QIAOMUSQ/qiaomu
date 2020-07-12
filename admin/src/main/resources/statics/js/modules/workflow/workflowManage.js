$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'processMessage/process/list',
        datatype: "json",
        mtype:"POST",
        colModel: [
            { label: '', name: 'id', width: 45,hidden:true,index: "id", key: true   },
            { label: '流程名称', name: 'processName', width: 45,sortable: false},
            { label: '流程类型', name: 'dicValueName', width: 75 ,sortable: false},
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

        getProcess: function(id){
            $.get(baseURL + "processMessage/getProcess/"+id, function(result){
                console.info(result);
                $("#phoneOne").val(result.process.phoneOneName);
                $("#communityName").val(result.process.communityName);
                $("#processName").val(result.process.processName);
                vm.processMessage.phoneOneId =result.process.phoneOneId ;
                vm.processMessage.processName =result.process.processName;
                vm.processMessage.dicValue = result.process.dicValue;
                vm.processMessage.communityId = result.process.communityId;
                vm.processMessage.id = result.process.id;
                vm.getDictor();
            });
        },
        //加载用户
        getUser: function(type){
            var communityId = vm.processMessage.communityId;
            if(communityId=='' || communityId ==undefined){
                return alert("请选择社区");
            }else {
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
                        var userId="";
                        debugger;
                        $.each(ids,function (index,item) {
                            userId += grid.getRowData(item).userId+",";
                            name +=grid.getRowData(item).realName+" ";
                        });
                        if(userId.length>0){
                            userId = userId.substring(0,userId.length-1);
                        }
                        if(type=="phoneOneId"){
                             vm.processMessage.phoneOneId =userId;
                            $("#phoneOne").val(name);
                        }
                        layer.close(index);
                    }
                });
                layer.ready(function () {
                    loadUser(communityId);
                });
            }
        },
        getDictor:function () {
            vm.typeList=[];
            $.get(baseURL + "sys/dict/getDictByType",{"type":"property_process"}, function(data){
                $("#dicValue").kendoDropDownList({
                    dataTextField: "code",
                    dataValueField: "value",
                    dataSource: data.dict,
                    index:0,
                    change: onchange
                });
                vm.processMessage.dicValue = $("#dicValue").val();
            });
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
                    vm.processMessage.phoneOneId =null;
                    $("#phoneOne").val("");

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
        },
        reload: function () {
            $("#userTable").css("display","none");
            vm.processMessage={
                communityId:null,
                id:null
            };
            $("#phoneOne").val("");
            $("#communityName").val("");
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

function loadUser (communityId) {
    $('#jqUserGrid').jqGrid('clearGridData');
    $('#jqUserGrid').jqGrid('setGridParam', {
        url: baseURL + 'communityUser/people/list',
        postData:{companyRoleType:"2",communityId:communityId}
    }).trigger('reloadGrid');
    $("#jqUserGrid").jqGrid({
        url: baseURL + 'communityUser/people/list',
        datatype: "json",
        mtype:"POST",
        postData:{companyRoleType:"2",communityId:communityId},
        colModel: [
            { label: '', name: 'id', width: 45,hidden:true  },
            { label: 'userId', name: 'userId',hidden:true },
            { label: '用户手机', name: 'userPhone', width: 75 },
            { label: '真实姓名', name: 'realName', width: 75 },
            { label: '所属社区', name: 'communityName',  width: 45 }
        ],
        viewrecords: false,
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
}

function onchange(e) {
    vm.processMessage.dicValue = $("#dicValue").val();
}

function saveOrUpdate() {
    if(vm.processMessage.phoneOneId == undefined || vm.processMessage.phoneOne ==""){
        return alert('请选择处理人员');
    }

    if( vm.processMessage.processName  ==undefined || vm.processMessage.processName  =="" ){
        return alert('请填写流程名称');
    }
    if(vm.processMessage.dicValue  ==undefined || vm.processMessage.dicValue  ==""){
        return alert('请填写流程类型');
    }
    if(vm.processMessage.communityId  ==undefined || vm.processMessage.communityId  ==""){
        return alert('请选择社区');
    }
    $.ajax({
        type: "POST",
        url: baseURL + "processMessage/process/save",
        contentType: "application/json",
         data: JSON.stringify(vm.processMessage),
        success: function(r){
            console.info(r);
            if(r.status == "success"){
                alert('操作成功', function(){
                    vm.reload();
                });
            }else if(r.data == 500){
                alert('保存失败');
            }
        }
    });
}