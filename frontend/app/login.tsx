import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView } from 'react-native';
import React, { useState } from 'react';
// MainPage

const login = () => {

  const [id, setId] = useState('');
  const [password, setPassword] = useState('');

  return (
    <ImageBackground source={require('../src/assets/POBbackGround.png')}
      className="flex-1"
    >
      
      <View className='flex-1 justify-center items-center'>
        <Text className='text-2xl m-2'>POB</Text>
        <Text className='text-xl'>로그인</Text>
        <SafeAreaView>
          <Text className='my-2'>아이디</Text>
          <TextInput className='my-1 w-60 bg-white' 
            onChangeText={(id) => setId(id)}>
          </TextInput>
          <Text className='my-2'>비밀번호</Text>
          <TextInput className='my-1 w-60 bg-white' 
            secureTextEntry={true} 
            onChangeText={(password) => setPassword(password)}>
          </TextInput>
        </SafeAreaView>
        <Link className='my-2' href={'/mainpage'}>로그인</Link>
        <Link className='my-2' href={'/signup/page1'}>회원가입</Link>
        <Link className='my-2' href={'/signup/page3'}>테스트중</Link>
      </View>
    </ImageBackground>
  );
}

export default login;
  