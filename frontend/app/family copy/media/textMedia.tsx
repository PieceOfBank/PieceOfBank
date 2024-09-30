import { Link, useRouter, useLocalSearchParams } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, Alert, Button, TouchableOpacity } from 'react-native';
import React, { useState, useEffect } from 'react';
import Toast from "react-native-toast-message";

const textMedia = () => {

    const [message, setMessage] = useState('')

    return(
        <View className='flex-1 justify-center items-center'>
            <Text className='text-2xl'>메세지를 작성해주세요</Text>
            <SafeAreaView>
            <Text className='my-2'>메세지 내용</Text>
            <TextInput className='bg-white w-64 h-32 rounded-lg px-2' onChangeText={(message) => setMessage(message)}></TextInput>
            </SafeAreaView>
            <Link className='w-32 p-2 rounded-3xl justify-center items-center font-bold bg-sky-300 m-2 text-center text-2xl text-white' 
                href={{pathname:'/family copy/familyMain'}}>보내기</Link>
        </View>
    )
}

export default textMedia;