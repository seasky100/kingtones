//----------------【定义flexgrid开始】--------------------------------------------------------------------------
	var addjwvideo='none';
	var deljwvideo ='none';
	var editjwvideo='none';
	 var str=document.cookie.split(";")
	 var temp;
	 var temp1;
	 var  i=0;
	 for(var i=0;i<str.length;i++){
		 temp = str[i];
		 if(temp.indexOf("ROLE_BUTTON")>=0){
		 	 temp1=temp.split("=")[1];
			 if(temp1.indexOf("addjwvideo")>=0){
				 addjwvideo='inline';
			 }
			 if(temp1.indexOf("deljwvideo")>=0){
				 deljwvideo='inline';
			 }
			 if(temp1.indexOf("editjwvideo")>=0){
				 editjwvideo='inline';
			 }
			 break;
		}
	 }
	$("#flex1").flexigrid(
	{
		url: webroot+'/xajw/jwVideoAC!queryJwVideoList.action',
		dataType: 'json',
		colModel : [
			{display: '视频编号', name : 'id', width : 100, sortable : true, align: 'center',hide: true},
			{display: 'IP地址', name : 'ip', width : 100, sortable : true, align: 'left'},
			{display: '上传端口', name : 'port', width : 100, sortable : true, align: 'left'},
			{display: '采集地点', name : 'point', width : 100, sortable : true, align: 'left'},
			{display: '是否可用', name : 'jk_state', width : 100, sortable : true, align: 'left'},
			{display: '排序编号', name : 'order_id', width : 100, sortable : true, align: 'center',hide: true},
			{display: '描述', name : 'description', width : 100, sortable : true, align: 'left'}
			],
		buttons : [
			{name: '增加',  onpress : btn_even,state:addjwvideo},
			{separator: true,sepstate:addjwvideo},
			{name: '修改', onpress : btn_even,state:editjwvideo},
			{separator: true,sepstate:editjwvideo},
			{name: '删除', onpress : btn_even,state:deljwvideo},
			{separator: true,sepstate:deljwvideo}
			],
		searchitems : [
			{display: 'IP地址', name : 'ip' , isdefault: true},
			{display: '上传端口', name : 'port'},
			{display: '采集地点', name : 'point'}
			],
		sortname: "iso",
		sortorder: "asc",
		usepager: true,
		useRp: true,
		rp: 10,
		showTableToggleBtn: true,
		resizable:false,  //窗口伸缩
		onSubmit: addFormData,
		width:800,
		height: 400
		}
		);	
		
	function addFormData()
	{
		return true;
	}
	//刷新列表	
	function refreshGrid(){
		$('#flex1').flexOptions({params: [{ name: 'null', value: 'null'}]}).flexReload();
	}	
	var selectItemId;
	function btn_even(com,grid){
		if(com=='反选'){
			$('tr',grid).toggleClass('trSelected'); 
		}
		else if (com=='增加')
		{
			
			url=webroot+"/biz/xajw/jwvideo_add.jsp";
            window.showModalDialog(url,window,'status:false;dialogWidth:450px;dialogHeight:400px;dialogLeft:300px;dialogTop:230px;center:yes');
			
		}	
		else if (com=='修改')
		{
			selectItemId = $('.trSelected',grid).find("td:first").eq(0).text();
			if(selectItemId==''){
				alert("请选择要修改的数据项！");
				return;
			}else if($('.trSelected',grid).length>1){
			alert("请选择一项要修改的数据项！");
				return;
			}
			url=webroot+"/biz/xajw/jwvideo_edit.jsp";
            window.showModalDialog(url,window,'status:false;dialogWidth:450px;dialogHeight:400px;dialogLeft:300px;dialogTop:230px;center:yes');
			
		}else if (com=='删除')
		{
			if($('.trSelected',grid).length==0){
				alert("请选择要删除的数据项！");
				return;
			}
			else{
			 if(confirm('是否要删除该项！')){  
			var jwvideoIds = $('.trSelected',grid).find("td:first").eq(0).text();
			for(var i=1;i<$('.trSelected',grid).find("td:first").length;i++){
				jwvideoIds+="split"+$('.trSelected',grid).find("td:first").eq(i).text();
			}
			$.ajax({
		    	url: webroot+'/xajw/jwVideoAC!delJwVideo.action',
		        data:'jwVideoID='+jwvideoIds,
		    	type: 'POST',
			    dataType: 'json',
			    error: function(){
					alert("删除失败");
			    },
			    success: function(data){
			        alert("删除成功");
					refreshGrid();
			    }
			});}}
		}	
								
	}