import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

export const router =  new VueRouter({
    mode: 'history',
    routes: [

        {
            path: "/",
            name: 'Login',
            component: () => import('@/components/user/login')
        },
        {
            path: "/main",
            name: 'Main',
            component: () => import('@/components/contents/main')
        },
        {
            path: "/interview",
            name: 'interview',
            component: () => import('@/components/webcam/interview')
        },
        {
            path: "/join",
            name: "Join",
            component: () => import("@/components/user/join"),
        },
        
        {
            path: "/test",
            name: 'Test',
            component: () => import('@/components/contents/test')
        },
        {
            path: "/board",
            name: 'Board',
            component: () => import('@/components/contents/board')
        },
        {
            path: "/uploadVideo",
            name: 'uploadVideo',
            component: () => import('@/components/contents/uploadVideo')
        },
        {
            path: "/resultpage",
            name: 'resultPage',
            component: () => import('@/components/contents/resultPage')
        },

    ],
})