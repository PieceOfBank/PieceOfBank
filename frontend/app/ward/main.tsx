import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, TouchableOpacity, Image, FlatList } from 'react-native';
import React, { useState, useEffect } from 'react';
import { useRouter } from 'expo-router';
import * as ScreenOrientation from 'expo-screen-orientation';
import WardListForm from "../../src/ui/components/WardList";
import NowAccount from "../../src/ui/components/NowAccount";
import SmallLogo from "../../src/assets/SmallLogo.png";


interface CareItem {
    id: string;
    title: string;
}

const caregiver = () => {

    const router = useRouter()

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

   return(
    <ImageBackground source={require('../../src/assets/POBbackGround.png')} className="flex-1">
        <SafeAreaView>
            <View className="h-16 bg-blue-400 dark:bg-blue-100 flex-row justify-start items-center">
            <Image source={SmallLogo} className="w-12 h-12" />
            <NowAccount />
            <TouchableOpacity 
                  className='w-24 h-8 m-5 rounded-3xl justify-center bg-gray-500'
                  onPress={() => router.push('/')} 
                  >
                <Text className='text-center text-white'>로그아웃</Text></TouchableOpacity>
            </View>
        </SafeAreaView>
        <View className='flex-row justify-center items-center mt-1'>
            <TouchableOpacity 
            className="w-28 bg-green-800 h-8 rounded-3xl justify-center items-center"
            onPress={() => router.push('/ward/noticeCheck')}>
                <Text className='text-white'>알림함</Text>
            </TouchableOpacity>
        </View>
        <View className='flex-1 items-center justify-center'>
            <WardListForm />
        </View>
    </ImageBackground>

   )
}

export default caregiver;



{/* <View className="w-56 h-56 m-4 bg-white justify-center items-center rounded-3xl">
<Image className="w-36 h-36 bg-teal-400" source={require('../../assets/favicon.png')}></Image>
<View className="h-4"></View>
<TouchableOpacity className='w-28 h-8 rounded-3xl justify-center bg-sky-200' onPress={() => console.log('냠')}>
<Text className='text-center rounded-3xl font-bold'>전체내역</Text></TouchableOpacity>   
</View> */}


// 임시

        {/* <View className="flex-row">
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
                <Text className='text-center rounded-3xl font-bold'>아들</Text></TouchableOpacity>    
            </View>
            <View className="w-56 h-56 m-4 bg-white justify-center items-center rounded-3xl">
                <Image className="w-36 h-36 bg-teal-400" source={require('../../assets/favicon.png')}></Image>
                <View className="h-4"></View>
                <TouchableOpacity className='w-28 h-8 rounded-3xl justify-center bg-sky-200' onPress={() => console.log('냠')}>
                <Text className='text-center rounded-3xl font-bold'>전체 기록</Text></TouchableOpacity>    
            </View>
          </View> */}
            
          {/* <Link className='my-2' href={'/caregiver/transaction'}>테스트중</Link> */}
                {/* <TouchableOpacity className='w-10 h-10 rounded-3xl justify-center bg-sky-200 ml-4 mt-28' onPress={() => nextPage(nowPage)} disabled={(nowPage + 1) * pageNum >= allList.length}> */}
                {/* <TouchableOpacity className='w-10 h-10 rounded-3xl justify-center bg-sky-200 ml-4 mt-28' onPress={() => prevPage(nowPage)} disabled={nowPage === 0}> */}