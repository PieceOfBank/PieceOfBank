import { Link, useNavigation, useRouter, useLocalSearchParams } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';
import TransactionList from "../../src/ui/components/TransactionList";

const careTransaction = () => {
    
    const router = useRouter()

    const {name} = useLocalSearchParams()

    // 화면 가로고정
    useEffect(() => {
        const screenChange = async() => {
            await ScreenOrientation.lockAsync(ScreenOrientation.OrientationLock.LANDSCAPE);
            console.log(name)
        };
        screenChange();
        return () => {
            ScreenOrientation.unlockAsync()
        }
        },[]);

    return (
        <View className='flex-1 justify-center items-center'>
          <Text className='text-2xl'>{name}의 거래내역</Text>
          <Link className='w-48 p-4 rounded-3xl justify-center items-center font-bold bg-sky-300 m-4 text-center text-2xl text-white' 
            href={{pathname:'/ward/transfer', params:{name:name}}}>송금하기</Link>
          <TransactionList />
          <View className='h-8'></View>
        </View>
      );
}

export default careTransaction;