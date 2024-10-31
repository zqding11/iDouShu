<template>
  <view class="container">
    <friend_item 
      v-for="friend in friends" 
      :key="friend.userId" 
      @click="item(friend)" 
      :username="friend.username" 
      :userimage="friend.avatarUrl" 
      :message="friend.message">
    </friend_item>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getCurrentInstance } from 'vue';
const app = getCurrentInstance().appContext.config.globalProperties;
const friends = ref([]);

onMounted(async () => {
	app.$toId=0;
    try {
        // 第一次请求：获取好友列表
        const friendResponse = await fetch(`${app.$baseUrl}friend/list/${app.$userId}`);
        const friendData = await friendResponse.json();
		console.log(friendData);
        // 第二次请求：根据 user2Id 获取用户头像
        const avatarPromises = friendData.map(async (friend) => {
            const userResponse = await fetch(`${app.$baseUrl}user/search?id=${friend.user2Id}`);
            const userData = await userResponse.json();
            return {
                userId: friend.user2Id,
                message: friend.message,
                avatarUrl: userData.avatar, // 假设用户数据中有 avatar 字段
                username: userData.nickName ,// 假设用户数据中有 username 字段
				fid:friend.fid
            };
        });

        // 等待所有头像请求完成
        const avatarData = await Promise.all(avatarPromises);
        friends.value = avatarData; // 更新好友列表
    } catch (error) {
        console.error('获取好友列表失败:', error);
    }
});

function item(friend) {
    console.log(friend.userId);
	
	app.$toId=friend.userId;

    uni.navigateTo({
        url: `/pages/message/message?fid=${friend.fid}` 
    });
}

</script>

<style lang="scss" scoped>
.container {
  background-image: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 2rpx;
  min-height: 100vh;
  box-sizing: border-box; 
}
</style>
