// main.js
import App from './App'

// #ifndef VUE3
import Vue from 'vue'
import './uni.promisify.adaptor'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
  ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
import axios from 'axios'

export function createApp() {
  const app = createSSRApp(App);

  // 设置全局变量
  app.config.globalProperties.$baseUrl = 'http://localhost:8082/';
  app.config.globalProperties.$userId = '8';
  app.config.globalProperties.$toId = '0';
  app.config.globalProperties.$nickname = '丁志强';
  app.config.globalProperties.$WS = null; // 初始化为 null

  // 定义 WebSocket 连接函数
  function connectWebSocket(username) {
    const ws = new WebSocket("ws://localhost:8082/websocket");

    ws.onopen = () => {
      console.log('WebSocket connection established.');
      ws.send(JSON.stringify({ action: "join", username }));
    };

    ws.onmessage = (event) => {
      const data = JSON.parse(event.data);
      console.log('Received message:', data);

      if (app.config.globalProperties.$toId != data.from) {
        uni.showModal({
          title: 'New Message',
          content: `收到来自 ${data.name} 的消息: ${data.message}`,
          showCancel: false,
        });
      }

      // 调用 reload 函数重新加载页面
      reload();
    };

    ws.onclose = () => {
      console.log('WebSocket connection closed.');
    };

    // 将 WebSocket 实例挂载到全局
    app.config.globalProperties.$WS = ws;
  }

  // 将 WebSocket 连接函数挂载到全局属性
  app.config.globalProperties.$connectWebSocket = connectWebSocket;

  // 定义 reload 函数
  function reload() {
    const pages = getCurrentPages();
    const curPage = pages[pages.length - 1];

    // 获取当前页面的路径和参数
    const route = curPage.route;
    const options = curPage.options;
    console.log("Page options:", options);

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

  // 挂载 reload 函数到全局
  app.config.globalProperties.$reload = reload;

  return {
    app
  }
}
// #endif
