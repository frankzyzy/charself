import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import Login from '@/components/Login'
import Message from '@/components/Message'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
	  path: '/login',
	  name: 'Login',
	  component: Login
	},
    {
    	path: '/message/:userid',
    	name: 'Message',
    	component: Message
    },
	{
		path: '/message',
		name: 'Message',
		component: Message
	}
  ]
})
