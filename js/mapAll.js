//In the following example, markers appear when the user clicks on the map.
	//The markers are stored in an array.
	//The user can then click an option to hide, show or delete the markers.
	var map;
	var markers = [];
	var geocoder;

	var marker_list = {};
	var mapNum_list = [];
	var map_list;
	var marker;
	var marker_to_add = [];
	var obj;
	var listcontent = [];
	
	var infowindow;
	var test;
	var hover_mark;
	var answer;
	var answeraction;
	
	var oEditors = []; // 개발되어 있는 소스에 맞추느라, 전역변수로 사용하였지만, 지역변수로 사용해도 전혀 무관 함.
	
	function initMap() {
		geocoder = new google.maps.Geocoder();
		var haightAshbury = {
			lat : 37.431114,
			lng : 126.67236
		};
		
		setMarkers(haightAshbury);

		map = new google.maps.Map(document.getElementById('map'), {
			zoom : 17,
			center : haightAshbury,
			mapTypeId : 'roadmap'
		});
		
		///////////////////////////////////gps적용//////////////////////////////////////
	    var infoWindow = new google.maps.InfoWindow({map: map});
	    
	    // gps가 동작하면 if 수행
	     if (navigator.geolocation) {
	       navigator.geolocation.getCurrentPosition(function(position) {
	         var pos = {
	           lat: position.coords.latitude,
	           lng: position.coords.longitude
	         };

	         addMarker(pos); // 해당 위치 pos에 마커 추가
	         infoWindow.setContent('현재 위치sdsdsdsd');
	         infoWindow.close();
	         map.setCenter(pos); // 마커를 중앙에 위치 시킴 (pos를 맵의 중앙 위치로 잡음)
	       }, function() {
	         handleLocationError(true, infoWindow, map.getCenter());
	       });
	     } else {
	       // Browser doesn't support Geolocation
	       handleLocationError(false, infoWindow, map.getCenter());
	     }
		 ///////////////////////////////////gps적용//////////////////////////////////////
		
//		map.addListener('click', function(event) {
//			deleteMarkers();
//			var latlng = event.latLng; // 클릭한 위도, 경도 정보를 가져옴
//			var lat = latlng.lat(); // 위도 경도중 위도를 추출
//			var lng = latlng.lng(); // 위도 경도중 경도를 추출
//			
//			addMarker(latlng); // 해당 위도, 경도에 마커를 추가시킴
//
//			document.maps.clickLatlng.value = latlng; // input의 name이 clickLatlng인 곳에 위도,경도를 추가시킴
//			document.maps.lat.value = lat; // input의 name이 lat인 곳에 위도를 추가시킴
//			document.maps.lng.value = lng; // input의 name이 lng인 곳에 경도를 추가시킴
//			
//			/*  document.getElementById('clickLatlng').innerHTML = latlng;
//			    document.getElementById('lat').innerHTML = lat;
//			    document.getElementById('lng').innerHTML = lng; */
//
//		});

		/////////////////////////////////지도 검색//////////////////////////////
		
		

		var input = document.getElementById('input_search_location');

		var autocomplete = new google.maps.places.Autocomplete(input);
		autocomplete.bindTo('bounds', map);
		
		
		
		
// 		$('#btn_search_location').click(function(){
			
// 			var locat = $('#input_search_location').val();
// 			locat = encodeURIComponent(locat);

			
		
// 		});

// 		map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

		autocomplete.addListener('place_changed', function() {

			var place = autocomplete.getPlace();
			if (!place.geometry) {
				return;
			}

			if (place.geometry.viewport) {
				map.fitBounds(place.geometry.viewport);
			} else {
				map.setCenter(place.geometry.location);
				map.setZoom(17);
			}

			// Set the position of the marker using the place ID and location.
			marker.setPlace({
				placeId : place.place_id,
				location : place.geometry.location
			});
			marker.setVisible(true);

			/* document.getElementById('place-name').textContent = place.name;
			 document.getElementById('place-id').textContent = place.place_id;
			 document.getElementById('place-address').textContent = place.formatted_address; */
//			infowindow
//					.setContent(document.getElementById('infowindow-content'));
//			infowindow.open(map, marker);
			
		});

		/////////////////////////////////지도 검색//////////////////////////////

		addMarker(haightAshbury);
		
	}
	
 	function setMarkers(location) {
       
        for (var i = 0; i < 1; i++) {
        	var marker = new google.maps.Marker({
            	position: location,
            	map: map
        	});
    		markers.push(marker);
    	}
        
	}
	
	


	function addMarker(location) {
		var marker = new google.maps.Marker({
			position : location,
			map : map
		});
		markers.push(marker);
	}


	function setMapOnAll(map) {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(map);
		}
	}

	
	function clearMarkers() {
		for (var i = 0; i < marker_to_add.length; i++) {
			//markers[i].setMap(null);
			marker_to_add[i].setMap(null);
		}
	}

	function showMarkers() {
		setMapOnAll(map);
	}

	
	function deleteMarkers() {
		clearMarkers();
		//markers = [];
		marker_to_add = [];
	}
	
	 function codeAddress() {
		    var address = document.getElementById('input_search_location').value;
		    geocoder.geocode( { 'address': address}, function(results, status) {
		      if (status == 'OK') {
		        map.setCenter(results[0].geometry.location);
		        var marker = new google.maps.Marker({
		            map: map,
		            position: results[0].geometry.location
		        });
		      } else {
		        alert('Geocode was not successful for the following reason: ' + status);
		      }
		    });
		  }
	 
	 
	 $(document).ready(function(){
			
		// Editor Setting
		 	nhn.husky.EZCreator.createInIFrame({
		 		oAppRef : oEditors, // 전역변수 명과 동일해야 함.
		 		elPlaceHolder : "mapRstMenu", // 에디터가 그려질 textarea ID 값과 동일 해야 함.
		 		sSkinURI : "SE2/SmartEditor2Skin.html", // Editor HTML
		 		fCreator : "createSEditor2", // SE2BasicCreator.js 메소드명이니 변경 금지 X
		 		htParams : {
		 			// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
		 			bUseToolbar : true,
		 			// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		 			bUseVerticalResizer : true,
		 			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		 			bUseModeChanger : false, 
		 		}
		 	});
		 
			$("#saveData").click(function(){
//				console.log( "click!" );
				//id 가 saveDataForm 이란놈 폼데이터 다 긁어오는거
				oEditors.getById["mapRstMenu"].exec("UPDATE_CONTENTS_FIELD", []);
				
				var formData = $("#saveDataForm").serialize();
					
				
				 $.ajax({
		 					type : "POST",
		 					url : "/MapInsert.mp",
		 					cache : false,
		 					data : formData,
		 					success : onSuccess2,
		 					error : onError
				}); 
			});
			
			$("#averageanswersave").click(function(){
				var formDataAverage = $("#averageansform").serialize();
					
				
				 $.ajax({
		 					type : "POST",
		 					url : "/MapInsertAnswerAction.mp",
		 					cache : false,
		 					data : formDataAverage,
		 					success : onSuccessAverage,
		 					error : onError
				}); 
			});
			
			
			$("#map").mouseup(function(){
//				console.log( "mouseup!" );
//				$("#test007").reset();		
				$("#test007").empty();
//				setMapOnAll(null);
				//deleteMarkers();
				 $.ajax({
					 		type : "POST",
					 		url : "/MapList.mp",
					 		cache : false,
					 		success : onSuccess1,
					 		error : onError
						}); 
				 
				});
//			$("#mainMenu").css("font-size", 20+"px");
			$("#mainMenu").css("background-color", "#F2F2F2");
		});
		//리스트 뽑아 오기


		
		var clickname = null;
		function onSuccess1(json, status){//돌려봐 ㄱㄱrrrr
			//console.log('START ');
			deleteMarkers();
//			console.log('length : '+json.array.length);
//			console.log(json.array);
			test = json.array;
//			test = json.mapNum;
			var bound_now = map.getBounds();
//			console.log(bound_now);
			
			for(var i=0;i<json.array.length;i++){
				obj = json.array[i];
				var mapRstName = obj.mapRstName;
				listcontent = "<div>"+mapRstName+"</div>";

//				console.log(listcontent);
				infowindow = new google.maps.InfoWindow({
				    content: mapRstName
				    
				});
				
				var bool_disautopan = true;
				hover_mark = new google.maps.InfoWindow({map: map, disableAutoPan: bool_disautopan});
				hover_mark.close();
				
				if(obj.lat > bound_now.f.b
						&& obj.lat < bound_now.f.f
						&& obj.lng > bound_now.b.b
						&& obj.lng < bound_now.b.f){
				
				//test = obj;
//				console.log(test); //
		 	 	var value = "<tr data-num="+obj.mapNum +" onclick=javascript:view_store('"+obj.mapNum +"')>"
		 	 	+"<td style='vertical-align: middle;'>"+obj.mapCategori
		 	 	+"</td><td style='vertical-align: middle;'>"+obj.mapRstName+"</td><td style='vertical-align: middle;'>"+obj.mapMenu
		 	 	+"</td><td style='vertical-align: middle;'>"+obj.mapTel+"</td><td style='vertical-align: middle;'>"+obj.mapMove
		 	 	+"</td><td style='vertical-align: middle;'>"+toStar(obj.mapAverage)+"</td></tr>";
		 	 	//console.log(value);
		 	 	
		 		$("#test007").append(value);
		 		
		 		//마커 입히기
		 		
				var image = {
					url : 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png',

					size : new google.maps.Size(20, 32),

					origin : new google.maps.Point(0, 0),

					anchor : new google.maps.Point(0, 32)
				};

				var shape = {
					coords : [ 1, 1, 1, 20, 18, 20, 18, 1 ],
					type : 'poly'
				};

				
					marker = new google.maps.Marker({
						position : {
							lat : parseFloat(obj.lat), /* id 가 lat1, lat2 ... 인 것의 값을 float로 형변환 하여 적용 */
							lng : parseFloat(obj.lng)/* id 가 lng1, lng2 ... 인 것의 값을 float로 형변환 하여 적용 */
						},
						map : map,
						icon : image,
						shape : shape
					});
					var mapNum = obj.mapNum;
					var maplat = obj.lat;
					var maplng = obj.maplng;
					var clicked_num = json.array[i].mapNum;
					var clicked_name = json.array[i].mapRstName;
					var clicked_lat = json.array[i].lat;
					var clicked_lng = json.array[i].lng;
//					console.log(json.array[i].mapNum);
					marker.addListener('mouseover', (function(mapNum,mapRstName,maplat,maplng){
						return function(){$("#test007>tr[data-num="+mapNum +"]").addClass("marker_over");
														$("#test007>tr[data-num="+mapNum +"]").css("background-color", "#F7BE81");
														hover_mark.setPosition({lat: parseFloat(maplat+0.0002), lng: parseFloat(maplng)});
														hover_mark.setContent(mapRstName);
														hover_mark.setMap(map);}
					})(clicked_num,clicked_name,clicked_lat,clicked_lng));
					
					marker.addListener('mouseout', (function(mapNum,mapRstName,maplat,maplng){
						return function(){$("#test007>tr[data-num="+mapNum +"]").removeClass("marker_over");
														$("#test007>tr[data-num="+mapNum +"]").css("background-color", "");
														hover_mark.close();}
					})(clicked_num,clicked_name,clicked_lat,clicked_lng));

					marker.addListener('click', (function(mapNum){
						return function(){exit_fullscreen();view_store(mapNum);}
					})(clicked_num));
					
					marker_to_add.push(marker);
				}

//				$("#test007").children('tr').hover(
//						function (clicked_idx,clicked_name,clicked_lat,clicked_lng){
////							console.log($(this))
//							$(this).css("background-color", "#F7BE81");
//							$(this).css("cursor", "pointer");
////							infowindow.open(map, marker);
////							console.log(clicked_idx);
//						},		
//						function (){
//							$(this).css("background-color", "");
//							infowindow.close();
//				});
				
				$("body").on("mouseover","#test007>tr[data-num="+mapNum +"]",(function (mapNum,mapRstName,maplat,maplng){
							return function(){$(this).css("background-color", "#F7BE81");
															$(this).css("cursor", "pointer");
															hover_mark.setPosition({lat: parseFloat(maplat+0.0002), lng: parseFloat(maplng)});
															hover_mark.setContent(mapRstName);
															hover_mark.setMap(map);}
				})(clicked_num,clicked_name,clicked_lat,clicked_lng));
				
				$("body").on("mouseout","#test007>tr",(function (mapNum,mapRstName,maplat,maplng){
					return function(){$(this).css("background-color", "");
													hover_mark.close();}
		})(clicked_num,clicked_name,clicked_lat,clicked_lng));
			}
			
			
			
//			var value = "<tr><td>"+obj.mapCategori
//					value +="</td><td>"+obj.mapRstName+"</td><td>"+obj.mapMenu
//					value +="</td><td>"+obj.mapTel+"</td><td>"+obj.mapMove
//					value +="</td><td></td></tr>";
//			$("#test007").append(value);
			
				
		}
		
		function toStar(mapAverage){
			if(mapAverage == 0){
				var str = "<div style='text-align: center;'><span class='star-input0'>"+
											"<span class='input'>"+
									  	"</span>"+					
									"</span></div>";
			}else if(mapAverage == 1){
				var str = "<div style='text-align: center;'><span class='star-input1'>"+
										"<span class='input'>"+
								  	"</span>"+					
								"</span></div>";
			}else if(mapAverage == 2){
				var str = "<div style='text-align: center;'><span class='star-input2'>"+
										"<span class='input'>"+
								  	"</span>"+					
								"</span></div>";
			}else if(mapAverage == 3){
				var str = "<div style='text-align: center;'><span class='star-input3'>"+
										"<span class='input'>"+
								  	"</span>"+					
								"</span></div>";
			}else if(mapAverage == 4){
				var str = "<div style='text-align: center;'><span class='star-input4'>"+
										"<span class='input'>"+
								  	"</span>"+					
								"</span></div>";
			}else if(mapAverage == 5){
				var str = "<div style='text-align: center;'><span class='star-input5'>"+
										"<span class='input'>"+
								  	"</span>"+					
								"</span></div>";
			}

			
			return str;
		}
		
		function exit_fullscreen() {
			var isInFullScreen = (document.fullscreenElement && document.fullscreenElement !== null) ||
			(document.webkitFullscreenElement && document.webkitFullscreenElement !== null) ||
			(document.mozFullScreenElement && document.mozFullScreenElement !== null) ||
			(document.msFullscreenElement && document.msFullscreenElement !== null);

			var docElm = document.documentElement;
			if (isInFullScreen) {
				if (document.exitFullscreen) {
					document.exitFullscreen();
				} else if (document.webkitExitFullscreen) {
					document.webkitExitFullscreen();
				} else if (document.mozCancelFullScreen) {
					document.mozCancelFullScreen();
				} else if (document.msExitFullscreen) {
					document.msExitFullscreen();
				}
			}
		}
		//function setMapOnAll(map){
