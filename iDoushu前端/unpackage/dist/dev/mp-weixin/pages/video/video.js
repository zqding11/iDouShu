"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "video",
  setup(__props) {
    common_vendor.ref("/static/祁厅长.mp4");
    const videoTitle = common_vendor.ref("祁厅长的经典发言");
    const uploaderName = common_vendor.ref("用户A");
    const uploaderAvatar = common_vendor.ref("/static/avatar.jpg");
    const liked = common_vendor.ref(false);
    const collected = common_vendor.ref(false);
    const likeCount = common_vendor.ref(120);
    const collectCount = common_vendor.ref(30);
    const commentCount = common_vendor.ref(50);
    const handleLike = () => {
      liked.value = !liked.value;
      liked.value ? likeCount.value++ : likeCount.value--;
    };
    const handleCollect = () => {
      collected.value = !collected.value;
      collected.value ? collectCount.value++ : collectCount.value--;
    };
    const handleComment = () => {
      alert("评论功能开发中...");
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.o(handleLike),
        b: uploaderAvatar.value,
        c: common_vendor.t(videoTitle.value),
        d: common_vendor.t(uploaderName.value),
        e: liked.value
      }, liked.value ? {} : {}, {
        f: common_vendor.t(likeCount.value),
        g: common_vendor.o(handleLike),
        h: collected.value
      }, collected.value ? {} : {}, {
        i: common_vendor.t(collectCount.value),
        j: common_vendor.o(handleCollect),
        k: common_vendor.t(commentCount.value),
        l: common_vendor.o(handleComment)
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-06518e47"], ["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/pages/video/video.vue"]]);
wx.createPage(MiniProgramPage);
