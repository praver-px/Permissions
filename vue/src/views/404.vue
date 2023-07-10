<template>
  <el-result title="404" subTitle="抱歉，请求错误">
    <template slot="icon">
      <el-image src="http://localhost:9090/api/file/cfb99202d0f846f1a32b9b5754565165.png"></el-image>
    </template>
    <template slot="extra">
      <el-button type="primary" size="medium" @click="$router.push('/')">返回</el-button>
    </template>
  </el-result>
</template>

<script>
export default {
  name: "404"
}
</script>

<style scoped>

</style>