//			for(var i = 0; i<marker.length; i++){
//				marker[i].setMap(map);
//			}
		//}
		function test1111(data){
			console.log(data);
		}

		function logout(){
				alert("정상적으로 로그아웃 되셨습니다^^");
				location.href = "/logoutAction.us";
		}
		 
		function logincheck2(){
			alert("로그인 하세요^^");
			return null;
		}
		
		function onSuccess2(json, status){
			alert('성공적으로 등록 되었습니다^^!');
		}
		function onSuccessAverage(json, status){
			$("#mapContent").val('');
			var Num = json.mapNum;
			
	 			$("#averageanswer").empty();
	 			$.ajax({
					url:'/MapAverageAnswerAction.mp',
					type:"POST",
					dataType: 'json',
					data:{"mapNum":Num},
					success:function(params1){
						for(var i=0;i<params1.array.length;i++){
							answeraction = params1.array[i];
							
					 	 	var value = "<tr><th>"+answeraction.mapNickcheck+"</th><td>"+answeraction.Date+"</td>"
							+"<td>"+toStar(answeraction.mapAverage)+"</td></tr>"
							+"<tr><td colspan='3'>"+answeraction.mapContent+"</td></tr>";
					 	 	
					 		$("#averageanswer").append(value);
						}
					}
				});
			
		}
		function onError(data, status){alert("빈칸 없이 입력해 주세요^^");}

	 
	 //등록창 맵
	 var map_add_store;
	 var marker_add_store;

	 function init_add_store_map() {
	 	var seoul = {lat: 37.5650168, lng: 126.8491196};

	 	map_add_store = new google.maps.Map(document.getElementById('map_add_store'), {
	 		center: seoul,
	 		zoom: 15
	 	});
	 	map_add_store.addListener('click',function(e){
	 		
	 		if(marker_add_store)
	 			marker_add_store.setMap(null);
	 		

	 		var geocoder = new google.maps.Geocoder();
	        
	        geocoder.geocode({'latLng' : e.latLng}, function(results, status) {

	              if (status !== 'OK') {
	                window.alert('Geocoder failed due to: ' + status);
	                return;
	              }
	              
	              document.saveDataForm.address.value = results[0].formatted_address; // 주소 저장하는 것
	              
	              /* document.getElementById('place-name').textContent = place.name;
	              document.getElementById('place-id').textContent = place.place_id;
	              document.getElementById('place-address').textContent =
	                 results[0].formatted_address;
	               */
	              
	            });

	        marker_add_store = new google.maps.Marker({
	 			map: map_add_store,
	 			position: e.latLng
	 		});
	 		$('#input_add_map_lat').val(e.latLng.lat);
	 		$('#input_add_map_lng').val(e.latLng.lng);
	 		
	 	});
	 	

	 	if (navigator.geolocation) {
	 		navigator.geolocation.getCurrentPosition(function(position) {
	 			var pos = {
	 				lat: position.coords.latitude,
	 				lng: position.coords.longitude
	 			};

	 			map_add_store.setCenter(pos);
	 			map_add_store.setZoom(14);
	 		});
	 	}

	 }



	 $(function(){
	 	$('#btn_add_store_map').click(function(){

	 		if(geocoder == null)
	 			geocoder = new google.maps.Geocoder();

	 		geocoder.geocode({'address': $('#input_add_store_map').val()}, function(results, status) {
	 			if (status === 'OK') {
	 				$('#input_add_store_map').val('');

	 				map_add_store.setCenter({
	 					lat:results[0].geometry.location.lat(),
	 					lng:results[0].geometry.location.lng()
	 				});
	 				if(marker_add_store)
	 					marker_add_store.setMap(null);

	 				marker_add_store = new google.maps.Marker({
	 					map: map_add_store,
	 					position: map_add_store.getCenter()
	 				});
	 				$('#input_add_map_lat').val(results[0].geometry.location.lat());
	 				$('#input_add_map_lng').val(results[0].geometry.location.lng());
	 				
	 			} else {
	 				alert("검색에 실패했습니다. :" + status);
	 				$(this_el).removeClass('disabled');
	 			}
	 		});

	 	});
	 	
	 	$('#add_store').on('show.bs.modal', function(){
	 		setTimeout(function(){init_add_store_map();},300)
	 	});
	 	$('.modal').on('show.bs.modal', function(){
	 		if($(this).find('form').get(0)!=null)
	 			$(this).find('form').get(0).reset();
	 	});
	 })
	 
	 
