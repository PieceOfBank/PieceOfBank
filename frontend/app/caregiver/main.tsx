import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, TouchableOpacity, Image } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';

const caregiver = () => {

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
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
          <Text>보호자 메인</Text>
          <View className="flex-row">
            <View className="w-56 h-56 m-4 bg-white justify-center items-center rounded-3xl">
                <Image className="w-36 h-36 bg-teal-400" source={require('../../assets/favicon.png')}></Image>
                <View className="h-4"></View>
                <TouchableOpacity className='w-28 h-8 rounded-3xl justify-center bg-sky-200' onPress={() => console.log('냠')}>
                <Text className='text-center rounded-3xl font-bold'>딸</Text></TouchableOpacity>    
            </View>
            <View className="w-56 h-56 m-4 bg-white justify-center items-center rounded-3xl">
                <Image className="w-36 h-36 bg-teal-400" source={require('../../assets/favicon.png')}></Image>
                <View className="h-4"></View>
                <TouchableOpacity className='w-28 h-8 rounded-3xl justify-center bg-sky-200' onPress={() => console.log('냠')}>
                <Text className='text-center rounded-3xl font-bold'>딸</Text></TouchableOpacity>    
            </View>
            <View className="w-56 h-56 m-4 bg-white justify-center items-center rounded-3xl">
                <Image className="w-36 h-36 bg-teal-400" source={require('../../assets/favicon.png')}></Image>
                <View className="h-4"></View>
                <TouchableOpacity className='w-28 h-8 rounded-3xl justify-center bg-sky-200' onPress={() => console.log('냠')}>
                <Text className='text-center rounded-3xl font-bold'>딸</Text></TouchableOpacity>    
            </View>
          </View>
            
          <Link className='my-2' href={'/caregiver/transaction'}>테스트중</Link>
        </View>
      );
}

export default caregiver;