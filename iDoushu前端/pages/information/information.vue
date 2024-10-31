<template>
  <view class="container">
    <!-- 返回按钮和标题 -->
    <view class="header">
		<uni-icons type="back" @click="goBack"></uni-icons>
      <view class="title">编辑资料</view>
    </view>

    <!-- 头像 -->
    <view class="avatar-section">
      <image class="avatar" :src="avatarUrl"  @click="selectAvatar"/>
    </view>

    <!-- 信息列表 -->
    <view class="info-list">
      <view class="info-item">
        <view class="label">名字</view>
        <input class="input" v-model="name" :placeholder="originalData.nickName" />
      </view>
      <view class="info-item">
        <view class="label">id号</view>
        <input class="input" v-model="account" :placeholder="account" disabled />
      </view>
      <view class="info-item">
        <view class="label">简介</view>
        <input class="input" v-model="bio" :placeholder="originalData.introduction" />
      </view>
      <view class="info-item">
        <view class="label">性别</view>
        <input class="input" v-model="gender" :placeholder="originalData.sex" />
      </view>

    </view>

    <!-- 确认按钮 -->
    <view class="confirm-button">
      <button :disabled="!isModified" @click="updateProfile">确认修改</button>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted, watch, getCurrentInstance } from 'vue';
const app = getCurrentInstance().appContext.config.globalProperties;
const avatarUrl = ref("https://your-avatar-url.com/avatar.png"); // 头像 URL
const selectedImage = ref(null); // 存储选择的图片文件
const name = ref("");
const account = ref(app.$userId); // id不允许修改
const bio = ref("");
const gender = ref("");
const birthday = ref("");
const job = ref("");
const region = ref("");
const school = ref("");
const originalData = ref({}); // 存储原始数据以进行比较
const isModified = ref(false); // 标记是否有修改

const goBack = () => {
  uni.switchTab({
  	url:"/pages/word/word"
  })
};

const selectAvatar = () => {
  // 选择图片
  uni.chooseImage({
    count: 1,
    success: (res) => {
      selectedImage.value = res.tempFilePaths[0];
      avatarUrl.value = selectedImage.value; // 显示选中的图片
      isModified.value = true; // 标记有修改
    },
  });
};

const uploadAvatar = async () => {
  if (selectedImage.value) {
    try {
      const uploadFileRes = await uni.uploadFile({
        url: `${app.$baseUrl}upload`, // 上传图片的接口
        filePath: selectedImage.value,
        name: 'file',
      });
      const data = uploadFileRes.data; // 返回的数据
	  console.log(data);
      avatarUrl.value = data; // 更新头像 URL
    } catch (error) {
      uni.showToast({
        title: "图片上传失败",
        icon: "none"
      });
      console.error("图片上传错误:", error);
    }
  }
};

const fetchUserData = async () => {
  try {
    const response = await uni.request({
      url: `${app.$baseUrl}user/search?id=${account.value}`,
      method: 'GET'
    });

    if (response) {
      const data = response.data; // 获取数据
      originalData.value = data;
      name.value = data.nickName;
      bio.value = data.introduction;
      gender.value = data.sex;
      avatarUrl.value = data.avatar; // 设置头像 URL
    } else {
      uni.showToast({
        title: "获取用户信息失败",
        icon: "none"
      });
    }
  } catch (error) {
    uni.showToast({
      title: "请求失败",
      icon: "none"
    });
    console.error("获取用户信息错误:", error);
  }
};

const checkModification = () => {
  isModified.value =
    name.value !== originalData.value.nickName ||
    bio.value !== originalData.value.introduction ||
    gender.value !== originalData.value.sex ||
    job.value !== originalData.value.job ||
    region.value !== originalData.value.region ||
    school.value !== originalData.value.school ||
    avatarUrl.value !== originalData.value.avatar;
};

const updateProfile = async () => {
  // 先上传头像（如果选择了图片）
  await uploadAvatar();

  try {
    const response = await uni.request({
      url: `${app.$baseUrl}user/update`,
      method: 'PUT',
      data: {
        id: account.value, 
        nickName: name.value,
        introduction: bio.value,
        sex: gender.value,
        avatar: avatarUrl.value // 头像链接
      }
    });

    if (response) {
      uni.showToast({
        title: "修改成功",
        icon: "success"
      });
      originalData.value = { 
        ...originalData.value,
        nickName: name.value,
        introduction: bio.value,
        sex: gender.value,
        avatar: avatarUrl.value, // 更新头像 URL
      };
      isModified.value = false; 
	  app.$reload();
	  uni.switchTab({
	  	url:"/pages/word/word"
	  });
    } else {
      uni.showToast({
        title: "修改失败",
        icon: "none"
      });
    }
  } catch (error) {
    uni.showToast({
      title: "更新请求失败",
      icon: "none"
    });
    console.error("更新个人资料错误:", error);
  }
};

// 组件挂载时获取用户数据
onMounted(() => {
  fetchUserData();
});

// 监视输入变化以检查修改
watch([name, bio, gender, job, region, school, avatarUrl], checkModification);
</script>

<style lang="scss" scoped>
.container {
  padding: 20px;
}

.header {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
}

.back-button {
  font-size: 24px;
  cursor: pointer;
}

.title {
  font-size: 20px;
  font-weight: bold;
  margin-left: 10px;
}

.avatar-section {
  display: flex;
  justify-content: center;
  position: relative;
  margin: 20px 0;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background-color: #f0f0f0;
}

.camera-icon {
  position: absolute;
  right: 15px;
  bottom: 10px;
  font-size: 20px;
  background-color: #fff;
  border-radius: 50%;
  padding: 5px;
}

.info-list {
  margin-top: 20px;
}

.info-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}

.label {
  color: #333;
  width: 80px;
}

.input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 16px;
  color: #333;
}

.confirm-button {
  margin-top: 20px;
  text-align: center;
}

.confirm-button button {
	display: flex;
	justify-content: center;
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  height: 130rpx;
  border-radius: 5px;
  cursor: pointer;
}

.confirm-button button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
