import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "@/store";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/gloable.css'
import request from "@/util/request";
import VueAMap from 'vue-amap'

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

Vue.config.productionTip = false
Vue.use(ElementUI, {size: "small"})
    .use(VueAMap).use(mavonEditor);
Vue.prototype.request = request
VueAMap.initAMapApiLoader = function (param) {

};
VueAMap.initAMapApiLoader({
    key: '02daeb21cfbf2e628aaf968e37fdd8bf',
    plugin: ['AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PlaceSearch', 'AMap.Geolocation', 'AMap.Geocoder'],
    v: '1.4.4',
    uiVersion: '1.0'
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
