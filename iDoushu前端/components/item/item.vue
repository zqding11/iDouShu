<template>
	<view class="box">
		<image :src="image" mode="widthFix"></image>
		<view class="title">{{ title }}</view>
		<view class="user">
			<image :src="userimage" mode="aspectFill" class="user"></image>
			<view class="name">{{ username }}</view>
			<uni-icons
				:type="handupType"
				style="margin-left: 100rpx;"
				@click="toggleHandup"
			></uni-icons>
			<view style="font-size: 20rpx;">{{ good }}</view>
		</view>
	</view>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
	title: {
		type: String,
		default: "丁志强同志视察计算机学院",
	},
	good: {
		type: Number,
		default: 20,
	},
	image: {
		type: String,
		default:
			"https://yuebaimage.oss-rg-china-mainland.aliyuncs.com/6f4f334d69d9482e835157f3674b62b2.jpg",
	},
	userimage: {
		type: String,
		default: "https://p1.ssl.qhimg.com/t01202765ddf451918a.png",
	},
	username: {
		type: String,
		default: "丁志强",
	},
});

// 定义 handupType 状态，初始为 "hand-up"
const handupType = ref("hand-up");

const good = ref(20);


// 切换 handupType 的函数，并根据状态增加或减少 good 值
const toggleHandup = () => {
	if (handupType.value === "hand-up") {
		handupType.value = "hand-up-filled";
		good.value++;
	} else {
		handupType.value = "hand-up";
		good.value--;
	}
};
</script>

<style lang="scss" scoped>
.box {
	width: 370rpx;
	border-radius: 5rpx;
	box-shadow: 0 0 5rpx rgba(0, 0, 0, 0.2);
	background-color: rgba(255, 255, 255, 0.6);
	height: auto;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	padding: 2rpx;
}

.box image {
	width: 98%;
	border-radius: 10rpx;
	margin-left: 2rpx;
}

.title {
	font-size: 32rpx;
	white-space: nowrap;           // 强制文本不换行
	overflow: hidden;              // 隐藏溢出部分
	text-overflow: ellipsis;       // 超出部分显示省略号
}

.user {
	padding: 10rpx;
	display: flex;
	align-items: center;
}

.user image {
	width: 50rpx;
	height: 50rpx;
	border-radius: 50%;
}

.name {
	font-size: 15rpx;
	margin-left: 20rpx;
}
</style>
