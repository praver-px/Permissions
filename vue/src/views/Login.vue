<template>
  <div class="wrapper">
    <div class="inBox">
      <div class="title"><b>登 录</b></div>
      <el-form :model="user" ref="loginForm" :rules="rules">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <div style="margin: 10px 0;text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
          <el-button type="success" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import da from "element-ui/src/locale/lang/da";
import {setRoutes} from "@/router";

export default {
  name: "login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名！', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在3-10之间', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码！', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在3-10之间', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['loginForm'].validate((valid) => {
        if (valid) {
          this.request.post("/user/login", this.user).then(res => {
            if (res.code === '200') {
              localStorage.setItem("user", JSON.stringify(res.data))//存储用户信息到浏览器
              localStorage.setItem("menus", JSON.stringify(res.data.menus))
              //动态设置当前用户的路由
              setRoutes()
              this.$notify.success("登录成功！");
              if (res.data.role === 'ROLE_STUDENT') {
                this.$router.push("/front/home");
              } else {
                this.$router.push("/");
              }

            } else {
              this.$notify.error(res.msg);
            }
          })
        }
      })
    }
  },
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
  overflow: hidden;
}

.inBox {
  margin: 200px auto;
  background-color: #fff;
  width: 350px;
  height: 300px;
  padding: 20px;
  border-radius: 10px
}

.title {
  margin: 20px 0;
  text-align: center;
  font-size: 24px;

}
</style>