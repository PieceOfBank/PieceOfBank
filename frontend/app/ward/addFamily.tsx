import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, TouchableOpacity } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';

const familyAdd = () => {

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
        <View className='justify-center items-center h-40'>
            <Text className='text-3xl my-1'>관계 요청</Text>
            <View className='w-80 h-12 bg-sky-300 flex-row items-center justify-between'>
                <View className='mx-2'>
                    <Text>이름</Text>
                </View>
                <View className='flex-row mx-2'>
                    <TouchableOpacity className='w-16 h-8 mx-1 rounded justify-center bg-sky-500'
                    // onPress={emailCheck} disabled={disabled}
                    >
                    <Text className='text-white text-center font-bold'>수락</Text></TouchableOpacity>
                    <TouchableOpacity className='w-16 h-8 mx-1 rounded justify-center bg-red-500'
                    // onPress={emailCheck} disabled={disabled}
                    >
                    <Text className='text-white text-center font-bold'>거절</Text></TouchableOpacity>
                </View>
            </View>
        </View>
      );
}

export default familyAdd;