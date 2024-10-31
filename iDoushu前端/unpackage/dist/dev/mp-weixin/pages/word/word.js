"use strict";
const common_vendor = require("../../common/vendor.js");
if (!Array) {
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  const _easycom_item2 = common_vendor.resolveComponent("item");
  (_easycom_uni_icons2 + _easycom_item2)();
}
const _easycom_uni_icons = () => "../../uni_modules/uni-icons/components/uni-icons/uni-icons.js";
const _easycom_item = () => "../../components/item/item2.js";
if (!Math) {
  (_easycom_uni_icons + _easycom_item)();
}
const _sfc_main = {
  __name: "word",
  setup(__props) {
    const items = common_vendor.ref([
      { image: "https://tse1-mm.cn.bing.net/th/id/OIP-C.A_3uAao9gpskoDD1vNDJBAAAAA?w=250&h=250&c=8&rs=1&qlt=90&o=6&dpr=1.6rm=2&dpr=1.6&pid=ImgDetMain" },
      { image: "https://th.bing.com/th/id/OIP.xzfpkJdhfQ8AEGsz6fxe2QAAAA?rs=1&pid=ImgDetMain" },
      { image: "https://img2.woyaogexing.com/2020/04/09/5e04cc3e28924bdeab573f6f1e4eb3e0!400x400.jpeg" },
      { image: "https://img2.woyaogexing.com/2020/03/06/e263c902f3fc41f1aff6b2b57a37eac4!400x400.jpeg" }
    ]);
    common_vendor.ref("mail-open-filled");
    common_vendor.ref("heart");
    common_vendor.ref("star");
    const activeIcon = common_vendor.ref("");
    function setting() {
      common_vendor.index.navigateTo({
        url: "/pages/information/information"
      });
    }
    function handleClick(item) {
      common_vendor.index.showToast({
        title: `点击了${item}`,
        icon: "none"
      });
    }
    function setActiveIcon(type) {
      if (activeIcon.value !== type) {
        activeIcon.value = type;
      }
    }
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o((...args) => _ctx.imageupload && _ctx.imageupload(...args)),
        b: common_vendor.o(($event) => handleClick("关注")),
        c: common_vendor.o(($event) => handleClick("粉丝")),
        d: common_vendor.o(($event) => handleClick("获赞")),
        e: common_vendor.o(setting),
        f: common_vendor.p({
          type: "settings",
          size: "60rpx",
          color: "#0c3ac0"
        }),
        g: common_vendor.p({
          type: activeIcon.value === "mail" ? "mail-open-filled" : "mail-open",
          size: "60rpx",
          color: "#9acdeb"
        }),
        h: common_vendor.o(($event) => setActiveIcon("mail")),
        i: common_vendor.p({
          type: activeIcon.value === "heart" ? "heart-filled" : "heart",
          size: "60rpx",
          color: "#9acdeb"
        }),
        j: common_vendor.o(($event) => setActiveIcon("heart")),
        k: common_vendor.p({
          type: activeIcon.value === "star" ? "star-filled" : "star",
          size: "60rpx",
          color: "#9acdeb"
        }),
        l: common_vendor.o(($event) => setActiveIcon("star")),
        m: common_vendor.f(items.value, (item, index, i0) => {
          return {
            a: index,
            b: "ce61a269-4-" + i0,
            c: common_vendor.p({
              image: item.image
            })
          };
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-ce61a269"], ["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/pages/word/word.vue"]]);
wx.createPage(MiniProgramPage);
