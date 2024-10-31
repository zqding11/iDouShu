<template>
  <view class="container" @scroll="onScroll">
    <view class="search">
      <input placeholder="搜索" v-model="searchText" class="search-input" />
      <uni-icons type="search" @click="search" mode="aspectFit" class="search-button"></uni-icons>
    </view>

    <view class="categories">
      <view 
        v-for="(category, index) in categories" 
        :key="index" 
        :class="['category-item', { active: activeCategory === index }]" 
        @click="selectCategory(index)">
        {{ category.name }}
      </view>
    </view>

    <view class="masonry">
      <view class="box" v-for="(list, index) in lists" :key="index" @click="goToNote(list.nid)">
        <item :username="list.username" :title="list.title" :image="list.coverImage" :userimage="list.userimage"></item>
      </view>
    </view>
    <view v-if="noMoreData" class="no-more" >没有更多了</view> 
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getCurrentInstance } from 'vue';
const searchText = ref('');
const activeCategory = ref(0);
const categories = ref([{ name: '全部' }, { name: '点赞最多' }, { name: '收藏最多' }]);
const lists = ref([]);
const noMoreData = ref(false);
let nowPage = 1;
const pageSize = 10;
const { appContext } = getCurrentInstance();
const baseUrl = appContext.config.globalProperties.$baseUrl;

async function getNoteList() {
  try {
    const sortBy = activeCategory.value === 0 ? 'time' : 'good';
    const response = await uni.request({
      url: `${baseUrl}note/list?type=${sortBy}&page=${nowPage}&size=${pageSize}`,
      method: 'GET',
    });
	console.log(response.data);
    return response.data;
  } catch (error) {
    console.error('获取笔记列表失败:', error);
    throw error;
  }
}

async function fetchNotes(isRefreshing = false) {
  try {
    const response = await getNoteList();
    const newRecords = response.records;
    const userPromises = newRecords.map(async (note) => {
      const userResponse = await uni.request({
        url: `${baseUrl}user/search?id=${note.userId}`,
        method: 'GET',
      });
      return {
        ...note,
        username: userResponse.data.nickName, 
        userimage: userResponse.data.avatar, 
      };
    });
    const notesWithUserInfo = await Promise.all(userPromises);
    if (isRefreshing) {
      lists.value = notesWithUserInfo;
    } else {
      lists.value.push(...notesWithUserInfo);
    }
    if (newRecords.length < pageSize) {
      noMoreData.value = true;
    }
  } catch (error) {
    console.error('获取笔记列表失败:', error);
  }
}

onMounted(async () => {
  await fetchNotes();


window.onscroll = function () {
    // 获取滚动位置和页面高度
    const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
    const windowHeight = window.innerHeight;
    const scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
    
    // console.log("滚动距离: " + scrollTop);

    // 当滚动到页面底部的90%时加载更多数据
    if (scrollTop + windowHeight >= scrollHeight * 0.9 && !noMoreData.value) {
      nowPage += 1;
      fetchNotes(); 
    }
  };
});
// 搜索功能
function search() {
  uni.showToast({
    title: `搜索内容: ${searchText.value}`,
    icon: 'none',
    duration: 2000,
  });
}

// 分类切换
function selectCategory(index) {
  activeCategory.value = index;
  nowPage = 1; 
  noMoreData.value = false;
  fetchNotes(true); 
}

// 跳转到笔记详情
function goToNote(noteId) {
  uni.navigateTo({
    url: `/pages/note/note?id=${noteId}`,
  });
}

// 下拉刷新
function onPullDownRefresh() {
  nowPage = 1;
  noMoreData.value = false;
  fetchNotes(true).then(() => {
    uni.stopPullDownRefresh();
  });
}

// 上拉加载更多
function onReachBottom() {
  if (!noMoreData.value) {
    nowPage += 1;
    fetchNotes(); 
  }
}
</script>

<style lang="scss" scoped>
.container {
  background-image: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 2rpx;
  min-height: 100vh;
}
.search {
  display: flex;
  border-bottom-right-radius: 7%;
  border-bottom-left-radius: 7%;
  border-bottom: 3rpx solid white;
  height: 220rpx;
  justify-content: center;
  align-items: center;
}
.search-input {
  padding: 7px;
  width: 650rpx;
  border: 1px solid rgba(255, 255, 255, 0.8);
  border-radius: 20rpx;
  height: 80rpx;
  background-color: rgba(255, 255, 255, 0.8);
}
.search-button {
  width: 50rpx;
  height: 50rpx;
  z-index: 1;
  position: absolute;
  right: 45rpx;
}
.categories {
  display: flex;
  justify-content: space-around;
  padding: 20rpx 0;
  background-color: rgba(255, 255, 255, 0);
  border-radius: 15rpx;
  margin-bottom: 20rpx;
}
.category-item {
  padding: 10rpx 20rpx;
  font-size: 28rpx;
  color: #333;
  background-color: #F3F3F3;
  border-radius: 30rpx;
  cursor: pointer;
}
.category-item.active {
  background-color: rgba(30, 155, 221, 0.4);
  color: #fff;
}
.masonry {
  column-count: 2;
  column-gap: 5rpx;
}
.box {
  break-inside: avoid;
  margin-bottom: 0rpx;
}
.no-more {
  height: 100rpx;
  text-align: center;
  font-size: 26rpx;
  color: #888;
  margin-top: 0rpx;
}
</style>
