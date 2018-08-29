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
            		<ul>
            			<li v-for="msgContent in contentDate" v-if="msgContent.to == sendUserId">
            				{{msgContent}}
            			</li>
            		</ul>
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
import axios from 'axios'
export default {
  name: 'Login',
  data () {
    return {
      userInfo : null,
      msgContent: '',
      message : [],
    }
  },
	props : {
		contentDate : {
			type :Array,
			required : true
		}
	},
  methods : {
    showMessageContext (){
    	
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
  			console.log(response.data);
  			self.userInfo = response.data;
  		})
  	},
  	//去掉未读提醒
  	removeMsgRemind () {
  		self = this;
  		console.log(self.contentDate)
  		for(let msg of self.contentDate){
  				console.log("msg");
  				console.log(msg);
  			axios.get('')
  			if(msg.fromUserId == self.sendUserIddynamicSegment && msg.toUserId == self.dynamicSegment){
  				msg.notRead = 0;
  			}
  		}
  		console.log("self.contentDate")
  		console.log(self.contentDate)
  		self.addComment(self.contentDate);
  	},
  	addComment(param) {
    	//添加完评论后发射监听事件，param是要添加的那条评论消息
    	this.$emit('add-comment', param);
    },
  	
  	//点击发送消息按钮方法
  	sendMsg () {
  		this.message.type = 0;
  		this.message.from = this.$route.query.fromUserId;
  		this.message.to = this.dynamicSegment;
  		this.message.content = this.msgContent;
  		this.$emit('sendContent',this.message);
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
	       this.initUserInfo();
  		},
  		
  		contentDate : {
  			handler(newValue, oldValue) {  
		　　　　　　console.log(newValue)  
	  			this.$forceUpdate();
		　　　　}, 
  		}
  },
  
  created  : function(){
	    this.initUserInfo();
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
</style>
