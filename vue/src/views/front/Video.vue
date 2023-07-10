<template>
  <div style="padding: 10px">
    <el-card>
      <el-card class="videoCard" v-for="item in videos" :key="item.id">
        <img src="http://localhost:9090/api/file/cfb99202d0f846f1a32b9b5754565165.png" alt="404"
             @click="detail(item.id)"/>
        <span class="videoName" @click="detail(item.id)">{{ item.name }}</span>
        <span style="float: right;color: #eee"> 文件大小：{{ item.size }}kb</span>
      </el-card>
    </el-card>
  </div>
</template>
<script>
export default {
  name: "Video",
  data() {
    return {
      videos: [],
    }
  },
  created() {
    this.request.get("/echarts/file/front/all").then(res => {
      console.log(res.data);
      this.videos = res.data.filter(v => v.type === 'mp4')
    })
  },
  methods: {
    detail(id) {
      this.$router.push({path: "/front/videoDetail", query: {id: id}})
    }
  }
}
</script>
<style>
.videoCard {
  padding: 0;
  width: 308px;
  height: 250px;
  color: #18191C;
}

.videoCard img {
  width: 308px;
  height: 173px;
  cursor: pointer;
}

.videoName {
  cursor: pointer;
}

.videoName:hover {
  cursor: pointer;
  color: #409EFF;
}
</style>