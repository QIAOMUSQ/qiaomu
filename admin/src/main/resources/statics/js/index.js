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
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:"控制台"
	},
	methods: {
		getMenuList: function (event) {
			$.getJSON("sys/menu/nav?_"+$.now(), function(r){
				let array = new Array();
				$.each(r.menuList,function (index,item) {
					if (item.menuId != 111){
						debugger;
						array.push(item);
					}
				});
				vm.menuList = array;
			});
		},
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
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
					    url: "sys/user/password",
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
			let user = vm.user;
			if(user.userId == 1 ){
				$("#reBack").css("display","none");
				$("#logo-lg").text("100分社区管理");
			}else {
				$.ajax({
					type:"POST",
					url:"propertyCompanyManage/findCompanyByUserId",
					success:function (result) {
						if(result.status=="success"){
							if (result.community){
								$(".navbar").css("background-color","#3cbc3f");
								$(".logo").css("background-color","#3cbc3f");
								$("#communityId").val(result.community.id);
								$("#logo-lg").text("社区管理系统");
							}else if (result.company) {
								if(result.company.name.length>6){
									$("#logo-lg").text((result.company.name+"管理系统").substring(0,10));

								}else {
									$("#logo-lg").text(result.company.name+"管理系统");
								}
								$("#companyId").val(result.company.id);
								$("#companyName").val(result.company.name);
								$("#welcome-system").text("欢迎进入"+result.company.name+"  管理员："+vm.user.username);
								$(".navbar").css("background-color","#3cbc3f");
								$(".logo").css("background-color","#3cbc3f");
							}
						}
					}
				});
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
		routerList(router, vm.menuList);
		router.start();
	}

});



function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			routerList(router, menu.list);
		}else if(menu.type == 1){

			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				//替换iframe的url
			    vm.main = url.replace('#', '');
			    
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


