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
        <uni-icons type="chat" size="80rpx" color="#0c3ac0" @click="chat" style="position: relative; top: -10rpx;"></uni-icons>
      </view>
    </view>

    <view class="menu">
      <view class="menu-item" @click="setActiveIcon('mail')">
        <uni-icons :type="activeIcon === 'mail' ? 'mail-open-filled' : 'mail-open'" size="60rpx" class="icon" color="#9acdeb"></uni-icons>
        <view class="text">他的发布</view>
      </view>
      <view class="menu-item" @click="setActiveIcon('heart')">
        <uni-icons :type="activeIcon === 'heart' ? 'heart-filled' : 'heart'" size="60rpx" class="icon" color="#9acdeb"></uni-icons>
        <view class="text">他的点赞</view>
      </view>
      <view class="menu-item" @click="setActiveIcon('star')">
        <uni-icons :type="activeIcon === 'star' ? 'star-filled' : 'star'" size="60rpx" class="icon" color="#9acdeb"></uni-icons>
        <view class="text">他的收藏</view>
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
const app = getCurrentInstance().appContext.config.globalProperties;
const page = app.$route;
const uid = String(page.query.uid); 
app.$toId=uid;
function goToNote(noteId) {
  uni.navigateTo({
    url: `/pages/note/note?id=${noteId}`,
  });
}
onMounted(() => {
  const userId =uid;
  
  uni.request({
      url: `${app.$baseUrl}user/search?id=${userId}`,
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
    url: `${app.$baseUrl}note/mylist?userid=${userId}`,
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
async function chat() {
    const user1Id = app.$userId;
    const user2Id = uid;
	console.log(user1Id);
	console.log(user2Id);	
    try {
        // 发送请求来添加好友并获取fid
        const response = await uni.request({
            url: `${app.$baseUrl}friend/add/${user1Id}/${user2Id}`, // 添加好友的API
            method: 'GET',
        
        });

        if (response.data) {
            // 假设返回的数据中包含fid
            const fid = response.data; // 根据实际情况调整

            // 导航到消息页面并传递fid
            uni.navigateTo({
                url: `/pages/message/message?fid=${fid}`
            });
        } else {
            uni.showToast({
                title: "添加好友失败",
                icon: "none"
            });
        }
    } catch (error) {
        uni.showToast({
            title: "请求失败",
            icon: "none"
        });
        console.error("请求错误:", error);
    }
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


.mybox{
	position: relative;
	left: -20rpx;
	margin-right: 20rpx;
	width:100vw;
	min-height: 1000rpx;
	background-color: white;
	border-radius: 5%;
	column-count: 2;
	column-gap: 1rpx;
}
.item {
  margin-top: 10rpx;
  margin-bottom: 10rpx;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  .item{
	  height: 100%;
	  width: 90%;
  }
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
