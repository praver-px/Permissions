"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[758],{5758:function(e,t,l){l.r(t),l.d(t,{default:function(){return m}});var a=function(){var e=this,t=e._self._c;return t("div",[t("div",{staticStyle:{padding:"10px 0"}},[t("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入名称","suffix-icon":"el-icon-user"},model:{value:e.params.name,callback:function(t){e.$set(e.params,"name",t)},expression:"params.name"}}),t("el-button",{staticClass:"ml-5",attrs:{type:"primary"},on:{click:e.load}},[e._v("搜索")]),t("el-button",{staticClass:"ml-5",attrs:{type:"warning"},on:{click:e.reset}},[e._v("重置")])],1),t("div",{staticStyle:{margin:"10px 0"}},[t("el-button",{staticClass:"ml-5",attrs:{type:"danger",disabled:0===this.multipleSelection.length},on:{click:e.rmvList}},[e._v(" 批量删除"),t("i",{staticClass:"el-icon-delete"})]),t("el-button",{staticClass:"ml-5",attrs:{type:"primary"},on:{click:e.handAdd}},[e._v("新增"),t("i",{staticClass:"el-icon-circle-plus-outline"})])],1),t("el-table",{attrs:{data:e.tableData,stripe:"",border:"","row-key":"id","default-expand-all":"","header-cell-class-name":"headerBg"},on:{"selection-change":e.handleSelectionChange}},[t("el-table-column",{attrs:{type:"selection",width:"55"}}),t("el-table-column",{attrs:{prop:"id",label:"ID",width:"55"}}),t("el-table-column",{attrs:{prop:"name",label:"名称"}}),t("el-table-column",{attrs:{prop:"path",label:"路径"}}),t("el-table-column",{attrs:{prop:"pagePath",label:"页面路径"}}),t("el-table-column",{staticClass:"fontSize18",attrs:{prop:"icon",align:"center","label-class-name":"fontSize12",label:"图标"},scopedSlots:e._u([{key:"default",fn:function(e){return[t("i",{class:e.row.icon})]}}])}),t("el-table-column",{attrs:{prop:"description",label:"描述"}}),t("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(l){return[l.row.pid||l.row.path?e._e():t("el-button",{attrs:{type:"success",circle:""},on:{click:function(t){return e.add2Menu(l.row)}}},[t("i",{staticClass:"el-icon-plus"})]),t("el-button",{staticClass:"ml-5",attrs:{type:"primary",circle:""},on:{click:function(t){return e.handEdit(l.row)}}},[t("i",{staticClass:"el-icon-edit"})]),t("el-popconfirm",{staticClass:"ml-5",attrs:{title:"您确定要删除这行信息嘛？"},on:{confirm:function(t){return e.del(l.row.id)}}},[t("el-button",{attrs:{slot:"reference",type:"danger",circle:""},slot:"reference"},[t("i",{staticClass:"el-icon-delete"})])],1)]}}])})],1),t("div",[t("el-dialog",{attrs:{title:"菜单信息",visible:e.centerDialogVisible,width:"40%",center:""},on:{"update:visible":function(t){e.centerDialogVisible=t}}},[t("el-form",{attrs:{"label-width":"200px"}},[t("el-form-item",{attrs:{label:"名称"}},[t("el-input",{attrs:{autocomplete:"off",placeholder:"请输入名称"},model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),t("el-form-item",{attrs:{label:"路径"}},[t("el-input",{attrs:{autocomplete:"off",placeholder:"请输入路径"},model:{value:e.form.path,callback:function(t){e.$set(e.form,"path",t)},expression:"form.path"}})],1),t("el-form-item",{attrs:{label:"页面路径"}},[t("el-input",{attrs:{autocomplete:"off",placeholder:"请输入路径"},model:{value:e.form.pagePath,callback:function(t){e.$set(e.form,"pagePath",t)},expression:"form.pagePath"}})],1),t("el-form-item",{attrs:{label:"图标"},scopedSlots:e._u([{key:"default",fn:function(l){return[t("el-select",{staticStyle:{width:"100%"},attrs:{clearable:"",placeholder:"请选择"},model:{value:e.form.icon,callback:function(t){e.$set(e.form,"icon",t)},expression:"form.icon"}},e._l(e.icons,(function(l){return t("el-option",{key:l.name,attrs:{label:l.name,value:l.value}},[t("i",{class:l.value}),e._v(" "+e._s(l.name)+" ")])})),1)]}}])}),t("el-form-item",{attrs:{label:"描述",prop:"description"}},[t("el-input",{attrs:{autocomplete:"off",placeholder:"请输入描述"},model:{value:e.form.description,callback:function(t){e.$set(e.form,"description",t)},expression:"form.description"}})],1)],1),t("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{attrs:{type:"primary"},on:{click:e.saveOrUpdate}},[e._v("确 定")]),t("el-button",{on:{click:function(t){e.centerDialogVisible=!1}}},[e._v("取 消")])],1)],1)],1)],1)},i=[],s=l(6263),o={name:"User",data(){return{tableData:[],total:0,form:{},icons:[],multipleSelection:[],visible:!1,centerDialogVisible:!1,params:{name:""}}},created(){this.load()},methods:{load(){s.Z.get("/menu",{params:this.params}).then((e=>{"200"===e.code&&(this.tableData=e.data)})),s.Z.get("/menu/icons").then((e=>{"200"===e.code&&(this.icons=e.data)}))},reset(){this.params.name="",this.load()},handAdd(){this.form={},this.centerDialogVisible=!0},handEdit(e){this.form=e,this.centerDialogVisible=!0},saveOrUpdate(){s.Z.post("/menu",this.form).then((e=>{"200"===e.code?(this.$notify.success("操作成功"),this.centerDialogVisible=!1,this.load()):this.$notify.error(e.msg)})),this.centerDialogVisible=!1,this.load()},del(e){s.Z.delete("/menu/"+e).then((e=>{"200"===e.code?(this.$notify.success("删除成功"),this.load()):this.$notify.error(e.msg)}))},handleSelectionChange(e){this.multipleSelection=e},rmvList(){let e=this.multipleSelection.map((e=>e.id));this.$confirm("确定要删除这"+e.length+"行数据吧？","确认信息",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{s.Z.delete("/menu/deleteList/"+e).then((e=>{"200"===e.code&&(this.$message.success("批量删除成功!"),this.load())}))}))},handleCurrentChange(e){this.params.pageNum=e,this.load()},add2Menu(e){this.centerDialogVisible=!0,this.form={},this.form.pid=e.id}}},n=o,r=l(1001),c=(0,r.Z)(n,a,i,!1,null,null,null),m=c.exports}}]);
//# sourceMappingURL=758.49cc0a04.js.map