"use strict";
const common_vendor = require("../../common/vendor.js");
if (!Array) {
  const _easycom_friend_item2 = common_vendor.resolveComponent("friend_item");
  _easycom_friend_item2();
}
const _easycom_friend_item = () => "../../components/friend_item/friend_item2.js";
if (!Math) {
  _easycom_friend_item();
}
const _sfc_main = {
  __name: "friend",
  setup(__props) {
    function item() {
      console.log("Item clicked");
      common_vendor.index.navigateTo({
        url: "/pages/message/message"
      });
    }
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(item),
        b: common_vendor.p({
          message: "谁把熊宁安放我床上了",
          userimage: "https://th.bing.com/th/id/R.fb6b6cc121f1fd21f78264efc917d9fa?rik=w8jKslrrlRVLyw&riu=http%3a%2f%2fi1.hdslb.com%2fbfs%2farchive%2f1531452dd13f6d56aaf0eeb91b88fc623d2c94ea.jpg&ehk=3QRMqW6TlQ9gFO7gOgPD1lDBEKAH3Vblo9bsRRK%2bEc8%3d&risl=&pid=ImgRaw&r=0"
        }),
        c: common_vendor.o(item),
        d: common_vendor.p({
          username: "祁厅长",
          userimage: "https://so1.360tres.com/t01b702eaaa9424289f.jpg",
          message: "我太想进步了"
        }),
        e: common_vendor.o(item),
        f: common_vendor.p({
          username: "赵德汉",
          message: "我是一分钱不敢花啊!",
          userimage: "https://th.bing.com/th/id/OIP.LEyrCTFWWBh7C8-eR2oL8wHaFg?rs=1&pid=ImgDetMain"
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-b7e6a9a8"], ["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/pages/friend/friend.vue"]]);
wx.createPage(MiniProgramPage);
