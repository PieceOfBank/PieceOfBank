import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView } from 'react-native';
import React, { useState } from 'react';
import { styled } from 'nativewind';
// MainPage

export default function LoginScreen() {

  const [id, setId] = useState('');
  const [password, setPassword] = useState('');

  return (
    <ImageBackground source={require('../src/assets/POBbackGround.png')}
      className="w-full h-full"
      resizeMode="contain"
    >
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
    <Text style={{fontSize:20}}>POB</Text>
    <View style={{height:5}}></View>
    <Text style={{fontSize:20}}>로그인</Text>
      <SafeAreaView>
      <View style={{height:10}}></View>
      <Text>아이디</Text>
      <View style={{height:5}}></View>
      <TextInput style={{backgroundColor:'white', width:250,}} 
        onChangeText={(id) => setId(id)}>
      </TextInput>
      <View style={{height:10}}></View>
      <Text>비밀번호</Text>
      <View style={{height:5}}></View>
      <TextInput style={{backgroundColor:'white', width:250,}}
        secureTextEntry={true} 
        onChangeText={(password) => setPassword(password)}>
      </TextInput>
      </SafeAreaView>
      <View style={{height:10}}></View>
      <Link href={'/mainpage'}>로그인</Link>
      <View style={{height:10}}></View>
      <Link href={'/signup/page1'}>회원가입</Link>
      
      </View>
      </ImageBackground>
  );
}