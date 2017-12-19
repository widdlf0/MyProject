
//common app object
var app={

	selector : {
		pattern : /((?:\.|:|#)?[*a-zA-Z0-9_-]+)/g
		, find : null
	}

	, browser : null

	, about : null

	, initialize : function(){
		app.about.initialize();
	}

}

//browser detection and support
app.browser={

	version : 0

	, disabled : ''

	, support : {}

	, detection : function(){

		var nua=navigator.userAgent.toLowerCase();
		this.ie=nua.match(/msie ([0-9])+/);//ie
		if(this.ie){
			this.version=parseInt(this.ie[1]);
		}
		this.firefox=(/firefox/).test(nua);//firefox
		this.webkit=(/applewebkit/).test(nua);//safari,chrome
		this.opera=(/opera/).test(nua);//opera
		this.ipod=(/(ipad)/).test(nua);//ipod
		this.iphone=(/(iphone)/).test(nua);//iphone
		this.ipad=(/(ipad)/).test(nua);//ipad
		this.ios=this.ipod || this.iphone || this.ipad;//ios
		this.android=(/android/).test(nua);//android
		this.mobile=this.ios || this.android;//mobile

		//browser support(temporary)
		this.support.opacity=!this.ie || (this.ie && this.version>8);//css opacity
		this.support.targeted=!this.ie || (this.ie && this.version>8);//css :target selector
		this.support.pseudoelement=!this.ie || (this.ie && this.version>7);//css pseudo element(:before, :after)
		this.support.eventlistener=document.addEventListener!=undefined;//addEventListener or not

	}

	, notsupport : function(){

		document.body.style.background='#000';
		document.body.innerHTML='<div id="notsupport"><p>이 페이지는 사용중인 브라우저를 지원하지 않습니다.<br />This page does not support your version of browser.</p><p class="links"><a href="http://www.google.com/chrome/" target="_blank">Chrome</a>, <a href="http://www.mozilla.or.kr/ko/" target="_blank">Firefox</a>, <a href="http://www.opera.com/browser/" target="_blank">Opera</a>, <a href="http://www.apple.com/safari/" target="_blank">Safari</a>, <a href="http://windows.microsoft.com/en-US/internet-explorer/downloads/ie" target="_blank">Internet Explorer</a></p></div>';

	}

	, initialize : function(){

		if(!app.browser.disabled) return true;

		var match, pattern=/([a-z]+)([0-9]+)/g;
		while(match=(pattern).exec(app.browser.disabled)){
			if(this[match[1]] && match[2]>this.version){
				this.notsupport();
				return false;
			}
		}

		return true;

	}

}
app.browser.detection();

//util
app.util={

	//event
	event : {

		add : function(target, name, handle){
			app.util.event.assign('add', target, name, handle);
		}

		, remove : function(target, name, handle){
			app.util.event.assign('remove', target, name, handle);
		}

		, assign : function(flag, target, name, handle){
			name=app.util.event.getname(name);
			if(app.browser.support.eventlistener){
				flag=(flag=='add')? 'addEventListener' : 'removeEventListener';
				target[flag](name, handle, false);
			}else{
				flag=(flag=='add')? 'attachEvent' : 'detachEvent';
				target[flag]('on'+name, handle);
			}
		}

		, getname : function(name){
			if(name=='mousewheel' && app.browser.firefox){
				return 'DOMMouseScroll';
			}
			if(name=='transitionend'){
				return (app.browser.webkit)? 'webkitTransitionEnd'
					: (app.browser.opera)? 'oTransitionEnd'
					: (app.browser.ie)? 'MSTransitionEnd'
					: 'transitionend';
			}
			return name;
		}

	}

	//classname
	, classname : {

		add : function(target, name){
			target.className=(target.className+' '+name).replace(/^ /, '');
		}

		, remove : function(target, name){
			target.className=(' '+target.className).replace(new RegExp(' '+name, 'g'), '').replace(/^ /, '');
		}

	}

	, style : {

		get : function(target, property){
			var rv;
			if(property=='scrollLeft' || property=='scrollTop'){
				return target[property];
			}
			if(!app.browser.support.opacity){
				property='filter';
			}
			if(target.style[property]){
				rv=target.style[property];
			}else if(target.currentStyle){
				rv=target.currentStyle[property];
			}else{
				rv=document.defaultView.getComputedStyle(target, null)[property];
			}
			if(!app.browser.support.opacity){
				if(rv){
					rv=parseFloat(rv.match(/alpha *\( *opacity *[=:] *([0-9\.]+) *\)/i)[1]);
					return (rv || rv===0)? rv/100 : 1;
				}else{
					return 1;
				}
			}
			if(property=='opacity'){
				return parseFloat(rv);
			}
			if(property=='backgroundPosition'){//not completed
				rv=app.util.style.get(target, 'backgroundPositionX')+' '+app.util.style.get(target, 'backgroundPositionY');
			}
			return rv;
		}

		, set : function(target, property, value){
			if(typeof(property)=='string'){
				target.style[property]=(!isNaN(value))? value+'px' : value;
			}else{
				for(var i in property){
					target.style[i]=(!isNaN(property[i]))? property[i]+'px' : property[i];
				}
			}
		}

	}

	, transition : {

		//delay action for css transition
		set : function(target, option){

			if(!target.dataset){
				target.dataset={};
			}

			clearTimeout(target.dataset.delayactiontimer);

			if(option.prestyle){
				app.util.style.set(target, option.prestyle);
			}

			if(option.onend){
				app.util.event.add(target, 'transitionend', option.onend);
			}

			target.dataset.delayactiontimer=setTimeout(function(){
				if(option.addclass){
					app.util.classname.add(target, option.addclass);
				}
				if(option.removeclass){
					app.util.classname.remove(target, option.removeclass);
				}
				if(option.style){
					app.util.style.set(target, option.style);
				}
			}, option.delay || 0);

		}

	}
	
	, cookie : {

		set : function(name, value, term, path, domain){
			var cookieset=name+'='+value+';';
			if(term){
				var expdate=new Date();
				expdate.setTime(expdate.getTime()+(term*1000)*(60*60));//day
				cookieset+='expires='+expdate.toGMTString()+';';
			}
			if(path){
				cookieset+='path='+path+';';
			}
			if(domain){
				cookieset+='domain='+domain+';';
			}
			document.cookie=cookieset;
		}

		, get : function(name){
			var match=(document.cookie || ' ').match(new RegExp(name+' *= *([^;]+)'))
			return (match)? match[1] : null;
		}

	}

}

//about app
app.about={

	layer : null

	, dimmed : null
	, button : null
	, scrolltarget : null

	, show : function(){
		this.layer.style.left='50%';
		this.layer.style.display='block';
		this.dimmed.style.display='block';
	}

	, hide : function(){
		this.layer.style.display='none';
		this.dimmed.style.display='none';
	}

	, toggle : function(){
		if(app.about.layer.style.display!='block'){
			app.about.show();
		}else{
			app.about.hide();
		}
		return false;
	}

	, initialize : function(){

		this.layer=document.getElementById('about');

		if(!this.layer){
			return;
		}

		this.dimmed=document.createElement('div');
		this.dimmed.id='dimmed';
		this.dimmed.onclick=this.toggle;
		document.body.insertBefore(this.dimmed, this.layer);

		this.button=document.getElementById('aboutbutton');
		this.button.onclick=this.toggle;

		this.scrolltarget=document.getElementById('aboutscroll');
		if(this.scrolltarget){
			fakescroll(this.scrolltarget);
			if(window.FB){
				FB.Event.subscribe('comment.create',
					function(response){
						app.about.scrolltarget.scrollbar.reset();
					}
				);
			}
		}

	}

}

app.browser.initialize();
app.util.event.add(window, 'load', app.initialize);