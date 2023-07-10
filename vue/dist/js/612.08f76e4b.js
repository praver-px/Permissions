"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[612],{2612:function(e,t,a){a.r(t),a.d(t,{default:function(){return c}});var s=function(){var e=this,t=e._self._c;return t("div",[t("div",{staticStyle:{margin:"10px 0"}},[t("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入名称","suffix-icon":"el-icon-search"},model:{value:e.params.name,callback:function(t){e.$set(e.params,"name",t)},expression:"params.name"}}),t("el-button",{staticClass:"ml-5",attrs:{type:"primary"},on:{click:e.load}},[e._v("搜索")]),t("el-button",{attrs:{type:"warning"},on:{click:e.reset}},[e._v("重置")])],1),t("div",{staticStyle:{margin:"10px 0"}},["ROLE_STUDENT"!==e.user.role?t("el-button",{attrs:{type:"primary"},on:{click:e.handleAdd}},[e._v("新增 "),t("i",{staticClass:"el-icon-circle-plus-outline"})]):e._e(),t("el-popconfirm",{staticClass:"ml-5",attrs:{"confirm-button-text":"确定","cancel-button-text":"我再想想",icon:"el-icon-info","icon-color":"red",title:"您确定批量删除这些数据吗？"},on:{confirm:e.delBatch}},["ROLE_STUDENT"!==e.user.role?t("el-button",{attrs:{slot:"reference",type:"danger"},slot:"reference"},[e._v("批量删除 "),t("i",{staticClass:"el-icon-remove-outline"})]):e._e()],1)],1),t("el-table",{attrs:{data:e.tableData,border:"",stripe:"","header-cell-class-name":"headerBg"},on:{"selection-change":e.handleSelectionChange}},[t("el-table-column",{attrs:{type:"selection",width:"55"}}),t("el-table-column",{attrs:{prop:"id",label:"ID",width:"80"}}),t("el-table-column",{attrs:{prop:"name",label:"课程名称"}}),t("el-table-column",{attrs:{prop:"score",label:"学分"}}),t("el-table-column",{attrs:{prop:"times",label:"课时"}}),t("el-table-column",{attrs:{prop:"teacher",label:"授课老师"}}),t("el-table-column",{attrs:{label:"启用"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("el-switch",{attrs:{"active-color":"#13ce66","inactive-color":"#ccc"},on:{change:function(t){return e.changeEnable(a.row)}},model:{value:a.row.state,callback:function(t){e.$set(a.row,"state",t)},expression:"scope.row.state"}})]}}])}),t("el-table-column",{attrs:{label:"操作",width:"280",align:"center"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.selectCourse(a.row.id)}}},[e._v("选课")]),"ROLE_STUDENT"!==e.user.role?t("el-button",{attrs:{type:"success"},on:{click:function(t){return e.handleEdit(a.row)}}},[e._v("编辑 "),t("i",{staticClass:"el-icon-edit"})]):e._e(),t("el-popconfirm",{staticClass:"ml-5",attrs:{"confirm-button-text":"确定","cancel-button-text":"我再想想",icon:"el-icon-info","icon-color":"red",title:"您确定删除吗？"},on:{confirm:function(t){return e.del(a.row.id)}}},["ROLE_STUDENT"!==e.user.role?t("el-button",{attrs:{slot:"reference",type:"danger"},slot:"reference"},[e._v("删除 "),t("i",{staticClass:"el-icon-remove-outline"})]):e._e()],1)]}}])})],1),t("div",{staticStyle:{padding:"10px 0"}},[t("el-pagination",{attrs:{"current-page":e.params.pageNum,"page-sizes":[2,5,10,20],"page-size":e.params.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1),t("el-dialog",{attrs:{title:"课程信息",visible:e.dialogFormVisible,width:"30%"},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[t("el-form",{attrs:{"label-width":"80px",size:"small"}},[t("el-form-item",{attrs:{label:"名称"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),t("el-form-item",{attrs:{label:"学分"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.score,callback:function(t){e.$set(e.form,"score",t)},expression:"form.score"}})],1),t("el-form-item",{attrs:{label:"课时"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.times,callback:function(t){e.$set(e.form,"times",t)},expression:"form.times"}})],1),t("el-form-item",{attrs:{label:"老师"}},[t("el-select",{attrs:{clearable:"",placeholder:"请选择"},model:{value:e.form.teacherId,callback:function(t){e.$set(e.form,"teacherId",t)},expression:"form.teacherId"}},e._l(e.teachers,(function(e){return t("el-option",{key:e.id,attrs:{label:e.nickname,value:e.id}})})),1)],1)],1),t("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),t("el-button",{attrs:{type:"primary"},on:{click:e.save}},[e._v("确 定")])],1)],1)],1)},l=[],o=(a(6263),{name:"Course",data(){return{form:{},tableData:[],multipleSelection:[],total:0,dialogFormVisible:!1,teachers:[],user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},params:{pageNum:1,pageSize:10,name:""}}},created(){this.load()},methods:{selectCourse(e){this.request.post("/course/checkCourse/"+e+"/"+this.user.id).then((e=>{"200"===e.code?this.$message.success("选课成功"):this.$message.success(e.msg)}))},load(){this.request.get("/course/page",{params:this.params}).then((e=>{"200"===e.code&&(this.tableData=e.data.records,this.total=e.data.total)})),this.request.get("/user/role/ROLE_TEACHER").then((e=>{this.teachers=e.data}))},changeEnable(e){this.request.post("/course/update",e).then((e=>{"200"===e.code&&this.$message.success("操作成功")}))},handleAdd(){this.dialogFormVisible=!0,this.form={}},handleEdit(e){this.form=JSON.parse(JSON.stringify(e)),this.dialogFormVisible=!0},del(e){this.request.delete("/course/"+e).then((e=>{"200"===e.code?(this.$message.success("删除成功"),this.load()):this.$message.error("删除失败")}))},handleSelectionChange(e){console.log(e),this.multipleSelection=e},delBatch(){let e=this.multipleSelection.map((e=>e.id));this.request.post("/course/del/batch",e).then((e=>{"200"===e.code?(this.$message.success("批量删除成功"),this.load()):this.$message.error("批量删除失败")}))},save(){this.request.post("/course",this.form).then((e=>{"200"===e.code?(this.$message.success("保存成功"),this.dialogFormVisible=!1,this.load()):this.$message.error("保存失败")}))},reset(){this.name="",this.load()},handleSizeChange(e){console.log(e),this.pageSize=e,this.load()},handleCurrentChange(e){console.log(e),this.pageNum=e,this.load()},download(e){window.open(e)}}}),r=o,i=a(1001),n=(0,i.Z)(r,s,l,!1,null,"67b4e7aa",null),c=n.exports}}]);
//# sourceMappingURL=612.08f76e4b.js.map