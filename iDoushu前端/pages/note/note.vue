<template>
	<view class="box" @click="homepage(itemid)" >
		<image :src="userimage" mode="as" class="avatar"></image>
		<view class="name">{{userName}}</view>
	</view>
	
	<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000">
	  <swiper-item v-for="(image, index) in imagelist" :key="index">
	    <view class="swiper-item" >
	      <image :src="image.image" mode="aspectFill"></image>
	    </view>
	  </swiper-item>
	</swiper>

	
	<view class="title">
		{{note.title}}
	</view>
	<view class="content">
		{{note.content}}
	</view>
	<view class="time" style="font-size: 22rpx; padding-left: 10rpx; color: rgb(0, 0, 0,0.2);">{{time}}</view>
	<view style="margin-left: 30rpx;color:rgb(0,0,0,0.1);" >————————————————————</view>
	
	<view class="box2">
		<image :src="myimage" mode="aspectFill" class="wordimage"></image>
		<view class="input" >
			<input type="text" v-model="mycontent" placeholder="  快来发表评论吧~"/>
			<button @click="submit">发布</button>
		</view>		
	</view>
	
	<view class="comment">
		
		<view class="comment-item"  v-for="(item,index) in commentlist" key="index">
			<image :src="item.icon"  mode="aspectFill" style="width: 100rpx;height: 100rpx; border-radius: 50%;"></image>
			<view class="smallbox" @click="homepage(item.uid)">
				<view class="name">{{item.username}}</view>
				<view class="content">{{item.content}}</view>
			</view>
		</view>
	</view>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getCurrentInstance } from 'vue';

const mycontent = ref("");
const userName = ref('');
const userimage = ref('');
const itemid = ref("");
const myname = ref("");
const myimage = ref("");
const formattedTime = ref('');
const note = ref({
  coverImage: '',
  title: '',
  noteContent: ''
});
const commentlist = ref([]);
const noteId = ref("");
const { appContext } = getCurrentInstance();
const baseUrl = appContext.config.globalProperties.$baseUrl;
const imagelist = ref([]);

function homepage(id){
	if(id==appContext.config.globalProperties.$userId){
		uni.switchTab({
			url:"/pages/word/word"
		})
	}
	else{
		uni.navigateTo({
		url:`/pages/homepage/homepage?uid=${id}`
	})
	}
	
	
}
// 请求图片列表
async function fetchImages(nId) {
  try {
    const response = await uni.request({
      url: `${baseUrl}image/list/${nId}`,
      method: 'GET',
    });
	
    imagelist.value = response.data;
  } catch (error) {
    console.error('获取图片列表失败:', error);
  }
}

function submit() {
	console.log(mycontent.value);
  if (mycontent.value) {
    uni.request({
      url: `${baseUrl}comment/add`, // 评论的添加接口
      method: 'POST',
      data: {
        nid: noteId.value,  // 当前笔记ID
        uid: appContext.config.globalProperties.$userId,  // 当前用户ID
        content: mycontent.value
      },
      success(res) {
        if (res.statusCode === 200) {
          console.log("评论添加成功");
          mycontent.value = ''; // 清空输入框
          fetchComments(noteId.value); // 重新获取评论列表
        } else {
          console.error("评论添加失败:", res);
        }
      },
      fail(error) {
        console.error("请求失败:", error);
      }
    });
  } else {
	  uni.showToast({
	  	title:"评论内容不能为空",
		icon:"null"
	  })
    
  }
}
// 获取笔记的函数
async function fetchNote(id) {
  try {
    const response = await uni.request({
      url: `${baseUrl}note/get?id=${id}`,
      method: 'GET',
    });
    
    // 处理笔记数据
    note.value = response.data;
    // 格式化时间
    formattedTime.value = new Date(note.value.createTime).toLocaleString();
    // 请求用户信息
	console.log("11111");
	itemid.value = note.value.userId;
    const userResponse =await fetchUser(note.value.userId);
	console.log(userResponse);
	userName.value = userResponse.nickName;
	userimage.value = userResponse.avatar;
	const userResponse1 =await fetchUser(appContext.config.globalProperties.$userId);
	myimage.value = userResponse1.avatar;
	myname.value = userResponse1.nickName;
	await fetchImages(id);
	await fetchComments(id);
  } catch (error) {
    console.error('获取笔记失败:', error);
  }
}
async function fetchComments(noteId) {
  try {
    const response = await uni.request({
      url: `${baseUrl}comment/list?nid=${noteId}`,
      method: 'GET',
    });
    const comments = response.data;
    for (const comment of comments) {
      const userResponse = await fetchUser(comment.uid);

      comment.icon = userResponse.avatar;
      comment.username = userResponse.nickName;
    }
    commentlist.value = comments;
  } catch (error) {
    console.error('获取评论失败:', error);
  }
}
// 获取用户信息
async function fetchUser(userId) {
  try {
    const response = await uni.request({
      url: `${baseUrl}user/search?id=${userId}`,
      method: 'GET',
    });
    
	return response.data;
  } catch (error) {
    console.error('获取用户信息失败:', error);
  }
}

// 组件挂载时请求数据
onMounted(() => {
  const page = getCurrentInstance().appContext.config.globalProperties.$route;
  console.log(page.query.id);
  noteId.value =String(page.query.id) ;
  fetchNote(noteId.value);
});
</script>

<style lang="scss" scoped>
.box{
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 10rpx;
	height: 100rpx;
	.avatar{
		width: 100rpx;
		height: 100rpx;
		position: absolute;
		left: 10rpx;
		border-radius: 50%;
		background-color: black;
		margin-right:40rpx;
	}
	.name{
		position: absolute;
		left: 150rpx;
		
	}
	.focus{
		position: absolute;
		right: 20rpx;

	}
	
	
}
swiper,swiper-item,.swiper-item,image{
	height: 800rpx;
	width: 100%;
	
}
.title{
	padding: 10rpx;
	font-size: 40rpx;
	font-weight: 549;
	margin-left: 20rpx;
}
.content{
	font-size: 32rpx;
	font-weight: 500;
	padding: 10rpx;
	margin-left: 20rpx;
}
.box2{
	display: flex;
	align-items: center;
	padding-top: 20rpx;
	.wordimage{
	width: 100rpx;
	height: 100rpx;
	border-radius: 50%;
	margin: 10rpx;
	margin-left: 85rpx;
	
}
	.input{
		display: flex;
		button{
			background-color: #ace0f9;
			
		}
	}
	.input,input{
		background-color: rgb(0,0,0, 0.02);
		height:80rpx;
		border-radius: 5%;
		width: 480rpx;
		input{
		padding: 5rpx;
	}
	}
	
}
.comment{
	position: relative;
	
	.comment-item{
		
		padding: 10rpx;
		display: flex;
		box-shadow: 0 0 5rpx rgba(0, 0, 0, 0.1);
	}
	.smallbox{
		.name{
		margin-left: 30rpx;
		font-size: 28rpx;
		color: rgb(0,0,0, 0.3);
	}
	
	}
	
}

</style>
