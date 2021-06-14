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
        <el-form :inline="true" :model="filters">
          <el-form-item>
            <el-input v-model="filters.id" placeholder="编号" @keyup.enter.native="handleSearch"></el-input>
          </el-form-item>

          <el-form-item>
            <el-input v-model="filters.name" placeholder="书名" @keyup.enter.native="handleSearch"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" :loading="loading" v-on:click="handleSearch">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <addForm :call="search" icon="el-icon-plus" type="primary" title="新增"/>
      <!--列表-->
      <el-table :data="dataList" highlight-current-row @selection-change="selsChange" style="width: 100%;">
        <el-table-column type="index" width="60"></el-table-column>
        <el-table-column prop="name" label="书名"></el-table-column>
        <el-table-column prop="status" label="状态"></el-table-column>
        <el-table-column prop="user" label="借书人"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <addForm :call="search" :data="scope.row" size="mini" title="编辑"/>
            <el-button type="danger" @click="del(scope.$index,scope.row)" size="mini">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
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
  import addForm from './components/addForm'
  import api from '@/api/apiBook';

  export default {
    data() {
      return {
        filters: {
          id: ''
        },
        dataList: [],
        total: 0,
        page: 1,
        pageSize: 10,
        loading: false,
      }
    },
    //组件
    components: {
      addForm,
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

