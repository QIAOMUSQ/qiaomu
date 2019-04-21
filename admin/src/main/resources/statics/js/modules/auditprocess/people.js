$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'communityUser/people/list',
        datatype: "json",
        mtype:"POST",
        colModel: [
            { label: '', name: 'id', width: 45,hidden:true  },
            { label: '用户手机', name: 'userPhone', width: 75 },
            { label: '真实姓名', name: 'realName', width: 75 },
            { label: '证件号', name: 'userIdentity', sortable: false, width: 75 },
            { label: '所属社区', name: 'communityName',  width: 45 },
            { label: '创建时间', name: 'creatTime', width: 85},
            { label: '角色人', name: 'propertyCompanyRoleType', width: 60,formatter:function (value, options, row) {
                if(value == 1){
                    return '<span class="label label-danger">超级管理员</span>';
                }else if(value == 2){
                    return '<span class="label label-success">工作人员</span>';
                }else if(value == 3){
                    return '<span class="label label-success" style="background: #ec971f">业主</span>';
                }else {
                    return '<span class="label label-success"></span>';
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
                }else {
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
                if(result.userExtendInfo.propertyCompanyRoleType == "1"
                    && result.userExtendInfo.propertyCompanyRoleType !=0){
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
                data: {"userPhone":$("#userPhone").val(),
                        "info":$("#info").val(),
                        "type":type,
                        "roleType": vm.userExtend.propertyCompanyRoleType
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
                if(result.userExtendInfo.propertyCompanyRoleType != 1
                    && result.userExtendInfo.propertyCompanyRoleType !=0){
                    $("#realName").val(result.userExtendInfo.realName);
                    $("#userIdentity").val(result.userExtendInfo.userIdentity);
                    $("#communityName").val(result.userExtendInfo.communityName);
                    $("#creatTime").val(result.userExtendInfo.creatTime);
                    $("#info").val(result.userExtendInfo.info);
                    $("#userPhone").val(result.userExtendInfo.userPhone);
                    $("#address").val(result.userExtendInfo.address);
                    vm.userExtend.propertyCompanyRoleType = result.userExtendInfo.propertyCompanyRoleType;
                    $("#imgUrl").attr("src", baseURL+"/App/upload/showPicture?id="+result.userExtendInfo.imgUrl);
                    vm.getTypeList();
                }else {
                    alert('超级管理员不能更改', function(){
                        vm.reload();
                    });
                }

            });
        },
        unUsed: function (type) {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }
            $.get(baseURL + "communityUser/info/"+userId, function(result){
                if(result.userExtendInfo.propertyCompanyRoleType != 1
                    && result.userExtendInfo.propertyCompanyRoleType !=0){
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
            $.get(baseURL + "sys/dict/getDictByType",{"type":"property_company"}, function(r){
                var i=0;
                $.each(r.dict,function (index,item) {
                    if(vm.userExtend.propertyCompanyRoleType = item.value){
                        i = index;
                    }
                    if(item.value != 1 && item.value != 0){
                        vm.typeList.push({"value":item.value,"name":item.code});
                    }
                    
                });
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