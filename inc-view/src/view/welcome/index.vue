<template>
  <el-container id="main" style="background-color: #f1f1f1">
    <el-header id="nav">
      <el-menu
        :default-active="activeIndex"
        mode="horizontal"
        active-text-color="#409EFF">
        <el-menu-item index="1">首页</el-menu-item>
        <el-menu-item index="4" style="float: right"><a href="http://inc.com" target="_blank">关于作者</a></el-menu-item>
        <el-menu-item index="3" style="float: right"><a href="/">inc开源项目</a>
        </el-menu-item>
        <el-menu-item index="2" style="float: right"><a
          href="http://inc.com">inc开源项目</a></el-menu-item>
      </el-menu>
    </el-header>
    <el-main id="content" >
      <el-carousel indicator-position="outside" style="margin-top: 30px;">
        <el-carousel-item>
          <el-row>
            <el-col :span="10" :offset="7">
              <div>
                <h1>inc</h1>
                <h2>
                  基于springboot的开源框架<br>单体项目急速开发利器
                </h2>
                <el-button round><a href="https://gitee.com/lcg0124/inc/wikis/Home">起步</a></el-button>
                <el-button round><a href="https://gitee.com/lcg0124/inc">源码</a></el-button>
              </div>
            </el-col>
          </el-row>
        </el-carousel-item>
        <el-carousel-item>
          <el-row>
            <el-col :span="10" :offset="7">
              <div>
                <h1>inc</h1>
                <h2>
                  基于springcloud的开源框架<br>微服务入门
                </h2>
                <el-button round>起步</el-button>
                <el-button round>源码</el-button>
              </div>
            </el-col>
          </el-row>
        </el-carousel-item>
      </el-carousel>

      <el-row :gutter="20" style="margin-top: 30px">
        <el-col :span="8">
          <el-card shadow="always" style="text-align: center">
            <h3>大道至简</h3>
            <p>减少过度封装，展现技术本质</p>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="hover" style="text-align: center">
            <h3>开源精神</h3>
            <p>做简洁高效，易于上手的开源项目</p>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="never" style="text-align: center">
            <h3>不懈追求</h3>
            <p>技术服务于人，服务于生活</p>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
    <el-footer >
      <el-row>
        <el-col style="text-align: center">保留权利</el-col>
      </el-row>
    </el-footer>
  </el-container>

</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        activeIndex: "1"
      }
    }
  }
</script>

<style scoped>

  /*#nav {*/
  /*width: 100%;*/
  /*height: 46px;*/
  /*position: fixed;*/
  /*}*/

  /*#content {*/
  /*width: 100%;*/
  /*position: absolute;*/
  /*top: 46px;*/
  /*bottom: 20px;*/
  /*left: 0px;*/
  /*}*/
</style>
