<template>
  <el-tooltip class="item" effect="dark" :content="content" :placement="placement">
    <i class="el-icon-warning-outline hint-custom"></i>
  </el-tooltip>
</template>

<script>

export default {
  name: 'FormTip',
  props: {
    content: {
      type: String,
      default: ""
    },
    placement: {
      type: String,
      default: "top"
    }
  },
  computed: {
  },
  methods: {
  }
}
</script>
<style type="text/css">
.hint-custom {
  color: #ef6565;
  position: absolute;
}
</style>
