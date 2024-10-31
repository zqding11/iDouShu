<template>
	<view class="background">
		<view class="login" style="font-weight: 550; font-size: 50rpx; font-style: inherit;position:absolute; top: 150rpx;">欢迎{{ isLogin ? '登录' : '注册' }}</view>
	</view>
	<view class="box">
		<view class="switch">
			<view class="login-switch" @click="toggleMode(true)">登录</view>
			<view class="register-switch" @click="toggleMode(false)">注册</view>
		</view>
		<view class="input-group">
			<image src="../../static/user.png" class="icon"></image>
			<input type="text" v-model="Name" placeholder="用户名" class="input" />
		</view>
		<view class="input-group">
			<image src="../../static/password.png" class="icon"></image>
			<input type="password" v-model="Password" placeholder="密码" class="input" />
		</view>
		<view v-if="!isLogin" class="input-group">
			<image src="../../static/password.png" class="icon"></image>
			<input type="password" v-model="confirmPassword" placeholder="确认密码" class="input" />
		</view>
		<button class="login-button" v-if="isLogin" @click="login">登录</button>
		<button class="register-button" v-else @click="register">注册</button>
	</view>
</template>

<script setup>
import { ref, getCurrentInstance } from 'vue';

const isLogin = ref(true);
const Name = ref("");
const Password = ref("");
const confirmPassword = ref("");

// 获取当前应用实例以访问全局配置
const app = getCurrentInstance().appContext.config.globalProperties;

// 切换登录和注册模式
function toggleMode(mode) {
  isLogin.value = mode;
  clearFields();
}

function clearFields() {
  Name.value = "";
  Password.value = "";
  confirmPassword.value = "";
}

async function login() {
  if (!Name.value || !Password.value) {
    uni.showToast({
      title: "请填写用户名和密码",
      icon: "none"
    });
    return;
  }

  try {
    const response = await uni.request({
      url: `${app.$baseUrl}user/login`,
      method: 'POST',
      data: {
        username: Name.value,
        password: Password.value
      }
    });
	
    // 检查请求是否成功
    console.log(response);
    if (response) {
      if (response.data) { // 确保 data 不为空
	  app.$userId=response.data.id;
	  console.log(app.$userId);
	  
	  connectWebSocket(app.$userId);
        uni.reLaunch({
          url: '/pages/index/index'
        });
        clearFields();
      } else {
        uni.showToast({
          title: "登录失败，用户名或密码错误",
          icon: "none"
        });
      }
    } else {
      uni.showToast({
        title: "请求失败",
        icon: "none"
      });
    }
  } catch (error) {
    uni.showToast({
      title: "登录请求失败",
      icon: "none"
    });
    console.error("登录请求错误:", error);
  }
}

async function register() {
  if (!Name.value || !Password.value || !confirmPassword.value) {
    uni.showToast({
      title: "请完整填写所有信息",
      icon: "none"
    });
    return;
  }

  if (Password.value !== confirmPassword.value) {
    uni.showToast({
      title: "两次密码输入不一致",
      icon: "none"
    });
    return;
  }

  try {
    const response = await uni.request({
      url: `${app.$baseUrl}user/register`,
      method: 'POST',
      data: {
        username: Name.value,
        password: Password.value
      }
    });

    // 检查请求是否成功
    if (response) {

      if (response.data) { // 确保 data.success 存在
        uni.showToast({
          title: "注册成功",
          icon: "success",
          duration: 1500,
          success() {
            setTimeout(() => {
              isLogin.value = true; // 注册成功后切换到登录界面
              clearFields();
            }, 1500);
          }
        });
      } else {
        uni.showToast({
          title: data.message || "注册失败",
          icon: "none"
        });
      }
    } else {
      uni.showToast({
        title: "请求失败",
        icon: "none"
      });
    }
  } catch (error) {
    uni.showToast({
      title: "注册请求失败",
      icon: "none"
    });
    console.error("注册请求错误:", error);
  }
}
function connectWebSocket(username) {
    const ws = new WebSocket("ws://localhost:8082/websocket");

    ws.onopen = () => {
        console.log('WebSocket connection established.');
        // 发送用户加入消息
        ws.send(JSON.stringify({ action: "join", username }));
    };

    ws.onmessage = (event) => {
        const data = JSON.parse(event.data);
        console.log('Received message:', data);
		console.log("toid="+app.$toId);
		console.log("收到"+data.from);
		if(app.$toId!=data.from){
			uni.showModal({
		        title: 'New Message',
		        content: `收到来自 ${data.name}的消息: ${data.message}`,
		        showCancel: false, // 只显示一个确认按钮
		        
		    });
		}
    // 使用 uni.reLaunch 重新加载当前页面
    reload();

		
		
    };

    ws.onclose = () => {
        console.log('WebSocket connection closed.');
    };
}
function reload() {
    const pages = getCurrentPages();
    const curPage = pages[pages.length - 1];

    // 获取当前页面的路径和参数
    const route = curPage.route;
    const options = curPage.options; // 获取页面的参数
	console.log("22222"+options);
    // 拼接参数为查询字符串
    const params = Object.keys(options)
        .map(key => `${key}=${options[key]}`)
        .join('&');

    // 重新加载页面并带上参数
    uni.reLaunch({
        url: `/${route}?${params}`,
        success() {
            console.log('页面重新加载成功');
        },
        fail(error) {
            console.error('页面重新加载失败:', error);
        }
    });
}
</script>


