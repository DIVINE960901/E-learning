import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/home.vue'


const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/login.vue'),
      // children:[
      //   {
      //     path:'/testRouter',
      //     name:'testRouter',
      //     component:()=>import('../components/testRouter.vue')
      //   }
      // ]
    },
    {
      path: '/chatSystem',
      name: 'chatSystem',
      component: () => import('../views/chatSystem.vue')
    },
    {
      path: '/exam/:examId',
      name: 'exam',
      component: () => import('../views/exam.vue')
    },
    {
      path: '/personPage',
      name: 'personPage',
      component: () => import('../views/personPage.vue'),
    },
    {
      path: '/course/:videoId',
      name: 'course',
      component: () => import('../views/course.vue'),
    },
    {
      path: '/testlist',
      name: 'testlist',
      component: () => import('../views/testlist.vue'),
    },
    {
      path:'/allcourse',
      name:'allcourse',
      component:()=>import('../views/allcourse.vue')
    },
    {
      path:'/search/:title',
      name:'search',
      component:()=>import('../views/search.vue')
    },
    {
      path:'/showExamData/:id',
      name:'showExamData',
      component:()=>import('../components/showExamData.vue')
    },
    {
      path:'/admin',
      name:'admin',
      component:()=>import('../views/admin.vue'),
      children:[
        {
          path:'/admin/courseReview',
          name:'courseReview',
          component:()=>import('../views/courseReview.vue'),
        },
        {
          path:'/admin/CourseControl',
          name:'uploadCourse',
          component:()=>import('../components/CourseControl.vue'),
        },
        {
          path:'/admin/commandControl',
          name:'commandControl',
          component:()=>import('../components/commandControl.vue'),
        },
        {
          path:'/admin/carouselControl',
          name:'carouselControl',
          component:()=>import('../components/carouselControl.vue'),
        },
        {
          path:'/admin/Recommended',
          name:'Recommended',
          component:()=>import('../components/Recommended.vue'),
        },
        {
          path:'/admin/userControl',
          name:'userControl',
          component:()=>import('../components/userControl.vue'),
        },
        {
          path:'/admin/examControl',
          name:'examControl',
          component:()=>import('../components/examControl.vue'),
        },
      ]
    },
    
    

  ]
})

export default router
