<template>
  <div class="box">
    <el-table :data="allUser" stripe style="width: 100%">
      <el-table-column type="index" label="序号" />
      <el-table-column label="头像">
        <template #default="scope">
          <el-avatar :size="50" :src="scope.row.avatarUrl"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="sex" label="性别" />
      <el-table-column prop="phone" label="手机" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="lastTime" label="最新登录时间" />
      <el-table-column align="right" fixed="right">
        <template #header>
          <el-input v-model="search" size="small" placeholder="Type to search" />
        </template>
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">更改角色</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script lang="ts" setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';

const allUser = ref([]);
const search = ref();
interface User {
  avatarUrl: '';
  email: '';
  id: '';
  lastTime: '';
  nickname: '';
  password: '';
  phone: '';
  roleId: '';
  sex: '';
  username: '';
}

function handleDelete(index: number, row: User) {
  console.log(index, row);
}
function handleEdit(index: number, row: User) {
  console.log(index, row);
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
    });
});
</script>
<style scoped></style>
