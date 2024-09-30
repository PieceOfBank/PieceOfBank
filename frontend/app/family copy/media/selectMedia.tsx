import { Link, useRouter, useLocalSearchParams } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, Alert, Button, TouchableOpacity } from 'react-native';
import React, { useState, useEffect } from 'react';
import Toast from "react-native-toast-message";


const selectMedia = () => {
    return(
        <View className='flex-1 justify-center items-center'>
            <Text>어떻게 보낼까요?</Text>
            <Link className='w-32 p-2 rounded-3xl justify-center items-center font-bold bg-pink-300 m-2 text-center text-2xl text-white' 
          href={{pathname:'/family copy/media/textMedia'}}>동영상</Link>
          <Link className='w-32 p-2 rounded-3xl justify-center items-center font-bold bg-pink-300 m-2 text-center text-2xl text-white' 
          href={{pathname:'/family copy/media/textMedia'}}>이미지</Link>
            <Link className='w-32 p-2 rounded-3xl justify-center items-center font-bold bg-sky-300 m-2 text-center text-2xl text-white' 
          href={{pathname:'/family copy/media/textMedia'}}>텍스트</Link>
        </View>
    )
}

export default selectMedia;