//가게 상세보기
function view_store(mapNum){
	$("#averageanswer").empty();
	$('#modal_view_store').modal('show');
	
	$.ajax({
		url:'/MapDetailViewAction.mp',
		type:"POST",
		dataType: 'json',
		data:{"mapNum":mapNum},
		success:function(params){
			if(params['error']){
				alert(params['msg']);
				$('#modal_view_store').modal('hide');
				return;
			}
			//var data = params['data'];
			$("#averageanswer").empty();
			var link_google_map = " <a target='_blank' href='https://www.google.com/maps/search/?api=1&query="+params.lat+","+params.lng+"&z=18'>지도 보기</a>";
			$('#modal_view_store #view_store_label').html(params.mapRstName);
			$('#modal_view_store .name').html(params.mapRstName);
			$('#modal_view_store .gpu').html(params.mapRstMenu);
			document.averageans.mapNumAns.value = params.mapNum;
			var geocoder = new google.maps.Geocoder();
			geocoder.geocode({
				'latLng' : {
					lat: parseFloat(params.lat),
					lng: parseFloat(params.lng)
				}
			}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					$('#modal_view_store .location').html(params.address);
					$('#modal_view_store .location').append(link_google_map);
				}
				else
				{
					$('#modal_view_store .location').html("주소 없음");
					$('#modal_view_store .location').append(link_google_map);
				}
			});

			$('#modal_view_store .options').html(params.mapMove);
			
			//평가 댓글
			$.ajax({
				url:'/MapAverageAnswerAction.mp',
				type:"POST",
				dataType: 'json',
				data:{"mapNum":mapNum},
				success:function(params){
					if(params['error']){
						alert(params['msg']);
						$('#modal_view_store').modal('hide');
						return;
					}
					
					//answer = params.array;
					for(var i=0;i<params.array.length;i++){
						answer = params.array[i];
				 	 	
				 	 	var value = "<tr><th>"+answer.mapNickcheck+"</th><td>"+answer.Date+"</td>"
						+"<td>"+toStar(answer.mapAverage)+"</td></tr>"
						+"<tr><td colspan='3'>"+answer.mapContent+"</td></tr>";
				 	 	
				 		$("#averageanswer").append(value);
				 		
					}	
				}
			});
			
		},
		fail:function(params){
			$('#modal_view_store').modal('hide');
		}
	});
	
