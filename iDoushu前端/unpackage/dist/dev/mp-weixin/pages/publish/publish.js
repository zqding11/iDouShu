"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "publish",
  setup(__props) {
    const { appContext } = common_vendor.getCurrentInstance();
    const baseUrl = appContext.config.globalProperties.$baseUrl;
    const userId = appContext.config.globalProperties.$userId;
    common_vendor.ref("");
    const title = common_vendor.ref("");
    const content = common_vendor.ref("");
    const filelist = common_vendor.ref([]);
    const imagelist = common_vendor.ref([]);
    function chooseFile() {
      common_vendor.index.chooseImage({
        count: 9,
        // 允许选择最多9张
        success: (res) => {
          res.tempFilePaths.forEach((filePath) => {
            filelist.value.push(filePath);
          });
        },
        fail: (err) => {
          console.log("选择文件失败: ", err);
        }
      });
    }
    async function uploadFile() {
      for (const filePath of filelist.value) {
        try {
          const uploadFileRes = await common_vendor.index.uploadFile({
            url: `${baseUrl}upload`,
            filePath,
            name: "file"
          });
          const imageUrl = uploadFileRes.data;
          console.log(imageUrl);
          imagelist.value.push(imageUrl);
          common_vendor.index.showToast({
            title: "上传成功",
            icon: "success",
            duration: 2e3
          });
        } catch (err) {
          console.error("上传失败: ", err);
          common_vendor.index.showToast({
            title: "上传失败",
            icon: "error",
            duration: 2e3
          });
        }
      }
    }
    function removeImage(index) {
      filelist.value.splice(index, 1);
      imagelist.value.splice(index, 1);
    }
    async function finish() {
      try {
        const noteData = {
          title: title.value,
          noteContent: content.value,
          userId,
          coverImage: imagelist.value[0]
        };
        console.log(imagelist.value[0]);
        const response = await common_vendor.index.request({
          url: `${baseUrl}note/add`,
          method: "POST",
          data: noteData,
          header: {
            "Content-Type": "application/json"
          }
        });
        console.log(response);
        if (response.data && response.data.noteId) {
          const noteId = response.data.noteId;
          for (let index = 0; index < imagelist.value.length; index++) {
            await common_vendor.index.request({
              url: `${baseUrl}image/add/${noteId}`,
              method: "POST",
              data: { image: imagelist.value[index], image_index: index },
              header: {
                "Content-Type": "application/json"
              }
            });
          }
          common_vendor.index.showToast({
            title: "发布成功",
            icon: "success",
            duration: 2e3
          });
          title.value = "";
          content.value = "";
          filelist.value = [];
          imagelist.value = [];
        } else {
          throw new Error("发布笔记失败");
        }
      } catch (error) {
        console.error("发布笔记失败:", error);
        common_vendor.index.showToast({
          title: "发布失败",
          icon: "error",
          duration: 2e3
        });
      }
    }
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(finish),
        b: !imagelist.value.length,
        c: common_vendor.o(chooseFile),
        d: common_vendor.o(uploadFile),
        e: !filelist.value.length,
        f: common_vendor.f(filelist.value, (item, index, i0) => {
          return common_vendor.e({
            a: item
          }, item ? {
            b: item
          } : {}, {
            c: common_vendor.o(($event) => removeImage(index), index),
            d: index
          });
        }),
        g: title.value,
        h: common_vendor.o(($event) => title.value = $event.detail.value),
        i: content.value,
        j: common_vendor.o(($event) => content.value = $event.detail.value)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-bfce3555"], ["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/pages/publish/publish.vue"]]);
wx.createPage(MiniProgramPage);
