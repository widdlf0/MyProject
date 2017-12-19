//var is_mobile = (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));

var max_bound = null;
var map;
var marker_inmodal;

var get_pcs_timer = null;

var pcs_idx_list = [];
var pcs_list = {};
var pcs_marker_list = {};

var pcs_idx_list_now = [];
var pcs_list_now;

var clicked_idx = null;

var geocoder;

var mc;

var hover_mark;


if (!String.prototype.includes) {
	String.prototype.includes = function() {
		'use strict';
		return String.prototype.indexOf.apply(this, arguments) !== -1;
	};
}
if (!Array.prototype.includes) {
	Object.defineProperty(Array.prototype, "includes", {
		enumerable: false,
		value: function(obj) {
			var newArr = this.filter(function(el) {
				return el == obj;
			});
			return newArr.length > 0;
		}
	});
}

function initMap() {
	var seoul = {lat: 37.593131286391944, lng: 127.08510735689084};

	map = new google.maps.Map(document.getElementById('map'), {
		center: seoul,
		zoom: 14
	});
	map.addListener('bounds_changed',function(){
		clearTimeout(get_pcs_timer);
		get_pcs_timer = setTimeout(get_pcs,600);
	});

	var bool_disautopan = true;
	/*if($('#main_row>.col-5').css('display') != 'none')
		bool_disautopan = false;*/

	hover_mark = new google.maps.InfoWindow({map: map, disableAutoPan: bool_disautopan});
	hover_mark.close();


	// Try HTML5 geolocation.
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var pos = {
				lat: position.coords.latitude,
				lng: position.coords.longitude
			};

			map.setCenter(pos);
			map.setZoom(14);
		});
	}

}


