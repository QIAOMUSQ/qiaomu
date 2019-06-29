$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'propertyCompanyManage/company/list',
        datatype: "json",
        mtype:"POST",
        colModel: [
            { label: 'id', name: 'id', index: "id", width: 45, key: true,hidden:true },
            { label: '企业名称', name: 'name',  width: 45 },
            { label: '法人名称', name: 'legalPerson', width: 75 },
            { label: '联系号码', name: 'telPhone', sortable: false, width: 75 },
            { label: '地址', name: 'address', width: 90 },
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
        company:{
            name:null,
            id:null
        },
        loginUser:"",
        userList:[]
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.getLoginAdministrator();
        },

        update: function () {
            var id = getSelectedRows();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getCompany(id);

            
        },
        del: function () {
            var id = getSelectedRows();
            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "propertyCompanyManage/delete",
                    contentType: "application/json",
                    data: JSON.stringify(id),
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
            var url = vm.company.id == null ? "propertyCompanyManage/save" : "propertyCompanyManage/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.company),
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
        getCompany: function(id){
            $.get(baseURL + "propertyCompanyManage/info/"+id, function(data){
                vm.company.id = data.company.id;
                vm.company.name = data.company.name;
                vm.company.legalPerson=data.company.legalPerson;
                vm.company.telPhone = data.company.telPhone;
                vm.company.address =data.company.address;
                vm.company.companyImg=data.company.companyImg;
                vm.company.adminPhone = data.company.adminPhone;
                vm.getLoginAdministrator();
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
        getLoginAdministrator:function () {
            $.post(baseURL + "sys/user/getLoginUser", {"deptId":6},function(result){
                console.info(result);
                /*vm.userList = [];
                var i =0;
                $.each(result.user,function (index,item) {
                    if(item.userName == vm.company.adminPhone){i = index;}
                    vm.userList.push({value:item.username,text:item.username});
                })
                vm.loginUser =vm.userList[i].value;*/
                $("#userId").kendoDropDownList({
                    dataTextField: "username",
                    dataValueField: "userId",
                    dataSource: result.user,
                    index:0,
                    change: onchange
                });
                vm.company.administratorId = $("#userId").val();
            });
        }
    }
});

function onchange() {
    vm.company.administratorId = $("#userId").val();
}
