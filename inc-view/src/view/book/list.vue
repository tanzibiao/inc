<template>
  <el-row class="warp">
    <el-col :span="24" class="warp-breadcrum">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }"><b>首页</b></el-breadcrumb-item>
        <el-breadcrumb-item>图书管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-col>

    <el-col :span="24" class="warp-main" v-loading="loading" element-loading-text="拼命加载中">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form role="form" size="small" id="projectForm" class="form-horizontal details">
          <el-row>
            <el-col :xs="colLayOut.xs" :lg="colLayOut.lg" :sm="colLayOut.sm" :md="colLayOut.md">
              <el-form-item :label-width="formLabelWidth" label="编号:">
                <el-input size="mini" type="text" v-model="filters.id" clearable>
                </el-input>
              </el-form-item>
            </el-col>
            <el-collapse-transition>
              <div v-show="showQueryCondition">

              </div>
            </el-collapse-transition>


            <el-col :span="24">
              <el-form-item label-width="100px" style="float: right">
                <el-button :loading="loading" router-preventReClick size="mini" type="primary" plain icon="el-icon-search"
                           @click="handleSearch">查询
                </el-button>
                <ShowMore :clickCall="()=>this.showQueryCondition=!this.showQueryCondition"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-col>

      <addForm size="mini" :call="search" icon="el-icon-plus" type="primary" title="新增"/>
      <!--列表-->
      <el-table :data="dataList" highlight-current-row @selection-change="selsChange" style="width: 100%;">
        <el-table-column type="index" width="60"></el-table-column>
        <el-table-column prop="name" label="书名"></el-table-column>
        <el-table-column prop="status" label="状态"></el-table-column>
        <el-table-column prop="user" label="借书人"></el-table-column>
        <el-table-column prop="del" label="是否删除"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <addForm :call="search" :data="scope.row" size="mini" title="编辑"/>
            <el-button type="danger" @click="del(scope.$index,scope.row)" size="mini">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col size="mini" :span="24" class="toolbar">
        <el-pagination
          style="margin-top: 10px"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="page"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </el-col>

    </el-col>
  </el-row>
</template>
<script>
  import addForm from './components/addForm';
  import api from '@/api/apiBook';
  import ShowMore from '@/components/ShowMore';

  export default {
    data() {
      return {
        colLayOut: {xs: 12,sm:12,md:6, lg: 6},
        formLabelWidth: "160px",
        filters: {
          id: ''
        },
        dataList: [],
        total: 0,
        page: 1,
        pageSize: 10,
        loading: false,
        showQueryCondition: false,
      }
    },
    //组件
    components: {
      addForm,ShowMore,
    },
    //方法
    methods: {
      handleSizeChange(val) {
        this.pageSize = val;
        this.search();
      },
      handleCurrentChange(val) {
        this.page = val;
        this.search();
      },
      handleSearch() {
        this.search();
      },
      search() {
        let that = this;
        let params = {
          pageNo: that.page,
          pageSize: that.pageSize,
          ...this.filters,
        };

        that.loading = true;
        api.listByPage(params, (result)=>{
          if (result.code == '0') {
            that.loading = false;
            if (result && result.page.list) {
              that.total = result.page.total;
              that.dataList = result.page.list;
            }
          } else {
            that.loading = false;
            that.$message. error({
              showClose: true,
              message: result.msg,
              duration: 2000
            });
          }
        });

      },
      selsChange(sels) {
        this.sels = sels;
      },
      //删除
      del(index, row) {
        let that = this;
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'}).then(() => {
          that.loading = true;
          api.delete({id:row.id}, (result) => {
            that.loading = false;
            if (result && result.code == '0') {
              that.$message.success({showClose: true, message: '删除成功', duration: 1500});
              that.search();
            } else {
              that.$message. error({showClose: true, message: result.msg, duration: 2000});
            }
          });
        })
      },
    },
    mounted() {
      this.handleSearch()

    }
  }
</script>

