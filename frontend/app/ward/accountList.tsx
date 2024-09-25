import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';

const careAccountList = () => {

    // 화면 가로고정
    useEffect(() => {
        const screenChange = async() => {
            await ScreenOrientation.lockAsync(ScreenOrientation.OrientationLock.LANDSCAPE);
        };
        screenChange();
        return () => {
            ScreenOrientation.unlockAsync()
        }
        },[]);
    return (
        <View className='flex-1 justify-center items-center'>
        <Text className='text-3xl my-1'>전체 계좌</Text>
        <View className='w-80 h-10 p-2 flex-row bg-sky-300'>
            <Text>국민은행</Text>
            <Text>123-456-7890</Text>
          
        </View>
      </View>
      );
}

export default careAccountList;