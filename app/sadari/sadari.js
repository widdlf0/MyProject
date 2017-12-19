
//set disabled browser less than given version
app.browser.disabled='ie9';

//sadari object
var sdr={

	mode : 'ready',//ready | ing

	result : [],

	linewidth : 4,

	numlines : 0,
	minlines : 2,
	maxlines : 15,

	zone : null,
	header : null,
	linebox : null,
	suggbox : null,

	size : {
		x : 0,
		y : 0,
		wrapper : {
			w : 0,
			h : 0
		}
	},

	ctrl : {
		input : null,
		updownbox : null,
		up : null,
		down : null,
		reset : null,
		goall : null
	},

	getitemsize : function(lines){
		var w=Math.floor((sdr.size.x-20)/lines);
		w=Math.round(w/2)*2;
		return {
			leftplus : Math.round((sdr.size.x-(w*lines))/2),
			width : w
		}
	}

}

//lines input
sdr.input={

	items : [],

	blurtimer : null,
	usekeydown : false,

	focus : function(tg, flag){
		sdr.suggest.targetinput=tg;
		sdr.suggest.show(flag);
	},

	blur : function(tg, flag){
		if(tg.value && tg.value.match(/\S/)){
			tg.className='added';
			sdr.suggest.savedata(tg.value, flag);
		}else{
			tg.className='';
		}
		sdr.suggest.targetinput=null;
		sdr.suggest.hide();
		sdr.input.check();
	},

	check : function(){
		var check=true;
		for(var i=-1, max=sdr.input.items.length; ++i<max;){
			if(!sdr.input.items[i].value){
				check=false;
				break;
			}
		}
		if(check){
			sdr.buttons.big.show();
		}else{
			sdr.buttons.big.hide();
		}
	},

	setdisabled : function(){
		for(var i=-1, max=sdr.input.items.length; ++i<max;){
			sdr.input.items[i].className='disabled';
		}
	},

	setting : function(tg, flag){

		tg.onfocus=function(){
			clearTimeout(sdr.input.blurtimer);
			sdr.input.focus(this, flag);
		}

		tg.onblur=function(){
			//sdr.input.blurtimer=setTimeout(function(){
				sdr.input.blur(this, flag);
			//}, 100);
		}

		tg.onkeydown=function(e){
			sdr.input.usekeydown=false;
			if(e.keyCode==13 || e.keyCode==38 || e.keyCode==40){
				if(e.keyCode==13){
					if(this.value && this.value.match(/\S/)){
						sdr.suggest.settabindex();
					}
				}else if(e.keyCode==38){
					sdr.suggest.itemfocusmove('up');
				}else if(e.keyCode==40){
					sdr.suggest.itemfocusmove('down');
				}
				sdr.input.usekeydown=true;
				return false;
			}
		}

		tg.onkeyup=function(e){
			if(sdr.input.usekeydown){
				return false;
			}
			if(!this.value.match(/\S/)){
				this.value='';
			}
			sdr.suggest.filtering();
		}

	}

}

