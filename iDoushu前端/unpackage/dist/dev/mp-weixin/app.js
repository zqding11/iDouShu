"use strict";
Object.defineProperty(exports, Symbol.toStringTag, { value: "Module" });
const common_vendor = require("./common/vendor.js");
if (!Math) {
  "./pages/login/login.js";
  "./pages/index/index.js";
  "./pages/word/word.js";
  "./pages/publish/publish.js";
  "./pages/friend/friend.js";
  "./pages/message/message.js";
  "./components/item/item.js";
  "./pages/video/video.js";
  "./pages/note/note.js";
  "./components/friend_item/friend_item.js";
  "./pages/ces/ces.js";
  "./pages/information/information.js";
}
const _sfc_main = {
  onLaunch: function() {
    console.log("App Launch");
  },
  onShow: function() {
    console.log("App Show");
  },
  onHide: function() {
    console.log("App Hide");
  }
};
const App = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/App.vue"]]);
function createApp() {
  const app = common_vendor.createSSRApp(App);
  app.config.globalProperties.$baseUrl = "http://localhost:8082/";
  app.config.globalProperties.$userId = "8";
  app.config.globalProperties.$toId = "0";
  app.config.globalProperties.$nickname = "丁志强";
  app.config.globalProperties.$WS = null;
  return {
    app
  };
}
createApp().app.mount("#app");
exports.createApp = createApp;
