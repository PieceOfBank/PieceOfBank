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
            <View className='h-32'></View>
          <Text className='text-2xl'>{name}의 거래내역</Text>
          <Link className='w-80 h-8 rounded-3xl justify-center items-center bg-sky-200 m-4 text-center rounded-3xl font-bold text-2xl' 
            href={{pathname:'/ward/transfer', params:{name:name}}}>송금하기</Link>
          <TransactionList />
        </View>
      );
}

export default careTransaction;