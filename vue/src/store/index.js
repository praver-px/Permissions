import Vue from "vue";
import Vuex from "vuex"
import router, {resetRouter} from "@/router";

Vue.use(Vuex)

const store=new Vuex.Store({
    state:{
        currenPathName:''
    },
    mutations:{
        setPath(state){
            state.currenPathName=localStorage.getItem("currenPathName")
        },
        logout(){
            localStorage.removeItem("user")
            localStorage.removeItem("menus")
            router.push("/login")

            resetRouter()
        }
    }
})

export default store