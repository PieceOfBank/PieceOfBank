import { Link, useRouter } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, Image, TouchableOpacity } from 'react-native';
import React, { useState } from 'react';
import { styled } from 'nativewind';
import { createAccount, registUser } from "../src/services/api";

// MainPage

export default function LoginScreen() {

  const [id, setId] = useState('');
  const [password, setPassword] = useState('');

  const router = useRouter()

/* */

  const test = async () => {
    try {

      const JsonData = {
        'accountTypeUniqueNo' : '12357',
      }
      
      const response = await createAccount(JsonData);

      console.log(response)

    }
    catch (error) {

      console.log(error)

    }
  }
/* */
  return (
    <ImageBackground source={require('../src/assets/POBbackGround.png')}
      className="flex-1"
    >
      <View className='flex-1 justify-center items-center'>
        <Image source={require('../src/assets/SmallLogo.png')} className='w-40 h-40'/>
        <SafeAreaView>
          <Text className='my-2'>아이디</Text>
          <TextInput className='my-1 w-60 bg-white border-gray-500 rounded-2xl' 
            onChangeText={(id) => setId(id)}>
          </TextInput>
          <Text className='my-2'>비밀번호</Text>
          <TextInput className='my-1 w-60 bg-white border-gray-500 rounded-2xl' 
            secureTextEntry={true} 
            onChangeText={(password) => setPassword(password)}>
          </TextInput>
        </SafeAreaView>
        <TouchableOpacity 
            className="my-4 w-28 bg-blue-500 h-8 rounded-3xl justify-center items-center"
            onPress={() => router.push('/mainpage')}>
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
            onPress={test}>
                <Text className='text-white'>테스트</Text>
        </TouchableOpacity>

      </View>
    </ImageBackground>
  );
}