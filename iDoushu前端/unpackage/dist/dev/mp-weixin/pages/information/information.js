"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "information",
  setup(__props) {
    const app = common_vendor.getCurrentInstance().appContext.config.globalProperties;
    const avatarUrl = common_vendor.ref("https://your-avatar-url.com/avatar.png");
    const selectedImage = common_vendor.ref(null);
    const name = common_vendor.ref("");
    const account = common_vendor.ref(app.$userId);
    const bio = common_vendor.ref("");
    const gender = common_vendor.ref("");
    const birthday = common_vendor.ref("");
    const job = common_vendor.ref("");
    const region = common_vendor.ref("");
    const school = common_vendor.ref("");
    const originalData = common_vendor.ref({});
    const isModified = common_vendor.ref(false);
    const selectAvatar = () => {
      common_vendor.index.chooseImage({
        count: 1,
        success: (res) => {
          selectedImage.value = res.tempFilePaths[0];
          avatarUrl.value = selectedImage.value;
          isModified.value = true;
        }
      });
    };
    const uploadAvatar = async () => {
      if (selectedImage.value) {
        try {
          const uploadFileRes = await common_vendor.index.uploadFile({
            url: `${app.$baseUrl}upload`,
            // 上传图片的接口
            filePath: selectedImage.value,
            name: "file"
          });
          const data = uploadFileRes.data;
          console.log(data);
          avatarUrl.value = data;
        } catch (error) {
          common_vendor.index.showToast({
            title: "图片上传失败",
            icon: "none"
          });
          console.error("图片上传错误:", error);
        }
      }
    };
    const fetchUserData = async () => {
      try {
        const response = await common_vendor.index.request({
          url: `${app.$baseUrl}user/search?id=${account.value}`,
          method: "GET"
        });
        if (response) {
          const data = response.data;
          originalData.value = data;
          name.value = data.nickName;
          bio.value = data.introduction;
          gender.value = data.sex;
          avatarUrl.value = data.avatar;
        } else {
          common_vendor.index.showToast({
            title: "获取用户信息失败",
            icon: "none"
          });
        }
      } catch (error) {
        common_vendor.index.showToast({
          title: "请求失败",
          icon: "none"
        });
        console.error("获取用户信息错误:", error);
      }
    };
    const checkModification = () => {
      isModified.value = name.value !== originalData.value.nickName || bio.value !== originalData.value.introduction || gender.value !== originalData.value.sex || job.value !== originalData.value.job || region.value !== originalData.value.region || school.value !== originalData.value.school || avatarUrl.value !== originalData.value.avatar;
    };
    const updateProfile = async () => {
      await uploadAvatar();
      try {
        const response = await common_vendor.index.request({
          url: `${app.$baseUrl}user/update`,
          method: "PUT",
          data: {
            id: account.value,
            nickName: name.value,
            introduction: bio.value,
            sex: gender.value,
            avatar: avatarUrl.value
            // 头像链接
          }
        });
        if (response) {
          common_vendor.index.showToast({
            title: "修改成功",
            icon: "success"
          });
          originalData.value = {
            ...originalData.value,
            nickName: name.value,
            introduction: bio.value,
            sex: gender.value,
            avatar: avatarUrl.value
            // 更新头像 URL
          };
          isModified.value = false;
        } else {
          common_vendor.index.showToast({
            title: "修改失败",
            icon: "none"
          });
        }
      } catch (error) {
        common_vendor.index.showToast({
          title: "更新请求失败",
          icon: "none"
        });
        console.error("更新个人资料错误:", error);
      }
    };
    common_vendor.onMounted(() => {
      fetchUserData();
    });
    common_vendor.watch([name, bio, gender, job, region, school, avatarUrl], checkModification);
    return (_ctx, _cache) => {
      return {
        a: avatarUrl.value,
        b: common_vendor.o(selectAvatar),
        c: originalData.value.nickName,
        d: name.value,
        e: common_vendor.o(($event) => name.value = $event.detail.value),
        f: account.value,
        g: account.value,
        h: common_vendor.o(($event) => account.value = $event.detail.value),
        i: originalData.value.introduction,
        j: bio.value,
        k: common_vendor.o(($event) => bio.value = $event.detail.value),
        l: originalData.value.sex,
        m: gender.value,
        n: common_vendor.o(($event) => gender.value = $event.detail.value),
        o: birthday.value,
        p: common_vendor.o(($event) => birthday.value = $event.detail.value),
        q: originalData.value.job,
        r: job.value,
        s: common_vendor.o(($event) => job.value = $event.detail.value),
        t: !isModified.value,
        v: common_vendor.o(updateProfile)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-17c83bce"], ["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/pages/information/information.vue"]]);
wx.createPage(MiniProgramPage);