//suggestion
sdr.suggest={

	playerdata : [],

	resultdata : [],

	nowflag : null,

	itemlength : 0,
	displayitemlength : 0,

	displayuseddata : false,

	targetinput : null,

	focuseditemno : -1,

	visible : false,

	show : function(flag){
		sdr.suggest.nowflag=flag;
		sdr.suggest.setitems();
		sdr.suggbox.style.left=(sdr.suggest.targetinput.offsetLeft+sdr.suggest.targetinput.parentNode.parentNode.offsetLeft)+'px';
		if(sdr.suggest.nowflag=='player'){
			sdr.suggbox.className='suggest';
			sdr.suggbox.style.bottom='auto';
			sdr.suggbox.style.top=(sdr.suggest.targetinput.parentNode.parentNode.parentNode.offsetTop+35)+'px';
		}else{
			sdr.suggbox.className='suggest lower';
			sdr.suggbox.style.top='auto';
			sdr.suggbox.style.bottom=(sdr.footer.offsetHeight+35)+'px';
		}
		sdr.suggest.filtering();
	},

	hide : function(){
		sdr.suggbox.style.display='none';
		sdr.suggest.visible=false;
		sdr.suggest.focuseditemno=-1;
	},

	setbox : function(){
		if(sdr.suggest.displayitemlength){
			sdr.suggbox.style.display='block';
			sdr.suggest.visible=true;
			sdr.suggbox.style.width=(sdr.suggest.targetinput.offsetWidth-2)+'px';
			sdr.suggbox.style.height=((sdr.suggest.displayitemlength>5)? 100 : sdr.suggest.displayitemlength*20)+'px';
			sdr.suggbox.scrollbar.reset();
		}else{
			sdr.suggbox.style.display='none';
			sdr.suggest.visible=false;
		}
	},

	settext : function(text){
		sdr.suggest.targetinput.value=text;
		sdr.suggest.settabindex();
	},

	settabindex : function(){
		if(sdr.numlines*2>sdr.suggest.targetinput.tabIndex){
			if(!app.browser.mobile){
				sdr.input.items[sdr.suggest.targetinput.tabIndex].focus();
			}
		}else{
			sdr.suggest.targetinput.blur();
		}
	},

	filtering : function(){
		var text=sdr.suggest.targetinput.value;
		var items=sdr.suggbox.list.childNodes;
		sdr.suggest.displayitemlength=0;
		for(var i=-1, max=items.length; ++i<max;){
			if((!text || (new RegExp('^'+text)).test(items[i].text)) && sdr.suggest.checkuseddata(items[i].text)){
				items[i].style.display='block';
				sdr.suggest.displayitemlength++;
			}else{
				items[i].style.display='none';
			}
		}
		sdr.suggest.setbox();
		sdr.suggest.focuseditemno=-1;
		sdr.suggest.itemfocus();
	},

	checkuseddata : function(text){
		if(sdr.suggest.displayuseddata) return true;//useable
		for(var i=-1, max=sdr.numlines; ++i<max;){
			if(text==sdr.lines.v.items[i][sdr.suggest.nowflag].input.value){
				return false;//not useable
			}
		}
		return true;//useable
	},

	itemfocusmove : function(flag){
		if(flag=='up'){
			sdr.suggest.focuseditemno=(0>=sdr.suggest.focuseditemno)? sdr.suggest.displayitemlength-1 : sdr.suggest.focuseditemno-1;
		}else{
			sdr.suggest.focuseditemno=(sdr.suggest.focuseditemno>=sdr.suggest.displayitemlength-1)? 0 : sdr.suggest.focuseditemno+1;
		}
		sdr.suggest.itemfocus();
	},

	itemfocus : function(e){
		var items=sdr.suggbox.list.childNodes;
		for(var i=-1, j=0, max=items.length; ++i<max;){
			if(items[i].style.display!='none'){
				if((!e && j==sdr.suggest.focuseditemno) || (e && items[i]==e.target)){
					items[i].className='on';
					sdr.suggest.focuseditemno=j;
					if(!e){
						sdr.suggest.targetinput.value=items[i].text;
						if(sdr.suggbox.scrollbar.y.show){
							if(j*20>sdr.suggbox.scrollbar.scrollTop()+180){
								sdr.suggbox.scrollbar.scrollTop(j*20-180, true);
							}else if(sdr.suggbox.scrollbar.scrollTop()>j*20){
								sdr.suggbox.scrollbar.scrollTop(j*20, true);
							}
						}
					}
				}else{
					items[i].className='';
				}
				j++;
			}
		}
	},

	itemup : function(e){
		sdr.suggest.settext(this.text);
		return false;
	},

	itemdeletebtnover : function(e){
		if(e.stopPropagation) e.stopPropagation();
		else e.cancelBubble=true;
	},

	itemdeletebtnup : function(e){
		sdr.suggest.removeitem(this.parentNode);
		if(e.stopPropagation) e.stopPropagation();
		else e.cancelBubble=true;
	},

	additem : function(text, no, addone){
		var newitem=document.createElement('li');
		newitem.text=text;
		newitem.innerHTML=text+'<span>ⓧ</span>';
		var deletebtn=newitem.getElementsByTagName('span')[0];
		newitem.onmouseover=sdr.suggest.itemfocus;
		newitem.onmouseup=sdr.suggest.itemup;
		deletebtn.onmouseover=sdr.suggest.itemdeletebtnover;
		deletebtn.onmouseup=sdr.suggest.itemdeletebtnup;
		newitem.onmousedown=deletebtn.onmousedown=function(){
			return false;
		}
		sdr.suggbox.list.appendChild(newitem);
		if(addone) sdr.suggbox.scrollbar.reset();
	},

	removeitem : function(tg){
		tg.parentNode.removeChild(tg);
		var data=sdr.suggest[sdr.suggest.nowflag+'data'];
		for(var i=-1; ++i<sdr.suggest.itemlength;){
			if(tg.text==data[i]){
				data.splice(i, 1);
				break;
			}
		}
		sdr.suggest.itemlength--;
		sdr.suggest.setlocalstorage(sdr.suggest.nowflag);
		sdr.suggest.filtering();
	},

	setitems : function(){
		sdr.suggbox.list.innerHTML='';
		var data=sdr.suggest[sdr.suggest.nowflag+'data'];
		sdr.suggest.itemlength=data.length;
		for(var i=-1; ++i<sdr.suggest.itemlength;){
			sdr.suggest.additem(data[i]);
		}
	},

	savedata : function(txt, flag){
		var check=false;
		var targetdata=sdr.suggest[flag+'data'];
		for(var i=-1, max=targetdata.length; ++i<max;){
			if(targetdata[i]==txt){
				return;
			}
		}
		targetdata.push(txt);
		sdr.suggest.setlocalstorage(flag);
	},

	setlocalstorage : function(flag){
		try{
			localStorage.setItem('sadari-'+flag, sdr.suggest[flag+'data'].join(','));
		}catch(e){}
	}

}

