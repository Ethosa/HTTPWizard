import * as vueRouter from 'vue-router'
import Main from '../components/Main.vue'

export const router = vueRouter.createRouter({
    history: vueRouter.createWebHashHistory(),
    routes: [
        { path: '/', component: Main },
    ],
})