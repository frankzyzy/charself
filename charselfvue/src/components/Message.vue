<template>
  <div class="message">
  <Layout>
        <Layout>
            <Header :style="{padding: '0px', height: '40px', background: '#fff'}">
            	{{userInfo.username}} 正在输入...{{dynamicSegment}}
            </Header>
            <Content :style="{padding: '0px', height: '300px', background: '#fff'}">
            	Content
            	<div id="messageContext">
            	<Scroll :height="250" id="msgUI" :on-reach-top="handleReachTop">
            		<ul>
            			<template v-for="msgItem in ChatHistory" >
	            			<li v-if="msgItem.toUserId == dynamicSegment">
	            				<div class="rightUI">{{msgItem}}</div>
	            			</li>
	            			<li v-else>
	            				<div>{{msgItem}}</div>
	            			</li>
            			</template>
            			<template v-for="msgItem in messageConents" >
	            			<li v-if="msgItem.to[0] == dynamicSegment">
	            				<div class="rightUI">{{msgItem}}</div>
	            			</li>
	            			<li v-else>
	            				<div>{{msgItem}}</div>
	            			</li>
            			</template>
            		</ul>
            	</Scroll>
            	</div>
            </Content>
            <Footer :style="{padding: '0px',  background: '#fff'}">
            	<Input v-model="msgContent" type="textarea" :rows="4" placeholder="Enter something..." />
            	<Button shape="circle" icon="ios-search" @click="sendMsg">发送</Button>
            </Footer>
        </Layout>
        <Sider hide-trigger>Sider</Sider>
    </Layout>
  </div>
</template>

<script>
import '../assets/js/jquery1.9.1/jquery.js'
import '../assets/js/common.js'
import axios from 'axios'
import qs from 'qs'
export default {
  name: 'Login',
  data () {
    return {
      ChatHistory:{},//聊天历史最后一页
      ChatHistoryMore:{},//聊天记录更多
      messageConents:[],
      pageNum : 0,
      userInfo : {},
      msgContent: '',
      message : [],
    }
  },
  props : ['contentDate','messageConent'],
  methods : {
  	handleReachTop () {
	  		console.log("this.pageNum")
  		
        return new Promise(resolve => {
            setTimeout(() => {
	  		this.pageNum++;
	  		this.initChatHistory();
                
                resolve();
            }, 2000);
        });
    },
    showMessageContext (){
    	console.log("messageConent");
    	console.log(this.messageConent);
    },
    websocketonmessage(e){ //数据接收
	    var self = this;
	    console.log(e);
	    var msg = $.parseJSON(e.data);
	    console.log(msg);
	  },
  	//初始化；联系人信息，及状态
  	initUserInfo () {
  	    var self = this;
  	    //self.websockets.onmessage = self.websocketonmessage;
  		axios.get('/user/get/'+this.dynamicSegment)
  		.then(function(response){
  			self.userInfo = response.data;
  		})
  	},
  	//去掉未读提醒
  	removeMsgRemind () {
  		self = this;
  		for(let msg of self.contentDate){
  			if(msg.from == self.sendUserIddynamicSegment && msg.to == self.dynamicSegment){
  				msg.notRead = 0;
  			}
  		}
  		console.log("self.contentDate")
  		console.log(self.contentDate)
  	},
  	
  	//点击发送消息按钮方法
  	sendMsg () {
  		var firstArray = [1,2,3];
		var secondArray = [4,5,6];
		Array.prototype.push.apply(secondArray, firstArray);
		console.log(secondArray);
  		
  		this.message.type = 1;
  		this.message.from = this.$route.query.fromUserId;
  		this.message.to = this.dynamicSegment;
  		this.message.content = this.msgContent;
  		this.messageConents.push({'content':this.msgContent,'from':this.message.from,'time':this.getDateFull(),'to':[this.message.to],'type':1});
  		this.$emit('sendContent',this.message);
  		self.$forceUpdate();
  	},
  	//初始化聊天记录
  	initChatHistory () {
  		var self = this;
  		axios.post('/msg/getMsgHistory',qs.stringify({
  			fromUserId : self.sendUserId,
  			toUserId : self.dynamicSegment,
  			pageSize : 10,
  			pageNum : self.pageNum
  		}))
  		.then(function(response){
  			if(self.pageNum == 0){
  				self.ChatHistory = response.data;
	  			$.each(self.ChatHistory,function(){
	  				this.sendMsgTime = hssduc.util.DateUtils.formatTimeStamp(this.sendMsgTime,"yyyy-MM-dd HH:mm:ss")
	  			})
  			}else{
	  			self.ChatHistory = self.ChatHistoryMore;
	  			self.ChatHistoryMore = response.data;
	  			Array.prototype.push.apply(self.ChatHistoryMore, self.ChatHistory);
	  			self.ChatHistory = self.ChatHistoryMore;
	  			$.each(self.ChatHistory,function(){
	  				this.sendMsgTime = hssduc.util.DateUtils.formatTimeStamp(this.sendMsgTime,"yyyy-MM-dd HH:mm:ss")
	  			})
  			}
  		})
  	},
  	addMsgContext (val){
  	    //var html = '<li>from:'+val.from + val.time + val.content+ '</li>'
  		//$("#addMsgContext").append(html);
  		this.messageConents.push(val);
		this.$nextTick(() => {
	  		var div = document.getElementById('msgUI');
	  		console.log(div);
	  		console.log(div.scrollTop);
	  		console.log(div.scrollHeight);
		    div.scrollTop = div.scrollHeight;
     	})
      
  	},
  	//获得当前时间
  	getDateFull() {
		var date = new Date();
		var currentdate = date.getFullYear() + "-"
				+ this.appendZero(date.getMonth() + 1) + "-"
				+ this.appendZero(date.getDate()) + " "
				+ this.appendZero(date.getHours()) + ":"
				+ this.appendZero(date.getMinutes()) + ":"
				+ this.appendZero(date.getSeconds());
		return currentdate;
	},
	//补0函数
	appendZero(s) {
		return ("00" + s).substr((s + "").length);
	} 
  	
  },
  
  computed: {
	    dynamicSegment () {
	        return this.$route.query.toUserId;
	    },
	    sendUserId () {
	        return this.$route.query.fromUserId;
	    }
  },
  
  watch : {
  		dynamicSegment (){
  		   this.pageNum = 0;//切换聊天对象时清空记录页数
  		   this.ChatHistoryMore = {};//切换聊天对象时清空记录
	       this.initUserInfo();
	       this.initChatHistory();
  		},
  		
  		contentDate : {
  			handler(newValue, oldValue) {  
		　　　　　　console.log(newValue)  
	  			this.$forceUpdate();
		　　　　}, 
  		},
  		//监听消息
  		messageConent (val){
  			console.log(val)
  			//this.showMessageContext();
  			this.addMsgContext(val);
  		}
  },
  
  mounted : function(){
	    this.initUserInfo();
	    this.initChatHistory();
	    this.removeMsgRemind();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.message{
    width : 100%;
    height : 100%;
}
.rightUI{
	float:right
}
#messageContext ul li{
	height : 50px;
}
</style>
