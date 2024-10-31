"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "message",
  setup(__props) {
    const appContext = common_vendor.getCurrentInstance().appContext;
    const myname = appContext.config.globalProperties.$userId;
    const messagelist = common_vendor.ref([]);
    const newMessage = common_vendor.ref("");
    const scrollTopHeight = common_vendor.ref(0);
    const ws = new WebSocket(`ws://localhost:8082/websocket`);
    ws.onopen = () => {
      console.log("WebSocket connection opened");
    };
    ws.onmessage = async (event) => {
      const data = JSON.parse(event.data);
      console.log("Message from server:", data);
      appContext.config.globalProperties.$toId = 4;
      console.log(appContext.config.globalProperties.$toId.$toId);
      console.log(data.from);
      if (appContext.config.globalProperties.$toId.$toId != data.from) {
        common_vendor.index.showModal({
          title: "New Message",
          content: `收到来自 ${data.from}的消息: ${data.message}`,
          showCancel: false
          // 只显示一个确认按钮
        });
      } else {
        await updateMessageList();
      }
    };
    common_vendor.onMounted(async () => {
      await updateMessageList();
    });
    async function updateMessageList() {
      try {
        const friendId = "1";
        const response = await common_vendor.axios.get(`http://localhost:8082/message/list/${friendId}`);
        const messages = response.data.records;
        const updatedMessages = await Promise.all(
          messages.map(async (message) => {
            console.log("111:" + message.userId);
            console.log("222" + myname);
            const userResponse = await common_vendor.axios.get(`http://localhost:8082/user/search?id=${message.userId}`);
            const user = userResponse.data;
            return {
              ...message,
              avatar: user.avatar,
              // 用户头像
              username: user.username,
              // 用户名
              mymessage: message.userId == myname ? 1 : 0
              // 判断消息是自己发的还是对方发的
            };
          })
        );
        messagelist.value = updatedMessages;
        common_vendor.nextTick$1(() => {
          scrollTopHeight.value = messagelist.value.length * 100;
        });
      } catch (error) {
        console.error("Failed to load messages:", error);
      }
    }
    function send() {
      if (newMessage.value.trim() !== "") {
        const message = {
          action: "send",
          userid: myname,
          touserid: "4",
          // 接收用户ID，替换为实际 ID
          name: appContext.config.globalProperties.$nickname,
          mymessage: 1,
          friendid: "1",
          // 示例 friendId
          content: newMessage.value
        };
        messagelist.value.push(message);
        ws.send(JSON.stringify(message));
        newMessage.value = "";
        common_vendor.nextTick$1(() => {
          scrollTopHeight.value = messagelist.value.length * 100;
        });
      }
    }
    return (_ctx, _cache) => {
      return {
        a: common_vendor.f(messagelist.value, (message, index, i0) => {
          return common_vendor.e({
            a: message.mymessage === 0
          }, message.mymessage === 0 ? {
            b: message.avatar,
            c: common_vendor.t(message.content)
          } : {}, {
            d: message.mymessage === 1
          }, message.mymessage === 1 ? {
            e: common_vendor.t(message.content),
            f: message.avatar
          } : {}, {
            g: index,
            h: message.mymessage === 1 ? 1 : "",
            i: message.mymessage === 0 ? 1 : ""
          });
        }),
        b: scrollTopHeight.value,
        c: newMessage.value,
        d: common_vendor.o(($event) => newMessage.value = $event.detail.value),
        e: common_vendor.o(send)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-4c1b26cf"], ["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/pages/message/message.vue"]]);
wx.createPage(MiniProgramPage);
