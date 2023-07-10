<template>
  <el-menu
      :default-active="$route.path"
      :default-openeds="opens"
      router
      style="min-height: 100%;"
      background-color="rgb(48,65,86)"
      active-text-color="#ffd04b"
      :collapse-transition="false"
      :collapse="isCollapse"
      text-color="#fff">
    <!--    系统标题-->
    <div style="height: 60px;line-height: 60px;text-align: center">
      <img src="@/assets/logo.png" alt="logo" style="width: 20px;position: relative;top: 5px;margin-right: 5px">
      <b style="color: #ffd04b" v-show="logoTextShow">后台管理系统</b>
    </div>

    <div v-for="item in menus " :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span>{{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id+''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span>{{ item.name }}</span>
          </template>
          <div v-for="subItem in item.children " :key="subItem.id">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon"></i>
              <span>{{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean,
  },
  data() {
    return {
      opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : [],
      menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
    }
  },

}
</script>

<style scoped>

</style>