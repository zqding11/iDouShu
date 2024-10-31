<template>
	<view class="box">
		<image :src="userimage" mode="aspectFill"></image>
		<view class="text">
			<view class="text1">{{username}}</view>
			<view class="text2">{{message}}</view>
			
		</view>

		<view class="date">
			{{date}}
		</view>
	</view>
</template>

<script setup>
import { ref } from 'vue';
const props = defineProps({
	userimage:{
		type:String,
		default:"https://th.bing.com/th/id/R.5da7e5c735cc95f8f8e3d3bb7e981d7a?rik=koE8E6kqXKbipA&riu=http%3a%2f%2fwww.beihaiting.com%2fuploads%2fallimg%2f150515%2f10723-15051522234J95.jpg&ehk=CTB857uvdo4mBMipOW25WsUo7aaZd7w73ebL%2b7H71jA%3d&risl=&pid=ImgRaw&r=0",
	},
	username:{
		type:String,
		default:"育良书记"
	},
	message:{
		type:String,
		default:"你好"
	},
	date:{
		type:String,
		default:"8月9日"
	}
})
</script>

<style lang="scss" scoped>
.box{
	image{
		height: 100rpx;
		width: 100rpx;
	}
	height: 100rpx;
	display: flex;
	align-items: center;
	background-color: rgb(255, 255, 255,0.8);
	padding-bottom: 10rpx;
	
}
.text{
	padding: 10rpx;
	.text1{
		font-size: 40rpx;
	}
	.text2{
		font-size: 25rpx;
		color: #999999;
	}
}
.date{
	position: relative;
	right: -465rpx;
	top:-25rpx;
	font-size: 15rpx;
	color: #b9b9b9;
}
</style>
