"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[402],{8402:function(t,e,a){a.r(e),a.d(e,{default:function(){return p}});var l=function(){var t=this,e=t._self._c;return e("div",[e("div",{staticStyle:{margin:"10px 0"}},[e("el-carousel",{attrs:{height:"450px",interval:1e4}},t._l(t.imgs,(function(t){return e("el-carousel-item",{key:t},[e("img",{staticStyle:{width:"100%"},attrs:{src:t,alt:""}})])})),1)],1),e("div",{staticStyle:{margin:"10px 0"}},[e("el-row",{attrs:{gutter:10}},t._l(t.files,(function(a){return e("el-col",{key:a.id,staticStyle:{"margin-bottom":"10px"},attrs:{span:6}},[e("div",{staticStyle:{border:"1px solid #ccc","padding-bottom":"10px"}},[e("img",{staticStyle:{width:"100%"},attrs:{src:a.url,alt:""}}),e("div",{staticStyle:{color:"#666",padding:"10px"}},[t._v(t._s(a.name))]),e("div",{staticStyle:{padding:"10px"}},[e("el-button",{attrs:{type:"primary"}},[t._v("购买")])],1)])])})),1)],1)])},i=[],s={name:"FrontHome",data(){return{imgs:["http://localhost:9090/api/file/5e36d13cd44f46f5a23fa0a55857c5d4.png","http://localhost:9090/api/file/6d250bad968d4a7b804e408ee57810ba.png","http://localhost:9090/api/file/92e5c844a3a1478c94926427842d15fe.png","http://localhost:9090/api/file/d7bbf44a2b784c8286eef496a87ef7c9.png"],files:[]}},created(){this.request.get("/echarts/file/front/all").then((t=>{console.log(t.data),this.files=t.data.filter((t=>"png"===t.type||"jpg"===t.type||"webp"===t.type))}))},methods:{}},r=s,n=a(1001),c=(0,n.Z)(r,l,i,!1,null,null,null),p=c.exports}}]);
//# sourceMappingURL=402.f9f8ba50.js.map