sdr.buttons={

	//go button each lines
	each : {

		over : function(){
			ani.set({target:this.parentNode, to:'width:40px;margin-top:-9px;', time:1, easing:'easeOutElastic', onupdate:sdr.buttons.each.onupdate});
		},

		out : function(){
			ani.set({target:this.parentNode, to:'width:22px;margin-top:0;', time:1, easing:'easeOutElastic', onupdate:sdr.buttons.each.onupdate});
		},

		show : function(delay){
			this.parentNode.className=this.parentNode.className.replace(/ ?hiding ?/g, '');
			this.parentNode.parentNode.linecolor='rgb('+(Math.round(Math.random()*150+50))+', '+(Math.round(Math.random()*150+50))+', '+(Math.round(Math.random()*150+50))+')';
			this.parentNode.style.background=this.parentNode.parentNode.linecolor;
			ani.set({target:this, to:'opacity:1;', time:0.5, easing:100, onend:function(){
				this.onmouseover=sdr.buttons.each.over;
				this.onmouseout=sdr.buttons.each.out;
				this.onclick=sdr.buttons.each.click;
			}});
			ani.set({target:this.parentNode, to:'top:40px;width:22px;border-width:2px;', time:1, delay:delay, easing:'easeOutElastic', onupdate:sdr.buttons.each.onupdate});
		},

		showall : function(){
			for(var i=-1; ++i<sdr.numlines;){
				sdr.lines.v.items[i].startbtn.text.show(0.075*i);
			}
		},

		hide : function(delay){
			this.onmouseover=this.onmouseout=this.onclick=null;
			this.parentNode.className+=' hiding';
			ani.set({target:this, to:'opacity:0;', time:0.5, easing:100});
			ani.set({target:this.parentNode, to:'top:46px;width:0px;height:0;margin-top:0;border-width:0;', time:0.75, delay:delay, easing:'easeInOutExpo', onupdate:sdr.buttons.each.onupdate});
		},

		hideall : function(){
			for(var i=-1; ++i<sdr.numlines;){
				sdr.lines.v.items[i].startbtn.text.hide(0.05*i);
			}
		},

		onupdate : function(){
			if(!this.vs || !this.vs.width) return;
			var now=this.vs.width[this.step];
			this.style.height=now+'px';
			this.style.marginLeft=-(now/2+parseInt(this.style.borderLeftWidth))+'px';
			this.style.borderRadius=(now/2+2)+'px';
		},

		click : function(){
			this.hide();
			var me=this;
			setTimeout(function(){
				for(var i=-1, ck=false; ++i<sdr.numlines;){
					if(sdr.lines.v.items[i].startbtn.text.onclick){
						ck=true;
						break;
					}
				}
				if(!ck){
					sdr.utils.goall.hide();
				}
				me.parentNode.parentNode.go();
			}, 300);
		},

		clickall : function(){
			for(var i=-1; ++i<sdr.numlines;){
				if(sdr.lines.v.items[i].startbtn.text.onclick){
					sdr.lines.v.items[i].startbtn.text.onclick();
				}
			}
		}

	},

	//big button at center of document
	big : {

		obj : null,

		display : false,

		show : function(){
			if(!sdr.buttons.big.display){
				sdr.buttons.big.obj.style.color='#FFF';
				sdr.buttons.big.obj.style.letterSpacing=0;
				sdr.buttons.big.obj.style.textIndent=0;
				sdr.buttons.big.obj.style.marginTop='-350px';
				sdr.buttons.big.obj.style.background='#000';
				sdr.buttons.big.obj.style.display='block';
				sdr.buttons.big.obj.style.opacity=0.75;
				ani.set({target:sdr.buttons.big.obj, to:'margin-top:-50px;opacity:0.75;', time:1, easing:'easeOutBounce', onend:function(){
					this.onmouseover=sdr.buttons.big.over;
					this.onmouseout=sdr.buttons.big.out;
					this.onclick=sdr.buttons.big.click;
				}});
				sdr.buttons.big.display=true;
			}
		},

		hide : function(){
			if(sdr.buttons.big.display){
				sdr.buttons.big.obj.onmouseover=null;
				sdr.buttons.big.obj.onmouseout=null;
				sdr.buttons.big.obj.onclick=null;
				ani.set({target:sdr.buttons.big.obj, to:'margin-top:450px;opacity:0;', time:0.75, easing:'easeInExpo', onend:function(){
					this.style.display='none';
				}});
				sdr.buttons.big.display=false;
			}
		},

		over : function(){
			ani.set({target:this, to:'color:#000;background:#FFF;opacity:1;', time:0.5, easing:100});
		},

		out : function(){
			ani.set({target:this, to:'color:#FFF;background:#000;opacity:0.75;', time:0.5, easing:100});
		},

		click : function(){
			sdr.buttons.big.obj.onmouseover=null;
			sdr.buttons.big.obj.onmouseout=null;
			ani.set({target:this, to:'color:#000;letter-spacing:30px;text-indent:-45px;background:#FFF;opacity:0;', time:0.75, easing:'easeOutExpo', onend:function(){
				sdr.input.setdisabled();
				sdr.lines.v.linedbox.show();
				sdr.buttons.big.obj.style.display='none';
				sdr.buttons.big.display=false;
				sdr.buttons.each.showall();
				sdr.utils.updownbox.hide();
				sdr.utils.goall.show();
				sdr.mode='ing';
			}});
			sdr.lines.h.draw('start');
		}

	}

}

