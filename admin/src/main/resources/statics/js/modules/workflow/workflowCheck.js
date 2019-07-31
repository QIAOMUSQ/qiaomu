$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mobile/processCheck/process/list',
        datatype: "json",
        mtype:"POST",
        colModel: [
            { label: '', name: 'id', width: 45,hidden:true  },
            { label: '业主', name: 'userName', width: 75 },
            { label: '流程名称', name: 'processName', width: 75 },
            { label: '处理时间', name: 'detailOneDate', sortable: false, width: 75 },
            { label: '主管审核时间', name: 'detailTwoDate',  width: 75 },
            { label: '创建时间', name: 'createTime', width: 75},
            { label: '详情', name: 'detail', width: 60},
            { label: '状态', name: 'type', width: 60, formatter: function(value, options, row){
                if(value == 0){
                    return '<span class="label label-danger">申请</span>';
                }else if(value == 1){
                    return '<span class="label label-success">一级受理完成</span>';
                }else if(value == 2){
                    return '<span class="label label-success" style="background: #ec971f">二级受理完成</span>';
                }else if(value == 3){
                    return '<span class="label label-success" style="background: #9f191f">上报</span>';
                }else if(value == 4){
                    return '<span class="label label-success" style="background: #9f191f">通过</span>';
                }else if(value == 5){
                    return '<span class="label label-success" style="background: #9f191f">不通过</span>';
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
            communityName: null,
            phone:null
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
                $.each(r.dict,function (index,item) {
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
                postData:{'username': vm.q.username},
                page:page
            }).trigger("reloadGrid");
        }
    }
});