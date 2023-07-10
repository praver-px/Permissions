<template>
  <div style="line-height: 60px; display: flex">
    <div style="flex:1;">
      <span :class="collapseBtnClass" style="cursor:pointer; font-size: 20px" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currenPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-dropdown style="width: 50px;cursor: pointer">
      <el-avatar size="medium" :src="user.avatar" style="margin-top: 10px"></el-avatar>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item disabled="">{{ user.nickname }}</el-dropdown-item>
        <el-dropdown-item><span @click="getUserInfo">个人信息</span></el-dropdown-item>
        <el-dropdown-item><span @click="logout">退出登录</span></el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Boolean,
    user: Object,

  },
  computed: {
    currenPathName() {
      return this.$store.state.currenPathName;
    }
  },
  data() {
    return {}
  },
  methods: {
    logout() {
      this.$store.commit("logout")
      this.$message.success("退出登录")
    },
    getUserInfo() {
      this.$router.push('/person');
    }
  }

}
</script>