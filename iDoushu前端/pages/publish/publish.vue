<template>
  <view class="container">
    <view class="box">
      <view class="text" style="font-weight: 600; font-size: 38rpx;">new 笔记</view>
      <button @click="finish" :disabled="!imagelist.length">发布</button>
    </view>
    <view class="imagebox">
      <view class="upload">
        <button @click="chooseFile" style="width: 120rpx;">+</button>
        <button @click="uploadFile" :disabled="!filelist.length" style="font-size: 30rpx;width: 120rpx;">上传</button>
      </view>
      <scroll-view scroll-x="true" style="white-space: nowrap; width: 100%; overflow-x: auto;">
        <view class="noteimage" style="display: inline-block;">
          <view class="image" v-for="(item, index) in filelist" :key="index" style="display: inline-block; position: relative;">
            <image v-if="item" :src="item" mode="aspectFill"></image>
            <image src="../../static/delete.png" @click="removeImage(index)" style="position: absolute; top: 0rpx; right: -5rpx; width: 30rpx; height: 30rpx; cursor: pointer;"></image>
          </view>
        </view>
      </scroll-view>
    </view>
    <view class="box2">
      <input type="text" v-model="title" class="title" placeholder="请输入标题" style="font-size: 35rpx; padding-left: 20rpx; padding-top: 10rpx;" />
      <view style="margin-left: 15rpx; color: rgb(0, 0, 0, 0.1);">———————————————————</view>
      <input type="text" v-model="content" class="content" placeholder="请输入正文" style="font-size: 30rpx; padding-left: 20rpx; padding-top: 10rpx;" />
    </view>
  </view>
</template>

<script setup>
import { ref, getCurrentInstance } from 'vue';

const { appContext } = getCurrentInstance();
const baseUrl = appContext.config.globalProperties.$baseUrl;
const userId = appContext.config.globalProperties.$userId;
const filePath = ref("");
const title = ref("");
const content = ref("");
const filelist = ref([]); // 本地图片文件路径
const imagelist = ref([]); // 上传成功的图片链接

function chooseFile() {

  uni.chooseImage({
    count: 9, // 允许选择最多9张
    success: (res) => {
      res.tempFilePaths.forEach(filePath => {
        filelist.value.push(filePath);
		
      });
    },
    fail: (err) => {
      console.log("选择文件失败: ", err);
    },
  });
}

async function uploadFile() {
  for (const filePath of filelist.value) {
    try {
      const uploadFileRes = await uni.uploadFile({
        url: `${baseUrl}upload`,
        filePath,
        name: 'file',
      });
     
      const imageUrl = uploadFileRes.data; // 假设返回的数据中包含图片链接
	  console.log(imageUrl);
      imagelist.value.push(imageUrl);
      
      uni.showToast({
        title: '上传成功',
        icon: 'success',
        duration: 2000,
      });
    } catch (err) {
      console.error('上传失败: ', err);
      uni.showToast({
        title: '上传失败',
        icon: 'error',
        duration: 2000,
      });
    }
  }
}


function removeImage(index) {
  filelist.value.splice(index, 1);
  imagelist.value.splice(index, 1);
}

async function finish() {
  try {
    const noteData = {
      title: title.value,
      noteContent: content.value,
      userId: userId,
      coverImage: imagelist.value[0],
    };
	console.log(imagelist.value[0]);
    const response = await uni.request({
      url: `${baseUrl}note/add`,
      method: 'POST',
      data: noteData,
      header: {
        'Content-Type': 'application/json',
      },
    });
	console.log(response);
    if (response.data && response.data.noteId) {
      const noteId = response.data.noteId;

      // 将图片列表逐一添加到 image 表，关联到 noteId
      for (let index = 0; index < imagelist.value.length; index++) {
        await uni.request({
          url: `${baseUrl}image/add/${noteId}`,
          method: 'POST',
          data: { image: imagelist.value[index], image_index:index },
          header: {
            'Content-Type': 'application/json',
          },
        });
      }

      uni.showToast({
        title: '发布成功',
        icon: 'success',
        duration: 2000,
      });

      // 清空输入框和图片列表
      title.value = '';
      content.value = '';
      filelist.value = [];
      imagelist.value = [];
    } else {
      throw new Error('发布笔记失败');
    }
  } catch (error) {
    console.error('发布笔记失败:', error);
    uni.showToast({
      title: '发布失败',
      icon: 'error',
      duration: 2000,
    });
  }
}

function goBack() {
  uni.navigateBack(); // 返回上一页
}
</script>

<style lang="scss" scoped>
.container {
  background-image: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  height: 100vh;
}

.box {
  display: flex;
  align-items: center;
  background-color: white;
  .text {
    padding-left: 20rpx;
  }
  button {
    border-radius: 5%;
    background-color: rgb(55, 149, 255);
    width: 120rpx;
    height: 60rpx;
    margin-left: 400rpx;
    font-size: 20rpx;
    color: white;
  }
}
.imagebox {
  margin-top: 30rpx;
  display: flex;
  width: 100%;
  height: 260rpx;
  scroll-view {
    position: relative;
    .noteimage {
      .image,
      image {
        width: 200rpx;
        height: 200rpx;
        margin-right: 10rpx;
      }
    }
  }
}
.box2 {
  background-color: white;
  margin: 30rpx;
  border-radius: 5%;
  height: 600rpx;
}
</style>
