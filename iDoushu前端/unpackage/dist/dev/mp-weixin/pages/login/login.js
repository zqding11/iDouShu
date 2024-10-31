"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "login",
  setup(__props) {
    const isLogin = common_vendor.ref(true);
    const Name = common_vendor.ref("");
    const Password = common_vendor.ref("");
    const confirmPassword = common_vendor.ref("");
    const app = common_vendor.getCurrentInstance().appContext.config.globalProperties;
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
        common_vendor.index.showToast({
          title: "请填写用户名和密码",
          icon: "none"
        });
        return;
      }
      try {
        const response = await common_vendor.index.request({
          url: `${app.$baseUrl}user/login`,
          method: "POST",
          data: {
            username: Name.value,
            password: Password.value
          }
        });
        console.log(response);
        if (response) {
          if (response.data) {
            app.$userId = response.data.id;
            console.log(app.$userId);
            connectWebSocket(app.$userId);
            common_vendor.index.reLaunch({
              url: "/pages/index/index"
            });
            clearFields();
          } else {
            common_vendor.index.showToast({
              title: "登录失败，用户名或密码错误",
              icon: "none"
            });
          }
        } else {
          common_vendor.index.showToast({
            title: "请求失败",
            icon: "none"
          });
        }
      } catch (error) {
        common_vendor.index.showToast({
          title: "登录请求失败",
          icon: "none"
        });
        console.error("登录请求错误:", error);
      }
    }
    async function register() {
      if (!Name.value || !Password.value || !confirmPassword.value) {
        common_vendor.index.showToast({
          title: "请完整填写所有信息",
          icon: "none"
        });
        return;
      }
      if (Password.value !== confirmPassword.value) {
        common_vendor.index.showToast({
          title: "两次密码输入不一致",
          icon: "none"
        });
        return;
      }
      try {
        const response = await common_vendor.index.request({
          url: `${app.$baseUrl}user/register`,
          method: "POST",
          data: {
            username: Name.value,
            password: Password.value
          }
        });
        if (response) {
          if (response.data) {
            common_vendor.index.showToast({
              title: "注册成功",
              icon: "success",
              duration: 1500,
              success() {
                setTimeout(() => {
                  isLogin.value = true;
                  clearFields();
                }, 1500);
              }
            });
          } else {
            common_vendor.index.showToast({
              title: data.message || "注册失败",
              icon: "none"
            });
          }
        } else {
          common_vendor.index.showToast({
            title: "请求失败",
            icon: "none"
          });
        }
      } catch (error) {
        common_vendor.index.showToast({
          title: "注册请求失败",
          icon: "none"
        });
        console.error("注册请求错误:", error);
      }
    }
    function connectWebSocket(username) {
      const ws = new WebSocket("ws://localhost:8082/websocket");
      ws.onopen = () => {
        console.log("WebSocket connection established.");
        ws.send(JSON.stringify({ action: "join", username }));
      };
      ws.onmessage = (event) => {
        const data2 = JSON.parse(event.data);
        console.log("Received message:", data2);
        console.log("toid=" + app.$toId);
        console.log("收到" + data2.from);
        if (app.$toId != data2.from) {
          common_vendor.index.showModal({
            title: "New Message",
            content: `收到来自 ${data2.from}的消息: ${data2.message}`,
            showCancel: false
            // 只显示一个确认按钮
          });
        }
      };
      ws.onclose = () => {
        console.log("WebSocket connection closed.");
      };
    }
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.t(isLogin.value ? "登录" : "注册"),
        b: common_vendor.o(($event) => toggleMode(true)),
        c: common_vendor.o(($event) => toggleMode(false)),
        d: Name.value,
        e: common_vendor.o(($event) => Name.value = $event.detail.value),
        f: Password.value,
        g: common_vendor.o(($event) => Password.value = $event.detail.value),
        h: !isLogin.value
      }, !isLogin.value ? {
        i: confirmPassword.value,
        j: common_vendor.o(($event) => confirmPassword.value = $event.detail.value)
      } : {}, {
        k: isLogin.value
      }, isLogin.value ? {
        l: common_vendor.o(login)
      } : {
        m: common_vendor.o(register)
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-e4e4508d"], ["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/pages/login/login.vue"]]);
wx.createPage(MiniProgramPage);