<style lang="scss" scoped>
.background {
	background-image: linear-gradient(to top, #fff1eb 0%, #ace0f9 100%);
	width: 100%;
	height: 100vh;
	border-bottom-left-radius: 40%;
	border-bottom-right-radius: 40%;
	display: flex;
	justify-content: center;
	align-items: center;
	box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2); /* 增加阴影感 */
	position: relative;
}

.login {
	font-weight: 700; /* 加粗字体 */
	color: #333; /* 深色调字体 */
	text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1); /* 添加轻微文字阴影 */
}

.switch {
	display: flex;
	justify-content: space-between; /* 两个按钮居中分布 */
	align-items: center;
	padding: 0 20rpx;
	margin-bottom: 50rpx;

	.login-switch, .register-switch {
		font-size: 35rpx;
		width: 50%;
		text-align: center;
		padding: 20rpx 0;
		cursor: pointer;
		transition: background-color 0.3s, transform 0.2s; /* 动画效果 */
		border-radius: 20rpx; /* 圆角 */
	}

	.login-switch {
		background-color: rgba(153, 205, 235, 0.5);
	}

	.register-switch {
		background-color: rgba(153, 205, 235, 0.5);
	}

	.login-switch:hover, .register-switch:hover {
		background-color: rgba(153, 205, 235, 0.9);
		transform: scale(1.2); /* 放大效果 */
	}
}

.box {
	display: flex;
	flex-direction: column;
	background-color: #fff;
	position: absolute;
	width: 90%;
	height: 1000rpx;
	top: 300rpx;
	border-radius: 30rpx; /* 圆角边框 */
	box-shadow: 0 8px 40px rgba(0, 0, 0, 0.2); /* 强烈的阴影 */
	padding: 40rpx;
}

.input-group {
	display: flex;
	align-items: center;
	margin-bottom: 50rpx;
	border-bottom: 1px solid #ddd;
	padding: 20rpx 0;
	background-color: rgba(240, 240, 240, 0.5); /* 半透明背景 */
	border-radius: 15rpx;
}

.icon {
	width: 50rpx;
	height: 50rpx;
	margin-right: 20rpx;
	opacity: 0.7;
}

.input {
	flex: 1;
	font-size: 34rpx;
	padding: 15rpx;
	border: none;
	outline: none;
	background-color: transparent;
}

.login-button, .register-button {
	width: 100%;
	padding: 25rpx;
	height: 80rpx;
	display: flex;
	justify-content: center;
	align-items: center;
	border-radius: 50rpx; /* 更圆滑的按钮 */
	font-size: 36rpx;
	color: #fff;
	margin-top: 30rpx;
	transition: all 0.3s ease; /* 平滑过渡效果 */
}

.login-button {
	background-color: rgba(153, 205, 235, 0.9);
}

.register-button {
	background-color: rgba(153, 205, 235, 0.9);
}

.login-button:hover, .register-button:hover {
	opacity: 0.9;
	transform: translateY(-5px); /* 按钮悬浮时上移效果 */
}

.login-button:active, .register-button:active {
	opacity: 0.8;
	transform: translateY(0); /* 点击后恢复原位 */
}
</style>
