<template>
  <div class="message">
  
  <Layout>
        <Layout>
            <Header :style="{padding: '0px', height: '40px', background: '#ebebeb'}">
            	
            </Header>
            <Content :style="{padding: '0px', height: '300px', background: '#ebebeb'}">
            	<Scroll :height="250" id="msgUI" :on-reach-top="handleReachTop">
            			<template v-for="msgItem in ChatHistory" >
            				<!-- Right -->
							<div v-if="msgItem.fromUserId == sendUserId" class="chat-receiver">
							  <div><img src="https://i.loli.net/2017/08/21/599a521472424.jpg"></div>
							  <div>{{msgItem.username}}</div>
							  <div>
							    <div class="chat-right_triangle"></div>
							    <span> {{msgItem.content}}</span>
							  </div>
							</div>
            				<!-- Left -->
	            			<div v-else class="chat-sender">
							  <div><img src="https://i.loli.net/2017/08/21/599a521472424.jpg"></div>
							  <div>{{msgItem.username}}</div>
							  <div>
							    <div class="chat-left_triangle"></div>
							    <span> {{msgItem.content}}</span>
							  </div>
							</div>
            			</template>
            			
            			<template v-for="msgItem in ChatHistoryCurrent" >
            				<!-- Right -->
							<div v-if="msgItem.fromUserId == sendUserId" class="chat-receiver">
							  <div><img src="https://i.loli.net/2017/08/21/599a521472424.jpg"></div>
							  <div>{{msgItem.username}}</div>
							  <div>
							    <div class="chat-right_triangle"></div>
							    <span> {{msgItem.content}}</span>
							  </div>
							</div>
            				<!-- Left -->
	            			<div v-else class="chat-sender">
							  <div><img src="https://i.loli.net/2017/08/21/599a521472424.jpg"></div>
							  <div>{{msgItem.username}}</div>
							  <div>
							    <div class="chat-left_triangle"></div>
							    <span> {{msgItem.content}}</span>
							  </div>
							</div>
            			</template>
            			
            			<template v-for="msgItem in messageConents" >
            				<!-- Right -->
							<div v-if="msgItem.from == sendUserId" class="chat-receiver">
							  <div><img src="https://i.loli.net/2017/08/21/599a521472424.jpg"></div>
							  <div>{{msgItem.username}}</div>
							  <div>
							    <div class="chat-right_triangle"></div>
							    <span> {{msgItem.content}}</span>
							  </div>
							</div>
            				<!-- Left -->
	            			<div v-else class="chat-sender">
							  <div><img src="https://i.loli.net/2017/08/21/599a521472424.jpg"></div>
							  <div>{{msgItem.username}}</div>
							  <div>
							    <div class="chat-left_triangle"></div>
							    <span> {{msgItem.content}}</span>
							  </div>
							</div>
            			</template>
            			
            	</Scroll>
            </Content>
            <Footer :style="{padding: '0px',  background: '#fff'}">
            	<Input v-model="msgContent" type="textarea" :rows="4" placeholder="Enter something..." />
            	<Button shape="circle" icon="ios-search" @click="sendMsg">发送</Button>
            </Footer>
        </Layout>
        <Sider width='170' ref="side1" hide-trigger collapsible :style="{background: '#fff'}" v-model="isCollapsed">
        	<Button>邀请好友</Button>
        	<Table :columns="groupUserColumns" :data="groupUserInfo"></Table>
        	<Icon @click.native="collapsedSider" :class="rotateIcon" style="float:right;padding-right:8px" type="md-menu" size="24"></Icon>
        </Sider>
        
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
      isCollapsed: false,
      ChatHistoryCurrent:{},//聊天历史最后一页
      ChatHistory:{},//聊天历史
      ChatHistoryMore:{},//聊天记录暂存更多
      messageConents:[],
      pageNum : 0,
      groupUserInfo : [], //群成员
      msgContent: '',
      message : [],
      groupUserColumns:[
      		{
                title: '群成员',
                key: 'userName',
                render: (h, params) => {
                	var self = this;
                	var row = params.row;
                	var status = 'success';
                	var statusStr = '在线';
                	if(row.isOnline != 1){
                		status = 'default';
                		statusStr = '离线';
                	}
                	return h('div',[
                		h('a',{
                			on : {
                				click : ()=>{
                					self.showUserInfo(row.userId)
                        		}
                			}
                		},[h('Avatar',{props:{src:'https://i.loli.net/2017/08/21/599a521472424.jpg'}}),h('span',params.row.userName+' ')]),
                		h('Badge',{
                			props : {
                				status : status,
                				text : statusStr,
                				offset : [50,50]
                			}
                		})
                	])
                }
            }
      ]
    }
  },
  props : ['messageConent'],
  methods : {
  	 collapsedSider () {
        this.$refs.side1.toggleCollapse();
    },
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
 	//初始化群信息
 	initGroupInfo () {
 		var self = this;
 		axios.get('/group/')
 	},
  	//初始化群成员信息；
  	initGroupUserInfo () {
  	    var self = this;
  		axios.get('/group/getGroupUser?groupId='+this.toGroupId)
  		.then(function(response){
  			self.groupUserInfo = response.data.data;
  		})
  	},
  	
  	//点击发送消息按钮方法
  	sendMsg () {
  		this.message.type = 2;
  		this.message.from = this.$route.query.fromUserId;
  		this.message.to = this.toGroupId;
  		this.message.content = this.msgContent;
  		this.messageConents.push({'content':this.msgContent,'from':this.message.from,'time':this.getDateFull(),'to':[this.message.to],'type':1});
  		this.$emit('sendContent',this.message);
  	},
  	//初始化聊天记录
  	initChatHistory () {
  		var self = this;
  		axios.post('/group/getGroupMsgHistory',qs.stringify({
  			groupId : self.toGroupId,
  			pageSize : 10,
  			pageNum : self.pageNum
  		}))
  		.then(function(response){
  			var charData = response.data;
  			if(charData.code == 1){
  				if(self.pageNum == 0){
	  				self.ChatHistoryCurrent = charData.data;
	  			}else{
		  			self.ChatHistory = self.ChatHistoryMore;
		  			self.ChatHistoryMore = charData.data;
		  			Array.prototype.push.apply(self.ChatHistoryMore, self.ChatHistory);
		  			self.ChatHistory = self.ChatHistoryMore;
	  			}
  			}else if (charData.code == 2 && self.pageNum != 0) {
  				self.$Message.info({
                    render: h => {
                        return h('span', charData.msg)
                    }
                });
  			}
  			
  		})
  	},
  	//将接受的信息放到记录中显示
  	addMsgContext (val){
  		this.messageConents.push(val);
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
  		rotateIcon () {
            return [
                'menu-icon',
                this.isCollapsed ? 'rotate-icon' : ''
            ];
        },
	    toGroupId () {
	        return this.$route.query.toGroupId;
	    },
	    sendUserId () {
	        return this.$route.query.fromUserId;
	    }
  },
  
  watch : {
  		toGroupId (){
  		   this.pageNum = 0;//切换聊天对象时清空记录页数
  		   this.ChatHistory = {};//切换聊天对象时清空记录
  		   this.ChatHistoryMore = {};//切换聊天对象时清空记录
  		   this.ChatHistoryCurrent = {};//切换聊天对象时清空记录
  		   this.messageConents = [];
  		   this.initGroupInfo();
	       this.initGroupUserInfo();
	       this.initChatHistory();
  		},
  		
  		//监听消息类型(type)
  		messageConent (val){
	  		this.addMsgContext(val);
  		}
  },
  
  mounted : function(){
  		this.initGroupInfo();
	    this.initGroupUserInfo();
	    this.initChatHistory();
	    //this.removeMsgRemind();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
body{
      background-color: #ebebeb;
      font-family: -apple-system;
      font-family: "-apple-system", "Helvetica Neue", "Roboto", "Segoe UI", sans-serif;
    }
.chat-sender{
      clear:both;
      font-size: 80%;
    }
    .chat-sender div:nth-of-type(1){
      float: left;
    }
    .chat-sender div:nth-of-type(2){
      margin: 0 50px 2px 50px;
      padding: 0px;
      color: #848484;
      font-size: 70%;
      text-align: left;
    }
    .chat-sender div:nth-of-type(3){
      background-color: white;
      /*float: left;*/
      margin: 0 50px 10px 50px;
      padding: 10px 10px 10px 10px;
      border-radius:7px;
      text-indent: -12px;
    }

    .chat-receiver{
      clear:both;
      font-size: 80%;
    }
    .chat-receiver div:nth-of-type(1){
      float: right;
    }
    .chat-receiver div:nth-of-type(2){
      margin: 0px 50px 2px 50px;
      padding: 0px;
      color: #848484;
      font-size: 70%;
      text-align: right;
    }
    .chat-receiver div:nth-of-type(3){
      /*float:right;*/
      background-color: #b2e281;
      margin: 0px 50px 10px 50px;
      padding: 10px 10px 10px 10px;
      border-radius:7px;
    }

    .chat-receiver div:first-child img,
    .chat-sender div:first-child img{
      width: 40px;
      height: 40px;
      /*border-radius: 10%;*/
    }

    .chat-left_triangle{
      height: 0px;
      width: 0px;
      border-width: 6px;
      border-style: solid;
      border-color: transparent white transparent transparent;
      position: relative;
      left: -22px;
      top: 3px;
    }
    .chat-right_triangle{
      height: 0px;
      width: 0px;
      border-width: 6px;
      border-style: solid;
      border-color: transparent transparent transparent #b2e281;
      position: relative;
      right:-22px;
      top:3px;
    }

    .chat-notice{
      clear: both;
      font-size: 70%;
      color: white;
      text-align: center;
      margin-top: 15px;
      margin-bottom: 15px;
    }
    .chat-notice span{
      background-color: #cecece;
      line-height: 25px;
      border-radius: 5px;
      padding: 5px 10px;
    }
</style>
