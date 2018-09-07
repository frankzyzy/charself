<template>
  <div class="message">
  
  <Layout>
        <Layout>
            <Header :style="{padding: '0px', height: '40px', background: '#fff'}">
            
            </Header>
            <Content :style="{padding: '0px', height: '300px', background: '#fff'}">
            	Content
            	<div id="messageContext">
            	<Scroll :height="250" id="msgUI" :on-reach-top="handleReachTop">
            		<ul>
            			<template v-for="msgItem in ChatHistory" >
	            			<li v-if="msgItem.toUserId == receiveUserId">
	            				<div class="rightUI">{{msgItem}}</div>
	            			</li>
	            			<li v-else>
	            				<div>{{msgItem}}</div>
	            			</li>
            			</template>
            			<template v-for="msgItem in messageConents" >
	            			<li v-if="msgItem.to[0] == receiveUserId">
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
        <Sider width='250' ref="side1" hide-trigger collapsible :style="{background: '#fff'}" v-model="isCollapsed">
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
      ChatHistory:{},//聊天历史最后一页
      ChatHistoryMore:{},//聊天记录更多
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
                	return h('div',[
                		h('a',{
                			on : {
                				click : ()=>{
                					self.showUserInfo(params.row.userId)
                        		}
                			}
                		},params.row.userName+' '),
                		h('Badge',{
                			props : {
                				status : 'success',
                				text : '在线',
                				offset : [50,50]
                			}
                		})
                	])
                }
            }
      ]
    }
  },
  props : [],
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
 	//显示用户详细信息
 	showUserInfo (userId) {
 		this.$Drawer({
 			render: h => {
 				return h('div','aaaaaaaaa')
 			}
 		})
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
  		self.$forceUpdate();
  	},
  	//初始化聊天记录
  	initChatHistory () {
  		var self = this;
  		axios.post('/msg/getMsgHistory',qs.stringify({
  			fromUserId : self.sendUserId,
  			toUserId : self.receiveUserId,
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
  	//将接受的信息放到记录中显示
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
  		   this.ChatHistoryMore = {};//切换聊天对象时清空记录
	       this.initGroupUserInfo();
	       //this.initChatHistory();
  		}
  },
  
  mounted : function(){
	    this.initGroupUserInfo();
	    //this.initChatHistory();
	    //this.removeMsgRemind();
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
.demo-drawer-profile{
    font-size: 14px;
}
.demo-drawer-profile .ivu-col{
    margin-bottom: 12px;
}
</style>
