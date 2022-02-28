<template>
  <div class="box">
    <div class="search">
      <el-input
        v-model="search"
        placeholder="输入关键字 搜索"
        :suffix-icon="Search"
        class="search"
        clearable
        style="width: 30%; margin-right: 10px"
        @keydown.enter="selSearchNote"
      />
      <el-button type="primary" plain @click="selSearchNote">搜索</el-button>
      <el-row class="noteInfo">
        <el-col :span="24" style="margin-bottom: 10px">
          <el-divider />
          <p class="searchResultCount">共{{ searchResultCount }}条数据</p>
        </el-col>
        <el-col :span="24">
          <el-radio-group v-model="orderBy" size="small" @change="changeOrderBy(orderBy)">
            <el-radio-button label="默认排序" style="margin-right: 10px"></el-radio-button>
            <el-radio-button label="最多浏览"></el-radio-button>
            <el-radio-button label="最多点赞"></el-radio-button>
            <el-radio-button label="最多评论"></el-radio-button>
          </el-radio-group>
        </el-col>
        <el-col :span="24">
          <el-radio-group v-model="groupBy" size="small" @change="changeGroupBy(groupBy)">
            <el-radio-button label="全部分类" style="margin-right: 10px"></el-radio-button>
            <el-radio-button label="生活杂谈"></el-radio-button>
            <el-radio-button label="学习分享"></el-radio-button>
            <el-radio-button label="工作经验"></el-radio-button>
          </el-radio-group>
        </el-col>
        <el-divider />
      </el-row>

      <el-empty description="无数据" v-if="showEmpty" />
      <div v-for="(item, index) in searchResult" :key="index" class="noteInfo">
        <el-row @click="toReadNote(item.id)" style="cursor: pointer">
          <el-col :span="24" style="margin-bottom: 10px">
            <span class="title"> {{ item.title }} </span>
          </el-col>
          <el-col :span="24" style="margin-bottom: 10px">
            <span class="articleTabled"> {{ item.article_tabled }} </span>
          </el-col>
          <el-col :span="24">
            <el-row>
              <el-col :span="6" style="display: flex; align-items: flex-end">
                <el-avatar :src="item.avatar_url" style="margin-right: 5px" /> <span>{{ item.name }}</span>
              </el-col>
              <el-col :span="6"> {{ item.select_categories }} </el-col>
              <el-col :span="4"> <span class="iconfont icon-eye" />{{ item.look }} </el-col>
              <el-col :span="4"> <span class="iconfont icon-like" />{{ item.likes }} </el-col>
              <el-col :span="4">
                <span class="iconfont icon-comment" /> {{ getNoteCommentCount(item.id, index) }}
                {{ item.noteCommentCount }}
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        <el-divider />
      </div>
      <!-- 分页 -->
      <el-pagination
        @current-change="changePage(page)"
        v-model:current-page="page"
        layout="prev, pager, next"
        :total="searchResultCount"
        background
        class="pagination"
        hide-on-single-page
      />
    </div>
    <!-- search end -->
  </div>
  <!-- box end -->
</template>
<script lang="ts" setup>
import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { Search } from '@element-plus/icons-vue';
import axios from 'axios';
import router from '../router';

const route = useRoute();
const { q, tagName } = route.query;
const searchResultCount = ref();
const search = ref(q);
const orderBy = ref('默认排序');
const groupBy = ref('全部分类');
const page = ref(1);
const searchResult = ref([
  {
    id: '', // 笔记ID
    title: '', // 标题
    text: '', // 正文
    label_values: '', // 标签
    select_type: '', // 笔记类型
    select_categories: '', // 笔记分类
    select_grade: '', // 笔记等级
    original_author: '', // 原作者
    message: '', // 是否公开
    create_time: '', // 创建时间
    likes: '', // 点赞数
    name: '', // 作者名
    article_tabled: '', // 笔记摘要
    look: '', // 浏览次数
    collection: '', // 收藏数
    user_id: '', // 作者ID
    avatar_url: '', // 头像
    noteCommentCount: 0, // 评论数
  },
]);
// 空状态是否展示
const showEmpty = computed(() => {
  if (searchResult.value.length === 0) {
    return true;
  }
  return false;
});

