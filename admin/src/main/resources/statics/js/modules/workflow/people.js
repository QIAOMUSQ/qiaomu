$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'communityUser/people/list',
        datatype: "json",
        mtype:"POST",
        colModel: [
            { label: '', name: 'id', width: 45,hidden:true  },
            { label: '用户手机', name: 'userPhone', width: 75 },
            { label: '真实姓名', name: 'realName', width: 75 },
            { label: '所属社区', name: 'communityName',  width: 45 },
            { label: '创建时间', name: 'createTime', width: 85},
            { label: '角色人', name: 'companyRoleType', width: 60,formatter:function (value, options, row) {
                if(value == "4"){
                    return '<span class="label label-danger">游客</span>';
                }else if(value == "3"){
                    return '<span class="label label-success">业主</span>';
                }else if(value == "2"){
                    return '<span class="label label-success" style="background: #ec971f">物业工作人员</span>';
                }else if(value == "1"){
                    return '<span class="label label-success" style="background: #ec971f">物业公司管理员</span>';
                }else if(value == "0"){
                    return '<span class="label label-success" style="background: #ec971f">超级管理员</span>';
                }
            }},
            { label: '审核账户', name: 'checkUser', width: 60},
            { label: '状态', name: 'check', width: 60, formatter: function(value, options, row){
                if(value == 0){
                    return '<span class="label label-danger">未审核</span>';
                }else if(value == 1){
                    return '<span class="label label-success">审核通过</span>';
                }else if(value == 2){
                    return '<span class="label label-success" style="background: #ec971f">审核不通过</span>';
                }else if(value == 3){
                    return '<span class="label label-success" style="background: #9f191f">禁用</span>';
                }
            }}

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
            idKey: "id",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }

    }
};

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            userPhone: null,
            communityName:null
        },
        showList: true,
        title:null,
        userExtend :{

        }, typeList:[]
    },
    methods: {
        check: function () {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }

            vm.showList = false;
            vm.title = "审核";
            vm.getUserExtent(userId);

            
        },
        query: function () {
            vm.reload();
        },
        del: function () {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }
            $.get(baseURL + "communityUser/info/"+userId, function(result){
                if(result.userExtendInfo.companyRoleType == "1"
                    && result.userExtendInfo.companyRoleType !=0){
                    confirm('确定要删除选中的记录？', function(){
                        $.ajax({
                            type: "POST",
                            url: baseURL + "communityUser/delete",
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
                }else {
                    alert('管理员不能删除', function(){
                        vm.reload();
                    });
                }
            });

        },
        saveOrUpdate: function (type) {
            $.ajax({
                type: "POST",
                url: baseURL + "communityUser/saveCheckInfo",
                data: {"id":$("#communityId").val(),
                        "info":$("#info").val(),
                        "type":type,
                        "companyRoleType": $("#companyRoleType").val()
                },
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
        getUserExtent: function(id){
            $.get(baseURL + "communityUser/info/"+id, function(result){
                var data = result.userExtendInfo;
                $("#realName").val(data.realName);
               // $("#userIdentity").val(data.userIdentity);
                $("#communityName").val(data.communityName);
                $("#createTime").val(data.createTime);
                $("#info").val(data.info);
                $("#userPhone").val(data.userPhone);
                $("#address").val(data.address);
                vm.userExtend.companyRoleType = data.companyRoleType;
                $("#companyRoleType").val(data.companyRoleType);
                $("#communityId").val(data.id);
              //  $("#imgUrl").attr("src", baseURL+"/mobile/sysFile/showPicForMany?id="+data.imgId);
                vm.getTypeList();
            });
        },
        unUsed: function (type) {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }
            $.get(baseURL + "communityUser/info/"+userId, function(result){
                if(result.userExtendInfo.companyRoleType != 1
                    && result.userExtendInfo.companyRoleType !=0){
                    $.ajax({
                        type: "POST",
                        url: baseURL + "communityUser/saveCheckInfo",
                        data: {"userPhone":result.userExtendInfo.userPhone,
                            "type":type
                        },
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
                }else {
                    alert('超级管理员不能更改', function(){
                        vm.reload();
                    });
                }

            });

        },
        getTypeList:function () {
            vm.typeList = [];
            $.get(baseURL + "sys/dict/getDictByType",{"type":"property_company"}, function(data){
                console.info(data);
                var i=0;
                $.each(data.dict,function (index,item) {
                    //过滤物业管理员和超级管理员
                    if(item.value != 1 && item.value != 0){
                        vm.typeList.push({"value":item.value,"name":item.code});
                    }
                });
                $("#roleType").kendoDropDownList({
                    dataTextField: "name",
                    dataValueField: "value",
                    dataSource: vm.typeList,
                    change: onchange,
                    value:$("#roleType").val()
                });
                vm.q.companyRoleType = $("#roleType").val();
            });
        }
        ,
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{"userPhone": vm.q.userPhone,
                            "communityName":vm.q.communityName},
                page:page
            }).trigger("reloadGrid");
        }
    }
});

function onchange() {
   // vm.q.companyRoleType = $("#roleType").val();
    $("#companyRoleType").val($("#roleType").val());
}