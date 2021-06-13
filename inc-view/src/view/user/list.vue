<template>
  <el-row class="warp">
    <el-col :span="24" class="warp-breadcrum">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }"><b>首页</b></el-breadcrumb-item>
        <el-breadcrumb-item>用户列表</el-breadcrumb-item>
      </el-breadcrumb>
    </el-col>

    <el-col :span="24" class="warp-main" v-loading="loading" element-loading-text="拼命加载中">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="filters">
          <el-form-item>
            <el-input v-model="filters.name" placeholder="用户名/姓名/昵称" style="min-width: 240px;"
                      @keyup.enter.native="handleSearch"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="showAddDialog">添加</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="users" highlight-current-row v-loading="loading" style="width: 100%;">
        <el-table-column type="index" width="60">
        </el-table-column>
        <el-table-column label="序号" prop="userId" width="80">
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="120" sortable>
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="120" sortable>
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="100" :formatter="formatSex" sortable>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="160" sortable>
        </el-table-column>
        <el-table-column prop="birth" label="出生日期" min-width="160" sortable>
        </el-table-column>
        <el-table-column prop="addr" label="地址" sortable>
        </el-table-column>
        <el-table-column label="操作" width="250">
          <template slot-scope="scope">
            <el-button size="mini" @click="showEditDialog(scope.$index,scope.row)">编辑</el-button>
            <el-button size="mini" @click="resetPwd(scope.row.userId)">重置密码</el-button>
            <el-button size="mini" type="danger" @click="removeUser(scope.$index,scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total"
                       style="float:right;">
        </el-pagination>
      </el-col>
      <!-- 添加界面 -->
      <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
        <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="addForm.username" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="addForm.password" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="addForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="出生日期" prop="birth">
            <el-date-picker type="date" placeholder="出生日期" v-model="addForm.birth"></el-date-picker>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input type="email" v-model="addForm.email"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="roleIds">
            <el-checkbox-group v-model="roleIds">
              <el-checkbox v-for="role in roles" :label="role.roleId" :key="role.roleId">{{role.roleName}}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="addFormVisible = false">取消</el-button>
          <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
        </div>
      </el-dialog>
      <!-- 编辑界面 -->
      <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
        <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="editForm.username" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="editForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="出生日期" prop="birth">
            <el-date-picker type="date" placeholder="出生日期" v-model="editForm.birth"></el-date-picker>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input type="email" v-model="editForm.email"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="roleIds">
            <el-checkbox-group v-model="roleIds">
              <el-checkbox v-for="role in roles" :label="role.roleId" :key="role.roleId">{{role.roleName}}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="editFormVisible = false">取消</el-button>
          <el-button type="primary" @click.native="editSubmit" :loading="addLoading">提交</el-button>
        </div>
      </el-dialog>

    </el-col>
  </el-row>
</template>

