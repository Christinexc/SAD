import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component: () => import(/* webpackChunkName: "about" */ '../views/Manage.vue'),
    redirect:"/login",
    children:[
      {
        path: 'home',
        name: 'Home',
        component: () => import(/* webpackChunkName: "about" */ '../views/home.vue'),
      },
      {
        path: 'application',
        name: 'Application',
        component: () => import(/* webpackChunkName: "about" */ '../views/Application.vue'),
      },
      {
        path: 'menu',
        name: 'Menu',
        component: () => import(/* webpackChunkName: "about" */ '../views/Menu.vue'),
      },
      {
        path: 'role',
        name: 'Role',
        component: () => import(/* webpackChunkName: "about" */ '../views/Role.vue'),
      },
      {
        path: 'user',
        name: 'User',
        component: () => import(/* webpackChunkName: "about" */ '../views/User.vue'),
      },
      {
        path: 'course',
        name: 'Course',
        component: () => import(/* webpackChunkName: "about" */ '../views/Course.vue'),
      },
      {
        path: 'studentcourse',
        name: 'StudentCourse',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/StudentCourse.vue')
      },
      {
        path: 'message',
        name: 'Message',
        component: () => import(/* webpackChunkName: "about" */ '../views/Message.vue')
      },
      {
        path: 'managestudent',
        name: 'ManageStudent',
        component: () => import(/* webpackChunkName: "about" */ '../views/ManageStudent.vue')
      },
      {
        path: 'manageteacher',
        name: 'ManageTeacher',
        component: () => import(/* webpackChunkName: "about" */ '../views/ManageTeacher.vue')
      },
    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },

  {
    path: '/login',
    name: 'Login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
// router.beforeEach((to,from,next)=>{
//   localStorage.setItem("currentPathName",to.name)//为了在header组件中去使用
//   store.commit("setPath")//触发store的数据更新
//   next()//放行路由
// })
export default router
