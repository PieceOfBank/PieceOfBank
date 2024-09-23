import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';

const careTransfer = () => {

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
          <View className='flex-1 flex-row justify-center items-center'>
            <View className="bg-sky-300 basis-2/5 m-2 p-2 h-5/6"><Text>ddssssssssss</Text></View>
            <View className="bg-green-300 basis-2/5 m-2 p-2 h-5/6"><Text>aa</Text></View>
          </View>
        </View>
      );
}

export default careTransfer;