"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[815],{2815:function(t,e,i){i.r(e),i.d(e,{default:function(){return d}});i(7658);var r=function(){var t=this,e=t._self._c;return e("div",[e("div",{staticStyle:{display:"flex",height:"60px","line-height":"60px","border-bottom":"1px solid #eee"}},[t._m(0),e("div",{staticStyle:{flex:"1"}},[e("el-menu",{staticClass:"el-menu-demo",attrs:{"default-active":t.$route.path,mode:"horizontal",router:""}},[e("el-menu-item",{attrs:{index:"/front/home"}},[t._v("首页")]),e("el-menu-item",{attrs:{index:"/front/video"}},[t._v("视频播放")]),e("el-menu-item",{attrs:{index:"/front/article"}},[t._v("文章列表")]),e("el-submenu",{attrs:{index:"2"}},[e("template",{slot:"title"},[t._v("我的工作台")]),e("el-menu-item",{attrs:{index:"/front/item1"}},[t._v("选项1")]),e("el-menu-item",{attrs:{index:"2-2"}},[t._v("选项2")]),e("el-menu-item",{attrs:{index:"2-3"}},[t._v("选项3")]),e("el-submenu",{attrs:{index:"2-4"}},[e("template",{slot:"title"},[t._v("选项4")]),e("el-menu-item",{attrs:{index:"2-4-1"}},[t._v("选项1")]),e("el-menu-item",{attrs:{index:"2-4-2"}},[t._v("选项2")]),e("el-menu-item",{attrs:{index:"2-4-3"}},[t._v("选项3")])],2)],2),e("el-menu-item",{attrs:{index:"3",disabled:""}},[t._v("消息中心")]),e("el-menu-item",{attrs:{index:"4"}},[e("a",{attrs:{href:"https://www.ele.me",target:"_blank"}},[t._v("订单管理")])])],1)],1),e("div",{staticStyle:{width:"200px"}},[t.user.username?e("div",[e("el-dropdown",{staticStyle:{width:"150px",cursor:"pointer","text-align":"right"}},[e("div",{staticStyle:{display:"inline-block"}},[e("img",{staticStyle:{width:"30px","border-radius":"50%",position:"relative",top:"10px",right:"5px"},attrs:{src:t.user.avatarUrl,alt:""}}),e("span",[t._v(t._s(t.user.nickname))]),e("i",{staticClass:"el-icon-arrow-down",staticStyle:{"margin-left":"5px"}})]),e("el-dropdown-menu",{staticStyle:{width:"100px","text-align":"center"},attrs:{slot:"dropdown"},slot:"dropdown"},[e("el-dropdown-item",{staticStyle:{"font-size":"14px",padding:"5px 0"}},[e("router-link",{attrs:{to:"/front/password"}},[t._v("修改密码")])],1),e("el-dropdown-item",{staticStyle:{"font-size":"14px",padding:"5px 0"}},[e("router-link",{attrs:{to:"/front/person"}},[t._v("个人信息")])],1),e("el-dropdown-item",{staticStyle:{"font-size":"14px",padding:"5px 0"}},[e("span",{staticStyle:{"text-decoration":"none"},on:{click:t.logout}},[t._v("退出")])])],1)],1)],1):e("div",{staticStyle:{"text-align":"right","padding-right":"30px"}},[e("el-button",{on:{click:function(e){return t.$router.push("/login")}}},[t._v("登录")]),e("el-button",{on:{click:function(e){return t.$router.push("/register")}}},[t._v("注册")])],1)])]),e("div",{staticStyle:{width:"1000px",margin:"0 auto"}},[e("router-view",{on:{refreshUser:t.getUser}})],1)])},s=[function(){var t=this,e=t._self._c;return e("div",{staticStyle:{width:"300px",display:"flex","padding-left":"30px"}},[e("div",{staticStyle:{width:"60px"}},[e("img",{staticStyle:{width:"50px",position:"relative",top:"5px",right:"0"},attrs:{src:"http://localhost:9090/api/file/6d250bad968d4a7b804e408ee57810ba.png",alt:""}})]),e("div",{staticStyle:{flex:"1"}},[t._v("欢迎来到xx系统")])])}],n={name:"Front",data(){return{user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}}},created(){},methods:{logout(){this.$store.commit("logout"),this.$message.success("退出成功")},getUser(){let t=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).username:"";t&&this.request.get("/user/username/"+t).then((t=>{this.user=t.data}))}}},a=n,l=i(1001),o=(0,l.Z)(a,r,s,!1,null,null,null),d=o.exports}}]);
//# sourceMappingURL=815.698ecf2f.js.map