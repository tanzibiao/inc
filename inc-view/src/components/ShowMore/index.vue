<template>
  <el-button type="text" @click="handleShowMore">
    <i :class="{'el-icon-arrow-down':true,'reverse':showMore,'revert':!showMore}"/>
    {{ showMore?moreText:lessText }}
  </el-button>
</template>

<script>

export default {
  name: 'ShowMore',
  props: {
    //展开时显示的文案
    moreText: {
      type: String,
      default: "收起"
    },
    //收起时展示的文案
    lessText: {
      type: String,
      default: "更多"
    },
    //点击事件
    clickCall: {
      type: Function,
      default: ()=>{}
    }
  },
  data(){
    return {
      text:this.lessText,
      showMore:false,
    }
  },
  computed: {
  },
  methods: {
    handleShowMore(){
      this.showMore=!this.showMore;
      this.clickCall();
    }
  }
}
</script>
<style type="text/css">
.reverse {
  transform-origin:center center;
  transform: rotate(180deg);
  -webkit-transform: rotate(180deg);
  -moz-transform: rotate(180deg);
  -ms-transform: rotate(180deg);
  -o-transform: rotate(180deg);
  transition: transform 0.2s;
  -moz-transition: -moz-transform 0.2s;
  -moz-transition: -moz-transform 0.2s;
  -o-transition: -o-transform 0.2s;
  -ms-transition: -ms-transform 0.2s;
}

.revert {
  transform: rotate(0deg);
  -webkit-transform: rotate(0deg);
  -moz-transform: rotate(0deg);
  -ms-transform: rotate(0deg);
  -o-transform: rotate(0deg);
  transition: transform 0.2s;
  -moz-transition: -moz-transform 0.2s;
  -moz-transition: -moz-transform 0.2s;
  -o-transition: -o-transform 0.2s;
  -ms-transition: -ms-transform 0.2s;
}
</style>
