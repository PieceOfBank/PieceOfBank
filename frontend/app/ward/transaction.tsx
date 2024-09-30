import { Link, useNavigation, useRouter, useLocalSearchParams } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';
import TransactionList from "../../src/ui/components/TransactionList";
import NowAccount from "../../src/ui/components/NowAccount";

const careTransaction = () => {
    
    const router = useRouter()

    // 카드 리스트에서 전달받은 이름, 계좌번호, 은행
    const {account, bank, name} = useLocalSearchParams()

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
          <Text className='text-xl'>{name}의 거래내역</Text>
          <Link className='w-32 p-2 rounded-3xl justify-center items-center font-bold bg-sky-300 m-2 text-center text-2xl text-white' 
            href={{pathname:'/ward/transfer', params:{nowAccount: account, nowBank: bank, nowName: name}}}>송금하기</Link>
          <TransactionList />
          <View className='h-8'></View>
        </View>
      );
}

export default careTransaction;