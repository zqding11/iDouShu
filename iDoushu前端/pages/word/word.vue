<template>
  <view class="container">
    <view class="header">
          <view class="image" @click="imageupload">
            <image :src="userData.avatar || 'https://tse4-mm.cn.bing.net/th/id/OIP-C.yp-D-KHI3e2nN4eMBJcEVAAAAA?w=250&h=250&c=8&rs=1&qlt=90&o=6&dpr=1.6rm=2&dpr=1.6&pid=ImgDetMain'" mode="aspectFill"></image>
          </view>
          <view class="user">
            <view class="username">{{ userData.nickName || '用户名' }}</view>
            <view class="id">ID: {{ userData.id || '未知ID' }}</view>
          </view>
        </view>
        
        <view class="introduction">{{ userData.introduction || '我的简介' }}</view>

    <view class="box">
      <view class="interest" @click="handleClick('关注')"><br>关注</view>
      <view class="fans" @click="handleClick('粉丝')"><br>粉丝</view>
      <view class="good" @click="handleClick('获赞')"><br>获赞</view>
      <view class="icon">
        <uni-icons type="settings" size="60rpx" color="#0c3ac0" @click="setting"></uni-icons>
      </view>
    </view>

    <view class="menu">
      <view class="menu-item" @click="setActiveIcon('mail')">
        <uni-icons :type="activeIcon === 'mail' ? 'mail-open-filled' : 'mail-open'" size="60rpx" class="icon" color="#9acdeb"></uni-icons>
        <view class="text">我的发布</view>
      </view>
      <view class="menu-item" @click="setActiveIcon('heart')">
        <uni-icons :type="activeIcon === 'heart' ? 'heart-filled' : 'heart'" size="60rpx" class="icon" color="#9acdeb"></uni-icons>
        <view class="text">我的点赞</view>
      </view>
      <view class="menu-item" @click="setActiveIcon('star')">
        <uni-icons :type="activeIcon === 'star' ? 'star-filled' : 'star'" size="60rpx" class="icon" color="#9acdeb"></uni-icons>
        <view class="text">我的收藏</view>
      </view>
    </view>
	
	
	<view class="masonry">
		  <view class="box2" v-for="(list, index) in items" :key="index" @click="goToNote(list.nid)">
			<item style="height: auto; position: relative; left: -30rpx; " :username="userData.nickName" :title="list.title" :image="list.coverImage" :userimage="userData.avatar"></item>
		  </view>
	</view>
	<view class="no-more" >没有更多了</view> 

    

  </view>
</template>

<script setup>
import { ref } from 'vue';
import { onMounted, getCurrentInstance } from 'vue';

const mailIcon = ref('mail-open-filled');
const heartIcon = ref('heart');
const starIcon = ref('star');
const activeIcon = ref(''); 
const items = ref([]);
const userData = ref({});
function goToNote(noteId) {
  uni.navigateTo({
    url: `/pages/note/note?id=${noteId}`,
  });
}
onMounted(() => {
  const userId = getCurrentInstance().appContext.config.globalProperties.$userId;
  uni.request({
      url: `${getCurrentInstance().appContext.config.globalProperties.$baseUrl}user/search?id=${userId}`,
      method: 'GET',
      success: (res) => {
        if (res.statusCode === 200) {
          userData.value = res.data; // 将用户数据存入 userData
        }
      },
      fail: (err) => {
        console.error("请求用户信息失败：", err);
      }
    });
  uni.request({
    url: `${getCurrentInstance().appContext.config.globalProperties.$baseUrl}note/mylist?userid=${userId}`,
    method: 'GET',
    success: (res) => {
      if (res.statusCode === 200) {
        // 填充数据到 items 列表中
		
        items.value = res.data
		console.log(res.data);
      }
    },
    fail: (err) => {
      console.error("请求失败：", err);
    }
  });
});
function setting() {
	uni.navigateTo({
		url:"/pages/information/information"
	});
	// uni.showToast({
	//   title: `点击了设置`,
	//   icon: 'none'
	// });

}

function handleClick(item) {
  uni.showToast({
    title: `点击了${item}`,
    icon: 'none'
  });
}

function setActiveIcon(type) {
  if (activeIcon.value !== type) {
    activeIcon.value = type;
  }
}
</script>



<style lang="scss" scoped>
.container {
  height: 100vh;
  background-image: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 20rpx;
}

.header {
  display: flex;
  align-items: center;
  padding: 20rpx;
  .user{
	  margin-bottom: 20rpx;
  }
  .username {
  font-size: 40rpx;
  padding: 20rpx;
  padding-bottom:20rpx ;
}
  .id{
	  font-size: 30rpx;
	  padding-left: 20rpx;
  }
}

.image {
  width: 150rpx;
  height: 150rpx;
  margin-bottom: 20rpx;
  border-radius: 50%;
  overflow: hidden; /* 确保图片不溢出 */
  image {
    width: 100%;
    height: 100%;
  }
}
.introduction{
	color: #555;
	font-size: 30rpx;
	margin-bottom: 20rpx;
}

.box{
	display: flex;
	height: 100rpx;
	justify-content: center;
	align-items: center;
	padding: 20rpx;
	.good,.fans,.interest,.icon{
		height: 80rpx;
		font-size: 28rpx;
		width: 150rpx;
		border-radius: 50rpx;
		font-size: 35rpx;
		color:#0c3ac0;
	}
	
	.icon{
		padding-top: 50rpx;
		margin-left: 60rpx;
	}
}

.menu {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 20rpx 0;
  height: 150rpx;
}


.menu-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 170rpx;
  height: 100%;
  border-radius: 10%;
  .icon{
	  margin-top: 15rpx;
  }
  background-color: rgba(255, 255, 255, 0.8);
}

.icon {
  width: 100rpx;
  height: 60rpx;
  margin-bottom: 10rpx;
}

.text {
  font-size: 28rpx;
  color: #333;
}
.masonry{
	margin-right: 20rpx;
	width:100vw;
	min-height: 1000rpx;
}

.masonry{
  position: relative;
	left: -20rpx;
	background-color: white;
	border-radius: 5%;
	column-count: 2;
	column-gap: 35rpx;
}
.box2 {
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
