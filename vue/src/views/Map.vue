<template>
  <div>
    <div id="container">
    </div>
    <div class="info">
      <p id='info'></p>
    </div>
  </div>


</template>

<script>
var content = [
  "<div>这是信息窗口</div>"
]

//获取用户所在城市信息
function showCityInfo() {
  //实例化城市查询类
  var citysearch = new AMap.CitySearch();
  //自动获取用户IP，返回当前城市
  citysearch.getLocalCity(function (status, result) {
    if (status === 'complete' && result.info === 'OK') {
      if (result && result.city && result.bounds) {
        var cityinfo = result.city;
        var citybounds = result.bounds;
        document.getElementById('info').innerHTML = '您当前所在城市：' + cityinfo;
        //地图显示当前城市
        map.setBounds(citybounds);
      }
    } else {
      document.getElementById('info').innerHTML = result.info;
    }
  });
}


export default {
  name: "Map",
  data() {
    return {}
  },
  mounted() {
    var map = new AMap.Map("container", {
      zoom: 13,
      center: [116.397428, 39.90923],  //设置地图中心点坐标
      mapStyle: 'amap://styles/whitesmoke',  //设置地图的显示样式
      viewMode: '2D',  //设置地图模式
      icon: '//vdata.amap.com/icons/b18/1/2.png',
      resizeEnable: true
    });

    var infoWindow = new AMap.InfoWindow({
      // anchor: 'top-right',
      content: content.join("<br>") // 传入dom对象，或者html字符
    })

    var clickHandle = function (e) {
      console.log('此处位置为' + e.lnglat.getLng() + ',' + e.lnglat.getLat());
      infoWindow.open(map, map.getCenter())
    }
    // map.on('click',clickHandle)

    var marker = new AMap.Marker({
      position: new AMap.LngLat(116.39, 39.9),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
      title: '北京'
    });
    marker.on('click', clickHandle)


    //将创建的点添加到地图中
    map.add(marker)

    var path = []
    path.push([116.390733, 39.901856])
    path.push([116.39554, 39.918447])
    path.push([116.427125, 39.903436])
    map.plugin('AMap.DragRoute', function () {
      //path是驾车起、途径和终点，最多支持16个途径点
      var route = new AMap.DragRoute(map, path, AMap.DrivingPolicy.LEAST_FEE)

      //查询导航路线，并开启拖拽导航
      route.search()
    })

    var polyLine = new AMap.Polyline({
      path: path,
      strokeWeight: 5,
      borderWeight: 5,
      strokeStyle: 'dashed',
      strokeColor: '#409EFF',
      lineJoin: 'round'
    })
    map.add(polyLine)

    showCityInfo();

  }
}
</script>

<style>
#container {
  height: calc(100vh - 100px);
  width: 100%;
}
</style>