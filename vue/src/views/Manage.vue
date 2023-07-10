<template>
  <el-container style="min-height: 100vh">
    <el-aside :width="sideWidth+'px'"
              style="background-color: rgb(238, 241, 246);
              overflow-x: hidden;
              box-shadow: 2px 0 6px rgb(0 21 41 /35%)">
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
    </el-aside>
    <el-container>
      <el-header style="border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"/>
      </el-header>

      <el-main>
        <!--        当前页面有子路由控制显示内容-->
        <router-view @refreshUser="getUser"/>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>

import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  name: 'HomeView',
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      user: {}
    }
  },
  components: {
    Aside,
    Header,
  },
  created() {
    this.getUser()
  },
  methods: {
    collapse() {// 控制侧边栏收缩
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {
        this.sideWidth = 64
        this.collapseBtnClass = "el-icon-s-unfold"
        this.logoTextShow = false
      } else {
        this.sideWidth = 200
        this.collapseBtnClass = "el-icon-s-fold"
        this.logoTextShow = true
      }
    },
    getUser() {
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
      this.request.get("/user/username/" + username).then(res => {
        this.user = res.data
      })
    },
  }
}
</script>


