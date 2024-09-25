import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, TouchableOpacity } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';
import WardAllAccount from "../../src/ui/components/WardAllAccount";

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
            <WardAllAccount />
        </View>
      );
}

export default careAccountList;