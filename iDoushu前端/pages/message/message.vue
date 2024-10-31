<template>
  <view class="container">
    <view class="chat-container" ref="chatContainer">
      <scroll-view scroll-y="true" :scroll-top="scrollTopHeight" style="height: 95vh;" scroll-with-animation="true">
        <view v-for="(message, index) in messagelist" :key="index" class="message-row" :class="{ 'mine': message.mymessage === 1, 'theirs': message.mymessage === 0 }">
          <view v-if="message.mymessage === 0" class="message">
            <image class="avatar" :src="favatar" />
            <view class="message-content">{{ message.content }}</view>
          </view>
          <view v-if="message.mymessage === 1" class="message my-message">
            <view class="message-content"> {{ message.content }}</view>
            <image class="avatar" :src="myavatar" />
          </view>
        </view>
      </scroll-view>
    </view>
    <view class="send_box">
      <input type="text" v-model="newMessage" placeholder="请输入..." class="send_input" />
      <button class="send_button" @click="send">发送</button>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue';
import { getCurrentInstance } from 'vue';
import axios from 'axios';

const appContext = getCurrentInstance().appContext;
const myname = appContext.config.globalProperties.$userId; // 当前用户ID
const myavatar = ref("");
const favatar = ref("");
const messagelist = ref([]);
const newMessage = ref('');
const scrollTopHeight = ref(0);
const page = getCurrentInstance().appContext.config.globalProperties.$route;
const friendId = String(page.query.fid); 
const toUserId = appContext.config.globalProperties.$toId;
// WebSocket初始化
const ws = new WebSocket(`ws://localhost:8082/websocket`);
const mynickname = ref("");
ws.onopen = () => {
  console.log('WebSocket connection opened');
};

// 接收WebSocket消息
ws.onmessage = async (event) => {
  const data = JSON.parse(event.data);
  console.log('Message from server:', data);
  // 弹出确认框
  await updateMessageList();
  console.log(appContext.config.globalProperties.$toId);
  console.log(data.from);
  
  if(appContext.config.globalProperties.$toId!=data.from){
  	uni.showModal({
          title: 'New Message',
          content: `收到来自 ${data.from}的消息: ${data.message}`,
          showCancel: false, // 只显示一个确认按钮
          
      });
  }
  
};

// 页面加载时请求消息列表
onMounted(async () => {
  await fetchUserAvatars(); // 获取头像
  await updateMessageList();//获取消息
});

async function fetchUserAvatars() {
  try {
    // 获取当前用户头像
    const userResponse = await axios.get(`${appContext.config.globalProperties.$baseUrl}user/search?id=${myname}`);
    myavatar.value = userResponse.data.avatar; // 存储当前用户头像
	mynickname.value = userResponse.data.nickName;
	console.log(mynickname.value);
    // 获取对话用户头像
    
	console.log(toUserId);
    const friendResponse = await axios.get(`${appContext.config.globalProperties.$baseUrl}user/search?id=${toUserId}`);
    favatar.value = friendResponse.data.avatar; // 存储对方用户头像

  } catch (error) {
    console.error('Failed to load user avatars:', error);
  }
}
async function updateMessageList() {
  try {
    
	
    const response = await axios.get(`${appContext.config.globalProperties.$baseUrl}message/list/${friendId}`);
    
    console.log(response.data); // 打印整个响应数据以进行调试

    const messages = response.data.records || []; // 提供默认值

    // 获取用户信息并添加到消息列表
    const updatedMessages = await Promise.all(
      messages.map(async (message) => {
        const userResponse = await axios.get(`${appContext.config.globalProperties.$baseUrl.$baseUrl}user/search?id=${message.userId}`);
        const user = userResponse.data;

        return {
          ...message,
          avatar: user.avatar, // 用户头像
          username: user.username, // 用户名
          mymessage: message.userId == myname ? 1 : 0 // 判断消息是自己发的还是对方发的
        };
      })
    );

    // 更新消息列表
    messagelist.value = updatedMessages;
	
    // 自动滚动到底部
    nextTick(() => {
      scrollTopHeight.value = messagelist.value.length * 100;
    });
  } catch (error) {
    console.error('Failed to load messages:', error);
  }
}

// 发送消息
function send() {
  if (newMessage.value.trim() !== '') {
    const message = {
      action: 'send',
      userid: myname,
      touserid: appContext.config.globalProperties.$toId,
      name: mynickname.value,
      mymessage: 1,
      friendid:friendId ,
      content: newMessage.value,
    };

    messagelist.value.push(message);
    ws.send(JSON.stringify(message));
    newMessage.value = '';

    // 自动滚动到底部
    nextTick(() => {
      scrollTopHeight.value = messagelist.value.length * 100;
    });
  }
}

</script>

<style lang="scss" scoped>
.container {
  background-image: linear-gradient(to top, #fed6e3 0%, #a8edea 100%);
  padding: 2rpx;
  min-height: 100vh;
  box-sizing: border-box; 
  z-index: 0;
}

.chat-container {
  height: 95vh;
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.message-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;

  &.mine {
    justify-content: flex-end;
  }

  &.theirs {
    justify-content: flex-start;
  }
}

.message {
  display: flex;
  align-items: center;
}

.message-content {
  background-color: #f1f1f1;
  padding: 10px;
  border-radius: 10px;
  max-width: 70%;
  word-wrap: break-word;
}

.my-message .message-content {
  background-color: #a8e6cf;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-left: 10px;
  margin-right: 10px;
}

.send_box {
  display: flex;
  height: 6vh;
  width: 98vw;
  background-color: #fff;
  border: 1px solid #f1f1f1;
  border-radius: 10rpx;
  justify-content: center;
  align-items: center;

  .send_input {
    width: 80vw;
    height: 60rpx;
    padding: 5rpx;
    border: 1px solid #f1f1f1;
  }

  .send_button {
    height: 65rpx;
    text-align: center;
    font-size: 30rpx;
    background-color: #41b87a;
    color: white;
  }
}
</style>