//	//평가 댓글
//	$.ajax({
//		url:'/MapAverageAnswerAction.mp',
//		type:"POST",
//		dataType: 'json',
//		data:{"mapNum":mapNum},
//		success:function(params){
//			if(params['error']){
//				alert(params['msg']);
//				$('#modal_view_store').modal('hide');
//				return;
//			}
//			
//			//answer = params.array;
//			for(var i=0;i<params.array.length;i++){
//				answer = params.array[i];
//		 	 	
//		 	 	var value = "<tr><th>"+answer.mapNickcheck+"</th><td>"+answer.Date+"</td>"
//				+"<td>"+toStar(answer.mapAverage)+"</td></tr>"
//				+"<tr><td colspan='3'>"+answer.mapContent+"</td></tr>";
//		 	 	
//		 		$("#averageanswer").append(value);
//		 		
//			
//			}
//		},
//		fail:function(params){
//			$('#modal_view_store').modal('hide');
//		}
//	});
//	
//	$("#averageanswersave").click(function(){
//		$("#averageanswer").empty();
//		view_store(mapNum);
//		
//	});
	
}
//	 $("#averageanswer").empty();
//		$.ajax({
//			url:'/MapAverageAnswerAction.mp',
//			type:"POST",
//			dataType: 'json',
//			data:{"mapNum":mapNum},
//			success:function(params){
//				if(params['error']){
//					alert(params['msg']);
//					$('#modal_view_store').modal('hide');
//					return;
//				}
//				
//				//answer = params.array;
//				
//				for(var i=0;i<params.array.length;i++){
//					answeraction = params.array[i];
//			 	 	
//			 	 	var value = "<tr><th>"+answeraction.mapNickcheck+"</th><td>"+answeraction.Date+"</td>"
//					+"<td>"+toStar(answeraction.mapAverage)+"</td></tr>"
//					+"<tr><td colspan='3'>"+answeraction.mapContent+"</td></tr>";
//			 	 	
//			 		$("#averageanswer").append(value);
//				
//					}
//			},
//			fail:function(params){
//				$('#modal_view_store').modal('hide');
//			}
//		});
		 


	 // 필수값 Check
	 function validation(){
	 	var contents = $.trim(oEditors[0].getContents());
	 	if(contents === '<p>&nbsp;</p>' || contents === ''){ // 기본적으로 아무것도 입력하지 않아도 <p>&nbsp;</p> 값이 입력되어 있음. 
	 		alert("내용을 입력하세요.");
	 		oEditors.getById['mapRstMenu'].exec('FOCUS');
	 		return false;
	 	}

	 	return true;
	 }	 