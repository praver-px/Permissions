import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";
import it from "element-ui/src/locale/lang/it";

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('@/views/Register')
    },
    {
        path: '/404',
        name: '404',
        component: () => import('@/views/404')
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('@/views/AboutView')
    },
    {
        path: '/front',
        name: 'Front',
        component: () => import('@/views/front/Front'),
        children: [
            {
                path: 'home',
                name: 'FrontHome',
                component: () => import('@/views/front/Home')
            },
            {
                path: 'password',
                name: 'Password',
                component: () => import('@/views/front/Password')
            },
            {
                path: 'person',
                name: 'Person',
                component: () => import('@/views/front/Person')
            },
            {
                path: 'video',
                name: 'Video',
                component: () => import('@/views/front/Video')
            },
            {
                path: 'videoDetail',
                name: 'VideoDetail',
                component: () => import('@/views/front/VideoDetail')
            },
            {
                path: 'article',
                name: 'Article',
                component: () => import('@/views/front/Article')
            },
            {
                path: 'articleDetail',
                name: 'ArticleDetail',
                component: () => import('@/views/front/ArticleDetail')
            },

        ]
    },

]


const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
//重置路由的方法
export const resetRouter = () => {
    router.matcher = new VueRouter({
        mode: 'history',
        base: process.env.BASE_URL,
        routes
    })
}

export const setRoutes = () => {
    const storeMenus = localStorage.getItem("menus")
    if (storeMenus) {
        const currentRoteNames = router.getRoutes().map(v => v.name);
        if (!currentRoteNames.includes("Manage")) {
            // 拼装动态路由
            const manageRoute = {
                path: '/',
                name: "Manage",
                component: () => import('@/views/Manage'),
                redirect: '/home',
                children: [
                    {path: 'person', name: '个人信息', component: () => import('@/views/Person')},
                ]
            }
            const menus = JSON.parse(storeMenus);
            menus.forEach(item => {
                if (item.path) {
                    let itemMenu = {
                        path: item.path.replace("/", ""),
                        name: item.name,
                        component: () => import('@/views/' + item.pagePath),
                    }
                    manageRoute.children.push(itemMenu)
                } else if (item.children.length) {
                    item.children.forEach(item => {
                        if (item.path) {
                            let itemMenu = {
                                path: item.path.replace("/", ""),
                                name: item.name,
                                component: () => import('@/views/' + item.pagePath),
                            }
                            manageRoute.children.push(itemMenu)
                        }
                    })
                }

            })
            //动态添加到现有的路由中
            router.addRoute(manageRoute)
        }

    }
}
setRoutes()

router.beforeEach((to, from, next) => {
    localStorage.setItem("currenPathName", to.name)//设置当前路由名称
    store.commit("setPath")//触发store的数据更新


    if (!to.matched.length) {
        const storeMenus = localStorage.getItem("menus")
        if (storeMenus) {
            next("/404")
        } else {
            next("/login")
        }
    }
    next()

})


export default router
