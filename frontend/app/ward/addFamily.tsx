import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, Alert, TouchableOpacity, Button, ScrollView } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';
import Checkbox from 'expo-checkbox';
import { useRouter } from 'expo-router';
import Toast from "react-native-toast-message";


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

     // 보호자 관계 수락
    const [mainConnect, setMainConnect] = useState('');

    const router = useRouter()

    // 임시 관계 요청자
    interface ConnectItem {
        id: string;
        name: string;
    }

    // 임시 관계 요청 목록
    const connectList : ConnectItem [] = [{id:'1', name:'영숙'}, {id:'2', name:'미숙'}, {id:'3', name:'정숙'}]

    // 요청 수락
    const checkAccept = (index:number, name:string) => {
        for (let i = 0; i < connectList.length; i++){
        if (i == index){
            const familyName : string = connectList[i]['name']
            // 요청 보내기
            Toast.show({
                type: 'success',
                text1: `${name}님과의 관계 설정이 완료되었습니다.`,
                text2: '앞으로 주요 서비스를 이용하실 수 있습니다'
              })
            router.push('/ward/main')
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
                text1: `${name}님과의 관계를 거절했습니다`,
                text2: '관계 설정 후 주요 서비스를 이용하실 수 있습니다'
              })
            }
        }
        }


    return (
        <View className='justify-center items-center h-80'>
            <Text className='text-3xl my-1'>관계 요청</Text>
            <ScrollView className='flex-1'>
            {connectList.map((list, index) => (
                <View key={index} className='w-80 h-12 p-2 m-2 flex-row bg-white justify-between'>
                    <View className='mx-2'>
                        <Text>{list.name}</Text>
                    </View>
                    <View className='flex-row mx-2'>
                        <TouchableOpacity className='w-16 h-8 mx-1 rounded justify-center bg-sky-500'
                        onPress={()=> checkAccept(index, list.name)} 
                        >
                        <Text className='text-white text-center font-bold'>수락</Text></TouchableOpacity>
                        <TouchableOpacity className='w-16 h-8 mx-1 rounded justify-center bg-red-500'
                        onPress={() => checkReject(index, list.name)}
                        >
                        <Text className='text-white text-center font-bold'>거절</Text></TouchableOpacity>
                    </View>
                </View>
            ))}
            {/* <Button title="등록" onPress={()=>console.log(mainConnect)}></Button> */}
            
          </ScrollView>
            {/* <View className='w-80 h-12 bg-sky-300 flex-row items-center justify-between'>
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
            </View> */}

        </View>
      );
}

export default familyAdd;