//lines
sdr.lines={

	h : {

		items : [],//[target vline(int), points, line divs]

		max : 0,

		draw : function(flag){

			sdr.lines.h.hideall();

			sdr.lines.h.max=0;
			var i, j, k, max, kmax, ppoint, npoint, hasclosepoint;

			for(i=-1; ++i<sdr.numlines-1;){

				sdr.lines.h.items[i]=[i, [], []];

				for(j=-1; ++j<100;){
					ppoint=(!j)? 0 : sdr.lines.h.items[i][1][j-1];
					npoint=Math.round(Math.random()*((!j)? 15 : 40)+5+ppoint);
					if(npoint>95) break;
					if(i){
						hasclosepoint=false;
						for(k=-1, kmax=sdr.lines.h.items[i-1][1].length; ++k<kmax;){
							if(sdr.lines.h.items[i-1][1][k]>=npoint-1 && npoint+1>=sdr.lines.h.items[i-1][1][k]){
								hasclosepoint=true;
								break;
							}
						}
						if(hasclosepoint){
							j--;
							continue;
						}
					}
					sdr.lines.h.items[i][1][j]=npoint;
					sdr.lines.h.items[i][2][j]=document.createElement('div');
					sdr.lines.h.items[i][2][j].setAttribute('class', 'hline');
					sdr.lines.h.items[i][2][j].mypoint=npoint;
					sdr.lines.h.items[i][2][j].style.top=npoint+'%';
					sdr.lines.h.items[i][2][j].style.opacity=0;
					sdr.lines.v.items[i].wrapper.appendChild(sdr.lines.h.items[i][2][j]);
					sdr.lines.h.items[i][2][j].hide=sdr.lines.h.hide;
					if(flag=='show'){
						sdr.lines.h.items[i][2][j].show=sdr.lines.h.show;
						sdr.lines.h.items[i][2][j].show();
					}else if(flag=='start'){
						sdr.lines.h.items[i][2][j].style.top=((sdr.size.wrapper.h*npoint/100)-40)+'px';
						ani.set({target:sdr.lines.h.items[i][2][j], to:'top:'+(sdr.size.wrapper.h*npoint/100)+'px;opacity:1', time:0.75, delay:i*0.1+j*0.1, easing:'easeOutBack', onend:function(){
							this.style.top=this.mypoint+'%';
						}});
					}
				}

				if(sdr.lines.h.items[i][1].length>sdr.lines.h.max){
					sdr.lines.h.max=sdr.lines.h.items[i][1].length;
				}

			}

		},

		show : function(){
			var shownhide=Math.round(Math.random());
			var delay=Math.random()*5;
			ani.set({target:this, to:'opacity:'+(shownhide*0.3), time:0.75, delay:delay, onend:function(){
				if(shownhide){
					ani.set({target:this, to:'opacity:0', time:0.75, delay:delay, onend:this.show});
				}else{
					this.show();
				}
			}});
		},

		hide : function(){
			ani.set({target:this, to:'opacity:0', time:0.5, onend:function(){
				this.parentNode.removeChild(this);
			}});
		},

		hideall : function(){
			for(var i=-1, max=sdr.lines.h.items.length; ++i<max;){
				for(var j=-1, jmax=sdr.lines.h.items[i][2].length; ++j<jmax;){
					sdr.lines.h.items[i][2][j].hide();
				}
			}
			sdr.lines.h.items=[];
		},

		ready : function(){
		}

	},

	v : {

		items : [],

		timer : null,

		clearfocustimer : null,

		settimer : function(flag){
			sdr.lines.v.timer=setTimeout(function(){
				if(flag!='resize') sdr.lines.h.hideall();
				sdr.lines.v.action();
			}, 300);
		},

		cleartimer : function(){
			clearTimeout(sdr.lines.v.timer);
		},

		create : function(lines){

			var newitem=document.createElement('li');
			newitem.linedbox=document.createElement('li');
			newitem.linedbox.setAttribute('class', 'linedbox');
			newitem.style.left=newitem.linedbox.style.left=sdr.size.x+'px';
			newitem.style.width=newitem.linedbox.style.width=(sdr.size.x/lines)+'px';

			newitem.player=document.createElement('p');
			newitem.player.setAttribute('class', 'input player');
			newitem.player.innerHTML='<input type="text" />';
			newitem.player.input=newitem.player.childNodes[0];
			newitem.appendChild(newitem.player);
			sdr.input.setting(newitem.player.input, 'player');

			newitem.result=document.createElement('p');
			newitem.result.setAttribute('class', 'input result');
			newitem.result.innerHTML='<input type="text" />';
			newitem.result.input=newitem.result.childNodes[0];
			newitem.appendChild(newitem.result);
			sdr.input.setting(newitem.result.input, 'result');

			newitem.resultbox=document.createElement('div');
			newitem.resultbox.setAttribute('class', 'resultbox');
			newitem.resultbox.innerHTML='<div></div><p></p>';
			newitem.resultbox.text=newitem.resultbox.getElementsByTagName('p')[0];
			newitem.appendChild(newitem.resultbox);
			newitem.showresultbox=sdr.lines.v.resultbox.show;
			newitem.hideresultbox=sdr.lines.v.resultbox.hide;

			newitem.wrapper=document.createElement('div');
			newitem.wrapper.setAttribute('class', 'wrapper');
			newitem.wrapper.style.height=sdr.size.wrapper.h+'px';
			newitem.appendChild(newitem.wrapper);
			newitem.linedbox.wrapper=document.createElement('div');
			newitem.linedbox.wrapper.setAttribute('class', 'wrapper');
			newitem.linedbox.wrapper.style.height=sdr.size.wrapper.h+'px';
			newitem.linedbox.appendChild(newitem.linedbox.wrapper);

			newitem.line=document.createElement('div');
			newitem.line.setAttribute('class', 'vline');
			newitem.wrapper.appendChild(newitem.line);

			newitem.startbtn=document.createElement('p');
			newitem.startbtn.setAttribute('class', 'startbtn hiding');
			newitem.startbtn.text=document.createElement('span');
			newitem.startbtn.text.innerHTML='Go';
			newitem.startbtn.text.show=sdr.buttons.each.show;
			newitem.startbtn.text.hide=sdr.buttons.each.hide;
			newitem.startbtn.appendChild(newitem.startbtn.text);
			newitem.linedbox.appendChild(newitem.startbtn);

			newitem.linedbox.lined=[];
			newitem.linedbox.nowpoint=0;
			newitem.linedbox.go=sdr.lines.v.linedbox.go;
			newitem.linedbox.onmouseover=sdr.lines.v.linedbox.over;
			newitem.linedbox.onmouseout=sdr.lines.v.linedbox.out;

			return newitem;

		},

		action : function(){

			var lines=parseInt(sdr.ctrl.input.value);
			if(lines==sdr.numlines) return;

			var i, max, newitem, delay;
			var flag=(sdr.numlines>lines)? 'minus' : 'plus';

			if(flag=='plus'){
				for(i=-1, max=lines-sdr.numlines; ++i<max;){
					newitem=sdr.lines.v.create(lines);
					sdr.lines.v.items.push(newitem);
					sdr.linebox.appendChild(newitem);
					sdr.linebox.appendChild(newitem.linedbox);
				}
				sdr.buttons.big.hide();
			}

			var itemsize=sdr.getitemsize(lines);
			sdr.size.wrapper.w=itemsize.width;
			sdr.input.items=[];
			for(i=-1, max=sdr.lines.v.items.length; ++i<max;){
				delay=0.15*(i-sdr.numlines);
				sdr.lines.v.items[i].linedbox.style.left=(itemsize.width*i+itemsize.leftplus)+'px';
				sdr.lines.v.items[i].linedbox.style.width=itemsize.width+'px';
				ani.set({target:sdr.lines.v.items[i], to:'left:'+(itemsize.width*i+itemsize.leftplus)+'px;width:'+itemsize.width+'px', time:1, delay:delay, easing:'easeOutExpo', onend:(i==max-1)? function(){
					sdr.lines.v.onendshow(flag=='minus');
				} : null});
			}

			sdr.numlines=lines;
			for(i=-1; ++i<sdr.numlines;){
				sdr.lines.v.items[i].no=sdr.lines.v.items[i].linedbox.no=i;
				sdr.lines.v.items[i].player.input.tabIndex=i+1;
				sdr.lines.v.items[i].result.input.tabIndex=i+sdr.numlines+1;
				sdr.input.items[i]=sdr.lines.v.items[i].player.input;
				sdr.input.items[i+sdr.numlines]=sdr.lines.v.items[i].result.input;
			}

		},

		linedbox : {

			go : function(){
				sdr.lines.go(this);
			},

			over : function(){
				clearTimeout(sdr.lines.v.clearfocustimer);
				sdr.lines.v.focus(this);
			},

			out : function(){
				sdr.lines.v.clearfocustimer=setTimeout(function(){
					sdr.lines.v.clearfocus();
				}, 200);
			},

			show : function(){
				for(var i=-1, max=sdr.numlines; ++i<max;){
					sdr.lines.v.items[i].linedbox.style.display='block';
				}
			},

			hide : function(){
				for(var i=-1, max=sdr.numlines; ++i<max;){
					sdr.lines.v.items[i].linedbox.style.display='none';
				}
			}

		},

		focus : function(tg){
			for(var i=-1, max=sdr.numlines; ++i<max;){
				if(i==tg.no){
					sdr.lines.v.items[i].linedbox.style.zIndex=2;
					ani.set({target:sdr.lines.v.items[i].linedbox.wrapper, to:'opacity:1', time:0.5});
				}else{
					sdr.lines.v.items[i].linedbox.style.zIndex=1;
					ani.set({target:sdr.lines.v.items[i].linedbox.wrapper, to:'opacity:0.3', time:0.5});
				}
			}
		},

		clearfocus : function(){
			for(var i=-1, max=sdr.numlines; ++i<max;){
				sdr.lines.v.items[i].linedbox.style.zIndex=1;
				ani.set({target:sdr.lines.v.items[i].linedbox.wrapper, to:'opacity:0.5', time:0.5});
			}
		},

		resultbox : {
			
			show : function(endline){
				var txt=sdr.lines.v.items[endline].result.input.value;
				if(!txt) txt='#result '+(endline+1);
				this.resultbox.text.innerHTML=txt;
				this.resultbox.style.marginTop='-20px';
				this.resultbox.style.display='block';
				ani.set({target:this.resultbox, to:'margin-top:0;opacity:1', time:0.75, easing:'easeOutElastic'});
			},

			hide : function(delay){
				ani.set({target:this.resultbox, to:'margin-top:20px;opacity:0', time:0.5, delay:delay, easing:'easeOutExpo', onend:function(){
					this.style.display='none';
				}});
			}

		},

		onendshow : function(remove){
			if(remove){
				for(var i=sdr.numlines-1, max=sdr.lines.v.items.length; ++i<max;){
					sdr.linebox.removeChild(sdr.lines.v.items[i]);
				}
				sdr.lines.v.items.length=sdr.numlines;
			}
			sdr.lines.h.draw('show');
		}

	},

	go : function(tg){

		if(sdr.mode!='ing'){
			return;
		}

		if(!tg.direction){
			tg.direction='y';
			tg.nowpoint=0;
			tg.nowline=tg.no;
			tg.nextline=0;
		}

		var lpoint=0, rpoint=0, newpoint, to;
		var nlined=document.createElement('div');
		var left=(tg.nowline-tg.no)*100+50;

		nlined.style.left=left+'%';
		nlined.style.top=tg.nowpoint+'%';
		nlined.style.backgroundColor=tg.linecolor;
		tg.wrapper.appendChild(nlined);

		if(tg.direction=='y'){
			for(var i=-1, j, jmax; ++i<sdr.lines.h.max;){
				if(tg.nowline){
					for(j=-1, jmax=sdr.lines.h.items[tg.nowline-1][1].length; ++j<jmax;){
						if(sdr.lines.h.items[tg.nowline-1][1][j]>tg.nowpoint){
							lpoint=sdr.lines.h.items[tg.nowline-1][1][j];
							break;
						}
					}
				}
				if(sdr.numlines-1>tg.nowline){
					for(j=-1, jmax=sdr.lines.h.items[tg.nowline][1].length; ++j<jmax;){
						if(sdr.lines.h.items[tg.nowline][1][j]>tg.nowpoint){
							rpoint=sdr.lines.h.items[tg.nowline][1][j];
							break;
						}
					}
				}
				if(lpoint && rpoint){
					newpoint=Math.min(lpoint, rpoint);
					tg.direction=(lpoint<rpoint)? 'left' : 'right';
				}else if(lpoint || rpoint){
					newpoint=Math.max(lpoint, rpoint);
					tg.direction=(lpoint==newpoint)? 'left' : 'right';
				}
				if(newpoint){
					if(tg.direction=='left'){
						tg.nextline=(tg.nowline)? tg.nowline-1 : tg.nowline;
					}else{
						tg.nextline=(sdr.numlines-1>tg.nowline)? tg.nowline+1 : tg.nowline;
					}
					break;
				}
			}
			if(!newpoint){
				newpoint=100;
			}
			nlined.setAttribute('class', 'vlined');
			nlined.fheight=newpoint-tg.nowpoint;
			ani.set({target:nlined, to:'height:'+(sdr.size.wrapper.h*(newpoint-tg.nowpoint)/100)+'px', time:0.5, easing:'easeOutCirc', onend:function(){
				this.style.height=this.fheight+'%';
				if(tg.nowpoint==100){
					sdr.lines.finish(tg.no, tg.nowline);
				}else{
					sdr.lines.go(tg);
				}
			}});
			tg.nowpoint=newpoint;
		}else{
			nlined.setAttribute('class', 'hlined');
			to='width:'+(sdr.size.wrapper.w)+'px;';
			if(tg.direction=='left'){
				to+='margin-left:-'+(sdr.size.wrapper.w)+'px';
				nlined.fmargin=-100;
			}else{
				nlined.fmargin=0;
			}
			ani.set({target:nlined, to:to, time:0.5, easing:'easeOutCirc', onend:function(){
				this.style.width='100%';
				this.style.marginLeft=this.fmargin+'%';
				sdr.lines.go(tg);
			}});
			tg.nowline=tg.nextline;
			tg.direction='y';
		}
		tg.lined.push(nlined);

	},

	finish : function(startline, endline){
		sdr.result[startline]=endline;
		sdr.lines.v.items[startline].showresultbox(endline);
	}

}

