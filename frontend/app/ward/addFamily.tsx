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
import { notifyList, subscriptionCreate, subscriptionApproval, subscriptionName } from "../../src/services/api";
import AsyncStorage from '@react-native-async-storage/async-storage';


const familyAdd = () => {


    // 요청리스트
    const [connectList, setConnectList] = useState<ConnectItem[]>([]);

    // 요청 이름 리스트
    const [nameList, setNameList] = useState<number[]>([]);

    // 요청 이름 리스트
    const [go, setGo] = useState<string[]>([]);

    // 화면 가로고정
    useEffect(() => {
        const screenChange = async() => {
            await ScreenOrientation.lockAsync(ScreenOrientation.OrientationLock.LANDSCAPE);
        };
        screenChange();

        
        // 알림 전체 조회
        const notifyView = async() => {
            try{
                const keyGet = await AsyncStorage.getItem("myKey");

                const myKey = JSON.parse(keyGet!)
          
                const data = {
                // 나중에 userkey 어떻게 받아와서 넣어야 할까요? authorization 으로 되는지 확인하기
                'receiverKey':myKey 
                }
                const response = await notifyList(data);
                setConnectList(response.data)
                console.log("관계 List")
                console.log(response.data)
                try{
                    setNameList(prevList => {
                        const items  = []
                        for (let i=0; i<response.data.length; i++){
                            // 이름 요청
                            const abc = subscriptionName(response.data[i]['notificationId'])
                            items.push(response.data[i]['notificationId']);
                            console.log('!!')
                            console.log(abc)
                            console.log(items)
                        }
                        return [...prevList, ...items]
                    })
                    console.log()
                }catch(error){
                    console.log(error)
                }
            }
            catch(error){
                console.log(`에러: ${error}`)
            }
            }

            

        notifyView()

        return () => {
            ScreenOrientation.unlockAsync()
        }
        },[]);

    // 보호자 관계 수락
    const [mainConnect, setMainConnect] = useState('');

    const router = useRouter()

    // 임시 관계 요청자
    interface ConnectItem {
        created: string, 
        notificationId: number, 
        notificationStatus: string, 
        notificationType: string, 
        readAt: null, 
        receiverKey: string, 
        senderKey: string
    }

    const subCreate = async() => {

    }
/*
[{"created": "2024-10-07T17:32:38.286974", "notificationId": 11, "notificationStatus": "UNREAD", "notificationType": "구독  
신청 알림", "readAt": null, "receiverKey": "c86cb677-4e29-4ede-9b0d-b159101a8cc5", "senderKey": "0573c1ee-0953-493c-a015-8905a524bfbe"}]
*/
    // 임시 관계 요청 목록
    // const connectList : ConnectItem [] = [{id:'1', name:'영숙'}, {id:'2', name:'미숙'}, {id:'3', name:'정숙'}]
    // 요청 수락
    const checkAccept = (index:number, name:string) => {
        for (let i = 0; i < connectList.length; i++){
        if (i == index){
            const userKey : string = connectList[i]['receiverKey']
            const targetKey : string = connectList[i]['senderKey']
            // 요청 보내기
            

            const subCreate = async() => {
                try{
                // 나중에 userkey 어떻게 받아와서 넣어야 할까요? authorization 으로 되는지 확인하기
                
                
                // const JsonData = {
                //     "userKey": userKey,
                //     "targetKey": targetKey
                // }
                // const response = await subscriptionCreate(JsonData);
                // console.log(response)
                } catch(error){
                    console.log(error)
                }
            }
            subCreate()


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
            const familyName : string = connectList[i]['created']
            // 요청 보내기
            Toast.show({
                type: 'info',
                text1: `${name}님과의 관계를 거절했습니다`,
                text2: '관계 설정 후 주요 서비스를 이용하실 수 있습니다'
              })
            }
        }
        }


    if (connectList == null || connectList == undefined)
        return null;
    
    return (
        <ImageBackground source={require('../../src/assets/POBbackGround.png')}
        className="flex-1">
        <Header />
        <View className='justify-center items-center h-4/5'>
              <Text className='text-2xl text-white font-bold my-2'>관계 맺기</Text>
            
            <ScrollView className='flex-1'>
                    {
                        connectList.map((list, index) => (
                    <View key={index} className='w-80 h-12 p-2 m-2 flex-row bg-white justify-between rounded-3xl'>
                        <View className='m-1 ml-3'>
                            <Text>{list.created}</Text>
                        </View>
                        <View className='flex-row mx-2'>
                            <TouchableOpacity className='w-16 h-8 mx-1 rounded-3xl justify-center bg-sky-500'
                            onPress={()=> checkAccept(index, list.created)} 
                            >
                            <Text className='text-white text-center font-bold'>수락</Text></TouchableOpacity>
                            <TouchableOpacity className='w-16 h-8 mx-1 rounded-3xl justify-center bg-red-500'
                            onPress={() => checkReject(index, list.created)}
                            >
                            <Text className='text-white text-center font-bold'>거절</Text></TouchableOpacity>
                        </View>
                            </View>      
                        ))
                    }          
          </ScrollView>
          <CancelButton />

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

        </ImageBackground>
      );
}

export default familyAdd;