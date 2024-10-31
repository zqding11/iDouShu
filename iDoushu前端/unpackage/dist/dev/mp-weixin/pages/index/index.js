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
const pageSize = 10;
const _sfc_main = {
  __name: "index",
  setup(__props) {
    const searchText = common_vendor.ref("");
    const activeCategory = common_vendor.ref(0);
    const categories = common_vendor.ref([{ name: "全部" }, { name: "点赞最多" }, { name: "收藏最多" }]);
    const lists = common_vendor.ref([]);
    const noMoreData = common_vendor.ref(false);
    let nowPage = 1;
    const { appContext } = common_vendor.getCurrentInstance();
    const baseUrl = appContext.config.globalProperties.$baseUrl;
    async function getNoteList() {
      try {
        const sortBy = activeCategory.value === 0 ? "time" : "good";
        const response = await common_vendor.index.request({
          url: `${baseUrl}note/list?type=${sortBy}&page=${nowPage}&size=${pageSize}`,
          method: "GET"
        });
        console.log(response.data);
        return response.data;
      } catch (error) {
        console.error("获取笔记列表失败:", error);
        throw error;
      }
    }
    async function fetchNotes(isRefreshing = false) {
      try {
        const response = await getNoteList();
        const newRecords = response.records;
        const userPromises = newRecords.map(async (note) => {
          const userResponse = await common_vendor.index.request({
            url: `${baseUrl}user/search?id=${note.userId}`,
            method: "GET"
          });
          return {
            ...note,
            username: userResponse.data.nickName,
            userimage: userResponse.data.avatar
          };
        });
        const notesWithUserInfo = await Promise.all(userPromises);
        if (isRefreshing) {
          lists.value = notesWithUserInfo;
        } else {
          lists.value.push(...notesWithUserInfo);
        }
        if (newRecords.length < pageSize) {
          noMoreData.value = true;
        }
      } catch (error) {
        console.error("获取笔记列表失败:", error);
      }
    }
    common_vendor.onMounted(async () => {
      await fetchNotes();
      window.onscroll = function() {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
        const windowHeight = window.innerHeight;
        const scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
        if (scrollTop + windowHeight >= scrollHeight * 0.9 && !noMoreData.value) {
          nowPage += 1;
          fetchNotes();
        }
      };
    });
    function search() {
      common_vendor.index.showToast({
        title: `搜索内容: ${searchText.value}`,
        icon: "none",
        duration: 2e3
      });
    }
    function selectCategory(index) {
      activeCategory.value = index;
      nowPage = 1;
      noMoreData.value = false;
      fetchNotes(true);
    }
    function goToNote(noteId) {
      common_vendor.index.navigateTo({
        url: `/pages/note/note?id=${noteId}`
      });
    }
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: searchText.value,
        b: common_vendor.o(($event) => searchText.value = $event.detail.value),
        c: common_vendor.o(search),
        d: common_vendor.p({
          type: "search",
          mode: "aspectFit"
        }),
        e: common_vendor.f(categories.value, (category, index, i0) => {
          return {
            a: common_vendor.t(category.name),
            b: index,
            c: common_vendor.n({
              active: activeCategory.value === index
            }),
            d: common_vendor.o(($event) => selectCategory(index), index)
          };
        }),
        f: common_vendor.f(lists.value, (list, index, i0) => {
          return {
            a: "1cf27b2a-1-" + i0,
            b: common_vendor.p({
              username: list.username,
              title: list.title,
              image: list.coverImage,
              userimage: list.userimage
            }),
            c: index,
            d: common_vendor.o(($event) => goToNote(list.nid), index)
          };
        }),
        g: noMoreData.value
      }, noMoreData.value ? {} : {}, {
        h: common_vendor.o((...args) => _ctx.onScroll && _ctx.onScroll(...args))
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-1cf27b2a"], ["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/pages/index/index.vue"]]);
wx.createPage(MiniProgramPage);