sdr.utils={

	reset : function(){

		sdr.result=[];

		sdr.lines.v.linedbox.hide();
		sdr.utils.updownbox.show();
		sdr.utils.goall.hide();
		sdr.buttons.each.hideall();

		var i, max, j, jmax, dlines;
		for(i=-1, max=sdr.input.items.length; ++i<max;){
			sdr.input.items[i].value='';
			sdr.input.items[i].className='';
		}

		if(sdr.mode=='ing'){
			for(i=-1, max=sdr.lines.v.items.length; ++i<max;){
				sdr.lines.v.items[i].hideresultbox(i*0.05);
				sdr.lines.v.items[i].linedbox.direction='';
				sdr.lines.v.items[i].linedbox.nowpoint=0;
				dlines=sdr.lines.v.items[i].linedbox.wrapper.getElementsByTagName('div');
				for(j=-1, jmax=dlines.length; ++j<jmax;){
					ani.set({target:dlines[j], to:'opacity:0;', time:0.5, delay:j*0.03, easing:0, onend:function(){
						this.parentNode.removeChild(this);
					}});
				}
			}
		}

		sdr.mode='ready';
		sdr.lines.h.draw('show');

	},

	updownbox : {

		show : function(){
			var w=(app.browser.mobile)? 46 : 15;
			ani.set({target:sdr.ctrl.updownbox, to:'width:'+w+'px;margin-left:-2px;opacity:1;', time:0.75, easing:'easeOutExpo'});
		},

		hide : function(){
			ani.set({target:sdr.ctrl.updownbox, to:'width:0;margin-left:-6px;opacity:0;', time:0.75, easing:'easeOutExpo'});
		}

	},

	goall : {

		show : function(){
			ani.set({target:sdr.ctrl.goall, to:'margin-right:0;', time:0.75, easing:'easeOutBack'});
		},

		hide : function(){
			ani.set({target:sdr.ctrl.goall, to:'margin-right:-57px;', time:0.75, easing:'easeOutExpo'});
		},

		click : function(){
			sdr.buttons.each.clickall();
			sdr.utils.goall.hide();
		}

	},
	
	setlines : {

		isfirst : true,
		timer : null,

		action : function(flag){

			sdr.lines.v.cleartimer();

			var lines=parseInt(sdr.ctrl.input.value);
			var setting=false;

			if(flag=='up' && sdr.maxlines>lines){
				lines++;
				setting=true;
			}else if(flag=='down' && lines>sdr.minlines){
				lines--;
				setting=true;
			}

			if(setting){
				sdr.ctrl.input.value=lines;
				sdr.utils.setlines.timer=setTimeout(function(){
					sdr.utils.setlines.action(flag);
				}, (sdr.utils.setlines.isfirst)? 500 : 100);
			}
			
			if(sdr.utils.setlines.isfirst){
				sdr.utils.setlines.isfirst=false;
			}

		},

		end : function(){
			document.documentElement.onmouseup=null;
			clearTimeout(sdr.utils.setlines.timer);
			sdr.utils.setlines.isfirst=true;
			sdr.lines.v.settimer();
		}

	}

}

