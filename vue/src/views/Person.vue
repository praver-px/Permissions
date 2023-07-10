<template>
  <el-card style="width: 500px;">
    <el-form label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" disabled autocomplete="off" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="form.nickname" autocomplete="off" placeholder="请输入昵称"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" autocomplete="off" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" autocomplete="off" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="form.address" autocomplete="off" placeholder="请输入地址"></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:9090/api/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
          <img v-if="form.avatar" :src="form.avatar" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </el-form-item>
    </el-form>

  </el-card>
</template>

<script>
import request from "@/util/request";

export default {
  name: "Person",
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getUser().then(res => {
      this.form = res
    })
  },
  methods: {
    async getUser() {
      return (await this.request.get("/user/username/" + this.user.username)).data
    },
    saveOrUpdate() {
      request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success("操作成功");
          this.$emit("refreshUser")
          this.$router.push("/user")
        } else {
          this.$notify.error(res.msg);
        }
      })
    },
    handleAvatarSuccess(res) {
      this.form.avatar = res
    },
  },
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
