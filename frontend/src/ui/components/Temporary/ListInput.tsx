import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet, SafeAreaView, KeyboardAvoidingView } from 'react-native';
import { useRouter, Link, useLocalSearchParams } from 'expo-router';
import React, { useState } from 'react';
import CancelButton from '../CancelButton';
import Toast from "react-native-toast-message";

interface existsInfo {
    onChange: (inputBalance:string) => void;
    name?: string
}

const ListInput = () => { 

    const [mainConnect, setMainConnect] = useState('');

    const router = useRouter()

    // 임시 관계 요청자
    interface ConnectItem {
        id: string;
        name: string;
    }

    // 임시 관계 요청 목록
    const connectList : ConnectItem [] = [{id:'1', name:'딸'}, {id:'2', name:'영숙'}, {id:'3', name:'미숙'}]

    // 요청 수락
    const checkAccept = (index:number, name:string) => {
        for (let i = 0; i < connectList.length; i++){
        if (i == index){
            const familyName : string = connectList[i]['name']
            // 요청 보내기
            Toast.show({
                type: 'success',
                text1: `요청을 허가했습니다`,
                text2: '계좌 이체가 되었습니다'
              })
            }
        }
        }

    // 요청 거절
    const checkReject = (index:number, name:string) => {
        for (let i = 0; i < connectList.length; i++){
        if (i == index){
            const familyName : string = connectList[i]['name']
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
        <View className='justify-center items-center h-72'>
            <ScrollView className='flex-1'>
            {connectList.map((list, index) => (
                <View key={index} className='w-64 h-12 p-2 m-2 flex-row bg-gray-300 rounded-3xl justify-between'>
                    <View className='flex-1'>
                        <Text className='text-center justify-center items-center font-bold text-lg'>{list.name}</Text>
                    </View>
                    <View className='flex-row mx-2'>
                        <TouchableOpacity className='w-16 h-8 mx-1 rounded justify-center rounded-2xl bg-sky-500'
                        onPress={() => router.push('/family copy/DirectoryObject')} 
                        >
                        <Text className='text-white text-center font-bold '>설정</Text></TouchableOpacity>
                        {/* <TouchableOpacity className='w-16 h-8 mx-1 rounded justify-center bg-red-500'
                        onPress={() => checkReject(index, list.name)}
                        >
                        <Text className='text-white text-center font-bold'>삭제</Text></TouchableOpacity> */}
                    </View>
                </View>
            ))}            
          </ScrollView>
            <View className='flex-row'>
            <TouchableOpacity 
            className='m-2 py-2 px-4 bg-red-400 rounded-3xl bg-sky-500'
            onPress={() => router.push('/family copy/DirectoryObject')} 
            >
              <Text className='text-white text-center font-bold'>+ 보호자 송금 대상 추가</Text></TouchableOpacity>
                <CancelButton />
            </View>
        </View>
        </View>

      );



}

export default ListInput