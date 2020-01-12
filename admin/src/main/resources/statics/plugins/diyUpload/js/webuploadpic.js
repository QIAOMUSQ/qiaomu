/************** 自定义上传图片js *******************/

function upload(pickId, uploadFilesId, entityPre, callback, pixel, fileNumLimit) {
	var tragger = true;
	// 初始化Web Uploader
	var uploader = WebUploader.create({
	    // 选完文件后，是否自动上传。
	    auto: true,
	    duplicate :true,
	    // 文件接收服务端。
		server: baseURL + 'welfare/sysFile/upload',
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: pickId,
		fileNumLimit: fileNumLimit,
	    fileSingleSizeLimit:2*1024*1024,
	    // 只允许选择图片文件。
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/*'
	    }
	});

	// 当有文件添加进来的时候
	uploader.on( 'fileQueued', function( file ) {
	});


	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function( file,response ) {
		callback(response, uploadFilesId, entityPre, tragger, pickId);
		tragger = true;
	});

	// 文件上传失败，显示上传出错。
	uploader.on( 'uploadError', function( file ) {
	    var $li = $( '#'+file.id ),
	        $error = $li.find('div.error');
	    // 避免重复创建
	    if ( !$error.length ) {
	        $error = $('<div class="error"></div>').appendTo( $li );
	    }
	    $error.text('上传失败');
	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').remove();
	});
	uploader.on('uploadBeforeSend', function (object) {
		if (pixel) {
			if (object.file._info) {
				var height = pixel.height;
				var width = pixel.width;
				if (height != 0 && object.file._info.height > height) {
					tragger = false;
					uploader.trigger('error', 'Q_TYPE_PIXEL_HEIGHT');
				}
				if (width == 0 && object.file._info.width > width) {
					tragger = false;
					uploader.trigger('error', 'Q_TYPE_PIXEL_WIDTH');
				}
			}
		}
	});
	uploader.on("error", function (type) {

		var text = '';
		switch (type) {
			case 'F_DUPLICATE':
				text = '该文件已经被选择了!';
				break;
			case 'Q_EXCEED_NUM_LIMIT':
				text = '上传文件数量超过限制!';
				break;
			case 'F_EXCEED_SIZE':
				text = '文件大小超过限制!';
				break;
			case 'Q_EXCEED_SIZE_LIMIT':
				text = '所有文件总大小超过限制!';
				break;
			case 'Q_TYPE_DENIED':
				text = '请上传GIF,JPG,JPEG,BMP,PNG格式文件';
				break;
			case 'Q_TYPE_PIXEL_HEIGHT':
				text = '该像素高度超过规定高度!';
				break;
			case 'Q_TYPE_PIXEL_WIDTH':
				text = '该像素宽度超过规定宽度!';
				break;
			default:
				text = '未知错误!';
				break;
		}
		alert(text);

    });

}

//图片tab中的上传图片
function setPics(response, uploadFilesId, entityPre, tragger, pickId) {
	if(response){
		let obj = response;
		if (obj.id && tragger) {
			let prevPicDiv = $("#picContents>div:last");
			let curPicDivId;
             if (prevPicDiv && prevPicDiv.length > 0) {
				 let prevPicDivId = prevPicDiv.attr("id").substring(12);
				 curPicDivId = parseInt(prevPicDivId) + 1;
             } else {
           	 	curPicDivId = 1;
             }
			let filedIsplayStr = "<div id='picContainerDiv"+curPicDivId+"' class=' ui-form-uploadfilediv' style='float: left;'>"+
										"<div id='picdiv"+curPicDivId+"' class='picdiv'>"+
				  							//"<img src=" + obj.path + " width='160px' height='120px' />" +
				  							"<img src=" + obj.path.substring(0,obj.path.indexOf("outapp/image"))+"welfare/sysFile/showPicForMany?id="+obj.id + " width='160px' height='120px' />" +
				  							"<p class='diyControl'>"+
												"<span class='diyCancel'><i></i></span>"+
											"</p>"+
				  							"<div class='pics-overlay'>"+
				  	  							"<a class='picDivClose close'><i class='iconfont icon-close'></i></a><br>"+
				  							"</div>"+
					                         "<div class='picDivSortNo'>"+
				  							"<input type='hidden' name='"+entityPre+"_"+curPicDivId+"' value='" + obj.path + "'>" +
											"<input type='hidden' name='fileSize' value='"+obj.fileSize+"'>"+
											"<input type='hidden' class='fileName' name='fileName' value='"+obj.path.substring(obj.path.lastIndexOf("/")+1,obj.path.length)+"'>"+
											"<input type='hidden' class='fileId' value='"+obj.id+"'>"+
			  							"</div>"+
									 "</div>";

			let filedisplay	= $(filedIsplayStr);
			$(".diyCancel", filedisplay).click(function(){
				let ob = $(this).parents("div.ui-form-uploadfilediv");
				let id = $($(ob).find('.fileId')).val();
				deleteFile(id);
				ob.remove();
			});
			$(uploadFilesId).append(filedisplay);
	 }else{
			deleteFile(obj.id);
	 	alert("图片上传失败！");
	 }
 }else{
    alert("图片上传失败！");
 }
}

//基本信息tab中的上传图片
function setPic(response, uploadFilesId, entityPre, tragger, pickId) {
	if (response) {
		 var obj = response;
		if (obj.id && tragger) {
			var filedisplay = $("<div class='fn-left ui-form-uploadfilediv' ><img src='" + obj.path + "' width='160px' height='120px'/><input type='hidden' name='" + entityPre + "' value='" + obj.path + "'/></div>");
            $(uploadFilesId).html(filedisplay);
		 }else{
			deleteFile(obj.id);
		 	alert("图片上传失败！");
		 }
     }else{
        alert("图片上传失败！");
     }
}
/************** 自定义上传图片js *******************/
/************** 自定义上传音视频start *******************/
function upload_audio(_application_static_url,pickId,uploadFilesId,entityPre,callback){
	// 初始化Web Uploader
	var uploader = WebUploader.create({
	    // 选完文件后，是否自动上传。
	    auto: true,
	    duplicate :true,
	    // swf文件路径
	    swf: _application_static_url+'/assets/baseinfo/webupload/Uploader.swf',

	    // 文件接收服务端。
	    server: _application_static_url+'/fileUpload/upload',

	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: pickId,
	    fileSingleSizeLimit:50*1024*1024,
	    // 只允许选择图片文件。
	    accept: {
	        title: 'Images',
	        extensions: 'mp3',
	        mimeTypes: 'video/*,audio/*,application/*'
	    }
	});

	// 当有文件添加进来的时候
	uploader.on( 'fileQueued', function( file ) {
	});


	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function( file,response ) {
		callback(response,_application_static_url,uploadFilesId,entityPre);
	});

	// 文件上传失败，显示上传出错。
	uploader.on( 'uploadError', function( file ) {
	    var $li = $( '#'+file.id ),
	        $error = $li.find('div.error');

	    // 避免重复创建
	    if ( !$error.length ) {
	        $error = $('<div class="error"></div>').appendTo( $li );
	    }

	    $error.text('上传失败');
	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').remove();
	});
	uploader.on("error", function (type) {
		debugger;
        if (type == "Q_TYPE_DENIED") {
			alert("请上传MP3格式文件", "warn");
        } else if (type == "Q_EXCEED_SIZE_LIMIT") {
        }else {
			alert("文件大小不能超过50M", "warn");
        }

    });
}
//音视频tab中的上传音视频
function setAudio(response,_application_static_url,uploadFilesId,entityPre){
	if(response){
        var obj = response;
        if(obj.flag == '1'){
             var prevPicDiv = $("#fileList>div:last");
             var curPicDivId;
             if (prevPicDiv && prevPicDiv.length > 0) {
           	 var prevPicDivId = prevPicDiv.attr("id").substring(15);
           	 curPicDivId = parseInt(prevPicDivId) + 1;
             } else {
           	 curPicDivId = 1;
             }
			  var filedIsplayStr = "<div id='avContainerDiv"+curPicDivId+"' class=' ui-form-uploadfilediv' style='min-height:175px;position:relative;'>"+
										"<div id='avdiv"+curPicDivId+"' class='picdiv'>"+
				 					    "<img src='"+_application_static_url+"/assets/baseinfo/images/video.jpg' width='160px' height='120px' />"+
				  							"<div class='pics-overlay'>"+
				  	  							"<a class='picDivClose close'><i class='iconfont icon-close'></i></a><br>"+
				  							"</div>"+
											"<input type='hidden' name='fileId' value=''>"+
											"<input type='hidden' name='path' value='"+obj.filePath+"'>"+
											"<input type='hidden' name='fileSize' value='"+obj.fileSize+"'>"+
											"<input type='hidden' class='ui-input' name='description' value='"+obj.fileNotes+"'>"+
											"<input type='text' name='fileName' value='"+obj.orgFileName+"' class='k-textbox' style='width:160px'><br/>"+
			  							"</div>"+
									 "</div>";

				var filedisplay	= $(filedIsplayStr);
				$(".close", filedisplay).click(function(){
						var ob = $(this).parents("div.ui-form-uploadfilediv");
						ob.remove();
				});
				$(uploadFilesId).append(filedisplay);
	 }else{
	 	alert("上传失败！");
	 }
 }else{
    alert("上传失败！");
 }
}

function deleteFile(id) {
	$.get(baseURL + "welfare/sysFile/removeFileById?id=" + id, function (result) {

	});
}