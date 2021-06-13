<template>
  <el-row class="warp">
    <el-col :span="24" class="warp-breadcrum">
      <el-page-header @back="()=>this.$router.back()" content="修改密码">
      </el-page-header>
    </el-col>

    <el-col :span="24" class="warp-main">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item prop="oldPwd" label="原密码">
          <el-input type="password" v-model="form.oldPwd"></el-input>
        </el-form-item>
        <el-form-item prop="newPwd" label="新密码">
          <el-input type="password" v-model="form.newPwd"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPwd" label="确认新密码">
          <el-input type="password" v-model="form.confirmPwd"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="default" @click="handleChangepwd">提交</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>
<script>
import API from '../../api/api_user';
  export default{
    data(){
      return {
        form: {
          oldPwd: '',
          newPwd: '',
          confirmPwd: ''
        },
        rules: {
          oldPwd: [
            {required: true, message: '请输入原密码', trigger: 'blur'}
          ],
          newPwd: [
            {required: true, message: '请输入新密码', trigger: 'blur'}
          ],
          confirmPwd: [
            {required: true, message: '请输再次输入新密码', trigger: 'blur'}
          ],
        },
      }
    },
    methods: {
      handleChangepwd() {
        let that = this;
        that.$refs.form.validate((valid) => {
          if (valid) {
            that.loading = true;
            API.updatePwd(Object.assign({}, that.form)).then(function (result) {
              that.loading = false;
              if (result && parseInt(result.code) == '0') {
                that.$message.success({showClose: true, message: '修改成功,请重新登录', duration: 2000});
                localStorage.removeItem("access-token");
                localStorage.removeItem("menus");
                API.logout("");
                that.$router.push({path:'/login'})
              } else {
                that.$message.error({showClose: true, message: result.msg, duration: 2000});
              }
            }, function (err) {
              that.loading = false;
              that.$message.error({showClose: true, message: err.msg, duration: 2000});
            }).catch(function (error) {
              that.loading = false;
              console.log(error);
              that.$message.error({showClose: true, message: '请求出现异常', duration: 2000});
            });
          }
        });
      }
    }
  }
</script>
