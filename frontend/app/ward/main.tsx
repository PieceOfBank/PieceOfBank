import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, TouchableOpacity, Image, FlatList } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';
import WardListForm from "../../src/ui/components/WardList";
import NowAccount from "../../src/ui/components/NowAccount";


interface CareItem {
    id: string;
    title: string;
}

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

   return(
    <View className='flex-1 justify-center items-center mx-1'>
        <NowAccount />
        <WardListForm />
    </View>
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
