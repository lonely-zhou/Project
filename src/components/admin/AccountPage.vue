<template>
  <div class="box">
    <el-table :data="allUser" stripe v-loading="loading">
      <el-table-column type="index" label="序号" />
      <el-table-column label="头像" min-width="50">
        <template #default="scope">
          <el-avatar :size="50" :src="scope.row.avatarUrl"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" min-width="50" />
      <el-table-column prop="nickname" label="昵称" min-width="50" />
      <el-table-column prop="sex" label="性别" min-width="30" />
      <el-table-column prop="phone" label="手机" min-width="60" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="lastTime" label="最新登录时间" />
      <el-table-column prop="rname" label="角色" />
      <el-table-column align="right" fixed="right">
        <template #header>
          <el-input v-model="search" size="small" placeholder="搜索用户" @keydown.enter="searchUser" />
        </template>
        <template #default="scope">
          <el-dropdown split-button size="small" @command="handleRole">
            <span>更改角色</span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item :command="`${scope.row.id},2`">用户</el-dropdown-item>
                <el-dropdown-item :command="`${scope.row.id},1`">管理员</el-dropdown-item>
                <el-dropdown-item :command="`${scope.row.id},4`">超级管理员</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <el-popconfirm
            title="确定删除该用户?"
            confirm-button-text="确定"
            cancel-button-text="取消"
            @confirm="handleDelete(scope.$index, scope.row)"
          >
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      style="margin: 2%"
      @current-change="changePage(page)"
      v-model:current-page="page"
      layout="prev, pager, next"
      :total="total"
      background
      hide-on-single-page
    />
  </div>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { onMounted, ref } from 'vue';
import Result from '../../api/common';

const allUser = ref([]);
const search = ref();
const page = ref(1);
const total = ref();
const loading = ref(true);
interface User {
  avatarUrl: '';
  email: '';
  id: '';
  lastTime: '';
  nickname: '';
  password: '';
  phone: '';
  rname: '';
  sex: '';
  username: '';
}
// 删除用户
function handleDelete(index: number, row: User) {
  let result: Result;
  axios
    .delete(`/api/user/delUser?userId=${row.id}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) {
        allUser.value.splice(index, 1);
        ElMessage.success('删除成功');
      } else ElMessage.error('删除失败');
    });
  console.log(index, row);
}
// 改变页码
function changePage(pageNum: number) {
  let result: any;
  axios
    .get(`/api/admin/selAllUser?page=${pageNum}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      allUser.value = result.data.records;
      total.value = Number(result.msg);
      console.log(result.data.records);
    });
}
// 更改角色
function handleRole(str: string) {
  let result: Result;
  const id = str.split(',')[0];
  const rid = str.split(',')[1];
  axios
    .get(`/api/admin/updUserRole?rid=${rid}&id=${id}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      if (result.code === 200) {
        ElMessage.success('角色修改成功');
        changePage(page.value);
      } else ElMessage.error(result.msg);
    });
}
function searchUser() {
  let result: Result;
  axios
    .get(`/api/admin/searchUser?page=1&username=${search.value}`)
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      allUser.value = result.data.records;
    });
}
onMounted(() => {
  let result: any;
  axios
    .get('/api/admin/selAllUser?page=1')
    .then((res) => {
      result = res.data;
    })
    .then(() => {
      allUser.value = result.data.records;
      total.value = Number(result.msg);
      loading.value = false;
    });
});
</script>
<style scoped></style>
