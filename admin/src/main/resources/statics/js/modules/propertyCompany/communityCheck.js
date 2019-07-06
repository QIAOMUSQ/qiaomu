/**
 * @description:
 * @author 李品先
 * @Date 2019-03-24 23:10
 */
$(function () {

    var currentDate = new Date();

    var currentTimeStamp = currentDate.getTime();

    currentTimeStamp = currentTimeStamp-86400000*2;
    var minDate = new Date(currentTimeStamp);


    var today = kendo.date.today();
    $("#startTime").kendoDatePicker({
        value: minDate,
        culture: "zh-CN",
        format: "yyyy-MM-dd",
        change: function () {

        },
    }).data("kendoDatePicker");

    $("#endTime").kendoDatePicker({
        value: today,
        culture: "zh-CN",
        min: $("#startTime").val(),//最小日期
        format: "yyyy-MM-dd",
        change: function () {

        }
    }).data("kendoDatePicker");

    $("#isCheck").kendoDropDownList();



    $("#jqGrid").jqGrid({
        url: baseURL + 'mobile/communityMessage/queryCommunityCheck',
        datatype: "json",
        mtype:"POST",
        colModel: [
            { label: 'id', name: 'id', index: "id", width: 45, key: true,hidden:true },
            { label: '申请人手机', name: 'userPhone',  width: 45 },
            { label: '社区名称', name: 'communityName', width: 75 },
            { label: '申请人人名', name: 'name', width: 90 },
            { label: '联系人号码', name: 'contactPhone', width: 90 },
            { label: '小区地址', name: 'address', width: 100 },
            { label: '时间', name: 'createTime', width: 100 },
            { label: '状态', name: 'isCheck', width: 100, formatter: function(value, options, row){
                if(value == "0"){
                    return   '<span class="label label-danger">未审核</span>';
                }else if(value =="1"){
                    return   '<span class="label label-success">通过</span>';
                }else {
                    return   '<span class="label label-danger">未通过</span>';
                }
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
        pager: "#jqGridPager",
        postData:{'startTime': $("#startTime").val(),
            'endTime': $("#endTime").val(),
            'isCheck':$("#isCheck").val()},
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
        },
    });
    //嵌套在指定容器中

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
        check: function () {
            var id = getSelectedRows();
            if(id == null){
                return ;
            }
           /* vm.showList = false;
            vm.title = "修改";*/
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
                layer.open({
                    type: 2,
                    skin: 'layui-layer-molv',
                    title: "社区审核",
                    area: ['650px', '350px'],
                    fixed:false,
                    content: baseURL + "mobile/communityMessage/getInfoDataById?id="+id,
                    closeBtn:0,
                    btn: ['确定','取消'],
                    yes:function(index,layero){//    //点击确定回调
                        var form = layer.getChildFrame('form', index);
                        $.ajax({
                            type:"POST",
                            url:baseURL + "mobile/communityMessage/addCommunity",
                            datType:"JSON",
                            data:form.serialize(),
                            success:function (data) {
                                 console.info(data);
                                if(data.status== "success"){
                                    vm.reload();
                                    layer.msg(data.data);
                                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                                }else {
                                    layer.msg(data.data); 
                                }
                            }
                        });

                    },
                    btn2: function (index) {

                    }
                });
            //});
        },
        reload: function () {
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'startTime': $("#startTime").val(),
                            'endTime': $("#endTime").val(),
                            'isCheck':$("#isCheck").val()},
                page:page
            }).trigger("reloadGrid");
        },

    }
});