function get_pcs(){
	var bounds_tmp = map.getBounds();
	var bounds = bounds_tmp.f.b+"/"+bounds_tmp.b.b+"/"+bounds_tmp.f.f+"/"+bounds_tmp.b.f;

	if(max_bound == null)
	{
		max_bound = bounds_tmp;
	}
	else
	{
		if(bounds_tmp.b.b > max_bound.b.b
			&& bounds_tmp.b.f < max_bound.b.f
			&& bounds_tmp.f.b > max_bound.f.b
			&& bounds_tmp.f.f < max_bound.f.f)
		{
			load_list(1);
			return;
		}
		else if(bounds_tmp.b.b < max_bound.b.b
			&& bounds_tmp.b.f > max_bound.b.f
			&& bounds_tmp.f.b < max_bound.f.b
			&& bounds_tmp.f.f > max_bound.f.f)
		{
			max_bound = bounds_tmp;
		}
	}

	$.ajax({
		url: "/MapInsert.mp",
		type: "POST",
		dataType: 'json',
		data:{
			bounds:bounds
		},
		success:function(params){
			console.log("amsadk");
			if(params['error']){
				return;
			}
			pcs_idx_list_now = params['data'];

			if(params['data'] != null)
			{
				
				var idxs_tmp = [];
				params['data'].forEach(function(value,index){
					if(!pcs_idx_list.includes(value)){
						idxs_tmp.push(value);
					}
				});
				var marker_to_add = [];

				if(idxs_tmp.length>0)
				{

					var idxs_str = "";
					idxs_tmp.forEach(function(value){
						idxs_str += value+"/";
					});


					$.ajax({
						url: "/MapInsert.mp",
						type: "POST",
						dataType: 'json',
						data:{
							idxs:idxs_str
						},
						success:function(params){
							data = params['data'];
							if(data != null)
							{
								for (var i = data.length - 1; i >= 0; i--)
								{
									var rank = data[i]['rank'];
									if(rank == '?')
										rank = '';

									var tmp = new google.maps.Marker({
										map: map,
										position: {lat:parseFloat(data[i]['lat']),lng:parseFloat(data[i]['lng'])},
										label: rank
									});
									marker_to_add.push(tmp);

									pcs_idx_list.push(data[i]['idx']);
									pcs_marker_list[data[i]['idx']] = tmp;
									pcs_list[data[i]['idx']] = data[i];

									clicked_idx = data[i]['idx'];
									tmp.addListener('mouseover', (function(idx){
										return function(){$("#main_pcs_list>tr[data-idx="+idx+"]").addClass("marker_over");}
									})(clicked_idx));
									tmp.addListener('mouseout', (function(idx){
										return function(){$("#main_pcs_list>tr[data-idx="+idx+"]").removeClass("marker_over");}
									})(clicked_idx));

									tmp.addListener('click', (function(idx){
										return function(){exit_fullscreen();view_pc(idx);}
									})(clicked_idx));
								}
								if(mc == null)
									mc = new MarkerClusterer(map, marker_to_add, {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'})
								else
									mc.addMarkers(marker_to_add);
							}
							load_list();
						}
					});
				}
				else
					load_list();
			}
		}
	})
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

function load_list(chk){
	if(chk == null)
		chk = 0;

	var el_tmp = "";
	pcs_list_now = [];

	if(chk == 1)
	{
		var bound_now = map.getBounds();

		Object.keys(pcs_list).forEach(function(i){
			if(pcs_list[i]['lat'] > bound_now.f.b
				&& pcs_list[i]['lat'] < bound_now.f.f
				&& pcs_list[i]['lng'] > bound_now.b.b
				&& pcs_list[i]['lng'] < bound_now.b.f)
			{
				pcs_list_now.push(pcs_list[i]);
			}
		})
	}
	else{
		pcs_idx_list_now.forEach(function(idx){
			if(pcs_list[idx] == null)
				return;
			pcs_list_now.push(pcs_list[idx]);
		});
	}

	pcs_list_now.sort(function(a,b){
		return b.s-a.s;
	});

	var i = 0;
	pcs_list_now.forEach(function(one_list_now){
		if(i>=30)
			return;

		el_tmp+="<tr data-idx='"+one_list_now['idx']+"'>";
			el_tmp+="<td>"+one_list_now['name']+"</td>";
			el_tmp+="<td>"+one_list_now['gpu']+"</td>";
			el_tmp+="<td>"+one_list_now['mem']+"</td>";
			el_tmp+="<td>"+one_list_now['seats']+"</td>";
			el_tmp+="<td>"+one_list_now['rank']+"</td>";
			el_tmp+="<td>"+toStar(one_list_now['star'])+"</td>";
		el_tmp+="</tr>";
		i++;
	});
	$('#main_pcs_list').html(el_tmp);
	if(i>=30)
		$('#main_pcs_list').append("<tr class='helper'><td colspan='5'>목록은 최대 30개까지 보여집니다. 지도를 확대하시면 정확한 정보를 볼 수 있습니다.</td><td></td></tr>");
}

/*function load_list(){
	var el_tmp = "";
	var i = 0;
	pcs_idx_list_now.forEach(function(idx){
		if(pcs_list[idx] == null)
			return;
		if(i>=30)
			return false;

		el_tmp+="<tr data-idx='"+pcs_list[idx]['idx']+"'>";
			el_tmp+="<td>"+pcs_list[idx]['name']+"</td>";
			el_tmp+="<td>"+pcs_list[idx]['gpu']+"</td>";
			el_tmp+="<td>"+pcs_list[idx]['mem']+"</td>";
			el_tmp+="<td>"+pcs_list[idx]['seats']+"</td>";
			el_tmp+="<td>"+pcs_list[idx]['rank']+"</td>";
			el_tmp+="<td>"+toStar(pcs_list[idx]['star'])+"</td>";
		el_tmp+="</tr>";
		i++;
	})
	$('#main_pcs_list').html(el_tmp);
	if(i>=30)
		$('#main_pcs_list').append("<tr class='helper'><td colspan='6'>목록은 최대 30개까지 보여집니다. 지도를 확대하시면 정확하게 볼 수 있습니다..</td></tr>");
}*/

function toStar(count){
	var str = '';
	for (var i = 0; i < 5-count; i++) {
		str += '<i class="material-icons">star_border</i>';
	}
	for (var i = 0; i < count; i++) {
		str += '<i class="material-icons">star</i>';
	}
	return str;
}
var this_el = null;

$(function(){
	$('[data-toggle="tooltip"]').tooltip()

	//init_main_map();

	$("body").on('click',"#main_pcs_list>tr:not(.helper)",function(){
		/*map.setZoom(18);
		map.panTo(pcs_marker_list[$(this).data('idx')].getPosition());*/
	});

	$("body").on('mouseenter',"#main_pcs_list>tr:not(.helper)",function(){
		var idx = $(this).data('idx');
		//pcs_marker_list[idx].setAnimation(google.maps.Animation.BOUNCE);

		hover_mark.setPosition({lat: parseFloat(pcs_list[idx].lat), lng: parseFloat(pcs_list[idx].lng)});
		hover_mark.setContent(pcs_list[idx].name);
		hover_mark.setMap(map);
	});
	$("body").on('mouseleave',"#main_pcs_list>tr:not(.helper)",function(){
		//pcs_marker_list[$(this).data('idx')].setAnimation(null);
		hover_mark.close();
	});

	$('#btn_search_location').click(function(){
		this_el = this;

		if($(this_el).hasClass('disabled'))
			return;

		$(this_el).addClass('disabled');

		if(geocoder == null)
			geocoder = new google.maps.Geocoder();

		geocoder.geocode({'address': $('#input_search_location').val()}, function(results, status) {
			if (status === 'OK') {

				$('#input_search_location').val('');

				$(this_el).removeClass('disabled');
				var pos = {
					lat:results[0].geometry.location.lat(),
					lng:results[0].geometry.location.lng()
				};

				map.setZoom(15);
				map.panTo(pos);

				var infoWindow = new google.maps.InfoWindow({map: map});
				infoWindow.setPosition(pos);
				infoWindow.setContent('검색된 위치입니다.');
				setTimeout(function(){
					infoWindow.close();
				}, 1000);
			} else {
				alert("검색에 실패했습니다. :" + status);
				$(this_el).removeClass('disabled');
			}
		});
	});
	$('#input_search_location').keydown(function(e){
		if(e.keyCode==13)
			$('#btn_search_location').click();
	})
	$('#input_add_pc_map').keydown(function(e){
		if(e.keyCode==13)
			$('#btn_add_pc_map').click();
	})

});










var map_add_pc;
var marker_add_pc;

function init_add_pc_map() {
	var seoul = {lat: 37.5650168, lng: 126.8491196};

	map_add_pc = new google.maps.Map(document.getElementById('map_add_pc'), {
		center: seoul,
		zoom: 15
	});
	map_add_pc.addListener('click',function(e){
		if(marker_add_pc)
			marker_add_pc.setMap(null);

		marker_add_pc = new google.maps.Marker({
			map: map_add_pc,
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

			map_add_pc.setCenter(pos);
			map_add_pc.setZoom(14);
		});
	}

}



$(function(){
	$('#btn_add_pc_map').click(function(){

		if(geocoder == null)
			geocoder = new google.maps.Geocoder();

		geocoder.geocode({'address': $('#input_add_pc_map').val()}, function(results, status) {
			if (status === 'OK') {
				$('#input_add_pc_map').val('');

				map_add_pc.setCenter({
					lat:results[0].geometry.location.lat(),
					lng:results[0].geometry.location.lng()
				});
				if(marker_add_pc)
					marker_add_pc.setMap(null);

				marker_add_pc = new google.maps.Marker({
					map: map_add_pc,
					position: map_add_pc.getCenter()
				});
				$('#input_add_map_lat').val(results[0].geometry.location.lat());
				$('#input_add_map_lng').val(results[0].geometry.location.lng());
				
			} else {
				alert("검색에 실패했습니다. :" + status);
				$(this_el).removeClass('disabled');
			}
		});

	});
	
	$('#add_pc').on('show.bs.modal', function(){
		setTimeout(function(){init_add_pc_map();},300)
	});
	$('.modal').on('show.bs.modal', function(){
		if($(this).find('form').get(0)!=null)
			$(this).find('form').get(0).reset();
	});
})




//PC방 상세보기
function view_pc(idx_pc){
	$('#modal_view_pc').modal('show');

	$.ajax({
		url:'/MapInsert.mp',
		type:"POST",
		dataType: 'json',
		data:{idx:idx_pc},
		success:function(params){
			if(params['error']){
				alert(params['msg']);
				$('#modal_view_pc').modal('hide');
				return;
			}
			var data = params['data'];
			var link_google_map = " <a target='_blank' href='https://www.google.com/maps/search/?api=1&query="+pcs_list[idx_pc]['lat']+","+pcs_list[idx_pc]['lng']+"&z=18'>지도 보기</a>";
			$('#modal_view_pc #view_pc_label').html(pcs_list[idx_pc]['name']);
			$('#modal_view_pc .name').html(pcs_list[idx_pc]['name']);
			$('#modal_view_pc .gpu').html(pcs_list[idx_pc]['gpu']);
			$('#modal_view_pc .mem').html(pcs_list[idx_pc]['mem']);
			$('#modal_view_pc .seats').html(pcs_list[idx_pc]['seats']);
			var geocoder = new google.maps.Geocoder();
			geocoder.geocode({
				'latLng' : {
					lat: parseFloat(pcs_list[idx_pc]['lat']),
					lng: parseFloat(pcs_list[idx_pc]['lng'])
				}
			}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					$('#modal_view_pc .location').html(results[0].formatted_address);
					$('#modal_view_pc .location').append(link_google_map);
				}
				else
				{
					$('#modal_view_pc .location').html("주소 없음");
					$('#modal_view_pc .location').append(link_google_map);
				}
			});



			$('#modal_view_pc .options').html(data['options']);
		},
		fail:function(params){
			$('#modal_view_pc').modal('hide');
		}
	});
}


$(function(){
	$(document).on('submit','form.json_form',function (e) {
	    //prevent the form from doing a submit
	    e.preventDefault();
	    return false;
	})
	
	$('.json_form .json_submit').click(function(){
		var form_el = $(this).parents('.json_form');
		var url = form_el.data('action');
		var data = {};

		if(!form_el.get(0).checkValidity())
		{
			alert('필수 항목을 입력해주세요.');
			return;
		}

		form_el.find('input,select,textarea').each(function(){
			if($(this).attr('name') == '')
				return;
			data[$(this).attr('name')] = $(this).val();
		});


		$.ajax({
			url:url,
			type:"POST",
			dataType: 'json',
			data:data,
			success:function(params){
				if(params['error'])
					alert(params['msg']);
				else{
					alert(params['msg']);
					form_el.parents('.modal').modal('hide');
				}
			}  
		});
	});

	
	$('body').on('click', '#main_pcs_list>tr:not(.helper)', function(){
		view_pc($(this).data('idx'));
	});

});




