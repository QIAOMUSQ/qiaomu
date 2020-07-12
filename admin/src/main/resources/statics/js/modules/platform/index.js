/**
 * @description:
 * @author 李品先
 * @Date 2020-07-12 10:58
 */
var baseURL="../../";
//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li>',
        '	<a v-if="item.type === 0" href="javascript:;">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '		<i class="fa fa-angle-left pull-right"></i>',
        '	</a>',
        '	<ul v-if="item.type === 0" class="treeview-menu">',
        '		<menu-item :item="item" v-for="item in item.list"></menu-item>',
        '	</ul>',
        '	<a v-if="item.type === 1 && item.parentId === 0" :href="\'#\'+item.url">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '	</a>',
        '	<a v-if="item.type === 1 && item.parentId != 0" :href="\'#\'+item.url"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join('')
});

//iframe自适应
$(window).on('resize', function() {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function() {
        $(this).height($content.height());
    });
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
    el:'#rrapp',
    data:{
        user:{},
        menuList:{},
        main:"../../main.html",
        password:'',
        newPassword:'',
        navTitle:"控制台"
    },
    methods: {
        getMenuList: function (event) {
            let array = new Array();
            $.getJSON(baseURL +"sys/menu/nav?_"+$.now(), function(r){
                $.each(r.menuList,function (index,item) {
                    if (item.menuId === 111){
                        array.push(item);
                    }
                });
                vm.menuList = array;
            });

        },
        getUser: function(){
            $.getJSON(baseURL +"sys/user/info?_"+$.now(), function(r){
                vm.user = r.user;
                vm.getCompany();
            });

        },
        updatePassword: function(){
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改','取消'],
                btn1: function (index) {
                    var data = "password="+vm.password+"&newPassword="+vm.newPassword;
                    $.ajax({
                        type: "POST",
                        url: baseURL +"sys/user/password",
                        data: data,
                        dataType: "json",
                        success: function(result){
                            if(result.status == "success"){
                                layer.close(index);
                                layer.alert('修改成功', function(index){
                                    location.reload();
                                });
                            }else{
                                layer.alert(result.msg);
                            }
                        }
                    });
                }
            });
        },
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['http://cdn.renren.io/donate.jpg', 'no']
            });
        },
        getCompany: function(){
            $.ajax({
                type:"POST",
                url:baseURL+"propertyCompanyManage/findCompanyByUserId",
                success:function (result) {
                    if(result.status=="success"){
                        $(".navbar").css("background-color","#3cbc3f");
                        $(".logo").css("background-color","#3cbc3f");
                        $("#communityId").val(result.community.id);
                        $("#logo-lg").text("社区管理系统");
                    }
                }
            });
        },
        routerList:function (router, menuList) {
            for(var key in menuList){
                var menu = menuList[key];
                if(menu.type == 0){
                    this.routerList(router, menu.list);
                }else if(menu.type == 1){
                    router.add('#'+menu.url, function() {
                        debugger;
                        var url = window.location.hash;
                        //替换iframe的url
                        vm.main =baseURL+url.replace('#', '');

                        //导航菜单展开
                        $(".treeview-menu li").removeClass("active");
                        $("a[href='"+url+"']").parents("li").addClass("active");

                        vm.navTitle = $("a[href='"+url+"']").text();
                    });
                    if (vm.main == 'main.html'){
                        vm.main = menu.url;
                        vm.navTitle = menu.name;
                    }
                }
            }
        }
    },
    created: function(){
        this.getMenuList();
        this.getUser();
    },
    updated: function(){
        //路由
        var router = new Router();
        this.routerList(router, vm.menuList);
        router.start();
    }
});