<script>
  import API from "../../api/api_user";
  import ROLE_API from "../../api/api_role";

  export default {
    data() {
      return {
        filters: {
          name: ""
        },
        loading: false,
        users: [],
        roles: [],
        roleIds: [],
        total: 0,
        page: 1,
        limit: 10,
        editFormVisible: false,
        editFormRules: {
          username: [
            {required: true, message: "请输入用户名", trigger: "blur"}
          ],
          password: [{required: true, message: "请输入作者", trigger: "blur"}],
          name: [{required: true, message: "请输入姓名", trigger: "blur"}]
        },
        editForm: {
          username: "",
          password: "",
          name: "",
          email: "",
          roleIds: []
        },
        //新增相关数据
        addFormVisible: false, //新增界面是否显示
        addLoading: false,
        addFormRules: {
          username: [
            {required: true, message: "请输入用户名", trigger: "blur"}
          ],
          password: [{required: true, message: "请输入作者", trigger: "blur"}],
          name: [{required: true, message: "请输入姓名", trigger: "blur"}]
        },
        addForm: {
          username: "",
          password: "",
          name: "",
          email: "",
          roleIds: []
        }
      };
    },
    methods: {
      //性别显示转换
      formatSex: function (row, column) {
        return row.sex == 1 ? "男" : row.sex == 0 ? "女" : "未知";
      },
      handleCurrentChange(val) {
        this.page = val;
        this.search();
      },
      handleSearch() {
        this.total = 0;
        this.page = 1;
        this.search();
      },
      //获取用户列表
      search: function () {
        let that = this;
        let params = {
          page: that.page,
          limit: 10,
          name: that.filters.name
        };

        that.loading = true;
        API.findList(params)
          .then(
            function (result) {
              that.loading = false;
              if (result && result.page.rows) {
                that.total = result.page.total;
                that.users = result.page.rows;
              }
            },
            function (err) {
              that.loading = false;
              that.$message.error({
                showClose: true,
                message: err.toString(),
                duration: 2000
              });
            }
          )
          .catch(function (error) {
            that.loading = false;
            console.log(error);
            that.$message.error({
              showClose: true,
              message: "请求出现异常",
              duration: 2000
            });
          });
      },
      showAddDialog: function () {
        let that = this;
        this.addFormVisible = true;
        that.roleIds = []
        ROLE_API.findList('').then(function (result) {
          that.roles = result.rows;
        })
      },
      showEditDialog: function (index, row) {
        let that = this;
        that.roleIds = []
        this.editFormVisible = true;
        this.editForm = Object.assign({}, row);
        ROLE_API.findList('').then(function (result) {
          that.roles = result.rows;
        })
        ROLE_API.findById(row.userId).then(function (result) {
          that.roleIds = result;
        })
      },
      addSubmit: function () {
        let that = this;
        this.$refs.addForm.validate(valid => {
          if (valid) {
            that.loading = true;
            let params = Object.assign({}, this.addForm);
            params.roleIds = that.roleIds
            API.addUser(params).then(function (result) {
              if ('0' === result.code) {
                that.loading = false;
                that.$message;
                that.$message.success({
                  showClose: true,
                  message: "新增成功",
                  duration: 2000
                });
                that.$refs["addForm"].resetFields();
                that.addFormVisible = false;
                that.search();
              } else {
                that.$message.error({
                  showClose: true,
                  message: result.msg || "新增失败",
                  duration: 2000
                });
              }
            });
          }
        });
      },
      editSubmit: function () {
        let that = this;
        this.$refs.editForm.validate(valid => {
          if (valid) {
            that.loading = true;
            let params = Object.assign({}, that.editForm);
            params.roleIds = that.roleIds
            API.editUser(params).then(function (result) {
              if ('0' === result.code) {
                // that.loading = false;
                // that.$message;
                that.$message.success({
                  showClose: true,
                  message: "修改成功",
                  duration: 2000
                });
                that.$refs["editForm"].resetFields();
                that.editFormVisible = false;
                that.search();
              } else {
                that.$message.error({
                  showClose: true,
                  message: result.msg,
                  duration: 2000
                });
              }
            });
          }
        });
      },
      resetPwd: function (userId) {
        let that = this;
        this.$confirm("确认重置该用户密码?", "提示", {type: "warning"})
          .then(() => {
            that.loading = true;
            API.resetPwd({userId}).then(function (result) {
              if ('0' === result.code) {
                that.$message.success({
                  showClose: true,
                  message: result.msg,
                  duration: 20000
                });
              } else {
                that.$message.error({
                  showClose: true,
                  message: result.msg,
                  duration: 2000
                });
              }
            })
              .catch(function (error) {
                that.loading = false;
                console.log(error);
                that.$message.error({
                  showClose: true,
                  message: "请求出现异常",
                  duration: 2000
                });
              });
          })
          .catch(() => {
          });

      },
      removeUser: function (index, row) {
        let that = this;
        this.$confirm("确认删除该记录吗?", "提示", {type: "warning"})
          .then(() => {
            that.loading = true;
            API.removeUser({id: row.userId})
              .then(
                function (result) {
                  that.loading = false;
                  if (result && parseInt(result.code) === 0) {
                    that.$message.success({
                      showClose: true,
                      message: "删除成功",
                      duration: 1500
                    });
                    that.search();
                  }
                },
                function (err) {
                  that.loading = false;
                  that.$message.error({
                    showClose: true,
                    message: err.toString(),
                    duration: 2000
                  });
                }
              )
              .catch(function (error) {
                that.loading = false;
                console.log(error);
                that.$message.error({
                  showClose: true,
                  message: "请求出现异常",
                  duration: 2000
                });
              });
          })
          .catch(() => {
          });
      }
    },
    mounted() {
      this.handleSearch();
    }
  };
</script>

<style scoped>

</style>