sdr.resize=function(){

	sdr.lines.v.cleartimer();

	sdr.size.x=document.documentElement.offsetWidth-100;
	sdr.size.y=document.documentElement.offsetHeight-100;
	sdr.zone.style.width=sdr.size.x+'px';
	sdr.zone.style.height=sdr.size.y+'px';
	sdr.zone.style.marginLeft=-(sdr.size.x/2)+'px';
	sdr.zone.style.marginTop=-(sdr.size.y/2)+'px';

	sdr.size.y-=sdr.header.offsetHeight+sdr.footer.offsetHeight;
	sdr.linebox.style.width=sdr.size.x+'px';
	sdr.linebox.style.height=sdr.size.y+'px';

	sdr.size.wrapper.h=sdr.size.y-92;

	if(sdr.numlines){
		var itemsize=sdr.getitemsize(sdr.numlines);
		sdr.size.wrapper.w=itemsize.width;
		for(i=-1, max=sdr.numlines; ++i<max;){
			sdr.lines.v.items[i].style.left=sdr.lines.v.items[i].linedbox.style.left=(i*sdr.size.wrapper.w+itemsize.leftplus)+'px';
			sdr.lines.v.items[i].style.width=sdr.lines.v.items[i].linedbox.style.width=sdr.size.wrapper.w+'px';
			sdr.lines.v.items[i].wrapper.style.height=sdr.lines.v.items[i].linedbox.wrapper.style.height=sdr.size.wrapper.h+'px';
		}
	}

	sdr.lines.v.settimer('resize');

}

