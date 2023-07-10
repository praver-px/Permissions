<template>
  <div class="wrapper">
    <div class="inBox">
      <div class="title"><b>注 册</b></div>
      <el-form :model="user" ref="loginForm" :rules="rules">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <div style="margin: 10px 0;text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">提交</el-button>
          <el-button type="success" size="small" autocomplete="off" @click="$router.push('/login')">返回</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import da from "element-ui/src/locale/lang/da";

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
        confirmPassword: [
          {required: true, message: '请确认密码！', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在3-10之间', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['loginForm'].validate((valid) => {
        if (valid) {
           if (this.user.password !== this.user.confirmPassword){
             this.$message.error("两次密码不一致")
             return false
           }
          this.request.post("/user/register", this.user).then(res => {
            if (res.code === '200') {
              this.$notify.success("注册成功！");
              this.$router.push("/login");
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
  height: 400px;
  padding: 20px;
  border-radius: 10px
}

.title {
  margin: 20px 0;
  text-align: center;
  font-size: 24px;

}
</style>