import { Link } from "expo-router";
import { View, Image, Text, ImageBackground, TextInput, SafeAreaView, Alert, TouchableOpacity, Button, ScrollView } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';
import Checkbox from 'expo-checkbox';
import { useRouter } from 'expo-router';
import Toast from "react-native-toast-message";
import smallLogo from "../../src/assets/SmallLogo.png";
import Header from "../../src/ui/components/Header";
import CancelButton from "../../src/ui/components/CancelButton";

const noticeCheck = () => {

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


    const router = useRouter()

    // 임시 알림
    interface ConnectItem {
        id: string;
        result: string;
        name: string;
        answer: boolean;
        date: string;
        money: string;
    }

    // 임시 알림
    const connectList : ConnectItem [] = [
        {id:'1', result:'승인 완료', name:'미숙', answer:true, date:'24-09-01', money:'3000000'}, 
        {id:'2', result:'승인 취소', name:'정숙', answer:false, date:'24-09-03', money:'5000000'}, 
        {id:'3', result:'승인 완료', name:'숙희', answer:true, date:'24-09-05', money:'1000000'}, ]




    return (
        <ImageBackground source={require('../../src/assets/POBbackGround.png')}
        className="flex-1">
        <Header />
        <View className='justify-center items-center h-4/5'>
              <Text className='text-2xl text-white font-bold my-2'>승인 결과</Text>
            <ScrollView className='flex-1'>
                {connectList.map((list, index) => (
                    <View key={index} className={`${list.answer ? 'bg-sky-200' : 'bg-red-200'} w-80 h-12 p-2 m-2 flex-row justify-between rounded-3xl`}>
                        <View className='m-1 ml-3 flex-row'>
                            <Text className='font-semibold mr-4'>{list.result}</Text>
                            <Text className='font-semibold mr-4'>{list.name}</Text>
                            <Text className='font-semibold mr-4'>{list.date}</Text>
                            <Text className='font-semibold mr-4'>{list.money}원</Text>
                        </View>
                        {/* <View className='flex-row mx-2'>
                            <TouchableOpacity className='w-16 h-8 mx-1 rounded-3xl justify-center bg-sky-500'
                            onPress={()=> checkAccept(index, list.name)} 
                            >
                            <Text className='text-white text-center font-bold'>수락</Text></TouchableOpacity>
                            <TouchableOpacity className='w-16 h-8 mx-1 rounded-3xl justify-center bg-red-500'
                            onPress={() => checkReject(index, list.name)}
                            >
                            <Text className='text-white text-center font-bold'>거절</Text></TouchableOpacity>
                        </View> */}
                    </View>
                ))}          
          </ScrollView>
          <CancelButton />

        </View>

        </ImageBackground>
      );
}

export default noticeCheck;