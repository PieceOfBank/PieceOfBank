import { Link, useRouter } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, Image, TouchableOpacity } from 'react-native';
import React, { useState } from 'react';
import { styled } from 'nativewind';
import { getAccount, loginUser } from "../src/services/api";
import Toast from "react-native-toast-message";
import { mediaPost, createAccount } from "../src/services/api";
import AsyncStorage from "@react-native-async-storage/async-storage";
// MainPage

export default function LoginScreen() {

const router = useRouter()

// 로그인 요청 보낼 정보
const [id, setId] = useState('');
const [password, setPassword] = useState('');


/* 로그인 실행 */
const loginTry = async() => {
  try {
    const JsonData = {
      userId: id,
      password: password,
    }
    const response = await loginUser(JsonData);
    console.log(response?.data)
    console.log(AsyncStorage.getItem("userKey"))
    Toast.show({
      type: 'success',
      text1: '로그인 성공!',
      text2: '로그인에 성공하였습니다'
    })
    // 구독 관계 1이면 보호자 페이지로 이동
    if (response?.data['subscriptionType'] == 1) {
      router.push('/family copy/familyMain')

    } 
    // 구독 관계 2이면 피보호자 페이지 이동
    else if (response?.data['subscriptionType'] == 2) {
      router.push('/ward/main')
    }
  }
  catch (error) {
    Toast.show({
      type: 'error',
      text1: '로그인 실패!',
      text2: '입력 정보를 다시 확인해주세요'
    })
    console.log(`에러: ${error}`)
  }
} 
const textPost = async() => {
  try{
      const transNo = 73869
      const type = 'VOICE'
      const content = '컨텐츠'
      const JsonData = {
          file:'Thankyou!'
        }
        const response = await mediaPost(transNo, type, content, JsonData);
        console.log(response)
        Toast.show({
          type: 'success',
          text1: '미디어 보내기 성공!',
          text2: '미디어가 정상적으로 보내졌습니다'
        })
      router.push('/family copy/familyMain')
  }
  catch(error){
      console.log(error)
      Toast.show({
          type: 'error',
          text1: '미디어 보내기 실패',
          text2: '전송에 실패했습니다. 다시 확인해주세요.'
        })
  }
}


const accountGo = async() => {
  try{

      const JsonData = {
        "accountTypeUniqueNo": "001-1-e7e3f77e997c46"
      }
        const response = await createAccount(JsonData);
        console.log(response)
        Toast.show({
          type: 'success',
          text1: '계좌 생성 성공!',
        })
      // router.push('/family copy/familyMain')
  }
  catch(error){
      console.log(error)
      Toast.show({
          type: 'error',
          text1: '실패',
        })
  }
}

const moneyCheck = async() => {
  try{

      const JsonData = {
        "accountNo": "0013863865815253"
      }
        const response = await getAccount(JsonData);
        console.log(response)
        Toast.show({
          type: 'success',
          text1: '계좌 조회 성공!',
        })
      // router.push('/family copy/familyMain')
  }
  catch(error){
      console.log(error)
      Toast.show({
          type: 'error',
          text1: '실패',
        })
  }
}

  return (
    <ImageBackground source={require('../src/assets/POBbackGround.png')}
      className="flex-1"
    >
      <View className='flex-1 justify-center items-center'>
        <Image source={require('../src/assets/SmallLogo.png')} className='w-40 h-40'/>
        <SafeAreaView>
          <Text className='my-2'>아이디</Text>
          <TextInput className='my-1 pl-3 py-1 w-60 bg-white border-gray-500 rounded-2xl' 
            onChangeText={(id) => setId(id)}>
          </TextInput>
          <Text className='my-2'>비밀번호</Text>
          <TextInput className='my-1 pl-3 py-1 w-60 bg-white border-gray-500 rounded-2xl' 
            secureTextEntry={true} 
            keyboardType='numeric'
            onChangeText={(password) => setPassword(password)}>
          </TextInput>
        </SafeAreaView>
        <TouchableOpacity 
            className="my-4 w-28 bg-blue-500 h-8 rounded-3xl justify-center items-center"
            onPress={loginTry}>
                <Text className='text-white'>로그인</Text>
        </TouchableOpacity>
        <TouchableOpacity 
            className="mb-4 w-28 bg-blue-500 h-8 rounded-3xl justify-center items-center"
            onPress={() => router.push('/signup/page1')}>
                <Text className='text-white'>회원가입</Text>
        </TouchableOpacity>
        <View className='flex-row'>
          <Link className='my-2 text-xl' href={'/ward/main'}>피보호자</Link>
          <Text className='my-2 text-xl'> | </Text>
          <Link className="my-2 text-xl" href={`/family copy/familyMain`}>보호자</Link>
        </View>
        <TouchableOpacity 
            className="mb-4 w-28 bg-blue-500 h-8 rounded-3xl justify-center items-center"
            onPress={accountGo}>
                <Text className='text-white'>계좌생성</Text>
        </TouchableOpacity>
        <TouchableOpacity 
            className="mb-4 w-28 bg-blue-500 h-8 rounded-3xl justify-center items-center"
            onPress={moneyCheck}>
                <Text className='text-white'>계좌확인</Text>
        </TouchableOpacity>
      </View>
    </ImageBackground>
  );
}