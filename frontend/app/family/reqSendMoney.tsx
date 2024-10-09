import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, Alert, TouchableOpacity, Button, ScrollView } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';
import Checkbox from 'expo-checkbox';
import { useRouter } from 'expo-router';
import Toast from "react-native-toast-message";
import CancelButton from "../../src/ui/components/CancelButton";
import Header from "../../src/ui/components/Header";

const ReqSendMoney = () => {
    const [mainConnect, setMainConnect] = useState('');

    const router = useRouter()

    // 임시 
    interface ConnectItem {
        notificationId: number,
        senderKey: string,
        receiverKey: string,
        notificationType: string,
        created: string,
        readAt: string,
        notificationStatus: string
    }

    // 임시 목록
    const connectList : ConnectItem [] = 
        [
            {
                notificationId: 0,
                senderKey: "key1",
                receiverKey: "key",
                notificationType: "string",
                created: "2024-10-06T10:03:01.328Z",
                readAt: "2024-10-06T10:03:01.328Z",
                notificationStatus: "UNREAD"
              },
            {
                notificationId: 1,
                senderKey: "key2",
                receiverKey: "key",
                notificationType: "string",
                created: "2024-10-06T10:03:01.328Z",
                readAt: "2024-10-06T10:03:01.328Z",
                notificationStatus: "UNREAD"
              },
            {
                notificationId: 2,
                senderKey: "key3",
                receiverKey: "key",
                notificationType: "string",
                created: "2024-10-06T10:03:01.328Z",
                readAt: "2024-10-06T10:03:01.328Z",
                notificationStatus: "UNREAD"
              },
]

    // 요청 수락
    const checkAccept = (index:number, name:string) => {
        for (let i = 0; i < connectList.length; i++){
        if (i == index){
            const familyName : string = connectList[i]['senderKey']
            // 요청 보내기
            Toast.show({
                type: 'success',
                text1: `요청을 허가했습니다`,
                text2: '계좌 이체가 완료되었습니다'
              })
            }
        }
        }

    // 요청 거절
    const checkReject = (index:number, name:string) => {
        for (let i = 0; i < connectList.length; i++){
        if (i == index){
            const familyName : string = connectList[i]['senderKey']
            // 요청 보내기
            Toast.show({
                type: 'info',
                text1: `요청을 거부했습니다`,
                text2: '계좌 이체가 거부되었습니다'
              })
            }
        }
        }


    return (
        <View className='flex-1'>
            <Header />
            <View className='justify-center items-center bg-gray-300 rounded-3xl mt-12 p-2 mx-20 mb-5'>
                <Text className='text-xl text-center font-semibold'>알림 내역</Text>
            </View>
        <View className='justify-center items-center h-3/4'>
            <ScrollView className='flex-1'>
            {connectList.map((list, index) => (
                <View key={index} className='w-80 p-2 m-2 flex-row bg-gray-300 rounded-3xl h-24 items-center justify-between'>
                    <View className='mx-2'>
                        <Text className='font-bold'>일자 : {list.created.slice(0,4)}년
                        {list.created.slice(5,7)}월
                        {list.created.slice(8,10)}일
                        {list.created.slice(11,13)}시
                        {list.created.slice(14,16)}분
                        </Text>
                        <Text className='font-bold'>대상 : {list.senderKey}</Text>
                        {/* <Text className='font-bold'>금액 : {list.money}</Text> */}
                    </View>
                    <View className=' mx-2'>
                        <TouchableOpacity className='w-16 h-8 mx-1 rounded-3xl justify-center bg-sky-500'
                        onPress={()=> checkAccept(index, list.senderKey)} 
                        >
                        <Text className='text-white text-center font-bold'>승인</Text></TouchableOpacity>
                        <TouchableOpacity className='w-16 h-8 mx-1 rounded-3xl justify-center bg-red-400'
                        onPress={() => checkReject(index, list.senderKey)}
                        >
                        <Text className='text-white text-center font-bold'>거부</Text></TouchableOpacity>
                    </View>
                </View>
            ))}        
                <View className='flex-row justify-center'>
                    <CancelButton />
                </View>    
          </ScrollView>

        </View>
        </View>

      );
}

export default ReqSendMoney;