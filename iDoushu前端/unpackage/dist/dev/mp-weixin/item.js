"use strict";
const common_vendor = require("./common/vendor.js");
const _sfc_main = {
  __name: "item",
  props: {
    title: {
      type: String,
      default: "丁志强同志视察计算机学院"
    },
    // image: {
    // 	type: String,
    // 	default:
    // 		"https://ts2.cn.mm.bing.net/th?id=OIP.L61KEJ6b9lk7PR0u3BwDdAHaKe&w=213&h=300&c=10&rs=1&qlt=99&bgcl=fffffe&r=0&o=6&dpr=1.6&pid=MultiSMRSV2Source",
    // },
    image: {
      type: String,
      default: "https://yuebaimage.oss-rg-china-mainland.aliyuncs.com/6f4f334d69d9482e835157f3674b62b2.jpg"
    },
    userimage: {
      type: String,
      default: "https://p1.ssl.qhimg.com/t01202765ddf451918a.png"
    },
    username: {
      type: String,
      default: "丁志强"
    }
  },
  setup(__props) {
    return (_ctx, _cache) => {
      return {
        a: __props.image,
        b: common_vendor.t(__props.title),
        c: __props.userimage,
        d: common_vendor.t(__props.username)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-d25a932c"], ["__file", "C:/Users/86182/Documents/HBuilderProjects/iDoushu/components/item/item.vue"]]);
exports.MiniProgramPage = MiniProgramPage;