// 笔记评论数
function getNoteCommentCount(noteId: string, index: number) {
  axios.get(`api/comment/selNoteCommentCount?noteId=${noteId}`).then((res) => {
    searchResult.value[index].noteCommentCount = res.data.data;
  });
}
// 搜索笔记
function selSearchNote() {
  const result = ref();
  axios
    .get(`api/note/selSearchNote?q=${search.value}&page=1`)
    .then((res) => {
      result.value = res.data;
      searchResult.value = result.value.data.records;
      searchResultCount.value = Number(result.value.msg);
    })
    .then(() => {
      if (searchResultCount.value > 1000) {
        searchResultCount.value = '1000+';
      }
    });
}
// 分页 当前页数改变
function changePage(pageNum: number) {
  const result = ref();
  axios
    .get(`api/note/selSearchNote?q=${search.value}&page=${pageNum}`)
    .then((res) => {
      result.value = res.data;
      searchResult.value = result.value.data.records;
      searchResultCount.value = Number(result.value.msg);
    })
    .then(() => {
      if (searchResultCount.value > 1000) {
        searchResultCount.value = '1000+';
      }
    });
}
// 排序
function changeOrderBy(order: string) {
  const result = ref();
  if (order === '最多评论') {
    const commentOrderByList = searchResult.value;
    for (let i = 0; i < commentOrderByList.length - 1; i += 1) {
      for (let j = 0; j < commentOrderByList.length - 1 - i; j += 1) {
        if (commentOrderByList[j].noteCommentCount < commentOrderByList[j + 1].noteCommentCount) {
          const temp = commentOrderByList[j + 1];
          commentOrderByList[j + 1] = commentOrderByList[j];
          commentOrderByList[j] = temp;
        }
      }
    }
    searchResult.value = commentOrderByList;
  } else {
    axios
      .get(`api/note/selSearchNote?q=${search.value}&page=1&orderBy=${order}&label=${tagName}`)
      .then((res) => {
        result.value = res.data;
        searchResult.value = result.value.data.records;
        searchResultCount.value = Number(result.value.msg);
      })
      .then(() => {
        if (searchResultCount.value > 1000) {
          searchResultCount.value = '1000+';
        }
      });
  }
}
// 分组
function changeGroupBy(group: string) {
  const result = ref();
  axios
    .get(`api/note/selSearchNote?q=${search.value}&page=1&groupBy=${group}&label=${tagName}`)
    .then((res) => {
      result.value = res.data;
      searchResult.value = result.value.data.records;
      searchResultCount.value = Number(result.value.msg);
    })
    .then(() => {
      if (searchResultCount.value > 1000) {
        searchResultCount.value = '1000+';
      }
    });
}
function toReadNote(noteId: string) {
  router.push({ name: 'ReadNote', query: { noteId } });
}
onMounted(() => {
  const result = ref();
  // 初始搜索笔记列表数据
  axios
    .get(`api/note/selSearchNote?q=${q}&page=1&label=${tagName}`)
    .then((res) => {
      result.value = res.data;
      searchResult.value = result.value.data.records;
      searchResultCount.value = Number(result.value.msg);
    })
    .then(() => {
      if (searchResultCount.value > 1000) {
        searchResultCount.value = '1000+';
      }
    });
});
</script>
<style scoped>
.search {
  width: 80%;
  text-align: center;
  margin: 0 auto;
}
.noteInfo,
.pagination {
  width: 60%;
  margin: 0 auto;
  text-align: left;
}
.el-row {
  align-items: flex-end;
}
.title {
  font-family: '思源黑体';
}
.articleTabled {
  font-size: small;
}
.searchResultCount {
  text-align: left;
}
</style>
