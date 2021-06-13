<template>
  <el-row class="warp">
    <el-col :span="24" class="warp-breadcrum">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }"><b>首页</b></el-breadcrumb-item>
        <el-breadcrumb-item>菜单列表</el-breadcrumb-item>
      </el-breadcrumb>
    </el-col>
    <el-col :span="24" class="warp-main" v-loading="loading" element-loading-text="拼命加载中">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" size="small">
          <el-form-item>
            <el-button type="primary" @click="showAddDialog(0,-1)">添加</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-col>
    <!--菜单表格-->
    <el-col :span="24" class="warp-main" v-loading="loading" element-loading-text="拼命加载中">
      <tree-table :data="menudata" :columns="columns" border highlight-current-row v-loading="loading"
                  style="width: 100%;">
        <el-table-column label="类型" prop="object.type">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.object.type === null"></el-tag>
            <el-tag v-if="scope.row.object.type === 0">目录</el-tag>
            <el-tag v-if="scope.row.object.type === 1">菜单</el-tag>
            <el-tag v-if="scope.row.object.type === 2">操作</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="路径" prop="object.url"></el-table-column>
        <el-table-column label="路径类型" prop="object.urlType"></el-table-column>
        <el-table-column label="权限" prop="object.perms"></el-table-column>
        <el-table-column label="操作" width="250">
          <template slot-scope="scope">
            <el-button size="mini" @click="showAddDialog(scope.row.id,scope.row.object.type)">增加</el-button>
            <el-button size="mini" @click="showEditDialog(scope.$index,scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="remove(scope.$index,scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </tree-table>
      <!-- 添加界面 -->
      <el-dialog title="编辑" :visible.sync="addFormVisible" :close-on-click-modal="false">
        <el-form :model="addForm" label-width="80px" :rules="editFormRules" ref="addForm">
          <el-form-item label="类型" prop="type">
            <el-radio-group v-model="addForm.type">
              <el-radio :label="0">目录</el-radio>
              <el-radio :label="1">菜单</el-radio>
              <el-radio :label="2">操作</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="名称" prop="name">
            <el-input v-model="addForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="路径" prop="url">
            <el-input v-model="addForm.url" auto-complete="off"></el-input>
          </el-form-item>
          <!--<el-form-item label="api类型" prop="perms">-->
          <!--<el-checkbox-group v-model="addForm.perms">-->
          <!--<el-checkbox v-for="item in permsItems" :label="item.key">{{item.value}}</el-checkbox>-->
          <!--</el-checkbox-group>-->
          <!--</el-form-item>-->
          <el-form-item label="权限" prop="perms">
            <el-input v-model="addForm.perms" auto-complete="off"></el-input>
          </el-form-item>

          <el-form-item label="图标" prop="icon">
            <el-input v-model="addForm.icon" auto-complete="off"></el-input>
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
          <el-form-item label="类型" prop="type">
            <el-radio-group v-model="editForm.type">
              <el-radio :label="0">目录</el-radio>
              <el-radio :label="1">菜单</el-radio>
              <el-radio :label="2">api</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="名称" prop="name">
            <el-input v-model="editForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="路径" prop="url">
            <el-input v-model="editForm.url" auto-complete="off"></el-input>
          </el-form-item>

          <el-form-item label="路径" prop="url">
            <el-input v-model="editForm.perms" auto-complete="off"></el-input>
          </el-form-item>
          <!--<el-form-item label="api类型" prop="perms">-->
          <!--<el-checkbox-group v-model="editForm.perms">-->
          <!--<el-checkbox v-for="item in permsItems" :label="item.key" :key="item.value">{{item.key}}</el-checkbox>-->
          <!--</el-checkbox-group>-->
          <!--</el-form-item>-->
          <el-form-item label="图标" prop="icon">
            <el-input v-model="editForm.icon" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="排序" prop="icon">
            <el-input v-model="editForm.orderNum" auto-complete="off"></el-input>
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
  /**

   */
  import treeTable from '@/components/TreeTable'
  import API from '../../api/api_menu'

  export default {
    name: 'treeTableDemo',
    components: {treeTable},
    data() {
      return {
        loading: false,
        addLoading: false,
        permsItems: [
          {key: "get", value: "get"},
          {key: "post", value: "post"},
          {key: "put", value: "put"},
          {key: "delete", value: "delete"}
        ],
        columns: [
          {
            text: '名称',
            value: 'text',
            width: 200
          },
        ],
        menudata: [],
        editFormVisible: false,
        editForm: {},
        editFormRules: {},
        addFormVisible: false,
        addForm: {}
      }
    },
    methods: {
      search: function () {
        let that = this
        API.menus().then(
          function (result) {
            that.menudata = result
          }
        )
      },
      showAddDialog: function (parentId, parentType) {
        if (parentType != 2) {
          this.addForm.type = parentType + 1
        } else {
          this.addForm.type = parentType
        }
        this.addForm.parentId = parentId
        this.addFormVisible = true
      },
      showEditDialog: function (index, row) {
        this.editFormVisible = true
        this.editForm = Object.assign({}, row.object)
        // this.editForm.perms = JSON.parse(this.editForm.perms)
        // if(!Array.isArray(this.editForm.perms)){
        //   this.editForm.perms = new Array()
        // }
      },
      editSubmit: function () {
        let that = this;
        this.$refs.editForm.validate(valid => {
          if (valid) {
            that.loading = true;
            let params = Object.assign({}, that.editForm);
            // params.perms = JSON.stringify(params.perms)
            API.editMenu(params).then(function (result) {
              if ('0' === result.code) {
                that.loading = false;
                that.$message;
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
                  message: "修改失败",
                  duration: 2000
                });
              }
            });
          }
        });
      },
      //新增
      addSubmit: function () {
        let that = this;
        this.$refs.addForm.validate((valid) => {
          if (valid) {
            that.loading = true;
            let para = Object.assign({}, this.addForm);
            API.add(para).then(function (result) {
              that.loading = false;
              if (result && parseInt(result.code) === 0) {
                that.$message.success({showClose: true, message: '新增成功', duration: 2000});
                that.$refs['addForm'].resetFields();
                that.addFormVisible = false;
                that.search();
              } else {
                that.$message.error({showClose: true, message: '修改失败', duration: 2000});
              }
            }, function (err) {
              that.loading = false;
              that.$message.error({showClose: true, message: err.toString(), duration: 2000});
            }).catch(function (error) {
              that.loading = false;
              console.log(error);
              that.$message.error({showClose: true, message: '请求出现异常', duration: 2000});
            });

          }
        });
      },
      remove: function (index, row) {
        let that = this;
        this.$confirm("确认删除该记录吗?", "提示", {type: "warning"})
          .then(() => {
            that.loading = true;
            API.remove({id: row.id})
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
      this.search();
    }
  }
</script>