window.onload=function(){

	if(!app.browser.initialize()){
		return;
	}

	sdr.zone=document.getElementById('zone');
	if(app.browser.mobile){
		sdr.zone.className='mobile';
	}

	sdr.header=document.getElementsByTagName('h1')[0];
	sdr.footer=document.getElementById('footer');

	sdr.ctrl.checkbox=document.getElementById('checkbox');
	sdr.ctrl.checkbox.onclick=function(){
		if(this.className.indexOf('on')==-1){
			this.className+=' on';
			sdr.suggest.displayuseddata=false;
		}else{
			this.className=this.className.replace(/ ?on ?/, '');
			sdr.suggest.displayuseddata=true;
		}
		return false;
	}

	sdr.ctrl.input=document.getElementById('numinput');
	sdr.ctrl.up=document.getElementById('btnup');
	sdr.ctrl.up.onmousedown=function(e){
		sdr.utils.setlines.action('up');
		if(sdr.suggest.visible) sdr.suggest.hide();
		document.documentElement.onmouseup=sdr.utils.setlines.end;
		return false;
	}
	sdr.ctrl.down=document.getElementById('btndown');
	sdr.ctrl.down.onmousedown=function(e){
		sdr.utils.setlines.action('down');
		if(sdr.suggest.visible) sdr.suggest.hide();
		document.documentElement.onmouseup=sdr.utils.setlines.end;
		return false;
	}
	sdr.ctrl.updownbox=sdr.ctrl.up.parentNode;
	sdr.ctrl.reset=document.getElementById('btnreset');
	sdr.ctrl.reset.onclick=sdr.utils.reset;
	sdr.ctrl.goall=document.getElementById('btngoall');
	sdr.ctrl.goall.onclick=sdr.utils.goall.click;

	sdr.linebox=document.createElement('ul');
	sdr.zone.appendChild(sdr.linebox);

	sdr.suggbox=document.createElement('div');
	sdr.suggbox.setAttribute('class', 'suggest');
	sdr.suggbox.list=document.createElement('ol');
	sdr.suggbox.list.setAttribute('class', 'asdf asdf2');
	sdr.suggbox.appendChild(sdr.suggbox.list);
	sdr.zone.appendChild(sdr.suggbox);
	fakescroll(sdr.suggbox, { movesizefix:20 });

	sdr.buttons.big.obj=document.createElement('p');
	sdr.buttons.big.obj.setAttribute('class', 'bigbutton');
	sdr.buttons.big.obj.innerHTML='Start';
	sdr.zone.appendChild(sdr.buttons.big.obj);


	if(localStorage.getItem('sadari-player')){
		sdr.suggest.playerdata=localStorage.getItem('sadari-player').split(',');
	}

	if(localStorage.getItem('sadari-result')){
		sdr.suggest.resultdata=localStorage.getItem('sadari-result').split(',');
	}

	//application cache - http://xguru.net/621
	if(!!window.applicationCache){
		window.applicationCache.addEventListener('updateready', function(e){
			window.applicationCache.swapCache();
		}, false);
	}

	sdr.resize();
	window.onresize=sdr.resize;

}