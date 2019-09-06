import Vue from 'vue'
import Router from 'vue-router'
import login1 from './views/login.vue'
import dep from './views/dep.vue'

import Json from './views/temp/Json.vue'
import Module from './views/main/doc/module.vue'
import Doc from './views/main/doc/doc.vue'
import Main from './views/main.vue'
import API from "./views/main/api.vue"
import ApiList from "./views/main/api/list.vue"
import action from "./views/main/api/action.vue"
import views from './views/views.vue'
import head from './views/head.vue'
import client from './views/client.vue'
import sidebar from './views/sidebar.vue'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'login',
            component: login1
        },
        {
            path: '/dep',
            name: 'dep',
            component: dep
        },
        {
            path: '/json',
            name: 'json',
            component: Json
        },
        {
            path: '/client',
            name: 'client',
            component: () => import(/* webpackChunkName: "about" */ './views/client.vue')
        },

        {
            path: '/views',
            component: views,
            children: [{
                path: 'c',
                components: {
                    default: head,
                    sidebar: sidebar,
                    client: client
                }
            }]

        },

        {
            path: '/main',
            name: 'main',
            component: Main,
            children: [

                {
                    path: "doc/:depId",
                    component: Doc,
                    children: [
                        {
                            path: "module",
                            component: Module,

                        }]

                }, {
                    path: "api/:depId",
                    component: API,
                    children: [{
                        path: 'list',
                        component: ApiList
                    }, {  path: 'action',
                        component: action}]
                    /*,
                    children: [
                        {
                            path: "actions",
                            component:Actions,

                        }]*/

                }]
        },
        {
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
        }
    ]
})
