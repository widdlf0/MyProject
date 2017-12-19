//In the following example, markers appear when the user clicks on the map.
	//The markers are stored in an array.
	//The user can then click an option to hide, show or delete the markers.
	var map;
	var markers = [];
	var geocoder;

	var marker_list = {};
	var mapNum_list = [];
	var map_list = {};
	var marker;
	var marker_to_add = [];

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
			infowindow
					.setContent(document.getElementById('infowindow-content'));
			infowindow.open(map, marker);
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
		setMapOnAll(null);
	}

	function showMarkers() {
		setMapOnAll(map);
	}

	
	function deleteMarkers() {
		clearMarkers();
		markers = [];
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
			
			$("#saveData").click(function(){
//				console.log( "click!" );
				//id 가 saveDataForm 이란놈 폼데이터 다 긁어오는거
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
			
			
			$("#map").mouseup(function(){
//				console.log( "mouseup!" );
//				$("#test007").reset();		
				$("#test007").empty();
//				setMapOnAll(null);
				deleteMarkers();
				 $.ajax({
					 		type : "POST",
					 		url : "/MapList.mp",
					 		cache : false,
					 		success : onSuccess1,
					 		error : onError
						}); 
				 
				});
			
			$("#mainMenu").css("background-color", "#F2F2F2");
		});
		//리스트 뽑아 오기


		var test;
		var clickname = null;
		function onSuccess1(json, status){//돌려봐 ㄱㄱrrrr
//			console.log('length : '+json.array.length);
//			console.log(json.array);
			test = json.array;
//			test = json.mapNum;
			var obj = [];
			
				
			for(var i=0;i<json.array.length;i++){
				obj = json.array[i];
				
				//test = obj;
//				console.log(test); //노가다해라  어오브잭트 안넘어간갑다
		 	 	var value = "<tr onclick=javascript:view_store('"+obj.mapNum +"')><td>"+obj.mapCategori
		 	 	+"</td><td>"+obj.mapRstName+"</td><td>"+obj.mapMenu
		 	 	+"</td><td>"+obj.mapTel+"</td><td>"+obj.mapMove
		 	 	+"</td><td></td></tr>";

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
				
				
					google.maps.event.addListener(marker,'click',function() {
//						view_store(obj.mapNum);
						console.log(obj.mapNum);
//						  map.setZoom(9);
//						  map.setCenter(marker.getPosition());
						
					});
					
					marker_to_add.push(marker);
		 		
			}
			
//			var value = "<tr><td>"+obj.mapCategori
//					value +="</td><td>"+obj.mapRstName+"</td><td>"+obj.mapMenu
//					value +="</td><td>"+obj.mapTel+"</td><td>"+obj.mapMove
//					value +="</td><td></td></tr>";
//			$("#test007").append(value);
			
				
				$("#test007").children('tr').hover(
						function (){
//							console.log($(this))
							$(this).css("background-color", "#F7BE81");
							$(this).css("cursor", "pointer");
							
						},		
						function (){
							$(this).css("background-color", "");
				});
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
		 

		function onSuccess2(json, status){
			alert('오마이갓!');
		}
		function onError(data, status){alert("error");}

	 
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
			var link_google_map = " <a target='_blank' href='https://www.google.com/maps/search/?api=1&query="+params.lat+","+params.lng+"&z=18'>지도 보기</a>";
			$('#modal_view_store #view_store_label').html(params.mapRstName);
			$('#modal_view_store .name').html(params.mapRstName);
			$('#modal_view_store .gpu').html(params.mapRstMenu);
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
		},
		fail:function(params){
			$('#modal_view_store').modal('hide');
		}
	});
}

