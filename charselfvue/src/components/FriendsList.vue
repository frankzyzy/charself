<template>
  <div>
  <Sider ref="side1" hide-trigger collapsible :style="{background: '#fff'}">
                    <Menu active-name="1-2" theme="light" width="auto" :open-names="['1']">
                        <Submenu name="1">
                            <template slot="title">
                                <Icon type="ios-navigate"></Icon>
                                 	我的好友
                            </template>
                            <template v-for="(item,index) in friendsList">
	                            	<MenuItem :name="'1-'+(index+1)" >
	                            		<span @click="initMsgUI(item.userId)">{{item.username}}</span>
	                            	</MenuItem>
                            </template>
                        </Submenu>
                        <Submenu name="2">
                            <template slot="title">
                                <Icon type="ios-keypad"></Icon>
                                Item 2
                            </template>
                            <MenuItem name="2-1">Option 1</MenuItem>
                            <MenuItem name="2-2">Option 2</MenuItem>
                        </Submenu>
                        <Submenu name="3">
                            <template slot="title">
                                <Icon type="ios-analytics"></Icon>
                                Item 3
                            </template>
                            <MenuItem name="3-1">Option 1</MenuItem>
                            <MenuItem name="3-2">Option 2</MenuItem>
                        </Submenu>
                    </Menu>
                </Sider>
  </div>
</template>

<script>
import '../assets/js/jquery1.9.1/jquery.js'
import axios from 'axios'
export default {
  name: 'FriendsList',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  props : ['friendsList']
  ,
  methods : {
  	  //初始化Msg界面
  	  initMsgUI (toUserId){
  	  	 this.$emit('initMsgUI',toUserId);
  	  }	
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	
</style>
