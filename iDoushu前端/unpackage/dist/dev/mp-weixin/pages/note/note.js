"use strict";
const common_vendor = require("../../common/vendor.js");
if (!Array) {
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  _easycom_uni_icons2();
}
const _easycom_uni_icons = () => "../../uni_modules/uni-icons/components/uni-icons/uni-icons.js";
if (!Math) {
  _easycom_uni_icons();
}
const _sfc_main = {
  __name: "note",
  setup(__props) {
    const mycontent = common_vendor.ref("");
    const userName = common_vendor.ref("");
    const userimage = common_vendor.ref("");
    const formattedTime = common_vendor.ref("");
    const note = common_vendor.ref({
      coverImage: "",
      title: "",
      noteContent: ""
    });
    const commentlist = common_vendor.ref([]);
    const noteId = common_vendor.ref("");
    const { appContext } = common_vendor.getCurrentInstance();
    const baseUrl = appContext.config.globalProperties.$baseUrl;
    const imagelist = common_vendor.ref([]);
    async function fetchImages(nId) {
      try {
        const response = await common_vendor.index.request({
          url: `${baseUrl}image/list/${nId}`,
          method: "GET"
        });
        console.log(response);
        imagelist.value = response.data;
      } catch (error) {
        console.error("获取图片列表失败:", error);
      }
    }
    function submit() {
      console.log(mycontent.value);
      if (mycontent.value) {
        common_vendor.index.request({
          url: `${baseUrl}comment/add`,
          // 评论的添加接口
          method: "POST",
          data: {
            nid: noteId.value,
            // 当前笔记ID
            uid: appContext.config.globalProperties.$userId,
            // 当前用户ID
            content: mycontent.value
          },
          success(res) {
            if (res.statusCode === 200) {
              console.log("评论添加成功");
              mycontent.value = "";
              fetchComments(noteId.value);
            } else {
              console.error("评论添加失败:", res);
            }
          },
          fail(error) {
            console.error("请求失败:", error);
          }
        });
      } else {
        common_vendor.index.showToast({
          title: "评论内容不能为空",
          icon: "null"
        });
      }
    }
    async function fetchNote(id) {
      try {
        const response = await common_vendor.index.request({
          url: `${baseUrl}note/get?id=${id}`,
          method: "GET"
        });
        note.value = response.data;
        formattedTime.value = new Date(note.value.createTime).toLocaleString();
        fetchImages(id);
        await fetchUser(note.value.userId);
        await fetchComments(id);
      } catch (error) {
        console.error("获取笔记失败:", error);
      }
    }
    async function fetchComments(noteId2) {
      try {
        const response = await common_vendor.index.request({
          url: `${baseUrl}comment/list?nid=${noteId2}`,
          method: "GET"
        });
        const comments = response.data;
        for (const comment of comments) {
          const userResponse = await fetchUser(comment.uid);
          comment.icon = userResponse.avatar;
          comment.username = userResponse.nickName;
        }
        commentlist.value = comments;
      } catch (error) {
        console.error("获取评论失败:", error);
      }
    }
    async function fetchUser(userId) {
      try {
        const response = await common_vendor.index.request({
          url: `${baseUrl}user/search?id=${userId}`,
          method: "GET"
        });
        userName.value = response.data.nickName;
        userimage.value = response.data.avatar;
        return response.data;
      } catch (error) {
        console.error("获取用户信息失败:", error);
      }
    }
    common_vendor.onMounted(() => {
      const page = common_vendor.getCurrentInstance().appContext.config.globalProperties.$route;
      console.log(page.query.id);
      noteId.value = String(page.query.id);
      fetchNote(noteId.value);
    });
    return (_ctx, _cache) => {
      return {
        a: userimage.value,
        b: common_vendor.t(userName.value),
        c: common_vendor.p({
          type: "personadd",
          size: "40",
          color: "#ff4f55"
        }),
        d: common_vendor.f(imagelist.value, (image, index, i0) => {
          return {
            a: image.image,
            b: index
          };
        }),
        e: common_vendor.t(note.value.title),
        f: common_vendor.t(note.value.content),
        g: common_vendor.t(_ctx.time),
        h: mycontent.value,
        i: common_vendor.o(($event) => mycontent.value = $event.detail.value),
        j: common_vendor.o(submit),
        k: common_vendor.f(commentlist.value, (item, index, i0) => {
          return {
            a: item.icon,
            b: common_vendor.t(item.username),
            c: common_vendor.t(item.content)
          };
        })
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-f9d84dbe"], ["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/pages/note/note.vue"]]);
wx.createPage(MiniProgramPage);
